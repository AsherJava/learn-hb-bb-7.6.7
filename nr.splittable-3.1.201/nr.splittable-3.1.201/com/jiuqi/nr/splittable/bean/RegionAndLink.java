/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.splittable.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.splittable.web.SplitDataPM;
import java.util.List;

public class RegionAndLink {
    private String formKey;
    private String regionKey;
    private DataRegionKind dataRegionKind;
    private List<DataLinkDefine> allLinksInRegion;
    private int regionLeft;
    private int regionRight;
    private int regionTop;
    private int regionBottom;

    public static RegionAndLink initializer(SplitDataPM splitDataPM, DataRegionDefine dataRegionDefine, List<DataLinkDefine> allLinksInRegion) {
        RegionAndLink regionAndLink = new RegionAndLink();
        regionAndLink.setFormKey(splitDataPM.getFormKey());
        regionAndLink.setRegionKey(dataRegionDefine.getKey());
        regionAndLink.setDataRegionKind(dataRegionDefine.getRegionKind());
        regionAndLink.setAllLinksInRegion(allLinksInRegion);
        regionAndLink.setRegionBottom(dataRegionDefine.getRegionBottom());
        regionAndLink.setRegionLeft(dataRegionDefine.getRegionLeft());
        regionAndLink.setRegionTop(dataRegionDefine.getRegionTop());
        regionAndLink.setRegionRight(dataRegionDefine.getRegionRight());
        return regionAndLink;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public void setDataRegionKind(DataRegionKind dataRegionKind) {
        this.dataRegionKind = dataRegionKind;
    }

    @JsonIgnore
    public void setAllLinksInRegion(List<DataLinkDefine> allLinksInRegion) {
        this.allLinksInRegion = allLinksInRegion;
    }

    public void setRegionLeft(int regionLeft) {
        this.regionLeft = regionLeft;
    }

    public void setRegionRight(int regionRight) {
        this.regionRight = regionRight;
    }

    public void setRegionTop(int regionTop) {
        this.regionTop = regionTop;
    }

    public void setRegionBottom(int regionBottom) {
        this.regionBottom = regionBottom;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public DataRegionKind getDataRegionKind() {
        return this.dataRegionKind;
    }

    @JsonIgnore
    public List<DataLinkDefine> getAllLinksInRegion() {
        return this.allLinksInRegion;
    }

    public int getRegionLeft() {
        return this.regionLeft;
    }

    public int getRegionRight() {
        return this.regionRight;
    }

    public int getRegionTop() {
        return this.regionTop;
    }

    public int getRegionBottom() {
        return this.regionBottom;
    }
}

