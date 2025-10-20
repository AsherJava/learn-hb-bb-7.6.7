/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

public class NpContextConsumer
implements Consumer<Map.Entry<String, Object>>,
Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Object> extInfo = null;

    public NpContextConsumer(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

    @Override
    public void accept(Map.Entry<String, Object> t) {
        this.extInfo.put(t.getKey(), t.getValue());
    }
}

