/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.KeyFunction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class Combining {
    public static <E> DataFrame<E> join(DataFrame<E> left, DataFrame<E> right, DataFrame.JoinType how, KeyFunction<E> on) {
        Object key;
        Object name;
        Iterator<Object> leftIt = left.rows().levels().iterator();
        Iterator<Object> rightIt = right.rows().levels().iterator();
        LinkedHashMap<Object, List<E>> leftMap = new LinkedHashMap<Object, List<E>>();
        LinkedHashMap<Object, List<E>> rightMap = new LinkedHashMap<Object, List<E>>();
        for (List<E> list : left) {
            name = leftIt.next();
            key = on == null ? name : on.apply(list);
            if (leftMap.put(key, list) == null) continue;
            throw new IllegalArgumentException("generated key is not unique: " + key);
        }
        for (List<E> list : right) {
            name = rightIt.next();
            key = on == null ? name : on.apply(list);
            if (rightMap.put(key, list) == null) continue;
            throw new IllegalArgumentException("generated key is not unique: " + key);
        }
        ArrayList<Object> columns = new ArrayList<Object>(how != DataFrame.JoinType.RIGHT ? left.columns().levels() : right.columns().levels());
        for (Object column : how != DataFrame.JoinType.RIGHT ? right.columns() : left.columns()) {
            int index = columns.indexOf(column);
            if (index >= 0) {
                if (column instanceof List) {
                    List l1 = (List)List.class.cast(columns.get(index));
                    l1.add(how != DataFrame.JoinType.RIGHT ? "left" : "right");
                    List l2 = (List)List.class.cast(column);
                    l2.add(how != DataFrame.JoinType.RIGHT ? "right" : "left");
                } else {
                    columns.set(index, String.format("%s_%s", columns.get(index), how != DataFrame.JoinType.RIGHT ? "left" : "right"));
                    column = String.format("%s_%s", column, how != DataFrame.JoinType.RIGHT ? "right" : "left");
                }
            }
            columns.add(column);
        }
        DataFrame dataFrame = new DataFrame((Collection<?>)columns);
        for (Map.Entry entry : how != DataFrame.JoinType.RIGHT ? leftMap.entrySet() : rightMap.entrySet()) {
            List<Object> row;
            ArrayList<Object> tmp = new ArrayList<Object>((Collection)entry.getValue());
            List<Object> list = row = how != DataFrame.JoinType.RIGHT ? (List<Object>)rightMap.get(entry.getKey()) : (List)leftMap.get(entry.getKey());
            if (row == null && how == DataFrame.JoinType.INNER) continue;
            tmp.addAll(row != null ? row : Collections.nCopies(right.columns().size(), null));
            dataFrame.add(entry.getKey(), tmp);
        }
        if (how == DataFrame.JoinType.OUTER) {
            for (Map.Entry entry : how != DataFrame.JoinType.RIGHT ? rightMap.entrySet() : leftMap.entrySet()) {
                List row = how != DataFrame.JoinType.RIGHT ? (List)leftMap.get(entry.getKey()) : (List)rightMap.get(entry.getKey());
                if (row != null) continue;
                ArrayList<Object> tmp = new ArrayList<Object>(Collections.nCopies(how != DataFrame.JoinType.RIGHT ? left.columns().size() : right.columns().size(), null));
                tmp.addAll((Collection)entry.getValue());
                dataFrame.add(entry.getKey(), tmp);
            }
        }
        return dataFrame;
    }

    public static <E> DataFrame<E> joinOn(DataFrame<E> left, DataFrame<E> right, DataFrame.JoinType how, final Integer ... cols) {
        return Combining.join(left, right, how, new KeyFunction<E>(){

            @Override
            public Object apply(List<E> value) {
                ArrayList key = new ArrayList(cols.length);
                Integer[] integerArray = cols;
                int n = integerArray.length;
                for (int i = 0; i < n; ++i) {
                    int col = integerArray[i];
                    key.add(value.get(col));
                }
                return Collections.unmodifiableList(key);
            }
        });
    }

    public static <E> DataFrame<E> merge(DataFrame<E> left, DataFrame<E> right, DataFrame.JoinType how) {
        LinkedHashSet<Object> intersection = new LinkedHashSet<Object>(left.nonnumeric().columns().levels());
        intersection.retainAll(right.nonnumeric().columns().levels());
        Object[] columns = intersection.toArray(new Object[intersection.size()]);
        return Combining.join(left.reindex(columns), right.reindex(columns), how, null);
    }

    @SafeVarargs
    public static <E> void update(DataFrame<E> dest, boolean overwrite, DataFrame<? extends E> ... others) {
        for (int col = 0; col < dest.size(); ++col) {
            block1: for (int row = 0; row < dest.length(); ++row) {
                if (!overwrite && dest.get(row, col) != null) continue;
                for (DataFrame<E> dataFrame : others) {
                    E value;
                    if (col >= dataFrame.size() || row >= dataFrame.length() || (value = dataFrame.get(row, col)) == null) continue;
                    dest.set(row, col, value);
                    continue block1;
                }
            }
        }
    }

    @SafeVarargs
    public static <E> DataFrame<E> concat(DataFrame<E> first, DataFrame<? extends E> ... others) {
        ArrayList<DataFrame<E>> dfs = new ArrayList<DataFrame<E>>(others.length + 1);
        dfs.add(first);
        dfs.addAll(Arrays.asList(others));
        int rows = 0;
        LinkedHashSet<Object> columns = new LinkedHashSet<Object>();
        for (DataFrame dataFrame : dfs) {
            rows += dataFrame.size();
            for (Object c : dataFrame.columns()) {
                columns.add(c);
            }
        }
        ArrayList newcols = new ArrayList(columns);
        DataFrame dataFrame = new DataFrame(columns).reshape(rows, columns.size());
        int offset = 0;
        for (DataFrame dataFrame2 : dfs) {
            ArrayList<Object> cols = new ArrayList<Object>(dataFrame2.columns().levels());
            for (int c = 0; c < cols.size(); ++c) {
                int newc = newcols.indexOf(cols.get(c));
                for (int r = 0; r < dataFrame2.size(); ++r) {
                    dataFrame.set(newc, offset + r, dataFrame2.get(c, r));
                }
            }
            offset += dataFrame2.size();
        }
        return dataFrame;
    }
}

