/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 */
package com.jiuqi.nr.calibre2;

import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreSolution;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;

public interface ICalibreResolutionService {
    public String getExpression(CalibreDataDTO var1);

    public CalibreSolution getList(CalibreDataDTO var1, ExecutorContext var2);
}

