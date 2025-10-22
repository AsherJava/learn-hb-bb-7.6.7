/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.enums.RatioType;
import com.jiuqi.nr.dafafill.model.enums.ShowContent;
import java.io.Serializable;

public class FieldFormat
implements Serializable {
    private static final long serialVersionUID = 1L;
    private ShowContent showContent;
    private int decimal;
    private boolean percent;
    private boolean permil;
    private RatioType ratioType;
    private String format;

    public ShowContent getShowContent() {
        return this.showContent;
    }

    public void setShowContent(ShowContent showContent) {
        this.showContent = showContent;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    @Deprecated
    public boolean isPercent() {
        return this.percent;
    }

    @Deprecated
    public void setPercent(boolean percent) {
        this.percent = percent;
    }

    public boolean isPermil() {
        return this.permil;
    }

    public void setPermil(boolean permil) {
        this.permil = permil;
    }

    public RatioType getRatioType() {
        return this.ratioType;
    }

    public void setRatioType(RatioType ratioType) {
        this.ratioType = ratioType;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

