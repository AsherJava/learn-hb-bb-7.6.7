/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.validation.constraints.NotNull
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcInsert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.unit.treestore.fmdmdisplay.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplaySchemeImpl;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FMDMDisplaySchemeDaoImpl
implements FMDMDisplaySchemeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] batchInsert(@NotNull List<FMDMDisplayScheme> schemes) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("NR_UNITTREE_CAPTION_FIELDS");
        MapSqlParameterSource[] sources = this.buildBatchSqlParameterSource(schemes);
        return insertActor.executeBatch((SqlParameterSource[])sources);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] batchUpdate(@NotNull List<FMDMDisplayScheme> schemes) {
        String sql = String.format("update %s set CF_FORMSCHEME = :%s,CF_ENTITYID = :%s,CF_OWNER = :%s,CF_FIELDS = :%s,CF_CREATIVE = :%s where CF_KEY = :%s", "NR_UNITTREE_CAPTION_FIELDS", "CF_FORMSCHEME", "CF_ENTITYID", "CF_OWNER", "CF_FIELDS", "CF_CREATIVE", "CF_KEY");
        MapSqlParameterSource[] sources = this.buildBatchSqlParameterSource(schemes);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sql, (SqlParameterSource[])sources);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int batchDelete(@NotNull List<String> keys) {
        String sql = String.format("delete from %s where CF_KEY in (:%s)", "NR_UNITTREE_CAPTION_FIELDS", "CF_KEY");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("CF_KEY", keys);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public FMDMDisplayScheme findByKey(@NotNull String key) {
        String sql = String.format("select CF_KEY,CF_FORMSCHEME,CF_ENTITYID,CF_OWNER,CF_FIELDS,CF_CREATIVE from %s where CF_KEY = :%s", "NR_UNITTREE_CAPTION_FIELDS", "CF_KEY");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("CF_KEY", (Object)key);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query.size() == 1 ? (FMDMDisplayScheme)query.get(0) : null;
    }

    @Override
    public FMDMDisplayScheme findByTaskAndOwner(@NotNull String formScheme, @NotNull String entityID, @NotNull String owner) {
        String sql = String.format("select CF_KEY,CF_FORMSCHEME,CF_ENTITYID,CF_OWNER,CF_FIELDS,CF_CREATIVE from %s where CF_FORMSCHEME = :%s and CF_ENTITYID = :%s and CF_OWNER = :%s order by CF_CREATIVE desc", "NR_UNITTREE_CAPTION_FIELDS", "CF_FORMSCHEME", "CF_ENTITYID", "CF_OWNER");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("CF_FORMSCHEME", (Object)formScheme);
        parameterSource.addValue("CF_ENTITYID", (Object)entityID);
        parameterSource.addValue("CF_OWNER", (Object)owner);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query.size() > 0 ? (FMDMDisplayScheme)query.get(0) : null;
    }

    public List<FMDMDisplayScheme> findAllRows() {
        String sql = String.format("select CF_KEY,CF_FORMSCHEME,CF_ENTITYID,CF_OWNER,CF_FIELDS,CF_CREATIVE from %s ", "NR_UNITTREE_CAPTION_FIELDS");
        return this.jdbcTemplate.query(sql, (rs, row) -> this.create(rs));
    }

    private FMDMDisplayScheme create(ResultSet rs) throws SQLException {
        FMDMDisplaySchemeImpl impl = new FMDMDisplaySchemeImpl();
        impl.setKey(rs.getString("CF_KEY"));
        impl.setFormScheme(rs.getString("CF_FORMSCHEME"));
        impl.setEntityId(rs.getString("CF_ENTITYID"));
        impl.setOwner(rs.getString("CF_OWNER"));
        impl.setFields(this.fieldsToList(rs.getString("CF_FIELDS")));
        impl.setCreateTime(rs.getDate("CF_CREATIVE"));
        return impl;
    }

    private MapSqlParameterSource[] buildBatchSqlParameterSource(List<FMDMDisplayScheme> impls) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[impls.size()];
        for (int i = 0; i < impls.size(); ++i) {
            FMDMDisplayScheme scheme = impls.get(i);
            sources[i] = this.buildSqlParameterSource(scheme);
        }
        return sources;
    }

    private MapSqlParameterSource buildSqlParameterSource(FMDMDisplayScheme scheme) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("CF_KEY", (Object)scheme.getKey()).addValue("CF_FORMSCHEME", (Object)scheme.getFormScheme()).addValue("CF_ENTITYID", (Object)scheme.getEntityId()).addValue("CF_OWNER", (Object)scheme.getOwner()).addValue("CF_FIELDS", (Object)this.fieldsToString(scheme.getFields())).addValue("CF_CREATIVE", (Object)Timestamp.valueOf(LocalDateTime.now()));
        return source;
    }

    private String fieldsToString(List<String> fields) {
        StringBuilder captionFields = new StringBuilder();
        if (fields != null) {
            for (String cf : fields) {
                captionFields.append(cf).append(";");
            }
        }
        return captionFields.toString();
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

