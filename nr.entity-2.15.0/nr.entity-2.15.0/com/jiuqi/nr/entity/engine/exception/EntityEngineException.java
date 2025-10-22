/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.entity.engine.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum EntityEngineException implements ErrorEnum
{
    ADAPTER_QUERY_ERROR("ADAPTER-01", "\u9002\u914d\u5668\u67e5\u8be2\u5f02\u5e38"),
    BUILD_REFER_INFO("ADAPTER-02", "\u6784\u5efa\u5b9e\u4f53\u4f9d\u8d56\u7684\u53c2\u6570\u65f6\u5f02\u5e38");

    private String code;
    private String message;

    private EntityEngineException(String code, String message) {
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

