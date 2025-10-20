/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.common.BillExecCtxGen
 *  com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO
 *  com.jiuqi.gcreport.billcore.util.BillParseTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.vo.BillInfoVo
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.gcreport.lease.leasebill.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.common.BillExecCtxGen;
import com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.lease.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class LeaseRuleTraceProcessor
implements UnionRuleTraceProcessor {
    private GcOffSetVchrItemAdjustEO offsetItem;
    private LeaseRuleDTO rule;
    private GcTaskBaseArguments taskArg;
    private ExecutorContext context;
    private GcBillFormulaEvalService billFormulaEvalService;
    private GcCalcEnvContextImpl env;
    private DimensionValueSet dimensionValueSet;
    private GcBillGroupDTO gcBillGroupDTO;
    private BillInfoVo billInfoVo;

    public static LeaseRuleTraceProcessor newInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        LeaseRuleTraceProcessor processor = new LeaseRuleTraceProcessor(offsetItem, (LeaseRuleDTO)rule, taskArg);
        return processor;
    }

    public LeaseRuleTraceProcessor(GcOffSetVchrItemAdjustEO offsetItem, LeaseRuleDTO rule, GcTaskBaseArguments taskArg) {
        this.offsetItem = offsetItem;
        this.rule = rule;
        this.taskArg = taskArg;
        this.billFormulaEvalService = (GcBillFormulaEvalService)SpringBeanUtils.getBean(GcBillFormulaEvalService.class);
        this.dimensionValueSet = this.getDimensionValueSet();
        this.env = this.getEnv();
        this.selectGcBillGroup();
    }

    public List<FetchItemDTO> getFetchItem() {
        List itemList = this.offsetItem.getOrient() == 1 ? this.rule.getDebitItemList() : this.rule.getCreditItemList();
        String regex = "(" + this.billInfoVo.getMasterTableName() + ")\\[([a-zA-Z]+)\\]";
        return itemList.stream().filter(item -> item.getSubjectCode().equals(this.offsetItem.getSubjectCode()) || item.getSubjectCode().matches(regex)).map(item -> new FetchItemDTO(item.getFetchFormula(), item)).collect(Collectors.toList());
    }

    public GcOffSetVchrItemAdjustEO getOffSetItem() {
        return this.offsetItem;
    }

    public ExecutorContext getExecutorContext() {
        if (Objects.isNull(this.context)) {
            this.context = BillExecCtxGen.createExecutorContext((String)this.billInfoVo.getMasterTableName());
        }
        return this.context;
    }

    public AbstractData formulaEval(OffsetAmtTraceItemVO offsetAmtTraceItemVO) {
        return this.formulaEval(offsetAmtTraceItemVO.getExpression());
    }

    public AbstractData formulaEval(String formula) {
        if (Objects.isNull(this.billInfoVo) || Objects.isNull(this.gcBillGroupDTO)) {
            return AbstractData.valueOf((double)0.0);
        }
        return this.billFormulaEvalService.evaluateBillAbstractData((GcCalcEnvContext)this.env, this.dimensionValueSet, formula, this.gcBillGroupDTO, this.billInfoVo.getAllTableNames());
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

    private DimensionValueSet getDimensionValueSet() {
        DimensionValueSet dset = DimensionUtils.generateDimSet(null, (Object)this.offsetItem.getDefaultPeriod(), (Object)this.offsetItem.getOffSetCurr(), (Object)this.taskArg.getOrgType(), (String)this.taskArg.getSelectAdjustCode(), (String)this.offsetItem.getTaskId());
        return dset;
    }

    private void selectGcBillGroup() {
        String billDefineId = this.rule.getBillDefineId();
        if (StringUtils.isEmpty((String)billDefineId)) {
            this.billInfoVo = null;
            return;
        }
        this.billInfoVo = BillParseTool.parseBillInfo((String)billDefineId);
        String billColumnSql = SqlUtils.getColumnsSqlByTableDefine((String)this.billInfoVo.getMasterTableName(), (String)"i");
        String billSql = "select " + billColumnSql + " from " + this.billInfoVo.getMasterTableName() + " i where i.id=?";
        List bill = InvestBillTool.queryBySql((String)billSql, (Object[])new Object[]{this.offsetItem.getSrcOffsetGroupId()});
        if (CollectionUtils.isEmpty(bill)) {
            this.gcBillGroupDTO = null;
            return;
        }
        String billItemColumnSql = SqlUtils.getColumnsSqlByTableDefine((String)this.billInfoVo.getFirstSubTableName(), (String)"i");
        String billItemSql = "select " + billItemColumnSql + " from " + this.billInfoVo.getFirstSubTableName() + " i where i.masterid=?";
        List billItems = InvestBillTool.queryBySql((String)billItemSql, (Object[])new Object[]{this.offsetItem.getSrcOffsetGroupId()});
        this.gcBillGroupDTO = new GcBillGroupDTO((DefaultTableEntity)bill.get(0), billItems);
    }
}

