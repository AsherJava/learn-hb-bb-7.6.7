/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result;

import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.AbstractCustomFetchResult;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;

public class CustomFetchSimpleResult
extends AbstractCustomFetchResult {
    private List<Object[]> rowDatas;

    public CustomFetchSimpleResult(Map<String, Integer> columnMap, Map<String, ResultColumnType> columnTypeMap) {
        super(columnMap, columnTypeMap);
    }

    public Object getData(Object[] row, String colName) {
        Assert.isNotNull((Object)row);
        Assert.isNotEmpty((String)colName);
        return row[this.getColumIdx(colName)];
    }

    public void setRowDatas(List<Object[]> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public List<Object[]> getRowDatas() {
        return this.rowDatas;
    }

    @Override
    public String toString() {
        return "CustomFetchSimpleResult [rowDatas=" + this.rowDatas + ", toString()=" + super.toString() + "]";
    }
}

