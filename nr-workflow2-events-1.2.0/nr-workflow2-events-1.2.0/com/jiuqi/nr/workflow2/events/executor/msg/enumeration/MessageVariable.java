/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.enumeration;

public enum MessageVariable {
    CURRENT_NODE_NAME("currentNodeName", "\u5f53\u524d\u8282\u70b9\u540d\u79f0"),
    FORM_SCHEME_NAME("formSchemeName", "\u62a5\u8868\u65b9\u6848\u540d\u79f0"),
    MD_NAME("mdName", "\u7ec4\u7ec7\u673a\u6784\u540d\u79f0"),
    REPORT_NAME("reportName", "\u62a5\u8868\u540d\u79f0"),
    GROUP_NAME("groupName", "\u5206\u7ec4\u540d\u79f0"),
    OPERATOR("operator", "\u64cd\u4f5c\u7528\u6237"),
    RECEIVER("receiver", "\u63a5\u6536\u4eba"),
    OPERATE_EXPLANATION("operateExplanation", "\u64cd\u4f5c\u8bf4\u660e"),
    TASK_NAME("taskName", "\u4efb\u52a1\u540d\u79f0"),
    PERIOD("period", "\u65f6\u671f"),
    ENTITY_CALIBER("entityCaliber", "\u5355\u4f4d\u53e3\u5f84");

    public final String code;
    public final String title;

    private MessageVariable(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

