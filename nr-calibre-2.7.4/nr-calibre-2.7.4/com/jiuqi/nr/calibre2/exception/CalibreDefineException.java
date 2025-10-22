/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.calibre2.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum CalibreDefineException implements ErrorEnum
{
    MOVE_CALIBRE_ERROR("D-01", "\u79fb\u52a8\u65f6\u9009\u62e9\u7684\u53e3\u5f84\u4e0d\u8fde\u7eed"),
    CALIBRE_CODE_NULL_ERROR("D-02", "\u53e3\u5f84\u7684code\u4e3a\u7a7a"),
    QUERY_CHILDREN_IDNULL_ERROR("D-03", "\u83b7\u53d6\u53e3\u5f84id\u4e3a\u7a7a"),
    SEARCH_CHILDREN_ID_ERROR("D-04", "\u83b7\u53d6\u53e3\u5f84\u6570\u636e\u9879\u9519\u8bef"),
    INSERT_CHILDREN_ERROR("D-05", "\u6dfb\u52a0\u53e3\u5f84\u5931\u8d25"),
    QUERY_CHILDREN_LIST_ERROR("D-06", "\u83b7\u53d6\u53e3\u5f84\u5206\u7ec4\u4e0b\u7684\u53e3\u5f84\u9519\u8bef"),
    DELETE_CHILDREN_ERROR("D-07", "\u5220\u9664\u53e3\u5f84\u5931\u8d25"),
    UPDATE_CHILDREN_ERROR("D-08", "\u66f4\u65b0\u53e3\u5f84\u5931\u8d25"),
    CHECK_CHILDREN_ERROR("D-09", "\u53e3\u5f84\u5b58\u5728\u5c5e\u6027\u4e3anull"),
    INSERT_COPY_ERROR("D-10", "\u53e3\u5f84\u590d\u5236\u5931\u8d25");

    private String code;
    private String message;

    private CalibreDefineException(String code, String message) {
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

