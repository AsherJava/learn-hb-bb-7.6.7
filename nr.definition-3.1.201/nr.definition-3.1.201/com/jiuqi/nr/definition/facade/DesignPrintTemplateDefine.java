/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import java.util.Date;

public interface DesignPrintTemplateDefine
extends PrintTemplateDefine {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setDescription(String var1);

    public void setPrintSchemeKey(String var1);

    public void setFormKey(String var1);

    public void setAutoRefreshForm(boolean var1);

    public void setFormUpdateTime(Date var1);

    public void setTemplateData(byte[] var1);

    public void setLabelData(byte[] var1);

    public void setComTemCode(String var1);
}

