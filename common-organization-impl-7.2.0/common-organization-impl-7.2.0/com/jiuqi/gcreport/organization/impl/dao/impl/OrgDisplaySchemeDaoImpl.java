/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.gcreport.organization.impl.dao.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.organization.impl.bean.OrgDisplaySchemeDO;
import com.jiuqi.gcreport.organization.impl.dao.OrgDisplaySchemeDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class OrgDisplaySchemeDaoImpl
implements OrgDisplaySchemeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public OrgDisplaySchemeDO findByOwner(String formScheme, String entityID, String owner) {
        String sql = String.format("select CF_KEY,CF_FORMSCHEME,CF_ENTITYID,CF_OWNER,CF_FIELDS,CF_CREATIVE from %s where CF_FORMSCHEME = :%s and CF_ENTITYID = :%s and CF_OWNER = :%s order by CF_CREATIVE desc", "NR_UNITTREE_CAPTION_FIELDS", "CF_FORMSCHEME", "CF_ENTITYID", "CF_OWNER");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("CF_FORMSCHEME", (Object)formScheme);
        parameterSource.addValue("CF_ENTITYID", (Object)entityID);
        parameterSource.addValue("CF_OWNER", (Object)owner);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query.size() > 0 ? (OrgDisplaySchemeDO)query.get(0) : null;
    }

    private OrgDisplaySchemeDO create(ResultSet rs) throws SQLException {
        OrgDisplaySchemeDO impl = new OrgDisplaySchemeDO();
        impl.setKey(rs.getString("CF_KEY"));
        impl.setFormScheme(rs.getString("CF_FORMSCHEME"));
        impl.setEntityId(rs.getString("CF_ENTITYID"));
        impl.setOwner(rs.getString("CF_OWNER"));
        impl.setFields(this.fieldsToList(rs.getString("CF_FIELDS")));
        impl.setCreateTime(rs.getDate("CF_CREATIVE"));
        return impl;
    }

    private List<String> fieldsToList(String fields) {
        List<String> captionFields = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)fields)) {
            String[] split = fields.split(";");
            captionFields = Arrays.asList(split);
        }
        return captionFields;
    }
}

