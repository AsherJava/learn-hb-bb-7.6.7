/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.type.RelationType;
import java.io.Serializable;

public interface DataTableRel
extends Cloneable,
Serializable {
    public String getKey();

    public RelationType getType();

    public String getDataSchemeKey();

    public String getSrcTableKey();

    public String getDesTableKey();

    public String[] getSrcFieldKeys();

    public String[] getDesFieldKeys();
}

