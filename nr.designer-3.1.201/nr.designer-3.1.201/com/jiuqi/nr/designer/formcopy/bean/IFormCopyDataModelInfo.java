/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.formcopy.bean;

import java.io.Serializable;

public interface IFormCopyDataModelInfo
extends Serializable {
    public String getDataFieldKey();

    public void setDataFieldKey(String var1);

    public String getDataTableKey();

    public String getDataSchemeKey();

    public void setDataSchemeKey(String var1);

    public void setDataTableKey(String var1);

    public String getSrcDataFieldKey();

    public void setSrcDataFieldKey(String var1);

    public String getSrcDataTableKey();

    public void setSrcDataTableKey(String var1);
}

