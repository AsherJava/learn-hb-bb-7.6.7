/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.graphics.Point
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 *  com.jiuqi.nr.common.importdata.ImportResultReportObject
 *  com.jiuqi.nr.common.importdata.ImportResultSheetObject
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.util.ExcelErrorUtil
 *  com.jiuqi.nr.dataentry.util.ExcelImportUtil
 *  com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO
 *  com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.table.data.ColumnType
 *  com.jiuqi.nr.table.df.DataFrame
 *  com.jiuqi.nr.table.df.Index
 *  com.jiuqi.nr.table.io.excel.XlsxReadOptions
 *  com.jiuqi.nr.table.io.excel.XlsxReader
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.graphics.Point;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.common.importdata.ImportResultReportObject;
import com.jiuqi.nr.common.importdata.ImportResultSheetObject;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
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
import com.jiuqi.nr.dafafill.model.enums.TableSample;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvService;
import com.jiuqi.nr.dafafill.service.IDataFillUploadService;
import com.jiuqi.nr.dafafill.util.DateUtils;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.util.ExcelErrorUtil;
import com.jiuqi.nr.dataentry.util.ExcelImportUtil;
import com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO;
import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nr.table.io.excel.XlsxReadOptions;
import com.jiuqi.nr.table.io.excel.XlsxReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ExcelDataFillUploadServiceImpl
implements IDataFillUploadService {
    private static Logger logger = LoggerFactory.getLogger(ExcelDataFillUploadServiceImpl.class);
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionParser;
    @Autowired
    private IDataFillDataEnvService dataFillDataEnvService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeCtrl;
    @Autowired
    private DFDimensionValueGetService dfDimensionValueGetService;
    @Autowired
    private AdjustPeriodDesignService adjustPeriodDesignService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public boolean accept(String suffix) {
        return "xlsx".equalsIgnoreCase(suffix);
    }

    @Override
    public void upload(AsyncTaskMonitor asyncTaskMonitor, AsyncUploadInfo asyncUploadInfo, File tempFile) {
        try {
            int c;
            asyncTaskMonitor.progressAndMessage(0.1, NrDataFillI18nUtil.buildCode("nr.dataFill.parseParam"));
            DataFillDataQueryInfo queryInfo = asyncUploadInfo.getQueryInfo();
            DataFillContext context = queryInfo.getContext();
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            List<QueryField> displayColsQueryFields = this.dFDimensionParser.getDisplayColsQueryFields(context);
            List<QueryField> sceneQueryFields = fieldTypeQueryFields.get((Object)FieldType.SCENE);
            ArrayList<QueryField> otherDimensionQueryFields = new ArrayList<QueryField>();
            if (null != sceneQueryFields) {
                otherDimensionQueryFields.addAll(sceneQueryFields);
            }
            List otherDimensionNames = otherDimensionQueryFields.stream().map(QueryField::getSimplifyFullCode).collect(Collectors.toList());
            ArrayList<String> rowDimensions = new ArrayList<String>();
            ArrayList<String> colDimensions = new ArrayList<String>();
            ArrayList commonDimensions = new ArrayList();
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
            QueryField adjustQueryField = null;
            if (fieldTypeQueryFields.containsKey((Object)FieldType.ADJUST)) {
                adjustQueryField = fieldTypeQueryFields.get((Object)FieldType.ADJUST).get(0);
            }
            String dwDimensionName = master.getSimplifyFullCode();
            String periodDimensionName = period.getSimplifyFullCode();
            String adjustDimensionName = Optional.ofNullable(adjustQueryField).map(QueryField::getFullCode).orElse(null);
            TableType tableType = context.getModel().getTableType();
            TableSample tableSample = context.getModel().getTableSample();
            DFDimensionValue hidePeriod = null;
            if (TableType.FIXED == tableType) {
                asyncTaskMonitor.progressAndMessage(0.15, NrDataFillI18nUtil.buildCode("nr.dataFill.fixTable"));
                if (tableSample == TableSample.PERIODUNITZB || tableSample == TableSample.NOTSUPPORTED) {
                    rowDimensions.add(periodDimensionName);
                    if (adjustDimensionName != null) {
                        rowDimensions.add(adjustDimensionName);
                    }
                    rowDimensions.add(dwDimensionName);
                    colDimensions.add("ZB");
                } else if (tableSample == TableSample.UNITPERIODZB) {
                    rowDimensions.add(dwDimensionName);
                    rowDimensions.add(periodDimensionName);
                    if (adjustDimensionName != null) {
                        rowDimensions.add(adjustDimensionName);
                    }
                    colDimensions.add("ZB");
                } else if (tableSample == TableSample.PERIODZBUNIT) {
                    rowDimensions.add(periodDimensionName);
                    if (adjustDimensionName != null) {
                        rowDimensions.add(adjustDimensionName);
                    }
                    rowDimensions.add("ZB");
                    colDimensions.add(dwDimensionName);
                } else if (tableSample == TableSample.UNITZBPERIOD) {
                    rowDimensions.add(dwDimensionName);
                    rowDimensions.add("ZB");
                    colDimensions.add(periodDimensionName);
                    if (adjustDimensionName != null) {
                        colDimensions.add(adjustDimensionName);
                    }
                }
            } else if (TableType.MASTER == tableType || TableType.FMDM == tableType) {
                asyncTaskMonitor.progressAndMessage(0.15, NrDataFillI18nUtil.buildCode("nr.dataFill.frontCover"));
                rowDimensions.add(dwDimensionName);
                colDimensions.add("ZB");
                QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
                List<DFDimensionValue> dimensionValues = queryInfo.getContext().getDimensionValues();
                Optional<DFDimensionValue> findFirst = dimensionValues.stream().filter(e -> e.getName().equals(periodField.getId())).findFirst();
                if (!findFirst.isPresent()) {
                    IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(periodField.getId());
                    int ordinal = periodEntity.getType().ordinal();
                    PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)PeriodUtil.getCurrentCalendar(), (int)ordinal, (int)0);
                    hidePeriod = new DFDimensionValue();
                    hidePeriod.setName(periodField.getId());
                    hidePeriod.setValues(currentPeriod.toString());
                } else {
                    hidePeriod = findFirst.get();
                }
            } else if (TableType.FLOAT == tableType || TableType.ACCOUNT == tableType) {
                asyncTaskMonitor.progressAndMessage(0.15, NrDataFillI18nUtil.buildCode("nr.dataFill.floatTable"));
                rowDimensions.add(periodDimensionName);
                if (adjustDimensionName != null) {
                    rowDimensions.add(adjustDimensionName);
                }
                rowDimensions.add(dwDimensionName);
                colDimensions.add("ZB");
            }
            ArrayList<DFDimensionValue> commonDimensionValues = new ArrayList<DFDimensionValue>();
            if (!commonDimensions.isEmpty()) {
                for (String commonDimension : commonDimensions) {
                    Optional<DFDimensionValue> findFirst = context.getDimensionValues().stream().filter(e -> e.getName().equals(commonDimension)).findFirst();
                    if (!findFirst.isPresent()) continue;
                    DFDimensionValue dfDimensionValue = (DFDimensionValue)findFirst.get();
                    commonDimensionValues.add(dfDimensionValue);
                }
            }
            ArrayList<Object> columnTypes = new ArrayList<Object>();
            for (int i = 0; i < rowDimensions.size(); ++i) {
                columnTypes.add(ColumnType.STRING);
            }
            ArrayList<QueryField> showQueryField = new ArrayList<QueryField>();
            for (QueryField queryField : displayColsQueryFields) {
                if (queryField.getDataType() == DataFieldType.DATE) {
                    columnTypes.add(ColumnType.LOCAL_DATE);
                } else if (queryField.getDataType() == DataFieldType.DATE_TIME) {
                    columnTypes.add(ColumnType.LOCAL_DATE_TIME);
                } else {
                    columnTypes.add(ColumnType.STRING);
                }
                if (queryField.getFieldType() == FieldType.MASTER || queryField.getFieldType() == FieldType.PERIOD) continue;
                showQueryField.add(queryField);
            }
            ColumnType[] colTypes = new ColumnType[columnTypes.size()];
            for (int i = 0; i < columnTypes.size(); ++i) {
                colTypes[i] = (ColumnType)columnTypes.get(i);
            }
            int cols = showQueryField.size();
            asyncTaskMonitor.progressAndMessage(0.2, NrDataFillI18nUtil.buildCode("nr.dataFill.beginParseExcel"));
            XlsxReadOptions build = XlsxReadOptions.builder((File)tempFile).allowDuplicateColumnNames(Boolean.valueOf(true)).columnTypes(colTypes).build();
            List tables = new XlsxReader().readMultiple(build);
            DataFrame df = (DataFrame)tables.get(0);
            asyncTaskMonitor.progressAndMessage(0.3, NrDataFillI18nUtil.buildCode("nr.dataFill.parseExcelSucc"));
            ArrayList<DataFillDataSaveRow> modifys = new ArrayList<DataFillDataSaveRow>();
            BigDecimal perCentMultiplicand = new BigDecimal("100");
            BigDecimal perMilMultiplicand = new BigDecimal("1000");
            HashMap<Integer, String> colIndexTitleMap = new HashMap<Integer, String>();
            Index columns = df.columns();
            asyncTaskMonitor.progressAndMessage(0.35, NrDataFillI18nUtil.buildCode("nr.dataFill.beginParseColIndex"));
            ArrayList c_levels = new ArrayList(columns.levels());
            for (int l = 0; l < columns.count(); ++l) {
                for (c = 0; c < columns.size(); ++c) {
                    Object column = c_levels.get(c);
                    String cellValue = column.toString();
                    colIndexTitleMap.put(c, cellValue);
                }
            }
            HashMap colIndexToAdjustTitleMap = new HashMap();
            if (adjustQueryField != null) {
                for (c = 0; c < columns.size(); ++c) {
                    if (c <= 1) continue;
                    Object o = df.getData().get(c, 0);
                    colIndexToAdjustTitleMap.put(c, Optional.ofNullable(o).map(Object::toString).orElse(null));
                }
            }
            ArrayList<DataFillSaveErrorDataInfo> errors = new ArrayList<DataFillSaveErrorDataInfo>();
            HashMap<String, String> periodMap = new HashMap<String, String>();
            HashMap<String, String> adjustCodeMap = new HashMap<String, String>();
            HashMap<String, String> masterMap = new HashMap<String, String>();
            HashMap<Integer, String> colIndexCodeMap = new HashMap<Integer, String>();
            HashMap<Integer, RowCol> masterTitleMap = new HashMap<Integer, RowCol>();
            Set entrySet = colIndexTitleMap.entrySet();
            Boolean isMasterOnCol = false;
            Boolean isPeriodOnCol = false;
            for (Map.Entry entry : entrySet) {
                String title;
                Integer index = (Integer)entry.getKey();
                if (index < rowDimensions.size() || !StringUtils.hasLength(title = (String)entry.getValue()) || !StringUtils.hasLength(title.trim())) continue;
                title = title.trim();
                if (colDimensions.contains("ZB")) {
                    QueryField findZb;
                    QueryField mustMatching = null;
                    int colIndex = index - rowDimensions.size();
                    if (colIndex < displayColsQueryFields.size()) {
                        mustMatching = displayColsQueryFields.get(colIndex);
                    }
                    if (null != (findZb = this.findZb(title, 1, index + 1, mustMatching, context))) {
                        colIndexCodeMap.put(index, findZb.getId());
                        continue;
                    }
                    this.zbError(1, index + 1, title, findZb, errors);
                    continue;
                }
                if (colDimensions.contains(periodDimensionName)) {
                    String periodCode = this.findPeriod(period, title, 1, index + 1, periodMap);
                    if (StringUtils.hasLength(periodCode)) {
                        colIndexCodeMap.put(index, periodCode);
                    }
                    isPeriodOnCol = true;
                    continue;
                }
                if (!colDimensions.contains(dwDimensionName)) continue;
                RowCol rowCol = new RowCol();
                rowCol.setTitle(title);
                rowCol.setRow(1);
                rowCol.setCol(index + 1);
                masterTitleMap.put(index, rowCol);
                isMasterOnCol = true;
            }
            Iterator iterator = df.iterator();
            asyncTaskMonitor.progressAndMessage(0.4, NrDataFillI18nUtil.buildCode("nr.dataFill.beginParseData"));
            int rowNum = 1;
            ArrayList<String> zbs = null;
            ArrayList<String> values = null;
            DataFillDataSaveRow modifyRow = null;
            HashMap<String, Point> valueXY = new HashMap<String, Point>();
            ArrayList<DataFillSaveErrorDataInfo> dimensionErrorList = new ArrayList<DataFillSaveErrorDataInfo>();
            HashSet<Integer> masterDimensionErrorVisitedSet = new HashSet<Integer>();
            HashSet<Integer> periodDimensionErrorRowSet = new HashSet<Integer>();
            Integer masterDimensionCol = null;
            Integer periodDimensionCol = null;
            String errorMsg = null;
            while (iterator.hasNext()) {
                ++rowNum;
                String zbId = "";
                DFDimensionValue periodDimensionValue = hidePeriod;
                DFDimensionValue adjustDimensionValue = null;
                DFDimensionValue masterDimensionValue = null;
                Object masterRowCol = null;
                zbs = new ArrayList<String>();
                values = new ArrayList<String>();
                modifyRow = new DataFillDataSaveRow();
                List rowData = (List)iterator.next();
                int size = rowData.size();
                int i = 0;
                if (rowNum == 2 && tableSample == TableSample.UNITZBPERIOD && adjustQueryField != null) {
                    i = 2;
                }
                while (i < size) {
                    ResultErrorInfo dimensionErrorInfo;
                    Object cellObject = rowData.get(i);
                    String cellValueTitle = "";
                    if (null != cellObject) {
                        if (cellObject instanceof LocalDateTime) {
                            cellValueTitle = DateUtils.formatter.format((LocalDateTime)cellObject);
                        } else if (cellObject instanceof Integer) {
                            cellValueTitle = (Integer)cellObject + "";
                        } else if (cellObject instanceof Double) {
                            cellValueTitle = (Double)cellObject + "";
                        } else if (cellObject instanceof Long) {
                            cellValueTitle = (Long)cellObject + "";
                        } else if (cellObject instanceof Boolean) {
                            cellValueTitle = ((Boolean)cellObject).toString();
                        } else if (cellObject instanceof String) {
                            cellValueTitle = (String)cellObject;
                        } else {
                            logger.error("\u672a\u77e5\u7684\u5355\u5143\u683c\u7c7b\u578b\uff1a\u3010" + cellObject + "\u3011");
                        }
                    } else {
                        cellValueTitle = "";
                    }
                    cellValueTitle = cellValueTitle.trim();
                    if (i < rowDimensions.size()) {
                        String rowDimensionName = (String)rowDimensions.get(i);
                        if (rowDimensionName.equals("ZB")) {
                            QueryField findZb;
                            int result;
                            int n = result = adjustQueryField == null ? (rowNum - 1) % cols : (rowNum - 1 - 1) % cols;
                            if (result == 0) {
                                result = cols;
                            }
                            int index = result - 1;
                            QueryField mustMatching = null;
                            if (index < cols) {
                                mustMatching = (QueryField)showQueryField.get(index);
                            }
                            if (null != (findZb = this.findZb(cellValueTitle, rowNum, i + 1, mustMatching, context))) {
                                zbId = findZb.getId();
                            } else {
                                this.zbError(rowNum, i + 1, cellValueTitle, findZb, errors);
                            }
                        } else if (rowDimensionName.equals(periodDimensionName)) {
                            periodDimensionCol = i + 1;
                            String periodCode = this.findPeriod(period, cellValueTitle, rowNum, i + 1, periodMap);
                            if (StringUtils.hasLength(periodCode)) {
                                periodDimensionValue = new DFDimensionValue();
                                periodDimensionValue.setName(period.getSimplifyFullCode());
                                periodDimensionValue.setValues(periodCode);
                            }
                            if (i == 0 && cellObject == null) {
                                errorMsg = NrDataFillI18nUtil.buildCode("nr.dataFill.lackTimeDimen");
                                DataFillSaveErrorDataInfo dimensionError = new DataFillSaveErrorDataInfo();
                                dimensionErrorInfo = new ResultErrorInfo();
                                dimensionErrorInfo.setErrorInfo(errorMsg);
                                dimensionErrorInfo.setErrorCode(ErrorCode.DATAERROR);
                                dimensionError.setDataError(dimensionErrorInfo);
                                dimensionError.setErrorLocX(i + 1);
                                dimensionError.setErrorLocY(rowNum);
                                dimensionErrorList.add(dimensionError);
                                periodDimensionErrorRowSet.add(rowNum);
                            }
                        } else if (rowDimensionName.equals(adjustDimensionName)) {
                            String adjustCode = this.findAdjustCode(period.getDataSchemeCode(), cellValueTitle, periodDimensionValue, rowNum, i + 1, adjustCodeMap);
                            if (adjustCode == null) {
                                errorMsg = NrDataFillI18nUtil.buildCode("nr.dataFill.lackAdjustDimen");
                                DataFillSaveErrorDataInfo dimensionError = new DataFillSaveErrorDataInfo();
                                dimensionErrorInfo = new ResultErrorInfo();
                                dimensionErrorInfo.setErrorInfo(errorMsg);
                                dimensionErrorInfo.setErrorCode(ErrorCode.DATAERROR);
                                dimensionError.setDataError(dimensionErrorInfo);
                                dimensionError.setErrorLocX(i + 1);
                                dimensionError.setErrorLocY(rowNum);
                                dimensionErrorList.add(dimensionError);
                            } else {
                                adjustDimensionValue = new DFDimensionValue();
                                adjustDimensionValue.setName("Y._BINDING_");
                                adjustDimensionValue.setValues(adjustCode);
                            }
                        } else if (rowDimensionName.equals(dwDimensionName)) {
                            masterDimensionCol = i + 1;
                            masterRowCol = new RowCol();
                            ((RowCol)masterRowCol).setTitle(cellValueTitle);
                            ((RowCol)masterRowCol).setRow(rowNum);
                            ((RowCol)masterRowCol).setCol(i + 1);
                            if (i == 0 && cellObject == null) {
                                errorMsg = NrDataFillI18nUtil.buildCode("nr.dataFill.lackMainDimen");
                                DataFillSaveErrorDataInfo dimensionError = new DataFillSaveErrorDataInfo();
                                ResultErrorInfo dimensionErrorInfo2 = new ResultErrorInfo();
                                dimensionErrorInfo2.setErrorInfo(errorMsg);
                                dimensionErrorInfo2.setErrorCode(ErrorCode.DATAERROR);
                                dimensionError.setDataError(dimensionErrorInfo2);
                                dimensionError.setErrorLocX(i + 1);
                                dimensionError.setErrorLocY(rowNum);
                                dimensionErrorList.add(dimensionError);
                                masterDimensionErrorVisitedSet.add(rowNum);
                            }
                        }
                    } else {
                        String masterCode;
                        String colVale = (String)colIndexCodeMap.get(i);
                        if (colDimensions.contains("ZB")) {
                            zbId = colVale;
                        } else if (colDimensions.contains(periodDimensionName)) {
                            periodDimensionCol = i + 1;
                            if (StringUtils.hasLength(colVale)) {
                                periodDimensionValue = new DFDimensionValue();
                                periodDimensionValue.setName(period.getSimplifyFullCode());
                                periodDimensionValue.setValues(colVale);
                            }
                        } else if (colDimensions.contains(dwDimensionName)) {
                            masterDimensionCol = i + 1;
                            RowCol rowCol = (RowCol)masterTitleMap.get(i);
                            String masterCode2 = this.findMaster(master, rowCol.getTitle(), rowCol.getRow(), rowCol.getCol(), period, null != periodDimensionValue ? this.dfDimensionValueGetService.getValues(periodDimensionValue, context.getModel()) : null, masterMap);
                            if (StringUtils.hasLength(masterCode2)) {
                                masterDimensionValue = new DFDimensionValue();
                                masterDimensionValue.setName(master.getSimplifyFullCode());
                                masterDimensionValue.setValues(masterCode2);
                            }
                        }
                        if (colDimensions.contains(adjustDimensionName)) {
                            String adjustCode = this.findAdjustCode(period.getDataSchemeCode(), (String)colIndexToAdjustTitleMap.get(i), periodDimensionValue, rowNum, i + 1, adjustCodeMap);
                            if (adjustCode == null) {
                                errorMsg = NrDataFillI18nUtil.buildCode("nr.dataFill.lackAdjustDimen");
                                DataFillSaveErrorDataInfo dimensionError = new DataFillSaveErrorDataInfo();
                                dimensionErrorInfo = new ResultErrorInfo();
                                dimensionErrorInfo.setErrorInfo(errorMsg);
                                dimensionErrorInfo.setErrorCode(ErrorCode.DATAERROR);
                                dimensionError.setDataError(dimensionErrorInfo);
                                dimensionError.setErrorLocX(i + 1);
                                dimensionError.setErrorLocY(rowNum);
                                dimensionErrorList.add(dimensionError);
                            } else {
                                adjustDimensionValue = new DFDimensionValue();
                                adjustDimensionValue.setName("Y._BINDING_");
                                adjustDimensionValue.setValues(adjustCode);
                            }
                        }
                        if (null != masterRowCol && null == masterDimensionValue && StringUtils.hasLength(masterCode = this.findMaster(master, ((RowCol)masterRowCol).getTitle(), ((RowCol)masterRowCol).getRow(), ((RowCol)masterRowCol).getCol(), period, null != periodDimensionValue ? this.dfDimensionValueGetService.getValues(periodDimensionValue, context.getModel()) : null, masterMap))) {
                            masterDimensionValue = new DFDimensionValue();
                            masterDimensionValue.setName(master.getSimplifyFullCode());
                            masterDimensionValue.setValues(masterCode);
                        }
                        if (StringUtils.hasLength(zbId) && null != masterDimensionValue && null != periodDimensionValue) {
                            if (cellValueTitle.contains("\u2030")) {
                                try {
                                    cellValueTitle = cellValueTitle.replace("\u2030", "");
                                    BigDecimal bigDecimal = new BigDecimal(cellValueTitle);
                                    cellValueTitle = bigDecimal.divide(perMilMultiplicand).toString();
                                }
                                catch (Exception bigDecimal) {}
                            } else if (cellValueTitle.contains("%")) {
                                try {
                                    cellValueTitle = cellValueTitle.replace("%", "");
                                    BigDecimal bigDecimal = new BigDecimal(cellValueTitle);
                                    cellValueTitle = bigDecimal.divide(perCentMultiplicand).toString();
                                }
                                catch (Exception bigDecimal) {
                                    // empty catch block
                                }
                            }
                            if (TableType.FLOAT == tableType || TableType.ACCOUNT == tableType || TableType.FMDM == tableType) {
                                zbs.add(zbId);
                                values.add(cellValueTitle);
                                List<DFDimensionValue> dimensionValues = modifyRow.getDimensionValues();
                                if (null == dimensionValues) {
                                    dimensionValues = new ArrayList<DFDimensionValue>();
                                    dimensionValues.add(masterDimensionValue);
                                    dimensionValues.add(periodDimensionValue);
                                    if (adjustDimensionValue != null) {
                                        dimensionValues.add(adjustDimensionValue);
                                    }
                                    if (!commonDimensionValues.isEmpty()) {
                                        dimensionValues.addAll(commonDimensionValues);
                                    }
                                    modifyRow.setDimensionValues(dimensionValues);
                                    modifyRow.setZbs(zbs);
                                    modifyRow.setValues(values);
                                }
                            } else {
                                modifyRow = new DataFillDataSaveRow();
                                zbs = new ArrayList();
                                zbs.add(zbId);
                                values = new ArrayList();
                                values.add(cellValueTitle);
                                ArrayList<DFDimensionValue> dimensionValues = new ArrayList<DFDimensionValue>();
                                dimensionValues.add(masterDimensionValue);
                                dimensionValues.add(periodDimensionValue);
                                if (adjustDimensionValue != null) {
                                    dimensionValues.add(adjustDimensionValue);
                                }
                                if (!commonDimensionValues.isEmpty()) {
                                    dimensionValues.addAll(commonDimensionValues);
                                }
                                modifyRow.setDimensionValues(dimensionValues);
                                modifyRow.setZbs(zbs);
                                modifyRow.setValues(values);
                                modifys.add(modifyRow);
                            }
                        } else {
                            ResultErrorInfo dimensionErrorInfo3;
                            DataFillSaveErrorDataInfo dimensionError;
                            this.printError(rowNum - 1, zbId, periodDimensionValue, masterDimensionValue, i + 1, cellValueTitle, errors);
                            Integer judgeMasterDimensionVisitedNum = rowNum;
                            Integer judgePeriodDimensionVisitedNum = rowNum;
                            if (isMasterOnCol.booleanValue()) {
                                judgeMasterDimensionVisitedNum = i;
                            }
                            if (isPeriodOnCol.booleanValue()) {
                                judgePeriodDimensionVisitedNum = i;
                            }
                            if (!masterDimensionErrorVisitedSet.contains(judgeMasterDimensionVisitedNum) && null == masterDimensionValue) {
                                errorMsg = NrDataFillI18nUtil.buildCode("nr.dataFill.mainDimenError");
                                dimensionError = new DataFillSaveErrorDataInfo();
                                dimensionErrorInfo3 = new ResultErrorInfo();
                                dimensionErrorInfo3.setErrorInfo(errorMsg);
                                dimensionErrorInfo3.setErrorCode(ErrorCode.DATAERROR);
                                dimensionError.setDataError(dimensionErrorInfo3);
                                if (isMasterOnCol.booleanValue()) {
                                    dimensionError.setErrorLocX(i + 1);
                                    dimensionError.setErrorLocY(1);
                                    masterDimensionErrorVisitedSet.add(i);
                                } else {
                                    dimensionError.setErrorLocX(masterDimensionCol);
                                    dimensionError.setErrorLocY(rowNum);
                                    masterDimensionErrorVisitedSet.add(rowNum);
                                }
                                dimensionErrorList.add(dimensionError);
                            } else if (!periodDimensionErrorRowSet.contains(judgePeriodDimensionVisitedNum) && null == periodDimensionValue) {
                                errorMsg = NrDataFillI18nUtil.buildCode("nr.dataFill.timeDimenError");
                                dimensionError = new DataFillSaveErrorDataInfo();
                                dimensionErrorInfo3 = new ResultErrorInfo();
                                dimensionErrorInfo3.setErrorInfo(errorMsg);
                                dimensionErrorInfo3.setErrorCode(ErrorCode.DATAERROR);
                                dimensionError.setDataError(dimensionErrorInfo3);
                                if (isPeriodOnCol.booleanValue()) {
                                    dimensionError.setErrorLocX(i + 1);
                                    dimensionError.setErrorLocY(1);
                                    periodDimensionErrorRowSet.add(i);
                                } else {
                                    dimensionError.setErrorLocX(periodDimensionCol);
                                    dimensionError.setErrorLocY(rowNum);
                                    periodDimensionErrorRowSet.add(rowNum);
                                }
                                dimensionErrorList.add(dimensionError);
                            }
                        }
                    }
                    if (masterDimensionValue != null && periodDimensionValue != null) {
                        valueXY.put(zbId + this.dfDimensionValueGetService.getValues(masterDimensionValue, context.getModel()) + this.dfDimensionValueGetService.getValues(periodDimensionValue, context.getModel()), new Point(i + 1, rowNum));
                    }
                    ++i;
                }
                if (TableType.FLOAT != tableType && TableType.ACCOUNT != tableType && TableType.FMDM != tableType) continue;
                modifys.add(modifyRow);
            }
            if (errors.isEmpty()) {
                DataFillDataSaveInfo saveInfo = new DataFillDataSaveInfo();
                saveInfo.setModifys(modifys);
                saveInfo.setContext(context);
                asyncTaskMonitor.progressAndMessage(0.5, NrDataFillI18nUtil.buildCode("nr.dataFill.dataSaveSubmit"));
                DataFillResult save = this.dataFillDataEnvService.save(saveInfo);
                if (save.isSuccess()) {
                    asyncTaskMonitor.finish("true", (Object)NrDataFillI18nUtil.buildCode("nr.dataFill.excelImportSucc"));
                } else {
                    String reportName = ((DataFrame)tables.get(0)).getName().split(".xlsx#")[0];
                    String sheetName = ((DataFrame)tables.get(0)).getName().split("xlsx#")[1];
                    if (save.getErrors() == null) {
                        save.setErrors(new ArrayList<DataFillSaveErrorDataInfo>());
                    }
                    if (save.getErrors().size() != 0) {
                        for (DataFillSaveErrorDataInfo eachError : save.getErrors()) {
                            Point p;
                            if (TableType.FIXED != tableType && TableType.MASTER != tableType || (p = (Point)valueXY.get(eachError.getZb() + this.dfDimensionValueGetService.getValues(eachError.getDimensionValues().get(0), context.getModel()) + this.dfDimensionValueGetService.getValues(eachError.getDimensionValues().get(1), context.getModel()))) == null) continue;
                            eachError.setErrorLocX(p.x);
                            eachError.setErrorLocY(p.y);
                        }
                    }
                    if (!dimensionErrorList.isEmpty()) {
                        save.getErrors().addAll(dimensionErrorList);
                    }
                    String errorMarkExcelPath = this.createErrorMarkExcel(save.getErrors(), tempFile, sheetName, reportName);
                    save.setErrorMarkExcelPath(errorMarkExcelPath);
                    asyncTaskMonitor.finish("false", (Object)save);
                    save.getErrors().forEach(each -> logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165Excel\u5bfc\u5165\u5931\u8d25\uff1b" + NrDataFillI18nUtil.parseMsg(each.getDataError().getErrorInfo())));
                }
            } else {
                DataFillResult res = new DataFillResult();
                res.setSuccess(false);
                res.setErrors(errors);
                String reportName = ((DataFrame)tables.get(0)).getName().split(".xlsx#")[0];
                String sheetName = ((DataFrame)tables.get(0)).getName().split("xlsx#")[1];
                String errorMarkExcelPath = this.createErrorMarkExcel(res.getErrors(), tempFile, sheetName, reportName);
                res.setErrorMarkExcelPath(errorMarkExcelPath);
                asyncTaskMonitor.finish("false", (Object)res);
                if (errors.size() > 0) {
                    logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165Excel\u5bfc\u5165\u5931\u8d25\uff1b" + ((DataFillSaveErrorDataInfo)errors.get(0)).getDataError().getErrorInfo());
                }
            }
        }
        catch (Exception e2) {
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u5bfc\u5165\u5f02\u5e38\uff01", e2);
            asyncTaskMonitor.error(NrDataFillI18nUtil.buildCode("nr.dataFill.customInputExcelError") + "\uff01", (Throwable)e2);
        }
    }

    private ImportResultExcelFileObject parseErrors(List<DataFillSaveErrorDataInfo> errors, File file, String sheetName, String reportName) {
        ImportResultExcelFileObject ret = new ImportResultExcelFileObject();
        ArrayList<ImportErrorDataInfo> importErrorDataInfoList = new ArrayList<ImportErrorDataInfo>();
        for (DataFillSaveErrorDataInfo eachError : errors) {
            ImportErrorDataInfo errorDataInfo = new ImportErrorDataInfo();
            if (eachError.getErrorLocX() != null && eachError.getErrorLocY() != null) {
                errorDataInfo.setExcelLocation(new Point(eachError.getErrorLocX().intValue(), eachError.getErrorLocY().intValue()));
            }
            ResultErrorInfo dataError = new ResultErrorInfo();
            BeanUtils.copyProperties(eachError.getDataError(), dataError);
            dataError.setErrorInfo(NrDataFillI18nUtil.parseMsg(eachError.getDataError().getErrorInfo()));
            errorDataInfo.setDataError(dataError);
            importErrorDataInfoList.add(errorDataInfo);
        }
        ArrayList<ImportResultRegionObject> importResultRegionObjectList = new ArrayList<ImportResultRegionObject>();
        ImportResultRegionObject importResultRegionObject = new ImportResultRegionObject();
        importResultRegionObject.setImportErrorDataInfoList(importErrorDataInfoList);
        importResultRegionObjectList.add(importResultRegionObject);
        ImportResultReportObject importResultReportObject = new ImportResultReportObject();
        importResultReportObject.setImportResultRegionObjectList(importResultRegionObjectList);
        importResultReportObject.setReportName(reportName);
        ArrayList<ImportResultSheetObject> importResultSheetObjectList = new ArrayList<ImportResultSheetObject>();
        ImportResultSheetObject importResultSheetObject = new ImportResultSheetObject();
        importResultSheetObject.setSheetName(sheetName);
        importResultSheetObject.setSheetError(null);
        importResultSheetObject.setImportResultReportObject(importResultReportObject);
        importResultSheetObjectList.add(importResultSheetObject);
        ret.setImportResultSheetObjectList(importResultSheetObjectList);
        ret.setFileName(file.getName());
        ret.setLocation(file.getPath());
        return ret;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String createErrorMarkExcel(List<DataFillSaveErrorDataInfo> errors, File fileTemp, String sheetName, String reportName) {
        ImportResultObject res = new ImportResultObject();
        res.setFails(new ArrayList());
        ImportResultExcelFileObject tempres = this.parseErrors(errors, fileTemp, sheetName, reportName);
        Workbook workbookError = null;
        try {
            Object object;
            res.setSuccess(false);
            workbookError = ExcelErrorUtil.exportExcel((ImportResultObject)res, (ImportResultExcelFileObject)tempres, (Workbook)ExcelImportUtil.create((File)fileTemp));
            String uid = UUID.randomUUID().toString();
            String filePath = fileTemp.getPath();
            String filePathNew = filePath.replace(fileTemp.getPath(), fileTemp.getPath() + "_\u9519\u8bef\u63d0\u793a.xlsx");
            File errorFile = new File(filePathNew);
            try {
                errorFile.getParentFile().mkdirs();
                object = errorFile.createNewFile();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            try (FileOutputStream out = new FileOutputStream(errorFile);){
                workbookError.write(out);
                out.flush();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            logger.info(filePathNew);
            object = filePathNew;
            return object;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (workbookError != null) {
                try {
                    workbookError.close();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        return null;
    }

    private void zbError(int rowNum, int col, String cellValueTitle, QueryField findZb, List<DataFillSaveErrorDataInfo> errors) {
        ResultErrorInfo dataError = new ResultErrorInfo();
        DataFillSaveErrorDataInfo dataFillSaveErrorDataInfo = new DataFillSaveErrorDataInfo();
        dataFillSaveErrorDataInfo.setDataError(dataError);
        dataError.setErrorCode(ErrorCode.DATAERROR);
        String error = NrDataFillI18nUtil.buildCode("nr.dataFill.cell") + ":" + NrDataFillI18nUtil.buildCode("nvwa.base.row") + "-" + rowNum + "," + NrDataFillI18nUtil.buildCode("nvwa.base.col") + "-" + col + NrDataFillI18nUtil.buildCode("nr.dataFill.val") + "\u3010" + cellValueTitle + "\u3011";
        if (null != findZb) {
            String alias = findZb.getAlias();
            if (!StringUtils.hasLength(alias)) {
                alias = findZb.getTitle();
            }
            error = error + NrDataFillI18nUtil.buildCode("nr.dataFill.shouldBe") + "\uff1a" + NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + "\u3010" + alias + "\u3011";
        } else {
            error = error + NrDataFillI18nUtil.buildCode("nr.dataFill.outOfTableRange");
        }
        logger.error(error);
        dataError.setErrorInfo(error);
        dataFillSaveErrorDataInfo.setErrorLocX(col);
        dataFillSaveErrorDataInfo.setErrorLocY(rowNum);
        errors.add(dataFillSaveErrorDataInfo);
    }

    private void printError(int rowNum, String zbId, DFDimensionValue periodDimensionValue, DFDimensionValue masterDimensionValue, int col, String cellValueTitle, List<DataFillSaveErrorDataInfo> errors) {
        if (!StringUtils.hasLength(zbId)) {
            return;
        }
        ResultErrorInfo dataError = new ResultErrorInfo();
        DataFillSaveErrorDataInfo dataFillSaveErrorDataInfo = new DataFillSaveErrorDataInfo();
        dataFillSaveErrorDataInfo.setZb(zbId);
        ArrayList<DFDimensionValue> dimensionValues = new ArrayList<DFDimensionValue>();
        dimensionValues.add(masterDimensionValue);
        dimensionValues.add(periodDimensionValue);
        dataFillSaveErrorDataInfo.setDimensionValues(dimensionValues);
        dataFillSaveErrorDataInfo.setValue((Serializable)((Object)cellValueTitle));
        dataFillSaveErrorDataInfo.setDataError(dataError);
        dataError.setErrorCode(ErrorCode.DATAERROR);
        if (!StringUtils.hasLength(zbId)) {
            String error = NrDataFillI18nUtil.buildCode("nr.dataFill.cell") + ":" + NrDataFillI18nUtil.buildCode("nvwa.base.row") + "-" + rowNum + NrDataFillI18nUtil.buildCode("nvwa.base.col") + "-" + NrDataFillI18nUtil.buildCode("nr.dataFill.val") + "\u3010" + cellValueTitle + "\u3011" + NrDataFillI18nUtil.buildCode("nr.dataFill.lackZb");
            logger.error(error);
            dataError.setErrorInfo(error);
        } else if (null == periodDimensionValue) {
            String error = NrDataFillI18nUtil.buildCode("nr.dataFill.cell") + ":" + NrDataFillI18nUtil.buildCode("nvwa.base.row") + "-" + rowNum + NrDataFillI18nUtil.buildCode("nvwa.base.col") + "-" + NrDataFillI18nUtil.buildCode("nr.dataFill.val") + "\u3010" + cellValueTitle + "\u3011" + NrDataFillI18nUtil.buildCode("nr.dataFill.lackPeriod");
            dataError.setErrorInfo(error);
        } else if (null == masterDimensionValue) {
            String error = NrDataFillI18nUtil.buildCode("nr.dataFill.cell") + ":" + NrDataFillI18nUtil.buildCode("nvwa.base.row") + "-" + rowNum + NrDataFillI18nUtil.buildCode("nvwa.base.col") + "-" + NrDataFillI18nUtil.buildCode("nr.dataFill.val") + "\u3010" + cellValueTitle + "\u3011" + NrDataFillI18nUtil.buildCode("nr.dataFill.lackMainDimension");
            logger.error(error);
            dataError.setErrorInfo(error);
        }
        dataFillSaveErrorDataInfo.setErrorLocX(col);
        dataFillSaveErrorDataInfo.setErrorLocY(rowNum + 1);
        errors.add(dataFillSaveErrorDataInfo);
    }

    private String findMaster(QueryField master, String title, int row, int col, QueryField period, String periodValue, Map<String, String> cacheMap) {
        String codeOrTitle;
        int n;
        String[] codeAndTitles;
        String[] splits = title.split("\\#\\^\\$");
        title = splits[0];
        String key = (StringUtils.hasLength(periodValue) ? periodValue : "") + ";" + title;
        if (cacheMap.containsKey(key)) {
            return cacheMap.get(key);
        }
        DimensionValueSet newValueSet = new DimensionValueSet();
        String sqlDimensionName = this.dFDimensionParser.getDimensionNameByField(master);
        newValueSet.setValue(sqlDimensionName, null);
        if (null != period && StringUtils.hasLength(periodValue)) {
            String sqlPeriodDimensionName = this.dFDimensionParser.getDimensionNameByField(period);
            newValueSet.setValue(sqlPeriodDimensionName, (Object)periodValue);
        }
        IEntityRow entityRow = null;
        String[] stringArray = codeAndTitles = title.split("\\|");
        int n2 = stringArray.length;
        for (n = 0; n < n2 && null == (entityRow = this.searchMaster(codeOrTitle = stringArray[n], true, false, newValueSet, master, period)); ++n) {
        }
        if (null == entityRow) {
            stringArray = codeAndTitles;
            n2 = stringArray.length;
            for (n = 0; n < n2 && null == (entityRow = this.searchMaster(codeOrTitle = stringArray[n], false, false, newValueSet, master, period)); ++n) {
            }
            if (null == entityRow) {
                stringArray = codeAndTitles;
                n2 = stringArray.length;
                for (n = 0; n < n2 && null == (entityRow = this.searchMaster(codeOrTitle = stringArray[n], false, true, newValueSet, master, period)); ++n) {
                }
            }
        }
        if (null != entityRow) {
            String code = entityRow.getEntityKeyData();
            cacheMap.put(key, code);
            return code;
        }
        logger.error("\u4e3b\u7ef4\u5ea6:\u884c\uff1a\u3010" + row + "\u3011\u5217\uff1a\u3010" + col + "\u3011\u503c\u3010" + title + "\u3011\u672a\u627e\u5230");
        cacheMap.put(key, null);
        return null;
    }

    private IEntityRow searchMaster(String search, boolean isCode, boolean isLike, DimensionValueSet newValueSet, QueryField master, QueryField period) {
        ExecutorContext executorContext = new ExecutorContext(this.runtimeCtrl);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setMasterKeys(newValueSet);
        String rowFilterExpression = "";
        rowFilterExpression = isCode ? " " + master.getTableCode() + "[CODE]= '" + search + "'" : (isLike ? " " + master.getTableCode() + "[NAME] LIKE '%" + search + "%'" : " " + master.getTableCode() + "[NAME]= '" + search + "'");
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(master.getId(), rowFilterExpression);
        iEntityQuery.setEntityView(entityView);
        executorContext.setPeriodView(period.getId());
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = iEntityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new DataFillRuntimeException("\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u5b9e\u4f53\u8868\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        List allRows = iEntityTable.getAllRows();
        if (null != allRows && allRows.size() > 0) {
            return (IEntityRow)allRows.get(0);
        }
        return null;
    }

    private String findPeriod(QueryField period, String cellValueTitle, int row, int col, Map<String, String> cacheMap) {
        if (cellValueTitle.contains("#")) {
            cellValueTitle = cellValueTitle.split("#")[0];
        }
        if (cacheMap.containsKey(cellValueTitle)) {
            return cacheMap.get(cellValueTitle);
        }
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(period.getId());
        if (periodEntity.getPeriodType() != PeriodType.CUSTOM) {
            try {
                PeriodWrapper periodWrapper = new PeriodWrapper();
                periodWrapper.parseTitleString(cellValueTitle);
                String code = periodWrapper.toString();
                cacheMap.put(cellValueTitle, code);
                return code;
            }
            catch (IllegalArgumentException e2) {
                try {
                    Double value = new Double(cellValueTitle);
                    Date javaDate = DateUtil.getJavaDate(value);
                    GregorianCalendar currentCalendar = PeriodUtil.getCurrentCalendar((Date)javaDate);
                    String periodValue = PeriodUtil.currentPeriod((GregorianCalendar)currentCalendar, (int)periodEntity.getType().type(), (int)0).toString();
                    cacheMap.put(cellValueTitle, periodValue);
                    return periodValue;
                }
                catch (NumberFormatException value) {
                    logger.error("\u65f6\u671f:\u884c\uff1a\u3010" + row + "\u3011\u5217\uff1a\u3010" + col + "\u3011\u503c\u3010" + cellValueTitle + "\u3011\u672a\u627e\u5230");
                }
            }
        } else {
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(period.getId());
            List periodItems = periodProvider.getPeriodItems();
            String finalCellValueTitle = cellValueTitle;
            Optional<IPeriodRow> findPeriod = periodItems.stream().filter(e -> finalCellValueTitle.equals(e.getAlias()) || finalCellValueTitle.equals(e.getTitle())).findFirst();
            if (findPeriod.isPresent()) {
                IPeriodRow periodRow = findPeriod.get();
                String code = periodRow.getCode();
                cacheMap.put(cellValueTitle, code);
                return code;
            }
            logger.error("\u65f6\u671f:\u884c\uff1a\u3010" + row + "\u3011\u5217\uff1a\u3010" + col + "\u3011\u503c\u3010" + cellValueTitle + "\u3011\u672a\u627e\u5230");
        }
        cacheMap.put(cellValueTitle, null);
        return null;
    }

    private String findAdjustCode(String dataSchemeCode, String cellValueTitle, DFDimensionValue periodDimensionValue, int row, int col, Map<String, String> cacheMap) {
        if ("\u4e0d\u8c03\u6574".equals(cellValueTitle.trim())) {
            return "0";
        }
        String key = cellValueTitle + periodDimensionValue.getValues();
        if (cacheMap.containsKey(key)) {
            return cacheMap.get(key);
        }
        DataScheme scheme = this.runtimeDataSchemeService.getDataSchemeByCode(dataSchemeCode);
        List adjustPeriodList = this.adjustPeriodDesignService.query(scheme.getKey());
        Map<String, String> adjustPeriodMap = adjustPeriodList.stream().collect(Collectors.toMap(obj -> obj.getTitle() + obj.getPeriod(), AdjustPeriodDO::getCode));
        cacheMap.putAll(adjustPeriodMap);
        if (cacheMap.containsKey(key)) {
            return cacheMap.get(key);
        }
        logger.error("\u8c03\u6574\u671f: \u884c\uff1a\u3010" + row + "\u3011\u5217\uff1a\u3010" + col + "\u3011\u503c\u3010" + cellValueTitle + "\u3011\u672a\u627e\u5230");
        cacheMap.put(key, null);
        return null;
    }

    private QueryField findZb(String cellValueTitle, int row, int col, QueryField mustMatching, DataFillContext context) {
        if (null != mustMatching) {
            String[] split2;
            String alias = mustMatching.getAlias();
            if (!StringUtils.hasLength(alias)) {
                alias = mustMatching.getTitle();
            }
            if (alias.equals(cellValueTitle)) {
                return mustMatching;
            }
            String[] split = cellValueTitle.split("\\#\\^\\$");
            if (alias.equals(split[0]) || alias.trim().equals(cellValueTitle)) {
                return mustMatching;
            }
            if (cellValueTitle.contains("\n") && (alias.equals((split2 = cellValueTitle.split("\n"))[0]) || alias.trim().equals(split2[0].trim()))) {
                return mustMatching;
            }
        }
        return null;
    }

    public class RowCol {
        private int row;
        private int col;
        private String title;

        public int getRow() {
            return this.row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return this.col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

