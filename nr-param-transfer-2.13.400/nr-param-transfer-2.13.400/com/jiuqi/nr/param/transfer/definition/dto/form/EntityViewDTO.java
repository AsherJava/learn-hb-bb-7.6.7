/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import java.time.Instant;

public class EntityViewDTO {
    private String entityViewKey;
    private String entityID;
    private String entityViewTitle;
    private String filterExpression;
    private String order;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Instant updateTime;

    public String getEntityViewKey() {
        return this.entityViewKey;
    }

    public void setEntityViewKey(String entityViewKey) {
        this.entityViewKey = entityViewKey;
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getEntityViewTitle() {
        return this.entityViewTitle;
    }

    public void setEntityViewTitle(String entityViewTitle) {
        this.entityViewTitle = entityViewTitle;
    }

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
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

    public static EntityViewDTO valueOf(FilterTemplateDO entityViewDefine) {
        EntityViewDTO entityView = new EntityViewDTO();
        entityView.setEntityViewKey(entityViewDefine.getFilterTemplateID());
        entityView.setEntityID(entityViewDefine.getEntityID());
        entityView.setEntityViewTitle(entityViewDefine.getFilterTemplateTitle());
        entityView.setFilterExpression(entityViewDefine.getFilterContent());
        entityView.setOrder(entityViewDefine.getOrder());
        entityView.setUpdateTime(entityViewDefine.getUpdateTime());
        return entityView;
    }

    public FilterTemplateDTO dto2Define() {
        FilterTemplateDTO entityViewDefine = new FilterTemplateDTO();
        entityViewDefine.setFilterTemplateID(this.getEntityViewKey());
        entityViewDefine.setEntityID(this.getEntityID());
        entityViewDefine.setFilterTemplateTitle(this.getEntityViewTitle());
        entityViewDefine.setFilterContent(this.getFilterExpression());
        entityViewDefine.setOrder(this.getOrder());
        entityViewDefine.setUpdateTime(this.getUpdateTime());
        return entityViewDefine;
    }
}

