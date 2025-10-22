/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType;
import java.util.List;

public interface SchemeRangeForm {
    public RangeFormType getRangeFormType();

    public List<String> getFormList();

    public String valueToClob();
}

