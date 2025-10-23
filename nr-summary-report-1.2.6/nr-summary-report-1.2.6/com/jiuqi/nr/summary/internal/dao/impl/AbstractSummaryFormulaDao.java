/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.summary.internal.dao.ISummaryFormulaDao;
import com.jiuqi.nr.summary.internal.entity.SummaryFormulaDO;
import com.jiuqi.nr.summary.utils.SummaryReportTransUtil;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractSummaryFormulaDao<DO extends SummaryFormulaDO>
extends BaseDao
implements ISummaryFormulaDao<DO> {
    public Class<?> getExternalTransCls() {
        return SummaryReportTransUtil.class;
    }

    @Override
    public String insert(DO summaryFormulaDO) throws DBParaException {
        Assert.notNull(summaryFormulaDO, "summaryFormulaDO must not be null.");
        if (!StringUtils.hasLength(((SummaryFormulaDO)summaryFormulaDO).getKey())) {
            ((SummaryFormulaDO)summaryFormulaDO).setKey(UUID.randomUUID().toString());
        }
        if (((SummaryFormulaDO)summaryFormulaDO).getModifyTime() == null) {
            ((SummaryFormulaDO)summaryFormulaDO).setModifyTime(Instant.now());
        }
        if (!StringUtils.hasLength(((SummaryFormulaDO)summaryFormulaDO).getOrder())) {
            ((SummaryFormulaDO)summaryFormulaDO).setOrder(OrderGenerator.newOrder());
        }
        super.insert(summaryFormulaDO);
        return ((SummaryFormulaDO)summaryFormulaDO).getKey();
    }

    @Override
    public void batchInsert(List<DO> summaryFormulaDOs) {
        super.insert(summaryFormulaDOs.toArray());
    }

    @Override
    public void delete(String key) throws DBParaException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete((Object)key);
    }

    @Override
    public void batchDelete(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        super.delete(keys.toArray());
    }

    @Override
    public void deleteByReport(String reportKey) throws DBParaException {
        Assert.notNull((Object)reportKey, "reportKey must not be null.");
        super.deleteBy(new String[]{"SR_KEY"}, (Object[])new String[]{reportKey});
    }

    @Override
    public void deleteBySolution(String solutionKey) throws DBParaException {
        Assert.notNull((Object)solutionKey, "solutionKey must not be null.");
        super.deleteBy(new String[]{"SS_KEY"}, (Object[])new String[]{solutionKey});
    }

    @Override
    public void update(DO summaryFormulaDO) throws DBParaException {
        Assert.notNull(summaryFormulaDO, "summaryFormulaDO must not be null.");
        Assert.notNull((Object)((SummaryFormulaDO)summaryFormulaDO).getKey(), "summaryFormulaKey must not be null.");
        if (((SummaryFormulaDO)summaryFormulaDO).getModifyTime() == null) {
            ((SummaryFormulaDO)summaryFormulaDO).setModifyTime(Instant.now());
        }
        super.update(summaryFormulaDO);
    }

    @Override
    public void batchUpdate(List<DO> summaryFormulaDOs) {
        super.update(summaryFormulaDOs.toArray());
    }

    @Override
    public DO getByKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((SummaryFormulaDO)super.getByKey((Object)key, this.getClz()));
    }

    @Override
    public List<DO> listByReport(String reportKey) {
        Assert.notNull((Object)reportKey, "reportKey must not be null.");
        return super.list(new String[]{"SR_KEY"}, (Object[])new String[]{reportKey}, this.getClz());
    }

    @Override
    public List<DO> listBySolution(String solutionKey) {
        Assert.notNull((Object)solutionKey, "solutionKey must not be null.");
        return super.list(new String[]{"SS_KEY"}, (Object[])new String[]{solutionKey}, this.getClz());
    }

    public abstract Class<DO> getClz();
}

