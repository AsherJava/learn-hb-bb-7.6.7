/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.entity;

public class GcFetchTaskLogClobEO {
    public static final String TABLENAME = "GC_FETCH_TASKLOGCLOB";
    private String id;
    private String clobContent;

    public GcFetchTaskLogClobEO() {
    }

    public GcFetchTaskLogClobEO(String id, String resultContent) {
        this.setId(id);
        this.clobContent = resultContent;
    }

    public String getClobContent() {
        return this.clobContent;
    }

    public void setClobContent(String clobContent) {
        this.clobContent = clobContent;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

