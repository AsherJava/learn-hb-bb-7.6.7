/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.basedata.dto;

public class DCBaseDataDTO {
    private String code;
    private String name;
    private String objectCode;
    private String showTitle;

    public DCBaseDataDTO() {
    }

    public DCBaseDataDTO(String code, String name, String objectCode) {
        this.code = code;
        this.name = name;
        this.objectCode = objectCode;
    }

    public DCBaseDataDTO(String code, String name, String objectCode, String showTitle) {
        this.code = code;
        this.name = name;
        this.objectCode = objectCode;
        this.showTitle = showTitle;
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

    public String getObjectCode() {
        return this.objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getShowTitle() {
        return this.showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }
}

