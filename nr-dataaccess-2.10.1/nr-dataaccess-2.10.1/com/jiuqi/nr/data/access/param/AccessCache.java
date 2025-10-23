/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessCode;

public class AccessCache {
    private AccessCode visit;
    private AccessCode read;
    private AccessCode write;

    public AccessCode getVisit() {
        return this.visit;
    }

    public void setVisit(AccessCode visit) {
        this.visit = visit;
    }

    public AccessCode getRead() {
        return this.read;
    }

    public void setRead(AccessCode read) {
        this.read = read;
    }

    public AccessCode getWrite() {
        return this.write;
    }

    public void setWrite(AccessCode write) {
        this.write = write;
    }
}

