/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.label.ILabelabled;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.UUID;

public interface IDataWrapper
extends ILabelabled<NodeLabel> {
    public static final AttrValueGetter<Object, String> DEFAULT_NODE_KEY_GETTER = o -> UUID.randomUUID().toString();

    public String getKey();

    public Object getData();

    public <T> T getData(Class<T> var1);
}

