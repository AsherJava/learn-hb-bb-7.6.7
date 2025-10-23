/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.TreeData
 */
package com.jiuqi.nr.report.web.vo;

import com.jiuqi.nr.task.api.tree.TreeData;

public class EntityAttributeVO
implements TreeData {
    private String code;
    private String title;

    public EntityAttributeVO() {
    }

    public EntityAttributeVO(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

