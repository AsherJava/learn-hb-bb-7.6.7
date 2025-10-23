/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.transmission.data.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SchemeGroupException implements ErrorEnum
{
    QUERY_CHILDREN_GROUP_IDNULL_ERROR("G-01", "\u5206\u7ec4id\u4e3a\u7a7a"),
    QUERY_CHILDREN_GROUP_ROOT_ERROR("G-02", "\u83b7\u53d6\u6839\u5206\u7ec4\u9519\u8bef"),
    QUERY_CHILDREN_GROUP_LIST_ERROR("G-03", "\u83b7\u53d6\u5206\u7ec4\u4e0b\u7ea7\u5206\u7ec4\u9519\u8bef"),
    SEARCH_CHILDREN_GROUP_ERROR("G-04", "\u67e5\u8be2\u5206\u7ec4\u6570\u636e\u9879\u9519\u8bef"),
    INSERT_CHILDREN_GROUP_ERROR("G-05", "\u65b0\u589e\u5206\u7ec4\u9519\u8bef"),
    DELETE_CHILDREN_GROUP_ERROR("G-06", "\u5220\u9664\u5206\u7ec4\u9519\u8bef"),
    UPDATE_CHILDREN_GROUP_ERROR("G-07", "\u66f4\u65b0\u5206\u7ec4\u4fe1\u606f\u5931\u8d25"),
    UPDATE_GROUP_ERROR("G-08", "\u66f4\u65b0\u5206\u7ec4\u5931\u8d25");

    private String code;
    private String message;

    private SchemeGroupException(String code, String message) {
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

