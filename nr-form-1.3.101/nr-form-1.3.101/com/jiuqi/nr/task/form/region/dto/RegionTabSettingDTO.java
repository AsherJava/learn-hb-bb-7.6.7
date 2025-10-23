/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.region.dto;

import com.jiuqi.nr.task.form.dto.AbstractState;

public class RegionTabSettingDTO
extends AbstractState {
    private String title;
    private String displayCondition;
    private String filterCondition;
    private String bindingExpression;
    private String order;
    private int rowNum;
    private String id;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayCondition() {
        return this.displayCondition;
    }

    public void setDisplayCondition(String displayCondition) {
        this.displayCondition = displayCondition;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getBindingExpression() {
        return this.bindingExpression;
    }

    public void setBindingExpression(String bindingExpression) {
        this.bindingExpression = bindingExpression;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

