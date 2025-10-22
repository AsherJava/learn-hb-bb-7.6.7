/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.formtype.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FormTypeExceptionEnum implements ErrorEnum
{
    ADD_DATA_ERROR("001", "\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5f02\u5e38"),
    UPDATE_DATA_ERROR("002", "\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5f02\u5e38"),
    DELETE_DATA_ERROR("003", "\u5220\u9664\u62a5\u8868\u7c7b\u578b\u6570\u636e\u5f02\u5e38"),
    ADD_DEFINE_ERROR("101", "\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5b9a\u4e49\u5f02\u5e38"),
    UPDATE_DEFINE_ERROR("102", "\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5b9a\u4e49\u5f02\u5e38"),
    DELETE_DEFINE_ERROR("103", "\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5b9a\u4e49\u5f02\u5e38"),
    ADD_GROUP_ERROR("201", "\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5f02\u5e38"),
    UPDATE_GROUP_ERROR("202", "\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5f02\u5e38"),
    DELETE_GROUP_ERROR("203", "\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5f02\u5e38"),
    DELETE_GROUP_CHECK_ERROR("204", "\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5206\u7ec4\u5f02\u5e38:\u4e0b\u7ea7\u5b58\u5728\u6570\u636e\u65e0\u6cd5\u5220\u9664"),
    CODE_EXISTS_ERROR("301", "\u6807\u8bc6\u5df2\u7ecf\u5b58\u5728"),
    BASEDATA_NOTEXISTS_ERROR("302", "\u62a5\u8868\u7c7b\u578b\u5bf9\u5e94\u57fa\u7840\u6570\u636e\u4e0d\u5b58\u5728"),
    CODE_INVALID_ERROR("303", "\u62a5\u8868\u7c7b\u578b\u6807\u8bc6\u4e0d\u5408\u89c4\u8303\uff08MD_BBLX_\u5f00\u5934\uff09"),
    CODE_NOCHANGE_ERROR("304", "\u62a5\u8868\u7c7b\u578b\u6807\u8bc6\u4e0d\u5141\u8bb8\u66f4\u6539"),
    ORG_ADD_BBLX_ERROR("401", "\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5\u5931\u8d25"),
    ORG_UPDATE_BBLX_ERROR("402", "\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5\u5931\u8d25"),
    ORG_DELETE_BBLX_ERROR("403", "\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5\u5931\u8d25"),
    ORG_ONTEXISTS_ERROR("404", "\u673a\u6784\u7c7b\u578b\u4e0d\u5b58\u5728"),
    ORG_REMOVE_BBLX_ERROR("405", "\u79fb\u9664\u62a5\u8868\u7c7b\u578b\u5173\u8054\u5931\u8d25\uff1a\u5df2\u6709\u5dee\u989d\u548c\u672c\u90e8\u5355\u4f4d\u7981\u6b62\u79fb\u9664."),
    ORG_UPDATE_BBLX_ED("406", "\u4fee\u6539\u62a5\u8868\u7c7b\u578b\u5173\u8054\u5931\u8d25\uff1a\u5df2\u6709\u6570\u636e\u7981\u6b62\u4fee\u6539."),
    DATA_DISABLE_DELETE("501", "\u62a5\u8868\u7c7b\u578b\u5df2\u7ecf\u88ab\u5173\u8054\u5230\u6709\u6570\u636e\u7684\u673a\u6784\u7c7b\u578b\uff0c\u7981\u6b62\u5220\u9664"),
    DATA_DISABLE_UPDATE("502", "\u62a5\u8868\u7c7b\u578b\u5df2\u7ecf\u88ab\u5173\u8054\u5230\u6709\u6570\u636e\u7684\u673a\u6784\u7c7b\u578b\uff0c\u7981\u6b62\u4fee\u6539"),
    DATA_UNITNATURE_UNIQUE("503", "\u8fdd\u53cd\u5355\u4f4d\u6027\u8d28\u552f\u4e00\u7ea6\u675f"),
    DATA_UNITNATURE_UNKNOWN("504", "\u672a\u77e5\u5355\u4f4d\u6027\u8d28\u7ea6\u675f"),
    IMPORT_ERROR("601", "\u62a5\u8868\u7c7b\u578b\u5bfc\u51fa\u5931\u8d25"),
    EXPORT_ERROR("602", "\u62a5\u8868\u7c7b\u578b\u5bfc\u5165\u5931\u8d25"),
    ERROR_701("701", "\u672a\u5728\u7cfb\u7edf\u9009\u9879\u4e2d\u542f\u7528\u56fd\u8d44\u59d4\u6a21\u5f0f");

    private String code;
    private String message;

    private FormTypeExceptionEnum(String code, String message) {
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

