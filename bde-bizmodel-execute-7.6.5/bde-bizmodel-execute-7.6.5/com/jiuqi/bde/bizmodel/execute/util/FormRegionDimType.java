/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.bde.bizmodel.execute.util;

import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;

public class FormRegionDimType
implements IDimType {
    private final String NAME = "FORM_REGION";
    private final String TITLE = "\u62a5\u8868\u533a\u57df";

    private FormRegionDimType() {
    }

    public static FormRegionDimType getInstance() {
        return InnerInstance.INSTANCE;
    }

    public String getName() {
        return "FORM_REGION";
    }

    public String getTitle() {
        return "\u62a5\u8868\u533a\u57df";
    }

    private static class InnerInstance {
        private static final FormRegionDimType INSTANCE = new FormRegionDimType();

        private InnerInstance() {
        }
    }
}

