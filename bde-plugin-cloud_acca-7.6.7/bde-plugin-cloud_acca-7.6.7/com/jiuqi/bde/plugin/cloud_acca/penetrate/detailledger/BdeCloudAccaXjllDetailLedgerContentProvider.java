/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.impl.assistdim.enums.ValueTypeEnum
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor;
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
import org.springframework.stereotype.Component;

@Component
public class BdeCloudAccaXjllDetailLedgerContentProvider
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
        Assert.isNotEmpty((String)condi.getCashCode(), (String)"\u73b0\u6d41\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        HashMap penetrateAssistDimMap = condi.getAssTypeList() == null ? new HashMap() : condi.getAssTypeList().stream().collect(Collectors.toMap(Dimension::getDimCode, item -> item, (k1, k2) -> k2));
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  0 AS ROWTYPE,");
        sql.append(" VCHR.ID AS ID,");
        sql.append(" VCHR.VCHRID AS VCHRID,");
        sql.append(" VCHR.VCHRTYPE || '-' || VCHR.VCHRNUM AS VCHRTYPE,  \n");
        sql.append(" 1 as ORIENT, \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VCHR.ACCTPERIOD AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VCHR.VCHRDATE")));
        sql.append("\tITEM.CFITEM as MD_CFITEM,");
        sql.append("    CFITEM.NAME as MD_CFITEM_NAME, ");
        if (!com.jiuqi.bi.util.StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append("          ITEM.SUBJECTCODE AS MD_ACCTSUBJECT,  \n");
            sql.append("          ITEM.SUBJECTNAME AS MD_ACCTSUBJECT_NAME,\n");
        }
        sql.append("\tCASE WHEN VCHR.ACCTPERIOD = ${ENDPERIOD} THEN ITEM.DEBIT ELSE 0 end  AS DEBIT,");
        sql.append("\tCASE WHEN VCHR.ACCTPERIOD = ${ENDPERIOD} THEN ITEM.CREDIT ELSE 0 end  AS CREDIT,");
        sql.append("\tCASE WHEN VCHR.ACCTPERIOD = ${ENDPERIOD} THEN ITEM.DEBIT ELSE 0 end  AS ORGND,");
        sql.append("\tCASE WHEN VCHR.ACCTPERIOD = ${ENDPERIOD} THEN ITEM.CREDIT ELSE 0 end  AS ORGNC,");
        sql.append("\tCASE WHEN VCHR.ACCTPERIOD >= 1 and VCHR.ACCTPERIOD <= ${ENDPERIOD} THEN ITEM.INFLUENCEAMOUNT ELSE 0 end  AS YE,");
        sql.append(" \tCASE WHEN VCHR.ACCTPERIOD >= 1 and VCHR.ACCTPERIOD <= ${ENDPERIOD} THEN ITEM.INFLUENCEAMOUNT ELSE 0 end  AS ORGNYE,");
        sql.append("       ${ORIGINFIELD}  \n");
        sql.append(String.format("       %s AS DIGEST  \n", sqlHandler.toChar("ITEM.DIGEST")));
        sql.append(" FROM GL_CF_VOUCHER VCHR ");
        sql.append("  JOIN GL_CF_PTVCHRITEM ITEM ON VCHR.VCHRID = ITEM.VCHRID ");
        sql.append("  JOIN MD_CF_ITEM CFITEM ON CFITEM.OBJECTCODE = ITEM.CFITEM ");
        sql.append("  JOIN MD_CURRENCY HB ON ITEM.CURRENCYCODE = HB.CODE ");
        sql.append("  ${ASSJOINSQL}");
        sql.append(" WHERE 1 = 1");
        sql.append(" \tAND VCHR.VCHRNUM <> -1 ");
        sql.append(" \tAND VCHR.UNITCODE = '${UNIT_CODE}' ");
        sql.append(" \tAND VCHR.ACCTYEAR =  ${ACCT_YEAR}");
        sql.append("\tAND VCHR.ACCTPERIOD <= ${ENDPERIOD} \n");
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" \tAND VCHR.BOOKCODE= '${BOOK_CODE}' ");
        }
        sql.append("    ${GLMAINBODYCONDI}");
        sql.append(this.buildSubjectCondi("ITEM", "CFITEM", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("ITEM", "SUBJECTCODE", condi.getSubjectCode()));
        }
        sql.append("    ${ASSWHERESQL}");
        sql.append(" ORDER BY VCHR.ID, VCHR.VCHRID, VCHR.VCHRTYPE,VCHR.VCHRNUM ");
        StringBuilder externalDimFieldSql = new StringBuilder();
        StringBuilder externalAllDimFieldSql = new StringBuilder();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder assWhereSql = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalDimFieldSql.append(String.format("ITEM.%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            groupField.append(String.format(",ITEM.%1$s", assistMapping.getAccountAssistCode()));
            externalAllDimFieldSql.append(String.format(",ITEM.%1$s", assistMapping.getAccountAssistCode()));
            Dimension dimension = (Dimension)penetrateAssistDimMap.get(assistMapping.getAssistCode());
            ValueTypeEnum valueTypeEnum = ValueTypeEnum.fromCode((String)((CloudAccaAssistPojo)assistMapping.getAccountAssist()).getValueType());
            switch (valueTypeEnum) {
                case BASEDATA: {
                    Variable joinVariable = new Variable();
                    joinVariable.put("TABLENAME", assistMapping.getAccountAssistCode());
                    joinVariable.put("ASNAME", assistMapping.getAssistCode());
                    joinVariable.put("ORGCODE", condi.getUnitCode());
                    joinVariable.put("TABLECODE", dimension.getDimValue());
                    assJoinSql.append(VariableParseUtil.parse((String)"LEFT JOIN ( SELECT CODE ,NAME FROM ${TABLENAME} ${ASNAME} WHERE (${ASNAME}.UNITCODE IN( SELECT ORG.CODE  FROM MD_ORG_FIN ORG INNER JOIN ( SELECT MDORG.PARENTS  FROM MD_ORG_FIN MDORG WHERE  MDORG.CODE = '${ORGCODE}') ORGT ON ORGT.PARENTS LIKE CONCAT(ORG.PARENTS, '%')) OR ${ASNAME}.UNITCODE ='-' ) AND ${ASNAME}.code='${TABLECODE}' GROUP BY NAME,CODE ) ${ASNAME} ON    ITEM.${TABLENAME} = ${ASNAME}.CODE ", (Map)joinVariable.getVariableMap()));
                    assWhereSql.append(this.matchByRule("ITEM", assistMapping.getAccountAssistCode(), dimension.getDimValue(), dimension.getDimRule()));
                    externalDimFieldSql.append(String.format("%1$s.NAME AS %2$s,", assistMapping.getAssistCode(), assistMapping.getAssistCode() + "_NAME"));
                    groupField.append(String.format(",%1$s.NAME", assistMapping.getAssistCode()));
                    break;
                }
                case DATE: 
                case STRING: {
                    assWhereSql.append(this.matchByRule("ITEM", assistMapping.getAccountAssistCode(), dimension.getDimValue(), dimension.getDimRule()));
                    break;
                }
            }
        }
        Variable variable = new Variable();
        variable.put("ORIGINFIELD", externalDimFieldSql.toString());
        variable.put("FIELDSQL", externalAllDimFieldSql.toString());
        variable.put("ASSJOINSQL", assJoinSql.toString());
        variable.put("GROUPFIELD", groupField.toString());
        variable.put("ASSWHERESQL", assWhereSql.toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("STARTPERIOD", String.valueOf(condi.getStartPeriod()));
        variable.put("GLMAINBODYCONDI", CloudAccaFetchUtil.penetrateGlMainBodyOrgMappingType(condi, "ITEM"));
        variable.put("UNIT_CODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("ACCT_YEAR", String.valueOf(condi.getAcctYear()));
        variable.put("BOOK_CODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("CURRENCYCODE", condi.getCurrencyCode());
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(PenetrateUtil.replaceContext((String)lastSql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new XjllDetailLedgerResultSetExtractor(condi, assistMappingList));
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }
}

