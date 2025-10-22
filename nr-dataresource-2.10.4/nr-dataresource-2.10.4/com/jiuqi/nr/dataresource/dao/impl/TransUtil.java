/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.dao.impl;

import com.jiuqi.nr.dataresource.DataLinkKind;
import com.jiuqi.nr.dataresource.DataResourceKind;

public class TransUtil {
    public DataResourceKind transDataResourceKind(Integer value) {
        return value != null ? DataResourceKind.valueOf(value) : null;
    }

    public Integer transDataResourceKind(DataResourceKind value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public DataLinkKind transDataLinkKind(Integer value) {
        return value != null ? DataLinkKind.valueOf(value) : null;
    }

    public Integer transDataLinkKind(DataLinkKind value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }
}

