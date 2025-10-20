/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream;

import java.util.List;

public interface IParamListStrem<P> {
    public IParamListStrem<P> i18n();

    public IParamListStrem<P> auth();

    public IParamListStrem<P> auth(String var1, String var2);

    public List<P> getList();
}

