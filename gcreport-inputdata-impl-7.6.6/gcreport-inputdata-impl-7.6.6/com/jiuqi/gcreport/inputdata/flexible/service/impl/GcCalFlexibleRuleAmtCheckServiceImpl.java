/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.service.GcCalAmtCheckService
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 */
package com.jiuqi.gcreport.inputdata.flexible.service.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.service.GcCalAmtCheckService;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.formula.GcInputDataFormulaEvalService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GcCalFlexibleRuleAmtCheckServiceImpl
implements GcCalAmtCheckService {
    private static Logger logger = LoggerFactory.getLogger(GcCalFlexibleRuleAmtCheckServiceImpl.class);

    public BigDecimal customizeMatchOrient(AbstractUnionRule rule, Collection<GcOffSetVchrItemDTO> offsetItems, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum) {
        if (rule instanceof FlexibleRuleDTO && !CollectionUtils.isEmpty(offsetItems)) {
            FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)rule;
            String offsetFormula = flexibleRuleDTO.getOffsetFormula();
            List<GcOffSetVchrItemDTO> debitOffSetVchrItems = offsetItems.stream().filter(gcOffSetVchrItemDTO -> gcOffSetVchrItemDTO.getOrient().equals((Object)OrientEnum.D)).collect(Collectors.toList());
            List<GcOffSetVchrItemDTO> creditOffSetVchrItems = offsetItems.stream().filter(gcOffSetVchrItemDTO -> gcOffSetVchrItemDTO.getOrient().equals((Object)OrientEnum.C)).collect(Collectors.toList());
            OrientEnum debitOrient = this.getOrientByOffsetFormula(debitOffSetVchrItems, offsetFormula);
            OrientEnum creditOrient = this.getOrientByOffsetFormula(creditOffSetVchrItems, offsetFormula);
            if (debitOrient != null) {
                return debitUncheckedAmtSum;
            }
            if (creditOrient != null) {
                return creditUncheckedAmtSum;
            }
            return BigDecimal.ZERO;
        }
        throw new UnsupportedOperationException(GcI18nUtil.getMessage((String)"gc.calculate.amtcheck.notcustomoffsetformulamsg"));
    }

    private OrientEnum getOrientByOffsetFormula(List<GcOffSetVchrItemDTO> offsetItems, String formula) {
        if (CollectionUtils.isEmpty(offsetItems)) {
            return null;
        }
        GcInputDataFormulaEvalService inputDataFormulaEvalService = (GcInputDataFormulaEvalService)SpringContextUtils.getBean(GcInputDataFormulaEvalService.class);
        for (GcOffSetVchrItemDTO offSetVchrItem : offsetItems) {
            InputDataEO inputData = GcCalFlexibleRuleAmtCheckServiceImpl.convertOffsetVchrItemToInputData(offSetVchrItem);
            try {
                boolean checkOffsetFormula = inputDataFormulaEvalService.checkMxInputData(DimensionUtils.generateDimSet((Object)inputData.getOrgCode(), (Object)inputData.getPeriod(), (Object)inputData.getCurrency(), (Object)inputData.getOrgType(), (String)((String)inputData.getFieldValue("ADJUST")), (String)inputData.getTaskId()), formula, inputData);
                if (!checkOffsetFormula) continue;
                return offSetVchrItem.getOrient();
            }
            catch (Exception e) {
                logger.error("\u62b5\u6d88\u65b9\u5f0f\u4e3a\u81ea\u5b9a\u4e49\u65f6\u6307\u5b9a\u7684\u516c\u5f0f\u6267\u884c\u53d1\u751f\u5f02\u5e38", e);
                return null;
            }
        }
        return null;
    }

    private static InputDataEO convertOffsetVchrItemToInputData(GcOffSetVchrItemDTO offSetVchrItem) {
        InputDataEO inputData = new InputDataEO();
        BeanUtils.copyProperties(offSetVchrItem, (Object)inputData);
        HashMap fields = new HashMap();
        fields.putAll(offSetVchrItem.getFields());
        inputData.resetFields(fields);
        inputData.setMdOrg(offSetVchrItem.getUnitId());
        inputData.setOrgCode(offSetVchrItem.getUnitId());
        inputData.setOppUnitId(offSetVchrItem.getOppUnitId());
        inputData.setSubjectCode(offSetVchrItem.getSubjectCode());
        inputData.setTaskId(offSetVchrItem.getTaskId());
        inputData.setCurrency(offSetVchrItem.getOffSetCurr());
        inputData.setOrgType(offSetVchrItem.getOrgType());
        inputData.setDc(offSetVchrItem.getOrient().getValue());
        inputData.setPeriod(offSetVchrItem.getDefaultPeriod());
        inputData.addFieldValue("MD_GCADJTYPE", GCAdjTypeEnum.BEFOREADJ.getCode());
        inputData.setReportSystemId(offSetVchrItem.getSystemId());
        Double amt = OrientEnum.D.equals((Object)offSetVchrItem.getOrient()) ? offSetVchrItem.getDebit() : offSetVchrItem.getCredit();
        inputData.setAmt(amt);
        return inputData;
    }
}

