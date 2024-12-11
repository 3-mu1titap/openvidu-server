package com.multitap.OpenVidu_adaptors.application;

public interface OpenViduService {

    public String initializeSession(String mentoringSessionUuid);
    public String createConnection(String mentoringSessionUuid, String userUuid);
}
