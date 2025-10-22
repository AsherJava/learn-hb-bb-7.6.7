/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.PageSize;
import java.io.Serializable;
import java.util.Date;

public interface PrintSettingDefine
extends Serializable {
    public PageSize getPageSize();

    public Boolean getLandscape();

    public Double getTopMargin();

    public Double getBottomMargin();

    public Double getLeftMargin();

    public Double getRightMargin();

    public Short getScale();

    public Short getFitToWidth();

    public Short getFitToHeight();

    public int[] getColumnBreaks();

    public int[] getRowBreaks();

    public Boolean getHorizontallyCenter();

    public Boolean getVerticallyCenter();

    public Boolean getLeftToRight();

    public String getPrintSchemeKey();

    public String getFormKey();

    public Date getUpdateTime();

    public String getOwnerLevelAndId();
}

