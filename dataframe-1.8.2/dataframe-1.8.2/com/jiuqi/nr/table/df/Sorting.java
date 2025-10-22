/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.jiuqi.nr.table.df.DataFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Sorting {
    public static <E> DataFrame<E> sort(DataFrame<E> df, final Map<Integer, DataFrame.SortDirection> cols) {
        Comparator comparator = new Comparator<List<E>>(){

            @Override
            public int compare(List<E> r1, List<E> r2) {
                int result = 0;
                for (Map.Entry col : cols.entrySet()) {
                    int c = (Integer)col.getKey();
                    Comparable v1 = (Comparable)Comparable.class.cast(r1.get(c));
                    Object v2 = r2.get(c);
                    result = v1.compareTo(v2);
                    if ((result *= col.getValue() == DataFrame.SortDirection.DESCENDING ? -1 : 1) == 0) continue;
                    break;
                }
                return result;
            }
        };
        return Sorting.sort(df, comparator);
    }

    public static <E> DataFrame<E> sort(final DataFrame<E> df, final Comparator<List<E>> comparator) {
        DataFrame<E> sorted = new DataFrame<E>(df.columns());
        sorted.rows().copyMeta(df.rows());
        Comparator<Integer> cmp = new Comparator<Integer>(){

            @Override
            public int compare(Integer r1, Integer r2) {
                return comparator.compare(df.row(r1), df.row(r2));
            }
        };
        Integer[] rows = new Integer[df.size()];
        for (int r = 0; r < df.size(); ++r) {
            rows[r] = r;
        }
        Arrays.sort(rows, cmp);
        ArrayList<Object> labels = new ArrayList<Object>(df.rows().levels());
        for (Integer r : rows) {
            Integer label = r < labels.size() ? labels.get(r) : r;
            sorted.add((Object)label, df.row(r));
        }
        return sorted;
    }
}

