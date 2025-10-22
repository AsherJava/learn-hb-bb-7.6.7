/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.nr.dataentry.gather.IGathers;
import java.util.List;

public interface IListGathers<T>
extends IGathers<T> {
    public List<T> gather();
}

