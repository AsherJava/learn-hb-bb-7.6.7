/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.ITemplateObject
 */
package com.jiuqi.nr.definition.facade.print.common.interactor.adjustment;

import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.ITemplateObject;

public interface AdjustmentFilter {
    public void doFilter(ITemplateObject var1, IDrawObject var2, int var3, PaginateInteractorBase var4, AdjustmentResponse var5, FilterChain var6);
}

