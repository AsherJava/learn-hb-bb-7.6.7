/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.transmission.data.dao.mapper;

import com.jiuqi.nr.transmission.data.domain.SyncSchemeDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SyncSchemeMapper
implements RowMapper<SyncSchemeDO> {
    public static final String TABLE_NAME = "NR_TRANS_SCHEME";
    public static final String FIELD_TS_KEY = "TS_KEY";
    public static final String FIELD_TS_CODE = "TS_CODE";
    public static final String FIELD_TS_TITLE = "TS_TITLE";
    public static final String FIELD_TS_GROUP = "TS_GROUP";
    public static final String FIELD_TS_DESC = "TS_DESC";
    public static final String FIELD_TS_UPDATE_TIME = "TS_UPDATE_TIME";
    public static final String FIELD_TS_ORDER = "TS_ORDER";

    public SyncSchemeDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SyncSchemeDO syncSchemeDO = new SyncSchemeDO();
        syncSchemeDO.setKey(rs.getString(FIELD_TS_KEY));
        syncSchemeDO.setCode(rs.getString(FIELD_TS_CODE));
        syncSchemeDO.setTitle(rs.getString(FIELD_TS_TITLE));
        syncSchemeDO.setGroup(rs.getString(FIELD_TS_GROUP));
        syncSchemeDO.setDesc(rs.getString(FIELD_TS_DESC));
        syncSchemeDO.setUpdataTime(rs.getDate(FIELD_TS_UPDATE_TIME));
        syncSchemeDO.setOrder(rs.getString(FIELD_TS_ORDER));
        return syncSchemeDO;
    }
}

