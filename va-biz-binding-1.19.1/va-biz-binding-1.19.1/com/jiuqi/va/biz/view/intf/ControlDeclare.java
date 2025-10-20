/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.intf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ControlDeclare {
    protected Map<String, Object> props = new HashMap<String, Object>();

    public void setId(UUID id) {
        this.props.put("id", id);
    }

    public void setType(String type) {
        this.props.put("type", type);
    }

    public void setPropValue(String name, Object value) {
        this.props.put(name, value);
    }

    public void addProps(Map<String, Object> props) {
        this.props.putAll(props);
    }
}

