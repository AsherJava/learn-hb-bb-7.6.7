/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.penetratebill.client.common;

import com.jiuqi.dc.penetratebill.client.common.CustomParam;
import com.jiuqi.dc.penetratebill.client.common.PenetrateContext;
import com.jiuqi.dc.penetratebill.client.common.PenetratePlugin;
import java.util.List;
import java.util.Map;

public class PenetrateParam {
    private PenetrateContext context;
    private List<CustomParam> customParams;
    private String url;
    private PenetratePlugin penetratePlugin;
    private Map<String, Object> extParam;

    public PenetrateParam() {
    }

    public PenetrateParam(PenetrateContext context, List<CustomParam> customParams, String url) {
        this.context = context;
        this.customParams = customParams;
        this.url = url;
    }

    public PenetrateParam(PenetrateContext context, List<CustomParam> customParams, String url, PenetratePlugin penetratePlugin, Map<String, Object> extParam) {
        this.context = context;
        this.customParams = customParams;
        this.url = url;
        this.penetratePlugin = penetratePlugin;
        this.extParam = extParam;
    }

    public PenetrateContext getContext() {
        return this.context;
    }

    public void setContext(PenetrateContext context) {
        this.context = context;
    }

    public Map<String, Object> getExtParam() {
        return this.extParam;
    }

    public void setExtParam(Map<String, Object> extParam) {
        this.extParam = extParam;
    }

    public List<CustomParam> getCustomParams() {
        return this.customParams;
    }

    public void setCustomParams(List<CustomParam> customParams) {
        this.customParams = customParams;
    }

    public PenetratePlugin getPenetratePlugin() {
        return this.penetratePlugin;
    }

    public void setPenetratePlugin(PenetratePlugin penetratePlugin) {
        this.penetratePlugin = penetratePlugin;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

