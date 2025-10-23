/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.enumeration;

public enum IProcessEventExecuteAttrKeys {
    ENV_DIMENSION_VALUE_MAP("envDimensionValueMap", "\u627f\u63a5\u5f55\u5165\u7684\u73af\u5883\u53d8\u91cf\uff1aDimensionMap"),
    EXECUTE_TASK_PARAM("executeTaskParam", "\u627f\u63a5\u5f55\u5165\u7684\u73af\u5883\u53d8\u91cf\uff1a\u5355\u4e2a\u6267\u884c\u4e0a\u62a5\u65f6\u73af\u5883\u53c2\u6570"),
    BATCH_EXECUTE_TASK_PARAM("batchExecuteTaskParam", "\u627f\u63a5\u5f55\u5165\u7684\u73af\u5883\u53d8\u91cf\uff1a\u6279\u91cf\u6267\u884c\u4e0a\u62a5\u65f6\u73af\u5883\u53c2\u6570"),
    SEND_MAIL("isSendMail", "\u901a\u77e5\u4e8b\u4ef6\u4e2d-\u662f\u5426\u53d1\u9001\u90ae\u4ef6"),
    IS_TODO_ENABLED("isTodoEnabled", "\u662f\u5426\u542f\u7528\u5f85\u529e: \u4efb\u52a1\u7684\u6d41\u7a0b\u8bbe\u7f6e\u4e0a\u548c\u7cfb\u7edf\u9009\u9879\u4e2d\u540c\u65f6\u542f\u7528\u5f85\u529e\u624d\u7b97\u542f\u7528");

    public final String attrKey;
    public final String description;

    private IProcessEventExecuteAttrKeys(String attrKey, String description) {
        this.attrKey = attrKey;
        this.description = description;
    }
}

