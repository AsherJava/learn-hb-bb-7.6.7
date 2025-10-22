/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.nr.definition.deploy.DeployParams;
import org.springframework.context.ApplicationEvent;

public class DeployFinishedEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 612258781376405944L;
    private DeployParams deployParams;

    public DeployFinishedEvent(Object source) {
        super(source);
    }

    public DeployFinishedEvent(Object source, DeployParams deployParams) {
        super(source);
        this.deployParams = deployParams;
    }

    public DeployParams getDeployParams() {
        return this.deployParams;
    }
}

