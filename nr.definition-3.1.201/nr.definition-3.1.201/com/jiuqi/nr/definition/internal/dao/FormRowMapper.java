/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.internal.dao.FormDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class FormRowMapper
implements RowMapper<FormDO> {
    FormRowMapper() {
    }

    public FormDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        FormDO formDO = new FormDO();
        formDO.setKey(rs.getString("FM_KEY"));
        formDO.setCode(rs.getString("FM_CODE"));
        formDO.setTitle(rs.getString("FM_TITLE"));
        formDO.setType(rs.getInt("FM_TYPE"));
        return formDO;
    }
}

