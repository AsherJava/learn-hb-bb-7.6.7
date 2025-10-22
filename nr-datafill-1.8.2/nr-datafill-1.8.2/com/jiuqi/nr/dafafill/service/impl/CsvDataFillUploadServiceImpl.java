/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.graphics.Point
 *  com.jiuqi.nr.table.df.DataFrame
 *  com.jiuqi.nr.table.df.Index
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.graphics.Point;
import com.jiuqi.nr.dafafill.model.AsyncUploadInfo;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DFDimensionValueGetService;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveRow;
import com.jiuqi.nr.dafafill.model.DataFillResult;
import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvService;
import com.jiuqi.nr.dafafill.service.IDataFillUploadService;
import com.jiuqi.nr.dafafill.util.DateUtils;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.Index;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CsvDataFillUploadServiceImpl
implements IDataFillUploadService {
    private static Logger logger = LoggerFactory.getLogger(CsvDataFillUploadServiceImpl.class);
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionParser;
    @Autowired
    private IDataFillDataEnvService dataFillDataEnvService;
    @Autowired
    private DFDimensionValueGetService dfDimensionValueGetService;

    @Override
    public boolean accept(String suffix) {
        return "csv".equalsIgnoreCase(suffix);
    }

    @Override
    public void upload(AsyncTaskMonitor asyncTaskMonitor, AsyncUploadInfo asyncUploadInfo, File tempFile) {
        try {
            asyncTaskMonitor.progressAndMessage(0.1, "\u5bf9\u53c2\u6570\u6570\u636e\u8fdb\u884c\u89e3\u6790");
            HashMap<String, Point> valuePointMap = new HashMap<String, Point>();
            DataFillDataQueryInfo queryInfo = asyncUploadInfo.getQueryInfo();
            DataFillContext context = queryInfo.getContext();
            Map<String, QueryField> simplifyQueryFieldsMap = this.dFDimensionParser.getSimplifyQueryFieldsMap(context);
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            List<QueryField> sceneQueryFields = fieldTypeQueryFields.get((Object)FieldType.SCENE);
            ArrayList<QueryField> otherDimensionQueryFields = new ArrayList<QueryField>();
            if (null != sceneQueryFields) {
                otherDimensionQueryFields.addAll(sceneQueryFields);
            }
            List otherDimensionNames = otherDimensionQueryFields.stream().map(QueryField::getSimplifyFullCode).collect(Collectors.toList());
            ArrayList<Object> commonDimensions = new ArrayList<Object>();
            if (!otherDimensionNames.isEmpty()) {
                commonDimensions.addAll(otherDimensionNames);
            }
            QueryField period = null;
            if (fieldTypeQueryFields.containsKey((Object)FieldType.PERIOD)) {
                period = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
            }
            QueryField master = null;
            if (fieldTypeQueryFields.containsKey((Object)FieldType.MASTER)) {
                master = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            }
            QueryField adjust = null;
            if (fieldTypeQueryFields.containsKey((Object)FieldType.ADJUST)) {
                adjust = fieldTypeQueryFields.get((Object)FieldType.ADJUST).get(0);
            }
            TableType tableType = context.getModel().getTableType();
            int rowIndex = 0;
            if (TableType.FIXED == tableType) {
                asyncTaskMonitor.progressAndMessage(0.15, "\u56fa\u5b9a\u8868");
                rowIndex = adjust != null ? 3 : 2;
            } else if (TableType.MASTER == tableType || TableType.FMDM == tableType) {
                asyncTaskMonitor.progressAndMessage(0.15, "\u5c01\u9762\u4ee3\u7801");
                rowIndex = 1;
                if (null != period) {
                    commonDimensions.add(period.getSimplifyFullCode());
                }
            } else if (TableType.FLOAT == tableType) {
                asyncTaskMonitor.progressAndMessage(0.15, "\u6d6e\u52a8\u884c");
                rowIndex = 3;
            }
            ArrayList<DFDimensionValue> commonDimensionValues = new ArrayList<DFDimensionValue>();
            if (!commonDimensions.isEmpty()) {
                for (String string : commonDimensions) {
                    Optional<DFDimensionValue> findFirst;
                    QueryField queryField = simplifyQueryFieldsMap.get(string);
                    if (null == queryField || queryField.getFieldType() == FieldType.TABLEDIMENSION || !(findFirst = context.getDimensionValues().stream().filter(e -> e.getName().equals(commonDimension)).findFirst()).isPresent()) continue;
                    DFDimensionValue dfDimensionValue = findFirst.get();
                    commonDimensionValues.add(dfDimensionValue);
                }
            }
            ArrayList<DataFillDataSaveRow> modifys = new ArrayList<DataFillDataSaveRow>();
            asyncTaskMonitor.progressAndMessage(0.2, "\u5f00\u59cb\u89e3\u6790csv");
            DataFrame dataFrame = DataFrame.read().csv(tempFile.getPath());
            asyncTaskMonitor.progressAndMessage(0.3, "\u89e3\u6790csv\u5b8c\u6210");
            HashMap<Integer, String> colIndexCodeMap = new HashMap<Integer, String>();
            Index columns = dataFrame.columns();
            asyncTaskMonitor.progressAndMessage(0.35, "\u5f00\u59cb\u89e3\u6790\u5217\u7d22\u5f15");
            ArrayList c_levels = new ArrayList(columns.levels());
            for (int l = 0; l < columns.count(); ++l) {
                for (int c = 0; c < columns.size(); ++c) {
                    Object column = c_levels.get(c);
                    String cellValue = column.toString();
                    String[] strs = cellValue.split("\\|");
                    String zbCode = strs[0];
                    colIndexCodeMap.put(c, zbCode);
                }
            }
            int valuePointY = 2;
            Iterator iterator = dataFrame.iterator();
            asyncTaskMonitor.progressAndMessage(0.4, "\u5f00\u59cb\u89e3\u6790\u6570\u636e\u533a\u57df");
            while (iterator.hasNext()) {
                DataFillDataSaveRow modifyRow = new DataFillDataSaveRow();
                ArrayList<DFDimensionValue> dimensionValues = new ArrayList<DFDimensionValue>();
                ArrayList<String> zbs = new ArrayList<String>();
                ArrayList<String> values = new ArrayList<String>();
                List rowData = (List)iterator.next();
                int size = rowData.size();
                for (int i = 0; i < size; ++i) {
                    Object cellObject = rowData.get(i);
                    String cellValueTitle = "";
                    if (null != cellObject) {
                        cellValueTitle = cellObject instanceof LocalDateTime ? DateUtils.formatter.format((LocalDateTime)cellObject) : (cellObject instanceof Integer ? (Integer)cellObject + "" : (cellObject instanceof Double ? (Double.isNaN((Double)cellObject) ? "" : (Double)cellObject + "") : (cellObject instanceof Long ? (Long)cellObject + "" : (cellObject instanceof Boolean ? ((Boolean)cellObject).toString() : (cellObject instanceof String ? (String)cellObject : cellObject.toString())))));
                    }
                    String[] strs = cellValueTitle.split("\\|");
                    String cellValue = strs[0];
                    if (i < rowIndex) {
                        DFDimensionValue masterDimensionValue;
                        if (i == 0) {
                            if (2 == rowIndex || 3 == rowIndex) {
                                DFDimensionValue periodDimensionValue = new DFDimensionValue();
                                periodDimensionValue.setName(period.getSimplifyFullCode());
                                periodDimensionValue.setValues(cellValue);
                                dimensionValues.add(periodDimensionValue);
                                continue;
                            }
                            masterDimensionValue = new DFDimensionValue();
                            masterDimensionValue.setName(master.getSimplifyFullCode());
                            masterDimensionValue.setValues(cellValue);
                            dimensionValues.add(masterDimensionValue);
                            if (rowIndex != 1) continue;
                            DFDimensionValue periodDim = new DFDimensionValue();
                            periodDim.setName(period.getSimplifyFullCode());
                            periodDim.setValues(period.getMinValue());
                            periodDim.setMaxValue(period.getMaxValue());
                            dimensionValues.add(periodDim);
                            continue;
                        }
                        if (rowIndex == 3) {
                            if (adjust != null) {
                                DFDimensionValue dfDimensionValue;
                                if (i == 1) {
                                    dfDimensionValue = new DFDimensionValue();
                                    dfDimensionValue.setName("Y._BINDING_");
                                    dfDimensionValue.setValues(cellValue);
                                    dimensionValues.add(dfDimensionValue);
                                    continue;
                                }
                                dfDimensionValue = new DFDimensionValue();
                                dfDimensionValue.setName(master.getSimplifyFullCode());
                                dfDimensionValue.setValues(cellValue);
                                dimensionValues.add(dfDimensionValue);
                                continue;
                            }
                            if (i == 1) {
                                masterDimensionValue = new DFDimensionValue();
                                masterDimensionValue.setName(master.getSimplifyFullCode());
                                masterDimensionValue.setValues(cellValue);
                                dimensionValues.add(masterDimensionValue);
                                continue;
                            }
                            if (i != 2) continue;
                            masterDimensionValue = new DFDimensionValue();
                            masterDimensionValue.setName("ID");
                            masterDimensionValue.setValues(cellValue);
                            dimensionValues.add(masterDimensionValue);
                            continue;
                        }
                        masterDimensionValue = new DFDimensionValue();
                        masterDimensionValue.setName(master.getSimplifyFullCode());
                        masterDimensionValue.setValues(cellValue);
                        dimensionValues.add(masterDimensionValue);
                        continue;
                    }
                    if (rowIndex == 3 && i == 3 && adjust == null) {
                        zbs.add("FLOATORDER");
                        values.add(cellValue);
                        continue;
                    }
                    String zbCode = (String)colIndexCodeMap.get(i);
                    QueryField zbQueryField = simplifyQueryFieldsMap.get(zbCode);
                    if (null == zbQueryField) continue;
                    zbs.add(zbQueryField.getId());
                    values.add(cellValue);
                    if (CollectionUtils.isEmpty(dimensionValues) || dimensionValues.size() < 2) continue;
                    valuePointMap.put(zbQueryField.getId() + this.dfDimensionValueGetService.getValues((DFDimensionValue)dimensionValues.get(0), context.getModel()) + this.dfDimensionValueGetService.getValues((DFDimensionValue)dimensionValues.get(1), context.getModel()), new Point(i + 1, valuePointY));
                }
                if (!zbs.isEmpty()) {
                    if (!commonDimensionValues.isEmpty()) {
                        dimensionValues.addAll(commonDimensionValues);
                    }
                    modifyRow.setDimensionValues(dimensionValues);
                    modifyRow.setZbs(zbs);
                    modifyRow.setValues(values);
                    modifys.add(modifyRow);
                }
                ++valuePointY;
            }
            DataFillDataSaveInfo saveInfo = new DataFillDataSaveInfo();
            saveInfo.setModifys(modifys);
            saveInfo.setContext(context);
            asyncTaskMonitor.progressAndMessage(0.5, "\u6570\u636e\u4fdd\u5b58\u63d0\u4ea4");
            DataFillResult save = this.dataFillDataEnvService.save(saveInfo);
            if (save.isSuccess()) {
                asyncTaskMonitor.finish("true", (Object)"csv\u5bfc\u5165\u6210\u529f");
            } else {
                if (save.getErrors().size() != 0) {
                    for (DataFillSaveErrorDataInfo error : save.getErrors()) {
                        Point p;
                        if (TableType.FIXED != tableType && TableType.MASTER != tableType || (p = (Point)valuePointMap.get(error.getZb() + this.dfDimensionValueGetService.getValues(error.getDimensionValues().get(0), context.getModel()) + this.dfDimensionValueGetService.getValues(error.getDimensionValues().get(1), context.getModel()))) == null) continue;
                        error.setErrorLocX(p.x);
                        error.setErrorLocY(p.y);
                    }
                }
                asyncTaskMonitor.finish("false", save.getErrors());
                if (save.getErrors().size() > 0) {
                    logger.warn("\u81ea\u5b9a\u4e49\u5f55\u5165CSV\u5bfc\u5165\u5931\u8d25\uff1b" + save.getErrors().get(0).getDataError().getErrorInfo());
                }
            }
        }
        catch (Exception e2) {
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u5bfc\u5165\u5f02\u5e38\uff01", e2);
            asyncTaskMonitor.error("\u81ea\u5b9a\u4e49\u5f55\u5165CSV\u5bfc\u5165\u5f02\u5e38\uff01", (Throwable)e2);
        }
    }
}

