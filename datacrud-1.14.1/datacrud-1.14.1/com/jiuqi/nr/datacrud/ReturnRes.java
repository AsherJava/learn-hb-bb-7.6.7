/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud;

import java.util.List;

public class ReturnRes {
    public static final int OK = 0;
    public static final int NO_LINK = 1001;
    public static final int NO_AUTH = 1101;
    public static final int NO_REGION_AUTH = 1102;
    public static final int DIM_OUT_OF_RANGE = 1103;
    public static final int NO_DATA = 1201;
    public static final int NO_DIM = 1202;
    public static final int NO_FORMAT = 1203;
    public static final int NO_VERIFICATION = 1204;
    public static final int NO_PARSE = 1211;
    public static final int ADD_ERR = 1401;
    public static final int FM_ADD_ERR = 1501;
    public static final int FM_UPDATE_ERR = 1502;
    public static final int FM_DEL_ERR = 1503;
    public static final int FM_MANAGER_ERR = 1504;
    public static final int ERR = 1900;
    protected int code;
    protected String data;
    protected String message;
    protected List<String> messages;

    public ReturnRes() {
    }

    public ReturnRes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public static ReturnRes build(int code, String message) {
        ReturnRes res = new ReturnRes();
        res.setCode(code);
        res.setMessage(message);
        return res;
    }

    public static ReturnRes build(int code) {
        ReturnRes res = new ReturnRes();
        res.setCode(code);
        return res;
    }

    @Deprecated
    public static ReturnRes ok() {
        return ReturnRes.build(0);
    }

    public static ReturnRes success() {
        return ReturnRes.build(0);
    }

    public static ReturnRes ok(String message) {
        return ReturnRes.build(0, message);
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.getCode() == 0;
    }
}

