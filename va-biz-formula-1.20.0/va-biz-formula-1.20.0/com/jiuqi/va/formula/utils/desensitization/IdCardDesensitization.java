/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.utils.desensitization;

import com.jiuqi.va.formula.domain.desensitization.SensitiveType;
import com.jiuqi.va.formula.utils.desensitization.DataDesensitization;
import org.springframework.util.StringUtils;

public class IdCardDesensitization
implements DataDesensitization {
    @Override
    public SensitiveType name() {
        return SensitiveType.ID_CARD;
    }

    @Override
    public String desensitize(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }
        int length = value.length();
        if (length < 11) {
            return value;
        }
        return value.substring(0, 2) + "**************" + value.substring(length - 2);
    }
}

