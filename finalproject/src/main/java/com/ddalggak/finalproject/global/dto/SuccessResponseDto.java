package com.ddalggak.finalproject.global.dto;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class SuccessResponseDto {

    private final int status;

    private final String message;

    public static ResponseEntity<SuccessResponseDto> toResponseEntity(SuccessCode successCode){
        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(SuccessResponseDto.builder()
                        .status(successCode.getHttpStatus().value())
                        .message(successCode.getDetail())
                        .build()
                );
    }

}
