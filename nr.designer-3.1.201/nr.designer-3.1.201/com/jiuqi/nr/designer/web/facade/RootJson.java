/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.web.facade.PreloadData;

public class RootJson {
    @JsonProperty
    private PreloadData PreloadData;

    @JsonIgnore
    public PreloadData getPreloadData() {
        return this.PreloadData;
    }

    @JsonIgnore
    public void setPreloadData(PreloadData preloadData) {
        this.PreloadData = preloadData;
    }
}

