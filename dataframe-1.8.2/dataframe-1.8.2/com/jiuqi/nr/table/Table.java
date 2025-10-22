/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table;

import com.jiuqi.nr.table.df.DataFrame;
import java.util.List;

public class Table {
    private String name;
    private List<DataFrame<?>> dfs;

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public DataFrame<?> get(int index) {
        return this.dfs.get(index);
    }

    public int size() {
        return this.dfs.size();
    }

    public void add(DataFrame<?> df) {
        this.dfs.add(df);
    }
}

