/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.migration.syncscheme.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SyncSchemeErrorEnum implements ErrorEnum
{
    SYNCSCHEMEGROUP_TITLE_HAS_EXIST("ERROR_SS_01", "\u5206\u7ec4\u6807\u9898\u91cd\u590d"),
    SYNCSCHEMEGROUP_ADD_ERROR("ERROR_SS_02", "\u65b0\u589e\u5206\u7ec4\u5931\u8d25"),
    SYNCSCHEMEGROUP_UPDATE_ERROR("ERROR_SS_03", "\u4fee\u6539\u5206\u7ec4\u5931\u8d25"),
    SYNCSCHEMEGROUP_DEL_ERROR("ERROR_SS_04", "\u5220\u9664\u5206\u7ec4\u5931\u8d25"),
    SYNCSCHEMEGROUP_MOVEUP_ERROR("ERROR_SS_05", "\u4e0a\u79fb\u5206\u7ec4\u5931\u8d25"),
    SYNCSCHEMEGROUP_MOVEDOWN_ERROR("ERROR_SS_06", "\u4e0b\u79fb\u5206\u7ec4\u5931\u8d25"),
    SYNCSCHEMEGROUP_QUERY_ERROR("ERROR_SS_14", "\u67e5\u8be2\u5206\u7ec4\u5931\u8d25"),
    SYNCSCHEME_CODE_HAS_EXIST("ERROR_SS_07", "\u65b9\u6848\u6807\u8bc6\u91cd\u590d"),
    SYNCSCHEME_TITLE_HAS_EXIST("ERROR_SS_08", "\u65b9\u6848\u6807\u9898\u91cd\u590d"),
    SYNCSCHEME_ADD_ERROR("ERROR_SS_09", "\u65b0\u589e\u65b9\u6848\u5931\u8d25"),
    SYNCSCHEME_UPDATE_ERROR("ERROR_SS_10", "\u4fee\u6539\u65b9\u6848\u5931\u8d25"),
    SYNCSCHEME_DEL_ERROR("ERROR_SS_11", "\u5220\u9664\u65b9\u6848\u5931\u8d25"),
    SYNCSCHEME_MOVEUP_ERROR("ERROR_SS_12", "\u4e0a\u79fb\u65b9\u6848\u5931\u8d25"),
    SYNCSCHEME_MOVEDOWN_ERROR("ERROR_SS_13", "\u4e0b\u79fb\u65b9\u6848\u5931\u8d25"),
    SYNCSCHEME_QUERY_ERROR("ERROR_SS_15", "\u67e5\u8be2\u65b9\u6848\u5931\u8d25"),
    SYNCSCHEMEGROUP_TREE_ERROR("ERROR_SS_16", "\u67e5\u8be2\u5206\u7ec4\u6811\u5f62\u5931\u8d25"),
    SYNCSCHEMEGROUP_TREE_LOCATION_ERROR("ERROR_SS_17", "\u5b9a\u4f4d\u5206\u7ec4\u6811\u5f62\u5931\u8d25"),
    SYNCSCHEME_DATA_SAVE_ERROR("ERROR_SS_18", "\u65b9\u6848\u6570\u636e\u4fdd\u5b58\u5931\u8d25");

    private final String code;
    private final String message;

    private SyncSchemeErrorEnum(String code, String message) {
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

