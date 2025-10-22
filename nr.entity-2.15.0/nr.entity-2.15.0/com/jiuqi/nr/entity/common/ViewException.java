/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.entity.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ViewException implements ErrorEnum
{
    QUERRY_ERROR("V-01", "\u67e5\u8be2\u89c6\u56fe\u5f02\u5e38"),
    DEPLOY_ERROR("V-02", "\u53d1\u5e03\u89c6\u56fe\u5f02\u5e38"),
    UPDATE_ERROR("V-03", "\u4fee\u6539\u89c6\u56fe\u5f02\u5e38"),
    DELETE_ERROR("V-04", "\u5220\u9664\u89c6\u56fe\u5f02\u5e38"),
    INSERT_ERROR("V-05", "\u63d2\u5165\u89c6\u56fe\u5f02\u5e38");

    private String code;
    private String message;

    private ViewException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMessage(String arg) {
        return new StringBuffer(this.message).append(arg).toString();
    }
}

