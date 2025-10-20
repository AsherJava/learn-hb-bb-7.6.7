/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.bde.bizmodel.execute.util;

import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;

public class FloatRegionAnalyzeDimType
implements IDimType {
    private final String NAME = "FLOAT_REGION_ANALYZE";
    private final String TITLE = "\u6d6e\u52a8\u533a\u57df\u89e3\u6790";

    private FloatRegionAnalyzeDimType() {
    }

    public static FloatRegionAnalyzeDimType getInstance() {
        return InnerInstance.INSTANCE;
    }

    public String getName() {
        return "FLOAT_REGION_ANALYZE";
    }

    public String getTitle() {
        return "\u6d6e\u52a8\u533a\u57df\u89e3\u6790";
    }

    private static class InnerInstance {
        private static final FloatRegionAnalyzeDimType INSTANCE = new FloatRegionAnalyzeDimType();

        private InnerInstance() {
        }
    }
}

