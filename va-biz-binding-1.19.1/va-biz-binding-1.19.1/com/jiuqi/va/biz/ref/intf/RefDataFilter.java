/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ref.intf;

import com.jiuqi.va.biz.ref.intf.RefDataContext;

public final class RefDataFilter {
    private String showType;
    private boolean showFullPath;
    private String tenantName;
    private String bizType;
    private String bizCode;
    private RefDataContext context;

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public boolean isShowFullPath() {
        return this.showFullPath;
    }

    public void setShowFullPath(boolean showFullPath) {
        this.showFullPath = showFullPath;
    }

    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public RefDataContext getContext() {
        return this.context;
    }

    public void setContext(RefDataContext context) {
        this.context = context;
    }
}

