/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public class OrderAfterImport
extends JtableLog {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String taskKey;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private AsyncTaskMonitor asyncTaskMonitor;
    private boolean importSuccess = false;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public boolean isImportSuccess() {
        return this.importSuccess;
    }

    public void setImportSuccess(boolean importSuccess) {
        this.importSuccess = importSuccess;
    }
}

