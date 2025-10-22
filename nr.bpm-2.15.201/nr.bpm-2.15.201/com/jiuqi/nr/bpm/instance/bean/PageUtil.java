/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.instance.bean;

import com.jiuqi.nr.bpm.instance.bean.GridDataItem;
import java.util.List;
import java.util.stream.Collectors;

public class PageUtil {
    public static List<GridDataItem> subList(List<GridDataItem> list, int pageSize, int pageNum) {
        try {
            int pages;
            int count = list.size();
            int n = pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            int start = pageNum <= 0 ? 0 : (pageNum > pages ? (pages - 1) * pageSize : (pageNum - 1) * pageSize);
            int end = Math.min(start + pageSize, count);
            return list.stream().skip(start).limit(end - start).collect(Collectors.toList());
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static List<String> pageList(List<String> list, int pageSize, int pageNum) {
        try {
            int pages;
            int count = list.size();
            int n = pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            int start = pageNum <= 0 ? 0 : (pageNum > pages ? (pages - 1) * pageSize : (pageNum - 1) * pageSize);
            int end = Math.min(start + pageSize, count);
            return list.stream().skip(start).limit(end - start).collect(Collectors.toList());
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

