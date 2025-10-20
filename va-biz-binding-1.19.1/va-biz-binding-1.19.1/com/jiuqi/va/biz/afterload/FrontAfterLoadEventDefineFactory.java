/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.afterload;

import com.jiuqi.va.biz.afterload.FrontAfterLoadEventDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontPluginDefineFactory;
import org.springframework.stereotype.Component;

@Component
public class FrontAfterLoadEventDefineFactory
implements FrontPluginDefineFactory {
    @Override
    public String getName() {
        return "afterLoadEvent";
    }

    @Override
    public Class<? extends FrontPluginDefine> getType() {
        return FrontAfterLoadEventDefine.class;
    }
}

