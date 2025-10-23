/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy.bean;

import java.io.Serializable;
import java.util.Date;

public interface IFormCopyRecord
extends Serializable {
    public String getKey();

    public void setKey(String var1);

    public String getFormSchemeKey();

    public void setFormSchemeKey(String var1);

    public String getFormKeys();

    public void setFormKeys(String var1);

    public String getAttScheme();

    public void setAttScheme(String var1);

    public Date getUpdateTime();

    public void setUpdateTime(Date var1);
}

