-- Insert sample UserProfile
INSERT INTO user_profile (name, email) VALUES ('John Doe', 'john.doe@example.com');

-- Insert skills
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'Java');
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'Spring Boot');
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'SQL');
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'REST API');
INSERT INTO user_profile_skills (user_profile_id, skills) VALUES (1, 'Git');

-- Insert experience
INSERT INTO user_profile_experience (user_profile_id, experience) VALUES (1, 'Software Engineer at TechCorp (2020-Present): Developed RESTful APIs using Java and Spring Boot. Improved database query performance by 20% using SQL optimizations.');
INSERT INTO user_profile_experience (user_profile_id, experience) VALUES (1, 'Junior Developer at WebSolutions (2018-2020): Assisted in building web applications and writing unit tests.');

-- Insert education
INSERT INTO user_profile_education (user_profile_id, education) VALUES (1, 'B.S. in Computer Science, University of Technology (2014-2018)');
