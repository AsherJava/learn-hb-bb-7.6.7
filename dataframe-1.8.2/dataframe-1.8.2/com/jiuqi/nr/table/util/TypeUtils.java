/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.concurrent.Immutable
 */
package com.jiuqi.nr.table.util;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.regex.Pattern;
import javax.annotation.concurrent.Immutable;

@Immutable
public class TypeUtils {
    private static final String EMPTY = "";
    private static final String MISSING_IND_1 = "NaN";
    private static final String MISSING_IND_2 = "*";
    private static final String MISSING_IND_3 = "NA";
    private static final String MISSING_IND_4 = "null";
    private static final String MISSING_IND_5 = "N/A";
    public static final ImmutableList<String> MISSING_INDICATORS = ImmutableList.of((Object)"NaN", (Object)"*", (Object)"NA", (Object)"null", (Object)"N/A");
    private static final Pattern ZERO_DECIMAL_PATTERN = Pattern.compile("\\.0+$");

    private TypeUtils() {
    }

    public static String removeZeroDecimal(String str) {
        if (Strings.isNullOrEmpty((String)str)) {
            return str;
        }
        return ZERO_DECIMAL_PATTERN.matcher(str).replaceFirst(EMPTY);
    }
}

