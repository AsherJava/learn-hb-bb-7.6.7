/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.executor.query;

import com.jiuqi.nr.summary.model.PageInfo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QueryPageConfig
implements Serializable {
    private Map<Integer, PageInfo> pageInfos = new HashMap<Integer, PageInfo>();

    public Map<Integer, PageInfo> getPageInfos() {
        return this.pageInfos;
    }

    public void setPageInfos(Map<Integer, PageInfo> pageInfos) {
        this.pageInfos = pageInfos;
    }

    public PageInfo getPageInfo(Integer rowNumber) {
        return this.pageInfos.get(rowNumber);
    }
}

