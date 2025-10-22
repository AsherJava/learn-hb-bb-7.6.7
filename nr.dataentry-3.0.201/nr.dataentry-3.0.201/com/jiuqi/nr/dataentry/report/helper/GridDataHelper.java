/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.jtable.common.LinkType
 *  com.jiuqi.nr.jtable.dataset.IRegionExportDataSet
 *  com.jiuqi.nr.jtable.dataset.IReportExportDataSet
 *  com.jiuqi.nr.jtable.dataset.impl.RegionExportDataSetImpl
 *  com.jiuqi.nr.jtable.dataset.impl.RegionNumberManager
 *  com.jiuqi.nr.jtable.params.base.EnumLinkData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.output.RegionDataSet
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.report.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.dataentry.util.Grid2DataSetValueUtil;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.dataset.IRegionExportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.dataset.impl.RegionExportDataSetImpl;
import com.jiuqi.nr.jtable.dataset.impl.RegionNumberManager;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.grid2.graphics.Point;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
    private IJtableResourceService jtableResourceService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
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
    public Grid2Data fillGrid2Dta(JtableContext jtableContext, Grid2Data grid2Data) {
        try {
            IReportExportDataSet reportExportData = this.jtableResourceService.getReportExportData(jtableContext);
            List<RegionData> dataRegions = reportExportData.getDataRegionList();
            if (CollectionUtils.isEmpty(dataRegions)) {
                return null;
            }
            dataRegions = this.orderRegions(dataRegions);
            int tempLine = 0;
            HashMap<String, Integer> addRows = new HashMap<String, Integer>();
            boolean emptyTable = true;
            ArrayList<String> fileGroupKeys = new ArrayList<String>();
            try {
                for (RegionData region : dataRegions) {
                    int eline;
                    if (region.getType() == 0) {
                        IRegionExportDataSet dataSet = reportExportData.getRegionExportDataSet(region);
                        boolean singleEmpty = this.exportFixRegion(jtableContext, dataSet, grid2Data, fileGroupKeys);
                        if (singleEmpty) continue;
                        emptyTable = false;
                        continue;
                    }
                    PagerInfo pagerInfo = new PagerInfo();
                    pagerInfo.setLimit(20000);
                    if (pagerInfo.getOffset() == 0 || pagerInfo.getTotal() == Integer.MAX_VALUE) {
                        pagerInfo.setOffset(-1);
                        pagerInfo.setTotal(0);
                    }
                    if ((eline = this.exportFloatRegion(jtableContext, reportExportData.getRegionExportDataSet(region, true, pagerInfo, false), grid2Data, tempLine, addRows, pagerInfo, fileGroupKeys)) == -1) continue;
                    tempLine = eline;
                    emptyTable = false;
                }
                if (emptyTable) {
                    return null;
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

    private List<RegionData> orderRegions(List<RegionData> dataRegions) {
        ArrayList<RegionData> fixRegins = new ArrayList<RegionData>();
        ArrayList<RegionData> floatRegins = new ArrayList<RegionData>();
        for (RegionData regionData : dataRegions) {
            if (regionData.getType() == 0) {
                fixRegins.add(regionData);
                continue;
            }
            floatRegins.add(regionData);
        }
        if (floatRegins.size() > 0 && ((RegionData)floatRegins.get(0)).getType() == 2) {
            floatRegins.sort(Comparator.comparingInt(RegionData::getRegionLeft));
        } else {
            floatRegins.sort(Comparator.comparingInt(RegionData::getRegionTop));
        }
        fixRegins.addAll(floatRegins);
        return fixRegins;
    }

    public boolean exportFixRegion(JtableContext jtableContext, IRegionExportDataSet fixRegionDataSet, Grid2Data gridData, List<String> fileGroupKeys) {
        boolean emptyTable;
        MemoryDataSet dataRowSet;
        if (fixRegionDataSet == null) {
            return true;
        }
        List fieldDefines = fixRegionDataSet.getFieldDataList();
        List dataLinkDefines = fixRegionDataSet.getLinkDataList();
        Object[] fixDatas = null;
        block0: for (LinkData linkData : dataLinkDefines) {
            for (FieldData field : fieldDefines) {
                if (!StringUtils.isNotEmpty((String)linkData.getZbid()) || !linkData.getZbid().equals(field.getFieldKey())) continue;
                field.setDataLinkKey(linkData.getKey());
                if (field.getDefaultValue() != null && !field.getDefaultValue().equals("") || linkData.getDefaultValue() == null || linkData.getDefaultValue().equals("")) continue block0;
                field.setDefaultValue(linkData.getDefaultValue());
                continue block0;
            }
        }
        if (fixRegionDataSet.hasNext() && (dataRowSet = (MemoryDataSet)fixRegionDataSet.next()).size() > 0) {
            fixDatas = dataRowSet.getBuffer(0);
        }
        if (null == fixDatas) {
            List<String[]> datas = this.queryVersionDatas(jtableContext, fieldDefines);
            fixDatas = null != datas && !datas.isEmpty() ? (Object[])datas.get(0) : new String[fieldDefines.size()];
        }
        if (emptyTable = this.isEmptyTable(fixDatas)) {
            return true;
        }
        HashMap<String, FieldData> fieldMap = new HashMap<String, FieldData>();
        for (FieldData fieldData : fieldDefines) {
            fieldMap.put(fieldData.getFieldKey(), fieldData);
        }
        for (int i = 0; i < dataLinkDefines.size(); ++i) {
            LinkData dataLink = (LinkData)dataLinkDefines.get(i);
            GridCellData cell = gridData.getGridCellData(dataLink.getCol(), dataLink.getRow());
            FieldData fieldData = (FieldData)fieldMap.get(dataLink.getZbid());
            FieldType type = FieldType.FIELD_TYPE_STRING;
            if (fieldData != null) {
                type = FieldType.forValue((int)fieldData.getFieldType());
            }
            if (cell != null) {
                String dataReturn = dataLink.getStyle();
                Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), dataReturn, cell, dataLink, fixDatas[i], gridData, jtableContext, null);
            }
            if (dataLink.getType() != LinkType.LINK_TYPE_FILE.getValue() && dataLink.getType() != LinkType.LINK_TYPE_PICTURE.getValue() || fixDatas[i] == null || !StringUtils.isNotEmpty((String)fixDatas[i].toString())) continue;
            fileGroupKeys.add(fixDatas[i].toString());
        }
        return emptyTable;
    }

    private List<String[]> queryVersionDatas(JtableContext jtableContext, List<FieldData> fieldDefines) {
        Map dimensionSet = jtableContext.getDimensionSet();
        if (!dimensionSet.containsKey("VERSIONID") || ((DimensionValue)dimensionSet.get("VERSIONID")).getValue().equals("00000000-0000-0000-0000-000000000000")) {
            return null;
        }
        String versionDv = ((DimensionValue)dimensionSet.get("VERSIONID")).getValue();
        ISecretLevelService secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
        List fileList = this.fileInfoService.getFileInfoByGroup(jtableContext.getFormKey(), "DataVer", FileStatus.AVAILABLE);
        ArrayList<FileInfo> tableFile = new ArrayList<FileInfo>();
        boolean secretLevelEnable = secretLevelService.secretLevelEnable(jtableContext.getTaskKey());
        SecretLevelInfo secretLevel = secretLevelService.getSecretLevel(jtableContext);
        for (FileInfo item : fileList) {
            boolean canAccess = true;
            if (StringUtils.isNotEmpty((String)item.getSecretlevel()) && secretLevelEnable && secretLevel != null) {
                SecretLevelItem secretLevelItem = secretLevelService.getSecretLevelItem(item.getSecretlevel());
                boolean bl = canAccess = secretLevelService.canAccess(secretLevelItem) && secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem);
            }
            if (!canAccess || !item.getName().equals(versionDv)) continue;
            tableFile.add(item);
        }
        Iterator iterator = tableFile.iterator();
        if (iterator.hasNext()) {
            FileInfo fileInfo = (FileInfo)iterator.next();
            byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String resultsss = null;
            try {
                out.write(bs);
                resultsss = out.toString();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            Gson gson = new Gson();
            List formList = (List)gson.fromJson(resultsss, new TypeToken<List<?>>(){}.getType());
            ArrayList<String[]> lists = new ArrayList<String[]>();
            String[] heads = new String[fieldDefines.size()];
            for (int i = 0; i < heads.length; ++i) {
                heads[i] = fieldDefines.get(i).getTableName() + "." + fieldDefines.get(i).getFieldCode();
            }
            assert (formList != null);
            for (Object object : formList) {
                Map o = (Map)object;
                for (Map.Entry table : o.entrySet()) {
                    int i = 0;
                    DataTable dataTable = null;
                    try {
                        dataTable = this.dataSchemeService.getDataTable((String)table.getKey());
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    if (null == dataTable) continue;
                    List deployInfoByDataTableKey = this.dataSchemeService.getDeployInfoByDataTableKey((String)table.getKey());
                    String tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName();
                    for (Map it : (List)table.getValue()) {
                        for (int j = 0; j < heads.length; ++j) {
                            String[] row;
                            if (!tableName.equals(heads[j].split("\\.")[0])) continue;
                            if (lists.isEmpty() || lists.size() <= i) {
                                row = new String[heads.length];
                                lists.add(row);
                            }
                            row = (String[])lists.get(i);
                            row[j] = String.valueOf(it.get(heads[j].split("\\.")[1]));
                        }
                        ++i;
                    }
                }
            }
            return lists;
        }
        return null;
    }

    public int exportFloatRegion(JtableContext jtableContext, IRegionExportDataSet floatRegionDataSet, Grid2Data gridData, int tempLine, Map<String, Integer> addRows, PagerInfo pagerInfo, List<String> fileGroupKeys) {
        List floatFieldDefines = floatRegionDataSet.getFieldDataList();
        List floatdataLinkDefines = floatRegionDataSet.getLinkDataList();
        floatdataLinkDefines.sort(Comparator.comparingInt(LinkData::getRow));
        RegionNumberManager numberManager = floatRegionDataSet.getNumberManager();
        RegionData regionData = floatRegionDataSet.getRegionData();
        int lineInfo = (regionData.getType() == 2 ? regionData.getRegionLeft() : regionData.getRegionTop()) + tempLine;
        String extentGridDataIsNullTableStr = this.iNvwaSystemOptionService.get("nr-data-entry-export", "EXTENT_GRID_IS_NULL_TABLE");
        boolean extentGridDataIsNullTable = extentGridDataIsNullTableStr.equals("1");
        int lineInfoNow = this.setDataMethod(jtableContext, floatRegionDataSet, lineInfo, floatFieldDefines, gridData, numberManager, tempLine, addRows, pagerInfo, fileGroupKeys);
        RegionExportDataSetImpl regionExportDataSet = (RegionExportDataSetImpl)floatRegionDataSet;
        RegionDataSet regionDataSet = regionExportDataSet.getRegionDataSet();
        if (lineInfoNow == -1 || regionDataSet != null && regionDataSet.isRegionOnlyHasExtentGridData() && extentGridDataIsNullTable) {
            return -1;
        }
        return tempLine += lineInfoNow - lineInfo;
    }

    private int setDataMethod(JtableContext jtableContext, IRegionExportDataSet floatRegionDataSet, int lineInfo, List<FieldData> floatFieldDefines, Grid2Data gridData, RegionNumberManager numberManager, int tempLine, Map<String, Integer> addRows, PagerInfo pagerInfo, List<String> fileGroupKeys) {
        int i;
        if (floatRegionDataSet.getRegionData().getType() == 2) {
            pagerInfo.setTotal(Integer.MAX_VALUE);
            return this.setDataMethod2(jtableContext, floatRegionDataSet, lineInfo, floatFieldDefines, gridData, numberManager, tempLine, addRows, pagerInfo, fileGroupKeys);
        }
        int page = 0;
        int regionTop = floatRegionDataSet.getRegionData().getRegionTop();
        int regionBottom = floatRegionDataSet.getRegionData().getRegionBottom();
        int moreRow = regionBottom - regionTop;
        boolean deleteRow = true;
        Map dimensionSet = jtableContext.getDimensionSet();
        String versionDv = null;
        if (dimensionSet.containsKey("VERSIONID") && !((DimensionValue)dimensionSet.get("VERSIONID")).getValue().equals("00000000-0000-0000-0000-000000000000")) {
            versionDv = ((DimensionValue)dimensionSet.get("VERSIONID")).getValue();
        }
        List<Object> lists = new ArrayList();
        if (null != versionDv) {
            lists = this.queryVersionDatas(jtableContext, floatFieldDefines);
        }
        int offset = pagerInfo.getOffset();
        int sheetCount = 0;
        boolean emptyTable = true;
        HashSet<String> linkCells = new HashSet<String>();
        while (sheetCount < 500000 && (floatRegionDataSet.hasNext() || pagerInfo.getOffset() > -1) && pagerInfo.getTotal() != Integer.MAX_VALUE) {
            Grid2Data tempGridData;
            Grid2FieldList merges;
            MemoryDataSet floatDataRowSet = (MemoryDataSet)floatRegionDataSet.next();
            int rowEmCount = floatDataRowSet.size();
            if (emptyTable) {
                for (i = 0; i < rowEmCount; ++i) {
                    Object[] buffer = floatDataRowSet.getBuffer(i);
                    emptyTable = this.isEmptyTable(buffer);
                }
            }
            if (floatRegionDataSet.getTotalCount() == 0) {
                pagerInfo.setTotal(Integer.MAX_VALUE);
            }
            int pageCount = 0;
            if (offset != -1) {
                pageCount = offset;
            }
            if (pageCount * pagerInfo.getLimit() + pagerInfo.getLimit() >= floatRegionDataSet.getTotalCount()) {
                pagerInfo.setTotal(Integer.MAX_VALUE);
            }
            if (floatDataRowSet.size() == 0 && floatRegionDataSet.getTotalCount() > 0) {
                pagerInfo.setOffset(++offset);
                break;
            }
            sheetCount += floatDataRowSet.size();
            int dataRowCount = floatDataRowSet.size();
            if (null != versionDv) {
                assert (lists != null);
                if (!lists.isEmpty()) {
                    dataRowCount = lists.size();
                }
            }
            int rowCount = dataRowCount;
            int gridAddRows = dataRowCount * (moreRow + 1);
            if (dataRowCount > 0) {
                int headerRowCount = gridData.getHeaderRowCount();
                gridData.insertRows(lineInfo, gridAddRows, lineInfo, true);
                if (moreRow > 0) {
                    int yy = (moreRow + 1) * dataRowCount + lineInfo;
                    for (int i2 = 0; i2 < dataRowCount; ++i2) {
                        gridData.copyFrom(gridData, 0, yy, gridData.getColumnCount() - 1, yy + moreRow, 0, i2 * (moreRow + 1) + lineInfo);
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
                if (page != 0) break;
                dataRowCount = 1;
                for (FieldData fieldDefine : floatFieldDefines) {
                    LinkData dataLinkDefine = floatRegionDataSet.getLinkDataByField(fieldDefine);
                    GridCellData gridcell = gridData.getGridCellData(dataLinkDefine.getCol(), lineInfo);
                    if (null == gridcell) continue;
                    gridcell.setShowText("");
                    gridcell.setEditText("");
                }
            }
            addRows.put(floatRegionDataSet.getRegionData().getKey(), dataRowCount * (moreRow + 1));
            Map<String, String> enumPosMap = this.getEnumPosMap(floatRegionDataSet.getLinkDataList());
            int rowDataIndex = 0;
            while (rowDataIndex < dataRowCount) {
                for (int fieldIndex = 0; fieldIndex < floatFieldDefines.size(); ++fieldIndex) {
                    FieldData fieldDefine = floatFieldDefines.get(fieldIndex);
                    LinkData dataLinkDefine = floatRegionDataSet.getLinkDataByField(fieldDefine);
                    RegionExportDataSetImpl regionDataSetImpl = (RegionExportDataSetImpl)floatRegionDataSet;
                    Map mergeCell = regionDataSetImpl.getRegionDataSet().getMergeCells();
                    if (dataLinkDefine == null) continue;
                    int temp = dataLinkDefine.getRow() + rowDataIndex * (moreRow + 1) + tempLine + page * pagerInfo.getLimit() * (moreRow + 1);
                    if (temp > lineInfo) {
                        lineInfo = temp;
                    }
                    GridCellData gridcell = gridData.getGridCellData(dataLinkDefine.getCol(), temp);
                    String posStr = this.getPosition(dataLinkDefine.getCol(), temp);
                    linkCells.add(posStr);
                    FieldType type = FieldType.forValue((int)fieldDefine.getFieldType());
                    if (gridcell == null) continue;
                    if (mergeCell.containsKey(dataLinkDefine.getKey())) {
                        List mergeCellInfo = (List)mergeCell.get(dataLinkDefine.getKey());
                        for (List merge : mergeCellInfo) {
                            if (merge.size() != 2) continue;
                            int top = (Integer)merge.get(0);
                            int bottom = (Integer)merge.get(1);
                            if (top != rowDataIndex) continue;
                            int tempBottom = dataLinkDefine.getRow() + bottom * (moreRow + 1) + tempLine + page * pagerInfo.getLimit() * (moreRow + 1);
                            gridData.mergeCells(dataLinkDefine.getCol(), temp, dataLinkDefine.getCol(), tempBottom);
                        }
                    }
                    String dataReturn = dataLinkDefine.getStyle();
                    Object[] buffer = null;
                    if (rowCount != 0) {
                        buffer = null != versionDv && !lists.isEmpty() ? (Object[])lists.get(rowDataIndex) : floatDataRowSet.getBuffer(rowDataIndex);
                    }
                    Object floatData = null;
                    if (null != buffer) {
                        floatData = buffer[fieldIndex];
                    }
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), dataReturn, gridcell, dataLinkDefine, floatData, gridData, jtableContext, enumPosMap);
                    if (dataLinkDefine.getType() != LinkType.LINK_TYPE_FILE.getValue() && dataLinkDefine.getType() != LinkType.LINK_TYPE_PICTURE.getValue() || floatData == null || !StringUtils.isNotEmpty((String)floatData.toString())) continue;
                    fileGroupKeys.add(floatData.toString());
                }
                if (numberManager != null && null != numberManager.getRegionNumber()) {
                    GridCellData gridcell = gridData.getGridCellData(numberManager.getRegionNumber().getColumn(), lineInfo - moreRow);
                    FieldType type = FieldType.FIELD_TYPE_STRING;
                    if (gridcell != null) {
                        Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), "10000020000 01,", gridcell, null, numberManager.next(), gridData, jtableContext, enumPosMap);
                    }
                }
                ++rowDataIndex;
                ++lineInfo;
            }
            RunTimeExtentStyleService extentStyleService = (RunTimeExtentStyleService)BeanUtil.getBean(RunTimeExtentStyleService.class);
            ExtentStyle extentStyle = extentStyleService.getExtentStyle(floatRegionDataSet.getRegionData().getKey());
            if (extentStyle != null && (merges = (tempGridData = extentStyle.getGriddata()).merges()).count() > 0) {
                int regionLeft = floatRegionDataSet.getRegionData().getRegionLeft();
                for (int i3 = 0; i3 < merges.count(); ++i3) {
                    Grid2CellField cell = merges.get(i3);
                    gridData.merges().addMergeRect(merges.get(i3));
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
            int rowSize = null != versionDv && !lists.isEmpty() ? lists.size() : floatDataRowSet.size();
            if (page == 0 && rowSize == 0) {
                --lineInfo;
                deleteRow = false;
            }
            ++page;
            pagerInfo.setOffset(++offset);
            if (floatDataRowSet.size() != 0) continue;
            pagerInfo.setOffset(++offset);
            break;
        }
        if (offset * pagerInfo.getLimit() + pagerInfo.getLimit() >= floatRegionDataSet.getTotalCount()) {
            pagerInfo.setTotal(Integer.MAX_VALUE);
        }
        if (emptyTable) {
            return -1;
        }
        if (pagerInfo.getOffset() * pagerInfo.getLimit() >= pagerInfo.getTotal()) {
            pagerInfo.setTotal(0);
        }
        if (moreRow > 0 && deleteRow) {
            gridData.deleteRows(lineInfo, moreRow + 1);
            gridData.insertRows(lineInfo, 1);
            Grid2FieldList merges = gridData.merges();
            ArrayList<Grid2CellField> removeList = new ArrayList<Grid2CellField>();
            for (i = 0; i < merges.count(); ++i) {
                Grid2CellField grid2CellField = merges.get(i);
                if (null == grid2CellField || grid2CellField.top < grid2CellField.bottom || grid2CellField.left < grid2CellField.right) continue;
                removeList.add(grid2CellField);
            }
            for (Grid2CellField grid2CellField : removeList) {
                merges.remove(grid2CellField);
            }
        }
        if (sheetCount > 0) {
            gridData.deleteRows(lineInfo, 1);
            --lineInfo;
        }
        return lineInfo;
    }

    private Map<String, String> getEnumPosMap(List<LinkData> linkDataList) {
        HashMap<String, String> enumPosMap = new HashMap<String, String>();
        for (LinkData linkData : linkDataList) {
            EnumLinkData enumLinkData;
            if (!(linkData instanceof EnumLinkData) || (enumLinkData = (EnumLinkData)linkData).getEnumFieldPosMap() == null || enumLinkData.getEnumFieldPosMap().size() <= 0) continue;
            for (Map.Entry enumPair : enumLinkData.getEnumFieldPosMap().entrySet()) {
                String enumPos = this.getPosStr((String)enumPair.getValue());
                enumPosMap.put(enumPos, (String)enumPair.getValue());
            }
        }
        for (LinkData linkData : linkDataList) {
            String position = this.getPosition(linkData.getCol(), linkData.getRow());
            enumPosMap.remove(position);
        }
        return enumPosMap;
    }

    public String getPosStr(String position) {
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

    private boolean isEmptyTable(Object[] buffer) {
        if (buffer == null) {
            return true;
        }
        boolean emptyTable = true;
        for (Object fixData : buffer) {
            if (!emptyTable) break;
            if (fixData instanceof String) {
                emptyTable = StringUtils.isEmpty((String)fixData.toString());
                continue;
            }
            if (fixData == null) continue;
            emptyTable = false;
        }
        return emptyTable;
    }

    private int setDataMethod2(JtableContext jtableContext, IRegionExportDataSet floatRegionDataSet, int lineInfo, List<FieldData> floatFieldDefines, Grid2Data gridData, RegionNumberManager numberManager, int tempLine, Map<String, Integer> addRows, PagerInfo pagerInfo, List<String> fileGroupKeys) {
        int i;
        int page = 0;
        int regionLeft = floatRegionDataSet.getRegionData().getRegionLeft();
        int regionRight = floatRegionDataSet.getRegionData().getRegionRight();
        int moreCol = regionRight - regionLeft;
        boolean deleteRow = true;
        Map dimensionSet = jtableContext.getDimensionSet();
        String versionDv = null;
        if (dimensionSet.containsKey("VERSIONID") && !((DimensionValue)dimensionSet.get("VERSIONID")).getValue().equals("00000000-0000-0000-0000-000000000000")) {
            versionDv = ((DimensionValue)dimensionSet.get("VERSIONID")).getValue();
        }
        List<Object> lists = new ArrayList();
        if (null != versionDv) {
            lists = this.queryVersionDatas(jtableContext, floatFieldDefines);
        }
        boolean emptyTable = true;
        while (floatRegionDataSet.hasNext()) {
            MemoryDataSet floatDataRowSet = (MemoryDataSet)floatRegionDataSet.next();
            int rowEmCount = floatDataRowSet.size();
            if (emptyTable) {
                for (i = 0; i < rowEmCount; ++i) {
                    Object[] buffer = floatDataRowSet.getBuffer(i);
                    emptyTable = this.isEmptyTable(buffer);
                }
            }
            int dataRowCount = floatDataRowSet.size();
            assert (lists != null);
            if (!lists.isEmpty()) {
                dataRowCount = lists.size();
            }
            int rowCount = dataRowCount;
            int gridAddRows = dataRowCount * (moreCol + 1);
            if (dataRowCount > 0) {
                int headerRowCount = gridData.getHeaderRowCount();
                GridCellData gridCellData = gridData.getGridCellData(lineInfo, 1);
                gridData.insertColumns(lineInfo, dataRowCount - 1, lineInfo);
                for (int i2 = 0; i2 < dataRowCount - 1; ++i2) {
                    gridData.getGridCellData(lineInfo + i2, 1).setShowText(gridCellData.getShowText());
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
                        for (int col = 0; col < floatFieldDefines.size(); ++col) {
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
                if (page != 0) break;
                dataRowCount = 1;
                for (FieldData fieldDefine : floatFieldDefines) {
                    LinkData dataLinkDefine = floatRegionDataSet.getLinkDataByField(fieldDefine);
                    GridCellData gridcell = gridData.getGridCellData(dataLinkDefine.getCol(), lineInfo);
                    if (null == gridcell) continue;
                    gridcell.setShowText("");
                    gridcell.setEditText("");
                }
            }
            addRows.put(floatRegionDataSet.getRegionData().getKey(), dataRowCount * (moreCol + 1));
            Map<String, String> enumPosMap = this.getEnumPosMap(floatRegionDataSet.getLinkDataList());
            int rowNum = lineInfo;
            int rowDataIndex = 0;
            while (rowDataIndex < dataRowCount) {
                for (int fieldIndex = 0; fieldIndex < floatFieldDefines.size(); ++fieldIndex) {
                    FieldData fieldDefine = floatFieldDefines.get(fieldIndex);
                    LinkData dataLinkDefine = floatRegionDataSet.getLinkDataByField(fieldDefine);
                    if (dataLinkDefine == null) continue;
                    lineInfo = dataLinkDefine.getCol() + rowDataIndex * (moreCol + 1) + tempLine + page * pagerInfo.getLimit() * (moreCol + 1);
                    GridCellData gridcell = gridData.getGridCellData(rowNum, dataLinkDefine.getRow());
                    FieldType type = FieldType.forValue((int)fieldDefine.getFieldType());
                    if (gridcell == null) continue;
                    String dataReturn = dataLinkDefine.getStyle();
                    Object[] buffer = null;
                    if (rowCount != 0) {
                        buffer = null != versionDv && !lists.isEmpty() ? (Object[])lists.get(rowDataIndex) : floatDataRowSet.getBuffer(rowDataIndex);
                    }
                    Object floatData = null;
                    if (null != buffer) {
                        floatData = buffer[fieldIndex];
                    }
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), dataReturn, gridcell, dataLinkDefine, floatData, gridData, jtableContext, enumPosMap);
                    if (dataLinkDefine.getType() != LinkType.LINK_TYPE_FILE.getValue() && dataLinkDefine.getType() != LinkType.LINK_TYPE_PICTURE.getValue() || floatData == null || !StringUtils.isNotEmpty((String)floatData.toString())) continue;
                    fileGroupKeys.add(floatData.toString());
                }
                if (numberManager != null && null != numberManager.getRegionNumber()) {
                    GridCellData gridcell = gridData.getGridCellData(lineInfo, numberManager.getRegionNumber().getRow());
                    FieldType type = FieldType.FIELD_TYPE_STRING;
                    if (gridcell != null) {
                        Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), "10000020000 01,", gridcell, null, numberManager.next(), gridData, jtableContext, enumPosMap);
                    }
                }
                ++rowNum;
                ++rowDataIndex;
                ++lineInfo;
            }
            int rowSize = lists.size();
            if (page == 0 && rowSize == 0) {
                --lineInfo;
                deleteRow = false;
            }
            ++page;
        }
        if (emptyTable) {
            return -1;
        }
        if (pagerInfo.getOffset() * pagerInfo.getLimit() >= pagerInfo.getTotal()) {
            pagerInfo.setTotal(0);
        }
        if (moreCol > 0 && deleteRow) {
            gridData.deleteColumns(lineInfo, moreCol + 1);
            Grid2FieldList merges = gridData.merges();
            ArrayList<Grid2CellField> removeList = new ArrayList<Grid2CellField>();
            for (i = 0; i < merges.count(); ++i) {
                Grid2CellField grid2CellField = merges.get(i);
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

