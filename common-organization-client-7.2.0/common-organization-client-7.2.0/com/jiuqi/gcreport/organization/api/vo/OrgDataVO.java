/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.organization.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.gcreport.organization.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.organization.api.vo.tree.INode;

public class OrgDataVO
implements INode {
    private String code;
    private String title;
    private boolean leaf = true;
    private String parentcode;
    private String parents;
    private String ordinal;
    @JsonProperty(value="stopflag")
    private boolean stopFlag;
    @JsonProperty(value="recoveryflag")
    private boolean recoveryFlag;
    @JsonProperty(value="kind")
    private GcOrgKindEnum orgKind;
    private String key;
    private String bblx;
    private String showTitle;
    private String icons;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getParentcode() {
        return this.parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getShowTitle() {
        return this.showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    @Override
    public String getIcons() {
        return this.icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public boolean isRecoveryFlag() {
        return this.recoveryFlag;
    }

    public void setRecoveryFlag(boolean recoveryFlag) {
        this.recoveryFlag = recoveryFlag;
    }

    public boolean isStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    public GcOrgKindEnum getOrgKind() {
        return this.orgKind;
    }

    public void setOrgKind(GcOrgKindEnum orgKind) {
        this.orgKind = orgKind;
    }
}

