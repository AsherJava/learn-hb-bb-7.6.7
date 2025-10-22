/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.FormFoldingDirEnum;
import com.jiuqi.nr.definition.common.FormFoldingSpecialRegion;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import java.util.Date;
import java.util.List;

public interface DesignFormFoldingDefine
extends FormFoldingDefine {
    public void setKey(String var1);

    public void setFormKey(String var1);

    public void setStartIdx(Integer var1);

    public void setEndIdx(Integer var1);

    public void setHiddenRegion(List<FormFoldingSpecialRegion> var1);

    public void setDirection(FormFoldingDirEnum var1);

    public void setFolding(boolean var1);

    public void setUpdateTime(Date var1);
}

