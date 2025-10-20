/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.utils.desensitization;

import com.jiuqi.va.formula.domain.desensitization.SensitiveType;

public interface DataDesensitization {
    public SensitiveType name();

    public String desensitize(String var1);
}

