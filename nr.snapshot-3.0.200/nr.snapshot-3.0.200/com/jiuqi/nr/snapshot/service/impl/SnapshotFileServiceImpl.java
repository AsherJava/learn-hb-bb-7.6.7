/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.common.ExtConstants
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.snapshot.service.impl;

import com.csvreader.CsvWriter;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.snapshot.message.RegionDataInfo;
import com.jiuqi.nr.snapshot.service.SnapshotFileService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SnapshotFileServiceImpl
implements SnapshotFileService {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotFileServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Override
    public String createSnapshotFile(String dataSchemeKey, DimensionCollection dimensionCollection, List<String> formKeys) {
        String path = ExtConstants.ROOTPATH + "/" + UUID.randomUUID().toString() + File.separator + "SnapshotDatas.zip";
        try {
            File file = FileUtil.createIfNotExists((String)path);
            try (FileOutputStream fos = new FileOutputStream(file);
                 ZipOutputStream zipos = new ZipOutputStream(fos);){
                zipos.setMethod(8);
                CsvWriter csvWriter = new CsvWriter((OutputStream)zipos, new Character(',').charValue(), Charset.forName("UTF-8"));
                List<RegionDataInfo> result = this.getRegionDatas(dimensionCollection, formKeys);
                this.initZipFile(result, zipos, csvWriter, dimensionCollection, dataSchemeKey);
                csvWriter.close();
            }
        }
        catch (IOException e) {
            logger.info("\u6587\u4ef6\u521b\u5efa\u51fa\u9519{}".concat(e.getMessage()));
        }
        return path;
    }

    private List<RegionDataInfo> getRegionDatas(DimensionCollection dimensionCollection, List<String> formKeys) {
        ArrayList<RegionDataInfo> regionDataInfos = new ArrayList<RegionDataInfo>();
        for (String formKey : formKeys) {
            List regionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
            for (DataRegionDefine regionDefine : regionDefines) {
                RegionSettingDefine regionSetting = this.runTimeViewController.getRegionSetting(regionDefine.getKey());
                List entityDefaultValues = null;
                if (null != regionSetting) {
                    entityDefaultValues = regionSetting.getEntityDefaultValue();
                }
                RegionDataInfo regionDataInfo = new RegionDataInfo();
                regionDataInfo.setRegionKey(regionDefine.getKey());
                List dimensionCombinations = dimensionCollection.getDimensionCombinations();
                for (DimensionCombination dimensionCombination : dimensionCombinations) {
                    DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                    if (null != entityDefaultValues && !CollectionUtils.isEmpty(entityDefaultValues)) {
                        for (EntityDefaultValue entityDefaultValue : entityDefaultValues) {
                            String value = entityDefaultValue.getItemValue();
                            String dimensionName = this.entityMetaService.getDimensionName(entityDefaultValue.getEntityId());
                            dimensionValueSet.setValue(dimensionName, (Object)value);
                        }
                    }
                    DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
                    QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)regionDefine.getKey(), (DimensionCombination)dimensionCombinationBuilder.getCombination());
                    IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
                    if (regionDataInfo.getMetaDatas().isEmpty()) {
                        regionDataInfo.getMetaDatas().addAll(iRegionDataSet.getMetaData());
                    }
                    regionDataInfo.getRowDatas().addAll(iRegionDataSet.getRowData());
                }
                regionDataInfos.add(regionDataInfo);
            }
        }
        return regionDataInfos;
    }

    private void initZipFile(List<RegionDataInfo> result, ZipOutputStream zipos, CsvWriter csvWriter, DimensionCollection dimensionCollection, String dataSchemeKey) {
        for (RegionDataInfo item : result) {
            String fileName = "";
            try {
                ArrayList<String> fieldCode = new ArrayList<String>();
                List<IMetaData> metaDatas = item.getMetaDatas();
                if (metaDatas.isEmpty()) continue;
                DataTable dataTable = null;
                boolean flag = false;
                for (IMetaData metaData : metaDatas) {
                    DataField dataField = metaData.getDataField();
                    if (null == dataField) continue;
                    flag = true;
                    String dataTableKey = metaData.getDataField().getDataTableKey();
                    dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
                    break;
                }
                fieldCode.add("SnapshotGatherFlag");
                fieldCode.add("SnapshotGatherGroupKey");
                fieldCode.add("isFilledRow");
                String tableCode = "";
                DimensionValueSet dimensionValueSet = dimensionCollection.combineDim();
                if (!flag) {
                    fieldCode.add("BIZKEYORDER");
                    List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey);
                    block4: for (int i = 0; i < dimensionValueSet.size(); ++i) {
                        String dimensionName = dimensionValueSet.getName(i);
                        for (DataDimension dataDimension : dataSchemeDimension) {
                            IEntityDefine iEntityDefine;
                            if (DimensionType.UNIT.getValue() == dataDimension.getDimensionType().getValue()) {
                                iEntityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                                if (!dimensionName.equals(iEntityDefine.getDimensionName())) continue;
                                fieldCode.add("MDCODE");
                                continue block4;
                            }
                            if (DimensionType.PERIOD.getValue() == dataDimension.getDimensionType().getValue()) {
                                IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                                IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dataDimension.getDimKey());
                                if (!dimensionName.equals(periodEntity.getDimensionName())) continue;
                                fieldCode.add("DATATIME");
                                continue block4;
                            }
                            if (DimensionType.DIMENSION.getValue() != dataDimension.getDimensionType().getValue()) continue;
                            if ("ADJUST".equals(dataDimension.getDimKey())) {
                                if (!dimensionName.equals("ADJUST")) continue;
                                fieldCode.add("ADJUST");
                                continue block4;
                            }
                            iEntityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                            if (!dimensionName.equals(iEntityDefine.getDimensionName())) continue;
                            fieldCode.add(iEntityDefine.getCode());
                            continue block4;
                        }
                    }
                } else {
                    tableCode = dataTable.getCode();
                    fieldCode.add(tableCode + "." + "BIZKEYORDER");
                    List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(dataTable.getKey());
                    String tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName();
                    ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                    IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
                    for (int i = 0; i < dimensionValueSet.size(); ++i) {
                        String dimensionName = dimensionValueSet.getName(i);
                        FieldDefine dimensionField = dataAssist.getDimensionField(tableName, dimensionName);
                        fieldCode.add(tableCode + "." + dimensionField.getCode());
                    }
                }
                ArrayList<Integer> noFieldIndex = new ArrayList<Integer>();
                for (int i = 0; i < metaDatas.size(); ++i) {
                    DataField dataField = metaDatas.get(i).getDataField();
                    if (null != dataField) {
                        fieldCode.add(tableCode + "." + dataField.getCode());
                        continue;
                    }
                    if (metaDatas.get(i).isFormulaLink()) {
                        fieldCode.add("FORMULALINK." + metaDatas.get(i).getDataLinkDefine().getKey());
                        continue;
                    }
                    noFieldIndex.add(i);
                }
                DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(item.getRegionKey());
                FormDefine formDefine = this.runTimeViewController.queryFormById(dataRegionDefine.getFormKey());
                fileName = formDefine.getFormCode();
                if (dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_ROW_LIST)) {
                    fileName = fileName + "_F" + dataRegionDefine.getRegionTop();
                }
                fileName = fileName + ".csv";
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipos.putNextEntry(zipEntry);
                String[] fieldDefineArray = new String[fieldCode.size()];
                for (int i = 0; i < fieldCode.size(); ++i) {
                    fieldDefineArray[i] = (String)fieldCode.get(i);
                }
                csvWriter.writeRecord(fieldDefineArray);
                ArrayList rowsDatas = new ArrayList();
                List<IRowData> iRowDatas = item.getRowDatas();
                for (IRowData iRowData : iRowDatas) {
                    ArrayList<String> rowDataArray = new ArrayList<String>();
                    if (iRowData.getGroupTreeDeep() >= 0) {
                        rowDataArray.add(Integer.toString(iRowData.getGroupTreeDeep()));
                        Object value = iRowData.getDimension().getValue("GROUP_KEY");
                        rowDataArray.add((String)value);
                    } else {
                        rowDataArray.add("-1");
                        rowDataArray.add("-1");
                    }
                    if (iRowData.isFilledRow()) {
                        rowDataArray.add("1");
                    } else {
                        rowDataArray.add("0");
                    }
                    rowDataArray.add(iRowData.getRecKey());
                    DimensionValueSet rowDimensionValueSet = iRowData.getMasterDimension().toDimensionValueSet();
                    for (int i = 0; i < dimensionValueSet.size(); ++i) {
                        String name = dimensionValueSet.getName(i);
                        rowDataArray.add((String)rowDimensionValueSet.getValue(name));
                    }
                    List linkDataValues = iRowData.getLinkDataValues();
                    for (int i = 0; i < linkDataValues.size(); ++i) {
                        if (noFieldIndex.contains(i)) continue;
                        rowDataArray.add(((IDataValue)linkDataValues.get(i)).getAsString());
                    }
                    rowsDatas.add(rowDataArray);
                }
                for (ArrayList arrayList : rowsDatas) {
                    if (arrayList.size() == 0) continue;
                    String[] dataArray = new String[arrayList.size()];
                    arrayList.toArray(dataArray);
                    csvWriter.writeRecord(dataArray);
                }
                csvWriter.flush();
                zipos.closeEntry();
            }
            catch (IOException e) {
                logger.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
            }
        }
    }
}

