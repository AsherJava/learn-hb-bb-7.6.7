/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.common.service.DescRecorder
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.ImpSettings
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.Result
 *  com.jiuqi.nr.data.common.service.TaskDataFactory
 *  com.jiuqi.nr.data.common.service.TransferContext
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.option.internal.UnitEdit
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.BatchFMDMDTO
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.IFMDMUpdateResult
 *  com.jiuqi.nr.io.tsd.dto.DefaultTransferContext
 *  com.jiuqi.nr.tds.ArrayRow
 *  com.jiuqi.nr.tds.TdColumn
 *  com.jiuqi.nr.tds.TdModel
 *  com.jiuqi.nr.tds.TdRowData
 *  com.jiuqi.nr.tds.api.DataTableReader
 *  com.jiuqi.nr.tds.api.DataTableWriter
 *  com.jiuqi.nr.tds.api.TdStoreFactory
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.nr.data.text.service.fmdm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.Version;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.common.service.DescRecorder;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.ImpSettings;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.Result;
import com.jiuqi.nr.data.common.service.TaskDataFactory;
import com.jiuqi.nr.data.common.service.TransferContext;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.data.text.exception.ExportTaskDataException;
import com.jiuqi.nr.data.text.exception.ImportTaskDataException;
import com.jiuqi.nr.data.text.service.fmdm.FmdmImportResult;
import com.jiuqi.nr.data.text.service.fmdm.FmdmParamInfo;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.option.internal.UnitEdit;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.IFMDMUpdateResult;
import com.jiuqi.nr.io.tsd.dto.DefaultTransferContext;
import com.jiuqi.nr.tds.ArrayRow;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.api.DataTableReader;
import com.jiuqi.nr.tds.api.DataTableWriter;
import com.jiuqi.nr.tds.api.TdStoreFactory;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=10)
public class FmdmTaskDataFactory
implements TaskDataFactory {
    @Value(value="${jiuqi.nr.transmission.FMDM.entity.not.import:false}")
    private boolean notImportFmdmEntityField;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;
    @Autowired
    private UnitEdit unitEdit;
    private static final String FMDM_FACTORY_CODE = "FMDM_DATA";
    private static final Version VERSION_1_0_0 = new Version(1, 0, 0);
    private static final String FMDM_DATA_BDF_PATH = "DATA";
    private static final String FMDM_PARAMINFO_JSON_PATH = "PARAM_INFO.json";
    private static final String UNIT_CODE_KEY = "FMDMUNITCODE";
    private static final String DOT = ".";
    private static final String SPLIT_DOT = "\\.";
    private static final Logger logger = LoggerFactory.getLogger(FmdmTaskDataFactory.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String TN_EXP = "FM_EXP";
    private static final String TN_EXP_EXPDATA = "FM_EXP_EXPDATA";
    private static final String TN_EXP_EXPDATA_HEADER = "TN_EXP_EXPDATA_HEADER";
    private static final String TN_EXP_EXPDATA_DATA = "TN_EXP_EXPDATA_DATA";
    private static final String TN_IMP = "TN_IMP";
    private static final String TN_IMP_DATA = "TN_IMP_DATA";

    public String getCode() {
        return FMDM_FACTORY_CODE;
    }

    public String getName() {
        return "\u5c01\u9762\u4ee3\u7801\u6570\u636e";
    }

    public String getDescription() {
        return "";
    }

    public Version getVersion() {
        return VERSION_1_0_0;
    }

    public void exportTaskData(TransferContext context, FileWriter writer) {
        TdColumn tdColumn;
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("FMDM_DATA_EXPORT_" + VERSION_1_0_0, OperLevel.USER_OPER);
        logHelper.info(null, null, "\u5f00\u59cb\u5bfc\u51fa\u5e76\u538b\u7f29FMDM\u6570\u636e\u6587\u4ef6", "\u5f00\u59cb\u5bfc\u51fa\u5e76\u538b\u7f29FMDM\u6570\u636e\u6587\u4ef6");
        IProgressMonitor progressMonitor = context.getProgressMonitor();
        progressMonitor.startTask(TN_EXP, new int[]{8, 1, 1});
        progressMonitor.startTask(TN_EXP_EXPDATA, new int[]{5, 5});
        String taskKey = context.getTaskKey();
        String formSchemeKey = context.getFormSchemeKey();
        DimensionCollection dimensionCollection = context.getMasterKeys();
        ParamsMapping paramsMapping = context.getParamsMapping();
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String entityDefineCode = entityDefine == null ? "" : entityDefine.getCode();
        TdModel tdModel = new TdModel();
        tdModel.setName(FMDM_FACTORY_CODE);
        progressMonitor.startTask(TN_EXP_EXPDATA_HEADER, new int[]{2, 2, 2, 1, 1, 2});
        FmdmAttribute fmdmAttribute = this.queryFmdmAttribute(formSchemeKey);
        List<IFMDMAttribute> fmdmAttributes = fmdmAttribute.getShowAttributes();
        String unitCodeName = fmdmAttribute.getUnitCodeName();
        String parentCodeName = fmdmAttribute.getParentCodeName();
        Map<Boolean, List<IFMDMAttribute>> partitionMap = fmdmAttributes.stream().collect(Collectors.partitioningBy(e -> e.getColumnType() != ColumnModelType.ATTACHMENT));
        List<IFMDMAttribute> exportFmdmAttributes = partitionMap.get(true);
        List<IFMDMAttribute> skipFmdmAttributes = partitionMap.get(false);
        progressMonitor.stepIn();
        HashMap dataFieldMappig = new HashMap();
        HashMap sysField2Table = new HashMap();
        HashMap defField2Table = new HashMap();
        List<String> orgFieldCodes = exportFmdmAttributes.stream().filter(e -> ColumnModelKind.SYSTEM.equals((Object)e.getKind())).map(IModelDefineItem::getCode).collect(Collectors.toList());
        orgFieldCodes.forEach(orgFieldCode -> sysField2Table.put(orgFieldCode, entityDefineCode));
        if (paramsMapping != null && paramsMapping.tryDataFieldMap(entityDefineCode)) {
            dataFieldMappig.putAll(paramsMapping.getOriginDataFieldCode(entityDefineCode, orgFieldCodes));
        }
        progressMonitor.stepIn();
        List zbKeys = exportFmdmAttributes.stream().filter(e -> ColumnModelKind.DEFAULT.equals((Object)e.getKind())).map(IFMDMAttribute::getZBKey).collect(Collectors.toList());
        Map<String, List<DataField>> tableKeyToFieldMap = this.runtimeDataSchemeService.getDataFields(zbKeys).stream().collect(Collectors.groupingBy(DataField::getDataTableKey));
        List dataTables = this.runtimeDataSchemeService.getDataTables(new ArrayList<String>(tableKeyToFieldMap.keySet()));
        for (Object dataTable : dataTables) {
            String dataTableCode = dataTable.getCode();
            List<String> fieldCodes = tableKeyToFieldMap.get(dataTable.getKey()).stream().map(Basic::getCode).collect(Collectors.toList());
            fieldCodes.forEach(fieldCode -> defField2Table.put(fieldCode, dataTableCode));
            if (paramsMapping == null || !paramsMapping.tryDataFieldMap(dataTableCode)) continue;
            dataFieldMappig.putAll(paramsMapping.getOriginDataFieldCode(dataTableCode, fieldCodes));
        }
        progressMonitor.stepIn();
        int columnCount = 0;
        for (IFMDMAttribute attribute : exportFmdmAttributes) {
            Object code = attribute.getCode();
            String tableCode = ColumnModelKind.SYSTEM.equals((Object)attribute.getKind()) ? (String)sysField2Table.get(code) : (String)defField2Table.get(code);
            code = Optional.ofNullable(dataFieldMappig.get(code)).map(DataFieldMp::getCode).filter(StringUtils::isNotEmpty).orElse((String)code);
            tdColumn = new TdColumn(String.join((CharSequence)DOT, new CharSequence[]{tableCode, code}), attribute.getColumnType().getValue(), attribute.getPrecision(), attribute.getDecimal());
            tdColumn.setNullable(attribute.isNullAble());
            tdModel.getColumns().add(tdColumn);
            ++columnCount;
        }
        progressMonitor.stepIn();
        TdColumn unitCodeColumn = new TdColumn(UNIT_CODE_KEY, 6);
        tdModel.getColumns().add(unitCodeColumn);
        ++columnCount;
        progressMonitor.stepIn();
        List<DimensionInfo> dataDimension = this.getTaskDataDimension(taskDefine);
        for (DimensionInfo dimensionInfo : dataDimension) {
            if (!DimensionType.DIMENSION.equals((Object)dimensionInfo.getDimensionType())) continue;
            tdColumn = new TdColumn(dimensionInfo.getDimName(), 6);
            tdModel.getColumns().add(tdColumn);
            ++columnCount;
        }
        progressMonitor.stepIn();
        progressMonitor.finishTask(TN_EXP_EXPDATA_HEADER);
        progressMonitor.stepIn();
        progressMonitor.startTask(TN_EXP_EXPDATA_DATA, new int[]{2, 6, 2});
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        fmdmDataDTO.setFormSchemeKey(formSchemeKey);
        fmdmDataDTO.setSortedByQuery(false);
        List fmdmDataList = this.fmdmDataService.list(fmdmDataDTO, dimensionCollection);
        progressMonitor.stepIn();
        TdStoreFactory storeFactory = new TdStoreFactory();
        try {
            DataTableWriter dataTableWriter = storeFactory.getDataTableWriter(tdModel, TdStoreFactory.VERSION_1_0_0);
            for (IFMDMData fmdmData : fmdmDataList) {
                ArrayRow rowData = new ArrayRow(columnCount);
                int index = 0;
                String unitCodeValue = fmdmData.getFMDMKey();
                if (paramsMapping != null && paramsMapping.tryOrgCodeMap()) {
                    unitCodeValue = Optional.ofNullable(paramsMapping.getOriginOrgCode(Collections.singletonList(unitCodeValue)).get(unitCodeValue)).filter(StringUtils::isNotEmpty).orElse(unitCodeValue);
                }
                for (IFMDMAttribute attribute : exportFmdmAttributes) {
                    Object value = this.getExportValueByType(fmdmData.getValue(attribute.getCode()), attribute.getColumnType().getValue());
                    if (unitCodeName.equals(attribute.getCode())) {
                        value = unitCodeValue;
                    }
                    if (parentCodeName.equals(attribute.getCode()) && paramsMapping != null && paramsMapping.tryOrgCodeMap()) {
                        value = Optional.ofNullable(paramsMapping.getOriginOrgCode(Collections.singletonList((String)value)).get(value)).filter(StringUtils::isNotEmpty).orElse((String)value);
                    }
                    rowData.setValue(index, value);
                    ++index;
                }
                rowData.setValue(index, (Object)unitCodeValue);
                for (DimensionInfo dimensionInfo : dataDimension) {
                    if (!DimensionType.DIMENSION.equals((Object)dimensionInfo.getDimensionType())) continue;
                    rowData.setValue(++index, fmdmData.getMasterKey().getValue(dimensionInfo.getDimName()));
                }
                dataTableWriter.appendRow((TdRowData)rowData);
            }
            dataTableWriter.flush();
            progressMonitor.stepIn();
            dataTableWriter.close();
            File dataFile = dataTableWriter.getFile();
            if (dataFile != null) {
                writer.addFile(FMDM_DATA_BDF_PATH, dataFile);
            }
            dataTableWriter.destroy();
            progressMonitor.stepIn();
            progressMonitor.finishTask(TN_EXP_EXPDATA_DATA);
            progressMonitor.stepIn();
        }
        catch (IOException e2) {
            String message = "\u83b7\u53d6\u53ca\u538b\u7f29BDF\u6587\u4ef6\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38";
            logHelper.error(null, null, message, e2.getMessage());
            logger.error("{}\uff1a{}", message, e2.getMessage(), e2);
            progressMonitor.finishTask(TN_EXP_EXPDATA_DATA);
            progressMonitor.finishTask(TN_EXP_EXPDATA);
            progressMonitor.finishTask(TN_EXP);
            throw new ExportTaskDataException(message + "\uff1a" + e2.getMessage(), e2);
        }
        progressMonitor.finishTask(TN_EXP_EXPDATA);
        progressMonitor.stepIn();
        FmdmParamInfo fmdmParamInfo = new FmdmParamInfo();
        fmdmParamInfo.setVersion(VERSION_1_0_0.toString());
        try {
            writer.addBytes(FMDM_PARAMINFO_JSON_PATH, mapper.writeValueAsBytes((Object)fmdmParamInfo));
        }
        catch (Exception e3) {
            String message = "\u538b\u7f29PARAM_INFO.json\u6587\u4ef6\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38";
            logHelper.error(null, null, message, e3.getMessage());
            logger.error("{}\uff1a{}", message, e3.getMessage(), e3);
            progressMonitor.finishTask(TN_EXP);
            throw new ExportTaskDataException(message + "\uff1a" + e3.getMessage(), e3);
        }
        progressMonitor.stepIn();
        StringBuilder message = new StringBuilder("\u5bfc\u51faFMDM\u6570\u636e\u6587\u4ef6\u6210\u529f\u5e76\u538b\u7f29\u5b8c\u6210");
        if (!skipFmdmAttributes.isEmpty()) {
            message = new StringBuilder("\u5c01\u9762\u4ee3\u7801\u62a5\u8868\u5185\uff0c\u4ee5\u4e0b\u6307\u6807\u4e3a\u56fe\u7247\u6216\u9644\u4ef6\u7c7b\u578b\u6307\u6807\uff0c\u4e0d\u8fdb\u884c\u5bfc\u51fa\uff1a");
            for (IFMDMAttribute attribute : exportFmdmAttributes) {
                message.append(attribute.getTitle()).append("[").append(attribute.getCode()).append("]\uff0c");
            }
            message.replace(message.length() - 1, message.length(), "\uff01");
        }
        logHelper.info(null, null, "\u5bfc\u51faFMDM\u6570\u636e\u6587\u4ef6\u6210\u529f\u5e76\u538b\u7f29\u5b8c\u6210", message.toString());
        progressMonitor.stepIn();
        progressMonitor.finishTask(TN_EXP);
    }

    public void importTaskData(TransferContext context, FileFinder finder) {
        List<String> headers;
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("FMDM_DATA_IMPORT_" + VERSION_1_0_0, OperLevel.USER_OPER);
        logHelper.info(null, null, "\u5f00\u59cb\u89e3\u6790\u5e76\u5bfc\u5165FMDM\u6570\u636e\u6587\u4ef6", "\u5f00\u59cb\u89e3\u6790\u5e76\u5bfc\u5165FMDM\u6570\u636e\u6587\u4ef6");
        IProgressMonitor progressMonitor = context.getProgressMonitor();
        progressMonitor.startTask(TN_IMP, new int[]{1, 8, 1});
        try {
            byte[] jsonBytes = finder.getFileBytes(FMDM_PARAMINFO_JSON_PATH);
            if (jsonBytes == null || jsonBytes.length == 0) {
                throw new ImportTaskDataException("PARAM.JSON\u6587\u4ef6\u4e3a\u7a7a\u6216\u4e0d\u5b58\u5728\uff01");
            }
            FmdmParamInfo fmdmParamInfo = (FmdmParamInfo)mapper.readValue(jsonBytes, FmdmParamInfo.class);
            if (fmdmParamInfo == null) {
                throw new ImportTaskDataException("PARAM.JSON\u6587\u4ef6\u4e3a\u7a7a");
            }
            String version = fmdmParamInfo.getVersion();
            if (StringUtils.isEmpty((String)version) || !version.equals(VERSION_1_0_0.toString())) {
                throw new ImportTaskDataException("\u672a\u4ecePARAM.JSON\u6587\u4ef6\u4e2d\u83b7\u53d6\u5230\u6b63\u786e\u7684\u7248\u672c\u4fe1\u606f\uff01");
            }
        }
        catch (IOException e2) {
            String message = "\u89e3\u6790PARAM.JSON\u6587\u4ef6\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38";
            logHelper.error(null, null, message, e2.getMessage());
            logger.error("{}\uff1a{}", message, e2.getMessage(), e2);
            progressMonitor.finishTask(TN_IMP);
            throw new ImportTaskDataException(message + "\uff1a" + e2.getMessage(), e2);
        }
        catch (ImportTaskDataException e3) {
            logHelper.error(null, null, "\u83b7\u53d6VERSION\u5931\u8d25", e3.getMessage());
            logger.error("\u83b7\u53d6VERSION\u5931\u8d25\uff1a{}", (Object)e3.getMessage(), (Object)e3);
            progressMonitor.finishTask(TN_IMP);
            throw e3;
        }
        progressMonitor.stepIn();
        progressMonitor.startTask(TN_IMP_DATA, new int[]{1, 1, 1, 3, 3, 1});
        ArrayList<Object[]> allRowDatas = new ArrayList<Object[]>();
        TdStoreFactory storeFactory = new TdStoreFactory();
        DataTableReader dataTableReader = null;
        File dataBdfFile = null;
        try {
            dataTableReader = storeFactory.getDataTableReader();
            dataBdfFile = finder.getFile(FMDM_DATA_BDF_PATH);
            dataTableReader.open(dataBdfFile, FMDM_FACTORY_CODE);
            headers = dataTableReader.columns().stream().map(TdColumn::getName).collect(Collectors.toList());
            while (dataTableReader.hasNext()) {
                TdRowData rowData = dataTableReader.next();
                allRowDatas.add(rowData.toArray());
            }
        }
        catch (IOException e4) {
            String message = "\u8bfb\u53d6DATA.BDF\u6570\u636e\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38";
            logHelper.error(null, null, message, e4.getMessage());
            logger.error("{}{}", message, e4.getMessage(), e4);
            progressMonitor.finishTask(TN_IMP_DATA);
            progressMonitor.finishTask(TN_IMP);
            throw new ImportTaskDataException(message + "\uff1a" + e4.getMessage(), e4);
        }
        finally {
            boolean delete;
            if (dataTableReader != null) {
                try {
                    dataTableReader.close();
                }
                catch (IOException e5) {
                    logger.error("\u8bfb\u53d6\u5c01\u9762\u4ee3\u7801\u6570\u636e\u8fc7\u7a0b\u4e2d\uff0c\u5173\u95edDataTableReader\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e5.getMessage(), (Object)e5);
                }
                dataTableReader.destroy();
            }
            if (dataBdfFile != null && !(delete = dataBdfFile.delete())) {
                logger.error("\u5220\u9664\u5c01\u9762\u4ee3\u7801BDF\u4e34\u65f6\u6570\u636e\u6587\u4ef6\u5931\u8d25\uff01");
            }
        }
        progressMonitor.stepIn();
        String taskKey = context.getTaskKey();
        String formSchemeKey = context.getFormSchemeKey();
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String entityDefineCode = entityDefine == null ? "" : entityDefine.getCode();
        ParamsMapping paramsMapping = context.getParamsMapping();
        ImpSettings impSettings = context.getImportSettings();
        List nonexistentUnit = Optional.ofNullable(impSettings).map(ImpSettings::getNonexistentUnits).orElseGet(() -> ((TransferContext)context).getNonexistentUnits());
        DimensionCollection dimensionCollection = Optional.ofNullable(impSettings).map(ImpSettings::getMasterKeys).orElseGet(() -> ((TransferContext)context).getMasterKeys());
        ArrayList<DimensionValueSet> allDimensionValueSets = new ArrayList<DimensionValueSet>();
        if (dimensionCollection != null) {
            dimensionCollection.getDimensionCombinations().forEach(e -> allDimensionValueSets.add(e.toDimensionValueSet()));
        }
        ImportCodeInfo importCodeInfo = this.filterImportFields(headers, formSchemeKey, entityDefineCode, paramsMapping);
        Map<String, Integer> importCodes = importCodeInfo.getImportCodes();
        int fmdmUnitCodeIndex = importCodes.get(UNIT_CODE_KEY);
        int unitCodeIndex = importCodeInfo.getUnitCodeIndex();
        int parentCodeIndex = importCodeInfo.getParentCodeIndex();
        progressMonitor.stepIn();
        Map unitCodeValueMapping = new HashMap();
        Map parentCodeValueMapping = new HashMap();
        String dataTime = Optional.of(allDimensionValueSets).filter(list -> !list.isEmpty()).map(list -> (DimensionValueSet)list.get(0)).map(e -> e.getValue("DATATIME")).map(Object::toString).orElse("");
        if (StringUtils.isEmpty((String)dataTime) && context instanceof DefaultTransferContext) {
            dataTime = ((DefaultTransferContext)context).getPeriodValue();
        }
        if (paramsMapping != null) {
            if (paramsMapping.tryOrgCodeMap()) {
                List fmdmUnitCodes = allRowDatas.stream().map(e -> (String)e[fmdmUnitCodeIndex]).collect(Collectors.toList());
                unitCodeValueMapping = paramsMapping.getOriginOrgCode(fmdmUnitCodes);
                if (parentCodeIndex > -1) {
                    List parentCodes = allRowDatas.stream().map(e -> (String)e[parentCodeIndex]).collect(Collectors.toList());
                    parentCodeValueMapping = paramsMapping.getOriginOrgCode(parentCodes);
                }
            }
            if (paramsMapping.tryPeriodMap() && StringUtils.isNotEmpty((String)dataTime)) {
                dataTime = Optional.ofNullable(paramsMapping.getOriginPeriod(Collections.singletonList(dataTime)).get(dataTime)).orElse(dataTime);
            }
        }
        progressMonitor.stepIn();
        DescRecorder descRecorder = context.getDescRecorder(FMDM_FACTORY_CODE);
        ArrayList<Object[]> addDatas = new ArrayList<Object[]>();
        ArrayList<Object[]> updateDatas = new ArrayList<Object[]>();
        HashSet<String> addCodes = new HashSet<String>();
        for (Object[] rowData : allRowDatas) {
            String code = (String)rowData[fmdmUnitCodeIndex];
            code = Optional.ofNullable(unitCodeValueMapping.get(code)).orElse(code);
            rowData[fmdmUnitCodeIndex] = code;
            if (unitCodeIndex > -1) {
                rowData[unitCodeIndex] = code;
            }
            if (parentCodeIndex > -1) {
                String parentCode = (String)rowData[parentCodeIndex];
                parentCode = Optional.ofNullable(parentCodeValueMapping.get(parentCode)).orElse(parentCode);
                rowData[parentCodeIndex] = parentCode;
            }
            if (nonexistentUnit.contains(code) && !addCodes.contains(code)) {
                addDatas.add(rowData);
                addCodes.add(code);
                continue;
            }
            updateDatas.add(rowData);
        }
        FmdmImportResult importResult = new FmdmImportResult();
        String entityID = taskDefine.getDw();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityID);
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        String bizKeyFieldCode = bizKeyField.getCode();
        String dwDimName = this.entityMetaService.getDimensionName(entityID);
        importResult.setDwDimName(dwDimName);
        IFMDMUpdateResult addResult = null;
        List addKeys = new ArrayList();
        if (!addDatas.isEmpty()) {
            importResult.setAddSuccess(true);
            importResult.getAddFailedUnits().addAll(nonexistentUnit);
            String optionValue = this.taskOptionController.getValue(taskDefine.getKey(), this.unitEdit.getKey());
            if ("1".equals(optionValue)) {
                try {
                    addResult = this.saveFmdm(addDatas, importCodeInfo, entityDefineCode, dataTime, null, context, bizKeyFieldCode, dwDimName, true);
                    if (addResult != null) {
                        addKeys = addResult.getUpdateKeys();
                        importResult.getAddSuccessfulUnits().addAll(addKeys);
                        importResult.getAddFailedUnits().clear();
                        allDimensionValueSets.addAll(addResult.getUpdateDimensions());
                        if (nonexistentUnit.size() >= addKeys.size()) {
                            addKeys.forEach(nonexistentUnit::remove);
                            importResult.getAddFailedUnits().addAll(nonexistentUnit);
                        }
                    } else {
                        importResult.setAddSuccess(false);
                    }
                    descRecorder.addDesc(nonexistentUnit, "\u672a\u77e5\u539f\u56e0\u5bfc\u81f4\u5355\u4f4d\u65b0\u589e\u5f02\u5e38\uff01");
                }
                catch (Exception e6) {
                    descRecorder.addDesc(nonexistentUnit, "\u65b0\u589e\u8fc7\u7a0b\u4e2d\u9047\u5230\u5f02\u5e38\uff1a" + e6.getMessage() + "\uff01");
                    importResult.setAddSuccess(false);
                    logger.error("\u6279\u91cf\u65b0\u589e\u5c01\u9762\u4ee3\u7801\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e6.getMessage(), (Object)e6);
                }
            } else {
                descRecorder.addDesc(nonexistentUnit, "\u5f53\u524d\u4efb\u52a1\u7981\u6b62\u7f16\u8f91\u5355\u4f4d\uff0c\u5355\u4f4d\u65b0\u589e\u5931\u8d25\uff01");
            }
        }
        progressMonitor.stepIn();
        if (!updateDatas.isEmpty()) {
            try {
                Set<String> addFailedUnits = importResult.getAddFailedUnits();
                while (!updateDatas.isEmpty()) {
                    HashSet<String> currentUpdateUnits = new HashSet<String>();
                    ArrayList<Object[]> currentUpdateDatas = new ArrayList<Object[]>();
                    Iterator iterator = updateDatas.iterator();
                    while (iterator.hasNext()) {
                        Object[] rowData = (Object[])iterator.next();
                        String unitCode = (String)rowData[fmdmUnitCodeIndex];
                        if (addFailedUnits.contains(unitCode)) {
                            iterator.remove();
                            continue;
                        }
                        if (currentUpdateUnits.contains(unitCode)) continue;
                        currentUpdateUnits.add(unitCode);
                        currentUpdateDatas.add(rowData);
                        iterator.remove();
                    }
                    IFMDMUpdateResult updateResult = this.saveFmdm(currentUpdateDatas, importCodeInfo, entityDefineCode, dataTime, addResult, context, bizKeyFieldCode, dwDimName, false);
                    if (updateResult == null) continue;
                    Set noAccessDims = updateResult.getNoAccessDims();
                    importResult.getNoAccessDims().addAll(noAccessDims);
                    noAccessDims.forEach(e -> {
                        String unitCode = Optional.ofNullable(e.getValue(dwDimName)).map(Object::toString).orElse("");
                        descRecorder.addDesc(unitCode, "\u5355\u4f4d" + unitCode + "\u5bf9\u5c01\u9762\u4ee3\u7801\u6ca1\u6709\u5199\u5165\u6743\u9650\uff0c\u66f4\u65b0\u5931\u8d25\uff01");
                    });
                    if (updateResult.getUpdateDimensions() == null) continue;
                    for (DimensionValueSet dimensionValueSet : updateResult.getUpdateDimensions()) {
                        String unitCode = dimensionValueSet.getValue(dwDimName).toString();
                        importResult.getUpdateSuccessUnits().add(unitCode);
                        if (addKeys.isEmpty() || !addKeys.contains(unitCode)) continue;
                        allDimensionValueSets.add(dimensionValueSet);
                    }
                }
                importResult.setUpdateSuccess(true);
            }
            catch (Exception e7) {
                Set units = updateDatas.stream().map(obj -> Optional.ofNullable(obj[fmdmUnitCodeIndex]).map(Object::toString).orElse("")).collect(Collectors.toSet());
                descRecorder.addDesc(new ArrayList(units), "\u66f4\u65b0\u8fc7\u7a0b\u4e2d\u9047\u5230\u5f02\u5e38\uff1a" + e7.getMessage() + "\uff01");
                importResult.setUpdateSuccess(false);
                logger.error("\u6279\u91cf\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e7.getMessage(), (Object)e7);
            }
        }
        progressMonitor.stepIn();
        if (!addKeys.isEmpty()) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(allDimensionValueSets);
            context.getImportSettings().resetMasterKeys(this.dimensionBuildUtil.getDimensionCollection(dimensionValueSet, formSchemeKey));
        }
        progressMonitor.stepIn();
        progressMonitor.finishTask(TN_IMP_DATA);
        progressMonitor.stepIn();
        context.setResult(FMDM_FACTORY_CODE, (Result)importResult);
        logHelper.info(null, null, "\u5bfc\u5165FMDM\u6570\u636e\u5b8c\u6210\uff01", importResult.getMessage());
        progressMonitor.stepIn();
        progressMonitor.finishTask(TN_IMP);
    }

    private ImportCodeInfo filterImportFields(List<String> headers, String formSchemeKey, String entityDefineCode, ParamsMapping paramsMapping) {
        HashMap<String, Integer> importCodes = new HashMap<String, Integer>();
        HashMap<String, Integer> codeToType = new HashMap<String, Integer>();
        int unitCodeIndex = -1;
        int parentCodeIndex = -1;
        FmdmAttribute fmdmAttribute = this.queryFmdmAttribute(formSchemeKey);
        List<IFMDMAttribute> fmdmAttributes = fmdmAttribute.getShowAttributes();
        String unitCodeName = fmdmAttribute.getUnitCodeName();
        String parentCodeName = fmdmAttribute.getParentCodeName();
        ArrayList entityFiledCodes = new ArrayList();
        ArrayList dataFieldCodes = new ArrayList();
        fmdmAttributes.forEach(attribute -> {
            if (attribute.getColumnType() != ColumnModelType.ATTACHMENT) {
                String code = attribute.getCode();
                if (attribute.getKind().equals((Object)ColumnModelKind.SYSTEM)) {
                    entityFiledCodes.add(code);
                    codeToType.put(String.join((CharSequence)DOT, entityDefineCode, code), attribute.getColumnType().getValue());
                } else {
                    dataFieldCodes.add(code);
                    codeToType.put(code, attribute.getColumnType().getValue());
                }
            }
        });
        Map tableToFields = headers.stream().filter(header -> header.contains(DOT)).map(header -> header.split(SPLIT_DOT)).filter(splits -> ((String[])splits).length >= 2).collect(Collectors.groupingBy(splits -> splits[0], Collectors.mapping(splits -> splits[1], Collectors.toList())));
        HashMap fieldMappings = new HashMap();
        for (int i = 0; i < headers.size(); ++i) {
            String header2 = headers.get(i);
            if (header2.contains(DOT)) {
                String[] splits2 = header2.split(SPLIT_DOT);
                String dataTableCode = splits2[0];
                String fieldCode = splits2[1];
                if (paramsMapping != null && paramsMapping.tryDataFieldMap(dataTableCode)) {
                    String mappingCode;
                    DataFieldMp dataFieldMp;
                    Map fieldMapping = (Map)fieldMappings.get(dataTableCode);
                    if (fieldMapping == null) {
                        fieldMapping = Optional.ofNullable(paramsMapping.getOriginDataFieldCode(dataTableCode, tableToFields.get(dataTableCode))).orElse(Collections.emptyMap());
                        fieldMappings.put(dataTableCode, fieldMapping);
                    }
                    String mappingTableCode = (dataFieldMp = (DataFieldMp)fieldMapping.get(fieldCode)) == null ? dataTableCode : dataFieldMp.getTableCode();
                    String string = mappingCode = dataFieldMp == null ? fieldCode : dataFieldMp.getCode();
                    if (entityDefineCode.equals(mappingTableCode) && (this.notImportFmdmEntityField || !entityFiledCodes.contains(mappingCode)) || !entityDefineCode.equals(mappingTableCode) && !dataFieldCodes.contains(mappingCode)) continue;
                    dataTableCode = mappingTableCode;
                    fieldCode = mappingCode;
                    header2 = String.join((CharSequence)DOT, dataTableCode, fieldCode);
                }
                if (dataTableCode.equals(entityDefineCode)) {
                    if (fieldCode.equals(unitCodeName)) {
                        unitCodeIndex = i;
                    }
                    if (fieldCode.equals(parentCodeName)) {
                        parentCodeIndex = i;
                    }
                }
            }
            importCodes.put(header2, i);
        }
        return new ImportCodeInfo(importCodes, codeToType, unitCodeIndex, parentCodeIndex);
    }

    private IFMDMUpdateResult saveFmdm(List<Object[]> saveData, ImportCodeInfo importCodeInfo, String entityDefineCode, String dataTime, IFMDMUpdateResult addResult, TransferContext context, String bizKeyFieldCode, String dwDimName, boolean isAdd) throws Exception {
        String formSchemeKey = context.getFormSchemeKey();
        IProviderStore providerStore = context.getProviderStore();
        ImpSettings impSettings = context.getImportSettings();
        CompletionDim completionDims = impSettings.getCompletionDims();
        FilterDim filterDims = impSettings.getFilterDims();
        List dynamicsFilterDims = Optional.ofNullable(filterDims.getDynamicsFilterDims()).orElse(Collections.emptyList());
        DimensionValueSet fixedFilterDims = Optional.ofNullable(filterDims.getFixedFilterDims()).orElse(new DimensionValueSet());
        Map<String, Integer> importCodes = importCodeInfo.getImportCodes();
        Map<String, Integer> codeToType = importCodeInfo.getCodeToType();
        int fmdmUnitCodeIndex = importCodes.get(UNIT_CODE_KEY);
        int unitCodeIndex = importCodeInfo.getUnitCodeIndex();
        int parentCodeIndex = importCodeInfo.getParentCodeIndex();
        BatchFMDMDTO batchFMDMDTO = new BatchFMDMDTO();
        batchFMDMDTO.setProviderStore(providerStore);
        batchFMDMDTO.setFormSchemeKey(formSchemeKey);
        DimensionValueSet batchDim = new DimensionValueSet();
        batchDim.setValue("DATATIME", (Object)dataTime);
        batchFMDMDTO.setDimensionValueSet(batchDim);
        ArrayList<FMDMDataDTO> fmdmList = new ArrayList<FMDMDataDTO>();
        for (Object[] rowData : saveData) {
            FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
            DimensionValueSet dimensionValueSet = completionDims.isCompletionDim() ? Optional.ofNullable(completionDims.getFixedCompletionDims()).orElse(new DimensionValueSet()) : new DimensionValueSet();
            for (Map.Entry<String, Integer> entry : importCodes.entrySet()) {
                String code = entry.getKey();
                int index = entry.getValue();
                if (index < fmdmUnitCodeIndex) {
                    Object value;
                    String[] splits = code.split(SPLIT_DOT);
                    if (splits.length != 2) continue;
                    code = splits[1];
                    if (entityDefineCode.equals(splits[0])) {
                        value = this.getImportValueByType(rowData[index], codeToType.get(String.join((CharSequence)DOT, entityDefineCode, code)));
                        if (addResult != null && value != null && (unitCodeIndex == index || parentCodeIndex == index)) {
                            value = Optional.ofNullable(addResult.getSaveKey((String)value)).orElse((String)value);
                        }
                        fmdmDataDTO.setEntityValue(code, value);
                        continue;
                    }
                    value = this.getImportValueByType(rowData[index], codeToType.get(code));
                    fmdmDataDTO.setValue(code, value);
                    continue;
                }
                if (index == fmdmUnitCodeIndex) {
                    fmdmDataDTO.setValue(bizKeyFieldCode, rowData[index]);
                    dimensionValueSet.setValue(dwDimName, rowData[index]);
                    continue;
                }
                if (filterDims.isFilterDim() && (dynamicsFilterDims.contains(code) || fixedFilterDims.getValue(code) != null)) continue;
                dimensionValueSet.setValue(code, rowData[index]);
            }
            dimensionValueSet.setValue("DATATIME", (Object)dataTime);
            fmdmDataDTO.setDimensionCombination(new DimensionCombinationBuilder(dimensionValueSet).getCombination());
            fmdmList.add(fmdmDataDTO);
        }
        batchFMDMDTO.setFmdmList(fmdmList);
        if (isAdd) {
            return this.fmdmDataService.batchAddFMDM(batchFMDMDTO);
        }
        return this.fmdmDataService.batchUpdateFMDM(batchFMDMDTO);
    }

    private FmdmAttribute queryFmdmAttribute(String formSchemeKey) {
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
        List showAttributes = this.fmdmAttributeService.listShowAttribute(fmdmAttributeDTO);
        IFMDMAttribute unitCodeField = this.fmdmAttributeService.getFMDMBizField(fmdmAttributeDTO);
        IFMDMAttribute parentField = this.fmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
        return new FmdmAttribute(unitCodeField.getName(), parentField.getName(), showAttributes);
    }

    private List<DimensionInfo> getTaskDataDimension(TaskDefine taskDefine) {
        ArrayList<DimensionInfo> dimensionInfoList = new ArrayList<DimensionInfo>();
        if (taskDefine != null) {
            List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
            for (DataDimension dataDimension : dataSchemeDimension) {
                DimensionInfo dimensionInfo;
                IEntityDefine entity;
                if (DimensionType.UNIT.equals((Object)dataDimension.getDimensionType())) {
                    entity = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                    dimensionInfo = new DimensionInfo(entity.getDimensionName(), entity.getId(), dataDimension.getDimensionType());
                } else if (DimensionType.PERIOD.equals((Object)dataDimension.getDimensionType())) {
                    dimensionInfo = new DimensionInfo("DATATIME", dataDimension.getDimKey(), dataDimension.getDimensionType());
                } else {
                    if (!DimensionType.DIMENSION.equals((Object)dataDimension.getDimensionType())) continue;
                    entity = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                    dimensionInfo = null != entity ? new DimensionInfo(entity.getDimensionName(), entity.getId(), DimensionType.DIMENSION) : new DimensionInfo("ADJUST", null, DimensionType.DIMENSION);
                }
                dimensionInfoList.add(dimensionInfo);
            }
        }
        return dimensionInfoList;
    }

    private Object getExportValueByType(AbstractData abstractData, int type) {
        if (abstractData == null || abstractData.isNull) {
            return null;
        }
        switch (type) {
            case 1: {
                return abstractData.getAsBool();
            }
            case 2: {
                return abstractData.getAsDateTime();
            }
            case 3: 
            case 8: {
                return abstractData.getAsFloat();
            }
            case 5: {
                return abstractData.getAsInt();
            }
            case 10: {
                return abstractData.getAsCurrency();
            }
        }
        return abstractData.getAsString();
    }

    private Object getImportValueByType(Object originalData, int type) {
        if (originalData == null || Objects.isNull(type)) {
            return null;
        }
        if (type == 2) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(originalData);
        }
        return originalData;
    }

    private static class DimensionInfo {
        private String dimName;
        private String entityId;
        private DimensionType dimensionType;

        public DimensionInfo(String dimName, String entityId, DimensionType dimensionType) {
            this.dimName = dimName;
            this.entityId = entityId;
            this.dimensionType = dimensionType;
        }

        public String getDimName() {
            return this.dimName;
        }

        public void setDimName(String dimName) {
            this.dimName = dimName;
        }

        public String getEntityId() {
            return this.entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public DimensionType getDimensionType() {
            return this.dimensionType;
        }

        public void setDimensionType(DimensionType dimensionType) {
            this.dimensionType = dimensionType;
        }
    }

    private static class ImportCodeInfo {
        private final Map<String, Integer> importCodes;
        private final Map<String, Integer> codeToType;
        private int unitCodeIndex;
        private int parentCodeIndex;

        public ImportCodeInfo(Map<String, Integer> importCodes, Map<String, Integer> codeToType, int unitCodeIndex, int parentCodeIndex) {
            this.importCodes = importCodes;
            this.codeToType = codeToType;
            this.unitCodeIndex = unitCodeIndex;
            this.parentCodeIndex = parentCodeIndex;
        }

        public Map<String, Integer> getImportCodes() {
            return this.importCodes;
        }

        public Map<String, Integer> getCodeToType() {
            return this.codeToType;
        }

        public int getUnitCodeIndex() {
            return this.unitCodeIndex;
        }

        public void setUnitCodeIndex(int unitCodeIndex) {
            this.unitCodeIndex = unitCodeIndex;
        }

        public int getParentCodeIndex() {
            return this.parentCodeIndex;
        }

        public void setParentCodeIndex(int parentCodeIndex) {
            this.parentCodeIndex = parentCodeIndex;
        }
    }

    private static class FmdmAttribute {
        private String unitCodeName;
        private String parentCodeName;
        private List<IFMDMAttribute> showAttributes;

        public FmdmAttribute(String unitCodeName, String parentCodeName, List<IFMDMAttribute> showAttributes) {
            this.unitCodeName = unitCodeName;
            this.parentCodeName = parentCodeName;
            this.showAttributes = showAttributes;
        }

        public String getUnitCodeName() {
            return this.unitCodeName;
        }

        public void setUnitCodeName(String unitCodeName) {
            this.unitCodeName = unitCodeName;
        }

        public String getParentCodeName() {
            return this.parentCodeName;
        }

        public void setParentCodeName(String parentCodeName) {
            this.parentCodeName = parentCodeName;
        }

        public List<IFMDMAttribute> getShowAttributes() {
            return this.showAttributes;
        }

        public void setShowAttributes(List<IFMDMAttribute> showAttributes) {
            this.showAttributes = showAttributes;
        }
    }
}

