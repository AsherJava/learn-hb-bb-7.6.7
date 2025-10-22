/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.context;

import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;

public interface BatchGatherGZWContextHelper {
    public BatchGatherGZWContextData initContextData();

    public BatchGatherGZWContextData getContextData();

    public void updateContextData(BatchGatherGZWContextData var1);

    public TaskDefine getCurrTaskDefine(String var1);

    public FormSchemeDefine getCurrFormSchemeDefine(TaskDefine var1);
}

