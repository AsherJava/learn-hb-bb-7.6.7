/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO;

public class EndCarryForwardResultVO {
    private MinorityRecoveryTableVO minorityRecoveryTableVO;
    private LossGainOffsetVO lossGainOffsetVO;

    public MinorityRecoveryTableVO getMinorityRecoveryTableVO() {
        return this.minorityRecoveryTableVO;
    }

    public void setMinorityRecoveryTableVO(MinorityRecoveryTableVO minorityRecoveryTableVO) {
        this.minorityRecoveryTableVO = minorityRecoveryTableVO;
    }

    public LossGainOffsetVO getLossGainOffsetVO() {
        return this.lossGainOffsetVO;
    }

    public void setLossGainOffsetVO(LossGainOffsetVO lossGainOffsetVO) {
        this.lossGainOffsetVO = lossGainOffsetVO;
    }
}

