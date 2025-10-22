/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.configuration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Deprecated
public class OptionGroupBO {
    private String id;
    private String name;
    private String desc;
    private String parent;
    private String type;
    private int childrenSize;
    private List<String> optionIds;

    public OptionGroupBO() {
    }

    public OptionGroupBO(String id, String name, String desc, String parent, String type, int childrenSize, List<String> optionIds) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.parent = parent;
        this.type = type;
        this.childrenSize = childrenSize;
        this.optionIds = optionIds;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getOptionIds() {
        return this.optionIds;
    }

    public void setOptionIds(List<String> optionIds) {
        this.optionIds = optionIds;
    }

    public int getChildrenSize() {
        return this.childrenSize;
    }

    public void setChildrenSize(int childrenSize) {
        this.childrenSize = childrenSize;
    }

    @JsonIgnore
    public int getOptionSize() {
        if (this.optionIds == null) {
            return 0;
        }
        return this.optionIds.size();
    }

    public String toString() {
        return "OptionGroupBO{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", parent='" + this.parent + '\'' + '}';
    }
}

