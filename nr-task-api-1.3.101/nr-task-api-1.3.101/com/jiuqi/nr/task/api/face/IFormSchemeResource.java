/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.face;

import com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.face.IResourceIOProvider;
import java.util.List;

public interface IFormSchemeResource
extends IResourceDataProvider,
IResourceIOProvider {
    public List<AbstractFormSchemeResourceFactory> getFactory(String var1);
}

