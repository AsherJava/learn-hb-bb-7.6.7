/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 */
package com.jiuqi.nr.task.form.link.dto;

import com.jiuqi.nr.task.form.dto.AbstractState;
import java.time.Instant;

public class FilterTemplateDTO
extends AbstractState {
    private String entityID;
    private String filterTemplateID;
    private String filterTemplateTitle;
    private String filterCondition;
    private String order;
    private String fieldKey;

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

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public static com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO cto(FilterTemplateDTO dto) {
        if (dto == null) {
            return null;
        }
        com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO filterTemplateDTO = new com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO();
        filterTemplateDTO.setFilterTemplateID(dto.getFilterTemplateID());
        filterTemplateDTO.setEntityID(dto.getEntityID());
        filterTemplateDTO.setFilterTemplateTitle(dto.getFilterTemplateTitle());
        filterTemplateDTO.setFilterContent(dto.getFilterCondition());
        filterTemplateDTO.setOrder(dto.getOrder());
        filterTemplateDTO.setUpdateTime(Instant.now());
        filterTemplateDTO.setFieldKey(dto.getFieldKey());
        return filterTemplateDTO;
    }

    public static FilterTemplateDTO cto(com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO dto) {
        if (dto == null) {
            return null;
        }
        FilterTemplateDTO filterTemplateDTO = new FilterTemplateDTO();
        filterTemplateDTO.setFilterTemplateID(dto.getFilterTemplateID());
        filterTemplateDTO.setEntityID(dto.getEntityID());
        filterTemplateDTO.setFilterTemplateTitle(dto.getFilterTemplateTitle());
        filterTemplateDTO.setFilterCondition(dto.getFilterContent());
        filterTemplateDTO.setOrder(dto.getOrder());
        filterTemplateDTO.setFieldKey(dto.getFieldKey());
        return filterTemplateDTO;
    }
}

