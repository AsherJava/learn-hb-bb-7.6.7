/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.investworkpaper.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

public class InvestWorkPaperColumnVO {
    private String prop;
    private String parentProp;
    private String label;
    private Object fixed;
    private String align;
    private int width = 160;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<InvestWorkPaperColumnVO> children;

    public InvestWorkPaperColumnVO(String prop, String label, String align) {
        this(prop, label, align, "", 150);
    }

    public InvestWorkPaperColumnVO(String prop, String label, String align, int width) {
        this(prop, label, align, "", width);
    }

    public InvestWorkPaperColumnVO(String prop, String label, String align, Object fixed, int width) {
        this.setProp(prop);
        this.setLabel(label);
        this.setFixed(fixed);
        this.setAlign(align);
        this.setWidth(width);
    }

    public String getParentProp() {
        return this.parentProp;
    }

    public void setParentProp(String parentProp) {
        this.parentProp = parentProp;
    }

    public String getProp() {
        return this.prop;
    }

    public void setProp(String key) {
        this.prop = key;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String title) {
        this.label = title;
    }

    public Object getFixed() {
        return this.fixed;
    }

    public void setFixed(Object fixed) {
        this.fixed = fixed;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<InvestWorkPaperColumnVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<InvestWorkPaperColumnVO> children) {
        this.children = children;
    }
}

