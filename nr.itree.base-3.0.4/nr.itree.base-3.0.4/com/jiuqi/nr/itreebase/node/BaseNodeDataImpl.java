/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.itreebase.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.io.Serializable;
import java.util.HashMap;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BaseNodeDataImpl
extends HashMap<String, Object>
implements IBaseNodeData,
Serializable,
Cloneable {
    @JsonIgnore
    private String key;
    @JsonIgnore
    private String code;
    @JsonIgnore
    private String title;
    @JsonIgnore
    private String[] path;

    public String getKey() {
        if (StringUtils.isEmpty((String)this.key) && this.containsKey("key")) {
            this.key = this.get("key") != null ? this.get("key").toString() : null;
        }
        return this.key;
    }

    @Override
    public boolean containsKey(String key) {
        return super.containsKey(key);
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }

    @Override
    public Object get(String key) {
        return super.get(key);
    }

    @Override
    public Object remove(String key) {
        return super.remove(key);
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        if (StringUtils.isEmpty((String)this.code) && this.containsKey("code")) {
            this.code = this.get("code") != null ? this.get("code").toString() : null;
        }
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        if (StringUtils.isEmpty((String)this.title) && this.containsKey("title")) {
            this.title = this.get("title") != null ? this.get("title").toString() : null;
        }
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String[] getPath() {
        return this.path;
    }

    public void setPath(String[] path) {
        this.path = path;
        this.put("path", (Object)path);
    }

    @Override
    public BaseNodeDataImpl clone() {
        return (BaseNodeDataImpl)super.clone();
    }

    public void putKey(String key) {
        this.key = key;
        this.put("key", (Object)key);
    }

    public void putCode(String code) {
        this.code = code;
        this.put("code", (Object)code);
    }

    public void putTitle(String title) {
        this.title = title;
        this.put("title", (Object)title);
    }

    public static BaseNodeDataImpl getInstance(String key) {
        BaseNodeDataImpl impl = new BaseNodeDataImpl();
        impl.setKey(key);
        return impl;
    }

    public static BaseNodeDataImpl getInstance(String key, String code) {
        BaseNodeDataImpl impl = new BaseNodeDataImpl();
        impl.setKey(key);
        impl.setCode(code);
        return impl;
    }

    public static BaseNodeDataImpl getInstance(String key, String code, String title) {
        BaseNodeDataImpl impl = new BaseNodeDataImpl();
        impl.setKey(key);
        impl.setCode(code);
        impl.setTitle(title);
        return impl;
    }
}

