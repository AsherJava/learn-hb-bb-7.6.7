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
import com.jiuqi.nr.summary.internal.dao.ISummaryDataCellDao;
import com.jiuqi.nr.summary.internal.entity.SummaryDataCellDO;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractSummaryDataCellDao<DO extends SummaryDataCellDO>
extends BaseDao
implements ISummaryDataCellDao<DO> {
    @Override
    public String insert(DO summaryDataCellDO) throws DBParaException {
        Assert.notNull(summaryDataCellDO, "summaryDataCellDO must not be null.");
        if (!StringUtils.hasLength(((SummaryDataCellDO)summaryDataCellDO).getKey())) {
            ((SummaryDataCellDO)summaryDataCellDO).setKey(UUID.randomUUID().toString());
        }
        if (((SummaryDataCellDO)summaryDataCellDO).getModifyTime() == null) {
            ((SummaryDataCellDO)summaryDataCellDO).setModifyTime(Instant.now());
        }
        super.insert(summaryDataCellDO);
        return ((SummaryDataCellDO)summaryDataCellDO).getKey();
    }

    @Override
    public void batchInsert(List<DO> summaryDataCellDOS) {
        super.insert(summaryDataCellDOS.toArray());
    }

    @Override
    public void delete(String key) throws DBParaException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete((Object)key);
    }

    @Override
    public void deleteByReport(String reportKey) throws DBParaException {
        super.deleteBy(new String[]{"SR_KEY"}, (Object[])new String[]{reportKey});
    }

    @Override
    public void deleteByReports(List<String> reportKeys) {
        StringBuilder deleteSqlBuilder = new StringBuilder();
        deleteSqlBuilder.append("delete from ");
        deleteSqlBuilder.append(this.tablename);
        deleteSqlBuilder.append(" where ");
        deleteSqlBuilder.append("SR_KEY");
        deleteSqlBuilder.append(" in (%s)");
        String placeholders = String.join((CharSequence)",", Collections.nCopies(reportKeys.size(), "?"));
        String sql = String.format(deleteSqlBuilder.toString(), placeholders);
        this.jdbcTemplate.update(sql, reportKeys.toArray());
    }

    @Override
    public void update(DO summaryDataCellDO) throws DBParaException {
        Assert.notNull(summaryDataCellDO, "summaryDataCellDO must not be null.");
        Assert.notNull((Object)((SummaryDataCellDO)summaryDataCellDO).getKey(), "summaryDataCelKey must not be null.");
        if (((SummaryDataCellDO)summaryDataCellDO).getModifyTime() == null) {
            ((SummaryDataCellDO)summaryDataCellDO).setModifyTime(Instant.now());
        }
        super.update(summaryDataCellDO);
    }

    @Override
    public void batchUpdate(List<DO> summaryDataCellDOS) {
        super.update(summaryDataCellDOS);
    }

    @Override
    public DO getByKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((SummaryDataCellDO)super.getByKey((Object)key, this.getClz()));
    }

    @Override
    public List<DO> listByReport(String reportKey) {
        Assert.notNull((Object)reportKey, "reportKey must not be null.");
        return super.list(new String[]{"SR_KEY"}, (Object[])new String[]{reportKey}, this.getClz());
    }

    public abstract Class<DO> getClz();
}

