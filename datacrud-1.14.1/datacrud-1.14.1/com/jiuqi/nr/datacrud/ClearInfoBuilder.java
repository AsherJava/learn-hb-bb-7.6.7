/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.util.Assert;

public final class ClearInfoBuilder {
    private String regionKey;
    private DimensionCombination dimension;
    private DimensionCollection dimensionCollection;
    private List<RowFilter> rowFilter;

    private ClearInfoBuilder() {
    }

    public ClearInfoBuilder(String regionKey, DimensionCombination dimension) {
        Assert.notNull((Object)regionKey, "\u533a\u57dfkey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)dimension, "\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        this.regionKey = regionKey;
        this.dimension = dimension;
    }

    private ClearInfoBuilder(String regionKey, DimensionCollection dimension) {
        Assert.notNull((Object)regionKey, "\u533a\u57dfkey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)dimension, "\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        this.regionKey = regionKey;
        this.dimensionCollection = dimension;
    }

    public static ClearInfoBuilder create(String regionKey, DimensionCombination dimension) {
        Assert.notNull((Object)regionKey, "\u533a\u57dfkey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)dimension, "\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        return new ClearInfoBuilder(regionKey, dimension);
    }

    public static ClearInfoBuilder create(String regionKey, DimensionCollection dimension) {
        Assert.notNull((Object)regionKey, "\u533a\u57dfkey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)dimension, "\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        return new ClearInfoBuilder(regionKey, dimension);
    }

    public ClearInfoBuilder where(RowFilter filter) {
        if (this.rowFilter == null) {
            this.rowFilter = new ArrayList<RowFilter>();
        }
        this.rowFilter.add(filter);
        return this;
    }

    public IClearInfo build() {
        return new ClearInfo();
    }

    private class ClearInfo
    implements IClearInfo {
        private ClearInfo() {
        }

        @Override
        public String getRegionKey() {
            return ClearInfoBuilder.this.regionKey;
        }

        @Override
        public DimensionCombination getDimensionCombination() {
            return ClearInfoBuilder.this.dimension;
        }

        @Override
        public RegionRelation getRegionRelation() {
            return null;
        }

        @Override
        public DimensionCollection getDimensionCollection() {
            return ClearInfoBuilder.this.dimensionCollection;
        }

        @Override
        public Iterator<RowFilter> rowFilterItr() {
            if (ClearInfoBuilder.this.rowFilter == null) {
                return null;
            }
            return ClearInfoBuilder.this.rowFilter.iterator();
        }
    }
}

