/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 */
package com.jiuqi.nr.data.attachment.service.impl;

import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DefaultFieldDataImpFileFinder
implements FileFinder {
    private String basePath;

    public DefaultFieldDataImpFileFinder(String basePath) {
        basePath = basePath.replace("\\\\", "\\/");
        String string = basePath = basePath.endsWith("/") ? basePath : basePath + "/";
        if (basePath.endsWith("attachment/")) {
            basePath = (basePath = basePath.replaceAll("attachment/", "")).endsWith("/") ? basePath : basePath + "/";
        }
        this.basePath = basePath;
    }

    public List<FileEntry> listFiles(String path) throws IOException {
        File[] files;
        ArrayList<FileEntry> fileEntries = new ArrayList<FileEntry>();
        File directory = new File(this.basePath + path);
        if (directory.isDirectory() && null != (files = directory.listFiles())) {
            for (File file : files) {
                FileEntry fileEntry = new FileEntry();
                fileEntry.setFileName(file.getName());
                fileEntry.setFilePath(file.getAbsolutePath());
                fileEntries.add(fileEntry);
            }
        }
        return fileEntries;
    }

    public InputStream getFileInputStream(String path) throws IOException {
        try {
            return new FileInputStream(this.basePath + path);
        }
        catch (Exception e) {
            return null;
        }
    }

    public File getFile(String path) throws IOException {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return null;
            }
            return file;
        }
        catch (Exception e) {
            return null;
        }
    }

    public byte[] getFileBytes(String path) throws IOException {
        return null;
    }
}

