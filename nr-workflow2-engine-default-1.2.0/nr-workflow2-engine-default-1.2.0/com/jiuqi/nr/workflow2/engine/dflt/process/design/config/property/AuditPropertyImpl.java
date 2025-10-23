/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty;

public class AuditPropertyImpl
implements AuditProperty {
    private String rename;
    private boolean isConfirmEnable;
    private boolean isReturnLayerByLayer;
    private boolean isReturnAllSuperior;
    private boolean isForceControl;
    private FillInDescStrategy returnDesc;
    private String returnType;

    @Override
    public String getRename() {
        return this.rename;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

    @Override
    public boolean isConfirmEnable() {
        return this.isConfirmEnable;
    }

    public void setConfirmEnable(boolean confirmEnable) {
        this.isConfirmEnable = confirmEnable;
    }

    @Override
    public boolean isReturnLayerByLayer() {
        return this.isReturnLayerByLayer;
    }

    public void setReturnLayerByLayer(boolean returnLayerByLayer) {
        this.isReturnLayerByLayer = returnLayerByLayer;
    }

    @Override
    public boolean isReturnAllSuperior() {
        return this.isReturnAllSuperior;
    }

    public void setReturnAllSuperior(boolean returnAllSuperior) {
        this.isReturnAllSuperior = returnAllSuperior;
    }

    @Override
    public boolean isForceControl() {
        return this.isForceControl;
    }

    public void setForceControl(boolean forceControl) {
        this.isForceControl = forceControl;
    }

    @Override
    public FillInDescStrategy getReturnDesc() {
        return this.returnDesc;
    }

    public void setReturnDesc(FillInDescStrategy returnDesc) {
        this.returnDesc = returnDesc;
    }

    @Override
    public String getReturnType() {
        return this.returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}

