/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import java.util.List;

public class GcOffSetVchrVO {
    private String mrecid;
    private List<GcOffSetVchrItemVO> items;

    public String getMrecid() {
        return this.mrecid;
    }

    public void setMrecid(String mrecid) {
        this.mrecid = mrecid;
    }

    public List<GcOffSetVchrItemVO> getItems() {
        return this.items;
    }

    public void setItems(List<GcOffSetVchrItemVO> items) {
        this.items = items;
    }
}

