/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.internal.deploy;

import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.Set;
import java.util.function.Consumer;

public interface IDataSchemeDeployer {
    public DataSchemeType[] getDataSchemeTypes();

    public void deployDataScheme(DeployContext var1, Consumer<ProgressItem> var2);

    public void deployDataTables(DeployContext var1, Set<String> var2, Consumer<ProgressItem> var3);
}

