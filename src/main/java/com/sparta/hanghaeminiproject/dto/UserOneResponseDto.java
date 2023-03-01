package com.sparta.hanghaeminiproject.dto;


import com.sparta.hanghaeminiproject.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserOneResponseDto {


    private Long id;

    private String nickname;

    private String username;

    private String part;

    private String introduction;

    public static UserOneResponseDto of(User user){
        return UserOneResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .part(user.getPart())
                .build();
    }
}
