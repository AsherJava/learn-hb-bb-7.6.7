/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade.vo;

import java.util.List;

public class RelatedTaskDimVO {
    private String entityId;
    private String code;
    private String name;
    private List<RelatedTaskDimVO> data;

    public RelatedTaskDimVO() {
    }

    public RelatedTaskDimVO(String entityId, String code, String name) {
        this.entityId = entityId;
        this.code = code;
        this.name = name;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RelatedTaskDimVO> getData() {
        return this.data;
    }

    public void setData(List<RelatedTaskDimVO> data) {
        this.data = data;
    }
}

