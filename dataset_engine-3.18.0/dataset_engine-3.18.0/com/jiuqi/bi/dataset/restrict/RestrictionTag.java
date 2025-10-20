/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.restrict;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestrictionTag {
    public static final String CURRENT = "CURRENT";
    public static final String CURRENT_ALIAS = "CUR";
    public static final String ALL = "ALL";
    public static final String MEMBERS = "MEMBERS";
    public static final String MEMBERS_ALIAS = "MB";
    public static final String NEXT = "NEXT";
    public static final String PREV = "PREV";
    public static final String LASTPERIOD = "LASTPERIOD";
    public static final String CURPERIOD = "CURPERIOD";
    public static final String CURPERIOD_ALIAS = "CURRENTPERIOD";
    public static final Set<String> TAGS = new HashSet<String>();
    public static final List<String> TAG_NAMES;

    private RestrictionTag() {
    }

    public static boolean isCURRENT(String tag) {
        return CURRENT.equalsIgnoreCase(tag) || CURRENT_ALIAS.equalsIgnoreCase(tag);
    }

    public static boolean isALL(String tag) {
        return ALL.equalsIgnoreCase(tag);
    }

    public static boolean isMB(String tag) {
        return MEMBERS.equalsIgnoreCase(tag) || MEMBERS_ALIAS.equalsIgnoreCase(tag);
    }

    public static boolean isNEXT(String tag) {
        return NEXT.equalsIgnoreCase(tag);
    }

    public static boolean isPREV(String tag) {
        return PREV.equalsIgnoreCase(tag);
    }

    public static boolean isLASTPERIOD(String tag) {
        return LASTPERIOD.equalsIgnoreCase(tag);
    }

    public static boolean isCURPERIOD(String tag) {
        return CURPERIOD.equalsIgnoreCase(tag) || CURPERIOD_ALIAS.equalsIgnoreCase(tag);
    }

    public static boolean isConstValue(String tag) {
        return RestrictionTag.isCURPERIOD(tag) || RestrictionTag.isLASTPERIOD(tag);
    }

    public static String aliasOf(String tag) {
        if (CURRENT.equalsIgnoreCase(tag)) {
            return CURRENT_ALIAS;
        }
        if (MEMBERS.equalsIgnoreCase(tag)) {
            return MEMBERS_ALIAS;
        }
        if (CURPERIOD.equalsIgnoreCase(tag)) {
            return CURPERIOD_ALIAS;
        }
        return null;
    }

    static {
        TAGS.add(CURRENT);
        TAGS.add(CURRENT_ALIAS);
        TAGS.add(ALL);
        TAGS.add(MEMBERS);
        TAGS.add(MEMBERS_ALIAS);
        TAGS.add(NEXT);
        TAGS.add(PREV);
        TAGS.add(LASTPERIOD);
        TAGS.add(CURPERIOD);
        TAGS.add(CURPERIOD_ALIAS);
        TAG_NAMES = Arrays.asList(CURRENT, ALL, MEMBERS, NEXT, PREV, LASTPERIOD, CURPERIOD);
    }
}

