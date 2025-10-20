/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

public class Col {
    private Object value;
    private StringBuffer style = new StringBuffer("word-break: break-all;");
    private StringBuffer attribute = new StringBuffer("");
    private boolean isShow = true;

    public Object getValue() {
        return this.value;
    }

    public void setShow(boolean show) {
        this.isShow = show;
    }

    public boolean isShow() {
        return this.isShow;
    }

    public Col clone() {
        Col col = new Col();
        col.value = null;
        col.style = new StringBuffer(this.style);
        col.attribute = new StringBuffer(this.attribute);
        return col;
    }

    public String toString() {
        if (!this.isShow) {
            this.style.append("display:none;");
            return "";
        }
        StringBuffer suf = new StringBuffer("<td ");
        suf.append(this.attribute.toString()).append(" style=\"");
        suf.append(this.style.toString());
        suf.append("\">");
        suf.append(this.value != null ? this.value : "");
        suf.append("</td>");
        return suf.toString();
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public StringBuffer getStyle() {
        return this.style;
    }

    public void addStyle(String style) {
        this.style.append(style);
    }

    public void clearStyle() {
        this.style = new StringBuffer("word-break: break-all;");
    }

    public StringBuffer getAttribute() {
        return this.attribute;
    }
}

