/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.invest.investbill.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class FairValueBillDaoImpl
implements FairValueBillDao {
    @Override
    public String queryFvchBillCode(String investUnit, String investedUnit, Integer acctYear) {
        String sql = "select t.billCode \nfrom GC_FVCHBILL t \nwhere t.unitCode=? \nand t.investedUnit=? \nand t.acctYear=? \n";
        List rs = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{investUnit, investedUnit, acctYear});
        if (!rs.isEmpty()) {
            return String.valueOf(((Map)rs.get(0)).get("BILLCODE"));
        }
        return null;
    }

    @Override
    public List<DefaultTableEntity> getFvchFixedItemBills(String masterId, String investUnit, String investedUnit, Integer acctYear) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        String sql = " select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_FVCH_FIXEDITEM", (String)"t") + " \n from " + "GC_FVCH_FIXEDITEM" + " t \nwhere 1=1 \n";
        if (!StringUtils.isEmpty((String)masterId)) {
            sql = sql + " and t.masterId=?\n";
            paramList.add(masterId);
        }
        if (!StringUtils.isEmpty((String)investUnit)) {
            sql = sql + " and t.UNITCODE=?\n";
            paramList.add(investUnit);
        }
        if (!StringUtils.isEmpty((String)investedUnit)) {
            sql = sql + " and t.investedUnit=?\n";
            paramList.add(investedUnit);
        }
        if (acctYear != null) {
            sql = sql + " and t.acctYear=?\n";
            paramList.add(acctYear);
        }
        return InvestBillTool.queryBySql((String)sql, paramList);
    }

    @Override
    public List<DefaultTableEntity> getFvchOtherItemBills(String masterId, String investUnit, String investedUnit, Integer acctYear) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        String sql = " select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_FVCH_OTHERITEM", (String)"t") + " \n from " + "GC_FVCH_OTHERITEM" + " t \nwhere 1=1 \n";
        if (!StringUtils.isEmpty((String)masterId)) {
            sql = sql + " and t.masterId=?\n";
            paramList.add(masterId);
        }
        if (!StringUtils.isEmpty((String)investUnit)) {
            sql = sql + " and t.UNITCODE=?\n";
            paramList.add(investUnit);
        }
        if (!StringUtils.isEmpty((String)investedUnit)) {
            sql = sql + " and t.investedUnit=?\n";
            paramList.add(investedUnit);
        }
        if (acctYear != null) {
            sql = sql + " and t.acctYear=? \n";
            paramList.add(acctYear);
        }
        return InvestBillTool.queryBySql((String)sql, paramList);
    }

    @Override
    public List<Map<String, Object>> queryFvchFixedItemBills(Map<String, Object> params) {
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        String mergeUnit = (String)params.get("mergeUnit");
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        List investBillIds = (List)params.get("investBillIds");
        String appendSql = null;
        if (!CollectionUtils.isEmpty((Collection)investBillIds)) {
            String inSql = SqlUtils.getConditionOfIdsUseOr((Collection)investBillIds, (String)"i.ID");
            appendSql = "and (UNITCODE,INVESTEDUNIT) in (select i.UNITCODE,i.INVESTEDUNIT from GC_INVESTBILL i where " + inSql + ")";
        }
        YearPeriodObject yp = new YearPeriodObject(acctYear, 12);
        Date orgValidate = yp.formatYP().getEndDate();
        String parentStr = this.getInvestOrgParentStr(mergeUnit, acctYear, orgType);
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_FVCH_FIXEDITEM", (String)"t") + " \nfrom " + "GC_FVCH_FIXEDITEM" + " t join " + orgType + " c on(t.UNITCODE=c.code)\nwhere \n c.VALIDTIME<=? and c.INVALIDTIME>= ? \n and c.parents like '" + parentStr + "%'\n";
        if (!CollectionUtils.isEmpty((Collection)investBillIds)) {
            sql = sql + appendSql;
        }
        sql = sql + " and t.acctYear=? \n order by t.UNITCODE,t.INVESTEDUNIT,t.ORDINAL \n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{orgValidate, orgValidate, acctYear});
    }

    @Override
    public List<Map<String, Object>> queryFvchOtherItemBills(Map<String, Object> params) {
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        String mergeUnit = (String)params.get("mergeUnit");
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        String appendSql = null;
        List investBillIds = (List)params.get("investBillIds");
        if (!CollectionUtils.isEmpty((Collection)investBillIds)) {
            String inSql = SqlUtils.getConditionOfIdsUseOr((Collection)investBillIds, (String)"i.ID");
            appendSql = "and (UNITCODE,INVESTEDUNIT) in (select i.UNITCODE,i.INVESTEDUNIT from GC_INVESTBILL i where " + inSql + ")";
        }
        String parentStr = this.getInvestOrgParentStr(mergeUnit, acctYear, orgType);
        YearPeriodObject yp = new YearPeriodObject(acctYear, 12);
        Date orgValidate = yp.formatYP().getEndDate();
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_FVCH_OTHERITEM", (String)"t") + " \n from " + "GC_FVCH_OTHERITEM" + " t join " + orgType + " c on(t.UNITCODE=c.code)\nwhere \n c.VALIDTIME<=? and c.INVALIDTIME>= ? \n and c.parents like '" + parentStr + "%'\n";
        if (!CollectionUtils.isEmpty((Collection)investBillIds)) {
            sql = sql + appendSql;
        }
        sql = sql + "and t.acctYear=? \n  order by t.UNITCODE,t.INVESTEDUNIT,t.ORDINAL \n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{orgValidate, orgValidate, acctYear});
    }

    private String getInvestOrgParentStr(String mergeUnit, int acctYear, String orgType) {
        YearPeriodObject yp = new YearPeriodObject(acctYear, 12);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = instance.getOrgByCode(mergeUnit);
        return orgCacheVO.getParentStr();
    }

    @Override
    public Map<String, Object> getByUnitAndYear(String investUnitCode, String investedUnitCode, int acctYear) {
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_FVCHBILL", (String)"i") + " from " + "GC_FVCHBILL" + " i where i.unitCode=? \n and i.investedUnit=? \n and i.acctYear=? \n";
        List records = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{investUnitCode, investedUnitCode, acctYear});
        return CollectionUtils.isEmpty((Collection)records) ? null : (Map)records.get(0);
    }

    @Override
    public void deleteMaster(String masterId) {
        if (StringUtils.isEmpty((String)masterId)) {
            return;
        }
        String sql = " delete from GC_FVCHBILL where ID = ? \n";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{masterId});
    }

    @Override
    public Map<String, Object> getMasterByYearAndSrcId(int acctYear, String srcId) {
        if (StringUtils.isEmpty((String)srcId)) {
            return null;
        }
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_FVCHBILL", (String)"i") + " from " + "GC_FVCHBILL" + " i where i." + "ACCTYEAR" + " = ? \n and i." + "SRCID" + " = ? \n";
        List data = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{acctYear, srcId});
        if (!CollectionUtils.isEmpty((Collection)data)) {
            return (Map)data.get(0);
        }
        return null;
    }
}

