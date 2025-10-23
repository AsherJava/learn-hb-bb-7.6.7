/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.task.i18n.bean.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;

public class I18nTaskOrgAliasDTO
extends I18nBaseDTO {
    private String taskOrgLinkKey;
    private String entityName;

    public I18nTaskOrgAliasDTO() {
    }

    public I18nTaskOrgAliasDTO(I18nBaseDTO baseDTO) {
        super.setOtherLanguageInfo(baseDTO.getOtherLanguageInfo());
        super.setLanguageKey(baseDTO.getLanguageKey());
    }

    public I18nTaskOrgAliasDTO(JsonNode node) {
        super(node);
        this.taskOrgLinkKey = node.get("taskOrgLinkKey").textValue();
        this.entityName = node.get("entityName").textValue();
    }

    public String getTaskOrgLinkKey() {
        return this.taskOrgLinkKey;
    }

    public void setTaskOrgLinkKey(String taskOrgLinkKey) {
        this.taskOrgLinkKey = taskOrgLinkKey;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}

