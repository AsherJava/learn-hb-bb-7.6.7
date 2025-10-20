/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.definition.option.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuditSchemeDTO {
    private String code;
    private String title;
    private String value;

    public AuditSchemeDTO() {
    }

    public AuditSchemeDTO(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public AuditSchemeDTO(String code, String title, String value) {
        this.code = code;
        this.title = title;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonIgnore
    public boolean isCondition() {
        return "AUDIT_SCHEME_CONDITION".equals(this.code);
    }

    @JsonIgnore
    public boolean isAuditType() {
        return !"AUDIT_SCHEME_CONDITION".equals(this.code);
    }
}

