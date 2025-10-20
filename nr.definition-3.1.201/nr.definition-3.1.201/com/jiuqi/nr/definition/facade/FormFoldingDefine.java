/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.common.FormFoldingDirEnum;
import com.jiuqi.nr.definition.common.FormFoldingSpecialRegion;
import java.util.List;

public interface FormFoldingDefine
extends IMetaItem {
    public String getFormKey();

    public Integer getStartIdx();

    public Integer getEndIdx();

    public List<FormFoldingSpecialRegion> getHiddenRegion();

    public FormFoldingDirEnum getDirection();

    public boolean isFolding();
}

