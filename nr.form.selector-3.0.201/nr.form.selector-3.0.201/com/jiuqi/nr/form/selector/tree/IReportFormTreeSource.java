/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.tree;

import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeData;

public interface IReportFormTreeSource {
    public String getFormSourceId();

    public IReportFormTreeData getTreeProvider(IFormQueryHelper var1);
}

