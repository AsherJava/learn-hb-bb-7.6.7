/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.offsetitem.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.SummarizingMethod;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OffsetQuerySqlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(OffsetQuerySqlBuilder.class);
    private ConsolidatedTaskService consolidatedTaskService;
    private UnionRuleService unionRuleService;
    private ConsolidatedSubjectClient subjectClient;
    private QueryParamsDTO queryParam;
    private ArrayList<String> columns;
    private StringBuilder selectFields;
    private StringBuilder fromJoinsTables;
    private StringBuilder whereCondition;
    private boolean whereNeedAnd;
    private StringBuilder orderByClause;
    private StringBuilder groupByClause;
    private Boolean isExtQueryFields;
    private Boolean isExtOrderByClause;
    private Boolean isExtGroupByClause;
    private int fieldIndex = 1;
    private static final String TABLE_ALIAS_PREFIX = "r_";
    private static final String TABLE_ALIAS = "record";
    private ArrayList<String> orderByItems;
    private GcOrgCenterService orgService;
    private Date endDate;
    private String orgTable;
    private String database = "";
    private SummarizingMethod summarizingMethod;

    private OffsetQuerySqlBuilder(QueryParamsDTO queryParam) {
        this.queryParam = queryParam;
    }

    public static OffsetQuerySqlBuilder builder(QueryParamsDTO queryParam) {
        OffsetQuerySqlBuilder sqlBuilder = new OffsetQuerySqlBuilder(queryParam);
        sqlBuilder.doInit();
        return sqlBuilder;
    }

    public String buildQuerySql(List<Object> params) {
        StringBuilder sqlBuilder = new StringBuilder();
        this.buildSqlByMainTable(params);
        this.buildSelectSql(sqlBuilder);
        logger.debug(sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    private void doInit() {
        this.fieldIndex = 1;
        this.selectFields = new StringBuilder();
        this.fromJoinsTables = new StringBuilder();
        this.whereCondition = new StringBuilder();
        this.groupByClause = new StringBuilder();
        this.orderByClause = new StringBuilder();
        this.isExtQueryFields = false;
        this.isExtOrderByClause = false;
        this.isExtGroupByClause = false;
        YearPeriodObject yp = new YearPeriodObject(null, this.queryParam.getPeriodStr());
        this.orgService = GcOrgPublicTool.getInstance((String)this.queryParam.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        this.endDate = yp.formatYP().getEndDate();
        this.orgTable = this.orgService.getCurrOrgType().getTable();
        this.consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        this.unionRuleService = (UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class);
        this.subjectClient = (ConsolidatedSubjectClient)SpringContextUtils.getBean(ConsolidatedSubjectClient.class);
    }

    private void buildSelectSql(StringBuilder sqlBuilder) {
        if (this.selectFields.charAt(this.selectFields.length() - 1) == ',') {
            this.selectFields.setLength(this.selectFields.length() - 1);
        }
        sqlBuilder.append("select ").append((CharSequence)this.selectFields).append(" from ").append((CharSequence)this.fromJoinsTables);
        if (this.whereCondition.length() > 0) {
            sqlBuilder.append(" where 1=1 ").append((CharSequence)this.whereCondition);
        }
        if (this.groupByClause != null && this.groupByClause.length() > 0) {
            this.appendGroupBySql(sqlBuilder, this.groupByClause, this.summarizingMethod);
        }
        if (this.orderByClause != null && this.orderByClause.length() > 0) {
            if (this.database.equalsIgnoreCase("GaussDB")) {
                String[] orderyFields;
                sqlBuilder.append(" ${OrderBy(");
                for (String orderyField : orderyFields = this.orderByClause.toString().split(",")) {
                    sqlBuilder.append("\"").append(orderyField).append("\"").append(",");
                }
                sqlBuilder.setLength(sqlBuilder.length() - 1);
                sqlBuilder.append(")}");
            } else {
                sqlBuilder.append(" order by ").append((CharSequence)this.orderByClause);
            }
        }
    }

    private void appendGroupBySql(StringBuilder sqlBuilder, StringBuilder groupByClause, SummarizingMethod summarizingMethod) {
        if (this.database.equalsIgnoreCase("MYSQL")) {
            sqlBuilder.append(" group by ");
            sqlBuilder.append((CharSequence)groupByClause);
            if (summarizingMethod == SummarizingMethod.RollUp) {
                sqlBuilder.append(" with rollup ");
            } else if (summarizingMethod == SummarizingMethod.Cube) {
                // empty if block
            }
        } else if (this.database.equalsIgnoreCase("ORACLE") || this.database.equalsIgnoreCase("DM") || this.database.equalsIgnoreCase("HANA") || this.database.equalsIgnoreCase("KINGBASE") || this.database.equalsIgnoreCase("KINGBASE8")) {
            sqlBuilder.append(" group by ");
            if (summarizingMethod == SummarizingMethod.RollUp) {
                sqlBuilder.append("rollup(");
            } else if (summarizingMethod == SummarizingMethod.Cube) {
                sqlBuilder.append("cube(");
            }
            sqlBuilder.append((CharSequence)groupByClause);
            if (summarizingMethod == SummarizingMethod.RollUp || summarizingMethod == SummarizingMethod.Cube) {
                sqlBuilder.append(")");
            }
        } else {
            sqlBuilder.append(" group by ");
            sqlBuilder.append((CharSequence)groupByClause);
        }
        sqlBuilder.append(" \n");
    }

    private void buildSqlByMainTable(List<Object> params) {
        this.buildQueryFields(this.selectFields);
        this.buildQueryTable(this.fromJoinsTables);
        this.buildWhereCondition(this.whereCondition, params);
        this.buildGroupBy(this.groupByClause);
        this.buildOrderBy(this.orderByClause);
    }

    private void buildQueryFields(StringBuilder sql) {
        if (this.isExtQueryFields.booleanValue()) {
            return;
        }
        if (this.columns == null || this.columns.isEmpty()) {
            sql.append(SqlUtils.getColumnsSqlByEntity(GcOffSetVchrItemAdjustEO.class, (String)TABLE_ALIAS));
            return;
        }
        sql.append(SqlUtils.getColumnsSql((String[])this.columns.toArray(new String[0]), (String)TABLE_ALIAS));
    }

    public OffsetQuerySqlBuilder buildOrderByClauseFromExternal(String extOrderByClause) {
        this.orderByClause.append(extOrderByClause);
        this.isExtOrderByClause = true;
        return this;
    }

    public OffsetQuerySqlBuilder buildGroupByClauseFromExternal(String extGroupByClause) {
        this.groupByClause.append(extGroupByClause);
        this.isExtGroupByClause = true;
        return this;
    }

    public OffsetQuerySqlBuilder buildQueryFieldsFromExternal(String queryFields) {
        this.selectFields.append(queryFields);
        this.isExtQueryFields = true;
        return this;
    }

    private void buildQueryTable(StringBuilder sql) {
        sql.append(" ").append("GC_OFFSETVCHRITEM");
        sql.append(" ");
        sql.append(TABLE_ALIAS);
        sql.append(" \n");
        this.buildOrgTableJoin(sql);
    }

    public void buildOrgTableJoin(StringBuilder sql) {
        sql.append("join ").append(this.orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n");
        sql.append("join ").append(this.orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
    }

    private void buildWhereCondition(StringBuilder whereCondition, List<Object> params) {
        if (this.queryParam == null) {
            return;
        }
        this.buildMergeUnitCondition(whereCondition, params);
        this.buildMultipleMergeUnitCondition(whereCondition, params);
        this.buildOtherCondition(whereCondition, params);
    }

    private void buildGroupBy(StringBuilder groupByClause) {
        if (this.isExtGroupByClause.booleanValue()) {
            return;
        }
    }

    private void buildOrderBy(StringBuilder orderByClause) {
        if (this.isExtOrderByClause.booleanValue()) {
            return;
        }
    }

    private void populateAttributesFromMap() {
        Map<String, Object> filterConditions = this.queryParam.getFilterCondition();
        if (filterConditions != null) {
            if (filterConditions.containsKey("gcBusinessTypeQueryRule")) {
                this.queryParam.setGcBusinessTypeQueryRule(ConverterUtils.getAsString((Object)filterConditions.get("gcBusinessTypeQueryRule")));
            }
            if (filterConditions.containsKey("gcbusinesstypecode")) {
                this.queryParam.setGcBusinessTypeCodes((List)filterConditions.get("gcbusinesstypecode"));
            }
            filterConditions.remove("gcbusinesstypecode");
            filterConditions.remove("gcBusinessTypeQueryRule");
        }
    }

    public void buildMergeUnitCondition(StringBuilder whereCondition, List<Object> params) {
        if (StringUtils.isEmpty((String)this.queryParam.getOrgId())) {
            return;
        }
        GcOrgCacheVO org = this.orgService.getOrgByCode(this.queryParam.getOrgId());
        if (org == null || org.getParents() == null) {
            return;
        }
        String parentStr = org.getParentStr();
        String gcParentStr = org.getGcParentStr();
        int orgCodeLength = this.orgService.getOrgCodeLength();
        String orgTypeId = this.queryParam.getOrgType();
        String emptyUUID = "NONE";
        int len = gcParentStr.length();
        whereCondition.append("and (substr(bfUnitTable.gcparents, 1, ").append(len + this.orgService.getOrgCodeLength() + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(")\n");
        whereCondition.append("and bfUnitTable.gcparents like ?\n");
        whereCondition.append("and dfUnitTable.gcparents like ?\n");
        params.add(gcParentStr + "%");
        params.add(gcParentStr + "%");
        whereCondition.append(" and record.md_gcorgtype in('" + emptyUUID + "',?)) \n");
        params.add(orgTypeId);
        if (this.queryParam.isDelete()) {
            ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(this.queryParam.getTaskId(), this.queryParam.getPeriodStr());
            if (consolidatedTaskVO == null) {
                whereCondition.append(" and record.md_gcorgtype <> '").append(emptyUUID).append("'\n");
                return;
            }
            if (!(consolidatedTaskVO.getTaskKey().equals(this.queryParam.getTaskId()) || !StringUtils.isEmpty((String)this.queryParam.getOrgId()) && consolidatedTaskVO.getManageTaskKeys().contains(this.queryParam.getTaskId()) && !CollectionUtils.isEmpty((Collection)consolidatedTaskVO.getManageCalcUnitCodes()) && consolidatedTaskVO.getManageCalcUnitCodes().contains(this.queryParam.getOrgId()))) {
                whereCondition.append(" and record.md_gcorgtype <> '").append(emptyUUID).append("'\n");
            }
        }
    }

    public void buildMultipleMergeUnitCondition(StringBuilder whereCondition, List<Object> params) {
        List<GcOrgCacheVO> mergeOrgs = this.queryParam.getOrgList();
        if (CollectionUtils.isEmpty(mergeOrgs)) {
            return;
        }
        int orgCodeLength = this.orgService.getOrgCodeLength();
        whereCondition.append(" and (");
        for (int i = 0; i < this.queryParam.getOrgList().size(); ++i) {
            String mergeParents = mergeOrgs.get(i).getGcParentStr();
            int len = mergeOrgs.get(i).getGcParentStr().length();
            if (i != 0) {
                whereCondition.append(" or ");
            }
            whereCondition.append(" (");
            whereCondition.append("bfUnitTable.gcparents like '" + mergeParents + "%' \n");
            whereCondition.append(" and dfUnitTable.gcparents like '" + mergeParents + "%' \n");
            whereCondition.append(" and substr(bfUnitTable.gcparents, 1, " + (len + orgCodeLength + 1) + ") <> substr(dfUnitTable.gcparents, 1," + (len + orgCodeLength + 1) + ")");
            whereCondition.append(") \n");
        }
        whereCondition.append(") \n");
        String emptyUUID = "NONE";
        whereCondition.append(" and record.md_gcorgtype in('" + emptyUUID + "',?) \n");
        params.add(this.queryParam.getOrgType());
    }

    public void buildOtherCondition(StringBuilder whereCondition, List<Object> params) {
        this.populateAttributesFromMap();
        this.initValidtimeCondition(whereCondition, params, this.endDate);
        this.initUnitCondition(whereCondition, params);
        this.initPeriodCondition(whereCondition, params);
        this.initOtherCondition(whereCondition, params);
    }

    private void initValidtimeCondition(StringBuilder whereCondition, List<Object> params, Date date) {
        whereCondition.append("and bfUnitTable.validtime<? and bfUnitTable.invalidtime>=? \n");
        whereCondition.append("and dfUnitTable.validtime<? and dfUnitTable.invalidtime>=? \n");
        params.add(date);
        params.add(date);
        params.add(date);
        params.add(date);
    }

    private void initUnitCondition(StringBuilder whereSql, List<Object> params) {
        List<String> unitIdList = this.queryParam.getUnitIdList();
        List<String> oppUnitIdList = this.queryParam.getOppUnitIdList();
        if (this.queryParam.isFixedUnitQueryPosition()) {
            whereSql.append(this.getUnitWhere(unitIdList, this.queryParam, this.orgService, " and "));
            whereSql.append(this.getOppUnitWhere(oppUnitIdList, this.queryParam, this.orgService, " and "));
        } else {
            String leftSign = "(";
            String orSign = " or ";
            if (!CollectionUtils.isEmpty(unitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getUnitWhere(unitIdList, this.queryParam, this.orgService, ""));
                leftSign = "";
            }
            if (!CollectionUtils.isEmpty(oppUnitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getOppUnitWhere(oppUnitIdList, this.queryParam, this.orgService, ""));
                whereSql.append(orSign).append(this.getUnitWhere(oppUnitIdList, this.queryParam, this.orgService, ""));
                orSign = " and ";
            }
            if (!CollectionUtils.isEmpty(unitIdList)) {
                whereSql.append(orSign).append(this.getOppUnitWhere(unitIdList, this.queryParam, this.orgService, ""));
            }
            if (!CollectionUtils.isEmpty(unitIdList) || !CollectionUtils.isEmpty(oppUnitIdList)) {
                whereSql.append(")\n");
                whereSql.append("and record.md_gcorgtype in('").append(GCOrgTypeEnum.NONE.getCode()).append("', ? ) \n");
                params.add(this.queryParam.getOrgType());
            }
        }
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

    private void initPeriodCondition(StringBuilder whereSql, List<Object> params) {
        if (StringUtils.isEmpty((String)this.queryParam.getSystemId())) {
            Assert.isNotNull((Object)this.queryParam.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(this.queryParam.getTaskId(), this.queryParam.getPeriodStr());
            this.queryParam.setSystemId(systemId);
        }
        Assert.isNotNull((Object)this.queryParam.getSystemId(), (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        whereSql.append("and record.systemid = ? ").append(" \n");
        params.add(this.queryParam.getSystemId());
        whereSql.append("and record.DATATIME=? ").append(" \n");
        if (this.queryParam.isSameCtrl()) {
            params.add(this.queryParam.getSameCtrlPeriodStr());
        } else {
            params.add(this.queryParam.getPeriodStr());
        }
        String currentcy = this.queryParam.getCurrencyUpperCase();
        whereSql.append("and record.offsetCurr=? \n");
        params.add(currentcy);
    }

    private void initOtherCondition(StringBuilder whereSql, List<Object> params) {
        TempTableCondition tempTableCondition;
        if (this.queryParam.isOnlyQueryNullRule()) {
            whereSql.append(" and record.RULEID is null ");
        } else if (!CollectionUtils.isEmpty(this.queryParam.getRules())) {
            HashSet<String> ruleIds = new HashSet<String>();
            for (String id : this.queryParam.getRules()) {
                ruleIds.add(id);
                List ruleEos = this.unionRuleService.selectUnionRuleChildrenByGroup(id);
                for (UnionRuleVO ruleEo : ruleEos) {
                    ruleIds.add(ruleEo.getId());
                }
            }
            if (!ruleIds.isEmpty()) {
                TempTableCondition tempTableCondition2 = SqlUtils.getConditionOfIds(ruleIds, (String)"record.RULEID");
                this.queryParam.getTempGroupIdList().add(tempTableCondition2.getTempGroupId());
                whereSql.append(" and ").append(tempTableCondition2.getCondition());
            }
        }
        if (!CollectionUtils.isEmpty(this.queryParam.getMrecids())) {
            tempTableCondition = SqlUtils.getConditionOfIds(this.queryParam.getMrecids(), (String)"record.MRECID");
            this.queryParam.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
            whereSql.append(" and ").append(tempTableCondition.getCondition());
        }
        if (this.queryParam.isOnlyQueryNullElmMode()) {
            whereSql.append(" and record.ELMMODE is null ");
        } else if (!CollectionUtils.isEmpty(this.queryParam.getElmModes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble(this.queryParam.getElmModes(), (String)"record.ELMMODE"));
        }
        if (this.queryParam.isOnlyQueryNullGcBusinessTypeCode()) {
            whereSql.append(" and record.GCBUSINESSTYPECODE is null ");
        } else if (!CollectionUtils.isEmpty(this.queryParam.getGcBusinessTypeCodes())) {
            tempTableCondition = SqlUtils.getConditionOfIds(this.queryParam.getGcBusinessTypeCodes(), (String)"record.GCBUSINESSTYPECODE");
            this.queryParam.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
            whereSql.append(" and ").append(tempTableCondition.getCondition());
        }
        if (!CollectionUtils.isEmpty(this.queryParam.getElmModes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble(this.queryParam.getElmModes(), (String)"record.ELMMODE"));
        }
        if (!CollectionUtils.isEmpty(this.queryParam.getOffSetSrcTypes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulDouble(this.queryParam.getOffSetSrcTypes(), (String)"record.OffSetSrcType"));
        }
        if (!CollectionUtils.isEmpty(this.queryParam.getForbidOffSetSrcTypes())) {
            if (this.queryParam.getForbidOffSetSrcTypes().size() == 1) {
                whereSql.append(" and record.OffSetSrcType <>").append(this.queryParam.getForbidOffSetSrcTypes().get(0)).append("\n");
            } else {
                StringBuffer inSql = new StringBuffer(16);
                for (Integer forbidOffSetSrcType : this.queryParam.getForbidOffSetSrcTypes()) {
                    inSql.append(forbidOffSetSrcType).append(",");
                }
                inSql.setLength(inSql.length() - 1);
                whereSql.append(" and record.offSetSrcType not in (").append(inSql).append(")");
            }
        }
        if (!CollectionUtils.isEmpty(this.queryParam.getSubjectCodes())) {
            HashSet<String> allChildrenCodeAndSelf = new HashSet<String>();
            if (!StringUtils.isEmpty((String)this.queryParam.getSystemId())) {
                List<String> subjectCodes = this.queryParam.getSubjectCodes();
                List allSubjects = this.subjectClient.listSubjectsBySystemIdNoSortOrder(this.queryParam.getSystemId());
                Map parentCode2DirectChildrenCodesMap = allSubjects.stream().collect(Collectors.groupingBy(ConsolidatedSubjectVO::getParentCode, Collectors.mapping(subject -> subject.getCode(), Collectors.toList())));
                for (String subjectCode : subjectCodes) {
                    if (allChildrenCodeAndSelf.contains(subjectCode)) continue;
                    allChildrenCodeAndSelf.addAll(MapUtils.listAllChildrens((String)subjectCode, parentCode2DirectChildrenCodesMap));
                    allChildrenCodeAndSelf.add(subjectCode);
                }
            }
            if (!allChildrenCodeAndSelf.isEmpty()) {
                whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(allChildrenCodeAndSelf, (String)"record.SUBJECTCODE"));
            }
            if (!CollectionUtils.isEmpty(this.queryParam.getEffectTypes())) {
                whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(this.queryParam.getEffectTypes(), (String)"record.EFFECTTYPE"));
            }
        }
        if (Boolean.TRUE.equals(this.queryParam.isFilterDisableItem())) {
            whereSql.append(" and (record.disableFlag<> ? or record.disableFlag is null )\n");
            params.add(1);
        }
        if (Boolean.FALSE.equals(this.queryParam.getExistGcBusinessType())) {
            whereSql.append(" and record.GCBUSINESSTYPECODE is null \n");
        }
        if (DimensionUtils.isExistAdjust((String)this.queryParam.getTaskId())) {
            Assert.isNotEmpty((String)this.queryParam.getSelectAdjustCode(), (String)"\u4efb\u52a1\u5df2\u5f00\u542f\u8c03\u6574\u671f\uff0c\u67e5\u8be2\u5c5e\u6027\u8c03\u6574\u671f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            whereSql.append(" and record.ADJUST = ").append("'").append(this.queryParam.getSelectAdjustCode()).append("'");
        }
        this.initOtherCondition(whereSql, this.queryParam.getFilterCondition(), this.queryParam.getSystemId(), this.queryParam.getSchemeId(), this.queryParam.getCurrencyUpperCase(), this.queryParam.getPeriodStr(), params);
    }

    private void initOtherCondition(StringBuilder whereSql, Map<String, Object> filterCondition, String systemId, String schemeId, String currencyCode, String periodStr, List<Object> params) {
        if (!org.springframework.util.CollectionUtils.isEmpty(filterCondition)) {
            for (String key : filterCondition.keySet()) {
                Object tempValue = filterCondition.get(key);
                if (tempValue instanceof List) {
                    Object biggerValue;
                    Object smallerValue;
                    List values;
                    TempTableCondition tempTableCondition;
                    Object biggerValue2;
                    Object smallerValue2;
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
                        if (StringUtils.isEmpty((String)systemId)) continue;
                        HashSet subjectCodes = new HashSet();
                        List selectedSubjects = (List)tempValue;
                        List allSubjectEos = this.subjectClient.listSubjectsBySystemIdNoSortOrder(systemId);
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
                            this.queryParam.getTempGroupIdList().add(ruleIdCondition.getTempGroupId());
                            TempTableCondition fetchSetGroupIdCondition = SqlUtils.getConditionOfIds(fetchSetGroupIds, (String)"record.fetchSetGroupId");
                            whereSql.append(" or ").append(fetchSetGroupIdCondition.getCondition()).append(" ) ");
                            this.queryParam.getTempGroupIdList().add(fetchSetGroupIdCondition.getTempGroupId());
                            continue;
                        }
                        if (!ruleIds.isEmpty()) {
                            tempTableCondition2 = SqlUtils.getConditionOfIds(ruleIds, (String)"record.RULEID");
                            this.queryParam.getTempGroupIdList().add(tempTableCondition2.getTempGroupId());
                            whereSql.append(" and ").append(tempTableCondition2.getCondition());
                        }
                        if (fetchSetGroupIds.isEmpty()) continue;
                        tempTableCondition2 = SqlUtils.getConditionOfIds(fetchSetGroupIds, (String)"record.fetchSetGroupId");
                        this.queryParam.getTempGroupIdList().add(tempTableCondition2.getTempGroupId());
                        whereSql.append(" and ").append(tempTableCondition2.getCondition());
                        continue;
                    }
                    if ("ruleVo".equals(key)) {
                        this.addRuleWhereSql(whereSql, (List)tempValue);
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
                        smallerValue2 = doubleValues.get(0);
                        if (smallerValue2 instanceof Number) {
                            whereSql.append(" and coalesce(record.offset_Credit,0)+coalesce(record.offset_Debit,0)>=").append(smallerValue2);
                        }
                        if (doubleValues.size() <= 1 || !((biggerValue2 = doubleValues.get(1)) instanceof Number)) continue;
                        whereSql.append(" and coalesce(record.offset_Credit,0)+coalesce(record.offset_Debit,0)<=").append(biggerValue2);
                        continue;
                    }
                    if ("offset_value_init_number".equals(key)) {
                        doubleValues = (List)tempValue;
                        if (CollectionUtils.isEmpty((Collection)doubleValues)) continue;
                        smallerValue2 = doubleValues.get(0);
                        if (smallerValue2 instanceof Number) {
                            whereSql.append(" and coalesce(record.offset_Credit,0)+coalesce(record.offset_Debit,0)>=").append(smallerValue2);
                        }
                        if (doubleValues.size() <= 1 || !((biggerValue2 = doubleValues.get(1)) instanceof Number)) continue;
                        whereSql.append(" and coalesce(record.offset_Credit,0)+coalesce(record.offset_Debit,0)<=").append(biggerValue2);
                        continue;
                    }
                    if ("unitVo".equals(key)) {
                        Set<String> unitIds = this.getUnitIdsSet((List)tempValue);
                        if (unitIds.isEmpty()) continue;
                        tempTableCondition = SqlUtils.getConditionOfIds(unitIds, (String)"bfUnitTable.code");
                        this.queryParam.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
                        whereSql.append(" and ").append(tempTableCondition.getCondition());
                        continue;
                    }
                    if ("oppUnitVo".equals(key)) {
                        Set<String> oppUnitIds = this.getUnitIdsSet((List)tempValue);
                        if (oppUnitIds.isEmpty()) continue;
                        tempTableCondition = SqlUtils.getConditionOfIds(oppUnitIds, (String)"dfUnitTable.code");
                        this.queryParam.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
                        whereSql.append(" and ").append(tempTableCondition.getCondition());
                        continue;
                    }
                    if (!(tempValue instanceof List)) continue;
                    List valueList = (List)tempValue;
                    if (key.endsWith("_number")) {
                        values = valueList.stream().map(item -> ConverterUtils.getAsString((Object)item)).collect(Collectors.toList());
                        key = key.replace("_number", "");
                        if (CollectionUtils.isEmpty(values)) continue;
                        smallerValue = (String)values.get(0);
                        if (!StringUtils.isEmpty((String)smallerValue) && ((String)smallerValue).matches("-?\\d+\\.?\\d*")) {
                            whereSql.append(" and record.").append(key).append(">=").append((String)smallerValue);
                        }
                        if (values.size() < 2 || StringUtils.isEmpty((String)(biggerValue = (String)values.get(1))) || !((String)biggerValue).matches("-?\\d+\\.?\\d*")) continue;
                        whereSql.append(" and record.").append(key).append("<=").append((String)biggerValue);
                        continue;
                    }
                    if (key.endsWith("_date")) {
                        key = key.replace("_date", "");
                        if (CollectionUtils.isEmpty((Collection)valueList)) continue;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        smallerValue = LocalDateTime.parse(valueList.get(0).toString(), formatter);
                        if (smallerValue != null) {
                            whereSql.append(" and record.").append(key).append(">=").append("?");
                            params.add(smallerValue);
                        }
                        if (valueList.size() < 2 || (biggerValue = LocalDateTime.parse(valueList.get(1).toString(), formatter)) == null) continue;
                        whereSql.append(" and record.").append(key).append("<").append("?");
                        params.add(((LocalDateTime)biggerValue).plusSeconds(1L));
                        continue;
                    }
                    values = valueList.stream().map(item -> ConverterUtils.getAsString((Object)item)).collect(Collectors.toList());
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

    private void addRuleWhereSql(StringBuilder whereSql, List tempValue) {
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
            this.queryParam.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
            whereSql.append(needOr ? " or " : "").append(tempTableCondition.getCondition());
            needOr = true;
        }
        if (!fetchSetGroupIds.isEmpty()) {
            tempTableCondition = SqlUtils.getConditionOfIds(fetchSetGroupIds, (String)"record.fetchSetGroupId");
            this.queryParam.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
            whereSql.append(needOr ? " or " : "").append(tempTableCondition.getCondition());
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

    public QueryParamsDTO getQueryParam() {
        return this.queryParam;
    }

    public void setQueryParam(QueryParamsDTO queryParam) {
        this.queryParam = queryParam;
    }

    public ArrayList<String> getColumns() {
        return this.columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public SummarizingMethod getSummarizingMethod() {
        return this.summarizingMethod;
    }

    public void setSummarizingMethod(SummarizingMethod summarizingMethod) {
        this.summarizingMethod = summarizingMethod;
    }

    public ConsolidatedTaskService getConsolidatedTaskService() {
        return this.consolidatedTaskService;
    }

    public void setConsolidatedTaskService(ConsolidatedTaskService consolidatedTaskService) {
        this.consolidatedTaskService = consolidatedTaskService;
    }

    public UnionRuleService getUnionRuleService() {
        return this.unionRuleService;
    }

    public void setUnionRuleService(UnionRuleService unionRuleService) {
        this.unionRuleService = unionRuleService;
    }

    public ConsolidatedSubjectClient getSubjectClient() {
        return this.subjectClient;
    }

    public void setSubjectClient(ConsolidatedSubjectClient subjectClient) {
        this.subjectClient = subjectClient;
    }
}

