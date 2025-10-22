/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dto;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;

public class GcDataCheckResultDTO {
    private String formKey;
    private String currency;
    private List<EfdcCheckResultVO> efdcCheckResultVOS;
    private List<FieldDefine> retEfdcZbFieldDefines;

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<EfdcCheckResultVO> getEfdcCheckResultVOS() {
        return this.efdcCheckResultVOS;
    }

    public void setEfdcCheckResultVOS(List<EfdcCheckResultVO> efdcCheckResultVOS) {
        this.efdcCheckResultVOS = efdcCheckResultVOS;
    }

    public List<FieldDefine> getRetEfdcZbFieldDefines() {
        return this.retEfdcZbFieldDefines;
    }

    public void setRetEfdcZbFieldDefines(List<FieldDefine> retEfdcZbFieldDefines) {
        this.retEfdcZbFieldDefines = retEfdcZbFieldDefines;
    }
}

