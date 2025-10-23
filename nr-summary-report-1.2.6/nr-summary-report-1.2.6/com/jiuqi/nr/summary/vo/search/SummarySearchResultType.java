/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 */
package com.jiuqi.nr.summary.vo.search;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum SummarySearchResultType {
    SUMMARY_SOLUTION_GROUP("SUMMARY_SOLUTION_GROUP", "\u6c47\u603b\u65b9\u6848\u5206\u7ec4"),
    SUMMARY_SOLUTION("SUMMARY_SOLUTION", "\u6c47\u603b\u65b9\u6848"),
    SUMMARY_REPORT("SUMMARY_REPORT", "\u6c47\u603b\u8868");

    private String key;
    private String title;

    private SummarySearchResultType(String key, String title) {
        this.key = key;
        this.title = title;
    }

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
}

