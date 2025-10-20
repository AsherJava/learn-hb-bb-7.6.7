/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import java.io.Serializable;
import java.util.Objects;

public class FormulaConditionObj
implements Serializable {
    private String key;
    private String code;
    private String title;
    private String condition;
    private String order;
    private String taskKey;
    private String updateTime;
    private Boolean binding;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getBinding() {
        return this.binding;
    }

    public void setBinding(Boolean binding) {
        this.binding = binding;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FormulaConditionObj that = (FormulaConditionObj)o;
        return Objects.equals(this.code, that.code);
    }

    public int hashCode() {
        return Objects.hash(this.code);
    }
}

