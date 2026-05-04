package com.ats.resumebuilder.service;

import com.ats.resumebuilder.model.AnalysisResponse;
import com.ats.resumebuilder.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ATSAnalyzerService {

    // A very basic keyword extraction mechanism for prototype purposes
    public List<String> extractKeywords(String jobDescription) {
        if (jobDescription == null) return new ArrayList<>();
        
        // Convert to lowercase and split by non-word characters
        String[] words = jobDescription.toLowerCase().split("\\W+");
        
        // Define a simple list of common tech skills to look for
        List<String> commonSkills = Arrays.asList(
            "java", "spring", "boot", "python", "javascript", "react", "angular", "vue",
            "html", "css", "sql", "mysql", "postgresql", "mongodb", "aws", "azure", "gcp",
            "docker", "kubernetes", "git", "ci/cd", "agile", "scrum", "rest", "api",
            "microservices", "hibernate", "jpa", "maven", "gradle", "node", "express"
        );
        
        return Arrays.stream(words)
                .filter(commonSkills::contains)
                .distinct()
                .collect(Collectors.toList());
    }

    public AnalysisResponse analyzeJob(String jobDescription, UserProfile userProfile) {
        List<String> jobKeywords = extractKeywords(jobDescription);
        
        List<String> userSkillsLower = userProfile.getSkills().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        for (String keyword : jobKeywords) {
            boolean matched = false;
            for (String userSkill : userSkillsLower) {
                if (userSkill.contains(keyword)) {
                    matchedSkills.add(keyword);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                missingSkills.add(keyword);
            }
        }

        int score = jobKeywords.isEmpty() ? 0 : (matchedSkills.size() * 100) / jobKeywords.size();

        AnalysisResponse response = new AnalysisResponse();
        response.setMatchedSkills(matchedSkills);
        response.setMissingSkills(missingSkills);
        response.setMatchScore(score);
        return response;
    }
}
