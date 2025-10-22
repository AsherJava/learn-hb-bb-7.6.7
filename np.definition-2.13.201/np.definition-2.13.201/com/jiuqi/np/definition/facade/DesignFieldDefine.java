/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.facade.DesignUniversalFieldDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;

public interface DesignFieldDefine
extends FieldDefine,
DesignUniversalFieldDefine {
    public void setFixedSize(boolean var1);

    public void setGlobalUnique(boolean var1);

    public void setIsMoneyMeasure(boolean var1);

    public void setGatherType(FieldGatherType var1);

    public void setAllowUndefinedCode(boolean var1);

    public void setAllowMultipleSelect(boolean var1);

    public void setMaxMultipleSelectedCount(int var1);

    public void setAllowNotLeafNodeRefer(boolean var1);

    public void setShowFormat(String var1);

    public void setSecretLevel(int var1);

    public void setMeasureUnit(String var1);

    public void setAllowMultipleMap(boolean var1);

    public void setCanModifyByMapTarget(boolean var1);

    public String getRealFieldName();

    public void setPropertyType(String var1);

    public void setFormatProperties(FormatProperties var1);

    public void setEntityKey(String var1);

    public void setAlias(String var1);
}

