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
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.api.UnionRuleClient
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.workingpaper.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.api.UnionRuleClient;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
public class ArbitrarilyMergeOffsetVchrQueryImpl {
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Resource
    private ConsolidatedSubjectClient subjectClient;
    @Autowired
    private UnionRuleService unionRuleService;
    @Resource
    private UnionRuleClient unionRuleClient;
    @Autowired
    private ConsolidatedTaskService taskService;

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

    public void initUnitCondition(QueryParamsVO queryParamsVO, StringBuilder whereSql, GcOrgCenterService service) {
        List unitIdList = queryParamsVO.getUnitIdList();
        List oppUnitIdList = queryParamsVO.getOppUnitIdList();
        if (queryParamsVO.isFixedUnitQueryPosition()) {
            whereSql.append(this.getUnitWhere(unitIdList, queryParamsVO, service, " and "));
            whereSql.append(this.getOppUnitWhere(oppUnitIdList, queryParamsVO, service, " and "));
        } else {
            String leftSign = "(";
            String orSign = " or ";
            if (!CollectionUtils.isEmpty((Collection)unitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getUnitWhere(unitIdList, queryParamsVO, service, ""));
                leftSign = "";
            }
            if (!CollectionUtils.isEmpty((Collection)oppUnitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getOppUnitWhere(oppUnitIdList, queryParamsVO, service, ""));
                whereSql.append(orSign).append(this.getUnitWhere(oppUnitIdList, queryParamsVO, service, ""));
                orSign = " and ";
            }
            if (!CollectionUtils.isEmpty((Collection)unitIdList)) {
                whereSql.append(orSign).append(this.getOppUnitWhere(unitIdList, queryParamsVO, service, ""));
            }
            if (!CollectionUtils.isEmpty((Collection)unitIdList) || !CollectionUtils.isEmpty((Collection)oppUnitIdList)) {
                whereSql.append(")\n");
            }
        }
    }

    public void initValidtimeCondition(StringBuilder head, StringBuilder whereSql, List<Object> params, Date date) {
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
        Integer orient = (Integer)record.get("ORIENT");
        if (null != orient) {
            if (orient == OrientEnum.C.getValue()) {
                Double credit = ConverterUtils.getAsDouble((Object)record.get("OFFSETCREDIT"));
                record.put("OFFSETDEBIT", "");
                record.put("OFFSETCREDIT", NumberUtils.doubleToString((double)(credit == null ? 0.0 : credit)));
            } else {
                Double debit = ConverterUtils.getAsDouble((Object)record.get("OFFSETDEBIT"));
                record.put("OFFSETDEBIT", NumberUtils.doubleToString((double)(debit == null ? 0.0 : debit)));
                record.put("OFFSETCREDIT", "");
            }
        }
        Double diffc = ConverterUtils.getAsDouble((Object)record.get("DIFFC"));
        Double diffd = ConverterUtils.getAsDouble((Object)record.get("DIFFD"));
        record.put("DIFF", NumberUtils.doubleToString((double)((null == diffc ? 0.0 : diffc) + (null == diffd ? 0.0 : diffd))));
        return record;
    }

