/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.definition.upgrade.checkidgrade;

import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.upgrade.checkidgrade.CheckKeyParam;
import com.jiuqi.nr.definition.upgrade.checkidgrade.GradeCheckKeyEvent;
import com.jiuqi.nr.definition.upgrade.checkidgrade.RuntimeCheckKeyGradeListener;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DefinitionGradeCheckKeyEventDispatcher
implements ApplicationListener<GradeCheckKeyEvent> {
    private static final Logger LOGGER = LogFactory.getLogger(DefinitionGradeCheckKeyEventDispatcher.class);
    @Autowired(required=false)
    private List<RuntimeCheckKeyGradeListener> listeners;

    @Override
    public void onApplicationEvent(GradeCheckKeyEvent event) {
        this.nofifyDeploy(event.getDeployParams());
    }

    private void nofifyDeploy(CheckKeyParam deployParams) {
        if (this.listeners != null) {
            for (RuntimeCheckKeyGradeListener listener : this.listeners) {
                try {
                    listener.onDeploy(deployParams);
                    LOGGER.info("\u54cd\u5e94\u53c2\u6570\u53d8\u66f4\u4e8b\u4ef6\u6267\u884c\u5b8c\u6210\u3002\u4e8b\u4ef6\uff1a\u5ba1\u6838\u4fe1\u606fkey\u5347\u7ea7\uff0c\u76d1\u542c\u5668\uff1a" + listener.getClass().getName());
                }
                catch (Exception e) {
                    LOGGER.error("\u54cd\u5e94\u53c2\u6570\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\u3002\u4e8b\u4ef6\uff1a\u5ba1\u6838\u4fe1\u606fkey\u5347\u7ea7\uff0c\u76d1\u542c\u5668\uff1a" + listener.getClass().getName(), (Throwable)e);
                }
            }
        }
    }
}

