/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.IDSFilter
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.util.ArrayKey
 */
package com.jiuqi.bi.quickreport.engine.context.cache;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSFilter;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.MappingFilterDescriptor;
import com.jiuqi.bi.util.ArrayKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

abstract class DSFilterAnalyzer {
    private String dsName;
    private List<IFilterDescriptor> filters;
    private List<DataSetMapping> dsMappings;

    public DSFilterAnalyzer(String dsName) {
        this.dsName = dsName;
        this.filters = new ArrayList<IFilterDescriptor>();
        this.dsMappings = new ArrayList<DataSetMapping>();
    }

    public List<IFilterDescriptor> getFilters() {
        return this.filters;
    }

    public List<FilterItem> analyse() throws ReportContextException {
        List<FilterItem> dsFilters = this.scanFilters();
        this.buildJoins(dsFilters);
        return dsFilters;
    }

    public IDSFilter createFilter(BIDataSet dataSet) throws ReportContextException {
        if (this.dsMappings.isEmpty()) {
            return null;
        }
        ArrayList<DataSetMapping> filterMappings = new ArrayList<DataSetMapping>();
        for (DataSetMapping dsMapping : this.dsMappings) {
            if (dsMapping.keyValues.isEmpty()) {
                return null;
            }
            if (dsMapping.mappings.size() <= 1) continue;
            dsMapping.init(dataSet);
            filterMappings.add(dsMapping);
        }
        return filterMappings.isEmpty() ? null : new DataSetFilter(filterMappings);
    }

    private List<FilterItem> scanFilters() throws ReportContextException {
        ArrayList<FilterItem> dsFilters = new ArrayList<FilterItem>();
        Iterator<IFilterDescriptor> i = this.filters.iterator();
        while (i.hasNext()) {
            IFilterDescriptor filter = i.next();
            if (!this.dsName.equalsIgnoreCase(filter.getDataSetName())) continue;
            if (filter instanceof MappingFilterDescriptor) {
                this.addMapping((MappingFilterDescriptor)filter);
            } else {
                dsFilters.add(filter.toFilter());
            }
            i.remove();
        }
        return dsFilters;
    }

    private void addMapping(MappingFilterDescriptor filter) throws ReportContextException {
        DSField mappedField;
        DataSetMapping dsMapping = this.findMapping(filter.getDataSetName(), filter.getMappingField().dataSetName);
        if (dsMapping == null) {
            dsMapping = new DataSetMapping(filter.getDataSetName(), filter.getMappingField().dataSetName);
            this.dsMappings.add(dsMapping);
        }
        if ((mappedField = dsMapping.mappings.get(filter.getField())) == null) {
            dsMapping.mappings.put(filter.getField(), filter.getMappingField().field);
        } else if (mappedField != filter.getMappingField().field) {
            throw new ReportContextException("\u6570\u636e\u96c6" + dsMapping.dsName + "\u548c" + dsMapping.mappedDSName + "\u5173\u8054\u5173\u7cfb\u5b58\u5728\u51b2\u7a81\uff0c\u8bf7\u68c0\u67e5\u62a5\u8868\u8bbe\u7f6e\u3002");
        }
    }

    private DataSetMapping findMapping(String dsName, String mappingDSName) {
        for (DataSetMapping dsMapping : this.dsMappings) {
            if (!dsMapping.dsName.equalsIgnoreCase(dsName) || !dsMapping.mappedDSName.equalsIgnoreCase(mappingDSName)) continue;
            return dsMapping;
        }
        return null;
    }

    private void buildJoins(List<FilterItem> dsFilters) throws ReportContextException {
        if (this.dsMappings.isEmpty()) {
            return;
        }
        for (DataSetMapping dsMapping : this.dsMappings) {
            FilterItem dsFilter = this.buildJoin(dsMapping, this.filters);
            dsFilters.add(dsFilter);
        }
    }

    private FilterItem buildJoin(DataSetMapping dsMapping, List<IFilterDescriptor> filters) throws ReportContextException {
        BIDataSet mappedDS = this.openDataSet(dsMapping.mappedDSName, new ArrayList<IFilterDescriptor>(filters));
        this.readKeyValues(dsMapping, mappedDS);
        return dsMapping.toDSFilter();
    }

