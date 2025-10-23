/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldNameUtils {
    private static final Logger log = LoggerFactory.getLogger(FieldNameUtils.class);
    private final Set<String> names;
    private int count = 1;

    public FieldNameUtils(Set<String> names) {
        this.names = names == null ? new HashSet<String>() : names;
    }

    public String next() {
        if (this.names.isEmpty()) {
            String fieldName = "ZB_PROP_F" + this.count++;
            this.names.add(fieldName);
            log.debug("field name: {}", (Object)fieldName);
            return fieldName;
        }
        while (this.count < 9999) {
            String fieldName = "ZB_PROP_F" + this.count++;
            if (this.names.contains(fieldName)) continue;
            this.names.add(fieldName);
            log.debug("field name: {}", (Object)fieldName);
            return fieldName;
        }
        log.error("field name overflow");
        throw new RuntimeException("field name overflow");
    }
}

