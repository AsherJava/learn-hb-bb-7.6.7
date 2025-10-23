/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.FormSyncPushSchemeVO;
import java.util.List;

public class FormSyncPushVO {
    private String srcFormKey;
    private String srcFormTitle;
    private String srcFormCode;
    private String srcFormOrder;
    List<FormSyncPushSchemeVO> desFormScheme;
    List<FormSyncPushSchemeVO> selectDesFormScheme;

    public String getSrcFormKey() {
        return this.srcFormKey;
    }

    public void setSrcFormKey(String srcFormKey) {
        this.srcFormKey = srcFormKey;
    }

    public String getSrcFormTitle() {
        return this.srcFormTitle;
    }

    public void setSrcFormTitle(String srcFormTitle) {
        this.srcFormTitle = srcFormTitle;
    }

    public String getSrcFormCode() {
        return this.srcFormCode;
    }

    public void setSrcFormCode(String srcFormCode) {
        this.srcFormCode = srcFormCode;
    }

    public String getSrcFormOrder() {
        return this.srcFormOrder;
    }

    public void setSrcFormOrder(String srcFormOrder) {
        this.srcFormOrder = srcFormOrder;
    }

    public List<FormSyncPushSchemeVO> getDesFormScheme() {
        return this.desFormScheme;
    }

    public void setDesFormScheme(List<FormSyncPushSchemeVO> desFormScheme) {
        this.desFormScheme = desFormScheme;
    }

    public List<FormSyncPushSchemeVO> getSelectDesFormScheme() {
        return this.selectDesFormScheme;
    }

    public void setSelectDesFormScheme(List<FormSyncPushSchemeVO> selectDesFormScheme) {
        this.selectDesFormScheme = selectDesFormScheme;
    }
}

