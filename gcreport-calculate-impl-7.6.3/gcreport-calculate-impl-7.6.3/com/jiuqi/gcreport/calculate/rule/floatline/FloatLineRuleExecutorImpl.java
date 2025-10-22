/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.FetchUnitEnum
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.gcreport.calculate.rule.floatline;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.formula.service.GcFormulaEvalService;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FetchUnitEnum;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatLineRuleExecutorImpl {
    private FloatLineRuleDTO floatLineRuleDTO;
    private GcCalcArgmentsDTO calcArgmentCondition;
    private FormDefine formDefine;
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    private IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
    private IDataAccessProvider provider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
    private GcCalcService gcCalcService = (GcCalcService)SpringContextUtils.getBean(GcCalcService.class);
    private GcFormulaEvalService formulaEvalService = (GcFormulaEvalService)SpringContextUtils.getBean(GcFormulaEvalService.class);
    private Logger logger = LoggerFactory.getLogger(FloatLineRuleExecutorImpl.class);

    public void calMerge(AbstractUnionRule rule, GcCalcEnvContext env) {
        ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(0));
        if (!(rule instanceof FloatLineRuleDTO) || Objects.isNull(rule)) {
            return;
        }
        this.floatLineRuleDTO = (FloatLineRuleDTO)rule;
        this.calcArgmentCondition = env.getCalcArgments();
        if (!FetchUnitEnum.UNION.getCode().equals(this.floatLineRuleDTO.getFetchUnit())) {
            return;
        }
        if (CollectionUtils.isEmpty((Collection)this.floatLineRuleDTO.getDebitItemList()) || CollectionUtils.isEmpty((Collection)this.floatLineRuleDTO.getCreditItemList())) {
            return;
        }
        DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(this.floatLineRuleDTO.getFloatLineDataRegion());
        this.formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
        GcOffSetAppOffsetService gcOffSetAppOffsetService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
        this.gcCalcService.deleteAutoOffsetEntrysByRule(this.floatLineRuleDTO.getId(), this.calcArgmentCondition);
        IDataTable dataTable = this.queryFloatLineData();
        String debitUnit = "";
        String creditUnit = "";
        if (StringUtils.isEmpty((String)((FloatLineRuleDTO.Item)this.floatLineRuleDTO.getDebitItemList().get(0)).getUnit())) {
            throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u501f\u65b9\u7b2c1\u884c\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        debitUnit = ((FloatLineRuleDTO.Item)this.floatLineRuleDTO.getDebitItemList().get(0)).getUnit();
        if (StringUtils.isEmpty((String)((FloatLineRuleDTO.Item)this.floatLineRuleDTO.getCreditItemList().get(0)).getUnit())) {
            throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u8d37\u65b9\u7b2c1\u884c\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        creditUnit = ((FloatLineRuleDTO.Item)this.floatLineRuleDTO.getCreditItemList().get(0)).getUnit();
        int count = dataTable.getCount();
        ArrayList<String> warningMsgs = new ArrayList<String>();
        for (int i = 0; i < count; ++i) {
            IDataRow dataRow = dataTable.getItem(i);
            if (!this.checkRuleCondition(this.floatLineRuleDTO.getRuleCondition(), dataRow)) {
                this.logger.info("\u6570\u636e\u4e0d\u6ee1\u8db3\u6d6e\u52a8\u884c\u89c4\u5219\uff1a" + this.floatLineRuleDTO.getLocalizedName() + "\u7684\u9002\u7528\u6761\u4ef6\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55, \u6570\u636e\u884c\uff1a" + dataRow.toString());
                continue;
            }
            if (Objects.isNull(dataRow)) continue;
            String mrecid = UUIDOrderUtils.newUUIDStr();
            ArrayList<GcOffSetVchrItemDTO> gcOffSetVchrItems = new ArrayList<GcOffSetVchrItemDTO>();
            List<GcOffSetVchrItemDTO> debitOffSetVchrItems = this.handleRuleDebitAndCreditItem(this.floatLineRuleDTO.getDebitItemList(), dataRow, mrecid, debitUnit, creditUnit, OrientEnum.D, warningMsgs, i);
            List<GcOffSetVchrItemDTO> creditOffSetVchrItems = this.handleRuleDebitAndCreditItem(this.floatLineRuleDTO.getCreditItemList(), dataRow, mrecid, creditUnit, debitUnit, OrientEnum.C, warningMsgs, i);
            if (CollectionUtils.isEmpty(debitOffSetVchrItems) || CollectionUtils.isEmpty(creditOffSetVchrItems)) continue;
            gcOffSetVchrItems.addAll(debitOffSetVchrItems);
            gcOffSetVchrItems.addAll(creditOffSetVchrItems);
            if (!GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)this.floatLineRuleDTO, gcOffSetVchrItems)) continue;
            GcOffSetAppOffsetService adjustService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
            GcOffSetVchrDTO offSetItemDTO = new GcOffSetVchrDTO();
            offSetItemDTO.setItems(gcOffSetVchrItems);
            adjustService.save(offSetItemDTO);
            ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(gcOffSetVchrItems.size()));
            if (!env.getCalcArgments().getPreCalcFlag().get()) continue;
            env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(gcOffSetVchrItems);
        }
        if (!CollectionUtils.isEmpty(warningMsgs)) {
            warningMsgs.stream().forEach(msg -> env.addResultItem("\u6267\u884c\u8b66\u544a:" + msg));
        }
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean checkRuleCondition(String formula, IDataRow dataRow) {
        if (StringUtils.isEmpty((String)formula)) {
            return true;
        }
        DefaultTableEntity defaultTableEntity = new DefaultTableEntity();
        IFieldsInfo fieldsInfo = dataRow.getFieldsInfo();
        for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
            FieldDefine fieldDefine = fieldsInfo.getFieldDefine(i);
            defaultTableEntity.addFieldValue(fieldDefine.getCode(), dataRow.getValue(fieldDefine).getAsObject());
        }
        String tableCode = "";
        try {
            String ownerTableKey = dataRow.getFieldsInfo().getFieldDefine(0).getOwnerTableKey();
            tableCode = this.iDataDefinitionRuntimeController.queryTableDefine(ownerTableKey).getCode();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u6d6e\u52a8\u884c\u6307\u6807\u5bf9\u5e94\u7269\u7406\u8868\u65f6\u51fa\u73b0\u5f02\u5e38", (Throwable)e);
        }
        GcReportDataSet dataSet = new GcReportDataSet(tableCode);
        String regionKey = this.floatLineRuleDTO.getFloatLineDataRegion();
        RegionData region = ((IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class)).getRegion(regionKey);
        if (Objects.isNull(region)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u6d6e\u52a8\u884c\u533a\u57df\uff1a" + regionKey);
        }
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(this.formDefine.getFormScheme());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DimensionValueSet dimSet = DimensionUtils.generateDimSet((Object)this.calcArgmentCondition.getOrgId(), (Object)this.calcArgmentCondition.getPeriodStr(), (Object)this.calcArgmentCondition.getCurrency(), (Object)this.calcArgmentCondition.getOrgType(), (String)this.calcArgmentCondition.getSelectAdjustCode(), (String)taskDefine.getKey());
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            GcReportExceutorContext context = new GcReportExceutorContext(this.iDataDefinitionRuntimeController);
            context.setOrgId(this.calcArgmentCondition.getOrgId());
            context.setTaskId(taskDefine.getKey());
            context.setData(defaultTableEntity);
            context.setDefaultGroupName(region.getFormCode());
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setDimensionValueSet(dimSet);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.iDataDefinitionRuntimeController, entityViewRunTimeController, formScheme.getKey());
            context.setEnv((IFmlExecEnvironment)environment);
            evaluator.prepare((ExecutorContext)context, dimSet, formula);
            GcReportDataRow row = new GcReportDataRow((Metadata<FieldDefine>)dataSet.getMetadata());
            row.setData(defaultTableEntity);
            boolean bl = evaluator.judge((DataRow)row);
            return bl;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5224\u65ad\u9002\u7528\u6761\u4ef6\u65f6\u51fa\u73b0\u5f02\u5e38: " + e.getMessage(), (Throwable)e);
        }
    }

    private List<GcOffSetVchrItemDTO> handleRuleDebitAndCreditItem(List<FloatLineRuleDTO.Item> debitOrCreditItem, IDataRow dataRow, String mrecid, String unit, String oppUnit, OrientEnum orient, List<String> warningMsgs, int dataRowIndex) {
        ArrayList<GcOffSetVchrItemDTO> offSetVchrItems = new ArrayList<GcOffSetVchrItemDTO>();
        for (int debitOrCreditIndex = 0; debitOrCreditIndex < debitOrCreditItem.size(); ++debitOrCreditIndex) {
            String errorMsg = this.checkRule(debitOrCreditItem.get(debitOrCreditIndex), orient, debitOrCreditIndex);
            if (!StringUtils.isEmpty((String)errorMsg)) {
                throw new BusinessRuntimeException(errorMsg);
            }
            ArrayList<String> rowWarningMsgs = new ArrayList<String>();
            GcOffSetVchrItemDTO gcOffSetVchrItemDTO = this.createOffsetItem(debitOrCreditItem.get(debitOrCreditIndex), debitOrCreditIndex, unit, oppUnit, dataRow, orient, mrecid, rowWarningMsgs, dataRowIndex);
            if (!Objects.isNull(gcOffSetVchrItemDTO)) {
                offSetVchrItems.add(gcOffSetVchrItemDTO);
            }
            warningMsgs.addAll(rowWarningMsgs);
        }
        return offSetVchrItems;
    }

    private String checkRule(FloatLineRuleDTO.Item unitItem, OrientEnum orient, int index) {
        if (StringUtils.isEmpty((String)unitItem.getSubject())) {
            if (OrientEnum.D.equals((Object)orient)) {
                return this.floatLineRuleDTO.getLocalizedName() + "\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\u3002";
            }
            return this.floatLineRuleDTO.getLocalizedName() + "\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty((String)unitItem.getAmt())) {
            if (OrientEnum.D.equals((Object)orient)) {
                return this.floatLineRuleDTO.getLocalizedName() + "\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u91d1\u989d\u4e0d\u80fd\u4e3a\u7a7a\u3002";
            }
            return this.floatLineRuleDTO.getLocalizedName() + "\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u91d1\u989d\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        return null;
    }

    private IDataTable queryFloatLineData() {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(this.formDefine.getFormScheme());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DimensionValueSet dimSet = DimensionUtils.generateDimSet((Object)this.calcArgmentCondition.getOrgId(), (Object)this.calcArgmentCondition.getPeriodStr(), (Object)this.calcArgmentCondition.getCurrency(), (Object)this.calcArgmentCondition.getOrgType(), (String)this.calcArgmentCondition.getSelectAdjustCode(), (String)taskDefine.getKey());
        this.iDataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(formScheme.getKey());
        queryEnvironment.setRegionKey(this.floatLineRuleDTO.getFloatLineDataRegion());
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setMasterKeys(dimSet);
        try {
            List dataLinkDefines = this.iRunTimeViewController.getAllLinksInRegion(this.floatLineRuleDTO.getFloatLineDataRegion());
            dataLinkDefines.forEach(dataLinkDefine -> {
                try {
                    FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(dataLinkDefine.getLinkExpression());
                    dataQuery.addColumn(fieldDefine);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
            IDataTable dataTable = dataQuery.executeQuery(context);
            return dataTable;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(this.formDefine.getTitle() + "\u67e5\u8be2\u6d6e\u52a8\u884c\u6570\u636e\u5f02\u5e38\u3002", (Throwable)e);
        }
    }

    private GcOffSetVchrItemDTO createOffsetItem(FloatLineRuleDTO.Item unitItem, int index, String unit, String oppUnit, IDataRow dataRow, OrientEnum orient, String mrecid, List<String> rowWarningMsgs, int dataRowIndex) {
        GcOrgCacheVO commonUnit;
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = new GcOffSetVchrItemDTO();
        StringBuilder rowWarningMsg = new StringBuilder();
        try {
            FieldDefine fieldDefineUnit = this.iDataDefinitionRuntimeController.queryFieldDefine(unit);
            String unitId = dataRow.getAsString(fieldDefineUnit);
            if (StringUtils.isEmpty((String)unitId)) {
                rowWarningMsg.append("\u672c\u65b9\u5355\u4f4d\u4e3a\u7a7a, ");
            }
            gcOffSetVchrItemDTO.setUnitId(unitId);
        }
        catch (Exception e) {
            if (OrientEnum.D.equals((Object)orient)) {
                this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u5355\u4f4d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
                throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u5355\u4f4d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            }
            this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u5355\u4f4d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u5355\u4f4d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
        }
        try {
            FieldDefine fieldDefineOppUnit = this.iDataDefinitionRuntimeController.queryFieldDefine(oppUnit);
            String oppUnitId = dataRow.getAsString(fieldDefineOppUnit);
            if (StringUtils.isEmpty((String)oppUnitId)) {
                rowWarningMsg.append("\u5bf9\u65b9\u5355\u4f4d\u4e3a\u7a7a, ");
            }
            gcOffSetVchrItemDTO.setOppUnitId(oppUnitId);
        }
        catch (Exception e) {
            if (OrientEnum.D.equals((Object)orient)) {
                this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u5355\u4f4d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
                throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u5355\u4f4d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            }
            this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u5355\u4f4d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u5355\u4f4d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
        }
        try {
            FieldDefine fieldDefineSubject = this.iDataDefinitionRuntimeController.queryFieldDefine(unitItem.getSubject());
            String subject = dataRow.getAsString(fieldDefineSubject);
            if (StringUtils.isEmpty((String)subject)) {
                rowWarningMsg.append("\u79d1\u76ee\u4e3a\u7a7a, ");
            }
            if (!StringUtils.isEmpty((String)subject) && subject.contains("||")) {
                subject = subject.substring(0, subject.indexOf("||"));
            }
            gcOffSetVchrItemDTO.setSubjectCode(subject);
        }
        catch (Exception e) {
            if (OrientEnum.D.equals((Object)orient)) {
                this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u79d1\u76ee\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
                throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u79d1\u76ee\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            }
            this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u79d1\u76ee\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u79d1\u76ee\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
        }
        try {
            FieldDefine fieldDefineAmt = this.iDataDefinitionRuntimeController.queryFieldDefine(unitItem.getAmt());
            String amt = dataRow.getAsString(fieldDefineAmt);
            if (StringUtils.isEmpty((String)amt) || NumberUtils.isZreo((Double)Double.parseDouble(amt))) {
                rowWarningMsg.append("\u91d1\u989d\u4e3a\u96f6\u6216\u7a7a, ");
            } else if (OrientEnum.D.equals((Object)orient)) {
                gcOffSetVchrItemDTO.addFieldValue("DEBIT_" + this.calcArgmentCondition.getCurrency(), (Object)amt);
                gcOffSetVchrItemDTO.addFieldValue("CREDIT_" + this.calcArgmentCondition.getCurrency(), (Object)0.0);
                gcOffSetVchrItemDTO.setDebit(Double.valueOf(Double.parseDouble(amt)));
                gcOffSetVchrItemDTO.setCredit(Double.valueOf(0.0));
            } else {
                gcOffSetVchrItemDTO.addFieldValue("DEBIT_" + this.calcArgmentCondition.getCurrency(), (Object)0.0);
                gcOffSetVchrItemDTO.addFieldValue("CREDIT_" + this.calcArgmentCondition.getCurrency(), (Object)amt);
                gcOffSetVchrItemDTO.setCredit(Double.valueOf(Double.parseDouble(amt)));
                gcOffSetVchrItemDTO.setDebit(Double.valueOf(0.0));
            }
        }
        catch (Exception e) {
            if (OrientEnum.D.equals((Object)orient)) {
                this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u91d1\u989d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
                throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u91d1\u989d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            }
            this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u91d1\u989d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u91d1\u989d\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
        }
        try {
            String descriptionInfo;
            if (!StringUtils.isEmpty((String)unitItem.getDescriptionInfo())) {
                FieldDefine fieldDefineDescriptionInfo = this.iDataDefinitionRuntimeController.queryFieldDefine(unitItem.getDescriptionInfo());
                descriptionInfo = dataRow.getAsString(fieldDefineDescriptionInfo);
            } else {
                descriptionInfo = "";
            }
            gcOffSetVchrItemDTO.setMemo(descriptionInfo);
        }
        catch (Exception e) {
            if (OrientEnum.D.equals((Object)orient)) {
                this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u63cf\u8ff0\u4fe1\u606f\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
                throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c\u63cf\u8ff0\u4fe1\u606f\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            }
            this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u63cf\u8ff0\u4fe1\u606f\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
            throw new BusinessRuntimeException(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c\u63cf\u8ff0\u4fe1\u606f\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
        }
        if (!Objects.isNull(unitItem.getDimensions())) {
            Map dim = unitItem.getDimensions();
            this.setDimensionToOffSetVchrItem(dim, gcOffSetVchrItemDTO, dataRow, orient, index, rowWarningMsg);
        }
        if (!StringUtils.isEmpty((String)gcOffSetVchrItemDTO.getUnitId()) && !StringUtils.isEmpty((String)gcOffSetVchrItemDTO.getOppUnitId()) && Objects.isNull(commonUnit = this.isUnionUnit(gcOffSetVchrItemDTO.getUnitId(), gcOffSetVchrItemDTO.getOppUnitId()))) {
            rowWarningMsg.append("\u672c\u5bf9\u65b9\u5355\u4f4d\u4e0d\u5728\u540c\u4e00\u4e2a\u5408\u5e76\u5c42\u7ea7, ");
        }
        if (!StringUtils.isEmpty((String)rowWarningMsg.toString())) {
            String origin = "\u89c4\u5219\u8d37\u65b9\u7b2c";
            if (OrientEnum.D.equals((Object)orient)) {
                origin = "\u89c4\u5219\u501f\u65b9\u7b2c";
            }
            rowWarningMsgs.add("[" + this.floatLineRuleDTO.getLocalizedName() + "]" + origin + (index + 1) + "\u884c\u5728\u8868[" + this.formDefine.getTitle() + "]\u4e2d\u7b2c" + (dataRowIndex + 1) + "\u6761\u6570\u636e" + rowWarningMsg.toString());
            return null;
        }
        gcOffSetVchrItemDTO.setDiffd(Double.valueOf(0.0));
        gcOffSetVchrItemDTO.setDiffc(Double.valueOf(0.0));
        gcOffSetVchrItemDTO.setOffSetDebit(Double.valueOf(0.0));
        gcOffSetVchrItemDTO.setOffSetCredit(Double.valueOf(0.0));
        gcOffSetVchrItemDTO.setmRecid(mrecid);
        gcOffSetVchrItemDTO.setTaskId(this.calcArgmentCondition.getTaskId());
        gcOffSetVchrItemDTO.setSchemeId(this.calcArgmentCondition.getSchemeId());
        gcOffSetVchrItemDTO.setFormId(this.formDefine.getKey());
        gcOffSetVchrItemDTO.setSystemId(this.floatLineRuleDTO.getReportSystem());
        gcOffSetVchrItemDTO.setRuleId(this.floatLineRuleDTO.getId());
        Object srcOffsetGroupId = dataRow.getRowKeys().getValue("RECORDKEY");
        gcOffSetVchrItemDTO.setSrcOffsetGroupId(srcOffsetGroupId.toString());
        gcOffSetVchrItemDTO.setSrcId(srcOffsetGroupId.toString());
        gcOffSetVchrItemDTO.setSubjectOrient(orient);
        gcOffSetVchrItemDTO.setOrient(orient);
        gcOffSetVchrItemDTO.setDefaultPeriod(this.calcArgmentCondition.getPeriodStr());
        gcOffSetVchrItemDTO.setGcBusinessTypeCode(this.floatLineRuleDTO.getBusinessTypeCode());
        gcOffSetVchrItemDTO.setAcctYear(this.calcArgmentCondition.getAcctYear());
        gcOffSetVchrItemDTO.setAcctPeriod(this.calcArgmentCondition.getAcctPeriod());
        gcOffSetVchrItemDTO.setCreateTime(new Date());
        gcOffSetVchrItemDTO.setOffSetCurr(this.calcArgmentCondition.getCurrency());
        gcOffSetVchrItemDTO.setOffSetSrcType(OffSetSrcTypeEnum.CONSOLIDATE);
        gcOffSetVchrItemDTO.setOrgType("NONE");
        gcOffSetVchrItemDTO.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        gcOffSetVchrItemDTO.setSelectAdjustCode((String)dataRow.getRowKeys().getValue("ADJUST"));
        return gcOffSetVchrItemDTO;
    }

    private void setDimensionToOffSetVchrItem(Map<String, String> dims, GcOffSetVchrItemDTO gcOffSetVchrItemDTO, IDataRow dataRow, OrientEnum orient, int index, StringBuilder rowWarningMsg) {
        ManagementDimensionCacheService managementDimensionCacheService = (ManagementDimensionCacheService)SpringContextUtils.getBean(ManagementDimensionCacheService.class);
        List dimensionCodes = managementDimensionCacheService.getManagementDimsBySystemId(this.floatLineRuleDTO.getReportSystem());
        dimensionCodes.forEach(dimensionCode -> {
            block12: {
                if (dims.containsKey(dimensionCode.getCode())) {
                    String dimKey = (String)dims.get(dimensionCode.getCode());
                    try {
                        String dimValue = null;
                        if ("customizeFormula".equals(dimKey)) {
                            if (StringUtils.isEmpty((String)((String)dims.get(dimensionCode.getCode() + "_customizeFormula")))) {
                                return;
                            }
                            DimensionValueSet dset = dataRow.getRowKeys();
                            AbstractData data = this.formulaEvalService.ordinaryFormulaEvaluate(dset, (String)dims.get(dimensionCode.getCode() + "_customizeFormula"), null);
                            dimValue = data.getAsString();
                        } else {
                            FieldDefine dimFieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(dimKey);
                            dimValue = dataRow.getAsString(dimFieldDefine);
                            if (StringUtils.isEmpty((String)dimValue) && Boolean.FALSE.equals(dimensionCode.getNullAble())) {
                                rowWarningMsg.append("\u4f53\u7cfb\u4e0b" + dimensionCode.getTitle() + "\u4e0d\u5141\u8bb8\u4e3a\u7a7a,");
                            }
                        }
                        gcOffSetVchrItemDTO.addFieldValue(dimensionCode.getCode(), (Object)dimValue);
                    }
                    catch (Exception e) {
                        if (OrientEnum.D.equals((Object)orient)) {
                            this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u501f\u65b9\u7b2c" + (index + 1) + "\u884c" + dimensionCode.getTitle() + "\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
                        } else {
                            this.logger.info(this.floatLineRuleDTO.getLocalizedName() + "\u89c4\u5219\u8d37\u65b9\u7b2c" + (index + 1) + "\u884c" + dimensionCode.getTitle() + "\u5728" + this.formDefine.getTitle() + "\u4e2d\u83b7\u53d6\u5931\u8d25\u3002");
                        }
                        if (Boolean.FALSE.equals(dimensionCode.getNullAble())) {
                            rowWarningMsg.append("\u4f53\u7cfb\u4e0b" + dimensionCode.getTitle() + "\u4e0d\u5141\u8bb8\u4e3a\u7a7a,");
                            break block12;
                        }
                        gcOffSetVchrItemDTO.addFieldValue(dimensionCode.getCode(), (Object)"");
                    }
                } else if (Boolean.FALSE.equals(dimensionCode.getNullAble())) {
                    rowWarningMsg.append("\u4f53\u7cfb\u4e0b" + dimensionCode.getTitle() + "\u4e0d\u5141\u8bb8\u4e3a\u7a7a,");
                }
            }
        });
    }

    private GcOrgCacheVO isUnionUnit(String unit, String oppUnit) {
        YearPeriodObject yp = new YearPeriodObject(null, this.calcArgmentCondition.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)this.calcArgmentCondition.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitOrg = orgTool.getOrgByCode(unit);
        GcOrgCacheVO oppUnitOrg = orgTool.getOrgByCode(oppUnit);
        if (Objects.isNull(unitOrg) || Objects.isNull(oppUnitOrg)) {
            return null;
        }
        GcOrgCacheVO commonUnit = orgTool.getCommonUnit(unitOrg, oppUnitOrg);
        if (Objects.isNull(commonUnit) || !commonUnit.getCode().equals(this.calcArgmentCondition.getOrgId())) {
            return null;
        }
        return commonUnit;
    }
}

