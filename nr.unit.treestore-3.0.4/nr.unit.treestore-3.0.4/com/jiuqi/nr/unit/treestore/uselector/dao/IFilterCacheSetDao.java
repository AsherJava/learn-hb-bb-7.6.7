/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.uselector.dao;

import com.jiuqi.nr.unit.treestore.uselector.bean.FilterCacheSetItem;
import java.util.List;
import java.util.Set;

public interface IFilterCacheSetDao {
    public static final String TABLE_NAME = "unit_selector_filter_set";
    public static final String FS_SELECTOR = "fs_selector";
    public static final String FS_ENTITY_DATA = "fs_entity_data";
    public static final String FS_ORDER = "fs_order";

    public int insert(String var1, Set<String> var2);

    public int intersection(String var1, Set<String> var2);

    public int update(String var1, List<FilterCacheSetItem> var2);

    public int count(String var1);

    public double maxOrder(String var1);

    public List<String> findOnePage(String var1, int var2, int var3);

    public List<String> findAllPages(String var1);

    public List<String> findRows(String var1, List<String> var2);

    public List<FilterCacheSetItem> findRowDetails(String var1, List<String> var2);

    public int remove(List<String> var1);

    public int removeAll(String var1, Set<String> var2);
}

