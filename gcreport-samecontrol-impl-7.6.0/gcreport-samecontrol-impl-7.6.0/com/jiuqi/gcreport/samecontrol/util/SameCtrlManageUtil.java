/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.unionrule.base.RuleManagerFactory
 *  com.jiuqi.gcreport.unionrule.base.RuleType
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.samecontrol.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlExtractManageCond;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SameCtrlManageUtil {
    public static void initAndCheckParam(SameCtrlChgEnvContextImpl sameCtrlChgEnvContext, SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        if (sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getDisposalDate() == null) {
            throw new BusinessRuntimeException("\u5904\u7f6e\u65e5\u671f\u4e3a\u7a7a");
        }
        if (sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangeDate() == null) {
            throw new BusinessRuntimeException("\u53d8\u52a8\u65e5\u671f\u4e3a\u7a7a");
        }
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setVirtual(true);
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setChangedOrgType(ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode());
        YearPeriodObject yearPeriodObject = new YearPeriodObject(sameCtrlExtractDataVO.getSchemeId(), sameCtrlExtractDataVO.getPeriodStr());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)sameCtrlExtractDataVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yearPeriodObject);
        GcOrgCacheVO changedOrg = orgCenterService.getOrgByCode(sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode());
        if (changedOrg == null) {
            throw new RuntimeException("\u53d8\u52a8\u5355\u4f4d\uff1a" + sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode() + " \u65f6\u671f\uff1a" + sameCtrlExtractDataVO.getPeriodStr() + " \u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + sameCtrlExtractDataVO.getOrgType() + "\u4e0d\u5b58\u5728");
        }
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setChangedName(changedOrg.getTitle());
        GcOrgCacheVO virtualOrg = orgCenterService.getOrgByCode(sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getVirtualCode());
        if (virtualOrg == null) {
            throw new RuntimeException("\u865a\u62df\u5355\u4f4d\uff1a" + sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getVirtualCode() + " \u65f6\u671f\uff1a" + sameCtrlExtractDataVO.getPeriodStr() + " \u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + sameCtrlExtractDataVO.getOrgType() + "\u4e0d\u5b58\u5728");
        }
        String reportSystem = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdByTaskId(sameCtrlExtractDataVO.getTaskId(), sameCtrlExtractDataVO.getPeriodStr());
        if (StringUtils.isEmpty((String)reportSystem)) {
            throw new BusinessRuntimeException("\u4efb\u52a1\u4e0e\u5408\u5e76\u4f53\u7cfb\u5173\u8054\u5173\u7cfb\u4e22\u5931\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setSystemId(reportSystem);
    }

    public static PeriodWrapper getCurrentPeriod(int periodType, Calendar date) {
        if (date == null) {
            date = Calendar.getInstance();
        }
        int year = date.get(1);
        int month = date.get(2);
        int week = date.get(3);
        int day = date.get(6);
        int dayOfMonth = date.get(5);
        date.get(7);
        int acctYear = year;
        int acctPriod = 1;
        if (1 == periodType) {
            acctPriod = 1;
        } else if (2 == periodType) {
            acctPriod = (month + 1) / 7 + 1;
        } else if (3 == periodType) {
            acctPriod = (month + 1) / 4 + 1;
        } else if (4 == periodType) {
            acctPriod = month + 1;
        } else if (5 == periodType) {
            acctPriod = month * 3 + (dayOfMonth - 1) / 10 + 1;
        } else if (6 == periodType) {
            acctPriod = day;
        } else if (7 == periodType) {
            acctPriod = week;
        } else if (8 == periodType) {
            // empty if block
        }
        return new PeriodWrapper(acctYear, periodType, acctPriod);
    }

    public static void checkSameCtrlChgOrgAuthority(String schemeId, String periodStr, String orgType, String virtualCode) {
        YearPeriodObject yp = new YearPeriodObject(schemeId, periodStr);
        GcOrgCenterService writeOrgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.WRITE, (YearPeriodObject)yp);
        GcOrgCacheVO writeOrgVO = writeOrgTool.getOrgByCode(virtualCode);
        if (writeOrgVO == null) {
            GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO noAuthOrgVO = noAuthzOrgTool.getOrgByCode(virtualCode);
            throw new BusinessRuntimeException("\u5f53\u524d\u865a\u62df\u5355\u4f4d\u3010" + (noAuthOrgVO != null ? noAuthOrgVO.getTitle() : virtualCode) + "\u3011\u6ca1\u6709\u5199\u6743\u9650\u3002");
        }
    }

    public static List<String> getInvestRuleIdListByType(String schemeId, String periodStr, boolean isInvest) {
        RuleManagerFactory ruleManagerFactory = (RuleManagerFactory)SpringContextUtils.getBean(RuleManagerFactory.class);
        List allRuleType = ruleManagerFactory.getAllRuleType();
        List matchRuleTypes = allRuleType.stream().map(RuleType::code).filter(item -> {
            if (item.equals(RuleTypeEnum.DIRECT_INVESTMENT.getCode()) || item.equals(RuleTypeEnum.INDIRECT_INVESTMENT.getCode()) || item.equals(RuleTypeEnum.DIRECT_INVESTMENT_SEGMENT.getCode()) || item.equals(RuleTypeEnum.INDIRECT_INVESTMENT_SEGMENT.getCode())) {
                return isInvest;
            }
            return !isInvest;
        }).collect(Collectors.toList());
        UnionRuleService unionRuleService = (UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class);
        List rules = unionRuleService.selectRuleListBySchemeIdAndRuleTypes(schemeId, periodStr, matchRuleTypes);
        ArrayList<String> ruleList = new ArrayList<String>(rules.size());
        rules.forEach(item -> ruleList.add(item.getId()));
        return ruleList;
    }

    public static List<String> getRuleIdListByType(String schemeId, String periodStr, List<String> ruleTypes) {
        RuleManagerFactory ruleManagerFactory = (RuleManagerFactory)SpringContextUtils.getBean(RuleManagerFactory.class);
        List allRuleType = ruleManagerFactory.getAllRuleType();
        List matchRuleTypes = allRuleType.stream().map(RuleType::code).filter(ruleTypes::contains).collect(Collectors.toList());
        UnionRuleService unionRuleService = (UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class);
        List rules = unionRuleService.selectRuleListBySchemeIdAndRuleTypes(schemeId, periodStr, matchRuleTypes);
        ArrayList<String> ruleList = new ArrayList<String>(rules.size());
        rules.forEach(item -> ruleList.add(item.getId()));
        return ruleList;
    }

    public static List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> investUnit, Set<String> investedUnit, int acctYear, int period) {
        String sql = " select %1$s from GC_INVESTBILL e  where e.acctYear=? and e.period = ? and %2$s";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"e");
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(investUnit, (String)"e.unitCode") + " and " + SqlUtils.getConditionOfIdsUseOr(investedUnit, (String)"e.investedUnit");
        String formatSql = String.format(sql, columns, whereCondition);
        return SameCtrlManageUtil.queryBySql(formatSql, acctYear, period);
    }

    public static List<DefaultTableEntity> getItemByInvestMasterId(String id) {
        String sql = " select %1$s from GC_INVESTBILLITEM e  where e.MASTERID = ?";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILLITEM", (String)"e");
        String formatSql = String.format(sql, columns);
        return SameCtrlManageUtil.queryBySql(formatSql, id);
    }

    public static List<DefaultTableEntity> getFvchFixedByInvestMasterSrcId(String srcId) {
        String sql = " select %1$s from GC_FVCHBILL e join GC_FVCH_FIXEDITEM f on e.id = f.masterid where e.SRCID = ? ";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_FVCH_FIXEDITEM", (String)"f");
        String formatSql = String.format(sql, columns);
        return SameCtrlManageUtil.queryBySql(formatSql, srcId);
    }

    public static List<DefaultTableEntity> getFvchOtherByInvestMasterSrcId(String srcId) {
        String sql = " select %1$s from GC_FVCHBILL e join GC_FVCH_OTHERITEM f on e.id = f.masterid where e.SRCID = ? ";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_FVCH_OTHERITEM", (String)"f");
        String formatSql = String.format(sql, columns);
        return SameCtrlManageUtil.queryBySql(formatSql, srcId);
    }

    public static List<DefaultTableEntity> queryBySql(String sql, Object ... params) {
        ArrayList<DefaultTableEntity> result = new ArrayList<DefaultTableEntity>();
        List fields = EntNativeSqlDefaultDao.getInstance().selectMap(sql, params);
        for (Map field : fields) {
            DefaultTableEntity entity = new DefaultTableEntity();
            entity.setId(String.valueOf(field.get("ID")));
            entity.resetFields(field);
            result.add(entity);
        }
        return result;
    }

    public static String getOrgTypeId(SameCtrlChgEnvContext sameCtrlChgEnvContext, String orgCode) {
        SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        SameCtrlExtractManageCond sameCtrlExtractManageCond = sameCtrlChgEnvContext.getSameCtrlExtractManageCond();
        GcOrgCacheVO changedUnit = sameCtrlExtractManageCond.getGcOrgCenterService().getOrgByCode(orgCode);
        return changedUnit != null && !StringUtils.isEmpty((String)changedUnit.getOrgTypeId()) ? changedUnit.getOrgTypeId() : cond.getChangedUnitCode();
    }
}

