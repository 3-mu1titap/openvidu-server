package com.multitap.OpenVidu_adaptors.presentation;

import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OpenViduControllerV2 {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
        System.out.println("init");
    }

    //test
    @GetMapping("/api/test")
    public String test() {
        System.out.println("성공!!");
        return "testtest";
    }

    /**
     * @param params The Session properties
     * @return The Session ID
     */
    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(
            @RequestHeader ("userUuid") String userUuid,
            @RequestParam String mentoringSessionUuid,
            @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        System.out.println("initialize session test");

        if (mentoringSessionUuid == null || mentoringSessionUuid.isEmpty()) {
            return new ResponseEntity<>("Mentoring session UUID is required", HttpStatus.BAD_REQUEST);
        }

        // 세션 중복 확인
        Session existingSession = openvidu.getActiveSession(mentoringSessionUuid);
        if (existingSession != null) {
            return new ResponseEntity<>("Session already exists", HttpStatus.CONFLICT);
        }

//         세션 속성 설정 (customSessionId에 mentoringSessionUuid 사용)
        SessionProperties properties = new SessionProperties.Builder()
                .customSessionId(mentoringSessionUuid)
                .build();

        log.info("properties: {}", properties);

        // 세션 생성
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    /**
     * @param mentoringSessionUuid The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */
    @PostMapping("/api/sessions/{mentoringSessionUuid}/connections")
    public ResponseEntity<String> createConnection(
            @RequestHeader ("userUuid") String userUuid,
            @PathVariable("mentoringSessionUuid") String mentoringSessionUuid,
            @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        if (userUuid == null || userUuid.isEmpty()) {
            return new ResponseEntity<>("User UUID is required", HttpStatus.BAD_REQUEST);
        }

        Session session = openvidu.getActiveSession(mentoringSessionUuid);

        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }

        // 연결 속성 설정 (userUuid를 data 필드에 추가)
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .data(userUuid) // 사용자 식별 정보 포함
                .role(OpenViduRole.PUBLISHER) // 역할 설정 (예: PUBLISHER)
                .build();

        // 연결 생성
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }
}
