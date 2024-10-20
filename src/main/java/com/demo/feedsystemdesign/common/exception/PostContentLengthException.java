package com.demo.feedsystemdesign.common.exception;

import lombok.Getter;

public class PostContentLengthException extends BusinessException {
    public PostContentLengthException(ErrorCode errorCode) {
        super(errorCode);
    }
}