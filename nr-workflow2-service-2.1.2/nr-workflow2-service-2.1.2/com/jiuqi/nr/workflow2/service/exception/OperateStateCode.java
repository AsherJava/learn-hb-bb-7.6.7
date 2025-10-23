/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.exception;

public enum OperateStateCode {
    OPT_SUCCESS("\u64cd\u4f5c\u6210\u529f"),
    OPT_FAILURE("\u64cd\u4f5c\u5931\u8d25"),
    ERR_TASK_NOT_FOUND("\u8fd0\u884c\u671f\u3010\u62a5\u8868\u4efb\u52a1\u3011\u4e0d\u5b58\u5728"),
    ERR_PROCESS_NOT_ENABLED("\u8fd0\u884c\u671f\u3010\u62a5\u8868\u4efb\u52a1\u3011\u672a\u542f\u7528\u6d41\u7a0b"),
    ERR_FORM_SCHEME_NOT_FOUND("\u8fd0\u884c\u671f\u3010\u62a5\u8868\u65b9\u6848\u3011\u4e0d\u5b58\u5728"),
    ERR_DIMENSION_NAME_NOT_FOUND("\u7ef4\u5ea6\u540d\u79f0\u4e0d\u80fd\u7a7a"),
    ERR_DIMENSION_KEY_NOT_FOUND("\u7ef4\u5ea6\u5b9e\u4f53ID\u4e0d\u80fd\u4e3a\u7a7a\uff08\u5b9e\u4f53ID\u4e0d\u80fd\u4e3a\u7a7a\uff09"),
    ERR_DIMENSION_VALUE_NOT_FOUND("\u7ef4\u5ea6\u503c\u4e0d\u80fd\u4e3a\u7a7a"),
    ERR_UNIT_DIM_VALUE_CAN_NOT_BE_NULL("\u5355\u4f4d\u7ef4\u5ea6\u503c\u4e0d\u80fd\u4e3a\u7a7a"),
    ERR_PERIOD_DIM_VALUE_CAN_NOT_BE_NULL("\u65f6\u671f\u7ef4\u5ea6\u503c\u4e0d\u80fd\u4e3a\u7a7a"),
    ERR_INSTANCE_NOT_FOUND("\u5f53\u524d\u6d41\u7a0b\u5b9e\u4f8b\u4e0d\u5b58\u5728"),
    ERR_UNIT_DIM_NOT_FOUND("\u5355\u4f4d\u7ef4\u5ea6\u4e0d\u5b58\u5728"),
    ERR_SITUATION_DIM_NOT_FOUND("\u60c5\u666f\u7ef4\u5ea6\u4e0d\u5b58\u5728");

    public final String description;

    private OperateStateCode(String description) {
        this.description = description;
    }
}

