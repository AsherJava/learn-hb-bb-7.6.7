/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontPluginDefineFactory;
import com.jiuqi.va.biz.front.FrontRulerDefine;
import org.springframework.stereotype.Component;

@Component
public class FrontRulerDefineFactory
implements FrontPluginDefineFactory {
    @Override
    public String getName() {
        return "ruler";
    }

    @Override
    public Class<? extends FrontPluginDefine> getType() {
        return FrontRulerDefine.class;
    }
}

