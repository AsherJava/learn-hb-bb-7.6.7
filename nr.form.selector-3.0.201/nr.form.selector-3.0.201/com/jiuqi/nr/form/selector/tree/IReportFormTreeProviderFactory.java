/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.tree;

import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import java.util.List;

public interface IReportFormTreeProviderFactory {
    public IReportFormTreeProvider getFormTreeProvider(IFormQueryHelper var1, List<IReportFormChecker> var2);

    public IReportFormTreeData getFormTreeDataProvider(IFormQueryHelper var1, List<IReportFormChecker> var2, String var3, List<String> var4);
}

