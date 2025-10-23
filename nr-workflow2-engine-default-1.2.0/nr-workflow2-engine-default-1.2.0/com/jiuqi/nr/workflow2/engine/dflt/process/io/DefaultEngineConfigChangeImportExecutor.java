/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io;

import com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.template.ImportExecuteTemplate;

public class DefaultEngineConfigChangeImportExecutor
implements IProcessConfigChangeImportExecutor {
    private final ImportExecuteTemplate executeTemplate;

    public DefaultEngineConfigChangeImportExecutor(ImportExecuteTemplate executeTemplate) {
        this.executeTemplate = executeTemplate;
    }

    public void executeDataImport(WorkflowSettingsDO originalSettingsDO, WorkflowSettingsDO newSettingsDO, IPorcessDataInputStream processDataInputStream) {
        this.executeTemplate.createTempTable();
        this.executeTemplate.executeImport();
        this.executeTemplate.swapTable();
    }

    public void commit() {
        this.executeTemplate.dropOriginTable();
        this.executeTemplate.afterRelevantOperation();
    }

    public void rollback() {
        this.executeTemplate.dropTempTable();
    }
}

