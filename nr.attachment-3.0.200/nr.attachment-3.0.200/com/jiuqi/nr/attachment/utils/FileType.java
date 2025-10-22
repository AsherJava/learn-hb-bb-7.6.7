/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public enum FileType {
    UNKNOW(""),
    PNG(".png", "image/png"),
    JPG(".jpg", "image/jpeg"),
    JPEG(".jpeg", "image/jpeg"),
    GIF(".gif", "image/gif"),
    SVG(".svg", "image/svg+xml"),
    ZIP(".zip", "application/zip"),
    GZIP(".gzip", "application/gzip");

    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    static Map<String, FileType> TYPES;
    private String extension;
    private String contentType;

    public String getExtension() {
        return this.extension;
    }

    public String getContentType() {
        return this.contentType;
    }

    private FileType(String extension) {
        this(extension, DEFAULT_CONTENT_TYPE);
    }

    private FileType(String extension, String contentType) {
        if (extension == null) {
            throw new IllegalArgumentException("'extension' must not be null.");
        }
        this.extension = extension.toLowerCase();
        this.contentType = contentType;
        FileType.register(this);
    }

    private static void register(FileType fileType) {
        if (TYPES == null) {
            TYPES = new LinkedHashMap<String, FileType>();
        }
        if (TYPES.keySet().contains(fileType.getExtension())) {
            throw new IllegalArgumentException("file type duplicate. file extension " + fileType.getExtension() + " has exists.");
        }
        TYPES.put(fileType.getExtension(), fileType);
    }

    public static FileType valueOfExtension(String extension) {
        if (extension == null) {
            return UNKNOW;
        }
        FileType fileType = TYPES.get(extension);
        return fileType == null ? UNKNOW : fileType;
    }
}

