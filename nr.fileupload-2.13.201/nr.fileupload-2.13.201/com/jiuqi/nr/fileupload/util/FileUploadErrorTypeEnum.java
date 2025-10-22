/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.fileupload.util;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FileUploadErrorTypeEnum implements ErrorEnum
{
    BLACK_LIST_ERROR("blackListError", "\u6587\u4ef6\u4fe1\u606f\u672a\u901a\u8fc7\u9ed1\u540d\u5355\u4fe1\u606f\u6821\u9a8c\uff01"),
    WHITE_LIST_ERROR("whiteListError", "\u6587\u4ef6\u4fe1\u606f\u672a\u901a\u8fc7\u767d\u540d\u5355\u4fe1\u606f\u6821\u9a8c\uff01"),
    FILE_SIZE_ERROR("fileSizeError", "\u6587\u4ef6\u5927\u5c0f\u672a\u901a\u8fc7\u7cfb\u7edf\u6821\u9a8c\uff01"),
    FILE_APP_ERROR("fileAPPError", "\u6587\u4ef6\u672a\u901a\u8fc7\u529f\u80fd\u4fe1\u606f\u6821\u9a8c\uff01"),
    FILE_APP_ERROR_FORMAT("fileAPPErrorFormat", "\u6587\u4ef6\u672a\u901a\u8fc7\u529f\u80fd\u4fe1\u606f\u4e2d\u540e\u7f00\u6821\u9a8c\uff01"),
    FILE_APP_ERROR_SIZE("fileAPPErrorSize", "\u6587\u4ef6\u672a\u901a\u8fc7\u529f\u80fd\u4fe1\u606f\u4e2d\u5927\u5c0f\u6821\u9a8c\uff01"),
    FILE_UPLOAD_EXCEPTION_ERROR("fileUploadExceptionError", "\u6587\u4ef6\u4e0a\u4f20\u5f02\u5e38\uff01"),
    FILE_SCENE_ERROR("fileSceneError", "\u6587\u4ef6\u7c7b\u578b\u672a\u901a\u8fc7\u573a\u666f\u4fe1\u606f\u6821\u9a8c\uff01"),
    SUFFIX_TYPE_MISMATCH("suffixTypeMismatch", "\u6587\u4ef6\u540e\u7f00\u4e0e\u6587\u4ef6\u7c7b\u578b\u4e0d\u5339\u914d"),
    FILE_UPLOAD_OTHER_ERROR("fileUploadOtherError", "\u6587\u4ef6\u6269\u5c55\u6821\u9a8c\u5931\u8d25\uff01");

    private final String code;
    private final String message;

    private FileUploadErrorTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

