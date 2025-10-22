/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkingPaperTableHeaderVO {
    private String prop;
    private String label;
    private Object fixed;
    private String align;
    private int width = 160;
    private Map<String, Object> params;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<WorkingPaperTableHeaderVO> children;

    public WorkingPaperTableHeaderVO() {
    }

    public WorkingPaperTableHeaderVO(String prop, String label, String align) {
        this(prop, label, align, "", 250);
    }

    public WorkingPaperTableHeaderVO(String prop, String label, String align, Object fixed, int width) {
        this(prop, label, align, fixed, width, null);
    }

    public WorkingPaperTableHeaderVO(String prop, String label, String align, Object fixed, int width, Map<String, Object> params) {
        this.prop = prop;
        this.label = label;
        this.fixed = fixed;
        this.align = align;
        this.width = width;
        this.params = params;
    }

    public Map<String, Object> getParams() {
        if (this.params == null) {
            this.params = new HashMap<String, Object>();
        }
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addChildren(WorkingPaperTableHeaderVO vo) {
        if (this.getChildren() == null) {
            this.setChildren(new ArrayList<WorkingPaperTableHeaderVO>());
        }
        this.getChildren().add(vo);
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

    public List<WorkingPaperTableHeaderVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<WorkingPaperTableHeaderVO> children) {
        this.children = children;
    }
}

