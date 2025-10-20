/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.intf.Dimension
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
 *  com.jiuqi.dc.base.impl.assistdim.enums.ValueTypeEnum
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import com.jiuqi.bde.plugin.cloud_acca.assist.CloudAccaAssistPojo;
import com.jiuqi.bde.plugin.cloud_acca.util.CloudAccaFetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.impl.assistdim.enums.ValueTypeEnum;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import tk.mybatis.mapper.util.StringUtil;

public abstract class AbstractBdeCloudAccaDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeCloudAccaPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    private static final String JOIN_SQL = "LEFT JOIN ( SELECT CODE ,NAME FROM ${TABLENAME} ${ASNAME} WHERE (${ASNAME}.UNITCODE IN( SELECT ORG.CODE  FROM MD_ORG_FIN ORG INNER JOIN ( SELECT MDORG.PARENTS  FROM MD_ORG_FIN MDORG WHERE  MDORG.CODE = '${ORGCODE}') ORGT ON ORGT.PARENTS LIKE CONCAT(ORG.PARENTS, '%')) OR ${ASNAME}.UNITCODE ='-' ) AND ${ASNAME}.code='${TABLECODE}' GROUP BY NAME,CODE ) ${ASNAME} ON  ";

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        HashMap penetrateAssistDimMap = condi.getAssTypeList() == null ? new HashMap() : condi.getAssTypeList().stream().collect(Collectors.toMap(Dimension::getDimCode, item -> item, (k1, k2) -> k2));
        String tableName = CloudAccaFetchUtil.getBalanceTableName(condi.getIncludeUncharged(), condi.getAcctYear());
        StringBuilder sql = new StringBuilder();
        sql.append(this.buildQCSql(condi));
        sql.append(" UNION ALL  ");
        sql.append(" SELECT 0                AS ROWTYPE,  ");
        sql.append("        V.ID             AS ID,  ");
        sql.append("        V.VCHRID         AS VCHRID,  ");
        sql.append("        SUBJECT.ORIENT   AS ORIENT,  ");
        sql.append(sqlHandler.toChar("V.DIGEST") + " AS DIGEST,  ");
        sql.append(sqlHandler.toChar("V.ACCTPERIOD") + " AS ACCTPERIOD,  ");
        sql.append(sqlHandler.toChar(sqlHandler.concat(new String[]{sqlHandler.formatDate("V.CREATEDATE", sqlHandler.serialDateFmt()), "V.VCHRTYPECODE", "LPAD(" + sqlHandler.toChar("V.VCHRNUM") + ", 8, '0')", "LPAD(" + sqlHandler.toChar("V.ITEMORDER") + ", 8, '0')"})) + " AS ORDERNO,");
        sql.append(sqlHandler.day("V.CREATEDATE"));
        sql.append("                         AS ACCTDAY,");
        sql.append("        V.ACCTYEAR       AS ACCTYEAR,  ");
        sql.append("        V.ORGNC          AS ORGNC,  ");
        sql.append("        V.ORGND          AS ORGND,  ");
        sql.append("        V.CREDIT         AS CREDIT,  ");
        sql.append("        V.DEBIT          AS DEBIT,  ");
        sql.append("        SUBJECT.NAME     AS SUBJECTNAME,  ");
        sql.append("        ${MXORIGINFIELD} NULL AS YE,  ");
        sql.append("        NULL             AS ORGNYE,  ");
        sql.append(sqlHandler.concat(new String[]{"V.VCHRTYPECODE", "'-'", "V.VCHRNUM"}));
        sql.append("                        AS VCHRTYPE,  ");
        sql.append("       V.SUBJECTCODE    AS SUBJECTCODE  ");
        sql.append(" FROM GL_VOUCHERITEMASS_${YEAR}  V  ");
        sql.append("LEFT JOIN (SELECT SUBJECT.CODE, MAX(SUBJECT.NAME) AS NAME, MAX(SUBJECT.ORIENT) AS ORIENT ");
        sql.append("              FROM MD_ACCTSUBJECT SUBJECT  ");
        sql.append("             WHERE (SUBJECT.UNITCODE IN  ");
        sql.append("                   (SELECT ORG.CODE  ");
        sql.append("                      FROM MD_ORG_FIN ORG  ");
        sql.append("                     INNER JOIN (SELECT ORGFIN.PARENTS  ");
        sql.append("                                  FROM MD_ORG_FIN ORGFIN  ");
        sql.append("                                 WHERE ORGFIN.CODE = '${UNITCODE}') ORGFIN  ");
        sql.append("                        ON ORGFIN.PARENTS LIKE CONCAT(ORG.PARENTS, '%')) OR SUBJECT.UNITCODE='-' ) ");
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode()));
        sql.append("             GROUP BY SUBJECT.CODE) SUBJECT  ");
        sql.append("   ON SUBJECT.CODE = V.SUBJECTCODE ${MXASSJOINSQL}  ");
        sql.append("WHERE 1 = 1  ");
        sql.append("    ${VOUCHERGLMAINBODYCONDI}");
        sql.append(this.buildSubjectCondi("V", "SUBJECTCODE", condi.getSubjectCode()));
        sql.append(this.matchByRule("V", "UNITCODE", condi.getUnitCode(), "EQ"));
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\tAND V.BOOKCODE ='${BOOKCODE}' ");
        }
        sql.append("    ${PERIODSCOPE} ");
        sql.append("    ${MXASSWHERESQL}");
        sql.append("    ORDER BY ORDERNO");
        StringBuilder externalDimFieldSql = new StringBuilder();
        StringBuilder mxExternalDimFieldSql = new StringBuilder();
        StringBuilder externalAllDimFieldSql = new StringBuilder();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder mxAssJoinSql = new StringBuilder();
        StringBuilder assWhereSql = new StringBuilder();
        StringBuilder mxAssWhereSql = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalDimFieldSql.append(String.format("B.%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            groupField.append(String.format(",B.%1$s", assistMapping.getAccountAssistCode()));
            mxExternalDimFieldSql.append(String.format("V.%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalAllDimFieldSql.append(String.format(",B.%1$s", assistMapping.getAccountAssistCode()));
            Dimension dimension = (Dimension)penetrateAssistDimMap.get(assistMapping.getAssistCode());
            ValueTypeEnum valueTypeEnum = ValueTypeEnum.fromCode((String)((CloudAccaAssistPojo)assistMapping.getAccountAssist()).getValueType());
            switch (valueTypeEnum) {
                case BASEDATA: {
                    Variable joinVariable = new Variable();
                    StringBuilder mxJoinSql = new StringBuilder();
                    joinVariable.put("TABLENAME", assistMapping.getAccountAssistCode());
                    joinVariable.put("ASNAME", assistMapping.getAssistCode());
                    joinVariable.put("ORGCODE", condi.getUnitCode());
                    joinVariable.put("TABLECODE", dimension.getDimValue());
                    mxJoinSql = mxJoinSql.append(JOIN_SQL);
                    assJoinSql = assJoinSql.append(VariableParseUtil.parse((String)"LEFT JOIN ( SELECT CODE ,NAME FROM ${TABLENAME} ${ASNAME} WHERE (${ASNAME}.UNITCODE IN( SELECT ORG.CODE  FROM MD_ORG_FIN ORG INNER JOIN ( SELECT MDORG.PARENTS  FROM MD_ORG_FIN MDORG WHERE  MDORG.CODE = '${ORGCODE}') ORGT ON ORGT.PARENTS LIKE CONCAT(ORG.PARENTS, '%')) OR ${ASNAME}.UNITCODE ='-' ) AND ${ASNAME}.code='${TABLECODE}' GROUP BY NAME,CODE ) ${ASNAME} ON    B.${TABLENAME} = ${ASNAME}.CODE ", (Map)joinVariable.getVariableMap()));
                    mxAssJoinSql = mxAssJoinSql.append(VariableParseUtil.parse((String)mxJoinSql.append("  V.${TABLENAME} = ${ASNAME}.CODE ").toString(), (Map)joinVariable.getVariableMap()));
                    assWhereSql.append(this.matchByRule("B", assistMapping.getAccountAssistCode(), dimension.getDimValue(), dimension.getDimRule()));
                    mxAssWhereSql.append(this.matchByRule("V", assistMapping.getAccountAssistCode(), dimension.getDimValue(), dimension.getDimRule()));
                    externalDimFieldSql.append(String.format("%1$s.NAME AS %2$s,", assistMapping.getAssistCode(), assistMapping.getAssistCode() + "_NAME"));
                    groupField.append(String.format(",%1$s.NAME", assistMapping.getAssistCode()));
                    mxExternalDimFieldSql.append(String.format("%1$s.NAME AS %2$s,", assistMapping.getAssistCode(), assistMapping.getAssistCode() + "_NAME"));
                    break;
                }
                case DATE: 
                case STRING: {
                    assWhereSql.append(this.matchByRule("B", assistMapping.getAccountAssistCode(), dimension.getDimValue(), dimension.getDimRule()));
                    mxAssWhereSql.append(this.matchByRule("V", assistMapping.getAccountAssistCode(), dimension.getDimValue(), dimension.getDimRule()));
                    break;
                }
            }
        }
        Variable variable = new Variable();
        variable.put("ENDPERIOD", String.valueOf(condi.getEndPeriod()));
        variable.put("ACCYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("UNITCODE", String.valueOf(condi.getUnitCode()));
        variable.put("SUBTRACTENDMONTH", String.valueOf(condi.getStartPeriod() - 1));
        variable.put("STARTPERIOD", String.valueOf(condi.getStartPeriod()));
        variable.put("ORIGINFIELD", externalDimFieldSql.toString());
        variable.put("MXORIGINFIELD", mxExternalDimFieldSql.toString());
        variable.put("FIELDSQL", externalAllDimFieldSql.toString());
        variable.put("TABLENAME", tableName);
        variable.put("ASSJOINSQL", assJoinSql.toString());
        variable.put("GROUPFIELD", groupField.toString());
        variable.put("MXASSJOINSQL", mxAssJoinSql.toString());
        variable.put("ASSWHERESQL", assWhereSql.toString());
        variable.put("MXASSWHERESQL", mxAssWhereSql.toString());
        variable.put("YEAR", String.valueOf(condi.getAcctYear()));
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("GLMAINBODYCONDI", CloudAccaFetchUtil.penetrateGlMainBodyOrgMappingType(condi, "B"));
        variable.put("VOUCHERGLMAINBODYCONDI", CloudAccaFetchUtil.penetrateGlMainBodyOrgMappingType(condi, "V"));
        variable.put("PERIODSCOPE", this.buildVoucherPeriodScope(condi));
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(PenetrateUtil.replaceContext((String)lastSql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
    }

    private String buildVoucherPeriodScope(PenetrateBaseDTO condi) {
        String startAdjustPeriod = condi.getStartAdjustPeriod();
        String endAdjustPeriod = condi.getEndAdjustPeriod();
        String periodCondi = String.format("and V.ACCTPERIOD>=%1$s and V.ACCTPERIOD<=%2$s", condi.getStartPeriod(), condi.getEndPeriod());
        if (StringUtil.isNotEmpty((String)startAdjustPeriod) && StringUtil.isNotEmpty((String)endAdjustPeriod)) {
            int start = Integer.parseInt(startAdjustPeriod);
            int end = Integer.parseInt(endAdjustPeriod);
            if (start <= 13 && end >= 13) {
                periodCondi = String.format("AND (V.ACCTPERIOD>=%1$s and V.ACCTPERIOD<=%2$s or (V.ACCTPERIOD = 13 AND  V.VCHRSRCTYPE = 'AUDIT'))", condi.getStartPeriod(), condi.getEndPeriod().toString());
            }
        }
        return periodCondi;
    }

    private String buildQCSql(PenetrateBaseDTO condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  ");
        sql.append("       NULL AS ID,  ");
        sql.append("       NULL AS VCHRID,  ");
        sql.append("       B.ORIENT AS ORIENT,  ");
        sql.append("       '\u671f\u521d\u4f59\u989d' AS DIGEST,  ");
        sql.append("       '${STARTPERIOD}' AS ACCTPERIOD,  ");
        sql.append("       '1' as ORDERNO,");
        sql.append("       NULL AS ACCTDAY,  ");
        sql.append("       ${ACCYEAR} AS ACCTYEAR,  ");
        sql.append("       NULL AS ORGNC,  ");
        sql.append("       NULL AS ORGND,  ");
        sql.append("       NULL AS CREDIT,  ");
        sql.append("       NULL AS DEBIT,  ");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  ");
        sql.append("       ${ORIGINFIELD}   ");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(B.BF * B.ORIENT) AS YE,");
            sql.append("       SUM(B.BF * B.ORIENT) AS ORGNYE,");
        } else {
            sql.append("       SUM(B.BF * B.ORIENT + B.DSUM_${SUBTRACTENDMONTH}-B.CSUM_${SUBTRACTENDMONTH}) AS YE,");
            sql.append("       SUM(B.BF * B.ORIENT + B.DSUM_${SUBTRACTENDMONTH}-B.CSUM_${SUBTRACTENDMONTH}) AS ORGNYE,");
        }
        sql.append("      NULL AS VCHRTYPE,  ");
        sql.append("      B.SUBJECTCODE AS SUBJECTCODE  ");
        sql.append(" FROM ${TABLENAME} B  ");
        sql.append("LEFT JOIN (SELECT  SUBJECT.CODE, MAX(SUBJECT.NAME) AS NAME  ");
        sql.append("              FROM MD_ACCTSUBJECT SUBJECT  ");
        sql.append("             WHERE (SUBJECT.UNITCODE IN  ");
        sql.append("                   (SELECT ORG.CODE  ");
        sql.append("                      FROM MD_ORG_FIN ORG  ");
        sql.append("                     INNER JOIN (SELECT ORGFIN.PARENTS  ");
        sql.append("                                  FROM MD_ORG_FIN ORGFIN  ");
        sql.append("                                 WHERE ORGFIN.CODE = '${UNITCODE}') ORGFIN  ");
        sql.append("                        ON ORGFIN.PARENTS LIKE CONCAT(ORG.PARENTS, '%')) OR SUBJECT.UNITCODE='-' ) ");
        sql.append("             GROUP BY SUBJECT.CODE) SUBJECT  ");
        sql.append("   ON SUBJECT.CODE = B.SUBJECTCODE ${ASSJOINSQL}  ");
        sql.append("WHERE 1 = 1  ");
        sql.append("    ${ASSWHERESQL}");
        sql.append("    ${GLMAINBODYCONDI}");
        sql.append(this.matchByRule("B", "SUBJECTCODE", condi.getSubjectCode(), "EQ"));
        sql.append(this.matchByRule("B", "UNITCODE", condi.getUnitCode(), "EQ"));
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\tAND B.BOOKCODE = '${BOOKCODE}' ");
        }
        sql.append("GROUP BY B.SUBJECTCODE,SUBJECT.NAME ,B.ORIENT ${GROUPFIELD}");
        return sql.toString();
    }
}

