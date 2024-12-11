package com.multitap.OpenVidu_adaptors.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Getter
@ToString
@Entity
@NoArgsConstructor
public class ViduToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("세션 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", referencedColumnName = "sessionId", nullable = false)
    private ViduSession viduSession;

    @Comment("유저 UUID")
    @Column(nullable = false)
    private String userUuid;

    @Comment("토큰")
    @Column(nullable = false)
    private String token;

    @Builder
    public ViduToken(Long id, ViduSession viduSession, String userUuid, String token) {
        this.id = id;
        this.viduSession = viduSession;
        this.userUuid = userUuid;
        this.token = token;
    }
}
