/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.constant;

import java.util.Arrays;
import java.util.List;

public class ExpConsts {
    public static final String EXP_PATH_NAME = "annotation";
    public static final String EXP_JSON_FILENAME_SUFFIX = ".json";
    public static final String EXP_CSV_FILENAME_SUFFIX = ".csv";
    public static final String EXP_ANNO_CSV_SUFFIX = "-anno";
    public static final String EXP_REL_CSV_SUFFIX = "-rel";
    public static final String EXP_COMMENT_CSV_SUFFIX = "-comment";
    public static final String EXP_TYPE_CSV_SUFFIX = "-type";
    public static final String EXP_ZIP_FILENAME_SUFFIX = ".zip";
    public static final char CSV_FILE_DELIMITER = ',';
    public static final List<String> EXP_ANNO_CSV_HEADER_CODES = Arrays.asList("id", "content", "userName", "date");
    public static final List<String> EXP_REL_ANNO_CSV_HEADER_CODES = Arrays.asList("id", "annoId", "formKey", "formCode", "formTitle", "regionKey", "regionLeft", "regionRight", "regionTop", "regionBottom", "type", "dataLinkKey", "posX", "posY", "fieldKey", "rowId", "show");
    public static final List<String> EXP_COMMENT_ANNO_CSV_HEADER_CODES = Arrays.asList("id", "annoId", "content", "userName", "repyUserName", "date");
    public static final List<String> EXP_TYPE_ANNO_CSV_HEADER_CODES = Arrays.asList("annoId", "typeCode");
}

