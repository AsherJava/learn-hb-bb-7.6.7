/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package nr.midstore2.core.definition.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrMidstoreErrorEnum implements ErrorEnum
{
    NRDESINGER_EXCEPTION_000("000", "\u4ea4\u6362\u65b9\u6848\u53d1\u5e03\u9519\u8bef"),
    NRDESINGER_EXCEPTION_001("001", "\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u5206\u7ec4\u5931\u8d25"),
    NRDESINGER_EXCEPTION_002("002", "\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u57fa\u672c\u4fe1\u606f\u5931\u8d25"),
    NRDESINGER_EXCEPTION_003("003", "\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u6269\u5c55\u4fe1\u606f\u5931\u8d25"),
    NRDESINGER_EXCEPTION_004("004", "\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u7ec4\u7ec7\u673a\u6784\u4fe1\u606f\u5931\u8d25"),
    NRDESINGER_EXCEPTION_005("005", "\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u5931\u8d25"),
    NRDESINGER_EXCEPTION_006("006", "\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u57fa\u7840\u6570\u636e\u5931\u8d25"),
    NRDESINGER_EXCEPTION_007("007", "\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u5931\u8d25"),
    NRDESINGER_EXCEPTION_008("008", "\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u6307\u6807\u4fe1\u606f\u5931\u8d25"),
    NRDESINGER_EXCEPTION_009("009", "\u6dfb\u52a0\u5173\u8054\u57fa\u7840\u6570\u636e\u5931\u8d25"),
    NRDESINGER_EXCEPTION_010("010", "\u68c0\u67e5\u53c2\u6570\u5931\u8d25\u5931\u8d25"),
    NRDESINGER_EXCEPTION_011("011", "\u62c9\u53d6\u6570\u636e\u5931\u8d25"),
    NRDESINGER_EXCEPTION_012("012", "\u63a8\u9001\u6570\u636e\u5931\u8d25"),
    NRDESINGER_EXCEPTION_013("013", "\u83b7\u53d6\u8ba1\u5212\u4efb\u52a1\u5931\u8d25");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private NrMidstoreErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

