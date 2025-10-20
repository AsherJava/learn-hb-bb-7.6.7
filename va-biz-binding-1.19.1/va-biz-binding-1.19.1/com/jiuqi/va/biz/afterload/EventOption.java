/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.afterload;

import java.util.Map;

public class EventOption {
    private String prodLine;
    private String moduleName;
    private String scriptPath;
    private String evalFunction;
    private String eventName;
    private Map<String, Object> params;

    public EventOption() {
    }

    public EventOption(String prodLine, String moduleName, String scriptPath, String eventName) {
        this.prodLine = prodLine;
        this.moduleName = moduleName;
        this.scriptPath = scriptPath;
        this.eventName = eventName;
    }

    public String getProdLine() {
        return this.prodLine;
    }

    public void setProdLine(String prodLine) {
        this.prodLine = prodLine;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getScriptPath() {
        return this.scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public String getEvalFunction() {
        return this.evalFunction;
    }

    public void setEvalFunction(String evalFunction) {
        this.evalFunction = evalFunction;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

