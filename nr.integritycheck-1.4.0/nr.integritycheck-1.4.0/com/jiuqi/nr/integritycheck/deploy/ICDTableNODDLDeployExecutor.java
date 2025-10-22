/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 */
package com.jiuqi.nr.integritycheck.deploy;

import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.integritycheck.deploy.IntegrityCheckObserver;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ICDTableNODDLDeployExecutor
implements NODDLDeployExecutor {
    @Autowired
    private IntegrityCheckObserver integrityCheckObserver;

    public List<String> preDeploy(String taskKey) {
        try {
            return this.integrityCheckObserver.getICDTableDeployTable(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doDeploy(String taskKey) {
        this.integrityCheckObserver.registerICDTable(taskKey);
    }

    public double getOrder() {
        return 0.0;
    }
}

