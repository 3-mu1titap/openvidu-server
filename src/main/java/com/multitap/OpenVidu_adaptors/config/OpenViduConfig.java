package com.multitap.OpenVidu_adaptors.config;

import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenViduConfig {

    @Bean
    public OpenVidu openvidu(@Value("${OPENVIDU_URL}") String openviduUrl,
                             @Value("${OPENVIDU_SECRET}") String openviduSecret) {
        return new OpenVidu(openviduUrl, openviduSecret);
    }
}
