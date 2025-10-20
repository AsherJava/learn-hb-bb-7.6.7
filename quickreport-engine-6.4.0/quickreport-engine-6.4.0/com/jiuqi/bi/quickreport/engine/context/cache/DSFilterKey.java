/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.context.cache;

import com.jiuqi.bi.quickreport.engine.context.filter.FilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.MappingFilterDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public final class DSFilterKey
implements Comparable<DSFilterKey>,
Iterable<IFilterDescriptor> {
    private final String dsName;
    private final IFilterDescriptor[] filters;

    public DSFilterKey(String dsName, IFilterDescriptor[] filters) {
        this.dsName = dsName.toUpperCase();
        this.filters = (IFilterDescriptor[])filters.clone();
        Arrays.sort(this.filters);
    }

    public DSFilterKey(String dsName, Collection<IFilterDescriptor> filters) {
        this.dsName = dsName.toUpperCase();
        this.filters = filters.toArray(new FilterDescriptor[filters.size()]);
        Arrays.sort(this.filters);
    }

    public DSFilterKey(String dsName, IFilterDescriptor filter) {
        this.dsName = dsName.toUpperCase();
        this.filters = new IFilterDescriptor[]{filter};
        Arrays.sort(this.filters);
    }

    public String getDataSetName() {
        return this.dsName;
    }

    public boolean isJoined(String dsName) {
        for (IFilterDescriptor filter : this.filters) {
            if (!(filter instanceof MappingFilterDescriptor) || !dsName.equalsIgnoreCase(((MappingFilterDescriptor)filter).getMappingField().dataSetName)) continue;
            return true;
        }
        return false;
    }

    public int filterSize() {
        return this.filters == null ? 0 : this.filters.length;
    }

    public IFilterDescriptor getFilter(int index) {
        return this.filters[index];
    }

    public int hashCode() {
        return this.dsName.hashCode() * 31 + Arrays.hashCode(this.filters);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof DSFilterKey) {
            return this.compareTo((DSFilterKey)obj) == 0;
        }
        return false;
    }

    @Override
    public int compareTo(DSFilterKey o) {
        int c = this.dsName.compareTo(o.dsName);
        if (c != 0) {
            return c;
        }
        c = this.filters.length - o.filters.length;
        if (c != 0) {
            return c;
        }
        for (int i = 0; i < this.filters.length; ++i) {
            c = this.filters[i].compareTo(o.filters[i]);
            if (c == 0) continue;
            return c;
        }
        return 0;
    }

    public String toString() {
        return this.dsName + "@" + Arrays.toString(this.filters);
    }

    @Override
    public Iterator<IFilterDescriptor> iterator() {
        return new Iterator<IFilterDescriptor>(){
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < DSFilterKey.this.filters.length;
            }

            @Override
            public IFilterDescriptor next() {
                IFilterDescriptor item = DSFilterKey.this.filters[this.index];
                ++this.index;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}

