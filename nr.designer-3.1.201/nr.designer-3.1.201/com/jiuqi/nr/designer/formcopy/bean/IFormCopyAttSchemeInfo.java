/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.formcopy.bean;

import com.jiuqi.nr.designer.formcopy.common.SchemeType;
import java.io.Serializable;

public interface IFormCopyAttSchemeInfo
extends Serializable {
    public String getKey();

    public void setKey(String var1);

    public String getFormSchemeKey();

    public void setFormSchemeKey(String var1);

    public String getSrcFormSchemeKey();

    public void setSrcFormSchemeKey(String var1);

    public SchemeType getSchemeType();

    public void setSchemeType(SchemeType var1);

    public String getSchemeKey();

    public void setSchemeKey(String var1);

    public String getSrcSchemeKey();

    public void setSrcSchemeKey(String var1);
}

