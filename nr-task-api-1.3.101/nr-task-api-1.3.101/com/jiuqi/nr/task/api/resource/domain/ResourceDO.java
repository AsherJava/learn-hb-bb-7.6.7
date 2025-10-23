/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.resource.domain;

import com.jiuqi.nr.task.api.resource.domain.Category;
import java.util.List;

public class ResourceDO
extends Category {
    private String[] fields;
    private List<Object[]> values;

    public ResourceDO(String type) {
        super(type);
    }

    public String[] getFields() {
        return this.fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public List<Object[]> getValues() {
        return this.values;
    }

    public void setValues(List<Object[]> values) {
        this.values = values;
    }
}

