/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface FormulaFunctionDefine
extends IMetaItem {
    public String getName();

    public String getTitle();

    public String getGroup();

    public String getApplication();

    public String getExample();

    public String getFunction();

    public int getFuntionType();

    public void setKey(String var1);

    public void setOrder(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setName(String var1);

    public void setTitle(String var1);

    public void setGroup(String var1);

    public void setApplication(String var1);

    public void setExample(String var1);

    public void setFunction(String var1);

    public void setFuntionType(int var1);
}

