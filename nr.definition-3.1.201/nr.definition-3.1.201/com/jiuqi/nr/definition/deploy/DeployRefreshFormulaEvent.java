/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.deploy;

import java.util.List;
import org.springframework.context.ApplicationEvent;

public class DeployRefreshFormulaEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private List<String> runTimeFormulaSchemeDefines;

    public DeployRefreshFormulaEvent(List<String> runTimeFormulaSchemeDefines) {
        super(runTimeFormulaSchemeDefines);
        this.runTimeFormulaSchemeDefines = runTimeFormulaSchemeDefines;
    }

    public List<String> getRunTimeFormulaSchemeDefines() {
        return this.runTimeFormulaSchemeDefines;
    }
}

