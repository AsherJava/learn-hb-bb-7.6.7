/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.mapping2.dto;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.mapping2.bean.FormMappingDO;

public class FormMappingDTO {
    private String key;
    private String sourceKey;
    private String sourceCode;
    private String sourceTitle;
    private String targetCode;
    private String targetTitle;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceTitle() {
        return this.sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getTargetCode() {
        return this.targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getTargetTitle() {
        return this.targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }

    public static FormMappingDTO getInstance(FormMappingDO mappingDO, FormDefine formDefine) {
        FormMappingDTO dto = new FormMappingDTO();
        dto.setKey(mappingDO.getKey());
        dto.setSourceKey(mappingDO.getSourceKey());
        dto.setTargetCode(mappingDO.getTargetCode());
        dto.setTargetTitle(mappingDO.getTargetTitle());
        dto.setSourceCode(formDefine.getFormCode());
        dto.setSourceTitle(formDefine.getTitle());
        return dto;
    }
}

