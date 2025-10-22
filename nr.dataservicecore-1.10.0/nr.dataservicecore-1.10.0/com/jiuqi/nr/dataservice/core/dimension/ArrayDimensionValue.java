/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import java.io.Serializable;
import java.util.Arrays;

public class ArrayDimensionValue
implements Serializable,
DimensionValue {
    private static final long serialVersionUID = 3342749646928341482L;
    private final Object[] value;
    private final String name;
    private String entityID;

    @Deprecated
    public ArrayDimensionValue(String name, Object[] value) {
        this.name = name;
        this.value = value;
    }

    public ArrayDimensionValue(String name, String entityID, Object[] value) {
        this.name = name;
        this.value = value;
        this.entityID = entityID;
    }

    public Object[] getValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEntityID() {
        return this.entityID;
    }

    public String toString() {
        return "ArrayDimensionValue{value=" + Arrays.toString(this.value) + ", name='" + this.name + '\'' + ", entityID='" + this.entityID + '\'' + '}';
    }
}

