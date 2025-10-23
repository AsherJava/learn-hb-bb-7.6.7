/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.common;

import java.util.Collections;
import java.util.List;

public class PageUtils {
    public static <T> List<T> paginate(List<T> fullList, int pageNumber, int pageSize) {
        if (fullList == null || fullList.isEmpty()) {
            return Collections.emptyList();
        }
        int total = fullList.size();
        int fromIndex = Math.max(0, (pageNumber - 1) * pageSize);
        int toIndex = Math.min(fromIndex + pageSize, total);
        if (fromIndex >= total) {
            return Collections.emptyList();
        }
        return fullList.subList(fromIndex, toIndex);
    }
}

