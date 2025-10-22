/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.enumeration;

public enum GroupServiceState {
    HAS_CHILD_GROUP("has_child_group", "\u5206\u7ec4\u4e0b\u6709\u5b50\u5206\u7ec4\uff0c\u4e0d\u80fd\u88ab\u5220\u9664"),
    HAS_CHILD_SCHEME("has_child_scheme", "\u5206\u7ec4\u4e0b\u6709\u6c47\u603b\u65b9\u6848\uff0c\u4e0d\u80fd\u88ab\u5220\u9664"),
    INVALID_TASK("invalid_task", "\u6765\u6e90\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    INVALID_GROUP_NAME("invalid_group_name", "\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SUCCESS("success", "\u6210\u529f\uff01"),
    FAIL("fail", "\u5931\u8d25\uff01"),
    CREATE_SUCCESS("success", "\u5206\u7ec4\u521b\u5efa\u6210\u529f\uff01"),
    CREATE_FAIL("fail", "\u5206\u7ec4\u521b\u5efa\u5931\u8d25\uff01"),
    DELETE_SUCCESS("success", "\u5206\u7ec4\u5df2\u5220\u9664\uff01"),
    DELETE_FAIL("fail", "\u5206\u7ec4\u5220\u9664\u5931\u8d25\uff01"),
    MODIFY_SUCCESS("success", "\u91cd\u547d\u540d\u5206\u7ec4\u6210\u529f\uff01"),
    MODIFY_FAIL("fail", "\u91cd\u547d\u540d\u5206\u7ec4\u5931\u8d25\uff01");

    public String code;
    public String title;

    private GroupServiceState(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

