/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.common.BillExecCtxGen
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.FixedAssetsTypeEnum
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.asset.assetbill.dto.GcAssetBillGroupDTO;
import com.jiuqi.gcreport.asset.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.billcore.common.BillExecCtxGen;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FixedAssetsTypeEnum;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AssestRuleTraceProcessor
implements UnionRuleTraceProcessor {
    private GcOffSetVchrItemAdjustEO offsetItem;
    private FixedAssetsRuleDTO rule;
    private GcTaskBaseArguments taskArg;
    private GcBillFormulaEvalService billFormulaEvalService;
    private DimensionValueSet dimensionValueSet;
    private GcAssetBillGroupDTO assetBillGroup;
    private GcCalcEnvContext calcEnvContext;
    private ExecutorContext context;

    public static AssestRuleTraceProcessor newInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        return new AssestRuleTraceProcessor(offsetItem, rule, taskArg);
    }

    public AssestRuleTraceProcessor(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        this.offsetItem = offsetItem;
        this.rule = (FixedAssetsRuleDTO)rule;
        this.taskArg = taskArg;
        this.billFormulaEvalService = (GcBillFormulaEvalService)SpringBeanUtils.getBean(GcBillFormulaEvalService.class);
        this.dimensionValueSet = this.getDimensionValueSet();
        this.assetBillGroup = this.getGcAssetBillGroupDTO();
        this.calcEnvContext = this.getEnv();
    }

    public List<FetchItemDTO> getFetchItem() {
        List itemList = this.offsetItem.getOrient() == 1 ? this.rule.getDebitItemList() : this.rule.getCreditItemList();
        return itemList.stream().filter(item -> {
            String subjectCode = this.getSubjectCode((FixedAssetsRuleDTO.Item)item, this.calcEnvContext, this.dimensionValueSet, this.assetBillGroup);
            return StringUtils.isEmpty((String)subjectCode) ? false : subjectCode.equals(this.offsetItem.getSubjectCode());
        }).map(item -> new FetchItemDTO(item.getFetchFormula(), item)).collect(Collectors.toList());
    }

    public GcOffSetVchrItemAdjustEO getOffSetItem() {
        return this.offsetItem;
    }

    public ExecutorContext getExecutorContext() {
        if (Objects.isNull(this.context)) {
            this.context = BillExecCtxGen.createExecutorContext((String)"GC_COMMONASSETBILL");
        }
        return this.context;
    }

    public AbstractData formulaEval(String formula) {
        if (Objects.isNull((Object)this.assetBillGroup)) {
            return AbstractData.valueOf((double)0.0);
        }
        return this.billFormulaEvalService.getAssetBillData(this.calcEnvContext, this.dimensionValueSet, formula, this.assetBillGroup);
    }

    private DimensionValueSet getDimensionValueSet() {
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)this.offsetItem.getUnitId(), (Object)this.offsetItem.getDefaultPeriod(), (Object)this.offsetItem.getOffSetCurr(), (Object)this.taskArg.getOrgType(), (String)this.taskArg.getSelectAdjustCode(), (String)this.offsetItem.getTaskId());
        return dset;
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

    private GcAssetBillGroupDTO getGcAssetBillGroupDTO() {
        String billColumnSql = SqlUtils.getColumnsSqlByTableDefine((String)"GC_COMMONASSETBILL", (String)"i");
        String billSql = "select " + billColumnSql + " from " + "GC_COMMONASSETBILL" + " i where i.id=?";
        List bill = InvestBillTool.queryBySql((String)billSql, (Object[])new Object[]{this.offsetItem.getSrcOffsetGroupId()});
        if (org.springframework.util.CollectionUtils.isEmpty(bill)) {
            return null;
        }
        String billItemColumnSql = SqlUtils.getColumnsSqlByTableDefine((String)"GC_COMMONASSETBILLITEM", (String)"i");
        String billItemSql = "select " + billItemColumnSql + " from " + "GC_COMMONASSETBILLITEM" + " i where i.masterid=?";
        List billItems = InvestBillTool.queryBySql((String)billItemSql, (Object[])new Object[]{this.offsetItem.getSrcOffsetGroupId()});
        GcAssetBillGroupDTO assetBillGroup = new GcAssetBillGroupDTO((DefaultTableEntity)bill.get(0), billItems);
        return assetBillGroup;
    }

    private String getSubjectCode(FixedAssetsRuleDTO.Item item, GcCalcEnvContext env, DimensionValueSet dset, GcAssetBillGroupDTO acceptData) {
        String subjectCode = null;
        FixedAssetsTypeEnum type = item.getType();
        if (type == null) {
            return null;
        }
        switch (type) {
            case DEPRECIATION_SUBJECT: {
                subjectCode = StringUtils.toViewString((Object)acceptData.getMaster().getFieldValue("DPCASUBJECT"));
                break;
            }
            case DISPOSE_SUBJECT: {
                subjectCode = StringUtils.toViewString((Object)acceptData.getMaster().getFieldValue("DSPESUBJECTCODE"));
                break;
            }
            case ASSET_SUBJECT: {
                subjectCode = StringUtils.toViewString((Object)acceptData.getMaster().getFieldValue("ASSETTYPE"));
                if (!StringUtils.isEmpty((String)subjectCode) || CollectionUtils.isEmpty((Collection)acceptData.getItems())) break;
                subjectCode = StringUtils.toViewString((Object)((DefaultTableEntity)acceptData.getItems().get(0)).getFieldValue("ASSETTYPE"));
                break;
            }
            case FORMULA: {
                if (StringUtils.isEmpty((String)item.getSubjectCode())) break;
                subjectCode = this.billFormulaEvalService.evaluateAssetBillDataGetSubject(env, dset, item.getSubjectCode(), acceptData);
                break;
            }
            case CUSTOMIZE: {
                subjectCode = item.getSubjectCode();
            }
        }
        return subjectCode;
    }
}

