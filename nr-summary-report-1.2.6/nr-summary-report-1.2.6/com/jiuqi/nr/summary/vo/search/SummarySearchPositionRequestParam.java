/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo.search;

import com.jiuqi.nr.summary.vo.search.SummarySearchResultType;

public class SummarySearchPositionRequestParam {
    private String key;
    private SummarySearchResultType type;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SummarySearchResultType getType() {
        return this.type;
    }

    public void setType(SummarySearchResultType type) {
        this.type = type;
    }
}

