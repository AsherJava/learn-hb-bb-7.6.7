/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 */
package com.jiuqi.nr.snapshot.deploy;

import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.snapshot.deploy.SnapshotTableUtils;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SnapshotTableNODDLDeployExecutor
implements NODDLDeployExecutor {
    public List<String> preDeploy(String taskKey) {
        try {
            SnapshotTableUtils snapshotTableUtils = new SnapshotTableUtils();
            return snapshotTableUtils.getSnapshotDeployTable(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doDeploy(String taskKey) {
        SnapshotTableUtils snapshotTableUtils = new SnapshotTableUtils();
        snapshotTableUtils.registerSnapshotTable(taskKey);
    }

    public double getOrder() {
        return 2.0;
    }
}

