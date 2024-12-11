package com.multitap.OpenVidu_adaptors.presentation;

import com.multitap.OpenVidu_adaptors.application.OpenViduService;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/openvidu")
public class OpenViduController {

    private final OpenViduService openViduService;

    /**
     * @param params The Session properties
     * @return The Session ID
     */
    @PostMapping("/session")
    public ResponseEntity<String> initializeSession(
            @RequestParam String mentoringSessionUuid,
            @RequestBody(required = false) Map<String, Object> params) {

        log.info("Received request to initialize session with UUID: {}", mentoringSessionUuid);

        try {
            String sessionId = openViduService.initializeSession(mentoringSessionUuid);
            return new ResponseEntity<>(sessionId, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param mentoringSessionUuid The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */
    @PostMapping("/session/{mentoringSessionUuid}/connection")
    public ResponseEntity<String> createConnection(
            @RequestHeader ("userUuid") String userUuid,
            @PathVariable("mentoringSessionUuid") String mentoringSessionUuid,
            @RequestBody(required = false) Map<String, Object> params) {

        log.info("Received request to create connection for session UUID: {}", mentoringSessionUuid);
        log.info("Received request to create connection for user UUID: {}", userUuid);

        try {
            String token = openViduService.createConnection(mentoringSessionUuid, userUuid);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error occurred: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
