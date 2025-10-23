/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.transmission.data.dao.mapper;

import com.jiuqi.nr.transmission.data.domain.SyncSchemeGroupDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SchemeGroupMapper
implements RowMapper<SyncSchemeGroupDO> {
    public static final String TABLE_NAME = "NR_TRANS_GROUP";
    public static final String FIELD_KEY = "TG_KEY";
    public static final String FIELD_TITLE = "TG_TITLE";
    public static final String FIELD_PARENT = "TG_PARENT";
    public static final String FIELD_ORDER = "TG_ORDER";

    public SyncSchemeGroupDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SyncSchemeGroupDO syncSchemeGroupDO = new SyncSchemeGroupDO();
        syncSchemeGroupDO.setKey(rs.getString(FIELD_KEY));
        syncSchemeGroupDO.setTitle(rs.getString(FIELD_TITLE));
        syncSchemeGroupDO.setParent(rs.getString(FIELD_PARENT));
        syncSchemeGroupDO.setOrder(rs.getString(FIELD_ORDER));
        return syncSchemeGroupDO;
    }
}

