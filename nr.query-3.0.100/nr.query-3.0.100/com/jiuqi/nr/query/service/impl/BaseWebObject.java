/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.service.impl;

import java.util.HashMap;
import java.util.Map;

public class BaseWebObject
implements Comparable<BaseWebObject> {
    public static final int STRINGDATA = 1;
    public static final int BOOLEANDATA = 2;
    public static final int NUMBERDATA = 3;
    public static final int UUIDDATA = 4;
    public static final int WEB_BASE_STATE_SUCCESS = 200;
    public static final int WEB_BASE_STATE_FAILED = 400;
    public static final int WEB_BASE_STATE_EXCEPTION = 500;
    public static final String WEB_BASE_RESPONSE_CODE = "respcode";
    public static final String WEB_BASE_RESPONSE_MESSAGE = "message";
    public static final String WEB_BASE_TITLE = "title";
    public static final String WEB_BASE_KEY = "key";
    public static final String WEB_BASE_CODE = "code";
    public static final String WEB_BASE_ORDER = "order";
    protected String key;
    protected String code;
    protected String title;
    protected String order;
    protected Map<String, Object> valueMap = new HashMap<String, Object>();
    protected Map<String, Integer> valueType = new HashMap<String, Integer>();

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Object> getValueMap() {
        return this.valueMap;
    }

    public Map<String, Integer> getValueType() {
        return this.valueType;
    }

    public void setFieldValue(String key, int type, Object value) {
        if (key == null) {
            throw new RuntimeException("\u952e\u503c\u90fd\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        this.valueMap.put(key, value);
        this.valueType.put(key, type);
    }

    public void setFieldValue(String key, Object value) {
        if (key == null) {
            throw new RuntimeException("\u952e\u503c\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        this.valueMap.put(key, value);
        this.valueType.put(key, 1);
    }

    public void setResponse(int state, String message) {
        this.valueMap.put(WEB_BASE_RESPONSE_CODE, state);
        this.valueMap.put(WEB_BASE_RESPONSE_MESSAGE, message);
    }

    public Object getFieldValue(String key) {
        return this.valueMap.get(key);
    }

    public int getFieldType(String key) {
        return this.valueType.get(key);
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int compareTo(BaseWebObject o) {
        return this.key.compareTo(o.getKey());
    }
}

