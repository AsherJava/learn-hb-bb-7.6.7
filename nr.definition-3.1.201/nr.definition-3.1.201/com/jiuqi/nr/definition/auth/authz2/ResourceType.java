/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.auth.authz2;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public enum ResourceType {
    TASK("tsk_"),
    TASK_GROUP("tskg_"),
    FORM_SCHEME("fms_"),
    FORM_GROUP("fmg_"),
    FORM("fm_"),
    FORMULA_SCHEME("fls_"),
    PRINT_SCHEME("prts_"),
    ALL_FORM("afm_"),
    ALL_FORMULA_SCHEME("afls_"),
    ALL_PRINT_SCHEME("aprts_"),
    ALL_TASK_ORG_STRUCTURE("atsko_"),
    TASK_ORG_STRUCTURE("tsko_");

    private static final Map<String, ResourceType> VALUE_MAP;
    private String prefix;

    private ResourceType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String toResourceId(String objectId) {
        return this.prefix.concat(objectId);
    }

    public String toObjectId(String resourceId) {
        Assert.notNull((Object)resourceId, "resourceId must not be null.");
        return resourceId.substring(this.prefix.length());
    }

    public static ResourceType parseFrom(String resourceId) {
        Assert.notNull((Object)resourceId, "resourceId must not be null.");
        int splitPosition = resourceId.indexOf("_");
        if (splitPosition < 0) {
            throw new UnsupportedOperationException("unrecognized task resource id: " + resourceId);
        }
        String prefix = resourceId.substring(0, splitPosition + 1);
        ResourceType type = VALUE_MAP.get(prefix);
        if (type == null) {
            throw new UnsupportedOperationException("unrecognized task resource id: " + resourceId);
        }
        return type;
    }

    static {
        VALUE_MAP = Arrays.stream(ResourceType.values()).collect(Collectors.toMap(t -> t.getPrefix(), t -> t));
    }
}

