/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.task.form.form.dto;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import java.text.DateFormat;

public class FormItemDTO {
    private String key;
    private String code;
    private String title;
    private FormType type;
    private String updateTime;
    private String updateUser;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public FormType getType() {
        return this.type;
    }

    public void setType(FormType type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public static FormItemDTO getInstance(DesignFormDefine formDefine, DateFormat sdf) {
        FormItemDTO formItemDTO = new FormItemDTO();
        formItemDTO.setKey(formDefine.getKey());
        formItemDTO.setCode(formDefine.getFormCode());
        formItemDTO.setTitle(formDefine.getTitle());
        formItemDTO.setType(formDefine.getFormType());
        String dateString = sdf.format(formDefine.getUpdateTime());
        formItemDTO.setUpdateTime(dateString);
        formItemDTO.setUpdateUser(formDefine.getUpdateUser());
        return formItemDTO;
    }
}

