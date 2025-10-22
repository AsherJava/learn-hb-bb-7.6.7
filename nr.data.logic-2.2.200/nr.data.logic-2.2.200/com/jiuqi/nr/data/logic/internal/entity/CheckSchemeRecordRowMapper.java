/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.data.logic.internal.entity;

import com.jiuqi.nr.data.logic.internal.entity.CheckSchemeRecordDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CheckSchemeRecordRowMapper
implements RowMapper<CheckSchemeRecordDO> {
    public CheckSchemeRecordDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CheckSchemeRecordDO checkSchemeRecordDO = new CheckSchemeRecordDO();
        checkSchemeRecordDO.setKey(rs.getString("RWIF_KEY"));
        checkSchemeRecordDO.setUserId(rs.getString("RWIF_USER"));
        checkSchemeRecordDO.setFormSchemeKey(rs.getString("RWIF_FM_SCHEME"));
        checkSchemeRecordDO.setCheckTime(rs.getLong("RWIF_DATETIME"));
        checkSchemeRecordDO.setCkrParJson(rs.getString("RWIF_CHECK_INFO"));
        return checkSchemeRecordDO;
    }
}

