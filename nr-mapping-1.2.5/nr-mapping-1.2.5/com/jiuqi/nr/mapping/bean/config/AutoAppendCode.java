/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.bean.config;

import java.util.HashMap;
import java.util.Map;

public class AutoAppendCode {
    private boolean autoAppendCode = false;
    private String appendCodeZb;
    private Map<String, String> codeMapping = new HashMap<String, String>();

    public boolean isAutoAppendCode() {
        return this.autoAppendCode;
    }

    public void setAutoAppendCode(boolean autoAppendCode) {
        this.autoAppendCode = autoAppendCode;
    }

    public String getAppendCodeZb() {
        return this.appendCodeZb;
    }

    public void setAppendCodeZb(String appendCodeZb) {
        this.appendCodeZb = appendCodeZb;
    }

    public Map<String, String> getCodeMapping() {
        return this.codeMapping;
    }

    public void setCodeMapping(Map<String, String> codeMapping) {
        this.codeMapping = codeMapping;
    }
}

