# ATS-Optimized Resume Builder Prototype

This is a working prototype of a web-based tool that helps users create ATS-friendly resumes tailored to specific job descriptions. 

## Features
- **Job Analysis**: Extracts keywords from a job description and matches them against your skills.
- **Resume Preview**: Generates a live, ATS-friendly HTML preview of your resume.
- **Keyword Highlighting**: Automatically highlights matched keywords in your skills, experience, and education sections.
- **Auto-Apply Mock**: Simulates a 1-click apply process.
- **PDF Export**: Allows you to print/save the resume cleanly as a PDF using your browser's print functionality.

## Tech Stack
- **Backend**: Java 17, Spring Boot, Spring Data JPA, H2 Database (in-memory)
- **Frontend**: HTML5, Vanilla CSS, Vanilla JavaScript

## Project Structure
- `src/main/java/.../model/`: Domain models (UserProfile, JobPosting, AnalysisResponse).
- `src/main/java/.../controller/`: REST API endpoints.
- `src/main/java/.../service/`: Business logic for keyword matching.
- `src/main/resources/static/`: Frontend static files (`index.html`, `app.js`, `styles.css`).
- `src/main/resources/data.sql`: Sample mock data populated on startup.

## How to Run
To run this application, you will need Java 17+ and Maven installed.

1. Open a terminal in the project root directory.
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Open your browser and navigate to: [http://localhost:8080](http://localhost:8080)

## Testing the Flow
1. Once the page loads, the app automatically fetches a mock user profile (John Doe) from the database and renders it in the preview pane.
2. **Job Analysis**: Copy a sample job description (e.g., "Looking for a software engineer with Java, Spring Boot, and Docker experience.") and paste it into the textarea on the left. Click **Analyze & Match**.
3. **Review Matches**: The system will display your match score, found keywords, and missing keywords. It will simultaneously highlight these matched keywords in yellow on the resume preview.
4. **Export**: Click **Download PDF** to see the print-ready, clean ATS format.
5. **Auto-Apply**: Type a job URL and your email into the Auto-Apply section and click the button to see a mock successful application.
