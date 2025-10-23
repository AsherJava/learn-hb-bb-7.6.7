/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.link.dto;

import java.util.Map;

public class RefEntityLinkConfigDTO {
    private int entitySize;
    private String captionFieldsString;
    private String dropDownFieldsString;
    private Map<String, Object> enumPosMap;

    public int getEntitySize() {
        return this.entitySize;
    }

    public void setEntitySize(int entitySize) {
        this.entitySize = entitySize;
    }

    public String getCaptionFieldsString() {
        return this.captionFieldsString;
    }

    public void setCaptionFieldsString(String captionFieldsString) {
        this.captionFieldsString = captionFieldsString;
    }

    public String getDropDownFieldsString() {
        return this.dropDownFieldsString;
    }

    public void setDropDownFieldsString(String dropDownFieldsString) {
        this.dropDownFieldsString = dropDownFieldsString;
    }

    public Map<String, Object> getEnumPosMap() {
        return this.enumPosMap;
    }

    public void setEnumPosMap(Map<String, Object> enumPosMap) {
        this.enumPosMap = enumPosMap;
    }
}

