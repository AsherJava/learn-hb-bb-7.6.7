/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.face;

import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.dto.ITransferContext;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.face.IResourceDeploy;
import com.jiuqi.nr.task.api.face.IResourceIOProvider;

public abstract class AbstractFormSchemeResourceFactory {
    public abstract String code();

    public abstract String title();

    public abstract double order();

    public abstract ComponentDefine component();

    public abstract boolean enable(String var1);

    public abstract IResourceDataProvider dataProvider();

    public abstract IResourceIOProvider transferProvider(ITransferContext var1);

    public abstract IResourceDeploy deployProvider();
}

