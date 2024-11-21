package com.multitap.OpenVidu_adaptors.presentation;

import com.multitap.OpenVidu_adaptors.application.OpenViduService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(
        origins = {"*"}
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/openvidu")
@Slf4j
public class OpenViduController {

    private final OpenViduService openViduService;

    @GetMapping("/")
    public Map<String, String> hello() {
        return Map.of("message", "Welcome to the OpenVidu Application!");
    }

    // 새로운 세션 생성
    @PostMapping("/create-session")
    public ResponseEntity<String> createSession() {
        try {
            String sessionId = openViduService.createSession();
            return ResponseEntity.ok(sessionId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 세션에서 토큰 생성
    @PostMapping("/generate-token")
    public ResponseEntity<Map<String, String>> generateToken(
            @RequestHeader ("userUuid") String userUuid,
            @RequestParam String mentoringSessionUuid) {
        log.info("Generating OpenVidu token1");
        try {
            String token = openViduService.generateToken(mentoringSessionUuid, userUuid);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            log.error("Error generating OpenVidu token", e);
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }
}
