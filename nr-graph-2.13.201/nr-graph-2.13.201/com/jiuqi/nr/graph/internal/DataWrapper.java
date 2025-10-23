/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IDataWrapper;
import com.jiuqi.nr.graph.exception.GraphException;
import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.Objects;
import org.springframework.util.Assert;

public class DataWrapper
implements IDataWrapper {
    private String key;
    private Object data;
    private NodeLabel label;

    public DataWrapper(NodeLabel label, String key, Object data) {
        this.key = key;
        this.data = data;
        this.label = label;
    }

    public DataWrapper(IDataWrapper data) {
        this.key = data.getKey();
        this.data = data.getData();
        this.label = (NodeLabel)data.getLabel();
    }

    protected DataWrapper() {
    }

    protected void init(NodeLabel label, AttrValueGetter<Object, String> keyGetter, Object data) {
        if (null != keyGetter) {
            this.key = keyGetter.get(data);
            this.data = data;
            this.label = label;
        } else if (data instanceof IDataWrapper) {
            IDataWrapper d = (IDataWrapper)data;
            this.key = d.getKey();
            this.data = d.getData();
            this.label = (NodeLabel)d.getLabel();
        } else {
            this.key = IDataWrapper.DEFAULT_NODE_KEY_GETTER.get(data);
            this.data = data;
            this.label = label;
        }
    }

    @Override
    public NodeLabel getLabel() {
        return this.label;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public <T> T getData(Class<T> clz) {
        Assert.notNull(clz, "clz must not be null.");
        if (null == this.data) {
            return null;
        }
        if (clz.isInstance(this.data)) {
            return (T)this.data;
        }
        throw new GraphException("\u5143\u6570\u636e\u975eclz\u7684\u5b9e\u4f8b");
    }

    public int hashCode() {
        return Objects.hash(this.key, this.label);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DataWrapper other = (DataWrapper)obj;
        return Objects.equals(this.key, other.key) && Objects.equals(this.label, other.label);
    }

    public String toString() {
        return "DataWrapper [label=" + this.label + ", key=" + this.key + "]";
    }
}

