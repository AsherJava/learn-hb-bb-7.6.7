/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.dto;

import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.entity.SearchDataFieldDO;

public class SearchDataFieldDTO {
    private String key;
    private String code;
    private String title;
    private DataResourceNodeDTO group;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataResourceNodeDTO getGroup() {
        return this.group;
    }

    public void setGroup(DataResourceNodeDTO group) {
        this.group = group;
    }

    public SearchDataFieldDTO() {
    }

    public SearchDataFieldDTO(SearchDataFieldDO fieldDO, DataResourceNodeDTO group) {
        this.key = fieldDO.getKey();
        this.code = fieldDO.getCode();
        this.title = fieldDO.getTitle();
        this.group = group;
    }

    public String toString() {
        return "SearchDataFieldDTO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", group=" + this.group + '}';
    }
}

