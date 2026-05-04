package com.ats.resumebuilder.controller;

import com.ats.resumebuilder.model.*;
import com.ats.resumebuilder.repository.UserProfileRepository;
import com.ats.resumebuilder.service.ATSAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // For prototype, allow all origins
public class ATSController {

    @Autowired
    private ATSAnalyzerService analyzerService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long id) {
        Optional<UserProfile> profile = userProfileRepository.findById(id);
        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public ResponseEntity<UserProfile> saveUserProfile(@RequestBody UserProfile userProfile) {
        UserProfile savedProfile = userProfileRepository.save(userProfile);
        return ResponseEntity.ok(savedProfile);
    }

    @PostMapping("/analyze-job")
    public ResponseEntity<AnalysisResponse> analyzeJob(@RequestBody AnalyzeRequest request) {
        Optional<UserProfile> profileOpt = userProfileRepository.findById(request.getUserId());
        if (profileOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        AnalysisResponse response = analyzerService.analyzeJob(request.getJobDescription(), profileOpt.get());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auto-apply")
    public ResponseEntity<Map<String, String>> autoApply(@RequestBody AutoApplyRequest request) {
        // Mock Auto-Apply logic
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Successfully applied to " + request.getJobUrl() + " with email " + request.getEmail());
        return ResponseEntity.ok(response);
    }
}
