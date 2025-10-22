/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.jiuqi.nr.table.df.BlockData;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.Index;
import com.zaxxer.sparsebits.SparseBitSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Selection {
    public static <E> SparseBitSet select(DataFrame<E> df, Predicate<List<E>> predicate) {
        SparseBitSet selected = new SparseBitSet();
        Iterator<List<E>> rows = df.iterator();
        int r = 0;
        while (rows.hasNext()) {
            if (predicate.test(rows.next())) {
                selected.set(r);
            }
            ++r;
        }
        return selected;
    }

    public static Index select(Index index, SparseBitSet selected) {
        ArrayList<Object> names = new ArrayList<Object>(index.levels());
        Index newidx = new Index();
        newidx.copyMeta(index);
        int r = selected.nextSetBit(0);
        while (r >= 0) {
            Object name = names.get(r);
            newidx.add(name, index.get(name));
            r = selected.nextSetBit(r + 1);
        }
        return newidx;
    }

    public static <E> BlockData<E> select(BlockData<E> blocks, SparseBitSet selected) {
        BlockData<E> bd = new BlockData<E>();
        int r = selected.nextSetBit(0);
        while (r >= 0) {
            List<E> row = blocks.getRow(r);
            bd.add(row);
            r = selected.nextSetBit(r + 1);
        }
        return bd;
    }

    public static <E> BlockData<E> select(BlockData<E> blocks, SparseBitSet rows, SparseBitSet cols) {
        BlockData bd = new BlockData();
        int r = rows.nextSetBit(0);
        while (r >= 0) {
            ArrayList<E> row = new ArrayList<E>(cols.cardinality());
            int c = cols.nextSetBit(0);
            while (c >= 0) {
                row.add(blocks.get(c, r));
                c = cols.nextSetBit(c + 1);
            }
            bd.addRow(row);
            r = rows.nextSetBit(r + 1);
        }
        return bd;
    }

    public static <V> SparseBitSet[] slice(DataFrame<V> df, Integer rowStart, Integer rowEnd, Integer colStart, Integer colEnd) {
        SparseBitSet rows = new SparseBitSet();
        SparseBitSet cols = new SparseBitSet();
        rows.set((int)rowStart, rowEnd);
        cols.set((int)colStart, colEnd);
        return new SparseBitSet[]{rows, cols};
    }

    public static class DropNaPredicate<V>
    implements Predicate<List<V>> {
        @Override
        public boolean test(List<V> values) {
            for (V value : values) {
                if (value != null) continue;
                return false;
            }
            return true;
        }
    }
}

