/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.data.CurrencyData
 *  com.jiuqi.np.dataengine.data.DateData
 *  com.jiuqi.np.dataengine.data.DateTimeData
 *  com.jiuqi.np.dataengine.data.FloatData
 *  com.jiuqi.np.dataengine.data.IntData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.snapshot.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.CurrencyData;
import com.jiuqi.np.dataengine.data.DateData;
import com.jiuqi.np.dataengine.data.DateTimeData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.snapshot.consts.RegionType;
import com.jiuqi.nr.snapshot.message.DataInfo;
import com.jiuqi.nr.snapshot.message.DifferenceData;
import com.jiuqi.nr.snapshot.message.DifferenceDataItem;
import com.jiuqi.nr.snapshot.message.FieldData;
import com.jiuqi.nr.snapshot.message.FixRegionData;
import com.jiuqi.nr.snapshot.message.FixedDataRegionCompareDifference;
import com.jiuqi.nr.snapshot.message.FloatCompareDifference;
import com.jiuqi.nr.snapshot.message.FloatDataRegionCompareDifference;
import com.jiuqi.nr.snapshot.message.FloatRegionData;
import com.jiuqi.nr.snapshot.message.NaturalAndData;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import com.jiuqi.nr.snapshot.service.DataSource;
import com.jiuqi.nr.snapshot.service.DataSourceOperationService;
import com.jiuqi.nr.snapshot.service.IDataRegionCompareDifference;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceOperationServiceImpl
implements DataSourceOperationService {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceOperationServiceImpl.class);
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private DesensitizedEncryptor encryptor;

    @Override
    public List<ComparisonResult> comparison(DataSource initialData, List<DataSource> compareDatas, List<String> formKeys) {
        ArrayList<ComparisonResult> comparisonResults = new ArrayList<ComparisonResult>();
        for (String formKey : formKeys) {
            List<FloatRegionData> initFloatRegionDatas;
            ComparisonResult comparisonResult = new ComparisonResult();
            comparisonResult.setFormKey(formKey);
            List<String> dataSourceNames = compareDatas.stream().map(t -> t.getTitle()).collect(Collectors.toList());
            comparisonResult.setDataSourceNames(dataSourceNames);
            HashMap<String, IDataRegionCompareDifference> differenceDatas = new HashMap<String, IDataRegionCompareDifference>();
            DataInfo initData = initialData.getData(formKey);
            if (null == initData) continue;
            FixRegionData initFixRegionData = initData.getFixData();
            if (null != initFixRegionData) {
                this.compareFixRegion(compareDatas, formKey, differenceDatas, initFixRegionData);
            }
            if (null != (initFloatRegionDatas = initData.getFloatDatas()) && !initFloatRegionDatas.isEmpty()) {
                this.compareFloatRegion(compareDatas, formKey, differenceDatas, initFloatRegionDatas);
            }
            comparisonResult.setDifferenceDatas(differenceDatas);
            comparisonResults.add(comparisonResult);
        }
        return comparisonResults;
    }

    private void compareFixRegion(List<DataSource> compareDatas, String formKey, Map<String, IDataRegionCompareDifference> differenceDatas, FixRegionData initFixRegionData) {
        List comparDatas = compareDatas.stream().map(t -> t.getData(formKey)).collect(Collectors.toList());
        ArrayList<FixRegionData> comparFixRegionDatas = new ArrayList<FixRegionData>();
        if (null != comparDatas) {
            for (Object comparData : comparDatas) {
                if (null != comparData && null != ((DataInfo)comparData).getFixData()) {
                    comparFixRegionDatas.add(((DataInfo)comparData).getFixData());
                    continue;
                }
                comparFixRegionDatas.add(null);
            }
        }
        ArrayList compareFieldKeyAndFieldDataMaps = new ArrayList();
        for (FixRegionData comparFixRegionData : comparFixRegionDatas) {
            HashMap<String, FieldData> compareFieldKeyAndFieldDataMap = new HashMap<String, FieldData>();
            if (null == comparFixRegionData) continue;
            List<FieldData> compareFixDatas = comparFixRegionData.getFixDatas();
            for (FieldData compareFixData : compareFixDatas) {
                compareFieldKeyAndFieldDataMap.put(compareFixData.getFieldKey(), compareFixData);
            }
            compareFieldKeyAndFieldDataMaps.add(compareFieldKeyAndFieldDataMap);
        }
        FixedDataRegionCompareDifference fixedDataRegionCompareDifference = new FixedDataRegionCompareDifference();
        fixedDataRegionCompareDifference.setRegionKey(initFixRegionData.getRegionKey());
        fixedDataRegionCompareDifference.setRegionName(initFixRegionData.getRegionName());
        fixedDataRegionCompareDifference.setRegionType(RegionType.FIXED);
        int differenceCount = 0;
        ArrayList<DifferenceDataItem> differenceDataItems = new ArrayList<DifferenceDataItem>();
        List<FieldData> initFixDatas = initFixRegionData.getFixDatas();
        for (FieldData initFixData : initFixDatas) {
            if (StringUtils.isEmpty((String)initFixData.getFieldCode()) || StringUtils.isEmpty((String)initFixData.getFieldTitle())) continue;
            boolean haveDifference = false;
            DifferenceDataItem differenceDataItem = new DifferenceDataItem();
            differenceDataItem.setFieldKey(initFixData.getFieldKey());
            differenceDataItem.setFieldCode(initFixData.getFieldCode());
            differenceDataItem.setFieldTitle(initFixData.getFieldTitle());
            ArrayList<DifferenceData> difDatas = new ArrayList<DifferenceData>();
            for (Map map : compareFieldKeyAndFieldDataMaps) {
                FieldData compareFixData = null;
                if (map.containsKey(initFixData.getFieldKey())) {
                    compareFixData = (FieldData)map.get(initFixData.getFieldKey());
                }
                if (null != compareFixData) {
                    this.structureFifferenceData(initFixData, difDatas, compareFixData);
                    if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) continue;
                    haveDifference = true;
                    continue;
                }
                this.structureFifferenceData(initFixData, difDatas);
                if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) continue;
                haveDifference = true;
            }
            if (comparFixRegionDatas.isEmpty()) {
                this.structureFifferenceData(initFixData, difDatas);
                if (0.0 != ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) {
                    haveDifference = true;
                }
            }
            if (haveDifference) {
                ++differenceCount;
            }
            differenceDataItem.setDifferenceDatas(difDatas);
            differenceDataItems.add(differenceDataItem);
        }
        fixedDataRegionCompareDifference.setDifferenceDataItems(differenceDataItems);
        fixedDataRegionCompareDifference.setDifferenceCount(differenceCount);
        differenceDatas.put(initFixRegionData.getRegionKey(), fixedDataRegionCompareDifference);
    }

    private void compareFloatRegion(List<DataSource> compareDatas, String formKey, Map<String, IDataRegionCompareDifference> differenceDatas, List<FloatRegionData> initFloatRegionDatas) {
        for (FloatRegionData initFloatRegionData : initFloatRegionDatas) {
            List comparDatas = compareDatas.stream().map(t -> t.getData(formKey)).collect(Collectors.toList());
            ArrayList<List<FloatRegionData>> comparFloatRegionDatas = new ArrayList<List<FloatRegionData>>();
            if (null != comparDatas) {
                for (DataInfo comparData : comparDatas) {
                    if (null != comparData && !comparData.getFloatDatas().isEmpty()) {
                        comparFloatRegionDatas.add(comparData.getFloatDatas());
                        continue;
                    }
                    ArrayList floatRegionDataList = new ArrayList();
                    comparFloatRegionDatas.add(floatRegionDataList);
                }
            }
            FloatDataRegionCompareDifference floatDataRegionCompareDifference = new FloatDataRegionCompareDifference();
            floatDataRegionCompareDifference.setRegionKey(initFloatRegionData.getRegionKey());
            floatDataRegionCompareDifference.setRegionName(initFloatRegionData.getRegionName());
            floatDataRegionCompareDifference.setRegionType(RegionType.FLOAT);
            floatDataRegionCompareDifference.setNaturalKeys(initFloatRegionData.getNaturalKeys());
            ArrayList<FloatCompareDifference> floatCompareDifferences = new ArrayList<FloatCompareDifference>();
            List<String> initNaturalCodes = initFloatRegionData.getNaturalCodes();
            List<List<FieldData>> initFloatDatass = initFloatRegionData.getFloatDatass();
            int differenceCount = 0;
            if (null != initNaturalCodes && initFloatRegionData.isAllowDuplicateKey()) {
                LinkedHashMap<String, List<NaturalAndData>> initNaturalAndDatasMap = this.getStringListLinkedHashMap(initNaturalCodes, initFloatDatass);
                List<LinkedHashMap<String, List<NaturalAndData>>> comparNaturalAndDatasMaps = this.getLinkedHashMaps(initFloatRegionData, comparFloatRegionDatas, initNaturalCodes, initNaturalAndDatasMap);
                differenceCount = this.compareInOrderWithPrimary(floatCompareDifferences, initNaturalCodes, initNaturalAndDatasMap, comparNaturalAndDatasMaps);
            } else {
                this.addInitFloatDatassNoRow(initFloatRegionData, comparFloatRegionDatas, initNaturalCodes, initFloatDatass);
                for (List<FieldData> initFloatDatas : initFloatDatass) {
                    boolean canExclude = false;
                    for (FieldData fieldData : initFloatDatas) {
                        String isFileRow;
                        if (!"isFilledRow".equals(fieldData.getFieldCode()) || !"1".equals(isFileRow = fieldData.getData().getAsString())) continue;
                        canExclude = true;
                        break;
                    }
                    if (canExclude) continue;
                    FloatCompareDifference floatCompareDifference = new FloatCompareDifference();
                    for (Object initFloatData3 : initFloatDatas) {
                        if (!"BIZKEYORDER".equals(((FieldData)initFloatData3).getFieldCode())) continue;
                        floatCompareDifference.setBizKeyOrder(((FieldData)initFloatData3).getData());
                        break;
                    }
                    if (null != initNaturalCodes) {
                        Object initFloatData3;
                        ArrayList<AbstractData> arrayList = new ArrayList<AbstractData>();
                        initFloatData3 = initNaturalCodes.iterator();
                        block5: while (initFloatData3.hasNext()) {
                            String initNaturalCode = (String)initFloatData3.next();
                            for (FieldData initFloatData4 : initFloatDatas) {
                                if (!initNaturalCode.equals(initFloatData4.getFieldCode())) continue;
                                arrayList.add(initFloatData4.getData());
                                continue block5;
                            }
                        }
                        floatCompareDifference.setNaturalDatas(arrayList);
                        boolean isGather = false;
                        StringBuilder initGatherData = new StringBuilder();
                        for (FieldData initFloatData : initFloatDatas) {
                            if ("SnapshotGatherFlag".equals(initFloatData.getFieldCode())) {
                                floatCompareDifference.setGroupTreeDeep(initFloatData.getData());
                                initGatherData.append(initFloatData.getData().getAsString());
                                if ("-1".equals(initFloatData.getData().getAsString())) continue;
                                isGather = true;
                                continue;
                            }
                            if (!"SnapshotGatherGroupKey".equals(initFloatData.getFieldCode())) continue;
                            floatCompareDifference.setGroupKey(initFloatData.getData());
                            initGatherData.append(initFloatData.getData().getAsString());
                        }
                        ArrayList<DifferenceDataItem> differenceDataItems = new ArrayList<DifferenceDataItem>();
                        differenceCount += this.compareByPrimaryKey(initFloatRegionData, comparFloatRegionDatas, initFloatDatas, arrayList, differenceDataItems, isGather, initGatherData.toString());
                        floatCompareDifference.setDifferenceDataItems(differenceDataItems);
                    } else {
                        ArrayList<DifferenceDataItem> arrayList = new ArrayList<DifferenceDataItem>();
                        differenceCount += this.compareInOrder(initFloatRegionData, comparFloatRegionDatas, initFloatDatass, initFloatDatas, arrayList);
                        floatCompareDifference.setDifferenceDataItems(arrayList);
                    }
                    floatCompareDifferences.add(floatCompareDifference);
                }
            }
            floatDataRegionCompareDifference.setDifferenceCount(differenceCount);
            floatDataRegionCompareDifference.setFloatCompareDifferences(floatCompareDifferences);
            differenceDatas.put(initFloatRegionData.getRegionKey(), floatDataRegionCompareDifference);
        }
    }

    private int compareInOrderWithPrimary(List<FloatCompareDifference> floatCompareDifferences, List<String> initNaturalCodes, LinkedHashMap<String, List<NaturalAndData>> initNaturalAndDatasMap, List<LinkedHashMap<String, List<NaturalAndData>>> comparNaturalAndDatasMaps) {
        int differenceCount = 0;
        for (String initNaturalKey : initNaturalAndDatasMap.keySet()) {
            List<NaturalAndData> initNaturalAndDatas = initNaturalAndDatasMap.get(initNaturalKey);
            for (NaturalAndData initNaturalAndData : initNaturalAndDatas) {
                List<FieldData> initFloatDatas = initNaturalAndData.getFloatDatas();
                FloatCompareDifference floatCompareDifference = new FloatCompareDifference();
                for (FieldData fieldData : initFloatDatas) {
                    if (!"BIZKEYORDER".equals(fieldData.getFieldCode())) continue;
                    floatCompareDifference.setBizKeyOrder(fieldData.getData());
                    break;
                }
                ArrayList<AbstractData> naturalDatas = new ArrayList<AbstractData>();
                block3: for (String initNaturalCode : initNaturalCodes) {
                    for (FieldData initFloatData3 : initFloatDatas) {
                        if (!initNaturalCode.equals(initFloatData3.getFieldCode())) continue;
                        naturalDatas.add(initFloatData3.getData());
                        continue block3;
                    }
                }
                floatCompareDifference.setNaturalDatas(naturalDatas);
                for (FieldData initFloatData4 : initFloatDatas) {
                    if ("SnapshotGatherFlag".equals(initFloatData4.getFieldCode())) {
                        floatCompareDifference.setGroupTreeDeep(initFloatData4.getData());
                        continue;
                    }
                    if (!"SnapshotGatherGroupKey".equals(initFloatData4.getFieldCode())) continue;
                    floatCompareDifference.setGroupKey(initFloatData4.getData());
                }
                ArrayList<DifferenceDataItem> arrayList = new ArrayList<DifferenceDataItem>();
                for (FieldData initFloatData5 : initFloatDatas) {
                    if (initNaturalCodes.contains(initFloatData5.getFieldCode()) || "BIZKEYORDER".equals(initFloatData5.getFieldCode()) || "SnapshotGatherFlag".equals(initFloatData5.getFieldCode()) || "SnapshotGatherGroupKey".equals(initFloatData5.getFieldCode()) || "isFilledRow".equals(initFloatData5.getFieldCode()) || StringUtils.isEmpty((String)initFloatData5.getFieldCode()) || StringUtils.isEmpty((String)initFloatData5.getFieldTitle())) continue;
                    boolean haveDifference = false;
                    DifferenceDataItem differenceDataItem = new DifferenceDataItem();
                    differenceDataItem.setFieldKey(initFloatData5.getFieldKey());
                    differenceDataItem.setFieldCode(initFloatData5.getFieldCode());
                    differenceDataItem.setFieldTitle(initFloatData5.getFieldTitle());
                    ArrayList<DifferenceData> difDatas = new ArrayList<DifferenceData>();
                    for (LinkedHashMap<String, List<NaturalAndData>> comparNaturalAndDatasMap : comparNaturalAndDatasMaps) {
                        List<NaturalAndData> comparNaturalAndData = comparNaturalAndDatasMap.get(initNaturalKey);
                        if (null != comparNaturalAndData) {
                            int rowIndex = initNaturalAndDatas.indexOf(initNaturalAndData);
                            if (comparNaturalAndData.size() > rowIndex) {
                                FieldData compareFloatData = null;
                                List<FieldData> comparFloatDatas = comparNaturalAndData.get(rowIndex).getFloatDatas();
                                for (FieldData index : comparFloatDatas) {
                                    if (!initFloatData5.getFieldKey().equals(index.getFieldKey())) continue;
                                    compareFloatData = index;
                                    break;
                                }
                                if (null == compareFloatData) continue;
                                this.structureFifferenceData(initFloatData5, difDatas, compareFloatData);
                                if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) continue;
                                haveDifference = true;
                                continue;
                            }
                            this.structureFifferenceData(initFloatData5, difDatas);
                            if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) continue;
                            haveDifference = true;
                            continue;
                        }
                        this.structureFifferenceData(initFloatData5, difDatas);
                        if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) continue;
                        haveDifference = true;
                    }
                    if (comparNaturalAndDatasMaps.isEmpty()) {
                        this.structureFifferenceData(initFloatData5, difDatas);
                        if (0.0 != ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) {
                            haveDifference = true;
                        }
                    }
                    if (haveDifference) {
                        ++differenceCount;
                    }
                    differenceDataItem.setDifferenceDatas(difDatas);
                    arrayList.add(differenceDataItem);
                }
                floatCompareDifference.setDifferenceDataItems(arrayList);
                floatCompareDifferences.add(floatCompareDifference);
            }
        }
        return differenceCount;
    }

    @NotNull
    private List<LinkedHashMap<String, List<NaturalAndData>>> getLinkedHashMaps(FloatRegionData initFloatRegionData, List<List<FloatRegionData>> comparFloatRegionDatas, List<String> initNaturalCodes, LinkedHashMap<String, List<NaturalAndData>> initNaturalAndDatasMap) {
        ArrayList<LinkedHashMap<String, List<NaturalAndData>>> comparNaturalAndDatasMaps = new ArrayList<LinkedHashMap<String, List<NaturalAndData>>>();
        for (List<FloatRegionData> comparFloatRegionData : comparFloatRegionDatas) {
            FloatRegionData comparFloatRegionDatum = null;
            for (FloatRegionData index : comparFloatRegionData) {
                if (!initFloatRegionData.getRegionKey().equals(index.getRegionKey())) continue;
                comparFloatRegionDatum = index;
                break;
            }
            LinkedHashMap<String, List<NaturalAndData>> comparNaturalAndDatasMap = new LinkedHashMap<String, List<NaturalAndData>>();
            comparNaturalAndDatasMaps.add(comparNaturalAndDatasMap);
            if (null == comparFloatRegionDatum) continue;
            ArrayList comparNaturalDatass = new ArrayList();
            List<List<FieldData>> comparFloatDatass = comparFloatRegionDatum.getFloatDatass();
            List<String> comparNaturalCodes = comparFloatRegionDatum.getNaturalCodes();
            for (List<FieldData> comparFloatDatas : comparFloatDatass) {
                ArrayList<AbstractData> comparNaturalDatas = new ArrayList<AbstractData>();
                block3: for (String comparNaturalCode : comparNaturalCodes) {
                    for (FieldData comparFloatData : comparFloatDatas) {
                        if (!comparNaturalCode.equals(comparFloatData.getFieldCode())) continue;
                        comparNaturalDatas.add(comparFloatData.getData());
                        continue block3;
                    }
                }
                comparNaturalDatass.add(comparNaturalDatas);
            }
            for (int i = 0; i < comparNaturalDatass.size(); ++i) {
                NaturalAndData naturalAndData;
                List comparNaturalDatas = (List)comparNaturalDatass.get(i);
                StringBuilder naturalKey = new StringBuilder();
                for (AbstractData comparNaturalData : comparNaturalDatas) {
                    naturalKey.append(comparNaturalData.getAsString());
                }
                List<NaturalAndData> naturalAndDatas = comparNaturalAndDatasMap.get(naturalKey.toString());
                if (null == naturalAndDatas) {
                    naturalAndDatas = new ArrayList<NaturalAndData>();
                    naturalAndData = new NaturalAndData();
                    naturalAndData.setNaturalDatas(comparNaturalDatas);
                    naturalAndData.setFloatDatas(comparFloatDatass.get(i));
                    naturalAndDatas.add(naturalAndData);
                    comparNaturalAndDatasMap.put(naturalKey.toString(), naturalAndDatas);
                    continue;
                }
                naturalAndData = new NaturalAndData();
                naturalAndData.setNaturalDatas(comparNaturalDatas);
                naturalAndData.setFloatDatas(comparFloatDatass.get(i));
                naturalAndDatas.add(naturalAndData);
            }
            this.fillInVacantRows(initNaturalCodes, initNaturalAndDatasMap, comparNaturalAndDatasMap);
        }
        return comparNaturalAndDatasMaps;
    }

    private void fillInVacantRows(List<String> initNaturalCodes, LinkedHashMap<String, List<NaturalAndData>> initNaturalAndDatasMap, LinkedHashMap<String, List<NaturalAndData>> comparNaturalAndDatasMap) {
        for (String naturalKey : comparNaturalAndDatasMap.keySet()) {
            List<NaturalAndData> initNaturalAndDatas = initNaturalAndDatasMap.get(naturalKey);
            List<NaturalAndData> comparNaturalAndData = comparNaturalAndDatasMap.get(naturalKey);
            if (null == initNaturalAndDatas) {
                initNaturalAndDatas = new ArrayList<NaturalAndData>();
                for (int i = 0; i < comparNaturalAndData.size(); ++i) {
                    NaturalAndData naturalAndData = new NaturalAndData();
                    naturalAndData.setNaturalDatas(comparNaturalAndData.get(i).getNaturalDatas());
                    ArrayList<FieldData> addInitRowDatas = new ArrayList<FieldData>();
                    if (!initNaturalAndDatasMap.isEmpty()) {
                        String initNaturalKey;
                        Object initNaturalAndData = null;
                        Iterator<Object> iterator = initNaturalAndDatasMap.keySet().iterator();
                        while (iterator.hasNext() && null == (initNaturalAndData = initNaturalAndDatasMap.get(initNaturalKey = iterator.next()).get(0))) {
                        }
                        if (null != initNaturalAndData) {
                            for (FieldData floatData : ((NaturalAndData)initNaturalAndData).getFloatDatas()) {
                                FieldData fieldData = new FieldData();
                                fieldData.setFieldKey(floatData.getFieldKey());
                                fieldData.setFieldCode(floatData.getFieldCode());
                                fieldData.setFieldTitle(floatData.getFieldTitle());
                                fieldData.setData(floatData.getData());
                                addInitRowDatas.add(fieldData);
                            }
                        }
                        block4: for (FieldData addInitRowData : addInitRowDatas) {
                            if (!initNaturalCodes.contains(addInitRowData.getFieldCode())) {
                                addInitRowData.setData(null);
                                continue;
                            }
                            for (FieldData comparFloatData : comparNaturalAndData.get(i).getFloatDatas()) {
                                if (!comparFloatData.getFieldCode().equals(addInitRowData.getFieldCode())) continue;
                                addInitRowData.setData(comparFloatData.getData());
                                continue block4;
                            }
                        }
                    } else {
                        for (FieldData comparFloatData : comparNaturalAndData.get(i).getFloatDatas()) {
                            FieldData fieldData = new FieldData();
                            fieldData.setFieldKey(comparFloatData.getFieldKey());
                            fieldData.setFieldCode(comparFloatData.getFieldCode());
                            fieldData.setFieldTitle(comparFloatData.getFieldTitle());
                            fieldData.setData(comparFloatData.getData());
                            addInitRowDatas.add(fieldData);
                        }
                        for (FieldData addInitRowData : addInitRowDatas) {
                            if (initNaturalCodes.contains(addInitRowData.getFieldCode())) continue;
                            addInitRowData.setData(null);
                        }
                    }
                    naturalAndData.setFloatDatas(addInitRowDatas);
                    initNaturalAndDatas.add(naturalAndData);
                }
                initNaturalAndDatasMap.put(naturalKey, initNaturalAndDatas);
                continue;
            }
            if (null == initNaturalAndDatas || initNaturalAndDatas.size() >= comparNaturalAndData.size()) continue;
            int initNaturalAndDatasSize = initNaturalAndDatas.size();
            for (int i = 0; i < comparNaturalAndData.size() - initNaturalAndDatasSize; ++i) {
                NaturalAndData naturalAndData = new NaturalAndData();
                naturalAndData.setNaturalDatas(initNaturalAndDatas.get(0).getNaturalDatas());
                ArrayList<FieldData> addInitRowDatas = new ArrayList<FieldData>();
                NaturalAndData initNaturalAndData = initNaturalAndDatas.get(0);
                for (FieldData floatData : initNaturalAndData.getFloatDatas()) {
                    FieldData fieldData = new FieldData();
                    fieldData.setFieldKey(floatData.getFieldKey());
                    fieldData.setFieldCode(floatData.getFieldCode());
                    fieldData.setFieldTitle(floatData.getFieldTitle());
                    fieldData.setData(floatData.getData());
                    addInitRowDatas.add(fieldData);
                }
                block10: for (FieldData addInitRowData : addInitRowDatas) {
                    if (!initNaturalCodes.contains(addInitRowData.getFieldCode())) {
                        addInitRowData.setData(null);
                        continue;
                    }
                    for (FieldData comparFloatData : comparNaturalAndData.get(0).getFloatDatas()) {
                        if (!comparFloatData.getFieldCode().equals(addInitRowData.getFieldCode())) continue;
                        addInitRowData.setData(comparFloatData.getData());
                        continue block10;
                    }
                }
                naturalAndData.setFloatDatas(addInitRowDatas);
                initNaturalAndDatas.add(naturalAndData);
            }
        }
    }

    @NotNull
    private LinkedHashMap<String, List<NaturalAndData>> getStringListLinkedHashMap(List<String> initNaturalCodes, List<List<FieldData>> initFloatDatass) {
        LinkedHashMap<String, List<NaturalAndData>> initNaturalAndDatasMap = new LinkedHashMap<String, List<NaturalAndData>>();
        ArrayList initNaturalDatass = new ArrayList();
        for (List<FieldData> initFloatDatas : initFloatDatass) {
            ArrayList<AbstractData> initNaturalDatas = new ArrayList<AbstractData>();
            block1: for (String initNaturalCode : initNaturalCodes) {
                for (FieldData initFloatData : initFloatDatas) {
                    if (!initNaturalCode.equals(initFloatData.getFieldCode())) continue;
                    initNaturalDatas.add(initFloatData.getData());
                    continue block1;
                }
            }
            initNaturalDatass.add(initNaturalDatas);
        }
        for (int i = 0; i < initNaturalDatass.size(); ++i) {
            NaturalAndData naturalAndData;
            List initNaturalDatas = (List)initNaturalDatass.get(i);
            StringBuilder naturalKey = new StringBuilder();
            for (AbstractData initNaturalData : initNaturalDatas) {
                naturalKey.append(initNaturalData.getAsString());
            }
            List<NaturalAndData> naturalAndDatas = initNaturalAndDatasMap.get(naturalKey.toString());
            if (null == naturalAndDatas) {
                naturalAndDatas = new ArrayList<NaturalAndData>();
                naturalAndData = new NaturalAndData();
                naturalAndData.setNaturalDatas(initNaturalDatas);
                naturalAndData.setFloatDatas(initFloatDatass.get(i));
                naturalAndDatas.add(naturalAndData);
                initNaturalAndDatasMap.put(naturalKey.toString(), naturalAndDatas);
                continue;
            }
            naturalAndData = new NaturalAndData();
            naturalAndData.setNaturalDatas(initNaturalDatas);
            naturalAndData.setFloatDatas(initFloatDatass.get(i));
            naturalAndDatas.add(naturalAndData);
        }
        return initNaturalAndDatasMap;
    }

    /*
     * WARNING - void declaration
     */
    private void addInitFloatDatassNoRow(FloatRegionData initFloatRegionData, List<List<FloatRegionData>> comparFloatRegionDatas, List<String> initNaturalCodes, List<List<FieldData>> initFloatDatass) {
        block27: {
            block26: {
                if (null == initNaturalCodes || initFloatRegionData.isAllowDuplicateKey()) break block26;
                ArrayList<List<Object>> initNaturalDatass = new ArrayList<List<Object>>();
                for (List<FieldData> list : initFloatDatass) {
                    ArrayList<AbstractData> initNaturalDatas = new ArrayList<AbstractData>();
                    block1: for (String string : initNaturalCodes) {
                        for (FieldData initFloatData : list) {
                            if (!string.equals(initFloatData.getFieldCode())) continue;
                            initNaturalDatas.add(initFloatData.getData());
                            continue block1;
                        }
                    }
                    initNaturalDatass.add(initNaturalDatas);
                }
                for (List<Object> list : comparFloatRegionDatas) {
                    if (null == list || list.isEmpty()) continue;
                    FloatRegionData comparFloatRegionDatum = null;
                    for (FloatRegionData floatRegionData : list) {
                        if (!initFloatRegionData.getRegionKey().equals(floatRegionData.getRegionKey())) continue;
                        comparFloatRegionDatum = floatRegionData;
                        break;
                    }
                    if (null == comparFloatRegionDatum) continue;
                    ArrayList comparNaturalDatass = new ArrayList();
                    List<List<FieldData>> list2 = comparFloatRegionDatum.getFloatDatass();
                    List<String> comparNaturalCodes = comparFloatRegionDatum.getNaturalCodes();
                    for (List<FieldData> list3 : list2) {
                        ArrayList<AbstractData> comparNaturalDatas = new ArrayList<AbstractData>();
                        block6: for (String string : comparNaturalCodes) {
                            for (FieldData comparFloatData : list3) {
                                if (!string.equals(comparFloatData.getFieldCode())) continue;
                                comparNaturalDatas.add(comparFloatData.getData());
                                continue block6;
                            }
                        }
                        comparNaturalDatass.add(comparNaturalDatas);
                    }
                    for (List<FieldData> list4 : comparNaturalDatass) {
                        Object fieldData;
                        boolean haveCompar = false;
                        for (List list5 : initNaturalDatass) {
                            if (list4.size() != list5.size() || !list4.containsAll(list5) || !list5.containsAll(list4)) continue;
                            haveCompar = true;
                            break;
                        }
                        if (haveCompar) continue;
                        int index = comparNaturalDatass.indexOf(list4);
                        List<FieldData> list6 = list2.get(index);
                        ArrayList<Object> addInitRowDatas = new ArrayList<Object>();
                        if (!initFloatDatass.isEmpty()) {
                            for (FieldData fieldData2 : initFloatDatass.get(0)) {
                                fieldData = new FieldData();
                                ((FieldData)fieldData).setFieldKey(fieldData2.getFieldKey());
                                ((FieldData)fieldData).setFieldCode(fieldData2.getFieldCode());
                                ((FieldData)fieldData).setFieldTitle(fieldData2.getFieldTitle());
                                ((FieldData)fieldData).setData(fieldData2.getData());
                                addInitRowDatas.add(fieldData);
                            }
                            block11: for (FieldData fieldData3 : addInitRowDatas) {
                                if (!(initNaturalCodes.contains(fieldData3.getFieldCode()) || "SnapshotGatherFlag".equals(fieldData3.getFieldCode()) || "SnapshotGatherGroupKey".equals(fieldData3.getFieldCode()) || "isFilledRow".equals(fieldData3.getFieldCode()))) {
                                    fieldData3.setData(null);
                                    continue;
                                }
                                for (FieldData comparFloatData : list6) {
                                    if (!comparFloatData.getFieldCode().equals(fieldData3.getFieldCode())) continue;
                                    fieldData3.setData(comparFloatData.getData());
                                    continue block11;
                                }
                            }
                        } else {
                            for (FieldData fieldData4 : list6) {
                                fieldData = new FieldData();
                                ((FieldData)fieldData).setFieldKey(fieldData4.getFieldKey());
                                ((FieldData)fieldData).setFieldCode(fieldData4.getFieldCode());
                                ((FieldData)fieldData).setFieldTitle(fieldData4.getFieldTitle());
                                ((FieldData)fieldData).setData(fieldData4.getData());
                                addInitRowDatas.add(fieldData);
                            }
                            for (FieldData fieldData5 : addInitRowDatas) {
                                if (initNaturalCodes.contains(fieldData5.getFieldCode()) || "SnapshotGatherFlag".equals(fieldData5.getFieldCode()) || "SnapshotGatherGroupKey".equals(fieldData5.getFieldCode()) || "isFilledRow".equals(fieldData5.getFieldCode())) continue;
                                fieldData5.setData(null);
                            }
                        }
                        initFloatDatass.add(addInitRowDatas);
                        initNaturalDatass.add(list4);
                    }
                }
                break block27;
            }
            if (null != initNaturalCodes && initFloatRegionData.isAllowDuplicateKey()) break block27;
            for (List<FloatRegionData> comparFloatRegionData : comparFloatRegionDatas) {
                List<List<FieldData>> comparFloatDatass;
                void var7_12;
                if (null == comparFloatRegionData || comparFloatRegionData.isEmpty()) continue;
                Object var7_13 = null;
                for (FloatRegionData index : comparFloatRegionData) {
                    if (!initFloatRegionData.getRegionKey().equals(index.getRegionKey())) continue;
                    FloatRegionData floatRegionData = index;
                    break;
                }
                if (null == var7_12 || (comparFloatDatass = var7_12.getFloatDatass()).size() <= initFloatDatass.size()) continue;
                for (int i = initFloatDatass.size(); i < comparFloatDatass.size(); ++i) {
                    FieldData fieldData;
                    List<FieldData> list = comparFloatDatass.get(i);
                    ArrayList<FieldData> addInitRowDatas = new ArrayList<FieldData>();
                    if (!initFloatDatass.isEmpty()) {
                        for (FieldData fieldData6 : initFloatDatass.get(0)) {
                            fieldData = new FieldData();
                            fieldData.setFieldKey(fieldData6.getFieldKey());
                            fieldData.setFieldCode(fieldData6.getFieldCode());
                            fieldData.setFieldTitle(fieldData6.getFieldTitle());
                            fieldData.setData(fieldData6.getData());
                            addInitRowDatas.add(fieldData);
                        }
                    } else {
                        for (FieldData fieldData7 : list) {
                            fieldData = new FieldData();
                            fieldData.setFieldKey(fieldData7.getFieldKey());
                            fieldData.setFieldCode(fieldData7.getFieldCode());
                            fieldData.setFieldTitle(fieldData7.getFieldTitle());
                            fieldData.setData(fieldData7.getData());
                            addInitRowDatas.add(fieldData);
                        }
                    }
                    for (FieldData fieldData8 : addInitRowDatas) {
                        if ("SnapshotGatherFlag".equals(fieldData8.getFieldCode()) || "SnapshotGatherGroupKey".equals(fieldData8.getFieldCode()) || "isFilledRow".equals(fieldData8.getFieldCode())) continue;
                        fieldData8.setData(null);
                    }
                    initFloatDatass.add(addInitRowDatas);
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private int compareByPrimaryKey(FloatRegionData initFloatRegionData, List<List<FloatRegionData>> comparFloatRegionDatas, List<FieldData> initFloatDatas, List<AbstractData> naturalDatas, List<DifferenceDataItem> differenceDataItems, boolean isGather, String initGatherData) {
        int differenceCount = 0;
        ArrayList comparFloatRegionNaturalDatass = new ArrayList();
        ArrayList<List<List<FieldData>>> comparRegionFloatDatass = new ArrayList<List<List<FieldData>>>();
        ArrayList comparFloatRegionGatherDatass = new ArrayList();
        for (List<FloatRegionData> comparFloatRegionData : comparFloatRegionDatas) {
            FloatRegionData comparFloatRegionDatum = null;
            for (FloatRegionData index : comparFloatRegionData) {
                if (!initFloatRegionData.getRegionKey().equals(index.getRegionKey())) continue;
                comparFloatRegionDatum = index;
                break;
            }
            List<List<FieldData>> comparFloatDatass = null;
            comparFloatDatass = null != comparFloatRegionDatum ? comparFloatRegionDatum.getFloatDatass() : new ArrayList<List<FieldData>>();
            comparRegionFloatDatass.add(comparFloatDatass);
            if (isGather) {
                ArrayList<String> comparGatherDatas = new ArrayList<String>();
                for (List<FieldData> list : comparFloatDatass) {
                    StringBuilder comparGatherData = new StringBuilder();
                    for (Object comparFloatData : list) {
                        if ("SnapshotGatherFlag".equals(((FieldData)comparFloatData).getFieldCode())) {
                            comparGatherData.append(((FieldData)comparFloatData).getData().getAsString());
                            continue;
                        }
                        if (!"SnapshotGatherGroupKey".equals(((FieldData)comparFloatData).getFieldCode())) continue;
                        comparGatherData.append(((FieldData)comparFloatData).getData().getAsString());
                        break;
                    }
                    comparGatherDatas.add(comparGatherData.toString());
                }
                comparFloatRegionGatherDatass.add(comparGatherDatas);
                continue;
            }
            List<String> comparNaturalCodes = null;
            comparNaturalCodes = null != comparFloatRegionDatum ? comparFloatRegionDatum.getNaturalCodes() : new ArrayList<String>();
            ArrayList comparNaturalDatass = new ArrayList();
            for (List<FieldData> comparFloatDatas3 : comparFloatDatass) {
                Object comparFloatData;
                ArrayList<AbstractData> comparNaturalDatas = new ArrayList<AbstractData>();
                comparFloatData = comparNaturalCodes.iterator();
                block5: while (comparFloatData.hasNext()) {
                    String comparNaturalCode = (String)comparFloatData.next();
                    for (FieldData comparFloatData2 : comparFloatDatas3) {
                        if (!comparNaturalCode.equals(comparFloatData2.getFieldCode())) continue;
                        comparNaturalDatas.add(comparFloatData2.getData());
                        continue block5;
                    }
                }
                comparNaturalDatass.add(comparNaturalDatas);
            }
            comparFloatRegionNaturalDatass.add(comparNaturalDatass);
        }
        List<String> initNaturalCodes = initFloatRegionData.getNaturalCodes();
        for (FieldData initFloatData : initFloatDatas) {
            List comparFloatDatas;
            FieldData compareFloatData;
            List comparFloatDatass;
            boolean bl;
            if (initNaturalCodes.contains(initFloatData.getFieldCode()) || "BIZKEYORDER".equals(initFloatData.getFieldCode()) || "SnapshotGatherFlag".equals(initFloatData.getFieldCode()) || "SnapshotGatherGroupKey".equals(initFloatData.getFieldCode()) || "isFilledRow".equals(initFloatData.getFieldCode()) || StringUtils.isEmpty((String)initFloatData.getFieldCode()) || StringUtils.isEmpty((String)initFloatData.getFieldTitle())) continue;
            boolean haveDifference = false;
            DifferenceDataItem differenceDataItem = new DifferenceDataItem();
            differenceDataItem.setFieldKey(initFloatData.getFieldKey());
            differenceDataItem.setFieldCode(initFloatData.getFieldCode());
            differenceDataItem.setFieldTitle(initFloatData.getFieldTitle());
            ArrayList<DifferenceData> difDatas = new ArrayList<DifferenceData>();
            if (isGather) {
                void var18_24;
                bl = false;
                while (var18_24 < comparFloatRegionGatherDatass.size()) {
                    List comparGatherDatas = (List)comparFloatRegionGatherDatass.get((int)var18_24);
                    comparFloatDatass = (List)comparRegionFloatDatass.get((int)var18_24);
                    boolean sameGatherDatas = false;
                    for (int j = 0; j < comparGatherDatas.size(); ++j) {
                        String comparGatherData = (String)comparGatherDatas.get(j);
                        if (!initGatherData.equals(comparGatherData)) continue;
                        sameGatherDatas = true;
                        compareFloatData = null;
                        comparFloatDatas = (List)comparFloatDatass.get(j);
                        for (FieldData index : comparFloatDatas) {
                            if (!initFloatData.getFieldKey().equals(index.getFieldKey())) continue;
                            compareFloatData = index;
                            break;
                        }
                        if (null == compareFloatData) break;
                        this.structureFifferenceData(initFloatData, difDatas, compareFloatData);
                        if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) break;
                        haveDifference = true;
                        break;
                    }
                    if (!sameGatherDatas) {
                        this.structureFifferenceData(initFloatData, difDatas);
                        if (0.0 != ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) {
                            haveDifference = true;
                        }
                    }
                    ++var18_24;
                }
                if (comparFloatRegionGatherDatass.isEmpty()) {
                    this.structureFifferenceData(initFloatData, difDatas);
                    if (0.0 != ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) {
                        haveDifference = true;
                    }
                }
            } else {
                void var18_25;
                bl = false;
                while (var18_25 < comparFloatRegionNaturalDatass.size()) {
                    List comparNaturalDatass = (List)comparFloatRegionNaturalDatass.get((int)var18_25);
                    comparFloatDatass = (List)comparRegionFloatDatass.get((int)var18_25);
                    boolean sameNatureDatas = false;
                    for (int j = 0; j < comparNaturalDatass.size(); ++j) {
                        List comparNaturalDatas = (List)comparNaturalDatass.get(j);
                        if (naturalDatas.size() != comparNaturalDatas.size() || !naturalDatas.containsAll(comparNaturalDatas) || !comparNaturalDatas.containsAll(naturalDatas)) continue;
                        sameNatureDatas = true;
                        compareFloatData = null;
                        comparFloatDatas = (List)comparFloatDatass.get(j);
                        for (FieldData index : comparFloatDatas) {
                            if (!initFloatData.getFieldKey().equals(index.getFieldKey())) continue;
                            compareFloatData = index;
                            break;
                        }
                        if (null == compareFloatData) break;
                        this.structureFifferenceData(initFloatData, difDatas, compareFloatData);
                        if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) break;
                        haveDifference = true;
                        break;
                    }
                    if (!sameNatureDatas) {
                        this.structureFifferenceData(initFloatData, difDatas);
                        if (0.0 != ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) {
                            haveDifference = true;
                        }
                    }
                    ++var18_25;
                }
                if (comparFloatRegionNaturalDatass.isEmpty()) {
                    this.structureFifferenceData(initFloatData, difDatas);
                    if (0.0 != ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) {
                        haveDifference = true;
                    }
                }
            }
            if (haveDifference) {
                ++differenceCount;
            }
            differenceDataItem.setDifferenceDatas(difDatas);
            differenceDataItems.add(differenceDataItem);
        }
        return differenceCount;
    }

    private int compareInOrder(FloatRegionData initFloatRegionData, List<List<FloatRegionData>> comparFloatRegionDatas, List<List<FieldData>> initFloatDatass, List<FieldData> initFloatDatas, List<DifferenceDataItem> differenceDataItems) {
        int differenceCount = 0;
        ArrayList<FloatRegionData> comparFloatRegionDatums = new ArrayList<FloatRegionData>();
        for (List<FloatRegionData> comparFloatRegionData : comparFloatRegionDatas) {
            boolean flag = false;
            for (FloatRegionData index : comparFloatRegionData) {
                if (!initFloatRegionData.getRegionKey().equals(index.getRegionKey())) continue;
                flag = true;
                comparFloatRegionDatums.add(index);
                break;
            }
            if (flag) continue;
            comparFloatRegionDatums.add(null);
        }
        List<String> initNaturalCodes = initFloatRegionData.getNaturalCodes();
        for (FieldData initFloatData : initFloatDatas) {
            if (null != initNaturalCodes && initNaturalCodes.contains(initFloatData.getFieldCode()) || "BIZKEYORDER".equals(initFloatData.getFieldCode()) || "SnapshotGatherFlag".equals(initFloatData.getFieldCode()) || "SnapshotGatherGroupKey".equals(initFloatData.getFieldCode()) || "isFilledRow".equals(initFloatData.getFieldCode()) || StringUtils.isEmpty((String)initFloatData.getFieldCode()) || StringUtils.isEmpty((String)initFloatData.getFieldTitle())) continue;
            boolean haveDifference = false;
            DifferenceDataItem differenceDataItem = new DifferenceDataItem();
            differenceDataItem.setFieldKey(initFloatData.getFieldKey());
            differenceDataItem.setFieldCode(initFloatData.getFieldCode());
            differenceDataItem.setFieldTitle(initFloatData.getFieldTitle());
            ArrayList<DifferenceData> difDatas = new ArrayList<DifferenceData>();
            for (FloatRegionData comparFloatRegionDatum : comparFloatRegionDatums) {
                if (null == comparFloatRegionDatum) {
                    this.structureFifferenceData(initFloatData, difDatas);
                    if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) continue;
                    haveDifference = true;
                    continue;
                }
                int rowIndex = initFloatDatass.indexOf(initFloatDatas);
                List<List<FieldData>> comparFloatDatass = comparFloatRegionDatum.getFloatDatass();
                if (comparFloatDatass.size() > rowIndex) {
                    FieldData compareFloatData = null;
                    List<FieldData> comparFloatDatas = comparFloatDatass.get(rowIndex);
                    for (FieldData index : comparFloatDatas) {
                        if (!initFloatData.getFieldKey().equals(index.getFieldKey())) continue;
                        compareFloatData = index;
                        break;
                    }
                    if (null == compareFloatData) continue;
                    this.structureFifferenceData(initFloatData, difDatas, compareFloatData);
                    if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) continue;
                    haveDifference = true;
                    continue;
                }
                this.structureFifferenceData(initFloatData, difDatas);
                if (0.0 == ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) continue;
                haveDifference = true;
            }
            if (comparFloatRegionDatums.isEmpty()) {
                this.structureFifferenceData(initFloatData, difDatas);
                if (0.0 != ((DifferenceData)difDatas.get(difDatas.size() - 1)).getScale()) {
                    haveDifference = true;
                }
            }
            if (haveDifference) {
                ++differenceCount;
            }
            differenceDataItem.setDifferenceDatas(difDatas);
            differenceDataItems.add(differenceDataItem);
        }
        return differenceCount;
    }

    private void structureFifferenceData(FieldData initData, List<DifferenceData> difDatas) {
        DifferenceData difData = null;
        AbstractData initAbstractData = initData.getData();
        if (null != initAbstractData && !initAbstractData.isNull) {
            if (initAbstractData instanceof IntData) {
                IntData initIntData = (IntData)initAbstractData;
                difData = new DifferenceData();
                difData.setInitialValue(Integer.toString(initIntData.getAsInt()));
                difData.setDifference(initIntData.getAsInt());
                difData.setScale(-1.0);
            } else if (initAbstractData instanceof FloatData) {
                FloatData initDoubleData = (FloatData)initAbstractData;
                difData = new DifferenceData();
                difData.setInitialValue(String.valueOf(initDoubleData.getAsFloat()));
                difData.setDifference(initDoubleData.getAsFloat());
                difData.setScale(-1.0);
            } else if (initAbstractData instanceof DateData) {
                DateData initDateData = (DateData)initAbstractData;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date initDate = new Date(initDateData.getAsDate());
                difData = new DifferenceData();
                difData.setInitialValue(format.format(initDate));
                difData.setScale(-1.0);
            } else if (initAbstractData instanceof DateTimeData) {
                DateTimeData initDateTimeData = (DateTimeData)initAbstractData;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date initDate = new Date(initDateTimeData.getAsDateTime());
                difData = new DifferenceData();
                difData.setInitialValue(format.format(initDate));
                difData.setScale(-1.0);
            } else if (initAbstractData instanceof StringData) {
                String initStringDataStr;
                StringData initStringData = (StringData)initAbstractData;
                difData = new DifferenceData();
                if (StringUtils.isNotEmpty((String)initData.getMaskCode()) && StringUtils.isNotEmpty((String)initAbstractData.getAsString())) {
                    try {
                        initStringDataStr = this.encryptor.desensitize(initData.getMaskCode(), initStringData.getAsString());
                    }
                    catch (Exception e) {
                        logger.warn("\u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{} | \u9519\u8bef:{}", initData.getMaskCode(), initStringData.getAsString(), e.getMessage());
                        initStringDataStr = initStringData.getAsString();
                    }
                } else {
                    initStringDataStr = initStringData.getAsString();
                }
                difData.setInitialValue(initStringDataStr);
                difData.setScale(-1.0);
            } else if (initAbstractData instanceof CurrencyData) {
                CurrencyData initCurrencyData = (CurrencyData)initAbstractData;
                BigDecimal initBigDecimal = initCurrencyData.getAsCurrency();
                difData = new DifferenceData();
                difData.setInitialValue(String.valueOf(initBigDecimal));
                difData.setDifference(initBigDecimal.doubleValue());
                difData.setScale(-1.0);
            } else if (initAbstractData instanceof BoolData) {
                BoolData initBoolData = (BoolData)initAbstractData;
                difData = new DifferenceData();
                difData.setInitialValue(initBoolData.getAsString());
                difData.setScale(-1.0);
            }
        } else {
            difData = new DifferenceData();
            difData.setScale(0.0);
        }
        difDatas.add(difData);
    }

    private void structureFifferenceData(FieldData initData, List<DifferenceData> difDatas, FieldData compareData) {
        DifferenceData difData = null;
        AbstractData initAbstractData = initData.getData();
        AbstractData compareAbstractData = compareData.getData();
        if (null == initAbstractData || initAbstractData.isNull) {
            this.structureFifferenceData(difDatas, compareData);
        } else if (null == compareAbstractData || compareAbstractData.isNull) {
            this.structureFifferenceData(initData, difDatas);
        } else if (null != initAbstractData && null != compareAbstractData) {
            if (initAbstractData instanceof IntData) {
                IntData initIntData = (IntData)initAbstractData;
                IntData compareIntData = (IntData)compareAbstractData;
                double scale = (double)initIntData.getAsInt() == (double)compareIntData.getAsInt() ? 0.0 : (0.0 == (double)compareIntData.getAsInt() ? -1.0 : Math.abs(((double)initIntData.getAsInt() - (double)compareIntData.getAsInt()) / (double)compareIntData.getAsInt()));
                difData = new DifferenceData(Integer.toString(initIntData.getAsInt()), Integer.toString(compareIntData.getAsInt()), (double)initIntData.getAsInt() - (double)compareIntData.getAsInt(), scale);
            } else if (initAbstractData instanceof FloatData) {
                FloatData initFloatData = (FloatData)initAbstractData;
                FloatData compareFloatData = (FloatData)compareAbstractData;
                double scale = initFloatData.getAsFloat() == compareFloatData.getAsFloat() ? 0.0 : (0.0 == compareFloatData.getAsFloat() ? -1.0 : Math.abs((initFloatData.getAsFloat() - compareFloatData.getAsFloat()) / compareFloatData.getAsFloat()));
                difData = new DifferenceData(String.valueOf(initFloatData.getAsFloat()), String.valueOf(compareFloatData.getAsFloat()), initFloatData.getAsFloat() - compareFloatData.getAsFloat(), scale);
            } else if (initAbstractData instanceof DateData) {
                DateData initDateData = (DateData)initAbstractData;
                DateData compareDateData = (DateData)compareAbstractData;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date initDate = new Date(initDateData.getAsDate());
                Date compareDate = new Date(compareDateData.getAsDate());
                difData = new DifferenceData();
                difData.setInitialValue(format.format(initDate));
                difData.setCompareValue(format.format(compareDate));
                if (format.format(initDate).equals(format.format(compareDate))) {
                    difData.setScale(0.0);
                } else {
                    difData.setScale(-1.0);
                }
            } else if (initAbstractData instanceof DateTimeData) {
                DateTimeData initDateTimeData = (DateTimeData)initAbstractData;
                DateTimeData compareDateTimeData = (DateTimeData)compareAbstractData;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date initDate = new Date(initDateTimeData.getAsDateTime());
                Date compareDate = new Date(compareDateTimeData.getAsDateTime());
                difData = new DifferenceData();
                difData.setInitialValue(format.format(initDate));
                difData.setCompareValue(format.format(compareDate));
                if (format.format(initDate).equals(format.format(compareDate))) {
                    difData.setScale(0.0);
                } else {
                    difData.setScale(-1.0);
                }
            } else if (initAbstractData instanceof StringData) {
                String compareStringDataStr;
                String initStringDataStr;
                StringData initStringData = (StringData)initAbstractData;
                StringData compareStringData = (StringData)compareAbstractData;
                difData = new DifferenceData();
                if (StringUtils.isNotEmpty((String)initData.getMaskCode()) && StringUtils.isNotEmpty((String)initAbstractData.getAsString())) {
                    try {
                        initStringDataStr = this.encryptor.desensitize(initData.getMaskCode(), initStringData.getAsString());
                    }
                    catch (Exception e) {
                        logger.warn("\u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{} | \u9519\u8bef:{}", initData.getMaskCode(), initStringData.getAsString(), e.getMessage());
                        initStringDataStr = initStringData.getAsString();
                    }
                } else {
                    initStringDataStr = initStringData.getAsString();
                }
                if (StringUtils.isNotEmpty((String)compareData.getMaskCode()) && StringUtils.isNotEmpty((String)compareStringData.getAsString())) {
                    try {
                        compareStringDataStr = this.encryptor.desensitize(compareData.getMaskCode(), compareStringData.getAsString());
                    }
                    catch (Exception e) {
                        logger.warn("\u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{} | \u9519\u8bef:{}", compareData.getMaskCode(), compareStringData.getAsString(), e.getMessage());
                        compareStringDataStr = compareStringData.getAsString();
                    }
                } else {
                    compareStringDataStr = compareStringData.getAsString();
                }
                difData.setInitialValue(initStringDataStr);
                difData.setCompareValue(compareStringDataStr);
                if (initStringData.getAsString().equals(compareStringData.getAsString())) {
                    difData.setScale(0.0);
                } else {
                    difData.setScale(-1.0);
                }
            } else if (initAbstractData instanceof CurrencyData) {
                BigDecimal compareBigDecimal;
                CurrencyData initCurrencyData = (CurrencyData)initAbstractData;
                CurrencyData compareCurrencyData = (CurrencyData)compareAbstractData;
                BigDecimal initBigDecimal = initCurrencyData.getAsCurrency();
                double differenceValue = initBigDecimal.subtract(compareBigDecimal = compareCurrencyData.getAsCurrency()).doubleValue();
                double scale = 0.0 == differenceValue ? 0.0 : (0.0 == compareBigDecimal.doubleValue() ? -1.0 : Math.abs(differenceValue / compareBigDecimal.doubleValue()));
                difData = new DifferenceData(String.valueOf(initBigDecimal), String.valueOf(compareBigDecimal), differenceValue, scale);
            } else if (initAbstractData instanceof BoolData) {
                BoolData initBoolData = (BoolData)initAbstractData;
                BoolData compareBoolData = (BoolData)compareAbstractData;
                difData = new DifferenceData();
                difData.setInitialValue(initBoolData.getAsString());
                difData.setCompareValue(compareBoolData.getAsString());
                if (initBoolData.getAsString().equals(compareBoolData.getAsString())) {
                    difData.setScale(0.0);
                } else {
                    difData.setScale(-1.0);
                }
            }
            difDatas.add(difData);
        }
    }

    private void structureFifferenceData(List<DifferenceData> difDatas, FieldData compareData) {
        DifferenceData difData = null;
        AbstractData compareAbstractData = compareData.getData();
        if (null != compareAbstractData && !compareAbstractData.isNull) {
            if (compareAbstractData instanceof IntData) {
                IntData compareIntData = (IntData)compareAbstractData;
                difData = new DifferenceData();
                difData.setCompareValue(Integer.toString(compareIntData.getAsInt()));
                difData.setDifference(0.0 - (double)compareIntData.getAsInt());
                difData.setScale(-1.0);
            } else if (compareAbstractData instanceof FloatData) {
                FloatData compareFloatData = (FloatData)compareAbstractData;
                difData = new DifferenceData();
                difData.setCompareValue(String.valueOf(compareFloatData.getAsFloat()));
                difData.setDifference(0.0 - compareFloatData.getAsFloat());
                difData.setScale(-1.0);
            } else if (compareAbstractData instanceof DateData) {
                DateData compareDateData = (DateData)compareAbstractData;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date compareDate = new Date(compareDateData.getAsDate());
                difData = new DifferenceData();
                difData.setCompareValue(format.format(compareDate));
                difData.setScale(-1.0);
            } else if (compareAbstractData instanceof DateTimeData) {
                DateTimeData compareDateTimeData = (DateTimeData)compareAbstractData;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date compareDate = new Date(compareDateTimeData.getAsDateTime());
                difData = new DifferenceData();
                difData.setCompareValue(format.format(compareDate));
                difData.setScale(-1.0);
            } else if (compareAbstractData instanceof StringData) {
                String compareStringDataStr;
                StringData compareStringData = (StringData)compareAbstractData;
                difData = new DifferenceData();
                if (StringUtils.isNotEmpty((String)compareData.getMaskCode()) && StringUtils.isNotEmpty((String)compareStringData.getAsString())) {
                    try {
                        compareStringDataStr = this.encryptor.desensitize(compareData.getMaskCode(), compareStringData.getAsString());
                    }
                    catch (Exception e) {
                        logger.warn("\u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{} | \u9519\u8bef:{}", compareData.getMaskCode(), compareStringData.getAsString(), e.getMessage());
                        compareStringDataStr = compareStringData.getAsString();
                    }
                } else {
                    compareStringDataStr = compareStringData.getAsString();
                }
                difData.setCompareValue(compareStringDataStr);
                difData.setScale(-1.0);
            } else if (compareAbstractData instanceof CurrencyData) {
                CurrencyData compareCurrencyData = (CurrencyData)compareAbstractData;
                BigDecimal compareBigDecimal = compareCurrencyData.getAsCurrency();
                difData = new DifferenceData();
                difData.setCompareValue(String.valueOf(compareBigDecimal));
                difData.setDifference(0.0 - compareBigDecimal.doubleValue());
                difData.setScale(-1.0);
            } else if (compareAbstractData instanceof BoolData) {
                BoolData compareBoolData = (BoolData)compareAbstractData;
                difData = new DifferenceData();
                difData.setCompareValue(compareBoolData.getAsString());
                difData.setScale(-1.0);
            }
        } else {
            difData = new DifferenceData();
            difData.setScale(0.0);
        }
        difDatas.add(difData);
    }

    @Override
    public void reversion(String formSchemeKey, DimensionCombination currentDim, DataSource reversionDataSource, List<String> formKeys) {
        for (String formKey : formKeys) {
            List<FloatRegionData> floatRegionDatas;
            DataInfo data = reversionDataSource.getData(formKey);
            if (null == data) continue;
            FixRegionData fixData = data.getFixData();
            if (null != fixData) {
                QueryEnvironment queryEnvironment = new QueryEnvironment();
                queryEnvironment.setFormSchemeKey(formSchemeKey);
                queryEnvironment.setFormKey(formKey);
                queryEnvironment.setRegionKey(fixData.getRegionKey());
                HashMap<String, AbstractData> fieldAndData = new HashMap<String, AbstractData>();
                List<FieldData> fixDatas = fixData.getFixDatas();
                for (FieldData fieldData : fixDatas) {
                    if ("SnapshotGatherFlag".equals(fieldData.getFieldCode()) || "SnapshotGatherGroupKey".equals(fieldData.getFieldCode()) || "isFilledRow".equals(fieldData.getFieldCode()) || StringUtils.isEmpty((String)fieldData.getFieldCode()) || StringUtils.isEmpty((String)fieldData.getFieldTitle())) continue;
                    fieldAndData.put(fieldData.getFieldKey(), fieldData.getData());
                }
                if (!fieldAndData.isEmpty()) {
                    try {
                        List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines(fieldAndData.keySet());
                        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
                        for (FieldDefine fieldDefine : fieldDefines) {
                            dataQuery.addColumn(fieldDefine);
                        }
                        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
                        executorContext.setEnv((IFmlExecEnvironment)environment);
                        IDataAssist iDataAssist = this.dataAccessProvider.newDataAssist(executorContext);
                        DimensionValueSet masterKeys = currentDim.toDimensionValueSet();
                        dataQuery.setMasterKeys(masterKeys);
                        IDataTable dataTable = dataQuery.executeQuery(executorContext);
                        for (int i = 0; i < dataTable.getTotalCount(); ++i) {
                            IDataRow iDataRow = dataTable.getItem(i);
                            for (FieldDefine fieldDefine : fieldDefines) {
                                String dimensionName = iDataAssist.getDimensionName(fieldDefine);
                                if (null != dimensionName && StringUtils.isNotEmpty((String)dimensionName)) {
                                    Object value = masterKeys.getValue(dimensionName);
                                    if (null != value) continue;
                                    iDataRow.setValue(fieldDefine, fieldAndData.get(fieldDefine.getKey()));
                                    continue;
                                }
                                iDataRow.setValue(fieldDefine, fieldAndData.get(fieldDefine.getKey()));
                            }
                        }
                        dataTable.commitChanges(true);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                }
            }
            if (null == (floatRegionDatas = data.getFloatDatas()) || floatRegionDatas.isEmpty()) continue;
            for (FloatRegionData floatRegionData : floatRegionDatas) {
                IDataUpdator iDataUpdator;
                String filterCondition;
                DataRegionDefine dataRegionDefine;
                DimensionValueSet masterKeys;
                ReportFmlExecEnvironment environment;
                IDataQuery dataQuery;
                String ownerTableKey;
                QueryEnvironment queryEnvironment = new QueryEnvironment();
                queryEnvironment.setFormSchemeKey(formSchemeKey);
                queryEnvironment.setFormKey(formKey);
                queryEnvironment.setRegionKey(floatRegionData.getRegionKey());
                ArrayList<Object> fieldAndDataList = new ArrayList<Object>();
                List<List<FieldData>> floatDatass = floatRegionData.getFloatDatass();
                for (List<FieldData> floatDatasRow : floatDatass) {
                    HashMap fieldAndData = new HashMap();
                    boolean flag = false;
                    for (FieldData fieldData : floatDatasRow) {
                        String gatherRow;
                        if ("SnapshotGatherFlag".equals(fieldData.getFieldCode()) && !"-1".equals(gatherRow = ((StringData)fieldData.getData()).getAsString())) {
                            flag = true;
                            break;
                        }
                        if ("SnapshotGatherFlag".equals(fieldData.getFieldCode()) || "SnapshotGatherGroupKey".equals(fieldData.getFieldCode()) || "isFilledRow".equals(fieldData.getFieldCode()) || StringUtils.isEmpty((String)fieldData.getFieldCode()) || StringUtils.isEmpty((String)fieldData.getFieldTitle())) continue;
                        if (StringUtils.isEmpty((String)fieldData.getFieldKey()) && "BIZKEYORDER".equals(fieldData.getFieldCode())) {
                            fieldAndData.put("BIZKEYORDER", fieldData.getData());
                            continue;
                        }
                        fieldAndData.put(fieldData.getFieldKey(), fieldData.getData());
                    }
                    if (flag) continue;
                    fieldAndDataList.add(fieldAndData);
                }
                if (!fieldAndDataList.isEmpty()) {
                    try {
                        Object fieldKey2;
                        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
                        ownerTableKey = "";
                        for (Object fieldKey2 : ((Map)fieldAndDataList.get(0)).keySet()) {
                            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine((String)fieldKey2);
                            if (null == fieldDefine) continue;
                            ownerTableKey = fieldDefine.getOwnerTableKey();
                            break;
                        }
                        List allFields = this.dataDefinitionRuntimeController.getAllFieldsInTable(ownerTableKey);
                        fieldKey2 = allFields.iterator();
                        while (fieldKey2.hasNext()) {
                            FieldDefine field = (FieldDefine)fieldKey2.next();
                            if (!"FLOATORDER".equals(field.getCode())) continue;
                            fieldDefines.add(field);
                            break;
                        }
                        fieldDefines.addAll(this.dataDefinitionRuntimeController.queryFieldDefines(((Map)fieldAndDataList.get(0)).keySet()));
                        dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
                        for (FieldDefine fieldDefine : fieldDefines) {
                            dataQuery.addColumn(fieldDefine);
                        }
                        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                        environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
                        executorContext.setEnv((IFmlExecEnvironment)environment);
                        masterKeys = currentDim.toDimensionValueSet();
                        dataQuery.setMasterKeys(masterKeys);
                        dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(floatRegionData.getRegionKey());
                        filterCondition = dataRegionDefine.getFilterCondition();
                        if (StringUtils.isNotEmpty((String)filterCondition)) {
                            dataQuery.setRowFilter(filterCondition);
                        }
                        iDataUpdator = dataQuery.openForUpdate(executorContext, true);
                        for (Map map : fieldAndDataList) {
                            IDataRow iDataRow = iDataUpdator.addInsertedRow();
                            for (FieldDefine fieldDefine : fieldDefines) {
                                if ("FLOATORDER".equals(fieldDefine.getCode())) {
                                    iDataRow.setValue(fieldDefine, (Object)((fieldAndDataList.indexOf(map) + 1) * 100));
                                    continue;
                                }
                                iDataRow.setValue(fieldDefine, map.get(fieldDefine.getKey()));
                            }
                        }
                        iDataUpdator.commitChanges();
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    continue;
                }
                try {
                    List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(floatRegionData.getRegionKey());
                    ownerTableKey = "";
                    for (String fieldKey2 : fieldKeys) {
                        FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey2);
                        if (null == fieldDefine) continue;
                        ownerTableKey = fieldDefine.getOwnerTableKey();
                        break;
                    }
                    List fieldDefines = this.dataDefinitionRuntimeController.getAllFieldsInTable(ownerTableKey);
                    dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
                    for (FieldDefine fieldDefine : fieldDefines) {
                        dataQuery.addColumn(fieldDefine);
                    }
                    ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                    environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
                    executorContext.setEnv((IFmlExecEnvironment)environment);
                    masterKeys = currentDim.toDimensionValueSet();
                    dataQuery.setMasterKeys(masterKeys);
                    dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(floatRegionData.getRegionKey());
                    filterCondition = dataRegionDefine.getFilterCondition();
                    if (StringUtils.isNotEmpty((String)filterCondition)) {
                        dataQuery.setRowFilter(filterCondition);
                    }
                    iDataUpdator = dataQuery.openForUpdate(executorContext, true);
                    iDataUpdator.commitChanges();
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }
}

