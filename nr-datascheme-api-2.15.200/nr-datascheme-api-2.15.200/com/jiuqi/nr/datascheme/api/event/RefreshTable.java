/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import java.io.Serializable;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

public class RefreshTable
implements Serializable {
    private static final long serialVersionUID = -719462539742179200L;
    private final String tableKey;
    private final String tableCode;

    public RefreshTable(@NonNull String tableKey, @NonNull String tableCode) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        this.tableKey = tableKey;
        this.tableCode = tableCode;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RefreshTable that = (RefreshTable)o;
        if (!Objects.equals(this.tableKey, that.tableKey)) {
            return false;
        }
        return Objects.equals(this.tableCode, that.tableCode);
    }

    public int hashCode() {
        int result = this.tableKey != null ? this.tableKey.hashCode() : 0;
        result = 31 * result + (this.tableCode != null ? this.tableCode.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "RefreshTable{tableKey='" + this.tableKey + '\'' + ", tableCode='" + this.tableCode + '\'' + '}';
    }
}

