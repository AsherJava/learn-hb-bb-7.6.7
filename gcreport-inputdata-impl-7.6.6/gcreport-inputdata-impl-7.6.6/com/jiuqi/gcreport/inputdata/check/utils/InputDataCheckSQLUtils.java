/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckLevelEnum
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleConverter
 */
package com.jiuqi.gcreport.inputdata.check.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckLevelEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class InputDataCheckSQLUtils {
    public static String getQueryCheckTabDataSql(InputDataCheckCondition inputDataCheckCondition, List<Object> params, InputDataCheckStateEnum inputDataCheckStateEnum, String systemId) {
        YearPeriodObject yp = new YearPeriodObject(null, inputDataCheckCondition.getDataTime());
        Date date = yp.formatYP().getEndDate();
        String orgTableName = inputDataCheckCondition.getUnitDefine();
        if (StringUtils.isEmpty((String)orgTableName)) {
            orgTableName = DimensionUtils.getDwEntitieTableByTaskKey((String)inputDataCheckCondition.getTaskId());
        }
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        if (Objects.isNull(inputDataCheckCondition.getUnitCode()) || StringUtils.isEmpty((String)inputDataCheckCondition.getUnitCode().getCode())) {
            throw new RuntimeException("\u8bf7\u9009\u62e9\u672c\u65b9\u5355\u4f4d\u3002");
        }
        StringBuffer sql = new StringBuffer();
        InputDataCheckSQLUtils.getUnionOrgTableSql(inputDataCheckCondition, params, sql, orgTableName, date, tool);
        InputDataCheckSQLUtils.getOtherWhereSql(sql, inputDataCheckCondition, params, inputDataCheckStateEnum, systemId);
        return sql.toString();
    }

    private static void getUnionOrgTableSql(InputDataCheckCondition inputDataCheckCondition, List<Object> params, StringBuffer sql, String orgTableName, Date date, GcOrgCenterService tool) {
        GcOrgCacheVO orgCacheVO = tool.getOrgByCode(inputDataCheckCondition.getUnitCode().getCode());
        String gcParents = orgCacheVO.getGcParentStr();
        String checkLevel = inputDataCheckCondition.getCheckLevel();
        if (InputDataCheckLevelEnum.ALL_MERGE.getCode().equals(checkLevel)) {
            sql.append(" join ").append(orgTableName).append("  bfUnitTable on record.mdcode = bfUnitTable.code \n");
            sql.append(" and bfUnitTable.validtime<? and bfUnitTable.invalidtime>=? \n");
            sql.append(" join ").append(orgTableName).append("  dfUnitTable on record.OPPUNITID = dfUnitTable.code \n");
            sql.append(" and dfUnitTable.validtime<? and dfUnitTable.invalidtime>=? \n");
            sql.append(" where \n");
            sql.append(" (bfUnitTable.GCPARENTS like '").append(gcParents).append("%%' ");
            sql.append(" or dfUnitTable.GCPARENTS like '").append(gcParents).append("%%' )");
            params.addAll(Arrays.asList(date, date, date, date));
        } else if (InputDataCheckLevelEnum.CURRENT_MERGE.getCode().equals(checkLevel)) {
            int mergeUnitChildBeginIndex = gcParents.length() + 2;
            int len = tool.getOrgCodeLength();
            sql.append(" join ").append(orgTableName).append("  bfUnitTable on record.mdcode = bfUnitTable.code \n");
            sql.append(" and bfUnitTable.validtime<? and bfUnitTable.invalidtime>=? \n");
            sql.append(" join ").append(orgTableName).append("  dfUnitTable on record.OPPUNITID = dfUnitTable.code \n");
            sql.append(" and dfUnitTable.validtime<? and dfUnitTable.invalidtime>=? \n");
            sql.append(" where \n");
            sql.append(" bfUnitTable.GCPARENTS like '").append(gcParents).append("%%' \n");
            sql.append(" AND dfUnitTable.GCPARENTS like '").append(gcParents).append("%%' \n");
            sql.append(" AND SUBSTR(bfUnitTable.GCPARENTS,").append(mergeUnitChildBeginIndex).append(",").append(len).append(") != ");
            sql.append(" SUBSTR(dfUnitTable.GCPARENTS,").append(mergeUnitChildBeginIndex).append(",").append(len).append(") \n");
            params.addAll(Arrays.asList(date, date, date, date));
        } else if (InputDataCheckLevelEnum.PARENT_MERGE.getCode().equals(checkLevel)) {
            sql.append(" join ").append(orgTableName).append("  bfUnitTable on record.mdcode = bfUnitTable.code \n");
            sql.append(" and bfUnitTable.validtime<? and bfUnitTable.invalidtime>=? \n");
            sql.append(" join ").append(orgTableName).append("  dfUnitTable on record.OPPUNITID = dfUnitTable.code \n");
            sql.append(" and dfUnitTable.validtime<? and dfUnitTable.invalidtime>=? \n");
            sql.append(" where \n");
            sql.append(" ((bfUnitTable.GCPARENTS like '").append(gcParents).append("%%' \n");
            sql.append(" AND SUBSTR(dfUnitTable.GCPARENTS,1,").append(gcParents.length()).append(") != '").append(gcParents).append("' )\n");
            sql.append(" or ( dfUnitTable.GCPARENTS like '").append(gcParents).append("%%' ");
            sql.append(" AND SUBSTR(bfUnitTable.GCPARENTS,1,").append(gcParents.length()).append(") != '").append(gcParents).append("' )) \n");
            params.addAll(Arrays.asList(date, date, date, date));
        } else if (InputDataCheckLevelEnum.CHILDREN_MERGE.getCode().equals(checkLevel)) {
            sql.append(" join ").append(orgTableName).append("  bfUnitTable on record.mdcode = bfUnitTable.code \n");
            sql.append(" and bfUnitTable.validtime<? and bfUnitTable.invalidtime>=? \n");
            sql.append(" join ").append(orgTableName).append("  dfUnitTable on record.OPPUNITID = dfUnitTable.code \n");
            sql.append(" and dfUnitTable.validtime<? and dfUnitTable.invalidtime>=? \n");
            sql.append(" where \n");
            int len = tool.getOrgCodeLength();
            List directChildren = orgCacheVO.getChildren();
            List directChildrens = directChildren.stream().filter(org -> org.getOrgKind() == GcOrgKindEnum.UNIONORG).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(directChildrens)) {
                sql.append(" (");
                for (int i = 0; i < directChildrens.size(); ++i) {
                    String mergeParents = ((GcOrgCacheVO)directChildrens.get(i)).getParentStr();
                    int directChildrenParentslen = ((GcOrgCacheVO)directChildrens.get(i)).getGcParentStr().length();
                    if (i != 0) {
                        sql.append(" or ");
                    }
                    sql.append(" (");
                    sql.append(" bfUnitTable.parents like '" + mergeParents + "%' \n");
                    sql.append(" and dfUnitTable.parents like '" + mergeParents + "%' \n");
                    sql.append(" and substr(bfUnitTable.gcparents, 1, " + (directChildrenParentslen + len + 1) + ") <> substr(dfUnitTable.gcparents, 1," + (directChildrenParentslen + len + 1) + ")");
                    sql.append(") \n");
                }
                sql.append(") \n");
            } else {
                sql.append(" 1 = 2 \n");
            }
            params.addAll(Arrays.asList(date, date, date, date));
        } else if (InputDataCheckLevelEnum.CUSTOM.getCode().equals(checkLevel)) {
            sql.append(" where \n");
            HashSet<String> orgCodes = new HashSet<String>();
            if (orgCacheVO.isLeaf()) {
                orgCodes.add(inputDataCheckCondition.getUnitCode().getCode());
            } else {
                orgCodes.addAll(InputDataCheckSQLUtils.orgTreeToSet(CollectionUtils.newArrayList((Object[])new GcOrgCacheVO[]{inputDataCheckCondition.getUnitCode()}), tool));
            }
            if (!CollectionUtils.isEmpty((Collection)inputDataCheckCondition.getOppUnitId())) {
                orgCodes.addAll(InputDataCheckSQLUtils.orgTreeToSet(inputDataCheckCondition.getOppUnitId(), tool));
                sql.append(" (").append(SqlUtils.getConditionOfMulStrUseOr(orgCodes, (String)"record.mdcode"));
                sql.append(" AND ").append(SqlUtils.getConditionOfMulStrUseOr(orgCodes, (String)"record.OPPUNITID")).append(")");
            } else {
                sql.append(" (").append(SqlUtils.getConditionOfMulStrUseOr(orgCodes, (String)"record.mdcode"));
                sql.append(" OR ").append(SqlUtils.getConditionOfMulStrUseOr(orgCodes, (String)"record.OPPUNITID")).append(")");
            }
        }
        if (!CollectionUtils.isEmpty((Collection)inputDataCheckCondition.getCheckGatherColumns())) {
            Set<String> orgCodes;
            if (orgCacheVO.isLeaf()) {
                sql.append(" and record.mdcode='").append(orgCacheVO.getCode()).append("'");
            } else {
                Set<String> orgCodes2 = InputDataCheckSQLUtils.orgTreeToSet(CollectionUtils.newArrayList((Object[])new GcOrgCacheVO[]{inputDataCheckCondition.getUnitCode()}), tool);
                if (!CollectionUtils.isEmpty(orgCodes2)) {
                    sql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(orgCodes2, (String)"record.mdcode"));
                }
            }
            if (!CollectionUtils.isEmpty((Collection)inputDataCheckCondition.getOppUnitId()) && !CollectionUtils.isEmpty(orgCodes = InputDataCheckSQLUtils.orgTreeToSet(inputDataCheckCondition.getOppUnitId(), tool))) {
                sql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(orgCodes, (String)"record.OPPUNITID"));
            }
        }
    }

    private static void getOtherWhereSql(StringBuffer sql, InputDataCheckCondition inputDataCheckCondition, List<Object> params, InputDataCheckStateEnum inputDataCheckStateEnum, String systemId) {
        sql.append(" and record.reportSystemId = '").append(systemId).append("' \n");
        sql.append(" and record.").append("DATATIME").append("=? \n");
        sql.append(" and record.").append("MD_CURRENCY").append("=? \n");
        sql.append(" and coalesce(record.uncheckamt,0) != 0 \n");
        params.add(inputDataCheckCondition.getDataTime());
        params.add(inputDataCheckCondition.getCurrencyCode());
        if (null != inputDataCheckStateEnum) {
            if (InputDataCheckStateEnum.NOTCHECK.equals((Object)inputDataCheckStateEnum)) {
                sql.append(" and (record.CHECKSTATE").append("=? )\n");
            } else {
                sql.append(" and record.CHECKSTATE").append("=? \n");
            }
            params.add(inputDataCheckStateEnum.getValue());
        }
        if (DimensionUtils.isExistAdjust((String)inputDataCheckCondition.getTaskId())) {
            sql.append(" and record.").append("ADJUST").append("=? \n");
            params.add(inputDataCheckCondition.getSelectAdjustCode());
        }
        if (!CollectionUtils.isEmpty((Collection)inputDataCheckCondition.getCheckRule())) {
            InputDataCheckSQLUtils.addRuleWhereSql(sql, inputDataCheckCondition.getCheckRule());
        } else {
            ArrayList<String> ruleTypes = new ArrayList<String>();
            ruleTypes.add(RuleTypeEnum.FLEXIBLE.getCode());
            List unionRules = ((UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class)).selectRuleListByReportSystemAndRuleTypes(systemId, ruleTypes);
            if (!CollectionUtils.isEmpty((Collection)unionRules)) {
                List<String> ruleIds = unionRules.stream().filter(unionRule -> {
                    FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)unionRule;
                    return flexibleRuleDTO.getCheckOffsetFlag();
                }).map(AbstractUnionRule::getId).collect(Collectors.toList());
                InputDataCheckSQLUtils.addRuleWhereSql(sql, ruleIds);
            } else {
                sql.append(" and 1=2 ");
            }
        }
    }

    private static void addRuleWhereSql(StringBuffer whereSql, List<String> checkRuleIds) {
        if (CollectionUtils.isEmpty(checkRuleIds)) {
            whereSql.append(" and 1=2 ");
            return;
        }
        Set<String> ruleIds = InputDataCheckSQLUtils.getUnionRuleChildrenIds(checkRuleIds);
        if (CollectionUtils.isEmpty(ruleIds)) {
            whereSql.append(" and 1=2 ");
            return;
        }
        whereSql.append(" AND ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.UNIONRULEID"));
    }

    private static Set<String> getUnionRuleChildrenIds(List<String> checkRuleIds) {
        HashSet<String> ruleIds = new HashSet<String>();
        UnionRuleService unionRuleService = (UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class);
        for (String ruleId : checkRuleIds) {
            AbstractUnionRule unionRule;
            if (StringUtils.isEmpty((String)ruleId) || "empty".equals(ruleId) || ruleIds.contains(ruleId) || Objects.isNull(unionRule = unionRuleService.selectUnionRuleDTOById(ruleId))) continue;
            FlexibleRuleDTO flexibleRule = (FlexibleRuleDTO)unionRule;
            if (flexibleRule.getCheckOffsetFlag().booleanValue()) {
                ruleIds.add(ruleId);
            }
            List rules = unionRuleService.selectAllChildrenRuleEo(ruleId);
            for (UnionRuleEO rule : rules) {
                FlexibleRuleDTO flexibleRuleChildrens = (FlexibleRuleDTO)UnionRuleConverter.convert((UnionRuleEO)rule);
                if (!flexibleRuleChildrens.getCheckOffsetFlag().booleanValue()) continue;
                ruleIds.add(rule.getId());
            }
        }
        return ruleIds;
    }

    private static Set<String> orgTreeToSet(List<GcOrgCacheVO> orgCodes, GcOrgCenterService tool) {
        if (CollectionUtils.isEmpty(orgCodes)) {
            return Collections.emptySet();
        }
        HashSet<String> orgIds = new HashSet<String>();
        for (GcOrgCacheVO orgCache : orgCodes) {
            GcOrgCacheVO cacheVO;
            if (Objects.isNull(orgCache) || Objects.isNull(cacheVO = tool.getOrgByCode(orgCache.getCode()))) continue;
            if (cacheVO.isLeaf()) {
                orgIds.add(cacheVO.getCode());
                continue;
            }
            List childrenOrg = tool.listAllOrgByParentIdContainsSelf(cacheVO.getCode());
            if (CollectionUtils.isEmpty((Collection)childrenOrg)) continue;
            orgIds.addAll(childrenOrg.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet()));
        }
        return orgIds;
    }

    public static void initOtherCondition(StringBuilder whereSql, Map<String, Object> filterCondition, String systemId, boolean isCheckDiffAmtFlag) {
        if (!filterCondition.isEmpty()) {
            for (String key : filterCondition.keySet()) {
                Object tempValue;
                if (StringUtils.isEmpty((String)key) || Objects.isNull(tempValue = filterCondition.get(key))) continue;
                if (tempValue instanceof List) {
                    List valueList;
                    List values;
                    Object biggerValue;
                    Object biggerValue2;
                    Object smallerValue;
                    List doubleValues;
                    List valueObj = (List)tempValue;
                    if (CollectionUtils.isEmpty((Collection)valueObj)) continue;
                    if ("subjectCodes".equals(key)) {
                        if (StringUtils.isEmpty((String)systemId)) continue;
                        HashSet subjectCodes = new HashSet();
                        List selectedSubjects = (List)tempValue;
                        List allSubjectEos = ((ConsolidatedSubjectClient)SpringContextUtils.getBean(ConsolidatedSubjectClient.class)).listSubjectsBySystemIdNoSortOrder(systemId);
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
                    if (key.contains("debitAmtInterval") || key.contains("creditAmtInterval")) {
                        doubleValues = (List)tempValue;
                        if (CollectionUtils.isEmpty((Collection)doubleValues)) continue;
                        smallerValue = doubleValues.get(0);
                        boolean isValueFlag = false;
                        if (smallerValue instanceof Number) {
                            isValueFlag = true;
                            whereSql.append(" and coalesce(record.uncheckamt,0)>=").append(smallerValue);
                        }
                        if (doubleValues.size() > 1 && (biggerValue2 = doubleValues.get(1)) instanceof Number) {
                            isValueFlag = true;
                            whereSql.append(" and coalesce(record.UNCHECKAMT,0)<=").append(biggerValue2);
                        }
                        if (!isValueFlag) continue;
                        if ("debitAmtInterval".equals(key)) {
                            whereSql.append(" and record.DC=1 \n");
                            continue;
                        }
                        if (!"creditAmtInterval".equals(key)) continue;
                        whereSql.append(" and record.DC=-1 \n");
                        continue;
                    }
                    if ("checkAmtInterval".equals(key)) {
                        doubleValues = (List)tempValue;
                        if (CollectionUtils.isEmpty((Collection)doubleValues)) continue;
                        smallerValue = doubleValues.get(0);
                        if (Objects.nonNull(smallerValue) && smallerValue instanceof Number) {
                            whereSql.append(" and coalesce(record.CHECKAMT,0)>=").append(smallerValue);
                        }
                        if (doubleValues.size() <= 1 || !Objects.nonNull(biggerValue = doubleValues.get(1)) || !(biggerValue instanceof Number)) continue;
                        whereSql.append(" and coalesce(record.CHECKAMT,0)<=").append(biggerValue);
                        continue;
                    }
                    if ("checkDiffAmtInterval".equals(key)) {
                        if (!isCheckDiffAmtFlag || CollectionUtils.isEmpty((Collection)(doubleValues = (List)tempValue))) continue;
                        smallerValue = doubleValues.get(0);
                        if (Objects.nonNull(smallerValue) && smallerValue instanceof Number) {
                            whereSql.append(" and coalesce(record.UNCHECKAMT,0)-coalesce(record.CHECKAMT,0)>=").append(smallerValue);
                        }
                        if (doubleValues.size() <= 1 || !Objects.nonNull(biggerValue = doubleValues.get(1)) || !(biggerValue instanceof Number)) continue;
                        whereSql.append(" and coalesce(record.UNCHECKAMT,0)-coalesce(record.CHECKAMT,0)<=").append(biggerValue);
                        continue;
                    }
                    if (!(tempValue instanceof List) || CollectionUtils.isEmpty(values = (valueList = (List)tempValue).stream().map(item -> {
                        if (item instanceof Map) {
                            Map mapItem = (Map)item;
                            Object codeValue = mapItem.get("code");
                            return codeValue != null ? codeValue.toString() : "";
                        }
                        return ConverterUtils.getAsString((Object)item);
                    }).collect(Collectors.toList()))) continue;
                    if (key.endsWith("_number")) {
                        key = key.replace("_number", "");
                        if (CollectionUtils.isEmpty(values)) continue;
                        String smallerValue2 = (String)values.get(0);
                        if (!StringUtils.isEmpty((String)smallerValue2) && smallerValue2.matches("-?\\d+\\.?\\d*")) {
                            whereSql.append(" and record.").append(key).append(">=").append(smallerValue2);
                        }
                        if (values.size() < 2 || StringUtils.isEmpty(biggerValue2 = (String)values.get(1)) || !((String)biggerValue2).matches("-?\\d+\\.?\\d*")) continue;
                        whereSql.append(" and record.").append(key).append("<=").append((String)biggerValue2);
                        continue;
                    }
                    if (!Objects.nonNull(values) || values.size() <= 0) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(values, (String)("record." + key)));
                    continue;
                }
                String strValue = String.valueOf(tempValue);
                if (StringUtils.isEmpty((String)strValue) || !strValue.matches("[^'\\s]+")) continue;
                if ("OnlyQueryEmpty".equals(strValue)) {
                    whereSql.append(" and (record.").append(key).append(" is null ").append(" or record.").append(key).append(" ='' )");
                    continue;
                }
                if ("memo".equals(key)) {
                    whereSql.append(" and record.").append(key).append(" like '%").append(strValue).append("%'");
                    continue;
                }
                String[] valueArr = strValue.split(",");
                if (valueArr.length <= 0) continue;
                whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)CollectionUtils.newArrayList((Object[])strValue.split(",")), (String)("record." + key)));
            }
        }
    }
}

