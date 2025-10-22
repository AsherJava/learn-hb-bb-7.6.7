/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class DimensionCombinationWrapper {
    private final DimensionCombination masterKey;
    private Integer hashCode;

    public DimensionCombination getMasterKey() {
        return this.masterKey;
    }

    public DimensionCombinationWrapper(DimensionCombination masterKey) {
        this.masterKey = masterKey;
    }

    public boolean equals(Object o) {
        Collection names;
        HashSet nameSet;
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DimensionCombinationWrapper that = (DimensionCombinationWrapper)o;
        DimensionCombination thatMasterKey = that.masterKey;
        if (thatMasterKey == this.masterKey) {
            return true;
        }
        if (thatMasterKey == null) {
            return false;
        }
        if (thatMasterKey.getSize() != this.masterKey.getSize()) {
            return false;
        }
        Collection tharNames = thatMasterKey.getNames();
        HashSet thatNameSet = new HashSet(tharNames);
        boolean nameEqual = Objects.equals(thatNameSet, nameSet = new HashSet(names = this.masterKey.getNames()));
        if (!nameEqual) {
            return false;
        }
        for (String s : thatNameSet) {
            Object value1;
            Object value = this.masterKey.getValue(s);
            boolean valueEqual = Objects.equals(value, value1 = thatMasterKey.getValue(s));
            if (valueEqual) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.masterKey == null) {
            return 0;
        }
        if (this.hashCode == null) {
            int result = 1;
            Collection names = this.masterKey.getNames();
            names = names.stream().sorted().collect(Collectors.toList());
            for (String name : names) {
                result = 31 * result + (name == null ? 0 : name.hashCode());
                Object value = this.masterKey.getValue(name);
                result = 31 * result + (value == null ? 0 : value.hashCode());
            }
            this.hashCode = result;
            return this.hashCode;
        }
        return this.hashCode;
    }

    public String toString() {
        if (this.masterKey == null) {
            return "";
        }
        return this.masterKey.toString();
    }
}

