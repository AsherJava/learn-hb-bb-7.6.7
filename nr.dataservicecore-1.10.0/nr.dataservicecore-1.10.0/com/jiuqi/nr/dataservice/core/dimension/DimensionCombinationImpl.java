/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationSetter;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DimensionCombinationImpl
implements DimensionCombinationSetter {
    private static final long serialVersionUID = 4305793424295790805L;
    private String[] names = new String[8];
    private Object[] values = new Object[8];
    private String[] entityIds = new String[8];
    private int dwIdx = -1;
    private int count = 0;
    private transient boolean sorted = false;

    public DimensionCombinationImpl() {
    }

    public DimensionCombinationImpl(Collection<FixedDimensionValue> fixedDimensionValues) {
        if (fixedDimensionValues != null && fixedDimensionValues.size() > 0) {
            fixedDimensionValues.forEach(this::setValue);
        }
    }

    public DimensionCombinationImpl(DimensionValueSet dimensionValueSet) {
        IntStream.range(0, dimensionValueSet.size()).mapToObj(arg_0 -> ((DimensionValueSet)dimensionValueSet).getName(arg_0)).forEach(name -> this.setValue((String)name, "", dimensionValueSet.getValue(name)));
    }

    @Override
    public void setValue(FixedDimensionValue fixedDimensionValue) {
        this.setValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), fixedDimensionValue.getValue());
    }

    @Override
    public void setDWValue(FixedDimensionValue fixedDimensionValue) {
        this.setDWValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), fixedDimensionValue.getValue());
    }

    @Override
    public void setDWValue(String name, String entityID, Object value) {
        this.dwIdx = this.count;
        this.setValue(name, entityID, value);
    }

    @Override
    public void setValue(String name, String entityID, Object value) {
        if (this.count == this.names.length) {
            this.names = Arrays.copyOf(this.names, this.names.length * 2);
            this.values = Arrays.copyOf(this.values, this.values.length * 2);
            this.entityIds = Arrays.copyOf(this.entityIds, this.entityIds.length * 2);
        }
        this.names[this.count] = name;
        this.values[this.count] = value;
        this.entityIds[this.count++] = entityID;
        this.sorted = false;
    }

    public void clearValue(String name) {
        IntStream.range(0, this.names.length).filter(i -> name.equalsIgnoreCase(this.names[i])).forEach((int i) -> {
            this.values[i] = null;
        });
    }

    @Override
    public Object getValue(String name) {
        for (int i = 0; i < this.count; ++i) {
            if (!name.equalsIgnoreCase(this.names[i])) continue;
            return this.values[i];
        }
        return null;
    }

    public void clearAll() {
        this.names = new String[8];
        this.values = new String[8];
        this.entityIds = new String[8];
        this.count = 0;
        this.dwIdx = -1;
        this.sorted = false;
    }

    @Override
    public boolean hasValue(String name) {
        for (int i = 0; i < this.names.length; ++i) {
            if (!name.equalsIgnoreCase(this.names[i])) continue;
            return this.values[i] != null;
        }
        return false;
    }

    @Override
    public void assignFrom(DimensionCombination source) {
        if (source instanceof DimensionCombinationImpl) {
            DimensionCombinationImpl obj = (DimensionCombinationImpl)source;
            this.names = Arrays.copyOf(obj.names, obj.names.length);
            this.values = Arrays.copyOf(obj.values, obj.values.length);
            this.entityIds = Arrays.copyOf(obj.entityIds, obj.entityIds.length);
            this.count = obj.count;
            this.dwIdx = obj.dwIdx;
        } else {
            this.count = source.getSize();
            this.names = source.getNames().toArray(new String[1]);
            FixedDimensionValue dwDimensionValue = source.getDWDimensionValue();
            for (int i = 0; i < this.count; ++i) {
                FixedDimensionValue fixedDimensionValue = source.getFixedDimensionValue(this.names[i]);
                if (dwDimensionValue != null && fixedDimensionValue.getName().equals(dwDimensionValue.getName())) {
                    this.dwIdx = i;
                }
                this.values[i] = fixedDimensionValue.getValue();
                this.entityIds[i] = fixedDimensionValue.getEntityID();
            }
        }
    }

    @Override
    public DimensionValueSet toDimensionValueSet() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (this.dwIdx > -1 && this.names[this.dwIdx] != null) {
            dimensionValueSet.setValue(this.names[this.dwIdx], this.values[this.dwIdx]);
        }
        for (int i = 0; i < this.names.length && this.names[i] != null; ++i) {
            if (i == this.dwIdx) continue;
            dimensionValueSet.setValue(this.names[i], this.values[i]);
        }
        return dimensionValueSet;
    }

    @Override
    public FixedDimensionValue getFixedDimensionValue(String name) {
        for (int i = 0; i < this.count; ++i) {
            if (!name.equalsIgnoreCase(this.names[i])) continue;
            return new FixedDimensionValue(name, this.entityIds[i], this.values[i]);
        }
        return null;
    }

    @Override
    public FixedDimensionValue getDWDimensionValue() {
        if (this.dwIdx > -1) {
            return new FixedDimensionValue(this.names[this.dwIdx], this.entityIds[this.dwIdx], this.values[this.dwIdx]);
        }
        for (int i = 0; i < this.names.length; ++i) {
            if (!"MD_ORG".equalsIgnoreCase(this.names[i])) continue;
            return new FixedDimensionValue(this.names[i], this.entityIds[i], this.values[i]);
        }
        return null;
    }

    @Override
    public FixedDimensionValue getPeriodDimensionValue() {
        return this.getFixedDimensionValue("DATATIME");
    }

    @Override
    public Collection<String> getNames() {
        if (this.count == 0) {
            return new ArrayList<String>();
        }
        return Arrays.stream(this.names).limit(this.count).collect(Collectors.toList());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.count; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(this.names[i]).append("=").append(this.values[i]);
        }
        return sb.toString();
    }

    @Override
    public int getSize() {
        return this.count;
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
            Object thisValue = this.getValue(this.names[i]);
            Object thatValue = that.getValue(this.names[i]);
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
        this.sortDimension();
        return Arrays.hashCode(this.values);
    }

    @Override
    public Iterator<FixedDimensionValue> iterator() {
        return new DimensionIterator();
    }

    @Override
    public void sortDimension() {
        if (this.sorted) {
            return;
        }
        List sortedList = Arrays.stream(this.names).filter(Objects::nonNull).sorted().collect(Collectors.toList());
        String[] newNames = new String[this.count];
        Object[] newValues = new Object[this.count];
        String[] newEntityIds = new String[this.count];
        for (int i = 0; i < this.count; ++i) {
            int index = sortedList.indexOf(this.names[i]);
            if (index == this.dwIdx) {
                this.dwIdx = i;
            }
            newNames[index] = this.names[i];
            newValues[index] = this.values[i];
            newEntityIds[index] = this.entityIds[i];
        }
        this.names = newNames;
        this.values = newValues;
        this.entityIds = newEntityIds;
        this.sorted = true;
    }

    @Override
    public Collection<FixedDimensionValue> getDimensionValues() {
        ArrayList<FixedDimensionValue> dimensionValues = new ArrayList<FixedDimensionValue>();
        for (int i = 0; i < this.count; ++i) {
            dimensionValues.add(new FixedDimensionValue(this.names[i], this.entityIds[i], this.values[i]));
        }
        return dimensionValues;
    }

    class DimensionIterator
    implements Iterator<FixedDimensionValue> {
        FixedDimensionValue current = null;
        int nextIndex = 0;

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
            this.current = this.nextIndex >= DimensionCombinationImpl.this.count ? null : new FixedDimensionValue(DimensionCombinationImpl.this.names[this.nextIndex], DimensionCombinationImpl.this.entityIds[this.nextIndex], DimensionCombinationImpl.this.values[this.nextIndex]);
            ++this.nextIndex;
            return result;
        }
    }
}

