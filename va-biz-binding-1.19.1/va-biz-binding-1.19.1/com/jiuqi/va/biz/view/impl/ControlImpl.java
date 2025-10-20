/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.impl;

import com.jiuqi.va.biz.view.intf.Control;
import java.util.Collections;
import java.util.Map;

public class ControlImpl
implements Control {
    private Map<String, Object> props;
    private transient Map<String, Object> propMap;

    @Override
    public Map<String, Object> getProps() {
        if (this.propMap == null) {
            this.propMap = Collections.unmodifiableMap(this.props);
        }
        return this.propMap;
    }

    void setProps(Map<String, Object> props) {
        this.props = props;
        this.propMap = null;
    }
}

