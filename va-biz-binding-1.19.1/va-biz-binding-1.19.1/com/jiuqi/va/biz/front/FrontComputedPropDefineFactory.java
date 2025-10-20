/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontComputedPropDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontPluginDefineFactory;
import org.springframework.stereotype.Component;

@Component
public class FrontComputedPropDefineFactory
implements FrontPluginDefineFactory {
    @Override
    public String getName() {
        return "computedProp";
    }

    @Override
    public Class<? extends FrontPluginDefine> getType() {
        return FrontComputedPropDefine.class;
    }
}

