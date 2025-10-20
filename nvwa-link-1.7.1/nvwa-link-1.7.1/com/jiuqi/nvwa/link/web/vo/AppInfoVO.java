/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.web.vo;

import com.jiuqi.nvwa.link.provider.ResourceAppInfo;

public class AppInfoVO
extends ResourceAppInfo {
    private String id;

    public AppInfoVO() {
    }

    public AppInfoVO(ResourceAppInfo info) {
        this.setAppName(info.getAppName());
        this.setFuncName(info.getFuncName());
        this.setProdLine(info.getProdLine());
        this.setType(info.getType());
    }

    public AppInfoVO(ResourceAppInfo info, String id) {
        this.id = id;
        this.setAppName(info.getAppName());
        this.setFuncName(info.getFuncName());
        this.setProdLine(info.getProdLine());
        this.setType(info.getType());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

