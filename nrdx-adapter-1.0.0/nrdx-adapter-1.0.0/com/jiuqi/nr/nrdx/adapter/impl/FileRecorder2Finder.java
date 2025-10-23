/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferFileCacheException
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 */
package com.jiuqi.nr.nrdx.adapter.impl;

import com.jiuqi.bi.transfer.engine.ex.TransferFileCacheException;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileRecorder2Finder
implements FileFinder {
    private final IFileRecorder recorder;

    public FileRecorder2Finder(IFileRecorder recorder) {
        this.recorder = recorder;
    }

    public List<FileEntry> listFiles(String path) throws IOException {
        throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301");
    }

    public InputStream getFileInputStream(String path) throws IOException {
        try {
            return this.recorder.getDataStream(path);
        }
        catch (TransferFileCacheException e) {
            throw new IOException(e);
        }
    }

    public File getFile(String path) throws IOException {
        throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301");
    }

    public byte[] getFileBytes(String path) throws IOException {
        try {
            return this.recorder.getData(path);
        }
        catch (TransferFileCacheException e) {
            throw new IOException(e);
        }
    }
}

