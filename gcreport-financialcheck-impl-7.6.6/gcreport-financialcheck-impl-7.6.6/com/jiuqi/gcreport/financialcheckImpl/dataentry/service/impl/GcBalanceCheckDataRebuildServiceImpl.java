/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.dao.FinancialCheckDataCollectionDao;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl.AbstractGcFinancialCheckDataCollectionService;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl.GcBalanceCheckDataCollectionServiceImpl;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GcBalanceCheckDataRebuildServiceImpl
extends AbstractGcFinancialCheckDataCollectionService {
    @Autowired
    DimensionService dimensionService;
    @Autowired
    FinancialCheckDataCollectionDao financialCheckDataCollectionDao;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> listIncrementalData(Integer beginBatchId, Integer endBatchId, Set<String> subjectSet, String unit, String dataTime) {
        String sql = " select * from GC_FINCUBES_DIM_Y item where   MDCODE = ? and OPPUNITCODE != '#' and datatime = ?  and MD_AUDITTRAIL in ('111', '121', '122', '123', '124') ";
        TempTableCondition subjectCondition = ReltxSqlUtils.getConditionOfMulStr(subjectSet, (String)"item.subjectcode");
        sql = sql + "and " + subjectCondition.getCondition();
        try {
            List maps = OuterDataSourceUtils.getJdbcTemplate().queryForList(sql, new Object[]{unit, dataTime});
            if (CollectionUtils.isEmpty(maps)) {
                ArrayList<GcRelatedItemEO> arrayList = new ArrayList<GcRelatedItemEO>();
                return arrayList;
            }
            List<GcRelatedItemEO> list = maps.stream().map(GcBalanceCheckDataCollectionServiceImpl::convert2RelatedItem).collect(Collectors.toList());
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId((String)subjectCondition.getTempGroupId());
        }
    }

    @Override
    public List<GcRelatedItemEO> listNeedDeleteData(String unitId, String dataTime) {
        return this.financialCheckDataCollectionDao.listSourceDIMNotExistItem(unitId, dataTime);
    }
}

