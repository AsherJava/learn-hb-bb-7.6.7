/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.lease.leasebill.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.lease.leasebill.dao.TenantryBillDao;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TenantryBillDaoImpl
implements TenantryBillDao {
    final String MERGELEVEL = "mergeLevel";

    @Override
    public List<Map<String, Object>> listTenantryBillsByPaging(TempTableCondition tempTableCondition, Map<String, Object> params) {
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        YearPeriodObject yp = this.getYearPeriodObject(params, acctYear);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode((String)params.get("mergeUnit"));
        if (null == orgCacheVO) {
            return new ArrayList<Map<String, Object>>();
        }
        Date orgValidate = yp.formatYP().getEndDate();
        ArrayList<Object> paramList = new ArrayList<Object>();
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", paramList);
        String mergeRange = TenantryBillDaoImpl.getMergeRange(params);
        String sql = "select" + this.getCols() + " from " + "GC_TENANTRYBILL" + " i \n";
        if ("mergeLevel".equals(mergeRange)) {
            sql = this.appendMergeLevelSql(orgTool, orgCacheVO, orgValidate, orgType, paramList, sql);
            sql = sql + " and (i.UNITCODE" + tempTableCondition.getCondition() + ")\n" + filterWhere;
        } else {
            sql = sql + " where (i.UNITCODE" + tempTableCondition.getCondition() + ")\n" + filterWhere;
        }
        sql = sql + " order by i.createtime desc";
        int pageNum = (Integer)params.get("pageNum");
        if (pageNum >= 1) {
            int pageSize = (Integer)params.get("pageSize");
            int offset = (pageNum - 1) * pageSize;
            return EntNativeSqlDefaultDao.getInstance().selectMapByPaging(sql, offset, offset + pageSize, paramList);
        }
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, paramList);
    }

    @Override
    public int countTenantryBills(TempTableCondition tempTableCondition, Map<String, Object> params) {
        String mergeUnit = (String)params.get("mergeUnit");
        String periodStr = (String)params.get("periodStr");
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(mergeUnit);
        if (null == orgCacheVO) {
            return 0;
        }
        String mergeRange = TenantryBillDaoImpl.getMergeRange(params);
        ArrayList<Object> paramList = new ArrayList<Object>();
        String filterWhere = InvestBillTool.getFilterWhere((Map)((Map)params.get("filterParam")), (String)"i.", paramList);
        String sql = " select i.BILLCODE\n from GC_TENANTRYBILL  i \n";
        if ("mergeLevel".equals(mergeRange)) {
            Date orgValidate = yp.formatYP().getEndDate();
            sql = this.appendMergeLevelSql(orgTool, orgCacheVO, orgValidate, orgType, paramList, sql);
            sql = sql + " and (i.UNITCODE" + tempTableCondition.getCondition() + ")\n" + filterWhere;
        } else {
            sql = sql + " where (i.UNITCODE" + tempTableCondition.getCondition() + ")\n" + filterWhere;
        }
        return EntNativeSqlDefaultDao.getInstance().count(sql, paramList);
    }

    @Override
    public void batchDeleteByIdList(List<String> ids) {
        this.batchDeleteSubByMasterIdList(ids);
        this.batchDeleteMasterByIdList(ids);
    }

    private void batchDeleteSubByMasterIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"masterId");
        String sql = "delete from GC_TENANTRYITEMBILL  where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    private void batchDeleteMasterByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(idList, (String)"ID");
        String sql = "delete from GC_TENANTRYBILL where " + inSql;
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    private String getCols() {
        return SqlUtils.getColumnsSqlByTableDefine((String)"GC_TENANTRYBILL", (String)"i");
    }

    private static String getMergeRange(Map<String, Object> params) {
        String mergeRange = "";
        Map filterParam = (Map)params.get("filterParam");
        if (null != filterParam && filterParam.size() > 0) {
            mergeRange = (String)filterParam.get("mergeRange");
        }
        return mergeRange;
    }

    private String appendMergeLevelSql(GcOrgCenterService instance, GcOrgCacheVO orgCacheVO, Date orgValidate, String orgType, List<Object> paramList, String sql) {
        int len = instance.getOrgCodeLength();
        int gcUnitParentsStartIndex = orgCacheVO.getGcParentStr().length() + 2;
        String parentStr = orgCacheVO.getParentStr();
        sql = sql + "join " + orgType + " INVESTUNIT on INVESTUNIT.code = i.UNITCODE \njoin " + orgType + " INVESTEDUNIT on INVESTEDUNIT.code = i.oppUnitCode  where 1=1 \n";
        sql = sql + " and substr(INVESTUNIT.gcparents, ?, " + len + " ) <> substr(INVESTEDUNIT.gcparents, ?, " + len + ")\n and INVESTUNIT.parents like '" + parentStr + "%%' \n and INVESTEDUNIT.parents like '" + parentStr + "%%' \n and INVESTUNIT.VALIDTIME <= ? and INVESTUNIT.INVALIDTIME >= ? \n and INVESTEDUNIT.VALIDTIME <= ? and INVESTEDUNIT.INVALIDTIME >= ? \n";
        paramList.addAll(Arrays.asList(gcUnitParentsStartIndex, gcUnitParentsStartIndex, orgValidate, orgValidate, orgValidate, orgValidate));
        return sql;
    }

    private YearPeriodObject getYearPeriodObject(Map<String, Object> params, int acctYear) {
        YearPeriodObject yp = null == params.get("periodStr") ? new YearPeriodObject(acctYear, 12) : new YearPeriodObject(null, (String)params.get("periodStr"));
        return yp;
    }
}

