/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.samecontrol;

import com.jiuqi.common.base.util.UUIDUtils;

public class SameControlConsts {
    public static final String SAME_CONTROL_API_BASE_PATH = "/api/gcreport/v1/samecontrol/";
    public static final String ROOT_NAME = "\u540c\u63a7\u671f\u521d\u5408\u5e76\u89c4\u5219";
    public static final String ROOT_PARENT_ID = UUIDUtils.emptyUUIDStr();

    public static interface NodeType {
        public static final String ROOT = "root";
        public static final String GROUP = "group";
    }
}

