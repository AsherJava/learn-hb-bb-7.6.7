/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.utils.desensitization;

import com.jiuqi.va.formula.domain.desensitization.SensitiveType;
import com.jiuqi.va.formula.utils.desensitization.DataDesensitization;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class PhoneDesensitization
implements DataDesensitization {
    private static final Pattern PHONE_PATTERN = Pattern.compile("(\\d{3})\\d{4}(\\d{4})");
    private static final String REPLACEMENT = "$1****$2";

    @Override
    public SensitiveType name() {
        return SensitiveType.PHONE;
    }

    @Override
    public String desensitize(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }
        int length = value.length();
        if (length != 11) {
            return value;
        }
        Matcher matcher = PHONE_PATTERN.matcher(value);
        return matcher.replaceAll(REPLACEMENT);
    }
}

