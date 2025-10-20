/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.sap.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.sap.BdeSapPluginType;
import com.jiuqi.bde.plugin.sap.util.SapDataSchemeMappingProvider;
import com.jiuqi.bde.plugin.sap.util.SapFetchUtil;
import com.jiuqi.bde.plugin.sap.util.SapOrgMappingType;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

@Component
public class SapXjllContentProvider
extends AbstractXjllBalanceContentProvider {
    @Autowired
    private BdeSapPluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isNotEmpty((String)condi.getCashCode(), (String)"\u73b0\u6d41\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        SapDataSchemeMappingProvider schemeMappingProvider = new SapDataSchemeMappingProvider(fetchCondi);
        List<AssistMappingBO<BaseAcctAssist>> assistMappingList = schemeMappingProvider.getAssistMappingList();
        SapOrgMappingType orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? SapOrgMappingType.DEFAULT : SapOrgMappingType.fromCode(schemeMappingProvider.getOrgMappingType());
        OrgMappingDTO orgMapping = condi.getOrgMapping();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        boolean containSubject = !StringUtils.isEmpty((String)condi.getSubjectCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT   \n");
        sql.append("      ${UNITSELECTFIELD}   \n");
        sql.append("   T.RSTGR             AS MD_CFITEM,            \n");
        sql.append("   MD_CFITEM.NAME      AS MD_CFITEM_NAME       \n");
        if (containSubject) {
            sql.append("   ,T.HKONT             AS MD_ACCTSUBJECT,       \n");
            sql.append("   MD_ACCTSUBJECT.NAME AS MD_ACCTSUBJECT_NAME   \n");
        }
        sql.append("   ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("       ,SUM(  \n");
        sql.append("       CASE WHEN ((MASTER.MONAT >= ${PERIOD_END} AND MASTER.MONAT <= ${PERIOD_END}) ${ADJUSTPERIODSQL} ) THEN  \n");
        sql.append("       CASE WHEN RTRIM(SHKZG) = 'S' THEN COALESCE(DMBTR, 0) ELSE 0 END - CASE WHEN RTRIM(SHKZG) = 'H' THEN COALESCE(DMBTR, 0) ELSE 0 END  \n");
        sql.append("       ELSE 0 END) AS BQNUM,  \n");
        sql.append("       SUM(  \n");
        sql.append("       CASE WHEN ((MASTER.MONAT >= 1 AND MASTER.MONAT <= ${PERIOD_END}) ${ADJUSTPERIODSQL} ) THEN  \n");
        sql.append("       CASE WHEN RTRIM(SHKZG) = 'S' THEN COALESCE(DMBTR, 0) ELSE 0 END - CASE WHEN RTRIM(SHKZG) = 'H' THEN COALESCE(DMBTR, 0) ELSE 0 END  \n");
        sql.append("       ELSE 0 END) AS LJNUM,  \n");
        sql.append("       SUM(  \n");
        sql.append("       CASE WHEN ((MASTER.MONAT >= ${PERIOD_END} AND MASTER.MONAT <= ${PERIOD_END}) ${ADJUSTPERIODSQL} ) THEN  \n");
        sql.append("       CASE WHEN RTRIM(SHKZG) = 'S' THEN COALESCE(WRBTR, 0) ELSE 0 END - CASE WHEN RTRIM(SHKZG) = 'H' THEN COALESCE(WRBTR, 0) ELSE 0 END  \n");
        sql.append("       ELSE 0 END) AS WBQNUM,  \n");
        sql.append("       SUM(  \n");
        sql.append("       CASE WHEN ((MASTER.MONAT >= 1 AND MASTER.MONAT <= ${PERIOD_END}) ${ADJUSTPERIODSQL} ) THEN  \n");
        sql.append("       CASE WHEN RTRIM(SHKZG) = 'S' THEN COALESCE(WRBTR, 0) ELSE 0 END - CASE WHEN RTRIM(SHKZG) = 'H' THEN COALESCE(WRBTR, 0) ELSE 0 END  \n");
        sql.append("       ELSE 0 END) AS WLJNUM  \n");
        sql.append("  FROM BKPF MASTER \n");
        sql.append("  JOIN BSEG T \n");
        sql.append("    ON MASTER.MANDT = T.MANDT  \n");
        sql.append("   AND MASTER.BUKRS = T.BUKRS  \n");
        sql.append("   AND MASTER.BELNR = T.BELNR  \n");
        sql.append("   AND MASTER.GJAHR = T.GJAHR  \n");
        sql.append("   ${UNITJOINSQL} \n");
        sql.append("   LEFT JOIN (${EXTERNAL_CFITEM_SQL}) MD_CFITEM ON MD_CFITEM.ID = T.RSTGR \n");
        if (containSubject) {
            sql.append("   LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.CODE = T.HKONT \n");
        }
        sql.append("   ${EXTERNAL_JOIN_SQL}  \n");
        sql.append("  WHERE 1 = 1  \n");
        sql.append(SapFetchUtil.buildUnitSql("MASTER.BUKRS", orgMappingType, orgMapping));
        sql.append(SapFetchUtil.buildAssistSql("MASTER.BUKRS", "T.PRCTR", orgMappingType, orgMapping));
        sql.append("  AND MASTER.GJAHR = '${YEAR}'  \n");
        if (!StringUtils.isEmpty((String)condi.getCurrencyCode())) {
            sql.append(String.format("AND MASTER.WAERS = '%s'", condi.getCurrencyCode()));
        }
        sql.append(SapFetchUtil.buildUnitSql("T.BUKRS", orgMappingType, orgMapping));
        sql.append(SapFetchUtil.buildAssistSql("T.BUKRS", "T.PRCTR", orgMappingType, orgMapping));
        sql.append("  AND T.GJAHR = '${YEAR}'  \n");
        sql.append(this.buildSubjectCondi("T", "RSTGR", condi.getCashCode()));
        if (containSubject) {
            sql.append(this.buildSubjectCondi("T", "HKONT", condi.getSubjectCode()));
        }
        sql.append("   ${EXTERNAL_CONDI_SQL}  \n");
        sql.append("  GROUP BY ${UNITFIELD}T.RSTGR, MD_CFITEM.NAME  \n");
        if (containSubject) {
            sql.append("  , T.HKONT, MD_ACCTSUBJECT.NAME  \n");
        }
        sql.append("   ${EXTERNAL_GROUP_SQL}   \n");
        sql.append("  ORDER BY ${UNITFIELD}T.RSTGR  \n");
        if (containSubject) {
            sql.append("  , T.HKONT  \n");
        }
        sql.append("   ${EXTERNAL_ORDER_SQL}   \n");
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalJoinSql = new StringBuilder();
        StringBuilder externalCondiSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        StringBuilder externalOrderSql = new StringBuilder();
        String SELECT_TMPL = ",%1$s AS %2$s,%2$s.NAME AS %2$s_NAME";
        String GROUP_TMPL = ",%1$s,%2$s.NAME";
        String ORDER_TMPL = ",%1$s";
        String TMPL = " JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.ID = #bizTableField# ";
        for (AssistMappingBO<BaseAcctAssist> assistMapping : assistMappingList) {
            externalSelectSql.append(String.format(",%1$s AS %2$s,%2$s.NAME AS %2$s_NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            String sqlJoin = " JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.ID = #bizTableField# ".replace("#advancedSql#", assistMapping.getAssistSql());
            sqlJoin = sqlJoin.replace("#odsTableAlias#", assistMapping.getAssistCode());
            sqlJoin = sqlJoin.replace("#bizTableField#", this.getOdsFieldSql(sqlHandler, assistMapping.getAccountAssistCode()));
            externalJoinSql.append(sqlJoin);
            externalJoinSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
            externalCondiSql.append(String.format("AND %1$s LIKE '%2$s%%' ", assistMapping.getAccountAssistCode(), assistMapping.getExecuteDim().getDimValue()));
            externalGroupSql.append(String.format(",%1$s,%2$s.NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalOrderSql.append(String.format(",%1$s", assistMapping.getAccountAssistCode()));
        }
        StringBuilder unitSelectFieldSql = new StringBuilder();
        StringBuilder unitJoinSql = new StringBuilder();
        StringBuilder unitFieldSql = new StringBuilder();
        if (!CollectionUtils.isEmpty((Collection)condi.getOrgMapping().getOrgMappingItems())) {
            if (SapOrgMappingType.DEFAULT == orgMappingType) {
                unitSelectFieldSql.append("MASTER.BUKRS AS ACCTORGCODE, ORG.NAME AS ACCTORGNAME,\n");
                unitJoinSql.append(String.format("LEFT JOIN (%1$s) ORG ON ORG.CODE = MASTER.BUKRS \n", schemeMappingProvider.getOrgSql()));
                unitFieldSql.append("MASTER.BUKRS, ORG.NAME,\n");
            } else {
                unitSelectFieldSql.append("MASTER.BUKRS AS ACCTORGCODE, ORG.NAME AS ACCTORGNAME, ORG.ASSISTCODE, ORG.ASSISTNAME AS ASSISTNAME,\n");
                unitJoinSql.append(String.format("LEFT JOIN (%1$s) ORG ON ORG.CODE = MASTER.BUKRS AND ORG.ASSISTCODE = T.PRCTR \n", schemeMappingProvider.getOrgSql()));
                unitFieldSql.append("MASTER.BUKRS, ORG.NAME, ORG.ASSISTCODE, ORG.ASSISTNAME, \n");
            }
        }
        StringBuilder adjustPeriodSql = new StringBuilder();
        if (StringUtil.isNotEmpty((String)condi.getStartAdjustPeriod()) && StringUtil.isNotEmpty((String)condi.getEndAdjustPeriod())) {
            adjustPeriodSql.append(" OR (MASTER.MONAT >= ").append(condi.getStartAdjustPeriod());
            adjustPeriodSql.append(" AND MASTER.MONAT <= ").append(condi.getEndAdjustPeriod()).append(")");
        }
        Variable variable = new Variable();
        variable.put("YEAR", String.valueOf(condi.getAcctYear()));
        variable.put("UNITSELECTFIELD", unitSelectFieldSql.toString());
        variable.put("UNITJOINSQL", unitJoinSql.toString());
        variable.put("UNITFIELD", unitFieldSql.toString());
        variable.put("UNITCODE", condi.getUnitCode());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_JOIN_SQL", externalJoinSql.toString());
        variable.put("EXTERNAL_CONDI_SQL", externalCondiSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("EXTERNAL_ORDER_SQL", externalOrderSql.toString());
        variable.put("PERIOD_START", String.valueOf(condi.getStartPeriod()));
        variable.put("PERIOD_END", String.valueOf(condi.getEndPeriod()));
        variable.put("EXTERNAL_CFITEM_SQL", schemeMappingProvider.getCfItemSql());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("ADJUSTPERIODSQL", adjustPeriodSql.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(querySql, new Object[0], (ResultSetExtractor)new XjllBalanceResultSetExtractor(condi, assistMappingList));
        return queryParam;
    }

    private String getOdsFieldSql(IDbSqlHandler sqlHandler, String odsFieldName) {
        Assert.isNotEmpty((String)odsFieldName);
        String[] odsFieldNameArr = odsFieldName.split("/");
        return sqlHandler.concatBySeparator("|", odsFieldNameArr);
    }
}

