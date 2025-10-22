/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

import java.util.HashMap;
import java.util.Map;

public class LogParam {
    private String title;
    private String module;
    private Map<String, Object> orherMsg = new HashMap<String, Object>();
    private String keyInfo;

    public String getKeyInfo() {
        return this.keyInfo;
    }

    public void setKeyInfo(String keyInfo) {
        this.keyInfo = keyInfo;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Map<String, Object> getOrherMsg() {
        return this.orherMsg;
    }

    public void setOrherMsg(Map<String, Object> orherMsg) {
        this.orherMsg = orherMsg;
    }
}

