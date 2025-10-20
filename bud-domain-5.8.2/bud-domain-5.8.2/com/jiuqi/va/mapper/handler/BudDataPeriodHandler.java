/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.type.BaseTypeHandler
 *  org.apache.ibatis.type.JdbcType
 *  org.apache.ibatis.type.MappedJdbcTypes
 *  org.apache.ibatis.type.MappedTypes
 */
package com.jiuqi.va.mapper.handler;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes(value={JdbcType.VARCHAR})
@MappedTypes(value={DataPeriod.class})
public class BudDataPeriodHandler
extends BaseTypeHandler<DataPeriod> {
    public void setNonNullParameter(PreparedStatement ps, int i, DataPeriod parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getName());
    }

    public DataPeriod getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String val = rs.getString(columnName);
        if (val == null) {
            return null;
        }
        return DataPeriodFactory.valueOf(val);
    }

    public DataPeriod getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String val = rs.getString(columnIndex);
        if (val == null) {
            return null;
        }
        return DataPeriodFactory.valueOf(val);
    }

    public DataPeriod getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String val = cs.getString(columnIndex);
        if (val == null) {
            return null;
        }
        return DataPeriodFactory.valueOf(val);
    }
}

