/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

public enum ObjectTypeEnum {
    EXT("exe", "application/octet-stream"),
    BIN("bin", "application/octet-stream"),
    CRT("crt", "application/x-x509-ca-cert"),
    DLL("dll", "application/x-msdownload"),
    DOC("doc", "application/msword"),
    DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOT("dot", "application/msword"),
    PDF("pdf", "application/pdf"),
    PPT("ppt", "application/vnd.ms-powerpoint"),
    PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    PPS("pps", "application/vnd.ms-powerpoint"),
    WPS("wps", "application/vnd.ms-works"),
    XLS("xls", "application/vnd.ms-excel"),
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    RTF("rtf", "application/rtf"),
    TAR("tar", "application/x-tar"),
    GTAR("gtar", "application/x-gtar"),
    GZ("gz", "application/x-gzip"),
    ZIP("zip", "application/zip"),
    AVI("avi", "video/x-msvideo"),
    WAV("wav", "audio/x-wav"),
    MP3("mp3", "audio/mpeg"),
    BMP("bmp", "image/bmp"),
    GIF("gif", "image/gif"),
    ICO("ico", "image/x-icon"),
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    TIFF("tiff", "image/tiff"),
    SVG("svg", "image/svg+xml"),
    C("c", "text/plain"),
    CSS("css", "text/css"),
    HTM("htm", "text/html"),
    HTML("html", "text/html"),
    TXT("txt", "text/plain"),
    OTHER("*", "application/octet-stream");

    private String extension;
    private String contentType;

    private ObjectTypeEnum(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getContentType() {
        return this.contentType;
    }

    public static ObjectTypeEnum findByExtension(String extension) {
        ObjectTypeEnum[] values;
        if (extension == null || extension.length() == 0) {
            return OTHER;
        }
        for (ObjectTypeEnum v : values = ObjectTypeEnum.values()) {
            if (!v.getExtension().equalsIgnoreCase(extension)) continue;
            return v;
        }
        return OTHER;
    }
}

