/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.impl.model.PluginDefineImpl
 *  com.jiuqi.va.biz.impl.model.PluginTypeBase
 */
package com.jiuqi.va.extend.plugin.reuse;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.extend.plugin.reuse.ReuseFieldPluginDefineImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component(value="vaReuseFieldPluginType")
@ConditionalOnMissingClass(value={"com.jiuqi.cfas.fo.bill.plugin.ReuserFieldPluginType"})
public class ReuseFieldPluginType
extends PluginTypeBase {
    public static final String NAME = "reuseFieldComponent";
    public static final String TITLE = "\u53ef\u590d\u7528\u5b57\u6bb5";

    public String getName() {
        return NAME;
    }

    public String getTitle() {
        return TITLE;
    }

    public Class<? extends ModelImpl> getDependModel() {
        return BillModelImpl.class;
    }

    public Class<? extends PluginDefineImpl> getPluginDefineClass() {
        return ReuseFieldPluginDefineImpl.class;
    }
}

