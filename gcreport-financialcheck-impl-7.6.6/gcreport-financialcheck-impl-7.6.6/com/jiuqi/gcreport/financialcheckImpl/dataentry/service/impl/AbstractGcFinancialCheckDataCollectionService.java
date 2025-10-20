/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.context.DataCollectionContext;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.FcIncrementCollectParam;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.impl.FcIncrementCollectServiceImpl;
import com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGcFinancialCheckDataCollectionService {
    @Autowired
    DimensionService dimensionService;
    @Autowired
    private FcIncrementCollectServiceImpl fcIncrementCollectService;

    public void dataCollection(String taskName, Integer beginBatchId, Integer endBatchId, String unit, String dataTime, StringBuilder log) {
        DataCollectionContext context = DataCollectionContext.newInstance();
        FinancialCheckConfigVO checkConfig = FinancialCheckConfigUtils.getCheckConfig();
        if (Objects.isNull(checkConfig)) {
            throw new BusinessRuntimeException("\u5bf9\u8d26\u914d\u7f6e\u4e2d\u5fc3\u672a\u8fdb\u884c\u914d\u7f6e\uff0c\u8bf7\u5148\u8fdb\u884c\u914d\u7f6e");
        }
        List orgRange = checkConfig.getOrgRange();
        if (CollectionUtils.isEmpty((Collection)orgRange)) {
            log.append("\u5355\u4f4d\u3010").append(unit).append("\u3011\u4e0d\u5728\u5bf9\u8d26\u4e2d\u5fc3\u5355\u4f4d\u8303\u56f4\u5185,\u4e0d\u8fdb\u884c\u53d6\u6570").append("\n");
            return;
        }
        Set allOrgRange = OrgUtils.getAllLevelsChildrenAndSelf((List)orgRange, (String)dataTime, (GcAuthorityType)GcAuthorityType.NONE, (String)checkConfig.getOrgType());
        if (!allOrgRange.contains(unit)) {
            log.append("\u5355\u4f4d\u3010").append(unit).append("\u3011\u4e0d\u5728\u5bf9\u8d26\u4e2d\u5fc3\u5355\u4f4d\u8303\u56f4\u5185,\u4e0d\u8fdb\u884c\u53d6\u6570").append("\n");
            return;
        }
        context.setCheckMode(FinancialCheckConfigUtils.getCheckWay().getCode());
        context.setOrgType(FinancialCheckConfigUtils.getCheckOrgType());
        context.setDataTime(dataTime);
        context.setUnitId(unit);
        context.setTaskName(taskName);
        List subjectRange = checkConfig.getSubjectRange();
        Set subjectSet = BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)subjectRange);
        log.append("\u5f00\u59cb\u67e5\u8be2\u589e\u91cf\u6570\u636e,\u67e5\u8be2\u53c2\u6570\u4e3a\uff1a\u6279\u6b21\u53f7\uff08").append(beginBatchId).append("-").append(endBatchId).append("\uff09\n");
        List<GcRelatedItemEO> incrementItems = this.listIncrementalData(beginBatchId, endBatchId, subjectSet, context.getUnitId(), context.getDataTime());
        log.append("\u67e5\u8be2\u5230\u589e\u91cf\u6570\u636e").append(incrementItems.size()).append("\n");
        this.disposeItems(context, incrementItems, log);
        log.append("\u589e\u91cf\u6570\u636e\u5165\u5e93\u6210\u529f");
    }

    public abstract List<GcRelatedItemEO> listIncrementalData(Integer var1, Integer var2, Set<String> var3, String var4, String var5);

    public List<GcRelatedItemEO> listNeedDeleteData(String unitId, String dataTime) {
        return Collections.emptyList();
    }

    private void disposeItems(DataCollectionContext context, List<GcRelatedItemEO> incrementItems, StringBuilder log) {
        List<GcRelatedItemEO> sourceNotExistData = this.listNeedDeleteData(context.getUnitId(), context.getDataTime());
        FcIncrementCollectParam param = FcIncrementCollectParam.newInstance(incrementItems, sourceNotExistData);
        this.fcIncrementCollectService.collect(param);
        log.append((CharSequence)param.getLog());
    }
}

