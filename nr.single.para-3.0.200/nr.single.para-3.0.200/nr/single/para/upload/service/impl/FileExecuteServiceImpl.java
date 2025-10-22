/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.util.StringUtils
 */
package nr.single.para.upload.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.para.asyn.SingleCompareController;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.bean.ParaCompareRegionResult;
import nr.single.para.compare.bean.ParaCompareResult;
import nr.single.para.compare.bean.ParaCompareTaskInfo;
import nr.single.para.compare.definition.CompareDataFieldDTO;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.ISingleCompareDataFieldService;
import nr.single.para.compare.definition.ISingleCompareInfoService;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareParaType;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.service.TaskFileCompareService;
import nr.single.para.upload.domain.CreateParamDTO;
import nr.single.para.upload.domain.ParamImportInfoDTO;
import nr.single.para.upload.domain.SingleCompareDTO;
import nr.single.para.upload.domain.SingleCompareResult;
import nr.single.para.upload.domain.UploadFileDTO;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.service.IFileExecuteService;
import nr.single.para.upload.vo.FixRegionCompareVO;
import nr.single.para.upload.vo.FloatRegionCompareVO;
import nr.single.para.upload.vo.FormSchemeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FileExecuteServiceImpl
implements IFileExecuteService {
    private static final Logger log = LoggerFactory.getLogger(FileExecuteServiceImpl.class);
    @Autowired
    private TaskFileCompareService taskFileCompareService;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private SingleCompareController singleCompareController;
    @Autowired
    private ISingleCompareDataFieldService fieldCompareService;
    @Autowired
    private ISingleCompareInfoService compareInfoService;
    private static final String JIOMATCHTASK = "JIO_MATCH_TASK";
    private static final String JIOSINGLECOMPARE = "JIO_SINGLE_COMPARE";

    @Override
    public UploadFileDTO uploadAndMatchFile(String fileName, byte[] file) throws Exception {
        UploadFileDTO result = new UploadFileDTO();
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, JIOMATCHTASK);
        ParaCompareResult netTaskBySingle = this.taskFileCompareService.findNetTaskBySingle(fileName, file, (AsyncTaskMonitor)monitor);
        assert (netTaskBySingle != null);
        result.setKey(netTaskBySingle.getCompareId());
        result.setTaskFindMode(netTaskBySingle.getTaskFindMode());
        result.setSingleTaskYear(netTaskBySingle.getSingleTaskYear());
        result.setSingleFromPeriod(netTaskBySingle.getSingleFromPeriod());
        result.setSingleToPeriod(netTaskBySingle.getSingleToPeriod());
        result.setNetTaskInfo(netTaskBySingle.getNetTaskInfo());
        result.setSingleTaskName(netTaskBySingle.getSingleTaskTitle());
        result.setSingleFileFlag(netTaskBySingle.getSingleFileFlag());
        if (netTaskBySingle.isSuccess()) {
            result.setMatchTask(true);
            result.setSingleTaskName(netTaskBySingle.getSingleTaskTitle());
            result.setTaskInfos(netTaskBySingle.getMathchTasks());
        } else {
            result.setMatchTask(false);
            result.setCustom(false);
            if (netTaskBySingle.getSinglePeriodType() == PeriodType.CUSTOM) {
                result.setCustom(true);
            }
            result.setPeriod(Character.toString((char)netTaskBySingle.getSinglePeriodType().code()));
            result.setParaCompareDataSchemeInfo(netTaskBySingle.getDataSchemeInfo());
        }
        return result;
    }

    @Override
    public AsyncTaskInfo analysisFile(CreateParamDTO createParamDTO) throws Exception {
        ParaCompareOption option = this.getParaCompareOptionByParam(createParamDTO);
        String taskId = this.singleCompareController.compareSingleToTasK(createParamDTO.getKey(), createParamDTO.getTaskKey(), createParamDTO.getFormSchemeKey(), createParamDTO.getDataSchemeKey(), option);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public SingleCompareResult analysisFileEx(CreateParamDTO createParamDTO) throws Exception {
        ParaCompareOption option = this.getParaCompareOptionByParam(createParamDTO);
        ParaCompareResult reulst2 = this.singleCompareController.compareSingleToTasKEx(createParamDTO.getKey(), createParamDTO.getTaskKey(), createParamDTO.getFormSchemeKey(), createParamDTO.getDataSchemeKey(), option);
        SingleCompareResult result = new SingleCompareResult();
        if (reulst2 == null) {
            result.setSuccess(false);
            result.setMessage("\u6bd4\u8f83\u5931\u8d25\uff0c\u65e0\u6cd5\u8fd4\u56de\u503c");
        } else {
            result.setSuccess(reulst2.isSuccess());
            result.setMessage(reulst2.getMessage());
        }
        return result;
    }

    private ParaCompareOption getParaCompareOptionByParam(CreateParamDTO createParamDTO) {
        ParaCompareOption option = new ParaCompareOption();
        option.setFieldContainForm(createParamDTO.getSameTitle());
        option.setOverWriteAll(createParamDTO.getCoverParam());
        if (createParamDTO.getImportBaseParam() != null) {
            option.setUploadBaseParam(createParamDTO.getImportBaseParam());
        } else {
            log.info("\u7f3a\u5c11importBaseParam\u9009\u9879\u503c");
            option.setUploadBaseParam(true);
        }
        option.setUploadFormula(createParamDTO.getImportFormula());
        option.setUploadPrint(createParamDTO.getImportPrint());
        option.setUploadQuery(createParamDTO.getImportQuery());
        option.setUseFormLevel(createParamDTO.getRecognitionLevel());
        option.setCompareType(this.GetCompareType(createParamDTO));
        option.setFloatRegionCompareType(createParamDTO.getFloatRegionCompareType());
        if (createParamDTO.getMatchTask().booleanValue()) {
            DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(createParamDTO.getTaskKey());
            createParamDTO.setDataSchemeKey(designTaskDefine.getDataScheme());
            option.setCorpEntityId(designTaskDefine.getDw());
            option.setFormSchemeTitle(createParamDTO.getFormSchemeTitle());
            this.setDataSchemeInfo(createParamDTO.getDataSchemeKey(), option);
        } else if (createParamDTO.getCreateDataScheme().booleanValue()) {
            option.setCorpEntityId(createParamDTO.getMainDimension());
            option.setDateEntityId(createParamDTO.getPeriod());
            option.setDimEntityIds(createParamDTO.getDimensions());
            option.setDataPrefix(createParamDTO.getPrefix());
            option.setDataSchemeCode(createParamDTO.getCode());
            option.setDataSchemeTitle(createParamDTO.getTitle());
            option.setTaskCode(createParamDTO.getTaskCode());
            option.setTaskTitle(createParamDTO.getTaskTitle());
            option.setFromPeriod(createParamDTO.getFromPeriod());
            option.setToPeriod(createParamDTO.getToPeriod());
        } else {
            option.setCorpEntityId(createParamDTO.getMainDimension());
            this.setDataSchemeInfo(createParamDTO.getDataSchemeKey(), option);
        }
        option.setEnumCompareType(CompareContextType.CONTEXT_TITLE);
        return option;
    }

    private ParaCompareOption setDataSchemeInfo(String dataSchemeKey, ParaCompareOption option) {
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        option.setDataPrefix(dataScheme.getPrefix());
        option.setDataSchemeCode(dataScheme.getCode());
        option.setDataSchemeTitle(dataScheme.getTitle());
        if (StringUtils.isEmpty((String)option.getCorpEntityId())) {
            List unitDimension = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.UNIT);
            option.setCorpEntityId(((DesignDataDimension)unitDimension.get(0)).getDimKey());
        }
        List dimension = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        List collect = dimension.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
        option.setDimEntityIds(StringUtils.join((Object[])collect.toArray(), (String)";"));
        List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.PERIOD);
        if (!CollectionUtils.isEmpty(dataSchemeDimension)) {
            option.setDateEntityId(((DesignDataDimension)dataSchemeDimension.get(0)).getDimKey());
        }
        return option;
    }

    @Override
    public AsyncTaskInfo executeUpload(String importKey) throws Exception {
        String taskId = this.singleCompareController.ImportSingleToTask(importKey, null);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public void queryProcess(String processKey) {
    }

    @Override
    public Boolean singleAnalysis(SingleCompareDTO single) throws Exception {
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, JIOSINGLECOMPARE);
        ParaCompareOption option = new ParaCompareOption();
        if (single.getCompareDataType() == CompareDataType.DATA_ENUM) {
            option.setEnumCompareType(single.getCompareContextType());
        } else {
            option.setItemCompareType(single.getCompareContextType());
        }
        option.setUpdateType(single.getUpdateType());
        List<String> compareKeys = single.getCompareDataKeys();
        if (compareKeys.size() > 1) {
            this.taskFileCompareService.batchCompareSingleToTasKByType(single.getCompareDataType(), compareKeys, single.getCompareKey(), option, (AsyncTaskMonitor)monitor);
        } else if (!compareKeys.isEmpty()) {
            this.taskFileCompareService.compareSingleToTasKByType(single.getCompareDataType(), compareKeys.get(0), single.getCompareKey(), option, (AsyncTaskMonitor)monitor);
        } else {
            this.taskFileCompareService.compareSingleToTasKByType(single.getCompareDataType(), null, single.getCompareKey(), option, (AsyncTaskMonitor)monitor);
        }
        return true;
    }

    @Override
    public AsyncTaskInfo deleteCompareInfo(String importKey) throws Exception {
        String taskId = this.singleCompareController.batchDelete(importKey);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo deleteCompareInfos(List<String> importKeys) throws Exception {
        String taskId = this.singleCompareController.batchDeleteByKeys(importKeys);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public void floatRegionReCompare(FloatRegionCompareVO param) throws Exception {
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, JIOSINGLECOMPARE);
        ParaCompareOption option = new ParaCompareOption();
        HashMap<String, String> map = new HashMap<String, String>();
        if (param.getSingleFloatingId() != null) {
            map.put("singleFloatingId", param.getSingleFloatingId().toString());
        }
        map.put("newTableKey", param.getNewTableKey());
        option.setVariableMap(map);
        this.taskFileCompareService.compareSingleToTasKByType(CompareDataType.DATA_REGION, param.getCompareDataKey(), param.getInfoKey(), option, (AsyncTaskMonitor)monitor);
    }

    @Override
    public List<ZBMappingDTO> fixRegionReCompare(FixRegionCompareVO param) throws Exception {
        if (param == null || param.getUpdateFields() == null || param.getUpdateFields().size() == 0) {
            throw new Exception("\u53c2\u6570\u9519\u8bef");
        }
        ArrayList<ZBMappingDTO> list = new ArrayList<ZBMappingDTO>();
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, JIOSINGLECOMPARE);
        ParaCompareOption option = new ParaCompareOption();
        HashMap<String, String> map = new HashMap<String, String>();
        if (param.getSingleFloatingId() != null) {
            map.put("singleFloatingId", "-1");
        }
        map.put("newTableKey", param.getNewTableKey());
        StringBuilder sb = new StringBuilder();
        for (ZBMappingDTO zb : param.getUpdateFields()) {
            sb.append(zb.getSingleCode() + ",");
        }
        sb.delete(sb.length() - 1, sb.length());
        map.put("singleFieldCodes", sb.toString());
        option.setVariableMap(map);
        ParaCompareResult compareResult = this.taskFileCompareService.compareSingleToTasKByType(CompareDataType.DATA_REGION, param.getCompareDataKey(), param.getInfoKey(), option, (AsyncTaskMonitor)monitor);
        HashMap<String, CompareDataFieldDTO> oldFieldMap = new HashMap<String, CompareDataFieldDTO>();
        if (compareResult != null && !compareResult.getCompareRegions().isEmpty()) {
            ParaCompareRegionResult regionResult = compareResult.getCompareRegions().get(0);
            if (!regionResult.getAddFieldItems().isEmpty()) {
                for (CompareDataFieldDTO compareDataFieldDTO : regionResult.getAddFieldItems()) {
                    oldFieldMap.put(compareDataFieldDTO.getSingleCode(), compareDataFieldDTO);
                }
            }
            if (!regionResult.getUpdateItems().isEmpty()) {
                for (CompareDataFieldDTO compareDataFieldDTO : regionResult.getUpdateItems()) {
                    oldFieldMap.put(compareDataFieldDTO.getSingleCode(), compareDataFieldDTO);
                }
            }
        } else {
            CompareDataFieldDTO fieldQueryParam = new CompareDataFieldDTO();
            fieldQueryParam.setInfoKey(param.getInfoKey());
            fieldQueryParam.setDataType(CompareDataType.DATA_FIELD);
            fieldQueryParam.setFormCompareKey(param.getCompareDataKey());
            fieldQueryParam.setSingleFloatingId(-1);
            List<CompareDataFieldDTO> oldFieldList = this.fieldCompareService.list(fieldQueryParam);
            for (CompareDataFieldDTO oldField : oldFieldList) {
                oldFieldMap.put(oldField.getSingleCode(), oldField);
            }
        }
        for (ZBMappingDTO zb : param.getUpdateFields()) {
            CompareDataFieldDTO compareDataFieldDTO = (CompareDataFieldDTO)oldFieldMap.get(zb.getSingleCode());
            zb.setNetKey(compareDataFieldDTO.getNetKey());
            zb.setNetCode(compareDataFieldDTO.getNetCode());
            zb.setNetTitle(compareDataFieldDTO.getNetTitle());
            zb.setNetMatchTitle(compareDataFieldDTO.getNetMatchTitle());
            list.add(zb);
        }
        return list;
    }

    @Override
    public void doEnumCompare(String importKey, String enumPrefix) throws Exception {
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, JIOSINGLECOMPARE);
        ParaCompareOption option = new ParaCompareOption();
        option.setEnumPrefix(enumPrefix);
        option.setEnumCompareType(CompareContextType.CONTEXT_TITLE);
        ParaCompareResult compareResult = this.taskFileCompareService.compareSingleToTasKByType(CompareDataType.DATA_ENUM, null, importKey, option, (AsyncTaskMonitor)monitor);
    }

    @Override
    public void doPartEnumCompare(String importKey, String enumPrefix, List<String> compareDataKeys) throws Exception {
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, JIOSINGLECOMPARE);
        ParaCompareOption option = new ParaCompareOption();
        option.setEnumPrefix(enumPrefix);
        option.setEnumCompareType(CompareContextType.CONTEXT_TITLE);
        ParaCompareResult compareResult = this.taskFileCompareService.batchCompareSingleToTasKByType(CompareDataType.DATA_ENUM, compareDataKeys, importKey, option, (AsyncTaskMonitor)monitor);
    }

    @Override
    public List<FormSchemeVO> getFormSchemeByTaskYear(String taskKey, String taskYear, String singleTaskTitle) throws Exception {
        ArrayList<FormSchemeVO> result = new ArrayList<FormSchemeVO>();
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, JIOMATCHTASK);
        ParaCompareResult paraCompareResult = this.taskFileCompareService.findNetFormShemesByTaskAndYear(taskKey, taskYear, singleTaskTitle, (AsyncTaskMonitor)monitor);
        List<ParaCompareTaskInfo> mathchTasks = paraCompareResult.getMathchTasks();
        if (mathchTasks.size() == 0) {
            ParaCompareTaskInfo netTaskInfo = paraCompareResult.getNetTaskInfo();
            FormSchemeVO param = new FormSchemeVO();
            param.setKey(netTaskInfo.getFormSchemeKey());
            param.setTitle(netTaskInfo.getFormSchemeTitle());
            result.add(param);
            return result;
        }
        List<String> collect = mathchTasks.stream().map(ParaCompareTaskInfo::getFormSchemeKey).collect(Collectors.toList());
        List formSchemeDefines = this.designTimeViewController.queryFormSchemeDefines(collect.toArray(new String[0]));
        for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
            FormSchemeVO param = new FormSchemeVO();
            param.setKey(formSchemeDefine.getKey());
            param.setCode(formSchemeDefine.getFormSchemeCode());
            param.setTitle(formSchemeDefine.getTitle());
            result.add(param);
            try {
                List periodLinks = this.designTimeViewController.querySchemePeriodLinkByScheme(formSchemeDefine.getKey());
                String minPeriod = "";
                String maxPeriod = "";
                for (DesignSchemePeriodLinkDefine link : periodLinks) {
                    param.getPeriodList().add(link.getPeriodKey());
                    if (StringUtils.isEmpty((String)minPeriod) || minPeriod.compareTo(link.getPeriodKey()) > 0) {
                        minPeriod = link.getPeriodKey();
                    }
                    if (!StringUtils.isEmpty((String)maxPeriod) && maxPeriod.compareTo(link.getPeriodKey()) >= 0) continue;
                    maxPeriod = link.getPeriodKey();
                }
                param.setFormPeriod(minPeriod);
                param.setToPeriod(maxPeriod);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u751f\u6548\u65f6\u671f\u5f02\u5e38" + e.getMessage());
            }
        }
        return result;
    }

    private CompareParaType GetCompareType(CreateParamDTO createParamDTO) {
        if (StringUtils.isNotEmpty((String)createParamDTO.getTaskKey())) {
            if (StringUtils.isNotEmpty((String)createParamDTO.getFormSchemeKey())) {
                return CompareParaType.PARA_FORMSCHEME_HAS;
            }
            return CompareParaType.PARA_FORMSCHEME_NEW;
        }
        if (createParamDTO.getCreateDataScheme().booleanValue()) {
            return CompareParaType.DATA_DATASCHEME_NEW;
        }
        return CompareParaType.PARA_TASK_NEW;
    }

    @Override
    public List<ParamImportInfoDTO> listAllCompareInfos() throws Exception {
        CompareInfoDTO param = new CompareInfoDTO();
        List<CompareInfoDTO> list = this.compareInfoService.list(param);
        ArrayList<ParamImportInfoDTO> list2 = new ArrayList<ParamImportInfoDTO>();
        HashMap<String, DesignTaskDefine> taskMap = new HashMap<String, DesignTaskDefine>();
        HashMap<String, DesignFormSchemeDefine> formSchemeMap = new HashMap<String, DesignFormSchemeDefine>();
        for (CompareInfoDTO info : list) {
            ParamImportInfoDTO info2 = new ParamImportInfoDTO();
            info2.setKey(info.getKey());
            info2.setCode(info.getCode());
            info2.setTitle(info.getTitle());
            info2.setImportStatus(info.getStatus());
            info2.setUpdateTime(info.getUpdateTime());
            info2.setLogFileKey(info.getLogFileKey());
            info2.setMessage(info.getMessage());
            info2.setSingleTaskCode(info.getCode());
            info2.setSingleFileFlag(info.getFileFlag());
            info2.setSingleTaskTitle(info.getTaskTitle());
            info2.setSingleTaskYear(info.getTaskYear());
            info2.setNetTaskKey(info.getTaskKey());
            if (StringUtils.isNotEmpty((String)info.getTaskKey())) {
                if (taskMap.containsKey(info.getTaskKey())) {
                    info2.setNetTaskTitle(((DesignTaskDefine)taskMap.get(info.getTaskKey())).getTitle());
                } else {
                    DesignTaskDefine task = this.designTimeViewController.queryTaskDefine(info.getTaskKey());
                    if (task != null) {
                        info2.setNetTaskTitle(task.getTitle());
                        taskMap.put(task.getKey(), task);
                    }
                }
            }
            info2.setFormSchemeKey(info.getFormSchemeKey());
            if (StringUtils.isNotEmpty((String)info.getFormSchemeKey())) {
                if (formSchemeMap.containsKey(info.getFormSchemeKey())) {
                    info2.setFormSchemeTitle(((DesignFormSchemeDefine)formSchemeMap.get(info.getFormSchemeKey())).getTitle());
                    info2.setSingleTaskCode(((DesignFormSchemeDefine)formSchemeMap.get(info.getFormSchemeKey())).getTaskPrefix());
                } else {
                    DesignFormSchemeDefine formScheme = this.designTimeViewController.queryFormSchemeDefine(info.getFormSchemeKey());
                    if (formScheme != null) {
                        info2.setFormSchemeTitle(formScheme.getTitle());
                        info2.setSingleTaskCode(formScheme.getTaskPrefix());
                        formSchemeMap.put(formScheme.getKey(), formScheme);
                    }
                }
            }
            ParaCompareOption cmpoareOption = null;
            try {
                if (StringUtils.isNotEmpty((String)info.getOptionData())) {
                    ObjectMapper mapper = new ObjectMapper();
                    cmpoareOption = (ParaCompareOption)mapper.readValue(info.getOptionData(), ParaCompareOption.class);
                }
            }
            catch (Exception e) {
                log.info(e.getMessage());
            }
            if (cmpoareOption != null) {
                info2.setImportBaseParam(cmpoareOption.isUploadBaseParam());
                info2.setImportFormula(cmpoareOption.isUploadFormula());
                info2.setImportPrint(cmpoareOption.isUploadPrint());
                info2.setImportQuery(cmpoareOption.isUploadQuery());
                info2.setCoverParam(cmpoareOption.isOverWriteAll());
            }
            list2.add(info2);
        }
        return list2;
    }

    @Override
    public List<String> getSingleEnumCodeInFmdm(String importKey) throws SingleCompareException {
        return this.taskFileCompareService.getSingleEnumCodeInFmdm(importKey);
    }
}

