/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.datascheme.common;

import com.jiuqi.np.common.exception.ErrorEnum;
import java.util.Arrays;

public enum DataSchemeEnum implements ErrorEnum
{
    DATA_SCHEME("DS", "\u5931\u8d25"),
    DATA_SCHEME_DS_1("DS_01", "\u65b0\u5efa\u6570\u636e\u65b9\u6848\u5931\u8d25\uff01"),
    DATA_SCHEME_DS_1_1("DS_01_1", "\u6570\u636e\u65b9\u6848\u6807\u8bc6%s\u91cd\u590d"),
    DATA_SCHEME_DS_1_2("DS_01_2", "\u6570\u636e\u65b9\u6848\u524d\u7f00%s\u91cd\u590d"),
    DATA_SCHEME_DS_1_3("DS_01_3", "\u6570\u636e\u65b9\u6848\u540d\u79f0%s\u91cd\u590d"),
    DATA_SCHEME_DS_1_4("DS_01_4", "\u6570\u636e\u65b9\u6848\u524d\u7f00\u4e0d\u5141\u8bb8\u4fee\u6539"),
    DATA_SCHEME_DS_1_5("DS_01_5", "\u6570\u636e\u65b9\u6848\u53d1\u5e03\u4e2d\u4e0d\u5141\u8bb8\u4fee\u6539"),
    DATA_SCHEME_DS_1_6("DS_01_6", "\u6570\u636e\u65b9\u6848\u6807\u8bc6\u4e0d\u5141\u8bb8\u4fee\u6539"),
    DATA_SCHEME_DS_1_7("DS_01_7", "\u4e0b\u53d1\u53c2\u6570\u4e0d\u5141\u8bb8\u4fee\u6539"),
    DATA_SCHEME_DS_1_9("DS_01_9", "\u6570\u636e\u65b9\u6848\u7c7b\u578b\u4e0d\u5141\u8bb8\u4fee\u6539\uff01"),
    DATA_SCHEME_DS_1_10("DS_01_10", "\u6570\u636e\u65b9\u6848\u4e1a\u52a1\u6807\u8bc6%s\u91cd\u590d\uff01"),
    DATA_SCHEME_DS_1_11("DS_01_11", "\u6570\u636e\u65b9\u6848\u5df2\u53d1\u5e03,\u52a0\u5bc6\u573a\u666f\u4e0d\u5141\u8bb8\u4fee\u6539\uff01"),
    DATA_SCHEME_DS_2("DS_02", "\u5220\u9664\u6570\u636e\u65b9\u6848\u5931\u8d25\uff01"),
    DATA_SCHEME_DS_3("DS_03", "\u66f4\u65b0\u6570\u636e\u65b9\u6848\u5931\u8d25\uff01"),
    DATA_SCHEME_DS_4("DS_04", "\u67e5\u8be2\u6570\u636e\u65b9\u6848\u5931\u8d25\uff01"),
    DATA_SCHEME_DS_5("DS_05", "\u590d\u5236\u6570\u636e\u65b9\u6848\u5931\u8d25\uff01"),
    DATA_SCHEME_DG_1("DG_01", "\u65b0\u5efa\u5206\u7ec4\u5931\u8d25\uff01"),
    DATA_SCHEME_DG_1_1("DG_01_1", "\u5206\u7ec4\u540d\u79f0%s\u91cd\u590d\uff01"),
    DATA_SCHEME_DG_2("DG_02", "\u5220\u9664\u5206\u7ec4\u5931\u8d25\uff01"),
    DATA_SCHEME_DG_2_1("DG_02_1", "\u5206\u7ec4\u4e0b\u6709\u65b9\u6848,\u4e0d\u5141\u8bb8\u5220\u9664\uff01"),
    DATA_SCHEME_DG_3("DG_03", "\u66f4\u65b0\u5206\u7ec4\u5931\u8d25\uff01"),
    DATA_SCHEME_DG_4("DG_04", "\u67e5\u8be2\u5206\u7ec4\u5931\u8d25\uff01"),
    DATA_SCHEME_DT_1("DG_01", "\u65b0\u5efa\u6570\u636e\u8868\u5931\u8d25\uff01"),
    DATA_SCHEME_DT_1_1("DT_01_1", "\u6570\u636e\u8868\u6807\u8bc6%s\u91cd\u590d\uff01"),
    DATA_SCHEME_DT_1_2("DT_01_2", "\u6570\u636e\u8868\u540d\u79f0%s\u91cd\u590d\uff01"),
    DATA_SCHEME_DT_1_3("DT_01_3", "\u4e00\u4e2a\u6570\u636e\u65b9\u6848\u6700\u591a\u6709\u4e00\u4e2a\u5355\u4f4d\u4fe1\u606f\u8868\uff01"),
    DATA_SCHEME_DT_2("DT_02", "\u5220\u9664\u6570\u636e\u8868\u5931\u8d25\uff01"),
    DATA_SCHEME_DT_3("DT_03", "\u66f4\u65b0\u6570\u636e\u8868\u5931\u8d25\uff01"),
    DATA_SCHEME_DT_4("DT_04", "\u67e5\u8be2\u6570\u636e\u8868\u5931\u8d25\uff01"),
    DATA_SCHEME_DF_1("DF_01", "\u65b0\u5efa\u6307\u6807\u5931\u8d25\uff01"),
    DATA_SCHEME_DF_1_1("DF_01_1", "\u65b0\u5efa\u7ef4\u5ea6\u6307\u6807\u5931\u8d25\uff01"),
    DATA_SCHEME_DF_1_2("DF_01_2", "\u6307\u6807\u6807\u8bc6%s\u91cd\u590d\uff01"),
    DATA_SCHEME_DF_1_3("DF_01_3", "\u5b57\u6bb5\u6807\u8bc6%s\u91cd\u590d\uff01"),
    DATA_SCHEME_DF_2("DF_02", "\u5220\u9664\u6307\u6807\u5931\u8d25\uff01"),
    DATA_SCHEME_DF_3("DF_03", "\u66f4\u65b0\u6307\u6807\u5931\u8d25\uff01"),
    DATA_SCHEME_DF_4("DF_04", "\u67e5\u8be2\u6307\u6807\u5931\u8d25\uff01"),
    DATA_SCHEME_DM_1("DF_01", "\u65b0\u5efa\u7ef4\u5ea6\u5931\u8d25\uff01"),
    DATA_SCHEME_DM_2("DF_02", "\u5220\u9664\u7ef4\u5ea6\u5931\u8d25\uff01"),
    DATA_SCHEME_DM_3("DF_03", "\u66f4\u65b0\u7ef4\u5ea6\u5931\u8d25\uff01"),
    DATA_SCHEME_DM_4("DF_04", "\u67e5\u8be2\u7ef4\u5ea6\u5931\u8d25\uff01"),
    DATA_SCHEME_D_1("D_01", "\u6709\u6570\u636e\u4e0d\u5141\u8bb8\u5220\u9664\uff01"),
    DATA_SCHEME_I18N_1("I18N_1", "\u5bfc\u5165\u6570\u636e\u65b9\u6848\u591a\u8bed\u8a00\u53c2\u6570\u5931\u8d25\uff01"),
    DATA_SCHEME_DEPLOY("DEPLOY", "\u53d1\u5e03\u6570\u636e\u65b9\u6848\u5f02\u5e38!"),
    DATA_SCHEME_DEPLOY_1("DEPLOY_1", "\u6307\u6807\u6216\u5b57\u6bb5\u7684\u5173\u8054\u679a\u4e3e\u4fe1\u606f\u5f02\u5e38!"),
    DATA_SCHEME_DEPLOYINFO_1("DEPLOYINFO_1", "\u6307\u6807\u6216\u5b57\u6bb5\u7684\u53d1\u5e03\u4fe1\u606f\u5f02\u5e38!"),
    DATA_SCHEME_CHECKDATA_1("CHECKDATA_1", "\u68c0\u67e5\u6570\u636e\u8868\u662f\u5426\u5df2\u7ecf\u5f55\u5165\u6570\u636e\u5931\u8d25: "),
    ADJUSTMENT_PERIOD_1("ADJUSTMENT_PERIOD_1", "\u4fdd\u5b58\u8c03\u6574\u671f\u6570\u636e\u5931\u8d25!"),
    ADJUSTMENT_PERIOD_2("ADJUSTMENT_PERIOD_2", "\u5f00\u542f\u8c03\u6574\u671f\u5931\u8d25!"),
    DATA_SCHEME_ADD_DIMENSION("DATA_SCHEME_ADD_DIMENSION", "\u6dfb\u52a0\u8868\u5185\u7ef4\u5ea6\u5931\u8d25!"),
    DATA_SCHEME_ADD_DIMENSION1("DATA_SCHEME_ADD_DIMENSION1", "\u6dfb\u52a0\u8868\u5185\u7ef4\u5ea6\u5b57\u6bb5\u6709\u7a7a\u503c\uff0c\u6dfb\u52a0\u5931\u8d25"),
    DATA_SCHEME_ERROR01("DATA_SCHEME_ERROR01", "\u6e05\u9664\u7a7a\u76ee\u5f55\u5931\u8d25! "),
    DATA_SCHEME_ERROR02("DATA_SCHEME_ERROR02", "\u65e0DDL\u6a21\u5f0f,\u7981\u6b62\u5220\u9664!"),
    UNAUTHORIZED("UNAUTHORIZED", "\u65e0\u6743\u64cd\u4f5c!");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message.replace("%s", "");
    }

    public String getMessage(Object ... args) {
        return String.format(this.message, Arrays.stream(args).map(x -> "[" + x + "]").toArray(Object[]::new));
    }

    private DataSchemeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

