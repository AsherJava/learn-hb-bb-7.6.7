/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.inputdata.dto.InputDataDTO
 *  com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition
 *  com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.UnitSceneEnum
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  org.apache.shiro.util.Assert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.inputdata.dto.InputDataDTO;
import com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition;
import com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.impl.UnitScenesTempDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO;
import com.jiuqi.gcreport.inputdata.util.I18nTableUtils;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.inputdata.util.InputLoggerUtils;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.enums.UnitSceneEnum;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class InputDataDaoImpl
implements InputDataDao {
    private final ConsolidatedSubjectService subjectService;
    private final UnionRuleService unionRuleService;
    private final ConsolidatedTaskService taskService;
    private final InputDataNameProvider inputDataNameProvider;
    private final TemplateEntDaoCacheService templateEntDaoCacheService;
    private final ConsolidatedOptionService optionService;
    private static final String SQL_QUERYINPUTDATA = " select %1$s \n   from %3$s e \n   %2$s \n";
    private static final String SQL_GETINPUTDATAEOS = " select %1$s \n   from %5$s e \n   join %2$s  unit on e.MDCODE = unit.code and unit.validTime <= ? and unit.invalidTime > ? \n   join %2$s  oppunit on e.oppunitid = oppunit.code and oppunit.validTime <= ? and oppunit.invalidTime > ? \n  where e.DATATIME = ? \n    and e.unionruleid = ? \n    and %6$s \n    and unit.parents like ? \n    and oppunit.parents like ? \n    and substr(unit.gcparents, %3$s, %4$s) <> substr(oppunit.gcparents, %3$s, %4$s) \n    and e.MD_CURRENCY = ? \n";
    private static final String SQL_QUERYINPUTDATABYKEYS = " select %1$s \n   from %3$s e \n  where %2$s \n";
    private static final String SQL_QUERYCHECKAMTERRORRECORDS = "  select %s \n    from %s i \n    join %s  o \n      on i.MDCODE = o.id \n   where %s \n     and i.DATATIME =? \n     and i.MD_CURRENCY = ? \n     and o.parents >= ? \n     and o.parents < ? + '0' \n     and i.offsetState = '" + ReportOffsetStateEnum.OFFSET.getValue() + "'\n     and i.amt <> i.offsetAmt + i.diffAmt \n";
    private static final String SQL_GETMANULAINPUTDATAEOS = " select %1$s \n   from %5$s record \n   join %2$s  dfUnitTable on record.MDCODE = dfUnitTable.code and dfUnitTable.validTime <= ? and dfUnitTable.invalidTime > ? \n   join %2$s  bfUnitTable on record.oppunitid = bfUnitTable.code and bfUnitTable.validTime <= ? and bfUnitTable.invalidTime > ? \n  where record.DATATIME = ? \n    and record.reportSystemId = ?     and substr(dfUnitTable.gcparents, %3$s, %4$s) <> substr(bfUnitTable.gcparents, %3$s, %4$s) \n    and dfUnitTable.gcparents like ? \n    and bfUnitTable.gcparents like ? \n    and record.offsetState = ? \n    and record.MD_CURRENCY = ? \n    %6$s \n";

    public InputDataDaoImpl(ConsolidatedSubjectService subjectService, UnionRuleService unionRuleService, ConsolidatedTaskService taskService, InputDataNameProvider inputDataNameProvider, TemplateEntDaoCacheService templateEntDaoCacheService, ConsolidatedOptionService optionService) {
        this.subjectService = subjectService;
        this.unionRuleService = unionRuleService;
        this.taskService = taskService;
        this.inputDataNameProvider = inputDataNameProvider;
        this.templateEntDaoCacheService = templateEntDaoCacheService;
        this.optionService = optionService;
    }

    private Map<String, Object> getUnOffsetObject(Map<String, Object> rowData, boolean isDebit) {
        HashMap<String, Object> result = new HashMap<String, Object>(rowData);
        Double ammount = ConverterUtils.getAsDouble(result.get("AMMOUNT"));
        if (null == ammount) {
            ammount = 0.0;
        }
        if (isDebit) {
            result.put("DEBITVALUE", ammount);
        } else {
            result.put("CREDITVALUE", ammount);
        }
        for (Map.Entry entry : result.entrySet()) {
            if ("AMMOUNT".equals(entry.getKey()) || !(entry.getValue() instanceof Double)) continue;
            result.put((String)entry.getKey(), NumberUtils.doubleToString((Double)((Double)entry.getValue())));
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryUnOffset(QueryParamsVO queryParamsVO, boolean isBizCodeOrUnionRuleNull) {
        ArrayList<Object> params;
        String selectSql;
        ArrayList<Map<String, Object>> debitDatas = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> creditDatas = new ArrayList<Map<String, Object>>();
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        List selectRs = dao.selectMap(selectSql = this.getQueryUnOffsetForAllRecordsSql(queryParamsVO, params = new ArrayList<Object>(), false, isBizCodeOrUnionRuleNull), params);
        if (!org.springframework.util.CollectionUtils.isEmpty(selectRs)) {
            for (Map rs : selectRs) {
                if (OrientEnum.C.getValue().equals(rs.get("DC"))) {
                    creditDatas.add(this.getUnOffsetObject(rs, false));
                    continue;
                }
                debitDatas.add(this.getUnOffsetObject(rs, true));
            }
        }
        return this.mergeDebitAndCrebitData(queryParamsVO.getNotOffsetOtherColumns(), debitDatas, creditDatas);
    }

    private List<Map<String, Object>> mergeDebitAndCrebitData(List<String> otherNotOffsetShowColumns, List<Map<String, Object>> debitDatas, List<Map<String, Object>> creditDatas) {
        creditDatas.addAll(debitDatas);
        for (int index = 0; index < creditDatas.size(); ++index) {
            Map<String, Object> map1 = creditDatas.get(index);
            int ind = index + 1;
            while (ind < creditDatas.size()) {
                Map<String, Object> map2 = creditDatas.get(ind);
                boolean equals = true;
                for (String string : otherNotOffsetShowColumns) {
                    if (map1.get(string) == null && map2.get(string) == null) continue;
                    if (map1.get(string) instanceof String || map2.get(string) instanceof String) {
                        equals = equals && MapUtils.compareStr(map1, map2, (Object)string) == 0;
                        continue;
                    }
                    if (map1.get(string) instanceof Date || map2.get(string) instanceof Date) {
                        equals = equals && DateUtils.compare((Date)((Date)map1.get(string)), (Date)((Date)map2.get(string)), (boolean)true) == 1;
                        continue;
                    }
                    if (map1.get(string) instanceof Double || map2.get(string) instanceof Double) {
                        equals = equals && MapUtils.compareDouble(map1, map2, (Object)string) == 0;
                        continue;
                    }
                    equals = equals && map1.get(string).equals(map2.get(string));
                }
                if (MapUtils.compareStr(map1, map2, (Object)"SUBJECTCODE") == 0 && MapUtils.compareStr(map1, map2, (Object)"GCBUSINESSTYPECODE") == 0 && equals && MapUtils.compareInt(map1, map2, (Object)"DC") != 0) {
                    for (Map.Entry entry : map2.entrySet()) {
                        if (entry.getKey().equals("SUBJECTCODE")) continue;
                        map1.put((String)entry.getKey(), entry.getValue());
                    }
                    creditDatas.remove(ind);
                    continue;
                }
                ++ind;
            }
        }
        return creditDatas;
    }

    private String getQueryUnOffsetForAllRecordsSql(QueryParamsVO queryParamsVO, List<Object> params, boolean isQueryParent, boolean isBizCodeOrUnionRuleNull) {
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
            if (isQueryParent) {
                if (gcParentsStr.length() < codeLength + 1) {
                    return "";
                }
                this.initParentMergeUnitCondition(whereSql, params, parentGuids, date, queryParamsVO.getOrgType());
            } else {
                this.initMergeUnitCondition(whereSql, params, parentGuids, gcParentsStr, date, codeLength);
            }
        } else {
            if (org.springframework.util.CollectionUtils.isEmpty(queryParamsVO.getUnitIdList())) {
                return "";
            }
            this.initValidtimeCondition(whereSql, params, date);
        }
        String orgTable = tool.getCurrOrgType().getTable();
        this.initUnitCondition(queryParamsVO, whereSql, tool);
        this.initPeriodCondition(queryParamsVO, params, whereSql);
        Map filterCondition = queryParamsVO.getFilterCondition();
        if (queryParamsVO.getFilterCondition().get("elmMode") != null) {
            filterCondition.remove("elmMode");
        }
        this.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append("record.subjectCode,record.dc,unionRule.businesstypecode gcbusinesstypecode,sum(record.amt) AMMOUNT").append(this.handleSelectColumnFields(queryParamsVO.getNotOffsetOtherColumns())).append(" from ").append(tableName).append("  record \n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.%1$s = bfUnitTable.code) \n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code) \n");
        sql.append("left join ").append("GC_UNIONRULE").append("  unionRule on record.unionRuleId = unionRule.id \n");
        sql.append(" where 1=1 and ");
        if (!org.springframework.util.CollectionUtils.isEmpty(queryParamsVO.getGcBusinessTypeCodes())) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr((Collection)queryParamsVO.getGcBusinessTypeCodes(), (String)"unionRule.businesstypecode"));
        }
        sql.append(whereSql);
        if (!isBizCodeOrUnionRuleNull) {
            sql.append(" and  unionRule.businesstypecode is not null ");
        }
        if (isBizCodeOrUnionRuleNull) {
            sql.append(" and ( unionRule.businesstypecode is  null or record.unionRuleId is null )");
        }
        sql.append("and record.offsetState='0'\n");
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            sql.append(" and record.ADJUST=").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        sql.append("group by record.subjectCode,unionRule.businesstypecode,record.dc\n").append(this.handleSelectColumnFields(queryParamsVO.getNotOffsetOtherColumns()));
        return String.format(sql.toString(), "MDCODE");
    }

    private String handleSelectColumnFields(List<String> otherNotOffsetShowColumns) {
        StringBuilder selectFields = new StringBuilder("");
        otherNotOffsetShowColumns.forEach(offsetVchrQuery -> {
            if (!("subjectVo".equals(offsetVchrQuery) || "gcbusinesstypecode".equals(offsetVchrQuery) || "ruleVo".equals(offsetVchrQuery) || "elmMode".equals(offsetVchrQuery))) {
                selectFields.append(" ,record.").append((String)offsetVchrQuery);
            }
        });
        return selectFields.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public Pagination<Map<String, Object>> queryUnOffsetRecords(QueryParamsVO queryParamsVO, Boolean isQueryParent) {
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        ArrayList datas = new ArrayList();
        page.setContent(datas);
        ArrayList<Object> params = new ArrayList<Object>();
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        if (FilterMethodEnum.RULESUMMARY.getCode().equals(queryParamsVO.getFilterMethod())) {
            return this.queryUnOffsetRuleRecords((Pagination<Map<String, Object>>)page, queryParamsVO, dao, isQueryParent);
        }
        try {
            List rs;
            String sql = this.getQueryUnOffsetRecordsSql(queryParamsVO, params, isQueryParent);
            if (StringUtils.isEmpty((String)sql)) {
                Pagination pagination = page;
                return pagination;
            }
            if (pageNum == -1 || pageSize == -1) {
                rs = dao.selectMap(sql, params);
            } else {
                int firstResult = (pageNum - 1) * pageSize;
                int count = dao.count(sql, params);
                if (count < 1) {
                    Pagination pagination = page;
                    return pagination;
                }
                page.setTotalElements(Integer.valueOf(count));
                rs = dao.selectMapByPaging(sql, firstResult, pageNum * pageSize, params);
            }
            if (rs != null && !rs.isEmpty()) {
                rs.forEach(row -> datas.add(this.getObject((Map<String, Object>)row)));
            }
            page.setContent(datas);
            page.setPageSize(Integer.valueOf(pageSize));
            page.setCurrentPage(Integer.valueOf(pageNum));
            if (pageNum == -1 || pageSize == -1) {
                page.setTotalElements(Integer.valueOf(datas.size()));
            }
            Pagination pagination = page;
            return pagination;
        }
        finally {
            if (UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene()) && !params.isEmpty()) {
                UnitScenesTempDao.newInstance().deleteIdRealTempByBatchId((String)params.get(params.size() - 1));
            }
        }
    }

    private Pagination<Map<String, Object>> queryUnOffsetRuleRecords(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO, EntNativeSqlDefaultDao<InputDataEO> dao, Boolean isQueryParent) {
        List rs;
        ArrayList<Object> params = new ArrayList<Object>();
        if (queryParamsVO.getUnitIdList() != null && queryParamsVO.getUnitIdList().size() > 0 || queryParamsVO.getOppUnitIdList() != null && queryParamsVO.getOppUnitIdList().size() > 0) {
            String sql = this.getQueryUnOffsetRuleRecordsSql(queryParamsVO, params, false, null, isQueryParent);
            if (StringUtils.isEmpty((String)sql)) {
                return page;
            }
            rs = dao.selectMap(sql, params);
        } else {
            int pageNum = queryParamsVO.getPageNum();
            int pageSize = queryParamsVO.getPageSize();
            int firstResult = (pageNum - 1) * pageSize;
            String sql = this.getQueryUnOffsetRuleRecordsSql(queryParamsVO, params, true, null, isQueryParent);
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
            sql = this.getQueryUnOffsetRuleRecordsSql(queryParamsVO, params, false, unionRuleIds, isQueryParent);
            rs = dao.selectMap(sql, params);
            page.setPageSize(Integer.valueOf(pageSize));
            page.setCurrentPage(Integer.valueOf(pageNum));
        }
        ArrayList datas = new ArrayList();
        if (rs != null && !rs.isEmpty()) {
            rs.forEach(row -> datas.add(this.getObject((Map<String, Object>)row)));
        }
        page.setContent(datas);
        return page;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<InputDataEO> queryUnOffsetRecordsForCalc(QueryParamsVO queryParamsVO) {
        ArrayList<Object> params = new ArrayList<Object>();
        queryParamsVO.setQueryAllColumns(true);
        try {
            String sql = this.getQueryUnOffsetRecordsSql(queryParamsVO, params, false);
            if (StringUtils.isEmpty((String)sql)) {
                List<InputDataEO> list = Collections.emptyList();
                return list;
            }
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
            EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
            List list = dao.selectEntity(sql, params);
            return list;
        }
        finally {
            if (UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene()) && !params.isEmpty()) {
                UnitScenesTempDao.newInstance().deleteIdRealTempByBatchId((String)params.get(params.size() - 1));
            }
        }
    }

    private String getQueryUnOffsetRuleRecordsSql(QueryParamsVO queryParamsVO, List<Object> params, Boolean isQueryCount, Set<String> unionRuleIds, Boolean isQueryParent) {
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
            if (isQueryParent.booleanValue()) {
                if (gcParentsStr.length() < codeLength + 1) {
                    return "";
                }
                this.initParentMergeUnitCondition(whereSql, params, parentGuids, date, queryParamsVO.getOrgType());
            } else {
                this.initMergeUnitCondition(whereSql, params, parentGuids, gcParentsStr, date, codeLength);
            }
        } else {
            if (org.springframework.util.CollectionUtils.isEmpty(queryParamsVO.getUnitIdList())) {
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
        this.initRuleSummaryUnit(queryParamsVO, tool);
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

    private String getQueryUnOffsetRecordsSql(QueryParamsVO queryParamsVO, List<Object> params, Boolean isQueryParent) {
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
            if (isQueryParent.booleanValue()) {
                if (gcParentsStr.length() < codeLength + 1) {
                    return "";
                }
                this.initParentMergeUnitCondition(whereSql, params, parentGuids, date, queryParamsVO.getOrgType());
            } else {
                this.initMergeUnitCondition(whereSql, params, parentGuids, gcParentsStr, date, codeLength);
            }
        } else {
            if (org.springframework.util.CollectionUtils.isEmpty(queryParamsVO.getUnitIdList())) {
                return "";
            }
            this.initValidtimeCondition(whereSql, params, date);
        }
        String orgTable = tool.getCurrOrgType().getTable();
        StringBuffer selectFields = new StringBuffer(32);
        if (UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene())) {
            selectFields.append(" record.UNIONRULEID, record.mdcode UNITID, record.mdcode,record.OPPUNITID");
        } else {
            selectFields = this.selectFields(queryParamsVO);
        }
        this.initUnitCondition(queryParamsVO, whereSql, tool);
        this.initPeriodCondition(queryParamsVO, params, whereSql);
        this.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append(tableName).append("  record\n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.%1$s = bfUnitTable.code)\n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        if (queryParamsVO.getSumTabPenetrateCondition() != null && queryParamsVO.getSumTabPenetrateCondition().get("gcBusinessTypeCode") != null) {
            ArrayList<String> gcBusinessCodes = new ArrayList<String>();
            gcBusinessCodes.add((String)queryParamsVO.getSumTabPenetrateCondition().get("gcBusinessTypeCode"));
            whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(gcBusinessCodes, (String)"unionRule.businesstypecode"));
            sql.append("left join ").append("GC_UNIONRULE").append("  unionRule on record.unionRuleId = unionRule.id \n");
        }
        if (queryParamsVO.getSumTabPenetrateCondition() != null && queryParamsVO.getSumTabPenetrateCondition().get("isExistGcBusinessType") != null && !((Boolean)queryParamsVO.getSumTabPenetrateCondition().get("isExistGcBusinessType")).booleanValue()) {
            whereSql.append(" and ( unionRule.businesstypecode is  null or record.unionRuleId is null ) ");
            sql.append("left join ").append("GC_UNIONRULE").append("  unionRule on record.unionRuleId = unionRule.id \n");
        }
        sql.append("where ");
        sql.append(whereSql);
        sql.append("and record.offsetState='0'\n");
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            sql.append(" and record.ADJUST=").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        if (UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene())) {
            sql = this.filterUnitScenes(queryParamsVO, params, tool, sql);
        }
        if (FilterMethodEnum.UNIT.getCode().equals(queryParamsVO.getFilterMethod())) {
            sql.append("order by (CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END),record.unionRuleId,record.DC desc\n");
        } else if (FilterMethodEnum.RULE.getCode().equals(queryParamsVO.getFilterMethod())) {
            sql.append("order by record.unionRuleId,(CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END),record.DC desc\n");
        } else if (FilterMethodEnum.AMT.getCode().equals(queryParamsVO.getFilterMethod())) {
            sql.append("order by record.amt desc,record.DC desc\n");
        } else if (FilterMethodEnum.UNITGROUP.getCode().equals(queryParamsVO.getFilterMethod())) {
            sql.append("order by (CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END),record.unionRuleId,record.DC desc\n");
        } else {
            sql.append("order by record.unionRuleId,(CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END) asc,record.DC desc\n");
        }
        return String.format(sql.toString(), "MDCODE");
    }

    private StringBuffer filterUnitScenes(QueryParamsVO queryParamsVO, List<Object> params, GcOrgCenterService tool, StringBuffer oldSql) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        List inputDataList = dao.selectEntity(String.format(oldSql.toString(), "MDCODE"), params);
        HashSet<ArrayKey> unitFirstKeySet = new HashSet<ArrayKey>();
        HashMap<String, String> ruleId2GcBussinessTypeCacheMap = new HashMap<String, String>(32);
        for (InputDataEO inputDataEO : inputDataList) {
            String bussinessType = this.getBussinessType(inputDataEO.getUnionRuleId(), ruleId2GcBussinessTypeCacheMap);
            unitFirstKeySet.add(new ArrayKey(new Object[]{inputDataEO.getUnitId(), inputDataEO.getOppUnitId(), bussinessType}));
        }
        ArrayList<ArrayKey> result = new ArrayList<ArrayKey>(16);
        Assert.isTrue((boolean)UnitSceneEnum.needFilter((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene()));
        boolean isBilateral = UnitSceneEnum.isBilateral((UnitSceneEnum)queryParamsVO.getNotOffsetFilterUnitScene());
        for (ArrayKey unitFirstKey : unitFirstKeySet) {
            ArrayKey oppUnitFirstKey = new ArrayKey(new Object[]{unitFirstKey.get(1), unitFirstKey.get(0), unitFirstKey.get(2)});
            if (unitFirstKeySet.contains(oppUnitFirstKey)) {
                if (!isBilateral) continue;
                result.add(unitFirstKey);
                continue;
            }
            if (isBilateral) continue;
            result.add(unitFirstKey);
        }
        StringBuffer sql = new StringBuffer(512);
        StringBuffer whereSql = new StringBuffer();
        params.clear();
        UnitScenesTempDao unitScenesTempDao = UnitScenesTempDao.newInstance();
        String batchId = unitScenesTempDao.insert(result);
        this.unitScenesInitUnitCondition(queryParamsVO, whereSql);
        this.initPeriodCondition(queryParamsVO, params, whereSql);
        this.initOtherCondition(whereSql, queryParamsVO.getFilterCondition(), queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        sql.append("select ").append(this.selectFields(queryParamsVO)).append(" from ").append(tableName).append("  record\n");
        sql.append(" left join ").append("GC_UNIONRULE").append(" unionRule on (record.reportsystemid = unionRule.reportSystem and record.unionRuleId = unionRule.id)\n");
        sql.append(" join ").append(unitScenesTempDao.getTableName()).append(" unit on (record.mdcode = unit.mdcode and record.oppUnitId = unit.oppUnitCode and unionRule.businesstypecode = unit.gcBusinessType )\n");
        sql.append("where 1=1");
        sql.append(whereSql);
        sql.append("and record.offsetState='0' and unit.batchId=?\n");
        params.add(batchId);
        return sql;
    }

    private String getBussinessType(String unionRuleId, Map<String, String> ruleId2GcBussinessTypeCacheMap) {
        if (StringUtils.isEmpty((String)unionRuleId)) {
            return null;
        }
        if (!ruleId2GcBussinessTypeCacheMap.containsKey(unionRuleId)) {
            AbstractUnionRule abstractUnionRule = this.unionRuleService.selectUnionRuleDTOById(unionRuleId);
            ruleId2GcBussinessTypeCacheMap.put(unionRuleId, abstractUnionRule == null ? null : abstractUnionRule.getBusinessTypeCode());
        }
        return ruleId2GcBussinessTypeCacheMap.get(unionRuleId);
    }

    private void unitScenesInitUnitCondition(QueryParamsVO queryParamsVO, StringBuffer whereSql) {
        List unitIdList = queryParamsVO.getUnitIdList();
        List oppUnitIdList = queryParamsVO.getOppUnitIdList();
        boolean unitEmpty = org.springframework.util.CollectionUtils.isEmpty(unitIdList);
        boolean oppUnitEmpty = org.springframework.util.CollectionUtils.isEmpty(oppUnitIdList);
        String orgFieldName = "record.MDCODE";
        if (!unitEmpty && !oppUnitEmpty) {
            whereSql.append(" and ((").append(this.unitScenesGetUnitWhere(orgFieldName, unitIdList, queryParamsVO));
            whereSql.append(" and ").append(this.unitScenesGetUnitWhere("record.oppunitid", oppUnitIdList, queryParamsVO)).append(" )");
            whereSql.append(" or ( ").append(this.unitScenesGetUnitWhere(orgFieldName, oppUnitIdList, queryParamsVO));
            whereSql.append(" and  ").append(this.unitScenesGetUnitWhere("record.oppunitid", unitIdList, queryParamsVO)).append(" ))");
            return;
        }
        if (!unitEmpty) {
            whereSql.append(" and (").append(this.unitScenesGetUnitWhere(orgFieldName, unitIdList, queryParamsVO));
            whereSql.append(" or ").append(this.unitScenesGetUnitWhere("record.oppunitid", unitIdList, queryParamsVO)).append(" )");
            return;
        }
        if (!oppUnitEmpty) {
            whereSql.append(" and (").append(this.unitScenesGetUnitWhere(orgFieldName, oppUnitIdList, queryParamsVO));
            whereSql.append(" or ").append(this.unitScenesGetUnitWhere("record.oppunitid", oppUnitIdList, queryParamsVO)).append(" )");
        }
    }

    private String unitScenesGetUnitWhere(String sqlFieldName, List<String> unitIdList, QueryParamsVO queryParamsVO) {
        if (org.springframework.util.CollectionUtils.isEmpty(unitIdList)) {
            return "";
        }
        if (queryParamsVO.getEnableTempTableFilterUnitOrOppUnit().booleanValue()) {
            TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(unitIdList, (String)sqlFieldName);
            queryParamsVO.getTempGroupIdList().add(newConditionOfIds.getTempGroupId());
            return newConditionOfIds.getCondition();
        }
        return SqlUtils.getConditionOfIdsUseOr(unitIdList, (String)sqlFieldName);
    }

    private List<String> getOrgCodeForTree(List<GcOrgCacheVO> gcOrgCacheVOList) {
        ArrayList<String> orgCodeList = new ArrayList<String>();
        for (GcOrgCacheVO gcOrgCacheVO : gcOrgCacheVOList) {
            if (gcOrgCacheVO.getChildren().size() > 0) {
                orgCodeList.addAll(this.getOrgCodeForTree(gcOrgCacheVO.getChildren()));
                continue;
            }
            orgCodeList.add(gcOrgCacheVO.getCode());
        }
        return orgCodeList;
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

    private void initOrgTypeCondition(String orgTypeId, List<Object> params, StringBuffer whereSql) {
        params.add(orgTypeId);
        whereSql.append("and record.md_gcorgtype in('" + GCOrgTypeEnum.NONE.getCode().toString() + "', ?) \n");
    }

    private StringBuffer selectFields(QueryParamsVO queryParamsVO) {
        StringBuffer selectFields = new StringBuffer(32);
        if (queryParamsVO.isQueryAllColumns()) {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
            selectFields.append(SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"record"));
            selectFields.append(",record.amt").append("  AMMOUNT");
        } else {
            selectFields.append("record.ID,record.UNIONRULEID,record.").append("DATATIME").append(",record.").append("MDCODE").append("  UNITID,record.OPPUNITID,record.SUBJECTCODE,record.DC,record.amt").append("  AMMOUNT,record.MEMO");
            for (String code : queryParamsVO.getOtherShowColumns()) {
                selectFields.append(",record.").append(code);
            }
        }
        return selectFields;
    }

    private void initMergeUnitCondition(StringBuffer whereSql, List<Object> params, String parentGuids, String gcParentsStr, Date date, int codeLength) {
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        int len = gcParentsStr.length();
        whereSql.append(" substr(bfUnitTable.gcparents, 1, ").append(len + codeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + codeLength + 1).append(")\n");
        whereSql.append("and bfUnitTable.parents like ?\n");
        whereSql.append("and dfUnitTable.parents like ?\n");
        this.initValidtimeCondition(whereSql, params, date);
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

    private void initOtherCondition(StringBuffer whereSql, Map<String, Object> filterCondition, String schemeId, String periodStr) {
        if (!org.springframework.util.CollectionUtils.isEmpty(filterCondition)) {
            for (String key : filterCondition.keySet()) {
                Object subjectEOs;
                Object tempValue = filterCondition.get(key);
                if ("subjectId".equals(key)) {
                    List selectedSubjects = (List)tempValue;
                    ArrayList<String> subjectIds = new ArrayList<String>();
                    for (String uuid : selectedSubjects) {
                        ConsolidatedSubjectEO subjectEO = this.subjectService.getSubjectById(uuid);
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
                        if (org.springframework.util.CollectionUtils.isEmpty(values)) continue;
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

    private void addRuleWhereSql(StringBuffer whereSql, List tempValue) {
        List selectedRules = tempValue;
        if (org.springframework.util.CollectionUtils.isEmpty(selectedRules)) {
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
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            whereSql.append(" and record.ADJUST=? ");
            params.add(queryParamsVO.getSelectAdjustCode());
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
            if (!org.springframework.util.CollectionUtils.isEmpty(unitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getUnitWhere(unitIdList, queryParamsVO, service, ""));
                leftSign = "";
            }
            if (!org.springframework.util.CollectionUtils.isEmpty(oppUnitIdList)) {
                whereSql.append(" and ").append(leftSign).append(this.getOppUnitWhere(oppUnitIdList, queryParamsVO, service, ""));
                whereSql.append(orSign).append(this.getUnitWhere(oppUnitIdList, queryParamsVO, service, ""));
                orSign = " and ";
            }
            if (!org.springframework.util.CollectionUtils.isEmpty(unitIdList)) {
                whereSql.append(orSign).append(this.getOppUnitWhere(unitIdList, queryParamsVO, service, ""));
            }
            if (!org.springframework.util.CollectionUtils.isEmpty(unitIdList) || !org.springframework.util.CollectionUtils.isEmpty(oppUnitIdList)) {
                whereSql.append(")\n");
            }
        }
    }

    private String getUnitWhere(List<String> unitIdList, QueryParamsVO queryParamsVO, GcOrgCenterService service, String prefix) {
        if (!org.springframework.util.CollectionUtils.isEmpty(unitIdList)) {
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
        if (!org.springframework.util.CollectionUtils.isEmpty(unitIdList)) {
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
        if (direct.equals(OrientEnum.D.getValue())) {
            result.put("DEBITVALUE", NumberUtils.doubleToString((Double)ammount));
        } else {
            result.put("CREDITVALUE", NumberUtils.doubleToString((Double)ammount));
        }
        return result;
    }

    @Override
    public void updateInputDataToOffset(Map<String, Double> needModifyRecordId2offsetAmtMap, String offsetGroupId, String tableName) {
        if (org.springframework.util.CollectionUtils.isEmpty(needModifyRecordId2offsetAmtMap)) {
            return;
        }
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<InputDataEO> inputItems = new ArrayList<InputDataEO>();
        for (Map.Entry<String, Double> entry : needModifyRecordId2offsetAmtMap.entrySet()) {
            InputDataEO filter = new InputDataEO();
            filter.setId(entry.getKey());
            InputDataEO inputDataEO = (InputDataEO)dao.selectByEntity((BaseEntity)filter);
            if (null == inputDataEO) continue;
            inputDataEO.setDiffAmt(NumberUtils.sub((Double)inputDataEO.getAmt(), (Double)entry.getValue()));
            inputDataEO.setOffsetAmt(entry.getValue());
            inputDataEO.setOffsetGroupId(offsetGroupId);
            inputDataEO.setOffsetState(ReportOffsetStateEnum.OFFSET.getValue());
            dao.update((BaseEntity)inputDataEO);
            inputItems.add(inputDataEO);
        }
        this.updateOffsetInfoByConvertGroupId(inputItems, tableName);
        InputLoggerUtils.info(inputItems);
    }

    @Override
    public void updateInputDataToOffsetNoDiff(Set<String> needModifyRecordIdSet, String offsetGroupId, String tableName) {
        if (org.springframework.util.CollectionUtils.isEmpty(needModifyRecordIdSet)) {
            return;
        }
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<InputDataEO> inputItems = new ArrayList<InputDataEO>();
        for (String recordId : needModifyRecordIdSet) {
            InputDataEO filter = new InputDataEO();
            filter.setId(recordId);
            InputDataEO inputDataEO = (InputDataEO)dao.selectByEntity((BaseEntity)filter);
            if (null == inputDataEO) continue;
            inputDataEO.setDiffAmt(0.0);
            inputDataEO.setOffsetAmt(inputDataEO.getAmt());
            inputDataEO.setOffsetGroupId(offsetGroupId);
            inputDataEO.setOffsetState(ReportOffsetStateEnum.OFFSET.getValue());
            dao.update((BaseEntity)inputDataEO);
            inputItems.add(inputDataEO);
        }
        this.updateOffsetInfoByConvertGroupId(inputItems, tableName);
        InputLoggerUtils.info(inputItems);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<InputDataEO> queryIdLimitFieldsByOffsetGroupId(Collection<String> offsetGroupIds, String tableName) {
        List rs;
        TempTableCondition conditionOfIds = SqlUtils.getConditionOfIds(offsetGroupIds, (String)"e.offsetGroupId");
        String sql = String.format(SQL_QUERYINPUTDATA, "e.id  id, e.taskId  taskId, e.DATATIME as DATATIME, e.MD_CURRENCY as MD_CURRENCY ", "where " + conditionOfIds.getCondition(), tableName);
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        try {
            rs = dao.selectEntity(sql, new Object[0]);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)conditionOfIds.getTempGroupId());
        }
        return rs;
    }

    @Override
    public List<InputDataEO> queryByCondition(InputRuleFilterCondition condition) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(condition.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        String selectFields = SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"item");
        StringBuilder sql = new StringBuilder(" select ");
        sql.append(selectFields);
        sql.append("   from " + tableName + "  item \n   where 1= 1 \n");
        StringBuilder conditionSql = new StringBuilder();
        conditionSql.append("     and ").append(SqlUtils.getConditionOfIdsUseOr((Collection)condition.getRelUnitIds(), (String)"item.MDCODE"));
        conditionSql.append("     and ").append(SqlUtils.getConditionOfIdsUseOr((Collection)condition.getRelUnitIds(), (String)"item.oppUnitId"));
        sql.append(this.parseSqlConditon(condition, conditionSql));
        return dao.selectEntity(sql.toString(), new Object[0]);
    }

    @Override
    public List<InputDataEO> queryByRuleCondition(InputRuleFilterCondition condition) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(condition.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        String selectFields = SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"item");
        StringBuilder sql = new StringBuilder(" select ");
        sql.append(selectFields);
        sql.append("   from " + tableName + "  item \n   where 1= 1 \n");
        StringBuilder conditionSql = new StringBuilder();
        sql.append(this.parseSqlConditon(condition, conditionSql));
        return dao.selectEntity(sql.toString(), new Object[0]);
    }

    private String parseSqlConditon(InputRuleFilterCondition condition, StringBuilder conditionSql) {
        if (!StringUtils.isEmpty((String)condition.getTaskId()) && !StringUtils.isEmpty((String)condition.getNrPeriod())) {
            String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(condition.getTaskId(), condition.getNrPeriod());
            if (StringUtils.isEmpty((String)systemId)) {
                systemId = "\u4e0d\u5b58\u5728";
            }
            conditionSql.append(" and item.reportSystemId = '").append(systemId).append("' \n");
        }
        if (!StringUtils.isEmpty((String)condition.getRuleId())) {
            conditionSql.append("     and item.unionRuleId = '").append(condition.getRuleId()).append("' \n");
        }
        if (!StringUtils.isEmpty((String)condition.getNrPeriod())) {
            conditionSql.append("     and item.DATATIME = '").append(condition.getNrPeriod()).append("' \n");
        }
        if (!StringUtils.isEmpty((String)condition.getOffsetState())) {
            conditionSql.append("     and item.offsetState = '").append(condition.getOffsetState()).append("' \n");
        }
        if (!StringUtils.isEmpty((String)condition.getCheckState())) {
            conditionSql.append("     and item.CHECKSTATE = '").append(condition.getCheckState()).append("' \n");
        }
        if (!StringUtils.isEmpty((String)condition.getCurrency())) {
            conditionSql.append("     and item.MD_CURRENCY = '").append(condition.getCurrency()).append("' \n");
        }
        if (DimensionUtils.isExistAdjust((String)condition.getTaskId())) {
            conditionSql.append("     and item.ADJUST = '").append(condition.getSelectAdjustCode()).append("' \n");
        }
        return conditionSql.toString();
    }

    private String[] formatOffsetUncheckSqlSql(String sql, MergeCalcFilterCondition mergeArg, String tableName) {
        String columns = SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"e");
        YearPeriodObject yp = new YearPeriodObject(null, mergeArg.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)mergeArg.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrg = tool.getOrgByCode(mergeArg.getOrgId());
        String mergeUnitParents = gcOrg.getParentStr();
        int mergeUnitChildBeginIndex = gcOrg.getGcParentStr().length() + 2;
        int len = tool.getOrgCodeLength();
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(mergeArg.getTaskId(), mergeArg.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        String systemCond = " e.reportSystemId = '" + systemId + "' ";
        String sqlFormat = String.format(sql, columns, mergeArg.getOrgType(), mergeUnitChildBeginIndex, len, tableName, systemCond);
        return new String[]{sqlFormat, mergeUnitParents + "%"};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<String> queryByElmModeAndSrcOffsetGroupId(List<String> offsetgroupids, String tableName) {
        List rs;
        if (org.springframework.util.CollectionUtils.isEmpty(offsetgroupids)) {
            return Collections.emptySet();
        }
        String sql = "  select item.srcOffsetGroupId  SRCOFFSETGROUPID \n    from GC_OFFSETVCHRITEM  item \n   where %s \n     and item.elmMode in ( " + OffsetElmModeEnum.MANUAL_ITEM.getValue() + "," + OffsetElmModeEnum.BATCH_MANUAL_ITEM.getValue() + " ) \n";
        TempTableCondition tempTableCondition = SqlUtils.getNewConditionOfIds(offsetgroupids, (String)"item.srcOffsetGroupId");
        try {
            EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
            rs = dao.selectFirstList(String.class, String.format(sql, tempTableCondition.getCondition()), new Object[0]);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
        Set<String> manualOffsetgroupids = rs.stream().collect(Collectors.toSet());
        return manualOffsetgroupids;
    }

    @Override
    public List<InputDataEO> queryByRuleAndMergeCondition(String ruleId, MergeCalcFilterCondition mergeArg, String offsetState) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(mergeArg.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        StringBuffer SQLBuffer = new StringBuffer(SQL_GETINPUTDATAEOS);
        if (!StringUtils.isEmpty((String)offsetState)) {
            SQLBuffer.append(" and e.offsetState = '").append(offsetState).append("'");
        }
        if (DimensionUtils.isExistAdjust((String)mergeArg.getTaskId())) {
            SQLBuffer.append(" and e.ADJUST = '").append(mergeArg.getSelectAdjustCode()).append("' \n");
        }
        Date periodData = new YearPeriodObject(mergeArg.getSchemeId(), mergeArg.getPeriodStr()).formatYP().getEndDate();
        String[] sqlAndParam = this.formatOffsetUncheckSqlSql(SQLBuffer.toString(), mergeArg, tableName);
        return dao.selectEntity(sqlAndParam[0], new Object[]{periodData, periodData, periodData, periodData, mergeArg.getPeriodStr(), ruleId, sqlAndParam[1], sqlAndParam[1], mergeArg.getCurrency()});
    }

    @Override
    public List<DesignFieldDefineVO> queryUnOffsetColumnSelect(String tableName) {
        String[] notColumnSelectPartStr = new String[]{"ID", "RECVER", "TASKID", "DATATIME", "ORG", "RECORDTIMESTAMP", "FLOATORDER", "OFFSETGROUPID", "REPORTSYSTEMID", "EFDCMOREFILTERFML"};
        HashSet<String> notColumnSelectPart = new HashSet<String>();
        for (String code : notColumnSelectPartStr) {
            notColumnSelectPart.add(code);
        }
        I18nTableUtils i18nTableUtils = (I18nTableUtils)SpringContextUtils.getBean(I18nTableUtils.class);
        return i18nTableUtils.getAllFieldsByTableName(tableName, notColumnSelectPart);
    }

    @Override
    public void updateCustomInfoByKeys(Collection<InputDataEO> inputItems, Map<String, String> dimFieldValueMap, String tableName) {
        String sqlTemplate = " update " + tableName + " t \n set id=?,srcType=?, \n taskId=?,offsetState=?, \n reportSystemId=?,createUser=?,createTime=?, \n dc=?,unionRuleId=?,formId=?,recordTimestamp=?,updateTime=?,orgCode=?,subjectCode=?, CHECKSTATE=?,OFFSETAMT=?,DIFFAMT=?,CHECKAMT=? %s \n   where t." + "BIZKEYORDER" + " = ? \n";
        long recordTimestamp = System.currentTimeMillis();
        StringBuilder dimUpdateFieldsSql = new StringBuilder();
        String sql = String.format(sqlTemplate, dimUpdateFieldsSql);
        List param = inputItems.stream().map(inputItem -> {
            Object key = inputItem.getFields().get("BIZKEYORDER");
            if (key == null) {
                return null;
            }
            ArrayList<Serializable> argValues = new ArrayList<Serializable>(Arrays.asList(inputItem.getId(), inputItem.getSrcType(), inputItem.getTaskId(), inputItem.getOffsetState(), inputItem.getReportSystemId(), inputItem.getCreateUser(), inputItem.getCreateTime(), inputItem.getDc(), inputItem.getUnionRuleId(), inputItem.getFormId(), recordTimestamp, inputItem.getUpdateTime(), inputItem.getOrgCode(), inputItem.getSubjectCode(), inputItem.getCheckState(), inputItem.getOffsetAmt(), inputItem.getDiffAmt(), inputItem.getCheckAmt()));
            argValues.add((Serializable)key);
            return argValues;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        dao.executeBatch(sql, param);
        InputLoggerUtils.info(inputItems);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<InputDataEO> queryByIds(Collection<String> ids, String tableName) {
        List inputDataEOS;
        if (org.springframework.util.CollectionUtils.isEmpty(ids)) {
            return new ArrayList<InputDataEO>();
        }
        Set inputDataIds = ids.stream().filter(id -> !StringUtils.isNull((String)id)).collect(Collectors.toSet());
        if (org.springframework.util.CollectionUtils.isEmpty(inputDataIds)) {
            return new ArrayList<InputDataEO>();
        }
        String selectFields = SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"e");
        TempTableCondition condition = SqlUtils.getNewConditionOfIds(inputDataIds, (String)"e.BIZKEYORDER");
        try {
            String sql = String.format(SQL_QUERYINPUTDATABYKEYS, selectFields, condition.getCondition(), tableName);
            EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
            inputDataEOS = dao.selectEntity(sql, new Object[0]);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)condition.getTempGroupId());
        }
        if (org.springframework.util.CollectionUtils.isEmpty(inputDataEOS)) {
            return new ArrayList<InputDataEO>();
        }
        return inputDataEOS;
    }

    @Override
    public List<InputDataEO> queryUnOffsetRecordsByWhere(String[] selectColumnNamesInDB, String[] whereColumnNamesInDB, Object[] whereValues, String tableName) {
        StringBuffer whereSql = new StringBuffer(128);
        for (int i = 0; i < whereValues.length; ++i) {
            whereSql.append(" e.").append(SqlUtils.getConditionOfObject((Object)whereValues[i], (String)whereColumnNamesInDB[i])).append(" and");
        }
        if (whereSql.length() > 0) {
            whereSql.setLength(whereSql.length() - 4);
        }
        String selectFields = "";
        selectFields = CollectionUtils.isEmpty((Object[])selectColumnNamesInDB) ? SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"e") : SqlUtils.getNewColumnsSql((String[])selectColumnNamesInDB, (String)"e");
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        String sql = String.format(SQL_QUERYINPUTDATABYKEYS, selectFields, whereSql, tableName);
        return dao.selectEntity(sql, new Object[0]);
    }

    @Override
    public long updateOffsetInfo(List<InputDataEO> inputItems, String tableName) {
        String sql = " update " + tableName + " t  set offsetAmt=?,offsetTime=?,offsetGroupId=?, \n offsetState=?,diffAmt=?,offsetPerson=?, recordTimestamp=? \n   where t.id = ? and t.recordTimestamp = ? \n";
        long newRecordTimestamp = System.currentTimeMillis();
        if (org.springframework.util.CollectionUtils.isEmpty(inputItems)) {
            return newRecordTimestamp;
        }
        List param = inputItems.stream().map(m -> Arrays.asList(m.getOffsetAmt(), m.getOffsetTime(), m.getOffsetGroupId(), m.getOffsetState(), m.getDiffAmt(), m.getOffsetPerson(), newRecordTimestamp, m.getId(), m.getRecordTimestamp())).collect(Collectors.toList());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        dao.executeBatch(sql, param);
        inputItems.forEach(v -> v.setRecordTimestamp(newRecordTimestamp));
        InputLoggerUtils.info(inputItems);
        return newRecordTimestamp;
    }

    @Override
    public void updateOffsetInfoByConvertGroupId(List<InputDataEO> inputItems, String tableName) {
        if (org.springframework.util.CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        InputDataEO inputDataFirst = inputItems.get(0);
        if (Objects.isNull((Object)inputDataFirst) || StringUtils.isEmpty((String)inputDataFirst.getReportSystemId())) {
            return;
        }
        ConsolidatedOptionVO optionData = this.optionService.getOptionData(inputDataFirst.getReportSystemId());
        if (!optionData.getRealTimeConversion().booleanValue()) {
            return;
        }
        HashSet<String> idSet = new HashSet<String>();
        for (InputDataEO inputDataEO : inputItems) {
            idSet.add(inputDataEO.getId());
        }
        Map convertGroupIdToInputData = inputItems.stream().filter(item -> !StringUtils.isEmpty((String)item.getConvertGroupId())).collect(Collectors.toMap(InputDataEO::getConvertGroupId, Function.identity(), (o1, o2) -> o1));
        if (org.springframework.util.CollectionUtils.isEmpty(convertGroupIdToInputData.keySet())) {
            return;
        }
        String sql = "SELECT ID FROM " + tableName + " WHERE " + SqlBuildUtil.getStrInCondi((String)"ConvertGroupId", new ArrayList<String>(convertGroupIdToInputData.keySet()));
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        List<String> idList = dao.selectFirstList(String.class, sql, new Object[0]).stream().filter(item -> !idSet.contains(item)).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(idList)) {
            return;
        }
        List<InputDataEO> inputDataEOS = this.queryByIds(idList, tableName);
        inputDataEOS.forEach(item -> {
            InputDataEO srcInputDataEO = (InputDataEO)((Object)((Object)convertGroupIdToInputData.get(item.getConvertGroupId())));
            item.setOffsetAmt(srcInputDataEO.getOffsetAmt());
            item.setOffsetTime(srcInputDataEO.getOffsetTime());
            item.setDiffAmt(srcInputDataEO.getDiffAmt());
            if (!srcInputDataEO.getAmt().equals(0.0)) {
                item.setOffsetAmt(srcInputDataEO.getOffsetAmt() * item.getAmt() / srcInputDataEO.getAmt());
                item.setDiffAmt(item.getAmt() - item.getOffsetAmt());
            }
            item.setOffsetGroupId(srcInputDataEO.getOffsetGroupId());
            item.setOffsetState(srcInputDataEO.getOffsetState());
            item.setOffsetPerson(srcInputDataEO.getOffsetPerson());
        });
        this.updateOffsetInfo(inputDataEOS, tableName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> queryByIdAndRecordTimeStamp(Collection<String> inputItemIds, long recordTimestamp, String tableName) {
        List rs;
        String sql = "  select item.id  ID \n    from %2$s item \n   where %1$s \n     and item.recordTimestamp = ? \n";
        TempTableCondition tempTableCondition = SqlUtils.getNewConditionOfIds(inputItemIds, (String)"item.id");
        try {
            EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
            rs = dao.selectFirstList(String.class, String.format("  select item.id  ID \n    from %2$s item \n   where %1$s \n     and item.recordTimestamp = ? \n", tempTableCondition.getCondition(), tableName), new Object[]{recordTimestamp});
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
        return rs;
    }

    @Override
    public long updateRuleInfo(Collection<InputDataEO> inputItems, String tableName) {
        String sql = " update " + tableName + " t  set unionRuleId=?,dc=?,reportSystemId=?,recordTimestamp=?, UNCHECKAMT=? \n   where t.id = ? \n     and t.recordTimestamp = ? \n";
        long newRecordTimestamp = System.currentTimeMillis();
        if (org.springframework.util.CollectionUtils.isEmpty(inputItems)) {
            return newRecordTimestamp;
        }
        List param = inputItems.stream().map(m -> Arrays.asList(m.getUnionRuleId(), m.getDc(), m.getReportSystemId(), newRecordTimestamp, m.getUnCheckAmt(), m.getId(), m.getRecordTimestamp())).collect(Collectors.toList());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        dao.executeBatch(sql, param);
        inputItems.forEach(v -> v.setRecordTimestamp(newRecordTimestamp));
        InputLoggerUtils.info(inputItems);
        return newRecordTimestamp;
    }

    @Override
    public List<InputDataEO> queryCheckAmtErrorRecords(OffsetDataCheckVO checkCondition) {
        GcTaskBaseArguments taskBaseArguments = checkCondition.getTaskBaseArguments();
        String orgTable = "MD_ORG_CORPORATE".equals(checkCondition.getOrgType()) ? "MD_ORG_CORPORATE" : "MD_ORG_MANAGEMENT";
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(checkCondition.getTaskBaseArguments().getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(taskBaseArguments.getTaskId(), taskBaseArguments.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        String whereSql = " i.reportSystemId = '" + systemId + "' ";
        if (DimensionUtils.isExistAdjust((String)taskBaseArguments.getTaskId())) {
            whereSql = whereSql + " and i." + "ADJUST" + " = '" + checkCondition.getSelectAdjustCode() + "' \n";
        }
        String sql = String.format(SQL_QUERYCHECKAMTERRORRECORDS, SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"i"), tableName, orgTable, whereSql);
        YearPeriodObject yp = new YearPeriodObject(null, checkCondition.getTaskBaseArguments().getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)checkCondition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return dao.selectEntityByPaging(sql, checkCondition.getStartPosition(), checkCondition.getPageSize().intValue(), new Object[]{taskBaseArguments.getTaskId(), taskBaseArguments.getPeriodStr(), checkCondition.getCurrency(), tool.getOrgByCode(checkCondition.getOrgId()).getParents(), tool.getOrgByCode(checkCondition.getOrgId()).getParents()});
    }

    @Override
    public void cancelLockedOffset(String lockId, String tableName) {
        String sql = "  update " + tableName + "  i \n     set offsetgroupId=null,offsetState='0',diffamt=0,         offsetamt=0,recordTimestamp=? \n   where i.offsetGroupId in \n         (select l.offsetGroupId from " + "GC_INPUTDATALOCK" + "  l \n           where l.lockId = ?) \n   and i.offsetGroupId is not null ";
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        dao.execute(sql, new Object[]{LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli(), lockId});
    }

    @Override
    public void cancelLockedOffsetByCurrency(String lockId, String tableName, List<String> currencyList) {
        String sql = "  update " + tableName + "  i \n     set offsetgroupId=null,offsetState='0',diffamt=0,         offsetamt=0,recordTimestamp=? \n   where i.offsetGroupId in \n         (select l.offsetGroupId from " + "GC_INPUTDATALOCK" + "  l \n           where l.lockId = ?) \n  and " + SqlUtils.getConditionOfMulStrUseOr(currencyList, (String)"i.MD_CURRENCY") + "   and i.offsetGroupId is not null ";
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        dao.execute(sql, new Object[]{LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli(), lockId});
    }

    @Override
    public List<InputDataEO> queryByTaskAndDimensions(String taskId, Map<String, String> dimensions) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        String sql = "  select %s \n    from %s  i \n   where %s \n     and i.MDCODE = ? \n     and i.DATATIME = ? \n     and i.MD_CURRENCY = ? \n     and i.MD_GCORGTYPE = ? \n";
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(taskId, dimensions.get("DATATIME"));
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        String whereSql = " i.reportSystemId = '" + systemId + "' ";
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            whereSql = whereSql + " and i." + "ADJUST" + " = '" + dimensions.get("ADJUST") + "' \n";
        }
        return dao.selectEntity(String.format(sql, SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"i"), tableName, whereSql), new Object[]{dimensions.get("MDCODE"), dimensions.get("DATATIME"), dimensions.get("MD_CURRENCY"), dimensions.get("MD_GCORGTYPE")});
    }

    @Override
    public void udpateRelationToMergeInputData(List<InputDataDTO> inputItemDTOs, String tableName) {
        if (org.springframework.util.CollectionUtils.isEmpty(inputItemDTOs)) {
            return;
        }
        ArrayList<InputDataEO> inputItems = new ArrayList<InputDataEO>();
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        for (int i = 0; i < inputItemDTOs.size(); ++i) {
            InputDataDTO inputDataDTO = inputItemDTOs.get(i);
            InputDataEO filter = new InputDataEO();
            filter.setId(inputDataDTO.getId());
            InputDataEO inputDataEO = (InputDataEO)dao.selectByEntity((BaseEntity)filter);
            if (null == inputDataEO) continue;
            inputDataEO.setOffsetAmt(0.0);
            inputDataEO.setDiffAmt(0.0);
            inputDataEO.setOffsetGroupId(inputDataDTO.getOffsetGroupId());
            inputDataEO.setOffsetState(inputDataDTO.getOffsetState());
            inputDataEO.setOffsetTime(inputDataDTO.getOffsetTime());
            inputItems.add(inputDataEO);
        }
        this.updateOffsetAndRelInfo(inputItems, dao);
    }

    private long updateOffsetAndRelInfo(List<InputDataEO> inputItems, EntNativeSqlDefaultDao<InputDataEO> dao) {
        String sql = "update " + dao.getTableName() + " t \nset offsetAmt=?,diffAmt=?,offsetGroupId=?,offsetState=?,offsetTime=? \n where t.id = ? \n";
        long newRecordTimestamp = System.currentTimeMillis();
        if (org.springframework.util.CollectionUtils.isEmpty(inputItems)) {
            return newRecordTimestamp;
        }
        List param = inputItems.stream().map(m -> Arrays.asList(m.getOffsetAmt(), m.getDiffAmt(), m.getOffsetGroupId(), m.getOffsetState(), m.getOffsetTime(), m.getId())).collect(Collectors.toList());
        dao.executeBatch(sql, param);
        inputItems.forEach(v -> v.setRecordTimestamp(newRecordTimestamp));
        InputLoggerUtils.info(inputItems);
        return newRecordTimestamp;
    }

    @Override
    public List<InputDataEO> queryInputDataForRelationToMerge(QueryParamsVO queryParamsVO) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = tool.getOrgByCode(queryParamsVO.getOrgId());
        if (null == orgCacheVO || orgCacheVO.getParentStr() == null) {
            return new ArrayList<InputDataEO>();
        }
        String parentGuids = orgCacheVO.getParentStr();
        int len = orgCacheVO.getGcParentStr().length();
        StringBuffer sql = new StringBuffer(512);
        sql.append(" select %s \n");
        sql.append(" from ").append(tableName).append("  i \n");
        sql.append(" left join ").append(queryParamsVO.getOrgType()).append("  bfUnitTable on (i.MDCODE = bfUnitTable.code)\n");
        sql.append(" left join ").append(queryParamsVO.getOrgType()).append("  dfUnitTable on (i.oppunitid = dfUnitTable.code)\n");
        sql.append(" where ");
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        sql.append(" i.reportSystemId = '").append(systemId).append("' \n");
        sql.append(" and i.DATATIME = ? \n");
        sql.append(" and i.MD_CURRENCY = ? \n");
        for (Map.Entry entry : queryParamsVO.getFilterCondition().entrySet()) {
            String dimensionCode = (String)entry.getKey();
            if (entry.getValue() != null) {
                sql.append(" and i." + dimensionCode + "='" + entry.getValue().toString() + "' \n");
                continue;
            }
            sql.append(" and (i." + dimensionCode + "='' \n");
            sql.append(" or i." + dimensionCode + " = null) \n");
        }
        sql.append(" and substr(bfUnitTable.gcparents, 1, ").append(len + tool.getOrgCodeLength() + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + tool.getOrgCodeLength() + 1).append(")\n");
        sql.append(" and bfUnitTable.parents like ?\n");
        sql.append(" and dfUnitTable.parents like ?\n");
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            sql.append("  and i.ADJUST = '").append(queryParamsVO.getSelectAdjustCode()).append("' \n");
        }
        String formatSql = String.format(sql.toString(), SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"i"));
        return dao.selectEntity(formatSql, new Object[]{queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr(), queryParamsVO.getCurrency(), parentGuids + "%", parentGuids + "%"});
    }

    @Override
    public void updateMemoById(String id, String memo, String tableName) {
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        String sql = " update " + tableName + "  t \n    set memo=? \n  where t.id=? \n";
        dao.execute(sql, new Object[]{memo, id});
    }

    @Override
    public void updateRuleAndDcById(Collection<InputDataEO> inputItems, String tableName) {
        String sql = " update " + tableName + " t  set unionRuleId=?,dc=?, UNCHECKAMT=?, CHECKSTATE=? \n   where t.id = ? \n";
        if (org.springframework.util.CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        List param = inputItems.stream().map(m -> Arrays.asList(m.getUnionRuleId(), m.getDc(), m.getUnCheckAmt(), m.getCheckState(), m.getId())).collect(Collectors.toList());
        dao.executeBatch(sql, param);
        List paramConvert = inputItems.stream().map(m -> Arrays.asList(m.getUnionRuleId(), m.getDc(), m.getId())).collect(Collectors.toList());
        String converDataSql = " update " + tableName + " t  set unionRuleId=?,dc=? \n   where  convertgroupid in (select convertgroupid from (select convertgroupid from " + tableName + " where id=? and convertgroupid is not null ) t ) \n";
        dao.executeBatch(converDataSql, paramConvert);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> listSubjectCodeBySystemIdAndSubjectCode(String dataSchemeKey, String systemId, Set<String> keySet) {
        String tableName = this.inputDataNameProvider.getTableNameByDataSchemeKey(dataSchemeKey);
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        TempTableCondition conditionOfIds = SqlUtils.getConditionOfIds(keySet, (String)"SUBJECTCODE");
        String sql = "SELECT SUBJECTCODE FROM " + tableName + " \n WHERE REPORTSYSTEMID= ? AND " + conditionOfIds.getCondition() + "\n  GROUP BY SUBJECTCODE";
        ArrayList<String> res = new ArrayList();
        try {
            res = dao.selectFirstList(String.class, sql, new Object[]{systemId});
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)conditionOfIds.getTempGroupId());
        }
        return res;
    }

    @Override
    public List<InputDataEO> queryByManualBatchOffsetParams(QueryParamsVO queryParamsVO, String offsetState, List<Map<String, Object>> unOffsetDatas, ManualBatchOffsetParamsVO manualBatchOffsetParamsVO) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        if (manualBatchOffsetParamsVO.isChooseFilter()) {
            return this.queryByIds(unOffsetDatas.stream().map(unoffset -> (String)unoffset.get("ID")).collect(Collectors.toList()), tableName);
        }
        return this.queryByIds(manualBatchOffsetParamsVO.getRecordIds(), tableName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<InputDataEO> queryManualConversionInputDataByOffsetGroupIds(Collection<String> offsetGroupIds, String currencyCode, String tableName) {
        List inputDataEOS;
        if (org.springframework.util.CollectionUtils.isEmpty(offsetGroupIds)) {
            return new ArrayList<InputDataEO>();
        }
        String selectFields = SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"e");
        TempTableCondition condition = SqlUtils.getNewConditionOfIds(offsetGroupIds, (String)"e.offsetGroupId");
        try {
            String sql = String.format(SQL_QUERYINPUTDATABYKEYS, selectFields, condition.getCondition(), tableName);
            if (!StringUtils.isEmpty((String)currencyCode)) {
                sql = sql + "and e." + "MD_CURRENCY" + "='" + currencyCode + "'";
            }
            sql = sql + "and e.CONVERTGROUPID is not null";
            EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
            inputDataEOS = dao.selectEntity(sql, new Object[0]);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)condition.getTempGroupId());
        }
        if (org.springframework.util.CollectionUtils.isEmpty(inputDataEOS)) {
            return new ArrayList<InputDataEO>();
        }
        return inputDataEOS;
    }

    @Override
    public void updateOffsetInfoManualConversion(List<InputDataEO> inputItems, String tableName) {
        String sql = "update " + tableName + " t \nset offsetGroupId=?,offsetState=?,SRCTYPE=? \n where t.id = ? and t.CONVERTGROUPID =?\n";
        if (org.springframework.util.CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        List param = inputItems.stream().map(m -> Arrays.asList(m.getOffsetGroupId(), m.getOffsetState(), m.getSrcType(), m.getId(), m.getConvertGroupId())).collect(Collectors.toList());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        dao.executeBatch(sql, param);
    }

    private String[] formatManualBatchOffsetSql(String sql, QueryParamsVO queryParamsVO, String tableName) {
        String columns = SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"record");
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrg = tool.getOrgByCode(queryParamsVO.getOrgId());
        String mergeUnitParents = gcOrg.getParentStr();
        int mergeUnitChildBeginIndex = gcOrg.getGcParentStr().length() + 2;
        int len = tool.getOrgCodeLength();
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(SqlUtils.getConditionOfIdsUseOr((Collection)queryParamsVO.getRules(), (String)"record.unionruleid"));
        this.initUnitCondition(queryParamsVO, whereSql, tool);
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            whereSql.append("     and record.ADJUST = '").append(queryParamsVO.getSelectAdjustCode()).append("' \n");
        }
        String sqlFormat = String.format(sql, columns, queryParamsVO.getOrgType(), mergeUnitChildBeginIndex, len, tableName, whereSql.toString());
        return new String[]{sqlFormat, mergeUnitParents + "%"};
    }

    @Override
    public int getUnOffsetInputDataItemCount(QueryParamsVO queryParamsVO) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(queryParamsVO.getTaskId());
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrg = tool.getOrgByCode(queryParamsVO.getOrgId());
        int mergeUnitChildBeginIndex = gcOrg.getGcParentStr().length();
        int codeLength = tool.getOrgCodeLength();
        StringBuffer whereSql = new StringBuffer();
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            whereSql.append(" and record.ADJUST = '").append(queryParamsVO.getSelectAdjustCode()).append("' \n");
        }
        String sql = String.format(SQL_GETMANULAINPUTDATAEOS, "record.ID", queryParamsVO.getOrgType(), 1, mergeUnitChildBeginIndex + codeLength + 1, tableName, whereSql.toString());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<Object> params = new ArrayList<Object>();
        Date date = yp.formatYP().getEndDate();
        params.add(date);
        params.add(date);
        params.add(date);
        params.add(date);
        params.add(queryParamsVO.getPeriodStr());
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        params.add(systemId);
        params.add(gcOrg.getGcParentStr() + "%");
        params.add(gcOrg.getGcParentStr() + "%");
        params.add("0");
        params.add(queryParamsVO.getCurrency());
        int totalCount = dao.count(sql, params);
        return totalCount;
    }
}

