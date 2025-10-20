/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.client.dto;

import java.io.Serializable;

public class ConvertRefDefineDTO
implements Serializable {
    private static final long serialVersionUID = 954299251160369094L;
    private String code;
    private String name;
    private String param;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}

