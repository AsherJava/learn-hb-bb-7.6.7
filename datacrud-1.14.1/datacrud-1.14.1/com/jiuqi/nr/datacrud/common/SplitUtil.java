/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.StringUtils;

public class SplitUtil {
    private SplitUtil() {
    }

    public static List<String> split(String value) {
        if (StringUtils.hasLength(value)) {
            String[] split;
            ArrayList<String> values = new ArrayList<String>();
            for (String s : split = value.split(";")) {
                if (!StringUtils.hasLength(s)) continue;
                values.add(s);
            }
            return values;
        }
        return Collections.emptyList();
    }
}

