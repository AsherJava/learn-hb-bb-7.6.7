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
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.u8.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bde.plugin.u8.BdeU8PluginType;
import com.jiuqi.bde.plugin.u8.assist.U8AssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class U8XjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeU8PluginType pluginType;
    @Autowired
    private BizDataRefDefineService bizDataDefineService;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        DataMappingDefineDTO assistDefine = this.bizDataDefineService.findByCode(condi.getOrgMapping().getDataSchemeCode(), "U8Assist");
        Assert.isNotNull((Object)assistDefine, (String)"\u4f59\u989d\u8868\u6ca1\u6709\u83b7\u53d6\u5bf9\u5e94\u7684\u4e1a\u52a1\u6570\u636e\u5b9a\u4e49", (Object[])new Object[0]);
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("          SUBJECT.CCODE AS SUBJECTCODE,\n");
        query.append("          CASH.CCASHITEM AS CFITEMCODE,\n");
        query.append("          CURRENCY.CEXCH_CODE AS CURRENCYCODE,\n");
        query.append("          CASE WHEN SUBJECT.BPROPERTY=1 THEN 1 ELSE -1 END AS ORIENT,  \n");
        query.append("          SUM(CASE WHEN VOUCHER.IPERIOD = ${ENDPERIOD} THEN CASH.mc-CASH.md ELSE 0 END) AS BQNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.IPERIOD <= ${ENDPERIOD} THEN CASH.mc-CASH.md ELSE 0 END) AS LJNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.IPERIOD = ${ENDPERIOD} THEN CASH.mc_F-CASH.md_F ELSE 0 END) AS WBQNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.IPERIOD <= ${ENDPERIOD} THEN CASH.mc_F-CASH.md_F ELSE 0 END) AS WLJNUM\n");
        query.append("          ${VOUCHERSELECT} \n");
        query.append("  FROM\n");
        query.append("          GL_ACCVOUCH VOUCHER\n");
        query.append("  INNER JOIN GL_CASHTABLE CASH ON\n");
        query.append("          VOUCHER.IPERIOD = CASH.IPERIOD\n");
        query.append("          AND VOUCHER.ISIGNSEQ = CASH.ISIGNSEQ\n");
        query.append("          AND VOUCHER.INO_ID = CASH.INO_ID\n");
        query.append("          AND VOUCHER.INID = CASH.INID\n");
        query.append("          AND VOUCHER.IYEAR =CASH.IYEAR\n");
        query.append("  INNER JOIN CODE SUBJECT ON\n");
        query.append("          VOUCHER.CCODE = SUBJECT.CCODE\n");
        query.append("  INNER JOIN FOREIGNCURRENCY CURRENCY ON\n");
        query.append("          CASE WHEN VOUCHER.CEXCH_NAME IS NULL THEN '\u4eba\u6c11\u5e01' ELSE VOUCHER.CEXCH_NAME END = CURRENCY.CEXCH_NAME\n");
        query.append("  WHERE   1=1 \n");
        query.append("          AND CASH.IYEAR=${YEAR} \n");
        query.append("          AND SUBJECT.IYEAR =${YEAR}    \n");
        query.append("          ${INCLUDECONDITION}  \n");
        query.append("  GROUP BY SUBJECT.CCODE,CASH.CCASHITEM,CURRENCY.CEXCH_CODE,CASE WHEN SUBJECT.BPROPERTY=1 THEN 1 ELSE -1 END \n");
        query.append("          ${VOUCHERGROUP} \n");
        StringBuilder voucherSelect = new StringBuilder();
        StringBuilder voucherGroup = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            voucherSelect.append(String.format(",VOUCHER.%1$s AS %2$s", ((U8AssistPojo)assistMapping.getAccountAssist()).getAssField(), assistMapping.getAssistCode()));
            voucherGroup.append(String.format(",VOUCHER.%1$s", ((U8AssistPojo)assistMapping.getAccountAssist()).getAssField()));
        }
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getEndPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("INCLUDECONDITION", condi.getIncludeUncharged() != false ? "" : "AND VOUCHER.IBOOK =1 \n");
        variable.put("VOUCHERSELECT", voucherSelect.toString());
        variable.put("VOUCHERGROUP", voucherGroup.toString());
        variable.put("YEAR", condi.getAcctYear().toString());
        String querySql = VariableParseUtil.parse((String)query.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)new Object[]{condi}, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, null, (ResultSetExtractor)new FetchDataExtractor());
    }
}

