/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface MaskCellDefine
extends IMetaItem {
    public int getX();

    public int getY();

    public String getCellUniqueCode();

    public String getLevelCode();

    public MaskCellDefine getLeftCell();

    public MaskCellDefine getRightCell();

    public MaskCellDefine getUpCell();

    public MaskCellDefine getDownCell();

    public boolean isDisabled();

    public String getRelatedLink();

    public boolean isMergeHead();

    public boolean isMergeBody();
}

