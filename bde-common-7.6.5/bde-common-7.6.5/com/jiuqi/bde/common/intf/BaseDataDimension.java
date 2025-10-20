/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 */
package com.jiuqi.bde.common.intf;

import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import java.util.List;

public class BaseDataDimension
extends Dimension {
    private static final long serialVersionUID = 5652127678155454134L;
    private String baseDataTable;
    private List<BaseDataVO> dimBaseDataValue;
    private List<BaseDataVO> excludeBaseDataValue;

    public String getBaseDataTable() {
        return this.baseDataTable;
    }

    public void setBaseDataTable(String baseDataTable) {
        this.baseDataTable = baseDataTable;
    }

    public List<BaseDataVO> getDimBaseDataValue() {
        return this.dimBaseDataValue;
    }

    public void setDimBaseDataValue(List<BaseDataVO> dimBaseDataValue) {
        this.dimBaseDataValue = dimBaseDataValue;
    }

    public List<BaseDataVO> getExcludeBaseDataValue() {
        return this.excludeBaseDataValue;
    }

    public void setExcludeBaseDataValue(List<BaseDataVO> excludeBaseDataValue) {
        this.excludeBaseDataValue = excludeBaseDataValue;
    }
}

