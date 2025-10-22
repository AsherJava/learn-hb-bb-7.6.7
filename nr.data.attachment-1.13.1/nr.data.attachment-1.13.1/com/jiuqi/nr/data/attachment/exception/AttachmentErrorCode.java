/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.exception.ErrorCode
 *  com.jiuqi.nr.data.common.exception.ErrorCodeEnum
 */
package com.jiuqi.nr.data.attachment.exception;

import com.jiuqi.nr.data.common.exception.ErrorCode;
import com.jiuqi.nr.data.common.exception.ErrorCodeEnum;

public enum AttachmentErrorCode implements ErrorCode
{
    ATTA_UNSPECIFIED("1000", "\u672a\u77e5\u5f02\u5e38"),
    ATTA_PARAM_ERROR("1001", "\u5165\u53c2\u5f02\u5e38,\u8bf7\u68c0\u67e5\u5165\u53c2\u540e\u518d\u6b21\u8c03\u7528"),
    ATTA_QUERY_ERROR("1002", "\u67e5\u8be2\u5f02\u5e38");

    private final String code;
    private final String description;

    private AttachmentErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static AttachmentErrorCode getByCode(String code) {
        for (AttachmentErrorCode value : AttachmentErrorCode.values()) {
            if (!value.getCode().equals(code)) continue;
            return value;
        }
        return ATTA_UNSPECIFIED;
    }

    public static Boolean contains(String code) {
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (!value.getCode().equals(code)) continue;
            return true;
        }
        return false;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String bizName() {
        return "\u9644\u4ef6\u5f02\u5e38";
    }
}

