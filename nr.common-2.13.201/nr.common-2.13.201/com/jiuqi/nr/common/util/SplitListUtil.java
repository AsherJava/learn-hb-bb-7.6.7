/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.util;

import java.util.ArrayList;
import java.util.List;

public class SplitListUtil {
    public static <T> List<List<T>> averageAssign(List<T> source, int num) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        int index = 0;
        while (source.size() > index * num) {
            int fromIndex = index * num;
            int pageEnd = (index + 1) * num - 1;
            int toIndex = source.size() - 1 > pageEnd ? pageEnd : source.size() - 1;
            List<T> value = source.subList(fromIndex, toIndex);
            result.add(value);
            ++index;
        }
        return result;
    }
}

