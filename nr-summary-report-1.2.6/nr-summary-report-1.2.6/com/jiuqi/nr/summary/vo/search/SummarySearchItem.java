/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo.search;

import com.jiuqi.nr.summary.vo.search.SummarySearchResultType;
import java.util.List;

public class SummarySearchItem {
    private String key;
    private String title;
    private String path;
    private List<String> pathIds;
    private List<SummarySearchResultType> type;
    private Object data;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getPathIds() {
        return this.pathIds;
    }

    public void setPathIds(List<String> pathIds) {
        this.pathIds = pathIds;
    }

    public List<SummarySearchResultType> getType() {
        return this.type;
    }

    public void setType(List<SummarySearchResultType> type) {
        this.type = type;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

