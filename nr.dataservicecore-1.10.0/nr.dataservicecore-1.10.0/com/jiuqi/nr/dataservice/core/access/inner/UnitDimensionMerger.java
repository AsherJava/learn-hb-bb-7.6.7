/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 */
package com.jiuqi.nr.dataservice.core.access.inner;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.dataservice.core.access.inner.DimResources;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnitDimensionMerger {
    private final List<String> allFormKeys;
    private final Map<String, Integer> formIndex;
    private final String mergeDimName;
    private List<BaseDimFormBit> dimFormBits = new ArrayList<BaseDimFormBit>();
    private final ENameSet names;

    public UnitDimensionMerger(List<String> allFormKeys, ENameSet names, String mergeDimName) {
        this.allFormKeys = allFormKeys;
        this.mergeDimName = mergeDimName;
        this.names = names;
        this.formIndex = new HashMap<String, Integer>();
        for (int i = 0; i < allFormKeys.size(); ++i) {
            this.formIndex.put(allFormKeys.get(i), i);
        }
    }

    public void addUnitDimension(DimensionValueSet dimensionValueSet, List<String> formKeys) {
        if (dimensionValueSet == null || formKeys == null || formKeys.isEmpty()) {
            return;
        }
        if (this.allFormKeys.size() <= 64) {
            this.dimFormBits.add(new DimFormBit(dimensionValueSet, formKeys));
        } else {
            this.dimFormBits.add(new DimFormBitSet(dimensionValueSet, formKeys));
        }
    }

    public List<DimResources> getDimForms() {
        List<DimResources> dimForms = this.mergeDimensionSets(this.dimFormBits);
        this.dimFormBits = new ArrayList<BaseDimFormBit>();
        return dimForms;
    }

    private List<DimResources> mergeDimensionSets(List<? extends BaseDimFormBit> originalSets) {
        Map<String, List<BaseDimFormBit>> groupedByNonUnit = originalSets.stream().collect(Collectors.groupingBy(ds -> {
            StringBuilder otherValueKey = new StringBuilder();
            for (String otherValue : ds.getOtherValues()) {
                otherValueKey.append(otherValue).append(";");
            }
            return otherValueKey.toString();
        }));
        ArrayList<DimResources> mergedResults = new ArrayList<DimResources>();
        for (Map.Entry<String, List<BaseDimFormBit>> otherValue2BitEntry : groupedByNonUnit.entrySet()) {
            List<BaseDimFormBit> otherDimSameBits = otherValue2BitEntry.getValue();
            HashMap<Object, List> mergeKeyToBits = new HashMap<Object, List>();
            for (BaseDimFormBit baseDimFormBit : otherDimSameBits) {
                mergeKeyToBits.computeIfAbsent(baseDimFormBit.getFormMergeKey(), k -> new ArrayList()).add(baseDimFormBit);
            }
            for (Map.Entry entry : mergeKeyToBits.entrySet()) {
                DimFormMerge merged = new DimFormMerge();
                List baseDimFormBits = (List)entry.getValue();
                BaseDimFormBit template = (BaseDimFormBit)baseDimFormBits.get(0);
                ArrayList<String> mergeValues = new ArrayList<String>();
                for (BaseDimFormBit baseDimFormBit : baseDimFormBits) {
                    mergeValues.add(baseDimFormBit.getMergeValue());
                }
                merged.setMergeValues(mergeValues);
                merged.setOtherValues(template.getOtherValues());
                merged.setForms(template.getForms());
                mergedResults.add(merged);
            }
        }
        return mergedResults;
    }

    private static class DimFormMerge
    implements DimResources {
        private List<String> forms;
        private List<String> mergeValues;
        private List<String> otherValues;

        private DimFormMerge() {
        }

        @Override
        public List<String> getForms() {
            return this.forms;
        }

        public void setForms(List<String> forms) {
            this.forms = forms;
        }

        @Override
        public List<String> getMergeValues() {
            return this.mergeValues;
        }

        public void setMergeValues(List<String> mergeValues) {
            this.mergeValues = mergeValues;
        }

        @Override
        public List<String> getOtherValues() {
            return this.otherValues;
        }

        public void setOtherValues(List<String> otherValues) {
            this.otherValues = otherValues;
        }
    }

    private class DimFormBitSet
    extends BaseDimFormBit {
        private BitSet formBits;

        public DimFormBitSet(DimensionValueSet masterKeys, List<String> forms) {
            super(masterKeys);
            this.formBits = this.convertToBitmask(forms);
        }

        private BitSet convertToBitmask(List<String> forms) {
            BitSet bits = new BitSet(UnitDimensionMerger.this.allFormKeys.size());
            for (String form : forms) {
                int index = (Integer)UnitDimensionMerger.this.formIndex.get(form);
                if (index == -1) continue;
                bits.set(index);
            }
            return bits;
        }

        @Override
        public List<String> getForms() {
            ArrayList<String> forms = new ArrayList<String>();
            int i = this.formBits.nextSetBit(0);
            while (i >= 0) {
                forms.add((String)UnitDimensionMerger.this.allFormKeys.get(i));
                i = this.formBits.nextSetBit(i + 1);
            }
            return forms;
        }

        @Override
        public Object getFormMergeKey() {
            return this.getFormBits();
        }

        public BitSet getFormBits() {
            return this.formBits;
        }

        public void setFormBits(BitSet formBits) {
            this.formBits = formBits;
        }
    }

    private class DimFormBit
    extends BaseDimFormBit {
        private long formBits;

        public DimFormBit() {
        }

        public DimFormBit(DimensionValueSet masterKeys, List<String> forms) {
            super(masterKeys);
            this.formBits = this.convertToBitmask(forms);
        }

        @Override
        public Object getFormMergeKey() {
            return this.getFormBits();
        }

        private long convertToBitmask(List<String> forms) {
            long mask = 0L;
            for (String form : forms) {
                int index = (Integer)UnitDimensionMerger.this.formIndex.get(form);
                if (index == -1) continue;
                mask |= 1L << index;
            }
            return mask;
        }

        @Override
        public List<String> getForms() {
            ArrayList<String> forms = new ArrayList<String>();
            for (int i = 0; i < UnitDimensionMerger.this.allFormKeys.size(); ++i) {
                if ((this.formBits & 1L << i) == 0L) continue;
                forms.add((String)UnitDimensionMerger.this.allFormKeys.get(i));
            }
            return forms;
        }

        public long getFormBits() {
            return this.formBits;
        }

        public void setFormBits(long formBits) {
            this.formBits = formBits;
        }
    }

    private abstract class BaseDimFormBit {
        protected String mergeValue;
        protected List<String> otherValues;

        public String getMergeValue() {
            return this.mergeValue;
        }

        public void setMergeValue(String mergeValue) {
            this.mergeValue = mergeValue;
        }

        public List<String> getOtherValues() {
            return this.otherValues;
        }

        public void setOtherValues(List<String> otherValues) {
            this.otherValues = otherValues;
        }

        public BaseDimFormBit() {
        }

        public BaseDimFormBit(DimensionValueSet masterKeys) {
            this.otherValues = new ArrayList<String>(UnitDimensionMerger.this.names.size() - 1);
            for (int i = 0; i < UnitDimensionMerger.this.names.size(); ++i) {
                String dimName = UnitDimensionMerger.this.names.get(i);
                Object valueObj = masterKeys.getValue(dimName);
                if (valueObj == null) {
                    throw new IllegalArgumentException("\u7ef4\u5ea6" + dimName + "\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a");
                }
                String value = valueObj.toString();
                if (UnitDimensionMerger.this.mergeDimName.equals(dimName)) {
                    this.mergeValue = value;
                    continue;
                }
                this.otherValues.add(value);
            }
        }

        public abstract Object getFormMergeKey();

        public abstract List<String> getForms();
    }
}

