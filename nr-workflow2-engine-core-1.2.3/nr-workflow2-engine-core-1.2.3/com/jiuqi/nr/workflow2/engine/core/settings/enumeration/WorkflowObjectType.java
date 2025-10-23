/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.enumeration;

public enum WorkflowObjectType {
    MAIN_DIMENSION("\u6309\u4e3b\u7ef4\u5ea6\u4e0a\u62a5"),
    MD_WITH_SFR("\u6309\u4e3b\u7ef4\u5ea6\u4e0a\u62a5+\u5355\u8868\u9000\u56de"),
    FORM("\u6309\u62a5\u8868\u4e0a\u62a5"),
    FORM_GROUP("\u6309\u62a5\u8868\u5206\u7ec4\u4e0a\u62a5");

    public final String title;

    private WorkflowObjectType(String title) {
        this.title = title;
    }
}

