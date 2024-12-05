//package com.multitap.OpenVidu_adaptors.application;
//
//import io.livekit.server.AccessToken;
//import io.livekit.server.RoomJoin;
//import io.livekit.server.RoomName;
//import io.livekit.server.WebhookReceiver;
//import livekit.LivekitWebhook;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class OpenViduService {
//
//    @Value("${livekit.api.key}")
//    private String LIVEKIT_API_KEY;
//
//    @Value("${livekit.api.secret}")
//    private String LIVEKIT_API_SECRET;
//
//    // AccessToken 생성
//    public String generateToken(String mentoringSessionUuid, String userUuid) {
//        if (mentoringSessionUuid == null || userUuid == null) {
//            throw new IllegalArgumentException("SessionId and userUuid are required");
//        }
//
//        // 세션 ID와 사용자 UUID를 사용하여 AccessToken 생성
//        AccessToken token = new AccessToken(LIVEKIT_API_KEY, LIVEKIT_API_SECRET);
//        token.setName(userUuid);
//        token.setIdentity(userUuid);
//        token.addGrants(new RoomJoin(true), new RoomName(mentoringSessionUuid));
//
//        return token.toJwt();  // 생성된 토큰 반환
//    }
//
//
//    public void receiveWebhook(String authHeader, String body) {
//        WebhookReceiver webhookReceiver = new WebhookReceiver(LIVEKIT_API_KEY, LIVEKIT_API_SECRET);
//        try {
//            LivekitWebhook.WebhookEvent event = webhookReceiver.receive(body, authHeader);
//            System.out.println("LiveKit Webhook: " + event.toString());
//        } catch (Exception e) {
//            System.err.println("Error validating webhook event: " + e.getMessage());
//        }
//    }
//}
