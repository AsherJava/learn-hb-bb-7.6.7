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
import com.jiuqi.nr.definition.deploy.DeployRefreshFormulaEvent;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionFinishedListener;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class DefinitionFinishedEventDispatcher
implements ApplicationListener<DeployRefreshFormulaEvent> {
    private static final Logger LOGGER = LogFactory.getLogger(DefinitionFinishedEventDispatcher.class);
    @Autowired(required=false)
    private List<RuntimeDefinitionFinishedListener> listeners;

    DefinitionFinishedEventDispatcher() {
    }

    @Override
    public void onApplicationEvent(DeployRefreshFormulaEvent event) {
        this.nofifyDeploy(event.getRunTimeFormulaSchemeDefines());
    }

    private void nofifyDeploy(List<String> formulaScheme) {
        if (this.listeners != null) {
            for (RuntimeDefinitionFinishedListener listener : this.listeners) {
                try {
                    listener.onDeployFinished(formulaScheme);
                }
                catch (Exception e) {
                    LOGGER.error("\u54cd\u5e94\u53c2\u6570\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\u3002\u4e8b\u4ef6\uff1a\u53c2\u6570\u53d1\u5e03\uff0c\u76d1\u542c\u5668\uff1a" + listener.getClass().getName(), (Throwable)e);
                }
            }
        }
    }
}

