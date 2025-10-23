/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public enum FinalaccountQueryAuthResourceType {
    FQ_TASK("ft_"),
    FQ_FROM_SCHEME("ffs_"),
    FQ_GROUP("fg_"),
    FQ_MODEL_NODE("fmn_");

    private final String prefix;
    private static final Map<String, FinalaccountQueryAuthResourceType> VALUE_MAP;

    private FinalaccountQueryAuthResourceType(String prefix) {
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

    public static String toObjectId(String resourceId, String prefix) {
        Assert.notNull((Object)resourceId, "resourceId must not be null.");
        return resourceId.substring(prefix.length());
    }

    public static FinalaccountQueryAuthResourceType parseFrom(String resourceId) {
        Assert.notNull((Object)resourceId, "resourceId must not be null.");
        int splitPosition = resourceId.indexOf("_");
        if (splitPosition < 0) {
            throw new UnsupportedOperationException("unrecognized task resource id: " + resourceId);
        }
        String prefix = resourceId.substring(0, splitPosition + 1);
        FinalaccountQueryAuthResourceType type = VALUE_MAP.get(prefix);
        if (type == null) {
            throw new UnsupportedOperationException("unrecognized task resource id: " + resourceId);
        }
        return type;
    }

    static {
        VALUE_MAP = Arrays.stream(FinalaccountQueryAuthResourceType.values()).collect(Collectors.toMap(FinalaccountQueryAuthResourceType::getPrefix, t -> t));
    }
}

