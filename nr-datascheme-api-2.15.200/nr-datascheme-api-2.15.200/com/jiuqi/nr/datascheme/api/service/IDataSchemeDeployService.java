/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.service.IDeployCallback;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface IDataSchemeDeployService {
    public DeployResult unsafeDeployDataScheme(String var1, Consumer<ProgressItem> var2, IDeployCallback var3);

    public DeployResult deployDataScheme(String var1, Consumer<ProgressItem> var2, IDeployCallback var3);

    public DeployResult deployDataTable(String var1, Set<String> var2, boolean var3, Consumer<ProgressItem> var4);

    public void deployDataTable(String var1, boolean var2) throws JQException;

    public List<String> preDeployDataScheme(String var1, boolean var2, Consumer<ProgressItem> var3) throws JQException;

    public void deployTableModel(String var1) throws JQException;
}

