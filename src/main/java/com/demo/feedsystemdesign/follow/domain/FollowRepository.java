package com.demo.feedsystemdesign.follow.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByTargetId(Long targetId);

    List<Follow> findAllBySourceId(Long sourceId);
}
