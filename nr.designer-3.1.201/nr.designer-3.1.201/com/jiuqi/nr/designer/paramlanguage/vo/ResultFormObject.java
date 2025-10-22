/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.paramlanguage.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ResultFormObject {
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="FormType")
    private int formType;
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="OwnGroupId")
    private String ownGroupId;
    @JsonProperty(value="PYCode")
    private List<String> pyCode;
    @JsonProperty(value="Selected")
    private boolean selected;
    @JsonProperty(value="Title")
    private String title;
    private boolean expand;
    private int nodeKey;
    private String rawTitle;
    private String serialNumber;
    private String type;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnGroupId() {
        return this.ownGroupId;
    }

    public void setOwnGroupId(String ownGroupId) {
        this.ownGroupId = ownGroupId;
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

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public int getNodeKey() {
        return this.nodeKey;
    }

    public void setNodeKey(int nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getRawTitle() {
        return this.rawTitle;
    }

    public void setRawTitle(String rawTitle) {
        this.rawTitle = rawTitle;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

