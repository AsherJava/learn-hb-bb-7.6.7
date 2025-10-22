/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.enums.GcBusinessTypeQueryRuleEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi
 */
package com.jiuqi.gcreport.workingpaper.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.enums.GcBusinessTypeQueryRuleEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOffSetVchrItemAdjustDao;
import com.jiuqi.gcreport.workingpaper.dao.impl.ArbitrarilyMergeOffsetVchrQueryImpl;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArbitrarilyMergeOffSetVchrItemAdjustDaoImpl
extends GcDbSqlGenericDAO<ArbitrarilyMergeOffSetVchrItemAdjustEO, String>
implements ArbitrarilyMergeOffSetVchrItemAdjustDao {
    private static DecimalFormat df = new DecimalFormat("0000");
    public static final String ORG_TEMPORARY_TABLENAME = "GC_ORGTEMPORARY_AM";
    private static final String SQL_QUERY_RY_OFFSETVCHRITEM = "select %s from GC_RY_OFFSETVCHRITEM  e \n %s \n";
    @Autowired
    private ArbitrarilyMergeOffsetVchrQueryImpl ryOffsetVchrQuery;
    private static final String CHECK_AMT_INFO = "\u62b5\u9500\u5206\u5f55\u6570\u636e\u4e2d\u3010%1$s\u3011\u5b57\u6bb5\u503c\u4e3a\u3010%2$s\u3011\u4e0e\u9ed8\u8ba4\u7cbe\u5ea62\u4e0d\u7b26\uff01";

    public ArbitrarilyMergeOffSetVchrItemAdjustDaoImpl() {
        super(ArbitrarilyMergeOffSetVchrItemAdjustEO.class);
    }

    @Override
    public void deleteRyBySrcOffsetGroupIds(String taskId, Collection<String> srcOffsetGroupIds, int acctYear, int effectType, int acctPeriod, String orgType, String currencyCode, int offSetSrcType, String selectAdjustCode) {
        if (srcOffsetGroupIds == null || srcOffsetGroupIds.size() <= 0) {
            return;
        }
        CalcLogUtil.getInstance().log(this.getClass(), "deleteRyBySrcOffsetGroupIds", new Object[]{srcOffsetGroupIds, acctYear, effectType, acctPeriod, orgType, offSetSrcType});
        String sql = "delete from GC_RY_OFFSETVCHRITEM  \n   where \n" + SqlUtils.getConditionOfIdsUseOr(srcOffsetGroupIds, (String)"srcOffsetGroupId");
        sql = effectType >= 0 ? sql + " and substr(dataTime, 1, 4) = '" + acctYear + "' and substr(dataTime, 6, 4) >= '" + df.format(acctPeriod) + "'\n" : sql.concat(" and dataTime like '%").concat(acctYear + "_" + df.format(acctPeriod)).concat("'\n");
        if (orgType != null) {
            sql = sql + "and UNITVERSION = '" + orgType + "'\n";
        }
        if (!StringUtils.isEmpty((String)currencyCode)) {
            sql = sql + "and offSetCurr = '" + currencyCode + "'\n";
        }
        this.execute(sql);
    }

    @Override
    public void deleteByOffsetGroupIdsAndSrcType(Collection<String> srcOffsetGroupIds, Integer offSetSrcType, GcTaskBaseArguments baseArguments) {
        if (srcOffsetGroupIds == null || srcOffsetGroupIds.size() <= 0) {
            return;
        }
        String sql = "select id from GC_RY_OFFSETVCHRITEM  \n  where " + SqlUtils.getConditionOfIdsUseOr(srcOffsetGroupIds, (String)"srcOffsetGroupId") + this.whereSql(baseArguments);
        if (baseArguments.getOrgType() != null) {
            sql = sql + "and UNITVERSION = '" + baseArguments.getOrgType() + "'\n";
        }
        if (offSetSrcType != null) {
            sql = sql + "and offSetSrcType = '" + offSetSrcType + "'\n";
        }
        List idList = this.selectFirstList(String.class, sql, new Object[0]);
        this.deleteRyByMrecids(idList);
    }

    private StringBuffer whereSql(GcTaskBaseArguments baseArguments) {
        StringBuffer whereSql = new StringBuffer(64);
        if (baseArguments == null) {
            return whereSql;
        }
        if (null != baseArguments.getAcctYear()) {
            whereSql.append(" and substr(dataTime,1,4) = '").append(baseArguments.getAcctYear()).append("'\n");
        }
        if (null != baseArguments.getAcctPeriod() && null != baseArguments.getAcctYear()) {
            whereSql.append(" and substr(dataTime,6,4) = '").append(df.format(baseArguments.getAcctPeriod())).append("'\n");
        }
        if (!StringUtils.isEmpty((String)baseArguments.getCurrency())) {
            whereSql.append(" and offSetCurr = '").append(baseArguments.getCurrency()).append("'\n");
        }
        return whereSql;
    }

    @Override
    public void deleteRyByMrecids(List<String> mrecids, String taskId, Integer acctYear, Integer acctPeriod, String orgTypeId, String currencyCode, String adjustCode) {
        if (mrecids == null || mrecids.size() <= 0) {
            return;
        }
        String sql = "delete from GC_RY_OFFSETVCHRITEM  \n  where " + SqlUtils.getConditionOfIdsUseOr(mrecids, (String)"mrecid") + this.whereDelRySql(taskId, acctYear, acctPeriod, currencyCode, adjustCode);
        this.execute(sql);
    }

    @Override
    public void deleteRyByMrecids(List<String> mrecids) {
        if (mrecids == null || mrecids.size() <= 0) {
            return;
        }
        String sql = "delete from GC_RY_OFFSETVCHRITEM  \n  where " + SqlUtils.getConditionOfIdsUseOr(mrecids, (String)"mrecid");
        this.execute(sql);
    }

    @Override
    public List<ArbitrarilyMergeOffSetVchrItemAdjustEO> queryRyOffsetRecordsByWhere(String[] columnNamesInDB, Object[] values, ArbitrarilyMergeInputAdjustQueryCondi condi) {
        StringBuffer conditionSql = new StringBuffer(128);
        for (int i = 0; i < values.length; ++i) {
            conditionSql.append(SqlUtils.getConditionOfObject((Object)values[i], (String)(" e." + columnNamesInDB[i]))).append(" and");
        }
        String whereSql = "";
        if (conditionSql.length() > 0) {
            conditionSql.setLength(conditionSql.length() - 4);
            whereSql = " where " + conditionSql;
        }
        if (DimensionUtils.isExistAdjust((String)condi.getTaskId())) {
            whereSql = whereSql + " and record.ADJUST= '" + condi.getSelectAdjustCode() + "'";
        }
        whereSql = whereSql + " order by e.sortorder";
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_RY_OFFSETVCHRITEM", (String)"e");
        String sql = String.format(SQL_QUERY_RY_OFFSETVCHRITEM, selectFields, whereSql);
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public Pagination<Map<String, Object>> queryRyOffsetingEntry(QueryParamsVO queryParamsVO) {
        Map filterCondition = queryParamsVO.getFilterCondition();
        if (filterCondition != null) {
            queryParamsVO.setGcBusinessTypeQueryRule(ConverterUtils.getAsString(filterCondition.get("gcBusinessTypeQueryRule")));
            if (filterCondition.get("gcbusinesstypecode") != null) {
                queryParamsVO.setGcBusinessTypeCodes((List)filterCondition.get("gcbusinesstypecode"));
            }
            filterCondition.remove("gcbusinesstypecode");
            filterCondition.remove("gcBusinessTypeQueryRule");
        }
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(queryParamsVO.getPageNum()), Integer.valueOf(queryParamsVO.getPageSize()));
        HashSet<String> mRecids = new HashSet<String>();
        int totalCount = this.queryRyMrecids(queryParamsVO, null, mRecids);
        if (CollectionUtils.isEmpty(mRecids)) {
            page.setContent(new ArrayList());
            return page;
        }
        List<Map<String, Object>> datas = this.queryRyOffsetingEntryByMrecids(queryParamsVO, mRecids);
        page.setTotalElements(Integer.valueOf(totalCount));
        page.setContent(datas);
        return page;
    }

    private void initMergeUnitCondition(QueryParamsVO queryParamsVO, StringBuilder head, StringBuilder whereSql, List<Object> params, String parentGuids, Date date, int orgCodeLength, String gcParentStr) {
        String orgTypeId = queryParamsVO.getOrgType();
        String emptyUUID = "NONE";
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            whereSql.append("where substr(bfUnitTable.parents, 1, ").append(queryParamsVO.getOrgComSupLength()).append(" ) <> substr(dfUnitTable.parents, 1, ").append(queryParamsVO.getOrgComSupLength()).append(")\n");
            params.add(orgTypeId);
            whereSql.append(" and record.UNITVERSION in('" + emptyUUID + "',?) \n");
        } else {
            int len = gcParentStr.length();
            whereSql.append("where (substr(bfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(")\n");
            whereSql.append("and bfUnitTable.parents like ?\n");
            whereSql.append("and dfUnitTable.parents like ?\n");
            params.add(parentGuids + "%");
            params.add(parentGuids + "%");
            params.add(orgTypeId);
            whereSql.append(" and record.md_gcorgtype in('" + emptyUUID + "',?) and record.inputunitid ='" + emptyUUID + "' \n");
            if (null != orgTypeId) {
                whereSql.append(" or record.md_gcorgtype = ? and record.inputunitid=?\n)\n");
                params.add(orgTypeId);
                params.add(queryParamsVO.getOrgId());
            }
            this.ryOffsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
        }
        if (queryParamsVO.isDelete() && !"MD_ORG_CORPORATE".equals(queryParamsVO.getOrgType())) {
            whereSql.append(" and record.inputunitid <> '").append(emptyUUID).append("'\n");
        }
    }

    @Override
    public int queryRyMrecids(QueryParamsVO queryParamsVO, Set<String> srcOffsetGroupIdResults, Set<String> mRecids) {
        String countSql;
        int count;
        String queryFields = "record.mrecid";
        if (null != srcOffsetGroupIdResults) {
            queryFields = "record.mrecid,record.elmMode,record.srcOffsetGroupId";
        }
        if (null == mRecids) {
            mRecids = new HashSet<String>();
        }
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        YearPeriodObject yp = new YearPeriodObject(null, this.ryOffsetVchrQuery.getQueryOrgPeriod(queryParamsVO.getPeriodStr()));
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        String orgTable = Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) ? ORG_TEMPORARY_TABLENAME : orgService.getCurrOrgType().getTable();
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder head = new StringBuilder(32);
        StringBuilder whereSql = new StringBuilder(32);
        Date date = yp.formatYP().getEndDate();
        if (!StringUtils.isEmpty((String)queryParamsVO.getOrgId())) {
            GcOrgCacheVO org = orgService.getOrgByCode(queryParamsVO.getOrgId());
            if (null == org || org.getParentStr() == null) {
                return 0;
            }
            String parentGuids = org.getParentStr();
            String gcParentStr = org.getGcParentStr();
            this.initMergeUnitCondition(queryParamsVO, head, whereSql, params, parentGuids, date, orgService.getOrgCodeLength(), gcParentStr);
        } else if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            this.initMergeUnitCondition(queryParamsVO, head, whereSql, params, null, date, orgService.getOrgCodeLength(), null);
        } else {
            if (CollectionUtils.isEmpty((Collection)queryParamsVO.getUnitIdList())) {
                return 0;
            }
            whereSql.append("where 1=1 ");
            this.ryOffsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
        }
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            whereSql.append(" and record.ADJUST=").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        this.ryOffsetVchrQuery.initUnitCondition(queryParamsVO, whereSql, orgService);
        this.ryOffsetVchrQuery.initPeriodCondition(queryParamsVO, params, whereSql);
        this.ryOffsetVchrQuery.initOtherCondition(whereSql, queryParamsVO);
        if (queryParamsVO.isFilterDisableItem()) {
            whereSql.append(" and (record.disableFlag<>1 or record.disableFlag is null )\n");
        }
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(queryFields).append(" from ").append("GC_RY_OFFSETVCHRITEM").append("  record\n");
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            sql.append("join ").append(orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n").append(" and bfUnitTable.batchId='").append(queryParamsVO.getOrgBatchId()).append("' ");
            sql.append("join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n").append(" and dfUnitTable.batchId='").append(queryParamsVO.getOrgBatchId()).append("' ");
        } else {
            sql.append("join ").append(orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n");
            sql.append("join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        }
        sql.append((CharSequence)whereSql);
        sql.append(" group by ").append(queryFields).append("\n");
        sql.append(" order by record.mrecid desc\n");
        int begin = -1;
        int range = -1;
        if (pageNum > 0 && pageSize > 0) {
            begin = (pageNum - 1) * pageSize;
            range = pageNum * pageSize;
        }
        if ((count = this.count(countSql = String.format("select count(*) from ( %1$s )  t", (sql = this.processingSqlByGcBusinessTypeQueryRule(sql, queryFields, queryParamsVO)).toString()), params)) < 1) {
            return 0;
        }
        List rs = this.selectMapByPaging(sql.toString(), begin, range, params);
        for (Map d : rs) {
            String value;
            int elmMode;
            mRecids.add(String.valueOf(d.get("MRECID")));
            if (d.size() <= 2 || (elmMode = ((Integer)d.get("ELMMODE")).intValue()) == OffsetElmModeEnum.WRITE_OFF_ITEM.getValue() || null == (value = String.valueOf(d.get("SRCOFFSETGROUPID")))) continue;
            srcOffsetGroupIdResults.add(value);
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> queryRyOffsetingEntryByMrecids(QueryParamsVO queryParamsVO, Set<String> mRecids) {
        String currency = queryParamsVO.getCurrencyUpperCase();
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append("record.ID,record.MRECID,record.ELMMODE,record.ORIENT,record.gcBusinessTypeCode,record.ruleId,record.UNITID,record.OPPUNITID,record.SUBJECTCODE,record.offset_Credit_" + currency + " as offsetCredit,record.offset_DEBIT_" + currency + " as offsetDEBIT,record.DIFFC_" + currency + " as DIFFC,record.DIFFD_" + currency + " as DIFFD,record.MEMO,record.OFFSETSRCTYPE as SRCTYPE,record.SRCOFFSETGROUPID,record.DISABLEFLAG");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_RY_OFFSETVCHRITEM").append("  record\n");
        sql.append("where ").append(SqlUtils.getConditionOfIdsUseOr(mRecids, (String)" record.mrecid")).append("\n");
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            sql.append(" and record.ADJUST=").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        sql.append(" and record.UNITVERSION in('" + GCOrgTypeEnum.NONE.getCode() + "', ? ) \n");
        sql.append("order by record.mrecid desc\n");
        List datas = this.selectMap(sql.toString(), new Object[]{queryParamsVO.getOrgType()});
        datas.forEach(item -> this.ryOffsetVchrQuery.convert((Map<String, Object>)item));
        return datas;
    }

    private StringBuffer processingSqlByGcBusinessTypeQueryRule(StringBuffer sql, String queryFields, QueryParamsVO queryParamsVO) {
        String operator;
        if (CollectionUtils.isEmpty((Collection)queryParamsVO.getGcBusinessTypeCodes())) {
            return sql;
        }
        GcBusinessTypeQueryRuleEnum enumByCode = GcBusinessTypeQueryRuleEnum.getEnumByCode((String)queryParamsVO.getGcBusinessTypeQueryRule());
        if (enumByCode == null) {
            return sql;
        }
        switch (enumByCode) {
            case ALL: {
                return sql;
            }
            case EQ: {
                operator = " = ";
                break;
            }
            case ACROSS: {
                operator = " <> ";
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u672a\u8bc6\u522b\u7684\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u67e5\u8be2\u89c4\u5219\uff1a" + enumByCode);
            }
        }
        StringBuffer processingSql = new StringBuffer();
        processingSql.append("select ").append(queryFields).append(" from ").append("GC_OFFSETVCHRITEM").append(" record \n");
        processingSql.append(" where mrecid in (select mrecid  from ").append("GC_OFFSETVCHRITEM").append(" t \n");
        processingSql.append("                   where t.mrecid in ( select mrecid  from (").append(sql).append(") temp )      \n");
        processingSql.append("                   group by t.mrecid     \n");
        processingSql.append("                   having max(gcbusinesstypecode)").append(operator).append("min(gcbusinesstypecode) \n");
        processingSql.append("                  )        \n");
        processingSql.append(" group by ").append(queryFields).append("\n");
        processingSql.append(" order by record.mrecid desc\n");
        return processingSql;
    }

    @Override
    public List<Map<String, Object>> sumRyOffsetValueGroupBySubjectcode(QueryParamsVO queryParamsVO) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder head = new StringBuilder(32);
        StringBuilder whereSql = new StringBuilder(32);
        if (!this.getRyOffsetEntryWhereSql(queryParamsVO, params, head, whereSql)) {
            return new ArrayList<Map<String, Object>>();
        }
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        String orgTable = Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) ? ORG_TEMPORARY_TABLENAME : orgService.getCurrOrgType().getTable();
        String currency = queryParamsVO.getCurrencyUpperCase();
        String selectFields = " record.UNITID,record.SUBJECTCODE,record.SUBJECTORIENT,record.OFFSETSRCTYPE,record.ELMMODE,record.GCBUSINESSTYPECODE,sum(record.OFFSET_DEBIT_" + currency + ") as OFFSET_DEBIT_VALUE,sum(record.OFFSET_CREDIT_" + currency + ") as OFFSET_CREDIT_VALUE, sum(record.DIFFD_" + currency + ") as DIFFD_VALUE, sum(record.DIFFC_" + currency + ") as DIFFC_VALUE, count(1) as OFFSETCOUNT ";
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_RY_OFFSETVCHRITEM").append("  record\n");
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            sql.append("join ").append(orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n").append(" and bfUnitTable.batchId='").append(queryParamsVO.getOrgBatchId()).append("' ");
            sql.append("join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n").append(" and dfUnitTable.batchId='").append(queryParamsVO.getOrgBatchId()).append("' ");
        } else {
            sql.append("join ").append(orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n");
            sql.append("join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        }
        sql.append((CharSequence)whereSql);
        sql.append("group by record.unitid,record.subjectCode,record.SUBJECTORIENT,record.OFFSETSRCTYPE,record.ELMMODE,record.GCBUSINESSTYPECODE\n");
        List datas = this.selectMap(sql.toString(), params.toArray());
        return datas;
    }

    private boolean getRyOffsetEntryWhereSql(QueryParamsVO queryParamsVO, List<Object> params, StringBuilder head, StringBuilder whereSql) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        Date date = yp.formatYP().getEndDate();
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            this.initRyMergeUnitCondition(queryParamsVO, head, whereSql, params, null, date, orgService.getOrgCodeLength(), null);
        } else {
            Assert.isNotNull((Object)queryParamsVO.getOrgId());
            GcOrgCacheVO org = orgService.getOrgByCode(queryParamsVO.getOrgId());
            if (null == org || org.getParentStr() == null) {
                return false;
            }
            String parentGuids = org.getParentStr();
            String gcParentStr = org.getGcParentStr();
            this.initRyMergeUnitCondition(queryParamsVO, head, whereSql, params, parentGuids, date, orgService.getOrgCodeLength(), gcParentStr);
        }
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            whereSql.append(" and record.ADJUST=").append("'").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        this.ryOffsetVchrQuery.initUnitCondition(queryParamsVO, whereSql, orgService);
        this.ryOffsetVchrQuery.initPeriodCondition(queryParamsVO, params, whereSql);
        this.ryOffsetVchrQuery.initOtherCondition(whereSql, queryParamsVO);
        whereSql.append(" and (record.disableFlag<>? or record.disableFlag is null )\n");
        params.add(1);
        return true;
    }

    private void initRyMergeUnitCondition(QueryParamsVO queryParamsVO, StringBuilder head, StringBuilder whereSql, List<Object> params, String parentGuids, Date date, int orgCodeLength, String gcParentStr) {
        String orgTypeId = queryParamsVO.getOrgType();
        String emptyUUID = "NONE";
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            whereSql.append("where substr(bfUnitTable.parents, 1, ").append(queryParamsVO.getOrgComSupLength()).append(" ) <> substr(dfUnitTable.parents, 1, ").append(queryParamsVO.getOrgComSupLength()).append(")\n");
            params.add(orgTypeId);
            whereSql.append(" and record.UNITVERSION in('" + emptyUUID + "',?) \n");
        } else {
            int len = gcParentStr.length();
            whereSql.append("where (substr(bfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(")\n");
            whereSql.append("and bfUnitTable.parents like ?\n");
            whereSql.append("and dfUnitTable.parents like ?\n");
            params.add(parentGuids + "%");
            params.add(parentGuids + "%");
            params.add(orgTypeId);
            whereSql.append(" and record.md_gcorgtype in('" + emptyUUID + "',?) and record.inputunitid ='" + emptyUUID + "' \n");
            if (null != orgTypeId) {
                whereSql.append(" or record.md_gcorgtype = ? and record.inputunitid=?\n)\n");
                params.add(orgTypeId);
                params.add(queryParamsVO.getOrgId());
            }
            this.ryOffsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
        }
        if (queryParamsVO.isDelete() && !"MD_ORG_CORPORATE".equals(queryParamsVO.getOrgType())) {
            whereSql.append(" and record.inputunitid <> '").append(emptyUUID).append("'\n");
        }
    }

    private StringBuffer whereDelRySql(String taskId, Integer acctYear, Integer acctPeriod, String currencyCode, String adjustCode) {
        StringBuffer whereSql = new StringBuffer(64);
        if (null != acctYear) {
            whereSql.append(" and acctYear = ").append(acctYear).append("\n");
        }
        if (null != acctPeriod && null != acctYear) {
            whereSql.append(" and dataTime like '%").append(acctYear).append("_").append(df.format(acctPeriod)).append("'\n");
        }
        if (!StringUtils.isEmpty((String)currencyCode)) {
            whereSql.append(" and offSetCurr = '").append(currencyCode).append("'\n");
        }
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            whereSql.append(" and adjust = '").append(adjustCode).append("'\n");
        }
        return whereSql;
    }
}

