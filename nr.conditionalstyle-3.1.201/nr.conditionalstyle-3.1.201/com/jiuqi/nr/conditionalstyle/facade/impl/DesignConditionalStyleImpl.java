/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.conditionalstyle.facade.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_CONDITIONAL_STYLE_DES")
public class DesignConditionalStyleImpl
implements DesignConditionalStyle {
    @DBAnno.DBField(dbField="CS_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="CS_FORM_KEY")
    private String formKey;
    @DBAnno.DBField(dbField="CS_POS_X")
    private int posX;
    @DBAnno.DBField(dbField="CS_POS_Y")
    private int posY;
    @DBAnno.DBField(dbField="CS_STYLE_EXPRESSION", dbType=Clob.class, appType=String.class)
    private String styleExpression;
    @DBAnno.DBField(dbField="CS_FONT_COLOR", get="getFontColor")
    private String fontColor;
    @DBAnno.DBField(dbField="CS_FOREGROUND_COLOR", get="getForeGroundColor")
    private String foreGroundColor;
    @DBAnno.DBField(dbField="CS_BOLD", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean bold;
    @DBAnno.DBField(dbField="CS_ITALIC", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean italic;
    @DBAnno.DBField(dbField="CS_READ_ONLY", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean readOnly;
    @DBAnno.DBField(dbField="CS_HORIZONTAL_BAR", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean horizontalBar;
    @DBAnno.DBField(dbField="CS_STRIKE_THROUGH", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean strikeThrough;
    @DBAnno.DBField(dbField="CS_ORDER")
    private String order;
    @DBAnno.DBField(dbField="CS_UPDATE_TIME", tranWith="transTimeStamp", autoDate=true, dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="CS_LINK_KEY")
    private String linkKey;

    @Override
    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return null;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public String getLinkKey() {
        return this.linkKey;
    }

    @Override
    public Boolean getHorizontalBar() {
        return this.horizontalBar == null ? false : this.horizontalBar;
    }

    @Override
    public Boolean getStrikeThrough() {
        return this.strikeThrough == null ? false : this.strikeThrough;
    }

    @Override
    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    @Override
    public void setHorizontalBar(Boolean horizontalBar) {
        this.horizontalBar = horizontalBar;
    }

    @Override
    public void setStrikeThrough(Boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public String getStyleExpression() {
        return this.styleExpression;
    }

    @Override
    public void setStyleExpression(String styleExpression) {
        this.styleExpression = styleExpression;
    }

    @Override
    public String getFontColor() {
        return this.fontColor == null ? "" : this.fontColor;
    }

    @Override
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    @Override
    public String getForeGroundColor() {
        return this.foreGroundColor == null ? "" : this.foreGroundColor;
    }

    @Override
    public void setForeGroundColor(String foreGroundColor) {
        this.foreGroundColor = foreGroundColor;
    }

    @Override
    public Boolean getBold() {
        return this.bold;
    }

    @Override
    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    @Override
    public Boolean getItalic() {
        return this.italic;
    }

    @Override
    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    @Override
    public Boolean getReadOnly() {
        return this.readOnly;
    }

    @Override
    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date time) {
        this.updateTime = time;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DesignConditionalStyleImpl) {
            DesignConditionalStyleImpl cs = new DesignConditionalStyleImpl();
            return this.fontColor.equals(cs.fontColor) && this.foreGroundColor.equals(cs.foreGroundColor) && this.italic.equals(cs.italic) && this.bold.equals(cs.bold) && this.readOnly.equals(cs.readOnly);
        }
        return super.equals(obj);
    }

    public int hashCode() {
        if (this.fontColor == null) {
            this.fontColor = "";
        }
        if (this.foreGroundColor == null) {
            this.foreGroundColor = "";
        }
        return this.fontColor.hashCode() + this.foreGroundColor.hashCode() + this.italic.hashCode() * 3 + this.readOnly.hashCode() * 5 + this.bold.hashCode() * 7;
    }
}

