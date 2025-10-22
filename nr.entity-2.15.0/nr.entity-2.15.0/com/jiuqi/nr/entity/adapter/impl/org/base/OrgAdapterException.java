/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.entity.adapter.impl.org.base;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum OrgAdapterException implements ErrorEnum
{
    ADAPTER_ORG_EXPORT_DEFINE("ADAPTER-ORG-01", "\u7ec4\u7ec7\u673a\u6784\u9002\u914d\u5668\u5bfc\u51fa\u7ec4\u7ec7\u673a\u6784\u5b9a\u4e49\u5f02\u5e38"),
    ADAPTER_ORG_EXPORT_DATA("ADAPTER-ORG-02", "\u7ec4\u7ec7\u673a\u6784\u9002\u914d\u5668\u5bfc\u51fa\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u5f02\u5e38"),
    ADAPTER_ORG_IMPORT_DEFINE("ADAPTER-ORG-03", "\u7ec4\u7ec7\u673a\u6784\u9002\u914d\u5668\u5bfc\u5165\u7ec4\u7ec7\u673a\u6784\u5b9a\u4e49\u5f02\u5e38"),
    ADAPTER_ORG_IMPORT_DATA("ADAPTER-ORG-04", "\u7ec4\u7ec7\u673a\u6784\u9002\u914d\u5668\u5bfc\u5165\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u5f02\u5e38");

    private String code;
    private String message;

    private OrgAdapterException(String code, String message) {
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

