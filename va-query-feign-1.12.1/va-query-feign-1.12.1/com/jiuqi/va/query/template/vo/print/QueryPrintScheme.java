/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo.print;

import java.util.Map;

public class QueryPrintScheme {
    private String name;
    private String title;
    Map<String, Object> document;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Object> getDocument() {
        return this.document;
    }

    public void setDocument(Map<String, Object> document) {
        this.document = document;
    }
}

