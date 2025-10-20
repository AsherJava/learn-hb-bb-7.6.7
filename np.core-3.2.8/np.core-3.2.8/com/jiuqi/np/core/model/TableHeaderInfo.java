/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.model;

import java.io.Serializable;

public class TableHeaderInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String title;

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

