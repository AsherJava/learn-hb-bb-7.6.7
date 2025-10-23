/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.service.TaskOrgLinkService
 *  com.jiuqi.nr.definition.option.core.TaskOption
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 *  com.jiuqi.nr.filterTemplate.service.IFilterTemplateService
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.task.DimensionFilterDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.task.TaskOrgLinkDTO
 *  com.jiuqi.nr.tds.Costs
 */
package com.jiuqi.nr.nrdx.param.task.service.transferImpl;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.option.core.TaskOption;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.dto.TaskParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.dto.task.NrdxTaskDTO;
import com.jiuqi.nr.nrdx.param.task.dto.task.NrdxTaskGroupDTO;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.nrdx.param.task.utils.UtilsService;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.DimensionFilterDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskOrgLinkDTO;
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
public class TaskTransfer
extends AbstractParamTransfer {
    private static final Logger logger = LoggerFactory.getLogger(TaskTransfer.class);
    public static final List<String> TASK_CSV_HEADER = Arrays.asList("key", "taskCode", "title", "measureUnit", "fromPeriod", "toPeriod", "taskPeriodOffset", "taskFilePrefix", "order", "version", "ownerLevelAndId", "updateTime", "description", "taskGatherType", "formulaSyntaxStyle", "viewsInEFDC", "taskType", "createUserName", "createTime", "efdcSwitch", "fillInAutomaticallyDue", "dataScheme", "dw", "datetime", "dims", "entityViewID", "filterExpression", "fillingDateType", "fillingDateDays", "taskFlowsBigData", "dimensionFilters", "taskOptions", "periodLinks", "entityViews", "formulaConditions", "desParamLanguageDTO", "taskOrgLinkDTOs");
    public static final List<String> TASK_GROUP_CSV_HEADER = Arrays.asList("key", "code", "title", "parentKey", "order", "version", "ownerLevelAndId", "updateTime", "description", "taskKeys");
    public static final String TASK_CSV = "TASK.CSV";
    public static final String TASK_GROUP_CSV = "TASK_GROUP.CSV";
    private final ObjectMapper objectMapper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFilterTemplateService filterTemplateService;
    @Autowired
    private DesignBigDataTableDao bigDataDao;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private UtilsService utilsService;
    @Autowired(required=false)
    private TaskOrgLinkService taskOrgLinkService;

    public TaskTransfer() {
        logger.info("TaskTransfer\u521d\u59cb\u5316objectMapper");
        this.objectMapper = new ObjectMapper();
        DefinitionModelTransfer.moduleRegister((ObjectMapper)this.objectMapper);
    }

    public void exportModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        List<String> taskKeys = param.getParamKeys();
        List<String> taskGroupKeys = ((TaskParamDTO)param).getTasKGroup();
        String path = context.getPath();
        Costs.createPathIfNotExists((Path)new File(path).toPath());
        this.exportTaskGroupCSV(taskGroupKeys, path);
        this.exportTaskCSV(taskKeys, path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportTaskGroupCSV(List<String> taskGroupKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + Costs.FILE_SEPARATOR + TASK_GROUP_CSV);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[TASK_GROUP_CSV_HEADER.size()];
            for (int i = 0; i < TASK_GROUP_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = TASK_GROUP_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String taskGroupKey : taskGroupKeys) {
                TaskGroupDefine taskGroupDefine = this.runTimeViewController.queryTaskGroupDefine(taskGroupKey);
                if (taskGroupDefine == null) continue;
                NrdxTaskGroupDTO nrdxTaskGroupDTO = NrdxTaskGroupDTO.valueOf(taskGroupDefine);
                List links = this.designTimeViewController.getGroupLinkByGroupKey(taskGroupKey);
                if (!CollectionUtils.isEmpty(links)) {
                    List<String> taskKeys = links.stream().map(DesignTaskGroupLink::getTaskKey).collect(Collectors.toList());
                    nrdxTaskGroupDTO.setTaskKeys(taskKeys);
                }
                this.exportTaskGroupInfo(csvWriter, nrdxTaskGroupDTO, taskGroupDefine);
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

    private void exportTaskGroupInfo(CsvWriter csvWriter, NrdxTaskGroupDTO nrdxTaskGroupDTO, TaskGroupDefine taskGroupDefine) throws Exception {
        String[] dataArray = new String[TASK_GROUP_CSV_HEADER.size()];
        for (int i = 0; i < TASK_GROUP_CSV_HEADER.size(); ++i) {
            switch (TASK_GROUP_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxTaskGroupDTO.getKey();
                    break;
                }
                case "code": {
                    dataArray[i] = nrdxTaskGroupDTO.getCode();
                    break;
                }
                case "title": {
                    dataArray[i] = nrdxTaskGroupDTO.getTitle();
                    break;
                }
                case "parentKey": {
                    dataArray[i] = nrdxTaskGroupDTO.getParentKey();
                    break;
                }
                case "order": {
                    dataArray[i] = nrdxTaskGroupDTO.getOrder();
                    break;
                }
                case "version": {
                    dataArray[i] = nrdxTaskGroupDTO.getVersion();
                    break;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxTaskGroupDTO.getOwnerLevelAndId();
                    break;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxTaskGroupDTO.getUpdateTime());
                    break;
                }
                case "description": {
                    dataArray[i] = nrdxTaskGroupDTO.getDescription();
                    break;
                }
                case "taskKeys": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxTaskGroupDTO.getTaskKeys());
                    break;
                }
            }
            csvWriter.writeRecord(dataArray);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportTaskCSV(List<String> taskKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + Costs.FILE_SEPARATOR + TASK_CSV);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[TASK_CSV_HEADER.size()];
            for (int i = 0; i < TASK_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = TASK_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String taskKey : taskKeys) {
                List byTask;
                DesignBigDataTable runTimeBigDataTable;
                FilterTemplateDTO entityViewDefine;
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
                if (taskDefine == null) continue;
                NrdxTaskDTO nrdxTaskDTO = NrdxTaskDTO.valueOf(taskDefine);
                if (nrdxTaskDTO.getEntityViewID() != null && (entityViewDefine = this.filterTemplateService.getFilterTemplate(taskDefine.getFilterTemplate())) != null) {
                    nrdxTaskDTO.setFilterExpression(entityViewDefine.getFilterContent());
                    EntityViewDTO entityViewDTO = EntityViewDTO.valueOf((FilterTemplateDO)new FilterTemplateDO(entityViewDefine));
                    ArrayList<EntityViewDTO> entityViewDTOS = new ArrayList<EntityViewDTO>();
                    entityViewDTOS.add(entityViewDTO);
                    nrdxTaskDTO.setEntityViews(entityViewDTOS);
                }
                if ((runTimeBigDataTable = this.bigDataDao.queryigDataDefine(taskKey, "FLOWSETTING")) != null) {
                    nrdxTaskDTO.setTaskFlowsBigData(runTimeBigDataTable.getData());
                }
                List options = this.iTaskOptionController.getOptions(taskKey);
                nrdxTaskDTO.setTaskOptions(options);
                List periods = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey);
                if (!CollectionUtils.isEmpty(periods)) {
                    ArrayList<SchemePeriodLinkDTO> links = new ArrayList<SchemePeriodLinkDTO>(periods.size());
                    for (SchemePeriodLinkDefine period : periods) {
                        SchemePeriodLinkDTO schemePeriodLinkDTO = new SchemePeriodLinkDTO();
                        schemePeriodLinkDTO.setSchemeKey(period.getSchemeKey());
                        schemePeriodLinkDTO.setPeriodKey(period.getPeriodKey());
                    }
                    nrdxTaskDTO.setPeriodLinks(links);
                }
                List filters = this.designTimeViewController.getDimensionFilterByTaskKey(taskKey);
                nrdxTaskDTO.setDimensionFilters(DimensionFilterDTO.convertDTO((List)filters));
                DesParamLanguageDTO desParamLanguage = this.utilsService.getDesParamLanguage(taskKey, LanguageResourceType.TASKTITLE, "2");
                if (desParamLanguage != null) {
                    nrdxTaskDTO.setDesParamLanguageDTO(desParamLanguage);
                }
                if (this.taskOrgLinkService != null && !CollectionUtils.isEmpty(byTask = this.taskOrgLinkService.getByTask(taskKey))) {
                    ArrayList<TaskOrgLinkDTO> taskOrgLinkDTOs = new ArrayList<TaskOrgLinkDTO>();
                    for (TaskOrgLinkDefine taskOrgLinkDefine : byTask) {
                        TaskOrgLinkDTO taskOrgLinkDTO = TaskOrgLinkDTO.valueOf((TaskOrgLinkDefine)taskOrgLinkDefine);
                        taskOrgLinkDTOs.add(taskOrgLinkDTO);
                    }
                    nrdxTaskDTO.setTaskOrgLinkDTOs(taskOrgLinkDTOs);
                }
                this.exportTaskInfo(csvWriter, nrdxTaskDTO, taskDefine);
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

    private void exportTaskInfo(CsvWriter csvWriter, NrdxTaskDTO nrdxTaskDTO, TaskDefine taskDefine) throws Exception {
        String[] dataArray = new String[TASK_CSV_HEADER.size()];
        for (int i = 0; i < TASK_CSV_HEADER.size(); ++i) {
            switch (TASK_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxTaskDTO.getKey();
                    break;
                }
                case "taskCode": {
                    dataArray[i] = nrdxTaskDTO.getTaskCode();
                    break;
                }
                case "title": {
                    dataArray[i] = nrdxTaskDTO.getTitle();
                    break;
                }
                case "measureUnit": {
                    dataArray[i] = nrdxTaskDTO.getMeasureUnit();
                    break;
                }
                case "fromPeriod": {
                    dataArray[i] = nrdxTaskDTO.getFromPeriod();
                    break;
                }
                case "toPeriod": {
                    dataArray[i] = nrdxTaskDTO.getToPeriod();
                    break;
                }
                case "taskPeriodOffset": {
                    dataArray[i] = String.valueOf(nrdxTaskDTO.getTaskPeriodOffset());
                    break;
                }
                case "taskFilePrefix": {
                    dataArray[i] = nrdxTaskDTO.getTaskFilePrefix();
                    break;
                }
                case "order": {
                    dataArray[i] = nrdxTaskDTO.getOrder();
                    break;
                }
                case "version": {
                    dataArray[i] = nrdxTaskDTO.getVersion();
                    break;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxTaskDTO.getOwnerLevelAndId();
                    break;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxTaskDTO.getUpdateTime());
                    break;
                }
                case "description": {
                    dataArray[i] = nrdxTaskDTO.getDescription();
                    break;
                }
                case "taskGatherType": {
                    dataArray[i] = nrdxTaskDTO.getTaskGatherType().name();
                    break;
                }
                case "formulaSyntaxStyle": {
                    dataArray[i] = nrdxTaskDTO.getFormulaSyntaxStyle().name();
                    break;
                }
                case "viewsInEFDC": {
                    dataArray[i] = nrdxTaskDTO.getViewsInEFDC();
                    break;
                }
                case "taskType": {
                    dataArray[i] = nrdxTaskDTO.getTaskType().name();
                    break;
                }
                case "createUserName": {
                    dataArray[i] = nrdxTaskDTO.getCreateUserName();
                    break;
                }
                case "createTime": {
                    dataArray[i] = nrdxTaskDTO.getCreateTime();
                    break;
                }
                case "efdcSwitch": {
                    dataArray[i] = String.valueOf(nrdxTaskDTO.isEfdcSwitch());
                    break;
                }
                case "fillInAutomaticallyDue": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxTaskDTO.getFillInAutomaticallyDue());
                    break;
                }
                case "dataScheme": {
                    dataArray[i] = nrdxTaskDTO.getDataScheme();
                    break;
                }
                case "dw": {
                    dataArray[i] = nrdxTaskDTO.getDw();
                    break;
                }
                case "datetime": {
                    dataArray[i] = nrdxTaskDTO.getDatetime();
                    break;
                }
                case "dims": {
                    dataArray[i] = nrdxTaskDTO.getDims();
                    break;
                }
                case "entityViewID": {
                    dataArray[i] = nrdxTaskDTO.getEntityViewID();
                    break;
                }
                case "filterExpression": {
                    dataArray[i] = nrdxTaskDTO.getFilterExpression();
                    break;
                }
                case "fillingDateType": {
                    dataArray[i] = String.valueOf(nrdxTaskDTO.getFillingDateType());
                    break;
                }
                case "fillingDateDays": {
                    dataArray[i] = String.valueOf(nrdxTaskDTO.getFillingDateDays());
                    break;
                }
                case "taskFlowsBigData": {
                    dataArray[i] = new String(nrdxTaskDTO.getTaskFlowsBigData());
                    break;
                }
                case "dimensionFilters": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxTaskDTO.getDimensionFilters());
                    break;
                }
                case "taskOptions": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxTaskDTO.getTaskOptions());
                    break;
                }
                case "periodLinks": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxTaskDTO.getPeriodLinks());
                    break;
                }
                case "entityViews": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxTaskDTO.getEntityViews());
                    break;
                }
                case "formulaConditions": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxTaskDTO.getFormulaConditions());
                    break;
                }
                case "desParamLanguageDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxTaskDTO.getDesParamLanguageDTO());
                    break;
                }
                case "taskOrgLinkDTOs": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxTaskDTO.getTaskOrgLinkDTOs());
                    break;
                }
            }
            csvWriter.writeRecord(dataArray);
        }
    }

    public void importModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        List<String> taskKeys = param.getParamKeys();
        List<String> taskGroupKeys = ((TaskParamDTO)param).getTasKGroup();
        String path = context.getPath();
        this.importTaskGroupCSV(taskGroupKeys, path);
        this.importTaskCSV(taskKeys, path);
    }

    private void importTaskGroupCSV(List<String> taskGroupKeys, String path) {
        String normalizedFilePath = FilenameUtils.normalize(path + Costs.FILE_SEPARATOR + TASK_GROUP_CSV);
        Path csvPath = Paths.get(normalizedFilePath, new String[0]);
        ArrayList<NrdxTaskGroupDTO> nrdxTaskGroupDTOs = new ArrayList<NrdxTaskGroupDTO>();
        try (CsvReader csvReader = null;){
            csvReader = new CsvReader(Files.newInputStream(csvPath, new OpenOption[0]), StandardCharsets.UTF_8);
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                NrdxTaskGroupDTO nrdxTaskGroupDTO = new NrdxTaskGroupDTO();
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
                            nrdxTaskGroupDTO.setKey(colValue);
                            break;
                        }
                        case "code": {
                            nrdxTaskGroupDTO.setCode(colValue);
                            break;
                        }
                        case "title": {
                            nrdxTaskGroupDTO.setTitle(colValue);
                            break;
                        }
                        case "parentKey": {
                            nrdxTaskGroupDTO.setParentKey(colValue);
                            break;
                        }
                        case "order": {
                            nrdxTaskGroupDTO.setOrder(colValue);
                            break;
                        }
                        case "version": {
                            nrdxTaskGroupDTO.setVersion(colValue);
                            break;
                        }
                        case "ownerLevelAndId": {
                            nrdxTaskGroupDTO.setOwnerLevelAndId(colValue);
                            break;
                        }
                        case "updateTime": {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                            nrdxTaskGroupDTO.setUpdateTime(sdf.parse(colValue));
                            break;
                        }
                        case "description": {
                            nrdxTaskGroupDTO.setDescription(colValue);
                            break;
                        }
                        case "taskKeys": {
                            nrdxTaskGroupDTO.setTaskKeys((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<String>>(){}));
                            break;
                        }
                    }
                    if (!taskGroupKeys.contains(nrdxTaskGroupDTO.getKey())) break;
                }
                nrdxTaskGroupDTOs.add(nrdxTaskGroupDTO);
            }
        }
        this.importTaskGroupDTO(nrdxTaskGroupDTOs);
    }

    private void importTaskGroupDTO(List<NrdxTaskGroupDTO> nrdxTaskGroupDTOs) {
    }

    private void importTaskCSV(List<String> taskKeys, String path) {
        String normalizedFilePath = FilenameUtils.normalize(path + Costs.FILE_SEPARATOR + TASK_CSV);
        Path csvPath = Paths.get(normalizedFilePath, new String[0]);
        ArrayList<NrdxTaskDTO> nrdxTaskDTOs = new ArrayList<NrdxTaskDTO>();
        try (CsvReader csvReader = null;){
            csvReader = new CsvReader(Files.newInputStream(csvPath, new OpenOption[0]), StandardCharsets.UTF_8);
            csvReader = new CsvReader(Files.newInputStream(csvPath, new OpenOption[0]), StandardCharsets.UTF_8);
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                NrdxTaskDTO nrdxTaskDTO = new NrdxTaskDTO();
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
                            nrdxTaskDTO.setKey(colValue);
                            break;
                        }
                        case "title": {
                            nrdxTaskDTO.setTitle(colValue);
                            break;
                        }
                        case "taskCode": {
                            nrdxTaskDTO.setTaskCode(colValue);
                            break;
                        }
                        case "measureUnit": {
                            nrdxTaskDTO.setMeasureUnit(colValue);
                            break;
                        }
                        case "fromPeriod": {
                            nrdxTaskDTO.setFromPeriod(colValue);
                            break;
                        }
                        case "toPeriod": {
                            nrdxTaskDTO.setToPeriod(colValue);
                            break;
                        }
                        case "taskPeriodOffset": {
                            nrdxTaskDTO.setTaskPeriodOffset(Integer.parseInt(colValue));
                            break;
                        }
                        case "taskFilePrefix": {
                            nrdxTaskDTO.setTaskFilePrefix(colValue);
                            break;
                        }
                        case "order": {
                            nrdxTaskDTO.setOrder(colValue);
                            break;
                        }
                        case "version": {
                            nrdxTaskDTO.setVersion(colValue);
                            break;
                        }
                        case "ownerLevelAndId": {
                            nrdxTaskDTO.setOwnerLevelAndId(colValue);
                            break;
                        }
                        case "updateTime": {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                            nrdxTaskDTO.setUpdateTime(sdf.parse(colValue));
                            break;
                        }
                        case "description": {
                            nrdxTaskDTO.setDescription(colValue);
                            break;
                        }
                        case "taskGatherType": {
                            TaskGatherType taskGatherType = TaskGatherType.valueOf((String)colValue);
                            nrdxTaskDTO.setTaskGatherType(taskGatherType);
                            break;
                        }
                        case "formulaSyntaxStyle": {
                            FormulaSyntaxStyle formulaSyntaxStyle = FormulaSyntaxStyle.valueOf((String)colValue);
                            nrdxTaskDTO.setFormulaSyntaxStyle(formulaSyntaxStyle);
                            break;
                        }
                        case "viewsInEFDC": {
                            nrdxTaskDTO.setViewsInEFDC(colValue);
                            break;
                        }
                        case "taskType": {
                            TaskType taskType = TaskType.valueOf((String)colValue);
                            nrdxTaskDTO.setTaskType(taskType);
                            break;
                        }
                        case "createUserName": {
                            nrdxTaskDTO.setCreateUserName(colValue);
                            break;
                        }
                        case "createTime": {
                            nrdxTaskDTO.setCreateTime(colValue);
                            break;
                        }
                        case "efdcSwitch": {
                            nrdxTaskDTO.setEfdcSwitch(Boolean.parseBoolean(colValue));
                            break;
                        }
                        case "fillInAutomaticallyDue": {
                            FillInAutomaticallyDue fillInAutomaticallyDue = (FillInAutomaticallyDue)this.objectMapper.readValue(colValue, FillInAutomaticallyDue.class);
                            nrdxTaskDTO.setFillInAutomaticallyDue(fillInAutomaticallyDue);
                            break;
                        }
                        case "dataScheme": {
                            nrdxTaskDTO.setDataScheme(colValue);
                            break;
                        }
                        case "dw": {
                            nrdxTaskDTO.setDw(colValue);
                            break;
                        }
                        case "datetime": {
                            nrdxTaskDTO.setDatetime(colValue);
                            break;
                        }
                        case "dims": {
                            nrdxTaskDTO.setDims(colValue);
                            break;
                        }
                        case "entityViewID": {
                            nrdxTaskDTO.setEntityViewID(colValue);
                            break;
                        }
                        case "filterExpression": {
                            nrdxTaskDTO.setFilterExpression(colValue);
                            break;
                        }
                        case "fillingDateType": {
                            nrdxTaskDTO.setFillingDateType(Integer.parseInt(colValue));
                            break;
                        }
                        case "fillingDateDays": {
                            nrdxTaskDTO.setFillingDateDays(Integer.parseInt(colValue));
                            break;
                        }
                        case "taskFlowsBigData": {
                            nrdxTaskDTO.setTaskFlowsBigData(colValue.getBytes());
                            break;
                        }
                        case "dimensionFilters": {
                            nrdxTaskDTO.setDimensionFilters((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<DimensionFilterDTO>>(){}));
                            break;
                        }
                        case "taskOptions": {
                            nrdxTaskDTO.setTaskOptions((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<TaskOption>>(){}));
                            break;
                        }
                        case "periodLinks": {
                            nrdxTaskDTO.setPeriodLinks((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<SchemePeriodLinkDTO>>(){}));
                            break;
                        }
                        case "entityViews": {
                            nrdxTaskDTO.setEntityViews((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<EntityViewDTO>>(){}));
                            break;
                        }
                        case "formulaConditions": {
                            nrdxTaskDTO.setFormulaConditions((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<FormulaConditionDTO>>(){}));
                            break;
                        }
                        case "desParamLanguageDTO": {
                            nrdxTaskDTO.setDesParamLanguageDTO((DesParamLanguageDTO)this.objectMapper.readValue(colValue, DesParamLanguageDTO.class));
                            break;
                        }
                        case "taskOrgLinkDTOs": {
                            nrdxTaskDTO.setTaskOrgLinkDTOs((List)this.objectMapper.readValue(colValue, (TypeReference)new TypeReference<List<TaskOrgLinkDTO>>(){}));
                            break;
                        }
                    }
                    if (!taskKeys.contains(nrdxTaskDTO.getKey())) break;
                }
                nrdxTaskDTOs.add(nrdxTaskDTO);
            }
        }
        this.importTaskDTO(nrdxTaskDTOs);
    }

    private void importTaskDTO(List<NrdxTaskDTO> nrdxTaskDTOs) {
    }

    public void preAnalysis(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
    }

    public DepResource depResource(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        return null;
    }

    @Override
    public List<ITransferModel> depModel(String s) {
        return null;
    }

    @Override
    public String code() {
        return NrdxParamNodeType.TASK.getCode();
    }

    @Override
    public String version() {
        return null;
    }
}

