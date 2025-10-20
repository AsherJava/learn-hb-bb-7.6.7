/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.dataimport.common;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import java.util.HashMap;
import java.util.Map;

public class ImportContext {
    private String sn;
    private String executor;
    private String param;
    private String fileName;
    private final ProgressDataImpl progressData;
    private final Map<String, Object> varMap;

    public ImportContext(String sn) {
        this.sn = sn;
        this.progressData = new ProgressDataImpl<String>(sn, "", "dataimport");
        this.varMap = new HashMap<String, Object>(0);
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

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
}

