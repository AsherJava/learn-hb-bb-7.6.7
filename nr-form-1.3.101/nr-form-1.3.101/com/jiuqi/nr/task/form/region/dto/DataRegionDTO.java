/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.RegionEnterNext
 */
package com.jiuqi.nr.task.form.region.dto;

import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.region.dto.RegionExtensionDTO;

public class DataRegionDTO
extends DataCore {
    private String formKey;
    private Integer regionLeft = -1;
    private Integer regionRight = -1;
    private Integer regionTop = -1;
    private Integer regionBottom = -1;
    private Integer regionKind = -1;
    private RegionEnterNext regionEnterNext;
    private RegionExtensionDTO regionExtension;

    public RegionExtensionDTO getRegionExtension() {
        return this.regionExtension;
    }

    public void setRegionExtension(RegionExtensionDTO regionExtension) {
        this.regionExtension = regionExtension;
    }

    public RegionEnterNext getRegionEnterNext() {
        return this.regionEnterNext;
    }

    public void setRegionEnterNext(RegionEnterNext regionEnterNext) {
        this.regionEnterNext = regionEnterNext;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Integer getRegionLeft() {
        return this.regionLeft;
    }

    public void setRegionLeft(Integer regionLeft) {
        this.regionLeft = regionLeft;
    }

    public Integer getRegionRight() {
        return this.regionRight;
    }

    public void setRegionRight(Integer regionRight) {
        this.regionRight = regionRight;
    }

    public Integer getRegionTop() {
        return this.regionTop;
    }

    public void setRegionTop(Integer regionTop) {
        this.regionTop = regionTop;
    }

    public Integer getRegionBottom() {
        return this.regionBottom;
    }

    public void setRegionBottom(Integer regionBottom) {
        this.regionBottom = regionBottom;
    }

    public Integer getRegionKind() {
        return this.regionKind;
    }

    public void setRegionKind(Integer regionKind) {
        this.regionKind = regionKind;
    }
}

