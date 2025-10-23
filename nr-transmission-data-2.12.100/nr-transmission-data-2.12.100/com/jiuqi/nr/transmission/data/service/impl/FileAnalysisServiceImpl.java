/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.mapping2.common.NrMappingUtil
 *  com.jiuqi.nr.mapping2.provider.NrMappingParam
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.common.FileHelper;
import com.jiuqi.nr.transmission.data.common.MappingType;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.dto.AnalysisDTO;
import com.jiuqi.nr.transmission.data.dto.AnalysisParam;
import com.jiuqi.nr.transmission.data.dto.SrcParam.SrcParamDefinitionObj;
import com.jiuqi.nr.transmission.data.dto.SrcParamDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.exception.DataImportException;
import com.jiuqi.nr.transmission.data.intf.AnalysisVO;
import com.jiuqi.nr.transmission.data.service.IFileAnalysisService;
import com.jiuqi.nr.transmission.data.vo.MappingSchemeVO;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FileAnalysisServiceImpl
implements IFileAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(FileAnalysisServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IMappingSchemeService mappingSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private List<ITransmissionDataGather> gatherList;

    @Override
    public AnalysisDTO analysisParam(Map<String, ZipUtils.ZipSubFile> zipFiles, AnalysisParam analysisParam) throws Exception {
        FileHelper.checkFile(zipFiles);
        AnalysisDTO schemeParam = FileHelper.getSchemeParam(zipFiles, analysisParam);
        SyncSchemeParamDTO param = schemeParam.getSyncSchemeParamDTO();
        if (schemeParam.getMappingType().getValue() > 0 && schemeParam.getIsNrd() && schemeParam.getSrcParamDTO() != null) {
            SrcParamDTO srcParamDTO = schemeParam.getSrcParamDTO();
            List<SrcParamDefinitionObj> formDefinitions = srcParamDTO.getFormDefinitions();
            this.checkTaskAndFormScheme(schemeParam, analysisParam);
            Map<String, FormDefine> formCodeToDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(param.getFormSchemeKey()).stream().collect(Collectors.toMap(FormDefine::getFormCode, a -> a));
            ArrayList<String> srcFormKeys = new ArrayList<String>(Arrays.asList(param.getForm().split(";")));
            ArrayList<String> desFormKeys = new ArrayList<String>();
            Map<String, SrcParamDefinitionObj> srcFormKeyToDefine = formDefinitions.stream().collect(Collectors.toMap(SrcParamDefinitionObj::getKey, a -> a));
            for (String srcFormKey : srcFormKeys) {
                String code = srcFormKeyToDefine.get(srcFormKey).getCode();
                FormDefine formDefine = formCodeToDefines.get(code);
                if (formDefine == null) continue;
                desFormKeys.add(formDefine.getKey());
                if (formDefine.getFormType() != FormType.FORM_TYPE_FMDM && formDefine.getFormType() != FormType.FORM_TYPE_NEWFMDM) continue;
                param.setFmdmForm(formDefine.getKey());
            }
            param.setForm(String.join((CharSequence)";", desFormKeys));
        } else if (schemeParam.getMappingType().getValue() == -1 && schemeParam.getIsNrd() && schemeParam.getSrcParamDTO() != null) {
            this.checkTaskAndFormScheme(schemeParam, analysisParam);
        } else {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(param.getPeriodValue(), param.getTask());
            if (schemePeriodLinkDefine != null) {
                param.setFormSchemeKey(schemePeriodLinkDefine.getSchemeKey());
            } else {
                throw new RuntimeException("\u4efb\u52a1\u548c\u65f6\u671f\u67e5\u8be2\u4e0d\u5230\u5173\u8054\u7684\u62a5\u8868\u65b9\u6848");
            }
        }
        if (schemeParam.getMappingType().getValue() == -1 && schemeParam.getIsNrd() && schemeParam.getSrcParamDTO() != null) {
            List allSchemes = this.mappingSchemeService.getAllSchemes();
            ArrayList<MappingSchemeVO> allThisFormSchemeMappings = new ArrayList<MappingSchemeVO>();
            for (MappingScheme allScheme : allSchemes) {
                NrMappingParam nrMappingParam = NrMappingUtil.getNrMappingParam((MappingScheme)allScheme);
                if (nrMappingParam == null || !StringUtils.hasText(nrMappingParam.getTaskKey()) || !StringUtils.hasText(nrMappingParam.getFormSchemeKey()) || !nrMappingParam.getTaskKey().equals(param.getTask()) || !nrMappingParam.getFormSchemeKey().equals(param.getFormSchemeKey())) continue;
                MappingSchemeVO mappingSchemeVO = new MappingSchemeVO(allScheme.getKey(), allScheme.getTitle(), nrMappingParam.getFormSchemeKey());
                allThisFormSchemeMappings.add(mappingSchemeVO);
            }
            schemeParam.setMappingSchemes(allThisFormSchemeMappings);
        }
        return schemeParam;
    }

    private void checkTaskAndFormScheme(AnalysisDTO schemeParam, AnalysisParam analysisParam) throws Exception {
        SyncSchemeParamDTO param = schemeParam.getSyncSchemeParamDTO();
        if (StringUtils.hasLength(analysisParam.getTaskKey()) && StringUtils.hasLength(analysisParam.getFormSchemeKey())) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(analysisParam.getTaskKey());
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(analysisParam.getFormSchemeKey());
            if (taskDefine == null || formScheme == null || !formScheme.getTaskKey().equals(analysisParam.getTaskKey())) {
                String errorMessage = String.format("\u591a\u7ea7\u90e8\u7f72\u5bfc\u5165\uff0c\u6307\u5b9a\u7684\u4efb\u52a1\uff1a%s \u548c\u62a5\u8868\u65b9\u6848: %s \u6821\u9a8c\u5931\u8d25\uff0c\u540c\u6b65\u7ed3\u675f", analysisParam.getTaskKey(), analysisParam.getFormSchemeKey());
                logger.error(errorMessage);
                throw new Exception(errorMessage);
            }
            param.setTask(analysisParam.getTaskKey());
            param.setFormSchemeKey(analysisParam.getFormSchemeKey());
        } else {
            SrcParamDTO srcParamDTO = schemeParam.getSrcParamDTO();
            SrcParamDefinitionObj taskDefinition = srcParamDTO.getTaskDefinition();
            TaskDefine taskDefine = null;
            taskDefine = this.runTimeViewController.queryTaskDefineByCode(taskDefinition.getCode());
            if (taskDefine == null) {
                List allTaskDefinesByType = this.runTimeViewController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
                for (TaskDefine define : allTaskDefinesByType) {
                    if (!define.getTitle().equals(taskDefinition.getTitle())) continue;
                    taskDefine = define;
                    break;
                }
            }
            if (taskDefine == null) {
                logger.error(String.format("\u5bfc\u5165\u6587\u4ef6\u4e2d\u7684\u4efb\u52a1: %s \u5728\u5f53\u524d\u7cfb\u7edf\u4e2d\u6839\u636ecode\u548ctitle\u5339\u914d\u4e0d\u5b58\u5728\uff0c\u540c\u6b65\u7ed3\u675f", taskDefinition.getTitle()));
                throw new Exception(MultilingualLog.analysisParamMessage(1, taskDefinition.getTitle(), "", "", ""));
            }
            param.setTask(taskDefine.getKey());
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(param.getPeriodValue(), param.getTask());
            param.setFormSchemeKey(schemePeriodLinkDefine.getSchemeKey());
        }
    }

    @Override
    public AnalysisVO analysisParam(AnalysisVO analysisVo) throws Exception {
        boolean newFile;
        AnalysisVO result = new AnalysisVO();
        String fileKey = analysisVo.getFileKey();
        if (!StringUtils.hasLength(fileKey)) {
            return null;
        }
        String tempDir = ZipUtils.newTempDir();
        String path = tempDir + "/" + "data.temp";
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                Utils.fileDelete(fileKey);
                e.printStackTrace();
            }
        }
        if (newFile = file.createNewFile()) {
            try (OutputStream fos = Files.newOutputStream(file.toPath(), new OpenOption[0]);
                 InputStream newIs = Files.newInputStream(file.toPath(), new OpenOption[0]);){
                boolean showWorkFlow;
                AnalysisDTO analysisDTO;
                Utils.fileDownLoad(fileKey, fos);
                Map<String, ZipUtils.ZipSubFile> zipFiles = ZipUtils.unZip((InputStream)newIs).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFileName, f -> f));
                AnalysisParam analysisParam = AnalysisParam.getAnalysisParam(null, MappingType.ANALYSIS_MAPPING);
                try {
                    analysisDTO = this.analysisParam(zipFiles, analysisParam);
                }
                catch (Exception e) {
                    Utils.deleteAllFilesOfDir(file);
                    Utils.fileDelete(fileKey);
                    logger.error(e.getMessage(), e);
                    throw new DataImportException(MultilingualLog.checkFlowTypeMessage(3, "") + e.getMessage(), e);
                }
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(analysisDTO.getSyncSchemeParamDTO().getFormSchemeKey());
                if (formScheme == null) {
                    throw new DataImportException(MultilingualLog.checkFlowTypeMessage(4, ""));
                }
                FlowsType flowsType = formScheme.getFlowsSetting().getFlowsType();
                result.setMappingSchemes(analysisDTO.getMappingSchemes());
                boolean bl = showWorkFlow = !FlowsType.NOSTARTUP.equals((Object)flowsType);
                if (analysisDTO.getFormDataZipMap().get("WORKFLOW_TRANSMISSION_DATA.zip") != null) {
                    showWorkFlow = false;
                }
                result.setFlowType(showWorkFlow);
                result.setFileKey(fileKey);
                this.setMessage(result, analysisDTO.getSyncSchemeParamDTO());
                Utils.deleteAllFilesOfDir(file);
            }
            catch (Exception e) {
                logger.error("\u591a\u7ea7\u90e8\u7f72\u79bb\u7ebf\u88c5\u5165\u89e3\u6790\u6587\u4ef6\u65f6\u4ece\u65e5\u5fd7\u670d\u52a1\u5668\u4e0b\u8f7d\u6587\u4ef6\u51fa\u9519" + e.getMessage(), e);
                Utils.fileDelete(fileKey);
                Utils.deleteAllFilesOfDir(file);
                throw new DataImportException(MultilingualLog.checkFlowTypeMessage(2, "") + e.getMessage(), e);
            }
        }
        return result;
    }

    private void setMessage(AnalysisVO result, SyncSchemeParamDTO syncSchemeParamDTO) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(syncSchemeParamDTO.getTask());
        result.setTaskKey(taskDefine.getKey());
        result.setTaskTitle(taskDefine.getTitle());
        result.setTaskDw(taskDefine.getDw());
        IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        if (iEntityDefine != null) {
            result.setTaskDwTitle(iEntityDefine.getTitle());
        }
        result.setFormSchemeKey(syncSchemeParamDTO.getFormSchemeKey());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(syncSchemeParamDTO.getFormSchemeKey());
        result.setFormSchemeTitle(formScheme.getTitle());
        result.setPeriodValue(syncSchemeParamDTO.getPeriodValue());
        int period = syncSchemeParamDTO.getPeriod();
        if (period == 0) {
            result.setPeriodType("\u5f53\u524d\u671f");
        } else if (period == 1) {
            result.setPeriodType("\u4e0b\u4e00\u671f");
        } else if (period == 2) {
            result.setPeriodType("\u6307\u5b9a\u671f");
        } else if (period == -1) {
            result.setPeriodType("\u4e0a\u4e00\u671f");
        }
        String dateTime = taskDefine.getDateTime();
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(dateTime);
        List periodItems = periodProvider.getPeriodItems();
        for (IPeriodRow periodItem : periodItems) {
            if (!periodItem.getCode().equals(syncSchemeParamDTO.getPeriodValue())) continue;
            result.setPeriodTitle(periodItem.getTitle());
        }
        if (StringUtils.hasText(syncSchemeParamDTO.getAdjustPeriod())) {
            result.setAdjustPeriod(syncSchemeParamDTO.getAdjustPeriod());
            AdjustPeriod adjustPeriod = this.adjustPeriodService.queryAdjustPeriods(syncSchemeParamDTO.getFormSchemeKey(), syncSchemeParamDTO.getPeriodValue(), syncSchemeParamDTO.getAdjustPeriod());
            if (adjustPeriod != null) {
                result.setAdjustTitle(adjustPeriod.getTitle());
            }
        }
        result.setEntitys(Arrays.asList(syncSchemeParamDTO.getEntity().split(";")));
        result.setForms(Arrays.asList(syncSchemeParamDTO.getForm().split(";")));
        List reportDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        List entityDefines = reportDimension.stream().filter(a -> !"ADJUST".equals(a.getDimKey())).map(a -> this.entityMetaService.queryEntity(a.getDimKey())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(entityDefines)) {
            HashMap<String, List<String>> dims = new HashMap<String, List<String>>(entityDefines.size());
            HashMap<String, String> dimTitle = new HashMap<String, String>(entityDefines.size());
            List<String> srcSelectDimCodes = Arrays.asList(syncSchemeParamDTO.getDimKeys().split(";"));
            List<String> srcSelectDimValues = Arrays.asList(syncSchemeParamDTO.getDimValues().split(";"));
            for (IEntityDefine entityDefine : entityDefines) {
                if (entityDefine == null) continue;
                dimTitle.put(entityDefine.getId(), entityDefine.getTitle());
                if (srcSelectDimCodes.contains(entityDefine.getCode())) {
                    int i = srcSelectDimCodes.indexOf(entityDefine.getCode());
                    String dimValue = srcSelectDimValues.get(i);
                    List<Object> dimValues = new ArrayList();
                    if (StringUtils.hasLength(dimValue)) {
                        dimValues = Arrays.asList(dimValue.split(","));
                    }
                    dims.put(entityDefine.getId(), dimValues);
                    continue;
                }
                dims.put(entityDefine.getId(), new ArrayList());
            }
            result.setDims(dims);
            result.setDimTitle(dimTitle);
        }
        if (!CollectionUtils.isEmpty(syncSchemeParamDTO.getExportData())) {
            Map<String, String> exportDataCodeToTitle = this.gatherList.stream().collect(Collectors.toMap(ITransmissionDataGather::getCode, ITransmissionDataGather::getTitle, (k1, k2) -> k1));
            ArrayList<String> exportDataTitleList = new ArrayList<String>();
            for (String exportData : syncSchemeParamDTO.getExportData()) {
                String exportDataTitle = exportDataCodeToTitle.get(exportData);
                if (!StringUtils.hasLength(exportDataTitle)) continue;
                exportDataTitleList.add(exportDataTitle);
            }
            result.setDataMessages(exportDataTitleList);
        }
    }
}

