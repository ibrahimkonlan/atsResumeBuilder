document.addEventListener('DOMContentLoaded', () => {
    // Current User State
    let currentUser = null;
    const USER_ID = 1; // Hardcoded for prototype

    // DOM Elements - Tabs
    const tabProfile = document.getElementById('tabProfile');
    const tabAnalyzer = document.getElementById('tabAnalyzer');
    const profileSection = document.getElementById('profileSection');
    const analyzerSection = document.getElementById('analyzerSection');
    const applySection = document.getElementById('applySection');

    // Profile Form Elements
    const profileName = document.getElementById('profileName');
    const profileEmail = document.getElementById('profileEmail');
    const profileSkills = document.getElementById('profileSkills');
    const profileExperience = document.getElementById('profileExperience');
    const profileEducation = document.getElementById('profileEducation');
    const saveProfileBtn = document.getElementById('saveProfileBtn');

    // Job Analyzer Elements
    const analyzeBtn = document.getElementById('analyzeBtn');
    const jobDescriptionInput = document.getElementById('jobDescription');
    const analysisCard = document.getElementById('analysisCard');
    
    // Auto Apply Elements
    const autoApplyBtn = document.getElementById('autoApplyBtn');
    const jobUrlInput = document.getElementById('jobUrl');
    const applyEmailInput = document.getElementById('applyEmail');
    const applyMessage = document.getElementById('applyMessage');

    // Fetch initial user profile on load
    fetchUserProfile();

    // Tab Switching Logic
    tabProfile.addEventListener('click', () => {
        tabProfile.classList.add('active');
        tabAnalyzer.classList.remove('active');
        profileSection.style.display = 'block';
        analyzerSection.style.display = 'none';
        applySection.style.display = 'none';
    });

    tabAnalyzer.addEventListener('click', () => {
        tabAnalyzer.classList.add('active');
        tabProfile.classList.remove('active');
        analyzerSection.style.display = 'block';
        applySection.style.display = 'block';
        profileSection.style.display = 'none';
    });

    async function fetchUserProfile() {
        try {
            const response = await fetch(`/api/user/${USER_ID}`);
            if (response.ok) {
                currentUser = await response.json();
                populateProfileForm(currentUser);
                renderResumePreview(currentUser);
            } else {
                console.error("Failed to load user profile");
            }
        } catch (error) {
            console.error("Error fetching user profile:", error);
        }
    }

    function populateProfileForm(user) {
        profileName.value = user.name || '';
        profileEmail.value = user.email || '';
        profileSkills.value = (user.skills || []).join(', ');
        profileExperience.value = (user.experience || []).join('\n');
        profileEducation.value = (user.education || []).join('\n');
    }

    // Save Profile Logic
    saveProfileBtn.addEventListener('click', async () => {
        const updatedUser = {
            id: currentUser ? currentUser.id : USER_ID,
            name: profileName.value.trim(),
            email: profileEmail.value.trim(),
            skills: profileSkills.value.split(',').map(s => s.trim()).filter(s => s),
            experience: profileExperience.value.split('\n').map(e => e.trim()).filter(e => e),
            education: profileEducation.value.split('\n').map(e => e.trim()).filter(e => e)
        };

        saveProfileBtn.disabled = true;
        saveProfileBtn.textContent = 'Saving...';

        try {
            const response = await fetch('/api/user', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedUser)
            });

            if (response.ok) {
                currentUser = await response.json();
                renderResumePreview(currentUser);
                alert("Profile saved successfully!");
            } else {
                alert("Failed to save profile.");
            }
        } catch (error) {
            console.error("Error saving profile:", error);
            alert("Error saving profile.");
        } finally {
            saveProfileBtn.disabled = false;
            saveProfileBtn.textContent = 'Save Profile';
        }
    });

    analyzeBtn.addEventListener('click', async () => {
        const jd = jobDescriptionInput.value.trim();
        if (!jd) {
            alert("Please paste a job description first.");
            return;
        }

        analyzeBtn.disabled = true;
        analyzeBtn.textContent = 'Analyzing...';

        try {
            const response = await fetch('/api/analyze-job', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    jobDescription: jd,
                    userId: USER_ID
                })
            });

            if (response.ok) {
                const analysisResult = await response.json();
                displayAnalysisResults(analysisResult);
                
                // Re-render resume with highlights
                renderResumePreview(currentUser, analysisResult.matchedSkills);
            }
        } catch (error) {
            console.error("Error analyzing job:", error);
            alert("An error occurred during analysis.");
        } finally {
            analyzeBtn.disabled = false;
            analyzeBtn.textContent = 'Analyze & Match';
        }
    });

    function displayAnalysisResults(result) {
        analysisCard.style.display = 'block';
        
        // Update Score
        document.getElementById('matchScore').textContent = result.matchScore;
        const deg = (result.matchScore / 100) * 360;
        document.querySelector('.score-circle').style.setProperty('--score-deg', `${deg}deg`);

        // Update Matched Skills
        const matchedContainer = document.getElementById('matchedKeywords');
        matchedContainer.innerHTML = '';
        if (result.matchedSkills.length === 0) {
            matchedContainer.innerHTML = '<span class="text-sm">None</span>';
        } else {
            result.matchedSkills.forEach(skill => {
                const span = document.createElement('span');
                span.className = 'chip matched';
                span.textContent = skill;
                matchedContainer.appendChild(span);
            });
        }

        // Update Missing Skills
        const missingContainer = document.getElementById('missingKeywords');
        missingContainer.innerHTML = '';
        if (result.missingSkills.length === 0) {
            missingContainer.innerHTML = '<span class="text-sm">None</span>';
        } else {
            result.missingSkills.forEach(skill => {
                const span = document.createElement('span');
                span.className = 'chip missing';
                span.textContent = skill;
                missingContainer.appendChild(span);
            });
        }
    }

    function renderResumePreview(user, matchedKeywords = []) {
        document.getElementById('resName').textContent = user.name;
        document.getElementById('resEmail').textContent = user.email;

        // Render Skills
        let skillsHtml = user.skills.join(' • ');
        document.getElementById('resSkills').innerHTML = highlightText(skillsHtml, matchedKeywords);

        // Render Experience
        const expContainer = document.getElementById('resExperience');
        expContainer.innerHTML = '';
        user.experience.forEach(exp => {
            const p = document.createElement('p');
            p.className = 'experience-item';
            p.innerHTML = highlightText(exp, matchedKeywords);
            expContainer.appendChild(p);
        });

        // Render Education
        const eduContainer = document.getElementById('resEducation');
        eduContainer.innerHTML = '';
        user.education.forEach(edu => {
            const p = document.createElement('p');
            p.className = 'education-item';
            p.innerHTML = highlightText(edu, matchedKeywords); // Highlighting keywords in education as well
            eduContainer.appendChild(p);
        });
    }

    function highlightText(text, keywords) {
        if (!keywords || keywords.length === 0) return text;
        
        let highlighted = text;
        // Case-insensitive replace for each keyword
        keywords.forEach(keyword => {
            const regex = new RegExp(`\\b(${keyword})\\b`, 'gi');
            highlighted = highlighted.replace(regex, '<span class="highlight">$1</span>');
        });
        return highlighted;
    }

    // Auto Apply Logic
    autoApplyBtn.addEventListener('click', async () => {
        const url = jobUrlInput.value.trim();
        const email = applyEmailInput.value.trim();

        if (!url || !email) {
            alert("Please enter both Job URL and your Email.");
            return;
        }

        autoApplyBtn.disabled = true;
        autoApplyBtn.textContent = 'Applying...';

        try {
            const response = await fetch('/api/auto-apply', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ jobUrl: url, email: email })
            });

            if (response.ok) {
                const result = await response.json();
                applyMessage.style.display = 'block';
                applyMessage.style.color = 'var(--success)';
                applyMessage.textContent = result.message;
            }
        } catch (error) {
            console.error("Auto apply error:", error);
            applyMessage.style.display = 'block';
            applyMessage.style.color = 'var(--danger)';
            applyMessage.textContent = 'Failed to submit application.';
        } finally {
            autoApplyBtn.disabled = false;
            autoApplyBtn.textContent = 'One-Click Apply';
            setTimeout(() => {
                applyMessage.style.display = 'none';
            }, 5000);
        }
    });
});
