/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.unionrule.constant;

import com.jiuqi.common.base.util.UUIDUtils;

public final class RuleConst {
    public static final String ROOT_PARENT_ID = UUIDUtils.emptyUUIDStr();
    public static final String ROOT_NAME = "\u5408\u5e76\u89c4\u5219";
    public static final String BUSINESS_TYPE_TABLE_NAME = "DES_GCBUSINESSTYPE";
    public static final String GC_OFFSETVCHRITEM_TABLE_NAME = "GC_OFFSETVCHRITEM";

    private RuleConst() {
    }

    public static interface NodeType {
        public static final String ROOT = "root";
        public static final String GROUP = "group";
    }
}

