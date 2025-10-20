/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.utils.desensitization;

import com.jiuqi.va.formula.domain.desensitization.SensitiveType;
import com.jiuqi.va.formula.utils.desensitization.DataDesensitization;
import org.springframework.util.StringUtils;

public class BankCardDataDesensitization
implements DataDesensitization {
    private static final String MASK_TEMPLATE = "**** **** **** ";
    private static final int VISIBLE_DIGITS = 4;

    @Override
    public SensitiveType name() {
        return SensitiveType.BANK_CARD;
    }

    @Override
    public String desensitize(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }
        int length = value.length();
        if (length < 16 || length > 19) {
            return value;
        }
        int startIndex = length - 4;
        return MASK_TEMPLATE + value.substring(startIndex, length);
    }
}

