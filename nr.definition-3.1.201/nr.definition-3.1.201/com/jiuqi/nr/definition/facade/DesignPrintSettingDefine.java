/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import java.util.Date;

public interface DesignPrintSettingDefine
extends PrintSettingDefine {
    public void setPageSize(PageSize var1);

    public void setLandscape(Boolean var1);

    public void setTopMargin(Double var1);

    public void setBottomMargin(Double var1);

    public void setLeftMargin(Double var1);

    public void setRightMargin(Double var1);

    public void setScale(Short var1);

    public void setFitToWidth(Short var1);

    public void setFitToHeight(Short var1);

    public void setColumnBreaks(int[] var1);

    public void setRowBreaks(int[] var1);

    public void setHorizontallyCenter(Boolean var1);

    public void setVerticallyCenter(Boolean var1);

    public void setLeftToRight(Boolean var1);

    public void setPrintSchemeKey(String var1);

    public void setFormKey(String var1);

    public void setUpdateTime(Date var1);

    public void setOwnerLevelAndId(String var1);
}

