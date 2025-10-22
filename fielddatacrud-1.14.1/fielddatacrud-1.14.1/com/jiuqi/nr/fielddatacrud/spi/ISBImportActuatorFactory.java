/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.spi;

import com.jiuqi.nr.fielddatacrud.ActuatorConfig;
import com.jiuqi.nr.fielddatacrud.ISBActuator;

public interface ISBImportActuatorFactory {
    public ISBActuator getActuator(ActuatorConfig var1);
}

