/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean;

import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.ForceControlInfo;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem;
import java.util.List;

public class SingleForceControlResult
extends ForceControlInfo {
    private List<StepByStepCheckItem> tips;

    public List<StepByStepCheckItem> getTips() {
        return this.tips;
    }

    public void setTips(List<StepByStepCheckItem> tips) {
        this.tips = tips;
    }
}

