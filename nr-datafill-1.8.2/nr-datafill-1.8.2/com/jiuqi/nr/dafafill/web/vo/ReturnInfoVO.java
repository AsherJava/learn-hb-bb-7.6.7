/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class ReturnInfoVO {
    private Object data;
    private String msg;
    private boolean success = false;
    private Map<String, String> extendMap;

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getExtendMap() {
        return this.extendMap;
    }

    public void setExtendMap(Map<String, String> extendMap) {
        this.extendMap = extendMap;
    }

    public void addExtendMap(String key, String value) {
        if (CollectionUtils.isEmpty(this.extendMap)) {
            this.extendMap = new HashMap<String, String>();
        }
        this.extendMap.put(key, value);
    }
}

