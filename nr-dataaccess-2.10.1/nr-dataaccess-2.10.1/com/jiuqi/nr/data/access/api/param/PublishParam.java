/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.access.api.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;

public class PublishParam
implements Serializable {
    private String formSchemeKey;
    private DimensionCollection masterKey;
    private List<String> formKeys;
    private boolean isPublish;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCollection getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionCollection masterKey) {
        this.masterKey = masterKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isPublish() {
        return this.isPublish;
    }

    public void setPublish(boolean publish) {
        this.isPublish = publish;
    }
}

