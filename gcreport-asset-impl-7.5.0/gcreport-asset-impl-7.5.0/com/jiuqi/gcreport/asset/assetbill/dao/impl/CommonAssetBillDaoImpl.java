/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
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
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.asset.assetbill.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.asset.assetbill.dao.CommonAssetBillDao;
import com.jiuqi.gcreport.asset.assetbill.enums.CommonAssetDepreItemTypeEnum;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommonAssetBillDaoImpl
implements CommonAssetBillDao {
    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"ID");
        String sql = "delete from GC_COMMONASSETBILL  \n where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    @Override
    public void batchUnDisposalByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"i.ID");
        String sql = "update GC_COMMONASSETBILL i \n set USESTATE='01',DISPOSALDATE=null,DSPEPRFLS=null,DSPESUBJECTCODE=null\n where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    @Override
    public void transfer2FixedAsset(String billCode) {
        if (StringUtils.isEmpty((String)billCode)) {
            return;
        }
        String sql = "update GC_COMMONASSETBILL i \n set assetState='02'\n where i.billCode=?";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{billCode});
    }

    @Override
    public List<String> listIdsByBillCode(String tableName, String billCode) {
        TableModelDefine tableDefine = ((DataModelService)SpringContextUtils.getBean(DataModelService.class)).getTableModelDefineByName(tableName);
        if (null == tableDefine) {
            return null;
        }
        String sql = "select i.id ID \n from " + tableName + " i \n where i.billCode=? ";
        List datas = EntNativeSqlDefaultDao.getInstance().selectMap(sql, Arrays.asList(billCode));
        return datas.stream().map(m -> String.valueOf(m.get("ID"))).collect(Collectors.toList());
    }

    @Override
    public int countAssetBills(TempTableCondition tempTableCondition, Map<String, Object> params) {
        ArrayList<Object> whereParam = new ArrayList<Object>();
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", whereParam);
        return StringUtils.isEmpty((String)filterWhere) ? this.countAssetBills(tempTableCondition, "", params, whereParam) : this.countAssetBills(tempTableCondition, filterWhere, params, whereParam);
    }

    @Override
    public Set<String> listAssetBillCodesByPaging(TempTableCondition tempTableCondition, Map<String, Object> params, int firstResult, int maxResults) {
        String mergeUnit = (String)params.get("mergeUnit");
        Map filterParaMmap = (Map)params.get("filterParam");
        String mergeRange = "";
        if (filterParaMmap != null && filterParaMmap.size() > 0) {
            mergeRange = (String)filterParaMmap.get("mergeRange");
        }
        ArrayList<Object> whereParam = new ArrayList<Object>();
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", whereParam);
        if (StringUtils.isEmpty((String)filterWhere) && StringUtils.isEmpty((String)mergeRange)) {
            return this.listAssetBillCodesByPaging(params, tempTableCondition, firstResult, maxResults, whereParam);
        }
        return this.listAssetBillCodesByPaging(params, mergeUnit, mergeRange, tempTableCondition, filterWhere, firstResult, maxResults, whereParam);
    }

    @Override
    public List<Map<String, Object>> listAssetBillsByBillCodes(Collection<String> billCodes) {
        String condition = SqlUtils.getConditionOfIdsUseOr(billCodes, (String)"i.BILLCODE");
        String sql = "select " + this.getCols() + " from " + "GC_COMMONASSETBILL" + " i \n where " + condition + " order by i.createtime desc";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
    }

    @Override
    public List<DefaultTableEntity> getCommonFixedAssetDatas(GcCalcEnvContext env, GcOrgCacheVO hbOrg) {
        ArrayList<Date> paramList = new ArrayList<Date>();
        int len = GcOrgPublicTool.getInstance().getOrgCodeLength();
        String sql = "select %1$s \n from GC_COMMONASSETBILL e \n join %4$s  unit on e.unitCode = unit.code \n join %4$s  oppunit on e.oppUnitCode = oppunit.code \n where substr(unit.gcparents, %2$s, " + len + ") <> substr(oppunit.gcparents, %2$s, " + len + ") \n and unit.parents like '%3$s'\n and oppunit.parents like '%3$s' \n and oppunit.validtime <= ? and oppunit.invalidtime > ?  \n and unit.validtime <= ? and unit.invalidtime > ?";
        String hbUnitParents = hbOrg.getParentStr() + "%";
        int gcUnitParentsStartIndex = hbOrg.getGcParentStr().length() + 2;
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_COMMONASSETBILL", (String)"e");
        String formatSQL = String.format(sql, columns, gcUnitParentsStartIndex, hbUnitParents, env.getCalcArgments().getOrgType());
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        Date orgvalidate = yp.formatYP().getEndDate();
        paramList.addAll(Arrays.asList(orgvalidate, orgvalidate, orgvalidate, orgvalidate));
        return InvestBillTool.queryBySql((String)formatSQL, paramList);
    }

    @Override
    public Set<String> listAssetBillCodes(TempTableCondition tempTableCondition, Map<String, Object> params) {
        ArrayList<Date> paramList = new ArrayList<Date>();
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", paramList);
        Date acctYearFirstDate = null;
        if (params.get("acctYear") != null) {
            acctYearFirstDate = this.getFirstDateByYear(Integer.parseInt(params.get("acctYear").toString()));
            filterWhere = filterWhere + " and ( i.DISPOSALDATE is null or i.DISPOSALDATE >= ? ) \n";
            paramList.add(acctYearFirstDate);
        }
        String sql = "select i.BILLCODE\n from GC_COMMONASSETBILL i \n where (i.UNITCODE" + tempTableCondition.getCondition() + " or i.OPPUNITCODE" + tempTableCondition.getCondition() + ")\n" + filterWhere + " order by i.createtime desc";
        List result = EntNativeSqlDefaultDao.getInstance().selectMap(sql, paramList);
        return result.stream().map(map -> (String)map.get("BILLCODE")).collect(Collectors.toSet());
    }

    @Override
    public void deleteCommonItemByMastId(String masterId, CommonAssetDepreItemTypeEnum type, Date start, Date end) {
        String sql = "delete  from GC_COMMONASSETBILLITEM  \n where masterId=? and dpcaType=? and dpcaMonth >= ? and dpcaMonth <= ?";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{masterId, type.toString(), start, end});
    }

    @Override
    public List<DefaultTableEntity> listCommonItemByMasterId(String masterId, Date endDate) {
        String sql = "select %1$s \n  from GC_COMMONASSETBILLITEM e \n where  masterId=? \n   and  dpcaMonth < ? \n order  by ORDINAL \n";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_COMMONASSETBILLITEM", (String)"e");
        String formatSql = String.format(sql, columns);
        return InvestBillTool.queryBySql((String)formatSql, (Object[])new Object[]{masterId, endDate});
    }

    @Override
    public double getMinOrdinalByMasterId(String id) {
        String sql = "select min(ORDINAL) \n  from GC_COMMONASSETBILLITEM e \n where  masterId=? \n";
        Double result = (Double)EntNativeSqlDefaultDao.getInstance().selectFirst(Double.class, sql, new Object[]{id});
        return result == null ? 0.0 : result;
    }

    private Date getFirstDateByYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(6, cal.getActualMinimum(6));
        cal.set(11, 0);
        cal.set(13, 0);
        cal.set(12, 0);
        cal.set(14, 0);
        Date acctYearFirstDate = cal.getTime();
        return acctYearFirstDate;
    }

    private int countAssetBills(TempTableCondition tempTableCondition, String filterWhere, Map<String, Object> params, List<Object> paramList) {
        String mergeUnit = (String)params.get("mergeUnit");
        Map filterParaMmap = (Map)params.get("filterParam");
        String mergeRange = "";
        if (filterParaMmap != null && filterParaMmap.size() > 0) {
            mergeRange = (String)filterParaMmap.get("mergeRange");
        }
        Integer acctYear = params.get("acctYear") != null ? Integer.valueOf(Integer.parseInt(params.get("acctYear").toString())) : null;
        String periodStr = (String)params.get("periodStr");
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        Date orgValidate = yp.formatYP().getEndDate();
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(mergeUnit);
        int len = orgTool.getOrgCodeLength();
        if (null == orgCacheVO) {
            return 0;
        }
        String parentStr = orgCacheVO.getParentStr();
        int gcUnitParentsStartIndex = orgCacheVO.getGcParentStr().length() + 2;
        int gcUnitParentsEndIndex = parentStr.length();
        Date acctYearFirstDate = null;
        if (acctYear != null) {
            acctYearFirstDate = this.getFirstDateByYear(acctYear);
            filterWhere = filterWhere + " and ( i.DISPOSALDATE is null or i.DISPOSALDATE >= ? ) \n";
            paramList.add(acctYearFirstDate);
        }
        String sql = " select i.BILLCODE\n from GC_COMMONASSETBILL  i \n";
        if ("\u5f53\u524d\u5408\u5e76\u5c42\u7ea7".equals(mergeRange)) {
            sql = sql + " join " + orgType + "  INVESTUNIT on INVESTUNIT.code = i.UNITCODE \njoin " + orgType + "  INVESTEDUNIT on INVESTEDUNIT.code = i.oppUnitCode \n";
        }
        sql = sql + " where (i.UNITCODE" + tempTableCondition.getCondition() + " or i.OPPUNITCODE" + tempTableCondition.getCondition() + ")\n" + filterWhere;
        if ("\u5f53\u524d\u5408\u5e76\u5c42\u7ea7".equals(mergeRange)) {
            sql = sql + " and substr(INVESTUNIT.gcparents, ?, " + len + " ) <> substr(INVESTEDUNIT.gcparents, ?, " + len + ")\n and substr(INVESTUNIT.parents, 1, ?) = ? \n and substr(INVESTEDUNIT.parents, 1, ?) = ? \n and INVESTUNIT.VALIDTIME <= ? and INVESTUNIT.INVALIDTIME >= ? \n and INVESTEDUNIT.VALIDTIME <= ? and INVESTEDUNIT.INVALIDTIME >= ? \n";
            paramList.addAll(Arrays.asList(gcUnitParentsStartIndex, gcUnitParentsStartIndex, gcUnitParentsEndIndex, parentStr, gcUnitParentsEndIndex, parentStr, orgValidate, orgValidate, orgValidate, orgValidate));
        }
        return EntNativeSqlDefaultDao.getInstance().count(sql, paramList);
    }

    private Set<String> listAssetBillCodesByPaging(Map<String, Object> params, TempTableCondition tempTableCondition, int firstResult, int maxResults, List<Object> paramList) {
        Integer acctYear = params.get("acctYear") == null ? null : Integer.valueOf(Integer.parseInt(params.get("acctYear").toString()));
        String sql = "select i.BILLCODE from GC_COMMONASSETBILL i \n where (i.UNITCODE" + tempTableCondition.getCondition() + " or i.OPPUNITCODE" + tempTableCondition.getCondition() + ")\n    %1$s \n order by i.createtime desc";
        Date acctYearFirstDate = null;
        String whereSql = "";
        if (acctYear != null) {
            acctYearFirstDate = this.getFirstDateByYear(acctYear);
            whereSql = " and ( i.DISPOSALDATE is null or i.DISPOSALDATE >=?) \n";
            paramList.add(acctYearFirstDate);
        }
        List result = EntNativeSqlDefaultDao.getInstance().selectMapByPaging(String.format(sql, whereSql), firstResult, maxResults, paramList);
        return result.stream().map(map -> (String)map.get("BILLCODE")).collect(Collectors.toSet());
    }

    private Set<String> listAssetBillCodesByPaging(Map<String, Object> params, String mergeUnit, String mergeRange, TempTableCondition tempTableCondition, String filterWhere, int firstResult, int maxResults, List<Object> paramList) {
        String periodStr = (String)params.get("periodStr");
        Integer acctYear = params.get("acctYear") == null ? null : Integer.valueOf(Integer.parseInt(params.get("acctYear").toString()));
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        Date orgValidate = yp.formatYP().getEndDate();
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(mergeUnit);
        int len = orgTool.getOrgCodeLength();
        if (null == orgCacheVO) {
            return new HashSet<String>();
        }
        String parentStr = orgCacheVO.getParentStr();
        int gcUnitParentsStartIndex = orgCacheVO.getGcParentStr().length() + 2;
        int gcUnitParentsEndIndex = parentStr.length();
        Date acctYearFirstDate = null;
        if (acctYear != null) {
            acctYearFirstDate = this.getFirstDateByYear(acctYear);
            filterWhere = filterWhere + " and ( i.DISPOSALDATE is null or i.DISPOSALDATE >= ? ) \n";
            paramList.add(acctYearFirstDate);
        }
        String sql = "select i.BILLCODE\n from GC_COMMONASSETBILL i \n";
        if ("\u5f53\u524d\u5408\u5e76\u5c42\u7ea7".equals(mergeRange)) {
            sql = sql + "join " + orgType + "  INVESTUNIT on INVESTUNIT.code = i.UNITCODE \njoin " + orgType + "  INVESTEDUNIT on INVESTEDUNIT.code = i.oppUnitCode \n";
        }
        sql = sql + " where (i.UNITCODE" + tempTableCondition.getCondition() + " or i.OPPUNITCODE" + tempTableCondition.getCondition() + ")\n" + filterWhere;
        if ("\u5f53\u524d\u5408\u5e76\u5c42\u7ea7".equals(mergeRange)) {
            sql = sql + " and substr(INVESTUNIT.gcparents, ?, " + len + " ) <> substr(INVESTEDUNIT.gcparents, ?, " + len + ")\n and substr(INVESTUNIT.parents, 1, ?) = ? \n and substr(INVESTEDUNIT.parents, 1, ?) = ? \n and INVESTUNIT.VALIDTIME <= ? and INVESTUNIT.INVALIDTIME >= ? \n and INVESTEDUNIT.VALIDTIME <= ? and INVESTEDUNIT.INVALIDTIME >= ? \n";
            paramList.addAll(Arrays.asList(gcUnitParentsStartIndex, gcUnitParentsStartIndex, gcUnitParentsEndIndex, parentStr, gcUnitParentsEndIndex, parentStr, orgValidate, orgValidate, orgValidate, orgValidate));
        }
        sql = sql + " order by i.createtime desc \n";
        List result = EntNativeSqlDefaultDao.getInstance().selectMapByPaging(sql, firstResult, maxResults, paramList);
        return result.stream().map(map -> (String)map.get("BILLCODE")).collect(Collectors.toSet());
    }

    @Override
    public List<Map<String, Object>> listAssetBillsByIds(List<String> ids) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"i.ID");
        String sql = "select " + this.getCols() + " from " + "GC_COMMONASSETBILL" + " i where " + inSql;
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
    }

    private String getCols() {
        return SqlUtils.getColumnsSqlByTableDefine((String)"GC_COMMONASSETBILL", (String)"i");
    }
}

