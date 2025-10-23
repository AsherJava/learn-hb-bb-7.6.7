/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.multcheck2.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum MultcheckSchemeError implements ErrorEnum
{
    SCHEME_001("S001", "\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_002("S002", "\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_003("S003", "\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_004("S004", "\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_005("S005", "\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_006("S006", "\u7ed1\u5b9a\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_007("S007", "\u9009\u62e9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_008("S008", "\u5355\u4f4d\u8fc7\u6ee4\u6761\u4ef6\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_009("S009", "\u65b9\u6848\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_010("S010", "\u6807\u8bc6\u4e0d\u80fd\u91cd\u590d\uff01"),
    SCHEME_011("S011", "\u6807\u9898\u4e0d\u80fd\u91cd\u590d\uff01"),
    SCHEME_012("S012", "\u8bf7\u91cd\u65b0\u9009\u62e9\u8bf4\u660e\u6821\u9a8c\u89c4\u5219\uff01"),
    SCHEME_013("S013", "\u8bf4\u660e\u6821\u9a8c\u89c4\u5219\u5206\u7ec4\u5fc5\u987b\u4e3a\u5b50\u5206\u7ec4\uff01"),
    INIT_001("I001", "\u65e0\u6709\u6548\u4efb\u52a1\uff01"),
    INIT_002("I002", "\u65e0\u6709\u6548\u62a5\u8868\u65b9\u6848\uff01"),
    INIT_003("I003", "\u65e0\u7efc\u5408\u5ba1\u6838\u65b9\u6848\uff01"),
    INIT_004("I004", "\u7ed1\u5b9a\u4efb\u52a1\u88ab\u5220\u9664\uff01"),
    INIT_005("I005", "\u65b9\u6848\u6ca1\u6709\u914d\u7f6e\u5ba1\u6838\u9879\uff01"),
    RUN_NOFORMSCHEME("E001", "\u65e0\u6cd5\u5339\u914d\u62a5\u8868\u65b9\u6848\uff01"),
    RUN_NOITEM("E001", "\u65b9\u6848\u672a\u9009\u62e9\u5ba1\u6838\u9879\uff01"),
    RUN_UNIT("E005", "\u6b64\u65b9\u6848\u4e0b\u65e0\u7528\u6237\u53ef\u5ba1\u6838\u5355\u4f4d\uff01"),
    RUN_UNIT_FLOW("E005", "\u4efb\u52a1\u65f6\u671f\u4e0b\u65e0\u7528\u6237\u53ef\u5ba1\u6838\u5355\u4f4d\uff01"),
    RUN_NOSCHEME("E006", "\u4efb\u52a1\u65f6\u671f\u4e0b\u4e0d\u5b58\u5728\u7efc\u5408\u5ba1\u6838\u65b9\u6848\uff01"),
    RUN_SCHEMEORG("E006", "\u83b7\u53d6\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u7684\u5355\u4f4d\u8303\u56f4\u5f02\u5e38\uff01"),
    RUN_ORGNOSCHEME("E006", "\u5168\u90e8\u5355\u4f4d\u672a\u5339\u914d\u7efc\u5408\u5ba1\u6838\u65b9\u6848"),
    RUN_ORGNOSCHEME1("E006", "\u9009\u62e9\u5355\u4f4d\u672a\u5339\u914d\u5230\u7efc\u5408\u5ba1\u6838\u65b9\u6848"),
    RUN_ENTRYSINGLE("E006", "\u5f53\u524d\u5355\u4f4d\u672a\u7ed1\u5b9a\u7efc\u5408\u5ba1\u6838\u65b9\u6848\uff01"),
    RUN_ASYNC("E006", "\u5f02\u6b65\u6267\u884c\u5668\u5f02\u5e38\uff01"),
    RUN_ITEM("E006", "\u5ba1\u6838\u9879\u5f02\u5e38\uff01"),
    RUN_ORG_AUTO("E006", "\u81ea\u52a8\u6c47\u603b\u4efb\u52a1\u4e0b\u5355\u4f4d\u6811\u5f02\u5e38\uff01"),
    RUN_APP_FLOW_NODIM("E007", "\u65b9\u6848\u6ca1\u6709\u914d\u7f6e\u4e0a\u62a5\u524d\u60c5\u666f"),
    EXECUTE_003("E003", "\u5b58\u5728\u672a\u7ed1\u5b9a\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u7684\u5355\u4f4d"),
    EXECUTE_004("E004", "\u5f53\u524d\u5355\u4f4d\u672a\u7ed1\u5b9a\u7efc\u5408\u5ba1\u6838\u65b9\u6848"),
    EXECUTE_005("E005", "\u7ec4\u7ec7\u4e0a\u62a5\u8fd4\u56de\u7ed3\u679c\u5f02\u5e38");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private MultcheckSchemeError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

