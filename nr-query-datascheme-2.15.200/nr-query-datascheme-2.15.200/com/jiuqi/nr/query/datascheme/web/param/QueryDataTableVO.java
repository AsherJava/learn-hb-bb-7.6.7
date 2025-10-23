/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.web.facade.DataTableVO
 */
package com.jiuqi.nr.query.datascheme.web.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.web.facade.DataTableVO;
import com.jiuqi.nr.query.datascheme.bean.TableRelInfo;
import com.jiuqi.nr.query.datascheme.web.param.QueryDataDimFieldVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryDataTableVO
extends DataTableVO {
    private String tableKey;
    private String tableName;
    private String tableType;
    private List<QueryDataDimFieldVO> dimFields;
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

    public List<QueryDataDimFieldVO> getDimFields() {
        return this.dimFields;
    }

    public TableRelInfo getTableRelInfo() {
        return this.tableRelInfo;
    }

    public void setTableRelInfo(TableRelInfo tableRelInfo) {
        this.tableRelInfo = tableRelInfo;
    }

    @JsonIgnore
    public List<QueryDataDimFieldVO> getDimFieldsOrNew() {
        if (null == this.dimFields) {
            this.dimFields = new ArrayList<QueryDataDimFieldVO>();
        }
        return this.dimFields;
    }

    @JsonIgnore
    public QueryDataDimFieldVO getUnitField() {
        if (null != this.dimFields) {
            for (QueryDataDimFieldVO dimField : this.dimFields) {
                if (DimensionType.UNIT.getValue() != dimField.getDimType()) continue;
                return dimField;
            }
        }
        return null;
    }

    @JsonIgnore
    public QueryDataDimFieldVO getPeriodField() {
        if (null != this.dimFields) {
            for (QueryDataDimFieldVO dimField : this.dimFields) {
                if (DimensionType.PERIOD.getValue() != dimField.getDimType()) continue;
                return dimField;
            }
        }
        return null;
    }

    @JsonIgnore
    public List<QueryDataDimFieldVO> getDimensionFields() {
        if (null != this.dimFields) {
            return this.dimFields.stream().filter(f -> DimensionType.DIMENSION.getValue() == f.getDimType()).collect(Collectors.toList());
        }
        return null;
    }

    public void setDimFields(List<QueryDataDimFieldVO> dimFields) {
        this.dimFields = dimFields;
    }
}

