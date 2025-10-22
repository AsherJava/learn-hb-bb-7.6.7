/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload.util;

public enum FileTypeByteEnum {
    JPG("FFD8FF", "jpg"),
    JPEG("FFD8FF", "jpeg"),
    PNG("89504E47", "png"),
    GIF("47494638", "gif"),
    XLS("D0CF11E0", "xls"),
    XLSX("504B0304", "xlsx"),
    DOC("D0CF11E0", "doc"),
    DOCX("504B0304", "docx"),
    XML("3C3F786D6C", "xml"),
    PDF("255044462D312E", "pdf"),
    CSV("4E4F434D", "csv"),
    PPT("D0CF11E0", "ppt"),
    PPTX("504B0304", "pptx"),
    WAV("57415645", "wav"),
    MID("4D546864", "mid"),
    AVI("52494646", "avi"),
    MOV("6D6F6F76", "mov"),
    RM("2E524D46", "rm"),
    MP4("0000001C", "mp4"),
    RAR("52617221", "rar"),
    ZIP("504B0304", "zip"),
    GZ("1F8B08", "gz");

    private String code;
    private String suffix;

    private FileTypeByteEnum(String code, String suffix) {
        this.code = code;
        this.suffix = suffix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getCode() {
        return this.code;
    }
}

