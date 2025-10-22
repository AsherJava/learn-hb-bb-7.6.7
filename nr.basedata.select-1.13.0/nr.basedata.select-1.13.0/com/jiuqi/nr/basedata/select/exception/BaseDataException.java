/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.basedata.select.exception;

import com.jiuqi.nr.common.exception.NrCommonException;

public class BaseDataException
extends NrCommonException {
    protected String errorCode;
    private String[] datas;
    private static final String DEFAULT_ERROR_CODE = "BASEDATAERROR";

    public BaseDataException(String errorCode) {
        super(errorCode);
    }

    public BaseDataException(String[] datas) {
        super(DEFAULT_ERROR_CODE, datas);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String[] getDatas() {
        return this.datas;
    }

    public String getErrorMes() {
        if (this.datas.length >= 0) {
            return this.datas[0];
        }
        return this.datas.toString();
    }

    public void setDatas(String[] datas) {
        this.datas = datas;
    }
}

