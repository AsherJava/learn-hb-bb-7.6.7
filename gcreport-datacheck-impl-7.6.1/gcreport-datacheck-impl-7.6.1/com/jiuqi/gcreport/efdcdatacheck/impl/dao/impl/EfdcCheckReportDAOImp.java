/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dao.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.EfdcCheckReportDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckReportLogEO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
public class EfdcCheckReportDAOImp
extends GcDbSqlGenericDAO<EfdcCheckReportLogEO, String>
implements EfdcCheckReportDAO {
    public EfdcCheckReportDAOImp() {
        super(EfdcCheckReportLogEO.class);
    }

    @Override
    public List<EfdcCheckReportLogEO> findAllByConditions(Map<String, Object> conditionMap) {
        String userName = ConverterUtils.getAsString((Object)conditionMap.get("userName"));
        StringBuilder sql = new StringBuilder();
        sql.append("  select " + SqlUtils.getColumnsSqlByEntity(EfdcCheckReportLogEO.class, (String)"e") + "\n");
        sql.append(" from ").append("GC_EFDCCHECKREPORTLOG").append(" e \n");
        if (!StringUtils.isEmpty((String)userName)) {
            sql.append(" left join ").append("GC_EFDCCHECKREPORTLOG_SHARE").append(" s \n");
            sql.append(" on e.ID = s.FILE_KEY \n");
        }
        sql.append(" where ");
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append(this.buildPageCondSQL(conditionMap, params));
        if (!StringUtils.isEmpty((String)userName)) {
            sql.append(" and (e.CREATE_USER = ? or s.SHARED_USER = ?) \n");
            params.add(userName);
            params.add(userName);
        }
        sql.append("  order by e.create_date desc  \n ");
        List eoList = this.selectEntity(sql.toString(), params);
        return eoList;
    }

    @Override
    public List<EfdcCheckReportLogEO> findAllByPageAndConditions(Integer pageSize, Integer pageNum, Map<String, Object> conditionMap) {
        String userName = ConverterUtils.getAsString((Object)conditionMap.get("userName"));
        StringBuilder sql = new StringBuilder();
        sql.append("  select " + SqlUtils.getColumnsSqlByEntity(EfdcCheckReportLogEO.class, (String)"e") + " \n");
        sql.append(" from ").append("GC_EFDCCHECKREPORTLOG").append("  e  \n");
        if (!StringUtils.isEmpty((String)userName)) {
            sql.append(" left join ").append("GC_EFDCCHECKREPORTLOG_SHARE").append(" s \n");
            sql.append(" on e.ID = s.FILE_KEY \n");
        }
        sql.append(" where ");
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append(this.buildPageCondSQL(conditionMap, params));
        if (!StringUtils.isEmpty((String)userName)) {
            sql.append(" and (e.CREATE_USER = ? or s.SHARED_USER = ?) \n");
            params.add(userName);
            params.add(userName);
        }
        sql.append("  order by e.create_date desc  \n ");
        List eoList = this.selectEntityByPaging(sql.toString(), pageSize * pageNum, pageSize * (pageNum + 1), params);
        return eoList;
    }

    private String buildPageCondSQL(Map<String, Object> conditionMap, List<Object> params) {
        Object selectAdjustCode;
        Object schemeId;
        Object taskId;
        Object defaultPeriodCondValue;
        Object acctPeriodCondValue;
        Object acctYearCondValue;
        StringBuilder builder = new StringBuilder();
        builder.append("1=1");
        String unitIdCondValue = (String)conditionMap.get("unitId");
        if (!StringUtils.isEmpty((String)unitIdCondValue)) {
            builder.append(" and e.unitId = ? \n");
            params.add(unitIdCondValue);
        }
        if (null != (acctYearCondValue = conditionMap.get("acctYear")) && !"".equals(acctYearCondValue)) {
            builder.append(" and e.acct_year = ? \n");
            params.add(acctYearCondValue);
        }
        if (null != (acctPeriodCondValue = conditionMap.get("acctPeriod")) && !"".equals(acctPeriodCondValue)) {
            builder.append(" and e.acct_period = ? \n");
            params.add(acctPeriodCondValue);
        }
        if (null != (defaultPeriodCondValue = conditionMap.get("defaultPeriod")) && !"".equals(defaultPeriodCondValue)) {
            builder.append(" and e.default_period = ? \n");
            params.add(defaultPeriodCondValue);
        }
        if (null != (taskId = conditionMap.get("taskId")) && !"".equals(taskId)) {
            builder.append(" and e.taskId = ? \n");
            params.add(taskId);
        }
        if (null != (schemeId = conditionMap.get("schemeId")) && !"".equals(schemeId)) {
            builder.append(" and e.schemeId =? \n");
            params.add(schemeId);
        }
        if (!ObjectUtils.isEmpty(selectAdjustCode = conditionMap.get("selectAdjustCode")) && DimensionUtils.isExistAdjust((String)((String)taskId))) {
            builder.append(" and e.ADJUST = ? \n");
            params.add(selectAdjustCode);
        }
        return builder.toString();
    }

    @Override
    public List<EfdcCheckReportLogEO> findCheckReportByRecids(Set<String> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select " + SqlUtils.getColumnsSqlByEntity(EfdcCheckReportLogEO.class, (String)"e") + "\n");
        sql.append(" from GC_EFDCCHECKREPORTLOG   e \n");
        sql.append("   where " + SqlUtils.getConditionOfIdsUseOr(ids, (String)"e.id") + " \n");
        return this.selectEntity(sql.toString(), new Object[0]);
    }

    @Override
    public void batchDeleteByRecids(Set<String> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append("\tdelete from GC_EFDCCHECKREPORTLOG   \n");
        sql.append("   where " + SqlUtils.getConditionOfIdsUseOr(ids, (String)"id") + " \n");
        this.execute(sql.toString());
    }
}