    public void initPeriodCondition(QueryParamsVO queryParamsVO, List<Object> params, StringBuilder whereSql) {
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

    public void initPeriodConditionForUnOffset(QueryParamsVO queryParamsVO, List<Object> params, StringBuilder whereSql) {
        if (StringUtils.isEmpty((String)queryParamsVO.getSystemId())) {
            Assert.isNotNull((Object)queryParamsVO.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
            queryParamsVO.setSystemId(systemId);
        }
        Assert.isNotNull((Object)queryParamsVO.getSystemId(), (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        whereSql.append(" and record.REPORTSYSTEMID = ? ");
        params.add(queryParamsVO.getSystemId());
        whereSql.append(" and record.DATATIME=? ");
        if (queryParamsVO.isSameCtrl()) {
            params.add(queryParamsVO.getSameCtrlPeriodStr());
        } else {
            params.add(queryParamsVO.getPeriodStr());
        }
        String currentcy = queryParamsVO.getCurrencyUpperCase();
        whereSql.append(" and record.MD_CURRENCY=? \n");
        params.add(currentcy);
    }

    public void initOtherCondition(StringBuilder whereSql, QueryParamsVO queryParamsVO) {
        if (queryParamsVO.isOnlyQueryNullRule()) {
            whereSql.append(" and record.RULEID is null ");
        } else if (!CollectionUtils.isEmpty((Collection)queryParamsVO.getRules())) {
            HashSet<String> ruleIds = new HashSet<String>();
            for (String id : queryParamsVO.getRules()) {
                ruleIds.add(id);
                List ruleEos = this.unionRuleService.selectAllChildrenRuleEo(id);
                for (UnionRuleEO ruleEo : ruleEos) {
                    ruleIds.add(ruleEo.getId());
                }
            }
            if (!ruleIds.isEmpty()) {
                whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.RULEID"));
            }
        }
        if (!CollectionUtils.isEmpty((Collection)queryParamsVO.getMrecids())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr((Collection)queryParamsVO.getMrecids(), (String)"record.MRECID"));
        }
        if (queryParamsVO.isOnlyQueryNullElmMode()) {
            whereSql.append(" and record.ELMMODE is null ");
        } else if (!CollectionUtils.isEmpty((Collection)queryParamsVO.getElmModes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble((Collection)queryParamsVO.getElmModes(), (String)"record.ELMMODE"));
        }
        if (queryParamsVO.isOnlyQueryNullGcBusinessTypeCode()) {
            whereSql.append(" and record.GCBUSINESSTYPECODE is null ");
        } else if (!CollectionUtils.isEmpty((Collection)queryParamsVO.getGcBusinessTypeCodes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr((Collection)queryParamsVO.getGcBusinessTypeCodes(), (String)"record.GCBUSINESSTYPECODE"));
        }
        if (!CollectionUtils.isEmpty((Collection)queryParamsVO.getElmModes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble((Collection)queryParamsVO.getElmModes(), (String)"record.ELMMODE"));
        }
        if (!CollectionUtils.isEmpty((Collection)queryParamsVO.getOffSetSrcTypes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble((Collection)queryParamsVO.getOffSetSrcTypes(), (String)"record.OffSetSrcType"));
        }
        if (!CollectionUtils.isEmpty((Collection)queryParamsVO.getForbidOffSetSrcTypes())) {
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
        if (!CollectionUtils.isEmpty((Collection)queryParamsVO.getSubjectCodes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)queryParamsVO.getSubjectCodes(), (String)"record.SUBJECTCODE"));
        }
        this.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSystemId(), queryParamsVO.getSchemeId(), queryParamsVO.getCurrencyUpperCase(), queryParamsVO.getPeriodStr());
    }

    private void initOtherCondition(StringBuilder whereSql, Map<String, Object> filterCondition, String systemId, String schemeId, String currencyCode, String periodStr) {
        if (!org.springframework.util.CollectionUtils.isEmpty(filterCondition)) {
            for (String key : filterCondition.keySet()) {
                Object tempValue = filterCondition.get(key);
                if (tempValue instanceof List) {
                    Object biggerValue;
                    Object smallerValue;
                    List doubleValues;
                    ArrayList<String> fetchSetGroupIds;
                    HashSet<String> ruleIds;
                    List selectedRules;
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
                        if (StringUtils.isEmpty((String)systemId)) continue;
                        HashSet<String> subjectCodes = new HashSet<String>();
                        List selectedSubjects = (List)tempValue;
                        List allSubjectEos = this.subjectClient.listSubjectsBySystemIdNoSortOrder(systemId);
                        Map parentCode2DirectChildrenCodesMap = allSubjectEos.stream().collect(Collectors.groupingBy(ConsolidatedSubjectVO::getParentCode, Collectors.mapping(subject -> subject.getCode(), Collectors.toList())));
                        for (Map selectedSubjectVo : selectedSubjects) {
                            String subjectCode = (String)selectedSubjectVo.get("code");
                            if (StringUtils.isEmpty((String)subjectCode) || subjectCodes.contains(subjectCode)) continue;
                            HashSet allChildrenSubjectCodes = new HashSet(MapUtils.listAllChildrens((String)subjectCode, parentCode2DirectChildrenCodesMap));
                            subjectCodes.addAll(allChildrenSubjectCodes);
                            subjectCodes.add(subjectCode);
                        }
                        if (subjectCodes.isEmpty()) continue;
                        whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectCodes, (String)"record.SUBJECTCODE"));
                        continue;
                    }
                    if ("ruleId".equals(key)) {
                        selectedRules = (List)tempValue;
                        ruleIds = new HashSet<String>();
                        fetchSetGroupIds = new ArrayList<String>();
                        for (String uuid : selectedRules) {
                            if (StringUtils.isEmpty((String)uuid)) continue;
                            String id = uuid;
                            if (this.unionRuleClient.selectUnionRuleById(id).getData() != null) {
                                ruleIds.add(id);
                                List ruleEos = this.unionRuleClient.selectUnionRuleChildrenByGroup(id);
                                for (UnionRuleVO ruleEo : ruleEos) {
                                    ruleIds.add(ruleEo.getId());
                                }
                                continue;
                            }
                            fetchSetGroupIds.add(id);
                        }
                        if (!ruleIds.isEmpty() && !fetchSetGroupIds.isEmpty()) {
                            whereSql.append(" and ( ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.RULEID"));
                            whereSql.append(" or ").append(SqlUtils.getConditionOfIdsUseOr(fetchSetGroupIds, (String)"record.fetchSetGroupId")).append(" ) ");
                            continue;
                        }
                        if (!ruleIds.isEmpty()) {
                            whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.RULEID"));
                        }
                        if (fetchSetGroupIds.isEmpty()) continue;
                        whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(fetchSetGroupIds, (String)"record.fetchSetGroupId"));
                        continue;
                    }
                    if ("ruleVo".equals(key)) {
                        selectedRules = (List)tempValue;
                        ruleIds = new HashSet();
                        fetchSetGroupIds = new ArrayList();
                        for (Map selectedRuleVo : selectedRules) {
                            String id;
                            String ruleTitle;
                            String ruleId = (String)selectedRuleVo.get("id");
                            if (StringUtils.isEmpty((String)ruleId) || StringUtils.isEmpty((String)(ruleTitle = this.unionRuleService.selectUnionRuleTitleById(id = ruleId))) || ruleIds.contains(id)) continue;
                            ruleIds.add(id);
                            List ruleEos = this.unionRuleService.selectAllChildrenRuleEo(id);
                            for (UnionRuleEO ruleEo : ruleEos) {
                                ruleIds.add(ruleEo.getId());
                            }
                        }
                        if (!ruleIds.isEmpty() && !fetchSetGroupIds.isEmpty()) {
                            whereSql.append(" and ( ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.RULEID"));
                            whereSql.append(" or ").append(SqlUtils.getConditionOfIdsUseOr(fetchSetGroupIds, (String)"record.fetchSetGroupId")).append(" ) ");
                            continue;
                        }
                        if (!ruleIds.isEmpty()) {
                            whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.RULEID"));
                        }
                        if (fetchSetGroupIds.isEmpty()) continue;
                        whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(fetchSetGroupIds, (String)"record.fetchSetGroupId"));
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
                            whereSql.append(" and coalesce(record.offset_Credit_").append(currencyCode).append(",0)+coalesce(record.offset_Debit_").append(currencyCode).append(",0)>=").append(smallerValue);
                        }
                        if (doubleValues.size() <= 1 || !((biggerValue = doubleValues.get(1)) instanceof Number)) continue;
                        whereSql.append(" and coalesce(record.offset_Credit_").append(currencyCode).append(",0)+coalesce(record.offset_Debit_").append(currencyCode).append(",0)<=").append(biggerValue);
                        continue;
                    }
                    if ("offset_value_init_number".equals(key)) {
                        doubleValues = (List)tempValue;
                        if (CollectionUtils.isEmpty((Collection)doubleValues)) continue;
                        smallerValue = doubleValues.get(0);
                        if (smallerValue instanceof Number) {
                            whereSql.append(" and coalesce(record.offset_Credit").append(",0)+coalesce(record.offset_Debit").append(",0)>=").append(smallerValue);
                        }
                        if (doubleValues.size() <= 1 || !((biggerValue = doubleValues.get(1)) instanceof Number)) continue;
                        whereSql.append(" and coalesce(record.offset_Credit").append(",0)+coalesce(record.offset_Debit").append(",0)<=").append(biggerValue);
                        continue;
                    }
                    if ("unitVo".equals(key)) {
                        Set<String> unitIds = this.getUnitIdsSet((List)tempValue);
                        if (unitIds.isEmpty()) continue;
                        whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(unitIds, (String)"bfUnitTable.code"));
                        continue;
                    }
                    if ("oppUnitVo".equals(key)) {
                        Set<String> oppUnitIds = this.getUnitIdsSet((List)tempValue);
                        if (oppUnitIds.isEmpty()) continue;
                        whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(oppUnitIds, (String)"dfUnitTable.code"));
                        continue;
                    }
                    if (!(tempValue instanceof List)) continue;
                    List values = (List)tempValue;
                    if (key.endsWith("_number")) {
                        key = key.replace("_number", "");
                        if (CollectionUtils.isEmpty((Collection)values)) continue;
                        smallerValue = (String)values.get(0);
                        if (!StringUtils.isEmpty(smallerValue) && ((String)smallerValue).matches("-?\\d+\\.?\\d*")) {
                            whereSql.append(" and record.").append(key).append(">=").append((String)smallerValue);
                        }
                        if (values.size() < 2 || StringUtils.isEmpty(biggerValue = (String)values.get(1)) || !((String)biggerValue).matches("-?\\d+\\.?\\d*")) continue;
                        whereSql.append(" and record.").append(key).append("<=").append((String)biggerValue);
                        continue;
                    }
                    if (values == null || values.size() <= 0) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)values, (String)("record." + key)));
                    continue;
                }
                String strValue = String.valueOf(tempValue);
                if ("hasDiffAmt".equals(key)) {
                    if (!"true".equals(strValue)) continue;
                    whereSql.append(" and (record.diffc_").append(currencyCode).append("+record.diffd_").append(currencyCode).append(")<>0 ");
                    continue;
                }
                if (StringUtils.isEmpty((String)strValue) || strValue.trim().contains(" ")) continue;
                if ("OnlyQueryEmpty".equals(strValue)) {
                    whereSql.append(" and (record.").append(key).append(" is null ").append(" or record.").append(key).append(" ='' )");
                    continue;
                }
                whereSql.append(" and record.").append(key).append("='").append(strValue).append("'");
            }
        }
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

    private String getUnitWhere(List<String> unitIdList, QueryParamsVO queryParamsVO, GcOrgCenterService service, String prefix) {
        if (!CollectionUtils.isEmpty(unitIdList)) {
            if (queryParamsVO.isWhenOneUnitIsAllChild() && unitIdList.size() == 1) {
                String unitParents = this.getUnitParents(unitIdList.get(0), service);
                return prefix + "bfUnitTable.parents like '" + unitParents + "%' ";
            }
            return prefix + SqlUtils.getConditionOfIdsUseOr(unitIdList, (String)"bfUnitTable.code");
        }
        return "";
    }

    private String getOppUnitWhere(List<String> unitIdList, QueryParamsVO queryParamsVO, GcOrgCenterService service, String prefix) {
        if (!CollectionUtils.isEmpty(unitIdList)) {
            if (queryParamsVO.isWhenOneUnitIsAllChild() && unitIdList.size() == 1) {
                String unitParents = this.getUnitParents(unitIdList.get(0), service);
                return prefix + "dfUnitTable.parents like '" + unitParents + "%' ";
            }
            return prefix + SqlUtils.getConditionOfIdsUseOr(unitIdList, (String)"dfUnitTable.code");
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
        return organization.getParentStr();
    }
}

