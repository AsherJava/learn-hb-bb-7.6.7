/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileWriter {
    public void addBytes(String var1, byte[] var2) throws IOException;

    public void addFile(String var1, File var2) throws IOException;

    public void addStream(String var1, InputStream var2) throws IOException;

    public OutputStream createFile(String var1) throws IOException;
}

