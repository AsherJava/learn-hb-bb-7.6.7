/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.SubmitProperty;

public class SubmitPropertyImpl
implements SubmitProperty {
    private String rename;
    private RetrieveStrategy retrieveOrReturn;

    @Override
    public String getRename() {
        return this.rename;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

    @Override
    public RetrieveStrategy getRetrieveOrReturn() {
        return this.retrieveOrReturn;
    }

    public void setRetrieveOrReturn(RetrieveStrategy retrieveOrReturn) {
        this.retrieveOrReturn = retrieveOrReturn;
    }
}

