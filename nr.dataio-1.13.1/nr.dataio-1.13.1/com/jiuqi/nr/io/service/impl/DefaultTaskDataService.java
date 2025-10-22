/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.Result
 *  com.jiuqi.nr.data.common.service.StatisticalRecorder
 *  com.jiuqi.nr.data.common.service.TaskDataFactory
 *  com.jiuqi.nr.data.common.service.TaskDataFactoryManager
 *  com.jiuqi.nr.data.common.service.TransferContext
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.CompletionDimFinder
 *  com.jiuqi.nr.data.common.service.dto.CompletionDimFinderImpl
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.data.common.utils.DataCommonUtils
 *  com.jiuqi.nr.dataentity_ext.api.IEntityDataService
 *  com.jiuqi.nr.dataentity_ext.api.IEntityQuery
 *  com.jiuqi.nr.dataentity_ext.common.EntityDataException
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow
 *  com.jiuqi.nr.dataentity_ext.dto.QueryParam
 *  com.jiuqi.nr.dataentity_ext.dto.SaveParam
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.dao.RunTimeSchemePeriodLinkDao
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.CSVParser
 *  org.apache.commons.csv.CSVPrinter
 *  org.apache.commons.csv.CSVRecord
 */
package com.jiuqi.nr.io.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.Version;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.StatisticalRecorder;
import com.jiuqi.nr.data.common.service.TaskDataFactory;
import com.jiuqi.nr.data.common.service.TaskDataFactoryManager;
import com.jiuqi.nr.data.common.service.TransferContext;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.CompletionDimFinder;
import com.jiuqi.nr.data.common.service.dto.CompletionDimFinderImpl;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.data.common.utils.DataCommonUtils;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.dataentity_ext.api.IEntityQuery;
import com.jiuqi.nr.dataentity_ext.common.EntityDataException;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow;
import com.jiuqi.nr.dataentity_ext.dto.QueryParam;
import com.jiuqi.nr.dataentity_ext.dto.SaveParam;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeSchemePeriodLinkDao;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.io.api.ITaskDataService;
import com.jiuqi.nr.io.params.input.AnalysisResultEnum;
import com.jiuqi.nr.io.params.input.UnitCountQueryParam;
import com.jiuqi.nr.io.params.input.UnitQueryParam;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.bean.ImportLog;
import com.jiuqi.nr.io.record.bean.ImportState;
import com.jiuqi.nr.io.record.service.FormStatisticService;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nr.io.record.service.UnitFailureService;
import com.jiuqi.nr.io.service.ParamsMappingService;
import com.jiuqi.nr.io.service.impl.BasePathFinder;
import com.jiuqi.nr.io.service.impl.BasePathWriter;
import com.jiuqi.nr.io.tsd.dto.AnalysisParam;
import com.jiuqi.nr.io.tsd.dto.AnalysisRes;
import com.jiuqi.nr.io.tsd.dto.DefaultTransferContext;
import com.jiuqi.nr.io.tsd.dto.DimValue;
import com.jiuqi.nr.io.tsd.dto.DimValueDTO;
import com.jiuqi.nr.io.tsd.dto.EParam;
import com.jiuqi.nr.io.tsd.dto.ExpSettingsImpl;
import com.jiuqi.nr.io.tsd.dto.ExportType;
import com.jiuqi.nr.io.tsd.dto.Form;
import com.jiuqi.nr.io.tsd.dto.IParam;
import com.jiuqi.nr.io.tsd.dto.IResult;
import com.jiuqi.nr.io.tsd.dto.ImpSettingsImpl;
import com.jiuqi.nr.io.tsd.dto.PackageData;
import com.jiuqi.nr.io.tsd.dto.Unit;
import com.jiuqi.nr.io.tsd.dto.UnitEntityDataRow;
import com.jiuqi.nr.io.tsd.service.DescRecorderImpl;
import com.jiuqi.nr.io.tsd.service.StatisticalRecorderImpl;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DefaultTaskDataService
implements ITaskDataService {
    @Autowired
    protected TaskDataFactoryManager factoryManager;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IProviderStore providerStore;
    @Autowired(required=false)
    protected ParamsMappingService paramsMappingService;
    @Autowired
    protected com.jiuqi.nr.entity.service.IEntityDataService entityDataService;
    @Autowired
    protected IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired(required=false)
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FormStatisticService formStatisticService;
    @Autowired
    private UnitFailureService unitFailureService;
    @Autowired
    private ImportHistoryService importHistoryService;
    @Autowired
    private RunTimeSchemePeriodLinkDao runTimeSchemePeriodLinkDao;
    @Autowired
    private TaskDataFactoryManager taskDataFactoryManager;
    @Autowired
    private IEntityDataService extEntityDataService;
    @Autowired
    private IMappingSchemeService mappingSchemeService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    public static Version VERSION_1_0_0 = new Version(1, 0, 0);
    public static String PACKAGE_INFO = "PACKAGE_INFO.json";
    public static String UNIT_INFO = "UNIT_INFO.csv";
    public static final String[] HEADER_NAMES = new String[6];
    public static Version CURRENT_VERSION;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportTaskData(EParam eParam, FileWriter writer, IProgressMonitor monitor) {
        try {
            monitor.startTask("\u6784\u5efa\u4efb\u52a1\u6570\u636e\u5305", new int[]{1, 1, 8, 1});
            List factory = this.factoryManager.getFactory(eParam.getExportTypes());
            DefaultTransferContext context = this.buildContext(eParam);
            context.setProviderStore(this.getProviderStore());
            context.setMonitor(monitor);
            monitor.stepIn();
            String mappingSchemeKey = eParam.getMappingSchemeKey();
            ParamsMapping paramsMapping = null;
            if (org.springframework.util.StringUtils.hasLength(mappingSchemeKey) && this.paramsMappingService != null) {
                paramsMapping = this.paramsMappingService.getParamsMapping(mappingSchemeKey);
                context.setParamsMapping(paramsMapping);
            }
            monitor.stepIn();
            int[] steps = DefaultTaskDataService.getExSteps(factory);
            try {
                monitor.startTask("\u5bfc\u51fa\u5404\u7c7b\u6570\u636e", steps);
                BasePathWriter basePathWriter = new BasePathWriter(writer, "");
                for (TaskDataFactory taskDataFactory : factory) {
                    basePathWriter.setBasePath(taskDataFactory.getCode());
                    taskDataFactory.exportTaskData((TransferContext)context, (FileWriter)basePathWriter);
                    monitor.stepIn();
                }
            }
            finally {
                monitor.finishTask("\u5bfc\u51fa\u5404\u7c7b\u6570\u636e");
            }
            monitor.stepIn();
            this.addPackageFile(eParam, paramsMapping, writer);
        }
        finally {
            monitor.finishTask("\u6784\u5efa\u4efb\u52a1\u6570\u636e\u5305");
        }
    }

    @Override
    public Result<AnalysisRes> preAnalysis(AnalysisParam analysisParam, FileFinder finder) {
        try {
            String contextEntityId;
            byte[] pkBytes = finder.getFileBytes(PACKAGE_INFO);
            PackageData packageData = (PackageData)this.objectMapper.readValue(pkBytes, PackageData.class);
            this.readUnit(packageData, finder);
            String version = packageData.getVersion();
            if (org.springframework.util.StringUtils.hasLength(version)) {
                Version importVersion = new Version(version);
                if (importVersion.getMajor() > CURRENT_VERSION.getMajor()) {
                    return this.getAnalysisResult(null, AnalysisResultEnum.VERSION_NOT_SUPPORT);
                }
            } else {
                return this.getAnalysisResult(null, AnalysisResultEnum.PACKAGE_NOT_SUPPORT);
            }
            AnalysisRes analysisRes = new AnalysisRes();
            TaskDefine taskDefine = Optional.ofNullable(analysisParam.getTaskKey()).map(e -> this.runTimeViewController.queryTaskDefine(e)).orElse(this.paramsMappingService.resolveTaskDefine(packageData));
            if (taskDefine == null) {
                return this.getAnalysisResult(analysisRes, AnalysisResultEnum.TASK_NOT_FOUND);
            }
            String taskKey = taskDefine.getKey();
            String dwEntityID = taskDefine.getDw();
            analysisRes.setTaskKey(taskKey);
            String dataTime = packageData.getPeriodValue();
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
            String periodTitle = periodProvider.getPeriodTitle(dataTime);
            if (!org.springframework.util.StringUtils.hasLength(periodTitle)) {
                return this.getAnalysisResult(analysisRes, AnalysisResultEnum.PERIOD_NOT_FOUND);
            }
            analysisRes.setPeriodValue(dataTime);
            analysisRes.setPeriodTitle(periodTitle);
            String formSchemeKey = analysisParam.getFormSchemeKey();
            if (StringUtils.isEmpty((String)formSchemeKey)) {
                try {
                    List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
                    formSchemeKey = formSchemeDefines.stream().filter(e -> this.runTimeSchemePeriodLinkDao.queryLinkByPeriodAndScheme(dataTime, e.getKey()) == null).findFirst().map(IBaseMetaItem::getKey).orElse(null);
                }
                catch (Exception e2) {
                    this.logger.error("\u6839\u636e\u4efb\u52a1\u548c\u65f6\u671f\u67e5\u8be2\u5bf9\u5e94\u62a5\u8868\u65b9\u6848\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e2.getMessage(), (Object)e2);
                }
            }
            if (StringUtils.isEmpty((String)formSchemeKey)) {
                return this.getAnalysisResult(analysisRes, AnalysisResultEnum.FORMSCHEME_NOT_FOUND);
            }
            analysisRes.setFormSchemeKey(formSchemeKey);
            boolean containFmdmForm = false;
            boolean containAccountForm = false;
            List<Form> forms = packageData.getForms();
            List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
            int matchFormCount = 0;
            for (Form form : forms) {
                FormDefine formDefine = this.paramsMappingService.resolveFormDefine(form, formDefines);
                if (formDefine == null) continue;
                ++matchFormCount;
                if (!containFmdmForm && formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)) {
                    containFmdmForm = true;
                }
                if (containAccountForm || !formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT)) continue;
                containAccountForm = true;
            }
            analysisRes.setFormCount(matchFormCount);
            analysisRes.setContainAccountForm(containAccountForm);
            analysisRes.setExportTypes(this.getExportTypes(packageData.getDataCatalog(), containFmdmForm));
            if (analysisParam.isAnalysisDw()) {
                String mappingSchemeKey = analysisParam.getMappingKey();
                List<Unit> exportEntitys = packageData.getEntityKeys();
                Map<String, String> orgMapping = this.paramsMappingService.getOrgMapping(mappingSchemeKey);
                List<IEntityDataRow> entityDataRows = DefaultTaskDataService.buildEntityDataRows(exportEntitys, orgMapping);
                ArrayList<EntityDataType> countType = new ArrayList<EntityDataType>();
                countType.add(EntityDataType.EXIST);
                if (containFmdmForm) {
                    countType.add(EntityDataType.NOT_EXIST);
                }
                SaveParam saveParam = new SaveParam();
                saveParam.setPeriod(dataTime);
                saveParam.setFormSchemeKey(formSchemeKey);
                saveParam.setCountType(countType);
                String dwSchemeKey = null;
                try {
                    dwSchemeKey = this.extEntityDataService.save(saveParam, entityDataRows);
                }
                catch (EntityDataException e3) {
                    this.logger.error("\u4fdd\u5b58\u5355\u4f4d\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e3.getMessage(), (Object)e3);
                }
                if (StringUtils.isEmpty((String)dwSchemeKey)) {
                    return this.getAnalysisResult(analysisRes, AnalysisResultEnum.UNIT_SAVE_FAILURE);
                }
                QueryParam queryParam = new QueryParam(dwSchemeKey);
                List<EntityDataType> entityDataTypes = Collections.singletonList(EntityDataType.NOT_EXIST);
                queryParam.setTypes(entityDataTypes);
                IEntityQuery entityQuery = this.getEntityQuery(queryParam);
                analysisRes.setAllUnitCount(exportEntitys.size());
                analysisRes.setNonexistentUnitCount(entityQuery != null ? entityQuery.getTotalCount() : 0);
                analysisRes.setDwSchemeKey(dwSchemeKey);
            }
            if (analysisParam.isAnalysisDim()) {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(dwEntityID);
                List<DimValueDTO> allDims = packageData.getAllDims();
                Map<Object, Object> exportDimNameMap = new HashMap();
                if (allDims != null) {
                    exportDimNameMap = allDims.stream().collect(Collectors.toMap(DimValueDTO::getDimName, e -> e));
                }
                Map<String, DimensionInfo> sysDimNameMap = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION).stream().map(e -> {
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(e.getDimKey());
                    return new DimensionInfo(e.getDimKey(), entityDefine.getDimensionName(), entityDefine.getTitle(), e.getDimAttribute());
                }).collect(Collectors.toMap(DimensionInfo::getDimName, e -> e));
                HashSet<String> sysDims = new HashSet<String>(sysDimNameMap.keySet());
                List<DimValueDTO> filterDims = exportDimNameMap.entrySet().stream().filter(e -> !sysDims.contains(e.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
                analysisRes.setFilterDims(filterDims);
                ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
                com.jiuqi.nr.entity.engine.intf.IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
                DimensionValueSet masterKey = new DimensionValueSet();
                masterKey.setValue("DATATIME", (Object)dataTime);
                entityQuery.setMasterKeys(masterKey);
                entityQuery.setAuthorityOperations(AuthorityType.None);
                HashSet<Object> exportDims = new HashSet<Object>(exportDimNameMap.keySet());
                List<DimValueDTO> completionDims = sysDimNameMap.entrySet().stream().filter(e -> !exportDims.contains(e.getKey())).map(e -> {
                    DimensionInfo dimensionInfo = (DimensionInfo)e.getValue();
                    IEntityAttribute attribute = entityModel.getAttribute(dimensionInfo.getDimAttributeName());
                    DimValueDTO dimValueDTO = new DimValueDTO();
                    dimValueDTO.setDimCode(dimensionInfo.getDimAttributeName());
                    dimValueDTO.setDimName(dimensionInfo.getDimName());
                    dimValueDTO.setDimTitle(dimensionInfo.getEntityTitle());
                    dimValueDTO.setDw1v1(attribute != null && !attribute.isMultival());
                    dimValueDTO.setDimValues(new ArrayList<DimValue>());
                    try {
                        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(dimensionInfo.getDimKey()));
                        IEntityTable entityTable = entityQuery.executeReader((IContext)context);
                        for (IEntityRow row : entityTable.getAllRows()) {
                            DimValue dimValue = new DimValue();
                            dimValue.setCode(row.getCode());
                            dimValue.setTitle(row.getTitle());
                            dimValueDTO.getDimValues().add(dimValue);
                        }
                    }
                    catch (Exception ex) {
                        throw new RuntimeException("\u67e5\u8be2\u60c5\u666f\u6570\u636e\u51fa\u9519", ex);
                    }
                    return dimValueDTO;
                }).collect(Collectors.toList());
                analysisRes.setCompletionDims(completionDims);
            }
            if (!org.springframework.util.StringUtils.hasLength(contextEntityId = DsContextHolder.getDsContext().getContextEntityId())) {
                contextEntityId = packageData.getCaliberEntity();
            }
            analysisRes.setContextEntityId(contextEntityId);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(dwEntityID);
            analysisRes.setDwDimName(entityDefine.getDimensionName());
            return this.getAnalysisResult(analysisRes, AnalysisResultEnum.ALL_MATCH_SUCCESS);
        }
        catch (Exception e4) {
            throw new RuntimeException("\u6570\u636e\u5305\u5206\u6790\u5931\u8d25", e4);
        }
    }

    @Override
    public List<IEntityDataDTO> getUnitByParam(UnitQueryParam unitQueryParam) {
        IEntityQuery entityQuery;
        QueryParam queryParam = new QueryParam(unitQueryParam.getDwSchemeKey());
        List<String> entityIDs = unitQueryParam.getEntityIds();
        queryParam.setTypes(Arrays.stream(unitQueryParam.getTypes()).collect(Collectors.toList()));
        queryParam.setPageInfo(unitQueryParam.getPageInfo());
        queryParam.setSort(true);
        if (entityIDs != null && !entityIDs.isEmpty()) {
            queryParam.setKeys(entityIDs);
        }
        if ((entityQuery = this.getEntityQuery(queryParam)) == null) {
            return Collections.emptyList();
        }
        return entityQuery.listAllRows();
    }

    @Override
    public Map<EntityDataType, Integer> countUnitByParam(UnitCountQueryParam unitQueryParam) {
        QueryParam queryParam = new QueryParam(unitQueryParam.getDwSchemeKey());
        List<String> entityIDs = unitQueryParam.getEntityIds();
        if (entityIDs != null && !entityIDs.isEmpty()) {
            queryParam.setKeys(entityIDs);
        }
        IEntityQuery entityQuery = this.getEntityQuery(queryParam);
        return entityQuery.countTypes();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IResult importTaskData(IParam iParam, FileFinder finder, IProgressMonitor monitor) {
        IResult importResult = new IResult();
        try {
            monitor.startTask("\u5bfc\u5165", new int[]{1, 1, 8});
            byte[] pkBytes = finder.getFileBytes(PACKAGE_INFO);
            PackageData packageData = (PackageData)this.objectMapper.readValue(pkBytes, PackageData.class);
            AnalysisParam analysisParam = new AnalysisParam();
            analysisParam.setTaskKey(iParam.getTaskKey());
            analysisParam.setFormSchemeKey(iParam.getFormSchemeKey());
            analysisParam.setMappingKey(iParam.getMappingKey());
            analysisParam.setAnalysisDw(false);
            analysisParam.setAnalysisDim(true);
            Result<AnalysisRes> analysisRes = this.preAnalysis(analysisParam, finder);
            monitor.stepIn();
            if (!Objects.equals(analysisRes.getCode(), AnalysisResultEnum.ALL_MATCH_SUCCESS.getErrorCode())) {
                throw new RuntimeException("\u6570\u636e\u5305\u5206\u6790\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + analysisRes.getMessage());
            }
            AnalysisRes analysis = (AnalysisRes)analysisRes.getDatas();
            this.updateHisLog(iParam.getRecKey(), analysis.getPeriodValue());
            DefaultTransferContext context = this.buildContext(iParam, packageData, analysis);
            context.setProviderStore(this.getProviderStore());
            context.setMonitor(monitor);
            monitor.stepIn();
            context.setPeriodValue(analysis.getPeriodValue());
            List factory = this.factoryManager.getFactory(iParam.getImportTypes());
            boolean isSuccess = true;
            if (null != context.getMasterKeys() || iParam.getImportTypes().contains("FMDM_DATA")) {
                try {
                    int[] steps = DefaultTaskDataService.getExSteps(factory);
                    monitor.startTask("\u6309\u7c7b\u578b\u5bfc\u5165\u6570\u636e", steps);
                    BasePathFinder basePathWriter = new BasePathFinder(finder, "");
                    HashSet<String> allFailCount = new HashSet<String>();
                    for (TaskDataFactory taskDataFactory : factory) {
                        basePathWriter.setBasePath(taskDataFactory.getCode());
                        taskDataFactory.importTaskData((TransferContext)context, (FileFinder)basePathWriter);
                        List<String> failUnits = this.importLog(iParam, taskDataFactory, context);
                        if (!CollectionUtils.isEmpty(failUnits)) {
                            isSuccess = false;
                            allFailCount.addAll(failUnits);
                            context.setFailedDwCount(allFailCount.size());
                            if (context.getAllDwCount() == allFailCount.size()) {
                                this.logger.warn("\u5bfc\u5165\u5355\u4f4d\u5168\u90e8\u5931\u8d25\uff0c\u9000\u51fa\u5bfc\u5165\u903b\u8f91");
                                break;
                            }
                            DimensionCollection masterKeys = context.getImportSettings().getMasterKeys();
                            masterKeys = DataCommonUtils.removeDimValuesFromCollection((DimensionCollection)masterKeys, (String)analysis.getDwDimName(), new HashSet<String>(failUnits));
                            context.getImportSettings().resetMasterKeys(masterKeys);
                        }
                        monitor.stepIn();
                    }
                }
                finally {
                    monitor.finishTask("\u6309\u7c7b\u578b\u5bfc\u5165\u6570\u636e");
                }
            }
            importResult.getForms().addAll(context.getFormKeys());
            importResult.setDimensionCollection(context.getImportSettings().getMasterKeys());
            this.overviewLog(iParam, context);
            if (isSuccess) {
                this.successHisLog(iParam.getRecKey());
            } else {
                this.failHisLog(iParam.getRecKey());
            }
        }
        catch (Exception e) {
            this.errHisLog(iParam.getRecKey());
            throw new RuntimeException("\u6570\u636e\u5305\u5bfc\u5165\u5931\u8d25", e);
        }
        finally {
            monitor.finishTask("\u5bfc\u5165");
        }
        return importResult;
    }

    private void overviewLog(IParam iParam, DefaultTransferContext context) {
        MappingScheme schemeByKey;
        String taskKey = iParam.getTaskKey();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        StringBuilder msg = new StringBuilder();
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
        msg.append("\u672c\u6b21\u5bfc\u5165\u4efb\u52a1\uff1a").append(taskDefine.getTitle());
        String periodTitle = periodProvider.getPeriodTitle(context.getPeriodValue());
        msg.append("\uff0c\u65f6\u671f\uff1a").append(periodTitle);
        String mappingKey = iParam.getMappingKey();
        if (org.springframework.util.StringUtils.hasLength(mappingKey) && (schemeByKey = this.mappingSchemeService.getSchemeByKey(mappingKey)) != null) {
            msg.append("\uff0c\u5bfc\u5165\u65b9\u6848\uff1a").append(schemeByKey.getTitle());
        }
        msg.append("\uff0c\u5171").append(context.getAllDwCount()).append("\u5bb6\u5355\u4f4d\u6267\u884c\u5bfc\u5165");
        if (context.getFailedDwCount() > 0) {
            msg.append("\uff0c").append(context.getFailedDwCount()).append("\u5bb6\u5355\u4f4d\u6267\u884c\u5931\u8d25");
        }
        msg.append("\u3002");
        ImportLog importLog = new ImportLog();
        importLog.setKey(UUIDUtils.getKey());
        importLog.setRecKey(iParam.getRecKey());
        importLog.setFactoryId("OVERVIEW");
        importLog.setDesc(msg.toString());
        importLog.setState(ImportState.FINISHED.getCode());
        this.importHistoryService.addImportLog(importLog);
    }

    private List<String> importLog(IParam iParam, TaskDataFactory taskDataFactory, DefaultTransferContext context) throws IOException {
        StatisticalRecorder flushable = context.getStatisticalRecord(taskDataFactory.getCode());
        flushable.flush();
        flushable = context.getDescRecorder(taskDataFactory.getCode());
        flushable.flush();
        com.jiuqi.nr.data.common.service.Result result = context.getResult(taskDataFactory.getCode());
        List failUnits = result.getFailUnits();
        this.appendFactoryDesc(iParam.getRecKey(), taskDataFactory.getCode(), result.getMessage(), CollectionUtils.isEmpty(failUnits));
        return failUnits;
    }

    private void appendFactoryDesc(String recKey, String code, String message, boolean success) {
        ImportLog importLog = new ImportLog();
        importLog.setKey(UUIDUtils.getKey());
        importLog.setRecKey(recKey);
        importLog.setFactoryId(code);
        importLog.setDesc(message);
        if (success) {
            importLog.setState(ImportState.FINISHED.getCode());
        } else {
            importLog.setState(ImportState.FAILED.getCode());
        }
        this.importHistoryService.addImportLog(importLog);
    }

    private void updateHisLog(String recKey, String periodValue) {
        ImportHistory importHistory = this.importHistoryService.queryByRecKey(recKey);
        importHistory.setDataTime(periodValue);
        this.importHistoryService.updateImportHistory(importHistory);
    }

    private void successHisLog(String recKey) {
        ImportHistory importHistory = this.importHistoryService.queryByRecKey(recKey);
        importHistory.setEndTime(new Date(System.currentTimeMillis()));
        importHistory.setState(ImportState.FINISHED.getCode());
        this.importHistoryService.updateImportHistory(importHistory);
    }

    private void failHisLog(String recKey) {
        ImportHistory importHistory = this.importHistoryService.queryByRecKey(recKey);
        importHistory.setEndTime(new Date(System.currentTimeMillis()));
        importHistory.setState(ImportState.FAILED.getCode());
        this.importHistoryService.updateImportHistory(importHistory);
    }

    private void errHisLog(String recKey) {
        ImportHistory importHistory = this.importHistoryService.queryByRecKey(recKey);
        importHistory.setEndTime(new Date(System.currentTimeMillis()));
        importHistory.setState(ImportState.EXCEPTION.getCode());
        this.importHistoryService.updateImportHistory(importHistory);
    }

    private void addPackageFile(EParam eParam, ParamsMapping paramsMapping, FileWriter writer) {
        List<Unit> units;
        Map originOrgCode = null;
        try {
            PackageData packageData = new PackageData();
            String formSchemeKey = eParam.getFormSchemeKey();
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String taskKey = formScheme.getTaskKey();
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            packageData.setTaskKey(taskKey);
            packageData.setTaskCode(taskDefine.getTaskCode());
            packageData.setTaskTitle(taskDefine.getTitle());
            packageData.setFormSchemeKey(formSchemeKey);
            packageData.setFormSchemeCode(formScheme.getFormSchemeCode());
            packageData.setFormSchemeTitle(formScheme.getTitle());
            packageData.setCaliberEntity(DsContextHolder.getDsContext().getContextEntityId());
            List<String> formKeys = eParam.getFormKeys();
            List formDefines = this.runTimeViewController.queryFormsById(formKeys);
            packageData.setForms(new ArrayList<Form>());
            for (FormDefine formDefine : formDefines) {
                Form form = new Form(formDefine);
                packageData.getForms().add(form);
            }
            packageData.setPeriodValue(eParam.getPeriodValue());
            String dw = taskDefine.getDw();
            String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
            if (org.springframework.util.StringUtils.hasLength(contextEntityId)) {
                dw = contextEntityId;
            }
            String dwDimName = this.entityMetaService.getDimensionName(dw);
            Map<String, DimensionValue> masterKeys = eParam.getMasterKeys();
            DimensionValue dwValue = masterKeys.get(dwDimName);
            ArrayList<String> entityKeys = new ArrayList<String>();
            if (org.springframework.util.StringUtils.hasLength(dwValue.getValue())) {
                String[] split = dwValue.getValue().split(";");
                entityKeys.addAll(Arrays.asList(split));
            }
            units = this.getUnits(entityKeys, dw, dwDimName, eParam.getPeriodValue());
            entityKeys.clear();
            for (Unit unit : units) {
                entityKeys.add(unit.getEntityDataKey());
            }
            String dims = taskDefine.getDims();
            if (org.springframework.util.StringUtils.hasLength(dims)) {
                List<DimValueDTO> allDims = this.buildDimInfo(eParam, taskDefine.getDataScheme(), dw);
                packageData.setAllDims(allDims);
            }
            if (paramsMapping != null) {
                if (paramsMapping.tryPeriodMap()) {
                    packageData.setPeriodValue(paramsMapping.getOriginPeriod(eParam.getPeriodValue()));
                }
                if (paramsMapping.tryOrgCodeMap()) {
                    originOrgCode = paramsMapping.getOriginOrgCode(new ArrayList<String>(entityKeys));
                }
            }
            packageData.setDataCatalog(eParam.getExportTypes());
            packageData.setVersion(CURRENT_VERSION.toString());
            byte[] pkBytes = this.objectMapper.writeValueAsBytes((Object)packageData);
            writer.addBytes(PACKAGE_INFO, pkBytes);
        }
        catch (Exception e) {
            throw new RuntimeException("\u6784\u5efa\u6570\u636e\u5305\u6e05\u5355\u5931\u8d25", e);
        }
        this.addUnitFile(units, originOrgCode, writer);
    }

    private List<DimValueDTO> buildDimInfo(EParam eParam, String dataSchemeKey, String dwEntityId) {
        Map<String, DimensionValue> masterKeys = eParam.getMasterKeys();
        List dataDimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        ArrayList<DimValueDTO> allDims = new ArrayList<DimValueDTO>();
        for (DataDimension dataDimension : dataDimensions) {
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
            DimValueDTO entityDimValue = new DimValueDTO();
            String dimName = iEntityDefine.getDimensionName();
            entityDimValue.setDimName(dimName);
            entityDimValue.setDimTitle(iEntityDefine.getTitle());
            String dimAttribute = dataDimension.getDimAttribute();
            if (org.springframework.util.StringUtils.hasLength(dimAttribute)) {
                entityDimValue.setDimCode(dimAttribute);
                IEntityModel entityModel = this.entityMetaService.getEntityModel(dwEntityId);
                IEntityAttribute attribute = entityModel.getAttribute(dimAttribute);
                if (attribute != null) {
                    entityDimValue.setDw1v1(!attribute.isMultival());
                }
            }
            ArrayList<DimValue> dimValues = new ArrayList<DimValue>();
            DimensionValue dimValue = masterKeys.get(dimName);
            String value = dimValue.getValue();
            ArrayList<String> codes = null;
            if (org.springframework.util.StringUtils.hasLength(value)) {
                if ("MD_CURRENCY".equals(dataDimension.getDimKey()) && ("PROVIDER_BASECURRENCY".equals(value) || "PROVIDER_PBASECURRENCY".equals(value))) {
                    entityDimValue.setDw1v1(true);
                    continue;
                }
                String[] codeArr = value.split(";");
                codes = new ArrayList<String>(Arrays.asList(codeArr));
            }
            com.jiuqi.nr.entity.engine.intf.IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            try {
                DimensionValueSet queryKey = new DimensionValueSet();
                if (!CollectionUtils.isEmpty(codes)) {
                    queryKey.setValue(dimName, codes);
                }
                queryKey.setValue("DATATIME", (Object)eParam.getPeriodValue());
                entityQuery.setMasterKeys(queryKey);
                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(dataDimension.getDimKey());
                entityQuery.setEntityView(entityView);
                entityQuery.setAuthorityOperations(AuthorityType.None);
                ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
                IEntityTable entityTables = entityQuery.executeReader((IContext)context);
                for (IEntityRow row : entityTables.getAllRows()) {
                    DimValue diValue = new DimValue();
                    diValue.setCode(row.getCode());
                    diValue.setTitle(row.getTitle());
                    dimValues.add(diValue);
                }
            }
            catch (Exception e) {
                throw new RuntimeException("\u67e5\u8be2\u60c5\u666f\u6570\u636e\u51fa\u9519", e);
            }
            entityDimValue.setDimValues(dimValues);
            allDims.add(entityDimValue);
        }
        return allDims;
    }

    private static int[] getExSteps(List<TaskDataFactory> factory) {
        int[] steps = new int[factory.size()];
        int i = 0;
        for (TaskDataFactory taskDataFactory : factory) {
            int weight = taskDataFactory.getWeight();
            steps[i++] = weight;
        }
        return steps;
    }

    private void addUnitFile(List<Unit> units, Map<String, String> originOrgCode, FileWriter writer) {
        try (OutputStream out = writer.createFile(UNIT_INFO);){
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(HEADER_NAMES).build();
            try (CSVPrinter csvPrinter = new CSVPrinter((Appendable)new OutputStreamWriter(out), csvFormat);){
                for (Unit unit : units) {
                    ArrayList<String> data = new ArrayList<String>();
                    String entityKey = unit.getEntityDataKey();
                    if (originOrgCode != null) {
                        entityKey = originOrgCode.getOrDefault(entityKey, entityKey);
                    }
                    data.add(entityKey);
                    data.add(unit.getCode());
                    data.add(unit.getTitle());
                    String parentKey = unit.getParentKey();
                    if (originOrgCode != null) {
                        parentKey = originOrgCode.getOrDefault(parentKey, parentKey);
                    }
                    data.add(parentKey);
                    BigDecimal order = unit.getOrder();
                    if (order != null) {
                        data.add(order.toPlainString());
                    } else {
                        data.add("0");
                    }
                    data.add(unit.getPath());
                    csvPrinter.printRecord(data);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u6784\u5efa\u6570\u636e\u5305\u5355\u4f4d\u8303\u56f4\u6e05\u5355\u5931\u8d25", e);
        }
    }

    private void readUnit(PackageData packageData, FileFinder finder) {
        ArrayList<Unit> units = new ArrayList<Unit>();
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(new String[0]).setSkipHeaderRecord(true).build();
        try (InputStream fileInputStream = finder.getFileInputStream(UNIT_INFO);){
            try (CSVParser csvParser = new CSVParser((Reader)new InputStreamReader(fileInputStream), format);){
                for (CSVRecord record : csvParser) {
                    Unit unit = new Unit();
                    unit.setEntityDataKey(record.get(HEADER_NAMES[0]));
                    unit.setCode(record.get(HEADER_NAMES[1]));
                    unit.setTitle(record.get(HEADER_NAMES[2]));
                    unit.setParentKey(record.get(HEADER_NAMES[3]));
                    unit.setOrder(new BigDecimal(record.get(HEADER_NAMES[4])));
                    unit.setPath(record.get(HEADER_NAMES[5]));
                    units.add(unit);
                }
            }
            packageData.setEntityKeys(units);
        }
        catch (Exception e) {
            throw new RuntimeException("\u8bfb\u53d6\u6570\u636e\u5305\u5355\u4f4d\u8303\u56f4\u6e05\u5355\u5931\u8d25", e);
        }
    }

    public List<Unit> getUnits(Collection<String> entityKeys, String dwEntityId, String dwDimName, String periodValue) {
        ArrayList<Unit> units = new ArrayList<Unit>();
        com.jiuqi.nr.entity.engine.intf.IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        try {
            DimensionValueSet masterKeys = new DimensionValueSet();
            if (!CollectionUtils.isEmpty(entityKeys)) {
                masterKeys.setValue(dwDimName, new ArrayList<String>(entityKeys));
            }
            masterKeys.setValue("DATATIME", (Object)periodValue);
            entityQuery.setMasterKeys(masterKeys);
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(dwEntityId);
            entityQuery.setEntityView(entityView);
            entityQuery.setAuthorityOperations(AuthorityType.None);
            ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
            IEntityTable entityTables = entityQuery.executeFullBuild((IContext)context);
            for (IEntityRow row : entityTables.getAllRows()) {
                Unit unit = new Unit();
                unit.setEntityDataKey(row.getEntityKeyData());
                CharSequence[] path = row.getParentsEntityKeyDataPath();
                unit.setParentKey(row.getParentEntityKey());
                unit.setCode(row.getCode());
                unit.setTitle(row.getTitle());
                String pathStr = String.join((CharSequence)"/", path);
                unit.setPath(pathStr);
                Object entityOrder = row.getEntityOrder();
                if (entityOrder instanceof BigDecimal) {
                    unit.setOrder((BigDecimal)entityOrder);
                }
                units.add(unit);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u4e3b\u4f53\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519", e);
        }
        return units;
    }

    private DefaultTransferContext buildContext(EParam eParam) {
        String formSchemeKey = eParam.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String taskKey = formScheme.getTaskKey();
        DefaultTransferContext context = new DefaultTransferContext();
        context.setTaskKey(taskKey);
        context.setFormSchemeKey(formSchemeKey);
        context.setFormKeys(eParam.getFormKeys());
        DimensionCollection masterColl = this.dimCollectionBuildUtil.buildDimensionCollection(eParam.getMasterKeys(), eParam.getFormSchemeKey());
        context.setMasterKeys(masterColl);
        ExpSettingsImpl expSettings = new ExpSettingsImpl();
        expSettings.setExportAttachments(eParam.isExportAttachments());
        context.setExportSettings(expSettings);
        for (String exportType : eParam.getExportTypes()) {
            StatisticalRecorderImpl statisticalRecorder = new StatisticalRecorderImpl(this.formStatisticService, null, exportType, null);
            context.putStatisticalRecorder(exportType, statisticalRecorder);
        }
        return context;
    }

    private DefaultTransferContext buildContext(IParam iParam, PackageData packageData, AnalysisRes analysis) {
        String formSchemeKey = iParam.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String taskKey = formScheme.getTaskKey();
        DefaultTransferContext context = new DefaultTransferContext();
        context.setTaskKey(taskKey);
        context.setFormSchemeKey(formSchemeKey);
        List<IEntityDataDTO> importUnits = this.getUnits(iParam);
        List<String> importTypes = iParam.getImportTypes();
        if (importTypes == null) {
            importTypes = packageData.getDataCatalog();
        }
        boolean isImportFMDM = importTypes.contains("FMDM_DATA");
        ImpSettingsImpl impSettings = new ImpSettingsImpl();
        ArrayList<String> updateUnitKeys = new ArrayList<String>();
        ArrayList<String> addUnitKeys = new ArrayList<String>();
        for (IEntityDataDTO unit : importUnits) {
            if (unit.getType() == EntityDataType.EXIST) {
                updateUnitKeys.add(unit.getKey());
                continue;
            }
            if (unit.getType() != EntityDataType.NOT_EXIST) continue;
            addUnitKeys.add(unit.getKey());
        }
        context.setAllDwCount(updateUnitKeys.size());
        if (isImportFMDM) {
            context.setAllDwCount(updateUnitKeys.size() + addUnitKeys.size());
            context.setNonexistentUnits(addUnitKeys);
            impSettings.setNonexistentUnits(addUnitKeys);
        }
        if (updateUnitKeys.isEmpty()) {
            context.setMasterKeys(null);
            impSettings.setMasterKeys(null);
        } else {
            Map<String, DimensionValue> dimensionSet = this.buildCurrMasterKeys(iParam, packageData, formScheme, updateUnitKeys);
            DimensionCollection masterColl = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, iParam.getFormSchemeKey());
            context.setMasterKeys(masterColl);
            impSettings.setMasterKeys(masterColl);
        }
        ParamsMapping paramsMapping = this.getParamsMapping(iParam, packageData);
        List<String> formKeys = this.buildFormKeys(packageData, paramsMapping);
        context.setFormKeys(formKeys);
        context.setImportSettings(impSettings);
        FilterDim filterDim = DefaultTaskDataService.buildFilterDim(iParam, analysis);
        impSettings.setFilterDim(filterDim);
        CompletionDim completionDim = this.buildCompletionDim(iParam, analysis);
        impSettings.setCompletionDim(completionDim);
        context.setParamsMapping(paramsMapping);
        context.setProviderStore(this.getProviderStore());
        List formDefines = this.runTimeViewController.queryFormsById(formKeys);
        HashMap<String, ImpMode> impModeMap = new HashMap<String, ImpMode>();
        for (String importType : importTypes) {
            impModeMap.put(importType, ImpMode.FULL_BY_DATA);
            if (iParam.isEmptyTableCover() && "FORMDATA".equals(importType)) {
                impModeMap.put(importType, ImpMode.FULL);
            }
            StatisticalRecorderImpl statisticalRecorder = new StatisticalRecorderImpl(this.formStatisticService, iParam.getRecKey(), importType, formDefines);
            context.putStatisticalRecorder(importType, statisticalRecorder);
            DescRecorderImpl descRecorder = new DescRecorderImpl(this.unitFailureService, iParam.getRecKey(), importType, iParam.getTaskKey(), analysis.getPeriodValue(), importUnits);
            context.putDescRecorder(importType, descRecorder);
        }
        impSettings.setImpModeMap(impModeMap);
        return context;
    }

    private CompletionDim buildCompletionDim(IParam iParam, AnalysisRes analysis) {
        CompletionDim completionDim = new CompletionDim();
        DimensionValueSet fixedCompletionDims = new DimensionValueSet();
        List<DimensionValue> completionDimVO = iParam.getCompletionDim();
        if (!CollectionUtils.isEmpty(completionDimVO)) {
            for (DimensionValue dimensionValue : completionDimVO) {
                fixedCompletionDims.setValue(dimensionValue.getName(), (Object)dimensionValue.getValue());
            }
            completionDim.setFixedCompletionDims(fixedCompletionDims);
        }
        ArrayList<String> dw1v1DimName = new ArrayList<String>();
        ArrayList<String> attrCodes = new ArrayList<String>();
        for (DimValueDTO completionDTO : analysis.getCompletionDims()) {
            if (!completionDTO.isDw1v1()) continue;
            attrCodes.add(completionDTO.getDimCode());
            dw1v1DimName.add(completionDTO.getDimName());
        }
        if (!CollectionUtils.isEmpty(dw1v1DimName)) {
            completionDim.setDynamicsCompletionDims(dw1v1DimName);
            CompletionDimFinderImpl finder = new CompletionDimFinderImpl();
            finder.setDw1v1DimNames(dw1v1DimName);
            finder.setAttrCodes(attrCodes);
            String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
            if (org.springframework.util.StringUtils.hasLength(contextEntityId)) {
                finder.setDwEntityId(contextEntityId);
            } else {
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(iParam.getTaskKey());
                finder.setDwEntityId(taskDefine.getDw());
            }
            finder.setTaskKey(iParam.getTaskKey());
            finder.setPeriodValue(analysis.getPeriodValue());
            completionDim.setFinder((CompletionDimFinder)finder);
        }
        return completionDim;
    }

    private static FilterDim buildFilterDim(IParam iParam, AnalysisRes analysis) {
        FilterDim filterDim = new FilterDim();
        List<DimensionValue> filterDimVO = iParam.getFilterDim();
        if (!CollectionUtils.isEmpty(filterDimVO)) {
            DimensionValueSet fix = new DimensionValueSet();
            for (DimensionValue dimensionValue : filterDimVO) {
                fix.setValue(dimensionValue.getName(), (Object)dimensionValue.getValue());
            }
            filterDim.setFixedFilterDims(fix);
        }
        ArrayList<String> dw1v1DimName = new ArrayList<String>();
        for (DimValueDTO filterDimDto : analysis.getFilterDims()) {
            if (!filterDimDto.isDw1v1()) continue;
            dw1v1DimName.add(filterDimDto.getDimName());
        }
        if (!CollectionUtils.isEmpty(dw1v1DimName)) {
            filterDim.setDynamicsFilterDims(dw1v1DimName);
        }
        return filterDim;
    }

    private List<IEntityDataDTO> getUnits(IParam iParam) {
        List<IEntityDataDTO> importUnits;
        List<String> unitKeys = iParam.getUnitKeys();
        if (CollectionUtils.isEmpty(unitKeys)) {
            UnitQueryParam unitQueryParam = new UnitQueryParam();
            unitQueryParam.setTypes(new EntityDataType[]{EntityDataType.EXIST, EntityDataType.NOT_EXIST});
            unitQueryParam.setDwSchemeKey(iParam.getDwSchemeKey());
            importUnits = this.getUnitByParam(unitQueryParam);
        } else {
            UnitQueryParam unitQueryParam = new UnitQueryParam();
            unitQueryParam.setTypes(new EntityDataType[]{EntityDataType.EXIST, EntityDataType.NOT_EXIST});
            unitQueryParam.setDwSchemeKey(iParam.getDwSchemeKey());
            unitQueryParam.setEntityIds(unitKeys);
            importUnits = this.getUnitByParam(unitQueryParam);
        }
        return importUnits;
    }

    private List<String> buildFormKeys(PackageData packageData, ParamsMapping paramsMapping) {
        List<Form> forms = packageData.getForms();
        Map originFormKey = paramsMapping.getOriginFormKey(forms.stream().map(Form::getKey).collect(Collectors.toList()));
        Collection formKeys = originFormKey.values();
        return new ArrayList<String>(formKeys);
    }

    private ParamsMapping getParamsMapping(IParam iParam, PackageData packageData) {
        AnalysisParam analysisParam = new AnalysisParam();
        analysisParam.setTaskKey(iParam.getTaskKey());
        analysisParam.setFormSchemeKey(iParam.getFormSchemeKey());
        analysisParam.setMappingKey(iParam.getMappingKey());
        return this.paramsMappingService.getParamsMapping(analysisParam, packageData);
    }

    private Map<String, DimensionValue> buildCurrMasterKeys(IParam iParam, PackageData packageData, FormSchemeDefine formScheme, List<String> updateUnitKeys) {
        String dw = formScheme.getDw();
        String dwDimName = this.entityMetaService.getDimensionName(dw);
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue dwValue = new DimensionValue();
        dwValue.setName(dwDimName);
        dwValue.setValue(String.join((CharSequence)";", updateUnitKeys));
        dimensionSet.put(dwDimName, dwValue);
        DimensionValue periodValue = new DimensionValue();
        periodValue.setName("DATATIME");
        periodValue.setValue(packageData.getPeriodValue());
        dimensionSet.put("DATATIME", periodValue);
        List<DimValueDTO> allDims = packageData.getAllDims();
        List<DimensionValue> filterDim = iParam.getFilterDim();
        if (allDims != null && filterDim != null) {
            Set filterNames = filterDim.stream().map(DimensionValue::getName).collect(Collectors.toSet());
            for (DimValueDTO dimValueDTO : allDims) {
                if (filterNames.contains(dimValueDTO.getDimName())) continue;
                String dimName = dimValueDTO.getDimName();
                List dimValues = dimValueDTO.getDimValues().stream().map(DimValue::getCode).collect(Collectors.toList());
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimName);
                dimensionValue.setValue(String.join((CharSequence)";", dimValues));
                dimensionSet.put(dimName, dimensionValue);
            }
        }
        return dimensionSet;
    }

    protected IProviderStore getProviderStore() {
        return this.providerStore;
    }

    private Result<AnalysisRes> getAnalysisResult(AnalysisRes analysisRes, AnalysisResultEnum analysisResultEnum) {
        Result resResult = new Result();
        resResult.setDatas((Object)analysisRes);
        resResult.setCode(analysisResultEnum.getErrorCode());
        resResult.setMessage(analysisResultEnum.getMessage());
        return resResult;
    }

    private List<ExportType> getExportTypes(List<String> packageExportTypes, boolean containFmdmForm) {
        ArrayList<ExportType> exportTypes = new ArrayList<ExportType>();
        List allFactory = this.taskDataFactoryManager.getAllFactory();
        for (TaskDataFactory taskDataFactory : allFactory) {
            if (!packageExportTypes.contains(taskDataFactory.getCode()) || "FMDM_DATA".equals(taskDataFactory.getCode()) && !containFmdmForm) continue;
            ExportType exportType = new ExportType();
            exportType.setName(taskDataFactory.getName());
            exportType.setDescription(taskDataFactory.getDescription());
            exportType.setCode(taskDataFactory.getCode());
            exportTypes.add(exportType);
        }
        return exportTypes;
    }

    private static List<IEntityDataRow> buildEntityDataRows(List<Unit> exportUnits, Map<String, String> orgMapping) {
        ArrayList<IEntityDataRow> entityDataRows = new ArrayList<IEntityDataRow>();
        for (Unit exportUnit : exportUnits) {
            UnitEntityDataRow entityDataRow = new UnitEntityDataRow(exportUnit);
            String entityKey = exportUnit.getEntityDataKey();
            entityDataRow.setEntityDataKey(orgMapping.getOrDefault(entityKey, entityKey));
            String parentKey = exportUnit.getParentKey();
            entityDataRow.setParentKey(orgMapping.getOrDefault(parentKey, parentKey));
            entityDataRows.add(entityDataRow);
        }
        return entityDataRows;
    }

    private IEntityQuery getEntityQuery(QueryParam queryParam) {
        IEntityQuery entityQuery;
        try {
            entityQuery = this.extEntityDataService.getEntityQuery(queryParam);
        }
        catch (EntityDataException e) {
            this.logger.error("\u83b7\u53d6\u81ea\u5b9a\u4e49\u5b9e\u4f53\u6570\u636e\u67e5\u8be2\u5668\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new RuntimeException(e);
        }
        return entityQuery;
    }

    static {
        DefaultTaskDataService.HEADER_NAMES[0] = "KEY";
        DefaultTaskDataService.HEADER_NAMES[1] = "CODE";
        DefaultTaskDataService.HEADER_NAMES[2] = "TITLE";
        DefaultTaskDataService.HEADER_NAMES[3] = "PARENT_KEY";
        DefaultTaskDataService.HEADER_NAMES[4] = "ORDER";
        DefaultTaskDataService.HEADER_NAMES[5] = "PATHS";
        CURRENT_VERSION = VERSION_1_0_0;
    }

    private static class DimensionInfo {
        private String dimKey;
        private String dimName;
        private String entityTitle;
        private String dimAttributeName;

        public DimensionInfo(String dimKey, String dimName, String entityTitle, String dimAttributeName) {
            this.dimKey = dimKey;
            this.dimName = dimName;
            this.entityTitle = entityTitle;
            this.dimAttributeName = dimAttributeName;
        }

        public String getDimKey() {
            return this.dimKey;
        }

        public void setDimKey(String dimKey) {
            this.dimKey = dimKey;
        }

        public String getDimName() {
            return this.dimName;
        }

        public void setDimName(String dimName) {
            this.dimName = dimName;
        }

        public String getEntityTitle() {
            return this.entityTitle;
        }

        public void setEntityTitle(String entityTitle) {
            this.entityTitle = entityTitle;
        }

        public String getDimAttributeName() {
            return this.dimAttributeName;
        }

        public void setDimAttributeName(String dimAttributeName) {
            this.dimAttributeName = dimAttributeName;
        }
    }
}

