/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Transient
 */
package com.jiuqi.gcreport.definition.impl.basic.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Transient;

public abstract class AbstractFieldDynamicDeclarator
implements Serializable {
    private static final long serialVersionUID = 5314281387031006090L;
    @Transient
    protected Map<String, Object> fields;

    public Map<String, Object> getFields() {
        if (this.fields == null) {
            this.fields = new HashMap<String, Object>();
        }
        return this.fields;
    }

    public void resetFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public void addFieldValue(String field, Object value) {
        if (this.fields == null) {
            this.fields = new HashMap<String, Object>();
        }
        this.fields.put(field, value);
    }

    public Object getFieldValue(String field) {
        if (this.fields == null) {
            return null;
        }
        return this.fields.get(field);
    }

    public boolean hasField(String field) {
        if (this.fields == null) {
            this.fields = new HashMap<String, Object>();
            return false;
        }
        return this.fields.containsKey(field);
    }
}

