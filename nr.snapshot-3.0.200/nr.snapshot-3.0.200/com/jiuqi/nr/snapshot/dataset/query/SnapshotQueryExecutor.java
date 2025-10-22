/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
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
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 */
package com.jiuqi.nr.snapshot.dataset.query;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
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
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.snapshot.dataset.model.SnapshotDSModel;
import com.jiuqi.nr.snapshot.dataset.model.SnapshotDsModelDefine;
import com.jiuqi.nr.snapshot.input.QueryPeriodDataSourceContext;
import com.jiuqi.nr.snapshot.input.QuerySnapshotDataSourceContext;
import com.jiuqi.nr.snapshot.message.DataInfo;
import com.jiuqi.nr.snapshot.message.DataRange;
import com.jiuqi.nr.snapshot.message.DataRegionAndFields;
import com.jiuqi.nr.snapshot.message.DataRegionRange;
import com.jiuqi.nr.snapshot.message.FieldData;
import com.jiuqi.nr.snapshot.message.FixRegionData;
import com.jiuqi.nr.snapshot.message.FloatRegionData;
import com.jiuqi.nr.snapshot.message.Snapshot;
import com.jiuqi.nr.snapshot.output.SnapshotInfo;
import com.jiuqi.nr.snapshot.service.DataSource;
import com.jiuqi.nr.snapshot.service.DataSourceBuilder;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnapshotQueryExecutor {
    @Autowired
    private DataSourceBuilder dataSourceBuilder;
    @Autowired
    private SnapshotService snapshotService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    public void buildDataSet(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet, IDSContext idsContext, SnapshotDSModel snapshotDSModel) throws Exception {
        SnapshotDsModelDefine snapshotDsModelDefine = snapshotDSModel.getSnapshotDsModelDefine();
        FormDefine formDefine = this.runTimeViewController.queryFormById(snapshotDsModelDefine.getFormKey());
        String snapshotDWName = idsContext.getEnhancedParameterEnv().getParameterModelByName("P_MD_ORG").getName();
        List dw = idsContext.getEnhancedParameterEnv().getValueAsList(snapshotDWName);
        String snapshotPeriodName = idsContext.getEnhancedParameterEnv().getParameterModelByName("P_DATATIME").getName();
        List period = idsContext.getEnhancedParameterEnv().getValueAsList(snapshotPeriodName);
        String snapshotKeyName = idsContext.getEnhancedParameterEnv().getParameterModelByName("P_SNAPSHOT").getName();
        ArrayList<String> snapshotKeys = idsContext.getEnhancedParameterEnv().getValueAsList(snapshotKeyName);
        if (null == snapshotKeys) {
            snapshotKeys = new ArrayList<String>();
        }
        snapshotKeys.add(0, "CUR_PERIOD");
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        builder.setValue(snapshotDSModel.getDWDimensionName(), dw.get(0));
        builder.setValue(snapshotDSModel.getDataTimeDimensionName(), (Object)TimeDimUtils.timeKeyToPeriod((String)((String)period.get(0)), (PeriodType)snapshotDSModel.getPeriodType()));
        DimensionCombination combination = builder.getCombination();
        List<String> allFieldKeys = snapshotDSModel.getAllFieldKeys();
        List<SnapshotInfo> snapshotInfos = this.snapshotService.querySnapshot(combination, snapshotDsModelDefine.getTaskId());
        DataRange dataRange = new DataRange();
        DataRegionRange dataRegionRange = new DataRegionRange();
        dataRegionRange.setFormKey(snapshotDsModelDefine.getFormKey());
        ArrayList<DataRegionAndFields> dataRegionAndFieldsList = new ArrayList<DataRegionAndFields>();
        DataRegionAndFields dataRegionAndFields = new DataRegionAndFields();
        dataRegionAndFields.setDataRegionKey(snapshotDSModel.getDataRegionKey());
        dataRegionAndFieldsList.add(dataRegionAndFields);
        dataRegionRange.setDataRegionAndFieldsList(dataRegionAndFieldsList);
        ArrayList<DataRegionRange> formAndFields = new ArrayList<DataRegionRange>();
        formAndFields.add(dataRegionRange);
        dataRange.setFormAndFields(formAndFields);
        ExecutorContext excuteContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(excuteContext);
        for (Object e : snapshotKeys) {
            Object currenPeriod;
            DimensionValueSet dimensionValueSet;
            DataSource dataSource = null;
            String snapshotId = "";
            String snapshotTitle = "";
            String snapshotKeyStr = (String)e;
            if (snapshotKeyStr.equals("CUR_PERIOD")) {
                snapshotId = "CUR_PERIOD";
                snapshotTitle = "\u5f53\u524d\u671f\u6570";
                QueryPeriodDataSourceContext queryContext = new QueryPeriodDataSourceContext(combination, formDefine.getFormScheme(), dataRange, snapshotTitle);
                dataSource = this.dataSourceBuilder.queryPeriodDataSource(queryContext);
                if (null == dataSource) continue;
                this.buildMemory(memoryDataSet, snapshotDSModel, snapshotDsModelDefine, combination, allFieldKeys, dataSource, snapshotId, snapshotTitle);
                continue;
            }
            if (snapshotKeyStr.equals("LAST_PERIOD")) {
                snapshotId = "LAST_PERIOD";
                snapshotTitle = "\u4e0a\u671f\u6570";
                dimensionValueSet = combination.toDimensionValueSet();
                DimensionValueSet lastPeriodDimensionValueSet = new DimensionValueSet(dimensionValueSet);
                currenPeriod = dimensionValueSet.getValue("DATATIME");
                String lastPeriod = PeriodUtils.priorPeriod((String)((String)currenPeriod));
                lastPeriodDimensionValueSet.setValue("DATATIME", (Object)lastPeriod);
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(lastPeriodDimensionValueSet);
                DimensionCombination lastPeriodCombination = dimensionCombinationBuilder.getCombination();
                QueryPeriodDataSourceContext queryContext = new QueryPeriodDataSourceContext(lastPeriodCombination, formDefine.getFormScheme(), dataRange, snapshotTitle);
                dataSource = this.dataSourceBuilder.queryPeriodDataSource(queryContext);
                if (null == dataSource) continue;
                this.buildMemory(memoryDataSet, snapshotDSModel, snapshotDsModelDefine, lastPeriodCombination, allFieldKeys, dataSource, snapshotId, snapshotTitle);
                continue;
            }
            if (snapshotKeyStr.equals("LASTYEAR_SAMEPERIOD")) {
                snapshotId = "LASTYEAR_SAMEPERIOD";
                snapshotTitle = "\u4e0a\u5e74\u540c\u671f\u6570";
                dimensionValueSet = combination.toDimensionValueSet();
                DimensionValueSet lastyearDimensionValueSet = new DimensionValueSet(dimensionValueSet);
                currenPeriod = dimensionValueSet.getValue("DATATIME");
                String currenPeriodCode = (String)currenPeriod;
                String currenYear = currenPeriodCode.substring(0, 4);
                int currenYearInt = Integer.parseInt(currenYear);
                int lastYearInt = currenYearInt - 1;
                String lastYearPeriod = Integer.toString(lastYearInt) + currenPeriodCode.substring(4);
                lastyearDimensionValueSet.setValue("DATATIME", (Object)lastYearPeriod);
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(lastyearDimensionValueSet);
                DimensionCombination lastYearPeriodCombination = dimensionCombinationBuilder.getCombination();
                QueryPeriodDataSourceContext queryContext = new QueryPeriodDataSourceContext(lastYearPeriodCombination, formDefine.getFormScheme(), dataRange, snapshotTitle);
                dataSource = this.dataSourceBuilder.queryPeriodDataSource(queryContext);
                if (null == dataSource) continue;
                this.buildMemory(memoryDataSet, snapshotDSModel, snapshotDsModelDefine, lastYearPeriodCombination, allFieldKeys, dataSource, snapshotId, snapshotTitle);
                continue;
            }
            for (SnapshotInfo snapshotInfo : snapshotInfos) {
                if (!snapshotKeyStr.equals(snapshotInfo.getSnapshot().getSnapshotId())) continue;
                Snapshot snapshot = snapshotInfo.getSnapshot();
                snapshotId = snapshot.getSnapshotId();
                snapshotTitle = snapshot.getTitle();
                QuerySnapshotDataSourceContext queryContext = new QuerySnapshotDataSourceContext(formDefine.getFormScheme(), combination, dataRange, snapshot.getSnapshotId());
                dataSource = this.dataSourceBuilder.querySnapshotDataSource(queryContext);
                break;
            }
            if (null == dataSource) continue;
            this.buildMemory(memoryDataSet, snapshotDSModel, snapshotDsModelDefine, allFieldKeys, dataAssist, dataSource, snapshotId, snapshotTitle);
        }
    }

    private void buildMemory(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet, SnapshotDSModel snapshotDSModel, SnapshotDsModelDefine snapshotDsModelDefine, DimensionCombination combination, List<String> allFieldKeys, DataSource dataSource, String snapshotId, String snapshotTitle) throws DataSetException {
        List<FloatRegionData> floatDatas;
        DimensionValueSet dimensionValueSet = combination.toDimensionValueSet();
        DataInfo data = dataSource.getData(snapshotDsModelDefine.getFormKey());
        FixRegionData fixData = data.getFixData();
        if (null != fixData && snapshotDSModel.getDataRegionKey().equals(fixData.getRegionKey())) {
            ArrayList<Object> datas = new ArrayList<Object>();
            datas.add(snapshotId);
            datas.add(snapshotTitle);
            List<FieldData> fixDatas = fixData.getFixDatas();
            block0: for (String allFieldKey : allFieldKeys) {
                if ("snapshotID".equals(allFieldKey) || "snapshotName".equals(allFieldKey)) continue;
                if (null != dimensionValueSet.getValue(allFieldKey)) {
                    if ("DATATIME".equals(allFieldKey)) {
                        datas.add(TimeDimUtils.periodToTimeKey((String)((String)dimensionValueSet.getValue(allFieldKey))));
                        continue;
                    }
                    datas.add((String)dimensionValueSet.getValue(allFieldKey));
                    continue;
                }
                for (FieldData fieldData : fixDatas) {
                    String string = fieldData.getFieldKey();
                    if (!StringUtils.isNotEmpty((String)string) || !allFieldKey.equals(string)) continue;
                    this.addData(datas, fieldData);
                    continue block0;
                }
            }
            DataRow dataRow = memoryDataSet.add();
            for (int i = 0; i < datas.size(); ++i) {
                dataRow.setValue(i, datas.get(i));
            }
        }
        if (null != (floatDatas = data.getFloatDatas()) && !floatDatas.isEmpty()) {
            for (FloatRegionData floatRegionData : floatDatas) {
                if (!snapshotDSModel.getDataRegionKey().equals(floatRegionData.getRegionKey())) continue;
                ArrayList<ArrayList<Object>> datass = new ArrayList<ArrayList<Object>>();
                List<List<FieldData>> floatDatass = floatRegionData.getFloatDatass();
                for (List<FieldData> list : floatDatass) {
                    ArrayList<Object> datas = new ArrayList<Object>();
                    datas.add(snapshotId);
                    datas.add(snapshotTitle);
                    block5: for (String allFieldKey : allFieldKeys) {
                        if ("snapshotID".equals(allFieldKey) || "snapshotName".equals(allFieldKey)) continue;
                        if (null != dimensionValueSet.getValue(allFieldKey)) {
                            if ("DATATIME".equals(allFieldKey)) {
                                datas.add(TimeDimUtils.periodToTimeKey((String)((String)dimensionValueSet.getValue(allFieldKey))));
                                continue;
                            }
                            datas.add((String)dimensionValueSet.getValue(allFieldKey));
                            continue;
                        }
                        for (FieldData fieldData : list) {
                            String fieldKey = fieldData.getFieldKey();
                            if (!StringUtils.isNotEmpty((String)fieldKey) || !allFieldKey.equals(fieldKey)) continue;
                            this.addData(datas, fieldData);
                            continue block5;
                        }
                    }
                    datass.add(datas);
                }
                for (List<FieldData> list : datass) {
                    DataRow dataRow = memoryDataSet.add();
                    for (int i = 0; i < list.size(); ++i) {
                        dataRow.setValue(i, (Object)list.get(i));
                    }
                    dataRow.commit();
                }
            }
        }
    }

    private void addData(List<Object> datas, FieldData fieldData) {
        AbstractData abstractData = fieldData.getData();
        if (null != abstractData && !abstractData.isNull) {
            if (abstractData instanceof IntData) {
                datas.add(abstractData.getAsInt());
            } else if (abstractData instanceof FloatData) {
                datas.add(abstractData.getAsFloat());
            } else if (abstractData instanceof DateData) {
                datas.add(abstractData.getAsDate());
            } else if (abstractData instanceof DateTimeData) {
                datas.add(abstractData.getAsDateTime());
            } else if (abstractData instanceof StringData) {
                datas.add(abstractData.getAsString());
            } else if (abstractData instanceof CurrencyData) {
                datas.add(abstractData.getAsCurrency().doubleValue());
            } else if (abstractData instanceof BoolData) {
                datas.add(abstractData.getAsBool());
            } else {
                datas.add("");
            }
        } else if (abstractData instanceof IntData) {
            datas.add(0);
        } else if (abstractData instanceof FloatData) {
            datas.add(0);
        } else if (abstractData instanceof DateData) {
            datas.add(0);
        } else if (abstractData instanceof DateTimeData) {
            datas.add(0);
        } else if (abstractData instanceof StringData) {
            datas.add("");
        } else if (abstractData instanceof CurrencyData) {
            datas.add(0);
        } else if (abstractData instanceof BoolData) {
            datas.add(false);
        } else {
            datas.add("");
        }
    }

    private void buildMemory(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet, SnapshotDSModel snapshotDSModel, SnapshotDsModelDefine snapshotDsModelDefine, List<String> allFieldKeys, IDataAssist dataAssist, DataSource dataSource, String snapshotId, String snapshotTitle) throws Exception {
        List<FloatRegionData> floatDatas;
        DataInfo data = dataSource.getData(snapshotDsModelDefine.getFormKey());
        FixRegionData fixData = data.getFixData();
        if (null != fixData && snapshotDSModel.getDataRegionKey().equals(fixData.getRegionKey())) {
            ArrayList<Object> datas = new ArrayList<Object>();
            datas.add(snapshotId);
            datas.add(snapshotTitle);
            List<FieldData> fixDatas = fixData.getFixDatas();
            for (String allFieldKey : allFieldKeys) {
                if ("snapshotID".equals(allFieldKey) || "snapshotName".equals(allFieldKey)) continue;
                for (FieldData fieldData : fixDatas) {
                    FieldDefine fieldDefine;
                    String string = fieldData.getFieldKey();
                    if (!StringUtils.isNotEmpty((String)string) || null == (fieldDefine = this.runTimeViewController.queryFieldDefine(string))) continue;
                    String dimensionName = dataAssist.getDimensionName(fieldDefine);
                    if (StringUtils.isNotEmpty((String)dimensionName) && allFieldKey.equals(dimensionName)) {
                        if ("DATATIME".equals(dimensionName)) {
                            datas.add(fieldData.getData().getAsString() == null ? "" : TimeDimUtils.periodToTimeKey((String)fieldData.getData().getAsString()));
                            continue;
                        }
                        datas.add(fieldData.getData().getAsString() == null ? "" : fieldData.getData().getAsString());
                        continue;
                    }
                    if (!allFieldKey.equals(string)) continue;
                    this.addData(datas, fieldData);
                }
            }
            DataRow dataRow = memoryDataSet.add();
            for (int i = 0; i < datas.size(); ++i) {
                dataRow.setValue(i, datas.get(i));
            }
        }
        if (null != (floatDatas = data.getFloatDatas()) && !floatDatas.isEmpty()) {
            for (FloatRegionData floatRegionData : floatDatas) {
                if (!snapshotDSModel.getDataRegionKey().equals(floatRegionData.getRegionKey())) continue;
                ArrayList<ArrayList<Object>> datass = new ArrayList<ArrayList<Object>>();
                List<List<FieldData>> floatDatass = floatRegionData.getFloatDatass();
                for (List<FieldData> list : floatDatass) {
                    ArrayList<Object> datas = new ArrayList<Object>();
                    datas.add(snapshotId);
                    datas.add(snapshotTitle);
                    for (String allFieldKey : allFieldKeys) {
                        if ("snapshotID".equals(allFieldKey) || "snapshotName".equals(allFieldKey)) continue;
                        for (FieldData fieldData : list) {
                            FieldDefine fieldDefine;
                            String fieldKey = fieldData.getFieldKey();
                            if (!StringUtils.isNotEmpty((String)fieldKey) || null == (fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey))) continue;
                            String dimensionName = dataAssist.getDimensionName(fieldDefine);
                            if (StringUtils.isNotEmpty((String)dimensionName) && allFieldKey.equals(dimensionName)) {
                                if ("DATATIME".equals(dimensionName)) {
                                    datas.add(fieldData.getData().getAsString() == null ? "" : TimeDimUtils.periodToTimeKey((String)fieldData.getData().getAsString()));
                                    continue;
                                }
                                datas.add(fieldData.getData().getAsString() == null ? "" : fieldData.getData().getAsString());
                                continue;
                            }
                            if (!allFieldKey.equals(fieldKey)) continue;
                            this.addData(datas, fieldData);
                        }
                    }
                    datass.add(datas);
                }
                for (List<FieldData> list : datass) {
                    DataRow dataRow = memoryDataSet.add();
                    for (int i = 0; i < list.size(); ++i) {
                        dataRow.setValue(i, (Object)list.get(i));
                    }
                    dataRow.commit();
                }
            }
        }
    }
}

