/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class GetAllTablesInTask {
    private boolean isTaskOrScheme;
    private String key;

    public GetAllTablesInTask() {
    }

    public GetAllTablesInTask(boolean isTaskOrScheme, String key) {
        this.isTaskOrScheme = isTaskOrScheme;
        this.key = key;
    }

    public boolean getIsTaskOrScheme() {
        return this.isTaskOrScheme;
    }

    public void setIsTaskOrScheme(boolean taskOrScheme) {
        this.isTaskOrScheme = taskOrScheme;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

