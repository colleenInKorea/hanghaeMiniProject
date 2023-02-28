package com.sparta.hanghaeminiproject.entity;

import com.sparta.hanghaeminiproject.dto.SignupRequestDto;
import com.sparta.hanghaeminiproject.dto.UserRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "users")
@ToString

public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String introduction;

    @Column(nullable = true)
    private String part;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User (SignupRequestDto requestDto){
        this.nickname = requestDto.getNickname();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        role = requestDto.isAdminCheck() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;
    }

    public void update(String part, String introduction) {
        this.introduction = introduction;
        this.part = part;
    }
}