    protected abstract BIDataSet openDataSet(String var1, List<IFilterDescriptor> var2) throws ReportContextException;

    private void readKeyValues(DataSetMapping dsMapping, BIDataSet dataSet) throws ReportContextException {
        if (dataSet.getRecordCount() == 0) {
            return;
        }
        int[] keyIndexes = dsMapping.mappings.values().stream().mapToInt(f -> {
            int index = dataSet.getMetadata().indexOf(f.getName());
            if (index < 0) {
                throw new QuickReportError("\u5b9a\u4f4d\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + f.getName());
            }
            return index;
        }).toArray();
        for (BIDataRow row : dataSet) {
            Object[] buffer = new Object[keyIndexes.length];
            for (int i = 0; i < keyIndexes.length; ++i) {
                Object value;
                buffer[i] = value = row.getValue(keyIndexes[i]);
            }
            ArrayKey key = new ArrayKey(buffer);
            dsMapping.keyValues.add(key);
        }
    }

    private static final class DataSetFilter
    implements IDSFilter {
        private List<DataSetMapping> mappings;

        public DataSetFilter(List<DataSetMapping> mappings) {
            this.mappings = mappings;
        }

        public boolean judge(DataRow row) throws BIDataSetException {
            for (DataSetMapping mapping : this.mappings) {
                if (mapping.filter(row)) continue;
                return false;
            }
            return true;
        }
    }

    private static class DataSetMapping {
        public final String dsName;
        public final String mappedDSName;
        public final Map<DSField, DSField> mappings;
        public final Set<ArrayKey> keyValues;
        private int[] dsIndexes;
        private Object[] buffer;

        public DataSetMapping(String dsName, String mappedDSName) {
            this.dsName = dsName;
            this.mappedDSName = mappedDSName;
            this.mappings = new TreeMap<DSField, DSField>((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
            this.keyValues = new TreeSet<ArrayKey>();
        }

        public String toString() {
            StringBuilder buffer = new StringBuilder();
            buffer.append(this.dsName).append(" JOIN ").append(this.mappedDSName).append(" ON ");
            Iterator<Map.Entry<DSField, DSField>> i = this.mappings.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<DSField, DSField> entry = i.next();
                buffer.append(entry.getKey().getName()).append(" = ").append(entry.getValue().getName());
                if (!i.hasNext()) continue;
                buffer.append(" AND ");
            }
            return buffer.toString();
        }

        public FilterItem toDSFilter() {
            DSField field = this.mappings.keySet().iterator().next();
            if (this.keyValues.isEmpty()) {
                return new FilterItem(field.getName(), "FALSE");
            }
            HashSet<Object> values = new HashSet<Object>(this.keyValues.size());
            for (ArrayKey key : this.keyValues) {
                values.add(key.get(0));
            }
            ArrayList keys = new ArrayList(values);
            return new FilterItem(field.getName(), keys);
        }

        public void init(BIDataSet dataSet) throws ReportContextException {
            this.dsIndexes = new int[this.mappings.size()];
            this.buffer = new Object[this.mappings.size()];
            int i = 0;
            for (DSField field : this.mappings.keySet()) {
                this.dsIndexes[i] = dataSet.getMetadata().indexOf(field.getName());
                if (this.dsIndexes[i] < 0) {
                    throw new ReportContextException("\u5728\u6570\u636e\u96c6" + this.dsName + "\u4e2d\u5b9a\u4f4d\u5b57\u6bb5" + field.getName() + "\u4e0d\u5b58\u5728\u3002");
                }
                ++i;
            }
        }

        public boolean filter(DataRow row) {
            for (int i = 0; i < this.dsIndexes.length; ++i) {
                Object value;
                this.buffer[i] = value = row.getValue(this.dsIndexes[i]);
            }
            ArrayKey key = new ArrayKey(this.buffer);
            return this.keyValues.contains(key);
        }
    }
}

