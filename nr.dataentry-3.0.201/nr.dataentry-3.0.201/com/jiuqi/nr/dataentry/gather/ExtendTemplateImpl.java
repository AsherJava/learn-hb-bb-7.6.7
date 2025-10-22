/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.nr.dataentry.gather.TemplateItem;

public class ExtendTemplateImpl
implements TemplateItem {
    private String code;
    private String desc;
    private String content;
    private String title;
    private String fileKey;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}

