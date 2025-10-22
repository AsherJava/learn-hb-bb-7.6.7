/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodeicon;

import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import java.util.Map;

public interface IconSourceProvider {
    public String getDefaultIconKey();

    public String getIconKey(IconCategory var1, String var2);

    public Map<String, String> getBase64IconMap();
}

