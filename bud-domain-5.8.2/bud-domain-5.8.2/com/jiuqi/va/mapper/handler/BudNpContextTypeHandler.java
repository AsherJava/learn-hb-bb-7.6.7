/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.ibatis.type.BaseTypeHandler
 *  org.apache.ibatis.type.JdbcType
 *  org.apache.ibatis.type.MappedJdbcTypes
 *  org.apache.ibatis.type.MappedTypes
 */
package com.jiuqi.va.mapper.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.IOException;
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

@MappedTypes(value={NpContext.class})
@MappedJdbcTypes(value={JdbcType.VARCHAR})
public class BudNpContextTypeHandler
extends BaseTypeHandler<NpContext> {
    private static final Logger logger = LoggerFactory.getLogger(BudNpContextTypeHandler.class);

    public void setNonNullParameter(PreparedStatement ps, int i, NpContext parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setString(i, null);
            return;
        }
        NpContextImpl npContext = new NpContextImpl();
        npContext.setIdentity(parameter.getIdentity());
        npContext.setLoginDate(parameter.getLoginDate());
        npContext.setOrganization(parameter.getOrganization());
        npContext.setTenant(parameter.getTenant());
        npContext.setUser(parameter.getUser());
        try {
            ps.setString(i, ((ObjectMapper)ApplicationContextRegister.getBean(ObjectMapper.class)).writeValueAsString((Object)npContext));
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public NpContext getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.get(rs.getString(columnName));
    }

    public NpContext getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.get(rs.getString(columnIndex));
    }

    public NpContext getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.get(cs.getString(columnIndex));
    }

    private NpContext get(String val) {
        if (val == null) {
            return null;
        }
        try {
            return (NpContext)((ObjectMapper)ApplicationContextRegister.getBean(ObjectMapper.class)).readValue(val, NpContext.class);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

