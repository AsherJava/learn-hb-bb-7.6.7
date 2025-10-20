/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.context;

import java.util.Map;

public class PagingUtils {
    private PagingUtils() {
    }

    public static int numOfPage(Map<String, Integer> pageNums) {
        if (pageNums == null || pageNums.isEmpty()) {
            return -1;
        }
        Integer pageNum = pageNums.get("@GLOBAL");
        if (pageNum != null) {
            return pageNum;
        }
        pageNum = pageNums.get("@DEFAULT");
        return pageNum == null ? -1 : pageNum;
    }

    public static int numOfPage(Map<String, Integer> pageNums, String pageID) {
        if (pageNums == null || pageNums.isEmpty()) {
            return -1;
        }
        Integer pageNum = pageNums.get(pageID);
        if (pageNum != null) {
            return pageNum <= 0 ? -1 : pageNum;
        }
        pageNum = pageNums.get("@DEFAULT");
        return pageNum == null || pageNum <= 0 ? -1 : pageNum;
    }
}

