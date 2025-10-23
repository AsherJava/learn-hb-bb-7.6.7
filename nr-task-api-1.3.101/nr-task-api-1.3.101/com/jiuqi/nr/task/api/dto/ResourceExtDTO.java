/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.dto;

import java.io.Serializable;

public class ResourceExtDTO
implements Serializable {
    private String code;
    private String title;
    private String productLine;
    private String componentName;
    private String entryName;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductLine() {
        return this.productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getEntryName() {
        return this.entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }
}

