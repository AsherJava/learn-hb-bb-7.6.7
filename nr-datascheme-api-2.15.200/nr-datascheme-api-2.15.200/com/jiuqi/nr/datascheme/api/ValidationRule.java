/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.io.Serializable;
import java.util.List;

public interface ValidationRule
extends Serializable {
    public String getVerification();

    public String getMessage();

    public CompareType getCompareType();

    public String getLeftValue();

    public String getRightValue();

    public String getValue();

    public List<String> getInValues();

    public String toVerification(String var1, String var2, DataFieldType var3);
}

