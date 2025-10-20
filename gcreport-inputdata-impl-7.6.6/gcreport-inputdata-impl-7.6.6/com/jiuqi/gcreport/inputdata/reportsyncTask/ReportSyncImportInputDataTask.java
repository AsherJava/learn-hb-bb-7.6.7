/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.paramInfo.BatchClearInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.service.IBatchClearService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.service.impl.TxtFileImportServiceImpl
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.transmission.data.internal.format.FormulaCheckFormat
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.apache.commons.io.FileUtils
 */
package com.jiuqi.gcreport.inputdata.reportsyncTask;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchClearInfo;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.service.IBatchClearService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.service.impl.TxtFileImportServiceImpl;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.transmission.data.internal.format.FormulaCheckFormat;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncImportInputDataTask
implements IReportSyncImportTask {
    private static final Logger logger = LoggerFactory.getLogger(ReportSyncImportInputDataTask.class);
    private static final String INPUT_FOLDER_NAME = "GC-data-report";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportSyncImportInputDataTask.class);
    @Autowired
    private TxtFileImportServiceImpl txtFileImportService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private InputDataSchemeService inputDataSchemeService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IFormulaCheckDesService iFormulaCheckDesService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private IBatchClearService batchClearService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    private final FormulaCheckFormat formulaCheckFormat = new FormulaCheckFormat();

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        String systemId;
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        String filePath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)INPUT_FOLDER_NAME);
        File inputFolderFile = new File(filePath);
        if (!inputFolderFile.exists()) {
            return null;
        }
        String json = CommonReportUtil.readJsonFile((String)(filePath + "/param.txt"));
        if (CommonReportUtil.isEmptyJson((String)json)) {
            return null;
        }
        ArrayList<String> msgList = new ArrayList<String>();
        ReportDataParam reportData = (ReportDataParam)JsonUtils.readValue((String)json, ReportDataParam.class);
        String inputDataSchemeJson = CommonReportUtil.readJsonFile((String)(filePath + "/inputDataSchme.txt"));
        if (CommonReportUtil.isEmptyJson((String)inputDataSchemeJson)) {
            return null;
        }
        InputDataSchemeVO inputDataSchemeVO = (InputDataSchemeVO)JsonUtils.readValue((String)inputDataSchemeJson, InputDataSchemeVO.class);
        if (inputDataSchemeVO != null) {
            this.inputDataSchemeService.createInputDataScheme(inputDataSchemeVO);
        }
        if ((systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(reportData.getTaskId(), reportData.getPeriodStr())) == null) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(reportData.getTaskId());
            msgList.add("\u5f53\u524d\u4efb\u52a1[" + taskDefine.getTitle() + "],\u65f6\u671f[ " + reportData.getPeriodStr() + "]\u672a\u914d\u7f6e\u5408\u5e76\u4f53\u7cfb");
            return null;
        }
        YearPeriodObject yp = new YearPeriodObject(null, reportData.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)reportData.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String unitCodeJson = CommonReportUtil.readJsonFile((String)(filePath + "/unitCode.txt"));
        Map<String, String> unitCode2finalCode = this.getUnitChangeCodeMap(tool, unitCodeJson);
        String subjectCodeJson = CommonReportUtil.readJsonFile((String)(filePath + "/subjectCode.txt"));
        Map<String, String> subjectCode2finalCode = this.getSubjectChangeCodeMap(systemId, subjectCodeJson);
        File[] files = inputFolderFile.listFiles();
        DimensionValueSet ds = new DimensionValueSet();
        ds.setValue("DATATIME", (Object)reportData.getPeriodStr());
        ds.setValue("ADJUST", (Object)(reportData.getAdjustCode() == null || reportData.getAdjustCode().isEmpty() ? "0" : reportData.getAdjustCode()));
        TableContext tbContext = new TableContext(reportData.getTaskId(), reportData.getSchemeId(), null, ds, OptTypes.FORM, ".txt");
        tbContext.setSplit(",");
        Map<String, String> formCode2KeyMap = this.getFormCode2KeyMap(reportData);
        try {
            ArrayList<String> insertFiles = new ArrayList<String>();
            int totalNum = 0;
            for (File file : files) {
                if (!file.isDirectory() || file.getName().contains("errrorExplain") || file.getName().contains("_")) continue;
                String unitCode = file.getName();
                String finUnitCode = unitCode2finalCode.get(unitCode) == null ? unitCode : unitCode2finalCode.get(unitCode);
                ds.setValue("MD_ORG", (Object)finUnitCode);
                DimensionParamsVO queryParamsVO = this.getDimensionParamsVO(reportData, finUnitCode);
                ReadWriteAccessDesc writeable = UploadStateTool.getInstance().writeable(queryParamsVO);
                if (!writeable.getAble().booleanValue()) continue;
                GcOrgCacheVO orgCacheVO = tool.getOrgByCode(finUnitCode);
                boolean isUnionOrDiffUnit = orgCacheVO.getOrgKind() == GcOrgKindEnum.UNIONORG || orgCacheVO.getOrgKind() == GcOrgKindEnum.DIFFERENCE;
                for (File dataFile : file.listFiles()) {
                    String formKey = this.parseFormKey(dataFile.getName(), formCode2KeyMap);
                    try {
                        writeable = FormUploadStateTool.getInstance().writeable(queryParamsVO, formKey);
                        if (!writeable.getAble().booleanValue()) {
                            continue;
                        }
                    }
                    catch (Exception e) {
                        LOGGER.error(String.format("\u5355\u4f4d\u3010%1$s\u3011\u62a5\u8868\u3010%2$s\u3011\u5224\u65ad\u6743\u9650\u5931\u8d25:", formKey, queryParamsVO.getOrgId()), e);
                    }
                    String tableName = this.readTableName(dataFile, queryParamsVO.getSchemeId());
                    if (!StringUtils.isEmpty((String)tableName) && tableName.toUpperCase().contains("GC_INPUTDATA")) {
                        if (!isUnionOrDiffUnit) {
                            this.deleteInputData(reportData, finUnitCode, formKey);
                        }
                        insertFiles.add(dataFile.getPath() + "---" + tableName + "---" + finUnitCode + "---" + formKey);
                        continue;
                    }
                    this.txtFileImportService.dealFileData(dataFile, tbContext);
                }
            }
            for (String file : insertFiles) {
                String[] insertFile = file.split("---");
                List<Map<String, Object>> dataMapList = this.readBase64Db(insertFile[0], insertFile[1], insertFile[2], unitCode2finalCode, subjectCode2finalCode, systemId);
                totalNum += this.createDataRow(reportData, dataMapList, insertFile[2], insertFile[3]);
            }
            if (totalNum != 0) {
                msgList.add(String.format("\u5185\u90e8\u8868\u5bfc\u5165\u6210\u529f%1d\u6761", totalNum));
            }
            this.importExplainErros(filePath, reportData);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            msgList.add(String.format("\u5185\u90e8\u8868\u5bfc\u5165\u5931\u8d25:%1s", e.getMessage()));
        }
        return (List)CommonReportUtil.appendImportMsgIfEmpty(msgList);
    }

    private void importExplainErros(String filePath, ReportDataParam reportData) throws IOException {
        File errorExplainFile = new File(filePath + "/errrorExplain");
        if (errorExplainFile.exists()) {
            File[] errorExplainFiles;
            for (File explainFile : errorExplainFiles = errorExplainFile.listFiles()) {
                String formulaSchemeId = explainFile.getName().substring(0, 36);
                FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeId);
                if (formulaSchemeDefine == null) continue;
                FormulaCheckDesQueryInfo queryInfo = this.buildQueryParam(reportData, formulaSchemeDefine.getKey());
                String unitCodeDimension = String.join((CharSequence)";", reportData.getUnitCodes());
                String adjustCode = reportData.getAdjustCode() == null || reportData.getAdjustCode().isEmpty() ? "0" : reportData.getAdjustCode();
                Map dimensionSet = DimensionUtils.buildDimensionMap((String)reportData.getTaskId(), (String)"", (String)reportData.getPeriodStr(), (String)"", (String)unitCodeDimension, (String)adjustCode);
                queryInfo.setDimensionSet(dimensionSet);
                String checkInfos = FileUtils.readFileToString((File)explainFile, (Charset)StandardCharsets.UTF_8);
                if (StringUtils.isEmpty((String)checkInfos)) continue;
                List allCheckDesInfoForFormulaScheme = this.formulaCheckFormat.deserialize(checkInfos, new HashMap(), WorkFlowType.ENTITY, "MD_ORG", false, reportData.getSchemeId());
                logger.info(String.format("\u6570\u636e\u540c\u6b65\u516c\u5f0f\u65b9\u6848%s[%s]\u89e3\u6790\u51fa\u6765\u5165%d\u6761\u51fa\u9519\u8bf4\u660e\u3002", formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey(), allCheckDesInfoForFormulaScheme.size()));
                if (CollectionUtils.isEmpty((Collection)allCheckDesInfoForFormulaScheme)) continue;
                FormulaCheckDesBatchSaveInfo saveInfo = new FormulaCheckDesBatchSaveInfo();
                saveInfo.setDesInfos(allCheckDesInfoForFormulaScheme);
                saveInfo.setQueryInfo(queryInfo);
                int successNum = this.iFormulaCheckDesService.batchSaveFormulaCheckDes(saveInfo);
                logger.info(String.format("\u591a\u7ea7\u90e8\u7f72\u516c\u5f0f\u65b9\u6848%s[%s]\u5171\u5bfc\u5165%d\u6761\u51fa\u9519\u8bf4\u660e\u3002", formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey(), successNum));
            }
        }
    }

    private FormulaCheckDesQueryInfo buildQueryParam(ReportDataParam reportDataParam, String formulaSchemeKey) {
        FormulaCheckDesQueryInfo queryInfo = new FormulaCheckDesQueryInfo();
        queryInfo.setFormSchemeKey(reportDataParam.getSchemeId());
        ArrayList forms = new ArrayList(reportDataParam.getFormKeys());
        if (forms != null) {
            String formKeys = String.join((CharSequence)";", forms);
            queryInfo.setFormKey(formKeys);
        }
        queryInfo.setFormulaSchemeKey(formulaSchemeKey);
        return queryInfo;
    }

    private Map<String, String> getFormCode2KeyMap(ReportDataParam reportData) {
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(reportData.getSchemeId());
        Map<String, String> formCode2KeyMap = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, IBaseMetaItem::getKey));
        return formCode2KeyMap;
    }

    private DimensionParamsVO getDimensionParamsVO(ReportDataParam params, String orgCode) {
        DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
        dimensionParamsVO.setOrgId(orgCode);
        dimensionParamsVO.setPeriodStr(params.getPeriodStr());
        dimensionParamsVO.setOrgType(params.getOrgType());
        String adjustCode = params.getAdjustCode() == null || params.getAdjustCode().isEmpty() ? "0" : params.getAdjustCode();
        dimensionParamsVO.setSelectAdjustCode(adjustCode);
        dimensionParamsVO.setSchemeId(params.getSchemeId());
        dimensionParamsVO.setTaskId(params.getTaskId());
        dimensionParamsVO.setCurrency("CNY");
        dimensionParamsVO.setCurrencyId("CNY");
        return dimensionParamsVO;
    }

    private String parseFormKey(String dataFileName, Map<String, String> formCode2KeyMap) {
        String formCode = dataFileName.indexOf("_F") >= 0 ? dataFileName.substring(0, dataFileName.indexOf("_F")) : dataFileName;
        return formCode2KeyMap.get(formCode);
    }

    private void deleteInputData(ReportDataParam reportData, String unitCode, String formKey) {
        List<FieldDefine> fieldDefines = this.listFieldDefines(reportData.getTaskId());
        Set<String> inputDataFieldIds = fieldDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        List<DataRegionDefine> regionDefineList = this.getInputDataDataRegion(formKey, inputDataFieldIds);
        if (CollectionUtils.isEmpty(regionDefineList)) {
            return;
        }
        String adjustCode = reportData.getAdjustCode() == null || reportData.getAdjustCode().isEmpty() ? "0" : reportData.getAdjustCode();
        YearPeriodObject yp = new YearPeriodObject(null, reportData.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)reportData.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List<Object> currencyIds = new ArrayList();
        GcOrgCacheVO orgCacheVO = tool.getOrgByCode(unitCode);
        currencyIds = reportData.getCurrency() == null || "all".equals(reportData.getCurrency()) ? Arrays.asList(orgCacheVO.getFields().get("CURRENCYIDS").toString()) : Arrays.asList(reportData.getCurrency().split(";"));
        String orgType = orgCacheVO.getOrgTypeId();
        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)unitCode, (Object)reportData.getPeriodStr(), currencyIds, (Object)orgType, (String)adjustCode, (String)reportData.getTaskId());
        Map dimension = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        BatchClearInfo batchClearInfo = new BatchClearInfo();
        JtableContext context = new JtableContext();
        context.setDimensionSet(dimension);
        context.setFormKey(formKey);
        context.setTaskKey(reportData.getTaskId());
        context.setFormSchemeKey(reportData.getSchemeId());
        batchClearInfo.setContext(context);
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(UUIDUtils.newUUIDStr(), this.cacheObjectResourceRemote);
        try {
            this.batchClearService.batchClearForm(batchClearInfo, (AsyncTaskMonitor)asyncTaskMonitor);
        }
        catch (Exception e) {
            LOGGER.error("\u5185\u90e8\u8868\u6570\u636e\u63d0\u4ea4\u53d1\u751f\u5f02\u5e38", e);
            throw new RuntimeException(e);
        }
    }

    private Set<String> allOrgCodeSet(ReportDataParam reportData) {
        if (StringUtils.isEmpty((String)reportData.getRootUnitCode())) {
            return Collections.emptySet();
        }
        YearPeriodObject yp = new YearPeriodObject(null, reportData.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List orgCacheS = instance.listAllOrgByParentIdContainsSelf(reportData.getRootUnitCode());
        Set<String> orgCodeSet = orgCacheS.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet());
        return orgCodeSet;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String readTableName(File txtFile, String formSchemeKey) {
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile));){
            TableModelDefine tableDefine;
            String oneRow = reader.readLine();
            Object[] columnCodes = oneRow.split(CommonReportUtil.SPLIT_SIGN);
            if (CollectionUtils.isEmpty((Object[])columnCodes)) {
                String string = null;
                return string;
            }
            if (((String)columnCodes[0]).contains(".")) {
                String string = ((String)columnCodes[0]).substring(0, ((String)columnCodes[0]).indexOf(46));
                return string;
            }
            String formCode = txtFile.getName().split("_F")[0];
            Set tableDefines = NrTool.getTableDefineByFormCode((String)formSchemeKey, (String)formCode, (DataRegionKind)DataRegionKind.DATA_REGION_ROW_LIST);
            Iterator iterator = tableDefines.iterator();
            do {
                if (!iterator.hasNext()) return null;
            } while (!(tableDefine = (TableModelDefine)iterator.next()).getName().toUpperCase().contains("GC_INPUTDATA"));
            String string = tableDefine.getName();
            return string;
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public String funcTitle() {
        return "\u5185\u90e8\u8868\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }

    private List<FieldDefine> listFieldDefines(String taskId) {
        ArrayList<FieldDefine> fieldDefines = new ArrayList();
        try {
            String inputDataTableKey = this.inputDataNameProvider.getDataTableKeyByTaskId(taskId);
            fieldDefines = this.dataDefinitionRuntimeController.getAllFieldsInTable(inputDataTableKey);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException((Throwable)e);
        }
        return fieldDefines;
    }

    private List<DataRegionDefine> getInputDataDataRegion(String formId, Set<String> inputDataFieldIds) {
        List allRegionsInForms = this.runTimeViewController.getAllRegionsInForm(formId);
        List<DataRegionDefine> currDataRegionDefines = allRegionsInForms.stream().filter(dataRegionDefine -> dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && inputDataFieldIds.containsAll(this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()))).collect(Collectors.toList());
        return currDataRegionDefines;
    }

    private List<Map<String, Object>> readBase64Db(String fileName, String tableName, String unitCode, Map<String, String> unitCode2finalCode, Map<String, String> subjectCode2finalCode, String systemId) {
        List defines = CommonReportUtil.getFieldDefines((String)tableName);
        Map<String, ColumnModelType> columnCode2FieldType = defines.stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getColumnType, (e1, e2) -> e1));
        File txtFile = new File(fileName);
        if (!txtFile.exists()) {
            return null;
        }
        Base64.Decoder decoder = Base64.getDecoder();
        ArrayList<Map<String, Object>> dataMapList = new ArrayList<Map<String, Object>>();
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile));){
            String oneRow = reader.readLine();
            String[] columnCodes = oneRow.split(CommonReportUtil.SPLIT_SIGN);
            while ((oneRow = reader.readLine()) != null) {
                oneRow = new String(decoder.decode(oneRow));
                HashMap<String, Object> dataMap = new HashMap<String, Object>();
                String[] rowDatas = oneRow.split(CommonReportUtil.SPLIT_SIGN);
                for (int i = 0; i < columnCodes.length && i < rowDatas.length; ++i) {
                    String subjectCode;
                    String srcSubjectCode;
                    ColumnModelType fieldType = columnCode2FieldType.get(columnCodes[i]);
                    if (columnCodes[i].equals("REPORTSYSTEMID")) {
                        dataMap.put(columnCodes[i], systemId);
                        continue;
                    }
                    if (columnCodes[i].equals("MDCODE") || columnCodes[i].equals("ORGCODE")) {
                        dataMap.put(columnCodes[i], unitCode);
                        continue;
                    }
                    if (columnCodes[i].equals("OPPUNITID")) {
                        String srcOppUnitCode = rowDatas[i];
                        String oppUnitCode = unitCode2finalCode.get(srcOppUnitCode) == null ? srcOppUnitCode : unitCode2finalCode.get(srcOppUnitCode);
                        dataMap.put(columnCodes[i], oppUnitCode);
                        continue;
                    }
                    if (columnCodes[i].equals("SUBJECTOBJ")) {
                        if (rowDatas[i].indexOf("|") == -1) continue;
                        srcSubjectCode = rowDatas[i].toString().substring(0, rowDatas[i].toString().indexOf("|"));
                        subjectCode = subjectCode2finalCode.get(srcSubjectCode) == null ? srcSubjectCode : subjectCode2finalCode.get(srcSubjectCode);
                        String subjectObject = subjectCode + "||" + systemId;
                        dataMap.put(columnCodes[i], subjectObject);
                        continue;
                    }
                    if (columnCodes[i].equals("SUBJECTCODE")) {
                        srcSubjectCode = rowDatas[i];
                        subjectCode = subjectCode2finalCode.get(srcSubjectCode) == null ? srcSubjectCode : subjectCode2finalCode.get(srcSubjectCode);
                        dataMap.put(columnCodes[i], subjectCode);
                        continue;
                    }
                    dataMap.put(columnCodes[i], CommonReportUtil.dataTransferOri((ColumnModelType)fieldType, (String)rowDatas[i]));
                }
                String offsetState = dataMap.get("OFFSETSTATE").toString();
                if (offsetState.equals("0")) {
                    dataMap.put("UNIONRULEID", null);
                    dataMap.put("CECKGROUPIDH", null);
                    dataMap.put("CHECKSTATE", null);
                    dataMap.put("CHECKAMT", null);
                    dataMap.put("UNCHECKAMT", null);
                    dataMap.put("CHECKTYPE", null);
                    dataMap.put("CHECKTIME", null);
                    dataMap.put("CHECKUSER", null);
                }
                if (dataMap.get("MDCODE").toString().equals(dataMap.get("OPPUNITID").toString())) continue;
                dataMapList.add(dataMap);
            }
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return dataMapList;
    }

    private int createDataRow(ReportDataParam reportData, List<Map<String, Object>> dataMapList, String unitCode, String formKey) {
        int rowCount = 0;
        List<FieldDefine> fieldDefines = this.listFieldDefines(reportData.getTaskId());
        Set<String> inputDataFieldIds = fieldDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        List<DataRegionDefine> regionDefineList = this.getInputDataDataRegion(formKey, inputDataFieldIds);
        if (CollectionUtils.isEmpty(regionDefineList)) {
            return 0;
        }
        String adjustCode = reportData.getAdjustCode() == null || reportData.getAdjustCode().isEmpty() ? "0" : reportData.getAdjustCode();
        YearPeriodObject yp = new YearPeriodObject(null, reportData.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)reportData.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List<Object> currencyIds = new ArrayList();
        if (reportData.getCurrency() == null || "all".equals(reportData.getCurrency())) {
            GcOrgCacheVO orgCacheVO = tool.getOrgByCode(unitCode);
            currencyIds = Arrays.asList(orgCacheVO.getFields().get("CURRENCYIDS").toString().split(";"));
        } else {
            currencyIds = Arrays.asList(reportData.getCurrency().split(";"));
        }
        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)unitCode, (Object)reportData.getPeriodStr(), currencyIds, (Object)reportData.getOrgType(), (String)adjustCode, (String)reportData.getTaskId());
        for (Map<String, Object> dataMap : dataMapList) {
            for (DataRegionDefine regionDefine : regionDefineList) {
                this.addInputDataItemByRegionDefine(reportData.getSchemeId(), regionDefine, dimensionValueSet, fieldDefines, dataMap);
            }
            ++rowCount;
        }
        return rowCount;
    }

    private void addInputDataItemByRegionDefine(String formSchemeKey, DataRegionDefine dataRegionDefine, DimensionValueSet dimensionValueSet, List<FieldDefine> fieldDefines, Map<String, Object> dataMap) {
        IDataTable dataTable;
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(formSchemeKey);
        queryEnvironment.setRegionKey(dataRegionDefine.getKey());
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setRowFilter(dataRegionDefine.getFilterCondition());
        dataQuery.setMasterKeys(dimensionValueSet);
        for (FieldDefine fieldDefine : fieldDefines) {
            dataQuery.addColumn(fieldDefine);
        }
        try {
            dataTable = dataQuery.executeQuery(context);
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u5185\u90e8\u8868\u6570\u636e\u53d1\u751f\u5f02\u5e38", e);
            throw new BusinessRuntimeException((Throwable)e);
        }
        try {
            IDataRow iDataRow = dataTable.appendRow(dimensionValueSet);
            for (FieldDefine fieldDefine : fieldDefines) {
                iDataRow.setValue(fieldDefine, dataMap.get(fieldDefine.getCode()));
            }
            IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(runTimeViewController, dataDefinitionRuntimeController, entityViewController, formSchemeKey);
            context.setEnv((IFmlExecEnvironment)environment);
            InputDataChangeMonitorEnvVo monitorEnvVo = new InputDataChangeMonitorEnvVo(false, false);
            environment.getVariableManager().add(new Variable("INPUTDATA_CHANGEMONITOR_ENV_VO", "\u6570\u636e\u4e0a\u4f20\u8bbe\u7f6e\u5185\u90e8\u5f55\u5165\u8868\u6570\u636e\u53d8\u5316\u73af\u5883", 0, (Object)monitorEnvVo));
            dataTable.commitChanges(true);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getUnitChangeCodeMap(GcOrgCenterService tool, String unitCodeJson) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (CommonReportUtil.isEmptyJson((String)unitCodeJson)) {
            return resultMap;
        }
        Map unitCode2ParentMap = (Map)JsonUtils.readValue((String)unitCodeJson, HashMap.class);
        for (String unitCode : unitCode2ParentMap.keySet()) {
            resultMap.put(unitCode, this.getExistsUnitCode(unitCode, unitCode2ParentMap, tool));
        }
        return resultMap;
    }

    private String getExistsUnitCode(String unitCode, Map<String, String> unitCode2ParentMap, GcOrgCenterService tool) {
        GcOrgCacheVO orgCacheVO = tool.getOrgByCode(unitCode);
        if (orgCacheVO != null) {
            return unitCode;
        }
        String parentCode = unitCode2ParentMap.get(unitCode);
        if (parentCode == null || parentCode.equals("-")) {
            return null;
        }
        return this.getExistsUnitCode(parentCode, unitCode2ParentMap, tool);
    }

    private Map<String, String> getSubjectChangeCodeMap(String systemId, String subjectCodeJson) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (CommonReportUtil.isEmptyJson((String)subjectCodeJson)) {
            return resultMap;
        }
        Map subjectCode2ParentMap = (Map)JsonUtils.readValue((String)subjectCodeJson, HashMap.class);
        List currServerSubjects = this.subjectService.listAllSubjectsBySystemId(systemId);
        List<String> currAllSubjectCode = currServerSubjects.stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toList());
        for (String subjectCode : subjectCode2ParentMap.keySet()) {
            resultMap.put(subjectCode, this.getExistsSubjectCode(subjectCode, subjectCode2ParentMap, currAllSubjectCode));
        }
        return resultMap;
    }

    private String getExistsSubjectCode(String subjectCode, Map<String, String> code2Parentid, List<String> currAllSubjectCode) {
        if (currAllSubjectCode.contains(subjectCode)) {
            return subjectCode;
        }
        String parentCode = code2Parentid.get(subjectCode);
        if (parentCode == null || parentCode.equals("-")) {
            return null;
        }
        return this.getExistsSubjectCode(parentCode, code2Parentid, currAllSubjectCode);
    }
}

