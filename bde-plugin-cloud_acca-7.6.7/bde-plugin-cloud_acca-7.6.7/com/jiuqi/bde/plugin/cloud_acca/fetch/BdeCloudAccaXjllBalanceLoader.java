/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.cloud_acca.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import com.jiuqi.bde.plugin.cloud_acca.util.CloudAccaFetchUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeCloudAccaXjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeCloudAccaPluginType bdeCloudAccaPluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.bdeCloudAccaPluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> argsList = new ArrayList<Object>();
        argsList.add(condi.getOrgMapping().getAcctOrgCode());
        argsList.add(condi.getAcctYear());
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append("\t1 as ORIENT,");
        sql.append("\tITEM.CFITEM as CFITEMCODE,");
        sql.append("\tITEM.SUBJECTCODE as SUBJECTCODE,");
        sql.append("\tHB.CODE AS CURRENCYCODE,");
        sql.append("       ${ORIGINFIELD}  \n");
        sql.append("\tSUM(CASE WHEN VCHR.ACCTPERIOD = ${ENDPERIOD} THEN ITEM.INFLUENCEAMOUNT ELSE 0 end ) AS BQNUM,");
        sql.append("\tSUM(CASE WHEN VCHR.ACCTPERIOD = ${ENDPERIOD} THEN ITEM.INFLUENCEAMOUNT ELSE 0 end ) AS WBQNUM,");
        sql.append("\tSUM(CASE WHEN VCHR.ACCTPERIOD >= 1 and VCHR.ACCTPERIOD <= ${ENDPERIOD} THEN ITEM.INFLUENCEAMOUNT ELSE 0 end ) AS LJNUM,");
        sql.append(" \tSUM(CASE WHEN VCHR.ACCTPERIOD >= 1 and VCHR.ACCTPERIOD <= ${ENDPERIOD} THEN ITEM.INFLUENCEAMOUNT ELSE 0 end ) AS WLJNUM");
        sql.append(" FROM ");
        sql.append("  GL_CF_VOUCHER VCHR ");
        sql.append("  JOIN GL_CF_PTVCHRITEM ITEM ON VCHR.VCHRID = ITEM.VCHRID ");
        sql.append("  JOIN MD_CF_ITEM CFITEM ON CFITEM.OBJECTCODE = ITEM.CFITEM ");
        sql.append("  JOIN MD_CURRENCY HB ON ITEM.CURRENCYCODE = HB.CODE ");
        TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"ITEM.SUBJECTCODE");
        sql.append(" WHERE 1 = 1");
        sql.append(" \tAND VCHR.VCHRNUM <> -1 ");
        sql.append(" \tAND VCHR.UNITCODE = ? ");
        sql.append(" \tAND VCHR.ACCTYEAR =  ?");
        sql.append("\tAND VCHR.ACCTPERIOD <= ${ENDPERIOD} \n");
        if (StringUtils.isNotEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" \tAND VCHR.BOOKCODE= ? ");
            argsList.add(condi.getOrgMapping().getAcctBookCode());
        }
        sql.append("    ${GLMAINBODYCONDI}");
        sql.append(" GROUP BY");
        sql.append("\tITEM.CFITEM, \n");
        sql.append("    ITEM.SUBJECTCODE,");
        sql.append(" ${FIELDSQL} HB.CODE \n");
        StringBuilder externalDimFieldSql = new StringBuilder();
        StringBuilder externalAllDimFieldSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalDimFieldSql.append(String.format("ITEM.%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalAllDimFieldSql.append(String.format("ITEM.%1$s,", assistMapping.getAccountAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("ORIGINFIELD", externalDimFieldSql.toString());
        variable.put("FIELDSQL", externalAllDimFieldSql.toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("GLMAINBODYCONDI", CloudAccaFetchUtil.buildGlMainBodyOrgMappingType(condi, "ITEM"));
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        Object[] args = argsList.toArray();
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)new Object[]{args, condi}, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, args, (ResultSetExtractor)new FetchDataExtractor());
    }
}

