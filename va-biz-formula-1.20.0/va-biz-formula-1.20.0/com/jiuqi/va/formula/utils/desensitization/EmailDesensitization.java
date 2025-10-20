/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.utils.desensitization;

import com.jiuqi.va.formula.domain.desensitization.SensitiveType;
import com.jiuqi.va.formula.utils.desensitization.DataDesensitization;
import org.springframework.util.StringUtils;

public class EmailDesensitization
implements DataDesensitization {
    private static final char CH_EMAIL = '@';

    @Override
    public SensitiveType name() {
        return SensitiveType.EMAIL;
    }

    @Override
    public String desensitize(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }
        int atIndex = value.indexOf(64);
        if (atIndex < 2) {
            return value;
        }
        return value.substring(0, 2) + "****" + value.substring(atIndex);
    }
}

