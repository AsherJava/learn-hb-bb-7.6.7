/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;

public interface IRuntimeFormSchemeService {
    public List<FormSchemeDefine> queryAllFormScheme();

    public List<FormSchemeDefine> queryFormSchemeByTask(String var1);

    public FormSchemeDefine getFormScheme(String var1);

    public FormSchemeDefine getFormschemeByCode(String var1);
}

