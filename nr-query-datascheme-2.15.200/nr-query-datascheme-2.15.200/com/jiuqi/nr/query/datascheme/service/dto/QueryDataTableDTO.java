/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableMap
 *  com.jiuqi.nr.datascheme.internal.dto.DataTableDTO
 *  com.jiuqi.nr.datascheme.internal.dto.DataTableDesignDTO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO
 */
package com.jiuqi.nr.query.datascheme.service.dto;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableMap;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDesignDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import com.jiuqi.nr.query.datascheme.bean.TableRelInfo;

public class QueryDataTableDTO
extends DataTableDesignDTO {
    private String tableKey;
    private String tableName;
    private String tableType;
    private TableRelInfo tableRelInfo;

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return this.tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public TableRelInfo getTableRelInfo() {
        return this.tableRelInfo;
    }

    public void setTableRelInfo(TableRelInfo tableRelInfo) {
        this.tableRelInfo = tableRelInfo;
    }

    public DataTableMap getDataTableMap() {
        DataTableMapDO obj = new DataTableMapDO();
        obj.setTableKey(this.getKey());
        obj.setTableCode(this.getCode());
        obj.setSrcKey(this.getTableKey());
        obj.setSrcCode(this.getTableName());
        obj.setSrcType(this.getTableType());
        return obj;
    }

    public static QueryDataTableDTO valueOf(DataTable table, DataTableMap tableMap) {
        QueryDataTableDTO dto = new QueryDataTableDTO();
        DataTableDTO.copyProperties((DataTable)table, (DataTableDTO)dto);
        dto.setTableKey(tableMap.getSrcKey());
        dto.setTableName(tableMap.getSrcCode());
        dto.setTableType(tableMap.getSrcType());
        return dto;
    }
}

