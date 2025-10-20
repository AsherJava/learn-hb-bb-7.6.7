/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.inputdata.query.constant;

import com.jiuqi.common.base.util.StringUtils;

public enum EntitiesOrgMode {
    UNIONORG(1, "\u5408\u5e76\u5355\u4f4d"),
    DIFFERENCE(2, "\u5dee\u989d\u5355\u4f4d"),
    SINGLE(3, "\u5355\u6237\u5355\u4f4d");

    private String id;
    private int code;
    private String title;

    private EntitiesOrgMode(int code, String title) {
        this.id = StringUtils.toViewString((Object)code);
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public static EntitiesOrgMode findOrgType(String type) {
        if (StringUtils.isEmpty((String)type)) {
            return null;
        }
        for (EntitiesOrgMode itt : EntitiesOrgMode.values()) {
            if (itt.name().equalsIgnoreCase(type)) {
                return itt;
            }
            if (type.equals(String.valueOf(itt.getCode()))) {
                return itt;
            }
            if (!type.equals(itt.getTitle())) continue;
            return itt;
        }
        return null;
    }
}

