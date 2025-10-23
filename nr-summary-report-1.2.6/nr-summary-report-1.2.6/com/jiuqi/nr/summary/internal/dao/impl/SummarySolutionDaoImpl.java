/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionDao;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionDO;
import com.jiuqi.nr.summary.utils.SummaryReportTransUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class SummarySolutionDaoImpl
extends BaseDao
implements ISummarySolutionDao {
    private static final Logger logger = LoggerFactory.getLogger(SummarySolutionDaoImpl.class);
    private static final Class<SummarySolutionDO> implClazz = SummarySolutionDO.class;

    public Class<?> getClz() {
        return implClazz;
    }

    public Class<?> getExternalTransCls() {
        return SummaryReportTransUtil.class;
    }

    @Override
    public String insert(SummarySolutionDO summarySolutionDO) throws DBParaException {
        super.insert((Object)summarySolutionDO);
        return summarySolutionDO.getKey();
    }

    @Override
    public void delete(String key) throws DBParaException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete((Object)key);
    }

    @Override
    public void update(SummarySolutionDO summarySolutionDO) throws DBParaException {
        super.update((Object)summarySolutionDO);
    }

    @Override
    public SummarySolutionDO getByKey(String key, boolean withDetail) {
        Assert.notNull((Object)key, "key must not be null.");
        if (withDetail) {
            return (SummarySolutionDO)super.getByKey((Object)key, implClazz);
        }
        String sql = this.getQuerySqlWithKey(key);
        List result = this.jdbcTemplate.query(sql, (rs, rowNum) -> this.buildFromRS(rs), new Object[]{key});
        return result.isEmpty() ? null : (SummarySolutionDO)result.get(0);
    }

    @Override
    public SummarySolutionDO getByName(String name) {
        return (SummarySolutionDO)super.getBy(String.format("%s=?", "SS_NAME"), (Object[])new String[]{name}, implClazz);
    }

    @Override
    public SummarySolutionDO getByGroupAndTitle(String group, String title) {
        if (StringUtils.hasLength(group)) {
            return (SummarySolutionDO)super.getBy(String.format("%s=? and %s=?", "SG_KEY", "SS_TITLE"), (Object[])new String[]{group, title}, implClazz);
        }
        return (SummarySolutionDO)super.getBy(String.format("%s is null and %s=?", "SG_KEY", "SS_TITLE"), (Object[])new String[]{title}, implClazz);
    }

    @Override
    public List<SummarySolutionDO> listByGroup(String groupKey, boolean withDetail) {
        if (withDetail) {
            return this.listByGroupWithDetail(groupKey);
        }
        String sql = this.getQuerySqlWithGroup(groupKey);
        if (StringUtils.hasLength(groupKey)) {
            return this.jdbcTemplate.query(sql, ps -> ps.setString(1, groupKey), (rs, rowNum) -> this.buildFromRS(rs));
        }
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> this.buildFromRS(rs));
    }

    @Override
    public List<SummarySolutionDO> listByGroups(List<String> groupKeys, boolean withDetail) {
        if (withDetail) {
            if (!CollectionUtils.isEmpty(groupKeys)) {
                StringBuilder whereBuilder = new StringBuilder("SG_KEY");
                whereBuilder.append(" in(");
                Object[] keyParamArr = new String[groupKeys.size()];
                Arrays.fill(keyParamArr, "?");
                String keyParamStr = String.join((CharSequence)",", (CharSequence[])keyParamArr);
                whereBuilder.append(keyParamStr);
                whereBuilder.append(")");
                return super.list(whereBuilder.toString(), groupKeys.toArray(), implClazz);
            }
            return Collections.emptyList();
        }
        String sql = "select %s,%s,%s,%s,%s,%s,%s,%s from %s where %s in (";
        sql = String.format(sql, "SS_KEY", "SS_NAME", "SS_TITLE", "SG_KEY", "SS_MAINTASK", "SS_TARGETDIMENSION", "SS_ORDER", "SS_MODIFY_TIME", this.tablename, "SG_KEY");
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append("%s)");
        String placeholders = String.join((CharSequence)",", Collections.nCopies(groupKeys.size(), "?"));
        String exeSql = String.format(sqlBuilder.toString(), placeholders);
        return this.jdbcTemplate.query(exeSql, groupKeys.toArray(), (rs, rowNum) -> this.buildFromRS(rs));
    }

    @Override
    public List<SummarySolutionDO> fuzzyQuery(String keywords) {
        String sql = "select %s,%s,%s,%s,%s,%s,%s,%s from %s where %s like ? or %s like ?";
        sql = String.format(sql, "SS_KEY", "SS_NAME", "SS_TITLE", "SG_KEY", "SS_MAINTASK", "SS_TARGETDIMENSION", "SS_ORDER", "SS_MODIFY_TIME", this.tablename, "SS_NAME", "SS_TITLE");
        return this.jdbcTemplate.query(sql, ps -> {
            ps.setString(1, "%" + keywords + "%");
            ps.setString(2, "%" + keywords + "%");
        }, (rs, rowNum) -> this.buildFromRS(rs));
    }

    private List<SummarySolutionDO> listByGroupWithDetail(String groupKey) {
        if (StringUtils.hasLength(groupKey)) {
            return super.list(new String[]{"SG_KEY"}, (Object[])new String[]{groupKey}, implClazz);
        }
        return super.list(String.format("%s is null", "SG_KEY"), null, implClazz);
    }

    private String getQuerySqlWithKey(String key) {
        String sql = "select %s,%s,%s,%s,%s,%s,%s,%s from %s ";
        sql = String.format(sql, "SS_KEY", "SS_NAME", "SS_TITLE", "SG_KEY", "SS_MAINTASK", "SS_TARGETDIMENSION", "SS_ORDER", "SS_MODIFY_TIME", this.tablename);
        String whereStr = String.format("where %s = ?", "SS_KEY");
        sql = sql + whereStr;
        return sql;
    }

    private String getQuerySqlWithGroup(String groupKey) {
        String sql = "select %s,%s,%s,%s,%s,%s,%s,%s from %s ";
        sql = String.format(sql, "SS_KEY", "SS_NAME", "SS_TITLE", "SG_KEY", "SS_MAINTASK", "SS_TARGETDIMENSION", "SS_ORDER", "SS_MODIFY_TIME", this.tablename);
        String whereStr = StringUtils.hasLength(groupKey) ? String.format("where %s = ?", "SG_KEY") : String.format("where %s is null", "SG_KEY");
        sql = sql + whereStr;
        return sql;
    }

    private SummarySolutionDO buildFromRS(ResultSet rs) {
        try {
            SummarySolutionDO solutionDO = new SummarySolutionDO();
            solutionDO.setKey(rs.getString("SS_KEY"));
            solutionDO.setName(rs.getString("SS_NAME"));
            solutionDO.setTitle(rs.getString("SS_TITLE"));
            solutionDO.setGroup(rs.getString("SG_KEY"));
            solutionDO.setMainTask(rs.getString("SS_MAINTASK"));
            solutionDO.setTargetDimension(rs.getString("SS_TARGETDIMENSION"));
            solutionDO.setOrder(rs.getString("SS_ORDER"));
            solutionDO.setModifyTime(Instant.ofEpochMilli(rs.getTimestamp("SS_MODIFY_TIME").getTime()));
            return solutionDO;
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

