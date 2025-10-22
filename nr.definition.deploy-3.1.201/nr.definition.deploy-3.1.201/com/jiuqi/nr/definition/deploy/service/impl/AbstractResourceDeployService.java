/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.ParamDeployController;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.deploy.service.IResourceDeployExecutor;
import com.jiuqi.nr.definition.deploy.service.IResourceDeployService;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractResourceDeployService
implements IResourceDeployService {
    protected static final Logger LOGGER = ParamDeployController.LOGGER;
    private final EnumMap<ParamResourceType, IResourceDeployExecutor> resourceDeployExecutors = new EnumMap(ParamResourceType.class);

    @Autowired
    public void setIResourceDeployExecutors(List<IResourceDeployExecutor> executors) {
        for (IResourceDeployExecutor resourceDeployExecutor : executors) {
            this.resourceDeployExecutors.put(resourceDeployExecutor.getType(), resourceDeployExecutor);
        }
    }

    private IResourceDeployExecutor getIResourceDeployExecutor(ParamResourceType type) {
        return this.resourceDeployExecutors.get(type);
    }

    protected void deploy(String schemeKey) {
        this.deploy(schemeKey, Collections.emptyList());
    }

    protected void rollback(String schemeKey) {
        this.rollback(schemeKey, Collections.emptyList());
    }

    @Override
    public void deploy(String schemeKey, List<String> sourceKeys) {
        IResourceDeployExecutor resourceDeployExecutor = this.getIResourceDeployExecutor(this.getType());
        if (null == resourceDeployExecutor) {
            throw new ParamDeployException("\u672a\u627e\u5230" + this.getType().getName() + "\u7684\u53d1\u5e03\u6267\u884c\u5668");
        }
        resourceDeployExecutor.deploy(schemeKey, sourceKeys);
    }

    @Override
    public void rollback(String schemeKey, List<String> sourceKeys) {
        IResourceDeployExecutor resourceDeployExecutor = this.getIResourceDeployExecutor(this.getType());
        if (null == resourceDeployExecutor) {
            throw new ParamDeployException("\u672a\u627e\u5230" + this.getType().getName() + "\u7684\u53d1\u5e03\u6267\u884c\u5668");
        }
        resourceDeployExecutor.rollback(schemeKey, sourceKeys);
    }
}

