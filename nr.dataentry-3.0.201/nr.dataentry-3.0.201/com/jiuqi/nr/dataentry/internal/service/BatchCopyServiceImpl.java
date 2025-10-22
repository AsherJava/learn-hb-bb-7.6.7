/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$NoAccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.datacopy.DataCopyManager
 *  com.jiuqi.nr.data.engine.datacopy.param.DataCopyParam
 *  com.jiuqi.nr.datacrud.ClearInfoBuilder
 *  com.jiuqi.nr.datacrud.IClearInfo
 *  com.jiuqi.nr.datacrud.ISaveInfo
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveData
 *  com.jiuqi.nr.datacrud.SaveDataBuilder
 *  com.jiuqi.nr.datacrud.SaveDataBuilderFactory
 *  com.jiuqi.nr.datacrud.api.IDataService
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.datastatus.facade.obj.ICopySetting
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.data.DateTimeData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.jtable.exception.NotFoundEntityException
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.params.output.BatchCopyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.CopyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.RegionDataSet
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableDataQueryService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DataCrudUtil
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.engine.datacopy.DataCopyManager;
import com.jiuqi.nr.data.engine.datacopy.param.DataCopyParam;
import com.jiuqi.nr.datacrud.ClearInfoBuilder;
import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.api.IDataService;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.attachment.util.AuthorityJudgmentUtil;
import com.jiuqi.nr.dataentry.internal.service.CurrencyService;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.internal.service.SubAsyncTaskProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCopyInfo;
import com.jiuqi.nr.dataentry.paramInfo.CopyStatusInfo;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.service.IBatchCopyService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.datastatus.facade.obj.ICopySetting;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.data.DateTimeData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.jtable.exception.NotFoundEntityException;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.BatchCopyReturnInfo;
import com.jiuqi.nr.jtable.params.output.CopyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableDataQueryService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BatchCopyServiceImpl
implements IBatchCopyService {
    private static final Logger logger = LoggerFactory.getLogger(BatchCopyServiceImpl.class);
    private String periodDimensionName;
    @Resource
    IRunTimeViewController runtimeView;
    @Autowired
    IJtableEntityService jtableEntityService;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    DataCopyManager dataCopyManager;
    @Autowired
    IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    IFormSchemeService iFormSchemeService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataStatusService dataStatusService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IDataService dataService;
    @Autowired
    private IJtableDataQueryService jtableDataQueryService;
    @Autowired
    private SaveDataBuilderFactory saveDataBuilderFactory;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IFMDMDataService ifmdmDataService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private ExportExcelNameService exportExcelNameService;

    @Override
    public void batchCopyForm(BatchCopyInfo batchCopyInfo, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext jtableContext = batchCopyInfo.getContext();
        BatchCopyReturnInfo batchCopyReturnInfo = new BatchCopyReturnInfo();
        batchCopyReturnInfo.setSourcePeriod(batchCopyInfo.getSourcePeriod());
        HashMap<String, String> dimensionNumSet = new HashMap<String, String>();
        Map allDimensionSet = jtableContext.getDimensionSet();
        for (DimensionValue value : allDimensionSet.values()) {
            if (StringUtils.isEmpty((String)value.getValue())) {
                dimensionNumSet.put(value.getName(), "showAll");
                continue;
            }
            dimensionNumSet.put(value.getName(), Integer.toString(value.getValue().split(";").length));
        }
        if (StringUtils.isEmpty((String)batchCopyInfo.getFormKeys())) {
            batchCopyReturnInfo.setCopyForms("showAll");
        } else {
            batchCopyReturnInfo.setCopyForms(Integer.toString(batchCopyInfo.getFormKeys().split(";").length));
        }
        batchCopyReturnInfo.setDimensionNum(dimensionNumSet);
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
        }
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        List<String> currOrgList = this.getContextOrgList(jtableContext, queryEntity.getDimensionName());
        double startProgress = 0.2;
        double endProgress = 0.95;
        double onrOrgProgress = 0.75 / (double)currOrgList.size();
        boolean isEnableNrdb = this.nrdbHelper.isEnableNrdb();
        ArrayList<String> exeCopyList = new ArrayList<String>();
        for (int orgNum = 0; orgNum < currOrgList.size(); ++orgNum) {
            Object[] dwArr;
            String currentOrgTitle = "";
            if (asyncTaskMonitor.isCancel()) {
                if (exeCopyList.size() > 0) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", (Object)"copyOrgCancel");
                    JSONObject param = new JSONObject();
                    param.put("num", exeCopyList.size());
                    jsonObject.put("param", (Object)param);
                    asyncTaskMonitor.canceled(jsonObject.toString(), (Object)"");
                } else {
                    asyncTaskMonitor.canceled("stop_execute", (Object)"");
                }
                LogHelper.info((String)"\u6279\u91cf\u590d\u5236", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                return;
            }
            JtableContext temCurrContext = new JtableContext(jtableContext);
            ((DimensionValue)temCurrContext.getDimensionSet().get(queryEntity.getDimensionName())).setValue(currOrgList.get(orgNum));
            this.formatCurrency(temCurrContext);
            String value = ((DimensionValue)temCurrContext.getDimensionSet().get(queryEntity.getDimensionName())).getValue();
            if (value != null && value.contains(";")) {
                dwArr = value.split(";");
                Arrays.sort(dwArr);
            } else if (value == null || value.equals("")) {
                List<IEntityRow> entityDataList = this.getEntityDataList(temCurrContext.getFormSchemeKey(), temCurrContext.getDimensionSet());
                if (entityDataList != null) {
                    dwArr = new String[entityDataList.size()];
                    int i = 0;
                    for (IEntityRow iEntityRow : entityDataList) {
                        dwArr[i] = iEntityRow.getCode();
                        ++i;
                    }
                    Arrays.sort(dwArr);
                } else {
                    dwArr = new String[]{};
                }
            } else {
                dwArr = new String[]{value};
                currentOrgTitle = this.getEntityDataList(temCurrContext.getFormSchemeKey(), temCurrContext.getDimensionSet()).get(0).getTitle();
            }
            Map dimensionSet = temCurrContext.getDimensionSet();
            String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
            this.periodDimensionName = this.dataAccesslUtil.getPeriodDimensionName(entityId);
            String period = ((DimensionValue)dimensionSet.get(this.periodDimensionName)).getValue();
            String dateTime = formScheme.getDateTime();
            LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
            logDimensionCollection.setPeriod(dateTime, period);
            logDimensionCollection.setDw(entityId, (String[])dwArr);
            DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6570\u636e\u5f55\u5165", OperLevel.USER_OPER);
            logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u6279\u91cf\u590d\u5236\u5f00\u59cb", "\u6279\u91cf\u590d\u5236\u5f00\u59cb");
            EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
            EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
            if (null == targetEntityInfo || null == periodEntityInfo) {
                throw new NotFoundEntityException(new String[]{null == targetEntityInfo ? "\u672a\u627e\u5230\u5355\u4f4d\u4e3b\u4f53" : "\u672a\u627e\u5230\u65f6\u671f\u4e3b\u4f53"});
            }
            batchCopyReturnInfo.setToPeriod(((DimensionValue)temCurrContext.getDimensionSet().get(periodEntityInfo.getDimensionName())).getValue());
            IDataAccessFormService dataFormAccess = this.dataAccessServiceProvider.getDataAccessFormService();
            String formKeyStr = batchCopyInfo.getFormKeys();
            List<Object> formKeys = new ArrayList();
            if (org.springframework.util.StringUtils.hasLength(formKeyStr)) {
                formKeys = Arrays.asList(formKeyStr.split(";"));
            }
            if (formKeys.size() == 0) {
                formKeys = this.getAllFormKeys(temCurrContext.getFormSchemeKey());
            }
            List allFormdefine = this.iRunTimeViewController.queryFormsById(formKeys);
            formKeys = allFormdefine.stream().filter(form -> !form.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS) && !form.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) && !form.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) && !form.getFormType().equals((Object)FormType.FORM_TYPE_FMDM)).map(item -> item.getKey()).collect(Collectors.toList());
            AccessFormParam accessFormParam = new AccessFormParam();
            accessFormParam.setCollectionMasterKey(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)dimensionSet, (String)temCurrContext.getFormSchemeKey()));
            accessFormParam.setTaskKey(temCurrContext.getTaskKey());
            accessFormParam.setFormSchemeKey(temCurrContext.getFormSchemeKey());
            accessFormParam.setFormKeys(formKeys);
            accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_WRITE);
            DimensionAccessFormInfo batchDimensionValueFormInfo = dataFormAccess.getBatchAccessForms(accessFormParam);
            List acessFormInfos = batchDimensionValueFormInfo.getAccessForms();
            List noAccessReasons = batchDimensionValueFormInfo.getNoAccessForms();
            if (batchCopyInfo.getSourcePeriodDim().containsKey("ADJUST")) {
                List toAdjust;
                List sourceAdjust;
                List adjustPeriods = this.iFormSchemeService.queryAdjustPeriods(temCurrContext.getFormSchemeKey());
                List collect = adjustPeriods.stream().filter(item -> !item.getCode().equals("0")).collect(Collectors.toList());
                if (collect.size() > 0 && !batchCopyInfo.getSourcePeriodDim().get("ADJUST").getValue().equals("0") && (sourceAdjust = collect.stream().filter(item -> item.getCode().equals(batchCopyInfo.getSourcePeriodDim().get("ADJUST").getValue()) && item.getPeriod().equals(batchCopyInfo.getSourcePeriod())).collect(Collectors.toList())).size() > 0) {
                    dimensionNumSet.put("sourceAdjust", ((AdjustPeriod)sourceAdjust.get(0)).getTitle());
                }
                if (collect.size() > 0 && !((DimensionValue)dimensionSet.get("ADJUST")).getValue().equals("0") && (toAdjust = collect.stream().filter(item -> item.getCode().equals(((DimensionValue)dimensionSet.get("ADJUST")).getValue()) && item.getPeriod().equals(((DimensionValue)dimensionSet.get(periodEntityInfo.getDimensionName())).getValue())).collect(Collectors.toList())).size() > 0) {
                    dimensionNumSet.put("toAdjust", ((AdjustPeriod)toAdjust.get(0)).getTitle());
                }
            }
            for (int i = 0; i < acessFormInfos.size(); ++i) {
                StringBuilder logMessage = new StringBuilder();
                if (((DimensionAccessFormInfo.AccessFormInfo)acessFormInfos.get(i)).getFormKeys().size() > 0) {
                    logMessage.append("\u590d\u5236\u62a5\u8868:");
                    List formDefines = this.iRunTimeViewController.queryFormsById(((DimensionAccessFormInfo.AccessFormInfo)acessFormInfos.get(i)).getFormKeys());
                    for (FormDefine formDefine : formDefines) {
                        logMessage.append(formDefine.getTitle() + "\u3001");
                    }
                    logMessage.delete(logMessage.length() - 1, logMessage.length());
                }
                DimensionAccessFormInfo.AccessFormInfo dimensionValueFormInfo = (DimensionAccessFormInfo.AccessFormInfo)acessFormInfos.get(i);
                Object[] arr = ((DimensionValue)dimensionValueFormInfo.getDimensions().get(queryEntity.getDimensionName())).getValue().split(";");
                Arrays.sort(arr);
                logDimensionCollection.setDw(entityId, (String[])arr);
                Map dimensionValue = dimensionValueFormInfo.getDimensions();
                List forms = dimensionValueFormInfo.getFormKeys();
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
                String periodCode = dimensionValueSet.getValue(periodEntityInfo.getDimensionName()).toString();
                temCurrContext.setDimensionSet(dimensionValue);
                try {
                    CopyReturnInfo copyReturnInfo = new CopyReturnInfo();
                    ArrayList<DataRegionDefine> regionDefines = new ArrayList<DataRegionDefine>();
                    ArrayList<DataRegionDefine> noFilterFloatRegionDefines = new ArrayList<DataRegionDefine>();
                    for (String formKey : forms) {
                        List dataRegionDefines = this.runtimeView.getAllRegionsInForm(formKey);
                        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                            if (isEnableNrdb) {
                                regionDefines.add(dataRegionDefine);
                                continue;
                            }
                            if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && (Objects.isNull(dataRegionDefine.getFilterCondition()) || Objects.equals(dataRegionDefine.getFilterCondition(), ""))) {
                                noFilterFloatRegionDefines.add(dataRegionDefine);
                                continue;
                            }
                            regionDefines.add(dataRegionDefine);
                        }
                    }
                    double stepOneStartProgress = 0.2 + onrOrgProgress * (double)orgNum;
                    double stepTwoEndProgress = stepOneStartProgress + onrOrgProgress;
                    double progress = (stepTwoEndProgress - stepOneStartProgress) / (double)(regionDefines.size() + noFilterFloatRegionDefines.size());
                    double stepOneEndProgress = stepOneStartProgress + progress * (double)regionDefines.size();
                    if (!regionDefines.isEmpty()) {
                        SubAsyncTaskProgressMonitor stepOneMonitor = new SubAsyncTaskProgressMonitor(asyncTaskMonitor, copyReturnInfo);
                        JtableContext sourceContext = new JtableContext(temCurrContext);
                        ((DimensionValue)sourceContext.getDimensionSet().get(this.periodDimensionName)).setValue(batchCopyInfo.getSourcePeriod());
                        sourceContext.getMeasureMap().clear();
                        if (batchCopyInfo.getSourcePeriodDim().containsKey("ADJUST")) {
                            ((DimensionValue)sourceContext.getDimensionSet().get("ADJUST")).setValue(batchCopyInfo.getSourcePeriodDim().get("ADJUST").getValue());
                        }
                        this.copyRegionData(temCurrContext, sourceContext, regionDefines, (IMonitor)stepOneMonitor, stepOneStartProgress, stepOneEndProgress);
                    }
                    if (!noFilterFloatRegionDefines.isEmpty()) {
                        SubAsyncTaskProgressMonitor stepTwoMonitor = new SubAsyncTaskProgressMonitor(asyncTaskMonitor, copyReturnInfo);
                        DataCopyParam dataCopyParam = new DataCopyParam();
                        dataCopyParam.setCurrDimValueSet(dimensionValueSet);
                        dataCopyParam.setSourceDimValueSet(DimensionValueSetUtil.getDimensionValueSet(batchCopyInfo.getSourcePeriodDim()));
                        dataCopyParam.setFormKeys(forms);
                        dataCopyParam.setTaskKey(temCurrContext.getTaskKey());
                        this.dataCopyManager.dataCopy(dataCopyParam, noFilterFloatRegionDefines, (IMonitor)stepTwoMonitor, stepOneEndProgress, stepTwoEndProgress);
                    }
                    CopyStatusInfo copyStatusInfo = new CopyStatusInfo(formScheme.getKey(), forms, this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, formScheme.getKey()), batchCopyInfo.getSourcePeriodDim().get("DATATIME").getValue(), batchCopyInfo.getSourcePeriodDim().containsKey("ADJUST") ? batchCopyInfo.getSourcePeriodDim().get("ADJUST").getValue() : null);
                    this.dataStatusService.copyDataStatus((ICopySetting)copyStatusInfo);
                    if (copyReturnInfo.getMessage().size() > 0) {
                        String[] dimensionSplit = ((DimensionValue)dimensionValue.get(targetEntityInfo.getDimensionName())).getValue().split(";");
                        for (int j = 0; j < dimensionSplit.length; ++j) {
                            CopyReturnInfo copyReturnInfoNew = new CopyReturnInfo();
                            EntityByKeyReturnInfo queryEntityDataByKey = new EntityByKeyReturnInfo();
                            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                            entityQueryByKeyInfo.setContext(temCurrContext);
                            entityQueryByKeyInfo.setEntityKey(dimensionSplit[j]);
                            entityQueryByKeyInfo.setEntityViewKey(targetEntityInfo.getKey());
                            queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                            copyReturnInfoNew.getMessage().addAll(copyReturnInfo.getMessage());
                            copyReturnInfoNew.setCopyEntity(queryEntityDataByKey.getEntity());
                            batchCopyReturnInfo.getCopyMessage().add(copyReturnInfoNew);
                        }
                    }
                    logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u6279\u91cf\u590d\u5236\u6267\u884c\u6210\u529f", "\u6279\u91cf\u590d\u5236\u6267\u884c\u6210\u529f" + logMessage);
                    continue;
                }
                catch (Exception e) {
                    logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u6279\u91cf\u590d\u5236\u6267\u884c\u5931\u8d25", "\u6279\u91cf\u590d\u5236\u6267\u884c\u5931\u8d25" + logMessage);
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            for (DimensionAccessFormInfo.NoAccessFormInfo noAccessFormInfo : noAccessReasons) {
                if (noAccessFormInfo.getReason().equals("\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6")) continue;
                EntityByKeyReturnInfo queryEntityDataByKey = new EntityByKeyReturnInfo();
                String dimensionValues = ((DimensionValue)noAccessFormInfo.getDimensions().get(targetEntityInfo.getDimensionName())).getValue();
                EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                entityQueryByKeyInfo.setContext(temCurrContext);
                entityQueryByKeyInfo.setEntityKey(dimensionValues);
                entityQueryByKeyInfo.setEntityViewKey(targetEntityInfo.getKey());
                queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                List copyMessage = batchCopyReturnInfo.getCopyMessage();
                int copyMesNum = 0;
                for (CopyReturnInfo copyMes : copyMessage) {
                    if (copyMes.getCopyEntity() == null || copyMes.getCopyEntity().getId() != queryEntityDataByKey.getEntity().getId()) continue;
                    String oneFormKeyReason = noAccessFormInfo.getReason();
                    FormData form2 = this.jtableParamService.getReport(noAccessFormInfo.getFormKey(), formScheme.getKey());
                    copyMes.getMessage().add(form2.getTitle() + ":" + oneFormKeyReason);
                    ++copyMesNum;
                }
                if (copyMesNum != 0) continue;
                CopyReturnInfo copyReturnInfoNew = new CopyReturnInfo();
                String oneFormKeyReason = noAccessFormInfo.getReason();
                if (((DimensionValue)noAccessFormInfo.getDimensions().get(targetEntityInfo.getDimensionName())).getValue().contains(noAccessFormInfo.getFormKey())) {
                    copyReturnInfoNew.setCopyEntity(queryEntityDataByKey.getEntity());
                    copyReturnInfoNew.getMessage().add("\u5355\u4f4d" + oneFormKeyReason);
                } else {
                    FormData form3 = this.jtableParamService.getReport(noAccessFormInfo.getFormKey(), formScheme.getKey());
                    copyReturnInfoNew.setCopyForm(form3);
                    copyReturnInfoNew.setCopyEntity(queryEntityDataByKey.getEntity());
                    copyReturnInfoNew.getMessage().add(form3.getTitle() + ":" + oneFormKeyReason);
                }
                batchCopyReturnInfo.getCopyMessage().add(copyReturnInfoNew);
            }
            exeCopyList.add(currentOrgTitle);
        }
        String retStr = "";
        batchCopyReturnInfo.setSourcePeriodTitle(this.exportExcelNameService.getPeriodTitle(batchCopyInfo.getContext().getFormSchemeKey(), batchCopyReturnInfo.getSourcePeriod()));
        batchCopyReturnInfo.setToPeriodTitle(this.exportExcelNameService.getPeriodTitle(batchCopyInfo.getContext().getFormSchemeKey(), batchCopyReturnInfo.getToPeriodTitle()));
        try {
            ObjectMapper mapper = new ObjectMapper();
            retStr = mapper.writeValueAsString((Object)batchCopyReturnInfo);
        }
        catch (JsonProcessingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (asyncTaskMonitor != null) {
            String batchCopy = "batch_copy_success_info";
            asyncTaskMonitor.finish(batchCopy, (Object)retStr);
        }
    }

    private List<IEntityRow> getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(formSchemeKey);
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueSet));
            EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.setAuthorityOperations(AuthorityType.Modify);
            iEntityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, new ExecutorContext(this.dataDefinitionRuntimeController), entityViewDefine, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (iEntityTable != null) {
            return iEntityTable.getAllRows();
        }
        return new ArrayList<IEntityRow>();
    }

    private List<String> getAllFormKeys(String formSchemeKey) {
        List<String> allformKeys = new ArrayList<String>();
        List queryAllFormDefinesByFormScheme = null;
        try {
            List queryRootGroupsByFormScheme = this.iRunTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
            if (CollectionUtils.isEmpty(queryRootGroupsByFormScheme)) {
                queryAllFormDefinesByFormScheme = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
            } else {
                queryAllFormDefinesByFormScheme = new ArrayList();
                for (FormGroupDefine formGroupDefine : queryRootGroupsByFormScheme) {
                    List allFormsInGroup = this.iRunTimeViewController.getAllFormsInGroupWithoutOrder(formGroupDefine.getKey());
                    if (allFormsInGroup == null) continue;
                    queryAllFormDefinesByFormScheme.addAll(allFormsInGroup);
                }
            }
            if (null != queryAllFormDefinesByFormScheme) {
                allformKeys = queryAllFormDefinesByFormScheme.stream().map(form -> form.getKey()).distinct().collect(Collectors.toList());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return allformKeys;
    }

    private void copyRegionData(JtableContext currJtableContext, JtableContext sourceContext, List<DataRegionDefine> dataRegionDefines, IMonitor monitor, double startProgress, double endProgress) {
        double progress = (endProgress - startProgress) / (double)dataRegionDefines.size();
        String formulaSchemeKey = currJtableContext.getFormulaSchemeKey();
        monitor.onProgress(startProgress);
        monitor.message("\u5f00\u59cb\u590d\u5236\u201c\u56fa\u5b9a\u533a\u57df+\u65e0\u8fc7\u6ee4\u6761\u4ef6\u6d6e\u52a8\u533a\u57df\u201d\u6570\u636e", (Object)this);
        monitor.start();
        for (int i = 0; i < dataRegionDefines.size(); ++i) {
            ArrayList<ReturnRes> returnResList = new ArrayList<ReturnRes>();
            String formKey = dataRegionDefines.get(i).getFormKey();
            String regionKey = dataRegionDefines.get(i).getKey();
            String regionTitle = dataRegionDefines.get(i).getTitle();
            currJtableContext.setFormKey(formKey);
            sourceContext.setFormKey(formKey);
            RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
            regionQueryInfo.setContext(sourceContext);
            regionQueryInfo.setRegionKey(regionKey);
            regionQueryInfo.getRestructureInfo().setCustomGrade(true);
            regionQueryInfo.getRestructureInfo().setNoSumData(true);
            RegionDataSet regionDataSet = this.jtableDataQueryService.queryRegionDatas(regionQueryInfo);
            IFMDMData ifmdmData = null;
            if (regionDataSet.getTotalCount() > 0) {
                int state;
                Map cells = regionDataSet.getCells();
                List dataList = regionDataSet.getData();
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)currJtableContext);
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
                SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createSaveDataBuilder(regionKey, dimensionCombinationBuilder.getCombination());
                if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
                    saveDataBuilder.setFormulaSchemeKey(formulaSchemeKey);
                }
                List bizKeyOrderFields = new ArrayList();
                List bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionKey, currJtableContext);
                if (bizKeyOrderFieldList != null && !bizKeyOrderFieldList.isEmpty()) {
                    bizKeyOrderFields = (List)bizKeyOrderFieldList.get(0);
                }
                List linkKeys = (List)cells.get(regionKey);
                int rowIDIndex = linkKeys.indexOf("ID");
                ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
                boolean containZb = false;
                for (String linkKey : linkKeys) {
                    int index = saveDataBuilder.addLink(linkKey);
                    linkIndexs.add(index);
                    if (dataRegionDefines.get(i).getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE || this.runtimeView.queryDataLinkDefine(linkKey).getType() != DataLinkType.DATA_LINK_TYPE_FMDM) continue;
                    containZb = true;
                }
                if (containZb) {
                    FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
                    fmdmDataDTO.setDimensionValueSet(dimensionValueSet);
                    fmdmDataDTO.setFormSchemeKey(currJtableContext.getFormSchemeKey());
                    List list = this.ifmdmDataService.list(fmdmDataDTO);
                    ifmdmData = (IFMDMData)list.get(0);
                }
                if (dataRegionDefines.get(i).getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                    state = 0;
                } else {
                    state = 1;
                    RegionQueryInfo currRegionQueryInfo = new RegionQueryInfo();
                    currRegionQueryInfo.setContext(currJtableContext);
                    currRegionQueryInfo.setRegionKey(regionKey);
                    RegionDataSet currRegionDataSet = this.jtableDataQueryService.queryRegionDatas(currRegionQueryInfo);
                    if (currRegionDataSet.getTotalCount() > 0) {
                        IClearInfo clearInfo = ClearInfoBuilder.create((String)regionKey, (DimensionCombination)dimensionCombinationBuilder.getCombination()).build();
                        ReturnRes returnRes = new ReturnRes();
                        try {
                            returnRes = this.dataService.clearRegionData(clearInfo);
                        }
                        catch (Exception e) {
                            monitor.error("\u5220\u9664\u6d6e\u52a8\u533a\u57df\u201c" + regionTitle + "\u201d\u539f\u6709\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef\uff1a" + returnRes.getMessage(), (Object)this);
                            monitor.exception(e);
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                    }
                    Iterator iterator = bizKeyOrderFields.iterator();
                    while (iterator.hasNext()) {
                        FieldData fieldData = (FieldData)iterator.next();
                        if (!"BIZKEYORDER".equals(fieldData.getFieldCode())) continue;
                        iterator.remove();
                        break;
                    }
                }
                for (List datas : dataList) {
                    if (dataRegionDefines.get(i).getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                        saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), state);
                    } else {
                        DimensionCombinationBuilder temDcBuilder = new DimensionCombinationBuilder(dimensionValueSet);
                        if (dataRegionDefines.get(i).getAllowDuplicateKey()) {
                            temDcBuilder.setValue("RECORDKEY", (Object)UUID.randomUUID().toString());
                        }
                        DataCrudUtil.setBizKeyValueForDimension((DimensionCombinationBuilder)temDcBuilder, (String)((String)datas.get(rowIDIndex)), bizKeyOrderFields);
                        saveDataBuilder.addRow(temDcBuilder.getCombination(), state);
                    }
                    for (int j = 0; j < datas.size(); ++j) {
                        ReturnRes returnRes;
                        Object data = datas.get(j);
                        if (data != null && data.toString().isEmpty()) {
                            data = null;
                        }
                        if (ifmdmData != null && this.runtimeView.queryDataLinkDefine((String)linkKeys.get(j)).getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                            String linkExpression = this.runtimeView.queryDataLinkDefine((String)linkKeys.get(j)).getLinkExpression();
                            AbstractData abstractData = ifmdmData.getEntityValue(linkExpression);
                            if (abstractData instanceof DateTimeData) {
                                abstractData = AbstractData.valueOf((Object)abstractData.getAsDateObj(), (int)5);
                            }
                            returnRes = "PARENTCODE".equals(linkExpression) && "-".equals(abstractData.getAsString()) ? saveDataBuilder.setData(((Integer)linkIndexs.get(j)).intValue(), null) : saveDataBuilder.setData(((Integer)linkIndexs.get(j)).intValue(), (Object)abstractData.getAsString());
                        } else {
                            if (j == rowIDIndex) continue;
                            returnRes = saveDataBuilder.setData(((Integer)linkIndexs.get(j)).intValue(), data);
                        }
                        returnResList.add(returnRes);
                    }
                }
                ISaveInfo saveInfo = saveDataBuilder.build();
                try {
                    SaveData saveData = saveInfo.getSaveData();
                    if (saveData.getRowCount() > 0) {
                        this.dataService.saveRegionData(saveInfo);
                    }
                }
                catch (Exception e) {
                    StringBuilder message = new StringBuilder("\u590d\u5236\u533a\u57df\u201c" + regionTitle + "\u201d\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef\uff1a");
                    if (!returnResList.isEmpty()) {
                        int erroeNum = 1;
                        for (ReturnRes returnRes : returnResList) {
                            if (!Objects.nonNull(returnRes.getMessage())) continue;
                            message.append("\n").append(erroeNum).append(". ").append(returnRes.getMessage());
                            ++erroeNum;
                        }
                    }
                    monitor.error(message.toString(), (Object)this);
                    monitor.exception(new Exception(message.toString()));
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            monitor.onProgress(startProgress + progress * (double)(i + 1));
            monitor.message("\u590d\u5236\u533a\u57df\u201c" + regionTitle + "\u201d\u8fdb\u884c\u4e2d,\u8fdb\u5ea6" + (startProgress + progress * (double)(i + 1)) * 100.0 + "%", (Object)this);
        }
        monitor.onProgress(endProgress);
        monitor.message("\u590d\u5236\u201c\u56fa\u5b9a\u533a\u57df+\u65e0\u8fc7\u6ee4\u6761\u4ef6\u6d6e\u52a8\u533a\u57df\u201d\u6570\u636e\u5df2\u5b8c\u6210!", (Object)this);
        monitor.finish();
    }

    private List<String> getContextOrgList(JtableContext jtableContext, String dimensionName) {
        ArrayList<String> orgList = new ArrayList<String>();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext);
        Object orgs = dimensionValueSet.getValue(dimensionName);
        if (orgs instanceof String) {
            orgList.add((String)orgs);
        } else {
            orgList = (List)orgs;
        }
        return orgList;
    }

    private AuthorityJudgmentUtil getAuthorityJudgmentUtil(JtableContext jtableContext) {
        ArrayList<Consts.FormAccessLevel> formAccessLevels = new ArrayList<Consts.FormAccessLevel>();
        formAccessLevels.add(Consts.FormAccessLevel.FORM_READ);
        formAccessLevels.add(Consts.FormAccessLevel.FORM_DATA_WRITE);
        List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(jtableContext.getFormSchemeKey());
        return new AuthorityJudgmentUtil(jtableContext, formDefines, formAccessLevels);
    }

    private Map<String, DimensionValue> cloneDimensionSet(Map<String, DimensionValue> dimensionSet) {
        HashMap<String, DimensionValue> newDimensionSet = new HashMap<String, DimensionValue>();
        if (dimensionSet != null) {
            for (String dimensionName : dimensionSet.keySet()) {
                DimensionValue dimensionValue = dimensionSet.get(dimensionName);
                newDimensionSet.put(dimensionName, new DimensionValue(dimensionValue));
            }
        }
        return newDimensionSet;
    }

    private void formatCurrency(JtableContext jtableContext) {
        if (jtableContext.getDimensionSet().containsKey("MD_CURRENCY") && (((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue().equals("PROVIDER_BASECURRENCY") || ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue().equals("PROVIDER_PBASECURRENCY"))) {
            JtableContext newJtableContext = new JtableContext(jtableContext);
            newJtableContext.setDimensionSet(jtableContext.getDimensionSet());
            List<String> currencyInfo = this.currencyService.getCurrencyInfo(newJtableContext, ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue());
            if (currencyInfo != null && !currencyInfo.isEmpty()) {
                ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).setValue(currencyInfo.get(0));
            }
        }
    }
}

