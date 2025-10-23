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
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionGroupDao;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionGroupDO;
import com.jiuqi.nr.summary.utils.SummaryReportTransUtil;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Repository
public class SummarySolutionGroupDaoImpl
extends BaseDao
implements ISummarySolutionGroupDao {
    private static final Class<SummarySolutionGroupDO> implClazz = SummarySolutionGroupDO.class;

    public Class<?> getClz() {
        return implClazz;
    }

    public Class<?> getExternalTransCls() {
        return SummaryReportTransUtil.class;
    }

    @Override
    public String insert(SummarySolutionGroupDO summarySolutionGroupDO) throws DBParaException {
        super.insert((Object)summarySolutionGroupDO);
        return summarySolutionGroupDO.getKey();
    }

    @Override
    public void delete(String key) throws DBParaException {
        super.delete((Object)key);
    }

    @Override
    public void batchDelete(List<String> keys) throws DBParaException {
        Object[] keyArr = new String[keys.size()];
        keys.toArray(keyArr);
        super.delete(keyArr);
    }

    @Override
    public void update(SummarySolutionGroupDO summarySolutionGroupDO) throws DBParaException {
        super.update((Object)summarySolutionGroupDO);
    }

    @Override
    public SummarySolutionGroupDO getByKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return (SummarySolutionGroupDO)super.getByKey((Object)key, implClazz);
    }

    @Override
    public SummarySolutionGroupDO getByGroupAndTitle(String groupKey, String title) {
        if (StringUtils.hasLength(groupKey)) {
            return (SummarySolutionGroupDO)super.getBy(String.format("%s=? and %s=?", "SG_PARENT", "SG_TITLE"), (Object[])new String[]{groupKey, title}, implClazz);
        }
        return (SummarySolutionGroupDO)super.getBy(String.format("%s is null and %s=?", "SG_PARENT", "SG_TITLE"), (Object[])new String[]{title}, implClazz);
    }

    @Override
    public List<SummarySolutionGroupDO> listByGroup(String groupKey) {
        if (StringUtils.hasLength(groupKey)) {
            return super.list(new String[]{"SG_PARENT"}, (Object[])new String[]{groupKey}, implClazz);
        }
        return super.list(String.format("%s is null", "SG_PARENT"), null, implClazz);
    }

    @Override
    public List<SummarySolutionGroupDO> fuzzyQuery(String keywords) {
        String sql = "select * from %s where %s like ?";
        sql = String.format(sql, this.tablename, "SG_TITLE");
        return this.jdbcTemplate.query(sql, ps -> ps.setString(1, "%" + keywords + "%"), this.getRowMapper(implClazz));
    }
}

