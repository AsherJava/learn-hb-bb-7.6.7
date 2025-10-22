/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.common.DataTypeConvert
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.util.ExtUtil
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.jiuqi.nr.snapshot.service.impl;

import com.csvreader.CsvReader;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.util.ExtUtil;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.snapshot.input.QueryPeriodDataSourceContext;
import com.jiuqi.nr.snapshot.input.QuerySnapshotDataSourceContext;
import com.jiuqi.nr.snapshot.message.DataInfo;
import com.jiuqi.nr.snapshot.message.DataRange;
import com.jiuqi.nr.snapshot.message.DataRegionAndFields;
import com.jiuqi.nr.snapshot.message.DataRegionRange;
import com.jiuqi.nr.snapshot.message.FieldData;
import com.jiuqi.nr.snapshot.message.FieldInfo;
import com.jiuqi.nr.snapshot.message.FixRegionData;
import com.jiuqi.nr.snapshot.message.FloatRegionData;
import com.jiuqi.nr.snapshot.service.DataSource;
import com.jiuqi.nr.snapshot.service.DataSourceBuilder;
import com.jiuqi.nr.snapshot.service.impl.DataSourceImpl;
import com.jiuqi.nr.snapshot.utils.SnapshotDataTypeUtil;
import com.jiuqi.nr.snapshot.utils.SnapshotFileTool;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceBuilderImpl
implements DataSourceBuilder {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceBuilderImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DataSource querySnapshotDataSource(QuerySnapshotDataSourceContext queryContext) {
        DataSourceImpl dataSource;
        block39: {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(queryContext.getFormSchemeKey());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            String snapshotFileKey = this.searchFileKey(queryContext, dataScheme, context);
            String snapshotTitle = this.getSnapshotTitle(queryContext, dataScheme, context);
            dataSource = new DataSourceImpl();
            dataSource.setTitle(snapshotTitle);
            List<DataRegionRange> formAndFields = queryContext.getDataRange().getFormAndFields();
            CsvReader reader = null;
            File[] listFiles = null;
            try {
                File[] formKeyAnddataInfoMap = new HashMap();
                dataSource.setData((Map<String, DataInfo>)formKeyAnddataInfoMap);
                listFiles = this.getAllRegionFiles(snapshotFileKey);
                for (DataRegionRange formAndField : formAndFields) {
                    List<List<FieldData>> allFloatDatass;
                    LinkedHashMap<String, FieldInfo> codeFieldDefineMap;
                    FloatRegionData floatRegionData;
                    FieldDefine allFieldDefine;
                    List<FieldData> allFieldDatas;
                    FieldInfo fieldInfo;
                    FieldInfo fieldInfo2;
                    IPeriodEntity periodEntity;
                    IPeriodEntityAdapter periodAdapter;
                    IEntityDefine iEntityDefine;
                    DataDimension dataDimension;
                    List allFieldDefines;
                    FieldDefine fieldDefine;
                    Object fieldKey2;
                    Object fieldInfos;
                    String snapshotFileName;
                    File listFile;
                    String fileName;
                    DataInfo dataInfo = null;
                    if (null == formKeyAnddataInfoMap.get(formAndField.getFormKey())) {
                        dataInfo = new DataInfo();
                        formKeyAnddataInfoMap.put(formAndField.getFormKey(), dataInfo);
                    } else {
                        dataInfo = (DataInfo)formKeyAnddataInfoMap.get(formAndField.getFormKey());
                    }
                    FormDefine formDefine = this.runTimeViewController.queryFormById(formAndField.getFormKey());
                    List<DataRegionAndFields> dataRegionAndFieldsList = formAndField.getDataRegionAndFieldsList();
                    if (null != dataRegionAndFieldsList && !dataRegionAndFieldsList.isEmpty()) {
                        for (DataRegionAndFields dataRegionAndFields : dataRegionAndFieldsList) {
                            List fieldKeysInRegion;
                            DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(dataRegionAndFields.getDataRegionKey());
                            fileName = formDefine.getFormCode();
                            if (dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_ROW_LIST)) {
                                fileName = fileName + "_F" + dataRegionDefine.getRegionTop();
                            }
                            listFile = null;
                            for (File file : listFiles) {
                                snapshotFileName = ExtUtil.trimEnd((String)file.getName(), (String)".csv");
                                if (!fileName.equals(snapshotFileName)) continue;
                                listFile = file;
                                break;
                            }
                            if (null == listFile) continue;
                            if (dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_SIMPLE)) {
                                fieldInfos = new ArrayList();
                                fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                                ArrayList<String> tableKeyCatch = new ArrayList<String>();
                                for (Object fieldKey2 : fieldKeysInRegion) {
                                    fieldDefine = this.runTimeViewController.queryFieldDefine((String)fieldKey2);
                                    if (null == fieldDefine || tableKeyCatch.contains(fieldDefine.getOwnerTableKey())) continue;
                                    allFieldDefines = this.dataDefinitionRuntimeController.getAllFieldsInTable(fieldDefine.getOwnerTableKey());
                                    for (FieldDefine allFieldDefine2 : allFieldDefines) {
                                        FieldInfo fieldInfo4 = new FieldInfo(allFieldDefine2.getCode(), allFieldDefine2.getType(), null, allFieldDefine2);
                                        fieldInfos.add(fieldInfo4);
                                    }
                                    tableKeyCatch.add(fieldDefine.getOwnerTableKey());
                                }
                                if (fieldKeysInRegion.isEmpty()) {
                                    List list = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
                                    fieldKey2 = list.iterator();
                                    while (fieldKey2.hasNext()) {
                                        dataDimension = (DataDimension)fieldKey2.next();
                                        if (DimensionType.UNIT.getValue() == dataDimension.getDimensionType().getValue()) {
                                            iEntityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                                            FieldInfo fieldInfo3 = new FieldInfo("MDCODE", FieldType.FIELD_TYPE_STRING, iEntityDefine.getDimensionName(), null);
                                            fieldInfos.add(fieldInfo3);
                                            continue;
                                        }
                                        if (DimensionType.PERIOD.getValue() == dataDimension.getDimensionType().getValue()) {
                                            periodAdapter = this.periodEngineService.getPeriodAdapter();
                                            periodEntity = periodAdapter.getPeriodEntity(dataDimension.getDimKey());
                                            FieldInfo fieldInfo22 = new FieldInfo("DATATIME", FieldType.FIELD_TYPE_STRING, periodEntity.getDimensionName(), null);
                                            fieldInfos.add(fieldInfo22);
                                            continue;
                                        }
                                        if (DimensionType.DIMENSION.getValue() != dataDimension.getDimensionType().getValue()) continue;
                                        if ("ADJUST".equals(dataDimension.getDimKey())) {
                                            fieldInfo2 = new FieldInfo("ADJUST", FieldType.FIELD_TYPE_STRING, "ADJUST", null);
                                            fieldInfos.add(fieldInfo2);
                                            continue;
                                        }
                                        iEntityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                                        fieldInfo = new FieldInfo(iEntityDefine.getCode(), FieldType.FIELD_TYPE_STRING, iEntityDefine.getDimensionName(), null);
                                        fieldInfos.add(fieldInfo);
                                    }
                                }
                                FixRegionData fixRegionData = new FixRegionData();
                                fixRegionData.setRegionKey(dataRegionDefine.getKey());
                                fixRegionData.setRegionName(dataRegionDefine.getTitle());
                                reader = new CsvReader(listFile.getAbsolutePath(), ',', Charset.forName("UTF-8"));
                                LinkedHashMap<String, FieldInfo> codeFieldDefineMap2 = this.readFileHeaders(reader, (List<FieldInfo>)fieldInfos);
                                allFieldDatas = this.readFixFieldData(reader, codeFieldDefineMap2, queryContext.getDimensionCombination());
                                List<FieldData> fieldDatas = this.filterFixFieldDatas(dataRegionAndFields, allFieldDatas);
                                fixRegionData.setFixDatas(fieldDatas);
                                dataInfo.setFixData(fixRegionData);
                                continue;
                            }
                            fieldInfos = new ArrayList();
                            fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                            if (fieldKeysInRegion.isEmpty()) continue;
                            FieldDefine fieldDefine2 = null;
                            Iterator iterator = fieldKeysInRegion.iterator();
                            while (iterator.hasNext() && null == (fieldDefine2 = this.runTimeViewController.queryFieldDefine((String)(fieldKey2 = (String)iterator.next())))) {
                            }
                            if (null == fieldDefine2) continue;
                            List list = this.dataDefinitionRuntimeController.getAllFieldsInTable(fieldDefine2.getOwnerTableKey());
                            fieldKey2 = list.iterator();
                            while (fieldKey2.hasNext()) {
                                allFieldDefine = (FieldDefine)fieldKey2.next();
                                fieldInfo2 = new FieldInfo(allFieldDefine.getCode(), allFieldDefine.getType(), null, allFieldDefine);
                                fieldInfos.add(fieldInfo2);
                            }
                            floatRegionData = this.constructionFloatRegionData(dataInfo, dataRegionDefine);
                            reader = new CsvReader(listFile.getAbsolutePath(), ',', Charset.forName("UTF-8"));
                            codeFieldDefineMap = this.readFileHeaders(reader, (List<FieldInfo>)fieldInfos);
                            allFloatDatass = this.readFloatFieldData(reader, codeFieldDefineMap, queryContext.getDimensionCombination());
                            List<List<FieldData>> floatDatass = this.filterFloatFieldDatas(dataRegionAndFields, allFloatDatass, dataRegionDefine);
                            floatRegionData.setFloatDatass(floatDatass);
                        }
                        continue;
                    }
                    if (null != dataRegionAndFieldsList) continue;
                    List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
                    for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                        List fieldKeysInRegion;
                        fileName = formDefine.getFormCode();
                        if (dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_ROW_LIST)) {
                            fileName = fileName + "_F" + dataRegionDefine.getRegionTop();
                        }
                        listFile = null;
                        for (File file : listFiles) {
                            snapshotFileName = ExtUtil.trimEnd((String)file.getName(), (String)".csv");
                            if (!fileName.equals(snapshotFileName)) continue;
                            listFile = file;
                            break;
                        }
                        if (null == listFile) continue;
                        if (dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_SIMPLE)) {
                            fieldInfos = new ArrayList();
                            fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                            ArrayList<String> tableKeyCatch = new ArrayList<String>();
                            for (Object fieldKey2 : fieldKeysInRegion) {
                                fieldDefine = this.runTimeViewController.queryFieldDefine((String)fieldKey2);
                                if (null == fieldDefine || tableKeyCatch.contains(fieldDefine.getOwnerTableKey())) continue;
                                allFieldDefines = this.dataDefinitionRuntimeController.getAllFieldsInTable(fieldDefine.getOwnerTableKey());
                                for (FieldDefine allFieldDefine2 : allFieldDefines) {
                                    FieldInfo fieldInfo3 = new FieldInfo(allFieldDefine2.getCode(), allFieldDefine2.getType(), null, allFieldDefine2);
                                    fieldInfos.add(fieldInfo3);
                                }
                                tableKeyCatch.add(fieldDefine.getOwnerTableKey());
                            }
                            if (fieldKeysInRegion.isEmpty()) {
                                List list = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
                                fieldKey2 = list.iterator();
                                while (fieldKey2.hasNext()) {
                                    dataDimension = (DataDimension)fieldKey2.next();
                                    if (DimensionType.UNIT.getValue() == dataDimension.getDimensionType().getValue()) {
                                        iEntityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                                        fieldInfo = new FieldInfo("MDCODE", FieldType.FIELD_TYPE_STRING, iEntityDefine.getDimensionName(), null);
                                        fieldInfos.add(fieldInfo);
                                        continue;
                                    }
                                    if (DimensionType.PERIOD.getValue() == dataDimension.getDimensionType().getValue()) {
                                        periodAdapter = this.periodEngineService.getPeriodAdapter();
                                        periodEntity = periodAdapter.getPeriodEntity(dataDimension.getDimKey());
                                        FieldInfo fieldInfo4 = new FieldInfo("DATATIME", FieldType.FIELD_TYPE_STRING, periodEntity.getDimensionName(), null);
                                        fieldInfos.add(fieldInfo4);
                                        continue;
                                    }
                                    if (DimensionType.DIMENSION.getValue() != dataDimension.getDimensionType().getValue()) continue;
                                    if ("ADJUST".equals(dataDimension.getDimKey())) {
                                        fieldInfo2 = new FieldInfo("ADJUST", FieldType.FIELD_TYPE_STRING, "ADJUST", null);
                                        fieldInfos.add(fieldInfo2);
                                        continue;
                                    }
                                    iEntityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                                    fieldInfo = new FieldInfo(iEntityDefine.getCode(), FieldType.FIELD_TYPE_STRING, iEntityDefine.getDimensionName(), null);
                                    fieldInfos.add(fieldInfo);
                                }
                            }
                            FixRegionData fixRegionData = new FixRegionData();
                            fixRegionData.setRegionKey(dataRegionDefine.getKey());
                            fixRegionData.setRegionName(dataRegionDefine.getTitle());
                            reader = new CsvReader(listFile.getAbsolutePath(), ',', Charset.forName("UTF-8"));
                            LinkedHashMap<String, FieldInfo> codeFieldDefineMap2 = this.readFileHeaders(reader, (List<FieldInfo>)fieldInfos);
                            allFieldDatas = this.readFixFieldData(reader, codeFieldDefineMap2, queryContext.getDimensionCombination());
                            fixRegionData.setFixDatas(allFieldDatas);
                            dataInfo.setFixData(fixRegionData);
                            continue;
                        }
                        fieldInfos = new ArrayList();
                        fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                        if (fieldKeysInRegion.isEmpty()) continue;
                        FieldDefine fieldDefine3 = null;
                        Iterator iterator = fieldKeysInRegion.iterator();
                        while (iterator.hasNext() && null == (fieldDefine3 = this.runTimeViewController.queryFieldDefine((String)(fieldKey2 = (String)iterator.next())))) {
                        }
                        if (null == fieldDefine3) continue;
                        List list = this.dataDefinitionRuntimeController.getAllFieldsInTable(fieldDefine3.getOwnerTableKey());
                        fieldKey2 = list.iterator();
                        while (fieldKey2.hasNext()) {
                            allFieldDefine = (FieldDefine)fieldKey2.next();
                            fieldInfo2 = new FieldInfo(allFieldDefine.getCode(), allFieldDefine.getType(), null, allFieldDefine);
                            fieldInfos.add(fieldInfo2);
                        }
                        floatRegionData = this.constructionFloatRegionData(dataInfo, dataRegionDefine);
                        reader = new CsvReader(listFile.getAbsolutePath(), ',', Charset.forName("UTF-8"));
                        codeFieldDefineMap = this.readFileHeaders(reader, (List<FieldInfo>)fieldInfos);
                        allFloatDatass = this.readFloatFieldData(reader, codeFieldDefineMap, queryContext.getDimensionCombination());
                        floatRegionData.setFloatDatass(allFloatDatass);
                    }
                }
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
                if (null == listFiles || listFiles.length == 0) break block39;
                for (File listFile : listFiles) {
                    if (listFile.delete()) continue;
                    logger.error("\u5feb\u7167\u4e34\u65f6\u6587\u4ef6\u672a\u5220\u9664\u6210\u529f");
                }
            }
        }
        return dataSource;
    }

    @NotNull
    private List<List<FieldData>> readFloatFieldData(CsvReader reader, LinkedHashMap<String, FieldInfo> codeFieldDefineMap, DimensionCombination dimensionCombination) throws IOException {
        DimensionChanger dimensionChanger = null;
        for (String code : codeFieldDefineMap.keySet()) {
            FieldInfo fieldInfo = codeFieldDefineMap.get(code);
            if (null == fieldInfo || null == fieldInfo.getFieldDefine()) continue;
            String tableName = "";
            try {
                List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(fieldInfo.getFieldDefine().getOwnerTableKey());
                tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
            break;
        }
        ArrayList<List<FieldData>> allFloatDatass = new ArrayList<List<FieldData>>();
        while (reader.readRecord()) {
            boolean canAdd = true;
            for (String code : codeFieldDefineMap.keySet()) {
                Object object;
                String value;
                if (code.equals("SnapshotGatherFlag") || code.equals("SnapshotGatherGroupKey") || code.equals("isFilledRow") || code.contains("FORMULALINK")) continue;
                String dimensionName = null;
                if (null != codeFieldDefineMap.get(code) && null != codeFieldDefineMap.get(code).getFieldDefine()) {
                    FieldDefine fieldDefine = codeFieldDefineMap.get(code).getFieldDefine();
                    dimensionName = dimensionChanger.getDimensionName(fieldDefine.getCode());
                }
                String data = reader.get(code);
                if (!StringUtils.isNotEmpty(dimensionName) || dimensionName.equals("DATATIME") || !StringUtils.isNotEmpty((String)(value = (String)(object = dimensionCombination.getValue(dimensionName)))) || data.equals(value)) continue;
                canAdd = false;
                break;
            }
            if (!canAdd) continue;
            ArrayList<FieldData> allFieldDatas = new ArrayList<FieldData>();
            boolean isGatherFlag = false;
            for (String code : codeFieldDefineMap.keySet()) {
                if (!code.equals("SnapshotGatherFlag")) continue;
                String data = reader.get(code);
                if ("-1".equals(data)) break;
                isGatherFlag = true;
                break;
            }
            for (String code : codeFieldDefineMap.keySet()) {
                FieldData fieldData = new FieldData();
                if (code.equals("SnapshotGatherFlag") || code.equals("SnapshotGatherGroupKey") || code.equals("isFilledRow")) {
                    fieldData.setFieldCode(code);
                    String data = reader.get(code);
                    if ("".equals(data)) {
                        data = null;
                    }
                    AbstractData abstractData = AbstractData.valueOf((Object)data, (int)6);
                    fieldData.setData(abstractData);
                } else {
                    FieldInfo fieldInfo = codeFieldDefineMap.get(code);
                    if (null != fieldInfo && null != fieldInfo.getFieldDefine()) {
                        FieldDefine fieldDefine = fieldInfo.getFieldDefine();
                        DataFieldDTO dataFieldDTO = (DataFieldDTO)fieldDefine;
                        fieldData.setFieldKey(fieldDefine.getKey());
                        fieldData.setFieldCode(fieldDefine.getCode());
                        fieldData.setFieldTitle(fieldDefine.getTitle());
                        fieldData.setMaskCode(dataFieldDTO.getDataMaskCode());
                        String data = reader.get(code);
                        if ("".equals(data)) {
                            data = null;
                        }
                        int dataType = ((DataField)fieldDefine).getDataFieldType().getValue();
                        int type = 6;
                        type = isGatherFlag ? SnapshotDataTypeUtil.dataFieldType2DataType(dataType, ((DataField)fieldDefine).getDataFieldGatherType()) : SnapshotDataTypeUtil.dataFieldType2DataType(dataType);
                        if (1 == type && "\u662f".equals(data)) {
                            data = "true";
                        } else if (1 == type && "\u5426".equals(data)) {
                            data = "false";
                        } else if (1 == type && (null == data || "".equals(data))) {
                            data = null;
                        }
                        if ("\u2014\u2014".equals(data)) {
                            type = 6;
                        }
                        AbstractData abstractData = AbstractData.valueOf((Object)data, (int)type);
                        fieldData.setData(abstractData);
                    } else {
                        String[] split = code.split("\\.");
                        if (1 == split.length) {
                            fieldData.setFieldKey(split[0]);
                        } else if (2 == split.length) {
                            fieldData.setFieldKey(split[1]);
                        }
                        String data = reader.get(code);
                        if ("".equals(data)) {
                            data = null;
                        }
                        AbstractData abstractData = AbstractData.valueOf((Object)data, (int)6);
                        fieldData.setData(abstractData);
                    }
                }
                allFieldDatas.add(fieldData);
            }
            allFloatDatass.add(allFieldDatas);
        }
        return allFloatDatass;
    }

    @NotNull
    private List<FieldData> readFixFieldData(CsvReader reader, LinkedHashMap<String, FieldInfo> codeFieldDefineMap, DimensionCombination dimensionCombination) throws IOException {
        DimensionChanger dimensionChanger = null;
        for (String code : codeFieldDefineMap.keySet()) {
            FieldInfo fieldInfo = codeFieldDefineMap.get(code);
            if (null == fieldInfo || null == fieldInfo.getFieldDefine()) continue;
            String tableName = "";
            try {
                List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(fieldInfo.getFieldDefine().getOwnerTableKey());
                tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
            break;
        }
        ArrayList<FieldData> allFieldDatas = new ArrayList<FieldData>();
        while (reader.readRecord()) {
            boolean canAdd = true;
            for (String code : codeFieldDefineMap.keySet()) {
                Object object;
                String value;
                if (code.equals("SnapshotGatherFlag") || code.equals("SnapshotGatherGroupKey") || code.equals("isFilledRow") || code.contains("FORMULALINK")) continue;
                String dimensionName = null;
                if (null != dimensionChanger) {
                    if (null != codeFieldDefineMap.get(code) && null != codeFieldDefineMap.get(code).getFieldDefine()) {
                        FieldDefine fieldDefine = codeFieldDefineMap.get(code).getFieldDefine();
                        dimensionName = dimensionChanger.getDimensionName(fieldDefine.getCode());
                    }
                } else {
                    dimensionName = codeFieldDefineMap.get(code).getDimName();
                }
                String data = reader.get(code);
                if (!StringUtils.isNotEmpty((String)dimensionName) || dimensionName.equals("DATATIME") || !StringUtils.isNotEmpty((String)(value = (String)(object = dimensionCombination.getValue(dimensionName)))) || data.equals(value)) continue;
                canAdd = false;
                break;
            }
            if (!canAdd) continue;
            for (String code : codeFieldDefineMap.keySet()) {
                if (code.equals("SnapshotGatherFlag") || code.equals("SnapshotGatherGroupKey") || code.equals("isFilledRow")) continue;
                FieldInfo fieldInfo = codeFieldDefineMap.get(code);
                FieldData fieldData = new FieldData();
                if (null != fieldInfo && null != fieldInfo.getFieldDefine()) {
                    int dataType;
                    int type;
                    FieldDefine fieldDefine = fieldInfo.getFieldDefine();
                    DataFieldDTO dataFieldDTO = (DataFieldDTO)fieldDefine;
                    fieldData.setFieldKey(fieldDefine.getKey());
                    fieldData.setFieldCode(fieldDefine.getCode());
                    fieldData.setFieldTitle(fieldDefine.getTitle());
                    fieldData.setMaskCode(dataFieldDTO.getDataMaskCode());
                    String data = reader.get(code);
                    if ("".equals(data)) {
                        data = null;
                    }
                    if (1 == (type = DataTypeConvert.dataFieldType2DataType((int)(dataType = ((DataField)fieldDefine).getDataFieldType().getValue()))) && "\u662f".equals(data)) {
                        data = "true";
                    } else if (1 == type && "\u5426".equals(data)) {
                        data = "false";
                    } else if (1 == type && (null == data || "".equals(data))) {
                        data = null;
                    }
                    AbstractData abstractData = AbstractData.valueOf((Object)data, (int)type);
                    fieldData.setData(abstractData);
                } else {
                    String[] split = code.split("\\.");
                    if (1 == split.length) {
                        fieldData.setFieldKey(split[0]);
                    } else if (2 == split.length) {
                        fieldData.setFieldKey(split[1]);
                    }
                    String data = reader.get(code);
                    if ("".equals(data)) {
                        data = null;
                    }
                    AbstractData abstractData = AbstractData.valueOf((Object)data, (int)6);
                    fieldData.setData(abstractData);
                }
                allFieldDatas.add(fieldData);
            }
        }
        return allFieldDatas;
    }

    @NotNull
    private LinkedHashMap<String, FieldInfo> readFileHeaders(CsvReader reader, List<FieldInfo> fieldInfos) throws IOException {
        LinkedHashMap<String, FieldInfo> codeFieldDefineMap = new LinkedHashMap<String, FieldInfo>();
        if (reader.readHeaders()) {
            HashMap<String, String> fieldCodeAndCode = new HashMap<String, String>();
            for (int i = 0; i < reader.getHeaderCount(); ++i) {
                String code = reader.getHeader(i);
                if (code.contains(".")) {
                    String[] split = code.split("\\.");
                    fieldCodeAndCode.put(split[1], code);
                    continue;
                }
                fieldCodeAndCode.put(code, code);
            }
            for (FieldInfo fieldInfo : fieldInfos) {
                if (fieldInfo.getType() == FieldType.FIELD_TYPE_TEXT || fieldInfo.getType() == FieldType.FIELD_TYPE_PICTURE || fieldInfo.getType() == FieldType.FIELD_TYPE_FILE || !fieldCodeAndCode.containsKey(fieldInfo.getCode())) continue;
                codeFieldDefineMap.put((String)fieldCodeAndCode.get(fieldInfo.getCode()), fieldInfo);
            }
            if (fieldCodeAndCode.containsKey("SnapshotGatherFlag")) {
                codeFieldDefineMap.put((String)fieldCodeAndCode.get("SnapshotGatherFlag"), (FieldInfo)null);
            }
            if (fieldCodeAndCode.containsKey("SnapshotGatherGroupKey")) {
                codeFieldDefineMap.put((String)fieldCodeAndCode.get("SnapshotGatherGroupKey"), (FieldInfo)null);
            }
            if (fieldCodeAndCode.containsKey("isFilledRow")) {
                codeFieldDefineMap.put((String)fieldCodeAndCode.get("isFilledRow"), (FieldInfo)null);
            }
            if (fieldCodeAndCode.containsKey("FORMULALINK")) {
                codeFieldDefineMap.put((String)fieldCodeAndCode.get("FORMULALINK"), (FieldInfo)null);
            }
        }
        return codeFieldDefineMap;
    }

    @Nullable
    private File[] getAllRegionFiles(String snapshotFileKey) throws IOException, ObjectStorageException {
        File file;
        boolean newFile;
        String uid = UUID.randomUUID().toString();
        String tempDir = System.getProperty("java.io.tmpdir") + "/snapshot/" + uid + "/";
        File dir = new File(tempDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!(newFile = (file = new File(tempDir + uid)).createNewFile())) {
            throw new RuntimeException("\u521b\u5efa\u5feb\u7167\u538b\u7f29\u6587\u4ef6\u4e0b\u8f7d\u8def\u5f84\u5931\u8d25");
        }
        try (FileOutputStream outputStream = new FileOutputStream(file);){
            SnapshotFileTool snapshotFileTool = new SnapshotFileTool();
            snapshotFileTool.download(snapshotFileKey, outputStream);
            snapshotFileTool.close();
            outputStream.flush();
        }
        FileUtil.unZip((File)file, (String)tempDir);
        boolean delete = file.delete();
        if (!delete) {
            throw new RuntimeException("\u5220\u9664\u5feb\u7167\u538b\u7f29\u6587\u4ef6\u4e0b\u8f7d\u8def\u5f84\u5931\u8d25");
        }
        File[] listFiles = dir.listFiles();
        return listFiles;
    }

    private String getSnapshotTitle(QuerySnapshotDataSourceContext queryContext, DataScheme dataScheme, DataAccessContext context) {
        String snapshotTitle = "";
        NvwaQueryModel snapshotInfoQueryModel = new NvwaQueryModel();
        TableModelDefine snapshotInfoTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        ColumnModelDefine snapshotTitleColum = null;
        List snapshotInfoFields = this.dataModelService.getColumnModelDefinesByTable(snapshotInfoTable.getID());
        for (ColumnModelDefine snapshotInfoField : snapshotInfoFields) {
            snapshotInfoQueryModel.getColumns().add(new NvwaQueryColumn(snapshotInfoField));
            if (snapshotInfoField.getCode().equals("ID")) {
                snapshotInfoQueryModel.getColumnFilters().put(snapshotInfoField, queryContext.getSnapshotId());
                continue;
            }
            if (!snapshotInfoField.getCode().equals("TITLE")) continue;
            snapshotTitleColum = snapshotInfoField;
        }
        INvwaDataAccess snapshotinfoDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(snapshotInfoQueryModel);
        try {
            INvwaDataSet iNvwaDataRows = snapshotinfoDataAccess.executeQueryWithRowKey(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                snapshotTitle = (String)row.getValue(snapshotTitleColum);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return snapshotTitle;
    }

    private String searchFileKey(QuerySnapshotDataSourceContext queryContext, DataScheme dataScheme, DataAccessContext context) {
        String snapshotFileKey = "";
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(snapshotRelTable.getName());
        DimensionValueSet dimensionValueSet = queryContext.getDimensionCombination().toDimensionValueSet();
        ColumnModelDefine snapshotFileColum = null;
        List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
        for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
            if (snapshotRelField.getCode().equals("SNAPSHOTID")) {
                queryModel.getColumnFilters().put(snapshotRelField, queryContext.getSnapshotId());
                continue;
            }
            if (snapshotRelField.getCode().equals("SSFILEKEY")) {
                snapshotFileColum = snapshotRelField;
                continue;
            }
            if (!snapshotRelField.getCode().equals("MDCODE")) continue;
            String dimensionName = dimensionChanger.getDimensionName(snapshotRelField);
            queryModel.getColumnFilters().put(snapshotRelField, dimensionValueSet.getValue(dimensionName));
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            INvwaDataSet iNvwaDataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                snapshotFileKey = (String)row.getValue(snapshotFileColum);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return snapshotFileKey;
    }

    @Override
    public DataSource queryPeriodDataSource(QueryPeriodDataSourceContext queryContext) {
        DataSourceImpl dataSource = new DataSourceImpl();
        dataSource.setTitle(queryContext.getDataName());
        HashMap<String, DataInfo> dataInfoMap = new HashMap<String, DataInfo>();
        dataSource.setData(dataInfoMap);
        try {
            DataRange dataRange = queryContext.getDataRange();
            List<DataRegionRange> formAndFields = dataRange.getFormAndFields();
            for (DataRegionRange formAndField : formAndFields) {
                DataInfo dataInfo = new DataInfo();
                dataInfoMap.put(formAndField.getFormKey(), dataInfo);
                List<DataRegionAndFields> dataRegionAndFieldsList = formAndField.getDataRegionAndFieldsList();
                if (null != dataRegionAndFieldsList && !dataRegionAndFieldsList.isEmpty()) {
                    for (DataRegionAndFields dataRegionAndFields : dataRegionAndFieldsList) {
                        DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(dataRegionAndFields.getDataRegionKey());
                        if (DataRegionKind.DATA_REGION_SIMPLE == dataRegionDefine.getRegionKind()) {
                            FixRegionData fixData = new FixRegionData();
                            dataInfo.setFixData(fixData);
                            fixData.setRegionKey(dataRegionDefine.getKey());
                            fixData.setRegionName(dataRegionDefine.getTitle());
                            List<FieldData> allFieldDatas = this.getFixFieldDatas(queryContext, dataRegionDefine);
                            List<FieldData> fieldDatas = this.filterFixFieldDatas(dataRegionAndFields, allFieldDatas);
                            fixData.setFixDatas(fieldDatas);
                            continue;
                        }
                        FloatRegionData floatRegionData = this.constructionFloatRegionData(dataInfo, dataRegionDefine);
                        List<List<FieldData>> allFloatDatass = this.getFloatFieldDatas(queryContext, dataRegionDefine);
                        List<List<FieldData>> floatDatass = this.filterFloatFieldDatas(dataRegionAndFields, allFloatDatass, dataRegionDefine);
                        floatRegionData.setFloatDatass(floatDatass);
                    }
                    continue;
                }
                List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formAndField.getFormKey());
                for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                    List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                    if (null == fieldKeys || fieldKeys.isEmpty()) continue;
                    if (DataRegionKind.DATA_REGION_SIMPLE == dataRegionDefine.getRegionKind()) {
                        FixRegionData fixData = new FixRegionData();
                        dataInfo.setFixData(fixData);
                        fixData.setRegionKey(dataRegionDefine.getKey());
                        fixData.setRegionName(dataRegionDefine.getTitle());
                        List<FieldData> allFieldDatas = this.getFixFieldDatas(queryContext, dataRegionDefine);
                        fixData.setFixDatas(allFieldDatas);
                        continue;
                    }
                    FloatRegionData floatRegionData = this.constructionFloatRegionData(dataInfo, dataRegionDefine);
                    List<List<FieldData>> allFloatDatass = this.getFloatFieldDatas(queryContext, dataRegionDefine);
                    floatRegionData.setFloatDatass(allFloatDatass);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return dataSource;
    }

    @NotNull
    private List<List<FieldData>> filterFloatFieldDatas(DataRegionAndFields dataRegionAndFields, List<List<FieldData>> allFloatDatass, DataRegionDefine dataRegionDefine) {
        String bizKeyFields = dataRegionDefine.getBizKeyFields();
        List<Object> bizKeys = null;
        if (StringUtils.isNotEmpty((String)bizKeyFields)) {
            String[] bizKeyFieldKeys = bizKeyFields.split(";");
            bizKeys = Arrays.asList(bizKeyFieldKeys);
        } else {
            bizKeys = new ArrayList();
        }
        ArrayList<List<FieldData>> floatDatass = new ArrayList<List<FieldData>>();
        List<String> fieldKeys = dataRegionAndFields.getFieldKeys();
        if (null != fieldKeys && !fieldKeys.isEmpty()) {
            for (List<FieldData> allFloatDatas : allFloatDatass) {
                ArrayList<FieldData> floatDatas = new ArrayList<FieldData>();
                block1: for (FieldData allFloatData : allFloatDatas) {
                    if (StringUtils.isEmpty((String)allFloatData.getFieldCode())) continue;
                    if (allFloatData.getFieldCode().equals("SnapshotGatherFlag") || allFloatData.getFieldCode().equals("SnapshotGatherGroupKey") || allFloatData.getFieldCode().equals("isFilledRow")) {
                        floatDatas.add(allFloatData);
                        continue;
                    }
                    for (String fieldKey : fieldKeys) {
                        if (!fieldKey.equals(allFloatData.getFieldKey()) && !"BIZKEYORDER".equals(allFloatData.getFieldCode()) && !bizKeys.contains(allFloatData.getFieldKey())) continue;
                        floatDatas.add(allFloatData);
                        continue block1;
                    }
                }
                floatDatass.add(floatDatas);
            }
        } else {
            floatDatass.addAll(allFloatDatass);
        }
        return floatDatass;
    }

    @NotNull
    private FloatRegionData constructionFloatRegionData(DataInfo dataInfo, DataRegionDefine dataRegionDefine) throws Exception {
        FloatRegionData floatRegionData = new FloatRegionData();
        dataInfo.getFloatDatas().add(floatRegionData);
        floatRegionData.setRegionKey(dataRegionDefine.getKey());
        floatRegionData.setRegionName(dataRegionDefine.getTitle());
        String bizKeyFields = dataRegionDefine.getBizKeyFields();
        if (StringUtils.isNotEmpty((String)bizKeyFields)) {
            String[] bizKeyFieldKeys = bizKeyFields.split(";");
            ArrayList<String> naturalKeys = new ArrayList<String>();
            ArrayList<String> naturalCodes = new ArrayList<String>();
            for (String bizKeyFieldKey : bizKeyFieldKeys) {
                FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(bizKeyFieldKey);
                if ("BZ1".equals(fieldDefine.getCode())) continue;
                naturalKeys.add(fieldDefine.getKey());
                naturalCodes.add(fieldDefine.getCode());
            }
            floatRegionData.setNaturalKeys(naturalKeys);
            floatRegionData.setNaturalCodes(naturalCodes);
        }
        floatRegionData.setAllowDuplicateKey(dataRegionDefine.getAllowDuplicateKey());
        return floatRegionData;
    }

    @NotNull
    private List<List<FieldData>> getFloatFieldDatas(QueryPeriodDataSourceContext queryContext, DataRegionDefine dataRegionDefine) {
        ArrayList<List<FieldData>> allFloatDatass = new ArrayList<List<FieldData>>();
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)dataRegionDefine.getKey(), (DimensionCombination)queryContext.getDimensionCombination());
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        List rowDatas = iRegionDataSet.getRowData();
        for (IRowData rowData : rowDatas) {
            FieldData fieldData;
            AbstractData abstractData;
            FieldData gatherFlagFieldData;
            ArrayList<FieldData> allFloatDatas = new ArrayList<FieldData>();
            if (rowData.getGroupTreeDeep() >= 0) {
                gatherFlagFieldData = new FieldData();
                gatherFlagFieldData.setFieldCode("SnapshotGatherFlag");
                AbstractData gatherFlagAbstractData = AbstractData.valueOf((Object)Integer.toString(rowData.getGroupTreeDeep()), (int)6);
                gatherFlagFieldData.setData(gatherFlagAbstractData);
                allFloatDatas.add(gatherFlagFieldData);
                Object value = rowData.getDimension().getValue("GROUP_KEY");
                FieldData gatherGroupKeyFieldData = new FieldData();
                gatherGroupKeyFieldData.setFieldCode("SnapshotGatherGroupKey");
                AbstractData gatherGroupKeyAbstractData = AbstractData.valueOf((Object)((String)value), (int)6);
                gatherGroupKeyFieldData.setData(gatherGroupKeyAbstractData);
                allFloatDatas.add(gatherGroupKeyFieldData);
            } else {
                gatherFlagFieldData = new FieldData();
                gatherFlagFieldData.setFieldCode("SnapshotGatherFlag");
                abstractData = AbstractData.valueOf((Object)"-1", (int)6);
                gatherFlagFieldData.setData(abstractData);
                allFloatDatas.add(gatherFlagFieldData);
                FieldData gatherGroupKeyFieldData = new FieldData();
                gatherGroupKeyFieldData.setFieldCode("SnapshotGatherGroupKey");
                AbstractData gatherGroupKeyAbstractData = AbstractData.valueOf((Object)"-1", (int)6);
                gatherGroupKeyFieldData.setData(gatherGroupKeyAbstractData);
                allFloatDatas.add(gatherGroupKeyFieldData);
            }
            if (rowData.isFilledRow()) {
                fieldData = new FieldData();
                fieldData.setFieldCode("isFilledRow");
                abstractData = AbstractData.valueOf((Object)"1", (int)6);
                fieldData.setData(abstractData);
                allFloatDatas.add(fieldData);
            } else {
                fieldData = new FieldData();
                fieldData.setFieldCode("isFilledRow");
                abstractData = AbstractData.valueOf((Object)"0", (int)6);
                fieldData.setData(abstractData);
                allFloatDatas.add(fieldData);
            }
            String bizKeyOrder = rowData.getRecKey();
            FieldData bizFieldData = new FieldData();
            bizFieldData.setFieldCode("BIZKEYORDER");
            bizFieldData.setData(AbstractData.valueOf((Object)bizKeyOrder, (int)6));
            allFloatDatas.add(bizFieldData);
            List linkDataValues = rowData.getLinkDataValues();
            for (IDataValue linkDataValue : linkDataValues) {
                IMetaData metaData = linkDataValue.getMetaData();
                DataField dataField = metaData.getDataField();
                if (null == dataField || dataField.getCode().equals("FLOATORDER") || dataField.getDataFieldType() == DataFieldType.CLOB || dataField.getDataFieldType() == DataFieldType.PICTURE || dataField.getDataFieldType() == DataFieldType.FILE) continue;
                FieldData fieldData2 = new FieldData();
                fieldData2.setFieldKey(dataField.getKey());
                fieldData2.setFieldCode(dataField.getCode());
                fieldData2.setFieldTitle(dataField.getTitle());
                fieldData2.setMaskCode(dataField.getDataMaskCode());
                fieldData2.setData(linkDataValue.getAbstractData());
                allFloatDatas.add(fieldData2);
            }
            allFloatDatass.add(allFloatDatas);
        }
        return allFloatDatass;
    }

    @NotNull
    private List<FieldData> filterFixFieldDatas(DataRegionAndFields dataRegionAndFields, List<FieldData> allFieldDatas) {
        ArrayList<FieldData> fieldDatas = new ArrayList<FieldData>();
        List<String> fieldKeys = dataRegionAndFields.getFieldKeys();
        if (null != fieldKeys && !fieldKeys.isEmpty()) {
            block0: for (FieldData fieldData : allFieldDatas) {
                if (StringUtils.isEmpty((String)fieldData.getFieldCode())) continue;
                if (fieldData.getFieldCode().equals("SnapshotGatherFlag") || fieldData.getFieldCode().equals("SnapshotGatherGroupKey") || fieldData.getFieldCode().equals("isFilledRow")) {
                    fieldDatas.add(fieldData);
                    continue;
                }
                for (String fieldKey : fieldKeys) {
                    if (!fieldKey.equals(fieldData.getFieldKey())) continue;
                    fieldDatas.add(fieldData);
                    continue block0;
                }
            }
        } else {
            fieldDatas.addAll(allFieldDatas);
        }
        return fieldDatas;
    }

    @NotNull
    private List<FieldData> getFixFieldDatas(QueryPeriodDataSourceContext queryContext, DataRegionDefine dataRegionDefine) {
        ArrayList<FieldData> allFieldDatas = new ArrayList<FieldData>();
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)dataRegionDefine.getKey(), (DimensionCombination)queryContext.getDimensionCombination());
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        List rowData = iRegionDataSet.getRowData();
        if (0 != rowData.size()) {
            IRowData iRowData = (IRowData)rowData.get(0);
            List linkDataValues = iRowData.getLinkDataValues();
            for (IDataValue linkDataValue : linkDataValues) {
                IMetaData metaData = linkDataValue.getMetaData();
                DataField dataField = metaData.getDataField();
                if (null == dataField || dataField.getDataFieldType() == DataFieldType.CLOB || dataField.getDataFieldType() == DataFieldType.PICTURE || dataField.getDataFieldType() == DataFieldType.FILE) continue;
                FieldData fieldData = new FieldData();
                fieldData.setFieldKey(dataField.getKey());
                fieldData.setFieldCode(dataField.getCode());
                fieldData.setFieldTitle(dataField.getTitle());
                fieldData.setMaskCode(dataField.getDataMaskCode());
                fieldData.setData(linkDataValue.getAbstractData());
                allFieldDatas.add(fieldData);
            }
        } else {
            List metaDatas = iRegionDataSet.getMetaData();
            for (IMetaData metaData : metaDatas) {
                DataField dataField = metaData.getDataField();
                if (null == dataField || dataField.getDataFieldType() == DataFieldType.CLOB || dataField.getDataFieldType() == DataFieldType.PICTURE || dataField.getDataFieldType() == DataFieldType.FILE) continue;
                FieldData fieldData = new FieldData();
                fieldData.setFieldKey(dataField.getKey());
                fieldData.setFieldCode(dataField.getCode());
                fieldData.setFieldTitle(dataField.getTitle());
                fieldData.setMaskCode(dataField.getDataMaskCode());
                int dataType = dataField.getDataFieldType().getValue();
                int type = DataTypeConvert.dataFieldType2DataType((int)dataType);
                AbstractData abstractData = AbstractData.valueOf(null, (int)type);
                fieldData.setData(abstractData);
                allFieldDatas.add(fieldData);
            }
        }
        return allFieldDatas;
    }
}

