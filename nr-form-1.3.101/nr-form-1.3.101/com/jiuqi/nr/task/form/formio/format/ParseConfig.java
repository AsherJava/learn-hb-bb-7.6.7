/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format;

public class ParseConfig {
    private Boolean useCache = true;

    public Boolean isUseCache() {
        return this.useCache != null && this.useCache != false;
    }

    public void setUseCache(Boolean useCache) {
        this.useCache = useCache;
    }
}

