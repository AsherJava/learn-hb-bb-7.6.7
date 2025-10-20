/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.billcore.common.BillExecCtxGen
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.util.PeriodUtils
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetExpressionExtendInfoVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.DirectInvestmentDTO
 *  com.jiuqi.gcreport.unionrule.dto.InDirectInvestmentDTO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.gcreport.invest.investbill.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.billcore.common.BillExecCtxGen;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.util.PeriodUtils;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetExpressionExtendInfoVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.DirectInvestmentDTO;
import com.jiuqi.gcreport.unionrule.dto.InDirectInvestmentDTO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class InvestRuleTraceProcessor
implements UnionRuleTraceProcessor {
    private GcOffSetVchrItemAdjustEO offsetItem;
    private AbstractUnionRule rule;
    private GcTaskBaseArguments taskArg;
    private ExecutorContext context;
    private GcBillFormulaEvalService billFormulaEvalService;
    private DimensionValueSet dimensionValueSet;
    private GcInvestBillGroupDTO investBillGroupDTO;
    private GcCalcEnvContextImpl env;

    public static InvestRuleTraceProcessor newInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        InvestRuleTraceProcessor processor = new InvestRuleTraceProcessor(offsetItem, rule, taskArg);
        return processor;
    }

    public InvestRuleTraceProcessor(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        this.offsetItem = offsetItem;
        this.rule = rule;
        this.taskArg = taskArg;
        this.billFormulaEvalService = (GcBillFormulaEvalService)SpringBeanUtils.getBean(GcBillFormulaEvalService.class);
        this.dimensionValueSet = this.getDimensionValueSet();
        int acctPeriod = null != taskArg.getPeriodType() ? PeriodUtils.getMonth((int)taskArg.getAcctYear(), (int)taskArg.getPeriodType(), (int)taskArg.getAcctPeriod()) : taskArg.getAcctPeriod();
        this.investBillGroupDTO = this.getGcInvestBillGroupDTO(acctPeriod);
        this.env = this.getEnv();
    }

    public List<FetchItemDTO> getFetchItem() {
        List itemList;
        if (this.rule instanceof DirectInvestmentDTO) {
            DirectInvestmentDTO directInvestmentDTO = (DirectInvestmentDTO)this.rule;
            itemList = this.offsetItem.getOrient() == 1 ? directInvestmentDTO.getDebitItemList() : directInvestmentDTO.getCreditItemList();
        } else {
            InDirectInvestmentDTO indirectInvestmentDTO = (InDirectInvestmentDTO)this.rule;
            itemList = this.offsetItem.getOrient() == 1 ? indirectInvestmentDTO.getDebitItemList() : indirectInvestmentDTO.getCreditItemList();
        }
        return itemList.stream().filter(item -> item.getSubjectCode().equals(this.offsetItem.getSubjectCode())).map(item -> new FetchItemDTO(item.getFetchFormula(), item)).collect(Collectors.toList());
    }

    public GcOffSetVchrItemAdjustEO getOffSetItem() {
        return this.offsetItem;
    }

    public ExecutorContext getExecutorContext() {
        if (Objects.isNull(this.context)) {
            this.context = BillExecCtxGen.createExecutorContext((String)"GC_INVESTBILL");
        }
        return this.context;
    }

    public AbstractData formulaEval(String formula) {
        return this.billFormulaEvalService.getInvestBillData((GcCalcEnvContext)this.env, this.dimensionValueSet, formula, this.investBillGroupDTO);
    }

    public AbstractData formulaEval(OffsetAmtTraceItemVO offsetAmtTraceItemVO) {
        String formula = offsetAmtTraceItemVO.getExpression();
        if (formula.startsWith("SEquity")) {
            this.dimensionValueSet.setValue("sequityPenetrateFlag", (Object)true);
            AbstractData abstactData = this.billFormulaEvalService.getInvestBillData((GcCalcEnvContext)this.env, this.dimensionValueSet, formula, this.investBillGroupDTO);
            Map data = (Map)abstactData.getAsObject();
            offsetAmtTraceItemVO.setValue((Object)NumberUtils.doubleToString((double)ConverterUtils.getAsDoubleValue(data.get("amt")), (int)10, (int)2, (boolean)true));
            this.setExpressionExtendInfo(offsetAmtTraceItemVO, data);
            return abstactData;
        }
        return this.formulaEval(offsetAmtTraceItemVO.getExpression());
    }

    private void setExpressionExtendInfo(OffsetAmtTraceItemVO offsetAmtTraceItemVO, Map<String, Object> data) {
        OffsetExpressionExtendInfoVO offsetExpressionExtendInfo = new OffsetExpressionExtendInfoVO();
        LinkedHashMap<String, String> filedMap = new LinkedHashMap<String, String>();
        filedMap.put("month", "\u6708\u4efd");
        filedMap.put("zbValue", data.get("zbTitle") == null ? "\u6307\u6807\u503c" : data.get("zbTitle").toString());
        filedMap.put("equityRatio", "\u80a1\u6743\u6bd4\u4f8b");
        filedMap.put("calcProcess", "\u8ba1\u7b97\u8fc7\u7a0b");
        filedMap.put("calcAmt", "\u8ba1\u7b97\u91d1\u989d");
        ArrayList tableColumns = new ArrayList();
        filedMap.forEach((key, val) -> {
            DesignFieldDefineVO designFieldDefineVO = new DesignFieldDefineVO();
            designFieldDefineVO.setKey(key);
            designFieldDefineVO.setLabel(val);
            tableColumns.add(designFieldDefineVO);
        });
        offsetExpressionExtendInfo.setTableColumns(tableColumns);
        offsetExpressionExtendInfo.setTableDatas((List)data.get("calcProcessDatas"));
        offsetExpressionExtendInfo.setCalcTitle("\u5206\u6bb5\u8ba1\u7b97\u8fc7\u7a0b:");
        offsetExpressionExtendInfo.setCalcProcessDes((String)data.get("calcProcessDes"));
        offsetAmtTraceItemVO.setExpressionExtendInfoShow(true);
        offsetAmtTraceItemVO.setOffsetExpressionExtendInfo(offsetExpressionExtendInfo);
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

    private GcInvestBillGroupDTO getGcInvestBillGroupDTO(int acctPeriod) {
        InvestBillDao investBillDao = (InvestBillDao)SpringBeanUtils.getBean(InvestBillDao.class);
        String[] columnNamesInDB = new String[]{"SRCID", "PERIOD"};
        Object[] values = new Object[]{this.offsetItem.getSrcOffsetGroupId(), acctPeriod};
        List<Map<String, Object>> investBillData = investBillDao.listByWhere(columnNamesInDB, values);
        DefaultTableEntity master = new DefaultTableEntity();
        if (CollectionUtils.isEmpty(investBillData)) {
            throw new BusinessRuntimeException("\u7cfb\u7edf\u672a\u627e\u5230\u5bf9\u5e94\u7684\u539f\u59cb\u6570\u636e, \u65e0\u6cd5\u7a7f\u900f\u3002");
        }
        master.resetFields(investBillData.get(0));
        List<DefaultTableEntity> investbillItemDatas = investBillDao.getInvestmentItemsByMastId((String)investBillData.get(0).get("ID"));
        return new GcInvestBillGroupDTO(master, investbillItemDatas, true);
    }
}

