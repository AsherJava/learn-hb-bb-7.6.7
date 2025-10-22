/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.enue;

import java.util.Arrays;
import java.util.List;

public enum FileTypes {
    WORD(Arrays.asList("doc", "docx")),
    EXCEL(Arrays.asList("xls", "xlsx")),
    PPT(Arrays.asList("ppt", "pptx")),
    PDF(Arrays.asList("pdf")),
    TXT(Arrays.asList("txt")),
    PICTURE(Arrays.asList("jpg", "png", "jpeg")),
    AUDIO(Arrays.asList("mp3", "wav", "wma")),
    VIDEO(Arrays.asList("mp4", "avi", "flv")),
    COMPRESS(Arrays.asList("zip", "rar", "7z")),
    OTHER(Arrays.asList("*")),
    ALL(Arrays.asList("doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "txt", "jpg", "png", "jpeg", "mp3", "wav", "wma", "mp4", "avi", "flv", "zip", "rar", "7z"));

    private List<String> type;

    private FileTypes(List<String> type) {
        this.type = type;
    }

    public List<String> getType() {
        return this.type;
    }
}

