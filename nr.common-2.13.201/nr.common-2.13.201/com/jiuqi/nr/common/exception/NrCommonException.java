/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

public abstract class NrCommonException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected String errorCode;
    private String[] datas;

    public NrCommonException(String errorCode) {
        this.setErrorCode(errorCode);
    }

    public NrCommonException(String errorCode, String[] datas) {
        this.setErrorCode(errorCode);
        this.setDatas(datas);
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

    @Override
    public String toString() {
        String s = this.getClass().getName();
        StringBuilder message = new StringBuilder();
        if (this.getDatas() != null) {
            for (String error : this.getDatas()) {
                message.append(error).append("\n");
            }
        }
        return message.length() > 0 ? s + ":\n" + message.toString() : s;
    }
}

