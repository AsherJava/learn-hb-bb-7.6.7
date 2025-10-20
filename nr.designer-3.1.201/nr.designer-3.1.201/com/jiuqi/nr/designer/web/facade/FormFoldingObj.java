/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormFoldingSpecialRegion
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.definition.common.FormFoldingSpecialRegion;
import java.util.List;

public class FormFoldingObj {
    private String key;
    private String formKey;
    private Integer startIdx;
    private Integer endIdx;
    private List<FormFoldingSpecialRegion> hiddenRegion;
    private Integer direction;
    private boolean folding;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Integer getStartIdx() {
        return this.startIdx;
    }

    public void setStartIdx(Integer startIdx) {
        this.startIdx = startIdx;
    }

    public Integer getEndIdx() {
        return this.endIdx;
    }

    public void setEndIdx(Integer endIdx) {
        this.endIdx = endIdx;
    }

    public List<FormFoldingSpecialRegion> getHiddenRegion() {
        return this.hiddenRegion;
    }

    public void setHiddenRegion(List<FormFoldingSpecialRegion> hiddenRegion) {
        this.hiddenRegion = hiddenRegion;
    }

    public Integer getDirection() {
        return this.direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public boolean isFolding() {
        return this.folding;
    }

    public void setFolding(boolean folding) {
        this.folding = folding;
    }
}

