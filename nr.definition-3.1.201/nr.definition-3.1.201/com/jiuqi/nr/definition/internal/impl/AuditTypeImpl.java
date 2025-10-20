/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.AuditType;

@DBAnno.DBTable(dbTable="sys_audittype")
public class AuditTypeImpl
implements AuditType {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="at_code", isPk=true)
    private Integer code;
    @DBAnno.DBField(dbField="at_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="at_title")
    private String title;
    @DBAnno.DBField(dbField="at_icon")
    private String icon;
    @DBAnno.DBField(dbField="at_cell_color")
    private String color;
    @DBAnno.DBField(dbField="at_background_color")
    private String backGroundColor;
    @DBAnno.DBField(dbField="at_font_color")
    private String fontColor;
    @DBAnno.DBField(dbField="at_grid_color")
    private String gridColor;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getBackGroundColor() {
        return this.backGroundColor;
    }

    @Override
    public void setBackGroundColor(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    @Override
    public String getFontColor() {
        return this.fontColor;
    }

    @Override
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    @Override
    public String getGridColor() {
        return this.gridColor;
    }

    @Override
    public void setGridColor(String gridColor) {
        this.gridColor = gridColor;
    }
}

