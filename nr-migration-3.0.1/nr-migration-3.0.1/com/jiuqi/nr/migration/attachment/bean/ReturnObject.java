/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.attachment.bean;

public class ReturnObject {
    public int status;
    public String errorDesc;
    private Object data;

    public ReturnObject(int status) {
        this.status = status;
    }

    public ReturnObject(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ReturnObject(int status, String errorDesc) {
        this.status = status;
        this.errorDesc = errorDesc;
    }

    public static ReturnObject Success() {
        return new ReturnObject(Status.SUCCESS.getValue());
    }

    public static ReturnObject Success(Object data) {
        return new ReturnObject(Status.SUCCESS.getValue(), data);
    }

    public static ReturnObject Error(String errorDesc) {
        return new ReturnObject(Status.ERROR.getValue(), errorDesc);
    }

    public boolean isSuccess() {
        return this.status == Status.SUCCESS.getValue();
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public Object getData() {
        return this.data;
    }

    private static enum Status {
        SUCCESS(2),
        ERROR(3);

        public int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

