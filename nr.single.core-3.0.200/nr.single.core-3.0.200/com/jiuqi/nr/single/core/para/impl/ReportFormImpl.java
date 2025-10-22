/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.impl;

public class ReportFormImpl {
    private String formName = "";
    private String formTitle = "";
    private String formFilter = "";
    private String formSubTitle = "";
    private String formSubNo = "";
    private String formType = "";
    private int type = 0;
    private boolean isGather = false;
    private String privateJEDW;
    private boolean privateCalcView;

    public final String getName() {
        return this.formName;
    }

    public final void setName(String name) {
        this.formName = name;
    }

    public final String getTitle() {
        return this.formTitle;
    }

    public final void setTitle(String title) {
        this.formTitle = title;
    }

    public final String getFilter() {
        return this.formFilter;
    }

    public final void setFilter(String filter) {
        this.formFilter = filter;
    }

    public final void setSubTitle(String subTitle) {
        this.formSubTitle = subTitle;
    }

    public final String getSubTitle() {
        return this.formSubTitle;
    }

    public final void setSubNo(String subNo) {
        this.formSubNo = subNo;
    }

    public final String getSubNo() {
        return this.formSubNo;
    }

    public final void setFormType(String FormType) {
        this.formType = FormType;
    }

    public final void setType(int type) {
        this.type = type;
    }

    public final void setGather(boolean value) {
        this.isGather = value;
    }

    public final boolean getGather() {
        return this.isGather;
    }

    public final String getJEDW() {
        return this.privateJEDW;
    }

    public final void setJEDW(String value) {
        this.privateJEDW = value;
    }

    public final boolean getCalcView() {
        return this.privateCalcView;
    }

    public final void setCalcView(boolean value) {
        this.privateCalcView = value;
    }

    public final void setOrder(long order) {
    }
}

