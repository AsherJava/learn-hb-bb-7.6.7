/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.filterTemplate.facade;

import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import com.jiuqi.nr.filterTemplate.web.vo.FilterTemplateVO;
import java.time.Instant;

public class FilterTemplateDTO {
    private String filterTemplateID;
    private String entityID;
    private String filterTemplateTitle;
    private String filterContent;
    private String order;
    private Instant updateTime;
    private String fieldKey;

    public FilterTemplateDTO() {
    }

    public FilterTemplateDTO(FilterTemplateVO filterTemplateVO) {
        if (filterTemplateVO == null) {
            return;
        }
        this.entityID = filterTemplateVO.getEntityID();
        this.filterTemplateID = filterTemplateVO.getFilterTemplateID();
        this.filterTemplateTitle = filterTemplateVO.getFilterTemplateTitle();
        this.filterContent = filterTemplateVO.getFilterCondition();
        this.order = filterTemplateVO.getOrder();
        this.fieldKey = filterTemplateVO.getFieldKey();
    }

    public FilterTemplateDTO(FilterTemplateDO filterTemplateDO) {
        if (filterTemplateDO == null) {
            return;
        }
        this.filterTemplateID = filterTemplateDO.getFilterTemplateID();
        this.entityID = filterTemplateDO.getEntityID();
        this.filterTemplateTitle = filterTemplateDO.getFilterTemplateTitle();
        this.filterContent = filterTemplateDO.getFilterContent();
        this.order = filterTemplateDO.getOrder();
        this.updateTime = filterTemplateDO.getUpdateTime();
    }

    public String getFilterTemplateID() {
        return this.filterTemplateID;
    }

    public void setFilterTemplateID(String filterTemplateID) {
        this.filterTemplateID = filterTemplateID;
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getFilterTemplateTitle() {
        return this.filterTemplateTitle;
    }

    public void setFilterTemplateTitle(String filterTemplateTitle) {
        this.filterTemplateTitle = filterTemplateTitle;
    }

    public String getFilterContent() {
        return this.filterContent;
    }

    public void setFilterContent(String filterContent) {
        this.filterContent = filterContent;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }
}

