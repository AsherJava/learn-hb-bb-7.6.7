/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.tree;

import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import java.util.List;

public interface IReportFormChecker {
    public String getCheckerId();

    public String getShowText();

    default public int getOrdinary() {
        return 0;
    }

    default public boolean isDisplay() {
        return false;
    }

    public List<IReportFormChecker> getGroupChecker();

    public IFormCheckExecutor getExecutor(IFormQueryContext var1);
}

