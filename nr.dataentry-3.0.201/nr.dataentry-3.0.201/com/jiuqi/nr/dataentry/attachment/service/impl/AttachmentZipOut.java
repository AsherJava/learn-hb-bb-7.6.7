/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.service.impl;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;

public class AttachmentZipOut
implements Closeable {
    private File file;
    private ZipOutputStream zipOut;

    @Override
    public void close() throws IOException {
        FileUtils.deleteQuietly(this.file);
        if (this.zipOut != null) {
            this.zipOut.close();
        }
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setZipOut(ZipOutputStream zipOut) {
        this.zipOut = zipOut;
    }

    public ZipOutputStream getZipOut() {
        return this.zipOut;
    }
}

