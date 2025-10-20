/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.common.elementtable.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

public class ElementTableTitleVO {
    private String key;
    private String title;
    private Object fixed;
    private String align;
    private boolean linkPage;
    private int width = 160;
    private String exportType = "s";
    private int precision = 2;
    private boolean rowSpan;
    private boolean columnSpan;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<ElementTableTitleVO> children;

    public ElementTableTitleVO(String key, String title, String align) {
        this(key, title, align, false, 160);
    }

    public ElementTableTitleVO() {
    }

    public ElementTableTitleVO(String key, String title, String align, Object fixed, int width) {
        this(key, title, align, fixed, width, false);
    }

    public ElementTableTitleVO(String key, String title, String align, Object fixed, int width, boolean linkPage) {
        this.setKey(key);
        this.setTitle(title);
        this.setFixed(fixed);
        this.setAlign(align);
        this.setWidth(width);
        this.setLinkPage(linkPage);
    }

    public ElementTableTitleVO addChildren(ElementTableTitleVO vo) {
        if (this.getChildren() == null) {
            this.setChildren(new ArrayList<ElementTableTitleVO>());
        }
        this.getChildren().add(vo);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public boolean isLinkPage() {
        return this.linkPage;
    }

    public String getTitle() {
        return this.title;
    }

    public Object getFixed() {
        return this.fixed;
    }

    public String getAlign() {
        return this.align;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isColumnSpan() {
        return this.columnSpan;
    }

    public String getExportType() {
        return this.exportType;
    }

    public boolean isRowSpan() {
        return this.rowSpan;
    }

    public List<ElementTableTitleVO> getChildren() {
        return this.children;
    }

    public ElementTableTitleVO setKey(String key) {
        this.key = key;
        return this;
    }

    public ElementTableTitleVO setLinkPage(boolean linkPage) {
        this.linkPage = linkPage;
        return this;
    }

    public ElementTableTitleVO setTitle(String title) {
        this.title = title;
        return this;
    }

    public ElementTableTitleVO setExportTypeWithString() {
        this.exportType = "s";
        return this;
    }

    public ElementTableTitleVO setExportTypeWithBoolean() {
        this.exportType = "b";
        return this;
    }

    public ElementTableTitleVO setExportTypeWithNumber() {
        this.exportType = "n";
        return this;
    }

    public ElementTableTitleVO setExportTypeWithInteger() {
        this.exportType = "n";
        this.precision = 0;
        return this;
    }

    public ElementTableTitleVO setExportTypeWithDate() {
        this.exportType = "d";
        return this;
    }

    public ElementTableTitleVO setFixed(Object fixed) {
        this.fixed = fixed;
        return this;
    }

    public ElementTableTitleVO setAlign(String align) {
        this.align = align;
        return this;
    }

    public ElementTableTitleVO setWidth(int width) {
        this.width = width;
        return this;
    }

    public ElementTableTitleVO setRowSpan(boolean rowSpan) {
        this.rowSpan = rowSpan;
        return this;
    }

    public ElementTableTitleVO setColumnSpan(boolean columnSpan) {
        this.columnSpan = columnSpan;
        return this;
    }

    public ElementTableTitleVO setChildren(List<ElementTableTitleVO> children) {
        this.children = children;
        return this;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}

