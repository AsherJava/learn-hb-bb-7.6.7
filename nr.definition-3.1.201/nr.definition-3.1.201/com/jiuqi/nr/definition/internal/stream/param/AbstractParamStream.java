/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.internal.stream.IParamStrem;

public abstract class AbstractParamStream<P>
implements IParamStrem<P> {
    protected P param;
    private boolean exeI18n;
    private boolean exeAuth;
    private String entityKeyData;
    private String entityId;

    AbstractParamStream(P param) {
        this.param = param;
    }

    @Override
    public IParamStrem<P> i18n() {
        this.exeI18n = true;
        return this;
    }

    @Override
    public IParamStrem<P> auth(String entityKeyData, String entityId) {
        this.exeAuth = true;
        this.entityKeyData = entityKeyData;
        this.entityId = entityId;
        return this;
    }

    @Override
    public IParamStrem<P> auth() {
        this.exeAuth = true;
        this.entityKeyData = null;
        this.entityId = null;
        return this;
    }

    abstract boolean hasAuth(P var1, String var2, String var3);

    abstract P transI18n(P var1);

    @Override
    public P get() {
        if (this.exeAuth && !this.hasAuth(this.param, this.entityKeyData, this.entityId)) {
            return null;
        }
        if (this.exeI18n) {
            return this.transI18n(this.param);
        }
        return this.param;
    }
}

