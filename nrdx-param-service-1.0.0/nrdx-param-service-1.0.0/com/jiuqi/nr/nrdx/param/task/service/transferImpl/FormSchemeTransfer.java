/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 *  com.jiuqi.nr.definition.facade.report.TransformReportDefine
 *  com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.mapping2.service.MappingTransferService
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisSchemeDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO
 *  com.jiuqi.nr.tds.Costs
 */
package com.jiuqi.nr.nrdx.param.task.service.transferImpl;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.mapping2.service.MappingTransferService;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.dto.formScheme.NrdxFormSchemeDTO;
import com.jiuqi.nr.nrdx.param.task.dto.formScheme.NrdxFormulaVariableDTO;
import com.jiuqi.nr.nrdx.param.task.dto.formScheme.NrdxTaskLinkDTO;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.nrdx.param.task.utils.UtilsService;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO;
import com.jiuqi.nr.tds.Costs;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FormSchemeTransfer
extends AbstractParamTransfer {
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeTransfer.class);
    public static final List<String> FORM_SCHEME_CSV_HEADER = Arrays.asList("key", "formSchemeCode", "title", "taskKey", "periodType", "order", "version", "ownerLevelAndId", "updateTime", "description", "taskPrefix", "filePrefix", "periodOffset", "measureUnit", "fillInAutomaticallyDue", "dw", "datetime", "dims", "filterExpression", "transformReportDefine", "periodLinks", "analysisSchemeDTO", "desParamLanguageDTO");
    public static final List<String> TASK_LINK_CSV_HEADER = Arrays.asList("key", "title", "order", "version", "ownerLevelAndId", "updateTime", "description", "linkAlias", "currentFormSchemeKey", "relatedTaskKey", "relatedFormSchemeKey", "relatedTaskCode", "periodOffset", "currentTaskFormula", "relatedTaskFormula", "matching", "configuration", "isHidden", "specified", "theoffset", "beginTime", "endTime", "matchingType", "expressionType", "relatedDims", "orgMappingRule");
    public static final List<String> FORMULA_VARIABLE_CSV_HEADER = Arrays.asList("key", "code", "title", "type", "formSchemeKey", "valueType", "order", "updateTime", "version", "ownerLevelAndId", "length", "initType", "initValue");
    public static final String FORM_SCHEME_CSV = "FORM_SCHEME.CSV";
    public static final String TASK_LINK_CSV = "TASK_LINK.CSV";
    public static final String FORMUILA_VARIABLE_CSV = "FORMUILA_VARIABLE.CSV";
    private final ObjectMapper objectMapper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    UtilsService utilsService;
    @Autowired
    private MappingTransferService mappingTransferService;

    public FormSchemeTransfer() {
        logger.info("FormSchemeTransfer\u521d\u59cb\u5316objectMapper");
        this.objectMapper = new ObjectMapper();
        DefinitionModelTransfer.moduleRegister((ObjectMapper)this.objectMapper);
    }

    @Override
    public List<ITransferModel> depModel(String s) {
        ArrayList<ITransferModel> result = new ArrayList<ITransferModel>();
        AbstractParamTransfer abstractParamTransfer = this.utilsService.getAbstractParamTransfer(NrdxParamNodeType.TASK.name());
        result.add(abstractParamTransfer);
        return result;
    }

    @Override
    public String code() {
        return NrdxParamNodeType.FORMSCHEME.getCode();
    }

    @Override
    public String version() {
        return null;
    }

    public void exportModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        String path = context.getPath();
        Costs.createPathIfNotExists((Path)new File(path).toPath());
        List<String> formSchemeKeys = param.getParamKeys();
        NrdxFormSchemeDTO nrdxFormSchemeDTO = new NrdxFormSchemeDTO();
        this.exportFormSchemeCSV(formSchemeKeys, path);
        this.exportTaskLinkCSV(formSchemeKeys, path);
        this.exportFormulaVariCSV(formSchemeKeys, path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportTaskLinkCSV(List<String> formSchemeKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + Costs.FILE_SEPARATOR + TASK_LINK_CSV);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[TASK_LINK_CSV_HEADER.size()];
            for (int i = 0; i < TASK_LINK_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = TASK_LINK_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String formSchemeKey : formSchemeKeys) {
                List links;
                List taskLinkDefineList;
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                if (formScheme == null || CollectionUtils.isEmpty(taskLinkDefineList = this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey)) || !CollectionUtils.isEmpty(links = taskLinkDefineList.stream().map(NrdxTaskLinkDTO::valueOf).collect(Collectors.toList()))) continue;
                for (NrdxTaskLinkDTO link : links) {
                    this.exportTaskLinkDTO(csvWriter, link);
                }
            }
        }
        catch (Exception exception) {
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }

    private void exportTaskLinkDTO(CsvWriter csvWriter, NrdxTaskLinkDTO nrdxTaskLinkDTO) throws Exception {
        String[] dataArray = new String[TASK_LINK_CSV_HEADER.size()];
        block56: for (int i = 0; i < TASK_LINK_CSV_HEADER.size(); ++i) {
            switch (TASK_LINK_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxTaskLinkDTO.getKey();
                    continue block56;
                }
                case "title": {
                    dataArray[i] = nrdxTaskLinkDTO.getTitle();
                    continue block56;
                }
                case "order": {
                    dataArray[i] = nrdxTaskLinkDTO.getOrder();
                    continue block56;
                }
                case "version": {
                    dataArray[i] = nrdxTaskLinkDTO.getVersion();
                    continue block56;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxTaskLinkDTO.getOwnerLevelAndId();
                    continue block56;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxTaskLinkDTO.getUpdateTime());
                    continue block56;
                }
                case "description": {
                    dataArray[i] = nrdxTaskLinkDTO.getDescription();
                    continue block56;
                }
                case "linkAlias": {
                    dataArray[i] = nrdxTaskLinkDTO.getLinkAlias();
                    continue block56;
                }
                case "currentFormSchemeKey": {
                    dataArray[i] = nrdxTaskLinkDTO.getCurrentFormSchemeKey();
                    continue block56;
                }
                case "relatedTaskKey": {
                    dataArray[i] = nrdxTaskLinkDTO.getRelatedTaskKey();
                    continue block56;
                }
                case "relatedFormSchemeKey": {
                    dataArray[i] = nrdxTaskLinkDTO.getRelatedFormSchemeKey();
                    continue block56;
                }
                case "relatedTaskCode": {
                    dataArray[i] = nrdxTaskLinkDTO.getRelatedTaskCode();
                    continue block56;
                }
                case "periodOffset": {
                    dataArray[i] = nrdxTaskLinkDTO.getPeriodOffset();
                    continue block56;
                }
                case "currentTaskFormula": {
                    dataArray[i] = nrdxTaskLinkDTO.getCurrentTaskFormula();
                    continue block56;
                }
                case "relatedTaskFormula": {
                    dataArray[i] = nrdxTaskLinkDTO.getRelatedTaskFormula();
                    continue block56;
                }
                case "matching": {
                    dataArray[i] = nrdxTaskLinkDTO.getMatching();
                    continue block56;
                }
                case "configuration": {
                    dataArray[i] = nrdxTaskLinkDTO.getConfiguration().name();
                    continue block56;
                }
                case "isHidden": {
                    dataArray[i] = String.valueOf(nrdxTaskLinkDTO.getIsHidden());
                    continue block56;
                }
                case "specified": {
                    dataArray[i] = nrdxTaskLinkDTO.getSpecified();
                    continue block56;
                }
                case "theoffset": {
                    dataArray[i] = nrdxTaskLinkDTO.getTheoffset();
                    continue block56;
                }
                case "beginTime": {
                    dataArray[i] = nrdxTaskLinkDTO.getBeginTime();
                    continue block56;
                }
                case "endTime": {
                    dataArray[i] = nrdxTaskLinkDTO.getEndTime();
                    continue block56;
                }
                case "matchingType": {
                    dataArray[i] = nrdxTaskLinkDTO.getMatchingType().name();
                    continue block56;
                }
                case "expressionType": {
                    dataArray[i] = nrdxTaskLinkDTO.getExpressionType().name();
                    continue block56;
                }
                case "relatedDims": {
                    dataArray[i] = nrdxTaskLinkDTO.getRelatedDims();
                    continue block56;
                }
                case "orgMappingRule": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxTaskLinkDTO.getOrgMappingRule());
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportFormulaVariCSV(List<String> formSchemeKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + Costs.FILE_SEPARATOR + FORMUILA_VARIABLE_CSV);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[FORMULA_VARIABLE_CSV_HEADER.size()];
            for (int i = 0; i < FORMULA_VARIABLE_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = FORMULA_VARIABLE_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String formSchemeKey : formSchemeKeys) {
                List formulaVars = this.runTimeViewController.queryAllFormulaVariable(formSchemeKey);
                if (CollectionUtils.isEmpty(formulaVars)) continue;
                List nrdxFormulaVariableDTOs = formulaVars.stream().map(NrdxFormulaVariableDTO::valueOf).collect(Collectors.toList());
                for (NrdxFormulaVariableDTO nrdxFormulaVariableDTO : nrdxFormulaVariableDTOs) {
                    this.exportFormulaVariDTO(csvWriter, nrdxFormulaVariableDTO);
                }
            }
        }
        catch (Exception exception) {
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }

    private void exportFormulaVariDTO(CsvWriter csvWriter, NrdxFormulaVariableDTO nrdxFormulaVariableDTO) throws Exception {
        String[] dataArray = new String[FORMULA_VARIABLE_CSV_HEADER.size()];
        block30: for (int i = 0; i < FORMULA_VARIABLE_CSV_HEADER.size(); ++i) {
            switch (FORMULA_VARIABLE_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxFormulaVariableDTO.getKey();
                    continue block30;
                }
                case "code": {
                    dataArray[i] = nrdxFormulaVariableDTO.getCode();
                    continue block30;
                }
                case "title": {
                    dataArray[i] = nrdxFormulaVariableDTO.getTitle();
                    continue block30;
                }
                case "type": {
                    dataArray[i] = String.valueOf(nrdxFormulaVariableDTO.getType());
                    continue block30;
                }
                case "formSchemeKey": {
                    dataArray[i] = nrdxFormulaVariableDTO.getFormSchemeKey();
                    continue block30;
                }
                case "valueType": {
                    dataArray[i] = String.valueOf(nrdxFormulaVariableDTO.getValueType());
                    continue block30;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxFormulaVariableDTO.getUpdateTime());
                    continue block30;
                }
                case "order": {
                    dataArray[i] = nrdxFormulaVariableDTO.getOrder();
                    continue block30;
                }
                case "version": {
                    dataArray[i] = nrdxFormulaVariableDTO.getVersion();
                    continue block30;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxFormulaVariableDTO.getOwnerLevelAndId();
                    continue block30;
                }
                case "length": {
                    dataArray[i] = String.valueOf(nrdxFormulaVariableDTO.getLength());
                    continue block30;
                }
                case "initType": {
                    dataArray[i] = String.valueOf(nrdxFormulaVariableDTO.getInitType());
                    continue block30;
                }
                case "initValue": {
                    dataArray[i] = nrdxFormulaVariableDTO.getInitValue();
                    continue block30;
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportFormSchemeCSV(List<String> formSchemeKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + Costs.FILE_SEPARATOR + FORM_SCHEME_CSV);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[FORM_SCHEME_CSV_HEADER.size()];
            for (int i = 0; i < FORM_SCHEME_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = FORM_SCHEME_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String formSchemeKey : formSchemeKeys) {
                DesParamLanguageDTO desParamLanguage;
                FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
                if (formSchemeDefine == null) continue;
                NrdxFormSchemeDTO nrdxFormSchemeDTO = NrdxFormSchemeDTO.valueOf(formSchemeDefine);
                AnalysisSchemeParamDefine analysisSchemeParamDefine = this.runTimeViewController.queryAnalysisSchemeParamDefine(formSchemeKey);
                if (analysisSchemeParamDefine != null) {
                    try {
                        AnalysisSchemeDTO analysisSchemeDTO = new AnalysisSchemeDTO();
                        analysisSchemeDTO.setAnalysisScheme((AnalysisSchemeParamDefineImpl)analysisSchemeParamDefine);
                        nrdxFormSchemeDTO.setAnalysisSchemeDTO(analysisSchemeDTO);
                    }
                    catch (Exception e) {
                        logger.error("\u5bfc\u51fa\u62a5\u8868\u65b9\u6848 " + formSchemeKey + "\u65f6\u5d4c\u5165\u5f0f\u5206\u6790\u53c2\u6570\u5bfc\u51fa\u5931\u8d25\uff1a " + e.getMessage());
                    }
                }
                nrdxFormSchemeDTO.setTransformReportDefine(null);
                List periods = this.runTimeViewController.querySchemePeriodLinkByScheme(formSchemeKey);
                if (!CollectionUtils.isEmpty(periods)) {
                    List<SchemePeriodLinkDTO> links = periods.stream().map(a -> {
                        SchemePeriodLinkDTO schemePeriodLinkDTO = new SchemePeriodLinkDTO();
                        schemePeriodLinkDTO.setSchemeKey(a.getSchemeKey());
                        schemePeriodLinkDTO.setPeriodKey(a.getPeriodKey());
                        return schemePeriodLinkDTO;
                    }).collect(Collectors.toList());
                    nrdxFormSchemeDTO.setPeriodLinks(links);
                }
                if ((desParamLanguage = this.utilsService.getDesParamLanguage(formSchemeKey, LanguageResourceType.SCHEMETITLE, "2")) != null) {
                    nrdxFormSchemeDTO.setDesParamLanguageDTO(desParamLanguage);
                }
                this.exportFormSchemeInfo(csvWriter, nrdxFormSchemeDTO, formSchemeDefine);
            }
        }
        catch (Exception exception) {
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }

    private void exportFormSchemeInfo(CsvWriter csvWriter, NrdxFormSchemeDTO nrdxFormSchemeDTO, FormSchemeDefine formSchemeDefine) throws Exception {
        String[] dataArray = new String[FORM_SCHEME_CSV_HEADER.size()];
        block50: for (int i = 0; i < FORM_SCHEME_CSV_HEADER.size(); ++i) {
            switch (FORM_SCHEME_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxFormSchemeDTO.getKey();
                    continue block50;
                }
                case "formSchemeCode": {
                    dataArray[i] = nrdxFormSchemeDTO.getFormSchemeCode();
                    continue block50;
                }
                case "title": {
                    dataArray[i] = nrdxFormSchemeDTO.getTitle();
                    continue block50;
                }
                case "taskKey": {
                    dataArray[i] = nrdxFormSchemeDTO.getTaskKey();
                    continue block50;
                }
                case "periodType": {
                    dataArray[i] = nrdxFormSchemeDTO.getPeriodType().name();
                    continue block50;
                }
                case "order": {
                    dataArray[i] = nrdxFormSchemeDTO.getOrder();
                    continue block50;
                }
                case "version": {
                    dataArray[i] = nrdxFormSchemeDTO.getVersion();
                    continue block50;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxFormSchemeDTO.getOwnerLevelAndId();
                    continue block50;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxFormSchemeDTO.getUpdateTime());
                    continue block50;
                }
                case "description": {
                    dataArray[i] = nrdxFormSchemeDTO.getDescription();
                    continue block50;
                }
                case "taskPrefix": {
                    dataArray[i] = nrdxFormSchemeDTO.getTaskPrefix();
                    continue block50;
                }
                case "filePrefix": {
                    dataArray[i] = nrdxFormSchemeDTO.getFilePrefix();
                    continue block50;
                }
                case "periodOffset": {
                    dataArray[i] = String.valueOf(nrdxFormSchemeDTO.getPeriodOffset());
                    continue block50;
                }
                case "measureUnit": {
                    dataArray[i] = nrdxFormSchemeDTO.getMeasureUnit();
                    continue block50;
                }
                case "fillInAutomaticallyDue": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormSchemeDTO.getFillInAutomaticallyDue());
                    continue block50;
                }
                case "dw": {
                    dataArray[i] = nrdxFormSchemeDTO.getDw();
                    continue block50;
                }
                case "datetime": {
                    dataArray[i] = nrdxFormSchemeDTO.getDatetime();
                    continue block50;
                }
                case "dims": {
                    dataArray[i] = nrdxFormSchemeDTO.getDims();
                    continue block50;
                }
                case "filterExpression": {
                    dataArray[i] = nrdxFormSchemeDTO.getFilterExpression();
                    continue block50;
                }
                case "transformReportDefine": {
                    dataArray[i] = "";
                    continue block50;
                }
                case "periodLinks": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormSchemeDTO.getPeriodLinks());
                    continue block50;
                }
                case "analysisSchemeDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormSchemeDTO.getAnalysisSchemeDTO());
                    continue block50;
                }
                case "desParamLanguageDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormSchemeDTO.getDesParamLanguageDTO());
                    continue block50;
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    public void importModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        List<String> formSchemeKeys = param.getParamKeys();
        String path = context.getPath();
        this.importFormSchemeCSV(formSchemeKeys, path);
        this.importTaskLinkCSV(formSchemeKeys, path);
        this.importFormulaVariCSV(formSchemeKeys, path);
    }

    private void importFormSchemeCSV(List<String> formSchemeKeys, String path) {
        String normalizedFilePath = FilenameUtils.normalize(path + Costs.FILE_SEPARATOR + FORM_SCHEME_CSV);
        Path csvPath = Paths.get(normalizedFilePath, new String[0]);
        ArrayList<NrdxFormSchemeDTO> nrdxFormSchemeDTOs = new ArrayList<NrdxFormSchemeDTO>();
        try (CsvReader csvReader = null;){
            csvReader = new CsvReader(Files.newInputStream(csvPath, new OpenOption[0]), StandardCharsets.UTF_8);
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                NrdxFormSchemeDTO nrdxFormSchemeDTO = new NrdxFormSchemeDTO();
                for (String header : headers) {
                    String colValue = "";
                    try {
                        colValue = csvReader.get(header);
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    switch (header) {
                        case "key": {
                            nrdxFormSchemeDTO.setKey(colValue);
                            break;
                        }
                        case "formSchemeCode": {
                            nrdxFormSchemeDTO.setFormSchemeCode(colValue);
                            break;
                        }
                        case "title": {
                            nrdxFormSchemeDTO.setTitle(colValue);
                            break;
                        }
                        case "taskKey": {
                            nrdxFormSchemeDTO.setTaskKey(colValue);
                            break;
                        }
                        case "periodType": {
                            nrdxFormSchemeDTO.setPeriodType(PeriodType.valueOf((String)colValue));
                            break;
                        }
                        case "order": {
                            nrdxFormSchemeDTO.setOrder(colValue);
                            break;
                        }
                        case "version": {
                            nrdxFormSchemeDTO.setVersion(colValue);
                            break;
                        }
                        case "ownerLevelAndId": {
                            nrdxFormSchemeDTO.setOwnerLevelAndId(colValue);
                            break;
                        }
                        case "updateTime": {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                            nrdxFormSchemeDTO.setUpdateTime(sdf.parse(colValue));
                            break;
                        }
                        case "description": {
                            nrdxFormSchemeDTO.setDescription(colValue);
                            break;
                        }
                        case "taskPrefix": {
                            nrdxFormSchemeDTO.setTaskPrefix(colValue);
                            break;
                        }
                        case "filePrefix": {
                            nrdxFormSchemeDTO.setFilePrefix(colValue);
                            break;
                        }
                        case "periodOffset": {
                            nrdxFormSchemeDTO.setPeriodOffset(Integer.parseInt(colValue));
                            break;
                        }
                        case "measureUnit": {
                            nrdxFormSchemeDTO.setMeasureUnit(colValue);
                            break;
                        }
                        case "fillInAutomaticallyDue": {
                            FillInAutomaticallyDue fillInAutomaticallyDue = (FillInAutomaticallyDue)this.objectMapper.readValue(colValue, FillInAutomaticallyDue.class);
                            nrdxFormSchemeDTO.setFillInAutomaticallyDue(fillInAutomaticallyDue);
                            break;
                        }
                        case "dw": {
                            nrdxFormSchemeDTO.setDw(colValue);
                            break;
                        }
                        case "datetime": {
                            nrdxFormSchemeDTO.setDatetime(colValue);
                            break;
                        }
                        case "dims": {
                            nrdxFormSchemeDTO.setDims(colValue);
                            break;
                        }
                        case "filterExpression": {
                            nrdxFormSchemeDTO.setFilterExpression(colValue);
                            break;
                        }
                        case "transformReportDefine": {
                            nrdxFormSchemeDTO.setTransformReportDefine((TransformReportDefine)this.objectMapper.readValue(colValue, TransformReportDefine.class));
                            break;
                        }
                        case "periodLinks": {
                            nrdxFormSchemeDTO.setPeriodLinks((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<SchemePeriodLinkDTO>>(){}));
                            break;
                        }
                        case "analysisSchemeDTO": {
                            nrdxFormSchemeDTO.setAnalysisSchemeDTO((AnalysisSchemeDTO)this.objectMapper.readValue(colValue, AnalysisSchemeDTO.class));
                            break;
                        }
                        case "desParamLanguageDTO": {
                            nrdxFormSchemeDTO.setDesParamLanguageDTO((DesParamLanguageDTO)this.objectMapper.readValue(colValue, DesParamLanguageDTO.class));
                            break;
                        }
                    }
                    if (!formSchemeKeys.contains(nrdxFormSchemeDTO.getKey())) break;
                }
                nrdxFormSchemeDTOs.add(nrdxFormSchemeDTO);
            }
        }
        this.importFormSchemeDTO(nrdxFormSchemeDTOs);
    }

    private void importFormSchemeDTO(List<NrdxFormSchemeDTO> nrdxFormSchemeDTOs) {
    }

    private void importTaskLinkCSV(List<String> formSchemeKeys, String path) {
        String normalizedFilePath = FilenameUtils.normalize(path + Costs.FILE_SEPARATOR + TASK_LINK_CSV);
        Path csvPath = Paths.get(normalizedFilePath, new String[0]);
        ArrayList<NrdxTaskLinkDTO> nrdxTaskLinkDTOs = new ArrayList<NrdxTaskLinkDTO>();
        try (CsvReader csvReader = null;){
            csvReader = new CsvReader(Files.newInputStream(csvPath, new OpenOption[0]), StandardCharsets.UTF_8);
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                NrdxTaskLinkDTO nrdxTaskLinkDTO = new NrdxTaskLinkDTO();
                for (String header : headers) {
                    String colValue = "";
                    try {
                        colValue = csvReader.get(header);
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    switch (header) {
                        case "key": {
                            nrdxTaskLinkDTO.setKey(colValue);
                            break;
                        }
                        case "title": {
                            nrdxTaskLinkDTO.setTitle(colValue);
                            break;
                        }
                        case "order": {
                            nrdxTaskLinkDTO.setOrder(colValue);
                            break;
                        }
                        case "version": {
                            nrdxTaskLinkDTO.setVersion(colValue);
                            break;
                        }
                        case "ownerLevelAndId": {
                            nrdxTaskLinkDTO.setOwnerLevelAndId(colValue);
                            break;
                        }
                        case "updateTime": {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                            nrdxTaskLinkDTO.setUpdateTime(sdf.parse(colValue));
                            break;
                        }
                        case "description": {
                            nrdxTaskLinkDTO.setDescription(colValue);
                            break;
                        }
                        case "linkAlias": {
                            nrdxTaskLinkDTO.setLinkAlias(colValue);
                            break;
                        }
                        case "currentFormSchemeKey": {
                            nrdxTaskLinkDTO.setCurrentFormSchemeKey(colValue);
                            break;
                        }
                        case "relatedTaskKey": {
                            nrdxTaskLinkDTO.setRelatedTaskKey(colValue);
                            break;
                        }
                        case "relatedFormSchemeKey": {
                            nrdxTaskLinkDTO.setRelatedFormSchemeKey(colValue);
                            break;
                        }
                        case "relatedTaskCode": {
                            nrdxTaskLinkDTO.setRelatedTaskCode(colValue);
                            break;
                        }
                        case "periodOffset": {
                            nrdxTaskLinkDTO.setPeriodOffset(colValue);
                            break;
                        }
                        case "currentTaskFormula": {
                            nrdxTaskLinkDTO.setCurrentTaskFormula(colValue);
                            break;
                        }
                        case "relatedTaskFormula": {
                            nrdxTaskLinkDTO.setRelatedTaskFormula(colValue);
                            break;
                        }
                        case "matching": {
                            nrdxTaskLinkDTO.setMatching(colValue);
                            break;
                        }
                        case "configuration": {
                            nrdxTaskLinkDTO.setConfiguration(PeriodMatchingType.valueOf((String)colValue));
                            break;
                        }
                        case "isHidden": {
                            nrdxTaskLinkDTO.setIsHidden(Integer.parseInt(colValue));
                            break;
                        }
                        case "specified": {
                            nrdxTaskLinkDTO.setSpecified(colValue);
                            break;
                        }
                        case "theoffset": {
                            nrdxTaskLinkDTO.setTheoffset(colValue);
                            break;
                        }
                        case "beginTime": {
                            nrdxTaskLinkDTO.setBeginTime(colValue);
                            break;
                        }
                        case "endTime": {
                            nrdxTaskLinkDTO.setEndTime(colValue);
                            break;
                        }
                        case "matchingType": {
                            nrdxTaskLinkDTO.setMatchingType(TaskLinkMatchingType.valueOf((String)colValue));
                            break;
                        }
                        case "expressionType": {
                            nrdxTaskLinkDTO.setExpressionType(TaskLinkExpressionType.valueOf((String)colValue));
                            break;
                        }
                        case "relatedDims": {
                            nrdxTaskLinkDTO.setRelatedDims(colValue);
                            break;
                        }
                        case "orgMappingRule": {
                            nrdxTaskLinkDTO.setOrgMappingRule((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<TaskLinkOrgMappingRule>>(){}));
                            break;
                        }
                    }
                    if (!formSchemeKeys.contains(nrdxTaskLinkDTO.getCurrentFormSchemeKey())) break;
                }
                nrdxTaskLinkDTOs.add(nrdxTaskLinkDTO);
            }
        }
        this.importTaskLinkDTO(nrdxTaskLinkDTOs);
    }

    private void importTaskLinkDTO(List<NrdxTaskLinkDTO> nrdxFormSchemeDTOs) {
    }

    private void importFormulaVariCSV(List<String> taskGroupKeys, String path) {
        String normalizedFilePath = FilenameUtils.normalize(path + Costs.FILE_SEPARATOR + TASK_LINK_CSV);
        Path csvPath = Paths.get(normalizedFilePath, new String[0]);
        ArrayList<NrdxFormulaVariableDTO> nrdxFormulaVariableDTOs = new ArrayList<NrdxFormulaVariableDTO>();
        try (CsvReader csvReader = null;){
            csvReader = new CsvReader(Files.newInputStream(csvPath, new OpenOption[0]), StandardCharsets.UTF_8);
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                NrdxFormulaVariableDTO nrdxFormulaVariableDTO = new NrdxFormulaVariableDTO();
                for (String header : headers) {
                    String colValue = "";
                    try {
                        colValue = csvReader.get(header);
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    switch (header) {
                        case "key": {
                            nrdxFormulaVariableDTO.setKey(colValue);
                            break;
                        }
                        case "code": {
                            nrdxFormulaVariableDTO.setCode(colValue);
                            break;
                        }
                        case "title": {
                            nrdxFormulaVariableDTO.setTitle(colValue);
                            break;
                        }
                        case "type": {
                            nrdxFormulaVariableDTO.setType(Integer.parseInt(colValue));
                            break;
                        }
                        case "formSchemeKey": {
                            nrdxFormulaVariableDTO.setFormSchemeKey(colValue);
                            break;
                        }
                        case "valueType": {
                            nrdxFormulaVariableDTO.setValueType(Integer.parseInt(colValue));
                            break;
                        }
                        case "order": {
                            nrdxFormulaVariableDTO.setOrder(colValue);
                            break;
                        }
                        case "updateTime": {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                            nrdxFormulaVariableDTO.setUpdateTime(sdf.parse(colValue));
                            break;
                        }
                        case "version": {
                            nrdxFormulaVariableDTO.setVersion(colValue);
                            break;
                        }
                        case "ownerLevelAndId": {
                            nrdxFormulaVariableDTO.setOwnerLevelAndId(colValue);
                            break;
                        }
                        case "length": {
                            nrdxFormulaVariableDTO.setLength(Integer.parseInt(colValue));
                            break;
                        }
                        case "initType": {
                            nrdxFormulaVariableDTO.setInitType(Integer.parseInt(colValue));
                            break;
                        }
                        case "initValue": {
                            nrdxFormulaVariableDTO.setInitValue(colValue);
                            break;
                        }
                    }
                    if (!taskGroupKeys.contains(nrdxFormulaVariableDTO.getFormSchemeKey())) break;
                }
                nrdxFormulaVariableDTOs.add(nrdxFormulaVariableDTO);
            }
        }
        this.importFormulaVariableDTO(nrdxFormulaVariableDTOs);
    }

    private void importFormulaVariableDTO(List<NrdxFormulaVariableDTO> nrdxFormulaVariableDTOs) {
    }

    public void preAnalysis(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
    }

    public DepResource depResource(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        DepResource depResource = new DepResource();
        ArrayList<ResItem> result = new ArrayList<ResItem>();
        List mappingByForm = this.mappingTransferService.getSchemeByForm(param.getFormScheme());
        if (mappingByForm == null) {
            logger.info("\u62a5\u8868\u65b9\u6848: " + param.getFormScheme() + " \u6dfb\u52a0\u5173\u8054\u8d44\u6e90\uff0c\u65b0\u6620\u5c04\u67e5\u8be2\u7ed3\u679c\u4e3a\u7a7a");
        } else {
            for (String s : mappingByForm) {
                result.add(new ResItem(s, "SHCEME", "nvwa_mapping"));
            }
        }
        depResource.setResItems(result);
        return depResource;
    }
}

