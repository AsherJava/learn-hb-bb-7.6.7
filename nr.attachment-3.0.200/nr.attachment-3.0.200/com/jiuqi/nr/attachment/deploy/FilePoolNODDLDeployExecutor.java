/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 */
package com.jiuqi.nr.attachment.deploy;

import com.jiuqi.nr.attachment.deploy.FilePoolObserver;
import com.jiuqi.nr.attachment.exception.DeployFileTableException;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilePoolNODDLDeployExecutor
implements NODDLDeployExecutor {
    @Autowired
    private FilePoolObserver filePoolObserver;

    public List<String> preDeploy(String taskKey) {
        try {
            return this.filePoolObserver.getDeployTable(taskKey);
        }
        catch (Exception e) {
            throw new DeployFileTableException(e.getMessage(), e);
        }
    }

    public void doDeploy(String taskKey) {
        this.filePoolObserver.registerTable(taskKey);
    }

    public double getOrder() {
        return 2.0;
    }
}

