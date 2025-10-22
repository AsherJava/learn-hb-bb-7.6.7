/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.draw2d.FontMetrics
 *  com.jiuqi.xg.draw2d.XG
 *  com.jiuqi.xg.draw2d.geometry.Insets
 *  com.jiuqi.xg.process.obj.TextTemplateObject
 *  com.jiuqi.xlib.measure.ILengthUnit
 */
package com.jiuqi.nr.definition.facade.print.core;

import com.jiuqi.util.StringUtils;
import com.jiuqi.xg.draw2d.Font;
import com.jiuqi.xg.draw2d.FontMetrics;
import com.jiuqi.xg.draw2d.XG;
import com.jiuqi.xg.draw2d.geometry.Insets;
import com.jiuqi.xg.process.obj.TextTemplateObject;
import com.jiuqi.xlib.measure.ILengthUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WordLabelTemplateObject
extends TextTemplateObject {
    public static String ATT_NAME = "reportGuid";
    public static String ATT_IS_SPLIT = "isSplitPrint";
    public static String ATT_SCOPE_EVENANDLAST = "isEvenAndLastPagePrint";
    public static String ATT_IS_UNDERTABLE = "isUnderTable";
    private String reportGuid;
    private boolean isSplitPrint = false;
    private boolean isEvenAndLastPagePrint = false;
    private boolean isUnderTable = false;

    public String getKind() {
        return "element_wordLabel";
    }

    public String getReportGuid() {
        return this.reportGuid;
    }

    public void setReportGuid(String reportGuid) {
        String old = this.reportGuid;
        if (!this.isRelatedReportChanged(reportGuid, old)) {
            return;
        }
        this.reportGuid = reportGuid;
        this.firePropertyChange(ATT_NAME, old, reportGuid);
        this.firePropertyChange("validate", false, true);
    }

    private boolean isRelatedReportChanged(String selectedReportGuid, String oldReportGuid) {
        if (null != selectedReportGuid && null != oldReportGuid) {
            return !selectedReportGuid.equals(oldReportGuid);
        }
        return null != selectedReportGuid || null != oldReportGuid;
    }

    public boolean isSplitPrint() {
        return this.isSplitPrint;
    }

    public void setSplitPrint(boolean isSplitPrint) {
        boolean old = this.isSplitPrint;
        if (old == isSplitPrint) {
            return;
        }
        this.isSplitPrint = isSplitPrint;
        this.firePropertyChange(ATT_IS_SPLIT, old, isSplitPrint);
        this.firePropertyChange("validate", false, true);
    }

    public boolean isEvenAndLastPagePrint() {
        return this.isEvenAndLastPagePrint;
    }

    public void setEvenAndLastPagePrint(boolean isEvenAndLastPagePrint) {
        boolean old = this.isEvenAndLastPagePrint;
        if (old == isEvenAndLastPagePrint) {
            return;
        }
        this.isEvenAndLastPagePrint = isEvenAndLastPagePrint;
        this.firePropertyChange(ATT_SCOPE_EVENANDLAST, old, isEvenAndLastPagePrint);
        this.firePropertyChange("validate", false, true);
    }

    public boolean isUnderTable() {
        return this.isUnderTable;
    }

    public void setUnderTable(boolean isUnderTable) {
        boolean old = this.isUnderTable;
        if (old == isUnderTable) {
            return;
        }
        this.isUnderTable = isUnderTable;
        this.firePropertyChange(ATT_IS_UNDERTABLE, old, isUnderTable);
        this.firePropertyChange("validate", false, true);
    }

    public void deserialize(Element element) {
        super.deserialize(element);
        String value = element.getAttribute(ATT_NAME);
        if (StringUtils.isNotEmpty((String)value)) {
            this.reportGuid = value;
        }
        this.isSplitPrint = Boolean.parseBoolean(element.getAttribute(ATT_IS_SPLIT));
        this.isEvenAndLastPagePrint = Boolean.parseBoolean(element.getAttribute(ATT_SCOPE_EVENANDLAST));
        this.isUnderTable = Boolean.parseBoolean(element.getAttribute(ATT_IS_UNDERTABLE));
    }

    public Element serialize(Document ownerDocument) {
        Element element = super.serialize(ownerDocument);
        String guid = this.getReportGuid();
        String value = guid == null ? "" : guid.toString();
        element.setAttribute(ATT_NAME, value);
        element.setAttribute(ATT_IS_SPLIT, String.valueOf(this.isSplitPrint));
        element.setAttribute(ATT_SCOPE_EVENANDLAST, String.valueOf(this.isEvenAndLastPagePrint));
        element.setAttribute(ATT_IS_UNDERTABLE, String.valueOf(this.isUnderTable));
        return element;
    }

    public void setFont(Font font) {
        if (!this.isAutoWrap() && !StringUtils.isEmpty((String)this.getContent())) {
            Font clone = font.clone();
            ILengthUnit lengthUnit = XG.DEFAULT_LENGTH_UNIT;
            clone.setSize(lengthUnit.fromPoint(font.getSize()));
            FontMetrics metrics = FontMetrics.getMetrics((Font)clone, (ILengthUnit)lengthUnit);
            Insets insets = this.getInsets();
            String[] split = this.getContent().split("\r?\n");
            double fontHeight = metrics.getFontHeight() * (double)split.length + insets.getTop() + insets.getBottom();
            if (this.getHeight() < fontHeight) {
                this.setHeight(fontHeight);
            }
        }
        super.setFont(font);
    }

    public void setAutoWrap(boolean isAutoWrap) {
        super.setAutoWrap(isAutoWrap);
        if (!isAutoWrap) {
            this.setFont(this.getFont());
        }
    }
}

