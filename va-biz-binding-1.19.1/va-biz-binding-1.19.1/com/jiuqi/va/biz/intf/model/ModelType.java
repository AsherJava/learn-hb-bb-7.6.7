/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;
import java.util.Collections;
import java.util.List;

public interface ModelType
extends NamedElement {
    @Override
    public String getName();

    public String getTitle();

    public String getBizModule();

    public String getMetaType();

    public String[] getDependPlugins();

    public Class<? extends ModelDefine> getModelDefineClass();

    public Class<? extends Model> getModelClass();

    public void initModelDefine(ModelDefine var1, String var2);

    public void initModelDefine(ModelDefine var1, String var2, String var3);

    public void initModel(Model var1, ModelContext var2, ModelDefine var3);

    default public List<String> getForbiddenActions() {
        return Collections.emptyList();
    }
}

