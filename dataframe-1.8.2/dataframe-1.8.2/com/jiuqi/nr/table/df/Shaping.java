/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.jiuqi.nr.table.df.DataFrame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.apache.commons.compress.utils.Lists;

public class Shaping {
    public static final <V> DataFrame<V> reshape(DataFrame<V> df, int rows, int cols) {
        Object name;
        int c;
        DataFrame<Object> reshaped = new DataFrame<Object>();
        reshaped.rows().copyMeta(df.rows());
        reshaped.columns().copyMeta(df.columns());
        Iterator<Object> it = df.columns().levels().iterator();
        for (c = 0; c < cols; ++c) {
            name = it.hasNext() ? it.next() : Integer.valueOf(c);
            reshaped.append(name);
        }
        it = df.rows().levels().iterator();
        for (int r = 0; r < rows; ++r) {
            name = it.hasNext() ? it.next() : Integer.valueOf(r);
            ArrayList<Object> rowData = Lists.newArrayList();
            for (int c2 = 0; c2 < cols; ++c2) {
                rowData.add(null);
            }
            reshaped.add(name, rowData);
        }
        for (c = 0; c < cols; ++c) {
            for (int r = 0; r < rows; ++r) {
                if (c >= df.length() || r >= df.size()) continue;
                reshaped.set(c, r, df.get(c, r));
            }
        }
        return reshaped;
    }

    public static final <V> DataFrame<V> reshape(DataFrame<V> df, Collection<?> rows, Collection<?> cols) {
        DataFrame reshaped = new DataFrame();
        reshaped.rows().copyMeta(df.rows());
        reshaped.columns().copyMeta(df.columns());
        for (Object name : cols) {
            reshaped.add(name);
        }
        for (Object name : rows) {
            reshaped.append(name, Collections.emptyList());
        }
        for (Object c : cols) {
            for (Object r : rows) {
                if (df.columns().levels().contains(c) && !df.rows().levels().contains(r)) continue;
            }
        }
        return reshaped;
    }
}

