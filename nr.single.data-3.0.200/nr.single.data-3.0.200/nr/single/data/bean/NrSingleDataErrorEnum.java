/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package nr.single.data.bean;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrSingleDataErrorEnum implements ErrorEnum
{
    NRSINGDATAER_EXCEPTION_000("000", "\u68c0\u67e5\u53c2\u6570\u6709\u8bef"),
    NRSINGDATAER_EXCEPTION_001("001", "\u68c0\u67e5\u8fc7\u7a0b\u51fa\u9519"),
    NRSINGDATAER_EXCEPTION_002("002", "\u590d\u5236\u53c2\u6570\u51fa\u9519"),
    NRSINGDATAER_EXCEPTION_003("003", "\u590d\u5236\u8fc7\u7a0b\u51fa\u9519");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private NrSingleDataErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

