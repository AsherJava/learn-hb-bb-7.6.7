/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.engine.datacopy.DataCopyManager
 *  com.jiuqi.nr.data.logic.api.ICKDCopyService
 *  com.jiuqi.nr.data.logic.api.IDataLogicServiceFactory
 *  com.jiuqi.nr.data.logic.api.param.ckdcopy.CopyDesParam
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.data.logic.spi.ICKDCopyOptionProvider
 *  com.jiuqi.nr.data.logic.spi.IFmlMappingProvider
 *  com.jiuqi.nr.data.logic.spi.IUnsupportedDesHandler
 *  com.jiuqi.nr.datacopy.factory.IDataCopyServiceFactory
 *  com.jiuqi.nr.datacopy.param.CopyDataParam
 *  com.jiuqi.nr.datacopy.param.DataCopyParamProvider
 *  com.jiuqi.nr.datacopy.param.DataCopyReturnInfo
 *  com.jiuqi.nr.datacopy.param.monitor.IDataCopyMonitor
 *  com.jiuqi.nr.datacopy.service.IDataCopyService
 *  com.jiuqi.nr.datacrud.ClearInfoBuilder
 *  com.jiuqi.nr.datacrud.IClearInfo
 *  com.jiuqi.nr.datacrud.ISaveInfo
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveData
 *  com.jiuqi.nr.datacrud.SaveDataBuilder
 *  com.jiuqi.nr.datacrud.SaveDataBuilderFactory
 *  com.jiuqi.nr.datacrud.api.IDataService
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 *  com.jiuqi.nr.dataservice.core.common.DimensionMapInfo
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.common.ProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.data.DateTimeData
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.params.output.BatchCopyBTWEntityReturnInfo
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
 *  com.jiuqi.xlib.utils.GUID
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.engine.datacopy.DataCopyManager;
import com.jiuqi.nr.data.logic.api.ICKDCopyService;
import com.jiuqi.nr.data.logic.api.IDataLogicServiceFactory;
import com.jiuqi.nr.data.logic.api.param.ckdcopy.CopyDesParam;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.spi.ICKDCopyOptionProvider;
import com.jiuqi.nr.data.logic.spi.IFmlMappingProvider;
import com.jiuqi.nr.data.logic.spi.IUnsupportedDesHandler;
import com.jiuqi.nr.datacopy.factory.IDataCopyServiceFactory;
import com.jiuqi.nr.datacopy.param.CopyDataParam;
import com.jiuqi.nr.datacopy.param.DataCopyParamProvider;
import com.jiuqi.nr.datacopy.param.DataCopyReturnInfo;
import com.jiuqi.nr.datacopy.param.monitor.IDataCopyMonitor;
import com.jiuqi.nr.datacopy.service.IDataCopyService;
import com.jiuqi.nr.datacrud.ClearInfoBuilder;
import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.api.IDataService;
import com.jiuqi.nr.dataentry.copydes.ICopyDesService;
import com.jiuqi.nr.dataentry.copydes.IExtensionFactory;
import com.jiuqi.nr.dataentry.internal.service.BatchCopyServiceImpl;
import com.jiuqi.nr.dataentry.internal.service.CurrencyService;
import com.jiuqi.nr.dataentry.monitor.DataCopyBTWMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCopyBTWEntityInfo;
import com.jiuqi.nr.dataentry.service.IBatchCopyBetweenEntityService;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import com.jiuqi.nr.dataservice.core.common.DimensionMapInfo;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.common.ProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.data.DateTimeData;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.BatchCopyBTWEntityReturnInfo;
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
import com.jiuqi.xlib.utils.GUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BatchCopyBetweenEntityServiceImpl
implements IBatchCopyBetweenEntityService {
    private static final Logger logger = LoggerFactory.getLogger(BatchCopyServiceImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private IJtableDataQueryService jtableDataQueryService;
    @Autowired
    private SaveDataBuilderFactory saveDataBuilderFactory;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IFMDMDataService ifmdmDataService;
    @Autowired
    private IDataService dataService;
    @Autowired
    private DataCopyManager dataCopyManager;
    @Autowired
    private IDataStatusService dataStatusService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private ICopyDesService copyDesService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private IFormSchemeService iFormSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private IExtensionFactory iExtensionFactory;
    @Autowired
    private IDataCopyServiceFactory iDataCopyServiceFactory;
    @Autowired
    private DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory;
    @Autowired
    private IDataLogicServiceFactory iDataLogicServiceFactory;

    @Override
    public void batchCopy(BatchCopyBTWEntityInfo batchCopyBTWEntityInfo, AsyncTaskMonitor asyncTaskMonitor) {
        BatchCopyBTWEntityReturnInfo batchCopyBTWEntityReturnInfo = new BatchCopyBTWEntityReturnInfo();
        batchCopyBTWEntityReturnInfo.setSourceEntity(batchCopyBTWEntityInfo.getSourceEntityTitle());
        batchCopyBTWEntityReturnInfo.setTargetEntity(batchCopyBTWEntityInfo.getTargetEntityTitle());
        JtableContext jtableContext = batchCopyBTWEntityInfo.getContext();
        HashMap<String, String> dimensionNumMap = new HashMap<String, String>();
        Map dimensionSet = jtableContext.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            DimensionValue dimensionValue = (DimensionValue)dimensionSet.get(key);
            if (StringUtils.isEmpty((String)dimensionValue.getValue())) {
                dimensionNumMap.put(key, "showAll");
                continue;
            }
            dimensionNumMap.put(key, Integer.toString(dimensionValue.getValue().split(";").length));
        }
        List<Object> copyForms = new ArrayList<String>();
        boolean copyBJ = false;
        if (StringUtils.isEmpty((String)jtableContext.getFormKey())) {
            copyBJ = true;
            batchCopyBTWEntityReturnInfo.setCopyForms("showAll");
            copyForms = this.getForms(jtableContext);
        } else {
            copyForms.addAll(Arrays.asList(jtableContext.getFormKey().split(";")));
            batchCopyBTWEntityReturnInfo.setCopyForms(Integer.toString(copyForms.size()));
        }
        batchCopyBTWEntityReturnInfo.setDimensionNum(dimensionNumMap);
        String periodDim = this.dataAccesslUtil.getPeriodDimensionName(batchCopyBTWEntityInfo.getSourceEntityId());
        batchCopyBTWEntityReturnInfo.setPeriod(((DimensionValue)dimensionSet.get(periodDim)).getValue());
        DimMapConverter dimMapConverter = new DimMapConverter(batchCopyBTWEntityInfo.getSourceEntityId(), batchCopyBTWEntityInfo.getTargetEntityId(), jtableContext);
        ProviderStore providerStore = new ProviderStore(this.dataPermissionEvaluatorFactory);
        DataCopyParamProvider dataCopyParamProvider = new DataCopyParamProvider(null, (DimensionMappingConverter)dimMapConverter);
        IDataCopyService idataCopyService = this.iDataCopyServiceFactory.getDataCopyService(dataCopyParamProvider, (IProviderStore)providerStore);
        CopyDataParam copyDataParam = new CopyDataParam(this.dimensionCollectionUtil.getDimensionCollection(jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey()), batchCopyBTWEntityInfo.getSourceEntityId(), batchCopyBTWEntityInfo.getTargetEntityId(), jtableContext.getTaskKey(), jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), jtableContext.getFormSchemeKey(), copyForms);
        DataCopyBTWMonitor dataCopyMonitor = new DataCopyBTWMonitor(asyncTaskMonitor, 0.8, 0.0);
        DataCopyReturnInfo dataCopyReturnInfo = idataCopyService.pullData(copyDataParam, (IDataCopyMonitor)dataCopyMonitor);
        Collection unAccessResources = dataCopyReturnInfo.getUnAccessResources();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        String mainDim = dwEntity.getDimensionName();
        HashMap<Object, List> unit2DataPermission = new HashMap<Object, List>();
        for (DataPermissionResource dataPermissionResource : unAccessResources) {
            String unit = dataPermissionResource.getDimensionCombination().getValue(mainDim).toString();
            List list = unit2DataPermission.getOrDefault(unit, new ArrayList());
            list.add(dataPermissionResource);
            unit2DataPermission.put(unit, list);
        }
        DsContext context = DsContextHolder.getDsContext();
        DsContextImpl dsContext = (DsContextImpl)context;
        dsContext.setEntityId(batchCopyBTWEntityInfo.getTargetEntityId());
        for (String unit : unit2DataPermission.keySet()) {
            CopyReturnInfo copyReturnInfoNew = new CopyReturnInfo();
            List dataPermissionResourceList = (List)unit2DataPermission.get(unit);
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            ((DimensionValue)jtableContext.getDimensionSet().get(mainDim)).setValue(unit);
            entityQueryByKeyInfo.setContext(jtableContext);
            entityQueryByKeyInfo.setEntityKey(unit);
            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            for (DataPermissionResource dataPermissionResource : dataPermissionResourceList) {
                FormData form = this.jtableParamService.getReport(dataPermissionResource.getResourceId(), jtableContext.getFormSchemeKey());
                copyReturnInfoNew.setCopyForm(form);
                copyReturnInfoNew.setCopyEntity(queryEntityDataByKey.getEntity());
                copyReturnInfoNew.getMessage().add(form.getTitle() + ":" + dataPermissionResource.getMessage());
            }
            batchCopyBTWEntityReturnInfo.getCopyMessage().add(copyReturnInfoNew);
        }
        IUnsupportedDesHandler unsupportedHandler = this.iDataLogicServiceFactory.getUnsupportedDesHandler(dataCopyReturnInfo.getBizKeyOrderMap());
        CKDCopyBTWOptionProvider ckdCopyBTWOptionProvider = new CKDCopyBTWOptionProvider(dimMapConverter, unsupportedHandler, copyBJ);
        ICKDCopyService copyService = this.iDataLogicServiceFactory.getCKDCopyService((IProviderStore)providerStore, (ICKDCopyOptionProvider)ckdCopyBTWOptionProvider);
        CopyDesParam copyDesParam = new CopyDesParam();
        copyDesParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        copyDesParam.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
        ArrayList dimName = new ArrayList(jtableContext.getDimensionSet().keySet());
        dimName.remove(mainDim);
        HashMap dim2Form2Unit = new HashMap();
        HashMap<String, DimensionValueSet> dim2DimvalueSet = new HashMap<String, DimensionValueSet>();
        Collection accessResources = dataCopyReturnInfo.getAccessResources();
        for (DataPermissionResource dataPermissionResource : accessResources) {
            ArrayList<String> units;
            HashMap form2Unit;
            StringBuffer sb = new StringBuffer();
            DimensionCombination dimensionCombination = dataPermissionResource.getDimensionCombination();
            for (String dName : dimName) {
                sb.append(dimensionCombination.getValue(dName).toString()).append("**");
            }
            String dimKey = sb.toString();
            String dw = dimensionCombination.getValue(mainDim).toString();
            String formKey = dataPermissionResource.getResourceId();
            if (!dimMapConverter.dimCombiInTargetDims(dimensionCombination)) continue;
            if (dim2DimvalueSet.containsKey(dimKey)) {
                form2Unit = (HashMap)dim2Form2Unit.get(dimKey);
                units = form2Unit.getOrDefault(formKey, new ArrayList());
                units.add(dw);
                form2Unit.put(formKey, units);
                continue;
            }
            dim2DimvalueSet.put(dimKey, dimensionCombination.toDimensionValueSet());
            form2Unit = new HashMap();
            units = new ArrayList<String>();
            units.add(dw);
            form2Unit.put(formKey, units);
            dim2Form2Unit.put(dimKey, form2Unit);
        }
        for (String key : dim2Form2Unit.keySet()) {
            HashMap form2Unit = (HashMap)dim2Form2Unit.get(key);
            DimensionValueSet dimensionValueSet = (DimensionValueSet)dim2DimvalueSet.get(key);
            HashMap<String, List> unit2Form = new HashMap<String, List>();
            for (String form : form2Unit.keySet()) {
                List unitList = (List)form2Unit.get(form);
                Collections.sort(unitList);
                String unit = String.join((CharSequence)";", unitList);
                List formList = unit2Form.getOrDefault(unit, new ArrayList());
                formList.add(form);
                unit2Form.put(unit, formList);
            }
            for (String unit : unit2Form.keySet()) {
                dimensionValueSet.setValue(mainDim, Arrays.asList(unit.split(";")));
                copyDesParam.setDims(this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, jtableContext.getFormSchemeKey()));
                copyDesParam.setFormKeys((List)unit2Form.get(unit));
                copyService.copy(copyDesParam);
            }
        }
        String retStr = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            retStr = mapper.writeValueAsString((Object)batchCopyBTWEntityReturnInfo);
        }
        catch (JsonProcessingException e) {
            logger.error("\u83b7\u53d6\u8de8\u5355\u4f4d\u53e3\u5f84\u6279\u91cf\u590d\u5236\u8be6\u7ec6\u4fe1\u606f\u5931\u8d25\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (asyncTaskMonitor != null) {
            String batchCopy = "batch_copy_success_info";
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.finish(batchCopy, (Object)retStr);
            }
        }
    }

    private List<String> getForms(JtableContext context) {
        String formKeyStr = context.getFormKey();
        ArrayList<String> formKeys = new ArrayList();
        if (StringUtils.isNotEmpty((String)formKeyStr)) {
            formKeys = new ArrayList<String>(Arrays.asList(formKeyStr.split(";")));
        }
        if (formKeys.size() == 0) {
            formKeys = this.getAllFormKeys(context.getFormSchemeKey());
        }
        List allFormdefine = this.iRunTimeViewController.queryFormsById(formKeys);
        formKeys = allFormdefine.stream().filter(form -> !form.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS) && !form.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) && !form.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) && !form.getFormType().equals((Object)FormType.FORM_TYPE_FMDM)).map(item -> item.getKey()).collect(Collectors.toList());
        return formKeys;
    }

    private List<String> getAllFormKeys(String formSchemeKey) {
        List<String> allformKeys = new ArrayList<String>();
        try {
            List queryAllFormDefinesByFormScheme;
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
            logger.error("\u8de8\u5355\u4f4d\u53e3\u5f84\u6279\u91cf\u590d\u5236\uff1a\u67e5\u8be2\u6240\u6709\u62a5\u8868\u5931\u8d25\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return allformKeys;
    }

    private Map<String, DimensionValue> copyDimensionSet(Map<String, DimensionValue> dimensionSet) {
        HashMap<String, DimensionValue> newDimensionSet = new HashMap<String, DimensionValue>();
        if (dimensionSet != null) {
            for (String dimensionName : dimensionSet.keySet()) {
                DimensionValue dimensionValue = dimensionSet.get(dimensionName);
                newDimensionSet.put(dimensionName, new DimensionValue(dimensionValue));
            }
        }
        return newDimensionSet;
    }

    private void copyRegionData(JtableContext targetContext, JtableContext sourceContext, List<DataRegionDefine> dataRegionDefines, IMonitor monitor, double startProgress, double endProgress, Map<String, String> bizKeyOrderMap) {
        double progress = (endProgress - startProgress) / (double)dataRegionDefines.size();
        String formulaSchemeKey = targetContext.getFormulaSchemeKey();
        monitor.onProgress(startProgress);
        monitor.message("\u5f00\u59cb\u590d\u5236\u6570\u636e", (Object)this);
        monitor.start();
        DsContext context = DsContextHolder.getDsContext();
        DsContextImpl dsContext = (DsContextImpl)context;
        for (int i = 0; i < dataRegionDefines.size(); ++i) {
            ArrayList<ReturnRes> returnResList = new ArrayList<ReturnRes>();
            String formKey = dataRegionDefines.get(i).getFormKey();
            String regionKey = dataRegionDefines.get(i).getKey();
            String regionTitle = dataRegionDefines.get(i).getTitle();
            targetContext.setFormKey(formKey);
            sourceContext.setFormKey(formKey);
            RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
            regionQueryInfo.setContext(sourceContext);
            regionQueryInfo.setRegionKey(regionKey);
            regionQueryInfo.getRestructureInfo().setCustomGrade(true);
            regionQueryInfo.getRestructureInfo().setNoSumData(true);
            dsContext.setEntityId(sourceContext.getContextEntityId());
            RegionDataSet regionDataSet = this.jtableDataQueryService.queryRegionDatas(regionQueryInfo);
            IFMDMData ifmdmData = null;
            if (regionDataSet.getTotalCount() > 0) {
                int state;
                Map cells = regionDataSet.getCells();
                List dataList = regionDataSet.getData();
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)targetContext);
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
                dsContext.setEntityId(targetContext.getContextEntityId());
                SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createSaveDataBuilder(regionKey, dimensionCombinationBuilder.getCombination());
                if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
                    saveDataBuilder.setFormulaSchemeKey(formulaSchemeKey);
                }
                List bizKeyOrderFields = new ArrayList();
                List bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionKey, targetContext);
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
                    if (dataRegionDefines.get(i).getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE || this.iRunTimeViewController.queryDataLinkDefine(linkKey).getType() != DataLinkType.DATA_LINK_TYPE_FMDM) continue;
                    containZb = true;
                }
                if (containZb) {
                    FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
                    fmdmDataDTO.setDimensionValueSet(dimensionValueSet);
                    fmdmDataDTO.setFormSchemeKey(targetContext.getFormSchemeKey());
                    List list = this.ifmdmDataService.list(fmdmDataDTO);
                    ifmdmData = (IFMDMData)list.get(0);
                }
                boolean noDimFloat = false;
                if (dataRegionDefines.get(i).getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                    state = 0;
                } else {
                    state = 1;
                    RegionQueryInfo currRegionQueryInfo = new RegionQueryInfo();
                    currRegionQueryInfo.setContext(targetContext);
                    currRegionQueryInfo.setRegionKey(regionKey);
                    RegionDataSet currRegionDataSet = this.jtableDataQueryService.queryRegionDatas(currRegionQueryInfo);
                    if (currRegionDataSet.getTotalCount() > 0) {
                        ReturnRes returnRes = new ReturnRes();
                        IClearInfo clearInfo = ClearInfoBuilder.create((String)regionKey, (DimensionCombination)dimensionCombinationBuilder.getCombination()).build();
                        try {
                            returnRes = this.dataService.clearRegionData(clearInfo);
                        }
                        catch (Exception e) {
                            monitor.error("\u5220\u9664\u6d6e\u52a8\u533a\u57df\u201c" + regionTitle + "\u201d\u539f\u6709\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef\uff1a" + returnRes.getMessage(), (Object)this);
                            monitor.exception(e);
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                    }
                    if (bizKeyOrderFields.size() == 1 || dataRegionDefines.get(i).getAllowDuplicateKey()) {
                        noDimFloat = true;
                    } else {
                        Iterator iterator = bizKeyOrderFields.iterator();
                        while (iterator.hasNext()) {
                            FieldData fieldData = (FieldData)iterator.next();
                            if (!"BIZKEYORDER".equals(fieldData.getFieldCode())) continue;
                            iterator.remove();
                            break;
                        }
                    }
                }
                for (List datas : dataList) {
                    if (dataRegionDefines.get(i).getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                        saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), state);
                    } else {
                        DimensionCombinationBuilder temDcBuilder = new DimensionCombinationBuilder(dimensionValueSet);
                        String rowID = (String)datas.get(rowIDIndex);
                        if (noDimFloat) {
                            String bizKeyOrderStr = GUID.newGUID();
                            String[] bizKeyValues = rowID.split("\\#\\^\\$");
                            StringBuffer newRowID = new StringBuffer();
                            int fieldDimIndex = 0;
                            for (FieldData bizKeyOrderField : bizKeyOrderFields) {
                                if ("BIZKEYORDER".equals(bizKeyOrderField.getFieldCode())) {
                                    newRowID.append(bizKeyOrderStr);
                                    bizKeyOrderMap.put(bizKeyValues[fieldDimIndex], bizKeyOrderStr);
                                } else {
                                    newRowID.append(bizKeyValues[fieldDimIndex]);
                                }
                                if (++fieldDimIndex == bizKeyOrderFields.size()) continue;
                                newRowID.append("#^$");
                            }
                            rowID = newRowID.toString();
                        }
                        DataCrudUtil.setBizKeyValueForDimension((DimensionCombinationBuilder)temDcBuilder, (String)rowID, bizKeyOrderFields);
                        saveDataBuilder.addRow(temDcBuilder.getCombination(), state);
                    }
                    for (int j = 0; j < datas.size(); ++j) {
                        ReturnRes returnRes;
                        Object data = datas.get(j);
                        if (data != null && data.toString().isEmpty()) {
                            data = null;
                        }
                        if (ifmdmData != null && this.iRunTimeViewController.queryDataLinkDefine((String)linkKeys.get(j)).getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                            String linkExpression = this.iRunTimeViewController.queryDataLinkDefine((String)linkKeys.get(j)).getLinkExpression();
                            AbstractData abstractData = ifmdmData.getEntityValue(linkExpression);
                            if (abstractData instanceof DateTimeData) {
                                abstractData = AbstractData.valueOf((Object)abstractData.getAsDateObj(), (int)5);
                            }
                            returnRes = "PARENTCODE".equals(linkExpression) && "-".equals(abstractData.getAsString()) ? saveDataBuilder.setData(((Integer)linkIndexs.get(j)).intValue(), null) : saveDataBuilder.setData(((Integer)linkIndexs.get(j)).intValue(), (Object)abstractData.getAsString());
                        } else {
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
        monitor.message("\u590d\u5236\u6570\u636e\u5df2\u5b8c\u6210!", (Object)this);
        monitor.finish();
    }

    private DimensionCollection buildDimensionCollection(Map<String, DimensionValue> dimensionValue) {
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (Map.Entry<String, DimensionValue> entry : dimensionValue.entrySet()) {
            builder.setValue(entry.getKey(), new Object[]{entry.getValue().getValue()});
        }
        return builder.getCollection();
    }

    private ExecutorContext createExecutorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.dataDefinitionController, this.entityViewController, formSchemeKey, true);
        context.setEnv((IFmlExecEnvironment)environment);
        return context;
    }

    private boolean checkNeedFormatCurrency(JtableContext jtableContext) {
        return jtableContext.getDimensionSet().containsKey("MD_CURRENCY") && (((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue().equals("PROVIDER_BASECURRENCY") || ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue().equals("PROVIDER_PBASECURRENCY"));
    }

    private void formatCurrency(JtableContext jtableContext) {
        List<String> currencyInfo = this.currencyService.getCurrencyInfo(jtableContext, ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue());
        if (currencyInfo != null && !currencyInfo.isEmpty()) {
            ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).setValue(currencyInfo.get(0));
        }
    }

    private Map<String, DimensionValue> matchDimensionSetInList(Map<String, DimensionValue> dimensionSet, List<Map<String, DimensionValue>> dimList, List<String> needCompareDimNames, String mainDim) {
        for (Map<String, DimensionValue> map : dimList) {
            boolean match = true;
            int sameNum = 0;
            for (String key : dimensionSet.keySet()) {
                if (!key.equals(mainDim) && dimensionSet.get(key).getValue().equals(map.get(key).getValue())) {
                    ++sameNum;
                }
                if (!needCompareDimNames.contains(key) || dimensionSet.get(key).getValue().equals(map.get(key).getValue())) continue;
                match = false;
            }
            if (sameNum == dimensionSet.size() - 1) {
                match = false;
            }
            if (!match) continue;
            return map;
        }
        return null;
    }

    private Map<String, DimensionValue> mergeDimLists(List<Map<String, DimensionValue>> dimList) {
        if (dimList.size() == 1) {
            return this.copyDimensionSet(dimList.get(0));
        }
        HashMap<String, DimensionValue> mergedDim = new HashMap<String, DimensionValue>();
        HashMap valueMap = new HashMap();
        for (Map<String, DimensionValue> map : dimList) {
            for (String string : map.keySet()) {
                if (!valueMap.containsKey(string)) {
                    valueMap.put(string, new LinkedHashSet());
                }
                ((Set)valueMap.get(string)).add(map.get(string).getValue());
            }
        }
        for (String key : valueMap.keySet()) {
            StringBuilder sb = new StringBuilder();
            for (String value : (Set)valueMap.get(key)) {
                sb.append(value).append(";");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            DimensionValue dimensionValue = new DimensionValue(dimList.get(0).get(key));
            dimensionValue.setValue(sb.toString());
            mergedDim.put(key, dimensionValue);
        }
        return mergedDim;
    }

    private String getDimensionString(Map<String, DimensionValue> dim, List<String> dimNameList) {
        StringBuffer dimString = new StringBuffer();
        for (String dimName : dimNameList) {
            dimString.append(dim.get(dimName).getValue()).append("**");
        }
        return dimString.toString();
    }

    public class CKDCopyBTWOptionProvider
    implements ICKDCopyOptionProvider {
        private DimMapConverter dimMapConverter;
        private IUnsupportedDesHandler iUnsupportedDesHandler;
        private boolean copyBJ = false;

        public CKDCopyBTWOptionProvider(DimMapConverter converter, IUnsupportedDesHandler unsupportedDesHandler) {
            this.dimMapConverter = converter;
            this.iUnsupportedDesHandler = unsupportedDesHandler;
        }

        public CKDCopyBTWOptionProvider(DimMapConverter converter, IUnsupportedDesHandler unsupportedDesHandler, boolean copyBJForm) {
            this.dimMapConverter = converter;
            this.iUnsupportedDesHandler = unsupportedDesHandler;
            this.copyBJ = copyBJForm;
        }

        public boolean isPullMode() {
            return true;
        }

        public DimensionMappingConverter getDimensionMappingConverter() {
            return this.dimMapConverter;
        }

        public IFmlMappingProvider getFmlMappingProvider() {
            return null;
        }

        public IUnsupportedDesHandler getUnsupportedDesHandler() {
            return this.iUnsupportedDesHandler;
        }

        public boolean copyBJFml() {
            return this.copyBJ;
        }

        public boolean updateUserTime() {
            return false;
        }
    }

    public class DimMapConverter
    implements DimensionMappingConverter {
        HashMap<String, DimensionMapInfo> dimMap = new HashMap();
        List<String> dimNameList;

        public DimMapConverter(String sourceContextEntityId, String targetContextEntityId, JtableContext jtableContext) {
            ArrayList<String> needCompareDimNames = new ArrayList<String>();
            FormSchemeDefine formScheme = BatchCopyBetweenEntityServiceImpl.this.iRunTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
            IEntityModel entityModel = BatchCopyBetweenEntityServiceImpl.this.entityMetaService.getEntityModel(BatchCopyBetweenEntityServiceImpl.this.entityUtil.getContextMainDimId(formScheme.getDw()));
            HashMap<String, Object> dimNameEntityIdMap = new HashMap<String, Object>();
            String dimString = formScheme.getDims();
            if (dimString != null && !dimString.equals("")) {
                for (String entityId : dimString.split(";")) {
                    String dimensionName = BatchCopyBetweenEntityServiceImpl.this.entityMetaService.getDimensionName(entityId);
                    dimNameEntityIdMap.put(dimensionName, entityId);
                }
            }
            this.dimNameList = new ArrayList<String>();
            EntityViewData dwEntity = BatchCopyBetweenEntityServiceImpl.this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
            String mainDim = dwEntity.getDimensionName();
            ArrayList<String> otherDimNames = new ArrayList<String>();
            for (String dimName : jtableContext.getDimensionSet().keySet()) {
                IEntityAttribute attribute;
                String dimAttributeByReportDim;
                this.dimNameList.add(dimName);
                if (dimNameEntityIdMap.containsKey(dimName) && (dimAttributeByReportDim = BatchCopyBetweenEntityServiceImpl.this.iFormSchemeService.getDimAttributeByReportDim(jtableContext.getFormSchemeKey(), (String)dimNameEntityIdMap.get(dimName))) != null && (attribute = entityModel.getAttribute(dimAttributeByReportDim)) != null && !attribute.isMultival()) {
                    otherDimNames.add(dimName);
                    continue;
                }
                needCompareDimNames.add(dimName);
            }
            List targetDimComs = new ArrayList();
            List sourceDimComs = new ArrayList();
            DsContext context = DsContextHolder.getDsContext();
            DsContextImpl dsContext = (DsContextImpl)context;
            if (BatchCopyBetweenEntityServiceImpl.this.checkNeedFormatCurrency(jtableContext)) {
                JtableContext newJtableContext;
                List dwList = Arrays.asList(((DimensionValue)jtableContext.getDimensionSet().get(mainDim)).getValue().split(";"));
                if (StringUtils.isEmpty((String)((DimensionValue)jtableContext.getDimensionSet().get(mainDim)).getValue())) {
                    dwList = (List)BatchCopyBetweenEntityServiceImpl.this.dimensionCollectionUtil.getDimensionCollection(jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey()).combineDim().getValue(mainDim);
                }
                for (String dw : dwList) {
                    newJtableContext = new JtableContext(jtableContext);
                    ((DimensionValue)newJtableContext.getDimensionSet().get(mainDim)).setValue(dw);
                    BatchCopyBetweenEntityServiceImpl.this.formatCurrency(newJtableContext);
                    targetDimComs.addAll(BatchCopyBetweenEntityServiceImpl.this.dimensionCollectionUtil.getDimensionCollection(newJtableContext.getDimensionSet(), jtableContext.getFormSchemeKey()).getDimensionCombinations());
                }
                dsContext.setEntityId(sourceContextEntityId);
                for (String dw : dwList) {
                    newJtableContext = new JtableContext(jtableContext);
                    ((DimensionValue)newJtableContext.getDimensionSet().get(mainDim)).setValue(dw);
                    BatchCopyBetweenEntityServiceImpl.this.formatCurrency(newJtableContext);
                    sourceDimComs.addAll(BatchCopyBetweenEntityServiceImpl.this.dimensionCollectionUtil.getDimensionCollection(newJtableContext.getDimensionSet(), jtableContext.getFormSchemeKey()).getDimensionCombinations());
                }
            } else {
                targetDimComs = BatchCopyBetweenEntityServiceImpl.this.dimensionCollectionUtil.getDimensionCollection(jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey()).getDimensionCombinations();
                dsContext.setEntityId(sourceContextEntityId);
                sourceDimComs = BatchCopyBetweenEntityServiceImpl.this.dimensionCollectionUtil.getDimensionCollection(jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey()).getDimensionCombinations();
            }
            dsContext.setEntityId(targetContextEntityId);
            HashMap<String, DimensionCombination> targetDimMap = new HashMap<String, DimensionCombination>();
            for (DimensionCombination dimensionCombination : targetDimComs) {
                targetDimMap.put(this.getDimStringByDimCombi(needCompareDimNames, dimensionCombination), dimensionCombination);
            }
            for (DimensionCombination dimensionCombination : sourceDimComs) {
                String sourceDimKey = this.getDimStringByDimCombi(needCompareDimNames, dimensionCombination);
                DimensionCombination targetDimCom = (DimensionCombination)targetDimMap.get(sourceDimKey);
                if (targetDimCom == null || !this.compareDimConbination(otherDimNames, dimensionCombination, targetDimCom)) continue;
                DimensionMapInfo dimensionMapInfo = new DimensionMapInfo(dimensionCombination.toDimensionValueSet(), targetDimCom.toDimensionValueSet());
                this.dimMap.put(this.getDimStringByDimCombi(this.dimNameList, dimensionCombination), dimensionMapInfo);
                this.dimMap.put(this.getDimStringByDimCombi(this.dimNameList, targetDimCom), dimensionMapInfo);
            }
        }

        private boolean compareDimConbination(List<String> otherDimNames, DimensionCombination a, DimensionCombination b) {
            for (String dim : otherDimNames) {
                if (a.getValue(dim).toString().equals(b.getValue(dim).toString())) continue;
                return true;
            }
            return false;
        }

        private String getDimStringByDimCombi(List<String> dimNameList, DimensionCombination dimensionCombination) {
            StringBuffer dimString = new StringBuffer();
            for (String dimName : dimNameList) {
                dimString.append(dimensionCombination.getValue(dimName).toString()).append("**");
            }
            return dimString.toString();
        }

        private String getDimStringByDimValueSet(List<String> dimNameList, DimensionValueSet dimensionValueSet) {
            StringBuffer dimString = new StringBuffer();
            for (String dimName : dimNameList) {
                dimString.append(dimensionValueSet.getValue(dimName).toString()).append("**");
            }
            return dimString.toString();
        }

        public List<DimensionValueSet> getMappingMasterKey(DimensionValueSet curMasterKey) {
            ArrayList<DimensionValueSet> res = new ArrayList<DimensionValueSet>();
            String dimKey = this.getDimStringByDimValueSet(this.dimNameList, curMasterKey);
            DimensionMapInfo dimensionMapInfo = this.dimMap.get(dimKey);
            if (dimensionMapInfo != null) {
                res.add(dimensionMapInfo.getSource());
            }
            return res;
        }

        public List<DimensionMapInfo> getMappingMasterKeys(List<DimensionValueSet> curMasterKeys) {
            ArrayList<DimensionMapInfo> res = new ArrayList<DimensionMapInfo>();
            for (DimensionValueSet dim : curMasterKeys) {
                String dimKey = this.getDimStringByDimValueSet(this.dimNameList, dim);
                DimensionMapInfo dimensionMapInfo = this.dimMap.get(dimKey);
                if (dimensionMapInfo == null) continue;
                res.add(dimensionMapInfo);
            }
            return res;
        }

        public boolean dimCombiInTargetDims(DimensionCombination dimensionCombination) {
            return this.dimMap.containsKey(this.getDimStringByDimCombi(this.dimNameList, dimensionCombination));
        }
    }
}

