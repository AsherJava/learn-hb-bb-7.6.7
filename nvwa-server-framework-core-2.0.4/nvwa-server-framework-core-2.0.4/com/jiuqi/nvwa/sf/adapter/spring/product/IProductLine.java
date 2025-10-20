/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.product;

import com.jiuqi.nvwa.sf.adapter.spring.product.ManifestResolver;

public interface IProductLine {
    public String id();

    public String title();

    default public String version() {
        return ManifestResolver.findProperty(this.getClass(), "version");
    }

    default public String buildTime() {
        return ManifestResolver.findProperty(this.getClass(), "buildTime");
    }
}

