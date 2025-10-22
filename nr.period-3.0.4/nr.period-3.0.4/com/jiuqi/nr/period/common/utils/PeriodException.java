/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.period.common.utils;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum PeriodException implements ErrorEnum
{
    PERIOD_EXCEPTION_100("100", "\u65f6\u671f\u5f00\u59cb\u6216\u7ed3\u675f\u65f6\u95f4\u4e3a\u7a7a"),
    PERIOD_EXCEPTION_101("101", "\u65f6\u671f\u67e5\u8be2\u4e3b\u952e\u4e3a\u7a7a"),
    PERIOD_EXCEPTION_102("102", "\u4e0d\u5141\u8bb8\u6dfb\u52a0\u975e\u81ea\u5b9a\u4e49\u65f6\u671f"),
    PERIOD_EXCEPTION_103("103", "\u53ea\u5141\u8bb8\u6269\u5c55\u9ed8\u8ba4\u65f6\u671f"),
    PERIOD_EXCEPTION_104("104", "\u9ed8\u8ba4\u65f6\u671f\u6570\u636e\u5b58\u5728\u95ee\u9898"),
    PERIOD_EXCEPTION_105("105", "\u65f6\u671f\u67e5\u8be2\u5931\u8d25"),
    PERIOD_EXCEPTION_106("106", "\u65f6\u671f\u6570\u636e\u67e5\u8be2\u5931\u8d25"),
    PERIOD_EXCEPTION_107("107", "\u6269\u5c55\u9ed8\u8ba4\u65f6\u671f\u5931\u8d25"),
    PERIOD_EXCEPTION_108("108", "\u65f6\u671f\u66f4\u65b0\u5931\u8d25"),
    PERIOD_EXCEPTION_109("109", "\u65f6\u671f\u5220\u9664\u5931\u8d25"),
    PERIOD_EXCEPTION_110("110", "\u5f00\u59cb\u65f6\u95f4\u4e0e\u5df2\u6709\u6570\u636e\u5b58\u5728\u91cd\u53e0"),
    PERIOD_EXCEPTION_111("111", "\u65f6\u671f\u7f16\u7801\u91cd\u590d"),
    PERIOD_EXCEPTION_112("112", "\u5220\u9664\u5931\u8d25"),
    PERIOD_EXCEPTION_113("113", "\u7ed3\u675f\u65f6\u95f4\u4e0e\u5df2\u6709\u6570\u636e\u5b58\u5728\u91cd\u53e0"),
    PERIOD_EXCEPTION_114("114", "\u81ea\u52a8\u751f\u6210\u7f16\u7801\u5931\u8d25"),
    PERIOD_EXCEPTION_115("115", "\u81ea\u5b9a\u4e49\u65f6\u671f\u6570\u636e\u7f16\u7801\u6821\u9a8c\u5931\u8d25"),
    PERIOD_EXCEPTION_116("116", "\u6821\u9a8c\u5f00\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25"),
    PERIOD_EXCEPTION_117("117", "\u65b0\u589e\u65f6\u671f\u6570\u636e\u91cd\u590d"),
    PERIOD_EXCEPTION_118("118", "\u8f6c\u6362\u5931\u8d25"),
    PERIOD_EXCEPTION_119("119", "\u504f\u79fb\u8d85\u51fa\u8303\u56f4"),
    PERIOD_EXCEPTION_120("120", "\u65f6\u671f\u5bfc\u51fa\u5f02\u5e38"),
    PERIOD_EXCEPTION_121("121", "\u65f6\u671f\u6570\u636e\u5bfc\u51fa\u5f02\u5e38"),
    PERIOD_EXCEPTION_122("122", "\u65f6\u671f\u5bfc\u5165\u5f02\u5e38"),
    PERIOD_EXCEPTION_123("123", "\u65f6\u671f\u6570\u636e\u5bfc\u5165\u5f02\u5e38"),
    PERIOD_EXCEPTION_124("124", "\u4e0d\u5b9a\u671f\u7f16\u7801\u7981\u6b62\u4f7f\u7528\u9ed8\u8ba4\u65f6\u671f\u7c7b\u578b\u5b57\u7b26"),
    PERIOD_EXCEPTION_125("125", "\u65f6\u671f\u5bfc\u51fa\u53c2\u6570\u9519\u8bef"),
    PERIOD_EXCEPTION_126("126", "\u9ed8\u8ba4\u65f6\u671f\u4e0d\u652f\u6301\u5bfc\u51fa"),
    PERIOD_EXCEPTION_127("127", "\u5bfc\u5165\u6587\u4ef6\u4e0d\u5b58\u5728"),
    PERIOD_EXCEPTION_128("128", "\u5bfc\u5165\u6587\u4ef6\u7c7b\u578b\u4e0d\u6b63\u786e"),
    PERIOD_EXCEPTION_129("129", "\u5bfc\u5165\u9875\u7b7e\u4e0d\u5b58\u5728"),
    PERIOD_EXCEPTION_130("130", "\u5bfc\u5165\u65f6\u671f\u4e0d\u662f\u5f53\u524d\u65f6\u671f"),
    PERIOD_EXCEPTION_131("131", "\u5bfc\u5165\u6587\u4ef6\u6807\u9898\u4e0d\u6b63\u786e"),
    PERIOD_EXCEPTION_132("132", "\u65f6\u671f\u540d\u79f0\u91cd\u590d"),
    PERIOD_EXCEPTION_133("133", "\u65f6\u671f\u540d\u79f0\u4e0e\u9ed8\u8ba4\u8bed\u8a00\u540d\u79f0\u91cd\u590d"),
    PERIOD_EXCEPTION_134("134", "13\u671f\u6570\u636e\u521b\u5efa\u5931\u8d25"),
    PERIOD_EXCEPTION_135("135", "\u5f00\u59cb\u5e74\u4efd\u5fc5\u987b\u5c0f\u4e8e\u7ed3\u675f\u5e74\u4efd"),
    PERIOD_EXCEPTION_136("136", "\u68c0\u67e5\u5f00\u542f13\u671f\u7684\u60c5\u51b5\u4e0b\uff0c\u671f\u6570\u8bbe\u7f6e\u5fc5\u987b\u5927\u4e8e\u7b49\u4e8e13"),
    PERIOD_EXCEPTION_137("137", "\u65f6\u671f\u7b80\u79f0\u6279\u91cf\u4fee\u6539\u5931\u8d25");

    private String code;
    private String message;

    private PeriodException(String value, String text) {
        this.code = value;
        this.message = text;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

