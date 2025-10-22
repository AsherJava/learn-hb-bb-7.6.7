/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionBuildException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.ValueList;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DimensionTable
implements Serializable {
    private static final long serialVersionUID = -6832639235600568944L;
    private DimensionValue[] dimensionValues = new DimensionValue[4];
    private ValueList[] values = new ValueList[4];
    private int dwIdx = -1;
    private int dimLength = 0;
    private transient boolean sorted = false;
    private int[] sortIdx = null;

    public int getIndexByName(String dimensionName) {
        if (this.notInit()) {
            return -1;
        }
        for (int i = 0; i < this.dimLength; ++i) {
            if (!dimensionName.equals(this.dimensionValues[i].getName())) continue;
            return i;
        }
        return -1;
    }

    private boolean notInit() {
        return this.dimLength == 0;
    }

    public Object getValue(int dimIndex, int valueIndex) {
        this.validateDimIndex(dimIndex);
        return this.values[dimIndex].get(valueIndex);
    }

    public int getDwIdx() {
        return this.dwIdx;
    }

    public DimensionValue getDimensionDefine(int index) {
        return this.dimensionValues[index];
    }

    public DimensionValue[] getDimensionDefines() {
        return this.dimensionValues;
    }

    public FixedDimensionValue getFixedDimensionValue(int dimIndex, int valueIndex) {
        this.validateDimIndex(dimIndex);
        DimensionValue dimensionValue = this.dimensionValues[dimIndex];
        return new FixedDimensionValue(dimensionValue.getName(), dimensionValue.getEntityID(), this.getValue(dimIndex, valueIndex));
    }

    public int getDimSize() {
        return this.dimLength;
    }

    public Collection<String> getNames() {
        return Arrays.stream(this.dimensionValues).limit(this.dimLength).map(DimensionValue::getName).collect(Collectors.toList());
    }

    public int addDimensionValue(DimensionValue dimensionValue) {
        if (this.dimensionValues.length == this.dimLength) {
            this.dimensionValues = Arrays.copyOf(this.dimensionValues, this.dimensionValues.length * 2);
            this.values = Arrays.copyOf(this.values, this.values.length * 2);
        }
        int idx = this.dimLength++;
        this.dimensionValues[idx] = dimensionValue;
        this.values[idx] = new ValueList();
        return idx;
    }

    public int addValue(int dimIndex, Object value) {
        this.validateDimIndex(dimIndex);
        return this.values[dimIndex].add(value);
    }

    private void validateDimIndex(int dimIndex) {
        if (dimIndex >= this.dimLength) {
            throw new DimensionBuildException("DimIndexOutOfBounds!");
        }
    }

    public int addDwDimensionValue(FixedDimensionValue fixedDimensionValue) {
        this.dwIdx = this.addDimensionValue(fixedDimensionValue);
        return this.dwIdx;
    }

    public void sortDimension() {
        if (this.sorted && this.sortIdx.length == this.dimLength) {
            return;
        }
        this.sortIdx = new int[this.dimLength];
        List sortedDimension = Arrays.stream(this.dimensionValues).filter(Objects::nonNull).sorted(Comparator.comparing(DimensionValue::getName)).collect(Collectors.toList());
        for (int i = 0; i < this.dimLength; ++i) {
            for (int s = 0; s < this.dimLength; ++s) {
                if (!this.dimensionValues[s].getName().equals(((DimensionValue)sortedDimension.get(i)).getName())) continue;
                this.sortIdx[i] = s;
            }
        }
        this.sorted = true;
    }

    public int getHashCode(int[] idxArr) {
        this.sortDimension();
        int hash = 1;
        for (int i = 0; i < this.dimLength; ++i) {
            Object value = this.getValue(this.sortIdx[i], idxArr[this.sortIdx[i]]);
            hash = hash * 31 + (value == null ? 0 : value.hashCode());
        }
        return hash;
    }
}

