/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionBuildException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationSetter;
import com.jiuqi.nr.dataservice.core.dimension.DimensionDefine;
import com.jiuqi.nr.dataservice.core.dimension.DimensionTable;
import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

class DimensionCombinationTableImpl
implements DimensionCombinationSetter {
    private static final long serialVersionUID = -1999680641028058154L;
    private final DimensionTable dimensionTable;
    private int[] idxArr = new int[]{-1, -1, -1, -1};

    public DimensionCombinationTableImpl(DimensionTable dimensionTable) {
        this.dimensionTable = dimensionTable;
    }

    @Override
    public void setValue(FixedDimensionValue fixedDimensionValue) {
        this.setValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), fixedDimensionValue.getValue());
    }

    @Override
    public void setValue(String name, String entityID, Object value) {
        int valueIndex;
        int dimIndex = this.dimensionTable.getIndexByName(name);
        if (dimIndex == -1) {
            dimIndex = this.dimensionTable.addDimensionValue(new DimensionDefine(name, entityID));
        }
        if (this.idxArr.length < this.dimensionTable.getDimSize()) {
            int oldLength = this.idxArr.length;
            this.idxArr = Arrays.copyOf(this.idxArr, this.idxArr.length * 2);
            for (int i = oldLength; i < this.idxArr.length; ++i) {
                this.idxArr[i] = -1;
            }
        }
        this.idxArr[dimIndex] = valueIndex = this.dimensionTable.addValue(dimIndex, value);
    }

    @Override
    public void setDWValue(FixedDimensionValue fixedDimensionValue) {
        int dwIdx = this.dimensionTable.getDwIdx();
        if (dwIdx == -1) {
            this.dimensionTable.addDwDimensionValue(fixedDimensionValue);
        } else {
            DimensionValue dwDimension = this.dimensionTable.getDimensionDefine(this.dimensionTable.getDwIdx());
            if (!dwDimension.getName().equals(fixedDimensionValue.getName()) || !dwDimension.getEntityID().equals(fixedDimensionValue.getEntityID())) {
                throw new DimensionBuildException("ReduplicateMainDimension");
            }
        }
        this.setValue(fixedDimensionValue);
    }

    @Override
    public void setDWValue(String name, String entityID, Object value) {
        this.setDWValue(new FixedDimensionValue(name, entityID, value));
    }

    @Override
    public Object getValue(String name) {
        int dimIndex = this.dimensionTable.getIndexByName(name);
        if (dimIndex == -1) {
            return null;
        }
        return this.dimensionTable.getValue(dimIndex, this.idxArr[dimIndex]);
    }

    @Override
    public boolean hasValue(String name) {
        int dimIndex = this.dimensionTable.getIndexByName(name);
        return dimIndex > -1;
    }

    @Override
    public void assignFrom(DimensionCombination source) {
        if (source instanceof DimensionCombinationTableImpl) {
            DimensionCombinationTableImpl obj = (DimensionCombinationTableImpl)source;
            if (this.dimensionTable != obj.dimensionTable) {
                throw new DimensionBuildException("ErrorDimensionTable");
            }
            this.idxArr = Arrays.copyOf(obj.idxArr, obj.idxArr.length);
        }
    }

    @Override
    public DimensionValueSet toDimensionValueSet() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        int dwIdx = this.dimensionTable.getDwIdx();
        if (dwIdx > -1) {
            DimensionValue dimensionValue = this.dimensionTable.getDimensionDefine(dwIdx);
            dimensionValueSet.setValue(dimensionValue.getName(), this.dimensionTable.getValue(dwIdx, this.idxArr[dwIdx]));
        }
        for (int i = 0; i < this.dimensionTable.getDimSize(); ++i) {
            if (i == dwIdx) continue;
            dimensionValueSet.setValue(this.dimensionTable.getDimensionDefines()[i].getName(), this.dimensionTable.getValue(i, this.idxArr[i]));
        }
        return dimensionValueSet;
    }

    @Override
    public FixedDimensionValue getFixedDimensionValue(String name) {
        int dimIndex = this.dimensionTable.getIndexByName(name);
        return this.dimensionTable.getFixedDimensionValue(dimIndex, this.idxArr[dimIndex]);
    }

    @Override
    public FixedDimensionValue getDWDimensionValue() {
        int dwIdx = this.dimensionTable.getDwIdx();
        if (dwIdx > -1) {
            return this.dimensionTable.getFixedDimensionValue(dwIdx, this.idxArr[dwIdx]);
        }
        return this.getFixedDimensionValue("MD_ORG");
    }

    @Override
    public FixedDimensionValue getPeriodDimensionValue() {
        return this.getFixedDimensionValue("DATATIME");
    }

    @Override
    public int getSize() {
        return this.dimensionTable.getDimSize();
    }

    @Override
    public Collection<String> getNames() {
        return this.dimensionTable.getNames();
    }

    @Override
    public Iterator<FixedDimensionValue> iterator() {
        return new DimensionIterator();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DimensionCombination)) {
            return false;
        }
        DimensionCombination that = (DimensionCombination)o;
        if (that.getSize() != this.getSize()) {
            return false;
        }
        for (int i = 0; i < this.getSize(); ++i) {
            Object thisValue = this.getValue(this.dimensionTable.getDimensionDefine(i).getName());
            Object thatValue = that.getValue(this.dimensionTable.getDimensionDefine(i).getName());
            if (thisValue == null) {
                if (thatValue == null) continue;
                return false;
            }
            if (thatValue == null) {
                return false;
            }
            if (thisValue.equals(thatValue)) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.dimensionTable.getHashCode(this.idxArr);
    }

    @Override
    public void sortDimension() {
        this.dimensionTable.sortDimension();
    }

    @Override
    public Collection<FixedDimensionValue> getDimensionValues() {
        ArrayList<FixedDimensionValue> dimensionValues = new ArrayList<FixedDimensionValue>();
        for (int i = 0; i < this.dimensionTable.getDimSize(); ++i) {
            dimensionValues.add(this.dimensionTable.getFixedDimensionValue(i, this.idxArr[i]));
        }
        return dimensionValues;
    }

    class DimensionIterator
    implements Iterator<FixedDimensionValue> {
        FixedDimensionValue current = null;
        int nextIndex = -1;

        protected DimensionIterator() {
            this.next();
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public FixedDimensionValue next() {
            FixedDimensionValue result = this.current;
            this.current = ++this.nextIndex >= DimensionCombinationTableImpl.this.dimensionTable.getDimSize() ? null : DimensionCombinationTableImpl.this.dimensionTable.getFixedDimensionValue(this.nextIndex, DimensionCombinationTableImpl.this.idxArr[this.nextIndex]);
            return result;
        }
    }
}

