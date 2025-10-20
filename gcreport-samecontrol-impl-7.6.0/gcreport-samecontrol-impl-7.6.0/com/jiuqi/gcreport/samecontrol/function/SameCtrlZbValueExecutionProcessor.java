/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.samecontrol.function;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.function.SameCtrlFormulaExecutionTool;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SameCtrlZbValueExecutionProcessor {
    private static final transient Logger logger = LoggerFactory.getLogger(SameCtrlZbValueExecutionProcessor.class);

    public static double calResult(QueryContext queryContext, String formula) {
        double result = 0.0;
        try {
            DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
            GcOrgCacheVO org = SameCtrlZbValueExecutionProcessor.getGcOrgCache(dimensionValueSet);
            if (GcOrgKindEnum.DIFFERENCE == org.getOrgKind()) {
                return SameCtrlZbValueExecutionProcessor.evaluateSameCtrlChgOrg(queryContext, formula, org);
            }
        }
        catch (Exception e) {
            logger.error(String.format("\u6267\u884c\u516c\u5f0f%s\u51fa\u73b0\u5f02\u5e38:%s", formula, e.getMessage()), e);
        }
        return result;
    }

    public static GcOrgCacheVO getGcOrgCache(DimensionValueSet dimensionValueSet) {
        String dataTime = (String)dimensionValueSet.getValue("DATATIME");
        String orgCode = (String)dimensionValueSet.getValue("MD_ORG");
        String orgType = (String)dimensionValueSet.getValue("MD_GCORGTYPE");
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return gcOrgCenterService.getOrgByCode(orgCode);
    }

    public static double evaluateSameCtrlChgOrg(QueryContext queryContext, String formula, GcOrgCacheVO org) {
        SameCtrlChgOrgService sameCtrlChgOrgService = (SameCtrlChgOrgService)SpringContextUtils.getBean(SameCtrlChgOrgService.class);
        double result = 0.0;
        try {
            List<String> sameParentCodeList;
            List<SameCtrlChgOrgEO> sameCtrlSameParentList;
            DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
            String dataTime = (String)dimensionValueSet.getValue("DATATIME");
            List<SameCtrlChgOrgEO> sameCtrlChangeCodeList = sameCtrlChgOrgService.listCurrYearChgOrgsByAcquisitionOrg(org.getParentId(), dataTime);
            if (!CollectionUtils.isEmpty(sameCtrlChangeCodeList)) {
                List<String> changeCodeList = sameCtrlChangeCodeList.stream().map(SameCtrlChgOrgEO::getChangedCode).collect(Collectors.toList());
                result += SameCtrlFormulaExecutionTool.evaluateSameCtrlZbValue(queryContext, changeCodeList, dimensionValueSet, formula);
            }
            if (!CollectionUtils.isEmpty(sameCtrlSameParentList = sameCtrlChgOrgService.listCurrYearChgOrgsBySameParentOrg(org.getParentId(), dataTime)) && !CollectionUtils.isEmpty(sameParentCodeList = sameCtrlSameParentList.stream().filter(sameCtrlChgOrg -> org.getParentId().equals(sameCtrlChgOrg.getSameParentCode())).map(SameCtrlChgOrgEO::getVirtualCode).collect(Collectors.toList()))) {
                result -= SameCtrlFormulaExecutionTool.evaluateSameCtrlZbValue(queryContext, sameParentCodeList, dimensionValueSet, formula);
            }
        }
        catch (Exception e) {
            logger.error(String.format("\u6267\u884c\u516c\u5f0f%s\u51fa\u73b0\u5f02\u5e38:%s", formula, e.getMessage()), e);
        }
        return result;
    }
}

