/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.constant;

public class PromptConsts {
    public static final String ERROR_TITLE = "\u9644\u4ef6\u5bfc\u5165\u5931\u8d25";
    public static final String FILE_DOWNLOAD_TITLE = "\u9644\u4ef6\u4e0b\u8f7d\u5f00\u59cb";
    public static final String BATCH_MARK_DEL_TITLE = "\u6279\u91cf\u6807\u8bb0\u5220\u9664\u5b8c\u6210";
    public static final String TABLE_NOTFOUND_MESSAGE = "\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01";
    public static final String RETURN_DATA_ERROR = "error";
    public static final String DOWNLOAD_FILE_SUCCESS = "\u9644\u4ef6\u4e0b\u8f7d\u6210\u529f";
    public static final String UPLOAD_FILEPOOL_FAILED = "\u9644\u4ef6\u4e0a\u4f20\u9644\u4ef6\u6c60\u5931\u8d25";
    public static final String FILE_FIELD_REL_FAILED = "\u9644\u4ef6\u548c\u6307\u6807\u5173\u8054\u5931\u8d25";
    public static final String BEGIN_UPLOAD_FILE = "\u9644\u4ef6\u4e0a\u4f20\u5f00\u59cb";
    public static final String UPLOAD_FILE_ERRTITLE = "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25";
    public static final String UPLOAD_FILE_SUCCESS = "\u9644\u4ef6\u4e0a\u4f20\u6210\u529f";
    public static final String UPLOAD_FILE_WITHFILEKEY_SUCCESS = "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u6210\u529f";
    public static final String UPLOAD_FILE_WITHFILEKEY_FAILED = "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u5931\u8d25";
    public static final String DELETE_FILE_FAILED = "\u9644\u4ef6\u5220\u9664\u5931\u8d25";
    public static final String MARKDEL_FILE_BYGROUP_FAILED = "\u6839\u636e\u5206\u7ec4\u6807\u8bb0\u5220\u9664\u9644\u4ef6\u5931\u8d25";

    private PromptConsts() {
    }

    public static String causeIfError(String cause) {
        return String.format("\u51fa\u9519\u539f\u56e0\uff1a%s", cause);
    }

    public static String fileOprateServiceError(String cause) {
        return String.format("\u4e1a\u52a1\u9519\u8bef\uff1a%s", cause);
    }

    public static String fileOprateSystemError(String cause) {
        return String.format("\u7cfb\u7edf\u9519\u8bef\uff1a%s", cause);
    }
}

