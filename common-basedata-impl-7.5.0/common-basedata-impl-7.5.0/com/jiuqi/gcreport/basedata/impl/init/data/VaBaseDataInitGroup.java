/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.impl.init.data;

import java.io.Serializable;

public class VaBaseDataInitGroup
implements Serializable {
    private static final long serialVersionUID = -1563817352634261698L;
    private String name;
    private String title;
    private String parentname;

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

    public String getParentname() {
        return this.parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }
}

