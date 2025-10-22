/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.input;

public class CommonParamsDTO {
    private String dataSchemeKey;
    @Deprecated
    private String taskKey;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    @Deprecated
    public String getTaskKey() {
        return this.taskKey;
    }

    @Deprecated
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

