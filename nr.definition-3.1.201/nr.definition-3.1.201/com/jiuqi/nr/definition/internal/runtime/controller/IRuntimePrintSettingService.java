/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import java.util.List;

public interface IRuntimePrintSettingService {
    public PrintSettingDefine query(String var1, String var2);

    public List<PrintSettingDefine> list(String var1);
}

