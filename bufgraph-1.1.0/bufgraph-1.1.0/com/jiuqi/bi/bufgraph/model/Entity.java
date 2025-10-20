/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Entity
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int NO_ID = -1;
    protected int id;
    protected String uid;
    protected Map<String, Object> props;

    public Entity(int id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    public int getId() {
        return this.id;
    }

    public String getUid() {
        return this.uid;
    }

    public Object getProperty(String key) {
        return this.props == null ? null : this.props.get(key);
    }

    public Object getProperty(String key, Object defaultValue) {
        Object v = this.props == null ? defaultValue : this.props.get(key);
        return v == null ? defaultValue : v;
    }

    public void setProperty(String key, Object value) {
        if (this.props == null) {
            this.props = new HashMap<String, Object>();
        }
        this.props.put(key, value);
    }

    public boolean containProperty(String key) {
        return this.props == null ? false : this.props.containsKey(key);
    }

    public void removeProperty(String key) {
        if (this.props != null) {
            this.props.remove(key);
        }
    }

    public Set<String> propertyKeys() {
        return this.props == null ? new HashSet(0) : this.props.keySet();
    }

    public void optimize() {
        this.props = null;
        this.uid = null;
    }
}

