/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils
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
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffsetparent;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset.RuleSummaryQueryAction;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
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
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RuleSummaryParentQueryAction
implements GcOffSetItemAction {
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private RuleSummaryQueryAction ruleSummaryQueryAction;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private ConsolidatedSubjectUIService subjectUIService;
    @Autowired
    private UnionRuleService unionRuleService;

    public String code() {
        return "query";
    }

    public String title() {
        return "\u67e5\u8be2";
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        GcOffsetItemUtils.logOffsetEntryFilterCondition((QueryParamsVO)queryParamsVO, (String)"\u4e0a\u7ea7\u672a\u62b5\u9500");
        if (org.springframework.util.StringUtils.isEmpty(queryParamsVO.getOrgId())) {
            return this.ruleSummaryQueryAction.execute(gcOffsetExecutorVO);
        }
        Pagination<Map<String, Object>> pagination = this.queryUnOffsetRuleRecords(queryParamsVO);
        if (CollectionUtils.isEmpty(pagination.getContent())) {
            return pagination;
        }
        this.ruleSummaryQueryAction.handleSpanMethod(pagination, queryParamsVO);
        this.ruleSummaryQueryAction.handleGroupByUnitIdOrRuleId(pagination, queryParamsVO);
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.gcOffsetAppInputDataService.setTitles(pagination, queryParamsVO, systemId);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u4e0a\u7ea7\u672a\u62b5\u9500\u67e5\u770b-\u4efb\u52a1-" + taskDefine.getTitle() + "-\u65f6\u671f-" + queryParamsVO.getAcctYear() + "\u5e74" + queryParamsVO.getAcctPeriod() + "\u6708"));
        return pagination;
    }

    private Pagination<Map<String, Object>> queryUnOffsetRuleRecords(QueryParamsVO queryParamsVO) {
        List rs;
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        ArrayList datas = new ArrayList();
        page.setContent(datas);
        ArrayList<Object> params = new ArrayList<Object>();
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
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
            rs.forEach(row -> datas.add(this.getObject((Map<String, Object>)row)));
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
            if (gcParentsStr.length() < codeLength + 1) {
                return "";
            }
            this.initParentMergeUnitCondition(whereSql, params, parentGuids, date, queryParamsVO.getOrgType());
        } else {
            if (CollectionUtils.isEmpty(queryParamsVO.getUnitIdList())) {
                return "";
            }
            this.initValidtimeCondition(whereSql, params, date);
        }
        String orgTable = tool.getCurrOrgType().getTable();
        StringBuffer selectFields = new StringBuffer(32);
        if (isQueryCount.booleanValue()) {
            selectFields.append(" record.UNIONRULEID,gcunionrule.sortorder as gcsortorder,unionrule.sortorder ");
        } else {
            selectFields = this.selectFields(queryParamsVO);
        }
        whereSql.append(" and record.UNIONRULEID is not null ");
        whereSql.append(" and record.SUBJECTCODE is not null ");
        whereSql.append(" and unionrule.ruletype ='").append(RuleTypeEnum.FLEXIBLE).append("' ");
        this.initUnitCondition(queryParamsVO, whereSql, tool);
        this.initPeriodCondition(queryParamsVO, params, whereSql);
        this.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append(tableName).append("  record\n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.%1$s = bfUnitTable.code)\n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        sql.append("left join ").append("GC_UNIONRULE").append(" unionrule on (unionrule.id = record.UNIONRULEID)\n");
        sql.append("left join ").append("GC_UNIONRULE").append(" gcunionrule on (unionrule.parentid = gcunionrule.id)\n");
        sql.append("where ");
        sql.append(whereSql);
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

    private Map<String, Object> getObject(Map<String, Object> rowData) {
        Double ammount;
        HashMap<String, Object> result = new HashMap<String, Object>(rowData);
        Integer direct = (Integer)result.get("DC");
        if (null == direct) {
            direct = OrientEnum.D.getValue();
        }
        if (null == (ammount = ConverterUtils.getAsDouble(result.get("AMMOUNT")))) {
            ammount = 0.0;
        }
        if (direct == OrientEnum.D.getValue()) {
            result.put("DEBITVALUE", NumberUtils.doubleToString((Double)ammount));
        } else {
            result.put("CREDITVALUE", NumberUtils.doubleToString((Double)ammount));
        }
        return result;
    }

    private void initParentMergeUnitCondition(StringBuffer whereSql, List<Object> params, String parentGuids, Date date, String orgType) {
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        whereSql.append("((bfUnitTable.parents like ? and dfUnitTable.parents not like ? ) \n");
        whereSql.append("or (dfUnitTable.parents like ? and bfUnitTable.parents not like ? )) \n");
        this.initValidtimeCondition(whereSql, params, date);
    }

    private void initValidtimeCondition(StringBuffer whereSql, List<Object> params, Date date) {
        if (whereSql.length() > 0) {
            whereSql.append("and ");
        }
        whereSql.append("bfUnitTable.validtime<=? and bfUnitTable.invalidtime>? \n");
        whereSql.append("and dfUnitTable.validtime<=? and dfUnitTable.invalidtime>? \n");
        params.add(date);
        params.add(date);
        params.add(date);
        params.add(date);
    }

    private StringBuffer selectFields(QueryParamsVO queryParamsVO) {
        StringBuffer selectFields = new StringBuffer(32);
        if (queryParamsVO.isQueryAllColumns()) {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
            selectFields.append(SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"record"));
            selectFields.append(",record.amt").append("  AMMOUNT");
        } else {
            selectFields.append("record.ID,record.UNIONRULEID,record.").append("DATATIME").append(",record.").append("MDCODE").append("  UNITID,record.OPPUNITID,record.SUBJECTCODE,record.DC,record.amt ").append("  AMMOUNT,record.MEMO");
            for (String code : queryParamsVO.getOtherShowColumns()) {
                selectFields.append(",record.").append(code);
            }
        }
        return selectFields;
    }

    private void initRuleSummaryUnit(QueryParamsVO queryParamsVO, GcOrgCenterService tool) {
        ArrayList unitIdList = new ArrayList();
        ArrayList oppUnitIdList = new ArrayList();
        if (queryParamsVO.getUnitIdList() != null) {
            for (String unitId : queryParamsVO.getUnitIdList()) {
                unitIdList.addAll(tool.listAllOrgByParentIdContainsSelf(unitId).stream().distinct().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
            }
            queryParamsVO.setUnitIdList(unitIdList.stream().distinct().collect(Collectors.toList()));
        }
        if (queryParamsVO.getOppUnitIdList() != null) {
            for (String oppUnitId : queryParamsVO.getOppUnitIdList()) {
                oppUnitIdList.addAll(tool.listAllOrgByParentIdContainsSelf(oppUnitId).stream().distinct().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
            }
            queryParamsVO.setOppUnitIdList(oppUnitIdList.stream().distinct().collect(Collectors.toList()));
        }
    }

    private void initUnitCondition(QueryParamsVO queryParamsVO, StringBuffer whereSql, GcOrgCenterService service) {
        List unitIdList = queryParamsVO.getUnitIdList();
        List oppUnitIdList = queryParamsVO.getOppUnitIdList();
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

    private void initPeriodCondition(QueryParamsVO queryParamsVO, List<Object> params, StringBuffer whereSql) {
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        whereSql.append(" and record.reportSystemId = '").append(systemId).append("' \n");
        whereSql.append(" and record.DATATIME=? ");
        params.add(queryParamsVO.getPeriodStr());
        String currentcy = queryParamsVO.getCurrency();
        if (StringUtils.isEmpty((String)currentcy)) {
            currentcy = "CNY";
        }
        whereSql.append(" and record.MD_CURRENCY=? ");
        params.add(currentcy);
    }

    private void initOtherCondition(StringBuffer whereSql, Map<String, Object> filterCondition, String schemeId, String periodStr) {
        if (!CollectionUtils.isEmpty(filterCondition)) {
            for (String key : filterCondition.keySet()) {
                Object subjectEOs;
                Object tempValue = filterCondition.get(key);
                if ("subjectId".equals(key)) {
                    List selectedSubjects = (List)tempValue;
                    ArrayList<String> subjectIds = new ArrayList<String>();
                    for (String uuid : selectedSubjects) {
                        ConsolidatedSubjectVO subjectEO = this.subjectUIService.getSubjectById(uuid);
                        subjectEOs = this.subjectService.listAllChildrenSubjects(subjectEO.getSystemId(), subjectEO.getCode());
                        subjectIds.add(subjectEO.getCode());
                        Iterator iterator = subjectEOs.iterator();
                        while (iterator.hasNext()) {
                            ConsolidatedSubjectEO consolidatedSubjectEO = (ConsolidatedSubjectEO)iterator.next();
                            subjectIds.add(consolidatedSubjectEO.getCode());
                        }
                    }
                    if (subjectIds.isEmpty()) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectIds, (String)"record.SUBJECTCODE")).append("\n");
                    continue;
                }
                if ("subjectVo".equals(key)) {
                    String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(schemeId, periodStr);
                    if (StringUtils.isEmpty((String)systemId)) continue;
                    HashSet subjectCodes = new HashSet();
                    List selectedSubjects = (List)tempValue;
                    List allSubjectEos = this.subjectService.listAllSubjectsBySystemId(systemId);
                    Map parentCode2DirectChildrenCodesMap = allSubjectEos.stream().collect(Collectors.groupingBy(ConsolidatedSubjectEO::getParentCode, Collectors.mapping(subject -> subject.getCode(), Collectors.toList())));
                    subjectEOs = selectedSubjects.iterator();
                    while (subjectEOs.hasNext()) {
                        Map selectedSubjectVo = (Map)subjectEOs.next();
                        String subjectCode = (String)selectedSubjectVo.get("code");
                        if (StringUtils.isEmpty((String)subjectCode) || subjectCodes.contains(subjectCode)) continue;
                        HashSet allChildrenSubjectCodes = new HashSet(MapUtils.listAllChildrens((String)subjectCode, parentCode2DirectChildrenCodesMap));
                        subjectCodes.addAll(allChildrenSubjectCodes);
                        subjectCodes.add(subjectCode);
                    }
                    if (subjectCodes.isEmpty()) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(subjectCodes, (String)"record.SUBJECTCODE")).append("\n");
                    continue;
                }
                if ("ruleId".equals(key)) {
                    List selectedRules = (List)tempValue;
                    ArrayList<String> ruleIds = new ArrayList<String>();
                    for (String uuid : selectedRules) {
                        if (StringUtils.isEmpty((String)uuid)) continue;
                        String id = uuid;
                        ruleIds.add(id);
                        List ruleVOs = this.unionRuleService.selectUnionRuleChildrenByGroup(id);
                        for (UnionRuleVO ruleVO : ruleVOs) {
                            ruleIds.add(ruleVO.getId());
                        }
                    }
                    if (ruleIds.isEmpty()) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.UNIONRULEID")).append("\n");
                    continue;
                }
                if ("ruleVo".equals(key)) {
                    this.addRuleWhereSql(whereSql, (List)tempValue);
                    continue;
                }
                if (tempValue instanceof List) {
                    List valueList = (List)tempValue;
                    List values = valueList.stream().map(item -> ConverterUtils.getAsString((Object)item)).collect(Collectors.toList());
                    if (key.endsWith("_number")) {
                        String biggerValue;
                        key = key.replace("_number", "");
                        if (CollectionUtils.isEmpty(values)) continue;
                        String smallerValue = (String)values.get(0);
                        if (!StringUtils.isEmpty((String)smallerValue) && smallerValue.matches("-?\\d+\\.?\\d*")) {
                            whereSql.append(" and record.").append(key).append(">=").append(smallerValue).append("\n");
                        }
                        if (values.size() <= 1 || StringUtils.isEmpty((String)(biggerValue = (String)values.get(1))) || !biggerValue.matches("-?\\d+\\.?\\d*")) continue;
                        whereSql.append(" and record.").append(key).append("<=").append(biggerValue).append("\n");
                        continue;
                    }
                    if (values == null || values.size() <= 0) continue;
                    whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(values, (String)("record." + key))).append("\n");
                    continue;
                }
                String strValue = String.valueOf(tempValue).trim();
                if (StringUtils.isEmpty((String)strValue)) continue;
                if (!strValue.matches("[^'\\s]+")) {
                    whereSql.append(" and 1=2 ");
                }
                if ("memo".equals(key)) {
                    whereSql.append(" and record.").append(key).append(" like '%%").append(strValue).append("%%'\n");
                    continue;
                }
                whereSql.append(" and record.").append(key).append("='").append(strValue).append("'").append("\n");
            }
        }
    }

    private String getUnitWhere(List<String> unitIdList, QueryParamsVO queryParamsVO, GcOrgCenterService service, String prefix) {
        if (!CollectionUtils.isEmpty(unitIdList)) {
            if (queryParamsVO.isWhenOneUnitIsAllChild() && unitIdList.size() == 1) {
                String unitParents = this.getUnitParents(unitIdList.get(0), service);
                return prefix + "bfUnitTable.parents like '" + unitParents + "%' ";
            }
            if (queryParamsVO.getEnableTempTableFilterUnitOrOppUnit().booleanValue()) {
                TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(unitIdList, (String)"bfUnitTable.code");
                queryParamsVO.getTempGroupIdList().add(newConditionOfIds.getTempGroupId());
                return prefix + newConditionOfIds.getCondition();
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
            if (queryParamsVO.getEnableTempTableFilterUnitOrOppUnit().booleanValue()) {
                TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(unitIdList, (String)"dfUnitTable.code");
                queryParamsVO.getTempGroupIdList().add(newConditionOfIds.getTempGroupId());
                return prefix + newConditionOfIds.getCondition();
            }
            return prefix + SqlUtils.getConditionOfIdsUseOr(unitIdList, (String)"dfUnitTable.code");
        }
        return "";
    }

    private String getUnitParents(String unitId, GcOrgCenterService service) {
        if (unitId == null) {
            return null;
        }
        GcOrgCacheVO organization = service.getOrgByID(unitId);
        if (organization == null) {
            return null;
        }
        return organization.getParentStr();
    }

    private void addRuleWhereSql(StringBuffer whereSql, List tempValue) {
        List selectedRules = tempValue;
        if (CollectionUtils.isEmpty(selectedRules)) {
            return;
        }
        boolean hasEmpty = false;
        HashSet<String> ruleIds = new HashSet<String>();
        for (Map selectedRuleVo : selectedRules) {
            String ruleId = (String)selectedRuleVo.get("id");
            if (StringUtils.isEmpty((String)ruleId)) continue;
            if ("empty".equals(ruleId)) {
                hasEmpty = true;
                continue;
            }
            if (ruleIds.contains(ruleId)) continue;
            ruleIds.add(ruleId);
            List ruleVOs = this.unionRuleService.selectUnionRuleChildrenByGroup(ruleId);
            for (UnionRuleVO ruleVO : ruleVOs) {
                ruleIds.add(ruleVO.getId());
            }
        }
        if (hasEmpty && !ruleIds.isEmpty()) {
            whereSql.append(" and (( record.UNIONRULEID is null or record.UNIONRULEID='') or ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.UNIONRULEID")).append(" ) \n");
            return;
        }
        if (hasEmpty) {
            whereSql.append(" and ( record.UNIONRULEID is null or record.UNIONRULEID='') \n");
            return;
        }
        if (!ruleIds.isEmpty()) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"record.UNIONRULEID")).append(" \n");
            return;
        }
    }
}

