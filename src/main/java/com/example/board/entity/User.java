package com.example.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(length = 100)
    private String password;

    @Column String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String password, String picture,Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = this.picture;
        return this;
    }

    /* 소셜로그인시 이미 등록된 회원이라면 수정날짜만 업데이트하고     * 기존 데이터는 그대로 보존하도록 예외처리 */
    public User updateModifiedDate() {
        this.onPreUpdate();
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}