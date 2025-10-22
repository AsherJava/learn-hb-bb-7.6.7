/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.ENameSet;
import java.io.Serializable;

public class DimensionSet
implements Serializable {
    private static final long serialVersionUID = 2451250669567249757L;
    public static DimensionSet EMPTY = new DimensionSet();
    private final ENameSet dimensions = new ENameSet();
    private boolean isLocked = false;

    public DimensionSet() {
    }

    public DimensionSet(ENameSet nameSet) {
        this.dimensions.addAll(nameSet);
    }

    public DimensionSet(DimensionSet template) {
        this.dimensions.addAll(template.dimensions);
    }

    public ENameSet getDimensions() {
        return this.dimensions;
    }

    public int size() {
        return this.dimensions.size();
    }

    public String get(int index) {
        return this.dimensions.get(index);
    }

    public int indexOf(String dimension) {
        return this.dimensions.indexOf(dimension);
    }

    public boolean contains(String dimension) {
        return this.dimensions.contains(dimension);
    }

    public int addDimension(String dimension) {
        if (this.isLocked) {
            throw new RuntimeException("DimensionSet is read only");
        }
        return this.dimensions.add(dimension);
    }

    public int removeDimension(String dimension) {
        if (this.isLocked) {
            throw new RuntimeException("DimensionSet is read only");
        }
        return this.dimensions.remove(dimension);
    }

    public void removeAt(int index) {
        if (this.isLocked) {
            throw new RuntimeException("DimensionSet is read only");
        }
        this.dimensions.removeAt(index);
    }

    public boolean containsAll(DimensionSet another) {
        return this.dimensions.containsAll(another.dimensions);
    }

    public boolean containsAny(DimensionSet another) {
        return this.dimensions.containsAll(another.dimensions);
    }

    public void addAll(DimensionSet another) {
        if (this.isLocked) {
            throw new RuntimeException("DimensionSet is read only");
        }
        this.dimensions.addAll(another.dimensions);
    }

    public void removeAll(DimensionSet another) {
        if (this.isLocked) {
            throw new RuntimeException("DimensionSet is read only");
        }
        this.dimensions.removeAll(another.dimensions);
    }

    public void clear() {
        this.dimensions.clear();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DimensionSet)) {
            return false;
        }
        return this.compareTo((DimensionSet)obj) == 0;
    }

    public int compareTo(DimensionSet another) {
        if (another == null) {
            return 1;
        }
        return this.dimensions.compareTo(another.dimensions);
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void setLocked() {
        this.isLocked = true;
    }

    public String toString() {
        return this.dimensions.toString();
    }
}

