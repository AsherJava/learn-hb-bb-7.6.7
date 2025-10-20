/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context.impl;

import com.jiuqi.np.core.context.ContextOrganization;

public class NpContextOrganization
implements ContextOrganization {
    private static final long serialVersionUID = 7041342503947837773L;
    private String id;
    private String code;
    private String name;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

