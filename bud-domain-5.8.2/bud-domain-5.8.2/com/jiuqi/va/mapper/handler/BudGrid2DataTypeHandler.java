/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.apache.ibatis.type.BaseTypeHandler
 *  org.apache.ibatis.type.JdbcType
 *  org.apache.ibatis.type.MappedJdbcTypes
 *  org.apache.ibatis.type.MappedTypes
 */
package com.jiuqi.va.mapper.handler;

import com.jiuqi.nvwa.grid2.Grid2Data;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MappedTypes(value={Grid2Data.class})
@MappedJdbcTypes(value={JdbcType.BLOB})
public class BudGrid2DataTypeHandler
extends BaseTypeHandler<Grid2Data> {
    private static final Logger logger = LoggerFactory.getLogger(BudGrid2DataTypeHandler.class);

    public void setNonNullParameter(PreparedStatement ps, int i, Grid2Data parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, Grid2Data.gridToBytes((Grid2Data)parameter));
    }

    public Grid2Data getNullableResult(ResultSet rs, String columnName) throws SQLException {
        byte[] bytes = rs.getBytes(columnName);
        if (bytes == null) {
            return null;
        }
        try {
            return Grid2Data.bytesToGrid((byte[])bytes);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public Grid2Data getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        byte[] bytes = rs.getBytes(columnIndex);
        if (bytes == null) {
            return null;
        }
        try {
            return Grid2Data.bytesToGrid((byte[])bytes);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public Grid2Data getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        byte[] bytes = cs.getBytes(columnIndex);
        if (bytes == null) {
            return null;
        }
        try {
            return Grid2Data.bytesToGrid((byte[])bytes);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

