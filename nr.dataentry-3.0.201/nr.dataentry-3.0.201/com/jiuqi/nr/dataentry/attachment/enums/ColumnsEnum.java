/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.enums;

public enum ColumnsEnum {
    INDEX("index", "\u5e8f\u53f7"),
    FILECATEGORY("fileCategory", "\u7c7b\u522b"),
    TITLE("title", "\u6587\u4ef6\u540d"),
    CREATETIME("createtime", "\u4e0a\u4f20\u65f6\u95f4"),
    SIZE("size", "\u5927\u5c0f"),
    CREATOR("creator", "\u4e0a\u4f20\u4eba"),
    CONFIDENTIAL("confidential", "\u5bc6\u7ea7"),
    INDEX_EN("index", "No"),
    FILECATEGORY_EN("fileCategory", "Category"),
    TITLE_EN("title", "file name"),
    CREATETIME_EN("createtime", "upload time"),
    SIZE_EN("size", "size"),
    CREATOR_EN("creator", "uploader"),
    CONFIDENTIAL_EN("confidential", "secret");

    private String key;
    private String title;

    private ColumnsEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

