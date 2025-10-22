/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValueList<E>
implements Serializable {
    private static final long serialVersionUID = 583859576743661825L;
    private final Set<E> valueSet = new HashSet();
    private final List<E> values = new ArrayList();

    public int add(E e) {
        if (!this.valueSet.add(e)) {
            return this.values.indexOf(e);
        }
        this.values.add(e);
        return this.values.size() - 1;
    }

    public E get(int index) {
        return this.values.get(index);
    }
}

