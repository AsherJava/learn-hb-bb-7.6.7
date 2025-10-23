/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core;

import java.util.List;

public interface ZbValidationRule {
    public String getMessage();

    public Integer getCompareType();

    public String getMin();

    public String getMax();

    public String getValue();

    public List<String> getInValues();
}

