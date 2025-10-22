/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.enumeration;

public enum SchemeServiceState {
    INVALID_SCHEME_DEFINE("invalid_scheme_define", "\u65e0\u6548\u7684\u6c47\u603b\u65b9\u6848\u5b9a\u4e49\uff0c\u8bf7\u4ed4\u7ec6\u68c0\u67e5\u5b9a\u4e49\u662f\u5426\u7b26\u5408\u8981\u6c42\uff01"),
    INVALID_SCHEME_KEY("invalid_scheme_key", "\u65e0\u6548\u7684\u6c47\u603b\u65b9\u6848\u4e3b\u952e\uff0c\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u8be5\u6c47\u603b\u65b9\u6848\uff01"),
    INVALID_TASK("invalid_task", "\u65e0\u6548\u7684\u6765\u6e90\u4efb\u52a1!"),
    INVALID_TARGET_CONDITION_DIM("invalid_target_condition_dim", "\u76ee\u6807\u7ef4\u5ea6\u2014\u2014\u81ea\u5b9a\u4e49\u5206\u7c7b\u6c47\u603b\u6761\u4ef6\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SUCCESS("success", "\u6210\u529f"),
    FAIL("fail", "\u5931\u8d25");

    public String code;
    public String title;

    private SchemeServiceState(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

