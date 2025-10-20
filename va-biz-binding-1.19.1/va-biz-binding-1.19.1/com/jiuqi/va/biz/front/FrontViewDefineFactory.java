/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontPluginDefineFactory;
import com.jiuqi.va.biz.front.FrontViewDefine;
import org.springframework.stereotype.Component;

@Component
public class FrontViewDefineFactory
implements FrontPluginDefineFactory {
    @Override
    public String getName() {
        return "view";
    }

    @Override
    public Class<? extends FrontPluginDefine> getType() {
        return FrontViewDefine.class;
    }
}

