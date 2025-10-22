/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.designer.util.DataRegionKindDeserialize;
import com.jiuqi.nr.designer.util.DataRegionKindSerialize;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataTableVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseLinkVO;
import java.util.Map;

public class ReverseRegionVO {
    private String key;
    private String formKey;
    private int regionLeft;
    private int regionRight;
    private int regionTop;
    private int regionBottom;
    @JsonDeserialize(using=DataRegionKindDeserialize.class)
    @JsonSerialize(using=DataRegionKindSerialize.class)
    private DataRegionKind regionKind;
    private Map<String, ReverseLinkVO> links;
    private Map<String, ReverseDataTableVO> tables;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getRegionLeft() {
        return this.regionLeft;
    }

    public void setRegionLeft(int regionLeft) {
        this.regionLeft = regionLeft;
    }

    public int getRegionRight() {
        return this.regionRight;
    }

    public void setRegionRight(int regionRight) {
        this.regionRight = regionRight;
    }

    public int getRegionTop() {
        return this.regionTop;
    }

    public void setRegionTop(int regionTop) {
        this.regionTop = regionTop;
    }

    public int getRegionBottom() {
        return this.regionBottom;
    }

    public void setRegionBottom(int regionBottom) {
        this.regionBottom = regionBottom;
    }

    public DataRegionKind getRegionKind() {
        return this.regionKind;
    }

    public void setRegionKind(DataRegionKind regionKind) {
        this.regionKind = regionKind;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, ReverseLinkVO> getLinks() {
        return this.links;
    }

    public void setLinks(Map<String, ReverseLinkVO> links) {
        this.links = links;
    }

    public Map<String, ReverseDataTableVO> getTables() {
        return this.tables;
    }

    public void setTables(Map<String, ReverseDataTableVO> tables) {
        this.tables = tables;
    }
}

