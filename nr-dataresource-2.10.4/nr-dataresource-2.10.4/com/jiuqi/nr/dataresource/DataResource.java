/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.GroupSetter
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 */
package com.jiuqi.nr.dataresource;

import com.jiuqi.nr.dataresource.DataBasic;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.datascheme.api.core.GroupSetter;
import com.jiuqi.nr.datascheme.api.core.Grouped;

public interface DataResource
extends DataBasic,
Grouped,
GroupSetter {
    public String getResourceDefineKey();

    public DataResourceKind getResourceKind();

    public void setResourceDefineKey(String var1);

    public void setResourceKind(DataResourceKind var1);

    public String getDimKey();

    public void setDimKey(String var1);

    public String getDataTableKey();

    public void setDataTableKey(String var1);

    public String getDataSchemeKey();

    public void setDataSchemeKey(String var1);

    public String getSource();

    public void setSource(String var1);

    public String getLinkZb();

    public void setLinkZb(String var1);
}

