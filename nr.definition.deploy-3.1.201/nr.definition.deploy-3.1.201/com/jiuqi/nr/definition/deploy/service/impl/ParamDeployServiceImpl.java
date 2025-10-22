/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.ParamDeployController;
import com.jiuqi.nr.definition.deploy.common.ParamDeployContext;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployExtendService;
import com.jiuqi.nr.definition.deploy.extend.IPartialDeployExtendService;
import com.jiuqi.nr.definition.deploy.service.IParamDeployService;
import com.jiuqi.nr.definition.deploy.service.IResourceDeployService;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ParamDeployServiceImpl
implements IParamDeployService {
    protected static final Logger LOGGER = ParamDeployController.LOGGER;
    private final EnumMap<ParamResourceType, List<IResourceDeployService>> resourceDeployServices = new EnumMap(ParamResourceType.class);
    private final EnumMap<ParamResourceType, List<IPartialDeployExtendService>> partialDeployExtendServices = new EnumMap(ParamResourceType.class);
    private final List<IParamDeployExtendService> paramDeployExtendServices = new ArrayList<IParamDeployExtendService>();

    @Autowired
    public void setResourceDeployServices(List<IResourceDeployService> resourceDeployServices) {
        for (IResourceDeployService resourceDeployService : resourceDeployServices) {
            this.resourceDeployServices.computeIfAbsent(resourceDeployService.getType(), k -> new ArrayList()).add(resourceDeployService);
        }
    }

    @Autowired(required=false)
    public void setIPartialDeployExtendServices(List<IPartialDeployExtendService> services) {
        if (CollectionUtils.isEmpty(services)) {
            return;
        }
        for (IPartialDeployExtendService paramDeployExtendService : services) {
            this.partialDeployExtendServices.computeIfAbsent(paramDeployExtendService.getType(), k -> new ArrayList()).add(paramDeployExtendService);
        }
    }

    @Autowired(required=false)
    public void setParamDeployExtendServices(List<IParamDeployExtendService> paramDeployExtendServices) {
        if (CollectionUtils.isEmpty(paramDeployExtendServices)) {
            return;
        }
        this.paramDeployExtendServices.addAll(paramDeployExtendServices);
    }

    private void beforeDeploy(String schemeKey, Consumer<String> progress) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u524d)\uff0c\u5f00\u59cb");
        progress.accept("\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u524d)");
        for (IParamDeployExtendService service : this.paramDeployExtendServices) {
            service.beforeDeploy(schemeKey, progress);
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u524d)\uff0c\u5b8c\u6210");
    }

    private void afterDeploy(String schemeKey, Consumer<String> progress) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u540e)\uff0c\u5f00\u59cb");
        progress.accept("\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u540e)");
        for (IParamDeployExtendService service : this.paramDeployExtendServices) {
            service.afterDeploy(schemeKey, progress);
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u540e)\uff0c\u5b8c\u6210");
    }

    private void beforeRollback(String schemeKey, Consumer<String> progress) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u524d)\uff0c\u5f00\u59cb");
        progress.accept("\u56de\u6eda\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u524d)");
        for (IParamDeployExtendService service : this.paramDeployExtendServices) {
            service.beforeRollback(schemeKey, progress);
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u524d)\uff0c\u5b8c\u6210");
    }

    private void afterRollback(String schemeKey, Consumer<String> progress) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u540e)\uff0c\u5f00\u59cb");
        progress.accept("\u56de\u6eda\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u540e)");
        for (IParamDeployExtendService service : this.paramDeployExtendServices) {
            service.afterRollback(schemeKey, progress);
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u62a5\u8868\u65b9\u6848\u6269\u5c55\u53c2\u6570(\u540e)\uff0c\u5b8c\u6210");
    }

    @Override
    public void deploy(ParamDeployContext context, String formSchemeKey) {
        this.beforeDeploy(formSchemeKey, message -> context.getProgressConsumer().accept(ParamResourceType.FORM, (String)message));
        for (ParamResourceType resourceType : ParamResourceType.values()) {
            if (!resourceType.isNeedDeploy()) continue;
            List<IResourceDeployService> iResourceDeployServices = this.resourceDeployServices.get(resourceType);
            if (CollectionUtils.isEmpty(iResourceDeployServices)) {
                throw new ParamDeployException("\u672a\u627e\u5230" + resourceType.getName() + "\u7684\u53d1\u5e03\u670d\u52a1");
            }
            for (IResourceDeployService iResourceDeployService : iResourceDeployServices) {
                iResourceDeployService.deploy(context, formSchemeKey);
            }
        }
        this.afterDeploy(formSchemeKey, message -> context.getProgressConsumer().accept(ParamResourceType.FORM, (String)message));
    }

    @Override
    public void rollback(ParamDeployContext context, String formSchemeKey) {
        this.beforeRollback(formSchemeKey, message -> context.getProgressConsumer().accept(ParamResourceType.FORM, (String)message));
        for (ParamResourceType resourceType : ParamResourceType.values()) {
            if (!resourceType.isNeedDeploy()) continue;
            List<IResourceDeployService> iResourceDeployServices = this.resourceDeployServices.get(resourceType);
            if (CollectionUtils.isEmpty(iResourceDeployServices)) {
                throw new ParamDeployException("\u672a\u627e\u5230" + resourceType.getName() + "\u7684\u53d1\u5e03\u670d\u52a1");
            }
            for (IResourceDeployService iResourceDeployService : iResourceDeployServices) {
                iResourceDeployService.rollback(context, formSchemeKey);
            }
        }
        this.afterRollback(formSchemeKey, message -> context.getProgressConsumer().accept(ParamResourceType.FORM, (String)message));
    }

    protected void beforeDeploy(ParamResourceType resourceType, String schemeKey, List<String> sourceKeys) {
        List<IPartialDeployExtendService> services = this.partialDeployExtendServices.get(resourceType);
        if (CollectionUtils.isEmpty(services)) {
            return;
        }
        for (IPartialDeployExtendService service : services) {
            service.beforeDeploy(schemeKey, sourceKeys);
        }
    }

    protected void beforeRollback(ParamResourceType resourceType, String schemeKey, List<String> sourceKeys) {
        List<IPartialDeployExtendService> services = this.partialDeployExtendServices.get(resourceType);
        if (CollectionUtils.isEmpty(services)) {
            return;
        }
        for (IPartialDeployExtendService service : services) {
            service.beforeRollback(schemeKey, sourceKeys);
        }
    }

    protected void afterDeploy(ParamResourceType resourceType, String schemeKey, List<String> sourceKeys) {
        List<IPartialDeployExtendService> services = this.partialDeployExtendServices.get(resourceType);
        if (CollectionUtils.isEmpty(services)) {
            return;
        }
        for (IPartialDeployExtendService service : services) {
            service.afterDeploy(schemeKey, sourceKeys);
        }
    }

    protected void afterRollback(ParamResourceType resourceType, String schemeKey, List<String> sourceKeys) {
        List<IPartialDeployExtendService> services = this.partialDeployExtendServices.get(resourceType);
        if (CollectionUtils.isEmpty(services)) {
            return;
        }
        for (IPartialDeployExtendService service : services) {
            service.afterRollback(schemeKey, sourceKeys);
        }
    }

    @Override
    public void deploy(ParamResourceType resourceType, String schemeKey, List<String> resourceKeys) {
        List<IResourceDeployService> iResourceDeployServices = this.resourceDeployServices.get(resourceType);
        if (CollectionUtils.isEmpty(iResourceDeployServices)) {
            throw new ParamDeployException("\u672a\u627e\u5230" + resourceType.getName() + "\u7684\u53d1\u5e03\u670d\u52a1");
        }
        this.beforeDeploy(resourceType, schemeKey, resourceKeys);
        for (IResourceDeployService iResourceDeployService : iResourceDeployServices) {
            iResourceDeployService.deploy(schemeKey, resourceKeys);
        }
        this.afterDeploy(resourceType, schemeKey, resourceKeys);
    }

    @Override
    public void rollback(ParamResourceType resourceType, String schemeKey, List<String> resourceKeys) {
        List<IResourceDeployService> iResourceDeployServices = this.resourceDeployServices.get(resourceType);
        if (CollectionUtils.isEmpty(iResourceDeployServices)) {
            throw new ParamDeployException("\u672a\u627e\u5230" + resourceType.getName() + "\u7684\u53d1\u5e03\u670d\u52a1");
        }
        this.beforeRollback(resourceType, schemeKey, resourceKeys);
        for (IResourceDeployService iResourceDeployService : iResourceDeployServices) {
            iResourceDeployService.rollback(schemeKey, resourceKeys);
        }
        this.afterRollback(resourceType, schemeKey, resourceKeys);
    }
}

