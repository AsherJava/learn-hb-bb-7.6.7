/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.ArrayDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.DimensionTable;
import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DynamicDimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

class DimensionCollectionImpl
implements DynamicDimensionCollection,
Serializable {
    private static final long serialVersionUID = 1257584441412563468L;
    private final List<FixedDimensionValue> fixDimensionList = new ArrayList<FixedDimensionValue>(8);
    private final List<VariableDimensionValue> variableDimensionList = new ArrayList<VariableDimensionValue>();
    private final List<ArrayDimensionValue> arrayDimensionList = new ArrayList<ArrayDimensionValue>();
    private int[] dwIdxArr = new int[]{-1, -1, -1};
    private DimensionContext context;

    DimensionCollectionImpl() {
    }

    public void setValue(String name, Object ... value) {
        if (value.length == 1) {
            if (value[0] instanceof List) {
                List list = (List)value[0];
                ArrayDimensionValue arrayDimensionValue = new ArrayDimensionValue(name, list.toArray());
                this.arrayDimensionList.add(arrayDimensionValue);
            } else {
                FixedDimensionValue dimensionVale = new FixedDimensionValue(name, value[0]);
                this.fixDimensionList.add(dimensionVale);
            }
        } else {
            ArrayDimensionValue arrayDimensionValue = new ArrayDimensionValue(name, value);
            this.arrayDimensionList.add(arrayDimensionValue);
        }
    }

    public void setEntityValue(String name, String entityID, Object ... value) {
        this.setEntityValue(false, name, entityID, value);
    }

    private void setEntityValue(boolean isDW, String name, String entityID, Object ... value) {
        if (value.length == 1) {
            if (value[0] instanceof List) {
                List list = (List)value[0];
                ArrayDimensionValue arrayDimensionValue = new ArrayDimensionValue(name, entityID, list.toArray());
                this.arrayDimensionList.add(arrayDimensionValue);
                if (isDW) {
                    this.setArrDW();
                }
            } else {
                FixedDimensionValue dimensionVale = new FixedDimensionValue(name, entityID, value[0]);
                this.fixDimensionList.add(dimensionVale);
                if (isDW) {
                    this.setFixDW();
                }
            }
        } else {
            ArrayDimensionValue arrayDimensionValue = new ArrayDimensionValue(name, entityID, value);
            this.arrayDimensionList.add(arrayDimensionValue);
            if (isDW) {
                this.setArrDW();
            }
        }
    }

    public void addVariableDimension(String name, String entityId, VariableDimensionValueProvider provider) {
        VariableDimensionValue dimensionvalue = new VariableDimensionValue(name, entityId, provider);
        this.variableDimensionList.add(dimensionvalue);
    }

    public void addVariableDW(String name, String entityId, VariableDimensionValueProvider provider) {
        VariableDimensionValue dimensionvalue = new VariableDimensionValue(name, entityId, provider);
        this.variableDimensionList.add(0, dimensionvalue);
        this.setVarDW();
    }

    @Override
    public List<DimensionCombination> getDimensionCombinations() {
        DimensionValue v;
        int i;
        this.initIdxArr();
        List<DimensionCombination> dimensionCombinations = new ArrayList<DimensionCombination>();
        DimensionTable dimensionTable = new DimensionTable();
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionTable);
        for (i = 0; i < this.fixDimensionList.size(); ++i) {
            if (this.dwIdxArr[0] == i) {
                builder.setDWValue(this.fixDimensionList.get(i));
                continue;
            }
            builder.setValue(this.fixDimensionList.get(i));
        }
        dimensionCombinations.add(builder.getCombination());
        if (this.arrayDimensionList.size() > 0) {
            for (i = 0; i < this.arrayDimensionList.size(); ++i) {
                v = this.arrayDimensionList.get(i);
                dimensionCombinations = this.splitDimension(dimensionTable, dimensionCombinations, (ArrayDimensionValue)v, this.dwIdxArr[2] == i);
            }
        }
        if (this.variableDimensionList.size() > 0) {
            for (i = 0; i < this.variableDimensionList.size(); ++i) {
                v = this.variableDimensionList.get(i);
                dimensionCombinations = this.splitDimension(dimensionTable, dimensionCombinations, (VariableDimensionValue)v, this.dwIdxArr[1] == i);
            }
        }
        return dimensionCombinations;
    }

    @Override
    public List<VariableDimensionValue> getVariableDimensionList() {
        return Collections.unmodifiableList(this.variableDimensionList);
    }

    @Override
    public List<FixedDimensionValue> getFixDimensionList() {
        return Collections.unmodifiableList(this.fixDimensionList);
    }

    @Override
    public List<ArrayDimensionValue> getArrayDimensionList() {
        return Collections.unmodifiableList(this.arrayDimensionList);
    }

    @Override
    public DimensionValue getDimensionValue(String dimensionName) {
        Optional<FixedDimensionValue> fixedDimensionValueOptional = this.getFixDimensionList().stream().filter(f -> f.getName().equalsIgnoreCase(dimensionName)).findFirst();
        if (fixedDimensionValueOptional.isPresent()) {
            return fixedDimensionValueOptional.get();
        }
        Optional<ArrayDimensionValue> arrayDimensionValueOptional = this.getArrayDimensionList().stream().filter(a -> a.getName().equalsIgnoreCase(dimensionName)).findFirst();
        if (arrayDimensionValueOptional.isPresent()) {
            return arrayDimensionValueOptional.get();
        }
        Optional<VariableDimensionValue> variableDimensionValueOptional = this.getVariableDimensionList().stream().filter(v -> v.getName().equalsIgnoreCase(dimensionName)).findFirst();
        if (variableDimensionValueOptional.isPresent()) {
            return variableDimensionValueOptional.get();
        }
        return null;
    }

    public void setDWValue(String name, String entityID, Object[] value) {
        this.setEntityValue(true, name, entityID, value);
    }

    private List<DimensionCombination> splitDimension(DimensionTable dimensionTable, List<DimensionCombination> dimensionCombinations, ArrayDimensionValue arrayDimensionValue, boolean isDW) {
        if (arrayDimensionValue == null) {
            return dimensionCombinations;
        }
        ArrayList<DimensionCombination> results = new ArrayList<DimensionCombination>();
        Object[] dimensionArr = arrayDimensionValue.getValue();
        if (dimensionArr == null || dimensionArr.length == 0) {
            return dimensionCombinations;
        }
        dimensionCombinations.forEach(d -> Arrays.stream(dimensionArr).forEach(v -> {
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionTable);
            builder.assignFrom((DimensionCombination)d);
            if (isDW) {
                builder.setDWValue(new FixedDimensionValue(arrayDimensionValue.getName(), arrayDimensionValue.getEntityID(), v));
            } else {
                builder.setValue(new FixedDimensionValue(arrayDimensionValue.getName(), arrayDimensionValue.getEntityID(), v));
            }
            results.add(builder.getCombination());
        }));
        return results;
    }

    private List<DimensionCombination> splitDimension(DimensionTable dimensionTable, List<DimensionCombination> dimensionCombinations, VariableDimensionValue variableDimensionValue, boolean isDW) {
        if (variableDimensionValue == null) {
            return dimensionCombinations;
        }
        ArrayList<DimensionCombination> results = new ArrayList<DimensionCombination>();
        dimensionCombinations.forEach(d -> {
            List<String> values = variableDimensionValue.getProvider().getValues(this.context, variableDimensionValue, (DimensionCombination)d);
            if (values != null && values.size() > 0) {
                values.forEach(v -> {
                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionTable);
                    builder.assignFrom((DimensionCombination)d);
                    if (isDW) {
                        builder.setDWValue(new FixedDimensionValue(variableDimensionValue.getName(), variableDimensionValue.getEntityID(), v));
                    } else {
                        builder.setValue(new FixedDimensionValue(variableDimensionValue.getName(), variableDimensionValue.getEntityID(), v));
                    }
                    results.add(builder.getCombination());
                });
            }
        });
        return results;
    }

    @Override
    public DimensionValueSet combineWithoutVarDim() {
        this.initIdxArr();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        this.fixDimensionList.forEach(fixedDimensionValue -> {
            dimensionValueSet.setValue(fixedDimensionValue.getName(), fixedDimensionValue.getValue());
            builder.setValue((FixedDimensionValue)fixedDimensionValue);
        });
        this.arrayDimensionList.stream().forEach(arrayDimensionValue -> dimensionValueSet.setValue(arrayDimensionValue.getName(), Arrays.stream(arrayDimensionValue.getValue()).collect(Collectors.toList())));
        for (int i = 0; i < this.variableDimensionList.size(); ++i) {
            VariableDimensionValue varDimensionValue = this.variableDimensionList.get(i);
            if (varDimensionValue.getProvider().isDynamicByDW(varDimensionValue)) {
                dimensionValueSet.setValue(varDimensionValue.getName(), null);
                continue;
            }
            dimensionValueSet.setValue(varDimensionValue.getName(), varDimensionValue.getProvider().getValues(this.context, varDimensionValue, builder.getCombination()));
        }
        return dimensionValueSet;
    }

    private void setFixDW() {
        this.initIdxArr();
        this.dwIdxArr[0] = this.fixDimensionList.size() - 1;
        this.dwIdxArr[1] = -1;
        this.dwIdxArr[2] = -1;
    }

    private void setVarDW() {
        this.initIdxArr();
        this.dwIdxArr[0] = -1;
        this.dwIdxArr[1] = 0;
        this.dwIdxArr[2] = -1;
    }

    private void setArrDW() {
        this.initIdxArr();
        this.dwIdxArr[0] = -1;
        this.dwIdxArr[1] = -1;
        this.dwIdxArr[2] = this.arrayDimensionList.size() - 1;
    }

    private void initIdxArr() {
        if (this.dwIdxArr == null) {
            this.dwIdxArr = new int[]{-1, -1, -1};
        }
    }

    @Override
    public DimensionValueSet combineDim() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        this.fixDimensionList.forEach(d -> {
            dimensionValueSet.setValue(d.getName(), d.getValue());
            combination.setValue((FixedDimensionValue)d);
        });
        this.arrayDimensionList.forEach(d -> {
            List values = Arrays.stream(d.getValue()).filter(Objects::isNull).collect(Collectors.toList());
            dimensionValueSet.setValue(d.getName(), values.size() == 1 ? values.stream().findFirst().orElse(null) : values);
        });
        this.variableDimensionList.forEach(d -> {
            if (d.getProvider().isDynamicByDW((VariableDimensionValue)d)) {
                Collection listValues;
                Object mergeValue = d.getProvider().getMergeValue();
                if (mergeValue instanceof Collection && (listValues = (Collection)mergeValue).size() == 1) {
                    Object value = listValues.stream().findFirst().orElse(null);
                    dimensionValueSet.setValue(d.getName(), value);
                    return;
                }
                dimensionValueSet.setValue(d.getName(), mergeValue);
            } else {
                List<String> values = d.getProvider().getValues(this.context, (VariableDimensionValue)d, combination);
                if (values != null && values.size() == 1) {
                    Object value = values.stream().findFirst().orElse(null);
                    dimensionValueSet.setValue(d.getName(), value);
                    return;
                }
                dimensionValueSet.setValue(d.getName(), values);
            }
        });
        return dimensionValueSet;
    }

    @Override
    public Iterator<DimensionValueSet> iterator() {
        return new DimensionIterator();
    }

    DimensionValue getDWDimension() {
        int dwDimensionType;
        this.initIdxArr();
        for (dwDimensionType = 0; dwDimensionType < this.dwIdxArr.length && this.dwIdxArr[dwDimensionType] <= -1; ++dwDimensionType) {
        }
        switch (dwDimensionType) {
            case 0: {
                return this.fixDimensionList.get(this.dwIdxArr[0]);
            }
            case 1: {
                return this.variableDimensionList.get(this.dwIdxArr[1]);
            }
            case 2: {
                return this.arrayDimensionList.get(this.dwIdxArr[2]);
            }
        }
        return null;
    }

    public void setContext(DimensionContext context) {
        this.context = context;
    }

    public String toString() {
        return "DimensionCollectionImpl{fixDimensionList=" + this.fixDimensionList + ", variableDimensionList=" + this.variableDimensionList + ", arrayDimensionList=" + this.arrayDimensionList + ", dwIdxArr=" + Arrays.toString(this.dwIdxArr) + ", context=" + this.context + '}';
    }

    class DimensionIterator
    implements Iterator<DimensionValueSet> {
        DimensionValueSet current = null;
        int nextIndex = 0;
        List<DimensionCombination> nodes = new ArrayList<DimensionCombination>();

        protected DimensionIterator() {
            this.nodes = DimensionCollectionImpl.this.getDimensionCombinations();
            this.next();
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public DimensionValueSet next() {
            DimensionValueSet result = this.current;
            this.current = this.nextIndex == this.nodes.size() ? null : this.nodes.get(this.nextIndex++).toDimensionValueSet();
            return result;
        }
    }
}

