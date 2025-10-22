/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.internal.dao.DataFieldDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class DataFieldRowMapper
implements RowMapper<DataFieldDO> {
    DataFieldRowMapper() {
    }

    public DataFieldDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DataFieldDO dataField = new DataFieldDO();
        dataField.setKey(rs.getString("DF_KEY"));
        dataField.setCode(rs.getString("DF_CODE"));
        dataField.setTitle(rs.getString("DF_TITLE"));
        dataField.setDefaultValue(rs.getString("DF_DEFAULT"));
        dataField.setDataTableKey(rs.getString("DF_DT_KEY"));
        dataField.setRefDataFieldKey(rs.getString("DF_REF_FIELD_ID"));
        dataField.setMeasureUnit(rs.getString("DF_MEASUREUNIT"));
        dataField.setRefDataEntityKey(rs.getString("DF_REF_ENTITY_ID"));
        dataField.setFieldName(rs.getString("DS_FIELD_NAME"));
        dataField.setTableName(rs.getString("DS_TABLE_NAME"));
        dataField.setDataType(rs.getInt("DF_DATATYPE"));
        dataField.setApplyType(rs.getInt("DF_APPLY_TYPE"));
        dataField.setAggrType(rs.getInt("DF_AGGRTYPE"));
        dataField.setNullable(rs.getInt("DF_NULLABLE"));
        return dataField;
    }
}

