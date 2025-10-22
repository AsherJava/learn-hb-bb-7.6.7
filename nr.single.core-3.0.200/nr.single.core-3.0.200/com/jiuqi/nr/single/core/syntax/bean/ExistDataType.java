/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;

public class ExistDataType
extends BaseCellDataType {
    private String tabSign;
    private boolean exist;
    private int tabIndex;

    public ExistDataType() {
        this.cellType = 2;
    }

    public String getTabSign() {
        return this.tabSign;
    }

    public void setTabSign(String tabSign) {
        this.tabSign = tabSign;
    }

    public boolean isExist() {
        return this.exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    @Override
    public void copyFrom(BaseCellDataType source) {
        ExistDataType source1 = (ExistDataType)source;
        this.cellType = source1.getCellType();
        this.tabSign = source1.getTabSign();
        this.exist = source1.isExist();
        this.tabIndex = source1.getTabIndex();
    }
}

