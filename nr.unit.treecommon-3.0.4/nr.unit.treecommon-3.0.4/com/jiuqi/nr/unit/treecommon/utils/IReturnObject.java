/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treecommon.utils;

public class IReturnObject<E> {
    private Integer code;
    private String message;
    private E data;
    private boolean success;
    public static final int CODE_500 = 500;
    public static final int CODE_200 = 200;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return this.data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static <E> IReturnObject<E> getErrorInstance(String message, E data) {
        IReturnObject<E> returnObjcet = new IReturnObject<E>();
        returnObjcet.setData(data);
        returnObjcet.setCode(500);
        returnObjcet.setMessage(message);
        returnObjcet.setSuccess(false);
        return returnObjcet;
    }

    public static <E> IReturnObject<E> getErrorInstance(String message) {
        IReturnObject<E> returnObjcet = new IReturnObject<E>();
        returnObjcet.setCode(500);
        returnObjcet.setMessage(message);
        returnObjcet.setSuccess(false);
        return returnObjcet;
    }

    public static <E> IReturnObject<E> getSuccessInstance(E data) {
        IReturnObject<E> returnObjcet = new IReturnObject<E>();
        returnObjcet.setData(data);
        returnObjcet.setCode(200);
        returnObjcet.setMessage("");
        returnObjcet.setSuccess(true);
        return returnObjcet;
    }

    public static <E> IReturnObject<E> getSuccessInstance(String message, E data) {
        IReturnObject<E> returnObjcet = new IReturnObject<E>();
        returnObjcet.setData(data);
        returnObjcet.setCode(200);
        returnObjcet.setMessage(message);
        returnObjcet.setSuccess(true);
        return returnObjcet;
    }
}

