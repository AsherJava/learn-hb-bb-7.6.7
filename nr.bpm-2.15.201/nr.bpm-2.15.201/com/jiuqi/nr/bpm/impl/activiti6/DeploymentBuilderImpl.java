/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.interceptor.Command
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.common.DeploymentBuilder;
import com.jiuqi.nr.bpm.common.ProcessDefinition;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.activiti6.Activiti6DeployServiceImpl;
import com.jiuqi.nr.bpm.impl.activiti6.extension.UpdateProcessIdCmd;
import com.jiuqi.nr.bpm.impl.event.ReferCacheEventImpl;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.interceptor.Command;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

class DeploymentBuilderImpl
implements DeploymentBuilder {
    private String deployName;
    private Map<String, InputStream> resources = new HashMap<String, InputStream>();
    private boolean isCoverMode;
    private String processDefinitionKey;
    private final Activiti6DeployServiceImpl deployService;
    private String oldResourceName;
    private final Logger logger = LoggerFactory.getLogger(DeploymentBuilderImpl.class);

    public DeploymentBuilderImpl(Activiti6DeployServiceImpl deployService) {
        Assert.notNull((Object)deployService, "'deployService' must not be null.");
        this.deployService = deployService;
    }

    @Override
    public DeploymentBuilder name(String name) {
        this.deployName = name;
        return this;
    }

    @Override
    public DeploymentBuilder addStreamResource(String resourceName, InputStream resource, String oldResourceName) {
        Assert.notNull((Object)resourceName, "parameter 'resourceName' must not be null.");
        Assert.notNull((Object)resource, "parameter 'resource' must not be null.");
        this.resources.put(resourceName, resource);
        this.oldResourceName = oldResourceName;
        return this;
    }

    @Override
    public DeploymentBuilder coverMode(String processDefinitionKey) {
        Assert.notNull((Object)processDefinitionKey, "'processDefintionKey' must not be null.");
        this.isCoverMode = true;
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    @Override
    public ProcessDefinition deploy() {
        Assert.notEmpty(this.resources, "resource is required.");
        ProcessDefinition ProcessDefinition2 = null;
        if (this.isCoverMode) {
            if (StringUtils.isEmpty((CharSequence)this.oldResourceName)) {
                this.deployService.coverProcess(this.processDefinitionKey, this.resources);
            } else {
                this.deployService.coverProcess(this.processDefinitionKey, this.resources, this.oldResourceName);
            }
            ReferCacheEventImpl event = new ReferCacheEventImpl();
            event.setProcessDefinitionKey(this.processDefinitionKey);
            try {
                this.deployService.getActionEventHandler().get().onCacheChanged(event);
            }
            catch (Exception e) {
                this.logger.error("\u5de5\u4f5c\u6d41\u7f13\u5b58\u4e8b\u4ef6\u5904\u7406\u9519\u8bef", e);
                throw new UserActionException(event.getProcessDefinitionKey(), e);
            }
        } else {
            ProcessDefinition2 = this.deployService.deployProcess(this.deployName, this.resources);
            UpdateProcessIdCmd updateProcessIdCmd = new UpdateProcessIdCmd(this.processDefinitionKey);
            this.deployService.getActivitExtensionService().getManagementService().executeCommand((Command)updateProcessIdCmd);
        }
        return ProcessDefinition2;
    }
}

