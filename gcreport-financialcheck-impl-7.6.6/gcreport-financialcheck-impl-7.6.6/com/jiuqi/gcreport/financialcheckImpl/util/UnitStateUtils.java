/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO
 *  com.jiuqi.gc.financial.status.enums.FinancialStatusEnum
 *  com.jiuqi.gc.financial.status.service.FinancialStatusQueryService
 *  com.jiuqi.gc.financial.status.vo.FinancialStatusQueryParam
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.financialcheckImpl.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO;
import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import com.jiuqi.gc.financial.status.service.FinancialStatusQueryService;
import com.jiuqi.gc.financial.status.vo.FinancialStatusQueryParam;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UnitStateUtils {
    public static FinancialStatusEnum getState(String mergeUnit, int year, int period) {
        String withMonth = "000" + period;
        String subMonth = withMonth.substring(withMonth.length() - 4);
        String dataTime = year + "Y" + subMonth;
        FinancialStatusQueryParam financialStatusQueryParam = new FinancialStatusQueryParam();
        financialStatusQueryParam.setDataTime(dataTime);
        financialStatusQueryParam.setModuleCode("RELATED_TRANSACTION");
        HashSet<String> unitSet = new HashSet<String>();
        unitSet.add(mergeUnit);
        financialStatusQueryParam.setUnitCodeSet(unitSet);
        List financialUnitStatusDTOS = ((FinancialStatusQueryService)SpringContextUtils.getBean(FinancialStatusQueryService.class)).listFinancialUnitStatusData(financialStatusQueryParam);
        if (CollectionUtils.isEmpty((Collection)financialUnitStatusDTOS)) {
            throw new BusinessRuntimeException("\u5408\u5e76\u5355\u4f4d " + mergeUnit + "\u7684\u5f00\u5173\u8d26\u72b6\u6001\u67e5\u8be2\u4e0d\u5230");
        }
        return ((FinancialUnitStatusDTO)financialUnitStatusDTOS.get(0)).getStatus();
    }

    public static GcOrgCacheVO getMergeUnit(GcOrgCenterService instance, String unit, String oppUnit) {
        GcOrgCacheVO unitOrg = instance.getOrgByCode(unit);
        GcOrgCacheVO oppUnitOrg = instance.getOrgByCode(oppUnit);
        return instance.getCommonUnit(unitOrg, oppUnitOrg);
    }

    public static FinancialStatusEnum getState(String unitId, String oppUnitId, int year, int period) {
        YearPeriodDO periodDO = YearPeriodUtil.transform(null, (int)year, (int)4, (int)PeriodUtils.standardPeriod((int)period));
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)periodDO);
        GcOrgCacheVO mergeUnit = UnitStateUtils.getMergeUnit(instance, unitId, oppUnitId);
        if (Objects.isNull(mergeUnit)) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d%1$s\u4e0e%2$s\u5728%3$s\u5e74%4$s\u6708\u7684\u5408\u5e76\u5355\u4f4d\u67e5\u8be2\u4e0d\u5230", unitId, oppUnitId, year, period));
        }
        return UnitStateUtils.getState(mergeUnit.getCode(), year, period);
    }

    public static void checkUnitStateIsOpen(String unitId, String oppUnitId, int year, int period) {
        YearPeriodDO periodDO = YearPeriodUtil.transform(null, (int)year, (int)4, (int)PeriodUtils.standardPeriod((int)period));
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)periodDO);
        GcOrgCacheVO mergeUnit = UnitStateUtils.getMergeUnit(instance, unitId, oppUnitId);
        if (Objects.isNull(mergeUnit)) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d%1$s\u4e0e%2$s\u5728%3$s\u5e74%4$s\u6708\u7684\u5408\u5e76\u5355\u4f4d\u67e5\u8be2\u4e0d\u5230", unitId, oppUnitId, year, period));
        }
        FinancialStatusEnum state = UnitStateUtils.getState(mergeUnit.getCode(), year, period);
        if (FinancialStatusEnum.CLOSE.equals((Object)state)) {
            throw new BusinessRuntimeException(String.format("\u5408\u5e76\u5355\u4f4d%1$s\u5728%2$s\u5e74%3$s\u6708\u5df2\u5173\u8d26\uff0c\u65e0\u6cd5\u6267\u884c\u6b64\u64cd\u4f5c\u3002", mergeUnit.getCode(), year, period));
        }
    }

    public static int getUnitOpenAccountPeriod(String unitId, String oppUnitId, int year, int period, int maxPeriod) {
        if (period > maxPeriod) {
            throw new BusinessRuntimeException(String.format("\u5bf9\u8d26\u671f\u95f4%1$s\u9ad8\u4e8e\u53ef\u5bf9\u8d26\u671f\u95f4%2$s", period, maxPeriod));
        }
        while (period <= maxPeriod) {
            FinancialStatusEnum state = UnitStateUtils.getState(unitId, oppUnitId, year, period);
            if (!FinancialStatusEnum.CLOSE.equals((Object)state)) {
                return period;
            }
            ++period;
        }
        return -1;
    }

    public static void checkReconciliationPeriodStatus(List<GcRelatedItemEO> vchrItems) {
        Map<String, List<GcRelatedItemEO>> groupDatas = vchrItems.stream().collect(Collectors.groupingBy(item -> {
            if (item.getUnitId().compareTo(item.getOppUnitId()) <= 0) {
                return item.getUnitId() + "|" + item.getOppUnitId() + "|" + item.getCheckYear() + "|" + item.getCheckPeriod();
            }
            return item.getOppUnitId() + "|" + item.getUnitId() + "|" + item.getCheckYear() + "|" + item.getCheckPeriod();
        }));
        groupDatas.forEach((k, v) -> {
            GcRelatedItemEO vchrItem = (GcRelatedItemEO)v.get(0);
            String unitId = vchrItem.getUnitId();
            Integer checkYear = vchrItem.getCheckYear();
            Integer checkPeriod = vchrItem.getCheckPeriod();
            String oppUnitId = vchrItem.getOppUnitId();
            YearPeriodDO periodDO = YearPeriodUtil.transform(null, (int)checkYear, (int)4, (int)PeriodUtils.standardPeriod((int)checkPeriod));
            GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)periodDO);
            GcOrgCacheVO mergeUnit = UnitStateUtils.getMergeUnit(instance, unitId, oppUnitId);
            if (Objects.isNull(mergeUnit)) {
                throw new BusinessRuntimeException(String.format("\u5355\u4f4d%1$s\u4e0e%2$s\u5728%3$s\u5e74%4$s\u6708\u7684\u5408\u5e76\u5355\u4f4d\u67e5\u8be2\u4e0d\u5230", unitId, oppUnitId, checkYear, checkPeriod));
            }
            FinancialStatusEnum state = UnitStateUtils.getState(mergeUnit.getCode(), checkYear, checkPeriod);
            if (FinancialStatusEnum.CLOSE.equals((Object)state)) {
                throw new BusinessRuntimeException("\u5408\u5e76\u5355\u4f4d\uff1a" + mergeUnit.getTitle() + "\u5df2\u5173\u8d26\uff0c\u65e0\u6cd5\u6267\u884c\u6b64\u64cd\u4f5c\u3002");
            }
        });
    }
}

