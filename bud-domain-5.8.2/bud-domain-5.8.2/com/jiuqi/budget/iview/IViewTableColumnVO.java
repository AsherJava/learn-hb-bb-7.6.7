/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.budget.iview;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class IViewTableColumnVO {
    private String type;
    private String title;
    private String key;
    private String slot;
    private Double width;
    private Double minWidth;
    private Double maxWidth;
    private String align;
    private String fixed;
    private Boolean ellipsis;
    private Boolean tooltip;
    private String tooltipTheme;
    private Double tooltipMaxWidth;
    private Boolean sortable;
    private String sortType;
    private Boolean resizable;
    private Boolean tree;
    private String queryType;
    private List<IViewTableColumnVO> children;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getWidth() {
        return this.width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getMinWidth() {
        return this.minWidth;
    }

    public void setMinWidth(Double minWidth) {
        this.minWidth = minWidth;
    }

    public Double getMaxWidth() {
        return this.maxWidth;
    }

    public void setMaxWidth(Double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getFixed() {
        return this.fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public Boolean getEllipsis() {
        return this.ellipsis;
    }

    public void setEllipsis(Boolean ellipsis) {
        this.ellipsis = ellipsis;
    }

    public Boolean getTooltip() {
        return this.tooltip;
    }

    public void setTooltip(Boolean tooltip) {
        this.tooltip = tooltip;
    }

    public String getTooltipTheme() {
        return this.tooltipTheme;
    }

    public void setTooltipTheme(String tooltipTheme) {
        this.tooltipTheme = tooltipTheme;
    }

    public Double getTooltipMaxWidth() {
        return this.tooltipMaxWidth;
    }

    public void setTooltipMaxWidth(Double tooltipMaxWidth) {
        this.tooltipMaxWidth = tooltipMaxWidth;
    }

    public Boolean getSortable() {
        return this.sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public String getSortType() {
        return this.sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Boolean getResizable() {
        return this.resizable;
    }

    public void setResizable(Boolean resizable) {
        this.resizable = resizable;
    }

    public Boolean getTree() {
        return this.tree;
    }

    public void setTree(Boolean tree) {
        this.tree = tree;
    }

    public List<IViewTableColumnVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<IViewTableColumnVO> children) {
        this.children = children;
    }

    public String getSlot() {
        return this.slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public static interface SortType {
        public static final String ASC = "asc";
        public static final String DESC = "desc";
    }

    public static interface TooltipThemeType {
        public static final String DARK = "dark";
        public static final String LIGHT = "light";
    }
}

