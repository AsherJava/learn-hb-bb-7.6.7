/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.model.FuncExecuteConfig;
import java.util.HashMap;
import java.util.Map;

public class ExecuteFuncParam {
    private FuncExecuteConfig templateConfig;
    private Map<String, Object> runtimePara = new HashMap<String, Object>();
    private boolean haveTask = true;
    private Map<String, Object> sysParam;

    public Map<String, Object> getRuntimeParam() {
        return this.runtimePara;
    }

    public void setRuntimeParam(Map<String, Object> runtimeParam) {
        this.runtimePara = runtimeParam;
    }

    public FuncExecuteConfig getTemplateConfig() {
        return this.templateConfig;
    }

    public void setTemplateConfig(FuncExecuteConfig templateConfig) {
        this.templateConfig = templateConfig;
    }

    public Map<String, Object> getSysParam() {
        return this.sysParam;
    }

    public void setSysParam(Map<String, Object> sysParam) {
        this.sysParam = sysParam;
    }

    public boolean isHaveTask() {
        return this.haveTask;
    }

    public void setHaveTask(boolean haveTask) {
        this.haveTask = haveTask;
    }
}

