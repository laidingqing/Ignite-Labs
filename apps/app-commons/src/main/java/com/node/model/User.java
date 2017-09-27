package com.node.model;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by skylai on 2017/9/27.
 */
@Getter
public class User implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Date creationDate;
    private String status;

    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }

    public static class UserBuilder {
        private User user = new User();

        public User build() {
            return user;
        }

        public User.UserBuilder id(Long id) {
            user.id = id;
            return this;
        }


        public User.UserBuilder name(String name) {
            user.name = name;
            return this;
        }

        public User.UserBuilder email(String email) {
            user.email = email;
            return this;
        }

        public User.UserBuilder creationDate(Date creationDate) {
            user.creationDate = creationDate;
            return this;
        }

        public User.UserBuilder status(String status) {
            user.status = status;
            return this;
        }
    }
}
