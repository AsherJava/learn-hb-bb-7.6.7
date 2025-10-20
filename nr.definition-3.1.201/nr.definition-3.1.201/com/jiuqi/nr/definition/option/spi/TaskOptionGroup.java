/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.spi;

public interface TaskOptionGroup {
    public String getPageTitle();

    public String getTitle();

    public Double getOrder();

    default public boolean isCustom() {
        return false;
    }

    default public String getPluginName() {
        return null;
    }

    default public String getExpose() {
        return null;
    }

    default public String getNewPluginName() {
        return null;
    }

    default public String getNewExpose() {
        return null;
    }
}

