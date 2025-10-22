/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.data.estimation.storage.dao.impl;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeTemplateDao;
import com.jiuqi.nr.data.estimation.storage.dao.NamedParameterSqlBuilder;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationForm;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationSchemeBase;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EstimationSchemeTemplateDao
implements IEstimationSchemeTemplateDao {
    public static final String tableName = "nr_data_estimation_scheme";
    public static final String[] columns = new String[]{"EC_KEY", "EC_CODE", "EC_TITLE", "EC_TASK", "EC_FORMSCHEME", "EC_FORMS", "EC_ACCESS_FORMULA", "EC_CAlC_FORMULA", "EC_UPDATE_TIME", "EC_CREATOR", "EC_ORDINAL"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<EstimationSchemeBase> findAllTemplate(String schemeTemplate) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.selectSQL(columns).andWhere(columns[9]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[9], (Object)schemeTemplate);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sqlBuilder.toString(), (SqlParameterSource)source, this::readEstimationScheme);
    }

    @Override
    public EstimationSchemeBase findTemplate(String schemeTemplate, String formSchemeId) {
        if ("manager_created_scheme".equals(schemeTemplate)) {
            List<EstimationSchemeBase> schemes = this.findEstimationSchemes(schemeTemplate, formSchemeId);
            return schemes.size() == 1 ? schemes.get(0) : null;
        }
        return null;
    }

    @Override
    public EstimationSchemeBase findEstimationScheme(String estimationSchemeKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.selectSQL(columns).andWhere(columns[0]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)estimationSchemeKey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List schemes = template.query(sqlBuilder.toString(), (SqlParameterSource)source, this::readEstimationScheme);
        return schemes.size() == 1 ? (EstimationSchemeBase)schemes.get(0) : null;
    }

    @Override
    public List<EstimationSchemeBase> findEstimationSchemes(String creator, String formSchemeId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.selectSQL(columns).andWhere(columns[9], columns[4]).orderBy(columns[8]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[9], (Object)creator);
        source.addValue(columns[4], (Object)formSchemeId);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sqlBuilder.toString(), (SqlParameterSource)source, this::readEstimationScheme);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int insertEstimationScheme(EstimationSchemeBase schemeInfo) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource source = this.buildMapSource(schemeInfo);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int updateEstimationScheme(EstimationSchemeBase schemeInfo) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.updateSQL(new String[]{columns[1], columns[2], columns[5], columns[6], columns[7], columns[8]}).andWhere(columns[0]);
        MapSqlParameterSource source = this.buildMapSource(schemeInfo);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    @Override
    public int deleteEstimationScheme(String eSchemeKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.deleteSQL().inWhere(columns[0]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)eSchemeKey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    private MapSqlParameterSource buildUpdateMapSource(EstimationSchemeBase schemeInfo) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)schemeInfo.getKey());
        source.addValue(columns[1], (Object)schemeInfo.getCode());
        source.addValue(columns[2], (Object)schemeInfo.getTitle());
        source.addValue(columns[5], (Object)EstimationSchemeUtils.toJSONStr(schemeInfo.getFormDefines()));
        source.addValue(columns[6], (Object)EstimationSchemeUtils.toJSONStr(schemeInfo.getAccessFormulaSchemes()));
        source.addValue(columns[7], (Object)EstimationSchemeUtils.toJSONStr(schemeInfo.getCalcFormulaSchemes()));
        source.addValue(columns[8], (Object)new Timestamp(schemeInfo.getUpdateTime().getTime()));
        return source;
    }

    private MapSqlParameterSource buildMapSource(EstimationSchemeBase schemeInfo) {
        MapSqlParameterSource source = this.buildUpdateMapSource(schemeInfo);
        source.addValue(columns[3], (Object)schemeInfo.getTaskId());
        source.addValue(columns[4], (Object)schemeInfo.getFormSchemeId());
        source.addValue(columns[9], (Object)schemeInfo.getCreator());
        source.addValue(columns[10], (Object)schemeInfo.getOrder());
        return source;
    }

    private EstimationSchemeBase readEstimationScheme(ResultSet rs, int rowIdx) throws SQLException {
        EstimationSchemeBase impl = new EstimationSchemeBase();
        impl.setKey(rs.getString(columns[0]));
        impl.setCode(rs.getString(columns[1]));
        impl.setTitle(rs.getString(columns[2]));
        impl.setTaskId(rs.getString(columns[3]));
        impl.setFormSchemeId(rs.getString(columns[4]));
        impl.setFormDefines(this.readFormInfos(rs.getClob(columns[5])));
        impl.setAccessFormulaSchemes(this.readFormulaSchemeInfos(rs.getClob(columns[6])));
        impl.setCalcFormulaSchemes(this.readFormulaSchemeInfos(rs.getClob(columns[7])));
        impl.setUpdateTime(this.translate2Date(rs.getTimestamp(columns[8])));
        impl.setCreator(rs.getString(columns[9]));
        impl.setOrder(rs.getString(columns[10]));
        return impl;
    }

    private Date translate2Date(Timestamp timestamp) {
        if (timestamp != null) {
            return new Date(timestamp.getTime());
        }
        return null;
    }

    private List<EstimationForm> readFormInfos(Clob clob) {
        if (clob != null) {
            return EstimationSchemeUtils.toJavaArray((String)EstimationSchemeUtils.clob2String((Clob)clob), EstimationForm.class);
        }
        return new ArrayList<EstimationForm>();
    }

    private List<String> readFormulaSchemeInfos(Clob clob) {
        if (clob != null) {
            return EstimationSchemeUtils.toJavaArray((String)EstimationSchemeUtils.clob2String((Clob)clob), String.class);
        }
        return null;
    }
}

