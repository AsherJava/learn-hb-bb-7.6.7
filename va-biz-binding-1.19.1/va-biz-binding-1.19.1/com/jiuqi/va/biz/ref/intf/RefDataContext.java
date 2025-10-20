/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ref.intf;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RefDataContext {
    private static final String DIM_NAME_UNITCODE = "unitcode";
    private final Map<String, Object> dims = new HashMap<String, Object>();
    private Date bizDate;
    private RefDataContext parent;

    public RefDataContext() {
    }

    public RefDataContext(RefDataContext parent) {
        this.parent = parent;
    }

    public Date getBizDate() {
        if (this.bizDate == null) {
            return this.parent == null ? null : this.parent.getBizDate();
        }
        return this.bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getUnitCode() {
        return (String)this.get(DIM_NAME_UNITCODE);
    }

    public void setUnitCode(String unitCode) {
        this.set(DIM_NAME_UNITCODE, unitCode);
    }

    public Map<String, Object> getDims() {
        return Collections.unmodifiableMap(this.dims);
    }

    public Object get(Object key) {
        Object value = this.dims.get(key);
        if (value == null) {
            return this.parent.get(key);
        }
        return value;
    }

    public void set(String key, Object value) {
        this.dims.put(key, value);
    }
}

