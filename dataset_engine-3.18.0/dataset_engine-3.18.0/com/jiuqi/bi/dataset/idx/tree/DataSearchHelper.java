/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx.tree;

import com.jiuqi.bi.dataset.idx.tree.IDataProvider;

public class DataSearchHelper {
    public static int quickSearch(IDataProvider dataProvider, int startRow) {
        Object[] curData;
        int r;
        Object[] data = dataProvider.getData(startRow);
        int l = startRow;
        int step = 0;
        while (true) {
            r = l + (int)Math.pow(2.0, step);
            ++step;
            curData = dataProvider.getData(r = Math.min(r, dataProvider.getSize() - 1));
            if (DataSearchHelper.compare(data, curData) != 0) break;
            if (r == dataProvider.getSize() - 1) {
                return r;
            }
            l = r;
        }
        if (r == l + 1) {
            return l;
        }
        int lastIndex = l;
        while (l < r) {
            int half = (r - l) / 2 + l;
            curData = dataProvider.getData(half);
            if (DataSearchHelper.compare(data, curData) == 0) {
                lastIndex = half;
                l = half + 1;
                continue;
            }
            r = half - 1;
        }
        return lastIndex;
    }

    public static int binarySearch(IDataProvider dataProvider, Object[] data) {
        int l = 0;
        int r = dataProvider.getSize() - 1;
        while (l <= r) {
            int half = (r - l) / 2 + l;
            Object[] curData = dataProvider.getData(half);
            int result = DataSearchHelper.compare(data, curData);
            if (l == r && result != 0) {
                return -1;
            }
            if (result > 0) {
                l = half + 1;
                continue;
            }
            if (result < 0) {
                r = half - 1;
                continue;
            }
            return half;
        }
        return -1;
    }

    private static int compare(Object[] o1, Object[] o2) {
        for (int i = 0; i < o1.length; ++i) {
            int result = ((Comparable)o1[i]).compareTo(o2[i]);
            if (result == 0) continue;
            return result;
        }
        return 0;
    }
}

