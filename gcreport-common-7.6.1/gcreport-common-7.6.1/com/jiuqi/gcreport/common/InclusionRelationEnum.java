/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common;

import java.util.Collection;

public enum InclusionRelationEnum {
    INCLUDING("\u5305\u542b"),
    INCLUDED("\u88ab\u5305\u542b"),
    EQUAL("\u76f8\u7b49"),
    NONE("\u6ca1\u5305\u542b\u5173\u7cfb");

    private String inclusionRelation;

    private InclusionRelationEnum(String inclusionRelation) {
        this.inclusionRelation = inclusionRelation;
    }

    public String getInclusionRelation() {
        return this.inclusionRelation;
    }

    public static InclusionRelationEnum getInclusionRelation(Collection<String> c1, Collection<String> c2) {
        boolean including = false;
        boolean included = false;
        if (c1.containsAll(c2)) {
            including = true;
        }
        if (c2.containsAll(c1)) {
            included = true;
        }
        if (including && included) {
            return EQUAL;
        }
        if (including) {
            return INCLUDING;
        }
        if (included) {
            return INCLUDED;
        }
        return NONE;
    }
}

