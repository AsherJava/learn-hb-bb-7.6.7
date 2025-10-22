/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.log.BeanUtils
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.log.BeanUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RuleSummaryQueryAction
implements GcOffSetItemAction {
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    private final Map<String, String> ruleId2ParentRule = new ConcurrentHashMap<String, String>();
    private static final Logger logger = LoggerFactory.getLogger(RuleSummaryQueryAction.class);

    public String code() {
        return "query";
    }

    public String title() {
        return "\u6309\u89c4\u5219\u6c47\u603b\u5c55\u793a";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        Pagination<Map<String, Object>> pagination;
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        try {
            pagination = this.queryUnOffsetRuleRecords(queryParamsVO);
        }
        finally {
            if (!CollectionUtils.isEmpty(queryParamsVO.getTempGroupIdList())) {
                IdTemporaryTableUtils.deteteByGroupIds((Collection)queryParamsVO.getTempGroupIdList());
            }
        }
        this.handleSpanMethod(pagination, queryParamsVO);
        this.handleGroupByUnitIdOrRuleId(pagination, queryParamsVO);
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.gcOffsetAppInputDataService.setTitles(pagination, queryParamsVO, systemId);
        return pagination;
    }

    private Pagination<Map<String, Object>> queryUnOffsetRuleRecords(QueryParamsVO queryParamsVO) {
        List rs;
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        ArrayList datas = new ArrayList();
        page.setContent(datas);
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<Object> params = new ArrayList<Object>();
        if (queryParamsVO.getUnitIdList() != null && queryParamsVO.getUnitIdList().size() > 0 || queryParamsVO.getOppUnitIdList() != null && queryParamsVO.getOppUnitIdList().size() > 0) {
            String sql = this.getQueryUnOffsetRuleRecordsSql(queryParamsVO, params, false, null);
            if (StringUtils.isEmpty((String)sql)) {
                return page;
            }
            rs = dao.selectMap(sql, params);
        } else {
            int firstResult = (pageNum - 1) * pageSize;
            String sql = this.getQueryUnOffsetRuleRecordsSql(queryParamsVO, params, true, null);
            if (StringUtils.isEmpty((String)sql)) {
                return page;
            }
            int count = dao.count(sql, params);
            if (count < 1) {
                return page;
            }
            page.setTotalElements(Integer.valueOf(count));
            rs = pageNum == -1 || pageSize == -1 ? dao.selectMap(sql, params) : dao.selectMapByPaging(sql, firstResult, pageNum * pageSize, params);
            HashSet<String> unionRuleIds = new HashSet<String>();
            for (Map d : rs) {
                unionRuleIds.add(String.valueOf(d.get("UNIONRULEID")));
            }
            params = new ArrayList();
            sql = this.getQueryUnOffsetRuleRecordsSql(queryParamsVO, params, false, unionRuleIds);
            rs = dao.selectMap(sql, params);
            page.setPageSize(Integer.valueOf(pageSize));
            page.setCurrentPage(Integer.valueOf(pageNum));
        }
        if (rs != null && !rs.isEmpty()) {
            rs.forEach(row -> datas.add(this.gcOffsetAppInputDataService.getObject((Map<String, Object>)row)));
        }
        page.setContent(datas);
        return page;
    }

    private String getQueryUnOffsetRuleRecordsSql(QueryParamsVO queryParamsVO, List<Object> params, Boolean isQueryCount, Set<String> unionRuleIds) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        int codeLength = tool.getOrgCodeLength();
        StringBuffer whereSql = new StringBuffer(64);
        Date date = yp.formatYP().getEndDate();
        if (!StringUtils.isEmpty((String)queryParamsVO.getOrgId())) {
            GcOrgCacheVO orgCacheVO = tool.getOrgByCode(queryParamsVO.getOrgId());
            if (null == orgCacheVO || orgCacheVO.getParentStr() == null) {
                return "";
            }
            String parentGuids = orgCacheVO.getParentStr();
            String gcParentsStr = orgCacheVO.getGcParentStr();
            this.gcOffsetAppInputDataService.initMergeUnitCondition(whereSql, params, parentGuids, gcParentsStr, date, codeLength);
        } else {
            if (CollectionUtils.isEmpty(queryParamsVO.getUnitIdList())) {
                return "";
            }
            this.gcOffsetAppInputDataService.initValidtimeCondition(whereSql, params, date);
        }
        String orgTable = tool.getCurrOrgType().getTable();
        StringBuffer selectFields = new StringBuffer(32);
        if (isQueryCount.booleanValue()) {
            selectFields.append(" record.UNIONRULEID,gcunionrule.sortorder as gcsortorder,unionrule.sortorder ");
        } else {
            selectFields = this.gcOffsetAppInputDataService.selectFields(queryParamsVO);
        }
        whereSql.append(" and record.UNIONRULEID is not null ");
        whereSql.append(" and record.SUBJECTCODE is not null ");
        whereSql.append(" and unionrule.ruletype ='").append(RuleTypeEnum.FLEXIBLE).append("' ");
        this.gcOffsetAppInputDataService.initUnitCondition(queryParamsVO, whereSql, tool);
        this.gcOffsetAppInputDataService.initPeriodCondition(queryParamsVO, params, whereSql);
        this.gcOffsetAppInputDataService.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append(tableName).append("  record\n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.%1$s = bfUnitTable.code)\n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        sql.append("left join ").append("GC_UNIONRULE").append(" unionrule on (unionrule.id = record.UNIONRULEID)\n");
        sql.append("left join ").append("GC_UNIONRULE").append(" gcunionrule on (unionrule.parentid = gcunionrule.id)\n");
        sql.append("where ");
        sql.append(whereSql);
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            sql.append(" and record.ADJUST = ").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        if (!isQueryCount.booleanValue() && unionRuleIds != null && unionRuleIds.size() > 0) {
            sql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(unionRuleIds, (String)" record.UNIONRULEID")).append("\n");
        }
        sql.append("and record.offsetState='0'\n");
        if (isQueryCount.booleanValue()) {
            sql.append("group by gcunionrule.sortorder,unionrule.sortorder,record.UNIONRULEID\n");
            sql.append("order by gcunionrule.sortorder,unionrule.sortorder,record.UNIONRULEID\n");
        } else if (queryParamsVO.getUnitIdList() != null && queryParamsVO.getUnitIdList().size() > 0 || queryParamsVO.getOppUnitIdList() != null && queryParamsVO.getOppUnitIdList().size() > 0) {
            sql.append("order by gcunionrule.sortorder,unionrule.sortorder,record.unionRuleId,record.%1$s,record.oppunitid,record.DC desc,record.SUBJECTCODE asc\n");
        } else {
            sql.append("order by gcunionrule.sortorder,unionrule.sortorder,record.unionRuleId,record.DC desc,record.SUBJECTCODE,(CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END) asc\n");
        }
        return String.format(sql.toString(), "MDCODE");
    }

    public void handleSpanMethod(Pagination<Map<String, Object>> pagination, QueryParamsVO queryParamsVO) {
        try {
            List recordListMap = pagination.getContent();
            if (queryParamsVO.getUnitIdList() != null && queryParamsVO.getUnitIdList().size() > 0 || queryParamsVO.getOppUnitIdList() != null && queryParamsVO.getOppUnitIdList().size() > 0) {
                this.handleUnitAndRuleSpanMethod(recordListMap, pagination);
            } else {
                pagination.setContent(this.handleRuleSpanMethod(recordListMap));
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u89c4\u5219\u6c47\u603b\u6570\u636e\u5931\u8d25:", e);
            String message = String.format("\u83b7\u53d6\u89c4\u5219\u6c47\u603b\u6570\u636e\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:%s", e.getMessage());
            ArrayList datas = new ArrayList();
            pagination.setContent(datas);
            throw new BusinessRuntimeException(message);
        }
    }

    public void handleGroupByUnitIdOrRuleId(Pagination<Map<String, Object>> pagination, QueryParamsVO queryParamsVO) {
        if (queryParamsVO.getUnitIdList() != null && queryParamsVO.getUnitIdList().size() > 0 || queryParamsVO.getOppUnitIdList() != null && queryParamsVO.getOppUnitIdList().size() > 0) {
            this.handleGroupByUnitIdAndRuleId(pagination, queryParamsVO);
        } else {
            this.handleGroupByRuleId(pagination);
        }
    }

    private void handleUnitAndRuleSpanMethod(List<Map<String, Object>> recordListMap, Pagination<Map<String, Object>> pagination) {
        if (recordListMap.size() > 1) {
            this.handleUnitAndRuleSpanMethodProcess(recordListMap, pagination);
        } else if (recordListMap.size() == 1) {
            ArrayList<HashMap<String, Object>> recordListMapAfter = new ArrayList<HashMap<String, Object>>();
            recordListMapAfter.add(new HashMap<String, Object>(recordListMap.get(0)));
            ((Map)recordListMapAfter.get(0)).put("rowSpanRule", 1);
            ((Map)recordListMapAfter.get(0)).put("rowSpanUnit", 1);
            if (recordListMap.get(0).get("ID") != null) {
                ((Map)recordListMapAfter.get(0)).put("recordIds", new StringBuilder(recordListMap.get(0).get("ID").toString()).append(","));
            }
            this.handleRuleMoneyValue((Map)recordListMapAfter.get(0), recordListMap.get(0));
            if (pagination != null) {
                pagination.setContent(recordListMapAfter);
                pagination.setTotalElements(Integer.valueOf(1));
            }
        }
    }

    private BigDecimal setAmountValue(List<Map<String, Object>> recordList, String mapAmountKey) {
        BigDecimal amountValue = recordList.stream().map(recordMap -> {
            Object value = recordMap.get(mapAmountKey);
            if (value != null) {
                return new BigDecimal(NumberUtils.tryParseNumber((String)value.toString(), null).toString());
            }
            return BigDecimal.ZERO;
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (amountValue.compareTo(new BigDecimal(0)) != 0) {
            recordList.get(0).put(mapAmountKey, NumberUtils.format((Number)amountValue));
            return amountValue;
        }
        recordList.get(0).put(mapAmountKey, null);
        return null;
    }

    private BigDecimal caleDifferenceValue(BigDecimal debitSum, BigDecimal creditSum, BigDecimal differenceValue) {
        if (debitSum != null) {
            differenceValue = differenceValue.add(debitSum);
        }
        if (creditSum != null) {
            differenceValue = differenceValue.subtract(creditSum);
        }
        return differenceValue;
    }

    private List<Map<String, Object>> handleRuleSpanMethodProcess(List<Map<String, Object>> recordListMap) {
        ArrayList<Map<String, Object>> recordListMapAfter = new ArrayList<Map<String, Object>>();
        Map unitId2recordList = recordListMap.stream().collect(Collectors.groupingBy(record -> {
            String ruleId = record.get("UNIONRULEID") != null ? (String)record.get("UNIONRULEID") : (String)record.get("RULEID");
            return ruleId;
        }, LinkedHashMap::new, Collectors.toList()));
        unitId2recordList.forEach((unitId, recordListMapValue) -> {
            Map<String, List<Map>> subjectCode2recordList = recordListMapValue.stream().collect(Collectors.groupingBy(record -> record.get("SUBJECTCODE").toString()));
            int firstRuleNum = recordListMapAfter.size();
            BigDecimal[] differenceValue = new BigDecimal[]{new BigDecimal(0)};
            subjectCode2recordList.forEach((subjectCode, recordList) -> {
                BigDecimal debitSum = this.setAmountValue((List<Map<String, Object>>)recordList, "DEBITVALUE");
                BigDecimal creditSum = this.setAmountValue((List<Map<String, Object>>)recordList, "CREDITVALUE");
                if (debitSum == null && creditSum == null) {
                    ((Map)recordList.get(0)).put("DEBITVALUE", "0.00");
                }
                differenceValue[0] = this.caleDifferenceValue(debitSum, creditSum, differenceValue[0]);
                recordListMapAfter.add((Map<String, Object>)recordList.get(0));
                this.handleRecordIdList((Map)recordListMapAfter.get(firstRuleNum), (List<Map<String, Object>>)recordList);
            });
            ((Map)recordListMapAfter.get(firstRuleNum)).put("rowSpanRule", recordListMapAfter.size() - firstRuleNum);
            ((Map)recordListMapAfter.get(recordListMapAfter.size() - 1)).put("ruleEnd", true);
            ((Map)recordListMapAfter.get(firstRuleNum)).put("DIFFERENCEVALUE", NumberUtils.format((Number)differenceValue[0]));
        });
        return recordListMapAfter;
    }

    private void handleGroupByUnitIdAndRuleId(Pagination<Map<String, Object>> pagination, QueryParamsVO queryParamsVO) {
        int startNum = (queryParamsVO.getPageNum() - 1) * queryParamsVO.getPageSize();
        int endNum = queryParamsVO.getPageNum() * queryParamsVO.getPageSize();
        int count = 0;
        List contentListMap = pagination.getContent();
        ArrayList<Map> contentListMapAfter = new ArrayList<Map>();
        for (Map contentMap : contentListMap) {
            if (count >= startNum && count < endNum || queryParamsVO.getPageSize() == -1 && queryParamsVO.getPageNum() == -1) {
                contentListMapAfter.add(contentMap);
            }
            if (!contentMap.containsKey("unitEnd")) continue;
            ++count;
        }
        pagination.setContent(contentListMapAfter);
        pagination.setTotalElements(Integer.valueOf(count));
    }

    private void handleUnitAndRuleSpanMethodProcess(List<Map<String, Object>> recordListMap, Pagination<Map<String, Object>> pagination) {
        HashMap<String, List<Map<String, Object>>> recordListMapAfter = new HashMap<String, List<Map<String, Object>>>();
        this.ruleId2ParentRule.clear();
        for (int index = 0; index < recordListMap.size(); ++index) {
            if (recordListMap.get(index).get("UNITISHANDLE") != null) continue;
            int finalIndex = index;
            List<Map<String, Object>> unitEquals = recordListMap.stream().filter(record -> {
                if (((Map)recordListMap.get(finalIndex)).get("ISHANDLE") == null && ((Map)recordListMap.get(finalIndex)).get("OPPUNITID").equals(record.get("OPPUNITID")) && ((Map)recordListMap.get(finalIndex)).get("UNITID").equals(record.get("UNITID"))) {
                    record.put("UNITISHANDLE", true);
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            List<Map<String, Object>> oppUnitEquals = recordListMap.stream().filter(record -> {
                if (((Map)recordListMap.get(finalIndex)).get("ISHANDLE") == null && ((Map)recordListMap.get(finalIndex)).get("OPPUNITID").equals(record.get("UNITID")) && ((Map)recordListMap.get(finalIndex)).get("UNITID").equals(record.get("OPPUNITID"))) {
                    record.put("UNITISHANDLE", true);
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            try {
                List<Map<String, Object>> unitList = this.handleRuleSpanMethodProcess(unitEquals);
                Map<String, List<Map<String, Object>>> parentRule2recordList = this.handleMergeCell(unitList);
                List<Map<String, Object>> oppUnitList = this.handleRuleSpanMethodProcess(oppUnitEquals);
                Map<String, List<Map<String, Object>>> parentRule2oppRecordList = this.handleMergeCell(oppUnitList);
                this.handleUnitRecordIdListMap(parentRule2recordList, parentRule2oppRecordList, recordListMapAfter);
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayList recordListAfter = new ArrayList();
        recordListMapAfter.forEach((parentRuleTitle, collectList) -> {
            recordListAfter.add(this.getEvenMap((String)parentRuleTitle));
            recordListAfter.addAll(collectList);
        });
        pagination.setContent(recordListAfter);
    }

    private String handleRuleMoneyValue(Map<String, Object> recordMapAfter, Map<String, Object> recordMap) {
        BigDecimal differenceValue = new BigDecimal(0);
        if (recordMapAfter.get("DIFFERENCEVALUE") != null) {
            differenceValue = new BigDecimal(NumberUtils.tryParseNumber((String)recordMapAfter.get("DIFFERENCEVALUE").toString(), null).toString());
        }
        if (recordMap.get("DEBITVALUE") != null) {
            differenceValue = differenceValue.add(new BigDecimal(NumberUtils.tryParseNumber((String)recordMap.get("DEBITVALUE").toString(), null).toString()));
        }
        if (recordMap.get("CREDITVALUE") != null) {
            differenceValue = differenceValue.subtract(new BigDecimal(NumberUtils.tryParseNumber((String)recordMap.get("CREDITVALUE").toString(), null).toString()));
        }
        return NumberUtils.format((Number)differenceValue);
    }

    private Map<String, List<Map<String, Object>>> handleMergeCell(List<Map<String, Object>> unitListMap) {
        HashMap<String, List<Map<String, Object>>> parentRule2recordList = new HashMap<String, List<Map<String, Object>>>();
        Map<String, List<Map<String, Object>>> collect = this.getIdenticalRuleRecordMap(unitListMap);
        collect.forEach((parentRuleTitle, collectList) -> {
            StringBuilder recordIds = new StringBuilder();
            for (Map recordMap : collectList) {
                if (recordMap.get("recordIds") == null) continue;
                recordIds.append(recordMap.get("recordIds"));
                recordMap.remove("recordIds");
            }
            ((Map)collectList.get(0)).put("rowSpanUnit", collectList.size());
            ((Map)collectList.get(0)).put("recordIds", recordIds.toString());
            ArrayList recordListMapAfter = new ArrayList(collectList);
            List recordList = parentRule2recordList.getOrDefault(parentRuleTitle, new ArrayList());
            recordList.addAll(recordListMapAfter);
            parentRule2recordList.put((String)parentRuleTitle, recordList);
        });
        return parentRule2recordList;
    }

    private Map<String, List<Map<String, Object>>> getIdenticalRuleRecordMap(List<Map<String, Object>> unitListMap) {
        if (unitListMap.size() == 0) {
            return new HashMap<String, List<Map<String, Object>>>();
        }
        for (Map<String, Object> recordMap : unitListMap) {
            recordMap.put("parentRuleTitle", this.getAllParentStrRuleById((String)recordMap.get("UNIONRULEID")));
        }
        return unitListMap.stream().collect(Collectors.groupingBy(map -> map.get("parentRuleTitle").toString()));
    }

    private void handleUnitRecordIdListMap(Map<String, List<Map<String, Object>>> parentRule2recordList, Map<String, List<Map<String, Object>>> parentRule2oppRecordList, Map<String, List<Map<String, Object>>> recordListMap) {
        Set<String> parentRuleSet = parentRule2recordList.keySet();
        ArrayList<String> parentRuleList = new ArrayList<String>(parentRuleSet);
        parentRuleList.addAll(new ArrayList<String>(parentRule2oppRecordList.keySet()));
        parentRuleSet = new HashSet<String>(parentRuleList);
        parentRuleSet.forEach(parentRule -> {
            List recordList = parentRule2recordList.getOrDefault(parentRule, new ArrayList());
            recordList.addAll(parentRule2oppRecordList.getOrDefault(parentRule, new ArrayList()));
            BigDecimal debitTotal = new BigDecimal(0);
            BigDecimal creditTotal = new BigDecimal(0);
            for (Map recordMap : recordList) {
                if (recordMap.get("DEBITVALUE") != null) {
                    debitTotal = debitTotal.add(new BigDecimal(NumberUtils.tryParseNumber((String)recordMap.get("DEBITVALUE").toString(), null).toString()));
                }
                if (recordMap.get("CREDITVALUE") == null) continue;
                creditTotal = creditTotal.add(new BigDecimal(NumberUtils.tryParseNumber((String)recordMap.get("CREDITVALUE").toString(), null).toString()));
            }
            ((Map)recordList.get(0)).put("DEBITTOTAL", NumberUtils.format((Number)debitTotal));
            ((Map)recordList.get(0)).put("CREDITTOTAL", NumberUtils.format((Number)creditTotal));
            ((Map)recordList.get(0)).put("DIFFERENCEVALUE", NumberUtils.format((Number)debitTotal.subtract(creditTotal)));
            ((Map)recordList.get(0)).put("unitStart", true);
            ((Map)recordList.get(recordList.size() - 1)).put("unitEnd", true);
            if (recordListMap.containsKey(parentRule)) {
                recordList.addAll((Collection)recordListMap.get(parentRule));
            }
            recordListMap.put((String)parentRule, recordList);
        });
    }

    private void handleGroupByRuleId(Pagination<Map<String, Object>> pagination) {
        List contentListMap = pagination.getContent();
        ArrayList<Map> contentListMapAfter = new ArrayList<Map>();
        String ruleTitle = "";
        int count = 0;
        BigDecimal debitTotal = new BigDecimal(0);
        BigDecimal creditTotal = new BigDecimal(0);
        for (Map stringObjectMap : contentListMap) {
            List<String> parentRuleTitle = this.getAllParentListRuleById((String)stringObjectMap.get("UNIONRULEID"));
            if (!parentRuleTitle.toString().equals(ruleTitle)) {
                ruleTitle = parentRuleTitle.toString();
                contentListMapAfter.add(this.getEvenMap(parentRuleTitle));
            }
            contentListMapAfter.add(stringObjectMap);
            if (stringObjectMap.get("rowSpanRule") != null) {
                count = contentListMapAfter.size() - 1;
                debitTotal = new BigDecimal(0);
                creditTotal = new BigDecimal(0);
            }
            if (stringObjectMap.get("DEBITVALUE") != null) {
                debitTotal = debitTotal.add(new BigDecimal(NumberUtils.tryParseNumber((String)stringObjectMap.get("DEBITVALUE").toString(), null).toString()));
                ((Map)contentListMapAfter.get(count)).put("DEBITTOTAL", NumberUtils.format((Number)debitTotal));
            }
            if (stringObjectMap.get("CREDITVALUE") == null) continue;
            creditTotal = creditTotal.add(new BigDecimal(NumberUtils.tryParseNumber((String)stringObjectMap.get("CREDITVALUE").toString(), null).toString()));
            ((Map)contentListMapAfter.get(count)).put("CREDITTOTAL", NumberUtils.format((Number)creditTotal));
        }
        pagination.setContent(contentListMapAfter);
    }

    private Map<String, Object> getEvenMap(List<String> parentRuleTitle) {
        HashMap<String, Object> allParentRuleMap = new HashMap<String, Object>();
        Collections.reverse(parentRuleTitle);
        allParentRuleMap.put("UNIONRULETITLE", String.join((CharSequence)"-", parentRuleTitle));
        allParentRuleMap.put("even", true);
        return allParentRuleMap;
    }

    private Map<String, Object> getEvenMap(String parentRuleTitle) {
        HashMap<String, Object> allParentRuleMap = new HashMap<String, Object>();
        allParentRuleMap.put("UNIONRULETITLE", parentRuleTitle);
        allParentRuleMap.put("even", true);
        return allParentRuleMap;
    }

    public List<Map<String, Object>> handleRuleSpanMethod(List<Map<String, Object>> recordListMap) {
        List<Object> recordListMapAfter = new ArrayList();
        if (recordListMap.size() > 1) {
            recordListMapAfter = this.handleRuleSpanMethodProcess(recordListMap);
        } else if (recordListMap.size() == 1) {
            recordListMapAfter = new ArrayList();
            recordListMapAfter.add(new HashMap<String, Object>(recordListMap.get(0)));
            ((Map)recordListMapAfter.get(0)).put("rowSpanRule", 1);
            if (recordListMap.get(0).get("ID") != null) {
                ((Map)recordListMapAfter.get(0)).put("recordIds", new StringBuilder(recordListMap.get(0).get("ID").toString()).append(","));
            }
            ((Map)recordListMapAfter.get(0)).put("DIFFERENCEVALUE", this.handleRuleMoneyValue((Map)recordListMapAfter.get(0), recordListMap.get(0)));
        }
        return recordListMapAfter;
    }

    private String getAllParentStrRuleById(String unionRuleId) {
        if (!this.ruleId2ParentRule.containsKey(unionRuleId)) {
            List<String> parentRuleTitle = this.getAllParentListRuleById(unionRuleId);
            Collections.reverse(parentRuleTitle);
            this.ruleId2ParentRule.put(unionRuleId, String.join((CharSequence)"-", parentRuleTitle));
        }
        return this.ruleId2ParentRule.get(unionRuleId);
    }

    private List<String> getAllParentListRuleById(String unionRuleId) {
        ArrayList<String> ruleList = new ArrayList<String>();
        UnionRuleVO rule = ((UnionRuleService)BeanUtils.getBean(UnionRuleService.class)).selectUnionRuleById(unionRuleId);
        if (rule != null) {
            this.getParentRuleById(rule.getParentId(), ruleList);
        }
        return ruleList;
    }

    private void getParentRuleById(String parentRuleId, List<String> ruleList) {
        UnionRuleVO parentRule = ((UnionRuleService)BeanUtils.getBean(UnionRuleService.class)).selectUnionRuleById(parentRuleId);
        if (parentRule != null && !parentRule.getRuleType().equals("root")) {
            ruleList.add(parentRule.getTitle());
            this.getParentRuleById(parentRule.getParentId(), ruleList);
        }
    }

    private void handleRecordIdList(Map<String, Object> recordListMapAfter, List<Map<String, Object>> recordMapList) {
        AtomicReference<StringBuilder> recordIds = new AtomicReference<StringBuilder>();
        if (recordListMapAfter.get("recordIds") != null) {
            recordIds.set(new StringBuilder(recordListMapAfter.get("recordIds").toString()));
        } else {
            recordIds.set(new StringBuilder());
        }
        recordMapList.forEach(record -> {
            if (record.get("ID") == null) {
                return;
            }
            ((StringBuilder)recordIds.get()).append(record.get("ID").toString()).append(",");
        });
        recordListMapAfter.put("recordIds", recordIds);
    }
}

