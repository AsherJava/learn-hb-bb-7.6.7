/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import java.util.Date;

public interface DesignPrintTemplateSchemeDefine
extends PrintTemplateSchemeDefine {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setDescription(String var1);

    public void setTaskKey(String var1);

    public void setFormSchemeKey(String var1);

    public void setCommonAttribute(byte[] var1);

    public void setGatherCoverData(byte[] var1);
}

