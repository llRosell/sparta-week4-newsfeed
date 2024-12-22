package com.spring.instafeed;

import com.spring.instafeed.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Comment("사용자 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("사용자 이름")
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(16)")
    private String name;

    @Comment("사용자 이메일")
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String email;

    @Comment("사용자 비밀번호")
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    // 기본 생성자
    public User() {
    }

    /**
     * 생성자
     *
     * @param name     : 사용자 이름
     * @param email    : 사용자 이메일
     * @param password : 사용자 비밀번호
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * 기능
     * 비밀번호 수정
     *
     * @param password : 수정하려는 사용자의 비밀번호
     */
    public void update(String password) {
        this.password = password;
    }
}