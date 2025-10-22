/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.definition.common.RegionEnterNext
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.designer.web.facade.LinkObj;
import com.jiuqi.nr.designer.web.treebean.TableObject;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegionObj {
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="FormID")
    private String FormID;
    @JsonProperty(value="RegionKind")
    private int RegionKind;
    @JsonProperty(value="InputOrderFieldID")
    private String InputOrderFieldID;
    @JsonProperty(value="RowsInFloatRegion")
    private int RowsInFloatRegion;
    @JsonProperty
    private String MultiLevel;
    @JsonProperty(value="RegionLeft")
    private int RegionLeft;
    @JsonProperty(value="RegionRight")
    private int RegionRight;
    @JsonProperty(value="RegionTop")
    private int RegionTop;
    @JsonProperty(value="RegionBottom")
    private int RegionBottom;
    @JsonProperty(value="Links")
    private Map<String, LinkObj> Links;
    @JsonProperty(value="Tables")
    private Map<String, TableObject> Tables;
    @JsonProperty(value="RegionExtension")
    private String RegionExtension;
    @JsonProperty(value="ID")
    private String ID;
    @JsonProperty(value="Title")
    private String Title;
    @JsonProperty
    private String Order;
    @JsonProperty(value="IsNew")
    private boolean IsNew = false;
    @JsonProperty(value="IsDirty")
    private boolean IsDirty = false;
    @JsonProperty(value="IsDeleted")
    private boolean IsDeleted = false;
    @JsonProperty(value="DictionaryFillLinks")
    private String DictionaryFillLinks;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="RegionEnterNext")
    private int regionEnterNext = RegionEnterNext.BOTTOM.getValue();
    @JsonProperty(value="DisplayLevel")
    private String displayLevel;
    @JsonProperty(value="RegionCardSurvey")
    private String regionCardSurvey;

    public String getRegionCardSurvey() {
        return this.regionCardSurvey;
    }

    public void setRegionCardSurvey(String regionCardSurvey) {
        this.regionCardSurvey = regionCardSurvey;
    }

    public String getDictionaryFillLinks() {
        return this.DictionaryFillLinks;
    }

    public void setDictionaryFillLinks(String dictionaryFillLinks) {
        this.DictionaryFillLinks = dictionaryFillLinks;
    }

    @JsonIgnore
    public String getID() {
        return this.ID;
    }

    @JsonIgnore
    public void setID(String iD) {
        this.ID = iD;
    }

    @JsonIgnore
    public String getOrder() {
        return this.Order;
    }

    @JsonIgnore
    public void setOrder(String order) {
        this.Order = order;
    }

    @JsonIgnore
    public boolean isIsNew() {
        return this.IsNew;
    }

    @JsonIgnore
    public void setIsNew(boolean isNew) {
        this.IsNew = isNew;
    }

    @JsonIgnore
    public boolean isIsDirty() {
        return this.IsDirty;
    }

    @JsonIgnore
    public void setIsDirty(boolean isDirty) {
        this.IsDirty = isDirty;
    }

    @JsonIgnore
    public boolean isIsDeleted() {
        return this.IsDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.IsDeleted = isDeleted;
    }

    @JsonIgnore
    public String getTitle() {
        return this.Title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.Title = title;
    }

    @JsonIgnore
    public String getFormID() {
        return this.FormID;
    }

    @JsonIgnore
    public void setFormID(String formID) {
        this.FormID = formID;
    }

    @JsonIgnore
    public int getRegionKind() {
        return this.RegionKind;
    }

    @JsonIgnore
    public void setRegionKind(int regionKind) {
        this.RegionKind = regionKind;
    }

    @JsonIgnore
    public String getInputOrderFieldID() {
        return this.InputOrderFieldID;
    }

    @JsonIgnore
    public void setInputOrderFieldID(String inputOrderFieldID) {
        this.InputOrderFieldID = inputOrderFieldID;
    }

    @JsonIgnore
    public int getRowsInFloatRegion() {
        return this.RowsInFloatRegion;
    }

    @JsonIgnore
    public void setRowsInFloatRegion(int rowsInFloatRegion) {
        this.RowsInFloatRegion = rowsInFloatRegion;
    }

    @JsonIgnore
    public int getRegionLeft() {
        return this.RegionLeft;
    }

    @JsonIgnore
    public void setRegionLeft(int regionLeft) {
        this.RegionLeft = regionLeft;
    }

    @JsonIgnore
    public int getRegionRight() {
        return this.RegionRight;
    }

    @JsonIgnore
    public void setRegionRight(int regionRight) {
        this.RegionRight = regionRight;
    }

    @JsonIgnore
    public int getRegionTop() {
        return this.RegionTop;
    }

    @JsonIgnore
    public void setRegionTop(int regionTop) {
        this.RegionTop = regionTop;
    }

    @JsonIgnore
    public int getRegionBottom() {
        return this.RegionBottom;
    }

    @JsonIgnore
    public void setRegionBottom(int regionBottom) {
        this.RegionBottom = regionBottom;
    }

    @JsonIgnore
    public Map<String, LinkObj> getLinks() {
        return this.Links;
    }

    @JsonIgnore
    public void setLinks(Map<String, LinkObj> links) {
        this.Links = links;
    }

    @JsonIgnore
    public Map<String, TableObject> getTables() {
        return this.Tables;
    }

    @JsonIgnore
    public void setTables(Map<String, TableObject> tables) {
        this.Tables = tables;
    }

    @JsonIgnore
    public String getRegionExtension() {
        return this.RegionExtension;
    }

    @JsonIgnore
    public void setRegionExtension(String regionExtension) {
        this.RegionExtension = regionExtension;
    }

    @JsonIgnore
    public String getMultiLevel() {
        return this.MultiLevel;
    }

    @JsonIgnore
    public void setMultiLevel(String multiLevel) {
        this.MultiLevel = multiLevel;
    }

    @JsonIgnore
    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @JsonIgnore
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @JsonIgnore
    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    @JsonIgnore
    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }

    @JsonIgnore
    public int getRegionEnterNext() {
        return this.regionEnterNext;
    }

    @JsonIgnore
    public void setRegionEnterNext(int regionEnterNext) {
        this.regionEnterNext = regionEnterNext;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayLevel() {
        return this.displayLevel;
    }

    public void setDisplayLevel(String displayLevel) {
        this.displayLevel = displayLevel;
    }
}

