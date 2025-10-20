/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Users {
    private final Map<String, User> contains = new HashMap<String, User>();

    public User getUsers(String user) {
        return this.contains.get(user);
    }

    public void addUser(String user, String pass, String encoder) {
        this.contains.put(user, new User(user, pass, encoder));
    }

    public static class User
    implements Serializable {
        private String name;
        private String encoder;
        private String password;

        public User() {
        }

        private User(String name, String password, String encoder) {
            this.name = name;
            this.password = password;
            this.encoder = encoder;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEncoder() {
            return this.encoder;
        }

        public void setEncoder(String encoder) {
            this.encoder = encoder;
        }
    }
}

