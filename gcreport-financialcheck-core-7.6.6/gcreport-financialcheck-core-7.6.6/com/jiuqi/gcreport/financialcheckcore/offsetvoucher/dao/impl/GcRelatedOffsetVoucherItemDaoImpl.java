/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 */
package com.jiuqi.gcreport.financialcheckcore.offsetvoucher.dao.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.dao.GcRelatedOffsetVoucherItemDao;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class GcRelatedOffsetVoucherItemDaoImpl
extends AbstractEntDbSqlGenericDAO<GcRelatedOffsetVoucherItemEO>
implements GcRelatedOffsetVoucherItemDao {
    private static final String UPDATERELATEDOFFSETVOUCHERITEMINFOSQL = "  update GC_RELATEDOFFSETVCHRITEM item  set OFFSETSUBJECT=?, DEBITOFFSET=?, CREDITOFFSET=?, OFFSETMETHOD=?, REMARK=?   where item.id = ? \n";
    private static final String QUERYOFFSETVCHRITEMBYIDSSQL = "  select %s \n    from GC_RELATEDOFFSETVCHRITEM  item \n   where %s \n";

    public GcRelatedOffsetVoucherItemDaoImpl() {
        super(GcRelatedOffsetVoucherItemEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELATEDOFFSETVCHRITEM");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public void deleteByCheckIdsAndOffsetPeriod(Collection<String> groupIds, Integer offsetPeriod) {
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(groupIds, "checkId");
        try {
            String sql = "  delete from GC_RELATEDOFFSETVCHRITEM   where " + tempTableCondition.getCondition() + " and OFFSETPERIOD >= ? \n \n";
            this.execute(sql, new Object[]{offsetPeriod});
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public List<GcRelatedOffsetVoucherItemEO> queryEntityByCheckGroupId(String groupId) {
        String sqlTemplate = "  select * from GC_RELATEDOFFSETVCHRITEM  vb  where checkId = ? \n";
        return this.selectEntity("  select * from GC_RELATEDOFFSETVCHRITEM  vb  where checkId = ? \n", new Object[]{groupId});
    }

    @Override
    public void updateRelatedOffsetVoucherItemInfo(List<GcRelatedOffsetVoucherItemEO> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        List param = items.stream().map(v -> Arrays.asList(v.getOffsetSubject(), v.getDebitOffset(), v.getCreditOffset(), v.getOffsetMethod(), v.getRemark(), v.getId())).collect(Collectors.toList());
        this.executeBatch(UPDATERELATEDOFFSETVOUCHERITEMINFOSQL, param);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedOffsetVoucherItemEO> queryByIds(Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(ids, "item.id");
        try {
            String sql = String.format(QUERYOFFSETVCHRITEMBYIDSSQL, SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATEDOFFSETVCHRITEM", (String)"item"), tempTableCondition.getCondition());
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }
}

