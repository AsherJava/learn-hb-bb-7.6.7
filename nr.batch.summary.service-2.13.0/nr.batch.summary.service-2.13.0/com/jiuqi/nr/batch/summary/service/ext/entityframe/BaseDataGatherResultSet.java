/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.common.CalibreTableColumn
 *  com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataResultSet
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.calibre2.common.CalibreTableColumn;
import com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataResultSet;
import java.util.HashMap;
import java.util.Map;

public class BaseDataGatherResultSet
extends BaseDataResultSet {
    private Map<String, String> columnMap;
    private Map<String, CorporateEntityData> corporateColumn2Value;

    public BaseDataGatherResultSet(BaseDataResultSet baseDataResultSet, Map<String, CorporateEntityData> corporateColumn2Value) {
        super(baseDataResultSet.getPageVO(), baseDataResultSet.getBaseDataClient(), baseDataResultSet.getBaseDataDefineClient(), baseDataResultSet.getEntityQueryParam());
        this.corporateColumn2Value = corporateColumn2Value;
        this.initMap(corporateColumn2Value);
    }

    protected Object getColumnObject(int index, String columnCode) {
        Object columnValue;
        String realCode = this.columnMap.get(columnCode);
        if (realCode == null) {
            realCode = columnCode;
        }
        if ((columnValue = super.getColumnObject(index, realCode)) == null && this.corporateColumn2Value.containsKey(columnCode)) {
            columnValue = this.corporateColumn2Value.get(columnCode).getCode();
        }
        return columnValue;
    }

    private void initMap(Map<String, CorporateEntityData> corporateColumn2Value) {
        this.columnMap = new HashMap<String, String>(2 + corporateColumn2Value.size());
        this.columnMap.put("CODE", "OBJECTCODE");
        this.columnMap.put("ORGCODE", CalibreTableColumn.CODE.getCode());
    }
}

