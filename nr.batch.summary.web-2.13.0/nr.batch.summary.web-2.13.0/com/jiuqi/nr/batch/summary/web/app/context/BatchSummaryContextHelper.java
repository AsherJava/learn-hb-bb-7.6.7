/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.batch.summary.web.app.context;

import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;

public interface BatchSummaryContextHelper {
    public BatchSummaryContextData initContextData();

    public BatchSummaryContextData getContextData();

    public void updateContextData(BatchSummaryContextData var1);

    public TaskDefine getCurrTaskDefine(String var1);

    public FormSchemeDefine getCurrFormSchemeDefine(TaskDefine var1);
}

