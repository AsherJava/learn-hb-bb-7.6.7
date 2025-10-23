/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

public class TaskActionDTO {
    private String code;
    private String title;
    private double order;
    private String productLine;
    private String entry;
    private String component;
    private boolean disable;

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

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
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

    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public boolean isDisable() {
        return this.disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}

