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

public class CustomFetchFourCondiResult
extends AbstractCustomFetchResult {
    private String condiKey1;
    private String condiKey2;
    private String condiKey3;
    private String condiKey4;
    private Map<String, Map<String, Map<String, Map<String, List<Object[]>>>>> dataMap;

    public CustomFetchFourCondiResult(Map<String, Integer> columnMap, Map<String, ResultColumnType> columnTypeMap) {
        super(columnMap, columnTypeMap);
    }

    public Object getData(Object[] row, String colName) {
        Assert.isNotNull((Object)row);
        Assert.isNotEmpty((String)colName);
        return row[this.getColumIdx(colName)];
    }

    public String getCondiKey1() {
        return this.condiKey1;
    }

    public String getCondiKey2() {
        return this.condiKey2;
    }

    public String getCondiKey3() {
        return this.condiKey3;
    }

    public String getCondiKey4() {
        return this.condiKey4;
    }

    public void setCondiKey1(String condiKey1) {
        this.condiKey1 = condiKey1;
    }

    public void setCondiKey2(String condiKey2) {
        this.condiKey2 = condiKey2;
    }

    public void setCondiKey3(String condiKey3) {
        this.condiKey3 = condiKey3;
    }

    public void setCondiKey4(String condiKey4) {
        this.condiKey4 = condiKey4;
    }

    public Map<String, Map<String, Map<String, Map<String, List<Object[]>>>>> getDataMap() {
        return this.dataMap;
    }

    public void setDataMap(Map<String, Map<String, Map<String, Map<String, List<Object[]>>>>> dataMap) {
        this.dataMap = dataMap;
    }
}

