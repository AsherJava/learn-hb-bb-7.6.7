/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.zb.scheme.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ZbSchemeErrorEnum implements ErrorEnum
{
    ZS_ERROR_001("ZS_ERROR_001", "\u5931\u8d25"),
    DELETE_ERROR("ZS_ERROR_002", "\u5220\u9664\u5931\u8d25"),
    UPDATE_ERROR("ZS_ERROR_003", "\u4fee\u6539\u5931\u8d25"),
    SELECT_ERROR("ZS_ERROR_004", "\u67e5\u8be2\u5931\u8d25"),
    INSERT_ERROR("ZS_ERROR_005", "\u65b0\u589e\u5931\u8d25"),
    ZB_CHECK_ERROR("ZS_CHECK_ERROR_001", "\u6307\u6807\u9884\u68c0\u67e5\u5931\u8d25"),
    ZB_CHECK_ASYNC_ERROR("ZS_CHECK_ERROR_002", "\u6307\u6807\u9884\u68c0\u67e5\u5931\u8d25"),
    ZB_CHECK_ASYNC_PROGRESS_ERROR("ZS_CHECK_ERROR_003", "\u6307\u6807\u9884\u68c0\u67e5\u8fdb\u5ea6\u67e5\u8be2\u5931\u8d25"),
    ZB_CHECK_QUERY_ERROR("ZS_CHECK_ERROR_004", "\u9884\u68c0\u67e5\u7ed3\u679c\u67e5\u8be2\u5931\u8d25"),
    ZB_CHECK_TREE_ERROR("ZS_CHECK_ERROR_005", "\u9884\u68c0\u67e5\u7ed3\u679c\u6811\u5f62\u67e5\u8be2\u5931\u8d25"),
    ZB_CHECK_GENERATE_ERROR("ZS_CHECK_ERROR_006", "\u9006\u5411\u751f\u6210\u6307\u6807\u5931\u8d25"),
    ZB_CHECK_GENERATE_PROGRESS("ZS_CHECK_ERROR_007", "\u9006\u5411\u751f\u6210\u6307\u6807\u8fdb\u5ea6\u67e5\u8be2\u5931\u8d25"),
    ZB_CHECK_EXPORT_ERROR("ZS_CHECK_ERROR_008", "\u9884\u68c0\u67e5\u7ed3\u679c\u5bfc\u51fa\u5931\u8d25");

    private final String code;
    private final String message;

    private ZbSchemeErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

