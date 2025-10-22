/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.FileWriter
 */
package com.jiuqi.nr.data.attachment.service.impl;

import com.jiuqi.nr.data.common.service.FileWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.FilenameUtils;

public class DefaultFieldDataExpFileWriter
implements FileWriter {
    private String basePath;

    public DefaultFieldDataExpFileWriter(String basePath) {
        this.basePath = basePath.replace("\\\\", "\\/");
    }

    public void addBytes(String path, byte[] data) throws IOException {
    }

    public void addFile(String path, File file) throws IOException {
    }

    public void addStream(String path, InputStream stream) throws IOException {
    }

    public OutputStream createFile(String path) throws IOException {
        boolean newFile;
        boolean mkdirs;
        String filePath = this.basePath.endsWith("/") ? this.basePath + path : this.basePath + "/" + path;
        File dir = new File(filePath = FilenameUtils.normalize(filePath));
        if (!dir.exists() && !(mkdirs = dir.mkdirs())) {
            throw new IOException("\u521b\u5efa\u76ee\u5f55\u5931\u8d25\uff1a" + filePath);
        }
        File attRelFile = new File(filePath);
        if (!attRelFile.exists() && !(newFile = attRelFile.createNewFile())) {
            throw new IOException("\u521b\u5efa\u6587\u4ef6\u5931\u8d25\uff1a" + filePath);
        }
        return new FileOutputStream(attRelFile);
    }
}

