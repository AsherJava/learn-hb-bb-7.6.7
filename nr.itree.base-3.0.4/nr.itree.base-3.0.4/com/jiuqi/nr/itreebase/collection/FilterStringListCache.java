/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.collection;

import com.jiuqi.nr.itreebase.cache.ITreeCacheArea;
import com.jiuqi.nr.itreebase.collection.FilterStringList;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import com.jiuqi.nr.itreebase.collection.IFilterStringListSortParam;
import java.util.List;

public class FilterStringListCache
implements IFilterStringList {
    private final String selector;
    private final ITreeCacheArea cacheArea;
    private FilterStringList owner = new FilterStringList();

    public FilterStringListCache(ITreeCacheArea cacheArea, String selector) {
        this.selector = selector;
        this.cacheArea = cacheArea;
        this.loadCacheFilterSet();
    }

    @Override
    public int size() {
        return this.owner.size();
    }

    @Override
    public boolean isEmpty() {
        return this.owner.isEmpty();
    }

    @Override
    public boolean contains(String e) {
        return this.owner.contains(e);
    }

    @Override
    public List<String> toList() {
        return this.owner.toList();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        return this.owner.subList(fromIndex, toIndex);
    }

    @Override
    public IFilterStringList unionAll(List<String> c) {
        this.owner.unionAll(c);
        this.setCacheFilterSet();
        return this;
    }

    @Override
    public IFilterStringList retainAll(List<String> c) {
        this.owner.retainAll(c);
        this.setCacheFilterSet();
        return this;
    }

    @Override
    public IFilterStringList supplementaryAll(List<String> c) {
        this.owner.supplementaryAll(c);
        this.setCacheFilterSet();
        return this;
    }

    @Override
    public IFilterStringList removeAll(List<String> c) {
        this.owner.removeAll(c);
        this.setCacheFilterSet();
        return this;
    }

    @Override
    public IFilterStringList clear() {
        this.owner.clear();
        this.setCacheFilterSet();
        return this;
    }

    @Override
    public IFilterStringList sort(IFilterStringListSortParam sortPara) {
        this.owner.sort(sortPara);
        this.setCacheFilterSet();
        return this;
    }

    private void loadCacheFilterSet() {
        if (this.cacheArea.contains(this.selector)) {
            this.owner = this.cacheArea.getCacheData(this.selector, FilterStringList.class);
        }
    }

    private void setCacheFilterSet() {
        this.cacheArea.putCacheData(this.selector, this.owner);
    }
}

