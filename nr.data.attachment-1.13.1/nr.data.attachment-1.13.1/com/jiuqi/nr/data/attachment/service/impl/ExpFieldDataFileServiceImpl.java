/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.data.attachment.service.impl;

import com.csvreader.CsvWriter;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.data.attachment.common.FieldDataFileConsts;
import com.jiuqi.nr.data.attachment.exception.AttachmentErrorCode;
import com.jiuqi.nr.data.attachment.param.AttFieldDataInfo;
import com.jiuqi.nr.data.attachment.param.ExpFileParams;
import com.jiuqi.nr.data.attachment.service.ExpFieldDataFileService;
import com.jiuqi.nr.data.attachment.service.impl.DefaultFieldDataExpFileWriter;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ExpFieldDataFileServiceImpl
implements ExpFieldDataFileService {
    private static final Logger logger = LoggerFactory.getLogger(ExpFieldDataFileServiceImpl.class);
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private AttachmentIOService attachmentIOService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    private static final String MOUDLE = "\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u670d\u52a1";

    @Override
    public String downloadFiles(ExpFileParams params) throws IOException {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(MOUDLE, OperLevel.USER_OPER);
        logHelper.info(null, null, "\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u5f00\u59cb", "\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u5f00\u59cb");
        if (null == params || StringUtils.isEmpty((String)params.getDataSchemeKey()) || null == params.getAttFieldDataInfos()) {
            logger.error("\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u53c2\u6570\u5f02\u5e38");
            logHelper.error(null, null, "\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u5931\u8d25", "\u9519\u8bef\u539f\u56e0\uff1a" + AttachmentErrorCode.ATTA_PARAM_ERROR.getDescription());
            throw new IllegalArgumentException(AttachmentErrorCode.ATTA_PARAM_ERROR.getDescription());
        }
        String filePath = params.getFilePath();
        if (null != params.getFileWriter() && StringUtils.isNotEmpty((String)params.getCsvFileName())) {
            this.exportFile(params, logHelper);
            return null;
        }
        if (StringUtils.isNotEmpty((String)filePath)) {
            params.setFileWriter(new DefaultFieldDataExpFileWriter(filePath));
            params.setCsvFileName("ATTACHMENT_RELATION_INFO");
            filePath = filePath.endsWith("/") ? filePath + "attachment" + File.separator : filePath + "/" + "attachment" + File.separator;
            filePath = FilenameUtils.normalize(filePath);
            this.exportFile(params, logHelper);
            return filePath;
        }
        logger.error("\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u53c2\u6570\u5f02\u5e38");
        logHelper.error(null, null, "\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u5931\u8d25", "\u9519\u8bef\u539f\u56e0\uff1a" + AttachmentErrorCode.ATTA_PARAM_ERROR.getDescription());
        throw new IllegalArgumentException(AttachmentErrorCode.ATTA_PARAM_ERROR.getDescription());
    }

    private void exportFile(ExpFileParams params, DataServiceLogHelper logHelper) throws IOException {
        Map<String, List<FileInfo>> groupKeyFileInfos;
        String csvFileRelativePath = "attachment/" + params.getCsvFileName() + ".csv";
        try {
            OutputStream fos = params.getFileWriter().createFile(csvFileRelativePath);
            Object object = null;
            try {
                CsvWriter csvWriter = new CsvWriter(fos, ',', StandardCharsets.UTF_8);
                ArrayList<String> dimNames = new ArrayList<String>();
                HashMap<String, DimensionType> dimNameTypeMapping = new HashMap<String, DimensionType>();
                HashMap<String, String> dimNameEntityIdMapping = new HashMap<String, String>();
                List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(params.getDataSchemeKey());
                for (DataDimension dataDimension : dataSchemeDimension) {
                    IEntityDefine entity;
                    if (DimensionType.UNIT.equals((Object)dataDimension.getDimensionType())) {
                        entity = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
                        dimNames.add(entity.getDimensionName());
                        dimNameTypeMapping.put(entity.getDimensionName(), DimensionType.UNIT);
                        continue;
                    }
                    if (DimensionType.PERIOD.equals((Object)dataDimension.getDimensionType())) {
                        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dataDimension.getDimKey());
                        dimNames.add(periodEntity.getDimensionName());
                        dimNameTypeMapping.put(periodEntity.getDimensionName(), DimensionType.PERIOD);
                        continue;
                    }
                    if (!DimensionType.DIMENSION.equals((Object)dataDimension.getDimensionType())) continue;
                    entity = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
                    if (null != entity) {
                        dimNames.add(entity.getDimensionName());
                        dimNameTypeMapping.put(entity.getDimensionName(), DimensionType.DIMENSION);
                        dimNameEntityIdMapping.put(entity.getDimensionName(), entity.getId());
                        continue;
                    }
                    dimNames.add("ADJUST");
                }
                ArrayList<String> headerCode = new ArrayList<String>(FieldDataFileConsts.ATTACHMENT_RELATION_HEADER);
                headerCode.addAll(dimNames);
                String[] csvHeads = new String[headerCode.size()];
                headerCode.toArray(csvHeads);
                csvWriter.writeRecord(csvHeads);
                csvWriter.flush();
                ParamsMapping paramsMapping = params.getParamsMapping();
                groupKeyFileInfos = null != paramsMapping ? this.expAttAndWriteCSVLine(params, csvWriter, dimNames, dimNameTypeMapping, dimNameEntityIdMapping, paramsMapping, logHelper) : this.expAttAndWriteCSVLine(params, csvWriter, dimNames, logHelper);
                csvWriter.flush();
                csvWriter.close();
                logHelper.info(null, null, "\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u6210\u529f", "\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u6210\u529f");
            }
            catch (Throwable csvWriter) {
                object = csvWriter;
                throw csvWriter;
            }
            finally {
                if (fos != null) {
                    if (object != null) {
                        try {
                            fos.close();
                        }
                        catch (Throwable csvWriter) {
                            ((Throwable)object).addSuppressed(csvWriter);
                        }
                    } else {
                        fos.close();
                    }
                }
            }
        }
        catch (IOException e) {
            logger.error("\u9644\u4ef6\u5bfc\u51fa\u9519\u8bef{}", (Object)e.getMessage());
            logHelper.error(null, null, "\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u5931\u8d25", "\u9519\u8bef\u539f\u56e0\uff1a" + e.getMessage());
            throw new IOException("\u9644\u4ef6\u5bfc\u51fa\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        commonParamsDTO.setDataSchemeKey(params.getDataSchemeKey());
        for (AttFieldDataInfo attFieldDataInfo : params.getAttFieldDataInfos()) {
            List<FileInfo> fileInfos;
            String groupKey = attFieldDataInfo.getGroupKey();
            if (StringUtils.isEmpty((String)groupKey) || CollectionUtils.isEmpty(fileInfos = groupKeyFileInfos.get(groupKey))) continue;
            for (FileInfo fileInfo : fileInfos) {
                String fileRelativePath = "attachment/" + groupKey + "/" + fileInfo.getKey();
                this.writeFiles(params.getFileWriter(), fileRelativePath, fileInfo, commonParamsDTO, logHelper);
            }
        }
    }

    private Map<String, List<FileInfo>> expAttAndWriteCSVLine(ExpFileParams params, CsvWriter csvWriter, List<String> dimNames, DataServiceLogHelper logHelper) throws IOException {
        HashMap<String, List<FileInfo>> groupKeyFileInfos = new HashMap<String, List<FileInfo>>();
        HashMap<String, String> fieldKeyTableCodeMapping = new HashMap<String, String>();
        HashSet<String> fieldKeys = new HashSet<String>();
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        commonParamsDTO.setDataSchemeKey(params.getDataSchemeKey());
        for (AttFieldDataInfo attFieldDataInfo : params.getAttFieldDataInfos()) {
            List fileInfos;
            String groupKey = attFieldDataInfo.getGroupKey();
            if (groupKey == null || (fileInfos = this.fileOperationService.getFileOrPicInfoByGroup(groupKey, commonParamsDTO)).isEmpty()) continue;
            groupKeyFileInfos.put(groupKey, fileInfos);
            fieldKeys.add(attFieldDataInfo.getFieldKey());
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList(fieldKeys));
        Map<String, String> fieldKeyCodeMapping = dataFields.stream().collect(Collectors.toMap(Basic::getKey, Basic::getCode));
        Set dataTableKeys = dataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
        List dataTables = this.runtimeDataSchemeService.getDataTables(new ArrayList(dataTableKeys));
        Map<String, String> tableKeyCodeMapping = dataTables.stream().collect(Collectors.toMap(Basic::getKey, Basic::getCode));
        for (DataField dataField : dataFields) {
            fieldKeyTableCodeMapping.put(dataField.getKey(), tableKeyCodeMapping.get(dataField.getDataTableKey()));
        }
        for (AttFieldDataInfo attFieldDataInfo : params.getAttFieldDataInfos()) {
            List fileInfos;
            String groupKey = attFieldDataInfo.getGroupKey();
            if (groupKey == null || CollectionUtils.isEmpty(fileInfos = (List)groupKeyFileInfos.get(groupKey))) continue;
            for (FileInfo fileInfo : fileInfos) {
                ArrayList<String> value = new ArrayList<String>();
                value.add(groupKey);
                value.add(fileInfo.getKey());
                value.add(fileInfo.getName());
                value.add(String.valueOf(fileInfo.getSize()));
                value.add(StringUtils.isEmpty((String)fileInfo.getSecretlevel()) ? "" : fileInfo.getSecretlevel());
                value.add(StringUtils.isEmpty((String)fileInfo.getCategory()) ? "" : fileInfo.getCategory());
                String fieldKey = attFieldDataInfo.getFieldKey();
                value.add((String)fieldKeyTableCodeMapping.get(fieldKey));
                value.add(fieldKeyCodeMapping.get(fieldKey));
                DimensionCombination dimensionCombination = attFieldDataInfo.getDimCombination();
                for (String dimName : dimNames) {
                    value.add((String)dimensionCombination.getValue(dimName));
                }
                String[] dataArray = new String[value.size()];
                value.toArray(dataArray);
                csvWriter.writeRecord(dataArray);
            }
        }
        return groupKeyFileInfos;
    }

    private Map<String, List<FileInfo>> expAttAndWriteCSVLine(ExpFileParams params, CsvWriter csvWriter, List<String> dimNames, Map<String, DimensionType> dimNameTypeMapping, Map<String, String> dimNameEntityIdMapping, ParamsMapping paramsMapping, DataServiceLogHelper logHelper) throws IOException {
        Set dataFieldCodes;
        Set dimValues;
        HashMap<String, List<FileInfo>> groupKeyFileInfos = new HashMap<String, List<FileInfo>>();
        HashMap<String, Set> dataTableDataFieldCode = new HashMap<String, Set>();
        HashMap<String, String> fieldKeyTableCodeMapping = new HashMap<String, String>();
        HashSet<String> fieldKeys = new HashSet<String>();
        HashSet<String> dwCodes = new HashSet<String>();
        HashSet<String> dataTimes = new HashSet<String>();
        HashMap<String, Set> entityIdDimValueMapping = new HashMap<String, Set>();
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        commonParamsDTO.setDataSchemeKey(params.getDataSchemeKey());
        for (AttFieldDataInfo attFieldDataInfo : params.getAttFieldDataInfos()) {
            Object fileInfos;
            String groupKey = attFieldDataInfo.getGroupKey();
            if (StringUtils.isEmpty((String)groupKey) || (fileInfos = this.fileOperationService.getFileOrPicInfoByGroup(groupKey, commonParamsDTO)).isEmpty()) continue;
            groupKeyFileInfos.put(groupKey, (List<FileInfo>)fileInfos);
            fieldKeys.add(attFieldDataInfo.getFieldKey());
            DimensionCombination dimensionCombination = attFieldDataInfo.getDimCombination();
            for (String dimName : dimNames) {
                DimensionType dimensionType = dimNameTypeMapping.get(dimName);
                if (DimensionType.UNIT == dimensionType) {
                    dwCodes.add((String)dimensionCombination.getValue(dimName));
                    continue;
                }
                if (DimensionType.PERIOD == dimensionType) {
                    dataTimes.add((String)dimensionCombination.getValue(dimName));
                    continue;
                }
                String entityId = dimNameEntityIdMapping.get(dimName);
                if (null == entityId) continue;
                dimValues = entityIdDimValueMapping.computeIfAbsent(entityId, k -> new HashSet());
                dimValues.add((String)dimensionCombination.getValue(dimName));
            }
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList(fieldKeys));
        Map<String, String> fieldKeyCodeMapping = dataFields.stream().collect(Collectors.toMap(Basic::getKey, Basic::getCode));
        Set dataTableKeys = dataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
        List dataTables = this.runtimeDataSchemeService.getDataTables(new ArrayList(dataTableKeys));
        for (Object dataTable : dataTables) {
            String dataTableCode = dataTable.getCode();
            dataFieldCodes = dataTableDataFieldCode.computeIfAbsent(dataTableCode, k -> new HashSet());
            for (DataField dataField : dataFields) {
                if (!dataTable.getKey().equals(dataField.getDataTableKey())) continue;
                dataFieldCodes.add(dataField.getCode());
                fieldKeyTableCodeMapping.put(dataField.getKey(), dataTableCode);
            }
        }
        HashMap<String, Map> tableCodeAndOriginDataFieldCodeMapping = new HashMap<String, Map>();
        for (String dataTableCode : dataTableDataFieldCode.keySet()) {
            dataFieldCodes = (Set)dataTableDataFieldCode.get(dataTableCode);
            tableCodeAndOriginDataFieldCodeMapping.put(dataTableCode, paramsMapping.getOriginDataFieldCode(dataTableCode, new ArrayList(dataFieldCodes)));
        }
        Map originOrgCode = paramsMapping.getOriginOrgCode(new ArrayList(dwCodes));
        Map originPeriod = paramsMapping.getOriginPeriod(new ArrayList(dataTimes));
        HashMap<String, Map> entityIdAndOriginBaseData = new HashMap<String, Map>();
        for (String entityId : entityIdDimValueMapping.keySet()) {
            dimValues = (Set)entityIdDimValueMapping.get(entityId);
            if (null == dimValues) continue;
            Map originBaseData = paramsMapping.getOriginBaseData(entityId, new ArrayList(dimValues));
            entityIdAndOriginBaseData.put(entityId, originBaseData);
        }
        for (AttFieldDataInfo attFieldDataInfo : params.getAttFieldDataInfos()) {
            List fileInfos;
            String groupKey = attFieldDataInfo.getGroupKey();
            if (StringUtils.isEmpty((String)groupKey) || CollectionUtils.isEmpty(fileInfos = (List)groupKeyFileInfos.get(groupKey))) continue;
            for (FileInfo fileInfo : fileInfos) {
                ArrayList<String> value = new ArrayList<String>();
                value.add(groupKey);
                value.add(fileInfo.getKey());
                value.add(fileInfo.getName());
                value.add(String.valueOf(fileInfo.getSize()));
                value.add(StringUtils.isEmpty((String)fileInfo.getSecretlevel()) ? "" : fileInfo.getSecretlevel());
                value.add(StringUtils.isEmpty((String)fileInfo.getCategory()) ? "" : fileInfo.getCategory());
                String fieldKey = attFieldDataInfo.getFieldKey();
                String tableCode = (String)fieldKeyTableCodeMapping.get(fieldKey);
                String fieldCode = fieldKeyCodeMapping.get(fieldKey);
                DataFieldMp dataFieldMp = (DataFieldMp)((Map)tableCodeAndOriginDataFieldCodeMapping.get(tableCode)).get(fieldCode);
                if (null != dataFieldMp) {
                    value.add(dataFieldMp.getTableCode());
                    value.add(dataFieldMp.getCode());
                } else {
                    value.add(tableCode);
                    value.add(fieldCode);
                }
                DimensionCombination dimensionCombination = attFieldDataInfo.getDimCombination();
                for (String dimName : dimNames) {
                    String originDimvalue;
                    String dimValue = (String)dimensionCombination.getValue(dimName);
                    DimensionType dimensionType = dimNameTypeMapping.get(dimName);
                    if (DimensionType.UNIT == dimensionType) {
                        originDimvalue = (String)originOrgCode.get(dimValue);
                        if (null != originDimvalue) {
                            value.add(originDimvalue);
                            continue;
                        }
                        value.add(dimValue);
                        continue;
                    }
                    if (DimensionType.PERIOD == dimensionType) {
                        originDimvalue = (String)originPeriod.get(dimValue);
                        if (null != originDimvalue) {
                            value.add(originDimvalue);
                            continue;
                        }
                        value.add(dimValue);
                        continue;
                    }
                    String entityId = dimNameEntityIdMapping.get(dimName);
                    if (null != entityId && null != entityIdAndOriginBaseData.get(entityId) && null != ((Map)entityIdAndOriginBaseData.get(entityId)).get(dimValue)) {
                        value.add((String)((Map)entityIdAndOriginBaseData.get(entityId)).get(dimValue));
                        continue;
                    }
                    value.add(dimValue);
                }
                String[] dataArray = new String[value.size()];
                value.toArray(dataArray);
                csvWriter.writeRecord(dataArray);
            }
        }
        return groupKeyFileInfos;
    }

    private void writeFiles(FileWriter fileWriter, String fileRelativePath, FileInfo fileInfo, CommonParamsDTO commonParamsDTO, DataServiceLogHelper logHelper) {
        try (OutputStream outputStream = fileWriter.createFile(fileRelativePath);){
            this.attachmentIOService.download(fileInfo.getKey(), commonParamsDTO, outputStream);
        }
        catch (IOException e) {
            logger.error("\u9644\u4ef6\u5bfc\u51fa\u9519\u8bef{}", (Object)e.getMessage());
            throw new RuntimeException("\u6307\u6807\u9644\u4ef6\u5bfc\u51fa\u5931\u8d25", e);
        }
    }
}

