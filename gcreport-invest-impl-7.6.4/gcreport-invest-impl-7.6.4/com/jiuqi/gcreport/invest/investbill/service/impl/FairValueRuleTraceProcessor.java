/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.common.BillExecCtxGen
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.gcreport.invest.investbill.service.impl;

import com.jiuqi.gcreport.billcore.common.BillExecCtxGen;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FairValueRuleTraceProcessor
implements UnionRuleTraceProcessor {
    private GcOffSetVchrItemAdjustEO offsetItem;
    private PublicValueAdjustmentRuleDTO rule;
    private GcTaskBaseArguments taskArg;
    private ExecutorContext context;
    private GcBillFormulaEvalService billFormulaEvalService;
    private DimensionValueSet dimensionValueSet;
    private DefaultTableEntity fvchItemBill;
    private GcCalcEnvContextImpl env;

    public static FairValueRuleTraceProcessor newInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        FairValueRuleTraceProcessor processor = new FairValueRuleTraceProcessor(offsetItem, rule, taskArg);
        return processor;
    }

    public FairValueRuleTraceProcessor(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        this.offsetItem = offsetItem;
        this.rule = (PublicValueAdjustmentRuleDTO)rule;
        this.taskArg = taskArg;
        this.billFormulaEvalService = (GcBillFormulaEvalService)SpringBeanUtils.getBean(GcBillFormulaEvalService.class);
        this.dimensionValueSet = this.getDimensionValueSet();
        this.fvchItemBill = this.getFvchItemData();
        this.env = this.getEnv();
    }

    public List<FetchItemDTO> getFetchItem() {
        List itemList = this.offsetItem.getOrient() == 1 ? this.rule.getDebitItemList() : this.rule.getCreditItemList();
        return itemList.stream().filter(item -> item.getSubjectCode().equals(this.offsetItem.getSubjectCode())).map(item -> new FetchItemDTO(item.getFetchFormula(), item)).collect(Collectors.toList());
    }

    public ExecutorContext getExecutorContext() {
        if (Objects.isNull(this.context)) {
            this.context = BillExecCtxGen.createExecutorContext((String)"GC_FVCHBILL");
        }
        return this.context;
    }

    public AbstractData formulaEval(String formula) {
        return this.billFormulaEvalService.evaluateFvchBillAbstractData((GcCalcEnvContext)this.env, this.dimensionValueSet, formula, this.fvchItemBill);
    }

    public AbstractData formulaEval(OffsetAmtTraceItemVO offsetAmtTraceItemVO) {
        return this.formulaEval(offsetAmtTraceItemVO.getExpression());
    }

    public GcOffSetVchrItemAdjustEO getOffSetItem() {
        return this.offsetItem;
    }

    private DimensionValueSet getDimensionValueSet() {
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)this.offsetItem.getUnitId(), (Object)this.offsetItem.getDefaultPeriod(), (Object)this.offsetItem.getOffSetCurr(), (Object)this.taskArg.getOrgType(), (String)this.taskArg.getSelectAdjustCode(), (String)this.offsetItem.getTaskId());
        return dset;
    }

    private GcCalcEnvContextImpl getEnv() {
        GcCalcArgmentsDTO calcArgmentsDTO = new GcCalcArgmentsDTO();
        calcArgmentsDTO.setPeriodStr(this.offsetItem.getDefaultPeriod());
        calcArgmentsDTO.setTaskId(this.offsetItem.getTaskId());
        calcArgmentsDTO.setCurrency(this.offsetItem.getOffSetCurr());
        calcArgmentsDTO.setOrgType(this.taskArg.getOrgType());
        calcArgmentsDTO.setSelectAdjustCode(this.taskArg.getSelectAdjustCode());
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = ((IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class)).querySchemePeriodLinkByPeriodAndTask(this.offsetItem.getDefaultPeriod(), this.offsetItem.getTaskId());
            calcArgmentsDTO.setSchemeId(schemePeriodLinkDefine.getSchemeKey());
        }
        catch (Exception e) {
            throw new RuntimeException("\u6839\u636e\u4efb\u52a1\u548c\u65f6\u671f\u4e3a\u627e\u5230\u62a5\u8868\u65b9\u6848", e);
        }
        YearPeriodObject yp = new YearPeriodObject(null, this.offsetItem.getDefaultPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)this.taskArg.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO commonUnit = tool.getCommonUnit(tool.getOrgByCode(this.offsetItem.getUnitId()), tool.getOrgByCode(this.offsetItem.getOppUnitId()));
        calcArgmentsDTO.setOrgId(commonUnit.getId());
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl();
        env.setCalcArgments(calcArgmentsDTO);
        return env;
    }

    private DefaultTableEntity getFvchItemData() {
        String srcOffsetGroupId = this.offsetItem.getSrcOffsetGroupId();
        DefaultTableEntity fvchItemBill = InvestBillTool.getEntityById((String)srcOffsetGroupId, (String)"GC_FVCH_FIXEDITEM");
        if (null != fvchItemBill) {
            fvchItemBill.getFields().put("fvchItemType", "GC_FVCH_FIXEDITEM");
        } else {
            fvchItemBill = InvestBillTool.getEntityById((String)srcOffsetGroupId, (String)"GC_FVCH_OTHERITEM");
            fvchItemBill.getFields().put("fvchItemType", "GC_FVCH_OTHERITEM");
        }
        return fvchItemBill;
    }
}

