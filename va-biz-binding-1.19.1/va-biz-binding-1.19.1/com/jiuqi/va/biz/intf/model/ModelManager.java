/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import java.util.List;

public interface ModelManager
extends NamedContainer<ModelType> {
    public List<ModelType> getModelList(String var1);

    public List<ModelType> getModelList(String var1, String var2);
}

