/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.bip_flagship.pentrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.bip_flagship.BdeBipFlagShipPluginType;
import com.jiuqi.bde.plugin.bip_flagship.util.BipFlagShipFetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BipFlagXjllBalanceContentProvider
extends AbstractXjllBalanceContentProvider {
    @Autowired
    private BdeBipFlagShipPluginType bdeBipFlagShipPluginType;

    public String getPluginType() {
        return this.bdeBipFlagShipPluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList args = new ArrayList();
        StringBuilder adjustPeriod = new StringBuilder();
        StringBuilder endAdjustPeriod = new StringBuilder();
        if (!StringUtils.isEmpty((String)condi.getStartAdjustPeriod()) && !StringUtils.isEmpty((String)condi.getEndAdjustPeriod())) {
            adjustPeriod.append(" OR (CF.PERIODUNION >= '${STARTADJUSTPERIOD}' AND CF.PERIODUNION <= '${ENDADJUSTPERIOD}')");
            endAdjustPeriod.append(" OR CF.PERIODUNION <= '${ENDADJUSTPERIOD}'");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ITEM.CODE AS MD_CFITEM,  \n");
        sql.append("       ITEM.NAME AS MD_CFITEM_NAME,  \n");
        sql.append("       SUM(CASE WHEN CF.PERIODUNION>='${STARTPERIODUNION}' AND CF.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN CF.AMOUNTORG ELSE 0 END) AS BQNUM, \n");
        sql.append("       SUM(CASE WHEN CF.PERIODUNION>'${NCPERIODUNION}' AND CF.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN CF.AMOUNTORG ELSE 0 END) AS LJNUM, \n");
        sql.append("       SUM(CASE WHEN CF.PERIODUNION>='${STARTPERIODUNION}' AND CF.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN CF.AMOUNTORIGINAL ELSE 0 END) AS WBQNUM, \n");
        sql.append("       SUM(CASE WHEN CF.PERIODUNION>'${NCPERIODUNION}' AND CF.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN CF.AMOUNTORIGINAL ELSE 0 END) AS WLJNUM \n");
        sql.append("  FROM CF_CASHFLOW CF \n");
        sql.append("  JOIN EPUB_CASHFLOWITEM ITEM ON ITEM.ID = CF.CASHFLOWITEM  \n");
        sql.append("  JOIN (SELECT * FROM EPUB_ACCOUNTBOOK WHERE 1 = 1\n");
        sql.append("  ").append(BipFlagShipFetchUtil.buildUnitSql("EPUB_ACCOUNTBOOK.CODE", condi.getOrgMapping())).append(") BOOK ON\n");
        sql.append("       BOOK.ID = CF.ACCBOOK\n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append(this.buildCfItemCondi("ITEM", "CODE", condi.getCashCode()));
        if (condi.getIncludeUncharged().booleanValue()) {
            sql.append("   AND CF.VOUCHERSTATUS IN('04','01','03') \n");
        } else {
            sql.append("   AND CF.VOUCHERSTATUS = '04' \n");
        }
        sql.append("   AND CF.VOUCHERSTATUS<>'05'  \n");
        sql.append(" GROUP BY ITEM.CODE, ITEM.NAME \n");
        Variable variable = new Variable();
        variable.put("STARTPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getStartPeriod()));
        variable.put("ENDPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getEndPeriod()));
        variable.put("NCPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), 0));
        variable.put("ORADJUSTPERIOD", adjustPeriod.toString());
        variable.put("ORENDADJUSTPERIOD", endAdjustPeriod.toString());
        variable.put("STARTADJUSTPERIOD", BipFlagShipFetchUtil.getAdjustPeriodUnion(condi.getAcctYear(), condi.getStartAdjustPeriod()));
        variable.put("ENDADJUSTPERIOD", BipFlagShipFetchUtil.getAdjustPeriodUnion(condi.getAcctYear(), condi.getEndAdjustPeriod()));
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new XjllBalanceResultSetExtractor(condi, assistMappingList));
    }
}

