/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IPropertiesEditor;
import com.jiuqi.nr.graph.exception.GraphException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.util.Assert;

public class Properties
implements IPropertiesEditor {
    protected Map<String, Object> properties;

    @Override
    public void setProperty(String key, Object value) {
        if (null == this.properties) {
            this.properties = new HashMap<String, Object>();
        }
        this.properties.put(key, value);
    }

    @Override
    public void clearProperty() {
        if (null != this.properties) {
            this.properties.clear();
        }
    }

    @Override
    public Set<String> getPropertyKeys() {
        if (null == this.properties) {
            return Collections.emptySet();
        }
        return this.properties.keySet();
    }

    @Override
    public Object getProperty(String key) {
        if (null == this.properties) {
            return null;
        }
        return this.properties.get(key);
    }

    @Override
    public <T> T getProperty(String key, Class<T> clz) {
        Assert.notNull(clz, "clz must not be null.");
        Object property = this.getProperty(key);
        if (null == property) {
            return null;
        }
        if (clz.isInstance(property)) {
            return (T)property;
        }
        throw new GraphException("\u5143\u6570\u636e\u975eclz\u7684\u5b9e\u4f8b");
    }
}

