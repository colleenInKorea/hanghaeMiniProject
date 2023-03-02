package com.sparta.hanghaeminiproject.entity;

import com.sparta.hanghaeminiproject.dto.ProjectRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "project")
@SQLDelete(sql = "UPDATE project SET deleted = true WHERE id = ?")
// delete 구문 입력 시 추가로 delete 컬럼이 true로 변경되도록 쿼리를 날린다.
@Where(clause = "deleted = false")
// isDeleted의 디폴트 값은 false이다.
public class Project extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = true)
    private String imageUrl;
    @Column(nullable = true)
    private int backEndMember;

    @Column(nullable = true)
    private int frontEndMember;
    private String backEndStack;
    private String frontEndStack;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("createdAt desc")
    List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    List<ProjectLike> likes = new ArrayList<>();

    @Builder
    public Project(ProjectRequestDto requestDto, User user, String imageUrl) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.imageUrl = imageUrl;
        this.backEndStack = requestDto.getBackEndStack();
        this.frontEndStack = requestDto.getFrontEndStack();
//        this.stacks = requestDto.getStacks();
        this.frontEndMember = requestDto.getFrontEndMember();
        this.backEndMember = requestDto.getBackEndMember();
        this.user = user;
    }

    public void update(ProjectRequestDto requestDto, String imageUrl) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.imageUrl = imageUrl;
//        this.stacks = requestDto.getStacks();
        this.backEndStack = requestDto.getBackEndStack();
        this.frontEndStack = requestDto.getFrontEndStack();
        this.frontEndMember = requestDto.getFrontEndMember();
        this.backEndMember = requestDto.getBackEndMember();
    }

}

