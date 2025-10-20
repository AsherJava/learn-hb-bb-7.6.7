/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

import com.jiuqi.va.query.template.enumerate.MessageTypeEnum;

public class QueryCheckItemVO {
    private MessageTypeEnum type;
    private String message;
    private String path;

    public QueryCheckItemVO() {
    }

    public QueryCheckItemVO(MessageTypeEnum type, String message, String path) {
        this.type = type;
        this.message = message;
        this.path = path;
    }

    public MessageTypeEnum getType() {
        return this.type;
    }

    public void setType(MessageTypeEnum type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        return "QueryCheckItemVO{type=" + (Object)((Object)this.type) + ", message='" + this.message + '\'' + ", path='" + this.path + '\'' + '}';
    }
}

