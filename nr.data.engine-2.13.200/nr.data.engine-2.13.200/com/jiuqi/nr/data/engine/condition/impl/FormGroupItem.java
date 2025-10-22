/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.condition.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FormGroupItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> groupKeys;
    private List<String> formKeys;
    private Map<String, List<String>> formsByGroup;
    private boolean groupCondition;
    private boolean formCondition;

    public List<String> getGroupKeys() {
        return this.groupKeys;
    }

    public void setGroupKeys(List<String> groupKeys) {
        this.groupKeys = groupKeys;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public Map<String, List<String>> getFormsByGroup() {
        return this.formsByGroup;
    }

    public void setFormsByGroup(Map<String, List<String>> formsByGroup) {
        this.formsByGroup = formsByGroup;
    }

    public boolean isGroupCondition() {
        return this.groupCondition;
    }

    public void setGroupCondition(boolean groupCondition) {
        this.groupCondition = groupCondition;
    }

    public boolean isFormCondition() {
        return this.formCondition;
    }

    public void setFormCondition(boolean formCondition) {
        this.formCondition = formCondition;
    }
}

