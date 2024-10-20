package com.demo.feedsystemdesign.infra.cache.domain;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class UserInfoCache implements Serializable {

    @Serial
    private static final long serialVersionUID = -6375540463340353191L;

    private String nickname;
    private String profileImageUrl;
    private String createdAt;
    private String modifiedAt;

}
