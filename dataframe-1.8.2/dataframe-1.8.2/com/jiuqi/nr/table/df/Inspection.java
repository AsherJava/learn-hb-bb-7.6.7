/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.jiuqi.nr.table.df.DataFrame;
import com.zaxxer.sparsebits.SparseBitSet;
import java.util.ArrayList;
import java.util.List;

public class Inspection {
    public static List<Class<?>> types(DataFrame<?> df) {
        ArrayList types = new ArrayList(df.size());
        for (int c = 0; c < df.size() && 0 < df.length(); ++c) {
            Object value = df.get(0, c);
            types.add(value != null ? value.getClass() : Object.class);
        }
        return types;
    }

    public static SparseBitSet numeric(DataFrame<?> df) {
        SparseBitSet numeric = new SparseBitSet();
        List<Class<?>> types = Inspection.types(df);
        for (int c = 0; c < types.size(); ++c) {
            if (!Number.class.isAssignableFrom(types.get(c))) continue;
            numeric.set(c);
        }
        return numeric;
    }

    public static SparseBitSet nonnumeric(DataFrame<?> df) {
        SparseBitSet nonnumeric = Inspection.numeric(df);
        nonnumeric.flip(0, df.size());
        return nonnumeric;
    }
}

