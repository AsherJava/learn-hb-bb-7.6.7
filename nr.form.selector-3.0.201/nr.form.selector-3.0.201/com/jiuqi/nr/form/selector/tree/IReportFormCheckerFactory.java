/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.tree;

import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import java.util.List;

public interface IReportFormCheckerFactory {
    public List<IReportFormChecker> getFormCheckerList();

    public IReportFormChecker getReportFormChecker(String var1);
}

