/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import java.util.Date;

public interface DesignPrintComTemDefine
extends PrintComTemDefine {
    public void setKey(String var1);

    public void setCode(String var1);

    public void setTitle(String var1);

    public void setOrder(String var1);

    public void setUpdateTime(Date var1);

    public void setPrintSchemeKey(String var1);

    public void setDescription(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTemplateData(byte[] var1);

    default public boolean isDefault() {
        return this.getKey().equals(this.getPrintSchemeKey());
    }
}

