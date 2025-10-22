/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.service;

import org.apache.poi.ss.usermodel.Sheet;

public interface DataFieldIOService {
    public String export(String var1, Sheet var2);

    public void imports(String var1, Sheet var2);
}

