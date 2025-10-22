/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodeicon;

import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import java.util.Set;

public interface IconSourceScheme {
    public String getSchemeId();

    public IconCategory getCategory();

    public Set<String> getValues();

    public IconSource getIconSource(String var1);

    default public boolean canBeCached() {
        return true;
    }
}

