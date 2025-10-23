/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.param;

public class ReleaseParam {
    private String formSchemeKey;
    private boolean publishDataScheme;
    private boolean syncPublish;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public boolean isPublishDataScheme() {
        return this.publishDataScheme;
    }

    public void setPublishDataScheme(boolean publishDataScheme) {
        this.publishDataScheme = publishDataScheme;
    }

    public boolean isSyncPublish() {
        return this.syncPublish;
    }

    public void setSyncPublish(boolean syncPublish) {
        this.syncPublish = syncPublish;
    }
}

