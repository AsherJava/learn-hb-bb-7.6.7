/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.summary.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SummaryErrorEnum implements ErrorEnum
{
    SSG_TITLE_NOTREPEAT_CODE("SSG_1001", "\u540c\u4e00\u5206\u7ec4\u4e0b\u4e0d\u80fd\u51fa\u73b0\u91cd\u590d\u7684\u6807\u9898\u3010%s\u3011\uff01"),
    SSG_ADD_DBEXCEPTION("SSG_1002", "\u65b0\u589e\u6c47\u603b\u65b9\u6848\u5206\u7ec4\u5931\u8d25"),
    SSG_ADD_NULLTITLE("SSG_1003", "\u65b0\u589e\u6c47\u603b\u65b9\u6848\u5206\u7ec4\u5931\u8d25\uff1a\u5206\u7ec4\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a"),
    SSG_DEL_HASSOLUTION("SSG_1003", "\u5206\u7ec4\u4e0b\u6709\u6c47\u603b\u65b9\u6848\uff0c\u4e0d\u80fd\u5220\u9664\uff0c\u8bf7\u5148\u5220\u9664\u6c47\u603b\u65b9\u6848"),
    SSG_DEL_DBEXCEPTION("SSG_1004", "\u5220\u9664\u6c47\u603b\u65b9\u6848\u5206\u7ec4\u5931\u8d25"),
    SSG_UPDATE_DBEXCEPTION("SSG_1005", "\u4fee\u6539\u6c47\u603b\u65b9\u6848\u5206\u7ec4\u5931\u8d25"),
    SSD_TITLE_NOTREPEAT_CODE("100", "\u540c\u4e00\u5206\u7ec4\u4e0b\u4e0d\u80fd\u51fa\u73b0\u91cd\u590d\u7684\u6807\u9898\u3010%s\u3011\uff01"),
    SSD_NAME_ALREADY_EXISTS("101", "\u6807\u8bc6\u3010%s\u3011\u5df2\u5b58\u5728\uff01"),
    SS_ADD_DBEXCEPTION("SS_1001", "\u65b0\u589e\u6c47\u603b\u65b9\u6848\u5931\u8d25"),
    SS_UPDATE_DBEXCEPTION("SS_1002", "\u4fee\u6539\u6c47\u603b\u65b9\u6848\u5931\u8d25"),
    SS_DEL_DBEXCEPTION("SS_1003", "\u5220\u9664\u6c47\u603b\u65b9\u6848\u5931\u8d25"),
    SS_ADD_NULLNAME("SS_1004", "\u65b0\u589e\u6c47\u603b\u65b9\u6848\u5931\u8d25\uff1a\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a"),
    SS_ADD_NULLTITLE("SS_1005", "\u65b0\u589e\u6c47\u603b\u65b9\u6848\u5931\u8d25\uff1a\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a"),
    SS_NAME_REPEAT("SS_1006", "\u6c47\u603b\u65b9\u6848\u6807\u8bc6\u91cd\u590d\u3010%s\u3011"),
    SS_TITLE_REPEAT("SS_1007", "\u540c\u4e00\u5206\u7ec4\u4e0b\u6c47\u603b\u65b9\u6848\u6807\u9898\u91cd\u590d\u3010%s\u3011"),
    SR_TITLE_NOTREPEAT_CODE("SR_1001", "\u540c\u4e00\u65b9\u6848\u4e0b\u4e0d\u80fd\u51fa\u73b0\u91cd\u590d\u7684\u6807\u9898\u3010%s\u3011"),
    SR_NAME_ALREADY_EXISTS("SR_1002", "\u6807\u8bc6\u3010%s\u3011\u5df2\u5b58\u5728\uff01"),
    SR_ADD_DBEXCEPTION("SR_1003", "\u65b0\u589e\u6c47\u603b\u8868\u5931\u8d25"),
    SR_RUNTIME_ADD_DBEXCEPTION("SR_1004", "\u65b0\u589e\u8fd0\u884c\u671f\u6c47\u603b\u8868\u3010%s\u3011\u5931\u8d25\u3010%s\u3011"),
    SR_UPDATE_DBEXCEPTION("SR_1005", "\u4fee\u6539\u6c47\u603b\u8868\u5931\u8d25\u3010%s\u3011"),
    SR_RUNTIME_UPDATE_DBEXCEPTION("SR_1006", "\u4fee\u6539\u8fd0\u884c\u671f\u6c47\u603b\u8868\u5931\u8d25\u3010%s\u3011"),
    SR_DES_DEL_DBEXCEPTION("SR_1007", "\u5220\u9664\u6c47\u603b\u8868\u5931\u8d25\u3010%s\u3011"),
    SR_RUNTIME_DEL_DBEXCEPTION("SR_1008", "\u5220\u9664\u8fd0\u884c\u671f\u6c47\u603b\u8868\u5931\u8d25\u3010%s\u3011"),
    SR_NOTEXIST("SR_1009", "\u6c47\u603b\u8868\u4e0d\u5b58\u5728"),
    SDC_DES_DEL_DBEXCEPTION("SDC_1001", "\u5220\u9664\u8bbe\u8ba1\u671f\u6570\u636e\u5355\u5143\u683c\u5931\u8d25\u3010%s\u3011"),
    SDC_RUNTIME_DEL_DBEXCEPTION("SDC_1002", "\u5220\u9664\u8fd0\u884c\u671f\u6570\u636e\u5355\u5143\u683c\u5931\u8d25\u3010%s\u3011"),
    DEPLOY_FAIL("DEPLOY_1001", "\u53d1\u5e03\u5931\u8d25\uff1a\u3010%s\u3011"),
    OBJMMAPER_WRITE_EXCEPTION("OBJMAPPER_1001", "objectmapper\u5e8f\u5217\u5316\u9519\u8bef\uff1a\u3010%s\u3011"),
    OBJMMAPER_READ_EXCEPTION("OBJMAPPER_1002", "objectmapper\u53cd\u5e8f\u5217\u5316\u9519\u8bef\uff1a\u3010%s\u3011"),
    TREE_LOAD_FAILED("TREE_1001", "\u6811\u5f62\u52a0\u8f7d\u5931\u8d25"),
    ENTITY_QUERY_FAILED("ENTITY_1001", "\u5b9e\u4f53\u3010%s\u3011\u67e5\u8be2\u5931\u8d25\uff1a\u3010%s\u3011"),
    DATA_QUERY_FAILED("DQ_1001", "\u7269\u7406\u8868\u3010%s\u3011\u6570\u636e\u67e5\u8be2\u5931\u8d25\uff1a\u3010%s\u3011"),
    PARAM_GETTASK_FAILED("PARAM_1001", "\u4efb\u52a1\u3010%s\u3011\u83b7\u53d6\u5931\u8d25\uff1a\u3010%s\u3011"),
    PREVIEW_INIT_FAILED("PREVIEW_1001", "\u521d\u59cb\u5316\u53c2\u6570\u83b7\u53d6\u5931\u8d25\uff1a\u3010%s\u3011"),
    XFFORM_LOAD_FAILED("XFFORM_1001", "\u53c2\u6570\u63a7\u4ef6\u52a0\u8f7d\u5931\u8d25"),
    SUMPARAM_SOLU_NULL("SP_1001", "\u83b7\u53d6\u6c47\u603b\u65b9\u6848\u5931\u8d25"),
    SUMPARAM_GETTASK_FAIL("SP_1002", "\u83b7\u53d6\u4efb\u52a1\u5931\u8d25"),
    SUMPARAM_PERIOD_NULL("SP_1003", "\u65f6\u671f\u53c2\u6570\u4e3a\u7a7a"),
    SUMPARAM_SCHEME_NULL("SP_1004", "\u83b7\u53d6\u6570\u636e\u65b9\u6848\u5931\u8d25"),
    SUMPARAM_RELASCHEME_NULL("SP_1005", "\u83b7\u53d6\u5173\u8054\u6570\u636e\u65b9\u6848\u5931\u8d25"),
    QUERY_RESULT_EXPORT_FAIL("QRE_1001", "\u5bfc\u51fa\u5931\u8d25"),
    QUERY_RESULT_FAIL("QR_1001", "\u67e5\u8be2\u5931\u8d25"),
    PERIOD_PARSE_FAIL("PP_1001", "\u65f6\u671f\u7248\u672c\u8f6c\u6362\u5f02\u5e38"),
    NO_READ_AUTH_REPORT("NRAR_1001", "\u6ca1\u6709\u8be5\u62a5\u8868\u7684\u67e5\u770b\u6743\u9650"),
    NO_SUM_AUTH_REPORT("NRAR_1002", "\u6ca1\u6709\u8be5\u62a5\u8868\u7684\u6c47\u603b\u6743\u9650"),
    NO_MANAGE_AUTH_REPORT("NRAR_1003", "\u6ca1\u6709\u8be5\u62a5\u8868\u7684\u7ba1\u7406\u6743\u9650"),
    NO_READ_AUTH_SOLUTION("NRAS_1001", "\u6ca1\u6709\u8be5\u6c47\u603b\u65b9\u6848\u7684\u67e5\u770b\u6743\u9650"),
    NO_SUM_AUTH_SOLUTION("NRAS_1002", "\u6ca1\u6709\u8be5\u6c47\u603b\u65b9\u6848\u7684\u6c47\u603b\u6743\u9650"),
    NO_MANAGE_AUTH_SOLUTION("NRAS_1003", "\u6ca1\u6709\u8be5\u6c47\u603b\u65b9\u6848\u7684\u7ba1\u7406\u6743\u9650"),
    NO_READ_AUTH_GROUP("NRAG_1001", "\u6ca1\u6709\u8be5\u5206\u7ec4\u7684\u67e5\u770b\u6743\u9650"),
    NO_SUM_AUTH_GROUP("NRAG_1002", "\u6ca1\u6709\u8be5\u5206\u7ec4\u7684\u6c47\u603b\u6743\u9650"),
    NO_MANAGE_AUTH_GROUP("NRAG_1003", "\u6ca1\u6709\u8be5\u5206\u7ec4\u7684\u7ba1\u7406\u6743\u9650");

    private final String code;
    private final String message;

    private SummaryErrorEnum(String code, String message) {
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

