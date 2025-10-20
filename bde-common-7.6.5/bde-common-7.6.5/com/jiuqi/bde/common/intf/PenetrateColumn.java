/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.common.intf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class PenetrateColumn {
    private String name;
    private String title;
    private String type;
    private String alignment;
    private Integer digits;
    private Integer width;
    private Integer minWidth;
    private Boolean fixed;
    private Boolean internal;
    private Boolean visiable;
    private Boolean showTooltip;
    private List<PenetrateColumn> children;

    public PenetrateColumn() {
    }

    public PenetrateColumn(String name, String title, String type) {
        this.name = name;
        this.title = title;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public PenetrateColumn setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public PenetrateColumn setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public PenetrateColumn setType(String type) {
        this.type = type;
        return this;
    }

    public String getAlignment() {
        return this.alignment;
    }

    public PenetrateColumn setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

    public Integer getDigits() {
        return this.digits;
    }

    public PenetrateColumn setDigits(Integer digits) {
        this.digits = digits;
        return this;
    }

    public Integer getWidth() {
        return this.width;
    }

    public PenetrateColumn setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getMinWidth() {
        return this.minWidth;
    }

    public PenetrateColumn setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public Boolean getFixed() {
        return this.fixed;
    }

    public PenetrateColumn setFixed(Boolean fixed) {
        this.fixed = fixed;
        return this;
    }

    public Boolean getInternal() {
        return this.internal;
    }

    public PenetrateColumn setInternal(Boolean internal) {
        this.internal = internal;
        return this;
    }

    public Boolean getVisiable() {
        return this.visiable;
    }

    public PenetrateColumn setVisiable(Boolean visiable) {
        this.visiable = visiable;
        return this;
    }

    public Boolean getShowTooltip() {
        return this.showTooltip;
    }

    public PenetrateColumn setShowTooltip(Boolean showTooltip) {
        this.showTooltip = showTooltip;
        return this;
    }

    public List<PenetrateColumn> getChildren() {
        return this.children;
    }

    public PenetrateColumn setChildren(List<PenetrateColumn> children) {
        this.children = children;
        return this;
    }

    public PenetrateColumn addChild(PenetrateColumn child) {
        if (this.children == null) {
            this.children = CollectionUtils.newArrayList();
        }
        this.children.add(child);
        return this;
    }
}

