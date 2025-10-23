/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.summary.internal.dao.ISummaryReportDao;
import com.jiuqi.nr.summary.internal.entity.SummaryReportDO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public abstract class AbstractSummaryReportDao<DO extends SummaryReportDO>
extends BaseDao
implements ISummaryReportDao<DO> {
    @Override
    public String insert(DO summaryReportDO) throws DBParaException {
        super.insert(summaryReportDO);
        return ((SummaryReportDO)summaryReportDO).getKey();
    }

    @Override
    public void batchInsert(List<DO> summaryReportDOS) {
        super.insert(summaryReportDOS);
    }

    @Override
    public void delete(String key) throws DBParaException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete((Object)key);
    }

    @Override
    public void batchDelete(List<String> keys) {
        StringBuilder deleteSqlBuilder = new StringBuilder();
        deleteSqlBuilder.append("delete from ");
        deleteSqlBuilder.append(this.tablename);
        deleteSqlBuilder.append(" where ");
        deleteSqlBuilder.append("SR_KEY");
        deleteSqlBuilder.append(" in (%s)");
        String placeholders = String.join((CharSequence)",", Collections.nCopies(keys.size(), "?"));
        String sql = String.format(deleteSqlBuilder.toString(), placeholders);
        this.jdbcTemplate.update(sql, keys.toArray());
    }

    @Override
    public void deleteBySolution(String solutionKey) {
        super.deleteBy(new String[]{"summarySolutionKey"}, (Object[])new String[]{solutionKey});
    }

    @Override
    public void update(DO summaryReportDO, boolean base) throws DBParaException {
        if (base) {
            String sql = "update %s set %s=?,%s=?, %s=? where %s=?";
            sql = String.format(sql, this.tablename, "SR_NAME", "SR_TITLE", "SR_ORDER", "SR_KEY");
            this.jdbcTemplate.update(sql, new Object[]{((SummaryReportDO)summaryReportDO).getName(), ((SummaryReportDO)summaryReportDO).getTitle(), ((SummaryReportDO)summaryReportDO).getOrder(), ((SummaryReportDO)summaryReportDO).getKey()});
        } else {
            super.update(summaryReportDO);
        }
    }

    @Override
    public DO getByKey(String key, boolean withDetail) {
        Assert.notNull((Object)key, "key must not be null.");
        if (withDetail) {
            return (DO)((SummaryReportDO)super.getByKey((Object)key, this.getClz()));
        }
        String sql = "select %s,%s,%s,%s,%s,%s from %s where %s=?";
        List list = this.jdbcTemplate.query(sql = String.format(sql, "SR_KEY", "SR_NAME", "SR_TITLE", "SS_KEY", "SR_MODIFY_TIME", "SR_ORDER", this.tablename, "SR_KEY"), ps -> ps.setString(1, key), this.rowMapper());
        return (DO)(CollectionUtils.isEmpty(list) ? null : (SummaryReportDO)list.get(0));
    }

    @Override
    public List<DO> getByKeys(List<String> keys, boolean withDetail) {
        Assert.notEmpty(keys, "keys must not be null.");
        StringBuilder sqlWhere = new StringBuilder();
        if (withDetail) {
            sqlWhere.append("SR_KEY");
            sqlWhere.append(" in (");
            Object[] keyParamArr = new String[keys.size()];
            Arrays.fill(keyParamArr, "?");
            String keyParamStr = String.join((CharSequence)",", (CharSequence[])keyParamArr);
            sqlWhere.append(keyParamStr);
            sqlWhere.append(")");
            return super.list(sqlWhere.toString(), keys.toArray(), this.getClz());
        }
        String sql = "select %s,%s,%s,%s,%s,%s from %s";
        sql = String.format(sql, "SR_KEY", "SR_NAME", "SR_TITLE", "SS_KEY", "SR_MODIFY_TIME", "SR_ORDER", this.tablename);
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(" where ");
        sqlBuilder.append("SR_KEY");
        sqlWhere.append(" in (%s)");
        String placeholders = String.join((CharSequence)",", Collections.nCopies(keys.size(), "?"));
        String exeSql = String.format(sqlBuilder.toString(), placeholders);
        return this.jdbcTemplate.query(exeSql, keys.toArray(), this.rowMapper());
    }

    @Override
    public DO getByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        return (DO)((SummaryReportDO)super.getBy(String.format("%s=?", "SR_NAME"), (Object[])new String[]{code}, this.getClz()));
    }

    @Override
    public DO getBySolutionAndTitle(String solutionKey, String title) {
        Assert.notNull((Object)solutionKey, "solutionKey must not be null.");
        Assert.notNull((Object)title, "title must not be null.");
        return (DO)((SummaryReportDO)super.getBy(String.format("%s=? and %s=?", "SS_KEY", "SR_TITLE"), (Object[])new String[]{solutionKey, title}, this.getClz()));
    }

    @Override
    public List<DO> listBySolution(String solutionKey, boolean withDetail) {
        Assert.notNull((Object)solutionKey, "solutionKey must not be null.");
        if (withDetail) {
            return super.list(new String[]{"SS_KEY"}, (Object[])new String[]{solutionKey}, this.getClz());
        }
        String sql = "select %s,%s,%s,%s,%s,%s from %s where %s=?";
        sql = String.format(sql, "SR_KEY", "SR_NAME", "SR_TITLE", "SS_KEY", "SR_MODIFY_TIME", "SR_ORDER", this.tablename, "SS_KEY");
        return this.jdbcTemplate.query(sql, ps -> ps.setString(1, solutionKey), this.rowMapper());
    }

    @Override
    public List<DO> listBySolutions(List<String> solutionKeys, boolean withDetail) {
        Assert.notEmpty(solutionKeys, "solutionKey must not be null.");
        if (withDetail) {
            if (!CollectionUtils.isEmpty(solutionKeys)) {
                StringBuilder whereBuilder = new StringBuilder("SS_KEY");
                whereBuilder.append(" in(");
                Object[] keyParamArr = new String[solutionKeys.size()];
                Arrays.fill(keyParamArr, "?");
                String keyParamStr = String.join((CharSequence)",", (CharSequence[])keyParamArr);
                whereBuilder.append(keyParamStr);
                whereBuilder.append(")");
                return super.list(whereBuilder.toString(), solutionKeys.toArray(), this.getClz());
            }
            return Collections.emptyList();
        }
        String sql = "select %s,%s,%s,%s,%s,%s from %s where %s in(";
        sql = String.format(sql, "SR_KEY", "SR_NAME", "SR_TITLE", "SS_KEY", "SR_MODIFY_TIME", "SR_ORDER", this.tablename, "SS_KEY");
        StringBuilder sqlBuilder = new StringBuilder(sql);
        String groupKeyStr = String.join((CharSequence)",", solutionKeys);
        sqlBuilder.append(groupKeyStr);
        sqlBuilder.append(")");
        return this.jdbcTemplate.query(sqlBuilder.toString(), this.rowMapper());
    }

    @Override
    public List<DO> fuzzyQuery(String keywords) {
        String sql = "select %s,%s,%s,%s,%s,%s from %s where %s like ? or %s like ?";
        sql = String.format(sql, "SR_KEY", "SR_NAME", "SR_TITLE", "SS_KEY", "SR_MODIFY_TIME", "SR_ORDER", this.tablename, "SR_NAME", "SR_TITLE");
        return this.jdbcTemplate.query(sql, ps -> {
            ps.setString(1, "%" + keywords + "%");
            ps.setString(2, "%" + keywords + "%");
        }, this.rowMapper());
    }

    public abstract Class<DO> getClz();

    public abstract ResultSetExtractor<DO> resultSetExtractor();

    public abstract RowMapper<DO> rowMapper();
}

