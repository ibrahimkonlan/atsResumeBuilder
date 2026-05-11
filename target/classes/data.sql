-- ── Demo login account (use these credentials to try the app) ───────────
-- Email: demo@ats.com  |  Password: demo123
INSERT INTO app_user (full_name, email, password) VALUES ('Demo User', 'demo@ats.com', 'demo123');

-- ── Sample UserProfile (linked to the resume builder) ───────────────────
INSERT INTO user_profile (name, email) VALUES ('John Doe', 'john.doe@example.com');

-- Skills
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'Java');
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'Spring Boot');
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'SQL');
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'REST API');
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'Git');

-- Experience
INSERT INTO user_profile_experience (user_profile_id, experience) VALUES (1, 'Software Engineer at TechCorp (2020-Present): Developed RESTful APIs using Java and Spring Boot. Improved database query performance by 20% using SQL optimizations.');
INSERT INTO user_profile_experience (user_profile_id, experience) VALUES (1, 'Junior Developer at WebSolutions (2018-2020): Assisted in building web applications and writing unit tests.');

-- Education
INSERT INTO user_profile_education (user_profile_id, education) VALUES (1, 'B.S. in Computer Science, University of Technology (2014-2018)');
