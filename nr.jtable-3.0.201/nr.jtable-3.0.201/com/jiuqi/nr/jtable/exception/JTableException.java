/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.common.exception.NrCommonException;

public abstract class JTableException
extends NrCommonException {
    private static final long serialVersionUID = 1L;
    protected String errorCode;
    private String[] datas;

    public JTableException(String errorCode) {
        super(errorCode);
    }

    public JTableException(String errorCode, String[] datas) {
        super(errorCode, datas);
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

    public void setDatas(String[] datas) {
        this.datas = datas;
    }
}

