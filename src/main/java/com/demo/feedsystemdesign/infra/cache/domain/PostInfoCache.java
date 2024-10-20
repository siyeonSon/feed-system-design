package com.demo.feedsystemdesign.infra.cache.domain;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class PostInfoCache implements Serializable {

    @Serial
    private static final long serialVersionUID = -7226343960274536587L;

    private String content;
    private Long userId;
    private String createdAt;
    private String modifiedAt;

}
