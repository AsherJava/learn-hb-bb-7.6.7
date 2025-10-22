/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.nr.definition.deploy.DeployParams;
import org.springframework.context.ApplicationEvent;

public class DeployPrepareEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private DeployParams deployParams;

    public DeployPrepareEvent(Object source) {
        super(source);
    }

    public DeployPrepareEvent(Object source, DeployParams deployParams) {
        super(source);
        this.deployParams = deployParams;
    }

    public DeployParams getDeployParams() {
        return this.deployParams;
    }
}

