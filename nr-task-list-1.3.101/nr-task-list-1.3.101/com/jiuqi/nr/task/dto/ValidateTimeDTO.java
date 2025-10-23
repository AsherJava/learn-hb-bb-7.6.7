/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

import com.jiuqi.nr.task.web.vo.ValidateTimeVO;

public class ValidateTimeDTO {
    private String from;
    private String end;
    private String formSchemeKey;
    private String entity;

    public ValidateTimeDTO() {
    }

    public ValidateTimeDTO(String from, String end, String formSchemeKey) {
        this.from = from;
        this.end = end;
        this.formSchemeKey = formSchemeKey;
    }

    public ValidateTimeDTO(ValidateTimeVO validateTime, String entity) {
        this.from = validateTime.getFrom();
        this.end = validateTime.getEnd();
        this.formSchemeKey = validateTime.getFormSchemeKey();
        this.entity = entity;
    }

    public ValidateTimeDTO(String from, String end, String formSchemeKey, String entity) {
        this.from = from;
        this.end = end;
        this.formSchemeKey = formSchemeKey;
        this.entity = entity;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}

