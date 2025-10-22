/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilder
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datareport.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datareport.helper.Grid2DataSetValueUtil;
import com.jiuqi.nr.datareport.obj.RegionNumber;
import com.jiuqi.nr.datareport.obj.RegionNumberManager;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.grid2.graphics.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class GridDataHelper {
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private DataValueFormatterBuilderFactory builderFactory;
    private static final Logger logger = LoggerFactory.getLogger(GridDataHelper.class);

    public Grid2Data getGridData(String formKey) {
        BigDataDefine formDefine = this.runtimeViewController.getReportDataFromForm(formKey);
        if (null != formDefine) {
            if (formDefine.getData() != null) {
                Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])formDefine.getData());
                String frontScriptFromForm = this.runtimeViewController.getFrontScriptFromForm(formDefine.getKey());
                if (StringUtils.isNotEmpty((String)frontScriptFromForm)) {
                    bytesToGrid.setScript(frontScriptFromForm);
                }
                return bytesToGrid;
            }
            Grid2Data griddata = new Grid2Data();
            griddata.setRowCount(10);
            griddata.setColumnCount(10);
            return griddata;
        }
        return null;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public Grid2Data fillGrid2Dta(String formKey, String formulaSchemeKey, DimensionCombination dimensionCombination, Grid2Data grid2Data) {
        try {
            List<DataRegionDefine> allRegionsInForm = this.runtimeViewController.getAllRegionsInForm(formKey);
            if (CollectionUtils.isEmpty(allRegionsInForm)) {
                return grid2Data;
            }
            allRegionsInForm = this.orderRegions(allRegionsInForm);
            int tempLine = 0;
            HashMap<String, Integer> addRows = new HashMap<String, Integer>();
            boolean emptyTable = true;
            ArrayList<String> fileGroupKeys = new ArrayList<String>();
            try {
                for (DataRegionDefine region : allRegionsInForm) {
                    IRegionDataSet regionDataSet;
                    QueryInfoBuilder queryInfoBuilder = new QueryInfoBuilder(region.getKey(), dimensionCombination);
                    queryInfoBuilder.setDesensitized(true);
                    IQueryInfo queryInfo = queryInfoBuilder.build();
                    if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                        regionDataSet = this.dataQueryService.queryRegionData(queryInfo);
                        boolean singleEmpty = this.exportFixRegion(regionDataSet, grid2Data, fileGroupKeys);
                        if (singleEmpty) continue;
                        emptyTable = false;
                        continue;
                    }
                    regionDataSet = this.dataQueryService.queryRegionData(queryInfo);
                    int eline = this.exportFloatRegion(regionDataSet, region, grid2Data, tempLine, addRows, fileGroupKeys);
                    if (eline == -1) continue;
                    tempLine = eline;
                    emptyTable = false;
                }
                if (emptyTable) {
                    return grid2Data;
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return grid2Data;
    }

    private List<DataRegionDefine> orderRegions(List<DataRegionDefine> dataRegions) {
        ArrayList<DataRegionDefine> fixRegions = new ArrayList<DataRegionDefine>();
        ArrayList<DataRegionDefine> floatRegions = new ArrayList<DataRegionDefine>();
        for (DataRegionDefine regionData : dataRegions) {
            if (DataRegionKind.DATA_REGION_SIMPLE == regionData.getRegionKind()) {
                fixRegions.add(regionData);
                continue;
            }
            floatRegions.add(regionData);
        }
        if (floatRegions.size() > 0 && ((DataRegionDefine)floatRegions.get(0)).getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST) {
            floatRegions.sort(Comparator.comparingInt(DataRegionDefine::getRegionLeft));
        } else {
            floatRegions.sort(Comparator.comparingInt(DataRegionDefine::getRegionTop));
        }
        fixRegions.addAll(floatRegions);
        return fixRegions;
    }

    private boolean exportFixRegion(IRegionDataSet regionDataSet, Grid2Data gridData, List<String> fileGroupKeys) {
        boolean emptyTable;
        if (regionDataSet == null) {
            return true;
        }
        List<Object> fixData = new ArrayList<IDataValue>();
        List rowData = regionDataSet.getRowData();
        if (!CollectionUtils.isEmpty(rowData)) {
            fixData = ((IRowData)rowData.get(0)).getLinkDataValues();
        }
        if (emptyTable = this.isEmptyTable(fixData)) {
            return true;
        }
        for (IDataValue iDataValue : fixData) {
            IMetaData metaData;
            DataLinkDefine dataLinkDefine;
            if (iDataValue == null || (dataLinkDefine = (metaData = iDataValue.getMetaData()).getDataLinkDefine()) == null) continue;
            DataField dataField = metaData.getDataField();
            GridCellData cell = gridData.getGridCellData(dataLinkDefine.getPosX(), dataLinkDefine.getPosY());
            if (cell != null) {
                DataValueFormatterBuilder formatterBuilder = this.builderFactory.createFormatterBuilder();
                DataValueFormatter dataValueFormatter = formatterBuilder.build();
                Grid2DataSetValueUtil.converterFieldTypeToGridCellData(dataValueFormatter, cell, iDataValue);
            }
            if (dataField == null || dataField.getDataFieldType() != DataFieldType.FILE && dataField.getDataFieldType() != DataFieldType.PICTURE || iDataValue.getAsNull() || !StringUtils.isNotEmpty((String)iDataValue.getAsString())) continue;
            fileGroupKeys.add(iDataValue.getAsString());
        }
        return emptyTable;
    }

    private int exportFloatRegion(IRegionDataSet regionDataSet, DataRegionDefine region, Grid2Data gridData, int tempLine, Map<String, Integer> addRows, List<String> fileGroupKeys) {
        int lineInfoNow;
        List rowData = regionDataSet.getRowData();
        boolean colFloat = region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST;
        int lineInfo = colFloat ? region.getRegionLeft() : region.getRegionTop() + tempLine;
        RegionNumberManager regionNumberManager = null;
        RegionSettingDefine regionSetting = this.runtimeViewController.getRegionSetting(region.getKey());
        List rowNumberSetting = regionSetting.getRowNumberSetting();
        if (null != rowNumberSetting && rowNumberSetting.size() > 0) {
            RegionNumber regionNumber = new RegionNumber((RowNumberSetting)rowNumberSetting.get(0));
            regionNumberManager = new RegionNumberManager(regionNumber);
        }
        if ((lineInfoNow = colFloat ? this.setDataMethod2(regionNumberManager, regionDataSet, region, lineInfo, gridData, tempLine, addRows, fileGroupKeys) : this.setDataMethod(regionNumberManager, regionDataSet, region, lineInfo, gridData, tempLine, addRows, fileGroupKeys)) == -1) {
            return -1;
        }
        return tempLine += lineInfoNow - lineInfo;
    }

    private int setDataMethod(RegionNumberManager regionNumberManager, IRegionDataSet regionDataSet, DataRegionDefine region, int lineInfo, Grid2Data gridData, int tempLine, Map<String, Integer> addRows, List<String> fileGroupKeys) {
        Grid2Data tempGridData;
        Grid2FieldList merges;
        int regionTop = region.getRegionTop();
        int regionBottom = region.getRegionBottom();
        int moreRow = regionBottom - regionTop;
        boolean deleteRow = true;
        boolean emptyTable = true;
        HashSet<String> linkCells = new HashSet<String>();
        List rowData = regionDataSet.getRowData();
        if (!CollectionUtils.isEmpty(rowData)) {
            for (IRowData data : rowData) {
                emptyTable = this.isEmptyTable(data.getLinkDataValues());
            }
        }
        if (emptyTable) {
            return -1;
        }
        int dataRowCount = rowData.size();
        int gridAddRows = dataRowCount * (moreRow + 1);
        if (dataRowCount > 0) {
            int headerRowCount = gridData.getHeaderRowCount();
            gridData.insertRows(lineInfo, gridAddRows, lineInfo, true);
            if (moreRow > 0) {
                int yy = (moreRow + 1) * dataRowCount + lineInfo;
                for (int i = 0; i < dataRowCount; ++i) {
                    gridData.copyFrom(gridData, 0, yy, gridData.getColumnCount() - 1, yy + moreRow, 0, i * (moreRow + 1) + lineInfo);
                }
            }
            if (lineInfo == headerRowCount) {
                gridData.setHeaderRowCount(headerRowCount);
            }
            int gridDataShowRow = moreRow + 1;
            for (int x = 0; x < gridDataShowRow; ++x) {
                int localGridShowRow = lineInfo + gridAddRows + x;
                int tempLineInfo = lineInfo + x;
                int y = 0;
                while (tempLineInfo < lineInfo + gridAddRows) {
                    for (int col = 0; col < gridData.getColumnCount(); ++col) {
                        GridCellData gridcell = gridData.getGridCellData(col, localGridShowRow);
                        String showText = gridcell.getShowText();
                        if (null == showText || "".equals(showText)) continue;
                        gridcell = gridData.getGridCellData(col, tempLineInfo);
                        gridcell.setShowText(showText);
                    }
                    tempLineInfo = tempLineInfo + 1 + moreRow;
                    ++y;
                }
            }
        } else {
            dataRowCount = 1;
            for (IMetaData metaDatum : regionDataSet.getMetaData()) {
                GridCellData gridcell;
                DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                if (dataLinkDefine == null || null == (gridcell = gridData.getGridCellData(dataLinkDefine.getPosX(), lineInfo))) continue;
                gridcell.setShowText("");
                gridcell.setEditText("");
            }
        }
        addRows.put(region.getKey(), dataRowCount * (moreRow + 1));
        Map<String, String> enumPosMap = this.getEnumPosMap(regionDataSet.getMetaData());
        int i = 0;
        while (i < dataRowCount) {
            IRowData rowDatum = (IRowData)rowData.get(i);
            List linkDataValues = rowDatum.getLinkDataValues();
            for (IDataValue linkDataValue : linkDataValues) {
                DataField dataField = linkDataValue.getMetaData().getDataField();
                DataLinkDefine dataLinkDefine = linkDataValue.getMetaData().getDataLinkDefine();
                HashMap mergeCell = new HashMap();
                if (dataLinkDefine == null) continue;
                int temp = dataLinkDefine.getPosY() + i * (moreRow + 1) + tempLine;
                if (temp > lineInfo) {
                    lineInfo = temp;
                }
                GridCellData gridcell = gridData.getGridCellData(dataLinkDefine.getPosX(), temp);
                String posStr = this.getPosition(dataLinkDefine.getPosX(), temp);
                linkCells.add(posStr);
                if (gridcell == null) continue;
                if (mergeCell.containsKey(dataLinkDefine.getKey())) {
                    List mergeCellInfo = (List)mergeCell.get(dataLinkDefine.getKey());
                    for (List merge : mergeCellInfo) {
                        if (merge.size() != 2) continue;
                        int top = (Integer)merge.get(0);
                        int bottom = (Integer)merge.get(1);
                        if (top != i) continue;
                        int tempBottom = dataLinkDefine.getPosY() + bottom * (moreRow + 1) + tempLine;
                        gridData.mergeCells(dataLinkDefine.getPosX(), temp, dataLinkDefine.getPosX(), tempBottom);
                    }
                }
                DataValueFormatterBuilder formatterBuilder = this.builderFactory.createFormatterBuilder();
                DataValueFormatter dataValueFormatter = formatterBuilder.build();
                Grid2DataSetValueUtil.converterFieldTypeToGridCellData(dataValueFormatter, gridcell, linkDataValue);
                if (dataField == null || dataField.getDataFieldType() != DataFieldType.FILE && dataField.getDataFieldType() != DataFieldType.PICTURE || linkDataValue.getAsNull() || !StringUtils.isNotEmpty((String)linkDataValue.getAsString())) continue;
                fileGroupKeys.add(linkDataValue.getAsString());
            }
            if (regionNumberManager != null) {
                GridCellData gridcell;
                regionNumberManager.setNumberStr("");
                if (null != regionNumberManager.getRegionNumber() && (gridcell = gridData.getGridCellData(regionNumberManager.getRegionNumber().getColumn(), lineInfo - moreRow)) != null) {
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridcell, regionNumberManager.next());
                }
            }
            ++i;
            ++lineInfo;
        }
        RunTimeExtentStyleService extentStyleService = (RunTimeExtentStyleService)BeanUtil.getBean(RunTimeExtentStyleService.class);
        ExtentStyle extentStyle = extentStyleService.getExtentStyle(region.getKey());
        if (extentStyle != null && (merges = (tempGridData = extentStyle.getGriddata()).merges()).count() > 0) {
            int regionLeft = region.getRegionLeft();
            for (int i2 = 0; i2 < merges.count(); ++i2) {
                Grid2CellField cell = merges.get(i2);
                gridData.merges().addMergeRect(merges.get(i2));
                for (int j = cell.left; j <= cell.right; ++j) {
                    for (int h = cell.top; h <= cell.bottom; ++h) {
                        int row = h + regionTop - 1;
                        int col = j + regionLeft - 1;
                        GridCellData cellData = gridData.getGridCellData(col, row);
                        GridCellData tempCellData = tempGridData.getGridCellData(j, h);
                        String posStr = this.getPosition(col, row);
                        if (!linkCells.contains(posStr)) {
                            cellData.setShowText(StringUtils.isNotEmpty((String)tempCellData.getShowText()) ? tempCellData.getShowText() : "");
                            cellData.setEditText(StringUtils.isNotEmpty((String)tempCellData.getEditText()) ? tempCellData.getEditText() : "");
                        }
                        GridCellStyleData cellStyleData = tempCellData.getCellStyleData();
                        cellData.setCellStyleData(cellStyleData);
                    }
                }
            }
        }
        if (regionDataSet.getRowCount() == 0) {
            --lineInfo;
            deleteRow = false;
        }
        if (moreRow > 0 && deleteRow) {
            gridData.deleteRows(lineInfo, moreRow + 1);
            gridData.insertRows(lineInfo, 1);
            Grid2FieldList merges2 = gridData.merges();
            ArrayList<Grid2CellField> removeList = new ArrayList<Grid2CellField>();
            for (int i3 = 0; i3 < merges2.count(); ++i3) {
                Grid2CellField grid2CellField = merges2.get(i3);
                if (null == grid2CellField || grid2CellField.top < grid2CellField.bottom || grid2CellField.left < grid2CellField.right) continue;
                removeList.add(grid2CellField);
            }
            for (Grid2CellField grid2CellField : removeList) {
                merges2.remove(grid2CellField);
            }
        }
        if (regionDataSet.getRowCount() > 0) {
            gridData.deleteRows(lineInfo, 1);
            --lineInfo;
        }
        return lineInfo;
    }

    private Map<String, String> getEnumPosMap(List<IMetaData> linkDataList) {
        HashMap<String, String> result = new HashMap<String, String>();
        for (IMetaData linkData : linkDataList) {
            Map enumPosMap;
            boolean linkInput;
            DataLinkDefine dataLinkDefine = linkData.getDataLinkDefine();
            if (dataLinkDefine == null) continue;
            DataLinkEditMode editMode = dataLinkDefine.getEditMode();
            boolean bl = linkInput = editMode == DataLinkEditMode.DATA_LINK_INPUT;
            if (!StringUtils.isNotEmpty((String)linkData.getDataField().getRefDataEntityKey()) || linkInput || CollectionUtils.isEmpty(enumPosMap = dataLinkDefine.getEnumPosMap())) continue;
            for (Map.Entry entry : enumPosMap.entrySet()) {
                String enumPos = this.getPosStr(entry.getValue().toString());
                result.put(enumPos, entry.getValue().toString());
            }
        }
        for (IMetaData linkData : linkDataList) {
            if (linkData.getDataLinkDefine() == null) continue;
            String position = this.getPosition(linkData.getDataLinkDefine().getPosX(), linkData.getDataLinkDefine().getPosY());
            result.remove(position);
        }
        return result;
    }

    private String getPosStr(String position) {
        String[] rows;
        String[] englishs = position.split("\\d");
        StringBuilder english = new StringBuilder();
        for (String n : englishs) {
            english.append(n);
        }
        StringBuilder relationRowString = new StringBuilder();
        for (String r : rows = position.split("\\D")) {
            relationRowString.append(r);
        }
        int relationRow = Integer.parseInt(relationRowString.toString());
        int relationCol = Grid2DataSetValueUtil.excelColStrToNum(english.toString(), english.length());
        return this.getPosition(relationCol, relationRow);
    }

    private String getPosition(int col, int row) {
        String posStr = String.valueOf(row);
        return posStr.concat("_").concat(String.valueOf(col));
    }

    private boolean isEmptyTable(List<IDataValue> data) {
        if (CollectionUtils.isEmpty(data)) {
            return true;
        }
        boolean emptyTable = true;
        for (IDataValue fixData : data) {
            if (!emptyTable) break;
            if (fixData == null || fixData.getAsNull()) continue;
            if (fixData.getAsObject() instanceof String) {
                emptyTable = StringUtils.isEmpty((String)fixData.toString());
                continue;
            }
            if (fixData.getAsObject() == null) continue;
            emptyTable = false;
        }
        return emptyTable;
    }

    private int setDataMethod2(RegionNumberManager regionNumberManager, IRegionDataSet regionDataSet, DataRegionDefine region, int lineInfo, Grid2Data gridData, int tempLine, Map<String, Integer> addRows, List<String> fileGroupKeys) {
        int regionLeft = region.getRegionLeft();
        int regionRight = region.getRegionRight();
        int moreCol = regionRight - regionLeft;
        boolean deleteRow = true;
        boolean emptyTable = true;
        HashSet linkCells = new HashSet();
        List rowData = regionDataSet.getRowData();
        if (!CollectionUtils.isEmpty(rowData)) {
            for (IRowData data : rowData) {
                emptyTable = this.isEmptyTable(data.getLinkDataValues());
            }
        }
        if (emptyTable) {
            return -1;
        }
        int dataRowCount = rowData.size();
        int gridAddRows = dataRowCount * (moreCol + 1);
        if (dataRowCount > 0) {
            int headerRowCount = gridData.getHeaderRowCount();
            GridCellData gridCellData = gridData.getGridCellData(lineInfo, 1);
            gridData.insertColumns(lineInfo, dataRowCount - 1, lineInfo);
            for (int i = 0; i < dataRowCount - 1; ++i) {
                gridData.getGridCellData(lineInfo + i, 1).setShowText(gridCellData.getShowText());
            }
            if (lineInfo == headerRowCount) {
                gridData.setHeaderRowCount(headerRowCount);
            }
            Grid2FieldList merges = gridData.merges();
            int gridDataShowRow = moreCol + 1;
            for (int x = 0; x < gridDataShowRow; ++x) {
                int tempLineInfo = lineInfo + x;
                int y = 0;
                while (tempLineInfo < lineInfo + gridAddRows) {
                    for (int col = 0; col < regionDataSet.getMetaData().size(); ++col) {
                        GridCellData gridcell = gridData.getGridCellData(lineInfo, col);
                        if (moreCol <= 0 || !gridcell.isMerged()) continue;
                        Point mergeInfo = gridcell.getMergeInfo();
                        int left = mergeInfo.x - gridAddRows + y * (moreCol + 1);
                        int bottom = gridcell.getColIndex() - gridAddRows + y * (moreCol + 1);
                        if (bottom - left <= 1) continue;
                        merges.addMergeRect(new Grid2CellField(left, mergeInfo.y, gridcell.getColIndex(), bottom));
                    }
                    tempLineInfo = tempLineInfo + 1 + moreCol;
                    ++y;
                }
            }
        } else {
            dataRowCount = 1;
            for (IMetaData metaDatum : regionDataSet.getMetaData()) {
                GridCellData gridcell;
                DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                if (dataLinkDefine == null || null == (gridcell = gridData.getGridCellData(dataLinkDefine.getPosX(), lineInfo))) continue;
                gridcell.setShowText("");
                gridcell.setEditText("");
            }
        }
        addRows.put(region.getKey(), dataRowCount * (moreCol + 1));
        Map<String, String> enumPosMap = this.getEnumPosMap(regionDataSet.getMetaData());
        int rowNum = lineInfo;
        int i = 0;
        while (i < dataRowCount) {
            IRowData rowDatum = (IRowData)rowData.get(i);
            List linkDataValues = rowDatum.getLinkDataValues();
            for (IDataValue linkDataValue : linkDataValues) {
                DataField dataField = linkDataValue.getMetaData().getDataField();
                DataLinkDefine dataLinkDefine = linkDataValue.getMetaData().getDataLinkDefine();
                if (dataLinkDefine == null) continue;
                lineInfo = dataLinkDefine.getPosX() + i * (moreCol + 1) + tempLine;
                GridCellData gridcell = gridData.getGridCellData(rowNum, dataLinkDefine.getPosY());
                if (gridcell == null) continue;
                DataValueFormatterBuilder formatterBuilder = this.builderFactory.createFormatterBuilder();
                DataValueFormatter dataValueFormatter = formatterBuilder.build();
                Grid2DataSetValueUtil.converterFieldTypeToGridCellData(dataValueFormatter, gridcell, linkDataValue);
                if (dataField == null || dataField.getDataFieldType() != DataFieldType.FILE && dataField.getDataFieldType() != DataFieldType.PICTURE || linkDataValue.getAsNull() || !StringUtils.isNotEmpty((String)linkDataValue.getAsString())) continue;
                fileGroupKeys.add(linkDataValue.getAsString());
            }
            if (regionNumberManager != null) {
                GridCellData gridcell;
                regionNumberManager.setNumberStr("");
                if (null != regionNumberManager.getRegionNumber() && (gridcell = gridData.getGridCellData(lineInfo, regionNumberManager.getRegionNumber().getRow())) != null) {
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridcell, regionNumberManager.next());
                }
            }
            ++i;
            ++lineInfo;
        }
        if (regionDataSet.getRowCount() == 0) {
            --lineInfo;
            deleteRow = false;
        }
        if (moreCol > 0 && deleteRow) {
            gridData.deleteRows(lineInfo, moreCol + 1);
            Grid2FieldList merges = gridData.merges();
            ArrayList<Grid2CellField> removeList = new ArrayList<Grid2CellField>();
            for (int i2 = 0; i2 < merges.count(); ++i2) {
                Grid2CellField grid2CellField = merges.get(i2);
                if (null == grid2CellField || grid2CellField.top < grid2CellField.bottom || grid2CellField.left < grid2CellField.right) continue;
                removeList.add(grid2CellField);
            }
            for (Grid2CellField grid2CellField : removeList) {
                merges.remove(grid2CellField);
            }
        }
        return lineInfo;
    }
}

