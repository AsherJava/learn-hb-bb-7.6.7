/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.repository.ProcessDefinition
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.impl.activiti6.extension.ReferCacheCmd;
import com.jiuqi.nr.bpm.impl.common.BpmCacheChangedMessage;
import java.util.List;
import java.util.Optional;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Subscriber(channels={"com.jiuqi.np.dataengine.auth.authgo.AuthgoCacheChangedMessage"})
public class BpmCacheMessageSubscriber
implements MessageSubscriber {
    @Autowired
    ProcessEngineProvider processEngineProvider;

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        if (!(message instanceof BpmCacheChangedMessage)) {
            return;
        }
        BpmCacheChangedMessage bpmCacheChangedMessage = (BpmCacheChangedMessage)message;
        Optional<ProcessEngine> processEngine = this.processEngineProvider.getProcessEngine();
        List processDefinitions = processEngine.get().getDeployService().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(bpmCacheChangedMessage.getProcessDefinitionKey()).list();
        for (ProcessDefinition processDefinition : processDefinitions) {
            ReferCacheCmd command = new ReferCacheCmd(processDefinition.getId());
            processEngine.get().getDeployService().getActivitExtensionService().getManagementService().executeCommand((Command)command);
        }
    }
}

