/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.entity.adapter.impl.org.base;

import com.jiuqi.nr.entity.adapter.impl.org.exception.OrgDataException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class OrgImportObject
implements MultipartFile {
    private String contentType;
    private String originalFilename;
    private String name;
    private byte[] content;

    public OrgImportObject(InputStream inputStream) throws IOException {
        this("multipartFile", UUID.randomUUID().toString(), "multipart/form-data; charset=ISO-8859-1", inputStream);
    }

    public OrgImportObject(String name, String originalFilename, String contentType, InputStream inputStream) throws IOException {
        this.content = IOUtils.toByteArray(inputStream);
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
        try (FileOutputStream outputStream = new FileOutputStream(dest);){
            outputStream.write(this.content);
        }
        catch (Exception e) {
            throw new OrgDataException("\u6587\u4ef6\u8f6c\u6362\u5931\u8d25", e);
        }
    }
}

