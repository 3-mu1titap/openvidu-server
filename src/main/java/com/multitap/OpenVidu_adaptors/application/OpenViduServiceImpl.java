package com.multitap.OpenVidu_adaptors.application;

import com.multitap.OpenVidu_adaptors.entity.ViduSession;
import com.multitap.OpenVidu_adaptors.infrastructure.ViduSessionRepository;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenViduServiceImpl implements OpenViduService {

    private final ViduSessionRepository viduSessionRepository;
    private final OpenVidu openvidu;

    @Override
    public String initializeSession(String mentoringSessionUuid) {

//        ViduSession bySessionId = viduSessionRepository.findBySessionId(mentoringSessionUuid);
//
//        if (bySessionId != null) {
//            return bySessionId.getSessionId();
//        }

        try {
            // 세션 속성 설정 (customSessionId에 mentoringSessionUuid 사용)
            SessionProperties properties = new SessionProperties.Builder()
                    .customSessionId(mentoringSessionUuid)
                    .build();

            log.info("Creating session with properties: {}", properties);

            // 세션 생성
            Session session = openvidu.createSession(properties);

            log.info("Session created successfully with ID: {}", session.getSessionId());

            return viduSessionRepository.save(toViduSession(session)).getSessionId();

        } catch (OpenViduHttpException e) {
            log.error("Failed to create session due to OpenVidu HTTP error: {}", e.getMessage());
            throw new RuntimeException("Unable to create session due to OpenVidu HTTP error", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating session: {}", e.getMessage());
            throw new RuntimeException("Unexpected error occurred while creating session", e);
        }
    }

    private static ViduSession toViduSession(Session session) {
        return ViduSession.builder()
                .sessionId(session.getSessionId())
                .build();
    }

    @Override
    public String createConnection(String mentoringSessionUuid, String userUuid) {

        try {
            // Find the session
            Session session = openvidu.getActiveSession(mentoringSessionUuid);

            log.info("seesion = {}", session.getSessionId());

            if (session == null) {
                log.warn("Session with UUID {} not found", mentoringSessionUuid);
                throw new IllegalArgumentException("Session not found");
            }

            // Set connection properties
            ConnectionProperties properties = new ConnectionProperties.Builder()
                    .data(userUuid) // Add user information
                    .role(OpenViduRole.PUBLISHER) // Set role (e.g., PUBLISHER)
                    .build();

            log.info("Creating connection with properties: {}", properties);

            // Create connection
            Connection connection = session.createConnection(properties);
            log.info("Connection created successfully for user UUID {} with token: {}", userUuid, connection.getToken());

            return connection.getToken();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
            log.error("Failed to create connection due to OpenVidu HTTP error: {}", e.getMessage());
            throw new RuntimeException("Unable to create connection due to OpenVidu HTTP error", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating connection: {}", e.getMessage());
            throw new RuntimeException("Unexpected error occurred while creating connection", e);
        }
    }

}
