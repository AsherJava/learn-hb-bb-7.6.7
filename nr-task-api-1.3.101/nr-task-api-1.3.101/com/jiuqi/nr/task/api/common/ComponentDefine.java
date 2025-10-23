/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.common;

public class ComponentDefine {
    private String componentName;
    private String productLine;
    private String entry;

    public ComponentDefine() {
    }

    public ComponentDefine(String componentName, String productLine, String entry) {
        this.componentName = componentName;
        this.productLine = productLine;
        this.entry = entry;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getProductLine() {
        return this.productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getEntry() {
        return this.entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}

