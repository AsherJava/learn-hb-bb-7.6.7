/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.invest.relation.dao.Impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.invest.relation.dao.InvestRelationDao;
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
public class InvestRelationDaoImpl
implements InvestRelationDao {
    @Override
    public List<Map<String, Object>> listInvestedBill(Map<String, Object> params) {
        String mergeType;
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        int acctPeriod = ConverterUtils.getAsIntValue((Object)params.get("acctPeriod"));
        YearPeriodObject yp = this.getYearPeriodObject(params, acctYear);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = gcOrgCenterService.getOrgByCode((String)params.get("mergeUnit"));
        if (null == orgCacheVO) {
            return new ArrayList<Map<String, Object>>();
        }
        Date orgValidate = yp.formatYP().getEndDate();
        ArrayList<Object> paramList = new ArrayList<Object>();
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i \n join " + orgType + " c on(i.INVESTEDUNIT=c.code) \n and c.VALIDTIME<=? and c.INVALIDTIME>= ? \n and c.code = '" + orgCacheVO.getCode() + "'\n  and i.ACCTYEAR=" + acctYear + " \n  and i.PERIOD=" + acctPeriod + " \n";
        paramList.addAll(Arrays.asList(orgValidate, orgValidate));
        String investDefineCode = (String)params.get("defineCode");
        if (!StringUtils.isEmpty((String)investDefineCode)) {
            sql = sql + "  and i.DEFINECODE=?  \n";
            paramList.add(investDefineCode);
        }
        if (!StringUtils.isEmpty((String)(mergeType = (String)params.get("mergeType")))) {
            sql = sql + "  and i.MERGETYPE=?  \n";
            paramList.add(mergeType);
        }
        sql = sql + "  order by i.CREATETIME desc \n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, paramList);
    }

    @Override
    public List<Map<String, Object>> listInvestBill(Map<String, Object> params) {
        String mergeType;
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        int acctPeriod = ConverterUtils.getAsIntValue((Object)params.get("acctPeriod"));
        YearPeriodObject yp = this.getYearPeriodObject(params, acctYear);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = gcOrgCenterService.getOrgByCode((String)params.get("mergeUnit"));
        if (null == orgCacheVO) {
            return new ArrayList<Map<String, Object>>();
        }
        Date orgValidate = yp.formatYP().getEndDate();
        ArrayList<Object> paramList = new ArrayList<Object>();
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i \n join " + orgType + " c on(i.UNITCODE=c.code) \n and c.VALIDTIME<=? and c.INVALIDTIME>= ? \n and c.code = '" + orgCacheVO.getCode() + "'\n  and i.ACCTYEAR=" + acctYear + " \n  and i.PERIOD=" + acctPeriod + " \n";
        paramList.addAll(Arrays.asList(orgValidate, orgValidate));
        String investDefineCode = (String)params.get("defineCode");
        if (!StringUtils.isEmpty((String)investDefineCode)) {
            sql = sql + "  and i.DEFINECODE=?  \n";
            paramList.add(investDefineCode);
        }
        if (!StringUtils.isEmpty((String)(mergeType = (String)params.get("mergeType")))) {
            sql = sql + "  and i.MERGETYPE=?  \n";
            paramList.add(mergeType);
        }
        sql = sql + "  order by i.CREATETIME desc \n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, paramList);
    }

    @Override
    public List<Map<String, Object>> listBill(Map<String, Object> params) {
        String mergeType;
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        YearPeriodObject yp = this.getYearPeriodObject(params, acctYear);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        Date orgValidate = yp.formatYP().getEndDate();
        ArrayList<Object> paramList = new ArrayList<Object>();
        String sql = "select " + this.getCols() + " from " + "GC_INVESTBILL" + " i \n join " + orgType + " c on(i.UNITCODE=c.code) \n and c.VALIDTIME<=? and c.INVALIDTIME>= ? \n  and i.ACCTYEAR=" + acctYear + " \n";
        paramList.addAll(Arrays.asList(orgValidate, orgValidate));
        String investDefineCode = (String)params.get("defineCode");
        if (!StringUtils.isEmpty((String)investDefineCode)) {
            sql = sql + "  and i.DEFINECODE=?  \n";
            paramList.add(investDefineCode);
        }
        if (!StringUtils.isEmpty((String)(mergeType = (String)params.get("mergeType")))) {
            sql = sql + "  and i.MERGETYPE=?  \n";
            paramList.add(mergeType);
        }
        sql = sql + "  order by i.CREATETIME desc \n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, paramList);
    }

    private YearPeriodObject getYearPeriodObject(Map<String, Object> params, int acctYear) {
        YearPeriodObject yp = null == params.get("periodStr") ? new YearPeriodObject(acctYear, 12) : new YearPeriodObject(null, (String)params.get("periodStr"));
        return yp;
    }

    private String getCols() {
        return SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"i");
    }
}

