/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LossGainOffsetVO {
    private List<GcOffSetVchrItemDTO> lastDeferredIncomeTaxResult = new ArrayList<GcOffSetVchrItemDTO>();
    private List<GcOffSetVchrItemDTO> currDeferredIncomeTaxResult = new ArrayList<GcOffSetVchrItemDTO>();
    private List<GcOffSetVchrItemDTO> lastMinorityRecoveryResult = new ArrayList<GcOffSetVchrItemDTO>();
    private List<GcOffSetVchrItemDTO> currMinorityRecoveryResult = new ArrayList<GcOffSetVchrItemDTO>();
    private List<GcOffSetVchrItemDTO> lastLossGainResult = new ArrayList<GcOffSetVchrItemDTO>();
    private List<GcOffSetVchrItemDTO> currLossGainResult = new ArrayList<GcOffSetVchrItemDTO>();
    private List<Map<String, Object>> currReclassifyResult = new ArrayList<Map<String, Object>>();
    private Boolean allowReclassify = false;
    private List<Map<String, String>> reclassifyDimension = new ArrayList<Map<String, String>>();
    private List<Map<String, Object>> currReduceReclassifyResult = new ArrayList<Map<String, Object>>();
    private Boolean allowReduceReclassify = false;
    private List<Map<String, String>> reduceReclassifyDimension = new ArrayList<Map<String, String>>();
    private List<DesignFieldDefineVO> otherShowColumns = new ArrayList<DesignFieldDefineVO>();

    public List<GcOffSetVchrItemDTO> currDeferredIncomeTaxResult() {
        return this.currDeferredIncomeTaxResult;
    }

    public List<Map<String, Object>> currDeferredIncomeTaxResultFields() {
        return this.convertDTO2Map(this.currDeferredIncomeTaxResult);
    }

    public List<GcOffSetVchrItemDTO> getCurrDeferredIncomeTaxResult() {
        return this.currDeferredIncomeTaxResult;
    }

    private List<Map<String, Object>> convertDTO2Map(List<GcOffSetVchrItemDTO> offSetVchrItemDTOList) {
        if (null == offSetVchrItemDTOList) {
            return null;
        }
        return offSetVchrItemDTOList.stream().map(AbstractFieldDynamicDeclarator::getFields).collect(Collectors.toList());
    }

    public void setCurrDeferredIncomeTaxResult(List<GcOffSetVchrItemDTO> currDeferredIncomeTaxResult) {
        this.currDeferredIncomeTaxResult = currDeferredIncomeTaxResult;
    }

    public List<GcOffSetVchrItemDTO> currMinorityRecoveryResult() {
        return this.currMinorityRecoveryResult;
    }

    public List<GcOffSetVchrItemDTO> getCurrMinorityRecoveryResult() {
        return this.currMinorityRecoveryResult;
    }

    public List<Map<String, Object>> currMinorityRecoveryResultFields() {
        return this.convertDTO2Map(this.currMinorityRecoveryResult);
    }

    public void setCurrMinorityRecoveryResult(List<GcOffSetVchrItemDTO> currMinorityRecoveryResult) {
        this.currMinorityRecoveryResult = currMinorityRecoveryResult;
    }

    public List<GcOffSetVchrItemDTO> currLossGainResult() {
        return this.currLossGainResult;
    }

    public List<GcOffSetVchrItemDTO> getCurrLossGainResult() {
        return this.currLossGainResult;
    }

    public List<Map<String, Object>> currLossGainResultFields() {
        return this.convertDTO2Map(this.currLossGainResult);
    }

    public void setCurrLossGainResult(List<GcOffSetVchrItemDTO> currLossGainResult) {
        this.currLossGainResult = currLossGainResult;
    }

    public List<DesignFieldDefineVO> getOtherShowColumns() {
        return this.otherShowColumns;
    }

    public void setOtherShowColumns(List<DesignFieldDefineVO> otherShowColumns) {
        this.otherShowColumns = otherShowColumns;
    }

    public List<Map<String, Object>> getCurrReclassifyResult() {
        return this.currReclassifyResult;
    }

    public void setCurrReclassifyResult(List<Map<String, Object>> currReclassifyResult) {
        this.currReclassifyResult = currReclassifyResult;
    }

    public List<Map<String, String>> getReclassifyDimension() {
        return this.reclassifyDimension;
    }

    public void setReclassifyDimension(List<Map<String, String>> reclassifyDimension) {
        this.reclassifyDimension = reclassifyDimension;
    }

    public void setAllowReclassify(Boolean allowReclassify) {
        this.allowReclassify = allowReclassify;
    }

    public Boolean getAllowReclassify() {
        return this.allowReclassify;
    }

    public List<Map<String, Object>> getCurrReduceReclassifyResult() {
        return this.currReduceReclassifyResult;
    }

    public void setCurrReduceReclassifyResult(List<Map<String, Object>> currReduceReclassifyResult) {
        this.currReduceReclassifyResult = currReduceReclassifyResult;
    }

    public Boolean getAllowReduceReclassify() {
        return this.allowReduceReclassify;
    }

    public void setAllowReduceReclassify(Boolean allowReduceReclassify) {
        this.allowReduceReclassify = allowReduceReclassify;
    }

    public List<Map<String, String>> getReduceReclassifyDimension() {
        return this.reduceReclassifyDimension;
    }

    public void setReduceReclassifyDimension(List<Map<String, String>> reduceReclassifyDimension) {
        this.reduceReclassifyDimension = reduceReclassifyDimension;
    }

    public List<GcOffSetVchrItemDTO> getLastDeferredIncomeTaxResult() {
        return this.lastDeferredIncomeTaxResult;
    }

    public void setLastDeferredIncomeTaxResult(List<GcOffSetVchrItemDTO> lastDeferredIncomeTaxResult) {
        this.lastDeferredIncomeTaxResult = lastDeferredIncomeTaxResult;
    }

    public List<GcOffSetVchrItemDTO> getLastMinorityRecoveryResult() {
        return this.lastMinorityRecoveryResult;
    }

    public void setLastMinorityRecoveryResult(List<GcOffSetVchrItemDTO> lastMinorityRecoveryResult) {
        this.lastMinorityRecoveryResult = lastMinorityRecoveryResult;
    }

    public List<GcOffSetVchrItemDTO> getLastLossGainResult() {
        return this.lastLossGainResult;
    }

    public void setLastLossGainResult(List<GcOffSetVchrItemDTO> lastLossGainResult) {
        this.lastLossGainResult = lastLossGainResult;
    }
}

