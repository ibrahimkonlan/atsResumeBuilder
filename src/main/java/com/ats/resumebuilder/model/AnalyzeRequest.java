package com.ats.resumebuilder.model;

public class AnalyzeRequest {
    private String jobDescription;
    private Long userId;

    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
