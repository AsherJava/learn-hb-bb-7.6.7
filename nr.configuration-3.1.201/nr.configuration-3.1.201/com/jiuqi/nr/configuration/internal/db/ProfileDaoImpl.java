/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.configuration.internal.db;

import com.jiuqi.nr.configuration.db.IProfileDao;
import com.jiuqi.nr.configuration.entity.ProfileEntity;
import com.jiuqi.nr.configuration.entity.ProfileRowMapper;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class ProfileDaoImpl
implements IProfileDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(ProfileEntity profile) throws Exception {
        String sql = "insert into SYS_PROFILE ( pf_user_key,pf_code,update_time,pf_content ) values(?,?,?,?)";
        this.jdbcTemplate.update(sql, new Object[]{profile.getUserKey() == null ? "" : profile.getUserKey().toString(), profile.getCode(), Timestamp.from(Instant.now()), profile.getProfileContent()});
    }

    @Override
    public void update(ProfileEntity profile) throws Exception {
        String sql = "update SYS_PROFILE  set  update_time = ?,pf_content = ? where pf_user_key = ? and pf_code = ?";
        this.jdbcTemplate.update(sql, new Object[]{Timestamp.from(Instant.now()), profile.getProfileContent(), profile.getUserKey() == null ? "" : profile.getUserKey().toString(), profile.getCode()});
    }

    @Override
    public void delete(String userKey, String code) throws Exception {
        String sql = "delete from SYS_PROFILE where pf_user_key = ? and pf_code = ?";
        this.jdbcTemplate.update(sql, new Object[]{userKey.toString(), code});
    }

    @Override
    public ProfileEntity queryUnique(String userKey, String code) throws Exception {
        String sql = "select        pf_user_key,       pf_code,       update_time,       pf_content     from SYS_PROFILE     where pf_user_key = ? and pf_code = ?";
        ProfileEntity profile = (ProfileEntity)this.jdbcTemplate.queryForObject(sql, (RowMapper)new ProfileRowMapper(), new Object[]{userKey.toString(), code});
        return profile;
    }

    @Override
    public List<ProfileEntity> queryByUser(String userKey) throws Exception {
        String sql = "    select        pf_user_key,       pf_code,       update_time,       pf_content     from SYS_PROFILE     where pf_user_key = ? ";
        List profileList = this.jdbcTemplate.query(sql, (RowMapper)new ProfileRowMapper(), new Object[]{userKey == null ? "" : userKey.toString()});
        return profileList;
    }

    @Override
    public List<ProfileEntity> queryByCode(String code) throws Exception {
        String sql = "    select        pf_user_key,       pf_code,       update_time,       pf_content     from SYS_PROFILE     where pf_code = ? ";
        List profileList = this.jdbcTemplate.query(sql, (RowMapper)new ProfileRowMapper(), new Object[]{code});
        return profileList;
    }
}

