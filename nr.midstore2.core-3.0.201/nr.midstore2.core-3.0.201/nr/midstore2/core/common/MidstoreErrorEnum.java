/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package nr.midstore2.core.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum MidstoreErrorEnum implements ErrorEnum
{
    MIDSTORE_EXCEPTION_000("000", "\u4e2d\u95f4\u5e93\u4e0b\u8f7d\u6587\u4ef6\u6709\u8bef");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private MidstoreErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

