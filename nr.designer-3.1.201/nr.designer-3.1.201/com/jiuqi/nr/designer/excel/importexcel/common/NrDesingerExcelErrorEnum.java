/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrDesingerExcelErrorEnum implements ErrorEnum
{
    NRDESINGER_EXCEPTION_001("001", "\u5f53\u524d\u7248\u672c\u7684Excel\u65e0\u6cd5\u89e3\u6790\uff01"),
    NRDESINGER_EXCEPTION_002("002", "\u5b58\u50a8\u8868\u7684BizKey\u8bbe\u7f6e\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_003("003", "\u6307\u6807\u5173\u8054\u679a\u4e3e\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_004("004", "\u62a5\u8868\u53c2\u6570\u5165\u5e93\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_005("005", "\u8868\u6837\u6784\u9020\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_006("006", "\u8868\u6837\u5408\u5e76\u5355\u5143\u683c\u6784\u9020\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_007("007", "\u521d\u59cb\u5316\u533a\u57df\u9519\u8bef\uff08\u6d6e\u52a8\u533a\u57df\uff09\uff01"),
    NRDESINGER_EXCEPTION_008("008", "\u521d\u59cb\u5316\u94fe\u63a5\uff0c\u5b58\u50a8\u8868\uff0c\u6307\u6807\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_009("009", "\u6570\u636e\u94fe\u63a5\u91cd\u6392\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_010("010", "\u5bfc\u5165\u679a\u4e3e\u6570\u636e\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_011("011", "\u521d\u59cb\u5316\u7b80\u5355\u679a\u4e3e\u6570\u636e\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_012("012", "\u53d1\u5e03\u6216\u8005\u63d2\u5165\u679a\u4e3e\u6570\u636e\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_013("013", "\u9884\u6d4b\u6307\u6807\u7c7b\u578b\u53d1\u751f\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_014("014", "\u672a\u914d\u7f6eAI\u9884\u6d4b\u5730\u5740\uff01"),
    NRDESINGER_EXCEPTION_015("015", "\u6307\u6807\u5173\u8054\u4e0b\u62c9\u9879\u8868\u8fbe\u5f0f\u4e66\u5199\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_016("016", "\u6307\u6807\u5173\u8054\u5916\u6302\u679a\u4e3ecode\uff0ctitle\u5217\u683c\u5f0f\u9519\u8bef\uff01"),
    NRDESINGER_EXCEPTION_017("017", "\u60a8\u6ca1\u6709\u6743\u9650\u8fdb\u884cEXCEL\u5bfc\u5165\uff01"),
    NRDESINGER_EXCEPTION_018("018", "\u8868\u95f4\u516c\u5f0f\u5bfc\u5165\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_019("019", "\u516c\u5f0f\u7f16\u8bd1\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_020("020", "\u62a5\u8868\u6807\u8bc6\u53ef\u4ee5\u7531\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u4e0b\u5212\u7ebf\u7ec4\u6210\uff01(\u5b57\u6bcd\u4e3a\u5927\u5199\u4e14\u7b2c\u4e00\u4f4d\u4e3a\u5927\u5199\u5b57\u6bcd)\uff01"),
    NRDESINGER_EXCEPTION_100("100", "\u62a5\u8868\u89e3\u6790\u6709\u8bef\uff0c\u8bf7\u6838\u5bf9\u540e\u91cd\u65b0\u5bfc\u5165");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private NrDesingerExcelErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

