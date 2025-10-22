/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.web.response;

import com.jiuqi.nr.tag.manager.bean.TagImpl;
import java.util.List;
import java.util.Map;

public class TagsOfEntityDataWithTagInfo {
    private Map<String, TagImpl> tagInfos;
    private Map<String, List<String>> node2TagsMap;

    public Map<String, TagImpl> getTagInfos() {
        return this.tagInfos;
    }

    public void setTagInfos(Map<String, TagImpl> tagInfos) {
        this.tagInfos = tagInfos;
    }

    public Map<String, List<String>> getNode2TagsMap() {
        return this.node2TagsMap;
    }

    public void setNode2TagsMap(Map<String, List<String>> node2TagsMap) {
        this.node2TagsMap = node2TagsMap;
    }
}

