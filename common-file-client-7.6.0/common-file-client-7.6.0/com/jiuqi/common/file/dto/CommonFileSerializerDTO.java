/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.file.dto;

public class CommonFileSerializerDTO {
    private String contentType;
    private String originalFilename;
    private String name;
    private String base64Content;

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOriginalFilename() {
        return this.originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64Content() {
        return this.base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }
}

