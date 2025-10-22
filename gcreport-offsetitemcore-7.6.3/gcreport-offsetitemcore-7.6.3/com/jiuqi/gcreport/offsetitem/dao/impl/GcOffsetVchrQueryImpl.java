/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.offsetitem.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GcOffsetVchrQueryImpl {
    @Resource
    private ConsolidatedSubjectClient subjectClient;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private UnionRuleService unionRuleService;

    public String getQueryOrgPeriod(String defaultPeriod) {
        if (StringUtils.isEmpty((String)defaultPeriod)) {
            return "";
        }
        if (defaultPeriod.endsWith("00")) {
            if (defaultPeriod.contains("N")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "1";
            }
            if (defaultPeriod.contains("H")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "2";
            }
            if (defaultPeriod.contains("J")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "4";
            }
            if (defaultPeriod.contains("Y")) {
                int monthValue = LocalDate.now().getMonthValue();
                String monthStr = monthValue > 9 ? String.valueOf(monthValue) : "0" + monthValue;
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + monthStr;
            }
            if (defaultPeriod.contains("X")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + "36";
            }
            if (defaultPeriod.contains("Z")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + "53";
            }
        }
        return defaultPeriod;
    }

    public void initUnitCondition(QueryParamsDTO queryParamsVO, StringBuffer whereSql, GcOrgCenterService service) {
        List<String> unitIdList = queryParamsVO.getUnitIdList();
        List<String> oppUnitIdList = queryParamsVO.getOppUnitIdList();
        if (queryParamsVO.isFixedUnitQueryPosition()) {
            whereSql.append(this.getUnitWhere(unitIdList, queryParamsVO, service, " and "));
            whereSql.append(this.getOppUnitWhere(oppUnitIdList, queryParamsVO, service, " and "));
        } else {
            String leftSign = "(";
            String orSign = " or ";
            if (!CollectionUtils.isEmpty(unitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getUnitWhere(unitIdList, queryParamsVO, service, ""));
                leftSign = "";
            }
            if (!CollectionUtils.isEmpty(oppUnitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getOppUnitWhere(oppUnitIdList, queryParamsVO, service, ""));
                whereSql.append(orSign).append(this.getUnitWhere(oppUnitIdList, queryParamsVO, service, ""));
                orSign = " and ";
            }
            if (!CollectionUtils.isEmpty(unitIdList)) {
                whereSql.append(orSign).append(this.getOppUnitWhere(unitIdList, queryParamsVO, service, ""));
            }
            if (!CollectionUtils.isEmpty(unitIdList) || !CollectionUtils.isEmpty(oppUnitIdList)) {
                whereSql.append(")\n");
            }
        }
    }

    public void initValidtimeCondition(StringBuffer head, StringBuffer whereSql, List<Object> params, Date date) {
        if (head.length() > 0) {
            head.append(",");
        }
        whereSql.append("and bfUnitTable.validtime<? and bfUnitTable.invalidtime>=? \n");
        whereSql.append("and dfUnitTable.validtime<? and dfUnitTable.invalidtime>=? \n");
        params.add(date);
        params.add(date);
        params.add(date);
        params.add(date);
    }

    public Map<String, Object> convert(Map<String, Object> record) {
        Double debit = ConverterUtils.getAsDouble((Object)record.get("OFFSETDEBIT"));
        Double credit = ConverterUtils.getAsDouble((Object)record.get("OFFSETCREDIT"));
        if (debit != null && debit != 0.0) {
            record.put("OFFSETDEBIT", NumberUtils.doubleToString((Double)debit));
            record.put("OFFSETCREDIT", "");
        } else if (credit != null && credit != 0.0) {
            record.put("OFFSETDEBIT", "");
            record.put("OFFSETCREDIT", NumberUtils.doubleToString((Double)credit));
        } else {
            Integer subjectOrient = (Integer)record.get("SUBJECTORIENT");
            record.put("OFFSETDEBIT", OrientEnum.C.getValue().equals(subjectOrient) ? "0.00" : "");
            record.put("OFFSETCREDIT", !OrientEnum.C.getValue().equals(subjectOrient) ? "0.00" : "");
        }
        Double diffc = ConverterUtils.getAsDouble((Object)record.get("DIFFC"));
        Double diffd = ConverterUtils.getAsDouble((Object)record.get("DIFFD"));
        record.put("DIFF", NumberUtils.doubleToString((double)((null == diffc ? 0.0 : diffc) + (null == diffd ? 0.0 : diffd))));
        return record;
    }

    public void initPeriodCondition(QueryParamsDTO queryParamsVO, List<Object> params, StringBuffer whereSql) {
        if (StringUtils.isEmpty((String)queryParamsVO.getSystemId())) {
            Assert.isNotNull((Object)queryParamsVO.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
            queryParamsVO.setSystemId(systemId);
        }
        Assert.isNotNull((Object)queryParamsVO.getSystemId(), (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        whereSql.append(" and record.systemid = ? ");
        params.add(queryParamsVO.getSystemId());
        whereSql.append(" and record.DATATIME=? ");
        if (queryParamsVO.isSameCtrl()) {
            params.add(queryParamsVO.getSameCtrlPeriodStr());
        } else {
            params.add(queryParamsVO.getPeriodStr());
        }
        String currentcy = queryParamsVO.getCurrencyUpperCase();
        whereSql.append(" and record.offsetCurr=? \n");
        params.add(currentcy);
    }

    public void initOtherCondition(StringBuffer whereSql, QueryParamsDTO queryParamsVO) {
        TempTableCondition tempTableCondition;
        if (queryParamsVO.isOnlyQueryNullRule()) {
            whereSql.append(" and record.RULEID is null ");
        } else if (!CollectionUtils.isEmpty(queryParamsVO.getRules())) {
            HashSet<String> ruleIds = new HashSet<String>();
            for (String id : queryParamsVO.getRules()) {
                ruleIds.add(id);
                List ruleEos = this.unionRuleService.selectUnionRuleChildrenByGroup(id);
                for (UnionRuleVO ruleEo : ruleEos) {
                    ruleIds.add(ruleEo.getId());
                }
            }
            if (!ruleIds.isEmpty()) {
                TempTableCondition tempTableCondition2 = SqlUtils.getConditionOfIds(ruleIds, (String)"record.RULEID");
                whereSql.append(" and ").append(tempTableCondition2.getCondition());
                queryParamsVO.getTempGroupIdList().add(tempTableCondition2.getTempGroupId());
            }
        }
        if (!CollectionUtils.isEmpty(queryParamsVO.getMrecids())) {
            tempTableCondition = SqlUtils.getConditionOfIds(queryParamsVO.getMrecids(), (String)"record.MRECID");
            whereSql.append(" and ").append(tempTableCondition.getCondition());
            queryParamsVO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
        }
        if (queryParamsVO.isOnlyQueryNullElmMode()) {
            whereSql.append(" and record.ELMMODE is null ");
        } else if (!CollectionUtils.isEmpty(queryParamsVO.getElmModes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble(queryParamsVO.getElmModes(), (String)"record.ELMMODE"));
        }
        if (queryParamsVO.isOnlyQueryNullGcBusinessTypeCode()) {
            whereSql.append(" and record.GCBUSINESSTYPECODE is null ");
        } else if (!CollectionUtils.isEmpty(queryParamsVO.getGcBusinessTypeCodes())) {
            tempTableCondition = SqlUtils.getConditionOfIds(queryParamsVO.getGcBusinessTypeCodes(), (String)"record.GCBUSINESSTYPECODE");
            whereSql.append(" and ").append(tempTableCondition.getCondition());
            queryParamsVO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
        }
        if (!CollectionUtils.isEmpty(queryParamsVO.getElmModes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble(queryParamsVO.getElmModes(), (String)"record.ELMMODE"));
        }
        if (!CollectionUtils.isEmpty(queryParamsVO.getOffSetSrcTypes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble(queryParamsVO.getOffSetSrcTypes(), (String)"record.OffSetSrcType"));
        }
        if (!CollectionUtils.isEmpty(queryParamsVO.getForbidOffSetSrcTypes())) {
            if (queryParamsVO.getForbidOffSetSrcTypes().size() == 1) {
                whereSql.append(" and record.OffSetSrcType <>").append(queryParamsVO.getForbidOffSetSrcTypes().get(0)).append("\n");
            } else {
                StringBuffer inSql = new StringBuffer(16);
                for (Integer forbidOffSetSrcType : queryParamsVO.getForbidOffSetSrcTypes()) {
                    inSql.append(forbidOffSetSrcType).append(",");
                }
                inSql.setLength(inSql.length() - 1);
                whereSql.append(" and record.offSetSrcType not in (").append(inSql).append(")");
            }
        }
        if (!CollectionUtils.isEmpty(queryParamsVO.getSubjectCodes())) {
            HashSet<String> subjectCodes = new HashSet<String>();
            if (!StringUtils.isEmpty((String)queryParamsVO.getSystemId())) {
                List subjectCodeList = queryParamsVO.getSubjectCodes().stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
                for (String subjectCode : subjectCodeList) {
                    if (subjectCodes.contains(subjectCode)) continue;
                    List subjectVOs = this.subjectClient.listAllChildrenSubjects(queryParamsVO.getSystemId(), subjectCode);
                    subjectCodes.add(subjectCode);
                    for (ConsolidatedSubjectVO subjectVO : subjectVOs) {
                        subjectCodes.add(subjectVO.getCode());
                    }
                }
            }
            if (!subjectCodes.isEmpty()) {
                whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectCodes, (String)"record.SUBJECTCODE"));
            }
            if (!CollectionUtils.isEmpty(queryParamsVO.getEffectTypes())) {
                whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(queryParamsVO.getEffectTypes(), (String)"record.EFFECTTYPE"));
            }
        }
        this.initOtherConditionByParamsVO(whereSql, queryParamsVO);
    }

    private void test() {
    }

    private void initOtherConditionByParamsVO(StringBuffer whereSql, QueryParamsDTO queryParamsVO) {
        if (!org.springframework.util.CollectionUtils.isEmpty(queryParamsVO.getFilterCondition())) {
            for (String key : queryParamsVO.getFilterCondition().keySet()) {
                Object tempValue = queryParamsVO.getFilterCondition().get(key);
                if (tempValue instanceof List) {
                    TempTableCondition tempTableCondition;
                    Object biggerValue;
                    Object smallerValue;
                    List doubleValues;
                    if ("subjectId".equals(key)) {
                        List selectedSubjects = (List)tempValue;
                        HashSet<String> subjectCodes = new HashSet<String>();
                        for (String uuid : selectedSubjects) {
                            ConsolidatedSubjectVO subjectEO = (ConsolidatedSubjectVO)this.subjectClient.getConsolidatedSubjectById(uuid).getData();
                            if (subjectCodes.contains(subjectEO.getCode())) continue;
                            subjectCodes.add(subjectEO.getCode());
                            List subjectVOs = this.subjectClient.listAllChildrenSubjects(subjectEO.getSystemId(), subjectEO.getCode());
                            for (ConsolidatedSubjectVO consolidatedSubjectVO : subjectVOs) {
                                subjectCodes.add(consolidatedSubjectVO.getCode());
                            }
                        }
                        if (subjectCodes.isEmpty()) continue;
                        whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectCodes, (String)"record.SUBJECTCODE"));
                        continue;
                    }
                    if ("subjectVo".equals(key)) {
                        if (StringUtils.isEmpty((String)queryParamsVO.getSystemId())) continue;
                        HashSet subjectCodes = new HashSet();
                        List selectedSubjects = (List)tempValue;
                        List allSubjectEos = this.subjectClient.listSubjectsBySystemIdNoSortOrder(queryParamsVO.getSystemId());
                        Map parentCode2DirectChildrenCodesMap = allSubjectEos.stream().collect(Collectors.groupingBy(ConsolidatedSubjectVO::getParentCode, Collectors.mapping(subject -> subject.getCode(), Collectors.toList())));
                        for (Map selectedSubjectVo : selectedSubjects) {
                            String subjectCode = (String)selectedSubjectVo.get("code");
                            if (StringUtils.isEmpty((String)subjectCode) || subjectCodes.contains(subjectCode)) continue;
                            HashSet allChildrenSubjectCodes = new HashSet(MapUtils.listAllChildrens((String)subjectCode, (Map)parentCode2DirectChildrenCodesMap));
                            subjectCodes.addAll(allChildrenSubjectCodes);
                            subjectCodes.add(subjectCode);
                        }
                        if (subjectCodes.isEmpty()) continue;
                        whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectCodes, (String)"record.SUBJECTCODE"));
                        continue;
                    }
                    if ("ruleId".equals(key)) {
                        TempTableCondition tempTableCondition2;
                        List selectedRules = (List)tempValue;
                        HashSet<String> ruleIds = new HashSet<String>();
                        ArrayList<String> fetchSetGroupIds = new ArrayList<String>();
                        for (String uuid : selectedRules) {
                            if (StringUtils.isEmpty((String)uuid)) continue;
                            if (this.unionRuleService.selectUnionRuleDTOById(uuid) != null) {
                                ruleIds.add(uuid);
                                List ruleEos = this.unionRuleService.selectUnionRuleChildrenByGroup(uuid);
                                for (UnionRuleVO ruleEo : ruleEos) {
                                    ruleIds.add(ruleEo.getId());
                                }
                                continue;
                            }
                            fetchSetGroupIds.add(uuid);
                        }
                        if (!ruleIds.isEmpty() && !fetchSetGroupIds.isEmpty()) {
                            TempTableCondition ruleIdCondition = SqlUtils.getConditionOfIds(ruleIds, (String)"record.RULEID");
                            whereSql.append(" and ( ").append(ruleIdCondition.getCondition());
                            queryParamsVO.getTempGroupIdList().add(ruleIdCondition.getTempGroupId());
                            TempTableCondition fetchSetGroupIdCondition = SqlUtils.getConditionOfIds(fetchSetGroupIds, (String)"record.fetchSetGroupId");
                            whereSql.append(" or ").append(fetchSetGroupIdCondition.getCondition()).append(" ) ");
                            queryParamsVO.getTempGroupIdList().add(fetchSetGroupIdCondition.getTempGroupId());
                            continue;
                        }
                        if (!ruleIds.isEmpty()) {
                            tempTableCondition2 = SqlUtils.getConditionOfIds(ruleIds, (String)"record.RULEID");
                            whereSql.append(" and ").append(tempTableCondition2.getCondition());
                            queryParamsVO.getTempGroupIdList().add(tempTableCondition2.getTempGroupId());
                        }
                        if (fetchSetGroupIds.isEmpty()) continue;
                        tempTableCondition2 = SqlUtils.getConditionOfIds(fetchSetGroupIds, (String)"record.fetchSetGroupId");
                        whereSql.append(" and ").append(tempTableCondition2.getCondition());
                        queryParamsVO.getTempGroupIdList().add(tempTableCondition2.getTempGroupId());
                        continue;
                    }
                    if ("ruleVo".equals(key)) {
                        this.addRuleWhereSql(whereSql, (List)tempValue, queryParamsVO);
                        continue;
                    }
                    if ("gcbusinesstypecode".equals(key)) {
                        List codeValuesTemp = (List)tempValue;
                        if (CollectionUtils.isEmpty((Collection)codeValuesTemp)) continue;
                        whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)codeValuesTemp, (String)("record." + key)));
                        continue;
                    }
                    if ("offset_value_number".equals(key)) {
                        doubleValues = (List)tempValue;
                        if (CollectionUtils.isEmpty((Collection)doubleValues)) continue;
                        smallerValue = doubleValues.get(0);
                        if (smallerValue instanceof Number) {
                            whereSql.append(" and coalesce(record.offset_Credit,0)+coalesce(record.offset_Debit,0)>=").append(smallerValue);
                        }
                        if (doubleValues.size() <= 1 || !((biggerValue = doubleValues.get(1)) instanceof Number)) continue;
                        whereSql.append(" and coalesce(record.offset_Credit,0)+coalesce(record.offset_Debit,0)<=").append(biggerValue);
                        continue;
                    }
                    if ("offset_value_init_number".equals(key)) {
                        doubleValues = (List)tempValue;
                        if (CollectionUtils.isEmpty((Collection)doubleValues)) continue;
                        smallerValue = doubleValues.get(0);
                        if (smallerValue instanceof Number) {
                            whereSql.append(" and coalesce(record.offset_Credit,0)+coalesce(record.offset_Debit,0)>=").append(smallerValue);
                        }
                        if (doubleValues.size() <= 1 || !((biggerValue = doubleValues.get(1)) instanceof Number)) continue;
                        whereSql.append(" and coalesce(record.offset_Credit,0)+coalesce(record.offset_Debit,0)<=").append(biggerValue);
                        continue;
                    }
                    if ("unitVo".equals(key)) {
                        Set<String> unitIds = this.getUnitIdsSet((List)tempValue);
                        if (unitIds.isEmpty()) continue;
                        tempTableCondition = SqlUtils.getConditionOfIds(unitIds, (String)"bfUnitTable.code");
                        whereSql.append(" and ").append(tempTableCondition.getCondition());
                        queryParamsVO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
                        continue;
                    }
                    if ("oppUnitVo".equals(key)) {
                        Set<String> oppUnitIds = this.getUnitIdsSet((List)tempValue);
                        if (oppUnitIds.isEmpty()) continue;
                        tempTableCondition = SqlUtils.getConditionOfIds(oppUnitIds, (String)"dfUnitTable.code");
                        whereSql.append(" and ").append(tempTableCondition.getCondition());
                        queryParamsVO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
                        continue;
                    }
                    if (!(tempValue instanceof List)) continue;
                    List valueList = (List)tempValue;
                    List values = valueList.stream().map(item -> ConverterUtils.getAsString((Object)item)).collect(Collectors.toList());
                    if (key.endsWith("_number")) {
                        String biggerValue2;
                        key = key.replace("_number", "");
                        if (CollectionUtils.isEmpty(values)) continue;
                        String smallerValue2 = (String)values.get(0);
                        if (!StringUtils.isEmpty((String)smallerValue2) && smallerValue2.matches("-?\\d+\\.?\\d*")) {
                            whereSql.append(" and record.").append(key).append(">=").append(smallerValue2);
                        }
                        if (values.size() < 2 || StringUtils.isEmpty((String)(biggerValue2 = (String)values.get(1))) || !biggerValue2.matches("-?\\d+\\.?\\d*")) continue;
                        whereSql.append(" and record.").append(key).append("<=").append(biggerValue2);
                        continue;
                    }
                    if (values == null || values.size() <= 0) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(values, (String)("record." + key)));
                    continue;
                }
                String strValue = String.valueOf(tempValue);
                if ("hasDiffAmt".equals(key)) {
                    if (!"true".equals(strValue)) continue;
                    whereSql.append(" and (record.diffc+record.diffd) <> 0 ");
                    continue;
                }
                if (StringUtils.isEmpty((String)strValue)) continue;
                if (!strValue.matches("[^'\\s]+")) {
                    whereSql.append(" and 1=2 ");
                }
                if ("memo".equals(key)) {
                    whereSql.append(" and record.").append(key).append(" like '%").append(strValue).append("%'");
                    continue;
                }
                if ("OnlyQueryEmpty".equals(strValue)) {
                    whereSql.append(" and (record.").append(key).append(" is null ").append(" or record.").append(key).append(" ='' )");
                    continue;
                }
                whereSql.append(" and record.").append(key).append("='").append(strValue).append("'");
            }
        }
    }

    private void addRuleWhereSql(StringBuffer whereSql, List tempValue, QueryParamsDTO queryParamsVO) {
        TempTableCondition tempTableCondition;
        List selectedRules = tempValue;
        if (CollectionUtils.isEmpty((Collection)selectedRules)) {
            return;
        }
        boolean hasEmpty = false;
        HashSet<String> ruleIds = new HashSet<String>();
        ArrayList<String> fetchSetGroupIds = new ArrayList<String>();
        for (Map selectedRuleVo : selectedRules) {
            String ruleId = (String)selectedRuleVo.get("id");
            if (StringUtils.isEmpty((String)ruleId)) continue;
            if ("empty".equals(ruleId)) {
                hasEmpty = true;
                continue;
            }
            if (ruleIds.contains(ruleId)) continue;
            if (this.unionRuleService.selectUnionRuleDTOById(ruleId) == null) {
                fetchSetGroupIds.add(ruleId);
                continue;
            }
            ruleIds.add(ruleId);
            List ruleEos = this.unionRuleService.selectUnionRuleChildrenByGroup(ruleId);
            for (UnionRuleVO ruleEo : ruleEos) {
                ruleIds.add(ruleEo.getId());
            }
        }
        if (!hasEmpty && ruleIds.isEmpty() && fetchSetGroupIds.isEmpty()) {
            return;
        }
        boolean needOr = false;
        whereSql.append(" and ( \n");
        if (hasEmpty) {
            whereSql.append("  ( record.RULEID is null or record.RULEID='') \n");
            needOr = true;
        }
        if (!ruleIds.isEmpty()) {
            tempTableCondition = SqlUtils.getConditionOfIds(ruleIds, (String)"record.RULEID");
            whereSql.append(needOr ? " or " : "").append(tempTableCondition.getCondition());
            queryParamsVO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
            needOr = true;
        }
        if (!fetchSetGroupIds.isEmpty()) {
            tempTableCondition = SqlUtils.getConditionOfIds(fetchSetGroupIds, (String)"record.fetchSetGroupId");
            whereSql.append(needOr ? " or " : "").append(tempTableCondition.getCondition());
            queryParamsVO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
        }
        whereSql.append(" )\n");
    }

    private Set<String> getUnitIdsSet(List<Map<String, String>> selectedUnitVos) {
        HashSet<String> unitIds = new HashSet<String>();
        for (Map<String, String> selectedUnitVo : selectedUnitVos) {
            String unitId;
            String string = unitId = StringUtils.isEmpty((String)selectedUnitVo.get("id")) ? selectedUnitVo.get("code") : selectedUnitVo.get("id");
            if (StringUtils.isEmpty((String)unitId)) continue;
            unitIds.add(unitId);
        }
        return unitIds;
    }

    private String getUnitWhere(List<String> unitIdList, QueryParamsDTO queryParamsVO, GcOrgCenterService service, String prefix) {
        if (!CollectionUtils.isEmpty(unitIdList)) {
            if (queryParamsVO.isWhenOneUnitIsAllChild() && unitIdList.size() == 1) {
                String unitParents = this.getUnitParents(unitIdList.get(0), service);
                return prefix + "bfUnitTable.gcparents like '" + unitParents + "%' ";
            }
            if (queryParamsVO.getEnableTempTableFilterUnitOrOppUnit().booleanValue()) {
                TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(unitIdList, (String)"bfUnitTable.code");
                queryParamsVO.getTempGroupIdList().add(newConditionOfIds.getTempGroupId());
                return prefix + newConditionOfIds.getCondition();
            }
            TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(unitIdList, (String)"bfUnitTable.code");
            queryParamsVO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
            return prefix + tempTableCondition.getCondition();
        }
        return "";
    }

    private String getOppUnitWhere(List<String> unitIdList, QueryParamsDTO queryParamsVO, GcOrgCenterService service, String prefix) {
        if (!CollectionUtils.isEmpty(unitIdList)) {
            if (queryParamsVO.isWhenOneUnitIsAllChild() && unitIdList.size() == 1) {
                String unitParents = this.getUnitParents(unitIdList.get(0), service);
                return prefix + "dfUnitTable.gcparents like '" + unitParents + "%' ";
            }
            if (queryParamsVO.getEnableTempTableFilterUnitOrOppUnit().booleanValue()) {
                TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(unitIdList, (String)"dfUnitTable.code");
                queryParamsVO.getTempGroupIdList().add(newConditionOfIds.getTempGroupId());
                return prefix + newConditionOfIds.getCondition();
            }
            TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(unitIdList, (String)"dfUnitTable.code");
            queryParamsVO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
            return prefix + tempTableCondition.getCondition();
        }
        return "";
    }

    private String getUnitParents(String unitRecid, GcOrgCenterService service) {
        if (unitRecid == null) {
            return null;
        }
        GcOrgCacheVO organization = service.getOrgByID(unitRecid);
        if (organization == null) {
            return null;
        }
        return organization.getGcParentStr();
    }
}

