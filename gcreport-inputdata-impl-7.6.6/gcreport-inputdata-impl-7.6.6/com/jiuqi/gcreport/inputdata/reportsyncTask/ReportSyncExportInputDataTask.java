/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.reportsync.dto.ReportDataSyncServerInfoBase
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  com.jiuqi.common.reportsync.task.IReportSyncExportTask
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nr.transmission.data.internal.format.FormulaCheckFormat
 *  nr.single.map.data.PathUtil
 */
package com.jiuqi.gcreport.inputdata.reportsyncTask;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.reportsync.dto.ReportDataSyncServerInfoBase;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.common.reportsync.task.IReportSyncExportTask;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataSrcTypeEnum;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.transmission.data.internal.format.FormulaCheckFormat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nr.single.map.data.PathUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncExportInputDataTask
implements IReportSyncExportTask {
    private static final Logger logger = LoggerFactory.getLogger(ReportSyncExportInputDataTask.class);
    private final String INPUT_FOLDER_NAME = "GC-data-report";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportSyncExportInputDataTask.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private InputDataDao inputDataDao;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IFormulaCheckDesService iFormulaCheckDesService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired(required=false)
    private MultistageUnitReplace multistageUnitReplaceImpl;
    private final FormulaCheckFormat formulaCheckFormat = new FormulaCheckFormat();

    public boolean match(ReportDataSyncParams dataSyncParam) {
        ReportDataParam reportData = dataSyncParam.getReportData();
        if (null == reportData || CollectionUtils.isEmpty((Collection)reportData.getUnitCodes())) {
            return false;
        }
        return reportData.getFileFormat() == null || !reportData.getFileFormat().equals("jio");
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        ReportDataParam reportData = dataSyncParam.getReportData();
        ReportDataSyncServerInfoBase syncServerInfoBase = reportSyncExportTaskContext.getReportDataSyncServerInfoBase();
        if (CollectionUtils.isEmpty((Collection)(reportData = this.dealFormCode(reportData)).getFormKeys())) {
            return null;
        }
        String inputFolderPath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)"GC-data-report");
        try {
            this.writeInputDataScheme(inputFolderPath, reportData);
            CommonReportUtil.writeFileJson((Object)reportData, (String)(inputFolderPath + "/param.txt"));
            String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(reportData.getTaskId(), reportData.getPeriodStr());
            List currServerSubjects = this.subjectService.listAllSubjectsBySystemId(systemId);
            Map<String, String> currCode2ParentCode = currServerSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, ConsolidatedSubjectEO::getParentCode, (f1, f2) -> f1));
            currCode2ParentCode.put("systemId", systemId);
            try {
                CommonReportUtil.writeFileJson(currCode2ParentCode, (String)(inputFolderPath + "/subjectCode.txt"));
            }
            catch (Exception e2) {
                logger.error("\u79d1\u76ee\u5bfc\u51fa\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e2.getMessage(), e2);
            }
            YearPeriodObject yp = new YearPeriodObject(null, reportData.getPeriodStr());
            HashSet<String> relatedUnitCode = new HashSet<String>();
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)reportData.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            for (String unitCode : reportData.getUnitCodes()) {
                boolean isUnionOrDifferencd;
                GcOrgCacheVO orgCacheVO = tool.getOrgByCode(unitCode);
                boolean bl = isUnionOrDifferencd = orgCacheVO.getOrgKind() == GcOrgKindEnum.UNIONORG || orgCacheVO.getOrgKind() == GcOrgKindEnum.DIFFERENCE;
                if (isUnionOrDifferencd) continue;
                DimensionValueSet ds = this.getDimensionValueSet(reportData, unitCode, tool);
                String unitFilePath = CommonReportUtil.createNewPath((String)inputFolderPath, (String)unitCode);
                for (String formKey : reportData.getFormKeys()) {
                    this.writeFormData(reportData, unitCode, ds, unitFilePath, formKey, relatedUnitCode);
                }
            }
            this.exportUnitMsg(relatedUnitCode, tool, inputFolderPath);
            this.exportErrorExplain(inputFolderPath, reportData);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private void exportUnitMsg(Set<String> relatedUnitCode, GcOrgCenterService tool, String inputFolderPath) {
        HashMap<String, String> currCode2ParentCode = new HashMap<String, String>();
        for (String orgCode : relatedUnitCode) {
            this.addUnitCode2ParentCode(orgCode, currCode2ParentCode, tool);
        }
        try {
            CommonReportUtil.writeFileJson(currCode2ParentCode, (String)(inputFolderPath + "/unitCode.txt"));
        }
        catch (Exception e) {
            logger.error("\u5185\u90e8\u8868\u5bfc\u51fa\u672c\u5bf9\u65b9\u5355\u4f4d\u4ee3\u7801\u4fe1\u606f\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private void addUnitCode2ParentCode(String unitCode, Map<String, String> currCode2ParentCode, GcOrgCenterService tool) {
        if (currCode2ParentCode.containsKey(unitCode) || StringUtils.isEmpty((String)unitCode) || unitCode.equals("-")) {
            return;
        }
        String parentCode = tool.getOrgByCode(unitCode).getParentId();
        currCode2ParentCode.put(unitCode, parentCode);
        this.addUnitCode2ParentCode(parentCode, currCode2ParentCode, tool);
    }

    private void exportErrorExplain(String inputFolderPath, ReportDataParam reportData) throws IOException {
        logger.info("\u6570\u636e\u540c\u6b65\u5f00\u59cb\u5bfc\u51fa\u5185\u90e8\u8868\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\uff01");
        String formSchemeKey = reportData.getSchemeId();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        List formulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(reportData.getTaskId());
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            FormulaCheckDesQueryInfo queryInfo = this.buildQueryParam(reportData, formulaSchemeDefine.getKey());
            String unitCodeDimension = String.join((CharSequence)";", reportData.getUnitCodes());
            String adjustCode = StringUtils.isEmpty((String)reportData.getAdjustCode()) ? "0" : reportData.getAdjustCode();
            Map dimensionSet = DimensionUtils.buildDimensionMap((String)formScheme.getTaskKey(), (String)"", (String)reportData.getPeriodStr(), (String)"", (String)unitCodeDimension, (String)adjustCode);
            queryInfo.setDimensionSet(dimensionSet);
            List formulaCheckDesInfos = this.iFormulaCheckDesService.queryFormulaCheckDes(queryInfo);
            if (this.multistageUnitReplaceImpl != null) {
                for (FormulaCheckDesInfo formulaCheckDesInfo : formulaCheckDesInfos) {
                    DimensionValue dimensionValue = (DimensionValue)formulaCheckDesInfo.getDimensionSet().get("MD_ORG");
                    String unit = dimensionValue.getValue();
                    String superiorCode = this.multistageUnitReplaceImpl.getSuperiorCode(unit);
                    dimensionValue.setValue(superiorCode);
                }
            }
            String desInfoJson = this.formulaCheckFormat.serialize(formulaCheckDesInfos);
            String filePath = inputFolderPath + "/errrorExplain/" + formulaSchemeDefine.getKey() + ".json";
            File file = new File(filePath);
            if (!file.exists()) {
                FileUtils.forceMkdirParent(file);
            }
            try (FileOutputStream out = new FileOutputStream(filePath);){
                out.write(desInfoJson.getBytes());
                out.flush();
            }
            logger.info(String.format("\u4efb\u52a1\uff1a%s\uff0c\u62a5\u8868\u65b9\u6848\uff1a%s\uff0c\u516c\u5f0f\u65b9\u6848\uff1a%s  \u5185\u90e8\u8868\u5171\u5bfc\u51fa%d\u6761\u51fa\u9519\u8bf4\u660e\u3002", taskDefine.getTitle(), formScheme.getTitle(), formulaSchemeDefine.getTitle(), formulaCheckDesInfos.size()));
        }
        logger.info("\u6570\u636e\u540c\u6b65\u5185\u90e8\u8868\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u7ed3\u675f\uff01");
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

    private void writeInputDataScheme(String inputFolderPath, ReportDataParam reportData) throws IOException {
        InputDataSchemeVO inputDataScheme = this.inputDataNameProvider.getInputDataSchemeByTaskKey(reportData.getTaskId());
        CommonReportUtil.writeFileJson((Object)inputDataScheme, (String)(inputFolderPath + "/inputDataSchme.txt"));
    }

    private void writeFormData(ReportDataParam reportData, String unitCode, DimensionValueSet ds, String unitFilePath, String formKey, Set<String> relatedUnitCode) throws Exception {
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        if (null == formDefine) {
            return;
        }
        List regionDefineList = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
        for (DataRegionDefine regionDefine : regionDefineList) {
            String fileName = formDefine.getFormCode() + "_F" + regionDefine.getRegionTop();
            RegionData regionData = new RegionData();
            regionData.initialize(regionDefine);
            TableContext tbContext = new TableContext(reportData.getTaskId(), reportData.getSchemeId(), formDefine.getKey(), ds, OptTypes.FORM, ".txt");
            tbContext.setSplit(",");
            if (this.isInputDataTable(regionDefine.getKey())) {
                this.writeInputData(reportData, unitCode, regionDefine.getKey(), unitFilePath + "/" + fileName, relatedUnitCode);
                continue;
            }
            RegionDataSet region = new RegionDataSet(tbContext, regionData);
            ReportSyncExportInputDataTask.writeNrRegionData((IRegionDataSet)region, unitFilePath + "/" + fileName);
        }
    }

    private DimensionValueSet getDimensionValueSet(ReportDataParam reportData, String unitCode, GcOrgCenterService tool) {
        String adjustCode = StringUtils.isEmpty((String)reportData.getAdjustCode()) ? "0" : reportData.getAdjustCode();
        List<Object> currencyIds = new ArrayList();
        if (reportData.getCurrency() == null || "all".equals(reportData.getCurrency())) {
            GcOrgCacheVO orgCacheVO = tool.getOrgByCode(unitCode);
            currencyIds = Arrays.asList(orgCacheVO.getFields().get("CURRENCYIDS").toString().split(";"));
        } else {
            currencyIds = Arrays.asList(reportData.getCurrency().split(";"));
        }
        return DimensionUtils.generateDimSet((Object)unitCode, (Object)reportData.getPeriodStr(), currencyIds, (Object)reportData.getOrgType(), (String)adjustCode, (String)reportData.getTaskId());
    }

    private void writeInputData(ReportDataParam reportData, String unitCode, String dataRegionKey, String filePath, Set<String> relatedUnitCode) throws Exception {
        List<InputDataEO> inputDataList = this.listInputDatas(reportData, unitCode, dataRegionKey);
        if (CollectionUtils.isEmpty(inputDataList)) {
            return;
        }
        for (InputDataEO inputDataEO : inputDataList) {
            String oppUnitId;
            String orgCode = inputDataEO.getOrgCode();
            if (orgCode != null) {
                relatedUnitCode.add(orgCode);
            }
            if ((oppUnitId = inputDataEO.getOppUnitId()) == null) continue;
            relatedUnitCode.add(oppUnitId);
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(reportData.getTaskId());
        CommonReportUtil.writeBase64((String)tableName, inputDataList, (String)filePath);
    }

    private static void writeNrRegionData(IRegionDataSet region, String filePath) throws Exception {
        List defines = region.getFieldDataList();
        if (CollectionUtils.isEmpty((Collection)defines)) {
            return;
        }
        if (!region.hasNext()) {
            return;
        }
        boolean hasContent = false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));){
            bw.write(ReportSyncExportInputDataTask.headStr(defines));
            do {
                ArrayList dataRows;
                if (CollectionUtils.isEmpty((Collection)(dataRows = (ArrayList)region.next()))) continue;
                StringBuffer formatResul = new StringBuffer();
                Arrays.asList(dataRows.toArray()).stream().forEach(x -> formatResul.append(x).append(","));
                String oneRowStr = formatResul.deleteCharAt(formatResul.length() - 1).toString();
                bw.newLine();
                bw.write(oneRowStr);
                hasContent = true;
            } while (region.hasNext());
            bw.flush();
        }
        if (!hasContent) {
            PathUtil.deleteFile((String)filePath);
        }
    }

    private static String headStr(List<ExportFieldDefine> defines) {
        StringBuffer oneRowStr = new StringBuffer(128);
        for (ExportFieldDefine define : defines) {
            oneRowStr.append(define.getCode()).append(",");
        }
        oneRowStr.setLength(oneRowStr.length() - 1);
        return oneRowStr.toString();
    }

    private ReportDataParam dealFormCode(ReportDataParam srcReportData) {
        ReportDataParam destReportData = srcReportData.clone();
        if (StringUtils.isEmpty((String)destReportData.getPeriodStr()) && null != destReportData.getPeriodOffset()) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(destReportData.getTaskId());
            PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)taskDefine.getPeriodType().type(), (int)destReportData.getPeriodOffset());
            destReportData.setPeriodStr(currentPeriod.toString());
        }
        Set<String> formKeyList = this.listInputDataFormKeySet(destReportData.getSchemeId());
        if (CollectionUtils.isEmpty((Collection)destReportData.getFormKeys())) {
            destReportData.setFormKeys(new ArrayList());
            destReportData.getFormKeys().addAll(formKeyList);
        } else {
            destReportData.getFormKeys().retainAll(formKeyList);
            srcReportData.getFormKeys().removeAll(formKeyList);
        }
        return destReportData;
    }

    private List<InputDataEO> listInputDatas(ReportDataParam reportData, String unitCode, String dataRegionKey) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(reportData.getTaskId());
        DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(dataRegionKey);
        List<String> ids = this.getInputDataIds(reportData, unitCode, dataRegionDefine);
        List<InputDataEO> inputDataEOs = this.inputDataDao.queryByIds(ids, tableName);
        for (InputDataEO inputDataEO : inputDataEOs) {
            inputDataEO.setSrcType(InputDataSrcTypeEnum.DATA_SYNC.getValue());
        }
        return inputDataEOs;
    }

    public String funcTitle() {
        return "\u5167\u90e8\u8868\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }

    public Set<String> listInputDataFormKeySet(String schemeId) {
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
        Set<String> formKeyList = formDefines.stream().filter(item -> this.hasInputDataTable(item.getKey())).map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        return formKeyList;
    }

    private boolean isInputDataTable(String dataRegionKey) {
        List defines = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(this.runTimeViewController.getFieldKeysInRegion(dataRegionKey).toArray(new String[0]));
        if (CollectionUtils.isEmpty((Collection)defines)) {
            return false;
        }
        return ((DataFieldDeployInfo)defines.get(0)).getTableName().contains("GC_INPUTDATA");
    }

    private boolean hasInputDataTable(String formKey) {
        List regionDefineList = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine regionDefine : regionDefineList) {
            if (!this.isInputDataTable(regionDefine.getKey())) continue;
            return true;
        }
        return false;
    }

    private List<String> getInputDataIds(ReportDataParam reportData, String unitCode, DataRegionDefine regionDefine) {
        IDataTable dataTable;
        FieldDefine bizKeyFieldDefine = this.getFieldDefine(reportData.getTaskId());
        YearPeriodObject yp = new YearPeriodObject(null, reportData.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)reportData.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet(reportData, unitCode, tool);
        ArrayList<String> ids = new ArrayList<String>();
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(reportData.getSchemeId());
        queryEnvironment.setRegionKey(regionDefine.getKey());
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setRowFilter(regionDefine.getFilterCondition());
        dataQuery.setMasterKeys(dimensionValueSet);
        dataQuery.addColumn(bizKeyFieldDefine);
        try {
            dataTable = dataQuery.executeQuery(context);
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u5185\u90e8\u8868\u6570\u636e\u53d1\u751f\u5f02\u5e38", e);
            throw new BusinessRuntimeException((Throwable)e);
        }
        int count = dataTable.getCount();
        for (int i = 0; i < count; ++i) {
            IDataRow dataRow = dataTable.getItem(i);
            String idStr = String.valueOf(dataRow.getRowKeys().getValue("RECORDKEY"));
            ids.add(idStr);
        }
        return ids;
    }

    private FieldDefine getFieldDefine(String taskId) {
        FieldDefine fieldDefine = null;
        try {
            String inputDataTableKey = this.inputDataNameProvider.getDataTableKeyByTaskId(taskId);
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("BIZKEYORDER", inputDataTableKey);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException((Throwable)e);
        }
        return fieldDefine;
    }
}

