/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringHelper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.configuration.entity;

import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.nr.configuration.entity.ProfileEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProfileRowMapper
implements RowMapper<ProfileEntity> {
    public ProfileEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProfileEntity profile = new ProfileEntity();
        String userKey = rs.getString("pf_user_key");
        profile.setUserKey(StringHelper.isNull((String)userKey) ? null : userKey);
        profile.setCode(rs.getString("pf_code"));
        profile.setUpdateTime(rs.getTimestamp("update_time").toInstant());
        profile.setProfileContent(rs.getString("pf_content"));
        return profile;
    }
}

