/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service;

import org.apache.poi.ss.usermodel.Sheet;

public interface IZbIOService {
    public void exportZbInfo(String var1, String var2, Sheet var3);

    public void importZbInfo(String var1, String var2, Sheet var3) throws Exception;
}

