/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 */
package com.jiuqi.nr.workflow2.engine.core;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataIntputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;

public interface IProcessIOService {
    public void exportProcessData(IBusinessKeyCollection var1, IPorcessDataOutputStream var2, ProcessDataOutputOptions var3, IProgressMonitor var4);

    public IProcessDataImportResult importProcessData(IPorcessDataInputStream var1, ProcessDataIntputOptions var2, IProgressMonitor var3);

    public IProcessConfigChangeImportExecutor getConfigChangeImportExecutor(IPorcessDataInputStream var1, WorkflowSettingsDO var2);
}

