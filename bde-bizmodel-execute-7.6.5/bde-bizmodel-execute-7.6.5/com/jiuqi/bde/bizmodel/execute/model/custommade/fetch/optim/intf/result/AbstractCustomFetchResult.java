/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result;

import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.common.base.BusinessRuntimeException;
import java.util.Map;

public class AbstractCustomFetchResult {
    private Map<String, Integer> columnMap;
    private Map<String, ResultColumnType> columnTypeMap;

    public AbstractCustomFetchResult(Map<String, Integer> columnMap, Map<String, ResultColumnType> columnTypeMap) {
        this.columnMap = columnMap;
        this.columnTypeMap = columnTypeMap;
    }

    public ResultColumnType findColType(String colName) {
        if (this.columnTypeMap.get(colName) == null) {
            return null;
        }
        return this.columnTypeMap.get(colName);
    }

    public Integer getColumIdx(String colName) {
        if (this.columnMap.get(colName) == null) {
            throw new BusinessRuntimeException(String.format("\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u63d0\u53d6\u51fa\u9519\uff0c\u5217\u540d\u3010%1$s\u3011\u5728\u7ed3\u679c\u96c6\u4e2d\u6ca1\u6709\u5305\u542b", colName));
        }
        return this.columnMap.get(colName);
    }

    public Map<String, Integer> getColumnMap() {
        return this.columnMap;
    }

    public String toString() {
        return "AbstractCustomFetchResult [columnMap=" + this.columnMap + ", columnTypeMap=" + this.columnTypeMap + "]";
    }
}

