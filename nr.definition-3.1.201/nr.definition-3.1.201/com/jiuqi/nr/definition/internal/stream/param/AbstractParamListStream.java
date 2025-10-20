/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.internal.stream.IParamListStrem;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractParamListStream<P>
implements IParamListStrem<P> {
    protected Collection<P> params;
    private boolean exeI18n;
    private boolean exeAuth;
    private String entityKeyData;
    private String entityId;

    AbstractParamListStream(Collection<P> params) {
        this.params = params;
    }

    Stream<P> getStream() {
        return this.params.stream();
    }

    @Override
    public IParamListStrem<P> i18n() {
        this.exeI18n = true;
        return this;
    }

    @Override
    public IParamListStrem<P> auth() {
        this.exeAuth = true;
        return this;
    }

    @Override
    public IParamListStrem<P> auth(String entityKeyData, String entityId) {
        this.exeAuth = true;
        this.entityKeyData = entityKeyData;
        this.entityId = entityId;
        return this;
    }

    abstract boolean hasAuth(P var1, String var2, String var3);

    abstract P transI18n(P var1);

    @Override
    public List<P> getList() {
        if (this.exeAuth) {
            this.params = this.getStream().filter(param -> this.hasAuth(param, this.entityKeyData, this.entityId)).collect(Collectors.toList());
        }
        if (this.exeI18n) {
            this.params = this.getStream().map(this::transI18n).collect(Collectors.toList());
        }
        return this.getStream().collect(Collectors.toList());
    }
}

