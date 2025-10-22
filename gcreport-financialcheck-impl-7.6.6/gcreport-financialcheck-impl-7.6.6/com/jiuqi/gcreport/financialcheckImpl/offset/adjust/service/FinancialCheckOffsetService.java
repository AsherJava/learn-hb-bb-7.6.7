/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto.RelationToMergeArgDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FinancialCheckOffsetService {
    public Pagination<Map<String, Object>> listFinancialCheckOffset(QueryParamsVO var1, Boolean var2);

    public void batchSaveOffsetData(GcOffSetVchrDTO var1);

    public void syncCheckData2OffsetData(List<GcRelatedItemEO> var1, String var2);

    public List<GcOffsetRelatedItemEO> batchMatchRule(List<GcOffsetRelatedItemEO> var1, String var2);

    public void deleteOffsetItemAndRel(String var1, Boolean var2, GcCalcArgmentsDTO var3);

    public List<GcOffsetRelatedItemEO> listByRelatedId(Collection<String> var1);

    public List<GcOffsetRelatedItemEO> listById(Collection<String> var1);

    public void batchDeleteByOffsetGroupIdAndLock(Collection<String> var1, Collection<String> var2, GcTaskBaseArguments var3);

    public void batchDeleteByOffsetGroupId(Collection<String> var1, GcTaskBaseArguments var2);

    public void saveOffsetData(FinancialCheckRuleExecutorImpl.OffsetResult var1, boolean var2);

    public List<GcOffsetRelatedItemEO> listByOffsetCondition(RelationToMergeArgDTO var1);
}

