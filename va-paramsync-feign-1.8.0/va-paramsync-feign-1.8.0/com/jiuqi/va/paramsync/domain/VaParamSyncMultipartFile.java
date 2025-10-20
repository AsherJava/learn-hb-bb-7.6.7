/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.paramsync.domain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class VaParamSyncMultipartFile
implements MultipartFile {
    private String contentType;
    private String originalFilename;
    private String name;
    private byte[] content;

    public VaParamSyncMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
        this.content = content;
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }

    public String getName() {
        return this.name;
    }

    public String getOriginalFilename() {
        return this.originalFilename;
    }

    public String getContentType() {
        return this.contentType;
    }

    public boolean isEmpty() {
        return this.content == null || this.content.length == 0;
    }

    public long getSize() {
        return this.content.length;
    }

    public byte[] getBytes() throws IOException {
        return this.content;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream stream = new FileOutputStream(dest);){
            stream.write(this.content);
        }
        catch (Throwable e) {
            throw new RuntimeException(e.getCause());
        }
    }
}

