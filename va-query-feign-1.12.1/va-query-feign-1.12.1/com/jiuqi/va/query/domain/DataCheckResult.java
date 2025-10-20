/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.domain;

public class DataCheckResult {
    private boolean pass;
    private String message;

    public DataCheckResult(boolean pass, String message) {
        this.pass = pass;
        this.message = message;
    }

    public boolean isPass() {
        return this.pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataCheckResult() {
    }

    public DataCheckResult(boolean pass) {
        this.pass = pass;
    }

    public static DataCheckResult pass() {
        return new DataCheckResult(true);
    }

    public static DataCheckResult reject(String message) {
        return new DataCheckResult(false, message);
    }
}

