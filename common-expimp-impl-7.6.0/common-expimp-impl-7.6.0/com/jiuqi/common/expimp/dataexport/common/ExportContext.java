/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.dataexport.common;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import java.util.HashMap;
import java.util.Map;

public class ExportContext {
    private String sn;
    private String executor;
    private String param;
    private boolean isAsync;
    private final ProgressDataImpl progressData;
    private final Map<String, Object> varMap;
    private boolean templateExportFlag;

    public ExportContext(String sn) {
        this.sn = sn;
        this.progressData = new ProgressDataImpl<String>(sn, "", "dataexport");
        this.varMap = new HashMap<String, Object>(0);
    }

    public boolean isAsync() {
        return this.isAsync;
    }

    public void setAsync(boolean async) {
        this.isAsync = async;
    }

    public String getSn() {
        return this.sn;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Map<String, Object> getVarMap() {
        return this.varMap;
    }

    public String getExecutor() {
        return this.executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public ProgressDataImpl getProgressData() {
        return this.progressData;
    }

    public void setTemplateExport(boolean templateExportFlag) {
        this.templateExportFlag = templateExportFlag;
    }

    public boolean isTemplateExportFlag() {
        return this.templateExportFlag;
    }

    public void setTemplateExportFlag(boolean templateExportFlag) {
        this.templateExportFlag = templateExportFlag;
    }
}

