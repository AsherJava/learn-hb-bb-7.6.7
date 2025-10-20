/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.definition.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ParamResourceType {
    TASK("nr-task", "\u4efb\u52a1", false),
    FORM("nr-form", "\u62a5\u8868", true),
    FORMULA("nr-formula", "\u516c\u5f0f", true),
    PRINT_TEMPLATE("nr-print-template", "\u6253\u5370\u6a21\u677f", true),
    REPORT_TEMPLATE("nr-report-template", "\u62a5\u544a\u6a21\u677f", true);

    @JsonValue
    private final String id;
    private final String name;
    private final boolean needDeploy;

    private ParamResourceType(String id, String name, boolean needDeploy) {
        this.id = id;
        this.name = name;
        this.needDeploy = needDeploy;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isNeedDeploy() {
        return this.needDeploy;
    }

    @JsonCreator
    public static ParamResourceType fromId(String id) {
        for (ParamResourceType type : ParamResourceType.values()) {
            if (!type.id.equals(id)) continue;
            return type;
        }
        return null;
    }
}

