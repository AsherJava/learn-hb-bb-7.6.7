/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardGroupDataVO;

public class EndCarryForwardDataPoolVO {
    private EndCarryForwardGroupDataVO deferredIncomeTaxGroup = new EndCarryForwardGroupDataVO();
    private EndCarryForwardGroupDataVO minorityRecoveryGroup = new EndCarryForwardGroupDataVO();
    private EndCarryForwardGroupDataVO lossGainGroup = new EndCarryForwardGroupDataVO();

    public EndCarryForwardGroupDataVO getDeferredIncomeTaxGroup() {
        return this.deferredIncomeTaxGroup;
    }

    public void setDeferredIncomeTaxGroup(EndCarryForwardGroupDataVO deferredIncomeTaxGroup) {
        this.deferredIncomeTaxGroup = deferredIncomeTaxGroup;
    }

    public EndCarryForwardGroupDataVO getMinorityRecoveryGroup() {
        return this.minorityRecoveryGroup;
    }

    public void setMinorityRecoveryGroup(EndCarryForwardGroupDataVO minorityRecoveryGroup) {
        this.minorityRecoveryGroup = minorityRecoveryGroup;
    }

    public EndCarryForwardGroupDataVO getLossGainGroup() {
        return this.lossGainGroup;
    }

    public void setLossGainGroup(EndCarryForwardGroupDataVO lossGainGroup) {
        this.lossGainGroup = lossGainGroup;
    }
}

