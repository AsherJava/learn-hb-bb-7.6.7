/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import java.io.Serializable;
import java.time.Instant;

public class PasswordHistoryDTO
implements Serializable,
Comparable<PasswordHistoryDTO> {
    private static final long serialVersionUID = 6631322370523772889L;
    protected String pwdHistoryKey;
    protected String userKey;
    protected String pwdContent;
    protected String pwdType;
    protected Instant pwdCreateTime;

    public PasswordHistoryDTO() {
    }

    public PasswordHistoryDTO(String pwdHistoryKey, String userKey, String pwdContent, String pwdType, Instant pwdCreateTime) {
        this.pwdHistoryKey = pwdHistoryKey;
        this.userKey = userKey;
        this.pwdContent = pwdContent;
        this.pwdType = pwdType;
        this.pwdCreateTime = pwdCreateTime;
    }

    public String getPwdHistoryKey() {
        return this.pwdHistoryKey;
    }

    public void setPwdHistoryKey(String pwdHistoryKey) {
        this.pwdHistoryKey = pwdHistoryKey;
    }

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPwdContent() {
        return this.pwdContent;
    }

    public void setPwdContent(String pwdContent) {
        this.pwdContent = pwdContent;
    }

    public String getPwdType() {
        return this.pwdType;
    }

    public void setPwdType(String pwdType) {
        this.pwdType = pwdType;
    }

    public Instant getPwdCreateTime() {
        return this.pwdCreateTime;
    }

    public void setPwdCreateTime(Instant pwdCreateTime) {
        this.pwdCreateTime = pwdCreateTime;
    }

    @Override
    public int compareTo(PasswordHistoryDTO o) {
        return this.pwdCreateTime.compareTo(o.getPwdCreateTime());
    }
}

