/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.formtype.org.extend.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FormTypeOrgExtendError implements ErrorEnum
{
    ERROR_UNKNOWN_NATURE("000", "\u672a\u77e5\u5355\u4f4d\u6027\u8d28"),
    ERROR_NO_NATURE("001", "\u65e0\u6cd5\u83b7\u53d6\u5355\u4f4d\u7684\u5355\u4f4d\u6027\u8d28"),
    ERROR_DISABLE_CE("002", "\u975e\u96c6\u56e2\u6c47\u603b\u5355\u4f4d\u4e0b\u4e0d\u5141\u8bb8\u5b58\u5728\u5dee\u989d\u6216\u8005\u672c\u90e8\u5355\u4f4d"),
    ERROR_DISABLE_BZHZ("003", "\u96c6\u56e2\u6c47\u603b\u5355\u4f4d\u4e0b\u4e0d\u5141\u8bb8\u521b\u5efa\u6807\u51c6\u6c47\u603b\u5355\u4f4d"),
    ERROR_DISABLE_CHILDREN("004", "\u5355\u6237\\\u5dee\u989d\u5355\u4f4d\u4e0d\u5141\u8bb8\u521b\u5efa\u4e0b\u7ea7\u5355\u4f4d"),
    ERROR_CODE_CE("005", "\u5dee\u989d\u5355\u4f4d\u7684\u673a\u6784\u7f16\u7801\u5fc5\u987b\u4e0e\u96c6\u56e2\u6c47\u603b\u5355\u4f4d\u7684\u673a\u6784\u7f16\u7801\u4e00\u81f4"),
    ERROR_CODE_ORGCODE("006", "\u4e0e\u96c6\u56e2\u6c47\u603b\u5355\u4f4d\u7684\u673a\u6784\u7f16\u7801\u4e00\u81f4\u7684\u53ea\u80fd\u662f\u5dee\u989d\u6216\u8005\u672c\u90e8\u5355\u4f4d"),
    ERROR_CODE_ORGCODE_01("007", "\u4e0e\u96c6\u56e2\u6c47\u603b\u5355\u4f4d\u7684\u673a\u6784\u7f16\u7801\u4e00\u81f4\u7684\u5dee\u989d\u6216\u8005\u5355\u6237\u5355\u4f4d\u6700\u591a\u6709\u4e00\u4e2a"),
    ERROR_AUTO_GEN_CE("008", "\u5dee\u989d\u5355\u4f4d\u5fc5\u987b\u7531\u7cfb\u7edf\u81ea\u52a8\u751f\u6210"),
    ERROR_AUTO_GEN_DISABLE("009", "\u81ea\u52a8\u751f\u6210(\u5dee\u989d\\\u672c\u90e8)\u5355\u4f4d\u4e0d\u5141\u8bb8\u4fee\u6539\u62a5\u8868\u7c7b\u578b\u3001\u673a\u6784\u7f16\u7801\u548c\u6240\u5c5e\u4e0a\u7ea7"),
    ERROR_UPDATE_DISABLE("010", "\u6709\u4e0b\u7ea7\u5355\u4f4d\u4e0d\u5141\u8bb8\u4fee\u6539\u4e3a\u5355\u6237\u5355\u4f4d"),
    ERROR_UPDATE_HZ_DISABLE("011", "\u4e0b\u7ea7\u6709\u6807\u51c6\u6c47\u603b\u5355\u4f4d\u4e0d\u5141\u8bb8\u4fee\u6539\u4e3a\u96c6\u56e2\u6c47\u603b\u5355\u4f4d"),
    ERROR_ADD_GEN_DISABLE("012", "\u5dee\u989d\\\u672c\u90e8\u5355\u4f4d\u5fc5\u987b\u7531\u7cfb\u7edf\u81ea\u52a8\u751f\u6210"),
    ERROR_UPDATE_HZ_CHILDREN("013", "\u96c6\u56e2\u6c47\u603b\u5355\u4f4d\u4e0b\u7ea7\u53ea\u5141\u8bb8\u624b\u52a8\u521b\u5efa\u5355\u6237"),
    ERROR_ORGCODE("014", "\u673a\u6784\u7f16\u7801\u91cd\u590d");

    private String code;
    private String message;

    private FormTypeOrgExtendError(String code, String message) {
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

