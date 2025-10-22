/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.filterTemplate.web.vo;

import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;

public class FilterTemplateVO {
    private String entityID;
    private String filterTemplateID;
    private String filterTemplateTitle;
    private String filterCondition;
    private String order;
    private String fieldKey;

    public FilterTemplateVO() {
    }

    public FilterTemplateVO(FilterTemplateDTO filterTemplateDTO) {
        if (filterTemplateDTO == null) {
            return;
        }
        this.filterTemplateID = filterTemplateDTO.getFilterTemplateID();
        this.entityID = filterTemplateDTO.getEntityID();
        this.filterTemplateTitle = filterTemplateDTO.getFilterTemplateTitle();
        this.filterCondition = filterTemplateDTO.getFilterContent();
        this.order = filterTemplateDTO.getOrder();
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getFilterTemplateID() {
        return this.filterTemplateID;
    }

    public void setFilterTemplateID(String filterTemplateID) {
        this.filterTemplateID = filterTemplateID;
    }

    public String getFilterTemplateTitle() {
        return this.filterTemplateTitle;
    }

    public void setFilterTemplateTitle(String filterTemplateTitle) {
        this.filterTemplateTitle = filterTemplateTitle;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String linkKey) {
        this.fieldKey = linkKey;
    }
}

