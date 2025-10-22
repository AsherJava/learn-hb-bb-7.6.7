/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.FileWriter
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.nr.data.common.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BasePathWriter
implements FileWriter {
    private FileWriter fileWriter;
    private String basePath;

    public BasePathWriter(FileWriter fileWriter, String basePath) {
        this.fileWriter = fileWriter;
        this.basePath = basePath;
    }

    public FileWriter getFileWriter() {
        return this.fileWriter;
    }

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void addBytes(String path, byte[] data) throws IOException {
        path = this.handleDir(path);
        this.fileWriter.addBytes(path, data);
    }

    public void addFile(String path, File file) throws IOException {
        path = this.handleDir(path);
        this.fileWriter.addFile(path, file);
    }

    public void addStream(String path, InputStream stream) throws IOException {
        path = this.handleDir(path);
        this.fileWriter.addStream(path, stream);
    }

    public OutputStream createFile(String path) throws IOException {
        path = this.handleDir(path);
        return this.fileWriter.createFile(path);
    }

    private String handleDir(String dir) {
        if (dir.startsWith("/")) {
            return "/" + this.basePath + dir;
        }
        return "/" + this.basePath + "/" + dir;
    }
}

