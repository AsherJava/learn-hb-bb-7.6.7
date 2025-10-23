/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.web.vo.ValidateTimeCheckResultVO;

public class FormSchemeCheckVO {
    private ValidateTimeCheckResultVO checkResult;
    private String formSchemeKey;

    public FormSchemeCheckVO() {
    }

    public FormSchemeCheckVO(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public ValidateTimeCheckResultVO getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(ValidateTimeCheckResultVO checkResult) {
        this.checkResult = checkResult;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

