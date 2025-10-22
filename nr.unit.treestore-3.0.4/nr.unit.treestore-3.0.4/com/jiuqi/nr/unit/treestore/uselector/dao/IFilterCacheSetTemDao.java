/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.uselector.dao;

import java.util.List;
import java.util.Set;

public interface IFilterCacheSetTemDao {
    public static final String TABLE_NAME = "unit_selector_filter_set_temp";
    public static final String FS_SELECTOR = "fs_selector";
    public static final String FS_ENTITY_DATA = "fs_entity_data";
    public static final String FS_ORDER = "fs_order";

    public Set<String> notExist(String var1, Set<String> var2);

    public int insert(String var1, Set<String> var2);

    public int clear(String var1);

    public int clear(List<String> var1);
}

