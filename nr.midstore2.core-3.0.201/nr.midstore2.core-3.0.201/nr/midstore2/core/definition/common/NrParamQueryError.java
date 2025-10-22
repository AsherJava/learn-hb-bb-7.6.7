/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package nr.midstore2.core.definition.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrParamQueryError implements ErrorEnum
{
    ENTITY_QUERY("Q01", "\u65e0\u6cd5\u67e5\u8be2\u5b9e\u4f53\u5217\u8868");

    private String code;
    private String message;

    private NrParamQueryError(String code, String message) {
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

