/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.common;

import java.io.Serializable;

public class SingleResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer status;
    private String msg;
    private transient Object data;

    public static SingleResult build(Integer status, String msg, Object data) {
        return new SingleResult(status, msg, data);
    }

    public static SingleResult ok(Object data) {
        return new SingleResult(data);
    }

    public static SingleResult ok() {
        return new SingleResult(null);
    }

    public SingleResult() {
    }

    public static SingleResult build(Integer status, String msg) {
        return new SingleResult(status, msg, null);
    }

    public SingleResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public SingleResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

