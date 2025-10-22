/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.attachment.common;

import java.util.Arrays;
import java.util.List;

public class FieldDataFileConsts {
    public static final String ATTACHMENT_PATH = "attachment";
    public static final String ATTACHMENT_RELATION_FILENAME = "ATTACHMENT_RELATION_INFO";
    public static final String ATTACHMENT_RELATION_FILESUFFIX = ".csv";
    public static final List<String> ATTACHMENT_RELATION_HEADER = Arrays.asList("groupKey", "fileKey", "fileName", "fileSize", "fileSecret", "category", "dataTableCode", "fieldCode");

    private FieldDataFileConsts() {
    }
}

