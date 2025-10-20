/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.query.tree.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class TableHeaderVO {
    private String align;
    private String key;
    private String title;
    private String width;
    private List<TableHeaderVO> children;
    private TemplateFieldSettingVO fieldSetting;

    public TableHeaderVO() {
    }

    public TableHeaderVO(String align, String key, String title, String width, List<TableHeaderVO> children) {
        this.align = align;
        this.key = key;
        this.title = title;
        this.width = width;
        this.children = children;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public List<TableHeaderVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<TableHeaderVO> children) {
        this.children = children;
    }

    public TemplateFieldSettingVO getFieldSetting() {
        return this.fieldSetting;
    }

    public void setFieldSetting(TemplateFieldSettingVO fieldSetting) {
        this.fieldSetting = fieldSetting;
    }

    public String toString() {
        return "TableHeaderVO [align=" + this.align + ", key=" + this.key + ", title=" + this.title + ", width=" + this.width + ", children=" + this.children + "]";
    }
}

