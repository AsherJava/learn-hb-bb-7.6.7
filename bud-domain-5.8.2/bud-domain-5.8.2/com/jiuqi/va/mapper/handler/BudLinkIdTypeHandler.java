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

import com.jiuqi.budget.components.LinkId;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

@MappedTypes(value={LinkId.class})
@MappedJdbcTypes(value={JdbcType.VARCHAR, JdbcType.NVARCHAR, JdbcType.CLOB})
public class BudLinkIdTypeHandler
extends BaseTypeHandler<LinkId> {
    public void setNonNullParameter(PreparedStatement ps, int i, LinkId parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter == null ? null : parameter.toString());
    }

    public LinkId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.get(rs.getString(columnName));
    }

    public LinkId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.get(rs.getString(columnIndex));
    }

    public LinkId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.get(cs.getString(columnIndex));
    }

    private LinkId get(String val) {
        if (!StringUtils.hasLength(val)) {
            return null;
        }
        return new LinkId(val);
    }
}

