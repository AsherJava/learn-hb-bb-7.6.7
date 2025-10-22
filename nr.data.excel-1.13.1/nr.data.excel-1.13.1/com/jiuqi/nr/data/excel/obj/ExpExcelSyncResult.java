/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ExpExcelSyncResult
implements Serializable {
    private static final long serialVersionUID = -2096271655605099005L;
    private String expPath;
    private boolean noDataNoExp;
    private final Set<String> expDws = new HashSet<String>();

    public String getExpPath() {
        return this.expPath;
    }

    public void setExpPath(String expPath) {
        this.expPath = expPath;
    }

    public boolean isNoDataNoExp() {
        return this.noDataNoExp;
    }

    public void setNoDataNoExp(boolean noDataNoExp) {
        this.noDataNoExp = noDataNoExp;
    }

    public Set<String> getExpDws() {
        return this.expDws;
    }
}

