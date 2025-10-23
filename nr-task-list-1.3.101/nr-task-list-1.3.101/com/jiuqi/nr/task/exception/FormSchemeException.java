/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.exception;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.task.common.TaskI18nUtil;

public enum FormSchemeException implements ErrorEnum
{
    FORM_SCHEME_INSERT_FAILED("ERROR_FS_01", "\u65b0\u589e\u62a5\u8868\u65b9\u6848\u5931\u8d25"),
    FORM_SCHEME_DELETE_FAILED("ERROR_FS_02", "\u5220\u9664\u62a5\u8868\u65b9\u6848\u5931\u8d25"),
    FORM_SCHEME_UPDATE_FAILED("ERROR_FS_03", "\u66f4\u65b0\u62a5\u8868\u65b9\u6848\u5931\u8d25"),
    FORM_SCHEME_QUERY_FAILED("ERROR_FS_04", "\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5931\u8d25"),
    FORM_SCHEME_TITLE_ILLEGAL("ERROR_FS_05", "\u6807\u9898\u4e0d\u5305\u542b/?*[]'"),
    FORM_SCHEME_CODE_ILLEGAL("ERROR_FS_06", "\u6807\u8bc6\u7531\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u4e0b\u5212\u7ebf\u7ec4\u6210"),
    FORM_SCHEME_NAME_LENGTH_ILLEGAL("ERROR_FS_07", "\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc725\u4e2a\u5b57\u7b26"),
    FORM_SCHEME_RELEASE_FAILED("ERROR_FS_08", "\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5931\u8d25"),
    FORM_SCHEME_RELEASE_FORM_FAILED("ERROR_FS_09", "\u53d1\u5e03\u5931\u8d25"),
    FORM_SCHEME_RELEASE_TIMEOUT("ERROR_FS_15", "\u7b49\u5f85\u8d85\u65f6\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5"),
    FORM_SCHEME_SCHEDULED_NO_DDL("ERROR_FS_10", "\u67e5\u8be2\u65e0DDL\u6743\u9650\u914d\u7f6e\u5931\u8d25"),
    FORM_SCHEME_SCHEDULED_NO_DDL1("ERROR_FS_11", "\u65e0DDL\u53d1\u5e03\u5931\u8d25"),
    FORM_SCHEME_SCHEDULED_NO_DDL2("ERROR_FS_12", "\u65e0DDL\u751f\u6210SQL\u5931\u8d25"),
    FORM_SCHEME_SCHEDULED_NO_DDL3("ERROR_FS_13", "\u65e0DDL\u64a4\u9500\u5931\u8d25"),
    FORM_SCHEME_CODE_REPEAT("ERROR_FS_14", "\u62a5\u8868\u65b9\u6848\u6807\u8bc6\u91cd\u590d"),
    OLD_MAPPING_SCHEME_QUERY("ERROR_FS_101", "\u6620\u5c04\u65b9\u6848\u67e5\u8be2\u5f02\u5e38"),
    OLD_MAPPING_SCHEME_DEL("ERROR_FS_102", "\u5220\u9664\u6620\u5c04\u65b9\u6848\u9519\u8bef");

    private String code;
    private String message;

    private FormSchemeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return TaskI18nUtil.getMessage("jqException.formScheme." + this.code, new Object[0]);
    }
}

