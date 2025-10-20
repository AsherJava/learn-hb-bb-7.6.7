/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 */
package com.jiuqi.gcreport.financialcheckapi.common.vo;

import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;

public class DimBaseDataVO {
    private String title;
    private String code;
    private GcBaseDataVO gcBaseDataVO;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GcBaseDataVO getGcBaseDataVO() {
        return this.gcBaseDataVO;
    }

    public void setGcBaseDataVO(GcBaseDataVO gcBaseDataVO) {
        this.gcBaseDataVO = gcBaseDataVO;
    }
}

