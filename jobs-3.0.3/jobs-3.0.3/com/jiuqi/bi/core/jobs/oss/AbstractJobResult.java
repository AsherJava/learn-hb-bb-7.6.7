/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.oss;

public abstract class AbstractJobResult {
    private String name;
    private String extName;
    private String userGuid;
    private String desc;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtName() {
        return this.extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getUserGuid() {
        return this.userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

