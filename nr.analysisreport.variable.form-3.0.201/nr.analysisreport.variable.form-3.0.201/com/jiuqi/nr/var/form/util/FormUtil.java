/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.var.form.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

public class FormUtil {
    public static <T> List<List<T>> splitList(ArrayList<T> list, int n) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(list) || n == 0) {
            return result;
        }
        int size = list.size();
        if (n > size) {
            n = size;
        }
        int quotient = size / n;
        int remainder = size % n;
        for (int i = 0; i < n; ++i) {
            int start = i * quotient + Math.min(i, remainder);
            int end = (i + 1) * quotient + Math.min(i + 1, remainder);
            result.add(list.subList(start, end));
        }
        return result;
    }
}

