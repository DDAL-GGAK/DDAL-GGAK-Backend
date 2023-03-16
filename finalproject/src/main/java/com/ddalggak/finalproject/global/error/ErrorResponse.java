package com.ddalggak.finalproject.global.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private final int status;
    private final String message;

    /*
     * 중요한 비즈니스 로직일 경우 메시지를 숨기기 위해 에러코드로 작성
     */

    public static ResponseEntity<ErrorResponse> of(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .message(errorCode.getErrorCode())
                        .build()
                );
    }

    /*
     * 중요하지 않은 비즈니스 로직일 경우 메시지를 노출해도 상관없음
     */

    public static ResponseEntity<ErrorResponse> from(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> from(ErrorCode errorCode, String message){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .message(message)
                        .build()
                );
    }

}