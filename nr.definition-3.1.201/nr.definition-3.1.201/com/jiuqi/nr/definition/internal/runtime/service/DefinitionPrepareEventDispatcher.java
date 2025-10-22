/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.DeployPrepareEvent;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DefinitionPrepareEventDispatcher
implements ApplicationListener<DeployPrepareEvent> {
    private static final Logger LOGGER = LogFactory.getLogger(DefinitionPrepareEventDispatcher.class);
    @Autowired(required=false)
    private List<DeployPrepareEventListener> listeners;

    @Override
    public void onApplicationEvent(DeployPrepareEvent event) {
        this.nofifyDeploy(event.getDeployParams());
    }

    private void nofifyDeploy(DeployParams deployParams) {
        if (null == this.listeners || this.listeners.isEmpty()) {
            return;
        }
        for (DeployPrepareEventListener listener : this.listeners) {
            try {
                listener.onDeploy(deployParams);
            }
            catch (Exception e) {
                LOGGER.error("\u54cd\u5e94\u53c2\u6570\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\u3002\u4e8b\u4ef6\uff1a\u53c2\u6570\u53d1\u5e03\uff0c\u76d1\u542c\u5668\uff1a" + listener.getClass().getName(), (Throwable)e);
                throw new CreateSystemTableException(e);
            }
        }
    }
}

