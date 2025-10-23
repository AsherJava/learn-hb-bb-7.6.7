/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.task.dto.ValidateTimeDTO;

public class ValidateTimeVO {
    private String from;
    private String end;
    private String formSchemeKey;
    private String formSchemeTitle;
    private String formSchemeCode;
    private String fromTimeTitle;
    private String endTimeTitle;

    public ValidateTimeVO() {
    }

    public ValidateTimeVO(ValidateTimeDTO validateTimeDTO, DesignFormSchemeDefine schemeDefine) {
        this.from = validateTimeDTO.getFrom();
        this.end = validateTimeDTO.getEnd();
        this.formSchemeKey = validateTimeDTO.getFormSchemeKey();
        if (schemeDefine != null) {
            this.formSchemeTitle = schemeDefine.getTitle();
            this.formSchemeCode = schemeDefine.getFormSchemeCode();
        }
    }

    public ValidateTimeVO(DesignFormSchemeDefine schemeDefine) {
        if (schemeDefine != null) {
            this.formSchemeKey = schemeDefine.getKey();
            this.formSchemeTitle = schemeDefine.getTitle();
            this.formSchemeCode = schemeDefine.getFormSchemeCode();
        }
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public String getFromTimeTitle() {
        return this.fromTimeTitle;
    }

    public void setFromTimeTitle(String fromTimeTitle) {
        this.fromTimeTitle = fromTimeTitle;
    }

    public String getEndTimeTitle() {
        return this.endTimeTitle;
    }

    public void setEndTimeTitle(String endTimeTitle) {
        this.endTimeTitle = endTimeTitle;
    }
}

