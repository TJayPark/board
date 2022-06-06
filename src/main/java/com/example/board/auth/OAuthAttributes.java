package com.example.board.auth;

import com.example.board.entity.Role;
import com.example.board.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey, name, email, picture, password;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String name, String email, String picture, String password) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.password = password;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes, String password) {
        return ofGoogle(userNameAttributeName, attributes, password);
    }

    public  static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes, String password)  {
    return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .password(password)
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
