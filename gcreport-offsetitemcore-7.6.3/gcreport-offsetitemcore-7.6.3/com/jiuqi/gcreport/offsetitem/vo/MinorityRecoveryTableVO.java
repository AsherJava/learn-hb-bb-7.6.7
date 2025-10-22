/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MinorityRecoveryTableVO {
    private MinorityRecoveryRowVO allTotal = new MinorityRecoveryRowVO();
    private MinorityRecoveryRowVO unrealizedGainLossTotal = new MinorityRecoveryRowVO();
    private MinorityRecoveryRowVO downStreamTotal = new MinorityRecoveryRowVO();
    private List<MinorityRecoveryRowVO> downStream = new ArrayList<MinorityRecoveryRowVO>();
    private MinorityRecoveryRowVO againstStreamTotal = new MinorityRecoveryRowVO();
    private List<MinorityRecoveryRowVO> againstStream = new ArrayList<MinorityRecoveryRowVO>();
    private MinorityRecoveryRowVO horizStreamTotal = new MinorityRecoveryRowVO();
    private List<MinorityRecoveryRowVO> horizStream = new ArrayList<MinorityRecoveryRowVO>();
    private MinorityRecoveryRowVO deferredIncomeTaxTotal = new MinorityRecoveryRowVO();
    private List<MinorityRecoveryRowVO> deferredIncomeTax = new ArrayList<MinorityRecoveryRowVO>();
    private MinorityRecoveryRowVO lossGainTotal = new MinorityRecoveryRowVO();
    private List<MinorityRecoveryRowVO> lossGain = new ArrayList<MinorityRecoveryRowVO>();
    private Integer fractionDigits;

    public MinorityRecoveryRowVO getAllTotal() {
        return this.allTotal;
    }

    public MinorityRecoveryRowVO getUnrealizedGainLossTotal() {
        return this.unrealizedGainLossTotal;
    }

    public List<MinorityRecoveryRowVO> getDownStream() {
        return this.downStream;
    }

    public List<MinorityRecoveryRowVO> getAgainstStream() {
        return this.againstStream;
    }

    public List<MinorityRecoveryRowVO> getHorizStream() {
        return this.horizStream;
    }

    public MinorityRecoveryRowVO getDeferredIncomeTaxTotal() {
        return this.deferredIncomeTaxTotal;
    }

    public List<MinorityRecoveryRowVO> getDeferredIncomeTax() {
        return this.deferredIncomeTax;
    }

    public MinorityRecoveryRowVO getDownStreamTotal() {
        return this.downStreamTotal;
    }

    public MinorityRecoveryRowVO getAgainstStreamTotal() {
        return this.againstStreamTotal;
    }

    public MinorityRecoveryRowVO getHorizStreamTotal() {
        return this.horizStreamTotal;
    }

    public List<MinorityRecoveryRowVO> getLossGain() {
        return this.lossGain;
    }

    public MinorityRecoveryRowVO getLossGainTotal() {
        return this.lossGainTotal;
    }

    public void sumTotalRow() {
        this.clearSumValue();
        MinorityRecoveryTableVO.sumValue(this.downStream, this.downStreamTotal);
        MinorityRecoveryTableVO.sumValue(this.againstStream, this.againstStreamTotal);
        MinorityRecoveryTableVO.sumValue(this.horizStream, this.horizStreamTotal);
        MinorityRecoveryTableVO.sumValue(this.downStreamTotal, this.unrealizedGainLossTotal);
        MinorityRecoveryTableVO.sumValue(this.againstStreamTotal, this.unrealizedGainLossTotal);
        MinorityRecoveryTableVO.sumValue(this.horizStreamTotal, this.unrealizedGainLossTotal);
        MinorityRecoveryTableVO.sumValue(this.deferredIncomeTax, this.deferredIncomeTaxTotal);
        MinorityRecoveryTableVO.sumValue(this.lossGain, this.lossGainTotal);
        MinorityRecoveryTableVO.sumValue(this.unrealizedGainLossTotal, this.allTotal);
        MinorityRecoveryTableVO.sumValue(this.deferredIncomeTaxTotal, this.allTotal);
        this.allTotal.setOffsetAmt(this.allTotal.getOffsetAmt().add(this.lossGainTotal.getOffsetAmt()));
        this.allTotal.setLossGainAmt(this.allTotal.getLossGainAmt().add(this.lossGainTotal.getLossGainAmt()));
    }

    public void repairValue(boolean minusDeferredIncomeTax, Boolean enableDeferredIncomeTax) {
        this.repairRecoveryColValue(this.downStream, minusDeferredIncomeTax, enableDeferredIncomeTax);
        this.repairRecoveryColValue(this.deferredIncomeTax, minusDeferredIncomeTax, enableDeferredIncomeTax);
        this.repairRecoveryColValue(this.lossGain, false, enableDeferredIncomeTax);
    }

    private void repairRecoveryColValue(List<MinorityRecoveryRowVO> rowList, boolean minusDeferredIncomeTax, Boolean enableDeferredIncomeTax) {
        for (MinorityRecoveryRowVO rowVO : rowList) {
            rowVO.repair(minusDeferredIncomeTax, enableDeferredIncomeTax);
        }
    }

    private void clearSumValue() {
        MinorityRecoveryTableVO.clearValue(this.downStreamTotal);
        MinorityRecoveryTableVO.clearValue(this.againstStreamTotal);
        MinorityRecoveryTableVO.clearValue(this.horizStreamTotal);
        MinorityRecoveryTableVO.clearValue(this.unrealizedGainLossTotal);
        MinorityRecoveryTableVO.clearValue(this.deferredIncomeTaxTotal);
        MinorityRecoveryTableVO.clearValue(this.lossGainTotal);
        MinorityRecoveryTableVO.clearValue(this.allTotal);
    }

    private static void clearValue(MinorityRecoveryRowVO rowVO) {
        rowVO.setOffsetAmt(BigDecimal.ZERO);
        rowVO.setDiTaxAmt(BigDecimal.ZERO);
        rowVO.setMinorityOffsetAmt(BigDecimal.ZERO);
        rowVO.setMinorityDiTaxAmt(BigDecimal.ZERO);
        rowVO.setMinorityTotalAmt(BigDecimal.ZERO);
        rowVO.setLossGainAmt(BigDecimal.ZERO);
    }

    private static void sumValue(List<MinorityRecoveryRowVO> srcRows, MinorityRecoveryRowVO destRow) {
        for (MinorityRecoveryRowVO rowVO : srcRows) {
            MinorityRecoveryTableVO.sumValue(rowVO, destRow);
        }
    }

    private static void sumValue(MinorityRecoveryRowVO srcRow, MinorityRecoveryRowVO destRow) {
        if (srcRow.getOffsetAmt() != null) {
            destRow.setOffsetAmt(destRow.getOffsetAmt().add(srcRow.getOffsetAmt().setScale(2, 4)));
        }
        if (srcRow.getDiTaxAmt() != null) {
            destRow.setDiTaxAmt(destRow.getDiTaxAmt().add(srcRow.getDiTaxAmt().setScale(2, 4)));
        }
        if (srcRow.getMinorityOffsetAmt() != null) {
            destRow.setMinorityOffsetAmt(destRow.getMinorityOffsetAmt().add(srcRow.getMinorityOffsetAmt().setScale(2, 4)));
            destRow.setMinorityDiTaxAmt(destRow.getMinorityDiTaxAmt().add(srcRow.getMinorityDiTaxAmt().setScale(2, 4)));
            destRow.setMinorityTotalAmt(destRow.getMinorityTotalAmt().add(srcRow.getMinorityTotalAmt().setScale(2, 4)));
        }
        destRow.setLossGainAmt(destRow.getLossGainAmt().add(srcRow.getLossGainAmt().setScale(2, 4)));
    }

    public Integer getFractionDigits() {
        return this.fractionDigits;
    }

    public void setFractionDigits(Integer fractionDigits) {
        this.fractionDigits = fractionDigits;
    }
}

