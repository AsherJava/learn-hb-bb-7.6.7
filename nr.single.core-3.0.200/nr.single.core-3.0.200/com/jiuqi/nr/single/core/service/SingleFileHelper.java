/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.service;

import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;

public interface SingleFileHelper {
    public void splitSingleFile(String var1, String var2, String var3) throws SingleFileException;

    public void splitSingleFile(SingleFile var1, String var2, String var3, String var4) throws SingleFileException;
}

