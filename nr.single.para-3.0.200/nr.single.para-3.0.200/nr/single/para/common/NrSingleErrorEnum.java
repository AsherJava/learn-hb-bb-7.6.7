/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package nr.single.para.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrSingleErrorEnum implements ErrorEnum
{
    NRDESINGER_EXCEPTION_000("000", "JIO\u5bfc\u5165\u53c2\u6570\u6709\u8bef"),
    NRDESINGER_EXCEPTION_001("001", "JIO\u6587\u4ef6\u4e0a\u4f20\u51fa\u9519"),
    NRDESINGER_EXCEPTION_002("002", "JIO\u53c2\u6570\u5bfc\u5165\u51fa\u9519"),
    NRDESINGER_EXCEPTION_003("003", "\u60a8\u6ca1\u6709\u6743\u9650\u8fdb\u884cJIO\u5bfc\u5165\uff01"),
    NRDESINGER_EXCEPTION_004("004", "\u5339\u914d\u7f51\u62a5\u4efb\u52a1\u5931\u8d25"),
    NRDESINGER_EXCEPTION_005("005", "JIO\u6587\u4ef6\u5206\u6790\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_006("006", "\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u5bf9\u6bd4\u7ed3\u679c\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_007("007", "\u66f4\u65b0\u679a\u4e3e\u5b9a\u4e49\u5bf9\u6bd4\u7ed3\u679c\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_008("008", "\u66f4\u65b0\u679a\u4e3e\u9879\u5bf9\u6bd4\u7ed3\u679c\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_009("009", "\u66f4\u65b0\u62a5\u8868\u5bf9\u6bd4\u7ed3\u679c\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_010("010", "\u66f4\u65b0\u6307\u6807\u5bf9\u6bd4\u7ed3\u679c\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_011("011", "\u83b7\u53d6\u4efb\u52a1\u4e0b\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_012("012", "\u66f4\u65b0\u5173\u8054\u4efb\u52a1\u5bf9\u6bd4\u7ed3\u679c\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_013("013", "\u66f4\u65b0\u6253\u5370\u65b9\u6848\u5bf9\u6bd4\u7ed3\u679c\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_014("014", "\u6267\u884cJIO\u5bfc\u5165\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_015("015", "\u5355\u9879\u6bd4\u8f83\u5931\u8d25\uff01"),
    NRDESIGNER_EXCEPTION_016("016", "\u6570\u636e\u65b9\u6848\u76f8\u5173\u5c5e\u6027\u4e0d\u6b63\u786e"),
    NRDESINGER_EXCEPTION_017("017", "\u5220\u9664\u6bd4\u8f83\u4fe1\u606f\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_018("018", "\u6d6e\u52a8\u533a\u57df\u6307\u5b9a\u540e\u91cd\u65b0\u6bd4\u8f83\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_019("019", "\u6839\u636e\u4efb\u52a1\u548c\u5e74\u5ea6\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_020("020", "\u66f4\u65b0\u4efb\u52a1\u65f6\u671f\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_021("021", "\u56fa\u5b9a\u533a\u57df\u6307\u5b9a\u540e\u91cd\u65b0\u6bd4\u8f83\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_022("022", "\u83b7\u53d6\u53c2\u6570\u6bd4\u8f83\u4fe1\u606f\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_023("022", "\u83b7\u53d6\u5bfc\u5165\u9009\u9879\u5931\u8d25\uff01"),
    NRDESINGER_EXCEPTION_024("022", "\u66f4\u65b0\u5bfc\u5165\u9009\u9879\u5931\u8d25\uff01");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private NrSingleErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

