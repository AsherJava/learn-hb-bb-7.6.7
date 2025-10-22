/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.web.response;

import com.jiuqi.nr.tag.manager.bean.TagImpl;
import java.util.List;

public class TagsOfEntityData {
    private boolean systemUser;
    private List<TagImpl> tagsOfUser;
    private List<String> tagOfEntityData;

    public boolean isSystemUser() {
        return this.systemUser;
    }

    public void setSystemUser(boolean systemUser) {
        this.systemUser = systemUser;
    }

    public List<TagImpl> getTagsOfUser() {
        return this.tagsOfUser;
    }

    public void setTagsOfUser(List<TagImpl> tagsOfUser) {
        this.tagsOfUser = tagsOfUser;
    }

    public List<String> getTagOfEntityData() {
        return this.tagOfEntityData;
    }

    public void setTagOfEntityData(List<String> tagOfEntityData) {
        this.tagOfEntityData = tagOfEntityData;
    }
}

