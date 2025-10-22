/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.constant;

public class PromptConsts {
    public static final String ADD_ANN_FAILED = "\u6279\u6ce8\u6dfb\u52a0\u5931\u8d25";
    public static final String TABLE_NOTFOUND = "\u6279\u6ce8\u76f8\u5173\u5b58\u50a8\u8868\u672a\u521b\u5efa";
    public static final String UPDATE_ANNOTATION_FAILED = "\u6279\u6ce8\u4fee\u6539\u5931\u8d25";

    public static String fileOprateServiceError(String cause) {
        return String.format("\u4e1a\u52a1\u9519\u8bef\uff1a%s", cause);
    }

    public static String fileOprateSystemError(String cause) {
        return String.format("\u7cfb\u7edf\u9519\u8bef\uff1a%s", cause);
    }

    public static String causeIfError(String cause) {
        return String.format("\u51fa\u9519\u539f\u56e0\uff1a%s", cause);
    }
}

