/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.define;

import com.jiuqi.nr.data.engine.summary.define.SumBaseZB;
import java.util.List;

public interface ISumBaseZBProvider {
    public String getType();

    public SumBaseZB getSumZBByName(Object var1, Object var2);

    public List<SumBaseZB> findZB(Object var1);
}

