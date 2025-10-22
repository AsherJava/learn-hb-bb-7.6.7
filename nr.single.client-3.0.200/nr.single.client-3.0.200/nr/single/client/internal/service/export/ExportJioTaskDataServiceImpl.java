/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentry.bean.BatchExportData
 *  com.jiuqi.nr.dataentry.bean.BatchExportInfo
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl
 *  com.jiuqi.nr.dataentry.model.BatchDimensionParam
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.dataentry.util.DataEntryUtil
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.jtable.dataset.IReportExportDataSet
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  javax.annotation.Resource
 *  nr.single.data.datain.service.ITaskFileBatchImportDataService
 *  nr.single.data.dataout.service.IFormDataExportService
 *  nr.single.data.dataout.service.ITaskFileBatchExportDataService
 *  nr.single.data.dataout.service.ITaskFileExportDataService
 *  nr.single.data.util.TaskFileDataOperateUtil
 *  nr.single.data.util.service.ISingleAttachmentService
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleDataError
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.service.SingleDimissionServcie
 *  nr.single.map.data.service.TaskDataService
 */
package nr.single.client.internal.service.export;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.client.bean.JioExportData;
import nr.single.client.service.entity.SingleEntityTypeService;
import nr.single.client.service.entitycheck.ISingleExportEntityCheckService;
import nr.single.client.service.export.IExportDataCheckResult;
import nr.single.client.service.export.IExportJioTaskDataService;
import nr.single.client.service.querycheck.ISingleExportQueryCheckService;
import nr.single.client.service.upload.bean.FormShareItem;
import nr.single.client.service.upload.bean.FormShareManager;
import nr.single.data.datain.service.ITaskFileBatchImportDataService;
import nr.single.data.dataout.service.IFormDataExportService;
import nr.single.data.dataout.service.ITaskFileBatchExportDataService;
import nr.single.data.dataout.service.ITaskFileExportDataService;
import nr.single.data.util.TaskFileDataOperateUtil;
import nr.single.data.util.service.ISingleAttachmentService;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.service.SingleDimissionServcie;
import nr.single.map.data.service.TaskDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportJioTaskDataServiceImpl
implements IExportJioTaskDataService {
    private static final Logger log = LoggerFactory.getLogger(ExportJioTaskDataServiceImpl.class);
    @Resource
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IJtableResourceService jtableResourceService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private TaskDataService taskDataService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private ReadWriteAccessProvider readWriteAccessProvider;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private ISingleAttachmentService singleAttachService;
    @Autowired
    private ITaskFileBatchImportDataService batchImportService;
    @Autowired
    private IFormDataExportService formDataService;
    @Autowired
    private ISecretLevelService iSecretLevelService;
    @Autowired
    private ITaskFileBatchExportDataService batchExportDataService;
    @Autowired
    private ITaskFileExportDataService exportDataService;
    @Autowired
    private ExportExcelNameServiceImpl exportExcelNameService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IExportDataCheckResult exportCheckResultServce;
    @Autowired
    private SingleEntityTypeService entityTypeService;
    @Autowired
    private IAdjustPeriodService adjuestPeriodService;
    @Autowired
    private SingleDimissionServcie singleDimService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired(required=false)
    private ISingleExportEntityCheckService exportEntityCheckService;
    @Autowired
    private ISingleExportQueryCheckService exportQueryCheckService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private NpApplication npApplication;
    private static final String INOF_IMPORT_FORM = "\u5bfc\u51faJIO\u6570\u636e\uff1a===\u8868\u5355";
    private static final String LOG_TITLE = "JIO\u6570\u636e\u5bfc\u51fa";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public JioExportData export(ExportParam param, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        JtableContext jtableContext = param.getContext();
        JioExportData data = null;
        TaskDataContext context = new TaskDataContext();
        try {
            Map.Entry entry;
            String corpKey;
            String corpCode;
            DimensionValue corpDim;
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.1, "");
                context.setAsyncTaskMonitor(asyncTaskMonitor);
            }
            context.info(log, "\u5f53\u524d\u5355\u4f4d\u5bfc\u51faJIO");
            String workPath = null;
            workPath = StringUtils.isEmpty((String)param.getExportWorkDir()) ? PathUtil.getExportTempFilePath((String)"JIOEXPORT") : param.getExportWorkDir();
            String taskFilePath = PathUtil.createNewPath((String)workPath, (String)(OrderGenerator.newOrder() + ".TSK"));
            String taskDataPath = PathUtil.createNewPath((String)taskFilePath, (String)"DATA");
            String jioFile = workPath + OrderGenerator.newOrder() + ".jio";
            FormDefine fmdmForm = this.viewController.queryFormByCodeInScheme(jtableContext.getFormSchemeKey(), "FMDM");
            String fmdmFormKey = null;
            fmdmFormKey = null != fmdmForm ? fmdmForm.getKey() : this.funcExecuteService.getFMDMFormKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
            context.setTaskKey(jtableContext.getTaskKey());
            context.setFormSchemeKey(jtableContext.getFormSchemeKey());
            this.entityTypeService.getEntityType(context, jtableContext.getFormSchemeKey(), param.getContext().getDimensionSet());
            this.taskDataService.initContext(context, jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), param.getConfigKey());
            context.setVariableMap(jtableContext.getVariableMap());
            context.setFmdmFormKey(fmdmFormKey);
            context.setNeedCreateDBF(true);
            context.setTaskDataPath(taskDataPath);
            context.setNeedDownLoad(true);
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.15, "");
                context.setSyncTaskID(asyncTaskMonitor.getTaskId());
            }
            ArrayList<String> forms = new ArrayList<String>();
            if (StringUtils.isNotEmpty((String)fmdmFormKey)) {
                forms.add(fmdmFormKey);
            }
            if (!param.isAllForm() && StringUtils.isEmpty((String)param.getFormKeys())) {
                if (StringUtils.isNotEmpty((String)jtableContext.getFormKey())) {
                    FormDefine formDefine;
                    if (!fmdmFormKey.equalsIgnoreCase(jtableContext.getFormKey())) {
                        forms.add(jtableContext.getFormKey());
                    }
                    if ((formDefine = this.viewController.queryFormById(jtableContext.getFormKey())) != null) {
                        context.setCurrentFormTitle(formDefine.getTitle());
                    }
                } else {
                    context.info(log, "\u5bfc\u51faJIO\u6570\u636e\uff1a===\u5f53\u524d\u8868\u5355KEY\u4e3a\u7a7a,\u65e0\u6cd5\u5bfc\u51fa");
                }
            } else {
                String formKeys = param.getFormKeys();
                List forms2 = DataEntryUtil.getAllForms((IDataEntryParamService)this.dataEntryParamService, (ExportParam)param, (String)formKeys);
                for (FormData aform : forms2) {
                    if (!forms.contains(aform.getKey())) {
                        forms.add(aform.getKey());
                    }
                    context.setCurrentFormTitle(aform.getTitle());
                }
                if (forms2.size() == 1) {
                    context.setCurrentFormTitle(((FormData)forms2.get(0)).getTitle());
                }
            }
            FormSchemeDefine formScheme = this.viewController.getFormScheme(jtableContext.getFormSchemeKey());
            DimensionValue dateDim = (DimensionValue)jtableContext.getDimensionSet().get(context.getEntityDateType());
            if (null != dateDim && StringUtils.isNotEmpty((String)dateDim.getValue())) {
                dateDim.setValue(this.taskDataService.getNetPeriodCode(context, dateDim.getValue()));
            }
            if (null != (corpDim = (DimensionValue)jtableContext.getDimensionSet().get(context.getEntityCompanyType())) && StringUtils.isNotEmpty((String)corpDim.getValue()) && StringUtils.isNotEmpty((String)(corpCode = corpDim.getValue())) && corpCode.indexOf("NBCORP@") < 0) {
                context.getDownloadEntityKeys().add(corpCode);
            }
            this.taskDataService.MapSingleEnityData(context);
            if (null != corpDim && StringUtils.isNotEmpty((String)corpDim.getValue())) {
                corpCode = corpDim.getValue();
                corpDim.setValue(this.taskDataService.getNetCompanyKey(context, corpCode));
            }
            HashMap<String, DimensionValue> mapNoUnit = new HashMap<String, DimensionValue>();
            for (String dimCode : param.getContext().getDimensionSet().keySet()) {
                if (dimCode.equals(context.getEntityCompanyType())) continue;
                DimensionValue dim = (DimensionValue)param.getContext().getDimensionSet().get(dimCode);
                mapNoUnit.put(dimCode, dim);
            }
            context.info(log, "\u5bfc\u51faJIO\uff1a\u8bfb\u53d6\u6743\u9650");
            IBatchAccessResult batchAccess = this.getBatchAuth(formScheme, jtableContext.getDimensionSet(), forms, 0);
            LinkedHashMap<String, List<String>> formSeeUnits = new LinkedHashMap<String, List<String>>();
            ArrayList<String> corpList = new ArrayList<String>();
            if (param.isAllCorp()) {
                List authEnties = this.taskDataService.getAuthEntityData(context, dateDim.getValue());
                Iterator iterator = authEnties.iterator();
                while (iterator.hasNext()) {
                    String corpKey1;
                    corpKey = corpKey1 = (String)iterator.next();
                    DataEntityInfo entityInfo = context.getEntityCache().findEntityByKey(corpKey1);
                    if (context.getEntityCodeKeyMap().containsKey(corpKey1)) {
                        corpKey = (String)context.getEntityCodeKeyMap().get(corpKey1);
                    } else {
                        if (entityInfo == null) continue;
                        corpKey = entityInfo.getEntityKey();
                    }
                    this.checkUnitFormAccess(context, mapNoUnit, corpKey, batchAccess, corpList, forms, formSeeUnits);
                }
            } else {
                String copyCode = ((DimensionValue)jtableContext.getDimensionSet().get(context.getEntityCompanyType())).getValue();
                String[] corpArray = copyCode.split(";");
                for (int i = 0; i < corpArray.length; ++i) {
                    if (!StringUtils.isNotEmpty((String)corpArray[i])) continue;
                    corpKey = corpArray[i];
                    this.checkUnitFormAccess(context, mapNoUnit, corpKey, batchAccess, corpList, forms, formSeeUnits);
                }
            }
            this.taskDataService.makeExportEnityList(context, corpList);
            boolean isMeasureMode = false;
            if (jtableContext.getMeasureMap() != null && jtableContext.getMeasureMap().size() > 0 && (entry = (Map.Entry)jtableContext.getMeasureMap().entrySet().stream().findFirst().orElse(null)) != null) {
                context.setMeasureKey((String)entry.getKey());
                context.setMeasureCode((String)jtableContext.getMeasureMap().get(entry.getKey()));
                context.setMeasureDecimal(jtableContext.getDecimal());
            }
            if (corpList.size() > 0 && !isMeasureMode) {
                context.info(log, "\u5bfc\u51faJIO\uff1a\u6309\u8868\u5355\u5bfc\u51fa");
                StringBuilder sb = new StringBuilder();
                for (String unitkey : corpList) {
                    if (sb.length() > 0) {
                        sb.append(";");
                    }
                    sb.append(unitkey);
                }
                LinkedHashMap<String, DimensionValue> newDimMap = new LinkedHashMap<String, DimensionValue>();
                newDimMap.putAll(mapNoUnit);
                DimensionValue unitDim = new DimensionValue();
                unitDim.setName(context.getEntityCompanyType());
                unitDim.setValue(sb.toString());
                newDimMap.put(unitDim.getName(), unitDim);
                this.exportCheckResultServce.LoadNetCheckDataToCache(context, taskDataPath, newDimMap, null);
                for (int i = 0; i < forms.size(); ++i) {
                    String curFormKey = (String)forms.get(i);
                    context.info(log, "\u5bfc\u51faJIO\uff1a\u8868\u5355\uff1a" + curFormKey);
                    context.getIntfObjects().put("fileService", this.fileService);
                    context.getIntfObjects().put("fileInfoService", this.fileInfoService);
                    context.getIntfObjects().put("singleAttachService", this.singleAttachService);
                    context.getIntfObjects().put("tenantRuntime", "__default_tenant__");
                    context.getIntfObjects().put("formSeeUnits", formSeeUnits);
                    context.getIntfObjects().put("batchImportService", this.batchImportService);
                    context.getIntfObjects().put("dataRuntimeController", this.dataRuntimeController);
                    this.batchExportDataService.batchOperFormData(context, taskDataPath, curFormKey, mapNoUnit, corpList);
                    this.exportCheckResultServce.exportCheckDataFromCache(context, taskDataPath, curFormKey);
                    if (null == asyncTaskMonitor) continue;
                    asyncTaskMonitor.progressAndMessage(0.2 + 0.7 * (double)(((float)(i * 2) + 1.0f) / (float)((forms.size() + 1) * 2)), "");
                    if (!asyncTaskMonitor.isCancel()) continue;
                    context.info(log, "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88");
                    DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(LOG_TITLE, OperLevel.USER_OPER);
                    logHelper.info(context.getTaskKey(), null, "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88", "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88");
                    asyncTaskMonitor.canceled(null, null);
                    PathUtil.deleteDir((String)taskFilePath);
                    return data;
                }
                this.exportCheckResultServce.exportCheckDataFromCache(context, taskDataPath, "00000000-0000-0000-0000-000000000000");
            } else if (!corpList.isEmpty()) {
                for (int i = 0; i < corpList.size(); ++i) {
                    ((DimensionValue)jtableContext.getDimensionSet().get(context.getEntityCompanyType())).setValue((String)corpList.get(i));
                    String corpKey2 = (String)corpList.get(i);
                    context.setCurrentEntintyKey(corpKey2);
                    if (context.getEntityKeyZdmMap().containsKey(corpKey2)) {
                        context.setCurrentZDM((String)context.getEntityKeyZdmMap().get(corpKey2));
                    }
                    boolean isCreateDBF = i == 0;
                    context.setNeedCreateDBF(isCreateDBF);
                    this.exportCheckResultServce.LoadNetCheckDataToCache(context, taskDataPath, jtableContext.getDimensionSet(), null);
                    for (String formKey : forms) {
                        String formCode = "";
                        FormDefine formDefine = this.viewController.queryFormById(formKey);
                        if (formDefine != null) {
                            formCode = formDefine.getFormCode();
                        }
                        boolean isFMDM = formKey.equalsIgnoreCase(fmdmFormKey);
                        boolean isCanSee = formSeeUnits.containsKey(formKey);
                        if (isCanSee) {
                            List seeUnits = (List)formSeeUnits.get(formKey);
                            isCanSee = seeUnits.contains(corpKey2);
                        }
                        if (isFMDM || isCanSee) {
                            context.info(log, INOF_IMPORT_FORM + formCode + "," + formKey + ",\u5355\u4f4d:" + corpKey2);
                            this.exportDataToDBF(param.getContext(), context, isCreateDBF, true, asyncTaskMonitor, formKey);
                            this.exportCheckResultServce.exportCheckDataFromCache(context, taskDataPath, formKey);
                        } else {
                            context.info(log, INOF_IMPORT_FORM + formCode + "," + formKey + ",\u5355\u4f4d:" + corpKey2 + ",\u65e0\u8bfb\u53d6\u6743\u9650");
                        }
                        if (null == asyncTaskMonitor || !asyncTaskMonitor.isCancel()) continue;
                        context.info(log, "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88");
                        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(LOG_TITLE, OperLevel.USER_OPER);
                        logHelper.info(context.getTaskKey(), null, "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88", "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88");
                        asyncTaskMonitor.canceled(null, null);
                        PathUtil.deleteDir((String)taskFilePath);
                        return data;
                    }
                    this.exportCheckResultServce.exportCheckDataFromCache(context, taskDataPath, "00000000-0000-0000-0000-000000000000");
                    if (null != asyncTaskMonitor) {
                        asyncTaskMonitor.progressAndMessage(0.2 + 0.7 * (double)(((float)(i * 2) + 0.0f) / (float)((corpList.size() + 1) * 2)), "");
                    }
                    if (null == asyncTaskMonitor) continue;
                    asyncTaskMonitor.progressAndMessage(0.2 + 0.7 * (double)(((float)(i * 2) + 1.0f) / (float)((corpList.size() + 1) * 2)), "");
                }
            } else {
                context.info(log, "\u5bfc\u51faJIO\u6570\u636e\uff1a===\u6240\u6709\u5355\u4f4d\u90fd\u65e0\u6743\u9650");
            }
            if (this.exportEntityCheckService != null) {
                this.exportEntityCheckService.exportEntityCheckResult(context, taskDataPath, jtableContext.getDimensionSet(), asyncTaskMonitor);
            }
            if (this.exportQueryCheckService != null) {
                this.exportQueryCheckService.exportQueryCheckResult(context, taskDataPath, jtableContext.getDimensionSet(), asyncTaskMonitor);
            }
            this.formDataService.ExpoxtTaskDataToSingleFromScheme(context, context.getFormSchemeKey(), taskFilePath, jioFile);
            PathUtil.deleteDir((String)taskFilePath);
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.9, "");
            }
            String fileName = context.getCurrentFormTitle();
            if (forms.size() > 2) {
                DataEntityInfo entityInfo = context.getEntityCache().findEntityByKey(context.getCurrentEntintyKey());
                if (entityInfo == null) {
                    entityInfo = context.getEntityCache().findEntityByCode(context.getCurrentEntintyKey());
                }
                if (entityInfo != null && StringUtils.isNotEmpty((String)entityInfo.getEntityTitle())) {
                    fileName = entityInfo.getEntityTitle();
                }
            }
            try (FileInputStream fileStream = new FileInputStream(SinglePathUtil.normalize((String)jioFile));){
                if (this.iSecretLevelService.secretLevelEnable(jtableContext.getTaskKey())) {
                    SecretLevelInfo secretLevelInfo = this.iSecretLevelService.getSecretLevel(jtableContext);
                    fileName = fileName + this.exportExcelNameService.getSysSeparator() + secretLevelInfo.getSecretLevelItem().getTitle();
                }
                byte[] Buffer = new byte[fileStream.available()];
                fileStream.read(Buffer, 0, fileStream.available());
                data = new JioExportData(fileName, Buffer);
                data.setAttachFileNum(context.getAttachFileNum());
                context.info(log, "\u5bfc\u51fa\u7684\u6570\u636e\u9644\u4ef6\u6570\uff1a" + context.getAttachFileNum());
            }
            finally {
                PathUtil.deleteFile((String)jioFile);
            }
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.98, "");
            }
        }
        catch (Exception ex) {
            context.error(log, ex.getMessage(), (Throwable)ex);
            throw ex;
        }
        return data;
    }

    private void checkUnitFormAccess(TaskDataContext context, Map<String, DimensionValue> mapNoUnit, String corpKey, IBatchAccessResult batchAccess, List<String> corpList, List<String> forms, Map<String, List<String>> formSeeUnits) {
        LinkedHashMap<String, DimensionValue> newDimMap = new LinkedHashMap<String, DimensionValue>();
        DimensionValue unitDim = new DimensionValue();
        unitDim.setName(context.getEntityCompanyType());
        unitDim.setValue(corpKey);
        unitDim.setType(1);
        newDimMap.putAll(mapNoUnit);
        newDimMap.put(unitDim.getName(), unitDim);
        List<String> haveSeeList = this.getAccessFormsResult(context.getFormSchemeKey(), batchAccess, newDimMap, forms);
        if (haveSeeList.size() > 0) {
            corpList.add(corpKey);
            for (String formKey : haveSeeList) {
                List<Object> seeUnitls = null;
                if (formSeeUnits.containsKey(formKey)) {
                    seeUnitls = formSeeUnits.get(formKey);
                } else {
                    seeUnitls = new ArrayList();
                    formSeeUnits.put(formKey, seeUnitls);
                }
                seeUnitls.add(corpKey);
            }
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public JioExportData ExportBathchDataByPeriods(BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor, List<BatchDimensionParam> dimensionInfoBuilds, List<String> multiplePeriodList, List<String> formKeys, String dateDir, List<BatchExportData> datas) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 45[FORLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void oneOperFormData(TaskDataContext context, String filePath, String netFormKey, Map<String, DimensionValue> mapNoUnit, List<String> corpList, String curFormKey, boolean isFMDM, Map<String, DimensionValue> unitDic, JtableContext jtableContext, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        JtableContext paramContext = new JtableContext(jtableContext);
        for (Map.Entry<String, DimensionValue> entry : mapNoUnit.entrySet()) {
            String dimName = entry.getKey();
            DimensionValue dimValue = (DimensionValue)paramContext.getDimensionSet().get(dimName);
            if (dimValue == null) continue;
            dimValue.setValue(entry.getValue().getValue());
        }
        int iIndex = 0;
        Map formSeeUnits = (Map)context.getIntfObjects().get("formSeeUnits");
        List formSeeUnitList = (List)formSeeUnits.get(curFormKey);
        for (String unitkey : corpList) {
            if (!unitDic.containsKey(unitkey)) continue;
            if (formSeeUnitList != null) {
                if (!formSeeUnitList.contains(unitkey)) {
                    continue;
                }
            } else if (!isFMDM) {
                context.info(log, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u91cf\u7eb2\u6a21\u5f0f\uff0c" + curFormKey + ",\u8be5\u8868\u65e0\u6743\u9650");
                continue;
            }
            ((DimensionValue)paramContext.getDimensionSet().get(context.getEntityCompanyType())).setValue(unitkey);
            if (StringUtils.isNotEmpty((String)unitkey) && context.getDimEntityCache().getEntitySingleDims().size() > 0 && context.getDimEntityCache().getEntitySingleDimList().containsKey(unitkey)) {
                Map unitSingleDims = (Map)context.getDimEntityCache().getEntitySingleDimList().get(unitkey);
                for (String dimCode : unitSingleDims.keySet()) {
                    String dimValue = ((DimensionValue)unitSingleDims.get(dimCode)).getValue();
                    ((DimensionValue)paramContext.getDimensionSet().get(dimCode)).setValue(dimValue);
                }
            }
            String corpKey = unitkey;
            context.setCurrentEntintyKey(corpKey);
            if (context.getEntityKeyZdmMap().containsKey(corpKey)) {
                context.setCurrentZDM((String)context.getEntityKeyZdmMap().get(corpKey));
            }
            boolean isCreateDBF = iIndex == 0;
            context.setNeedCreateDBF(isCreateDBF);
            this.exportDataToDBF(paramContext, context, isCreateDBF, true, asyncTaskMonitor, curFormKey);
            ++iIndex;
        }
    }

    public void exportDataToDBF(JtableContext jtableContext, TaskDataContext context, boolean isNeedCreateDBF, boolean isNeedFMDM, AsyncTaskMonitor asyncTaskMonitor, String formKey) throws Exception {
        context.setNeedCreateDBF(isNeedCreateDBF);
        DimensionValue periodDimValue = (DimensionValue)jtableContext.getDimensionSet().get(context.getEntityDateType());
        DimensionValue companyDimValue = (DimensionValue)jtableContext.getDimensionSet().get(context.getEntityCompanyType());
        String corpID = null;
        if (null != companyDimValue && StringUtils.isNotEmpty((String)companyDimValue.getValue())) {
            corpID = companyDimValue.getValue();
            context.setCurrentEntintyKey(corpID);
        }
        if (null != periodDimValue) {
            context.setNetPeriodCode(periodDimValue.getValue());
        }
        try {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(context.getFormSchemeKey());
            context.setFormScheme(formScheme);
            double firstLevel = 0.2;
            double idIndex = 0.0;
            if (null != asyncTaskMonitor) {
                double percent = firstLevel + 0.35 * (idIndex += 1.0) / 1.0;
                asyncTaskMonitor.progressAndMessage(percent, "");
            }
            JtableContext paramContext = new JtableContext(jtableContext);
            paramContext.setFormKey(formKey);
            FormDefine formDefine = this.runtimeView.queryFormById(formKey);
            if (formDefine == null) {
                return;
            }
            String formCode = formDefine.getFormCode();
            String formTitle = formDefine.getTitle();
            if (StringUtils.isEmpty((String)context.getCurrentFormTitle())) {
                context.setCurrentFormTitle(formTitle);
            }
            context.info(log, INOF_IMPORT_FORM + formCode + "," + formKey + ",\u5b9e\u4f53\uff1a" + corpID);
            boolean isFMDM = formKey.equalsIgnoreCase(context.getFmdmFormKey());
            if (!isFMDM && null != context.getMapingCache().getNetFieldMap() && !context.getMapingCache().getNetFieldMap().containsKey(formCode)) {
                return;
            }
            IReportExportDataSet reportExportData = this.jtableResourceService.getReportExportData(paramContext);
            context.getIntfObjects().put("fileService", this.fileService);
            context.getIntfObjects().put("fileInfoService", this.fileInfoService);
            context.getIntfObjects().put("singleAttachService", this.singleAttachService);
            context.getIntfObjects().put("tenantRuntime", "__default_tenant__");
            context.getIntfObjects().put("batchImportService", this.batchImportService);
            this.exportDataService.dataOper(context, reportExportData, context.getTaskDataPath());
        }
        catch (Exception ex) {
            context.error(log, ex.getMessage(), (Throwable)ex);
            throw ex;
        }
    }

    private String getLogEntityInfo(TaskDataContext context) {
        String corpinfo1 = "\u9009\u62e9\u7684\u5355\u4f4d\u8303\u56f4\u4e2a\u6570\uff1a" + String.valueOf(context.getSourceCorpCount()) + "\u4e2a";
        String corpinfo2 = String.format("%s,\u5bfc\u51fa\u5230JIO\u6587\u4ef6\u4e2d\u7684\u5355\u4f4d\u4e2a\u6570\uff1a%d\u4e2a\uff1b", corpinfo1, context.getSuccessCorpCount());
        String corpinfo3 = "";
        List errorLogs = (List)context.getLogs().get("MAPFMDM");
        if (errorLogs != null && errorLogs.size() > 0) {
            for (SingleDataError error : errorLogs) {
                if (!context.getDownloadEntityKeyZdmMap().containsKey(error.getCompanyCode()) && !context.getDownloadEntityKeyZdmMap().containsKey(error.getCompanyKey())) continue;
                if (StringUtils.isNotEmpty((String)corpinfo3)) {
                    corpinfo3 = corpinfo3 + ";";
                }
                if ("empty".equalsIgnoreCase(error.getErrorCode())) {
                    corpinfo3 = String.format("%s %s\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u4e3a\u7a7a", corpinfo3, error.getCompanyCode());
                    continue;
                }
                if ("smallLength".equalsIgnoreCase(error.getErrorCode())) {
                    corpinfo3 = String.format("%s %s\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u957f\u5ea6\u4e0d\u8db3\uff0c\u503c\u4e3a\uff1a%s", corpinfo3, error.getCompanyCode(), error.getSingleCode());
                    continue;
                }
                if ("bigLength".equalsIgnoreCase(error.getErrorCode())) {
                    corpinfo3 = String.format("%s %s\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u957f\u5ea6\u8d85\u957f\uff0c\u503c\u4e3a\uff1a%s", corpinfo3, error.getCompanyCode(), error.getSingleCode());
                    continue;
                }
                if (!"codeRepeat".equalsIgnoreCase(error.getErrorCode())) continue;
                corpinfo3 = String.format("%s %s\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u91cd\u590d\uff0c\u503c\u4e3a\uff1a%s", corpinfo3, error.getCompanyCode(), error.getSingleCode());
            }
        }
        String corpinfo = String.format("%s,\u5bfc\u51fa\u5f02\u5e38\u7684\u5355\u4f4d\u6709\uff1a%s", corpinfo2, corpinfo3);
        return corpinfo;
    }

    private IBatchAccessResult getBatchAuth(FormSchemeDefine formScheme, Map<String, DimensionValue> masterDims, List<String> formKeys, int authType) {
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(formScheme.getTaskKey(), formScheme.getKey());
        DimensionCollection masterKey = DimensionValueSetUtil.buildDimensionCollection(masterDims, (String)formScheme.getKey());
        IBatchAccessResult batchAccess = null;
        batchAccess = authType == 1 ? dataAccessService.getWriteAccess(masterKey, formKeys) : dataAccessService.getReadAccess(masterKey, formKeys);
        return batchAccess;
    }

    private List<String> getAccessFormsResult(String formSchemeKey, IBatchAccessResult batchAccess, Map<String, DimensionValue> unitDim, List<String> formKeys) {
        ArrayList<String> canReadForms = new ArrayList<String>();
        DimensionValueSet dimValueSet = DimensionValueSetUtil.getDimensionValueSet(unitDim);
        DimensionCombination unitDimCombination = DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)dimValueSet, (String)formSchemeKey);
        for (String formKey : formKeys) {
            IAccessResult accessResult = batchAccess.getAccess(unitDimCombination, formKey);
            try {
                if (!accessResult.haveAccess()) continue;
                canReadForms.add(formKey);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return canReadForms;
    }

    private List<FormShareItem> getFormShareList(TaskDataContext context, List<FormDefine> dataForms, Map<String, List<String>> netFormMapSingleTables) {
        ArrayList<FormShareItem> list = new ArrayList<FormShareItem>();
        for (FormDefine form : dataForms) {
            List<String> singleFormCodes = null;
            singleFormCodes = netFormMapSingleTables != null ? netFormMapSingleTables.get(form.getKey()) : TaskFileDataOperateUtil.getSingleFormsByMapNetForm((TaskDataContext)context, (FormDefine)form);
            if (singleFormCodes == null || singleFormCodes.isEmpty()) continue;
            HashSet<String> singleFormCodeSet = new HashSet<String>(singleFormCodes);
            ArrayList<FormShareItem> findlist = new ArrayList<FormShareItem>();
            if (!list.isEmpty()) {
                block1: for (String singleFormCode : singleFormCodes) {
                    for (FormShareItem item : list) {
                        if (!item.getShareSingleTables().contains(singleFormCode)) continue;
                        findlist.add(item);
                        continue block1;
                    }
                }
            }
            if (findlist.isEmpty()) {
                FormShareItem item = new FormShareItem();
                item.setCode(OrderGenerator.newOrder());
                item.add(form, null, singleFormCodeSet);
                list.add(item);
                continue;
            }
            ArrayList<FormShareItem> oldList = new ArrayList<FormShareItem>();
            oldList.addAll(list);
            list.clear();
            HashMap<String, FormShareItem> deleteItems = new HashMap<String, FormShareItem>();
            FormShareItem destItem = new FormShareItem();
            destItem.setCode(OrderGenerator.newOrder());
            for (FormShareItem item : findlist) {
                destItem.merge(item);
                deleteItems.put(item.getCode(), item);
            }
            destItem.add(form, null, singleFormCodeSet);
            list.add(destItem);
            for (FormShareItem item : oldList) {
                if (deleteItems.containsKey(item.getCode())) continue;
                list.add(item);
            }
        }
        return list;
    }

    private void updateProgressAsyn(TaskDataContext importContext, AsyncTaskMonitor asyncTaskMonitor, double addProgress) {
        importContext.updateProgressAsyn(importContext, asyncTaskMonitor, addProgress);
    }

    private /* synthetic */ Integer lambda$ExportBathchDataByPeriods$0(TaskDataContext context, String mainTheadName, int threadId, FormShareManager queueManager, AsyncTaskMonitor asyncTaskMonitor, double addProgress, boolean isMeasureMode, String taskDataPath, Map mapNoUnit, List corpList, Map unitDic, JtableContext jtableContext, String taskFilePath) throws Exception {
        context.info(log, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355\u5bfc\u51fa\u7ebf\u7a0b " + mainTheadName + "_SUB" + threadId + ",\u5f00\u59cb");
        block0: while (!queueManager.isThreadQueueEmpty()) {
            List<FormDefine> threadForms = queueManager.getThreadFormsAsyn();
            for (FormDefine formDefine : threadForms) {
                String curFormKey = formDefine.getKey();
                boolean isFMDM = false;
                context.info(log, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355\u5bfc\u51fa\u7ebf\u7a0b " + mainTheadName + "_SUB" + threadId + ",\u8868\u5355\u6807\u8bc6\uff1a" + formDefine.getFormCode());
                this.updateProgressAsyn(context, asyncTaskMonitor, addProgress);
                if (!isMeasureMode) {
                    this.batchExportDataService.batchOperFormData(context, taskDataPath, curFormKey, mapNoUnit, corpList);
                } else {
                    context.info(log, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u91cf\u7eb2\u6a21\u5f0f\uff0c" + curFormKey);
                    this.oneOperFormData(context, taskDataPath, curFormKey, mapNoUnit, corpList, curFormKey, isFMDM, unitDic, jtableContext, asyncTaskMonitor);
                }
                this.exportCheckResultServce.exportCheckDataFromCache(context, taskDataPath, curFormKey);
                if (asyncTaskMonitor == null || !asyncTaskMonitor.isCancel()) continue;
                context.info(log, "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88");
                DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(LOG_TITLE, OperLevel.USER_OPER);
                logHelper.info(context.getTaskKey(), null, "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88", "JIO\u6570\u636e\u5bfc\u51fa\u5df2\u53d6\u6d88");
                asyncTaskMonitor.canceled(null, null);
                PathUtil.deleteDir((String)taskFilePath);
                continue block0;
            }
        }
        context.info(log, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355\u5bfc\u51fa\u7ebf\u7a0b" + mainTheadName + "_SUB" + threadId + ",\u5b8c\u6210");
        return 1;
    }
}

