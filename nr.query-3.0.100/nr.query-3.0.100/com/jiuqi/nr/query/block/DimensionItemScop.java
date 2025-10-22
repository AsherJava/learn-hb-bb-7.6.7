/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

public enum DimensionItemScop {
    DIS_DEFAULT,
    DIS_SUBALL,
    DIS_SUBDIRECT,
    DIS_CURRENT,
    DIS_OWN,
    DIS_LEAF,
    DIS_OWNANDSUBDIRECT,
    DIS_OWNANDSUBALL;


    public static int getType(DimensionItemScop item) {
        if (item == DIS_DEFAULT) {
            return 0;
        }
        if (item == DIS_SUBALL) {
            return 1;
        }
        if (item == DIS_SUBDIRECT) {
            return 2;
        }
        if (item == DIS_CURRENT) {
            return 3;
        }
        if (item == DIS_OWN) {
            return 4;
        }
        if (item == DIS_LEAF) {
            return 5;
        }
        if (item == DIS_OWNANDSUBDIRECT) {
            return 6;
        }
        if (item == DIS_OWNANDSUBALL) {
            return 7;
        }
        return -1;
    }

    public static String getTitle(DimensionItemScop item) {
        if (item == DIS_DEFAULT) {
            return "\u9ed8\u8ba4";
        }
        if (item == DIS_SUBALL) {
            return "\u6240\u6709\u4e0b\u7ea7\u8282\u70b9";
        }
        if (item == DIS_SUBDIRECT) {
            return "\u76f4\u63a5\u4e0b\u7ea7\u8282\u70b9";
        }
        if (item == DIS_CURRENT) {
            return "\u672c\u7ea7\u8282\u70b9";
        }
        if (item == DIS_OWN) {
            return "\u672c\u8282\u70b9";
        }
        if (item == DIS_LEAF) {
            return "\u6240\u6709\u53f6\u5b50\u7ed3\u70b9";
        }
        if (item == DIS_OWNANDSUBDIRECT) {
            return "\u5f53\u524d\u8282\u70b9\u53ca\u76f4\u63a5\u4e0b\u7ea7";
        }
        if (item == DIS_OWNANDSUBALL) {
            return "\u5f53\u524d\u8282\u70b9\u53ca\u6240\u6709\u4e0b\u7ea7";
        }
        return "\u9ed8\u8ba4";
    }
}

