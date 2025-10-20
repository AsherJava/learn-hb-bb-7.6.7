/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.type.BaseTypeHandler
 *  org.apache.ibatis.type.BooleanTypeHandler
 *  org.apache.ibatis.type.JdbcType
 *  org.apache.ibatis.type.MappedTypes
 */
package com.jiuqi.budget.jsr310;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.BooleanTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(value={Boolean.class})
public class BudBooleanDataTypeHandler
extends BaseTypeHandler<Boolean> {
    private final BooleanTypeHandler booleanTypeHandler = new BooleanTypeHandler();

    public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        try {
            this.booleanTypeHandler.setNonNullParameter(ps, i, parameter, jdbcType);
        }
        catch (Exception e) {
            if (parameter == null) {
                ps.setObject(i, null);
            }
            ps.setInt(i, parameter != false ? 1 : 0);
        }
    }

    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            return this.booleanTypeHandler.getNullableResult(rs, columnName);
        }
        catch (Exception e) {
            Integer object = rs.getObject(columnName, Integer.class);
            return object == null ? null : Boolean.valueOf(object == 1);
        }
    }

    public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            return this.booleanTypeHandler.getNullableResult(rs, columnIndex);
        }
        catch (Exception e) {
            Integer object = rs.getObject(columnIndex, Integer.class);
            return object == null ? null : Boolean.valueOf(object == 1);
        }
    }

    public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            return this.booleanTypeHandler.getNullableResult(cs, columnIndex);
        }
        catch (Exception e) {
            Integer object = cs.getObject(columnIndex, Integer.class);
            return object == null ? null : Boolean.valueOf(object == 1);
        }
    }
}

