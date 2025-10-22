/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.analysisreport.common;

import com.jiuqi.common.base.util.UUIDUtils;

public class AnalysisReportTemplateConsts {
    public static final String ROOT_PARENT_ID = UUIDUtils.emptyUUIDStr();
    public static final String ROOT_NAME = "\u5206\u6790\u62a5\u544a";

    public static interface NodeType {
        public static final String ROOT = "root";
        public static final String GROUP = "group";
        public static final String ITEM = "item";
    }
}

