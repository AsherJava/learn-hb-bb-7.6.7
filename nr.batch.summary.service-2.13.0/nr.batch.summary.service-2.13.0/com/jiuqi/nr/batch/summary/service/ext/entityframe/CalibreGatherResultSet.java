/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.common.CalibreTableColumn
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.calibre2.internal.adapter.CalibreResultSet
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.calibre2.common.CalibreTableColumn;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreResultSet;
import java.util.HashMap;
import java.util.Map;

public class CalibreGatherResultSet
extends CalibreResultSet {
    private Map<String, String> columnMap;

    public CalibreGatherResultSet(CalibreResultSet calibreResultSet, Map<String, CorporateEntityData> corporateColumn2Value) {
        super(calibreResultSet.getTableCode(), calibreResultSet.getList(), calibreResultSet.getCalibreDataService(), calibreResultSet.getCalibreDefineService());
        this.initMap(corporateColumn2Value);
    }

    protected Object getColumnObject(int index, String columnCode) {
        CalibreDataDTO calibreDataDTO = (CalibreDataDTO)this.list.get(index);
        if (calibreDataDTO == null) {
            return null;
        }
        String realCode = this.columnMap.get(columnCode);
        if (realCode != null) {
            return calibreDataDTO.getValue(realCode);
        }
        return null;
    }

    private void initMap(Map<String, CorporateEntityData> corporateColumn2Value) {
        this.columnMap = new HashMap<String, String>(5 + corporateColumn2Value.size());
        this.columnMap.put("ID", CalibreTableColumn.KEY.getCode());
        this.columnMap.put("CODE", CalibreTableColumn.CODE.getCode());
        this.columnMap.put("ORGCODE", CalibreTableColumn.CODE.getCode());
        this.columnMap.put("NAME", CalibreTableColumn.NAME.getCode());
        this.columnMap.put("PARENTCODE", CalibreTableColumn.PARENT.getCode());
        this.columnMap.put("ORDINAL", CalibreTableColumn.ORDER.getCode());
        corporateColumn2Value.keySet().forEach(k -> this.columnMap.put((String)k, ((CorporateEntityData)corporateColumn2Value.get(k)).getCode()));
    }
}

