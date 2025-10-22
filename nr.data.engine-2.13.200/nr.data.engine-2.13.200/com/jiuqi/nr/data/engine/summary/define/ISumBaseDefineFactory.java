/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.define;

import com.jiuqi.nr.data.engine.summary.define.IRelationInfoProvider;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseZBProvider;

public interface ISumBaseDefineFactory {
    public String getType();

    public ISumBaseZBProvider getZBProvider();

    public IRelationInfoProvider getRelationInfoProvider();
}

