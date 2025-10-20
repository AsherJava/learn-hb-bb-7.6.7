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
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.bip_flagship.pentrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.bip_flagship.BdeBipFlagShipPluginType;
import com.jiuqi.bde.plugin.bip_flagship.util.BipFlagShipFetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BipFlagXjllDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeBipFlagShipPluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isNotEmpty((String)condi.getCashCode(), (String)"\u73b0\u6d41\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder adjustPeriod = new StringBuilder();
        StringBuilder endAdjustPeriod = new StringBuilder();
        if (!StringUtils.isEmpty((String)condi.getStartAdjustPeriod()) && !StringUtils.isEmpty((String)condi.getEndAdjustPeriod())) {
            adjustPeriod.append(" OR (VOUCHER.PERIODUNION >= '${STARTADJUSTPERIOD}' AND VOUCHER.PERIODUNION <= '${ENDADJUSTPERIOD}')");
            endAdjustPeriod.append(" OR VOUCHER.PERIODUNION <= '${ENDADJUSTPERIOD}'");
        }
        StringBuilder sql = new StringBuilder();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("          DETAIL.ID AS ID,\n");
        sql.append("          VOUCHER.ID AS VCHRID,\n");
        sql.append(String.format("          %1$s AS ACCTYEAR,\n", sqlHandler.SubStr("DETAIL.PERIOD", "1", "4")));
        sql.append(String.format("          %1$s AS ACCTPERIOD,\n", sqlHandler.SubStr("DETAIL.PERIOD", "6", "2")));
        sql.append(String.format("          %1$s AS ACCTDAY,\n", sqlHandler.SubStr("DETAIL.BUSIDATE", "9", "2")));
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"VOUCHERTYPE.NAME", String.format("%s", sqlHandler.toChar("VOUCHER.BILLCODE"))})).append(" AS VCHRTYPE, \n");
        sql.append("          DETAIL.ID AS ORDERID,  \n");
        sql.append("          1 AS ORIENT,  \n");
        sql.append("          ITEM.CODE AS MD_CFITEM,  \n");
        sql.append("          ITEM.NAME AS MD_CFITEM_NAME,  \n");
        sql.append("          DETAIL.DESCRIPTION AS DIGEST,\n");
        sql.append("          DETAIL.DEBIT_ORG AS DEBIT,  \n");
        sql.append("          DETAIL.CREDIT_ORG AS CREDIT,  \n");
        sql.append("          DETAIL.DEBIT_ORIGINAL AS ORGND,  \n");
        sql.append("          DETAIL.CREDIT_ORIGINAL AS ORGNC,  \n");
        sql.append("          0 AS YE,  \n");
        sql.append("          0 AS ORGNYE  \n");
        sql.append(" FROM\n");
        sql.append("          (SELECT FI_VOUCHER.PERIODUNION,FI_VOUCHER.ID,FI_VOUCHER.VOUCHERTYPE,FI_VOUCHER.BILLCODE  \n");
        sql.append("            FROM  FI_VOUCHER  \n");
        sql.append("           INNER JOIN EPUB_ACCOUNTBOOK BOOK \n");
        sql.append("                 ON BOOK.ID = FI_VOUCHER.ACCBOOK");
        sql.append(BipFlagShipFetchUtil.buildUnitSql("BOOK.CODE", condi.getOrgMapping())).append("\n");
        sql.append("           WHERE 1=1  \n");
        if (condi.getIncludeUncharged().booleanValue()) {
            sql.append("                  AND FI_VOUCHER.VOUCHERSTATUS IN('04','01','03') \n");
        } else {
            sql.append("                  AND FI_VOUCHER.VOUCHERSTATUS = '04' \n");
        }
        sql.append("                 AND FI_VOUCHER.VOUCHERSTATUS<>'05') VOUCHER  \n");
        sql.append(" JOIN EPUB_VOUCHERTYPE VOUCHERTYPE ON VOUCHERTYPE.ID = VOUCHER.VOUCHERTYPE");
        sql.append(" JOIN FI_VOUCHER_B DETAIL ON\n");
        sql.append("          DETAIL.VOUCHERID = VOUCHER.ID\n");
        sql.append(" JOIN CF_CASHFLOW CF ON CF.VOUCHER = VOUCHER.ID");
        sql.append(" JOIN EPUB_CASHFLOWITEM ITEM ON ITEM.ID = CF.CASHFLOWITEM  \n");
        sql.append(" WHERE\n");
        sql.append("          1 = 1\n");
        sql.append("          AND VOUCHER.PERIODUNION like '${YEAR}%'\n");
        sql.append("          AND VOUCHER.PERIODUNION<='${ENDPERIODUNION}'\n");
        sql.append("          AND VOUCHER.PERIODUNION>='${STARTPERIODUNION}'\n");
        sql.append("          ${ORADJUSTPERIOD} \n");
        sql.append(this.buildCfItemCondi("ITEM", "CODE", condi.getCashCode()));
        sql.append(" ORDER BY ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ORDERID");
        Variable variable = new Variable();
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("STARTPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getStartPeriod()));
        variable.put("ENDPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getEndPeriod()));
        variable.put("ORADJUSTPERIOD", adjustPeriod.toString());
        variable.put("ORENDADJUSTPERIOD", endAdjustPeriod.toString());
        variable.put("STARTADJUSTPERIOD", BipFlagShipFetchUtil.getAdjustPeriodUnion(condi.getAcctYear(), condi.getStartAdjustPeriod()));
        variable.put("ENDADJUSTPERIOD", BipFlagShipFetchUtil.getAdjustPeriodUnion(condi.getAcctYear(), condi.getEndAdjustPeriod()));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(querySql, new Object[0], (ResultSetExtractor)new XjllDetailLedgerResultSetExtractor(condi, assistMappingList));
    }
}

