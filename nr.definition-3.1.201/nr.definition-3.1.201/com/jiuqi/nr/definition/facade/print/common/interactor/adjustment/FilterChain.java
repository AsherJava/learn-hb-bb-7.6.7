/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.ITemplateObject
 */
package com.jiuqi.nr.definition.facade.print.common.interactor.adjustment;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentFilter;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.ITemplateObject;
import java.util.ArrayList;
import java.util.List;

public class FilterChain {
    private ExecutorContext executorContext;
    private List<AdjustmentFilter> allFilter = new ArrayList<AdjustmentFilter>();
    private int index = 0;

    private FilterChain() {
        this.init();
    }

    private void init() {
        String[] beanNamesForType;
        for (String baenName : beanNamesForType = BeanUtil.getApplicationContext().getBeanNamesForType(AdjustmentFilter.class)) {
            this.allFilter.add(BeanUtil.getApplicationContext().getBean(baenName, AdjustmentFilter.class));
        }
    }

    public static FilterChain getInstance() {
        return new FilterChain();
    }

    public void doFilter(ITemplateObject templateObj, IDrawObject drawObj, int pageIndex, PaginateInteractorBase pdfIPaginateInteractor, AdjustmentResponse res) {
        if (this.allFilter.size() <= this.index) {
            return;
        }
        AdjustmentFilter one = this.allFilter.get(this.index);
        ++this.index;
        one.doFilter(templateObj, drawObj, pageIndex, pdfIPaginateInteractor, res, this);
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }
}

