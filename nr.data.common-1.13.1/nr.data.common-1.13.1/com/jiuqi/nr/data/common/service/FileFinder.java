/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service;

import com.jiuqi.nr.data.common.service.dto.FileEntry;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileFinder {
    public List<FileEntry> listFiles(String var1) throws IOException;

    public InputStream getFileInputStream(String var1) throws IOException;

    public File getFile(String var1) throws IOException;

    public byte[] getFileBytes(String var1) throws IOException;
}

