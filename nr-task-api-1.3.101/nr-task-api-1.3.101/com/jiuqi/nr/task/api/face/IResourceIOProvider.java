/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.face;

import java.io.InputStream;
import java.io.OutputStream;

public interface IResourceIOProvider {
    public void exportParam(OutputStream var1);

    public void importParam(InputStream var1);

    public void exportData(OutputStream var1);

    public void importData(InputStream var1);
}

