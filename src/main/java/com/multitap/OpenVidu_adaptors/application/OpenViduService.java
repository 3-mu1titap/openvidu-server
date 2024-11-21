package com.multitap.OpenVidu_adaptors.application;

import io.livekit.server.AccessToken;
import io.livekit.server.RoomJoin;
import io.livekit.server.RoomName;
import io.openvidu.java.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenViduService {

    @Value("${livekit.api.key}")
    private String LIVEKIT_API_KEY;

    @Value("${livekit.api.secret}")
    private String LIVEKIT_API_SECRET;

    private final OpenVidu openVidu;

    public OpenViduService(
            @Value("${openvidu.url}") String openViduUrl,
            @Value("${openvidu.secret}") String openViduSecret) {
        this.openVidu = new OpenVidu(openViduUrl, openViduSecret);
    }

    // 세션 생성
    public String createSession() throws OpenViduJavaClientException, OpenViduHttpException {
        // 새로운 세션 생성
        Session session = openVidu.createSession();
        return session.getSessionId();
    }

    // 사용자 UUID와 세션 UUID를 기반으로 토큰 생성
    public String generateToken(String mentoringSessionUuid, String userUuid) {

        if (mentoringSessionUuid == null || userUuid == null) {
            throw new IllegalArgumentException("roomName and participantName are required");
        }

        // Create AccessToken
        AccessToken token = new AccessToken(LIVEKIT_API_KEY, LIVEKIT_API_SECRET);
        token.setName(userUuid);
        token.setIdentity(userUuid);
        token.addGrants(new RoomJoin(true), new RoomName(mentoringSessionUuid));

        return token.toJwt();
    }
}
