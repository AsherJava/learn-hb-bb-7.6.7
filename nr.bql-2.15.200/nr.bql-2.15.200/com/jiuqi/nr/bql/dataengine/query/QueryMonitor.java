/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryMonitor
extends AbstractMonitor {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.nvwa.bql.engine");

    public QueryMonitor(DataEngineConsts.DataEngineRunType runType) {
        super(runType);
    }

    protected Logger getLogger() {
        return logger;
    }

    public boolean isDebug() {
        return logger.isDebugEnabled() || super.isDebug();
    }
}

