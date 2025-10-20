/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.inputdata.dto.GcBatchCopyActionParamDTO
 *  com.jiuqi.gcreport.inputdata.dto.GcBatchCopyEnvDTO
 *  com.jiuqi.gcreport.inputdata.dto.GcBatchCopyOrgAndFormTableEnvDTO
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.service.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.inputdata.dto.GcBatchCopyActionParamDTO;
import com.jiuqi.gcreport.inputdata.dto.GcBatchCopyEnvDTO;
import com.jiuqi.gcreport.inputdata.dto.GcBatchCopyOrgAndFormTableEnvDTO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataSrcTypeEnum;
import com.jiuqi.gcreport.inputdata.service.GcBatchCopyService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcBatchCopyServiceImpl
implements GcBatchCopyService {
    private Logger LOGGER = LoggerFactory.getLogger(GcBatchCopyServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public void batchCopy(GcBatchCopyEnvDTO env) {
        try {
            BusinessLogUtils.operate((String)"\u6279\u91cf\u590d\u5236\uff08\u5408\u5e76\uff09", (String)"\u6267\u884c\u6279\u91cf\u590d\u5236", (String)JsonUtils.writeValueAsString((Object)env));
            this.createAsyncTask(env);
            this.fillAndCheckEnvArgs(env);
            this.modifyAsyncTaskState(env, TaskState.PROCESSING, 0.1, "\u6b63\u5728\u6821\u9a8c\u6279\u91cf\u590d\u5236\u53c2\u6570\u3002");
            this.executeBatchCopy(env);
        }
        catch (Exception e) {
            env.getMessages().add(e.getMessage());
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        finally {
            this.modifyAsyncTaskState(env, TaskState.PROCESSING, 1.0, "");
        }
    }

    private void executeBatchCopy(GcBatchCopyEnvDTO env) throws Exception {
        GcBatchCopyActionParamDTO param = env.getParam();
        List<GcBatchCopyOrgAndFormTableEnvDTO> batchCopyItemEnvs = this.getOrgAndFormAndTableContextEnvs(env);
        if (CollectionUtils.isEmpty((Collection)param.getOrgIds())) {
            return;
        }
        double stepProgress = this.getStepProgress(batchCopyItemEnvs);
        env.setStepProgress(stepProgress);
        GcBatchCopyServiceImpl batchCopyService = (GcBatchCopyServiceImpl)SpringContextUtils.getBean(GcBatchCopyServiceImpl.class);
        DefinitionsCache definitionsCache = this.getDefinitionsCache();
        batchCopyItemEnvs.stream().forEach(batchCopyItemEnv -> {
            String msg = "\u6279\u91cf\u8fd0\u7b97[\u5355\u4f4d\uff1a".concat(batchCopyItemEnv.getOrg().getTitle()).concat(", \u5e01\u79cd\uff1a").concat(ConverterUtils.getAsString((Object)batchCopyItemEnv.getDimensionValueSet().getValue("MD_CURRENCY"))).concat(", \u8868\u5355\uff1a").concat(batchCopyItemEnv.getFormDefine().getTitle()).concat(", \u533a\u57df\uff1a").concat(batchCopyItemEnv.getDataRegionDefine().getTitle());
            try {
                double currentProgress = env.getCurrentProgress() + env.getStepProgress();
                this.modifyAsyncTaskState(env, TaskState.PROCESSING, currentProgress, msg);
                batchCopyService.dispatchBatchCopyItem((GcBatchCopyOrgAndFormTableEnvDTO)batchCopyItemEnv, definitionsCache);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(msg.concat("\u6267\u884c\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff0c\u8be6\u60c5\uff1a").concat(e.getMessage()), (Throwable)e);
            }
        });
    }

    private double getStepProgress(List<GcBatchCopyOrgAndFormTableEnvDTO> orgAndFormContextEnvs) {
        double stepProgress = orgAndFormContextEnvs.size() > 0 ? new BigDecimal(0.95f / (float)orgAndFormContextEnvs.size()).setScale(5, 1).doubleValue() : 0.95;
        return stepProgress;
    }

    private List<GcBatchCopyOrgAndFormTableEnvDTO> getOrgAndFormAndTableContextEnvs(GcBatchCopyEnvDTO env) {
        Map<String, FormDefine> formDefineMap = this.getFormDefinesByEnv(env);
        if (formDefineMap == null || formDefineMap.size() == 0) {
            return null;
        }
        GcBatchCopyActionParamDTO param = env.getParam();
        Map dimensionSet = param.getEnvContext().getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        DimensionValue gcorgtypeValue = (DimensionValue)dimensionSet.get("MD_GCORGTYPE");
        DimensionValue adjustValue = (DimensionValue)dimensionSet.get("ADJUST");
        String periodStr = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        String selectAdjustCode = adjustValue == null ? "0" : adjustValue.getValue();
        String orgTypeId = gcorgtypeValue == null ? null : gcorgtypeValue.getValue();
        ArrayList<GcBatchCopyOrgAndFormTableEnvDTO> orgAndFormContextEnvs = new ArrayList<GcBatchCopyOrgAndFormTableEnvDTO>();
        List paramCurrencyIds = param.getCurrencyIds();
        param.getFormIds().stream().filter(Objects::nonNull).forEach(formId -> {
            YearPeriodDO yp;
            FormDefine formDefine = (FormDefine)formDefineMap.get(formId);
            if (formDefine == null) {
                return;
            }
            Map<DataRegionDefine, Set<TableModelDefine>> formTableDefines = this.getFormTableDefines(formDefine.getKey());
            if (formTableDefines == null) {
                return;
            }
            try {
                yp = GcPeriodAssistUtil.getPeriodObject((String)param.getEnvContext().getFormSchemeKey(), (String)periodStr);
            }
            catch (Exception e) {
                this.LOGGER.info("\u89e3\u6790\u8868\u5355[" + formDefine.getFormCode() + "]\u7684\u671f\u95f4" + periodStr + "\u5f02\u5e38:" + e.getMessage());
                return;
            }
            GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)param.getOrgVersionType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodDO)yp);
            param.getOrgIds().stream().filter(Objects::nonNull).forEach(orgId -> {
                GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(orgId);
                String orgTitle = orgToJsonVO.getTitle();
                if (GcOrgKindEnum.UNIONORG.equals((Object)orgToJsonVO.getOrgKind()) || GcOrgKindEnum.DIFFERENCE.equals((Object)orgToJsonVO.getOrgKind())) {
                    String msg = "\u5408\u5e76\u5355\u4f4d\u548c\u5dee\u989d\u5355\u4f4d\u4e0d\u652f\u6301\u6279\u91cf\u590d\u5236\uff0c\u5355\u4f4d\uff1a\u3010" + orgTitle + "\u3011\u3002";
                    env.getMessages().add(msg);
                    this.LOGGER.info(msg);
                    return;
                }
                String currencyIds = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYIDS"));
                paramCurrencyIds.stream().filter(Objects::nonNull).forEach(paramCurrencyId -> {
                    ReadWriteAccessDesc readWriteAccessDesc;
                    if (currencyIds.indexOf((String)paramCurrencyId) == -1) {
                        String msg = "\u5355\u4f4d\uff1a\u3010" + orgTitle + "\u3011\u65e0\u5e01\u79cd\u3010" + paramCurrencyId + "\u3011\uff0c\u8df3\u8fc7\u3002";
                        env.getMessages().add(msg);
                        this.LOGGER.info(msg);
                        return;
                    }
                    DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
                    dimensionParamsVO.setCurrency(paramCurrencyId);
                    dimensionParamsVO.setCurrencyId(paramCurrencyId);
                    dimensionParamsVO.setOrgType(env.getParam().getOrgVersionType());
                    dimensionParamsVO.setOrgTypeId(orgTypeId);
                    dimensionParamsVO.setPeriodStr(periodStr);
                    dimensionParamsVO.setOrgId(orgId);
                    dimensionParamsVO.setSchemeId(param.getEnvContext().getFormSchemeKey());
                    dimensionParamsVO.setTaskId(param.getEnvContext().getTaskKey());
                    if (DimensionUtils.isExistAdjust((String)param.getEnvContext().getTaskKey())) {
                        dimensionParamsVO.setSelectAdjustCode(ConverterUtils.getAsString((Object)selectAdjustCode, (String)"0"));
                    }
                    if (Boolean.FALSE.equals((readWriteAccessDesc = FormUploadStateTool.getInstance().writeable(dimensionParamsVO, formId)).getAble())) {
                        String msg = "\u5355\u4f4d\uff1a\u3010".concat(orgTitle).concat("\u3011\uff0c\u8868\u3010").concat(formDefine.getTitle()).concat("\u3011").concat(readWriteAccessDesc.getDesc()).concat("\u8df3\u8fc7\u3002");
                        this.LOGGER.info(msg);
                        return;
                    }
                    formTableDefines.forEach((dataRegionDefine, tableDefines) -> tableDefines.stream().filter(Objects::nonNull).forEach(tableDefine -> {
                        GcBatchCopyOrgAndFormTableEnvDTO singleOrgEnv = new GcBatchCopyOrgAndFormTableEnvDTO();
                        DimensionValueSet currDimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)param.getEnvContext().getDimensionSet());
                        currDimensionValueSet.setValue("MD_ORG", orgId);
                        currDimensionValueSet.setValue("DATATIME", (Object)periodStr);
                        if (DimensionUtils.isExistAdjust((String)param.getEnvContext().getTaskKey())) {
                            currDimensionValueSet.setValue("ADJUST", (Object)selectAdjustCode);
                        }
                        singleOrgEnv.setDimensionValueSet(currDimensionValueSet);
                        singleOrgEnv.setFormDefine(formDefine);
                        singleOrgEnv.setDataRegionDefine(dataRegionDefine);
                        singleOrgEnv.setTableDefine(tableDefine);
                        singleOrgEnv.setOrg(orgToJsonVO);
                        singleOrgEnv.setSrcPeriod(param.getSrcPeriod());
                        singleOrgEnv.setSrcSelectAdjustCode(param.getSrcSelectAdjustCode());
                        singleOrgEnv.setAfterRealTimeOffset(Boolean.TRUE.equals(param.getAfterRealTimeOffset()));
                        singleOrgEnv.setSchemeId(param.getEnvContext().getFormSchemeKey());
                        singleOrgEnv.setTaskId(param.getEnvContext().getTaskKey());
                        orgAndFormContextEnvs.add(singleOrgEnv);
                    }));
                });
            });
        });
        return orgAndFormContextEnvs;
    }

    private Map<DataRegionDefine, Set<TableModelDefine>> getFormTableDefines(String formId) {
        List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formId);
        if (CollectionUtils.isEmpty((Collection)dataRegionDefines)) {
            return null;
        }
        HashMap<DataRegionDefine, Set<TableModelDefine>> map = new HashMap<DataRegionDefine, Set<TableModelDefine>>();
        dataRegionDefines.stream().filter(Objects::nonNull).forEach(dataRegionDefine -> {
            List deployInfos;
            Set mapValue = (Set)map.get(dataRegionDefine);
            if (mapValue == null) {
                map.put((DataRegionDefine)dataRegionDefine, (Set<TableModelDefine>)new TreeSet<TableModelDefine>(new Comparator<TableModelDefine>(){

                    @Override
                    public int compare(TableModelDefine o1, TableModelDefine o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                }));
            }
            if (CollectionUtils.isEmpty((Collection)(deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()).toArray(new String[0]))))) {
                return;
            }
            deployInfos.stream().filter(deployInfo -> {
                boolean isAllowPost;
                String dataTableKey = deployInfo.getDataTableKey();
                DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
                switch (dataTable.getKind()) {
                    case TABLE_KIND_DICTIONARY: 
                    case TABLE_KIND_ENTITY_PERIOD: 
                    case TABLE_KIND_MEASUREMENT_UNIT: 
                    case TABLE_KIND_SYSTEM: 
                    case TABLE_KIND_ENTITY: {
                        isAllowPost = false;
                        break;
                    }
                    default: {
                        isAllowPost = true;
                    }
                }
                if (dataTable.getCode().equalsIgnoreCase("GC_OFFSETVCHRITEM")) {
                    isAllowPost = false;
                }
                return isAllowPost;
            }).forEach(deployInfo -> {
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(deployInfo.getTableName());
                ((Set)map.get(dataRegionDefine)).add(tableModelDefine);
            });
        });
        if (map == null || map.size() == 0) {
            return null;
        }
        return map;
    }

    private Map<String, FormDefine> getFormDefinesByEnv(GcBatchCopyEnvDTO env) {
        List formDefines = null;
        try {
            formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(env.getParam().getEnvContext().getFormSchemeKey());
        }
        catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
        if (CollectionUtils.isEmpty((Collection)formDefines)) {
            return null;
        }
        Map formDefineMap = formDefines.stream().filter(Objects::nonNull).filter(formDefine -> {
            Optional<String> optional = env.getParam().getFormIds().stream().filter(formId -> formDefine.getKey().equals(formId)).findAny();
            boolean present = optional.isPresent();
            return present;
        }).collect(Collectors.toConcurrentMap(formDefine -> formDefine.getKey(), Function.identity()));
        return Collections.unmodifiableMap(formDefineMap);
    }

    private void fillAndCheckEnvArgs(GcBatchCopyEnvDTO envDTO) {
        String adjustCode;
        GcBatchCopyActionParamDTO param = envDTO.getParam();
        Map dimensionSet = param.getEnvContext().getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        DimensionValue adjustCodeValue = (DimensionValue)dimensionSet.get("ADJUST");
        String periodStr = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        String string = adjustCode = adjustCodeValue == null ? null : adjustCodeValue.getValue();
        if (CollectionUtils.isEmpty((Collection)param.getOrgIds())) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (StringUtils.isEmpty((String)param.getOrgVersionType())) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u7248\u672c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (CollectionUtils.isEmpty((Collection)param.getFormIds())) {
            throw new BusinessRuntimeException("\u62a5\u8868\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (CollectionUtils.isEmpty((Collection)param.getCurrencyIds())) {
            throw new BusinessRuntimeException("\u5e01\u79cd\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        Objects.requireNonNull(param.getSrcPeriod(), "\u6e90\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        if (param.getSrcPeriod().equals(periodStr) && param.getSrcSelectAdjustCode().equals(adjustCode)) {
            throw new BusinessRuntimeException("\u6279\u91cf\u590d\u5236\u7684\u6e90\u65f6\u671f\u548c\u5f53\u524d\u65f6\u671f\u503c\u4e0d\u5141\u8bb8\u76f8\u540c\u3002");
        }
    }

    private void createAsyncTask(GcBatchCopyEnvDTO env) {
        AsyncTaskMonitor asyncTaskMonitor = env.getAsyncTaskMonitor();
        if (asyncTaskMonitor == null) {
            return;
        }
        asyncTaskMonitor.progressAndMessage(0.0, "\u5f00\u59cb\u6279\u91cf\u590d\u5236\u4efb\u52a1");
    }

    private void modifyAsyncTaskState(GcBatchCopyEnvDTO env, TaskState taskState, double progress, String result) {
        AsyncTaskMonitor asyncTaskMonitor = env.getAsyncTaskMonitor();
        if (asyncTaskMonitor == null) {
            return;
        }
        asyncTaskMonitor.progressAndMessage(progress, result);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void dispatchBatchCopyItem(GcBatchCopyOrgAndFormTableEnvDTO batchCopyItemEnv, DefinitionsCache definitionsCache) throws Exception {
        TableModelRunInfo tableInfo = definitionsCache.getDataModelDefinitionsCache().getTableInfo(batchCopyItemEnv.getTableDefine().getName());
        String tableName = batchCopyItemEnv.getTableDefine().getName();
        if (StringUtils.isEmpty((String)tableName)) {
            return;
        }
        switch (tableName) {
            case "GC_OFFSETVCHRITEM": {
                break;
            }
            default: {
                if (tableName.contains("GC_INPUTDATA")) {
                    this.batchCopyItemByInputData(batchCopyItemEnv, tableInfo);
                    break;
                }
                this.batchCopyItemByNotInputData(batchCopyItemEnv, tableInfo);
            }
        }
    }

    public void batchCopyItemByInputData(GcBatchCopyOrgAndFormTableEnvDTO batchCopyItemEnv, TableModelRunInfo tableInfo) throws Exception {
        IDataTable srcDataTable;
        int srcTotalCount;
        String targetCurrency = ConverterUtils.getAsString((Object)batchCopyItemEnv.getDimensionValueSet().getValue("MD_CURRENCY"));
        Set fieldNames = tableInfo.getColumnFieldMap().keySet().stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
        Collection<FieldDefine> tableFieldDefines = tableInfo.getColumnFieldMap().values();
        Set dimFeildNames = tableInfo.getDimFields().stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
        DimensionValueSet targetDimensionValueSet = batchCopyItemEnv.getDimensionValueSet();
        Map srcDimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)targetDimensionValueSet);
        DimensionValueSet srcDimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)srcDimensionSet);
        srcDimensionValueSet.setValue("DATATIME", (Object)batchCopyItemEnv.getSrcPeriod());
        if (DimensionUtils.isExistAdjust((String)batchCopyItemEnv.getTaskId())) {
            srcDimensionValueSet.setValue("ADJUST", (Object)batchCopyItemEnv.getSrcSelectAdjustCode());
        }
        if ((srcTotalCount = (srcDataTable = this.getQueryDataTable(batchCopyItemEnv, srcDimensionValueSet, tableFieldDefines)).getTotalCount()) == 0) {
            return;
        }
        IDataTable targetDataTable = this.getQueryDataTable(batchCopyItemEnv, targetDimensionValueSet, tableFieldDefines);
        int targetTotalCount = targetDataTable.getTotalCount();
        if (targetTotalCount > 0) {
            targetDataTable.deleteAll();
        }
        FieldDefine idFieldDefine = null;
        for (String fieldNameStr : fieldNames) {
            String fieldName;
            FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, fieldNameStr);
            switch (fieldName = fieldNameStr.toUpperCase()) {
                case "ID": {
                    idFieldDefine = fieldDefine;
                    break;
                }
            }
        }
        for (int i = 0; i < srcTotalCount; ++i) {
            IDataRow srcDataTableItem = srcDataTable.getItem(i);
            String id = UUIDUtils.newUUIDStr();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)DimensionValueSetUtil.getDimensionSet((DimensionValueSet)targetDataTable.getMasterKeys()));
            dimFeildNames.stream().forEach(dimFieldName -> {
                FieldDefine dimFieldDefine = this.getFieldDefineByFieldName(tableInfo, (String)dimFieldName);
                String dimensionName = tableInfo.getDimensionName(dimFieldName);
                if (dimensionValueSet.getValue(dimensionName) == null) {
                    Object dimensionValue = srcDataTableItem.getValue(dimFieldDefine).getAsObject();
                    dimensionValueSet.setValue(dimensionName, dimensionValue);
                }
            });
            dimensionValueSet.setValue("RECORDKEY", (Object)id);
            dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
            IDataRow targetItem = targetDataTable.appendRow(dimensionValueSet);
            block37: for (String fieldNameStr : fieldNames) {
                String fieldName;
                FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, fieldNameStr);
                if (dimFeildNames.contains(fieldNameStr)) {
                    String dimensionName = tableInfo.getDimensionName(fieldNameStr);
                    Object masterKeyValue = targetDimensionValueSet.getValue(dimensionName);
                    targetItem.setValue(fieldDefine, masterKeyValue);
                    continue;
                }
                switch (fieldName = fieldNameStr.toUpperCase()) {
                    case "ID": 
                    case "BIZKEYORDER": {
                        targetItem.setValue(fieldDefine, (Object)id);
                        continue block37;
                    }
                    case "ORGID": {
                        targetItem.setValue(fieldDefine, (Object)batchCopyItemEnv.getOrg().getId());
                        continue block37;
                    }
                    case "MD_CURRENCY": {
                        targetItem.setValue(fieldDefine, (Object)targetCurrency);
                        continue block37;
                    }
                    case "SRCID": {
                        targetItem.setValue(fieldDefine, (Object)srcDataTableItem.getValue(idFieldDefine).getAsString());
                        continue block37;
                    }
                    case "CONVERTSRCID": {
                        targetItem.setValue(fieldDefine, null);
                        continue block37;
                    }
                    case "SRCTYPE": {
                        targetItem.setValue(fieldDefine, (Object)InputDataSrcTypeEnum.BATCHCOPY.getValue());
                        continue block37;
                    }
                    case "OFFSETTIME": {
                        targetItem.setValue(fieldDefine, null);
                        continue block37;
                    }
                    case "OFFSETPERSON": {
                        targetItem.setValue(fieldDefine, null);
                        continue block37;
                    }
                    case "OFFSETSTATE": {
                        targetItem.setValue(fieldDefine, (Object)0);
                        continue block37;
                    }
                    case "OFFSETGROUPID": {
                        targetItem.setValue(fieldDefine, null);
                        continue block37;
                    }
                    case "DIFFAMT": {
                        targetItem.setValue(fieldDefine, (Object)BigDecimal.ZERO);
                        continue block37;
                    }
                    case "OFFSETAMT": {
                        targetItem.setValue(fieldDefine, (Object)BigDecimal.ZERO);
                        continue block37;
                    }
                }
                AbstractData value = srcDataTableItem.getValue(fieldDefine);
                if (value.isNull) {
                    targetItem.setValue(fieldDefine, null);
                    continue;
                }
                targetItem.setValue(fieldDefine, value.getAsObject());
            }
        }
        targetDataTable.commitChanges(true);
    }

    public void batchCopyItemByNotInputData(GcBatchCopyOrgAndFormTableEnvDTO batchCopyItemEnv, TableModelRunInfo tableInfo) throws Exception {
        IDataTable srcDataTable;
        int srcTotalCount;
        Set fieldNames = tableInfo.getColumnFieldMap().keySet().stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
        Collection<FieldDefine> tableFieldDefines = tableInfo.getColumnFieldMap().values();
        Set<String> dimFeildNames = tableInfo.getDimFields().stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
        DimensionValueSet targetDimensionValueSet = batchCopyItemEnv.getDimensionValueSet();
        Map srcDimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)targetDimensionValueSet);
        DimensionValueSet srcDimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)srcDimensionSet);
        srcDimensionValueSet.setValue("DATATIME", (Object)batchCopyItemEnv.getSrcPeriod());
        if (DimensionUtils.isExistAdjust((String)batchCopyItemEnv.getTaskId())) {
            srcDimensionValueSet.setValue("ADJUST", (Object)batchCopyItemEnv.getSrcSelectAdjustCode());
        }
        if ((srcTotalCount = (srcDataTable = this.getQueryDataTable(batchCopyItemEnv, srcDimensionValueSet, tableFieldDefines)).getTotalCount()) == 0) {
            return;
        }
        IDataTable targetDataTable = this.getQueryDataTable(batchCopyItemEnv, targetDimensionValueSet, tableFieldDefines);
        int targetTotalCount = targetDataTable.getTotalCount();
        if (targetTotalCount > 0) {
            targetDataTable.deleteAll();
        }
        for (int i = 0; i < srcTotalCount; ++i) {
            IDataRow srcDataTableItem = srcDataTable.getItem(i);
            String id = UUIDUtils.newUUIDStr();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)DimensionValueSetUtil.getDimensionSet((DimensionValueSet)targetDataTable.getMasterKeys()));
            dimFeildNames.forEach(dimFieldName -> {
                FieldDefine dimFieldDefine = this.getFieldDefineByFieldName(tableInfo, (String)dimFieldName);
                String dimensionName = tableInfo.getDimensionName(dimFieldName);
                if (dimensionValueSet.getValue(dimensionName) == null) {
                    Object dimensionValue = srcDataTableItem.getValue(dimFieldDefine).getAsObject();
                    dimensionValueSet.setValue(dimensionName, dimensionValue);
                }
            });
            dimensionValueSet.setValue("RECORDKEY", (Object)id);
            dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
            IDataRow targetItem = targetDataTable.appendRow(dimensionValueSet);
            block8: for (String fieldNameStr : fieldNames) {
                String fieldName;
                FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, fieldNameStr);
                if (dimFeildNames.contains(fieldNameStr)) {
                    String dimensionName = tableInfo.getDimensionName(fieldNameStr);
                    Object masterKeyValue = targetDimensionValueSet.getValue(dimensionName);
                    targetItem.setValue(fieldDefine, masterKeyValue);
                    continue;
                }
                switch (fieldName = fieldNameStr.toUpperCase()) {
                    case "ID": 
                    case "BIZKEYORDER": {
                        targetItem.setValue(fieldDefine, (Object)id);
                        continue block8;
                    }
                }
                AbstractData value = srcDataTableItem.getValue(fieldDefine);
                if (value.isNull) {
                    targetItem.setValue(fieldDefine, null);
                    continue;
                }
                targetItem.setValue(fieldDefine, value.getAsObject());
            }
        }
        targetDataTable.commitChanges(true);
    }

    public IDataTable getQueryDataTable(GcBatchCopyOrgAndFormTableEnvDTO env, DimensionValueSet dimensionValueSet, Collection<FieldDefine> fieldDefines) throws Exception {
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        IEntityViewRunTimeController entityViewController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
        ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(runTimeViewController, dataDefinitionRuntimeController, entityViewController, env.getSchemeId());
        context.setEnv((IFmlExecEnvironment)environment);
        InputDataChangeMonitorEnvVo monitorEnvVo = new InputDataChangeMonitorEnvVo(false, env.isAfterRealTimeOffset());
        environment.getVariableManager().add(new Variable("INPUTDATA_CHANGEMONITOR_ENV_VO", "\u6279\u91cf\u590d\u5236\u8bbe\u7f6e\u5185\u90e8\u5f55\u5165\u8868\u6570\u636e\u53d8\u5316\u73af\u5883", 0, (Object)monitorEnvVo));
        context.setUseDnaSql(false);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(env.getSchemeId());
        queryEnvironment.setRegionKey(env.getDataRegionDefine().getKey());
        queryEnvironment.setFormKey(env.getFormDefine().getKey());
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        dataAccessProvider.newDataAssist(context);
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        fieldDefines.forEach(fieldDefine -> dataQuery.addColumn(fieldDefine));
        dataQuery.setMasterKeys(dimensionValueSet);
        IDataTable beforeDataTable = dataQuery.executeQuery(context);
        return beforeDataTable;
    }

    public DefinitionsCache getDefinitionsCache() throws ParseException {
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
        DefinitionsCache definitionsCache = new DefinitionsCache(context);
        return definitionsCache;
    }

    protected FieldDefine getFieldDefineByFieldName(TableModelRunInfo tableInfo, String fieldName) {
        ColumnModelDefine columnModelDefine = tableInfo.getFieldByName(fieldName);
        FieldDefine fieldDefine = (FieldDefine)tableInfo.getColumnFieldMap().get(columnModelDefine);
        return fieldDefine;
    }
}

