/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.dto;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegionInputItem {
    private String regionId;
    List<Map<String, Object>> detailItems;
    List<Map<String, Object>> sumItems;
    List<DataRow> detailRows;
    List<DataRow> sumRows;
    private List<ColumnModelDefine> allFields;

    public RegionInputItem(String regionId, List<ColumnModelDefine> allFields) {
        this.regionId = regionId;
        this.allFields = allFields;
        this.detailItems = new ArrayList<Map<String, Object>>();
        this.sumItems = new ArrayList<Map<String, Object>>();
        this.detailRows = new ArrayList<DataRow>();
        this.sumRows = new ArrayList<DataRow>();
    }

    public void addItem(Map<String, Object> item) {
        DataRow dataRow = this.toDataRow(item);
        if ("1".equals(String.valueOf(item.get("SUMXYZ")))) {
            this.sumItems.add(item);
            this.sumRows.add(dataRow);
        } else {
            this.detailItems.add(item);
            this.detailRows.add(dataRow);
        }
    }

    private DataRow toDataRow(Map<String, Object> inputData) {
        DataRow dataRow = new DataRow(this.allFields.size()){

            public boolean commit() {
                return false;
            }
        };
        for (int index = 0; index < this.allFields.size(); ++index) {
            dataRow.setValue(index, inputData.get(this.allFields.get(index).getName()));
        }
        return dataRow;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public List<DataRow> getDetailRows() {
        return this.detailRows;
    }

    public List<DataRow> getSumRows() {
        return this.sumRows;
    }
}

