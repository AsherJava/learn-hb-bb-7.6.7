/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import java.util.Objects;

public class SecretLevel {
    public static final String NOSEE_NAME = "NOSEE";
    public static final String NOSEE_TITLE = "\u8d85\u51fa\u5bc6\u7ea7";
    public static final SecretLevel NOSEE = new SecretLevel("NOSEE", "\u8d85\u51fa\u5bc6\u7ea7");
    private String name;
    private String title;

    public SecretLevel() {
    }

    public SecretLevel(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SecretLevel that = (SecretLevel)o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.title, that.title);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.title);
    }
}

