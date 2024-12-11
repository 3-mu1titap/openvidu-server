package com.multitap.OpenVidu_adaptors.infrastructure;

import com.multitap.OpenVidu_adaptors.entity.ViduToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ViduTokenRepository extends JpaRepository<ViduToken, Long> {

    // sessionId와 userUuid를 기반으로 조회
    @Query("SELECT vt FROM ViduToken vt WHERE vt.viduSession.sessionId = :sessionId AND vt.userUuid = :userUuid")
    ViduToken findByUserUuidAndSessionId(@Param("userUuid") String userUuid, @Param("sessionId") String sessionId);
}
