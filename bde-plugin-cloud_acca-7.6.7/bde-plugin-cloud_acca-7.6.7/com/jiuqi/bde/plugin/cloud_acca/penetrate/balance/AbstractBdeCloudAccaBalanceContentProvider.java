/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.impl.assistdim.enums.ValueTypeEnum
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import com.jiuqi.bde.plugin.cloud_acca.assist.CloudAccaAssistPojo;
import com.jiuqi.bde.plugin.cloud_acca.util.CloudAccaFetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.impl.assistdim.enums.ValueTypeEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import tk.mybatis.mapper.util.StringUtil;

public abstract class AbstractBdeCloudAccaBalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeCloudAccaPluginType bdeCloudAccaPluginType;
    private static final String JOIN_SQL = "LEFT JOIN ( SELECT CODE ,NAME FROM ${TABLENAME} ${ASNAME} WHERE (${ASNAME}.UNITCODE IN(SELECT ORG.CODE FROM MD_ORG_FIN ORG INNER JOIN (SELECT MDORG.PARENTS FROM MD_ORG_FIN MDORG WHERE MDORG.CODE = '${ORGCODE}' ) ORGT ON  ORGT.PARENTS LIKE CONCAT(ORG.PARENTS, '%')) OR ${ASNAME}.UNITCODE='-' ) GROUP BY CODE,NAME ) ${ASNAME} ON B.${TABLENAME} = ${ASNAME}.CODE ";

    public String getPluginType() {
        return this.bdeCloudAccaPluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        HashMap<String, Dimension> penetrateAssistDimMap = condi.getAssTypeList() == null ? new HashMap<String, Dimension>() : condi.getAssTypeList().stream().collect(Collectors.toMap(Dimension::getDimCode, item -> item, (k1, k2) -> k2));
        String tableName = CloudAccaFetchUtil.getBalanceTableName(condi.getIncludeUncharged(), condi.getAcctYear());
        String lastSql = this.getBalanceQuerySql(condi, tableName, penetrateAssistDimMap, assistMappingList);
        QueryParam queryParam = new QueryParam(PenetrateUtil.replaceContext((String)lastSql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private String getBalanceQuerySql(PenetrateBaseDTO condi, String tableName, Map<String, Dimension> penetrateAssistDimMap, List<AssistMappingBO<CloudAccaAssistPojo>> assistMappingList) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT B.SUBJECTCODE,  ");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  ");
        sql.append("       SUM(B.BF * B.ORIENT) AS NC,  ");
        sql.append("       SUM(B.QTYBF * B.ORIENT) AS ORGNNC,  ");
        sql.append("       B.ORIENT AS ORIENT,  ");
        sql.append("       B.CURRENCYCODE as CURRENCYCODE,  ");
        if (condi.getStartPeriod() == 1) {
            sql.append("        SUM(B.DSUM_${ENDPERIOD}) AS DEBIT,  ");
            sql.append("        SUM(B.CSUM_${ENDPERIOD}) AS CREDIT,  ");
            sql.append("        SUM(B.ORGNDSUM_${ENDPERIOD}) AS ORGND,  ");
            sql.append("        SUM(B.ORGNCSUM_${ENDPERIOD}) AS ORGNC,  ");
            sql.append("        SUM(B.BF * B.ORIENT) AS QC,  ");
            sql.append("        SUM(B.QTYBF * B.ORIENT) AS ORGNQC,  ");
        } else {
            sql.append("        SUM(B.DSUM_${ENDPERIOD} - B.DSUM_${SUBTRACTENDMONTH} ${DSUM_ADJUST_PERIOD}) AS DEBIT,");
            sql.append("        SUM(B.CSUM_${ENDPERIOD} - B.CSUM_${SUBTRACTENDMONTH} ${CSUM_ADJUST_PERIOD}) AS CREDIT,");
            sql.append("        SUM(B.ORGNDSUM_${ENDPERIOD} - B.ORGNDSUM_${SUBTRACTENDMONTH} ${ORGN_DSUM_ADJUST_PERIOD}) AS ORGND,");
            sql.append("        SUM(B.ORGNCSUM_${ENDPERIOD} - B.ORGNCSUM_${SUBTRACTENDMONTH} ${ORGN_CSUM_ADJUST_PERIOD}) AS ORGNC,");
            sql.append("        SUM(B.BF * B.ORIENT + B.DSUM_${SUBTRACTENDMONTH} - B.CSUM_${SUBTRACTENDMONTH}) AS QC,");
            sql.append("        SUM(B.QTYBF * B.ORIENT + B.QTYDSUM_${SUBTRACTENDMONTH} - B.QTYCSUM_${SUBTRACTENDMONTH}) AS ORGNQC,");
        }
        sql.append("      ${ORIGINFIELD} ");
        sql.append("      SUM(B.DSUM_${ENDPERIOD} ${DSUM_ADJUST_PERIOD}) AS DSUM,  ");
        sql.append("      SUM(B.CSUM_${ENDPERIOD} ${CSUM_ADJUST_PERIOD}) AS CSUM,  ");
        sql.append("      SUM(B.ORGNDSUM_${ENDPERIOD} ${ORGN_DSUM_ADJUST_PERIOD}) AS ORGNDSUM,  ");
        sql.append("      SUM(B.ORGNCSUM_${ENDPERIOD} ${ORGN_CSUM_ADJUST_PERIOD}) AS ORGNCSUM,  ");
        sql.append("      SUM(B.BF * B.ORIENT + B.DSUM_${ENDPERIOD} - B.CSUM_${ENDPERIOD} ${YE_ADJUST_PERIOD}) AS YE,  ");
        sql.append("      SUM(B.ORGNBF * B.ORIENT + B.ORGNDSUM_${ENDPERIOD} - B.ORGNCSUM_${ENDPERIOD} ${WYE_ADJUST_PERIOD}) AS ORGNYE  ");
        sql.append(" FROM ${TABLENAME} B  ");
        sql.append(" LEFT JOIN (SELECT SUBJECT.CODE,MAX(SUBJECT.NAME) AS NAME  ");
        sql.append("              FROM MD_ACCTSUBJECT SUBJECT  ");
        sql.append("             WHERE (SUBJECT.UNITCODE IN  ");
        sql.append("                   (SELECT ORG.CODE  ");
        sql.append("                      FROM MD_ORG_FIN ORG  ");
        sql.append("                      INNER JOIN (SELECT MDORG.parents  ");
        sql.append("                                  FROM MD_ORG_FIN MDORG  ");
        sql.append("                                 WHERE MDORG.code = '${UNITCODE}'  ");
        sql.append("                        ) ORGT  on ORGT.PARENTS LIKE concat(ORG.PARENTS, '%')) OR SUBJECT.UNITCODE='-') ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\tAND SUBJECT.BOOKCODE ='${BOOKCODE}' ");
        }
        sql.append("            GROUP BY SUBJECT.CODE");
        sql.append("   ) SUBJECT ON B.SUBJECTCODE = SUBJECT.CODE ${ASSJOINSQL}  ");
        sql.append("WHERE 1 = 1  ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\tAND B.BOOKCODE ='${BOOKCODE}' ");
        }
        sql.append("    AND B.UNITCODE='${UNITCODE}'");
        sql.append("    ${ASSWHERESQL}");
        sql.append(this.buildSubjectCondi("B", "SUBJECTCODE", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("B", "SUBJECTCODE", condi.getExcludeSubjectCode()));
        sql.append("  ${GLMAINBODYCONDI}");
        sql.append(" GROUP BY");
        sql.append("    B.ORIENT,");
        sql.append("    SUBJECT.NAME,");
        sql.append("    B.CURRENCYCODE,");
        sql.append("    ${FIELDSQL}");
        sql.append("    B.SUBJECTCODE");
        sql.append(" ORDER BY B.SUBJECTCODE");
        Variable variable = new Variable();
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("SUBJECTCODE", condi.getSubjectCode());
        StringBuilder externalDimFieldSql = new StringBuilder();
        StringBuilder externalAllDimFieldSql = new StringBuilder();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder assWhereSql = new StringBuilder();
        for (AssistMappingBO<CloudAccaAssistPojo> assistMapping : assistMappingList) {
            externalDimFieldSql.append(String.format("B.%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalAllDimFieldSql.append(String.format("B.%1$s,", assistMapping.getAccountAssistCode()));
            externalAllDimFieldSql.append(String.format("%1$s.NAME,", assistMapping.getAssistCode()));
            Dimension dimension = penetrateAssistDimMap.get(assistMapping.getAssistCode());
            ValueTypeEnum valueTypeEnum = ValueTypeEnum.fromCode((String)((CloudAccaAssistPojo)assistMapping.getAccountAssist()).getValueType());
            switch (valueTypeEnum) {
                case BASEDATA: {
                    Variable joinVariable = new Variable();
                    joinVariable.put("TABLENAME", assistMapping.getAccountAssistCode());
                    joinVariable.put("ASNAME", assistMapping.getAssistCode());
                    joinVariable.put("ORGCODE", condi.getOrgMapping().getAcctOrgCode());
                    joinVariable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
                    assJoinSql.append(VariableParseUtil.parse((String)JOIN_SQL, (Map)joinVariable.getVariableMap()));
                    assWhereSql.append(this.matchByRule("B", assistMapping.getAccountAssistCode(), dimension.getDimValue(), dimension.getDimRule()));
                    externalDimFieldSql.append(String.format("%1$s.NAME AS %2$s,", assistMapping.getAssistCode(), assistMapping.getAssistCode() + "_NAME"));
                    break;
                }
                case DATE: 
                case STRING: {
                    assWhereSql.append(this.matchByRule("B", assistMapping.getAccountAssistCode(), dimension.getDimValue(), dimension.getDimRule()));
                    break;
                }
            }
        }
        variable.put("ORIGINFIELD", externalDimFieldSql.toString());
        variable.put("FIELDSQL", externalAllDimFieldSql.toString());
        variable.put("TABLENAME", tableName);
        variable.put("SUBTRACTENDMONTH", String.valueOf(condi.getStartPeriod() - 1));
        variable.put("STARTPERIOD", String.valueOf(condi.getStartPeriod()));
        variable.put("ASSJOINSQL", assJoinSql.toString());
        variable.put("ASSWHERESQL", assWhereSql.toString());
        variable.put("GLMAINBODYCONDI", CloudAccaFetchUtil.penetrateGlMainBodyOrgMappingType(condi, "B"));
        variable.putAll(this.buildBalanceAdjustPeriodParam(condi));
        return VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
    }

    private Variable buildBalanceAdjustPeriodParam(PenetrateBaseDTO condi) {
        String startAdjustPeriod = condi.getStartAdjustPeriod();
        String endAdjustPeriod = condi.getEndAdjustPeriod();
        Variable variable = new Variable();
        String dsumAdjustPeriod = "";
        String csum_adjust_period = "";
        String orgnDsumAdjustPeriod = "";
        String orgnCsumAdjustPeriod = "";
        String yeAdjustPeriod = "";
        String wyeAdjustPeriod = "";
        if (StringUtil.isEmpty((String)startAdjustPeriod) && StringUtil.isEmpty((String)endAdjustPeriod)) {
            variable.put("DSUM_ADJUST_PERIOD", dsumAdjustPeriod);
            variable.put("CSUM_ADJUST_PERIOD", csum_adjust_period);
            variable.put("ORGN_DSUM_ADJUST_PERIOD", orgnDsumAdjustPeriod);
            variable.put("ORGN_CSUM_ADJUST_PERIOD", orgnCsumAdjustPeriod);
            variable.put("YE_ADJUST_PERIOD", yeAdjustPeriod);
            variable.put("WYE_ADJUST_PERIOD", wyeAdjustPeriod);
            return variable;
        }
        int start = Integer.parseInt(startAdjustPeriod);
        int end = Integer.parseInt(endAdjustPeriod);
        if (start <= 13 && end >= 13) {
            dsumAdjustPeriod = " + DSUM_12A ";
            csum_adjust_period = " + CSUM_12A ";
            orgnDsumAdjustPeriod = " + ORGNDSUM_12A ";
            orgnCsumAdjustPeriod = " + ORGNCSUM_12A ";
            yeAdjustPeriod = " + (DSUM_12A - CSUM_12A) ";
            wyeAdjustPeriod = " + (ORGNDSUM_12A - ORGNCSUM_12A) ";
        }
        variable.put("DSUM_ADJUST_PERIOD", dsumAdjustPeriod);
        variable.put("CSUM_ADJUST_PERIOD", csum_adjust_period);
        variable.put("ORGN_DSUM_ADJUST_PERIOD", orgnDsumAdjustPeriod);
        variable.put("ORGN_CSUM_ADJUST_PERIOD", orgnCsumAdjustPeriod);
        variable.put("YE_ADJUST_PERIOD", yeAdjustPeriod);
        variable.put("WYE_ADJUST_PERIOD", wyeAdjustPeriod);
        return variable;
    }
}

