/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.fmdmdisplay.intf;

import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;

public interface FMDMDisplaySchemeService {
    public FMDMDisplayScheme findDisplayScheme(String var1);

    public FMDMDisplayScheme findDisplayScheme(String var1, String var2, String var3);

    public FMDMDisplayScheme getCurrentDisplayScheme(String var1, String var2);

    public FMDMDisplayScheme getCurrentDisplayScheme(String var1);

    public int saveFMDMDisplayScheme(FMDMDisplayScheme var1);

    public int updateFMDMDisplayScheme(FMDMDisplayScheme var1);
}

