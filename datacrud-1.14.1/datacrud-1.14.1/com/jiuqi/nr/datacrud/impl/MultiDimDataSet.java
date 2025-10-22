/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MultiDimDataSet
extends RegionDataSet
implements MultiDimensionalDataSet {
    private static final Logger log = LoggerFactory.getLogger(MultiDimDataSet.class);
    private DimensionCollection dimensionCollection;
    private Map<String, IRegionDataSet> dataSetMap;
    private boolean init;
    private Set<DimensionCombination> dims;

    public void setDims(Set<DimensionCombination> dims) {
        this.dims = dims;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public MultiDimDataSet() {
    }

    public MultiDimDataSet(List<? extends IMetaData> metaData, List<IRowData> rows) {
        super(metaData, rows);
    }

    @Override
    public DimensionCollection getMasterDimensionCollection() {
        return this.dimensionCollection;
    }

    @Override
    public IRegionDataSet getRegionDataSet(DimensionCombination dimension) {
        IRegionDataSet set;
        if (log.isDebugEnabled()) {
            log.debug("\u83b7\u53d6\u7ef4\u5ea6\u7ec4\u5408 {} \u6570\u636e\u6620\u5c04\u952e", (Object)dimension);
        }
        if (dimension == null) {
            return new RegionDataSetEmpty(this, null);
        }
        DimensionValueSet rowKeys = dimension.toDimensionValueSet();
        String dimStr = rowKeys.toString();
        if (this.dataSetMap == null) {
            this.dataSetMap = new HashMap<String, IRegionDataSet>();
        }
        if ((set = this.dataSetMap.get(dimStr)) != null) {
            return set;
        }
        this.initRegionDataSet();
        set = this.dataSetMap.get(dimStr);
        if (set == null) {
            return new RegionDataSetEmpty(this, dimension);
        }
        return set;
    }

    @Override
    public IRegionDataSet removeRegionDataSet(DimensionCombination dimension) {
        int rowIndex;
        if (log.isDebugEnabled()) {
            log.debug("\u5254\u9664\u7ef4\u5ea6\u7ec4\u5408 {} \u6570\u636e\u6620\u5c04\u952e", (Object)dimension);
        }
        if (dimension == null) {
            return null;
        }
        if (this.dataSetMap == null) {
            if (log.isDebugEnabled()) {
                log.debug("\u6570\u636e\u96c6\u8fd8\u672a\u521d\u59cb\u5316");
            }
            return null;
        }
        DimensionValueSet rowKeys = dimension.toDimensionValueSet();
        String dimStr = rowKeys.toString();
        IRegionDataSet remove = this.dataSetMap.remove(dimStr);
        if (remove instanceof RegionDataSetInner && (rowIndex = ((RegionDataSetInner)remove).rowIndex) >= 0 && rowIndex < this.rows.size()) {
            if (log.isDebugEnabled()) {
                log.debug("\u6570\u636e\u96c6\u7ed3\u679c\u4e22\u5f03 {}", (Object)dimension);
            }
            this.rows.set(rowIndex, null);
        }
        return null;
    }

    @Override
    public boolean containsRegionDataSet(DimensionCombination dimension) {
        return this.dims.contains(dimension);
    }

    private void initRegionDataSet() {
        if (!this.init) {
            this.init = true;
            for (int i = 0; i < this.rows.size(); ++i) {
                IRowData row = (IRowData)this.rows.get(i);
                DimensionCombination dimension = row.getDimension();
                String dimStr = dimension.toDimensionValueSet().toString();
                IRegionDataSet regionDataSet = this.dataSetMap.get(dimStr);
                if (regionDataSet != null) continue;
                RegionDataSetInner innerSet = new RegionDataSetInner(dimension, i);
                this.dataSetMap.put(dimStr, innerSet);
            }
            if (log.isDebugEnabled()) {
                log.debug("\u521d\u59cb\u5316\u6570\u636e\u6620\u5c04\u952e {}", (Object)this.dataSetMap.keySet());
            }
        }
    }

    private class RegionDataSetInner
    implements IRegionDataSet {
        private DimensionCombination dimensionCombination;
        private int rowIndex = -1;

        public RegionDataSetInner() {
        }

        public RegionDataSetInner(DimensionCombination dimensionCombination, int rowIndex) {
            this.dimensionCombination = dimensionCombination;
            this.rowIndex = rowIndex;
        }

        @Override
        public String getRegionKey() {
            return MultiDimDataSet.this.regionKey;
        }

        @Override
        public DimensionCombination getMasterDimension() {
            return this.dimensionCombination;
        }

        @Override
        public List<IMetaData> getMetaData() {
            return MultiDimDataSet.this.metaData;
        }

        @Override
        public int getPage() {
            return 0;
        }

        @Override
        public int getRowCount() {
            if (this.rowIndex == -1) {
                return 0;
            }
            return 1;
        }

        @Override
        public int getTotalCount() {
            int rowCount = this.getRowCount();
            if (MultiDimDataSet.this.totalCount == null) {
                return rowCount;
            }
            if (MultiDimDataSet.this.totalCount < rowCount) {
                return rowCount;
            }
            return MultiDimDataSet.this.totalCount;
        }

        @Override
        public List<IRowData> getRowData() {
            if (this.rowIndex == -1) {
                return Collections.emptyList();
            }
            IRowData iRowData = (IRowData)MultiDimDataSet.this.rows.get(this.rowIndex);
            if (iRowData == null) {
                throw new IllegalStateException("\u5f53\u524d\u6570\u636e\u7ed3\u679c\u5df2\u7ecf\u88ab\u4e22\u5f03,\u8bf7\u68c0\u67e5\u4ee3\u7801");
            }
            return Collections.singletonList(iRowData);
        }

        @Override
        public List<IDataValue> getDataValuesByLink(String link) {
            if (MultiDimDataSet.this.metaData == null || !StringUtils.hasLength(link)) {
                return Collections.emptyList();
            }
            ArrayList<IDataValue> values = new ArrayList<IDataValue>();
            for (int i = 0; i < MultiDimDataSet.this.metaData.size(); ++i) {
                if (!((IMetaData)MultiDimDataSet.this.metaData.get(i)).getLinkKey().equals(link)) continue;
                for (IRowData row : this.getRowData()) {
                    IDataValue iDataValue = row.getLinkDataValues().get(i);
                    values.add(iDataValue);
                }
                return values;
            }
            return Collections.emptyList();
        }

        @Override
        public boolean supportTreeGroup() {
            return false;
        }
    }

    private static class RegionDataSetEmpty
    implements IRegionDataSet {
        private IRegionDataSet regionDataSet;
        private DimensionCombination dimensionCombination;

        public RegionDataSetEmpty() {
        }

        public RegionDataSetEmpty(IRegionDataSet regionDataSet, DimensionCombination dimensionCombination) {
            this.regionDataSet = regionDataSet;
            this.dimensionCombination = dimensionCombination;
        }

        @Override
        public String getRegionKey() {
            return this.regionDataSet.getRegionKey();
        }

        @Override
        public DimensionCombination getMasterDimension() {
            return this.dimensionCombination;
        }

        @Override
        public List<IMetaData> getMetaData() {
            return this.regionDataSet.getMetaData();
        }

        @Override
        public int getPage() {
            return 0;
        }

        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getTotalCount() {
            return 0;
        }

        @Override
        public List<IRowData> getRowData() {
            return Collections.emptyList();
        }

        @Override
        public List<IDataValue> getDataValuesByLink(String link) {
            return Collections.emptyList();
        }

        @Override
        public boolean supportTreeGroup() {
            return false;
        }
    }
}

