/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.va6.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor;
import com.jiuqi.bde.plugin.va6.BdeVa6PluginType;
import com.jiuqi.bde.plugin.va6.util.Va6FetchUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Va6XjllBalanceContentProvider
extends AbstractXjllBalanceContentProvider {
    @Autowired
    private BdeVa6PluginType va6PluginType;

    public String getPluginType() {
        return this.va6PluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ITEM.STDCODE AS MD_CFITEM,\n");
        sql.append("       ITEM.STDNAME AS MD_CFITEM_NAME,\n");
        sql.append("       SUM(CF.THISDATA) AS BQNUM, \n");
        sql.append("       SUM(CF.ADDUPDATA) AS LJNUM, \n");
        sql.append("       SUM(CF.THISDATA) AS WBQNUM, \n");
        sql.append("       SUM(CF.ADDUPDATA) AS WLJNUM \n");
        sql.append("  FROM CF_REPORT CF \n");
        sql.append(" INNER JOIN MD_CFITEM ITEM ON CF.PRJID = ITEM.OBJECTID \n");
        sql.append(" INNER JOIN MD_FINORG ORG ON CF.UNITID = ORG.RECID  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" INNER JOIN SM_BOOK BOOK ON CF.ACCTBOOKID = BOOK.RECID AND BOOK.STDCODE = ?  \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        }
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND CF.ACCTYEAR = ?  \n");
        args.add(condi.getAcctYear());
        sql.append("   AND CF.ACCTPERIOD = ?  \n");
        args.add(condi.getEndPeriod());
        sql.append("   AND ORG.STDCODE = ?  \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append(this.buildCfItemCondi("ITEM", "STDCODE", condi.getCashCode()));
        sql.append(" GROUP BY ITEM.STDCODE, ITEM.STDNAME \n");
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getEndPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        executeSql = Va6FetchUtil.parse(executeSql, condi.getOrgMapping().getAcctOrgCode(), condi.getOrgMapping().getAcctBookCode(), String.valueOf(condi.getAcctYear()), String.valueOf(condi.getEndPeriod()), condi.getIncludeUncharged());
        return new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new XjllBalanceResultSetExtractor(condi, (List)CollectionUtils.newArrayList()));
    }
}

