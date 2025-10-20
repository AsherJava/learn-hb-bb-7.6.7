/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;

public class SimpleCellSheet
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String name;
    private String groupCode;

    public SimpleCellSheet(String name, String title, String groupCode) {
        this.name = name;
        this.title = title;
        this.groupCode = groupCode;
    }

    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}

