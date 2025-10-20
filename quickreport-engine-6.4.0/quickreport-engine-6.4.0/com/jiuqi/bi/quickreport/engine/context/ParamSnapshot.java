/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.util.StringUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ParamSnapshot {
    private final String paramName;
    private final List<?> value;
    private final String dimTree;
    private Set<String> affects;

    public ParamSnapshot(String paramName, List<?> value, String dimTree) {
        this.paramName = paramName;
        this.value = value;
        this.dimTree = dimTree;
        this.affects = new HashSet<String>();
    }

    public String getParamName() {
        return this.paramName;
    }

    public List<?> getValue() {
        return this.value;
    }

    public String getDimTree() {
        return this.dimTree;
    }

    public Set<String> getAffects() {
        return this.affects;
    }

    public int hashCode() {
        int h = this.paramName.hashCode();
        h = h * 31 + (this.dimTree == null ? 0 : this.dimTree.hashCode());
        for (Object v : this.value) {
            h = h * 31 + (v == null ? 0 : v.hashCode());
        }
        return h;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ParamSnapshot)) {
            return false;
        }
        ParamSnapshot snap = (ParamSnapshot)obj;
        return this.paramName.equals(snap.paramName) && this.equalsValue(snap.value, snap.dimTree);
    }

    public boolean equalsValue(List<?> anotherValue, String dimTree) {
        if (!StringUtils.equals((String)this.dimTree, (String)dimTree)) {
            return false;
        }
        if (this.value == anotherValue) {
            return true;
        }
        if (this.value == null || anotherValue == null) {
            return false;
        }
        if (this.value.size() != anotherValue.size()) {
            return false;
        }
        for (int i = 0; i < anotherValue.size(); ++i) {
            Object v1 = this.value.get(i);
            Object v2 = anotherValue.get(i);
            if (!(v1 == null ? v2 != null : !v1.equals(v2))) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.paramName).append("=").append(this.value);
        if (this.dimTree != null) {
            buffer.append('@').append(this.dimTree);
        }
        buffer.append(" ").append(this.affects);
        return buffer.toString();
    }
}

