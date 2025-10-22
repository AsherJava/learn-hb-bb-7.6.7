/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext
 *  com.jiuqi.nr.attachment.input.FileUploadInfo
 *  com.jiuqi.nr.attachment.output.FileImportResult
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.data.attachment.service.impl;

import com.csvreader.CsvReader;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.output.FileImportResult;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.data.attachment.common.FieldDataFileConsts;
import com.jiuqi.nr.data.attachment.exception.AttachmentErrorCode;
import com.jiuqi.nr.data.attachment.param.AttRelRow;
import com.jiuqi.nr.data.attachment.param.ImpFileParams;
import com.jiuqi.nr.data.attachment.service.ImpFieldDataFileService;
import com.jiuqi.nr.data.attachment.service.impl.DefaultFieldDataImpFileFinder;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ImpFieldDataFileServiceImpl
implements ImpFieldDataFileService {
    private static final Logger logger = LoggerFactory.getLogger(ImpFieldDataFileServiceImpl.class);
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private AttachmentIOService attachmentIOService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    private static final String MOUDLE = "\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u670d\u52a1";
    private static final String ATTACHMENT_IMPORT_FAILED = "\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u5931\u8d25";

    @Override
    public void uploadFileds(ImpFileParams params) throws IOException {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(MOUDLE, OperLevel.USER_OPER);
        logHelper.info(params.getTaskKey(), null, "\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u5f00\u59cb", "\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u5f00\u59cb");
        if (null == params.getDataSchemeKey() || null == params.getTaskKey() || null == params.getFormSchemeKey() || null == params.getOldAndNewGroupKeyMapping()) {
            logger.error("\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u53c2\u6570\u5f02\u5e38");
            logHelper.error(params.getTaskKey(), null, ATTACHMENT_IMPORT_FAILED, "\u9519\u8bef\u539f\u56e0\uff1a" + AttachmentErrorCode.ATTA_PARAM_ERROR.getDescription());
            throw new IllegalArgumentException(AttachmentErrorCode.ATTA_PARAM_ERROR.getDescription());
        }
        String attachmentPath = params.getAttachmentPath();
        if (StringUtils.hasText(attachmentPath)) {
            params.setFileFinder(new DefaultFieldDataImpFileFinder(attachmentPath));
            this.importFileds(params, logHelper);
        } else if (null != params.getFileFinder()) {
            this.importFileds(params, logHelper);
        } else {
            logger.error("\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u53c2\u6570\u5f02\u5e38");
            logHelper.error(params.getTaskKey(), null, ATTACHMENT_IMPORT_FAILED, "\u9519\u8bef\u539f\u56e0\uff1a" + AttachmentErrorCode.ATTA_PARAM_ERROR.getDescription());
            throw new IllegalArgumentException(AttachmentErrorCode.ATTA_PARAM_ERROR.getDescription());
        }
    }

    private void importFileds(ImpFileParams params, DataServiceLogHelper logHelper) throws IOException {
        boolean uploadRes;
        File attInfoCSV = null;
        FileFinder fileFinder = params.getFileFinder();
        List fileEntries = fileFinder.listFiles("attachment");
        for (FileEntry fileEntry : fileEntries) {
            String fileName = fileEntry.getFileName();
            if (!fileName.endsWith(".csv")) continue;
            attInfoCSV = fileFinder.getFile(fileEntry.getFilePath());
        }
        if (null == attInfoCSV || !attInfoCSV.exists()) {
            logger.error("\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u672a\u627e\u5230\u9644\u4ef6\u6e05\u5355\u6587\u4ef6");
            logHelper.error(params.getTaskKey(), null, ATTACHMENT_IMPORT_FAILED, "\u9519\u8bef\u539f\u56e0\uff1a\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u672a\u627e\u5230\u9644\u4ef6\u6e05\u5355\u6587\u4ef6");
            throw new IllegalArgumentException("\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u672a\u627e\u5230\u9644\u4ef6\u6e05\u5355\u6587\u4ef6");
        }
        ArrayList<String> dimNames = new ArrayList<String>();
        HashMap<String, DimensionType> dimNameTypeMapping = new HashMap<String, DimensionType>();
        HashMap<String, String> dimNameEntityIdMapping = new HashMap<String, String>();
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(params.getDataSchemeKey());
        for (DataDimension dataDimension : dataSchemeDimension) {
            IEntityDefine entity;
            if (DimensionType.UNIT == dataDimension.getDimensionType()) {
                entity = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
                dimNames.add(entity.getDimensionName());
                dimNameTypeMapping.put(entity.getDimensionName(), DimensionType.UNIT);
                continue;
            }
            if (DimensionType.PERIOD == dataDimension.getDimensionType()) {
                IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dataDimension.getDimKey());
                dimNames.add(periodEntity.getDimensionName());
                dimNameTypeMapping.put(periodEntity.getDimensionName(), DimensionType.PERIOD);
                continue;
            }
            if (DimensionType.DIMENSION != dataDimension.getDimensionType()) continue;
            entity = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
            if (null != entity) {
                dimNames.add(entity.getDimensionName());
                dimNameTypeMapping.put(entity.getDimensionName(), DimensionType.DIMENSION);
                dimNameEntityIdMapping.put(entity.getDimensionName(), entity.getId());
                continue;
            }
            dimNames.add("ADJUST");
        }
        Map<String, List<AttRelRow>> groupKeyAttRelRows = this.readAttCSV(attInfoCSV, dimNames, logHelper, params);
        if (groupKeyAttRelRows.isEmpty()) {
            return;
        }
        ParamsMapping paramsMapping = params.getParamsMapping();
        if (null != paramsMapping) {
            this.constructMappingData(dimNameTypeMapping, dimNameEntityIdMapping, groupKeyAttRelRows, paramsMapping);
        }
        if (uploadRes = this.uploadAttachment(params, groupKeyAttRelRows, logHelper)) {
            logHelper.info(params.getTaskKey(), null, "\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u6210\u529f", "\u6307\u6807\u9644\u4ef6\u5bfc\u5165\u6210\u529f");
        }
    }

    private boolean uploadAttachment(ImpFileParams params, Map<String, List<AttRelRow>> groupKeyAttRelRows, DataServiceLogHelper logHelper) throws FileNotFoundException, IOException {
        List fieldCodes;
        HashMap<String, String> fieldCodeFieldKeyMapping = new HashMap<String, String>();
        HashMap<String, List> tableCodeFieldCodesMapping = new HashMap<String, List>();
        for (String string : groupKeyAttRelRows.keySet()) {
            List<AttRelRow> attRelRows = groupKeyAttRelRows.get(string);
            String dataTableCode = attRelRows.get(0).getDataTableCode();
            fieldCodes = tableCodeFieldCodesMapping.computeIfAbsent(dataTableCode, k -> new ArrayList());
            fieldCodes.add(attRelRows.get(0).getFieldCode());
        }
        for (Map.Entry entry : tableCodeFieldCodesMapping.entrySet()) {
            String tableCode = (String)entry.getKey();
            DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(tableCode);
            fieldCodes = (List)tableCodeFieldCodesMapping.get(tableCode);
            for (String fieldCode : fieldCodes) {
                DataField dataField = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), fieldCode);
                fieldCodeFieldKeyMapping.put(fieldCode, dataField.getKey());
            }
        }
        Map<String, String> oldAndNewGroupKeyMapping = params.getOldAndNewGroupKeyMapping();
        for (String groupKey : groupKeyAttRelRows.keySet()) {
            String newGroupKey = oldAndNewGroupKeyMapping.get(groupKey);
            if (null == newGroupKey) continue;
            List<AttRelRow> attRelRows = groupKeyAttRelRows.get(groupKey);
            String fieldCode = attRelRows.get(0).getFieldCode();
            String fieldKey = (String)fieldCodeFieldKeyMapping.get(fieldCode);
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
            Map<String, String> dimNameValue = attRelRows.get(0).getDimNameValue();
            for (Map.Entry<String, String> entry : dimNameValue.entrySet()) {
                String dimName = entry.getKey();
                builder.setValue(dimName, (Object)dimNameValue.get(dimName));
            }
            ArrayList<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
            FileUploadByGroupKeyContext context = new FileUploadByGroupKeyContext(fieldKey, newGroupKey, fileUploadInfos, params.getDataSchemeKey(), params.getTaskKey(), builder.getCombination(), params.getFormSchemeKey(), null);
            for (AttRelRow attRelRow : attRelRows) {
                String filePath = "attachment/" + groupKey + "/" + attRelRow.getFileKey();
                InputStream fileInputStream = params.getFileFinder().getFileInputStream(filePath);
                if (null == fileInputStream) continue;
                FileUploadInfo fileUploadInfo = new FileUploadInfo();
                fileUploadInfo.setFile(fileInputStream);
                fileUploadInfo.setName(attRelRow.getFileName());
                fileUploadInfo.setSize(Long.parseLong(attRelRow.getFileSize()));
                fileUploadInfo.setFileSecret(attRelRow.getFileSecret());
                fileUploadInfo.setCategory(attRelRow.getCategory());
                if (params.isUploadByFileKey()) {
                    fileUploadInfo.setFileKey(attRelRow.getFileKey());
                    fileUploadInfo.setJioImportAttachFileKey(true);
                }
                fileUploadInfos.add(fileUploadInfo);
            }
            FileImportResult result = this.attachmentIOService.uploadByGroup(context);
            if (result.isSuccess()) continue;
            logger.error("\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + result.getErrorMsg());
            logHelper.error(params.getTaskKey(), null, ATTACHMENT_IMPORT_FAILED, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + result.getErrorMsg());
            return false;
        }
        return true;
    }

    private void constructMappingData(Map<String, DimensionType> dimNameTypeMapping, Map<String, String> dimNameEntityIdMapping, Map<String, List<AttRelRow>> groupKeyAttRelRows, ParamsMapping paramsMapping) {
        HashMap<String, Set> tableCodeFieldCodesMapping = new HashMap<String, Set>();
        HashSet<String> dwCodes = new HashSet<String>();
        HashSet<String> dataTimes = new HashSet<String>();
        HashMap<String, Set> entityIdDimValueMapping = new HashMap<String, Set>();
        for (String string : groupKeyAttRelRows.keySet()) {
            List<AttRelRow> list = groupKeyAttRelRows.get(string);
            String dataTableCode = list.get(0).getDataTableCode();
            String fieldCode = list.get(0).getFieldCode();
            Set fieldCodes = tableCodeFieldCodesMapping.computeIfAbsent(dataTableCode, k -> new HashSet());
            fieldCodes.add(fieldCode);
            Map<String, String> dimNameValue = list.get(0).getDimNameValue();
            for (Map.Entry<String, String> entry : dimNameValue.entrySet()) {
                String entityId;
                String dimName = entry.getKey();
                String dimValue = dimNameValue.get(dimName);
                DimensionType dimensionType = dimNameTypeMapping.get(dimName);
                if (DimensionType.UNIT == dimensionType) {
                    dwCodes.add(dimValue);
                    continue;
                }
                if (DimensionType.PERIOD == dimensionType) {
                    dataTimes.add(dimValue);
                    continue;
                }
                if (DimensionType.DIMENSION != dimensionType || null == (entityId = dimNameEntityIdMapping.get(dimName))) continue;
                Set dimValues = entityIdDimValueMapping.computeIfAbsent(entityId, k -> new HashSet());
                dimValues.add(dimValue);
            }
        }
        HashMap<String, Map> tableCodeAndOriginDataFieldCodeMapping = new HashMap<String, Map>();
        for (Map.Entry entry : tableCodeFieldCodesMapping.entrySet()) {
            String tableCode = (String)entry.getKey();
            Set fieldCodes = (Set)tableCodeFieldCodesMapping.get(tableCode);
            tableCodeAndOriginDataFieldCodeMapping.put(tableCode, paramsMapping.getOriginDataFieldCode(tableCode, new ArrayList(fieldCodes)));
        }
        Map map = paramsMapping.getOriginOrgCode(new ArrayList(dwCodes));
        Map map2 = paramsMapping.getOriginPeriod(new ArrayList(dataTimes));
        HashMap<String, Map> entityIdAndOriginBaseData = new HashMap<String, Map>();
        for (Map.Entry entry : entityIdDimValueMapping.entrySet()) {
            String entityId = (String)entry.getKey();
            Set dimValues = (Set)entityIdDimValueMapping.get(entityId);
            if (null == dimValues) continue;
            Map originBaseData = paramsMapping.getOriginBaseData(entityId, new ArrayList(dimValues));
            entityIdAndOriginBaseData.put(entityId, originBaseData);
        }
        for (String groupKey : groupKeyAttRelRows.keySet()) {
            List<AttRelRow> attRelRows = groupKeyAttRelRows.get(groupKey);
            for (AttRelRow attRelRow : attRelRows) {
                String dataTableCode = attRelRow.getDataTableCode();
                String fieldCode = attRelRow.getFieldCode();
                DataFieldMp dataFieldMp = (DataFieldMp)((Map)tableCodeAndOriginDataFieldCodeMapping.get(dataTableCode)).get(fieldCode);
                if (null != dataFieldMp) {
                    attRelRow.setDataTableCode(dataFieldMp.getTableCode());
                    attRelRow.setFieldCode(dataFieldMp.getCode());
                }
                Map<String, String> dimNameValue = attRelRow.getDimNameValue();
                for (Map.Entry<String, String> entry : dimNameValue.entrySet()) {
                    String entityId;
                    String originDimvalue;
                    String dimName = entry.getKey();
                    String dimValue = dimNameValue.get(dimName);
                    DimensionType dimensionType = dimNameTypeMapping.get(dimName);
                    if (DimensionType.UNIT == dimensionType) {
                        originDimvalue = (String)map.get(dimValue);
                        if (null == originDimvalue) continue;
                        dimNameValue.put(dimName, originDimvalue);
                        continue;
                    }
                    if (DimensionType.PERIOD == dimensionType) {
                        originDimvalue = (String)map2.get(dimValue);
                        if (null == originDimvalue) continue;
                        dimNameValue.put(dimName, originDimvalue);
                        continue;
                    }
                    if (DimensionType.DIMENSION != dimensionType || null == (entityId = dimNameEntityIdMapping.get(dimName)) || null == entityIdAndOriginBaseData.get(entityId) || null == ((Map)entityIdAndOriginBaseData.get(entityId)).get(dimValue)) continue;
                    dimNameValue.put(dimName, (String)((Map)entityIdAndOriginBaseData.get(entityId)).get(dimValue));
                }
            }
        }
    }

    @NotNull
    private Map<String, List<AttRelRow>> readAttCSV(File attInfoCSV, List<String> dimNames, DataServiceLogHelper logHelper, ImpFileParams params) throws IOException {
        HashMap<String, List<AttRelRow>> groupKeyAttRelRows = new HashMap<String, List<AttRelRow>>();
        FilterDim filterDims = params.getFilterDims();
        DimensionValueSet fixedFilterDims = null;
        if (null != filterDims && null != filterDims.getFixedFilterDims()) {
            fixedFilterDims = filterDims.getFixedFilterDims();
        }
        CompletionDim completionDims = params.getCompletionDims();
        DimensionValueSet fixedCompletionDims = null;
        if (null != completionDims && null != completionDims.getFixedCompletionDims()) {
            fixedCompletionDims = completionDims.getFixedCompletionDims();
        }
        String dwDimName = "";
        if (null != completionDims && null != completionDims.getDynamicsCompletionDims()) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(params.getTaskKey());
            IEntityDefine entity = this.iEntityMetaService.queryEntity(taskDefine.getDw());
            dwDimName = entity.getDimensionName();
        }
        try (FileInputStream fos = new FileInputStream(attInfoCSV);){
            ArrayList<String> headerCodes = new ArrayList<String>(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER);
            headerCodes.addAll(dimNames);
            CsvReader reader = new CsvReader((InputStream)fos, ',', StandardCharsets.UTF_8);
            reader.readHeaders();
            String[] headers = reader.getHeaders();
            HashMap<String, Integer> headerMap = new HashMap<String, Integer>();
            for (int i = 0; i < headers.length; ++i) {
                headerMap.put(headers[i], i);
            }
            for (String headerCode : headerCodes) {
                if (headerMap.containsKey(headerCode) || null == completionDims || !completionDims.isCompletionDim() || completionDims.getCompletionDims().contains(headerCode)) continue;
                logger.error("\u9644\u4ef6\u4fe1\u606fcsv\u6587\u4ef6\u5185\u5bb9\u8868\u5934\u4e0d\u7b26\u5408\u89c4\u8303");
                logHelper.error(params.getTaskKey(), null, ATTACHMENT_IMPORT_FAILED, "\u9519\u8bef\u539f\u56e0\uff1a\u9644\u4ef6\u4fe1\u606fcsv\u6587\u4ef6\u5185\u5bb9\u8868\u5934\u4e0d\u7b26\u5408\u89c4\u8303");
                throw new IllegalArgumentException("\u9644\u4ef6\u4fe1\u606fcsv\u6587\u4ef6\u5185\u5bb9\u8868\u5934\u4e0d\u7b26\u5408\u89c4\u8303");
            }
            while (reader.readRecord()) {
                Map<String, String> dimNameValue;
                if (this.filterDim(fixedFilterDims, reader) || null == (dimNameValue = this.completionDim(dimNames, completionDims, fixedCompletionDims, dwDimName, reader))) continue;
                String groupKey = reader.get(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER.get(0));
                String fileKey = reader.get(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER.get(1));
                String fileName = reader.get(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER.get(2));
                String fileSize = reader.get(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER.get(3));
                String fileSecret = reader.get(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER.get(4));
                String category = reader.get(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER.get(5));
                String dataTableCode = reader.get(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER.get(6));
                String fieldCode = reader.get(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER.get(7));
                List attRelRows = groupKeyAttRelRows.computeIfAbsent(groupKey, k -> new ArrayList());
                attRelRows.add(new AttRelRow(groupKey, fileKey, fileName, fileSize, fileSecret, category, dataTableCode, fieldCode, dimNameValue));
            }
            reader.close();
        }
        return groupKeyAttRelRows;
    }

    private Map<String, String> completionDim(List<String> dimNames, CompletionDim completionDims, DimensionValueSet fixedCompletionDims, String dwDimName, CsvReader reader) throws IOException {
        HashMap<String, String> dimNameValue = new HashMap<String, String>();
        for (String dimName : dimNames) {
            boolean isCompletion = false;
            if (null != fixedCompletionDims) {
                for (int i = 0; i < fixedCompletionDims.size(); ++i) {
                    if (!dimName.equals(fixedCompletionDims.getName(i))) continue;
                    dimNameValue.put(dimName, fixedCompletionDims.getValue(i).toString());
                    isCompletion = true;
                    break;
                }
            }
            if (StringUtils.hasText(dwDimName)) {
                List dynamicsCompletionDims = completionDims.getDynamicsCompletionDims();
                for (String dynamicsCompletionDim : dynamicsCompletionDims) {
                    if (!dimName.equals(dynamicsCompletionDim)) continue;
                    String dimValue = completionDims.getFinder().findByDw(reader.get(dwDimName), dimName);
                    if (!StringUtils.hasText(dimValue)) {
                        return null;
                    }
                    dimNameValue.put(dimName, dimValue);
                    isCompletion = true;
                    break;
                }
            }
            if (isCompletion) continue;
            dimNameValue.put(dimName, reader.get(dimName));
        }
        return dimNameValue;
    }

    private boolean filterDim(DimensionValueSet fixedFilterDims, CsvReader reader) throws IOException {
        if (null != fixedFilterDims) {
            for (int i = 0; i < fixedFilterDims.size(); ++i) {
                String dimValue = reader.get(fixedFilterDims.getName(i));
                if (dimValue.equals(fixedFilterDims.getValue(i).toString())) continue;
                return true;
            }
            return false;
        }
        return false;
    }
}

