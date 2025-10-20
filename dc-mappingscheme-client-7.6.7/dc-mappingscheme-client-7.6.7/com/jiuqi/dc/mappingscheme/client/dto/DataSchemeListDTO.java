/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import java.io.Serializable;

public class DataSchemeListDTO
implements Serializable {
    private static final long serialVersionUID = 291375143714133017L;
    private String searchKey;
    private String pluginType;

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getPluginType() {
        return this.pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }
}

