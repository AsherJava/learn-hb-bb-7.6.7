/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.data.attachment.param.AttFieldDataInfo
 *  com.jiuqi.nr.data.attachment.param.ExpFileParams
 *  com.jiuqi.nr.data.attachment.service.ExpFieldDataFileService
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.common.DataTypeConvert
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.fielddatacrud.FieldRelation
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.fielddatacrud.TableDimSet
 *  com.jiuqi.nr.fielddatacrud.spi.IDataReader
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.tds.TdColumn
 *  com.jiuqi.nr.tds.TdModel
 *  com.jiuqi.nr.tds.api.DataTableWriter
 *  com.jiuqi.nr.tds.api.TdStoreFactory
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.data.text.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.data.attachment.param.AttFieldDataInfo;
import com.jiuqi.nr.data.attachment.param.ExpFileParams;
import com.jiuqi.nr.data.attachment.service.ExpFieldDataFileService;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.data.text.service.impl.DataFileImpl;
import com.jiuqi.nr.data.text.spi.IFieldDataMonitor;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.api.DataTableWriter;
import com.jiuqi.nr.tds.api.TdStoreFactory;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class IDataReaderImpl
implements IDataReader,
Closeable {
    private static final Logger logger = LoggerFactory.getLogger(IDataReaderImpl.class);
    private final IRuntimeDataSchemeService runtimeDataSchemeService;
    private final FieldRelationFactory fieldRelationFactory;
    private final ExpFieldDataFileService expFieldDataFileService;
    private final DataServiceLoggerFactory dataServiceLoggerFactory;
    private final String fileName;
    private final boolean isExpZip;
    private IFieldDataMonitor zbDataMonitor;
    private ParamProvider paramProvider;
    private ParamsMapping paramsMapping;
    private DataFileImpl dataFile;
    private FileWriter fileWriter;
    private int exportCount;
    private String dataSchemeKey;
    private String path;
    private File file;
    private final Map<Integer, String> fieldIndexEntityId = new HashMap<Integer, String>();
    private String dwDimName;
    private final LinkedHashMap<String, String> dimNameEntityId = new LinkedHashMap();
    private long totalCount = -1L;
    private long writeCount = 0L;
    private final List<IRowData> rowDatas = new ArrayList<IRowData>();
    private Map<String, Map<String, String>> dimensionNameOriginValues = new HashMap<String, Map<String, String>>();
    private List<AttFieldDataInfo> attFieldDataInfos = new ArrayList<AttFieldDataInfo>();
    private static final String MOUDLE = "\u6307\u6807\u6570\u636e\u5bfc\u51fa\u670d\u52a1";
    private DataServiceLogHelper logHelper;
    private boolean haveFileOrPicField = false;
    private static final int MAX_WRITE_ROW = 5000;
    private TdStoreFactory tdStoreFactory;
    private DataTableWriter dataTableWriter;

    public IDataReaderImpl(IRuntimeDataSchemeService runtimeDataSchemeService, FieldRelationFactory fieldRelationFactory, ExpFieldDataFileService expFieldDataFileService, DataServiceLoggerFactory dataServiceLoggerFactory, String fileName, boolean isExpZip, FileWriter fileWriter, int exportCount) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.fieldRelationFactory = fieldRelationFactory;
        this.expFieldDataFileService = expFieldDataFileService;
        this.dataServiceLoggerFactory = dataServiceLoggerFactory;
        this.isExpZip = isExpZip;
        this.fileName = fileName;
        this.fileWriter = fileWriter;
        this.tdStoreFactory = new TdStoreFactory();
        this.exportCount = exportCount;
    }

    public IDataReaderImpl(IRuntimeDataSchemeService runtimeDataSchemeService, FieldRelationFactory fieldRelationFactory, ExpFieldDataFileService expFieldDataFileService, DataServiceLoggerFactory dataServiceLoggerFactory, String fileName, boolean isExpZip, IFieldDataMonitor zbDataMonitor, FileWriter fileWriter, int exportCount) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.fieldRelationFactory = fieldRelationFactory;
        this.expFieldDataFileService = expFieldDataFileService;
        this.dataServiceLoggerFactory = dataServiceLoggerFactory;
        this.fileName = fileName;
        this.isExpZip = isExpZip;
        this.zbDataMonitor = zbDataMonitor;
        this.fileWriter = fileWriter;
        this.tdStoreFactory = new TdStoreFactory();
        this.exportCount = exportCount;
    }

    public void setParamProvider(ParamProvider paramProvider) {
        this.paramProvider = paramProvider;
    }

    public void setParamDataProvider(ParamsMapping paramsMapping) {
        this.paramsMapping = paramsMapping;
    }

    public DataFileImpl getDataFile() {
        return this.dataFile;
    }

    public void start(List<IMetaData> metas, long totalCount) {
        this.logHelper = this.dataServiceLoggerFactory.getLogger(MOUDLE, OperLevel.USER_OPER);
        this.logHelper.info(null, null, "\u6d41\u5f0f\u8bfb\u53d6\u6307\u6807\u6570\u636e\u5f00\u59cb", "\u6d41\u5f0f\u8bfb\u53d6\u6307\u6807\u6570\u636e\u5f00\u59cb");
        if (null != this.zbDataMonitor) {
            this.zbDataMonitor.progressAndMessage(0.01, "\u5f00\u59cb\u5bfc\u51fa\u6570\u636e");
        }
        this.totalCount = totalCount;
        FieldRelation fieldRelation = this.fieldRelationFactory.getFieldRelation();
        TableDimSet tableDimSet = (TableDimSet)fieldRelation.getTableDim(metas).get(0);
        this.dwDimName = tableDimSet.getDwDimName();
        List dimFields = tableDimSet.getDimField();
        Map fieldCode2DimName = tableDimSet.getFieldCode2DimName();
        TdModel tdModel = new TdModel();
        tdModel.setName(this.fileName);
        ArrayList<TdColumn> columns = new ArrayList<TdColumn>();
        tdModel.setColumns(columns);
        for (DataField dimField : dimFields) {
            if (StringUtils.isEmpty((String)this.dataSchemeKey) && StringUtils.isNotEmpty((String)dimField.getDataSchemeKey())) {
                this.dataSchemeKey = dimField.getDataSchemeKey();
            }
            String dimensionName = (String)fieldCode2DimName.get(dimField.getCode());
            if (StringUtils.isNotEmpty((String)dimField.getRefDataEntityKey())) {
                this.dimNameEntityId.put(dimensionName, dimField.getRefDataEntityKey());
            } else {
                this.dimNameEntityId.put(dimensionName, null);
            }
            int scale = dimField.getDecimal() == null ? -1 : dimField.getDecimal();
            int precision = dimField.getPrecision() == null ? -1 : dimField.getPrecision();
            TdColumn column = new TdColumn(dimField.getCode(), DataTypeConvert.dataFieldType2TdsType((int)dimField.getDataFieldType().getValue()), precision, scale);
            columns.add(column);
        }
        HashMap<String, String> dataTableKeyCodeCatch = new HashMap<String, String>();
        HashMap<String, String> fieldCodeTableCodeMapping = new HashMap<String, String>();
        if (null != this.paramsMapping) {
            HashMap<String, List> tableCodeFieldCodeMapping = new HashMap<String, List>();
            for (int i = 0; i < metas.size(); ++i) {
                String tableKey;
                String dataTableCode;
                DataField dataField = metas.get(i).getDataField();
                if (!(this.haveFileOrPicField || DataFieldType.FILE != dataField.getDataFieldType() && DataFieldType.PICTURE != dataField.getDataFieldType())) {
                    this.haveFileOrPicField = true;
                }
                if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind()) continue;
                String fieldCode = dataField.getCode();
                if (metas.get(i).isEnumType()) {
                    this.fieldIndexEntityId.put(i, metas.get(i).getEntityId());
                }
                if (StringUtils.isEmpty((String)(dataTableCode = (String)dataTableKeyCodeCatch.get(tableKey = dataField.getDataTableKey())))) {
                    DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
                    dataTableKeyCodeCatch.put(tableKey, dataTable.getCode());
                    dataTableCode = dataTable.getCode();
                }
                fieldCodeTableCodeMapping.put(fieldCode, dataTableCode);
                List fieldCodes = tableCodeFieldCodeMapping.computeIfAbsent(dataTableCode, k -> new ArrayList());
                fieldCodes.add(dataField.getCode());
            }
            HashMap<String, Map> tableCodeAndOriginDataFieldCodeMapping = new HashMap<String, Map>();
            for (Map.Entry entry : tableCodeFieldCodeMapping.entrySet()) {
                String tableCode = (String)entry.getKey();
                tableCodeAndOriginDataFieldCodeMapping.put(tableCode, this.paramsMapping.getOriginDataFieldCode(tableCode, (List)tableCodeFieldCodeMapping.get(tableCode)));
            }
            for (IMetaData meta : metas) {
                DataField dataField = meta.getDataField();
                if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind()) continue;
                String tableCode = (String)dataTableKeyCodeCatch.get(dataField.getDataTableKey());
                String fieldCode = dataField.getCode();
                DataFieldMp dataFieldMp = (DataFieldMp)((Map)tableCodeAndOriginDataFieldCodeMapping.get(tableCode)).get(fieldCode);
                String fieldName = null != dataFieldMp ? dataFieldMp.getTableCode() + "." + dataFieldMp.getCode() : (String)fieldCodeTableCodeMapping.get(fieldCode) + "." + fieldCode;
                int scale = dataField.getDecimal() == null ? -1 : dataField.getDecimal();
                int precision = dataField.getPrecision() == null ? -1 : dataField.getPrecision();
                TdColumn column = new TdColumn(fieldName, DataTypeConvert.dataFieldType2TdsType((int)dataField.getDataFieldType().getValue()), precision, scale);
                columns.add(column);
            }
        } else {
            for (IMetaData meta : metas) {
                DataField dataField = meta.getDataField();
                if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind()) continue;
                String tableKey = dataField.getDataTableKey();
                String dataTableCode = (String)dataTableKeyCodeCatch.get(tableKey);
                if (StringUtils.isEmpty((String)dataTableCode)) {
                    DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
                    dataTableKeyCodeCatch.put(tableKey, dataTable.getCode());
                    dataTableCode = dataTable.getCode();
                }
                String fieldName = dataTableCode + "." + dataField.getCode();
                int scale = dataField.getDecimal() == null ? -1 : dataField.getDecimal();
                int precision = dataField.getPrecision() == null ? -1 : dataField.getPrecision();
                TdColumn column = new TdColumn(fieldName, DataTypeConvert.dataFieldType2TdsType((int)dataField.getDataFieldType().getValue()), precision, scale);
                columns.add(column);
            }
        }
        this.dataTableWriter = this.fileWriter != null ? this.tdStoreFactory.getDataTableWriter(tdModel, TdStoreFactory.VERSION_1_0_0) : this.tdStoreFactory.getDataTableWriter(tdModel, TdStoreFactory.VERSION_0_0_1);
        if (null != this.zbDataMonitor) {
            this.zbDataMonitor.progressAndMessage(0.1, "\u53c2\u6570\u521d\u59cb\u5316\u5b8c\u6210");
        }
    }

    public void readRow(IRowData rowData) {
        this.rowDatas.add(rowData);
        if (this.rowDatas.size() >= 5000) {
            this.writeFile();
        }
    }

    public void finish() {
        block9: {
            if (!this.rowDatas.isEmpty()) {
                this.writeFile();
            }
            try {
                this.dataTableWriter.close();
            }
            catch (IOException e) {
                logger.error("\u5173\u95ed\u6570\u636e\u6587\u4ef6\u5199\u5165\u5668\u5931\u8d25\uff1a{}".concat(e.getMessage()));
                this.logHelper.error(null, null, "\u5173\u95ed\u6570\u636e\u6587\u4ef6\u5199\u5165\u5668\u5931\u8d25", "\u5173\u95ed\u6570\u636e\u6587\u4ef6\u5199\u5165\u5668\u5931\u8d25\uff1a{}".concat(e.getMessage()));
                if (null == this.zbDataMonitor) break block9;
                this.zbDataMonitor.error("\u5173\u95ed\u6570\u636e\u6587\u4ef6\u5199\u5165\u5668\u5931\u8d25", e);
            }
        }
        this.file = this.dataTableWriter.getFile();
        this.path = this.file.getParent();
        if (this.fileWriter != null) {
            block10: {
                try {
                    this.fileWriter.addFile(this.fileName, this.file);
                    this.dataTableWriter.destroy();
                }
                catch (IOException e) {
                    logger.error("\u6dfb\u52a0\u6587\u4ef6\u5931\u8d25\uff1a{}".concat(e.getMessage()));
                    this.logHelper.error(null, null, "\u6dfb\u52a0\u6587\u4ef6\u5931\u8d25", "\u6dfb\u52a0\u6587\u4ef6\u5931\u8d25\uff1a{}".concat(e.getMessage()));
                    if (null == this.zbDataMonitor) break block10;
                    this.zbDataMonitor.error("\u6dfb\u52a0\u6587\u4ef6\u5931\u8d25", e);
                }
            }
            this.expFileZip();
        } else {
            File expFile = this.expFileZip();
            if (null == this.dataFile) {
                this.dataFile = new DataFileImpl();
            }
            this.dataFile.setFile(expFile);
        }
        if (null != this.zbDataMonitor) {
            this.zbDataMonitor.finish("\u6570\u636e\u5bfc\u51fa\u5b8c\u6210", null);
        }
        this.logHelper.info(null, null, "\u6d41\u5f0f\u8bfb\u53d6\u6307\u6807\u6570\u636e\u6210\u529f", "\u6d41\u5f0f\u8bfb\u53d6\u6307\u6807\u6570\u636e\u6210\u529f");
    }

    @Override
    public void close() throws IOException {
        List files;
        if (this.fileWriter != null && this.path != null && !CollectionUtils.isEmpty(files = FileUtil.getFiles((String)this.path, null))) {
            for (File file : files) {
                boolean delete = file.delete();
                if (delete) continue;
                throw new IOException("\u5220\u9664\u5bfc\u51fa\u6587\u4ef6\u5931\u8d25\uff1a" + file.getPath());
            }
        }
    }

    @NotNull
    private File expFileZip() {
        String expFilePath = "";
        if (!this.attFieldDataInfos.isEmpty()) {
            try {
                ExpFileParams params = new ExpFileParams();
                params.setDataSchemeKey(this.dataSchemeKey);
                params.setAttFieldDataInfos(this.attFieldDataInfos);
                params.setParamsMapping(this.paramsMapping);
                params.setFilePath(this.path);
                params.setFileWriter(this.fileWriter);
                params.setCsvFileName(this.fileName);
                expFilePath = this.expFieldDataFileService.downloadFiles(params);
                expFilePath = FilenameUtils.normalize(expFilePath);
            }
            catch (Exception e) {
                logger.error("\u5bfc\u51fa\u9644\u4ef6\u5f02\u5e38\uff1a" + e.getMessage(), e);
                this.logHelper.error(null, null, "\u6d41\u5f0f\u8bfb\u53d6\u6307\u6807\u6570\u636e\u5931\u8d25", "\u5bfc\u51fa\u9644\u4ef6\u5f02\u5e38\uff1a" + e.getMessage());
            }
        }
        List files = FileUtil.getFiles((String)this.path, null);
        if (this.isExpZip) {
            File file = new File(this.path);
            String parent = file.getParent();
            String destZipFile = parent + File.separator + this.fileName + ".zip";
            File zip = new File(destZipFile);
            try {
                block55: {
                    boolean delete;
                    if (zip.exists() && !(delete = zip.delete())) {
                        throw new IOException("\u5220\u9664\u540c\u540d\u538b\u7f29\u6587\u4ef6\u5931\u8d25\uff1a" + destZipFile);
                    }
                    boolean newFile = zip.createNewFile();
                    if (!newFile) {
                        throw new IOException("\u521b\u5efa\u538b\u7f29\u6587\u4ef6\u5931\u8d25\uff1a" + destZipFile);
                    }
                    try (FileOutputStream output = new FileOutputStream(zip);
                         ZipOutputStream zipOut = new ZipOutputStream(output);){
                        if (null == files) break block55;
                        for (File currFile : files) {
                            FileInputStream input = new FileInputStream(currFile);
                            Throwable throwable = null;
                            try {
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
                                Throwable throwable2 = null;
                                try {
                                    int num;
                                    ZipEntry zipEntry = new ZipEntry(currFile.getPath().substring(this.path.length()));
                                    zipOut.putNextEntry(zipEntry);
                                    byte[] buffer = new byte[512];
                                    while ((num = bufferedInputStream.read(buffer)) != -1) {
                                        zipOut.write(buffer, 0, num);
                                    }
                                }
                                catch (Throwable throwable3) {
                                    throwable2 = throwable3;
                                    throw throwable3;
                                }
                                finally {
                                    if (bufferedInputStream == null) continue;
                                    if (throwable2 != null) {
                                        try {
                                            bufferedInputStream.close();
                                        }
                                        catch (Throwable throwable4) {
                                            throwable2.addSuppressed(throwable4);
                                        }
                                        continue;
                                    }
                                    bufferedInputStream.close();
                                }
                            }
                            catch (Throwable throwable5) {
                                throwable = throwable5;
                                throw throwable5;
                            }
                            finally {
                                if (input == null) continue;
                                if (throwable != null) {
                                    try {
                                        input.close();
                                    }
                                    catch (Throwable throwable6) {
                                        throwable.addSuppressed(throwable6);
                                    }
                                    continue;
                                }
                                input.close();
                            }
                        }
                    }
                }
                if (StringUtils.isNotEmpty((String)expFilePath)) {
                    FileUtil.deleteFiles((String)expFilePath);
                }
            }
            catch (IOException e) {
                logger.error("\u6253\u5305\u5bfc\u51fa\u6587\u4ef6\u5f02\u5e38\uff1a" + e.getMessage(), e);
                this.logHelper.error(null, null, "\u6d41\u5f0f\u8bfb\u53d6\u6307\u6807\u6570\u636e\u5931\u8d25", "\u6253\u5305\u5bfc\u51fa\u6587\u4ef6\u5f02\u5e38\uff1a" + e.getMessage());
            }
            return zip;
        }
        return new File(this.path);
    }

    private void writeFile() {
        block26: {
            try {
                Object dimValue;
                DimensionCombination dimension;
                ArrayList<Object> values = new ArrayList<Object>();
                if (null != this.paramsMapping) {
                    Integer index;
                    Object entityId;
                    Map<String, String> dataOriginData;
                    Iterator dimensionNameValues = new HashMap();
                    for (IRowData iRowData : this.rowDatas) {
                        dimension = iRowData.getDimension();
                        for (String dimName : this.dimNameEntityId.keySet()) {
                            dimValue = (String)dimension.getValue(dimName);
                            Map<String, String> dataOriginData2 = this.dimensionNameOriginValues.get(dimName);
                            if (null != dataOriginData2 && dataOriginData2.containsKey(dimValue)) continue;
                            Set dimValues = dimensionNameValues.computeIfAbsent(dimName, k -> new LinkedHashSet());
                            dimValues.add(dimValue);
                        }
                    }
                    for (Map.Entry entry : dimensionNameValues.entrySet()) {
                        String dimName = (String)entry.getKey();
                        Set dimValues = (Set)dimensionNameValues.get(dimName);
                        if (null == dimValues || dimValues.isEmpty()) continue;
                        dataOriginData = this.dimensionNameOriginValues.computeIfAbsent(dimName, k -> new HashMap());
                        if (this.dwDimName.equals(dimName)) {
                            Map originOrgCode = this.paramsMapping.getOriginOrgCode(new ArrayList(dimValues));
                            dataOriginData.putAll(originOrgCode);
                            continue;
                        }
                        if ("DATATIME".equals(dimName)) {
                            Map originPeriod = this.paramsMapping.getOriginPeriod(new ArrayList(dimValues));
                            dataOriginData.putAll(originPeriod);
                            continue;
                        }
                        entityId = this.dimNameEntityId.get(dimName);
                        if (null == entityId) continue;
                        Map originBaseData = this.paramsMapping.getOriginBaseData((String)entityId, new ArrayList(dimValues));
                        dataOriginData.putAll(originBaseData);
                    }
                    HashMap<Integer, List> hashMap = new HashMap<Integer, List>();
                    for (Map.Entry<Integer, String> entry : this.fieldIndexEntityId.entrySet()) {
                        index = entry.getKey();
                        dataOriginData = this.dimensionNameOriginValues.get(this.fieldIndexEntityId.get(index));
                        for (IRowData data : this.rowDatas) {
                            String fieldData;
                            boolean asNull = ((IDataValue)data.getLinkDataValues().get(index)).getAsNull();
                            if (asNull || !StringUtils.isNotEmpty((String)(fieldData = ((IDataValue)data.getLinkDataValues().get(index)).getAsString())) || null != dataOriginData && dataOriginData.containsKey(fieldData)) continue;
                            List fieldValues = hashMap.computeIfAbsent(index, k -> new ArrayList());
                            fieldValues.add(fieldData);
                        }
                    }
                    for (Map.Entry<Integer, String> entry : this.fieldIndexEntityId.entrySet()) {
                        index = entry.getKey();
                        List fieldDatas = (List)hashMap.get(index);
                        if (null == fieldDatas || fieldDatas.isEmpty()) continue;
                        entityId = this.fieldIndexEntityId.get(index);
                        Map dataOriginData2 = this.dimensionNameOriginValues.computeIfAbsent((String)entityId, k -> new HashMap());
                        Map originFieldData = this.paramsMapping.getOriginBaseData((String)entityId, fieldDatas);
                        dataOriginData2.putAll(originFieldData);
                    }
                    for (IRowData data : this.rowDatas) {
                        ArrayList value = new ArrayList();
                        DimensionCombination dimension2 = data.getDimension();
                        DimensionCombinationBuilder originDimBuilder = null;
                        if (this.haveFileOrPicField) {
                            originDimBuilder = new DimensionCombinationBuilder();
                        }
                        for (String dimName : this.dimNameEntityId.keySet()) {
                            Object dimValue2 = dimension2.getValue(dimName);
                            Map<String, String> dataOriginData3 = this.dimensionNameOriginValues.get(dimName);
                            if (null != dataOriginData3 && null != dataOriginData3.get((String)dimValue2)) {
                                String originDimValue = dataOriginData3.get((String)dimValue2);
                                value.add(originDimValue);
                                if (null == originDimBuilder) continue;
                                originDimBuilder.setValue(dimName, (Object)originDimValue);
                                continue;
                            }
                            value.add(dimValue2);
                            if (null == originDimBuilder) continue;
                            originDimBuilder.setValue(dimName, dimValue2);
                        }
                        List linkDataValues = data.getLinkDataValues();
                        for (int j = 0; j < linkDataValues.size(); ++j) {
                            IDataValue dataValue = (IDataValue)linkDataValues.get(j);
                            DataField dataField = dataValue.getMetaData().getDataField();
                            if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind()) continue;
                            Object fieldData = dataValue.getAsObject();
                            String entityId2 = this.fieldIndexEntityId.get(j);
                            if (StringUtils.isNotEmpty((String)entityId2)) {
                                Map<String, String> dataOriginData4 = this.dimensionNameOriginValues.get(entityId2);
                                if (null != dataOriginData4 && null != dataOriginData4.get((String)fieldData)) {
                                    value.add(dataOriginData4.get(fieldData));
                                } else {
                                    value.add(fieldData);
                                }
                            } else {
                                value.add(fieldData);
                            }
                            if (DataFieldType.FILE != dataField.getDataFieldType() && DataFieldType.PICTURE != dataField.getDataFieldType() || !StringUtils.isNotEmpty((String)((String)fieldData))) continue;
                            AttFieldDataInfo attFieldDataInfo = new AttFieldDataInfo((String)fieldData, null != originDimBuilder ? originDimBuilder.getCombination() : dimension2, dataField.getKey());
                            this.attFieldDataInfos.add(attFieldDataInfo);
                        }
                        values.add(value);
                    }
                } else {
                    for (IRowData iRowData : this.rowDatas) {
                        ArrayList<Object> arrayList = new ArrayList<Object>();
                        dimension = iRowData.getDimension();
                        for (String dimName : this.dimNameEntityId.keySet()) {
                            dimValue = dimension.getValue(dimName);
                            arrayList.add(dimValue);
                        }
                        List linkDataValues = iRowData.getLinkDataValues();
                        for (IDataValue linkDataValue : linkDataValues) {
                            DataField dataField = linkDataValue.getMetaData().getDataField();
                            if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind()) continue;
                            Object fieldData = linkDataValue.getAsObject();
                            arrayList.add(fieldData);
                            if (DataFieldType.FILE != dataField.getDataFieldType() && DataFieldType.PICTURE != dataField.getDataFieldType() || !StringUtils.isNotEmpty((String)((String)fieldData))) continue;
                            AttFieldDataInfo attFieldDataInfo = new AttFieldDataInfo((String)fieldData, dimension, dataField.getKey());
                            this.attFieldDataInfos.add(attFieldDataInfo);
                        }
                        values.add(arrayList);
                    }
                }
                for (ArrayList arrayList : values) {
                    if (arrayList.isEmpty()) continue;
                    this.dataTableWriter.appendRow((List)arrayList);
                    ++this.exportCount;
                }
                this.dataTableWriter.flush();
                if (null != this.zbDataMonitor && -1L != this.totalCount) {
                    double currProgress = 0.1 + 0.7 * (double)((5000L * this.writeCount + (long)this.rowDatas.size()) / this.totalCount);
                    BigDecimal bigDecimal = new BigDecimal(currProgress);
                    currProgress = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
                    this.zbDataMonitor.progressAndMessage(currProgress, "\u6307\u6807\u6570\u636e\u5199\u5165csv\u6587\u4ef6\u4e2d");
                }
                ++this.writeCount;
                this.rowDatas.clear();
            }
            catch (IOException e) {
                logger.error("\u6307\u6807\u6570\u636e\u5199\u5165csv\u6587\u4ef6\u5931\u8d25\uff1a{}".concat(e.getMessage()));
                this.logHelper.error(null, null, "\u6d41\u5f0f\u8bfb\u53d6\u6307\u6807\u6570\u636e\u5931\u8d25", "\u6307\u6807\u6570\u636e\u5199\u5165csv\u6587\u4ef6\u5931\u8d25\uff1a{}".concat(e.getMessage()));
                if (null == this.zbDataMonitor) break block26;
                this.zbDataMonitor.error("\u6307\u6807\u6570\u636e\u5199\u5165csv\u6587\u4ef6\u5931\u8d25", e);
            }
        }
    }
}

