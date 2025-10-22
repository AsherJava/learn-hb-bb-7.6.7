/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.resource;

public enum TaskAuthorityType {
    READ("task_privilege_read", "read", "\u8bbf\u95ee"),
    WRITE("task_privilege_write", "input", "\u6570\u636e\u5199"),
    SUBMIT("task_privilege_submit", "submit", "\u9001\u5ba1"),
    UPLOAD("task_privilege_upload", "upload", "\u4e0a\u62a5"),
    AUDIT("task_privilege_audit", "audit", "\u5ba1\u6279"),
    MODELING("task_privilege_modeling", "modeling", "\u5efa\u6a21"),
    DATA_PUBLISH("task_privilege_data_publish", "dataPublish", "\u6570\u636e\u53d1\u5e03"),
    READ_UN_PUBLISH("task_privilege_read_unpublish", "readUnPublish", "\u8bbf\u95ee\u672a\u53d1\u5e03\u6570\u636e");

    private final String id;
    private final String name;
    private final String title;

    private TaskAuthorityType(String id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}

