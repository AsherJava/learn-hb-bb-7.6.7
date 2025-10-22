/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.authz.bean;

public class PasswordRule {
    private String regular;
    private String message;
    private Integer history;

    public PasswordRule() {
    }

    public PasswordRule(String regular, String message, Integer history) {
        this.regular = regular;
        this.message = message;
        this.history = history;
    }

    public String getRegular() {
        return this.regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getHistory() {
        return this.history;
    }

    public void setHistory(Integer history) {
        this.history = history;
    }
}

