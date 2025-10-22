/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common;

import com.jiuqi.nr.data.common.AbstractMessage;

public interface Message<T extends AbstractMessage> {
    public T getMessage();
}

