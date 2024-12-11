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
public class ViduSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("세션 ID")
    @Column(nullable = false)
    private String sessionId;

    @Builder
    public ViduSession(Long id, String sessionId) {
        this.id = id;
        this.sessionId = sessionId;
    }

}
