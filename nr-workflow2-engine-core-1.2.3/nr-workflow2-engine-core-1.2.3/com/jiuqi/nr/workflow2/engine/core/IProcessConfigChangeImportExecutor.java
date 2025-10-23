/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core;

import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;

public interface IProcessConfigChangeImportExecutor {
    public void executeDataImport(WorkflowSettingsDO var1, WorkflowSettingsDO var2, IPorcessDataInputStream var3);

    public void commit();

    public void rollback();
}

