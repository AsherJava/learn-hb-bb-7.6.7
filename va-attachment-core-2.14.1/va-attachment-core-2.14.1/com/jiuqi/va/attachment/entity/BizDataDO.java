/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.attachment.entity;

import com.jiuqi.va.mapper.domain.TenantDO;

public class BizDataDO
extends TenantDO {
    private String tablenName;
    private String id;
    private String quoteCode;

    public BizDataDO(String tablenName, String id) {
        this.tablenName = tablenName;
        this.id = id;
    }

    public BizDataDO(String tablenName, String id, String quoteCode) {
        this.tablenName = tablenName;
        this.id = id;
        this.quoteCode = quoteCode;
    }

    public String getTablenName() {
        return this.tablenName;
    }

    public void setTablenName(String tablenName) {
        this.tablenName = tablenName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuoteCode() {
        return this.quoteCode;
    }

    public void setQuoteCode(String quoteCode) {
        this.quoteCode = quoteCode;
    }
}

