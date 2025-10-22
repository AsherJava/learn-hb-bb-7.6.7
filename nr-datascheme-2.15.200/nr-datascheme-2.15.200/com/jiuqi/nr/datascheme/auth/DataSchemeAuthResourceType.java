/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public enum DataSchemeAuthResourceType {
    DATA_SCHEME_GROUP("dsg_"),
    DATA_SCHEME("ds_"),
    DATA_GROUP("dg_"),
    DATA_TABLE("dt_"),
    DATA_FIELD("df_");

    private final String prefix;
    private static final Map<String, DataSchemeAuthResourceType> VALUE_MAP;

    private DataSchemeAuthResourceType(String prefix) {
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

    public static DataSchemeAuthResourceType parseFrom(String resourceId) {
        Assert.notNull((Object)resourceId, "resourceId must not be null.");
        int splitPosition = resourceId.indexOf("_");
        if (splitPosition < 0) {
            throw new UnsupportedOperationException("unrecognized task resource id: " + resourceId);
        }
        String prefix = resourceId.substring(0, splitPosition + 1);
        DataSchemeAuthResourceType type = VALUE_MAP.get(prefix);
        if (type == null) {
            throw new UnsupportedOperationException("unrecognized task resource id: " + resourceId);
        }
        return type;
    }

    static {
        VALUE_MAP = Arrays.stream(DataSchemeAuthResourceType.values()).collect(Collectors.toMap(DataSchemeAuthResourceType::getPrefix, t -> t));
    }
}

