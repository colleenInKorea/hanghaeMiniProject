package com.sparta.hanghaeminiproject.dto;

import com.sparta.hanghaeminiproject.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;

    private String nickname;

    private String username;

    private String part;

    private String introduction;

    @Builder
    public UserResponseDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.part = user.getPart();
        this.introduction = user.getIntroduction();
    }

}
