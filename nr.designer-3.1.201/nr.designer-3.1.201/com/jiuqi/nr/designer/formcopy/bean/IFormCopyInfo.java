/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.formcopy.bean;

import java.io.Serializable;
import java.util.Date;

public interface IFormCopyInfo
extends Serializable {
    public String getFormKey();

    public void setFormKey(String var1);

    public String getFormSchemeKey();

    public void setFormSchemeKey(String var1);

    public String getSrcFormKey();

    public void setSrcFormKey(String var1);

    public String getSrcFormSchemeKey();

    public void setSrcFormSchemeKey(String var1);

    public Date getUpdateTime();

    public void setUpdateTime(Date var1);
}

