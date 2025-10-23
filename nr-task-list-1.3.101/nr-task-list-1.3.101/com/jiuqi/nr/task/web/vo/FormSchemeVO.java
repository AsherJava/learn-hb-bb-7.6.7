/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import java.util.List;

public class FormSchemeVO {
    private String key;
    private String code;
    private String title;
    private String taskKey;
    private String dateTime;
    private String copyFormScheme;
    private List<ValidateTimeVO> validateTime;

    public FormSchemeVO() {
    }

    public FormSchemeVO(DesignFormSchemeDefine formSchemeDefine) {
        this.key = formSchemeDefine.getKey();
        this.taskKey = formSchemeDefine.getTaskKey();
        this.code = formSchemeDefine.getFormSchemeCode();
        this.title = formSchemeDefine.getTitle();
    }

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

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCopyFormScheme() {
        return this.copyFormScheme;
    }

    public void setCopyFormScheme(String copyFormScheme) {
        this.copyFormScheme = copyFormScheme;
    }

    public List<ValidateTimeVO> getValidateTime() {
        return this.validateTime;
    }

    public void setValidateTime(List<ValidateTimeVO> validateTime) {
        this.validateTime = validateTime;
    }
}

