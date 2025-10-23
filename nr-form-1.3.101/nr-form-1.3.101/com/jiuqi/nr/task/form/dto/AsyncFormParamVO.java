/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.FormDoCopyParams;
import com.jiuqi.nr.task.form.dto.FormSyncParamsVO;
import java.io.Serializable;
import java.util.List;

public class AsyncFormParamVO
implements Serializable {
    private List<FormSyncParamsVO> formSchemeParam;
    private FormDoCopyParams formDoCopyParams;
    private String downLoadKey;

    public List<FormSyncParamsVO> getFormSchemeParam() {
        return this.formSchemeParam;
    }

    public void setFormSchemeParam(List<FormSyncParamsVO> formSchemeParam) {
        this.formSchemeParam = formSchemeParam;
    }

    public FormDoCopyParams getFormDoCopyParams() {
        return this.formDoCopyParams;
    }

    public void setFormDoCopyParams(FormDoCopyParams formDoCopyParams) {
        this.formDoCopyParams = formDoCopyParams;
    }

    public String getDownLoadKey() {
        return this.downLoadKey;
    }

    public void setDownLoadKey(String downLoadKey) {
        this.downLoadKey = downLoadKey;
    }
}

