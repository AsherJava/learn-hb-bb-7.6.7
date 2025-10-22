/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.asset.assetbill.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.asset.assetbill.dao.CombinedAssetBillDao;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CombinedAssetBillDaoImpl
implements CombinedAssetBillDao {
    @Override
    public List<Map<String, Object>> listAllBillList(String type) {
        try {
            String sql = "select uniquecode,title from meta_info where metatype = ?";
            List result = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{type});
            return result;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteByIdList(List<String> idList) {
        this.batchDeleteSubByMasterIdList(idList);
        this.batchDeleteMasterByIdList(idList);
    }

    @Override
    public void batchUnDisposalByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"i.ID");
        String sql = "update GC_COMBINEDASSETBILL i \n set USESTATE='01',DISPOSALDATE=null,DSPEPRFLS=null,DSPESUBJECTCODE=null\n where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    @Override
    public void transfer2FixedAsset(String billCode) {
        if (StringUtils.isEmpty((String)billCode)) {
            return;
        }
        String sql = "update GC_COMBINEDASSETBILL i \n set assetState='02'\n where i.billCode=? ";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{billCode});
    }

    @Override
    public List<String> listIdsByBillCode(String tableName, String billCode) {
        TableModelDefine tableDefine = ((DataModelService)SpringContextUtils.getBean(DataModelService.class)).getTableModelDefineByName(tableName);
        if (null == tableDefine) {
            return null;
        }
        String sql = " select i.id \n from " + tableName + "i \n where i.billCode=?";
        List rs = EntNativeSqlDefaultDao.getInstance().selectMap(sql, Arrays.asList(billCode));
        return rs.stream().map(v -> String.valueOf(v.get("ID"))).collect(Collectors.toList());
    }

    @Override
    public int countAssetBills(TempTableCondition tempTableCondition, Map<String, Object> params) {
        ArrayList<Object> whereParams = new ArrayList<Object>();
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", whereParams);
        Integer acctYear = params.get("acctYear") == null ? null : Integer.valueOf(Integer.parseInt(params.get("acctYear").toString()));
        return StringUtils.isEmpty((String)filterWhere) ? this.countAssetBills(acctYear, tempTableCondition, whereParams) : this.countAssetBills(acctYear, tempTableCondition, filterWhere, whereParams);
    }

    @Override
    public Set<String> listAssetBillCodesByPaging(TempTableCondition tempTableCondition, Map<String, Object> params, int firstResult, int maxResults) {
        Integer acctYear;
        ArrayList<Object> whereParams = new ArrayList<Object>();
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", whereParams);
        Integer n = acctYear = params.get("acctYear") == null ? null : Integer.valueOf(Integer.parseInt(params.get("acctYear").toString()));
        if (StringUtils.isEmpty((String)filterWhere)) {
            return this.listAssetBillCodesByPaging(acctYear, tempTableCondition, firstResult, maxResults, whereParams);
        }
        return this.listAssetBillCodesByPaging(acctYear, tempTableCondition, filterWhere, firstResult, maxResults, whereParams);
    }

    @Override
    public List<Map<String, Object>> listAssetBillsByBillCodes(Collection<String> billCodes) {
        String condition = SqlUtils.getConditionOfIdsUseOr(billCodes, (String)"i.BILLCODE");
        String sql = " select " + this.getCols() + " from " + "GC_COMBINEDASSETBILL" + "  i \n where " + condition + " order by BILLCODE desc \n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
    }

    @Override
    public List<DefaultTableEntity> getFixedAssetItemsDatas(GcCalcEnvContext env, GcOrgCacheVO hbOrg) {
        int len = GcOrgPublicTool.getInstance().getOrgCodeLength();
        String sql = " select %1$s \n from GC_COMBINEDASSETBILLITEM  e \n join %4$s  unit on e.unitCode = unit.code \n join %4$s  oppunit on e.oppUnitCode = oppunit.code \n where substr(unit.gcparents, %2$s, " + len + ") <> substr(oppunit.gcparents, %2$s, " + len + ") \n and unit.parents like '%3$s'\n and oppunit.parents like '%3$s' \n and oppunit.validtime <= ? and oppunit.invalidtime > ?  \n and unit.validtime <= ? and unit.invalidtime > ?  \n";
        String hbUnitParents = hbOrg.getParentStr() + "%";
        int gcUnitParentsStartIndex = hbOrg.getGcParentStr().length() + 2;
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_COMBINEDASSETBILLITEM", (String)"e");
        String formatSQL = String.format(sql, columns, gcUnitParentsStartIndex, hbUnitParents, env.getCalcArgments().getOrgType());
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        Date orgvalidate = yp.formatYP().getEndDate();
        return InvestBillTool.queryBySql((String)formatSQL, (Object[])new Object[]{orgvalidate, orgvalidate, orgvalidate, orgvalidate});
    }

    @Override
    public List<DefaultTableEntity> getFixedAssetsDatas(GcCalcEnvContext env, Set<String> assetIds) {
        String sql = " select %1$s \n from GC_COMBINEDASSETBILL  ei \n where %2$s \n";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_COMBINEDASSETBILL", (String)"ei");
        String condiSql = SqlUtils.getConditionOfIdsUseOr(assetIds, (String)"ei.id");
        String formatSQL = String.format(sql, columns, condiSql);
        return InvestBillTool.queryBySql((String)formatSQL, (Object[])new Object[0]);
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
        String sql = " select i.BILLCODE\n from GC_COMBINEDASSETBILL  i \n where i.BILLCODE in(\n   select i.BILLCODE   from GC_COMBINEDASSETBILL  i \n   where i.UNITCODE" + tempTableCondition.getCondition() + "   union \n   select i." + "BILLCODE" + "   from " + "GC_COMBINEDASSETBILLITEM" + "  i \n   where i.OPPUNITCODE" + tempTableCondition.getCondition() + " )\n" + filterWhere + " order by BILLCODE desc \n";
        List result = EntNativeSqlDefaultDao.getInstance().selectMap(sql, paramList);
        return result.stream().map(map -> (String)map.get("BILLCODE")).collect(Collectors.toSet());
    }

    private Date getFirstDateByYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(6, cal.getActualMinimum(6));
        Date acctYearFirstDate = cal.getTime();
        return acctYearFirstDate;
    }

    @Override
    public List<Map<String, Object>> listItemAssetBillsByBillCodes(Set<String> mastBillCodes, List<String> itemColumnCodes, Set<String> mastAssetTypeOrTitele) {
        String condition = SqlUtils.getConditionOfIdsUseOr(mastBillCodes, (String)"i.BILLCODE");
        String sql = " select  %1$s \n from GC_COMBINEDASSETBILLITEM  i \n  %2$s \n where " + condition + " order by i.BILLCODE desc \n";
        StringBuffer columnSql = new StringBuffer();
        boolean needJoin = false;
        for (String column : itemColumnCodes) {
            if (("ASSETTYPE".equals(column) || "ASSETTITLE".equals(column)) && mastAssetTypeOrTitele.contains(column)) {
                columnSql.append(" mast.");
                needJoin = true;
            } else {
                columnSql.append(" i.");
            }
            columnSql.append(column).append(",");
        }
        columnSql.deleteCharAt(columnSql.length() - 1);
        StringBuffer leftJoinSql = new StringBuffer();
        if (needJoin) {
            leftJoinSql.append(" left join GC_COMBINEDASSETBILL  mast \n");
            leftJoinSql.append("   on mast.ID = i.MASTERID");
        }
        sql = String.format(sql, columnSql, leftJoinSql);
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
    }

    private int countAssetBills(Integer acctYear, TempTableCondition tempTableCondition, List<Object> paramList) {
        Date acctYearFirstDate = null;
        String whereSql = "";
        if (acctYear != null) {
            acctYearFirstDate = this.getFirstDateByYear(acctYear);
            whereSql = " and ( i.DISPOSALDATE is null or i.DISPOSALDATE >= ? ) \n";
            paramList.add(acctYearFirstDate);
        }
        String sql = " select i.BILLCODE from GC_COMBINEDASSETBILL i \n where i.UNITCODE" + tempTableCondition.getCondition() + "    %1$s \n union \n select i." + "BILLCODE" + " from " + "GC_COMBINEDASSETBILLITEM" + " i \n where i.OPPUNITCODE" + tempTableCondition.getCondition();
        return EntNativeSqlDefaultDao.getInstance().count(String.format(sql, whereSql), paramList);
    }

    private int countAssetBills(Integer acctYear, TempTableCondition tempTableCondition, String filterWhere, List<Object> paramList) {
        Date acctYearFirstDate = null;
        if (acctYear != null) {
            acctYearFirstDate = this.getFirstDateByYear(acctYear);
            filterWhere = filterWhere + " and ( i.DISPOSALDATE is null or i.DISPOSALDATE >= ? ) \n";
            paramList.add(acctYearFirstDate);
        }
        String sql = "select i.BILLCODE\n from GC_COMBINEDASSETBILL i \n where i.BILLCODE in(\n   select i.BILLCODE   from GC_COMBINEDASSETBILL i \n   where i.UNITCODE" + tempTableCondition.getCondition() + "   union \n   select i." + "BILLCODE" + "   from " + "GC_COMBINEDASSETBILLITEM" + "  i \n   where i.OPPUNITCODE" + tempTableCondition.getCondition() + " )\n" + filterWhere;
        return EntNativeSqlDefaultDao.getInstance().count(sql, paramList);
    }

    private Set<String> listAssetBillCodesByPaging(Integer acctYear, TempTableCondition tempTableCondition, int firstResult, int maxResults, List<Object> paramList) {
        String sql = " select i.BILLCODE from GC_COMBINEDASSETBILL i \n where i.UNITCODE" + tempTableCondition.getCondition() + "    %1$s \n union \n select i." + "BILLCODE" + " from " + "GC_COMBINEDASSETBILLITEM" + "  i \n where i.OPPUNITCODE" + tempTableCondition.getCondition() + " order by BILLCODE desc ";
        Date acctYearFirstDate = null;
        String whereSql = "";
        if (acctYear != null) {
            acctYearFirstDate = this.getFirstDateByYear(acctYear);
            whereSql = " and ( i.DISPOSALDATE is null or i.DISPOSALDATE >= ? ) \n";
            paramList.add(acctYearFirstDate);
        }
        List result = EntNativeSqlDefaultDao.getInstance().selectMapByPaging(String.format(sql, whereSql), firstResult, maxResults, paramList);
        return result.stream().map(map -> (String)map.get("BILLCODE")).collect(Collectors.toSet());
    }

    private Set<String> listAssetBillCodesByPaging(Integer acctYear, TempTableCondition tempTableCondition, String filterWhere, int firstResult, int maxResults, List<Object> paramList) {
        Date acctYearFirstDate = null;
        if (acctYear != null) {
            acctYearFirstDate = this.getFirstDateByYear(acctYear);
            filterWhere = filterWhere + " and ( i.DISPOSALDATE is null or i.DISPOSALDATE >= ? ) \n";
            paramList.add(acctYearFirstDate);
        }
        String sql = " select i.BILLCODE\n from GC_COMBINEDASSETBILL  i \n where i.BILLCODE in(\n   select i.BILLCODE   from GC_COMBINEDASSETBILL  i \n   where i.UNITCODE" + tempTableCondition.getCondition() + "   union \n   select i." + "BILLCODE" + "   from " + "GC_COMBINEDASSETBILLITEM" + "  i \n   where i.OPPUNITCODE" + tempTableCondition.getCondition() + " )\n" + filterWhere + " order by BILLCODE desc \n";
        List result = EntNativeSqlDefaultDao.getInstance().selectMapByPaging(sql, firstResult, maxResults, paramList);
        return result.stream().map(map -> (String)map.get("BILLCODE")).collect(Collectors.toSet());
    }

    private void batchDeleteMasterByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"ID");
        String sql = "delete from GC_COMBINEDASSETBILL where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    private void batchDeleteSubByMasterIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"masterId");
        String sql = "delete from GC_COMBINEDASSETBILLITEM where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    private String getCols() {
        return SqlUtils.getColumnsSqlByTableDefine((String)"GC_COMBINEDASSETBILL", (String)"i");
    }
}

