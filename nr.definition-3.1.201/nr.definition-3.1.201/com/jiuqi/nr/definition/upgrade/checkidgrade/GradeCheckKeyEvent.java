/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.upgrade.checkidgrade;

import com.jiuqi.nr.definition.upgrade.checkidgrade.CheckKeyParam;
import org.springframework.context.ApplicationEvent;

public class GradeCheckKeyEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private CheckKeyParam eventParam;

    public GradeCheckKeyEvent(Object source) {
        super(source);
    }

    public GradeCheckKeyEvent(Object source, CheckKeyParam eventParam) {
        super(source);
        this.eventParam = eventParam;
    }

    public CheckKeyParam getDeployParams() {
        return this.eventParam;
    }
}

