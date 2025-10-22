/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BasePathFinder
implements FileFinder {
    private FileFinder fileFinder;
    private String basePath;

    public BasePathFinder(FileFinder fileFinder, String basePath) {
        this.fileFinder = fileFinder;
        this.basePath = basePath;
    }

    public BasePathFinder(FileFinder fileFinder) {
        this.fileFinder = fileFinder;
    }

    public BasePathFinder() {
    }

    public FileFinder getFileFinder() {
        return this.fileFinder;
    }

    public void setFileFinder(FileFinder fileFinder) {
        this.fileFinder = fileFinder;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    private String handleDir(String dir) {
        if (dir == null) {
            return "/" + this.basePath;
        }
        if (dir.startsWith("/")) {
            return "/" + this.basePath + dir;
        }
        return "/" + this.basePath + "/" + dir;
    }

    public List<FileEntry> listFiles(String path) throws IOException {
        String dir = path;
        path = this.handleDir(path);
        List fileEntries = this.fileFinder.listFiles(path);
        for (FileEntry fileEntry : fileEntries) {
            if (dir == null || "/".equals(dir) || dir.isEmpty()) {
                fileEntry.setFilePath(fileEntry.getFileName());
                continue;
            }
            if (dir.endsWith("/")) {
                fileEntry.setFilePath(dir + fileEntry.getFileName());
                continue;
            }
            fileEntry.setFilePath(dir + "/" + fileEntry.getFileName());
        }
        return fileEntries;
    }

    public InputStream getFileInputStream(String path) throws IOException {
        path = this.handleDir(path);
        return this.fileFinder.getFileInputStream(path);
    }

    public File getFile(String path) throws IOException {
        path = this.handleDir(path);
        return this.fileFinder.getFile(path);
    }

    public byte[] getFileBytes(String path) throws IOException {
        path = this.handleDir(path);
        return this.fileFinder.getFileBytes(path);
    }
}

