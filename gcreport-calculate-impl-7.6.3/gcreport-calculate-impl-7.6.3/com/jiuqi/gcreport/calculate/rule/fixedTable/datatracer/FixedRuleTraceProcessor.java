/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO$Item
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.calculate.rule.fixedTable.datatracer;

import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.formula.service.GcFormulaEvalService;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.calculate.rule.fixedTable.FixedTableRuleExecutorImpl;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FixedRuleTraceProcessor
implements UnionRuleTraceProcessor {
    private GcOffSetVchrItemAdjustEO offsetItem;
    private FixedTableRuleDTO rule;
    private GcTaskBaseArguments taskArg;
    private GcFormulaEvalService formulaEvalService;
    private DimensionValueSet fetchItemDimensionValueSet;
    private GcCalcEnvContext calcEnvContext;
    private ExecutorContext context;

    public static FixedRuleTraceProcessor newInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        return new FixedRuleTraceProcessor(offsetItem, rule, taskArg);
    }

    public FixedRuleTraceProcessor(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        this.offsetItem = offsetItem;
        this.rule = (FixedTableRuleDTO)rule;
        this.taskArg = taskArg;
        this.formulaEvalService = (GcFormulaEvalService)SpringBeanUtils.getBean(GcFormulaEvalService.class);
        this.calcEnvContext = this.getEnv();
    }

    public void initFetchItem(FetchItemDTO item) {
        YearPeriodObject yp = new YearPeriodObject(null, this.taskArg.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)this.taskArg.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        FixedTableRuleDTO.Item fixedTableItem = (FixedTableRuleDTO.Item)item.getFetchItem();
        GcOrgCacheVO fetchUnitOrg = tool.getOrgByCode(fixedTableItem.getFetchUnit());
        String orgTypeId = fetchUnitOrg.getOrgTypeId();
        this.fetchItemDimensionValueSet = DimensionUtils.generateDimSet((Object)fixedTableItem.getFetchUnit(), (Object)this.offsetItem.getDefaultPeriod(), (Object)this.offsetItem.getOffSetCurr(), (Object)orgTypeId, (String)this.taskArg.getSelectAdjustCode(), (String)this.offsetItem.getTaskId());
    }

    public List<FetchItemDTO> getFetchItem() {
        YearPeriodObject yp = new YearPeriodObject(null, this.taskArg.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)this.taskArg.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        HashMap<String, String> unitCode2TitleMap = new HashMap<String, String>();
        FixedTableRuleExecutorImpl fixedTableRuleExecutor = new FixedTableRuleExecutorImpl();
        fixedTableRuleExecutor.setProcessEnv(this.calcEnvContext);
        List<FixedTableRuleDTO.Item> itemList = this.offsetItem.getOrient() == 1 ? fixedTableRuleExecutor.transRuleList(this.rule.getDebitItemList(), tool, unitCode2TitleMap) : fixedTableRuleExecutor.transRuleList(this.rule.getCreditItemList(), tool, unitCode2TitleMap);
        return itemList.stream().filter(item -> StringUtils.isEmpty((String)item.getSubjectCode()) ? false : item.getSubjectCode().equals(this.offsetItem.getSubjectCode())).map(item -> new FetchItemDTO(item.getFetchFormula(), item)).collect(Collectors.toList());
    }

    public GcOffSetVchrItemAdjustEO getOffSetItem() {
        return this.offsetItem;
    }

    public ExecutorContext getExecutorContext() {
        if (Objects.isNull(this.context)) {
            try {
                IDataDefinitionRuntimeController runtimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
                GcReportExceutorContext context = new GcReportExceutorContext(runtimeController);
                context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
                IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
                IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, runtimeController, entityViewRunTimeController, this.calcEnvContext.getCalcArgments().getSchemeId());
                context.setEnv((IFmlExecEnvironment)environment);
                this.context = context;
            }
            catch (ParseException e) {
                throw new BusinessRuntimeException("\u516c\u5f0f\u6267\u884c\u73af\u5883\u51c6\u5907\u51fa\u9519\u3002", (Throwable)e);
            }
        }
        return this.context;
    }

    public AbstractData formulaEval(String formula) {
        if (Objects.isNull(formula)) {
            return AbstractData.valueOf((double)0.0);
        }
        AbstractData v = this.formulaEvalService.evaluateUnitDataFormula(this.fetchItemDimensionValueSet, formula, this.calcEnvContext.getCalcArgments().getSchemeId());
        return v;
    }

    private GcCalcEnvContextImpl getEnv() {
        GcCalcArgmentsDTO calcArg = new GcCalcArgmentsDTO();
        calcArg.setPeriodStr(this.offsetItem.getDefaultPeriod());
        calcArg.setTaskId(this.offsetItem.getTaskId());
        calcArg.setCurrency(this.offsetItem.getOffSetCurr());
        calcArg.setOrgType(this.taskArg.getOrgType());
        calcArg.setSelectAdjustCode(this.taskArg.getSelectAdjustCode());
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = ((IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class)).querySchemePeriodLinkByPeriodAndTask(this.offsetItem.getDefaultPeriod(), this.offsetItem.getTaskId());
            calcArg.setSchemeId(schemePeriodLinkDefine.getSchemeKey());
        }
        catch (Exception e) {
            throw new RuntimeException("\u6839\u636e\u4efb\u52a1\u548c\u65f6\u671f\u4e3a\u627e\u5230\u62a5\u8868\u65b9\u6848", e);
        }
        YearPeriodObject yp = new YearPeriodObject(null, this.offsetItem.getDefaultPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)this.taskArg.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO commonUnit = tool.getCommonUnit(tool.getOrgByCode(this.offsetItem.getUnitId()), tool.getOrgByCode(this.offsetItem.getOppUnitId()));
        calcArg.setOrgId(commonUnit.getId());
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl();
        env.setCalcArgments(calcArg);
        return env;
    }
}

