/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum
 *  com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryLevelEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.check.dao.helper;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.helper.FcAllDataSqlBuilderHelper;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.helper.FcCurrentDataSqlBuilderHelper;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.helper.FcCustomDataSqlBuilderHelper;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.helper.FcDownDataSqlBuilderHelper;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.helper.FcUpDataSqlBuilderHelper;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum;
import com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryLevelEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public abstract class FcBaseSqlBuilderHelper {
    public static FcBaseSqlBuilderHelper getNewInstance(CheckQueryLevelEnum checkQueryLevelEnum) {
        switch (Objects.requireNonNull(checkQueryLevelEnum)) {
            case UP: {
                return new FcUpDataSqlBuilderHelper();
            }
            case CURRENT: {
                return new FcCurrentDataSqlBuilderHelper();
            }
            case DOWN: {
                return new FcDownDataSqlBuilderHelper();
            }
            case CUSTOM: {
                return new FcCustomDataSqlBuilderHelper();
            }
            case ALL: {
                return new FcAllDataSqlBuilderHelper();
            }
        }
        throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u67e5\u8be2\u5c42\u7ea7");
    }

    public abstract String buildCheckedConditionSql(FinancialCheckQueryConditionDTO var1, String var2, List<Object> var3, Set<String> var4);

    public abstract String buildUncheckedGroupByUnitSql(FinancialCheckQueryConditionDTO var1, String var2, Map<String, String> var3, List<Object> var4, Set<String> var5);

    public abstract String buildUncheckedGroupByOppUnitSql(FinancialCheckQueryConditionDTO var1, String var2, Map<String, String> var3, List<Object> var4, Set<String> var5);

    public abstract String buildUncheckedSchemeSql(FinancialCheckQueryConditionDTO var1, String var2, Map<String, String> var3, List<Object> var4, Set<String> var5);

    public abstract String buildQueryAmtSumSql(FinancialCheckQueryConditionDTO var1, String var2, List<Object> var3, Set<String> var4);

    protected void buildCommonConditionSql(FinancialCheckQueryConditionDTO condition, StringBuilder sql, List<Object> param, Set<String> tempGroupIds, CheckStateEnum checkState) {
        List subjectCodes;
        sql.append(" and item.ACCTYEAR = ? ");
        param.add(condition.getAcctYear());
        if (Boolean.TRUE.equals(condition.getPeriodTotalEnable()) && ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            sql.append(" and item.ACCTPERIOD <= ? ");
        } else {
            sql.append(" and item.ACCTPERIOD = ? ");
        }
        param.add(condition.getAcctPeriod());
        sql.append(" and CHKSTATE = '").append(checkState.name()).append("' ");
        if (!CollectionUtils.isEmpty(condition.getSchemeIds())) {
            TempTableCondition schemeTempCond = ReltxSqlUtils.getConditionOfMulStr((Collection)condition.getSchemeIds(), (String)"item.CHECKRULEID");
            sql.append(" and ").append(schemeTempCond.getCondition());
            if (!StringUtils.isEmpty((String)schemeTempCond.getTempGroupId())) {
                tempGroupIds.add(schemeTempCond.getTempGroupId());
            }
        }
        if (!CollectionUtils.isEmpty(subjectCodes = condition.getSubjectCodes())) {
            Set allChildrenContainSelfByCodes = BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)subjectCodes);
            TempTableCondition subjectTempCond = ReltxSqlUtils.getConditionOfMulStr((Collection)allChildrenContainSelfByCodes, (String)"item.SUBJECTCODE");
            sql.append(" and  ").append(subjectTempCond.getCondition());
            if (!StringUtils.isEmpty((String)subjectTempCond.getTempGroupId())) {
                tempGroupIds.add(subjectTempCond.getTempGroupId());
            }
        }
        if (!StringUtils.isEmpty((String)condition.getGcNumber())) {
            sql.append(" and ").append("item.GCNUMBER").append(" = ? ");
            param.add(condition.getGcNumber());
        }
        if (!StringUtils.isEmpty((String)condition.getVchrType())) {
            sql.append(" and ").append("item.VCHRTYPE").append(" = ? ");
            param.add(condition.getVchrType());
        }
        if (!StringUtils.isEmpty((String)condition.getVchrNum())) {
            sql.append(" and ").append("item.VCHRNUM").append(" = ? ");
            param.add(condition.getVchrNum());
        }
        if (!StringUtils.isEmpty((String)condition.getOrigCurrencyCode())) {
            sql.append(" and item.ORIGINALCURR = ? ");
            param.add(condition.getOrigCurrencyCode());
        }
        if (Objects.nonNull(condition.getDebitOrigMin())) {
            sql.append(" and ").append("item.DEBITORIG").append(" >= ? ");
            param.add(condition.getDebitOrigMin());
        }
        if (Objects.nonNull(condition.getDebitOrigMax())) {
            sql.append(" and ").append("item.DEBITORIG").append(" <= ? ");
            param.add(condition.getDebitOrigMax());
        }
        if (Objects.nonNull(condition.getCreditOrigMin())) {
            sql.append(" and ").append("item.CREDITORIG").append(" >= ? ");
            param.add(condition.getCreditOrigMin());
        }
        if (Objects.nonNull(condition.getCreditOrigMax())) {
            sql.append(" and ").append("item.CREDITORIG").append(" <= ? ");
            param.add(condition.getCreditOrigMax());
        }
        if (Objects.nonNull(condition.getDebitMin())) {
            sql.append(" and ").append("item.DEBIT").append(" >= ? ");
            param.add(condition.getDebitMin());
        }
        if (Objects.nonNull(condition.getDebitMax())) {
            sql.append(" and ").append("item.DEBIT").append(" <= ? ");
            param.add(condition.getDebitMax());
        }
        if (Objects.nonNull(condition.getCreditMin())) {
            sql.append(" and ").append("item.CREDIT").append(" >= ? ");
            param.add(condition.getCreditMin());
        }
        if (Objects.nonNull(condition.getCreditMax())) {
            sql.append(" and ").append("item.CREDIT").append(" <= ? ");
            param.add(condition.getCreditMax());
        }
        if (!StringUtils.isEmpty((String)condition.getDigest())) {
            sql.append(" and ").append("item.DIGEST").append(" = ? ");
            param.add(condition.getDigest());
        }
        if (!StringUtils.isEmpty((String)condition.getBillCode())) {
            sql.append(" and ").append("item.BILLCODE").append(" = ? ");
            param.add(condition.getBillCode());
        }
        if (!StringUtils.isEmpty((String)condition.getInputWay())) {
            if (condition.getInputWay().equals(VchrSrcWayEnum.BATCHINPUT.name())) {
                sql.append(" and ").append("item.INPUTWAY").append(" = ? ");
            } else {
                sql.append(" and ").append("item.INPUTWAY").append(" != ? ");
            }
            param.add(VchrSrcWayEnum.BATCHINPUT.name());
        }
        if (!StringUtils.isEmpty((String)condition.getCheckAttribute())) {
            sql.append(" and ").append("CHECKATTRIBUTE").append(" = ? ");
            param.add(condition.getCheckAttribute());
        }
        if (Objects.nonNull(condition.getDimensions()) && !condition.getDimensions().isEmpty()) {
            condition.getDimensions().forEach((code, value) -> {
                if (Objects.nonNull(value)) {
                    if (value instanceof Collection) {
                        if (!CollectionUtils.isEmpty((Collection)value)) {
                            TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr((Collection)((Collection)value), (String)("item." + code));
                            sql.append(" and ").append(tempTableCondition.getCondition());
                            if (!StringUtils.isEmpty((String)tempTableCondition.getTempGroupId())) {
                                tempGroupIds.add(tempTableCondition.getTempGroupId());
                            }
                        }
                    } else if (!StringUtils.isEmpty((String)((String)value))) {
                        sql.append(" and item.").append((String)code).append(" = ? ");
                        param.add(value);
                    }
                }
            });
        }
    }

    protected void buildOtherCheckedConditionSql(FinancialCheckQueryConditionDTO condition, StringBuilder sql, List<Object> param) {
        if (!StringUtils.isEmpty((String)condition.getCheckType())) {
            sql.append(" and CHECKTYPE = ? ");
            param.add(condition.getCheckType());
        }
        if (Objects.nonNull(condition.getCheckYear())) {
            sql.append(" and ").append("CHECKYEAR").append(" = ? ");
            param.add(condition.getCheckYear());
        }
        if (Objects.nonNull(condition.getCheckPeriod())) {
            if (Boolean.TRUE.equals(condition.getCheckPeriodTotalEnable()) && ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
                sql.append(" and ").append("CHECKPERIOD").append(" <= ? ");
            } else {
                sql.append(" and ").append("CHECKPERIOD").append(" = ? ");
            }
            param.add(condition.getCheckPeriod());
        }
    }

    protected Date buildBusinessDate(FinancialCheckQueryConditionDTO condition) {
        Calendar calendar = Calendar.getInstance();
        Integer acctPeriod = condition.getAcctPeriod();
        if (acctPeriod == 0) {
            acctPeriod = 1;
        } else if (acctPeriod == 13) {
            acctPeriod = 12;
        }
        calendar.set(1, condition.getAcctYear());
        calendar.set(2, acctPeriod - 1);
        calendar.set(5, 1);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        return calendar.getTime();
    }

    protected void buildBusinessRoleConditionSql(FinancialCheckQueryConditionDTO condition, StringBuilder sql, boolean isLocal) {
        if (Objects.nonNull(condition.getBusinessRole())) {
            BusinessRoleEnum enumByCode = BusinessRoleEnum.getEnumByCode((Integer)condition.getBusinessRole());
            if (BusinessRoleEnum.ASSET.equals((Object)enumByCode) && isLocal || BusinessRoleEnum.DEBT.equals((Object)enumByCode) && !isLocal) {
                sql.append(" and item.BUSINESSROLE = ").append(BusinessRoleEnum.ASSET.getCode());
            } else {
                sql.append(" and item.BUSINESSROLE = ").append(BusinessRoleEnum.DEBT.getCode());
            }
        }
    }

    protected void buildSortSql(StringBuilder sql, Map<String, String> sortField2WayMap, boolean firstOrder) {
        if (sortField2WayMap.isEmpty()) {
            return;
        }
        StringBuilder orderSql = new StringBuilder();
        sortField2WayMap.forEach((field, sortWay) -> orderSql.append(", item.").append((String)field).append(" ").append((String)sortWay));
        if (firstOrder) {
            sql.append(orderSql.substring(1, orderSql.length()));
        } else {
            sql.append((CharSequence)orderSql);
        }
    }

    protected void buildUncheckedOppConditionSql(FinancialCheckQueryConditionDTO condition, StringBuilder sql, List<Object> param, Set<String> tempGroupIds) {
        List subjectCodes;
        sql.append(" and ACCTYEAR = ? ");
        param.add(condition.getAcctYear());
        if (Boolean.TRUE.equals(condition.getPeriodTotalEnable()) && ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            sql.append(" and ACCTPERIOD <= ? ");
        } else {
            sql.append(" and ACCTPERIOD = ? ");
        }
        param.add(condition.getAcctPeriod());
        sql.append(" and CHKSTATE = '").append(CheckStateEnum.UNCHECKED.name()).append("' ");
        if (!CollectionUtils.isEmpty(condition.getSchemeIds())) {
            TempTableCondition schemeTempCond = ReltxSqlUtils.getConditionOfMulStr((Collection)condition.getSchemeIds(), (String)"CHECKRULEID");
            sql.append(" and ").append(schemeTempCond.getCondition());
            if (!StringUtils.isEmpty((String)schemeTempCond.getTempGroupId())) {
                tempGroupIds.add(schemeTempCond.getTempGroupId());
            }
        }
        if (!CollectionUtils.isEmpty(subjectCodes = condition.getOppSubjectCodes())) {
            Set allChildrenContainSelfByCodes = BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)subjectCodes);
            TempTableCondition subjectTempCond = ReltxSqlUtils.getConditionOfMulStr((Collection)allChildrenContainSelfByCodes, (String)"SUBJECTCODE");
            sql.append(" and ").append(subjectTempCond.getCondition());
            if (!StringUtils.isEmpty((String)subjectTempCond.getTempGroupId())) {
                tempGroupIds.add(subjectTempCond.getTempGroupId());
            }
        }
        if (!StringUtils.isEmpty((String)condition.getOppGcNumber())) {
            sql.append(" and ").append("GCNUMBER").append(" = ? ");
            param.add(condition.getGcNumber());
        }
        if (!StringUtils.isEmpty((String)condition.getOppVchrType())) {
            sql.append(" and ").append("VCHRTYPE").append(" = ? ");
            param.add(condition.getVchrType());
        }
        if (!StringUtils.isEmpty((String)condition.getOppVchrNum())) {
            sql.append(" and ").append("VCHRNUM").append(" = ? ");
            param.add(condition.getVchrNum());
        }
        if (Objects.nonNull(condition.getOppDebitOrigMin())) {
            sql.append(" and ").append("DEBITORIG").append(" >= ? ");
            param.add(condition.getDebitOrigMin());
        }
        if (Objects.nonNull(condition.getOppDebitOrigMax())) {
            sql.append(" and ").append("DEBITORIG").append(" <= ? ");
            param.add(condition.getDebitOrigMax());
        }
        if (Objects.nonNull(condition.getOppCreditOrigMin())) {
            sql.append(" and ").append("CREDITORIG").append(" >= ? ");
            param.add(condition.getCreditOrigMin());
        }
        if (Objects.nonNull(condition.getOppCreditOrigMax())) {
            sql.append(" and ").append("CREDITORIG").append(" <= ? ");
            param.add(condition.getCreditOrigMax());
        }
        if (!StringUtils.isEmpty((String)condition.getOppDigest())) {
            sql.append(" and ").append("DIGEST").append(" = ? ");
            param.add(condition.getDigest());
        }
        if (!StringUtils.isEmpty((String)condition.getOppInputWay())) {
            sql.append(" and ").append("INPUTWAY").append(" = ? ");
            param.add(condition.getOppInputWay());
        }
        if (!StringUtils.isEmpty((String)condition.getCheckAttribute())) {
            sql.append(" and ").append("CHECKATTRIBUTE").append(" = ? ");
            param.add(condition.getCheckAttribute());
        }
        if (Objects.nonNull(condition.getOppDimensions()) && !condition.getOppDimensions().isEmpty()) {
            condition.getOppDimensions().forEach((code, value) -> {
                if (Objects.nonNull(value)) {
                    if (value instanceof Collection) {
                        if (!CollectionUtils.isEmpty((Collection)value)) {
                            TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr((Collection)((Collection)value), (String)code);
                            sql.append(" and ").append(tempTableCondition.getCondition());
                            if (!StringUtils.isEmpty((String)tempTableCondition.getTempGroupId())) {
                                tempGroupIds.add(tempTableCondition.getTempGroupId());
                            }
                        }
                    } else if (!StringUtils.isEmpty((String)((String)value))) {
                        sql.append(" and ").append((String)code).append(" = ? ");
                        param.add(value);
                    }
                }
            });
        }
    }

    protected void buildUncheckedOppUnitSql(FinancialCheckQueryConditionDTO condition, String orgType, StringBuilder sql, Set<String> tempGroupIds) {
        Set oppChildrenAndSelf;
        YearPeriodObject yp = new YearPeriodObject(condition.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)condition.getAcctPeriod()));
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        Set localChildrenAndSelf = tool.listAllOrgByParentIdContainsSelf(condition.getUnitId()).stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet());
        String oppUnitId = condition.getOppUnitId();
        GcOrgCenterService noneService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(condition.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)condition.getAcctPeriod())));
        if (StringUtils.isEmpty((String)oppUnitId)) {
            List allUnits = noneService.listOrgBySearch(null);
            oppChildrenAndSelf = allUnits.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet());
        } else {
            oppChildrenAndSelf = noneService.listAllOrgByParentIdContainsSelf(oppUnitId).stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet());
        }
        if (localChildrenAndSelf.containsAll(oppChildrenAndSelf)) {
            localChildrenAndSelf.removeAll(oppChildrenAndSelf);
        }
        if (oppChildrenAndSelf.containsAll(localChildrenAndSelf)) {
            oppChildrenAndSelf.removeAll(localChildrenAndSelf);
        }
        if (CollectionUtils.isEmpty(localChildrenAndSelf) || CollectionUtils.isEmpty(oppChildrenAndSelf)) {
            sql.append(" and 1= 2 ");
            return;
        }
        TempTableCondition unitTempCond = ReltxSqlUtils.getConditionOfMulStr(localChildrenAndSelf, (String)"OPPUNITID");
        sql.append(" and  ").append(unitTempCond.getCondition());
        if (!StringUtils.isEmpty((String)unitTempCond.getTempGroupId())) {
            tempGroupIds.add(unitTempCond.getTempGroupId());
        }
        TempTableCondition oppUnitTempCond = ReltxSqlUtils.getConditionOfMulStr(oppChildrenAndSelf, (String)"UNITID");
        sql.append(" and  ").append(oppUnitTempCond.getCondition());
        if (!StringUtils.isEmpty((String)oppUnitTempCond.getTempGroupId())) {
            tempGroupIds.add(oppUnitTempCond.getTempGroupId());
        }
    }
}

