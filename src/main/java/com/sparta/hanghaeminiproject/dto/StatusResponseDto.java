package com.sparta.hanghaeminiproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusResponseDto<T> {

    private int statusCode;
    private T result;


    public StatusResponseDto(int statusCode, T result){
        this.statusCode = statusCode;
        this.result = result;
    }

    public static <T> StatusResponseDto<T> success(T result){
        return new StatusResponseDto<>(200, result);
    }


    // 좋아요 boolean 리턴용
    public static <T> StatusResponseDto<T> likeSuccess(T result, boolean isLike){
        return new StatusResponseDto<>(200, result, isLike);
    }


    public static <T> StatusResponseDto<T> fail(int statusCode, T result){
        return new StatusResponseDto<>(statusCode, result);
    }

//    public static <T> StatusResponseDto<T> isLike(int statusCode, T result, boolean isLike){
//        return new StatusResponseDto<>(statusCode, result, isLike);
//    }
}
