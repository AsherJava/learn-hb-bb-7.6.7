/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.workingpaper.querytask.dto;

import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.workingpaper.querytask.dto.ArbitrarilyMergeOffSetVchrItemDTO;
import java.util.List;
import javax.validation.constraints.NotNull;

public class ArbitrarilyMergeOffSetVchrDTO {
    @NotNull
    private String mrecid = UUIDOrderUtils.newUUIDStr();
    private boolean needDelete;
    private boolean allowMoreUnit;
    private List<ArbitrarilyMergeOffSetVchrItemDTO> items;

    public static ArbitrarilyMergeOffSetVchrDTO getNotNeedDeleteVchr(String mrecid) {
        ArbitrarilyMergeOffSetVchrDTO offSetVchr = new ArbitrarilyMergeOffSetVchrDTO();
        offSetVchr.mrecid = mrecid;
        return offSetVchr;
    }

    public String getMrecid() {
        return this.mrecid;
    }

    public void setMrecid(String mrecid) {
        this.setNeedDelete(true);
        this.mrecid = mrecid;
    }

    public List<ArbitrarilyMergeOffSetVchrItemDTO> getItems() {
        return this.items;
    }

    public void setItems(List<ArbitrarilyMergeOffSetVchrItemDTO> items) {
        this.items = items;
    }

    public boolean isNeedDelete() {
        return this.needDelete;
    }

    public void setNeedDelete(boolean needDelete) {
        this.needDelete = needDelete;
    }

    public boolean isAllowMoreUnit() {
        return this.allowMoreUnit;
    }

    public void setAllowMoreUnit(boolean allowMoreUnit) {
        this.allowMoreUnit = allowMoreUnit;
    }
}

