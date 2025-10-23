/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.dynamic;

import com.jiuqi.nr.multcheck2.bean.MCSchemeParam;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import java.util.List;

public interface IMultcheckSchemeService {
    public List<MultcheckScheme> getSchemeByForm(String var1, String var2);

    public void createDefaultScheme(String var1, String var2, String var3);

    public List<MultcheckScheme> getAllMCSchemes();

    public List<MultcheckScheme> getMCSchemes(String var1);

    public List<MCSchemeParam> getMCSchemeParams(String var1);

    public void deleteMCSchemeParams(List<String> var1);

    public void addMCSchemeParams(List<MCSchemeParam> var1);
}

