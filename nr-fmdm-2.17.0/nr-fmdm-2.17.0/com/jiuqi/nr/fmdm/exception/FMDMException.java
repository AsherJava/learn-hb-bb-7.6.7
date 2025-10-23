/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.fmdm.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FMDMException implements ErrorEnum
{
    REPEAT_COLUMN_CODE("ERROR-001", "\u5b9e\u4f53\u5b9a\u4e49\u4e0e\u5c01\u9762\u4ee3\u7801\u6269\u5c55\u8868\u6807\u8bc6\u91cd\u590d"),
    REPEAT_DATA_CODE("ERROR-002", "\u5b9e\u4f53\u6570\u636e\u8868\u4e0e\u5c01\u9762\u4ee3\u7801\u6269\u5c55\u8868\u6570\u636e\u6807\u8bc6\u91cd\u590d"),
    MORE_THAN_ONE_KEY("ERROR-003", "\u5c01\u9762\u4ee3\u7801\u6570\u636e\u67e5\u8be2\u65f6\u5c01\u9762\u4e3b\u952e\u8d85\u8fc71\u4e2a"),
    EMPTY_DIMENSION("ERROR-004", "\u672a\u8bbe\u7f6e\u7ef4\u5ea6\u503c\u6216\u4ece\u503c\u5217\u8868\u4e2d\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u7ef4\u5ea6\u503c"),
    EMPTY_DATA_SCHEME_DATA("ERROR-005", "\u5c01\u9762\u4ee3\u7801\u6269\u5c55\u8868\u672a\u627e\u5230\u7ed3\u679c"),
    DATA_SCHEME_DATA_QUERY("ERROR-006", "\u7269\u7406\u8868\u6570\u636e\u67e5\u8be2\u5f02\u5e38"),
    ENTITY_DATA_QUERY("ERROR-007", "\u5b9e\u4f53\u6570\u636e\u67e5\u8be2\u5f02\u5e38"),
    EMPTY_DATA("ERROR-008", "\u5c01\u9762\u6570\u636e\u4e3a\u7a7a");

    private String code;
    private String message;

    private FMDMException(String code, String message) {
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

