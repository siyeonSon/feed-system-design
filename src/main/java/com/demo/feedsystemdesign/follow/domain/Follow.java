package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    private long sourceId;
    private long targetId;

    public Follow(long sourceId, long targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

}

