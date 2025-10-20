/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.paramlanguage.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.paramlanguage.vo.ResultFormGroupObject;
import java.util.List;

public class ResultFormSchemeObject {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="PYCode")
    private List<String> pyCode;
    @JsonProperty(value="Selected")
    private boolean selected;
    @JsonProperty(value="Title")
    private String title;
    private List<ResultFormGroupObject> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPyCode() {
        return this.pyCode;
    }

    public void setPyCode(List<String> pyCode) {
        this.pyCode = pyCode;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ResultFormGroupObject> getChildren() {
        return this.children;
    }

    public void setChildren(List<ResultFormGroupObject> children) {
        this.children = children;
    }
}

