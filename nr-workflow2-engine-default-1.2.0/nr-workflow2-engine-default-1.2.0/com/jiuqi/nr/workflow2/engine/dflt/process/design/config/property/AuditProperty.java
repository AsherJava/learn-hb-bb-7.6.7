/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;

public interface AuditProperty {
    public String getRename();

    public boolean isConfirmEnable();

    public boolean isReturnLayerByLayer();

    public boolean isReturnAllSuperior();

    public boolean isForceControl();

    public FillInDescStrategy getReturnDesc();

    public String getReturnType();
}

