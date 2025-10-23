/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import java.io.Serializable;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

public class RefreshScheme
implements Serializable {
    private static final long serialVersionUID = 18646406668300660L;
    private final String key;
    private final String code;
    private final boolean refreshAll;

    public RefreshScheme(@NonNull String key, @NonNull String code) {
        this(key, code, true);
    }

    public RefreshScheme(@NonNull String key, @NonNull String code, boolean refreshAll) {
        Assert.notNull((Object)key, "key must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        this.key = key;
        this.code = code;
        this.refreshAll = refreshAll;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public boolean isRefreshAll() {
        return this.refreshAll;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RefreshScheme that = (RefreshScheme)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public String toString() {
        return "RefreshScheme: {key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", refreshAll=" + this.refreshAll + '}';
    }
}

