/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontDataDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontPluginDefineFactory;
import org.springframework.stereotype.Component;

@Component
public class FrontDataDefineFactory
implements FrontPluginDefineFactory {
    @Override
    public String getName() {
        return "data";
    }

    @Override
    public Class<? extends FrontPluginDefine> getType() {
        return FrontDataDefine.class;
    }
}

