/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingBlockVO;
import java.util.List;

public class DataDockingFormVO {
    private String formCode;
    private List<DataDockingBlockVO> dataBlocks;

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public List<DataDockingBlockVO> getDataBlocks() {
        return this.dataBlocks;
    }

    public void setDataBlocks(List<DataDockingBlockVO> dataBlocks) {
        this.dataBlocks = dataBlocks;
    }
}

