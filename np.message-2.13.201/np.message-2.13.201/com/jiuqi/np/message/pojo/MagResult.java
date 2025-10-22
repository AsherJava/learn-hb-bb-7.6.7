/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.pojo;

public class MagResult {
    private RT type = RT.NULL;

    public RT getType() {
        return this.type;
    }

    public void setType(RT type) {
        this.type = type;
    }

    public void success() {
        this.type = RT.SUCCESS;
    }

    public void fail() {
        this.type = RT.FAIL;
    }

    public void setNull() {
        this.type = RT.NULL;
    }

    public boolean isSuccess() {
        return RT.SUCCESS.equals((Object)this.type);
    }

    public boolean isFail() {
        return RT.FAIL.equals((Object)this.type);
    }

    public boolean isNull() {
        return RT.NULL.equals((Object)this.type);
    }

    static enum RT {
        SUCCESS,
        FAIL,
        NULL;

    }
}

