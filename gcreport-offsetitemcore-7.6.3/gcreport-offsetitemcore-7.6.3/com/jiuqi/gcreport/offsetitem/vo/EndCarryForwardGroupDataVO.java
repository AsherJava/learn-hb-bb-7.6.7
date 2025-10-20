/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO;
import java.util.List;

public class EndCarryForwardGroupDataVO {
    private List<MinorityRecoveryRowVO> rowData;
    private List<GcOffSetVchrItemDTO> endCarryForwarditem;

    public List<MinorityRecoveryRowVO> getRowData() {
        return this.rowData;
    }

    public void setRowData(List<MinorityRecoveryRowVO> rowData) {
        this.rowData = rowData;
    }

    public List<GcOffSetVchrItemDTO> getEndCarryForwarditem() {
        return this.endCarryForwarditem;
    }

    public void setEndCarryForwarditem(List<GcOffSetVchrItemDTO> endCarryForwarditem) {
        this.endCarryForwarditem = endCarryForwarditem;
    }
}

