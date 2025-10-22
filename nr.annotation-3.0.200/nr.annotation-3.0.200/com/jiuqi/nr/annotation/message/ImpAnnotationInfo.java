/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.message;

import java.util.Map;

public class ImpAnnotationInfo {
    private String id;
    private Map<String, String> dimNameValue;
    private String content;
    private String userName;
    private long date;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getDimNameValue() {
        return this.dimNameValue;
    }

    public void setDimNameValue(Map<String, String> dimNameValue) {
        this.dimNameValue = dimNameValue;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}

