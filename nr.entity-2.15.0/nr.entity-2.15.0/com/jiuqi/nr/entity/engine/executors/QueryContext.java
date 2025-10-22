/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.executors;

import com.jiuqi.nr.entity.engine.executors.QueryService;
import com.jiuqi.nr.entity.log.EntityLogger;

public class QueryContext {
    private final EntityLogger entityLogger;
    private final QueryService queryService;

    public QueryContext(EntityLogger entityLogger, QueryService queryService) {
        this.entityLogger = entityLogger;
        this.queryService = queryService;
    }

    public EntityLogger getLogger() {
        return this.entityLogger;
    }

    public QueryService getQueryService() {
        return this.queryService;
    }
}

