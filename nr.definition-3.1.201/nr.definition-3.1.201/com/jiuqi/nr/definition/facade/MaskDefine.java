/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.facade.MaskCellDefine;
import java.util.List;

public interface MaskDefine
extends IMetaItem {
    public int getRowCount();

    public int getColCount();

    public String getLevelCode();

    public String getHideRowNums();

    public String getHideColNums();

    public MaskCellDefine getMaskCell(int var1, int var2);

    public MaskCellDefine getMaskCellByUniqueCode(String var1);

    public List<MaskCellDefine> getMaskCellByLevel(String var1);

    public MaskDefine mergeMask(MaskDefine var1);

    public boolean isCurrentLevelChanged();
}

