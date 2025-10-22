/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.treestore.uselector.dao;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface USerarchTempDao {
    public static final String TABLE_NAME = "unit_selector_search_temp";
    public static final String ST_SELECTOR = "st_selector";
    public static final String ST_ID = "st_id";
    public static final String ST_CODE = "st_code";
    public static final String ST_TITLE = "st_title";
    public static final String ST_ORDER = "st_order";

    public int insert(String var1, List<IEntityRow> var2);

    public List<String> containsOnePage(String var1, boolean var2, int var3, int var4, List<String> var5, List<String> var6);

    public List<String> contains(String var1, boolean var2, List<String> var3, List<String> var4);

    public int count(String var1, boolean var2, List<String> var3, List<String> var4);

    public int clear(String var1);

    public int clear(List<String> var1);
}

