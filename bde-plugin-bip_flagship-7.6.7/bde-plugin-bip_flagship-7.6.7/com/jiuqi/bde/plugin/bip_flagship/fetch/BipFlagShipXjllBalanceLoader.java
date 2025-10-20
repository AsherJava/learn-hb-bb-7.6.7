/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.bip_flagship.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.bip_flagship.BdeBipFlagShipPluginType;
import com.jiuqi.bde.plugin.bip_flagship.util.BipFlagShipFetchUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BipFlagShipXjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeBipFlagShipPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        StringBuilder adjustPeriod = new StringBuilder();
        StringBuilder endAdjustPeriod = new StringBuilder();
        if (!StringUtils.isEmpty((String)condi.getStartAdjustPeriod()) && !StringUtils.isEmpty((String)condi.getEndAdjustPeriod())) {
            adjustPeriod.append(" OR (CF.PERIODUNION >= '${STARTADJUSTPERIOD}' AND CF.PERIODUNION <= '${ENDADJUSTPERIOD}')");
            endAdjustPeriod.append(" OR CF.PERIODUNION <= '${ENDADJUSTPERIOD}'");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ITEM.CODE AS CFITEMCODE,  \n");
        sql.append("       CURRENCY.CODE AS CURRENCYCODE,  \n");
        sql.append("       1 AS ORIENT, \n");
        sql.append("       SUM(CASE WHEN CF.PERIODUNION>='${STARTPERIODUNION}' AND CF.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN CF.AMOUNTORG ELSE 0 END) AS BQNUM, \n");
        sql.append("       SUM(CASE WHEN CF.PERIODUNION>'${NCPERIODUNION}' AND CF.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN CF.AMOUNTORG ELSE 0 END) AS LJNUM, \n");
        sql.append("       SUM(CASE WHEN CF.PERIODUNION>='${STARTPERIODUNION}' AND CF.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN CF.AMOUNTORIGINAL ELSE 0 END) AS WBQNUM, \n");
        sql.append("       SUM(CASE WHEN CF.PERIODUNION>'${NCPERIODUNION}' AND CF.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN CF.AMOUNTORIGINAL ELSE 0 END) AS WLJNUM \n");
        sql.append("  FROM CF_CASHFLOW CF \n");
        sql.append("  JOIN EPUB_CASHFLOWITEM ITEM ON ITEM.ID = CF.CASHFLOWITEM  \n");
        sql.append("  JOIN (SELECT * FROM EPUB_ACCOUNTBOOK WHERE 1 = 1\n");
        sql.append("  ").append(BipFlagShipFetchUtil.buildUnitSql("EPUB_ACCOUNTBOOK.CODE", condi.getOrgMapping())).append(") BOOK ON\n");
        sql.append("       BOOK.ID = CF.ACCBOOK\n");
        sql.append("  JOIN BD_CURRENCY_TENANT CURRENCY ON\n");
        sql.append("       CURRENCY.ID = CF.CURRENCY\n");
        sql.append(" WHERE 1=1 \n");
        if (condi.getIncludeUncharged().booleanValue()) {
            sql.append("   AND CF.VOUCHERSTATUS IN('04','01','03') \n");
        } else {
            sql.append("   AND CF.VOUCHERSTATUS = '04' \n");
        }
        sql.append("   AND CF.VOUCHERSTATUS<>'05'  \n");
        sql.append(" GROUP BY ITEM.CODE, CURRENCY.CODE \n");
        Variable variable = new Variable();
        variable.put("STARTPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getStartPeriod()));
        variable.put("ENDPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getEndPeriod()));
        variable.put("NCPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), 0));
        variable.put("ORADJUSTPERIOD", adjustPeriod.toString());
        variable.put("ORENDADJUSTPERIOD", endAdjustPeriod.toString());
        variable.put("STARTADJUSTPERIOD", BipFlagShipFetchUtil.getAdjustPeriodUnion(condi.getAcctYear(), condi.getStartAdjustPeriod()));
        variable.put("ENDADJUSTPERIOD", BipFlagShipFetchUtil.getAdjustPeriodUnion(condi.getAcctYear(), condi.getEndAdjustPeriod()));
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)condi, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, null, (ResultSetExtractor)new FetchDataExtractor());
    }
}

