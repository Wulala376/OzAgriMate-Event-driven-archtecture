package com.ozagrimate.platform.loginservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Map<String, String> mockOtps = new HashMap<>();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String farmId = request.get("farmId");
        mockOtps.put(farmId, "849201"); 
        return ResponseEntity.ok(Map.of(
            "status", "otp_sent",
            "expiresInSeconds", 300
        ));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> request) {
        String farmId = request.get("farmId");
        String otp = request.get("otp");
        
        if ("849201".equals(otp) && mockOtps.containsKey(farmId)) {
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            ));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired OTP"));
    }
}