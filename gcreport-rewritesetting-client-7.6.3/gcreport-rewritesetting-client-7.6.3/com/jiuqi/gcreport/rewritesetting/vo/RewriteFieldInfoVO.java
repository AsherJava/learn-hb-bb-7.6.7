/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rewritesetting.vo;

public class RewriteFieldInfoVO {
    private String name;
    private String title;

    public RewriteFieldInfoVO(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

