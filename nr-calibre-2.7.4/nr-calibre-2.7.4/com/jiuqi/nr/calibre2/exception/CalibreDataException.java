/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.calibre2.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum CalibreDataException implements ErrorEnum
{
    QUERY_ROOT_ERROR("E-01", "\u53e3\u5f84\u9879\u6839\u6570\u636e\u67e5\u8be2\u9519\u8bef"),
    QUERY_CHILDREN_ERROR("E-02", "\u53e3\u5f84\u9879\u4e0b\u7ea7\u6570\u636e\u67e5\u8be2\u9519\u8bef"),
    SEARCH_ERROR("E-03", "\u641c\u7d22\u53e3\u5f84\u9879\u6839\u6570\u636e\u9519\u8bef"),
    INSERT_ERROR("E-04", "\u65b0\u5efa\u53e3\u5f84\u9879\u6570\u636e\u9519\u8bef"),
    UPDATE_ERROR("E-05", "\u4fee\u6539\u53e3\u5f84\u9879\u6570\u636e\u9519\u8bef"),
    DELETE_ERROR("E-06", "\u5220\u9664\u53e3\u5f84\u9879\u6570\u636e\u9519\u8bef"),
    EXPORT_ERROR("E-07", "\u53e3\u5f84\u9879\u6570\u636e\u5bfc\u51fa\u9519\u8bef"),
    IMPORT_FILE_TYPE_ERROR("E-08", "\u5bfc\u5165\u7684\u6587\u4ef6\u4e0d\u662fexcel\u6216et\u6587\u4ef6"),
    IMPORT_ERROR("E-09", "\u53e3\u5f84\u6570\u636e\u5bfc\u5165\u9519\u8bef"),
    FILE_ERROR("E-10", "\u53e3\u5f84\u5bfc\u5165\u6587\u4ef6\u6a21\u677f\u683c\u5f0f\u4e0d\u6b63\u786e"),
    DATA_ERROR("E-11", "\u53e3\u5f84\u5bfc\u5165\u6587\u4ef6\u6570\u636e\u5b58\u5728\u5f02\u5e38"),
    CALIBRE_CODE_ERROR("E-12", "\u5b58\u5728\u53e3\u5f84\u6570\u636e\u4ee3\u7801\u3001\u540d\u79f0\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u540e\u91cd\u65b0\u5bfc\u5165"),
    CALIBRE_NAME_ERROR("E-13", "\u5b58\u5728\u53e3\u5f84\u6570\u636e\u4e3anull"),
    CALIBRE_ERROR_DATA("E-14", "\u5b58\u5728\u5f02\u5e38\u53e3\u5f84\u6570\u636e"),
    CALIBRE_ENTITY_EMPTY("E-15", "\u5bfc\u5165\u7684\u6570\u636e\u8868\u91cc\u53e3\u5f84\u5173\u8054\u7684\u7ef4\u5ea6\u4ee3\u7801\u4e3a\u7a7a"),
    CALIBRE_ENTITY_ERROR("E-16", "\u5bfc\u5165\u7684\u6570\u636e\u8868\u91cc\u53e3\u5f84\u5173\u8054\u7684\u7ef4\u5ea6\u4ee3\u7801\u4e0e\u5f53\u524d\u53e3\u5f84\u4e0d\u4e00\u6837"),
    QUERY_DEFINE("E-17", "\u4e0d\u5b58\u5728\u7684\u53e3\u5f84\u5b9a\u4e49");

    private String code;
    private String message;

    private CalibreDataException(String code, String message) {
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

