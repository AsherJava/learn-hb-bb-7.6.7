/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.api.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum EntityException implements ErrorEnum
{
    QUERY("101", "\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38"),
    TREE_INIT("task_entity_tree_1001", "\u5b9e\u4f53\u6811\u521d\u59cb\u5316\u53d1\u751f\u5f02\u5e38"),
    TREE_LOAD_CHILDREN("task_entity_tree_1002", "\u5b9e\u4f53\u6811\u52a0\u8f7d\u5b50\u8282\u70b9\u53d1\u751f\u5f02\u5e38"),
    TREE_SEARCH("task_entity_tree_1003", "\u5b9e\u4f53\u6811\u641c\u7d22\u53d1\u751f\u5f02\u5e38"),
    TREE_LOCATE("task_entity_tree_1004", "\u5b9e\u4f53\u6811\u5b9a\u4f4d\u53d1\u751f\u5f02\u5e38");

    private String code;
    private String message;

    private EntityException(String code, String message) {
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

