/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.sap.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bde.plugin.sap.BdeSapPluginType;
import com.jiuqi.bde.plugin.sap.util.SapDataSchemeMappingProvider;
import com.jiuqi.bde.plugin.sap.util.SapFetchUtil;
import com.jiuqi.bde.plugin.sap.util.SapOrgMappingType;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

@Component
public class SapXjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeSapPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    protected IFieldMappingSqlBuilderGather gather;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        SapDataSchemeMappingProvider schemeMappingProvider = new SapDataSchemeMappingProvider(condi);
        SapOrgMappingType orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? SapOrgMappingType.DEFAULT : SapOrgMappingType.fromCode(schemeMappingProvider.getOrgMappingType());
        OrgMappingDTO orgMapping = condi.getOrgMapping();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TRIM(T.RSTGR) AS CFITEMCODE,  \n");
        sql.append("       TRIM(T.HKONT) AS SUBJECTCODE,  \n");
        sql.append("        1              AS ORIENT,            \n");
        sql.append("       TRIM(MASTER.WAERS) AS CURRENCYCODE,  \n");
        sql.append("   ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("       SUM(  \n");
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
        sql.append("  WHERE 1 = 1  \n");
        sql.append(SapFetchUtil.buildUnitSql("MASTER.BUKRS", orgMappingType, orgMapping));
        sql.append(SapFetchUtil.buildAssistSql("MASTER.BUKRS", "T.PRCTR", orgMappingType, orgMapping));
        sql.append("  AND MASTER.GJAHR = '${YEAR}'  \n");
        sql.append(SapFetchUtil.buildUnitSql("T.BUKRS", orgMappingType, orgMapping));
        sql.append(SapFetchUtil.buildAssistSql("T.BUKRS", "T.PRCTR", orgMappingType, orgMapping));
        sql.append("  AND T.GJAHR = '${YEAR}'  \n");
        sql.append("  AND T.RSTGR IS NOT NULL  \n");
        sql.append("  GROUP BY T.RSTGR, T.HKONT,  \n");
        sql.append("   ${EXTERNAL_GROUP_SQL} MASTER.WAERS  \n");
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        for (AssistMappingBO<BaseAcctAssist> assistMapping : schemeMappingProvider.getAssistMappingList()) {
            externalSelectSql.append(String.format("%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalGroupSql.append(String.format("%1$s,", assistMapping.getAccountAssistCode()));
        }
        StringBuilder adjustPeriodSql = new StringBuilder();
        if (StringUtil.isNotEmpty((String)condi.getStartAdjustPeriod()) && StringUtil.isNotEmpty((String)condi.getEndAdjustPeriod())) {
            adjustPeriodSql.append(" OR (MASTER.MONAT >= ").append(condi.getStartAdjustPeriod());
            adjustPeriodSql.append(" AND MASTER.MONAT <= ").append(condi.getEndAdjustPeriod()).append(")");
        }
        Variable variable = new Variable();
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("PERIOD_START", String.valueOf(condi.getStartPeriod()));
        variable.put("PERIOD_END", String.valueOf(condi.getEndPeriod()));
        variable.put("ADJUSTPERIODSQL", adjustPeriodSql.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        Object[] args = new Object[]{};
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)new Object[]{args, condi}, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, args, (ResultSetExtractor)new FetchDataExtractor());
    }
}

