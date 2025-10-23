/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.datascheme.web.param;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class AuthQueryPM {
    private ResourceType resourceType;
    private PrivilegeType privilegeType;
    private String resourceId;

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public PrivilegeType getPrivilegeType() {
        return this.privilegeType;
    }

    public void setPrivilegeType(PrivilegeType privilegeType) {
        this.privilegeType = privilegeType;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public static enum ResourceType {
        SCHEME_GROUP("scheme_group"),
        SCHEME("scheme");

        private final String value;

        private ResourceType(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }

        @JsonCreator
        public static ResourceType fromValue(String value) {
            for (ResourceType type : ResourceType.values()) {
                if (!type.value.equals(value)) continue;
                return type;
            }
            return null;
        }
    }

    public static enum PrivilegeType {
        READ("read"),
        WRITE("write");

        private final String value;

        private PrivilegeType(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }

        @JsonCreator
        public static PrivilegeType fromValue(String value) {
            for (PrivilegeType type : PrivilegeType.values()) {
                if (!type.value.equals(value)) continue;
                return type;
            }
            return null;
        }
    }
}

