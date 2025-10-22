/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.output;

public class CellAnnotationComment {
    private String id;
    private String content;
    private String usName;
    private String userName;
    private String repyUserName;
    private long date;
    private boolean canEditOrDelete;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsName() {
        return this.usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
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

    public String getRepyUserName() {
        return this.repyUserName;
    }

    public void setRepyUserName(String repyUserName) {
        this.repyUserName = repyUserName;
    }

    public boolean isCanEditOrDelete() {
        return this.canEditOrDelete;
    }

    public void setCanEditOrDelete(boolean canEditOrDelete) {
        this.canEditOrDelete = canEditOrDelete;
    }
}

