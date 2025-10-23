/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshTable;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class RefreshCache
implements Serializable {
    private static final long serialVersionUID = -1683094471477280020L;
    private Map<RefreshScheme, Set<RefreshTable>> refreshTable;
    private boolean refreshAll;

    public RefreshCache() {
    }

    public RefreshCache(boolean refreshAll) {
        this.refreshAll = refreshAll;
    }

    public RefreshCache(Map<RefreshScheme, Set<RefreshTable>> refreshTable) {
        this.refreshTable = refreshTable;
    }

    public Map<RefreshScheme, Set<RefreshTable>> getRefreshTable() {
        return this.refreshTable;
    }

    public void setRefreshTable(Map<RefreshScheme, Set<RefreshTable>> refreshTable) {
        this.refreshTable = refreshTable;
    }

    public boolean isRefreshAll() {
        return this.refreshAll;
    }

    public void setRefreshAll(boolean refreshAll) {
        this.refreshAll = refreshAll;
    }

    public String toString() {
        if (this.refreshAll) {
            return "RefreshCache{ refreshAll=true}";
        }
        return "RefreshCache{refreshTable=" + this.refreshTable + '}';
    }
}

