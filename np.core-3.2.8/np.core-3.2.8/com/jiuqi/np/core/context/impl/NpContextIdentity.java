/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context.impl;

import com.jiuqi.np.core.context.ContextIdentity;

public class NpContextIdentity
implements ContextIdentity {
    private static final long serialVersionUID = 8041342503947837773L;
    private String id;
    private String title;
    private String orgCode;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}

