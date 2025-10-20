/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql;

import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.common.base.util.StringUtils;

public class CustomFetchFixedConditionHolder {
    private String fixedCondition;

    public CustomFetchFixedConditionHolder(String fixedCondition) {
        this.fixedCondition = fixedCondition;
    }

    public String buildSql(FetchTaskContext fetchTaskContext) {
        if (StringUtils.isEmpty((String)this.fixedCondition)) {
            return "";
        }
        String orgnFiexdCondition = ContextVariableParseUtil.parse((String)this.fixedCondition, (FetchTaskContext)fetchTaskContext);
        if (!orgnFiexdCondition.trim().toUpperCase().startsWith("AND")) {
            return " AND " + orgnFiexdCondition;
        }
        return orgnFiexdCondition;
    }
}

