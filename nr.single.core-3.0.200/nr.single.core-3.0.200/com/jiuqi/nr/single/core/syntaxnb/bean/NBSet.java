/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntaxnb.common.SetKindType;

public class NBSet
extends BaseCellDataType {
    private SetKindType kind;
    private String[] strings;
    private Double[] reals;
    private Integer[] flags;

    public SetKindType getKind() {
        return this.kind;
    }

    public String[] getStrings() {
        return this.strings;
    }

    public Double[] getReals() {
        return this.reals;
    }

    public Integer[] getFlags() {
        return this.flags;
    }

    public void setKind(SetKindType kind) {
        this.kind = kind;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public void setReals(Double[] reals) {
        this.reals = reals;
    }

    public void setFlags(Integer[] flags) {
        this.flags = flags;
    }

    @Override
    public void copyFrom(BaseCellDataType source) {
        NBSet source1 = (NBSet)source;
        this.cellType = source1.getCellType();
        this.kind = source1.getKind();
        if (source1.getStrings() != null) {
            this.strings = new String[source1.getStrings().length];
            System.arraycopy(source1.getStrings(), 0, this.strings, 0, source1.getStrings().length);
        }
        if (source1.getReals() != null) {
            this.reals = new Double[source1.getReals().length];
            System.arraycopy(source1.getReals(), 0, this.reals, 0, source1.getReals().length);
        }
        if (source1.getFlags() != null) {
            this.flags = new Integer[source1.getFlags().length];
            System.arraycopy(source1.getFlags(), 0, this.flags, 0, source1.getFlags().length);
        }
    }
}

