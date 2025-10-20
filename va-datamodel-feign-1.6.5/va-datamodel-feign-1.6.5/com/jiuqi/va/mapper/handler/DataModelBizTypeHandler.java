/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.type.BaseTypeHandler
 *  org.apache.ibatis.type.JdbcType
 *  org.apache.ibatis.type.MappedJdbcTypes
 *  org.apache.ibatis.type.MappedTypes
 *  org.apache.ibatis.type.TypeException
 */
package com.jiuqi.va.mapper.handler;

import com.jiuqi.va.domain.datamodel.DataModelType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeException;

@MappedTypes(value={DataModelType.BizType.class})
@MappedJdbcTypes(value={JdbcType.VARCHAR})
public class DataModelBizTypeHandler
extends BaseTypeHandler<DataModelType.BizType> {
    public void setParameter(PreparedStatement ps, int i, DataModelType.BizType parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            try {
                ps.setNull(i, JdbcType.VARCHAR.TYPE_CODE);
            }
            catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: " + e, (Throwable)e);
            }
        }
        try {
            this.setNonNullParameter(ps, i, parameter, jdbcType);
        }
        catch (Exception e) {
            throw new TypeException("Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . Try setting a different JdbcType for this parameter or a different configuration property. Cause: " + e, (Throwable)e);
        }
    }

    public void setNonNullParameter(PreparedStatement ps, int i, DataModelType.BizType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    public DataModelType.BizType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.getResult(rs.getString(columnName));
    }

    public DataModelType.BizType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.getResult(rs.getString(columnIndex));
    }

    public DataModelType.BizType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.getResult(cs.getString(columnIndex));
    }

    private DataModelType.BizType getResult(String value) {
        if (value == null) {
            return null;
        }
        return DataModelType.BizType.valueOf(value);
    }
}

