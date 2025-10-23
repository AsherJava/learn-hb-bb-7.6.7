/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 */
package com.jiuqi.nr.task.form.form.dto;

import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import java.text.SimpleDateFormat;

public class FormGroupDTO {
    private String key;
    private String code;
    private String title;
    private String condition;
    private String parent;
    private String formSchemeKey;
    private String updateTime;

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

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static FormGroupDTO getInstance(DesignFormGroupDefine groupDefine) {
        FormGroupDTO groupDTO = new FormGroupDTO();
        groupDTO.setKey(groupDefine.getKey());
        groupDTO.setCode(groupDefine.getCode());
        groupDTO.setTitle(groupDefine.getTitle());
        groupDTO.setCondition(groupDefine.getCondition());
        groupDTO.setFormSchemeKey(groupDefine.getFormSchemeKey());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(groupDefine.getUpdateTime());
        groupDTO.setUpdateTime(dateString);
        return groupDTO;
    }
}

