/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.GetClobDataUtil
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.batch.summary.storage.Impl;

import com.jiuqi.np.definition.common.GetClobDataUtil;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.batch.summary.storage.CustomCalibreDao;
import com.jiuqi.nr.batch.summary.storage.NamedParameterSqlBuilder;
import com.jiuqi.nr.batch.summary.storage.SummarySchemeDao;
import com.jiuqi.nr.batch.summary.storage.common.BatchSummaryHelper;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeUnit;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim;
import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SchemeRangeFormInfo;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SchemeRangeUnitInfo;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SchemeTargetDimInfo;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SingleDimDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeUnitType;
import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Repository
public class SummarySchemeDaoImpl
implements SummarySchemeDao {
    private static final String BS_KEY = "BS_KEY";
    private static final String BS_TITLE = "BS_TITLE";
    private static final String BS_CODE = "BS_CODE";
    private static final String BS_GROUP = "BS_GROUP";
    private static final String BS_TASK = "BS_TASK";
    private static final String BS_CREATOR = "BS_CREATOR";
    private static final String BS_ENTITY_ID = "BS_ENTITY_ID";
    private static final String TABLE_NAME = "NR_BS_SCHEME";
    private static final String BS_SINGLE_DIM = "BS_SINGLE_DIM";
    private static final String[] columns = new String[]{"BS_KEY", "BS_CODE", "BS_TITLE", "BS_GROUP", "BS_TASK", "BS_ORDINAL", "BS_CREATOR", "BS_UPDATE_TIME", "BS_SUM_DATA_TIME", "BS_TARGET_DIM", "BS_TARGET_DIM_TYPE", "BS_RANGE_UNIT", "BS_RANGE_UNIT_TYPE", "BS_RANGE_FORM", "BS_RANGE_FORM_TYPE", "BS_ENTITY_ID", "BS_SINGLE_DIM"};
    private static final String[] updateColumns = new String[]{columns[2], columns[3], columns[5], columns[7], columns[8], columns[11], columns[12], columns[13], columns[14]};
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private CustomCalibreDao customCalibreDao;
    @Autowired
    private BatchSummaryHelper batchSummaryHelper;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int insertRow(SummarySchemeDefine scheme) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource source = this.buildMapSource(scheme);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int modifyRow(SummarySchemeImpl scheme) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(updateColumns).andWhere(BS_KEY);
        MapSqlParameterSource source = this.buildUpdateMapSource(scheme);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public int modifySchemeDataDate(String schemeKey, Date date) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(new String[]{columns[8]}).andWhere(BS_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_KEY, (Object)schemeKey);
        source.addValue(columns[8], (Object)date);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int removeRow(String schemeKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(BS_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_KEY, (Object)schemeKey);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public int moveRow(String groupKey, List<String> schemeKeys) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(new String[]{BS_GROUP}).inWhere(BS_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_GROUP, (Object)groupKey);
        source.addValue(BS_KEY, schemeKeys);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public SummaryScheme findScheme(String schemeKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(BS_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_KEY, (Object)schemeKey);
        List<SummaryScheme> schemes = this.executeQuery(sqlBuilder.toString(), source);
        return schemes.size() == 1 ? schemes.get(0) : null;
    }

    @Override
    public SummaryScheme findSchemeByTaskGroupAndTitle(String task, String groupKey, String schemeTitle) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(BS_TASK, BS_GROUP, BS_TITLE);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_TASK, (Object)task);
        source.addValue(BS_GROUP, (Object)groupKey);
        source.addValue(BS_TITLE, (Object)schemeTitle);
        List<SummaryScheme> schemes = this.executeQuery(sqlBuilder.toString(), source);
        return schemes.size() == 1 ? schemes.get(0) : null;
    }

    @Override
    public SummaryScheme findScheme(String task, String schemeCode) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(BS_TASK, BS_CODE);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_TASK, (Object)task);
        source.addValue(BS_CODE, (Object)schemeCode);
        List<SummaryScheme> schemes = this.executeQuery(sqlBuilder.toString(), source);
        return schemes.size() == 1 ? schemes.get(0) : null;
    }

    @Override
    public List<SummaryScheme> findSchemes(String task, String creator) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(BS_TASK, BS_CREATOR).orderBy(columns[2]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_TASK, (Object)task);
        source.addValue(BS_CREATOR, (Object)creator);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<SummaryScheme> findSchemes(List<String> schemeKeys) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).inWhere(BS_KEY).orderBy(columns[2]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_KEY, schemeKeys);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<SummaryScheme> findGroupSchemes(String task, String groupKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(BS_TASK, BS_GROUP, BS_CREATOR).orderBy(columns[2]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_TASK, (Object)task);
        source.addValue(BS_GROUP, (Object)groupKey);
        source.addValue(BS_CREATOR, (Object)BatchSummaryUtils.getCurrentUserID());
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<SummaryScheme> findGroupSchemes(String task, List<String> groupKeys) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).inWhere(BS_GROUP).and().andColumns(BS_TASK, BS_CREATOR).orderBy(columns[2]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BS_TASK, (Object)task);
        source.addValue(BS_GROUP, groupKeys);
        source.addValue(BS_CREATOR, (Object)BatchSummaryUtils.getCurrentUserID());
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    private List<SummaryScheme> executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readSummaryScheme);
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private MapSqlParameterSource buildUpdateMapSource(SummarySchemeImpl scheme) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)scheme.getKey());
        source.addValue(columns[2], (Object)scheme.getTitle());
        source.addValue(columns[3], (Object)scheme.getGroup());
        source.addValue(columns[5], (Object)scheme.getOrdinal());
        source.addValue(columns[7], (Object)new Timestamp(scheme.getUpdateTime().getTime()));
        source.addValue(columns[8], (Object)new Timestamp(scheme.getSumDataTime().getTime()));
        source.addValue(columns[11], (Object)scheme.getRangeUnit().valueToClob());
        source.addValue(columns[12], (Object)scheme.getRangeUnit().getRangeUnitType().value);
        source.addValue(columns[13], (Object)scheme.getRangeForm().valueToClob());
        source.addValue(columns[14], (Object)scheme.getRangeForm().getRangeFormType().value);
        return source;
    }

    private MapSqlParameterSource buildMapSource(SummarySchemeDefine scheme) {
        MapSqlParameterSource source = this.buildUpdateMapSource(scheme);
        source.addValue(columns[1], (Object)scheme.getCode());
        source.addValue(columns[4], (Object)scheme.getTask());
        source.addValue(columns[6], (Object)scheme.getCreator());
        source.addValue(columns[9], (Object)scheme.getTargetDim().getDimValue());
        source.addValue(columns[10], (Object)scheme.getTargetDim().getTargetDimType().value);
        String entityId = scheme.getEntityId();
        if (!StringUtils.hasLength(entityId)) {
            entityId = this.batchSummaryHelper.getCorporateDefaultEntityId(scheme.getTask());
        }
        source.addValue(columns[15], (Object)entityId);
        List<SingleDim> singleDims = scheme.getSingleDims();
        if (singleDims != null && !singleDims.isEmpty()) {
            StringBuilder singleDimsString = new StringBuilder();
            for (SingleDim singleDim : singleDims) {
                singleDimsString.append(singleDim.getEntityId()).append(":").append(singleDim.getValue()).append(";");
            }
            if (singleDimsString.length() > 0) {
                singleDimsString.setLength(singleDimsString.length() - 1);
            }
            source.addValue(columns[16], (Object)singleDimsString.toString());
        } else {
            String corporateDefaultValue = this.batchSummaryHelper.getCorporateDefaultValue(scheme);
            source.addValue(columns[16], (Object)corporateDefaultValue);
        }
        return source;
    }

    private SummaryScheme readSummaryScheme(ResultSet rs, int rowIdx) throws SQLException {
        SummarySchemeDefine impl = new SummarySchemeDefine();
        impl.setKey(rs.getString(columns[0]));
        impl.setCode(rs.getString(columns[1]));
        impl.setTitle(rs.getString(columns[2]));
        impl.setGroup(rs.getString(columns[3]));
        impl.setTask(rs.getString(columns[4]));
        impl.setOrdinal(rs.getString(columns[5]));
        impl.setCreator(rs.getString(columns[6]));
        impl.setUpdateTime(this.translate2Date(rs.getTimestamp(columns[7])));
        impl.setSumDataTime(this.translate2Date(rs.getTimestamp(columns[8])));
        impl.setTargetDim(this.readTargetDim(impl, rs.getString(columns[9]), rs.getInt(columns[10])));
        impl.setRangeUnit(this.readRangeUnit(GetClobDataUtil.getClobFieldData((ResultSet)rs, (String)columns[11]), rs.getInt(columns[12])));
        impl.setRangeForm(this.readRangeForm(GetClobDataUtil.getClobFieldData((ResultSet)rs, (String)columns[13]), rs.getInt(columns[14])));
        impl.setEntityId(rs.getString(columns[15]));
        String singleDimJson = rs.getString(columns[16]);
        if (singleDimJson != null && !singleDimJson.isEmpty() && !"DEFAULT_TEMP_COPORATE_VALUE".equals(singleDimJson)) {
            String[] singDimStr = singleDimJson.split(";");
            ArrayList<SingleDim> singleDims = new ArrayList<SingleDim>();
            for (String singleDim : singDimStr) {
                String[] singleDimArr = singleDim.split(":");
                if (singleDimArr.length <= 1) continue;
                singleDims.add(new SingleDimDefine(singleDimArr[0], singleDimArr[1]));
            }
            impl.setSingleDims(singleDims);
        }
        return impl;
    }

    private Date translate2Date(Timestamp timestamp) {
        if (timestamp != null) {
            return new Date(timestamp.getTime());
        }
        return null;
    }

    private SchemeTargetDim readTargetDim(SummarySchemeDefine schemeDefine, String dimValue, int dimTypeVal) {
        SchemeTargetDimInfo impl = new SchemeTargetDimInfo(this.customCalibreDao, schemeDefine);
        impl.setDimValue(dimValue);
        impl.setTargetDimType(TargetDimType.valueOf(dimTypeVal));
        return impl;
    }

    private SchemeRangeUnit readRangeUnit(String jsonStr, int rangeType) {
        SchemeRangeUnitInfo impl = new SchemeRangeUnitInfo();
        impl.setRangeUnitType(RangeUnitType.valueOf(rangeType));
        impl.transformAndSetCheckList(jsonStr);
        return impl;
    }

    private SchemeRangeForm readRangeForm(String jsonStr, int rangeType) {
        SchemeRangeFormInfo impl = new SchemeRangeFormInfo();
        impl.setRangeFormType(RangeFormType.valueOf(rangeType));
        impl.transformAndSetFromList(jsonStr);
        return impl;
    }
}

