/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam
 *  com.jiuqi.gc.financial.status.enums.FinancialStatusEnum
 *  com.jiuqi.gc.financialcubes.financialstatus.entity.FinancialUnitStatusEO
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 */
package com.jiuqi.gc.financialcubes.relatedtransactionstatus.dao.impl;

import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam;
import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import com.jiuqi.gc.financialcubes.financialstatus.entity.FinancialUnitStatusEO;
import com.jiuqi.gc.financialcubes.relatedtransactionstatus.dao.RelatedTransactionUnitStatusDao;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RelatedTransactionUnitStatusDaoImpl
extends GcDbSqlGenericDAO<FinancialUnitStatusEO, String>
implements RelatedTransactionUnitStatusDao {
    public RelatedTransactionUnitStatusDaoImpl() {
        super(FinancialUnitStatusEO.class);
    }

    @Override
    public List<FinancialUnitStatusEO> selectByUnitCode(FinancialStatusParam param, String tempGroupId, String moduleCode, String status) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID,DATATIME,UNITCODE,PERIODTYPE,CREATOR,STATUS,VALIDTIME,INVALIDTIME,UPDATETIME \n");
        sql.append("FROM %1$s WHERE DATATIME = ? AND MODULECODE = ? AND STATUS = ? \n");
        sql.append(" AND UNITCODE IN ( SELECT TBCODE FROM GC_IDTEMPORARY WHERE GROUP_ID = ? ) \n");
        String formatSql = String.format(sql.toString(), "GC_FINANCIAL_UNIT_STATUS");
        return this.selectEntity(formatSql, new Object[]{param.getDataTime(), moduleCode, status, tempGroupId});
    }

    @Override
    public List<FinancialUnitStatusEO> listCloseUnit(FinancialStatusParam param, String unitParentsString, Date date) {
        StringBuilder sql = new StringBuilder();
        if (IdTemporaryTableUtils.IS_MYSQL) {
            sql.append("SELECT T.UNITCODE,T.CREATOR,T.UPDATETIME AS INVALIDTIME, -1 AS ORDINAL  \n");
        } else {
            sql.append("SELECT T.UNITCODE,to_char(T.CREATOR) AS CREATOR,T.UPDATETIME AS INVALIDTIME, -1 AS ORDINAL  \n");
        }
        sql.append("  FROM GC_FINANCIAL_UNIT_STATUS T \n");
        sql.append("  JOIN %1$s ORG ON T.UNITCODE = ORG.CODE \n");
        sql.append("   AND ORG.PARENTS LIKE ?\n");
        sql.append("   AND ORG.VALIDTIME < ? AND ORG.INVALIDTIME >= ? \n");
        sql.append(" WHERE T.DATATIME = ? \n");
        sql.append("   AND T.STATUS = 2 \n");
        sql.append("   AND T.MODULECODE = ? \n");
        sql.append(" ORDER BY T.VALIDTIME DESC \n");
        String formatSql = String.format(sql.toString(), param.getOrgType());
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(unitParentsString + "%");
        args.add(date);
        args.add(date);
        args.add(param.getDataTime());
        args.add("RELATED_TRANSACTION");
        return this.selectEntity(formatSql, args);
    }

    @Override
    public List<FinancialUnitStatusEO> listOpenUnit(FinancialStatusParam param, String unitParentsString, Date date) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT UNITCODE,CREATOR,VALIDTIME ");
        sql.append("  FROM (SELECT T.UNITCODE,T.CREATOR,T.VALIDTIME \n");
        sql.append("        FROM GC_FINANCIAL_UNIT_STATUS T \n");
        sql.append("        JOIN %1$s ORG ON T.UNITCODE = ORG.CODE \n");
        sql.append("         AND ORG.PARENTS LIKE ? \n");
        sql.append("         AND ORG.VALIDTIME < ? AND ORG.INVALIDTIME >= ? \n");
        sql.append("       WHERE T.DATATIME = ? \n");
        sql.append("         AND T.STATUS = 1 \n");
        sql.append("         AND T.MODULECODE = ? \n");
        sql.append("      UNION ALL \n");
        sql.append("      SELECT CODE AS UNITCODE, null AS CREATOR, null AS VALIDTIME FROM %1$s \n");
        sql.append("       WHERE 1 = 1 \n");
        sql.append("         AND PARENTS LIKE ? \n");
        sql.append("         AND CODE IN (SELECT PARENTCODE FROM %1$s WHERE VALIDTIME <? AND INVALIDTIME >=? GROUP BY PARENTCODE ) \n");
        sql.append("         AND CODE NOT IN (SELECT UNITCODE FROM GC_FINANCIAL_UNIT_STATUS WHERE DATATIME = ?) \n");
        sql.append("         AND VALIDTIME <? AND INVALIDTIME >=?  \n");
        sql.append("       GROUP BY CODE) T \n");
        sql.append("       ORDER BY VALIDTIME DESC \n");
        String formatSql = String.format(sql.toString(), param.getOrgType());
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(unitParentsString + "%");
        args.add(date);
        args.add(date);
        args.add(param.getDataTime());
        args.add("RELATED_TRANSACTION");
        args.add(unitParentsString + "%");
        args.add(date);
        args.add(date);
        args.add(param.getDataTime());
        args.add(date);
        args.add(date);
        return this.selectEntity(formatSql, args);
    }

    @Override
    public List<FinancialUnitStatusEO> selectUnitCodesByDataTime(FinancialStatusParam param, String tempGroupId, String moduleCode, String status) {
        String yearPart = param.getDataTime().substring(0, 4) + "Y";
        String startDataTime = yearPart + "0001";
        String endDataTime = yearPart + "0012";
        boolean isClosed = FinancialStatusEnum.CLOSE.getCode().equals(status);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID,DATATIME,UNITCODE,PERIODTYPE,CREATOR,STATUS,VALIDTIME,INVALIDTIME,UPDATETIME \n");
        sql.append("  FROM ").append("GC_FINANCIAL_UNIT_STATUS \n");
        if (isClosed) {
            sql.append(" WHERE DATATIME <= ? AND DATATIME > ? AND STATUS = ? AND MODULECODE = ? \n");
        } else {
            sql.append(" WHERE DATATIME >= ? AND DATATIME < ? AND STATUS = ? AND MODULECODE = ? \n");
        }
        sql.append(" AND UNITCODE IN ( SELECT TBCODE FROM GC_IDTEMPORARY WHERE GROUP_ID = ? ) \n");
        ArrayList<String> args = new ArrayList<String>();
        args.add(isClosed ? endDataTime : startDataTime);
        args.add(param.getDataTime());
        args.add(isClosed ? FinancialStatusEnum.CLOSE.getCode() : FinancialStatusEnum.OPEN.getCode());
        args.add("RELATED_TRANSACTION");
        args.add(tempGroupId);
        return this.selectEntity(sql.toString(), args.toArray());
    }
}

