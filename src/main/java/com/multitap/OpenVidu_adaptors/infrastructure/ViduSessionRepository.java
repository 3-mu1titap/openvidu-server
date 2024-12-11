package com.multitap.OpenVidu_adaptors.infrastructure;

import com.multitap.OpenVidu_adaptors.entity.ViduSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViduSessionRepository extends JpaRepository<ViduSession, Long> {
    ViduSession findBySessionId(String sessionId);
}
