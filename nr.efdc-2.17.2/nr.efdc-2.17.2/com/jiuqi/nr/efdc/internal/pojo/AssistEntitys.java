/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.internal.pojo;

import com.jiuqi.nr.efdc.bean.EntityDataObject;
import java.util.List;

public class AssistEntitys {
    private String viewKey;
    private List<EntityDataObject> entitys;

    public AssistEntitys() {
    }

    public AssistEntitys(String viewKey, List<EntityDataObject> entitys) {
        this.viewKey = viewKey;
        this.entitys = entitys;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public List<EntityDataObject> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityDataObject> entitys) {
        this.entitys = entitys;
    }
}

