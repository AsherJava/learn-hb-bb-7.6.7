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

public class CustomFetchOneCondiResult
extends AbstractCustomFetchResult {
    private String condiKey;
    private Map<String, List<Object[]>> dataMap;

    public CustomFetchOneCondiResult(Map<String, Integer> columnMap, Map<String, ResultColumnType> columnTypeMap) {
        super(columnMap, columnTypeMap);
    }

    public Object getData(Object[] row, String colName) {
        Assert.isNotNull((Object)row);
        Assert.isNotEmpty((String)colName);
        return row[this.getColumIdx(colName)];
    }

    public String getCondiKey() {
        return this.condiKey;
    }

    public void setCondiKey(String condiKey) {
        this.condiKey = condiKey;
    }

    public Map<String, List<Object[]>> getDataMap() {
        return this.dataMap;
    }

    public void setDataMap(Map<String, List<Object[]>> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String toString() {
        return "CustomFetchOneCondiResult [condiKey=" + this.condiKey + ", dataMap=" + this.dataMap + ", toString()=" + super.toString() + "]";
    }
}

