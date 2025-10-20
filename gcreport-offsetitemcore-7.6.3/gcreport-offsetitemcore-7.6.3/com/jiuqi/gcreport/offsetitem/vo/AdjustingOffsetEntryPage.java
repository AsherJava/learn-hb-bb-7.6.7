/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.AdjustingOffsetEntryVO;
import java.util.List;

public class AdjustingOffsetEntryPage {
    private Integer total;
    private List<AdjustingOffsetEntryVO> offsetEntryVOS;

    public AdjustingOffsetEntryPage() {
    }

    public AdjustingOffsetEntryPage(Integer total, List<AdjustingOffsetEntryVO> offsetEntryVOS) {
        this.total = total;
        this.offsetEntryVOS = offsetEntryVOS;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<AdjustingOffsetEntryVO> getOffsetEntryVOS() {
        return this.offsetEntryVOS;
    }

    public void setOffsetEntryVOS(List<AdjustingOffsetEntryVO> offsetEntryVOS) {
        this.offsetEntryVOS = offsetEntryVOS;
    }
}

