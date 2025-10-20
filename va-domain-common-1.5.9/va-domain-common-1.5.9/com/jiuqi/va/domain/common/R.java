/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.domain.common;

import java.util.HashMap;
import java.util.Map;

public class R
extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String EXIST = "exist";
    public static final String DATA = "data";
    public static final String STATUS = "status";
    public static final String RESULT = "result";

    public R() {
        this.put(CODE, (Object)0);
        this.put(MSG, (Object)"\u64cd\u4f5c\u6210\u529f");
    }

    public static R error() {
        return R.error(1, "\u64cd\u4f5c\u5931\u8d25");
    }

    public static R error(String msg) {
        return R.error(1, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put(CODE, (Object)code);
        r.put(MSG, (Object)msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put(MSG, (Object)msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        if (map.containsKey(CODE)) {
            try {
                Integer.parseInt("" + map.get(CODE));
            }
            catch (Exception e) {
                throw new RuntimeException("Then 'code' key must be integer");
            }
        }
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        if (key.equals(CODE)) {
            try {
                Integer.parseInt("" + value);
            }
            catch (Exception e) {
                throw new RuntimeException("Then 'code' key must be integer");
            }
        }
        super.put(key, value);
        this.serverError();
        return this;
    }

    private void serverError() {
        if (this.containsKey("error") && this.containsKey(STATUS) && "Internal Server Error".equals((String)this.get("error"))) {
            super.put(CODE, this.get(STATUS));
        }
    }

    public void setCode(int code) {
        this.put(CODE, (Object)code);
    }

    public int getCode() {
        Object code = this.get(CODE);
        if (code == null) {
            return 0;
        }
        return (Integer)code;
    }

    public void setMsg(String msg) {
        this.put(MSG, (Object)msg);
    }

    public void setMsg(int code, String msg) {
        this.put(CODE, (Object)code);
        this.put(MSG, (Object)msg);
    }

    public String getMsg() {
        return (String)this.get(MSG);
    }
}

