/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.transmission.data.dao.mapper;

import com.jiuqi.nr.transmission.data.domain.SyncEntityLastHistoryDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

public class SyncEntityLastHistoryMapper
implements RowMapper<SyncEntityLastHistoryDO> {
    public static final String TABLE_NAME = "NR_TRANS_ENTITY_HISTORY";
    public static final String FIELD_TEH_KEY = "TEH_KEY";
    public static final String FIELD_TEH_TASK = "TEH_TASK";
    public static final String FIELD_TEH_PERIOD = "TEH_PERIOD";
    public static final String FIELD_TEH_FORM = "TEH_FORM";
    public static final String FIELD_TEH_ENTITY = "TEH_ENTITY";
    public static final String FIELD_TEH_USERID = "TEH_USERID";
    public static final String FIELD_TEH_TIME = "TEH_TIME";

    public SyncEntityLastHistoryDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SyncEntityLastHistoryDO syncSchemeGroupDO = new SyncEntityLastHistoryDO();
        syncSchemeGroupDO.setKey(rs.getString(FIELD_TEH_KEY));
        syncSchemeGroupDO.setTaskKey(rs.getString(FIELD_TEH_TASK));
        syncSchemeGroupDO.setPeriod(rs.getString(FIELD_TEH_PERIOD));
        syncSchemeGroupDO.setFormKey(rs.getString(FIELD_TEH_FORM));
        syncSchemeGroupDO.setEntity(rs.getString(FIELD_TEH_ENTITY));
        syncSchemeGroupDO.setUserId(rs.getString(FIELD_TEH_USERID));
        if (rs.getTimestamp(FIELD_TEH_TIME) != null) {
            syncSchemeGroupDO.setTime(new Date(rs.getTimestamp(FIELD_TEH_TIME).getTime()));
        }
        return syncSchemeGroupDO;
    }
}

