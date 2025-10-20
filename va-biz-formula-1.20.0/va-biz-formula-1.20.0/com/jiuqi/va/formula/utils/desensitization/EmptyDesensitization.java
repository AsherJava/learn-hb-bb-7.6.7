/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.utils.desensitization;

import com.jiuqi.va.formula.domain.desensitization.SensitiveType;
import com.jiuqi.va.formula.utils.desensitization.DataDesensitization;

public class EmptyDesensitization
implements DataDesensitization {
    private static final EmptyDesensitization EMPTY_DESENSITIZATION = new EmptyDesensitization();

    public static DataDesensitization getEmptyDesensitization() {
        return EMPTY_DESENSITIZATION;
    }

    @Override
    public SensitiveType name() {
        return SensitiveType.NONE;
    }

    @Override
    public String desensitize(String value) {
        return value;
    }
}

