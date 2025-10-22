/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import java.util.List;

public interface DataGroupDesignService {
    public List<DesignDataGroup> searchBy(String var1, String var2, int var3);

    public List<DesignDataGroup> searchBy(List<String> var1, String var2, int var3);

    public List<DesignDataGroup> searchBy(String var1);

    public List<DesignDataGroup> searchBy(String var1, String var2);

    public boolean existScheme(String var1);
}

