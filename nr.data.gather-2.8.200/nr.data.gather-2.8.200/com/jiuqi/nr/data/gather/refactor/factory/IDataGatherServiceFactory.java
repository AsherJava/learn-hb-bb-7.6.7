/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.data.gather.refactor.factory;

import com.jiuqi.nr.data.gather.refactor.provider.NodeCheckOptionProvider;
import com.jiuqi.nr.data.gather.refactor.provider.NodeGatherOptionProvider;
import com.jiuqi.nr.data.gather.refactor.service.NodeCheckService;
import com.jiuqi.nr.data.gather.refactor.service.NodeGatherService;
import com.jiuqi.nr.data.gather.refactor.service.SelectGatherService;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;

public interface IDataGatherServiceFactory {
    public NodeGatherService getNodeGatherService();

    public NodeGatherService getNodeGatherService(IProviderStore var1);

    public NodeGatherService getNodeGatherService(NodeGatherOptionProvider var1);

    public NodeGatherService getNodeGatherService(IProviderStore var1, NodeGatherOptionProvider var2);

    public NodeCheckService getNodeCheckService();

    public NodeCheckService getNodeCheckService(IProviderStore var1);

    public NodeCheckService getNodeCheckService(NodeCheckOptionProvider var1);

    public NodeCheckService getNodeCheckService(IProviderStore var1, NodeCheckOptionProvider var2);

    public SelectGatherService getSelectGatherService();

    public SelectGatherService getSelectGatherService(IProviderStore var1);

    public SelectGatherService getSelectGatherService(NodeGatherOptionProvider var1);

    public SelectGatherService getSelectGatherService(IProviderStore var1, NodeGatherOptionProvider var2);
}

