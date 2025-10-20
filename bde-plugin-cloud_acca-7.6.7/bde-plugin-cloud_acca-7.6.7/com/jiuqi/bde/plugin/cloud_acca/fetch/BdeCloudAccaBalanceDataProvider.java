/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.cloud_acca.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.cloud_acca.util.CloudAccaFetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

@Component
public class BdeCloudAccaBalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    protected FetchData queryData(BalanceCondition condi) {
        String lastSql = this.queryBalanceData(condi);
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }

    private String queryBalanceData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.SUBJECTCODE as SUBJECTCODE,  \n");
        sql.append("       T.CURRENCYCODE as CURRENCYCODE,  \n");
        sql.append("       ${ORIGINFIELD}  \n");
        sql.append("       T.ORIENT AS ORIENT,  \n");
        sql.append("       SUM(T.BF * T.ORIENT) as NC,  \n");
        sql.append("       SUM(T.ORGNBF*T.ORIENT) as WNC,  \n");
        sql.append("       SUM(T.DSUM_${ENDPERIOD} ${DSUM_ADJUST_PERIOD}) as JL,  \n");
        sql.append("       SUM(T.CSUM_${ENDPERIOD} ${CSUM_ADJUST_PERIOD}) as DL,  \n");
        sql.append("       SUM(T.ORGNDSUM_${ENDPERIOD} ${ORGN_DSUM_ADJUST_PERIOD}) as WJL,  \n");
        sql.append("       SUM(T.ORGNCSUM_${ENDPERIOD} ${ORGN_CSUM_ADJUST_PERIOD}) as WDL,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("        SUM(T.DSUM_${ENDPERIOD}) as JF,  \n");
            sql.append("        SUM(T.CSUM_${ENDPERIOD}) as DF,  \n");
            sql.append("        SUM(T.ORGNDSUM_${ENDPERIOD}) as WJF,  \n");
            sql.append("        SUM(T.ORGNCSUM_${ENDPERIOD}) as WDF,  \n");
            sql.append("        SUM(T.BF * T.ORIENT) AS C,  \n");
            sql.append("        SUM(T.ORGNBF * T.ORIENT) AS WC,  \n");
        } else {
            sql.append("\t    SUM(T.DSUM_${ENDPERIOD} - T.DSUM_${SUBTRACTENDMONTH} ${DSUM_ADJUST_PERIOD}) as JF,");
            sql.append("        SUM(T.CSUM_${ENDPERIOD} - T.CSUM_${SUBTRACTENDMONTH} ${CSUM_ADJUST_PERIOD}) as DF,");
            sql.append("        SUM(T.ORGNDSUM_${ENDPERIOD} - T.ORGNDSUM_${SUBTRACTENDMONTH} ${ORGN_DSUM_ADJUST_PERIOD}) as WJF,");
            sql.append(" \t    SUM(T.ORGNCSUM_${ENDPERIOD} - T.ORGNCSUM_${SUBTRACTENDMONTH} ${ORGN_CSUM_ADJUST_PERIOD}) AS WDF,");
            sql.append("\t    SUM(T.BF * T.ORIENT + T.DSUM_${SUBTRACTENDMONTH} - T.CSUM_${SUBTRACTENDMONTH}) AS C,");
            sql.append("\t    SUM(T.ORGNBF * T.ORIENT + T.ORGNDSUM_${SUBTRACTENDMONTH} - T.ORGNCSUM_${SUBTRACTENDMONTH}) AS WC,");
        }
        sql.append("\t    SUM(T.BF * T.ORIENT  + T.DSUM_${ENDPERIOD} - T.CSUM_${ENDPERIOD}  ${YE_ADJUST_PERIOD})AS YE,");
        sql.append("\t    SUM(T.ORGNBF * T.ORIENT + T.ORGNDSUM_${ENDPERIOD} - T.ORGNCSUM_${ENDPERIOD} ${WYE_ADJUST_PERIOD}) AS WYE");
        sql.append(" FROM ${TABLENAME} T ");
        TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"T.SUBJECTCODE");
        sql.append(" WHERE");
        sql.append("\t    T.UNITCODE = '${UNITCODE}' ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\tAND T.BOOKCODE ='${BOOKCODE}' ");
        }
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"T.SUBJECTCODE"));
        sql.append("        ${GLMAINBODYCONDI}");
        sql.append(" GROUP BY");
        sql.append("\tT.SUBJECTCODE,");
        sql.append("\t${FIELDSQL}");
        sql.append("    T.ORIENT,");
        sql.append("    T.CURRENCYCODE");
        Variable variable = new Variable();
        StringBuilder externalDimFieldSql = new StringBuilder();
        StringBuilder externalAllDimFieldSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalDimFieldSql.append(String.format("T .%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalAllDimFieldSql.append(String.format("T.%1$s,", assistMapping.getAccountAssistCode()));
        }
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("ORIGINFIELD", externalDimFieldSql.toString());
        variable.put("FIELDSQL", externalAllDimFieldSql.toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("TABLENAME", CloudAccaFetchUtil.getBalanceTableName(condi.isIncludeUncharged(), condi.getAcctYear()));
        variable.put("SUBTRACTENDMONTH", String.valueOf(condi.getStartPeriod() - 1));
        variable.put("GLMAINBODYCONDI", CloudAccaFetchUtil.buildGlMainBodyOrgMappingType(condi, "T"));
        variable.putAll(this.buildBalanceAdjustPeriodParam(condi));
        return VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
    }

    private Variable buildBalanceAdjustPeriodParam(BalanceCondition condi) {
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

