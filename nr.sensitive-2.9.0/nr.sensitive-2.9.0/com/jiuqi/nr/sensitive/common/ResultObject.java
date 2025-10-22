/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.sensitive.common;

import com.jiuqi.np.common.exception.ErrorEnum;
import java.io.Serializable;

public class ResultObject
implements Serializable {
    private static final long serialVersionUID = -71190747922860097L;
    private boolean state;
    private String message;
    private transient Object data;

    public ResultObject() {
        this.state = true;
        this.message = "\u64cd\u4f5c\u6210\u529f!";
    }

    public ResultObject(ErrorEnum error) {
        this.state = false;
        this.message = error.getCode() + error.getMessage();
    }

    public ResultObject(Exception e) {
        this.state = false;
        this.message = e.getMessage();
    }

    public boolean isState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return this.message;
    }

    public ResultObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public ResultObject setData(Object data) {
        this.data = data;
        return this;
    }

    public static ResultObject ok() {
        return new ResultObject();
    }
}

