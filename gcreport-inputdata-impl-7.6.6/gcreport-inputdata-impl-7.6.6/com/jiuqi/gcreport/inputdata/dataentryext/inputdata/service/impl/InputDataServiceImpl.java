/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.dto.InputDataDTO
 *  com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition
 *  com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.CheckFailedInputDataVO
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.SoluctionQueryService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.xlib.utils.StringUtil
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.dao.InputDataCheckDao;
import com.jiuqi.gcreport.inputdata.conversion.realtime.ConversionRealTimeGather;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataAdvanceService;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.dto.InputDataDTO;
import com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition;
import com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.RuleChecker;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.FlexibleRuleExecutorImpl;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataLockService;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.CheckFailedInputDataVO;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.SoluctionQueryService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service(value="inputDataServiceImpl")
public class InputDataServiceImpl
implements InputDataService {
    private final InputDataDao dao;
    private final InputDataAdvanceService inputAdvanceService;
    private final GcOffsetAppInputDataService offsetAppInputDataService;
    private final ConsolidatedOptionService optionService;
    private final IRunTimeViewController viewController;
    private final ConsolidatedTaskService taskService;
    private final RunTimeAuthViewController runTimeAuthViewController;
    private final IEntityViewRunTimeController entityViewRunTimeController;
    private final IFormulaRunTimeController formulaRunTimeController;
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private final SoluctionQueryService efdc;
    private final INvwaSystemOptionService systemOptionsService;
    private final ManagementDimensionCacheService dimensionCacheService;
    private final InputDataLockService inputDataLockService;
    private final GcOffSetAppOffsetService adjustingEntryService;
    private final GcOffSetItemAdjustCoreService offsetCoreService;
    private final InputDataNameProvider inputDataNameProvider;
    private final ConversionRealTimeGather realTimeConversionGather;
    private final TemplateEntDaoCacheService templateEntDaoCacheService;
    private Logger logger = LoggerFactory.getLogger(InputDataServiceImpl.class);
    private static final int MAXAMOUNTOFREALTIMEOFFSET_DEFAULT = Integer.MAX_VALUE;

    public InputDataServiceImpl(GcOffsetAppInputDataService offsetAppInputDataService, InputDataDao dao, InputDataAdvanceService inputAdvanceService, ConsolidatedOptionService optionService, IRunTimeViewController viewController, ConsolidatedTaskService taskService, RunTimeAuthViewController runTimeAuthViewController, IEntityViewRunTimeController entityViewRunTimeController, IFormulaRunTimeController formulaRunTimeController, INvwaSystemOptionService systemOptionsService, ManagementDimensionCacheService dimensionService, IDataDefinitionRuntimeController dataDefinitionRuntimeController, SoluctionQueryService efdc, InputDataLockService inputDataLockService, GcOffSetAppOffsetService adjustingEntryService, InputDataNameProvider inputDataNameProvider, ConversionRealTimeGather realTimeConversionGather, TemplateEntDaoCacheService templateEntDaoCacheService, GcOffSetItemAdjustCoreService offsetCoreService) {
        this.offsetAppInputDataService = offsetAppInputDataService;
        this.dao = dao;
        this.inputAdvanceService = inputAdvanceService;
        this.optionService = optionService;
        this.viewController = viewController;
        this.taskService = taskService;
        this.runTimeAuthViewController = runTimeAuthViewController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.formulaRunTimeController = formulaRunTimeController;
        this.systemOptionsService = systemOptionsService;
        this.dimensionCacheService = dimensionService;
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.efdc = efdc;
        this.inputDataLockService = inputDataLockService;
        this.adjustingEntryService = adjustingEntryService;
        this.offsetCoreService = offsetCoreService;
        this.inputDataNameProvider = inputDataNameProvider;
        this.realTimeConversionGather = realTimeConversionGather;
        this.templateEntDaoCacheService = templateEntDaoCacheService;
    }

    @Override
    @Transactional(propagation=Propagation.NESTED, rollbackFor={Exception.class})
    public Integer doOffset(List<InputDataEO> inputItems, GcOffSetVchrDTO offsetVchr) {
        String lockId = null;
        if (!CollectionUtils.isEmpty(inputItems)) {
            InputDataEO inputItem = inputItems.get(0);
            InputWriteNecLimitCondition limitCondition = InputWriteNecLimitCondition.newMergeOrgLimit(inputItem.getTaskId(), String.valueOf(inputItem.getFieldValue("DATATIME")), String.valueOf(inputItem.getFieldValue("MD_CURRENCY")));
            List<String> inputItemIds = inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
            lockId = this.inputDataLockService.tryLock(inputItemIds, limitCondition, 0L, TimeUnit.NANOSECONDS, "\u751f\u6210\u62b5\u9500");
            if (StringUtils.isEmpty(lockId)) {
                String userName = this.inputDataLockService.queryUserNameByInputItemId(inputItemIds);
                Object[] args = new String[]{userName};
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.lockidemptyoffsetfailmsg", (Object[])args));
            }
        }
        try {
            Integer offsetedNum = this.updateOffsetInfo(inputItems);
            this.adjustingEntryService.save(offsetVchr);
            Integer n = offsetedNum;
            return n;
        }
        catch (Exception e) {
            this.logger.error("\u4fdd\u5b58\u62b5\u9500\u6570\u636e\u5f02\u5e38\uff0c\u5185\u90e8\u8868\u6570\u636e\uff1a" + inputItems.toString() + " \n \u62b5\u9500\u5206\u5f55\u6570\u636e\uff1a" + offsetVchr.getItems().toString());
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        finally {
            this.inputDataLockService.unlock(lockId);
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public Integer doOffsetNewTran(List<InputDataEO> inputItems, GcOffSetVchrDTO offsetVchr) {
        return this.doOffset(inputItems, offsetVchr);
    }

    private Integer updateOffsetInfo(List<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return 0;
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputItems.get(0).getTaskId());
        this.dao.updateOffsetInfoByConvertGroupId(inputItems, tableName);
        long newRecordTimestamp = this.dao.updateOffsetInfo(inputItems, tableName);
        List<String> updateIds = this.dao.queryByIdAndRecordTimeStamp(inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList()), newRecordTimestamp, tableName);
        if (updateIds.size() != inputItems.size()) {
            InputDataEO inputItem = inputItems.get(0);
            YearPeriodObject yp = new YearPeriodObject(null, String.valueOf(inputItem.getFieldValue("DATATIME")));
            String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)inputItems.get(0).getTaskId());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            Set inputItemOrgIds = inputItems.stream().map(InputDataEO::getMdOrg).collect(Collectors.toSet());
            StringBuffer orgStr = new StringBuffer();
            for (String orgId : inputItemOrgIds) {
                GcOrgCacheVO org = tool.getOrgByCode(orgId);
                if (orgStr.length() <= 0) {
                    orgStr.append(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.enterpriseMsg"));
                }
                orgStr.append(org.getCode()).append("|").append(org.getTitle()).append("\u3001");
            }
            Object[] args = new String[]{orgStr.toString().substring(0, orgStr.toString().length() - 1)};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.offsetfailmsg", (Object[])args));
        }
        return updateIds.size();
    }

    @Override
    public List<InputDataEO> queryByCondition(InputRuleFilterCondition condition) {
        List<InputDataEO> queryEos = this.dao.queryByCondition(condition);
        return this.removeMergeAccountItems(queryEos);
    }

    @Override
    public List<InputDataEO> queryUnoffsetedByRuleAndMergeCondition(String ruleId, MergeCalcFilterCondition mergeArg, String offsetState) {
        List<InputDataEO> list = this.dao.queryByRuleAndMergeCondition(ruleId, mergeArg, offsetState);
        return this.removeMergeAccountItems(list);
    }

    @Override
    public List<InputDataEO> queryOffsetedByRuleAndMergeCondition(String ruleId, MergeCalcFilterCondition mergeArg) {
        List<InputDataEO> list = this.dao.queryByRuleAndMergeCondition(ruleId, mergeArg, ReportOffsetStateEnum.OFFSET.getValue());
        List<String> offsetgroupids = list.stream().map(InputDataEO::getOffsetGroupId).collect(Collectors.toList());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(mergeArg.getTaskId());
        Set<String> manualOffsetgroupids = this.dao.queryByElmModeAndSrcOffsetGroupId(offsetgroupids, tableName);
        List<InputDataEO> automaticList = list.stream().filter(inputItem -> !manualOffsetgroupids.contains(inputItem.getOffsetGroupId())).collect(Collectors.toList());
        return this.removeMergeAccountItems(automaticList);
    }

    @Override
    public List<InputDataEO> queryOffsetedByInputRuleFilterCondition(InputRuleFilterCondition condition) {
        List<InputDataEO> inputDataItems = this.dao.queryByCondition(condition);
        List<String> offsetgroupids = inputDataItems.stream().map(InputDataEO::getOffsetGroupId).collect(Collectors.toList());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(condition.getTaskId());
        Set<String> manualOffsetgroupids = this.dao.queryByElmModeAndSrcOffsetGroupId(offsetgroupids, tableName);
        List<InputDataEO> automaticList = inputDataItems.stream().filter(inputItem -> !manualOffsetgroupids.contains(inputItem.getOffsetGroupId())).collect(Collectors.toList());
        return this.removeMergeAccountItems(automaticList);
    }

    @Override
    public List<InputDataEO> queryIdLimitFieldsByOffsetGroupId(Collection<String> srcOffsetGroupIds, String tableName) {
        if (CollectionUtils.isEmpty(srcOffsetGroupIds)) {
            return Collections.emptyList();
        }
        List<InputDataEO> inputDataEOS = this.dao.queryIdLimitFieldsByOffsetGroupId(srcOffsetGroupIds, tableName);
        if (inputDataEOS == null) {
            return Collections.emptyList();
        }
        return inputDataEOS;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void afterSave(DataEntryContext envContext, List<InputDataEO> insertInputItems, List<String> updateRowIds, String lockId, InputDataChangeMonitorEnvVo inputDataChangeMonitorEnvVo) {
        String systemId;
        if (CollectionUtils.isEmpty(insertInputItems) && CollectionUtils.isEmpty(updateRowIds)) {
            return;
        }
        ArrayList<InputDataEO> inputItems = new ArrayList<InputDataEO>();
        if (!CollectionUtils.isEmpty(insertInputItems)) {
            inputItems.addAll(insertInputItems);
        }
        List<InputDataEO> updateInputItems = null;
        if (!CollectionUtils.isEmpty(updateRowIds)) {
            try {
                Date updateTime = new Date();
                updateInputItems = this.queryByIds(updateRowIds, envContext.getTaskKey());
                this.checkDim(updateInputItems, envContext.getTaskKey(), InputDataConver.getDimFieldValueMap(envContext.getDimensionSet(), envContext.getTaskKey()));
                this.checkUpdateData(updateInputItems);
                inputItems.addAll(updateInputItems);
                updateInputItems.forEach(inputItem -> {
                    String subjectObj = String.valueOf(inputItem.getFieldValue("SUBJECTOBJ"));
                    GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", subjectObj);
                    inputItem.setSubjectCode(baseData.getCode());
                    inputItem.setDiffAmt(0.0);
                    inputItem.setOffsetAmt(0.0);
                    inputItem.setCheckAmt(0.0);
                    inputItem.setOffsetState(ReportOffsetStateEnum.NOTOFFSET.getValue());
                    inputItem.setCheckState(InputDataCheckStateEnum.NOTCHECK.getValue());
                    inputItem.setUpdateTime(updateTime);
                });
            }
            catch (RuntimeException e) {
                this.inputDataLockService.unlock(lockId);
                throw e;
            }
        }
        try {
            String period = envContext.getDimensionSet().getOrDefault("DATATIME", new DimensionValue()).getValue();
            systemId = this.taskService.getSystemIdBySchemeId(envContext.getFormSchemeKey(), period);
            if (StringUtils.isEmpty(systemId)) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notsystemerrormsg"));
            }
        }
        finally {
            try {
                this.inputAdvanceService.updateCustomInfoAfterSave(InputDataConver.getDimFieldValueMap(envContext.getDimensionSet(), envContext.getTaskKey()), inputItems);
            }
            finally {
                if (!StringUtils.isEmpty(lockId)) {
                    this.inputDataLockService.unlock(lockId);
                }
            }
        }
        int maxAmountOfRealTimeOffset = this.getMaxAmountOfRealTimeOffset(systemId);
        ConsolidatedOptionVO optionData = this.optionService.getOptionData(systemId);
        if (optionData.getRealTimeConversion().booleanValue()) {
            Map<String, List<InputDataEO>> currencyToNeedOffsetList = this.realTimeConversion(envContext, optionData.getRealTimeConversionExecutorName(), updateInputItems, inputItems, maxAmountOfRealTimeOffset);
            if (inputDataChangeMonitorEnvVo.isRealTimeOffSet()) {
                DataEntryContext envContextCopy = this.copyDataEntryContext(envContext);
                for (String currency : currencyToNeedOffsetList.keySet()) {
                    envContextCopy.getDimensionSet().getOrDefault("MD_CURRENCY", new DimensionValue()).setValue(currency);
                    this.inputAdvanceService.realTimeOffsetLimit(currencyToNeedOffsetList.get(currency), envContextCopy, maxAmountOfRealTimeOffset);
                }
            }
            return;
        }
        if (inputDataChangeMonitorEnvVo.isRealTimeOffSet()) {
            this.inputAdvanceService.realTimeOffsetLimit(inputItems, envContext, maxAmountOfRealTimeOffset);
        }
    }

    private Map<String, List<InputDataEO>> realTimeConversion(DataEntryContext envContext, String executorName, List<InputDataEO> updateInputItems, List<InputDataEO> inputItems, int maxAmountOfRealTimeOffset) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(envContext.getTaskKey());
        List<String> inputItemIdList = inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
        inputItems = this.dao.queryByIds(inputItemIdList, tableName);
        return this.realTimeConversionGather.getByExecutorName(executorName).realTimeConversion(envContext, updateInputItems, inputItems);
    }

    private DataEntryContext copyDataEntryContext(DataEntryContext envContext) {
        DataEntryContext dataEntryContextCopy = new DataEntryContext();
        BeanUtils.copyProperties(envContext, dataEntryContextCopy);
        Map dimensionSetCopy = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)dataEntryContextCopy.getDimensionSet()), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
        dataEntryContextCopy.setDimensionSet(dimensionSetCopy);
        return dataEntryContextCopy;
    }

    private int getMaxAmountOfRealTimeOffset(String reportSystemId) {
        if (reportSystemId == null) {
            return Integer.MAX_VALUE;
        }
        Object maxAmountOfRealTimeOffset = this.optionService.getOptionItem(reportSystemId, "maxAmountOfRealTimeOffset");
        if (maxAmountOfRealTimeOffset == null) {
            return Integer.MAX_VALUE;
        }
        return Integer.valueOf(String.valueOf(maxAmountOfRealTimeOffset));
    }

    private void checkUpdateData(List<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        inputItems.forEach(inputItem -> {
            if (inputItem.getTaskId() == null) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.nottaskkeyemptymsg"));
            }
            if (StringUtils.isEmpty(inputItem.getCurrency())) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notcurrncyemptymsg"));
            }
            if (StringUtils.isEmpty(inputItem.getPeriod())) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notdatatimeemptymsg"));
            }
            if (ReportOffsetStateEnum.OFFSET.getValue().equals(inputItem.getOffsetState())) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.offsetcheckedmsg"));
            }
        });
    }

    private List<InputDataEO> removeMergeAccountItems(List<InputDataEO> inputItems) {
        return inputItems;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String beforeDelete(List<String> inputItemIds, String taskId, Map<String, String> dimFieldAndValueMapping, String lockDes) {
        if (CollectionUtils.isEmpty(inputItemIds)) {
            return null;
        }
        InputWriteNecLimitCondition limitCondition = InputWriteNecLimitCondition.newLeafOrgLimit(taskId, dimFieldAndValueMapping.get("DATATIME"), dimFieldAndValueMapping.get("MD_CURRENCY"), dimFieldAndValueMapping.get("MDCODE"));
        String lockId = this.inputDataLockService.tryLock(inputItemIds, limitCondition, lockDes);
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.inputDataLockService.queryUserNameByInputItemId(inputItemIds);
            Object[] args = new String[]{userName};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.lockidemptyoffsetfailmsg", (Object[])args));
        }
        try {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
            ConsolidatedTaskVO consolidatedTaskVO = this.taskService.getTaskByTaskKeyAndPeriodStr(taskId, dimFieldAndValueMapping.get("DATATIME"));
            List<String> currencyList = this.getCancelOffsetCurrencyList(taskId, consolidatedTaskVO, dimFieldAndValueMapping);
            if (CollectionUtils.isEmpty(currencyList)) {
                this.offsetAppInputDataService.cancelLockedInputOffset(lockId, tableName);
            } else {
                this.offsetAppInputDataService.cancelLockedInputOffsetByCurrency(lockId, tableName, currencyList);
            }
            ((InputDataCheckDao)SpringContextUtils.getBean(InputDataCheckDao.class)).cancelLockedCheck(lockId, tableName);
            this.realTimeConversionDeleteHistoryData(consolidatedTaskVO, inputItemIds, taskId, dimFieldAndValueMapping, true);
        }
        catch (RuntimeException e) {
            this.inputDataLockService.unlock(lockId);
            throw e;
        }
        return lockId;
    }

    private List<String> getCancelOffsetCurrencyList(String taskId, ConsolidatedTaskVO consolidatedTaskVO, Map<String, String> dimFieldAndValueMapping) {
        ConsolidatedOptionVO optionData = this.optionService.getOptionData(consolidatedTaskVO.getSystemId());
        if (optionData.getRealTimeConversion().booleanValue()) {
            return this.realTimeConversionGather.getByExecutorName(optionData.getRealTimeConversionExecutorName()).getCancelOffsetCurrencyList(taskId, dimFieldAndValueMapping);
        }
        String currency = dimFieldAndValueMapping.get("MD_CURRENCY");
        return Arrays.asList(currency);
    }

    private void realTimeConversionDeleteHistoryData(ConsolidatedTaskVO consolidatedTaskVO, List<String> inputItemIds, String taskId, Map<String, String> dimFieldAndValueMapping, boolean isCalculate) {
        ConsolidatedOptionVO optionData = this.optionService.getOptionData(consolidatedTaskVO.getSystemId());
        if (optionData.getRealTimeConversion().booleanValue()) {
            this.realTimeConversionGather.getByExecutorName(optionData.getRealTimeConversionExecutorName()).deleteHistoryData(taskId, dimFieldAndValueMapping, inputItemIds, isCalculate);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String beforeUpdate(List<InputDataEO> inputItems, String taskId, Map<String, String> dimFieldAndValueMapping) {
        InputWriteNecLimitCondition limitCondition;
        if (CollectionUtils.isEmpty(inputItems)) {
            return null;
        }
        if (CollectionUtils.isEmpty(inputItems = this.removeMergeAccountItems(inputItems))) {
            return null;
        }
        List<String> inputItemIds = inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
        String lockId = this.inputDataLockService.tryLock(inputItemIds, limitCondition = InputWriteNecLimitCondition.newLeafOrgLimit(taskId, dimFieldAndValueMapping.get("DATATIME"), dimFieldAndValueMapping.get("MD_CURRENCY"), dimFieldAndValueMapping.get("MD_ORG")), "\u4fee\u6539\u524d");
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.inputDataLockService.queryUserNameByInputItemId(inputItemIds);
            Object[] args = new String[]{userName};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.lockidemptyoffsetfailmsg", (Object[])args));
        }
        try {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
            ConsolidatedTaskVO consolidatedTaskVO = this.taskService.getTaskByTaskKeyAndPeriodStr(taskId, dimFieldAndValueMapping.get("DATATIME"));
            List<String> currencyList = this.getCancelOffsetCurrencyList(taskId, consolidatedTaskVO, dimFieldAndValueMapping);
            if (CollectionUtils.isEmpty(currencyList)) {
                this.offsetAppInputDataService.cancelLockedInputOffset(lockId, tableName);
            } else {
                this.offsetAppInputDataService.cancelLockedInputOffsetByCurrency(lockId, tableName, currencyList);
            }
            ((InputDataCheckDao)SpringContextUtils.getBean(InputDataCheckDao.class)).cancelLockedCheck(lockId, tableName);
            this.realTimeConversionDeleteHistoryData(consolidatedTaskVO, inputItemIds, taskId, dimFieldAndValueMapping, false);
        }
        catch (RuntimeException e) {
            this.inputDataLockService.unlock(lockId);
            throw e;
        }
        return lockId;
    }

    private void checkDim(List<InputDataEO> inputItems, String taskId, Map<String, String> dimFieldValueMapping) {
        HashMap<String, Object> existsDims = new HashMap<String, Object>(4);
        existsDims.put("MDCODE", inputItems.get(0).getFieldValue("MDCODE"));
        existsDims.put("DATATIME", inputItems.get(0).getFieldValue("DATATIME"));
        existsDims.put("MD_GCORGTYPE", inputItems.get(0).getFieldValue("MD_GCORGTYPE"));
        existsDims.put("MD_CURRENCY", inputItems.get(0).getFieldValue("MD_CURRENCY"));
        if (!existsDims.get("MDCODE").equals(dimFieldValueMapping.get("MDCODE"))) {
            this.logger.info("\u4e0d\u80fd\u66f4\u65b0\u975e\u5f53\u524d\u9009\u4e2d\u5355\u4f4d\u7684\u6570\u636e " + existsDims.get("MDCODE").toString() + "  " + dimFieldValueMapping.get("MDCODE"));
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.mdorgcheckedmsg"));
        }
        if (!existsDims.get("DATATIME").equals(dimFieldValueMapping.get("DATATIME"))) {
            this.logger.info("\u4e0d\u80fd\u66f4\u65b0\u975e\u5f53\u524d\u9009\u4e2d\u65f6\u671f\u7684\u6570\u636e " + existsDims.get("DATATIME").toString() + "  " + dimFieldValueMapping.get("DATATIME"));
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.datatimecheckedmsg"));
        }
        if (!existsDims.get("MD_GCORGTYPE").equals(dimFieldValueMapping.get("MD_GCORGTYPE"))) {
            this.logger.info("\u4e0d\u80fd\u66f4\u65b0\u975e\u5f53\u524d\u9009\u4e2d\u53e3\u5f84\u7684\u6570\u636e " + existsDims.get("MD_GCORGTYPE").toString() + "  " + dimFieldValueMapping.get("MD_GCORGTYPE"));
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.orgtypecheckedmsg"));
        }
        if (!existsDims.get("MD_CURRENCY").equals(dimFieldValueMapping.get("MD_CURRENCY"))) {
            this.logger.info("\u4e0d\u80fd\u66f4\u65b0\u975e\u5f53\u524d\u9009\u4e2d\u5e01\u79cd\u7684\u6570\u636e " + existsDims.get("MD_CURRENCY").toString() + "  " + dimFieldValueMapping.get("MD_CURRENCY"));
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.currncycheckedmsg"));
        }
        String systemId = this.getSystemIdByTaskId(taskId, dimFieldValueMapping.get("DATATIME"));
        for (InputDataEO inputItem : inputItems) {
            if (!systemId.equals(inputItem.getReportSystemId())) {
                this.logger.info("\u4e0d\u80fd\u8de8\u4f53\u7cfb systemId:" + systemId + "   " + inputItem.getReportSystemId());
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.systemidcheckedmsg"));
            }
            if (!inputItem.getFieldValue("MDCODE").equals(existsDims.get("MDCODE"))) {
                this.logger.info("\u4e0d\u80fd\u8de8\u5355\u4f4d code:" + existsDims.get("MDCODE") + "   " + inputItem.getFieldValue("MDCODE"));
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notcrossunitcheckedmsg"));
            }
            if (!inputItem.getFieldValue("DATATIME").equals(existsDims.get("DATATIME"))) {
                this.logger.info("\u4e0d\u80fd\u8de8\u65f6\u671f PERIOD:" + existsDims.get("DATATIME") + "   " + inputItem.getFieldValue("DATATIME"));
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notcrossdatatimecheckedmsg"));
            }
            if (!inputItem.getFieldValue("MD_GCORGTYPE").equals(existsDims.get("MD_GCORGTYPE"))) {
                this.logger.info("\u4e0d\u80fd\u8de8\u53e3\u5f84 ORGTYPE:" + existsDims.get("MD_GCORGTYPE") + "   " + inputItem.getFieldValue("MD_GCORGTYPE"));
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notcrossorgtypecheckedmsg"));
            }
            if (inputItem.getFieldValue("MD_CURRENCY").equals(existsDims.get("MD_CURRENCY"))) continue;
            this.logger.info("\u4e0d\u80fd\u8de8\u5e01\u79cd CURRENCY:" + existsDims.get("MD_CURRENCY") + "   " + inputItem.getFieldValue("MD_CURRENCY"));
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notcrosscurrncycheckedmsg"));
        }
    }

    @Override
    public void beforeSave(DataEntryContext dataEntryContext, Map<String, Object> fieldValues) {
        String systemId = this.taskService.getSystemIdBySchemeId(dataEntryContext.getFormSchemeKey(), String.valueOf(fieldValues.get("DATATIME")));
        if (StringUtils.isEmpty(systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notsystemerrormsg"));
        }
        this.checkAgain(systemId, fieldValues);
        YearPeriodObject yp = new YearPeriodObject(null, String.valueOf(fieldValues.get("DATATIME")));
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)dataEntryContext.getTaskKey());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(String.valueOf(fieldValues.get("MDCODE")));
        GcOrgCacheVO oppUnit = tool.getOrgByCode(String.valueOf(fieldValues.get("OPPUNITID")));
        String[] subject = String.valueOf(fieldValues.get("SUBJECTOBJ")).split(";");
        if (subject.length > 1) {
            Object[] args = new String[]{oppUnit.getTitle()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.oppunitsubjectcheckedmsg", (Object[])args));
        }
        GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", subject[0]);
        if (baseData == null) {
            Object[] args = new String[]{subject[0]};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notfindsubjectmsg", (Object[])args));
        }
        if (org.getOrgKind() == GcOrgKindEnum.DIFFERENCE) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notdifferencewritemsg"));
        }
        if (org.getOrgKind() == GcOrgKindEnum.UNIONORG) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notunionorgwritemsg"));
        }
    }

    private void checkAgain(String systemId, Map<String, Object> fieldValues) {
        if (fieldValues.get("MD_GCORGTYPE") == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.unionorgnotemptymsg"));
        }
        if (fieldValues.get("DATATIME") == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.datatimenotemptymsg"));
        }
        if (fieldValues.get("MDCODE") == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.unitnotemptymsg"));
        }
        if (fieldValues.get("MD_CURRENCY") == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.currncynotemptymsg"));
        }
        if (fieldValues.get("SUBJECTOBJ") == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.subjectnotemptymsg"));
        }
        if (fieldValues.get("OPPUNITID") == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.oppunitnotemptymsg"));
        }
        this.dimensionCacheService.getManagementDimsBySystemId(systemId).forEach(dimension -> {
            boolean dimensionIsEmpty = false;
            if (fieldValues.get(dimension.getCode()) instanceof String) {
                dimensionIsEmpty = StringUtil.isEmpty((String)((String)fieldValues.get(dimension.getCode())));
            }
            if (Boolean.FALSE.equals(dimension.getNullAble()) && (fieldValues.get(dimension.getCode()) == null || dimensionIsEmpty)) {
                Object[] args = new String[]{dimension.getTitle()};
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.dimensionnotemptymsg", (Object[])args));
            }
        });
        String amtFieldName = "AMT";
        if (fieldValues.get(amtFieldName) == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.amtnotemptymsg"));
        }
    }

    @Override
    public String queryRecordOffsetState(String recordId, String taskId) {
        GcOffSetVchrItemDTO offSetVchrItemAdjustEO;
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        InputDataEO inputData = new InputDataEO();
        inputData.setId(recordId);
        inputData.setBizkeyOrder(recordId);
        InputDataEO inputItem = (InputDataEO)dao.selectByEntity((BaseEntity)inputData);
        if (inputItem == null ? (offSetVchrItemAdjustEO = this.offsetCoreService.getGcOffSetVchrItemDTO(recordId)) == null : ReportOffsetStateEnum.NOTOFFSET.getValue().equals(inputItem.getOffsetState()) || inputItem.getOffsetGroupId() == null) {
            return ReportOffsetStateEnum.NOTOFFSET.getValue();
        }
        return ReportOffsetStateEnum.OFFSET.getValue();
    }

    @Override
    public List<InputDataEO> queryByIds(Collection<String> uuids, String taskId) {
        if (CollectionUtils.isEmpty(uuids)) {
            return Collections.emptyList();
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        return this.dao.queryByIds(uuids, tableName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateRuleInfo(Collection<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        InputDataEO inputItem = inputItems.stream().findAny().get();
        InputWriteNecLimitCondition limitCondition = InputWriteNecLimitCondition.newMergeOrgLimit(inputItem.getTaskId(), String.valueOf(inputItem.getFieldValue("DATATIME")), String.valueOf(inputItem.getFieldValue("MD_CURRENCY")));
        List<String> inputItemIds = inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
        String lockId = this.inputDataLockService.tryLock(inputItemIds, limitCondition, 5L, TimeUnit.SECONDS, "\u66f4\u65b0\u89c4\u5219");
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.inputDataLockService.queryUserNameByInputItemId(inputItemIds);
            Object[] args = new String[]{userName};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.rulechangeoffsetfailmsg", (Object[])args));
        }
        try {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputItem.getTaskId());
            long newRecordTimestamp = this.dao.updateRuleInfo(inputItems, tableName);
            List<String> updateIds = this.dao.queryByIdAndRecordTimeStamp(inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList()), newRecordTimestamp, tableName);
            if (updateIds.size() != inputItems.size()) {
                YearPeriodObject yp = new YearPeriodObject(null, String.valueOf(inputItem.getFieldValue("DATATIME")));
                String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)inputItem.getTaskId());
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
                Set inputItemOrgIds = inputItems.stream().map(InputDataEO::getMdOrg).collect(Collectors.toSet());
                StringBuffer orgStr = new StringBuffer();
                for (String orgId : inputItemOrgIds) {
                    GcOrgCacheVO org = tool.getOrgByCode(orgId);
                    if (orgStr.length() <= 0) {
                        orgStr.append(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.enterpriseMsg"));
                    }
                    orgStr.append(org.getCode()).append("|").append(org.getTitle()).append("\u3001");
                }
                throw new BusinessRuntimeException(orgStr.toString().substring(0, orgStr.toString().length() - 1) + GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.ruleupdatefailmsg"));
            }
        }
        finally {
            this.inputDataLockService.unlock(lockId);
        }
    }

    @Override
    public List<CheckFailedInputDataVO> checkOffsetData(OffsetDataCheckVO checkCondition) {
        List<InputDataEO> inputItems = this.dao.queryCheckAmtErrorRecords(checkCondition);
        if (CollectionUtils.isEmpty(inputItems)) {
            return Collections.emptyList();
        }
        YearPeriodObject yp = new YearPeriodObject(null, checkCondition.getTaskBaseArguments().getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)checkCondition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return inputItems.stream().map(inputItem -> this.converE2CheckFailed((InputDataEO)((Object)inputItem), instance)).collect(Collectors.toList());
    }

    private CheckFailedInputDataVO converE2CheckFailed(InputDataEO inputItem, GcOrgCenterService orgTool) {
        GcBaseData currency;
        GcOrgCacheVO oppUnit;
        GcOrgCacheVO unit;
        FormDefine form;
        TaskDefine task;
        CheckFailedInputDataVO checkFailedItem = new CheckFailedInputDataVO();
        checkFailedItem.setId(inputItem.getId());
        checkFailedItem.setPeriodStr(inputItem.getPeriod());
        checkFailedItem.setAmt(inputItem.getAmt());
        checkFailedItem.setOffsetAmt(inputItem.getOffsetAmt());
        checkFailedItem.setDiffAmt(inputItem.getDiffAmt());
        checkFailedItem.setOffsetGroupId(inputItem.getOffsetGroupId());
        try {
            task = this.viewController.queryTaskDefine(inputItem.getTaskId());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.querytaskexceptionmsg"), (Throwable)e);
        }
        if (task != null) {
            checkFailedItem.setTaskTitle(task.getTitle());
        }
        if ((form = this.viewController.queryFormById(inputItem.getFormId())) != null) {
            checkFailedItem.setFormTitle(form.getTitle());
        }
        if ((unit = orgTool.getOrgByID(inputItem.getUnitId())) != null) {
            checkFailedItem.setUnitTitle(unit.getTitle());
        }
        if ((oppUnit = orgTool.getOrgByID(inputItem.getOppUnitId())) != null) {
            checkFailedItem.setOppUnitTitle(oppUnit.getTitle());
        }
        if ((currency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", inputItem.getCurrency())) != null) {
            checkFailedItem.setCurrencyTitle(currency.getTitle());
        }
        return checkFailedItem;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<CheckFailedInputDataVO> cancelOffset(OffsetDataCheckVO checkCondition, Boolean cancelAll, Collection<String> inputItemIds) {
        if (Boolean.TRUE.equals(cancelAll)) {
            List<InputDataEO> inputItems = this.dao.queryCheckAmtErrorRecords(checkCondition);
            if (CollectionUtils.isEmpty(inputItems)) {
                return Collections.emptyList();
            }
            inputItemIds = inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(inputItemIds)) {
            return Collections.emptyList();
        }
        GcTaskBaseArguments taskBaseArguments = checkCondition.getTaskBaseArguments();
        String lockId = this.inputDataLockService.tryLock(inputItemIds, InputWriteNecLimitCondition.newMergeOrgLimit(taskBaseArguments.getTaskId(), taskBaseArguments.getPeriodStr(), checkCondition.getCurrency()), "\u9519\u8bef\u62b5\u9500\u5206\u5f55\u5220\u9664");
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.inputDataLockService.queryUserNameByInputItemId(inputItemIds);
            Object[] args = new String[]{userName};
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.lockidemptyoffsetfailmsg", (Object[])args));
        }
        try {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskBaseArguments.getTaskId());
            this.offsetAppInputDataService.cancelLockedInputOffset(lockId, tableName);
        }
        catch (RuntimeException e) {
            this.inputDataLockService.unlock(lockId);
            throw e;
        }
        return this.checkOffsetData(checkCondition);
    }

    @Override
    public String canOffset(List<String> inputItemIds, boolean realTimeOffset, String orgType, String taskId) {
        if (CollectionUtils.isEmpty(inputItemIds)) {
            return GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notoffsetdatamsg");
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        List<InputDataEO> inputItems = this.dao.queryByIds(inputItemIds, tableName);
        for (int index = 0; index < inputItems.size(); ++index) {
            InputDataEO inputItem = inputItems.get(index);
            if (inputItem.getUnionRuleId() == null) {
                return GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.emptyruledatamsg");
            }
            if (index == 0 || inputItem.getUnionRuleId().equals(inputItems.get(0).getUnionRuleId())) continue;
            return GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.rulenotsamemsg");
        }
        AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)inputItems.get(0).getUnionRuleId());
        RuleChecker ruleChecker = this.getRuleChecker(rule);
        if (ruleChecker == null) {
            return GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notrulecheckermsg");
        }
        String canOffset = ruleChecker.canOffset(inputItems, realTimeOffset, rule, orgType);
        return StringUtils.isEmpty(canOffset) ? GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.canoffsetmsg") : canOffset;
    }

    @Override
    public String canOffsetByData(List<InputDataEO> inputItems, boolean realTimeOffset, String orgType) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notoffsetdatamsg");
        }
        for (int index = 0; index < inputItems.size(); ++index) {
            InputDataEO inputItem = inputItems.get(index);
            if (inputItem.getUnionRuleId() == null) {
                return GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.emptyruledatamsg");
            }
            if (index == 0 || inputItem.getUnionRuleId().equals(inputItems.get(0).getUnionRuleId())) continue;
            return GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.rulenotsamemsg");
        }
        AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)inputItems.get(0).getUnionRuleId());
        RuleChecker ruleChecker = this.getRuleChecker(rule);
        if (ruleChecker == null) {
            return GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notrulecheckermsg");
        }
        String canOffset = ruleChecker.canOffset(inputItems, realTimeOffset, (AbstractUnionRule)FlexibleRuleExecutorImpl.convertFlexibleRule(rule), orgType);
        return StringUtils.isEmpty(canOffset) ? GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.alldatacanoffsetmsg") : canOffset;
    }

    private RuleChecker getRuleChecker(AbstractUnionRule rule) {
        switch (RuleTypeEnum.valueOf((String)rule.getRuleType())) {
            case FLEXIBLE: {
                return FlexibleRuleExecutorImpl.getRuleCheckInstance(rule);
            }
            case FINANCIAL_CHECK: {
                return FlexibleRuleExecutorImpl.getRuleCheckInstance(rule);
            }
        }
        return null;
    }

    @Override
    public JSONObject efdcPenetrableSearch(JtableContext jtableContext, List<String> recordIds, String fieldCode) {
        recordIds.remove(null);
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(jtableContext.getTaskKey());
        List<InputDataEO> inputItems = this.dao.queryByIds(recordIds, tableName);
        GcOrgCacheVO org = this.org(inputItems);
        String frangeFml = null;
        String formula = null;
        String firstNumberFieldFml = null;
        String firstNumberFieldCode = null;
        FormulaSchemeDefine formulaSchemeDefine = this.cwFormulaSchemeDefine(jtableContext, org.getId());
        List allFormulaDefines = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemeDefine.getKey(), jtableContext.getFormKey());
        FormDefine form = this.runTimeAuthViewController.queryFormById(jtableContext.getFormKey());
        StringBuffer filterFml = new StringBuffer(64);
        try {
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            if (form != null) {
                executorContext.setDefaultGroupName(form.getFormCode());
            }
            executorContext.setDefaultGroupName(form.getFormCode());
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeAuthViewController.getRuntimeView(), this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formulaSchemeDefine.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            QueryContext qContext = new QueryContext(executorContext, null);
            ReportFormulaParser parser = executorContext.getCache().getFormulaParser(true);
            for (FormulaDefine formulaDefine : allFormulaDefines) {
                boolean isNumberField;
                String expression = formulaDefine.getExpression();
                int equalIndex = expression.indexOf("=");
                String assginExp = expression.substring(0, equalIndex);
                String efdcExpression = expression.substring(equalIndex + 1);
                if (assginExp.contains("*")) {
                    frangeFml = efdcExpression;
                    continue;
                }
                IExpression exp = parser.parseEval(assginExp, (IContext)qContext);
                DynamicDataNode dataNode = null;
                for (IASTNode child : exp) {
                    if (!(child instanceof DynamicDataNode)) continue;
                    dataNode = (DynamicDataNode)child;
                    break;
                }
                FieldDefine fieldDefine = dataNode.getDataLink().getField();
                this.filterFml(inputItems, filterFml, efdcExpression, fieldDefine);
                if (formula != null || !(isNumberField = fieldDefine.getType() == FieldType.FIELD_TYPE_FLOAT || fieldDefine.getType() == FieldType.FIELD_TYPE_DECIMAL || fieldDefine.getType() == FieldType.FIELD_TYPE_INTEGER)) continue;
                if (fieldCode.equals(fieldDefine.getCode())) {
                    formula = efdcExpression;
                }
                if (null != firstNumberFieldCode) continue;
                firstNumberFieldCode = fieldDefine.getCode();
                firstNumberFieldFml = efdcExpression;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        String address = this.systemOptionsService.get("fext-settings-group", "EFDCURL");
        jsonObject.put("frangeFml", frangeFml);
        jsonObject.put("userName", (Object)loginUser.getName());
        jsonObject.put("address", (Object)address);
        jsonObject.put("unitCode", (Object)org.getCode());
        if (filterFml.length() > 0) {
            filterFml.append("'");
        }
        System.out.println(filterFml);
        jsonObject.put("filterFml", (Object)filterFml.toString());
        if (formula == null) {
            jsonObject.put("formula", firstNumberFieldFml);
            jsonObject.put("fieldCode", (Object)(Objects.requireNonNull(form).getFormCode() + "_" + firstNumberFieldCode));
        } else {
            jsonObject.put("formula", formula);
            jsonObject.put("fieldCode", (Object)(Objects.requireNonNull(form).getFormCode() + "_" + fieldCode));
        }
        return jsonObject;
    }

    private void filterFml(List<InputDataEO> inputItems, StringBuffer filterFml, String efdcExpression, FieldDefine fieldDefine) {
        String regex = "getval\\(\"\\*\\d+\"\\)";
        if (efdcExpression.matches(regex)) {
            if (filterFml.length() == 0) {
                filterFml.append(" filters='");
            }
            String values = this.getRecordValues(inputItems, fieldDefine.getCode());
            filterFml.append(efdcExpression).append("=\"").append(values).append("\",");
        } else if ("EFDCMOREFILTERFML".equalsIgnoreCase(fieldDefine.getCode())) {
            String reg = "\\s*\\+\\s*\\\";\\\"\\s*\\+\\s*";
            String[] efdcExpressions = efdcExpression.split(reg);
            for (int i = 0; i < efdcExpressions.length; ++i) {
                String expression = efdcExpressions[i];
                if (StringUtils.isEmpty(expression)) continue;
                StringBuilder values = new StringBuilder();
                for (InputDataEO inputItem : inputItems) {
                    String[] oneFieldSplitValues;
                    Object value = inputItem.getFieldValue(fieldDefine.getCode().toUpperCase());
                    if (null == value || (oneFieldSplitValues = String.valueOf(value).split(";")).length < efdcExpressions.length) continue;
                    values.append(oneFieldSplitValues[i]).append(";");
                }
                filterFml.append(expression).append("=\"").append((CharSequence)values).append("\",");
            }
        }
    }

    private String getRecordValues(List<InputDataEO> inputItems, String fieldCode) {
        if ("OPPUNITID".equals(fieldCode)) {
            StringBuilder oppUnitCodes = new StringBuilder();
            for (InputDataEO inputItem : inputItems) {
                String oppUnitId = (String)inputItem.getFieldValue(fieldCode.toUpperCase());
                YearPeriodObject yp = new YearPeriodObject(null);
                GcOrgCenterService instance = GcOrgPublicTool.getInstance(null, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
                GcOrgCacheVO oppUnit = instance.getOrgByCode(oppUnitId);
                if (null == oppUnit) continue;
                oppUnitCodes.append(oppUnit.getCode()).append(";");
            }
            return oppUnitCodes.toString();
        }
        StringBuilder values = new StringBuilder();
        for (InputDataEO inputItem : inputItems) {
            Object value = inputItem.getFieldValue(fieldCode.toUpperCase());
            if (null == value) continue;
            values.append(value).append(";");
        }
        return values.toString();
    }

    private FormulaSchemeDefine cwFormulaSchemeDefine(JtableContext jtableContext, String unitId) {
        HashMap<String, String> paras = new HashMap<String, String>(3);
        paras.put("MD_CURRENCY", this.getDimValue(jtableContext, "MD_CURRENCY"));
        paras.put("MD_GCORGTYPE", this.getDimValue(jtableContext, "MD_GCORGTYPE"));
        QueryObjectImpl object = new QueryObjectImpl();
        object.setTaskKey(jtableContext.getTaskKey());
        object.setFormSchemeKey(jtableContext.getFormSchemeKey());
        object.setMainDim(unitId);
        return this.efdc.getSoluctionByDimensions(object, paras, null);
    }

    private GcOrgCacheVO org(List<InputDataEO> inputItems) {
        Assert.isNotEmpty(inputItems, (String)GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.inputdataemptymsg"), (Object[])new Object[0]);
        HashSet<String> unitIds = new HashSet<String>();
        for (InputDataEO inputItem : inputItems) {
            unitIds.add(inputItem.getUnitId());
        }
        Assert.isTrue((unitIds.size() == 1 ? 1 : 0) != 0, (String)GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.unitonlymsg"), (Object[])new Object[0]);
        String unitId = (String)unitIds.iterator().next();
        GcOrgCenterService instance = GcOrgPublicTool.getInstance();
        GcOrgCacheVO org = instance.getOrgByCode(unitId);
        Assert.isNotNull((Object)org, (String)GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.unitemptymsg"), (Object[])new Object[0]);
        return org;
    }

    private String getDimValue(JtableContext jtableContext, String dimKey) {
        DimensionValue dimensionValue = (DimensionValue)jtableContext.getDimensionSet().get(dimKey);
        if (null == dimensionValue) {
            return null;
        }
        return dimensionValue.getValue();
    }

    @Override
    public List<InputDataEO> queryByTaskAndDimensions(String taskId, Map<String, String> dimensions) {
        return this.dao.queryByTaskAndDimensions(taskId, dimensions);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void udpateRelationToMergeInputData(List<InputDataDTO> inputItemDTOs) {
        if (CollectionUtils.isEmpty(inputItemDTOs)) {
            return;
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputItemDTOs.get(0).getTaskId());
        this.dao.udpateRelationToMergeInputData(inputItemDTOs, tableName);
    }

    @Override
    public List<InputDataDTO> queryInputDataForRelationToMerge(QueryParamsVO queryParamsVO) {
        List<InputDataEO> inputDataEOS = this.dao.queryInputDataForRelationToMerge(queryParamsVO);
        ArrayList<InputDataDTO> inputDataDTOS = new ArrayList<InputDataDTO>();
        inputDataEOS.forEach(eo -> {
            InputDataDTO dto = new InputDataDTO();
            BeanUtils.copyProperties(eo, dto);
            inputDataDTOS.add(dto);
        });
        return inputDataDTOS;
    }

    @Override
    public int getUnOffsetInputDataItemCount(QueryParamsVO queryParamsVO) {
        return this.dao.getUnOffsetInputDataItemCount(queryParamsVO);
    }

    private String getSystemIdByTaskId(String taskId, String periodStr) {
        String systemId = this.taskService.getSystemIdByTaskId(taskId, periodStr);
        if (StringUtils.isEmpty(systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notsystemerrormsg"));
        }
        return systemId;
    }
}

