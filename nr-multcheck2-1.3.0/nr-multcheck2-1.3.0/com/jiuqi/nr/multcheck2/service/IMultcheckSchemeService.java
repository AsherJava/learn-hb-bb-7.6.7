/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import java.util.List;

@Deprecated
public interface IMultcheckSchemeService {
    public List<MultcheckScheme> getSchemeByForm(String var1);

    @Deprecated
    public void createDefaultScheme(String var1, String var2);

    public void createDefaultScheme(String var1, String var2, String var3);
}

