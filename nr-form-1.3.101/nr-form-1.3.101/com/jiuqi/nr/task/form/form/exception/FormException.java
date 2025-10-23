/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.form.form.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FormException implements ErrorEnum
{
    FORM_ERROE_001("001", "\u62a5\u8868\u5206\u7ec4\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u62a5\u8868\u5206\u7ec4"),
    FORM_ERROE_002("002", "\u62a5\u8868\u79fb\u52a8\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u62a5\u8868\u987a\u5e8f"),
    FORM_ERROE_003("003", "\u62a5\u8868\u67e5\u8be2\u4e3a\u7a7a"),
    FORM_ERROE_004("004", "\u5220\u9664\u62a5\u8868\u5931\u8d25"),
    FORM_ERROE_005("005", "\u67e5\u8be2\u6307\u6807\u6811\u5f62\u5931\u8d25"),
    FORM_ERROE_006("006", "\u67e5\u8be2\u6307\u6807\u5931\u8d25"),
    FORM_ERROE_007("007", "\u65b0\u5efa\u62a5\u8868\u5f02\u5e38"),
    FORM_ERROE_008("008", "\u66f4\u65b0\u62a5\u8868\u5f02\u5e38"),
    FORM_ERROE_009("009", "\u79fb\u52a8\u62a5\u8868\u5f02\u5e38"),
    FORM_ERROE_010("010", "\u5220\u9664\u8868\u5355\u5f15\u7528\u7684\u57fa\u7840\u6570\u636e\u5931\u8d25"),
    FORM_GROUP_ERROE_001("FORM_GROUP_001", "\u65b0\u589e\u62a5\u8868\u5206\u7ec4\u5f02\u5e38"),
    FORM_GROUP_ERROE_001_1("FORM_GROUP_001_1", "\u6dfb\u52a0\u5931\u8d25\uff0c\u5206\u7ec4\u6807\u9898\u91cd\u590d"),
    FORM_GROUP_ERROE_002("FORM_GROUP_002", "\u66f4\u65b0\u62a5\u8868\u5206\u7ec4\u5f02\u5e38"),
    FORM_GROUP_ERROE_002_1("FORM_GROUP_002_1", "\u5206\u7ec4\u6807\u9898\u91cd\u590d\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165"),
    FORM_GROUP_ERROE_003("FORM_GROUP_003", "\u62a5\u8868\u5206\u7ec4\u5220\u9664\u5f02\u5e38"),
    FORM_GROUP_ERROE_004("FORM_GROUP_004", "\u79fb\u52a8\u62a5\u8868\u5206\u7ec4\u5f02\u5e38"),
    FORM_GROUP_ERROE_005("FORM_GROUP_004", "\u79fb\u52a8\u62a5\u8868\u5206\u7ec4\u5f02\u5e38"),
    FORM_TREE_ERROE_001("001", "\u62a5\u8868\u6811\u578b\u67e5\u8be2\u5931\u8d25");

    private String code;
    private String message;

    private FormException(String code, String message) {
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

