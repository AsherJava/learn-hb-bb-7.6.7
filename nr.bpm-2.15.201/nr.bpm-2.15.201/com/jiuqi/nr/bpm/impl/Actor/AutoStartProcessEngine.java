/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.ApplicationInitialization
 *  org.activiti.engine.impl.util.IoUtil
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.common.DeploymentBuilder;
import com.jiuqi.nr.bpm.common.ProcessDefinition;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.exception.NotFoundResourceFileException;
import com.jiuqi.nr.bpm.impl.Actor.ActivitiModuleDescriptor;
import com.jiuqi.nr.bpm.service.DeployService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.activiti.engine.impl.util.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class AutoStartProcessEngine
extends ApplicationObjectSupport
implements ApplicationInitialization {
    private static final Logger logger = LoggerFactory.getLogger(AutoStartProcessEngine.class);
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    private DeployService deployService;

    public void init() throws IllegalStateException, IOException {
        Resource[] resources = this.getApplicationContext().getResources("classpath*:/activiti/*.activiti");
        if (resources == null || resources.length <= 0) {
            return;
        }
        for (Resource resource : resources) {
            logger.info(resource.getURL().toString());
            ActivitiModuleDescriptor module = ActivitiModuleDescriptor.createModuleDescriptor(resource);
            this.deployProcessEngine(module);
        }
    }

    public void deployProcessEngine(ActivitiModuleDescriptor module) {
        Optional<ProcessEngine> engine = this.processEngineProvider.getProcessEngine();
        if (!engine.isPresent()) {
            return;
        }
        this.deployService = engine.get().getDeployService();
        DeploymentBuilder deploymentBuilder = this.deployService.createDeploymentBuilder();
        ClassPathResource resource = new ClassPathResource(module.getFilePath());
        InputStream resourceIs = null;
        try {
            resourceIs = resource.getInputStream();
            String resourceName = module.getTitle() + ".bpmn20.xml";
            String processDefinitionKey = module.getKey();
            boolean isNeedRedeploy = false;
            List<ProcessDefinition> processDefinitions = this.deployService.getProcessDefinitionByKey(processDefinitionKey);
            String dbResource = "";
            if (processDefinitions.size() > 0) {
                dbResource = processDefinitions.get(processDefinitions.size() - 1).getResourceName();
                isNeedRedeploy = this.needRedeploy(resourceName, dbResource);
            } else {
                isNeedRedeploy = true;
            }
            if (isNeedRedeploy) {
                deploymentBuilder.addStreamResource(resourceName, resourceIs, dbResource).coverMode(processDefinitionKey).deploy();
            }
        }
        catch (IOException e) {
            logger.error("\u6587\u4ef6\u8f6c\u4e3a\u6d41\u65f6\u53d1\u751fIO\u5f02\u5e38");
            throw new BpmException("\u6587\u4ef6\u8f6c\u4e3a\u6d41\u65f6\u53d1\u751fIO\u5f02\u5e38", e);
        }
        finally {
            IoUtil.closeSilently((InputStream)resourceIs);
        }
    }

    private boolean needRedeploy(String localResource, String dbResource) {
        String[] temp;
        if (localResource.isEmpty() || dbResource.isEmpty()) {
            throw new NotFoundResourceFileException("\u627e\u4e0d\u5230\u8d44\u6e90\u6587\u4ef6");
        }
        String localResourceName = localResource.split("\\.")[0];
        String dbResourceName = dbResource.split("\\.")[0];
        int localResourceNameVersion = Integer.parseInt(String.valueOf(localResourceName.charAt(localResourceName.length() - 1)));
        int dbResourceNameVersion = Integer.parseInt(String.valueOf(dbResourceName.charAt(dbResourceName.length() - 1)));
        if (localResourceName.contains("!")) {
            temp = localResourceName.split("!");
            localResourceNameVersion = Integer.parseInt(temp[temp.length - 1]);
        }
        if (dbResourceName.contains("!")) {
            temp = dbResourceName.split("!");
            dbResourceNameVersion = Integer.parseInt(temp[temp.length - 1]);
        }
        return localResourceNameVersion > dbResourceNameVersion;
    }

    public void init(boolean isSysTenant) {
        try {
            this.init();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("\u79df\u6237\u521d\u59cb\u5316\u5f02\u5e38");
        }
    }
}

