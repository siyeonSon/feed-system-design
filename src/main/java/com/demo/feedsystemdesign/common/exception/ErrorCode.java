package com.demo.feedsystemdesign.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "C_001", "서버에 문제가 발생하였습니다."),
    METHOD_NOT_ALLOWED(405, "C_002", "잘못된 HTTP Request Method 입니다."),
    INVALID_INPUT_VALUE(400, "C_003", "적절하지 않은 요청 값입니다."),
    INVALID_TYPE_VALUE(400, "C_004", "요청 값의 타입이 잘못되었습니다."),
    ENTITY_NOT_FOUND(400, "C_005", "지정한 엔티티를 찾을 수 없습니다."),

    USER_NOT_FOUND(404, "U_001", "존재하지 않는 사용자입니다."),

    POST_CONTENT_TOO_SHORT(400, "P_001", "게시물 내용이 짧습니다."),
    POST_CONTENT_TOO_LONG(400, "P_002", "게시물 내용이 너무 깁니다."),
    POST_NOT_FOUND(500, "P_003", "포스트가 존재하지 않습니다."),

    SELF_FOLLOWING(400, "F_001", "자신을 팔로우 할 수 없습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
