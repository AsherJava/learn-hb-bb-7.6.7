/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.bean;

import com.jiuqi.nr.tag.manager.bean.TagImpl;

public class TagItemData {
    private String key;
    private String title;
    private String category;

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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static TagItemData assign(TagImpl impl) {
        TagItemData tagData = new TagItemData();
        tagData.key = impl.getKey();
        tagData.title = impl.getTitle();
        tagData.category = impl.getCategory();
        return tagData;
    }
}

