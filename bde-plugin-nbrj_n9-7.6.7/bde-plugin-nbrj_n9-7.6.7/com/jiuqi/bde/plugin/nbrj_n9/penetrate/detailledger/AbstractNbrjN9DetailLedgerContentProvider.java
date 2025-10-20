/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nbrj_n9.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nbrj_n9.BdeNbrjN9PluginType;
import com.jiuqi.bde.plugin.nbrj_n9.util.NbrjN9FetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractNbrjN9DetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeNbrjN9PluginType nbrjN9PluginType;

    public String getPluginType() {
        return this.nbrjN9PluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        ArrayList<Integer> args = new ArrayList<Integer>();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       %d as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       NULL AS PZNUMBER,  \n");
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       MASTER.SUBJECTCODE AS SUBJECTCODE,  \n");
        sql.append("       MASTER.SUBJECTNAME AS SUBJECTNAME,   \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       0 AS DEBIT,  \n");
        sql.append("       0 AS CREDIT,  \n");
        sql.append("       0 AS ORGND,  \n");
        sql.append("       0 AS ORGNC,  \n");
        sql.append("       SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.C ELSE 0 END) AS YE, \n");
        sql.append("       SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.WC ELSE 0 END)AS ORGNYE \n");
        sql.append("  FROM \n");
        sql.append("       (SELECT B.CODE00 AS SUBJECTCODE,  \n");
        sql.append("               SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("               SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append("               0 AS PERIOD, \n");
        sql.append("               SUM(").append(sqlHandler.nullToValue("B.SCYBALANCE", "0")).append("* SUBJECT.ORIENT) AS C,\n");
        sql.append("               SUM(").append(sqlHandler.nullToValue("B.FCYBALANCE", "0")).append("* SUBJECT.ORIENT) AS WC \n");
        sql.append("          FROM  BALANCE B  \n");
        sql.append("          LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON SUBJECT.CODE  = B.CODE00 \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "B."));
        sql.append("           AND B.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("           AND B.MONTH = (SELECT MIN(MONTH)  \n");
        sql.append("                            FROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("           AND B.CODE00 IS NOT NULL  \n");
        sql.append("         GROUP BY B.CODE00, SUBJECT.ORIENT, SUBJECT.NAME \n");
        sql.append("         UNION ALL \n");
        sql.append("        SELECT VI.CODE00 as SUBJECTCODE,  \n");
        sql.append("               SUBJECT.NAME AS SUBJECTNAME, \n");
        sql.append("               SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append("               VI.MONTH AS PERIOD, \n");
        sql.append("               SUM(").append(sqlHandler.nullToValue("VI.SCY", "0")).append(") * VI.VDC AS C,\n");
        sql.append("               SUM(").append(sqlHandler.nullToValue("VI.FCY", "0")).append(") * VI.VDC AS WC \n");
        sql.append("          FROM IVOUCHER VI \n");
        sql.append("          LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON SUBJECT.CODE = VI.CODE00 \n");
        sql.append("         WHERE 1 = 1 \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "VI."));
        sql.append("           AND VI.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("           AND VI.MONTH > (SELECT MIN(MONTH) \n");
        sql.append("                     \t\t FROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("           AND VI.MONTH < ${STARTPERIOD} \n");
        sql.append("           AND VI.CODE00 IS NOT NULL  \n");
        sql.append("         GROUP BY VI.CODE00, SUBJECT.ORIENT, VI.VDC, SUBJECT.NAME, VI.MONTH ) MASTER \n");
        sql.append(String.format("         WHERE MASTER.SUBJECTCODE LIKE '%s'  \n", condi.getSubjectCode()));
        sql.append("         GROUP BY MASTER.SUBJECTCODE, MASTER.SUBJECTNAME, MASTER.ORIENT \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT T.* FROM (  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VI.INO AS ID,  \n");
        sql.append("       VI.HVICODE AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VI.MONTH AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VI.VDATE")));
        sql.append(sqlHandler.concatBySeparator(" ", new String[]{"' '", String.format("%s", sqlHandler.toChar("VI.VNO"))})).append(" AS VCHRTYPE, \n");
        sql.append(String.format("       %s AS PZNUMBER,  \n", sqlHandler.lpad("VI.VNO", 15, "0")));
        sql.append("       VI.INO AS ORDERID,  \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append("       VI.CODE00 AS SUBJECTCODE,  \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VI.EXPL")));
        sql.append("       CASE WHEN VI.VDC = 1 THEN VI.SCY ELSE 0 END AS DEBIT,  \n");
        sql.append("       CASE WHEN VI.VDC = -1 THEN VI.SCY ELSE 0 END AS CREDIT,  \n");
        sql.append("       CASE WHEN VI.VDC = 1 THEN VI.FCY ELSE 0 END AS ORGND,  \n");
        sql.append("       CASE WHEN VI.VDC = -1 THEN VI.FCY ELSE 0 END AS ORGNC,  \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("  FROM IVOUCHER VI  \n");
        sql.append("  LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON SUBJECT.CODE = VI.CODE00 \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "VI."));
        sql.append("   AND VI.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND VI.MONTH > (SELECT MIN(MONTH) \n");
        sql.append("                     FROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("   AND VI.MONTH<=${ENDPERIOD} \n");
        sql.append("   AND VI.MONTH>=${STARTPERIOD} \n");
        sql.append(this.buildSubjectCondi("VI", "CODE00", condi.getSubjectCode())).append(" \n");
        sql.append("   ) T ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, PZNUMBER, ORDERID \n");
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

