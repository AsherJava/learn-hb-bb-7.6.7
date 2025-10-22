/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investbill.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InvestBillDaoImpl
implements InvestBillDao {
    final String MERGELEVEL = "mergeLevel";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SQL_QUERY_ITEM = "select %s from GC_INVESTBILL  i \n %s \n";

    @Override
    public List<Map<String, Object>> listInvestBillsByPaging(Map<String, Object> params) {
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        int period = ConverterUtils.getAsIntValue((Object)params.get("acctPeriod"));
        YearPeriodObject yp = this.getYearPeriodObject(params, acctYear);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = gcOrgCenterService.getOrgByCode((String)params.get("mergeUnit"));
        if (null == orgCacheVO) {
            return new ArrayList<Map<String, Object>>();
        }
        Date orgValidate = yp.formatYP().getEndDate();
        ArrayList<Object> paramList = new ArrayList<Object>();
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i \n";
        String mergeRange = this.getMergeRange((Map)params.get("filterParam"));
        if ("mergeLevel".equals(mergeRange)) {
            sql = this.appendMergeLevelSql(gcOrgCenterService, orgCacheVO, orgValidate, orgType, paramList, sql, mergeRange);
        } else {
            sql = sql + " join " + orgType + " c on(i.UNITCODE=c.code) \n and c.VALIDTIME<=? and c.INVALIDTIME>= ? \n and c.parents like '" + orgCacheVO.getParentStr() + "%%'\n";
            paramList.addAll(Arrays.asList(orgValidate, orgValidate));
        }
        sql = sql + "  and i.ACCTYEAR=" + acctYear + " and i.period=" + period + " \n";
        String investDefineCode = (String)params.get("defineCode");
        if (!StringUtils.isEmpty((String)investDefineCode)) {
            sql = sql + "  and i.DEFINECODE=?  \n";
            paramList.add(investDefineCode);
        }
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", paramList);
        sql = sql + filterWhere + "  order by i.CREATETIME desc \n";
        int pageNum = ConverterUtils.getAsIntValue((Object)params.get("pageNum"));
        if (pageNum >= 1) {
            int pageSize = ConverterUtils.getAsIntValue((Object)params.get("pageSize"));
            int offset = (pageNum - 1) * pageSize;
            return EntNativeSqlDefaultDao.getInstance().selectMapByPaging(sql, offset, offset + pageSize, paramList);
        }
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, paramList);
    }

    @Override
    public List<Map<String, Object>> listInvests(Map<String, Object> params) {
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        int period = ConverterUtils.getAsIntValue((Object)params.get("acctPeriod"));
        YearPeriodObject yp = this.getYearPeriodObject(params, acctYear);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = gcOrgCenterService.getOrgByCode((String)params.get("mergeUnit"));
        if (null == orgCacheVO) {
            return new ArrayList<Map<String, Object>>();
        }
        Date orgValidate = yp.formatYP().getEndDate();
        ArrayList<Object> paramList = new ArrayList<Object>();
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i \n";
        String mergeRange = this.getMergeRange((Map)params.get("filterParam"));
        if ("mergeLevel".equals(mergeRange)) {
            sql = this.appendMergeLevelSql(gcOrgCenterService, orgCacheVO, orgValidate, orgType, paramList, sql, mergeRange);
        } else {
            sql = sql + " join " + orgType + " c on(i.UNITCODE=c.code) \n and c.VALIDTIME<=? and c.INVALIDTIME>= ? \n and c.parents like '" + orgCacheVO.getParentStr() + "%%'\n";
            paramList.addAll(Arrays.asList(orgValidate, orgValidate));
        }
        sql = sql + "  and i.ACCTYEAR=" + acctYear + " and i.period=" + period + " \n";
        String investDefineCode = (String)params.get("defineCode");
        if (!StringUtils.isEmpty((String)investDefineCode)) {
            sql = sql + "  and i.DEFINECODE=?  \n";
            paramList.add(investDefineCode);
        }
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", paramList);
        sql = sql + filterWhere + "  order by i.CREATETIME desc \n";
        int pageNum = ConverterUtils.getAsIntValue((Object)params.get("pageNum"));
        if (pageNum >= 1) {
            int pageSize = ConverterUtils.getAsIntValue((Object)params.get("pageSize"));
            int offset = (pageNum - 1) * pageSize;
            return EntNativeSqlDefaultDao.getInstance().selectMapByPaging(sql, offset, offset + pageSize, paramList);
        }
        return this.jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    @Override
    public int countInvestBills(Map<String, Object> params) {
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        int period = ConverterUtils.getAsIntValue((Object)params.get("acctPeriod"));
        YearPeriodObject yp = this.getYearPeriodObject(params, acctYear);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = instance.getOrgByCode((String)params.get("mergeUnit"));
        if (null == orgCacheVO) {
            return 0;
        }
        Date orgValidate = yp.formatYP().getEndDate();
        ArrayList<Object> paramList = new ArrayList<Object>();
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", paramList);
        String sql = "select count(*) from GC_INVESTBILL i\n";
        String mergeRange = this.getMergeRange((Map)params.get("filterParam"));
        if ("mergeLevel".equals(mergeRange)) {
            sql = this.appendMergeLevelSql(instance, orgCacheVO, orgValidate, orgType, paramList, sql, mergeRange);
        } else {
            sql = sql + " join " + orgType + " c on(i.UNITCODE=c.code) where 1=1\n and c.VALIDTIME<=? and c.INVALIDTIME>= ? \n and c.parents like '" + orgCacheVO.getParentStr() + "%%'\n";
            paramList.addAll(Arrays.asList(orgValidate, orgValidate));
        }
        sql = sql + "  and i.ACCTYEAR=" + acctYear + " and i.period=" + period + filterWhere + " \n";
        String investDefineCode = (String)params.get("defineCode");
        if (!StringUtils.isEmpty((String)investDefineCode)) {
            sql = sql + "  and i.DEFINECODE=?  \n";
            paramList.add(investDefineCode);
        }
        return EntNativeSqlDefaultDao.getInstance().count(sql, paramList);
    }

    @Override
    public List<Map<String, Object>> getInvestBillItemByBillCode(String billCode) {
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILLITEM", (String)"i") + " from " + "GC_INVESTBILLITEM" + " i where i.billCode=?";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{billCode});
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteByIdList(List<String> idList) {
        this.batchDeleteSubByMasterIdList(idList);
        this.batchDeleteMasterByIdList(idList);
    }

    @Override
    public String getIdByUnitAndYear(String investUnitCode, String investedUnitCode, int acctYear) {
        String sql = "select t.ID from GC_INVESTBILL t \n where t.unitCode=? \n and t.investedUnit=? \n and t.acctYear=? \n";
        return (String)EntNativeSqlDefaultDao.getInstance().selectFirst(String.class, sql, new Object[]{investUnitCode, investedUnitCode, acctYear});
    }

    @Override
    public Map<String, Object> getByUnitAndYear(String investUnitCode, String investedUnitCode, int acctYear) {
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i where i.unitCode=? and i.investedUnit=? and i.acctYear=?";
        List records = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{investUnitCode, investedUnitCode, acctYear});
        return CollectionUtils.isEmpty((Collection)records) ? null : (Map)records.get(0);
    }

    private void batchDeleteSubByMasterIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"masterId");
        String sql = "delete from GC_INVESTBILLITEM  where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    private void batchDeleteMasterByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"ID");
        String sql = "delete from GC_INVESTBILL where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    @Override
    public List<Map<String, Object>> listInvestBillsByIds(List<String> ids) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"i.ID");
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i where " + inSql + " order by i.UNITCODE, i.INVESTEDUNIT \n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
    }

    @Override
    public Map<String, Object> getInvestBillById(String investBillId) {
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i where i.id = ? ";
        Map record = (Map)EntNativeSqlDefaultDao.getInstance().selectEntityAssignResultExtractor(sql, ps -> ps.setObject(1, (Object)investBillId), rs -> rs.getRowData(null));
        return record;
    }

    @Override
    public void updateFairValueAdjustFlag(String investUnit, String investedUnit, Integer acctYear, int fairValueAdjustFlag) {
        if (StringUtils.isEmpty((String)investUnit) || StringUtils.isEmpty((String)investedUnit) || acctYear == null) {
            return;
        }
        String sql = "update GC_INVESTBILL set FAIRVALUEADJUSTFLAG=? where unitCode=? and investedUnit=? and acctYear=?";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{fairValueAdjustFlag, investUnit, investedUnit, acctYear});
    }

    @Override
    public void updateOffsetStatus(String investBillId, int offsetStatus) {
        if (StringUtils.isEmpty((String)investBillId)) {
            return;
        }
        String sql = "update GC_INVESTBILL set OFFSETINITFLAG = ? where srcid = ?";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{offsetStatus, investBillId});
    }

    @Override
    public void updateNumberFieldValue(String id, String fieldCode, Object fieldValue) {
        if (StringUtils.isEmpty((String)id)) {
            return;
        }
        String sql = "update GC_INVESTBILL set " + fieldCode + " = ? where id = ?";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{fieldValue, id});
    }

    @Override
    public void updateFairValueOffsetStatus(String investBillId, int fairValueOffsetStatus) {
        if (StringUtils.isEmpty((String)investBillId)) {
            return;
        }
        String sql = "update GC_INVESTBILL set FAIRVALUEOFFSETFLAG = ? where srcid = ?";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{fairValueOffsetStatus, investBillId});
    }

    @Override
    public void updateDisPoseDate(Date disposeDate, Set<String> srcIdSet) {
        if (CollectionUtils.isEmpty(srcIdSet)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(srcIdSet, (String)"SRCID");
        String sql = "update GC_INVESTBILL set DISPOSEDATE = ?, DISPOSEFLAG= ? where " + inSql;
        int disposeFlag = disposeDate == null ? Integer.valueOf(InvestInfoEnum.DISPOSE_UNDO.getCode()) : Integer.valueOf(InvestInfoEnum.DISPOSE_DONE.getCode());
        if (null != disposeDate) {
            sql = sql + " and PERIOD > " + disposeDate.getMonth();
        }
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{disposeDate, disposeFlag});
    }

    @Override
    public List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> investUnit, Set<String> investedUnit, int acctYear, int period) {
        String sql = " select %1$s from GC_INVESTBILL e  where e.acctYear=? and e.PERIOD=? and %2$s";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"e");
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(investUnit, (String)"e.unitCode") + " and " + SqlUtils.getConditionOfIdsUseOr(investedUnit, (String)"e.investedUnit");
        String formatSql = String.format(sql, columns, whereCondition);
        List inputDataEOs = InvestBillTool.queryBySql((String)formatSql, (Object[])new Object[]{acctYear, period});
        return inputDataEOs;
    }

    @Override
    public List<DefaultTableEntity> getInvestmentItemsByMastId(String investmentIds) {
        String sql = "select %1$s  from GC_INVESTBILLITEM ei where ei.masterId = ? ";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILLITEM", (String)"ei");
        String formatSQL = String.format(sql, columns);
        return InvestBillTool.queryBySql((String)formatSQL, (Object[])new Object[]{investmentIds});
    }

    @Override
    public List<Map<String, Object>> getByYear(int acctYear, int srcType, List<String> orgCodeList) {
        ArrayList<Integer> paramList = new ArrayList<Integer>();
        paramList.add(acctYear);
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"e") + " from " + "GC_INVESTBILL" + " e  where e.acctYear = ?";
        if (srcType != 0) {
            sql = sql + " and e.srcType = ?";
            paramList.add(srcType);
        }
        sql = sql + " and %1$s order by e.createtime desc";
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(orgCodeList, (String)"e.UNITCODE");
        String formatSQL = String.format(sql, whereCondition);
        return EntNativeSqlDefaultDao.getInstance().selectMap(formatSQL, paramList);
    }

    @Override
    public List<Map<String, Object>> getByYear(int acctYear, int peroid, int srcType, List<String> orgCodeList) {
        ArrayList<Integer> paramList = new ArrayList<Integer>();
        paramList.add(acctYear);
        paramList.add(peroid);
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"e") + " from " + "GC_INVESTBILL" + " e  where e.acctYear = ? and e.PERIOD=?";
        if (srcType != 0) {
            sql = sql + " and e.srcType = ?";
            paramList.add(srcType);
        }
        sql = sql + " and %1$s order by e.createtime desc";
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(orgCodeList, (String)"e.UNITCODE");
        String formatSQL = String.format(sql, whereCondition);
        return EntNativeSqlDefaultDao.getInstance().selectMap(formatSQL, paramList);
    }

    @Override
    public int deleteByYearAndUnit(String tableName, int acctYear, int srcType, List<String> orgCodeList) {
        String sql = " delete from " + tableName + "  \n where acctYear = ? \n and srcType = ? \n and %1$s ";
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(orgCodeList, (String)"UNITCODE");
        String formatSQL = String.format(sql, whereCondition);
        return EntNativeSqlDefaultDao.getInstance().execute(formatSQL, new Object[]{acctYear, srcType});
    }

    @Override
    public int deleteInvestItemByMasterId(Set<String> investBillIds) {
        if (CollectionUtils.isEmpty(investBillIds)) {
            return 0;
        }
        String sql = " delete from GC_INVESTBILLITEM  \n where 1=1\n and %1$s \n";
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(investBillIds, (String)"MASTERID");
        String formatSQL = String.format(sql, whereCondition);
        return EntNativeSqlDefaultDao.getInstance().execute(formatSQL, new ArrayList());
    }

    @Override
    public Double sumInvestNumber(String unitCode, Integer acctYear, String fieldCode, String filterCondition, Date period) {
        List maps;
        if (StringUtils.isEmpty((String)fieldCode) || null == acctYear || StringUtils.isEmpty((String)unitCode)) {
            return null;
        }
        String sql = " select sum(t." + fieldCode + ") FILEDVALUE from " + "GC_INVESTBILL" + " t \n where 1=1 \n  and t.acctYear = ? \n and t.unitCode = ? \n and (t.disposeflag is null or t.disposeflag = ? or t.disposedate > ?) \n";
        if (!StringUtils.isEmpty((String)filterCondition)) {
            sql = sql + " and (" + filterCondition + ") \n";
        }
        if (!CollectionUtils.isEmpty((Collection)(maps = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{acctYear, unitCode, false, period})))) {
            return (Double)((Map)maps.get(0)).get("FILEDVALUE");
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> listInvestedCompreEquityRatio(int acctYear) {
        String sql = "select i.INVESTEDUNIT,i.INVESTEDCOMPREEQUITYRATIO\n from GC_INVESTBILL i \n  where i.acctYear=? \n  and i.mergeType ='DIRECT'\n  and i.investedCompreEquityRatio is not null\n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{acctYear});
    }

    @Override
    public List<Map<String, Object>> listByWhere(String[] columnNamesInDB, Object[] values) {
        StringBuffer conditionSql = new StringBuffer(128);
        for (int i = 0; i < values.length; ++i) {
            conditionSql.append(SqlUtils.getConditionOfObject((Object)values[i], (String)(" i." + columnNamesInDB[i]))).append(" and");
        }
        String whereSql = "";
        if (conditionSql.length() > 0) {
            conditionSql.setLength(conditionSql.length() - 4);
            whereSql = " where " + conditionSql;
        }
        String sql = String.format(SQL_QUERY_ITEM, this.getCols(), whereSql);
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
    }

    @Override
    public List<String> getInvestIdsBySrcIdAndBeginPeriod(String srcId, int begainPeriod) {
        if (StringUtils.isEmpty((String)srcId)) {
            return null;
        }
        String sql = "select i.ID from GC_INVESTBILL i \n where i.SRCID = ? \n and i.PERIOD >= ? \n";
        List data = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{srcId, begainPeriod});
        return data.stream().map(item -> (String)item.get("ID")).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getInvestBySrcIdAndPeriod(String srcId, int period) {
        if (StringUtils.isEmpty((String)srcId)) {
            return null;
        }
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i \n where i." + "SRCID" + " = ? \n and i." + "PERIOD" + " = ? \n";
        List data = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{srcId, period});
        if (!CollectionUtils.isEmpty((Collection)data)) {
            return (Map)data.get(0);
        }
        return null;
    }

    private String getCols() {
        return SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"i");
    }

    private String getInvestBIllItemCols() {
        return SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILLITEM", (String)"s");
    }

    private String getMergeRange(Map<String, Object> filterParaMmap) {
        String mergeRange = "";
        if (filterParaMmap != null && filterParaMmap.size() > 0) {
            mergeRange = (String)filterParaMmap.get("mergeRange");
        }
        return mergeRange;
    }

    @Override
    public Set<String> listInvestIdsBySrcIdsAndExactPeriod(Collection<String> srcIds, int period) {
        if (CollectionUtils.isEmpty(srcIds)) {
            return null;
        }
        TempTableCondition conditionOfIds = SqlUtils.getConditionOfIds(srcIds, (String)"SRCID");
        String sql = "select i.ID from GC_INVESTBILL i \n where i." + conditionOfIds.getCondition() + " and i." + "PERIOD" + " = ? \n";
        List data = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{period});
        return data.stream().map(item -> (String)item.get("ID")).collect(Collectors.toSet());
    }

    @Override
    public List<Map<String, Object>> listSubItemsOfManual(int acctYear, List<String> orgCodeList) {
        String sql = "select m.srcid from gc_investbillitem s join gc_investbill m on s.masterid = m.id and m.srctype=33 and m.acctyear=?";
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(orgCodeList, (String)"m.UNITCODE");
        String formatSQL = String.format(sql, whereCondition);
        return EntNativeSqlDefaultDao.getInstance().selectMap(formatSQL, new Object[]{acctYear});
    }

    @Override
    public int deleteBySrcIds(Collection<String> srcIdSet, Integer acctYear) {
        if (CollectionUtils.isEmpty(srcIdSet)) {
            return 0;
        }
        if (null == acctYear || acctYear == 0) {
            throw new BusinessRuntimeException("deleteBySrcIds\u65b9\u6cd5\u4e2d\u672a\u4f20\u5e74\u5ea6\uff01");
        }
        String sql = " delete from GC_INVESTBILL  \n where 1=1\n and %1$s \n and acctYear=? \n";
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(srcIdSet, (String)"SRCID");
        String formatSQL = String.format(sql, whereCondition);
        return EntNativeSqlDefaultDao.getInstance().execute(formatSQL, Arrays.asList(acctYear));
    }

    @Override
    public Map<String, Object> getInvestBySrcIdAndYearAndPeriod(String srcId, int acctYear, int period) {
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i \n where i.SRCID=? \n and i.ACCTYEAR=? \n and i.PERIOD=? \n";
        return this.jdbcTemplate.queryForMap(sql, new Object[]{srcId, acctYear, period});
    }

    private YearPeriodObject getYearPeriodObject(Map<String, Object> params, int acctYear) {
        YearPeriodObject yp = null == params.get("periodStr") ? new YearPeriodObject(acctYear, 12) : new YearPeriodObject(null, (String)params.get("periodStr"));
        return yp;
    }

    private String appendMergeLevelSql(GcOrgCenterService instance, GcOrgCacheVO orgCacheVO, Date orgValidate, String orgType, List<Object> paramList, String sql, String mergeRange) {
        int len = instance.getOrgCodeLength();
        int gcUnitParentsStartIndex = orgCacheVO.getGcParentStr().length() + 2;
        String parentStr = orgCacheVO.getParentStr();
        sql = sql + "join " + orgType + " INVESTUNIT on INVESTUNIT.code = i.UNITCODE \njoin " + orgType + " INVESTEDUNIT on INVESTEDUNIT.code = i.INVESTEDUNIT  where 1=1 \n";
        sql = sql + " and substr(INVESTUNIT.gcparents, ?, " + len + " ) <> substr(INVESTEDUNIT.gcparents, ?, " + len + ")\n and INVESTUNIT.parents like '" + parentStr + "%%' \n and INVESTEDUNIT.parents like '" + parentStr + "%%' \n and INVESTUNIT.VALIDTIME <= ? and INVESTUNIT.INVALIDTIME >= ? \n and INVESTEDUNIT.VALIDTIME <= ? and INVESTEDUNIT.INVALIDTIME >= ? \n";
        paramList.addAll(Arrays.asList(gcUnitParentsStartIndex, gcUnitParentsStartIndex, orgValidate, orgValidate, orgValidate, orgValidate));
        return sql;
    }
}

