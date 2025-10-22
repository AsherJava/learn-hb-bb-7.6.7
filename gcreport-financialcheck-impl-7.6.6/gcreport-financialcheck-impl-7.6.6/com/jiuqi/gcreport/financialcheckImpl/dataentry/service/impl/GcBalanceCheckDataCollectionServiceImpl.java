/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl.AbstractGcFinancialCheckDataCollectionService;
import com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GcBalanceCheckDataCollectionServiceImpl
extends AbstractGcFinancialCheckDataCollectionService {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> listIncrementalData(Integer beginBatchId, Integer endBatchId, Set<String> subjectSet, String unit, String dataTime) {
        String sql = " select * from GC_FINCUBES_DIM_Y item where   MDCODE = ? and OPPUNITCODE != '#' and datatime = ? and sn > ? and sn <= ? and MD_AUDITTRAIL in ('111', '121', '122', '123', '124') ";
        TempTableCondition subjectCondition = ReltxSqlUtils.getConditionOfMulStr(subjectSet, (String)"item.subjectcode");
        sql = sql + "and " + subjectCondition.getCondition();
        try {
            List maps = OuterDataSourceUtils.getJdbcTemplate().queryForList(sql, new Object[]{unit, dataTime, beginBatchId, endBatchId});
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

    public static GcRelatedItemEO convert2RelatedItem(Map<String, Object> map) {
        GcRelatedItemEO item = new GcRelatedItemEO();
        item.setSrcItemId((String)map.get("ID"));
        String dataTime = (String)map.get("DATATIME");
        item.setAcctYear(ConverterUtils.getAsInteger((Object)dataTime.substring(0, 4)));
        item.setAcctPeriod(ConverterUtils.getAsInteger((Object)dataTime.substring(7)));
        item.setUnitId((String)map.get("UNITCODE"));
        item.setOppUnitId((String)map.get("OPPUNITCODE"));
        item.setSubjectCode((String)map.get("SUBJECTCODE"));
        item.setOriginalCurr((String)map.get("ORGNCURRENCY"));
        item.setCurrency((String)map.get("MD_CURRENCY"));
        if (OrientEnum.D.getValue().equals(ConverterUtils.getAsInteger((Object)map.get("SUBJECTORIENT")))) {
            item.setDebitOrig(Double.valueOf((Double)map.get("ORGNCF")));
            item.setCreditOrig(Double.valueOf(0.0));
            item.setDebit(Double.valueOf((Double)map.get("CF")));
            item.setCredit(Double.valueOf(0.0));
        } else {
            item.setDebitOrig(Double.valueOf(0.0));
            item.setCreditOrig(Double.valueOf((Double)map.get("ORGNCF")));
            item.setDebit(Double.valueOf(0.0));
            item.setCredit(Double.valueOf((Double)map.get("CF")));
        }
        item.setInputWay(VchrSrcWayEnum.DATACOLLECTION_DIM.name());
        List dimensionS = ((DimensionService)SpringBeanUtils.getBean(DimensionService.class)).findDimFieldsByTableName("GC_RELATED_ITEM");
        if (!CollectionUtils.isEmpty(dimensionS)) {
            List dimensionCodes = dimensionS.stream().map(DimensionEO::getCode).collect(Collectors.toList());
            for (String dimensionCode : dimensionCodes) {
                item.addFieldValue(dimensionCode, map.get(dimensionCode.toUpperCase()));
            }
        }
        return item;
    }
}

