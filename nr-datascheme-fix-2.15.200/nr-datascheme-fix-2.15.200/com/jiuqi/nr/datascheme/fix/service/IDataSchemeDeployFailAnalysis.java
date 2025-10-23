/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.service;

import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDataTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModel;
import java.util.List;

public interface IDataSchemeDeployFailAnalysis {
    public DeployExType doAnalysis(DeployFixDataTable var1, List<DeployFixTableModel> var2, boolean var3);
}

