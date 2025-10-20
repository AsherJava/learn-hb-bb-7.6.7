/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend.service;

import com.jiuqi.va.extend.domain.BillReuseField;
import java.util.ArrayList;
import java.util.List;

public interface BillCopyActionExtend {
    default public List<BillReuseField> filterFields(List<BillReuseField> fields) {
        return fields;
    }

    default public List<String> notCopyTables() {
        return new ArrayList<String>();
    }

    default public List<String> notCopyModels() {
        return new ArrayList<String>();
    }
}

