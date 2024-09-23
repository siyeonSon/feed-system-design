package com.demo.feedsystemdesign.infra.cache.domain;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class PostInfoCache implements Serializable {

    @Serial
    private static final long serialVersionUID = -7226343960274536587L;

    private String content;
    private Long userId;
    private String createdAt;
    private String modifiedAt;

}
