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
package com.jiuqi.bde.plugin.va6.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bde.plugin.va6.BdeVa6PluginType;
import com.jiuqi.bde.plugin.va6.util.Va6FetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Va6XjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeVa6PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ITEM.STDCODE AS CFITEMCODE, \n");
        sql.append("       1 AS ORIENT, \n");
        sql.append("       SUM(CF.THISDATA) AS BQNUM, \n");
        sql.append("       SUM(CF.ADDUPDATA) AS LJNUM, \n");
        sql.append("       SUM(CF.THISDATA) AS WBQNUM, \n");
        sql.append("       SUM(CF.ADDUPDATA) AS WLJNUM \n");
        sql.append("  FROM CF_REPORT CF \n");
        sql.append(" INNER JOIN MD_CFITEM ITEM ON CF.PRJID = ITEM.OBJECTID \n");
        sql.append(" INNER JOIN MD_FINORG ORG ON CF.UNITID = ORG.RECID AND ORG.STDCODE = '${UNITCODE}'  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" INNER JOIN SM_BOOK BOOK ON CF.ACCTBOOKID = BOOK.RECID AND BOOK.STDCODE = '${BOOKCODE}'  \n");
        }
        sql.append(" WHERE 1=1 \n");
        sql.append("   AND CF.ACCTYEAR = ${ACCTYEAR}  \n");
        sql.append("   AND CF.ACCTPERIOD = ${ACCTPERIOD}  \n");
        sql.append(" GROUP BY ITEM.STDCODE \n");
        Variable variable = new Variable();
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("ACCTPERIOD", String.valueOf(condi.getEndPeriod()));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        querySql = Va6FetchUtil.parse(querySql, condi.getOrgMapping().getAcctOrgCode(), condi.getOrgMapping().getAcctBookCode(), String.valueOf(condi.getAcctYear()), String.valueOf(condi.getEndPeriod()), condi.getIncludeUncharged());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)condi, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, null, (ResultSetExtractor)new FetchDataExtractor());
    }
}

