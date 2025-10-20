/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 */
package com.jiuqi.gcreport.offsetitem.vo.sumtab;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import java.util.Map;

public class SumTabResultVo
extends AbstractFieldDynamicDeclarator {
    private String secondSub;
    private String gcBusinessTypeCode;
    private Double offsetCreditValue;
    private Double offsetDebitValue;
    private Double offsetDiffValue;
    private Double unOffsetCreditValue;
    private Double unOffsetDebitValue;

    @Deprecated
    public SumTabResultVo copyFromOffset(Map<String, Object> record) {
        this.setSecondSub((String)record.get("SECONDSUB"));
        this.setGcBusinessTypeCode((String)record.get("GCBUSINESSTYPECODE"));
        this.setOffsetDebitValue(ConverterUtils.getAsDouble((Object)record.get("DEBITVALUE")));
        this.setOffsetCreditValue(ConverterUtils.getAsDouble((Object)record.get("CREDITVALUE")));
        this.setOffsetDiffValue(ConverterUtils.getAsDouble((Object)record.get("DIFFVALUE")));
        this.setUnOffsetDebitValue(0.0);
        this.setUnOffsetCreditValue(0.0);
        return this;
    }

    @Deprecated
    public SumTabResultVo copyFromUnOffset(Map<String, Object> record) {
        this.setSecondSub((String)record.get("SECONDSUB"));
        this.setGcBusinessTypeCode((String)record.get("GCBUSINESSTYPECODE"));
        this.setOffsetDebitValue(0.0);
        this.setOffsetCreditValue(0.0);
        this.setUnOffsetDebitValue(ConverterUtils.getAsDouble((Object)record.get("DEBITVALUE")));
        this.setUnOffsetCreditValue(ConverterUtils.getAsDouble((Object)record.get("CREDITVALUE")));
        return this;
    }

    @Deprecated
    public Map<String, Object> convert2Map() {
        return this.getFields();
    }

    public SumTabResultVo add(SumTabResultVo sumTabResultVo) {
        if (null == sumTabResultVo) {
            return this;
        }
        this.setOffsetDebitValue(NumberUtils.sum((Double)this.offsetDebitValue, (Double)sumTabResultVo.getOffsetDebitValue()));
        this.setOffsetCreditValue(NumberUtils.sum((Double)this.offsetCreditValue, (Double)sumTabResultVo.getOffsetCreditValue()));
        this.setOffsetDiffValue(NumberUtils.sum((Double)this.offsetDiffValue, (Double)sumTabResultVo.getOffsetDiffValue()));
        this.setUnOffsetDebitValue(NumberUtils.sum((Double)this.unOffsetDebitValue, (Double)sumTabResultVo.getUnOffsetDebitValue()));
        this.setUnOffsetCreditValue(NumberUtils.sum((Double)this.unOffsetCreditValue, (Double)sumTabResultVo.getUnOffsetCreditValue()));
        return this;
    }

    public String getSecondSub() {
        return this.secondSub;
    }

    private void setSecondSub(String secondSub) {
        this.secondSub = secondSub;
        this.addFieldValue("SECONDSUB", secondSub);
    }

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    private void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.gcBusinessTypeCode = gcBusinessTypeCode;
        this.addFieldValue("GCBUSINESSTYPECODE", gcBusinessTypeCode);
    }

    public Double getOffsetCreditValue() {
        return this.offsetCreditValue;
    }

    private void setOffsetCreditValue(Double offsetCreditValue) {
        this.offsetCreditValue = offsetCreditValue;
        this.addFieldValue("OFFSETCREDITVALUE", offsetCreditValue);
    }

    public Double getOffsetDebitValue() {
        return this.offsetDebitValue;
    }

    private void setOffsetDebitValue(Double offsetDebitValue) {
        this.offsetDebitValue = offsetDebitValue;
        this.addFieldValue("OFFSETDEBITVALUE", offsetDebitValue);
    }

    public Double getOffsetDiffValue() {
        return this.offsetDiffValue;
    }

    private void setOffsetDiffValue(Double offsetDiffValue) {
        this.offsetDiffValue = offsetDiffValue;
        this.addFieldValue("OFFSETDIFFVALUE", offsetDiffValue);
    }

    public Double getUnOffsetCreditValue() {
        return this.unOffsetCreditValue;
    }

    private void setUnOffsetCreditValue(Double unOffsetCreditValue) {
        this.unOffsetCreditValue = unOffsetCreditValue;
        this.addFieldValue("UNOFFSETCREDITVALUE", unOffsetCreditValue);
    }

    public Double getUnOffsetDebitValue() {
        return this.unOffsetDebitValue;
    }

    private void setUnOffsetDebitValue(Double unOffsetDebitValue) {
        this.unOffsetDebitValue = unOffsetDebitValue;
        this.addFieldValue("UNOFFSETDEBITVALUE", unOffsetDebitValue);
    }
}

