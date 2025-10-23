/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 */
package com.jiuqi.nr.fmdm.internal.check;

import io.swagger.annotations.ApiModel;

@ApiModel(value="CheckNodeInfo", description="\u5c5e\u6027\u8282\u70b9\u4fe1\u606f")
public class CheckNodeInfo {
    private int type;
    private String content;

    public CheckNodeInfo() {
    }

    public CheckNodeInfo(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

