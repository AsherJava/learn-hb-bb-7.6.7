/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.csvreader.CsvWriter
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.option.internal.UnitEdit
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.BatchFMDMDTO
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.domain.FMDMUpdateResult
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.option.internal.UnitEdit;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.domain.FMDMUpdateResult;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.common.FileHelper;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.intf.ContextExpendParam;
import com.jiuqi.nr.transmission.data.intf.DataImportMessage;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.intf.MappingImportParam;
import com.jiuqi.nr.transmission.data.intf.MappingParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionZBMapping;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FMDMData
implements ITransmissionDataGather {
    private static final Logger logger = LoggerFactory.getLogger(FMDMData.class);
    @Value(value="${jiuqi.nr.transmission.FMDM.entity.not.import:false}")
    private boolean notImportFMDMEntity;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFMDMAttributeService runTimeFMDMAttributeServiceImpl;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IReportParamService reportParamService;
    @Autowired(required=false)
    private MultistageUnitReplace multistageUnitReplaceImpl;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;
    private static final String EXIT_UNIT = "1";
    private static final String FMDM_ORGCODE = "ORGCODE";

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public String getCode() {
        return "FMDM_DATA";
    }

    @Override
    public String getTitle() {
        return "\u5c01\u9762\u4ee3\u7801";
    }

    @Override
    public DataImportResult dataImport(InputStream inputStream, ITransmissionContext context) throws Exception {
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        ILogHelper logHelper = context.getLogHelper();
        DataImportResult importBusinessDataResult = new DataImportResult();
        ContextExpendParam contextExpendParam = context.getContextExpendParam();
        if (!StringUtils.hasLength(context.getFmdmForm())) {
            logHelper.appendLog(MultilingualLog.fmdmDataImportMessage(1, ""));
            importBusinessDataResult.setLog("\u6ca1\u6709\u5c01\u9762\u62a5\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165");
            if (contextExpendParam.getNoExistUnit().size() > 0) {
                context.getDataImportResult().setSyncErrorNumInc();
            }
            monitor.finish("\u6ca1\u6709\u5c01\u9762\u4ee3\u7801\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165", (Object)"\u6ca1\u6709\u5c01\u9762\u4ee3\u7801\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165");
            return importBusinessDataResult;
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u5f00\u59cb\u88c5\u5165\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e");
        IExecuteParam executeParam = context.getExecuteParam();
        DataImportResult dataImportResult = context.getDataImportResult();
        DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
        String dimensionName = contextExpendParam.getDimensionName();
        HashMap<String, List<String>> uploadUnitToAdjustForFmdm = new HashMap<String, List<String>>();
        this.getUploadUnitToAdjustForFmdm(contextExpendParam.getNotNeedImportFormMaps().get(context.getFmdmForm()), executeParam.getAdjustPeriod(), uploadUnitToAdjustForFmdm, dimensionName);
        Map<String, ZipUtils.ZipSubFile> zipFiles = ZipUtils.unZip((InputStream)inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFilePath, f -> f));
        String exitUnitValue = this.taskOptionController.getValue(executeParam.getTaskKey(), ((UnitEdit)SpringBeanUtils.getBean(UnitEdit.class)).getKey());
        ZipUtils.ZipSubFile subFile = zipFiles.get(this.getFilePathInZip());
        String tempDir = ZipUtils.newTempDir();
        if (subFile != null) {
            Object thisUnitFMDMDatas;
            File tempFile = FileHelper.getTempFile(subFile, tempDir);
            CsvReader reader = new CsvReader(tempFile.getAbsolutePath(), new Character(",".toCharArray()[0]).charValue(), Charset.forName("UTF-8"));
            ArrayList<String> srcFieldDataCodes = new ArrayList<String>();
            ArrayList<List<String>> allDatas = new ArrayList<List<String>>();
            HashMap<String, String> fmdmFieldMap = new HashMap<String, String>();
            int firstDimIndex = this.readData(reader, srcFieldDataCodes, allDatas, context, uploadUnitToAdjustForFmdm, fmdmFieldMap);
            int fmdmCodeIndex = srcFieldDataCodes.indexOf("FMDMCODE");
            int fmdmUnitCodeIndex = srcFieldDataCodes.indexOf("FMDMUNITCODE");
            int adjustIndex = srcFieldDataCodes.indexOf("ADJUSTVALUE");
            if (firstDimIndex == -1) {
                firstDimIndex = fmdmCodeIndex;
            }
            ArrayList<String> existFieldCodes = new ArrayList<String>();
            ArrayList<String> notExistFieldCodes = new ArrayList<String>();
            HashSet systemFields = this.getexistFieldAndType(executeParam.getFormSchemeKey(), existFieldCodes, notExistFieldCodes, firstDimIndex, srcFieldDataCodes, fmdmFieldMap);
            HashMap<String, List<FMDMDataDTO>> unitTofmdmDataUploadMap = new HashMap<String, List<FMDMDataDTO>>();
            HashMap<String, List<FMDMDataDTO>> unitTofmdmDataAddMap = new HashMap<String, List<FMDMDataDTO>>();
            List<String> noExistUnit = contextExpendParam.getNoExistUnit();
            String codeFieldTitle = this.getCodeFieldTitle(executeParam.getFormSchemeKey());
            DimensionValueSet dimensionValueSetForPeriod = new DimensionValueSet();
            dimensionValueSetForPeriod.setValue("DATATIME", dimensionValueSet.getValue("DATATIME"));
            IEntityTable entityList = this.reportParamService.getEntityList(dimensionValueSetForPeriod, executeParam.getFormSchemeKey());
            if (entityList == null && fmdmUnitCodeIndex == -1) {
                throw new Exception(MultilingualLog.fmdmDataImportMessage(10, ""));
            }
            Map<String, Integer> unitOrder = this.setFmdmData(allDatas, unitTofmdmDataAddMap, unitTofmdmDataUploadMap, firstDimIndex, fmdmUnitCodeIndex, fmdmCodeIndex, adjustIndex, entityList, codeFieldTitle, existFieldCodes, srcFieldDataCodes, fmdmFieldMap, context, this.notImportFMDMEntity || contextExpendParam.getNotImportFMDMEntity() ? systemFields : new HashSet());
            HashSet<String> addKeys = new HashSet<String>();
            StringBuilder insertErrorUnitMessage = null;
            if (unitTofmdmDataAddMap.size() > 0 && EXIT_UNIT.equals(exitUnitValue)) {
                FMDMUpdateResult addResult = new FMDMUpdateResult();
                int maxAddUnitFMDMDataNum = 0;
                for (List value : unitTofmdmDataAddMap.values()) {
                    if (CollectionUtils.isEmpty(value) || value.size() <= maxAddUnitFMDMDataNum) continue;
                    maxAddUnitFMDMDataNum = value.size();
                }
                for (int i = 0; i < maxAddUnitFMDMDataNum; ++i) {
                    ArrayList newFmdmDataAddList = new ArrayList();
                    for (Map.Entry unitListEntry : unitTofmdmDataAddMap.entrySet()) {
                        thisUnitFMDMDatas = (List)unitListEntry.getValue();
                        String unit = (String)unitListEntry.getKey();
                        if (thisUnitFMDMDatas.size() <= i) continue;
                        if (!addKeys.contains(unit)) {
                            newFmdmDataAddList.add(thisUnitFMDMDatas.get(i));
                            continue;
                        }
                        if (!CollectionUtils.isEmpty((Collection)unitTofmdmDataUploadMap.get(unit))) continue;
                        unitTofmdmDataUploadMap.computeIfAbsent(unit, key -> new ArrayList()).addAll(thisUnitFMDMDatas.subList(i, thisUnitFMDMDatas.size()));
                    }
                    if (newFmdmDataAddList.size() <= 0) continue;
                    BatchFMDMDTO batchAddFMDMDto = new BatchFMDMDTO();
                    newFmdmDataAddList.sort(Comparator.comparing(a -> (Integer)unitOrder.get(a.getValue(codeFieldTitle).getAsString())));
                    batchAddFMDMDto.setFmdmList(newFmdmDataAddList);
                    batchAddFMDMDto.setFormSchemeKey(executeParam.getFormSchemeKey());
                    batchAddFMDMDto.setDimensionValueSet(dimensionValueSet);
                    try {
                        addResult = this.fmdmDataService.batchAddFMDM(batchAddFMDMDto);
                    }
                    catch (Exception e) {
                        logger.error("\u591a\u7ea7\u90e8\u7f72\u5c01\u9762\u4ee3\u7801\u8868\u5355\u4f4d\u65b0\u589e\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage());
                    }
                    addKeys.addAll(addResult.getUpdateKeys());
                }
                this.uploadAfterAddFmdmData(addKeys, context);
                if (noExistUnit.size() != addKeys.size()) {
                    ArrayList<String> insertErrorUnit = new ArrayList<String>(noExistUnit);
                    insertErrorUnit.removeAll(addKeys);
                    dataImportResult.setSyncErrorNumInc();
                    Map<String, List<DataImportMessage>> failUnits = dataImportResult.getFailUnits();
                    String noName = MultilingualLog.fmdmDataImportMessage(8, "");
                    String unitInsertErrorMessage = MultilingualLog.fmdmDataImportMessage(9, "");
                    insertErrorUnitMessage = new StringBuilder();
                    insertErrorUnitMessage.append("\u5c01\u9762\u4ee3\u7801\u65b0\u589e\u5931\u8d25\u7684\u5355\u4f4d\u6709\uff1a");
                    for (String a2 : insertErrorUnit) {
                        insertErrorUnitMessage.append(a2).append("\u3001");
                        failUnits.computeIfAbsent(a2, key -> new ArrayList()).add(new DataImportMessage(noName, a2, unitInsertErrorMessage));
                    }
                    insertErrorUnitMessage.append("\r\n");
                    insertErrorUnit.forEach(a -> failUnits.computeIfAbsent((String)a, key -> new ArrayList()).add(new DataImportMessage(noName, (String)a, unitInsertErrorMessage)));
                }
            } else if (unitTofmdmDataAddMap.size() > 0 && !EXIT_UNIT.equals(exitUnitValue)) {
                Utils.allowEditingUnitError(dataImportResult, logHelper, noExistUnit);
                dataImportResult.setSyncErrorNumInc();
            }
            if ((contextExpendParam.isAddUnitUpload() || contextExpendParam.getUnits().size() == 0) && addKeys.size() == 0) {
                monitor.finish("\u5c01\u9762\u4ee3\u7801\u8868\u65b0\u589e\u5355\u4f4d\u5931\u8d25\uff0c\u6ca1\u6709\u53ef\u4ee5\u8fdb\u884c\u6570\u636e\u88c5\u5165\u7684\u5355\u4f4d\uff0c\u6570\u636e\u88c5\u5165\u63d0\u524d\u7ed3\u675f\uff01", (Object)"\u5c01\u9762\u4ee3\u7801\u8868\u65b0\u589e\u5355\u4f4d\u5931\u8d25\uff0c\u6ca1\u6709\u53ef\u4ee5\u8fdb\u884c\u6570\u636e\u88c5\u5165\u7684\u5355\u4f4d\uff0c\u6570\u636e\u88c5\u5165\u63d0\u524d\u7ed3\u675f\uff01");
                throw new Exception(MultilingualLog.fmdmDataImportMessage(2, ""));
            }
            if (unitTofmdmDataUploadMap.size() > 0) {
                int maxUpdateUnitFMDMDataNum = 0;
                for (List value : unitTofmdmDataUploadMap.values()) {
                    if (CollectionUtils.isEmpty(value) || value.size() <= maxUpdateUnitFMDMDataNum) continue;
                    maxUpdateUnitFMDMDataNum = value.size();
                }
                HashSet updateKeys = new HashSet();
                for (int i = 0; i < maxUpdateUnitFMDMDataNum; ++i) {
                    ArrayList newFmdmDataUpdateList = new ArrayList();
                    for (Map.Entry unitListEntry : unitTofmdmDataUploadMap.entrySet()) {
                        thisUnitFMDMDatas = (List)unitListEntry.getValue();
                        if (thisUnitFMDMDatas.size() <= i) continue;
                        newFmdmDataUpdateList.add(thisUnitFMDMDatas.get(i));
                    }
                    if (newFmdmDataUpdateList.size() <= 0) continue;
                    BatchFMDMDTO batchFMDMDto = new BatchFMDMDTO();
                    batchFMDMDto.setFmdmList(newFmdmDataUpdateList);
                    batchFMDMDto.setFormSchemeKey(executeParam.getFormSchemeKey());
                    batchFMDMDto.setDimensionValueSet(dimensionValueSet);
                    FMDMUpdateResult updateResult = new FMDMUpdateResult();
                    try {
                        updateResult = this.fmdmDataService.batchUpdateFMDM(batchFMDMDto);
                    }
                    catch (Exception e) {
                        logger.error("\u591a\u7ea7\u90e8\u7f72\u5c01\u9762\u4ee3\u7801\u8868\u5355\u4f4d\u66f4\u65b0\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage());
                    }
                    updateKeys.addAll(updateResult.getUpdateKeys());
                }
            }
            StringBuilder importMessage = new StringBuilder();
            if (notExistFieldCodes.size() > 0) {
                StringBuilder notExistFieldMessage = new StringBuilder();
                for (String notExistFieldCode : notExistFieldCodes) {
                    notExistFieldMessage.append(notExistFieldCode).append("\u3001");
                }
                notExistFieldMessage.replace(notExistFieldMessage.length() - 1, notExistFieldMessage.length(), "\uff01");
                importMessage.append(MultilingualLog.fmdmDataImportMessage(3, notExistFieldMessage.toString()));
            }
            if (addKeys.size() > 0) {
                StringBuilder notExistUnitMessage = new StringBuilder();
                for (String notExistCode : addKeys) {
                    notExistUnitMessage.append(notExistCode).append("\u3001");
                }
                notExistUnitMessage.replace(notExistUnitMessage.length() - 1, notExistUnitMessage.length(), "\uff01");
                importMessage.append(MultilingualLog.fmdmDataImportMessage(4, notExistUnitMessage.toString()));
            }
            importMessage.append(MultilingualLog.fmdmDataImportMessage(5, ""));
            logHelper.appendLog(importMessage.toString());
            if (insertErrorUnitMessage != null) {
                logHelper.appendLog(insertErrorUnitMessage.toString());
            }
            logger.info(importMessage.toString());
        } else {
            String fileEmpytMessage = MultilingualLog.fmdmDataImportMessage(6, "");
            logHelper.appendLog(fileEmpytMessage);
            if (contextExpendParam.getNoExistUnit().size() > 0) {
                dataImportResult.setSyncErrorNumInc();
            }
            logger.info(fileEmpytMessage);
        }
        monitor.finish("\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e\u5bfc\u5165\u6210\u529f\uff01", (Object)"\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e\u5bfc\u5165\u6210\u529f\uff01");
        Utils.addSyncResult(dataImportResult, importBusinessDataResult);
        Utils.deleteAllFilesOfDirByPath(tempDir);
        return null;
    }

    private void uploadAfterAddFmdmData(Set<String> addKeys, ITransmissionContext context) {
        if (!CollectionUtils.isEmpty(addKeys)) {
            IExecuteParam executeParam = context.getExecuteParam();
            ContextExpendParam contextExpendParam = context.getContextExpendParam();
            DataImportResult dataImportResult = context.getDataImportResult();
            DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
            String dimensionName = contextExpendParam.getDimensionName();
            Object unit = dimensionValueSet.getValue(dimensionName);
            ArrayList<String> entitys = new ArrayList<String>((List)unit);
            entitys.addAll(addKeys);
            dimensionValueSet.setValue(dimensionName, entitys);
            DimensionValueSet notExistDimensionValueSet = new DimensionValueSet(dimensionValueSet);
            notExistDimensionValueSet.setValue(dimensionName, new ArrayList<String>(addKeys));
            List<EntityInfoParam> entityInfoParams = this.reportParamService.getEntityList(notExistDimensionValueSet, executeParam.getFormSchemeKey(), null, true);
            String successInsertMessage = MultilingualLog.fmdmDataImportMessage(7, "");
            entityInfoParams.forEach(a -> dataImportResult.addSuccessEntity(a.getTitle(), a.getEntityKeyData(), successInsertMessage));
            Map<String, EntityInfoParam> notExistUnits = entityInfoParams.stream().collect(Collectors.toMap(EntityInfoParam::getEntityKeyData, a -> a, (k1, k2) -> k1));
            contextExpendParam.getUnits().putAll(notExistUnits);
        }
    }

    private Map<String, Integer> setFmdmData(List<List<String>> allDatas, Map<String, List<FMDMDataDTO>> unitTofmdmDataAddMap, Map<String, List<FMDMDataDTO>> unitTofmdmDataUploadMap, int firstDimIndex, int fmdmUnitCodeIndex, int fmdmCodeIndex, int adjustIndex, IEntityTable entityList, String codeFieldTitle, List<String> existFieldCodes, List<String> srcFieldDataCodes, Map<String, String> fmdmFieldMap, ITransmissionContext context, Set<String> systemFields) {
        IExecuteParam executeParam = context.getExecuteParam();
        HashMap<String, Integer> orderResult = new HashMap<String, Integer>();
        if (systemFields == null) {
            systemFields = new HashSet<String>();
        }
        List<String> noExistUnit = context.getContextExpendParam().getNoExistUnit();
        for (int i = 0; i < allDatas.size(); ++i) {
            FMDMDataDTO fmdmDataDtoForUnit = new FMDMDataDTO();
            fmdmDataDtoForUnit.setFormSchemeKey(executeParam.getFormSchemeKey());
            List<String> listData = allDatas.get(i);
            String unit = this.getUnit(listData, fmdmUnitCodeIndex, fmdmCodeIndex, entityList, context);
            for (int j = 0; j < firstDimIndex; ++j) {
                String fieldCode = existFieldCodes.get(j);
                if (!StringUtils.hasText(fieldCode) || systemFields.contains(fieldCode)) continue;
                fmdmDataDtoForUnit.setValue(fieldCode, (Object)listData.get(j));
            }
            fmdmDataDtoForUnit.setValue(codeFieldTitle, (Object)unit, DataLinkType.DATA_LINK_TYPE_FMDM);
            orderResult.put(unit, i);
            String adjustValue = adjustIndex != -1 ? listData.get(adjustIndex) : "NO_ADJUST";
            DimensionValueSet dimensionValueSetForUnit = this.setDimensionValueSetForFMDMDataDTO(executeParam.getDimensionValueSet().getValue("DATATIME").toString(), firstDimIndex, fmdmCodeIndex, adjustIndex, srcFieldDataCodes, fmdmFieldMap, listData, context, unit);
            fmdmDataDtoForUnit.setDimensionValueSet(dimensionValueSetForUnit);
            if (noExistUnit.contains(unit)) {
                unitTofmdmDataAddMap.computeIfAbsent(unit, key -> new ArrayList()).add(fmdmDataDtoForUnit);
                continue;
            }
            unitTofmdmDataUploadMap.computeIfAbsent(unit, key -> new ArrayList()).add(fmdmDataDtoForUnit);
        }
        return orderResult;
    }

    private void getUploadUnitToAdjustForFmdm(List<DimensionValueSet> dimensionValueSets, List<String> adjustPeriod, Map<String, List<String>> uploadUnitToAdjustForFmdm, String dimensionName) {
        block4: {
            if (CollectionUtils.isEmpty(dimensionValueSets)) break block4;
            if (adjustPeriod.size() > 0) {
                for (DimensionValueSet notImportValueSet : dimensionValueSets) {
                    uploadUnitToAdjustForFmdm.computeIfAbsent(notImportValueSet.getValue(dimensionName).toString(), key -> new ArrayList()).add(notImportValueSet.getValue("ADJUST").toString());
                }
            } else {
                for (DimensionValueSet notImportValueSet : dimensionValueSets) {
                    uploadUnitToAdjustForFmdm.computeIfAbsent(notImportValueSet.getValue(dimensionName).toString(), key -> new ArrayList()).add("NO_ADJUST");
                }
            }
        }
    }

    private Set<String> getexistFieldAndType(String formSchemeKey, List<String> existFieldCodes, List<String> notExistFieldCodes, int firstDimIndex, List<String> srcFieldDataCodes, Map<String, String> fmdmFieldMap) {
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
        List ifmdmAttributes = this.runTimeFMDMAttributeServiceImpl.listShowAttribute(fmdmAttributeDTO);
        Set<String> systemFields = ifmdmAttributes.stream().filter(t -> t.getKind() == ColumnModelKind.SYSTEM).map(IModelDefineItem::getCode).collect(Collectors.toSet());
        List allfieldDataCodes = ifmdmAttributes.stream().filter(t -> t.getColumnType() != ColumnModelType.ATTACHMENT).map(IModelDefineItem::getCode).collect(Collectors.toList());
        for (int i = 0; i < firstDimIndex; ++i) {
            String srcFieldDataCode = srcFieldDataCodes.get(i);
            if (allfieldDataCodes.contains(srcFieldDataCode = fmdmFieldMap.get(srcFieldDataCode))) {
                existFieldCodes.add(srcFieldDataCode);
                continue;
            }
            existFieldCodes.add("");
            notExistFieldCodes.add(srcFieldDataCode);
        }
        return systemFields;
    }

    private DimensionValueSet setDimensionValueSetForFMDMDataDTO(String period, int firstDimIndex, int fmdmCodeIndex, int adjustIndex, List<String> srcFieldDataCodes, Map<String, String> fmdmFieldMap, List<String> listData, ITransmissionContext context, String unit) {
        String dimensionName = context.getContextExpendParam().getDimensionName();
        DimensionValueSet dimensionValueSetForUnit = new DimensionValueSet();
        dimensionValueSetForUnit.setValue("DATATIME", (Object)period);
        dimensionValueSetForUnit.setValue(dimensionName, (Object)unit);
        MappingImportParam mappingImportParam = context.getMappingImportParam() != null ? context.getMappingImportParam() : new MappingImportParam();
        for (int j = firstDimIndex; j < fmdmCodeIndex; ++j) {
            String dimCode = srcFieldDataCodes.get(j);
            String desDimCode = fmdmFieldMap.get(dimCode);
            String dimValue = listData.get(j);
            String desDimValue = mappingImportParam.getBaseToDataMappingByBaseAndData(dimCode, dimValue);
            dimensionValueSetForUnit.setValue(desDimCode, (Object)desDimValue);
        }
        if (adjustIndex != -1) {
            dimensionValueSetForUnit.setValue("ADJUST", (Object)listData.get(adjustIndex));
        }
        return dimensionValueSetForUnit;
    }

    private String getCodeFieldTitle(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(formScheme.getDw());
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        return bizKeyField.getCode();
    }

    public String getUnit(List<String> listData, int fmdmUnitCodeIndex, int fmdmCodeIndex, IEntityTable entityList, ITransmissionContext context) {
        MappingImportParam mappingImportParam;
        String unit = "";
        MappingImportParam mappingImportParam2 = mappingImportParam = context.getMappingImportParam() != null ? context.getMappingImportParam() : new MappingImportParam();
        if (fmdmUnitCodeIndex != -1) {
            unit = listData.get(fmdmUnitCodeIndex);
            unit = mappingImportParam.getOrgToMappings(unit);
        } else {
            unit = listData.get(fmdmCodeIndex);
            IEntityRow entityRow = entityList.findByCode(unit);
            if (entityRow != null) {
                unit = entityRow.getEntityKeyData();
            }
        }
        return unit;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int readData(CsvReader reader, List<String> srcFieldDataCodes, List<List<String>> allDatas, ITransmissionContext context, Map<String, List<String>> uploadUnitToAdjustForFmdm, Map<String, String> fmdmFieldMap) {
        int i;
        String formCode;
        Map<String, List<TransmissionZBMapping>> fmdmTableFieldMap;
        Map<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings;
        IExecuteParam executeParam = context.getExecuteParam();
        HashSet<String> srcDimCodes = new HashSet<String>(context.getContextExpendParam().getSrcDimCodes());
        Set<String> dimKeys = executeParam.getUploadDimMap().keySet();
        MappingImportParam mappingImportParam = context.getMappingImportParam() != null ? context.getMappingImportParam() : new MappingImportParam();
        int firstDimIndex = -1;
        List<String> adjustPeriod = executeParam.getAdjustPeriod();
        try {
            if (reader.readHeaders()) {
                for (int i2 = 0; i2 < reader.getHeaderCount(); ++i2) {
                    String header = reader.getHeader(i2);
                    srcFieldDataCodes.add(header);
                    if (!srcDimCodes.contains(header) && !dimKeys.contains(header) || firstDimIndex != -1) continue;
                    firstDimIndex = i2;
                }
            }
            while (reader.readRecord()) {
                String srcUnit = reader.get("FMDMUNITCODE");
                String destUnit = mappingImportParam.getOrgToMappings(srcUnit);
                if (adjustPeriod.size() == 0) {
                    if (!CollectionUtils.isEmpty((Collection)uploadUnitToAdjustForFmdm.get(destUnit))) continue;
                    ArrayList<String> listData = new ArrayList<String>();
                    for (String string : srcFieldDataCodes) {
                        listData.add(reader.get(string));
                    }
                    allDatas.add(listData);
                    continue;
                }
                List<String> adjustValues = uploadUnitToAdjustForFmdm.get(destUnit);
                if (!CollectionUtils.isEmpty(adjustValues) && adjustValues.contains(reader.get("ADJUSTVALUE"))) continue;
                ArrayList<String> listData = new ArrayList<String>();
                for (String fieldCode3 : srcFieldDataCodes) {
                    listData.add(reader.get(fieldCode3));
                }
                allDatas.add(listData);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            reader.close();
        }
        int fmdmCodeIndex = srcFieldDataCodes.indexOf("FMDMCODE");
        if (firstDimIndex == -1) {
            firstDimIndex = fmdmCodeIndex;
        }
        if (!CollectionUtils.isEmpty(formToDataTableToZBMappings = mappingImportParam.getFormToDataTableToZBMappings()) && !CollectionUtils.isEmpty(fmdmTableFieldMap = formToDataTableToZBMappings.get(formCode = this.runTimeViewController.queryFormById(context.getFmdmForm()).getFormCode()))) {
            for (Map.Entry<String, List<TransmissionZBMapping>> stringListEntry : fmdmTableFieldMap.entrySet()) {
                List<TransmissionZBMapping> value = stringListEntry.getValue();
                if (CollectionUtils.isEmpty(value)) continue;
                for (TransmissionZBMapping zbMapping : value) {
                    fmdmFieldMap.put(zbMapping.getZbCode(), zbMapping.getMapping());
                }
            }
        }
        for (i = 0; i < firstDimIndex; ++i) {
            String srcZBCode = srcFieldDataCodes.get(i);
            String string = StringUtils.hasText(fmdmFieldMap.get(srcZBCode)) ? fmdmFieldMap.get(srcZBCode) : srcZBCode;
            fmdmFieldMap.put(srcZBCode, string);
        }
        if (firstDimIndex != fmdmCodeIndex) {
            for (i = firstDimIndex; i < fmdmCodeIndex; ++i) {
                String dimCode = srcFieldDataCodes.get(i);
                String string = mappingImportParam.getBaseToMappings(dimCode);
                fmdmFieldMap.put(dimCode, string);
            }
        }
        return firstDimIndex;
    }

    @Override
    public void dataExport(OutputStream outputStream, ITransmissionContext context) throws Exception {
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        String fmdmForm = context.getFmdmForm();
        if (!StringUtils.hasText(fmdmForm) && this.runTimeViewController.queryFormById(fmdmForm) == null) {
            monitor.finish("\u591a\u7ea7\u90e8\u7f72\u6240\u9009\u62a5\u8868\u6ca1\u6709\u5c01\u9762\u4ee3\u7801\u8868\uff01", (Object)"\u591a\u7ea7\u90e8\u7f72\u6240\u9009\u62a5\u8868\u6ca1\u6709\u5c01\u9762\u4ee3\u7801\u8868\uff01");
            return;
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u6253\u5f00\u59cb\u6253\u5305\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e\uff01");
        monitor.progressAndMessage(0.0, "\u5f00\u59cb\u6267\u884c\u6240\u9009\u5355\u4f4d\u62a5\u8868\u6570\u636e\u5bfc\u51fa\u3002");
        IExecuteParam executeParam = context.getExecuteParam();
        String formSchemeKey = executeParam.getFormSchemeKey();
        ZipOutputStream zipOut = new ZipOutputStream(outputStream);
        FMDMDataDTO dto = new FMDMDataDTO();
        dto.setFormSchemeKey(formSchemeKey);
        dto.setSortedByQuery(false);
        DimensionValueSet dimensionValueSet = new DimensionValueSet(executeParam.getDimensionValueSet());
        ArrayList<String> adjustPeriod = new ArrayList<String>(executeParam.getAdjustPeriod());
        List list = new ArrayList();
        HashMap<String, List<IFMDMData>> fmdmDatasMap = new HashMap<String, List<IFMDMData>>();
        DimensionCollection collection = this.dimensionBuildUtil.getDimensionCollection(context.getContextExpendParam().getDimensionValueSetWithAllDim(), executeParam.getFormSchemeKey());
        if (adjustPeriod.size() == 0) {
            adjustPeriod.add("NO_ADJUST");
            dto.setDimensionValueSet(dimensionValueSet);
            list = this.fmdmDataService.list(dto, collection);
            fmdmDatasMap.put("NO_ADJUST", list);
        } else {
            for (String adjustValue : adjustPeriod) {
                dimensionValueSet.setValue("ADJUST", (Object)adjustValue);
                dto.setDimensionValueSet(dimensionValueSet);
                list = this.fmdmDataService.list(dto, collection);
                fmdmDatasMap.put(adjustValue, list);
            }
        }
        String path = "";
        try {
            List<Object> headAndData = this.serialize(context, fmdmDatasMap);
            path = ZipUtils.newTempDir();
            assert (headAndData != null);
            zipOut.setMethod(8);
            this.getCsv(headAndData, zipOut);
        }
        catch (Exception e) {
            ILogHelper logHelper = context.getLogHelper();
            String errorMessage = MultilingualLog.exportFormDataError(this.getCode()) + e.getMessage();
            logHelper.appendLog(errorMessage + "\r\n");
            logger.error(errorMessage, e);
            throw new Exception(errorMessage, e);
        }
        finally {
            zipOut.finish();
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u6253\u5305\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e\u5b8c\u6210\uff01");
        monitor.finish("\u6240\u9009\u5355\u4f4d\u5c01\u9762\u4ee3\u7801\u8868\u8868\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\uff01", (Object)"\u6240\u9009\u5355\u4f4d\u5c01\u9762\u4ee3\u7801\u8868\u8868\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\uff01");
        Utils.deleteAllFilesOfDirByPath(path);
    }

    private void getCsv(List<Object> headAndData, ZipOutputStream zipos) {
        List fmdmHead = (List)headAndData.get(0);
        List fmdmData = (List)headAndData.get(1);
        CsvWriter csvWriter = new CsvWriter((OutputStream)zipos, ",".toCharArray()[0], StandardCharsets.UTF_8);
        String fileName = "FMDM.csv";
        ZipEntry zipEntry = new ZipEntry(fileName);
        try {
            zipos.putNextEntry(zipEntry);
            String[] fieldDefineArray = new String[fmdmHead.size()];
            for (int i = 0; i < fmdmHead.size(); ++i) {
                fieldDefineArray[i] = (String)fmdmHead.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (List fmdmDatum : fmdmData) {
                String[] dataArray = new String[fmdmDatum.size()];
                fmdmDatum.toArray(dataArray);
                csvWriter.writeRecord(dataArray);
            }
            csvWriter.flush();
            zipos.closeEntry();
        }
        catch (Exception e) {
            logger.info("\u591a\u7ea7\u90e8\u7f72\u5bfc\u51fa\u5c01\u9762\u4ee3\u7801\u6570\u636e\u51fa\u9519{}", e);
        }
    }

    private List<Object> serialize(ITransmissionContext context, Map<String, List<IFMDMData>> fmdmDatasMap) throws Exception {
        boolean replaceParent;
        String formSchemeKey = context.getExecuteParam().getFormSchemeKey();
        String fmdmForm = context.getFmdmForm();
        String entityCode = context.getContextExpendParam().getEntityCode();
        FormDefine formDefine = null;
        formDefine = this.runTimeViewController.queryFormById(fmdmForm);
        if (formDefine == null) {
            return null;
        }
        String fmdmFormCode = formDefine.getFormCode();
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
        IFMDMAttribute fmdmCodeField = this.runTimeFMDMAttributeServiceImpl.getFMDMCodeField(fmdmAttributeDTO);
        String fmdmCode = fmdmCodeField.getCode();
        List allShowAttributes = this.runTimeFMDMAttributeServiceImpl.listShowAttribute(fmdmAttributeDTO);
        ArrayList allPicOrFileAttributes = new ArrayList();
        ArrayList dataTableFiltereds = new ArrayList();
        List allShowAttributesFiltered = allShowAttributes.stream().filter(t -> {
            if (ColumnModelKind.DEFAULT == t.getKind()) {
                dataTableFiltereds.add(t);
            }
            if (t.getColumnType() == ColumnModelType.ATTACHMENT) {
                allPicOrFileAttributes.add(t);
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        if (allPicOrFileAttributes.size() > 0) {
            StringBuilder message = new StringBuilder(MultilingualLog.exportFmdmDataError(1));
            for (IFMDMAttribute fmdmAttribute : allPicOrFileAttributes) {
                message.append(fmdmAttribute.getTitle()).append("[").append(fmdmAttribute.getCode()).append("]\uff0c");
            }
            message.replace(message.length() - 1, message.length(), "\uff01");
            context.getLogHelper().appendLog(message.toString());
        }
        List<String> dataCode = allShowAttributesFiltered.stream().map(IModelDefineItem::getCode).collect(Collectors.toList());
        HashSet dataCodeSet = new HashSet(dataCode);
        HashMap<String, String> parentKeyMapping = new HashMap<String, String>();
        IFMDMAttribute fmdmParentField = this.runTimeFMDMAttributeServiceImpl.getFMDMParentField(fmdmAttributeDTO);
        String parentCode = fmdmParentField.getCode();
        boolean bl = replaceParent = dataCode.contains(parentCode) && this.multistageUnitReplaceImpl != null;
        if (dataCode.size() != dataCodeSet.size()) {
            context.getLogHelper().appendLog(MultilingualLog.exportFmdmDataError(2));
        }
        MappingParam mappingParam = context.getMappingParam();
        ArrayList<ArrayList<String>> allDataInForm = new ArrayList<ArrayList<String>>();
        ArrayList<String> dimCodes = new ArrayList<String>(context.getExecuteParam().getUploadDimMap().keySet());
        if (fmdmDatasMap.size() == 1 && fmdmDatasMap.get("NO_ADJUST") != null) {
            List<IFMDMData> dataList = fmdmDatasMap.get("NO_ADJUST");
            Iterator iterator = dataList.iterator();
            while (iterator.hasNext()) {
                IFMDMData data = (IFMDMData)iterator.next();
                ArrayList<String> dataInFormForFmdmKey = new ArrayList<String>();
                if (replaceParent) {
                    this.setValue(dataCode, data, dataInFormForFmdmKey, fmdmCode, dimCodes, parentCode, parentKeyMapping, mappingParam);
                } else {
                    this.setValue(dataCode, data, dataInFormForFmdmKey, fmdmCode, dimCodes, mappingParam);
                }
                allDataInForm.add(dataInFormForFmdmKey);
            }
        } else {
            for (Map.Entry<String, List<IFMDMData>> stringListEntry : fmdmDatasMap.entrySet()) {
                List<IFMDMData> dataList = stringListEntry.getValue();
                String adjustValue = stringListEntry.getKey();
                for (IFMDMData data : dataList) {
                    ArrayList<String> dataInFormForFmdmKey = new ArrayList<String>();
                    if (replaceParent) {
                        this.setValue(dataCode, data, dataInFormForFmdmKey, fmdmCode, dimCodes, parentCode, parentKeyMapping, mappingParam);
                    } else {
                        this.setValue(dataCode, data, dataInFormForFmdmKey, fmdmCode, dimCodes, mappingParam);
                    }
                    dataInFormForFmdmKey.add(adjustValue);
                    allDataInForm.add(dataInFormForFmdmKey);
                }
            }
        }
        HashMap<String, String> tableDataFieldMapping = new HashMap<String, String>();
        Map<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings = null;
        if (mappingParam != null) {
            formToDataTableToZBMappings = mappingParam.getFormToDataTableToZBMappings();
        }
        if (!CollectionUtils.isEmpty(formToDataTableToZBMappings) && !CollectionUtils.isEmpty(formToDataTableToZBMappings.get(fmdmFormCode))) {
            List zbKeys = dataTableFiltereds.stream().map(IFMDMAttribute::getZBKey).collect(Collectors.toList());
            List dataFields = this.runtimeDataSchemeService.getDataFields(zbKeys);
            Map<String, List<DataField>> tableKeyToFieldMap = dataFields.stream().collect(Collectors.groupingBy(DataField::getDataTableKey));
            HashMap tableCodeToFieldCodeMap = new HashMap();
            List dataTables = this.runtimeDataSchemeService.getDataTables(new ArrayList<String>(tableKeyToFieldMap.keySet()));
            for (DataTable dataTable : dataTables) {
                List<DataField> dataFieldsForTable = tableKeyToFieldMap.get(dataTable.getKey());
                tableCodeToFieldCodeMap.put(dataTable.getCode(), dataFieldsForTable.stream().map(Basic::getCode).collect(Collectors.toList()));
            }
            Set zbCodes = dataTableFiltereds.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
            List orgFieldCode = dataCode.stream().filter(a -> !zbCodes.contains(a)).collect(Collectors.toList());
            if (orgFieldCode.size() > 0) {
                tableCodeToFieldCodeMap.put(entityCode, orgFieldCode);
            }
            for (Map.Entry entry : tableCodeToFieldCodeMap.entrySet()) {
                String tableCode = (String)entry.getKey();
                List fieldCodes = (List)entry.getValue();
                Map<String, String> fieldMap = mappingParam.getFieldMappingForFormAndDataTable(fmdmFormCode, tableCode, fieldCodes);
                tableDataFieldMapping.putAll(fieldMap);
            }
            for (int i = 0; i < dataCode.size(); ++i) {
                String string = dataCode.get(i);
                if (!StringUtils.hasText((String)tableDataFieldMapping.get(string))) continue;
                dataCode.set(i, (String)tableDataFieldMapping.get(string));
            }
        }
        ArrayList<String> srcAllDimCodes = new ArrayList<String>();
        if (mappingParam != null) {
            for (String dimCode : dimCodes) {
                dimCode = mappingParam.getBaseToMappings(dimCode);
                dataCode.add(dimCode);
                srcAllDimCodes.add(dimCode);
            }
        } else {
            dataCode.addAll(dimCodes);
            srcAllDimCodes.addAll(dimCodes);
        }
        context.getContextExpendParam().setSrcDimCodes(srcAllDimCodes);
        dataCode.add("FMDMCODE");
        dataCode.add("FMDMUNITCODE");
        if (fmdmDatasMap.size() != 1 || fmdmDatasMap.get("NO_ADJUST") == null) {
            dataCode.add("ADJUSTVALUE");
        }
        ArrayList<Object> values = new ArrayList<Object>(2);
        values.add(dataCode);
        values.add(allDataInForm);
        return values;
    }

    private void setValue(List<String> dataCode, IFMDMData data, List<String> dataInFormForFmdmKey, String fmdmCode, List<String> dimCodes, MappingParam mappingParam) {
        for (String s : dataCode) {
            String stringValue;
            AbstractData value = data.getValue(s);
            if (value.dataType == 1) {
                stringValue = value.getAsBool() ? "true" : "false";
            } else {
                String string = stringValue = value.getAsString() == null ? "" : value.getAsString();
            }
            if ((s.equals(fmdmCode) || s.equals(FMDM_ORGCODE)) && this.multistageUnitReplaceImpl != null && StringUtils.hasLength(this.multistageUnitReplaceImpl.getSuperiorOrgCode(stringValue))) {
                stringValue = this.multistageUnitReplaceImpl.getSuperiorOrgCode(stringValue);
            }
            dataInFormForFmdmKey.add(stringValue);
        }
        this.getDimValues(dimCodes, data, dataInFormForFmdmKey, mappingParam);
        String unit = data.getValue(fmdmCode).getAsString();
        String unitCode = data.getFMDMKey();
        if (this.multistageUnitReplaceImpl != null) {
            unit = this.multistageUnitReplaceImpl.getSuperiorOrgCode(unit);
            unitCode = this.multistageUnitReplaceImpl.getSuperiorCode(unitCode);
        }
        dataInFormForFmdmKey.add(unit);
        unitCode = mappingParam != null ? mappingParam.getOrgMapByCode(unitCode) : unitCode;
        dataInFormForFmdmKey.add(unitCode);
    }

    private void setValue(List<String> dataCode, IFMDMData data, List<String> dataInFormForFmdmKey, String fmdmCode, List<String> dimCodes, String parentCode, Map<String, String> parentKeyMapping, MappingParam mappingParam) {
        for (String s : dataCode) {
            String stringValue;
            AbstractData value = data.getValue(s);
            if (value.dataType == 1) {
                stringValue = value.getAsBool() ? "true" : "false";
            } else {
                String string = stringValue = value.getAsString() == null ? "" : value.getAsString();
            }
            if ((s.equals(fmdmCode) || s.equals(FMDM_ORGCODE)) && this.multistageUnitReplaceImpl != null && StringUtils.hasLength(this.multistageUnitReplaceImpl.getSuperiorOrgCode(stringValue))) {
                stringValue = this.multistageUnitReplaceImpl.getSuperiorOrgCode(stringValue);
            }
            if (parentCode.equals(s) && StringUtils.hasLength(stringValue)) {
                if (StringUtils.hasLength(parentKeyMapping.get(stringValue))) {
                    stringValue = parentKeyMapping.get(stringValue);
                } else {
                    String srcStringValue = stringValue;
                    stringValue = this.multistageUnitReplaceImpl.getSuperiorCode(stringValue);
                    parentKeyMapping.put(srcStringValue, stringValue);
                }
            }
            dataInFormForFmdmKey.add(stringValue);
        }
        this.getDimValues(dimCodes, data, dataInFormForFmdmKey, mappingParam);
        String unit = data.getValue(fmdmCode).getAsString();
        unit = this.multistageUnitReplaceImpl.getSuperiorOrgCode(unit);
        dataInFormForFmdmKey.add(unit);
        String unitCode = data.getFMDMKey();
        unitCode = this.multistageUnitReplaceImpl.getSuperiorCode(unitCode);
        unitCode = mappingParam != null ? mappingParam.getOrgMapByCode(unitCode) : unitCode;
        dataInFormForFmdmKey.add(unitCode);
    }

    private void getDimValues(List<String> dimCodes, IFMDMData data, List<String> dataInFormForFmdmKey, MappingParam mappingParam) {
        if (!CollectionUtils.isEmpty(dimCodes)) {
            DimensionValueSet masterKey = data.getMasterKey();
            for (String dimCode : dimCodes) {
                String dimValue;
                String string = dimValue = masterKey.getValue(dimCode) == null ? "" : masterKey.getValue(dimCode).toString();
                if (mappingParam != null && StringUtils.hasLength(dimValue)) {
                    dimValue = mappingParam.getBaseToDataMappingByBaseAndData(dimCode, dimValue);
                }
                dataInFormForFmdmKey.add(dimValue);
            }
        }
    }

    private String getFilePathInZip() {
        return "FMDM.csv";
    }
}

