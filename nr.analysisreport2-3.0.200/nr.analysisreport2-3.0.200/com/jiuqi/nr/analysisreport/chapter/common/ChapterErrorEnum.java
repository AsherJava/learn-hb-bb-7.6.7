/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.analysisreport.chapter.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ChapterErrorEnum implements ErrorEnum
{
    NRANALYSISECHAPTERRRORENUM_1101("1101", "\u521b\u5efa\u7ae0\u8282\u5f02\u5e38"),
    NRANALYSISECHAPTERRRORENUM_1102("1102", "\u66f4\u65b0\u7ae0\u8282\u5f02\u5e38"),
    NRANALYSISECHAPTERRRORENUM_1103("1103", "\u5220\u9664\u7ae0\u8282\u5f02\u5e38"),
    NRANALYSISECHAPTERRRORENUM_1104("1104", "\u4fee\u6539\u7ae0\u8282\u6392\u5e8f\u5f02\u5e38");

    private String code;
    private String message;

    private ChapterErrorEnum(String code, String message) {
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

