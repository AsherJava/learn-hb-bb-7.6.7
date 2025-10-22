/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.internal.springcache.CacheProvider
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.splittable.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.internal.springcache.CacheProvider;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.splittable.bean.BaseBook;
import com.jiuqi.nr.splittable.bean.BaseSheet;
import com.jiuqi.nr.splittable.bean.CellObj;
import com.jiuqi.nr.splittable.bean.Header;
import com.jiuqi.nr.splittable.bean.MergedCell;
import com.jiuqi.nr.splittable.bean.RegionAndLink;
import com.jiuqi.nr.splittable.config.SplitGridCacheManagerConfig;
import com.jiuqi.nr.splittable.exception.ExceptionEnum;
import com.jiuqi.nr.splittable.exception.SplitTableException;
import com.jiuqi.nr.splittable.other.LinkHeap;
import com.jiuqi.nr.splittable.other.LinkObj;
import com.jiuqi.nr.splittable.other.SplitUtil;
import com.jiuqi.nr.splittable.service.SplitGridService;
import com.jiuqi.nr.splittable.util.GridCellDataUtil;
import com.jiuqi.nr.splittable.util.SerializeUtil;
import com.jiuqi.nr.splittable.web.SplitController;
import com.jiuqi.nr.splittable.web.SplitDataPM;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SplitGridServiceImpl
implements SplitGridService {
    private static final Logger log = LoggerFactory.getLogger(SplitController.class);
    private final IRunTimeViewController runTimeViewController;
    private static final int ARR_LINK = 1;
    private NedisCache cache;

    @Autowired
    public void setCache(CacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager(SplitGridCacheManagerConfig.NAME).getCache("splittable_title");
    }

    public SplitGridServiceImpl(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    @Override
    public Map<String, String> getGridAreaByKey(SplitDataPM splitDataPM) throws SplitTableException {
        FormDefine formDefine = this.runTimeViewController.queryFormById(splitDataPM.getFormKey());
        if (formDefine == null) {
            throw new SplitTableException(ExceptionEnum.FORM_KEY_FAILED.getMessage());
        }
        BigDataDefine bigDataDefine = this.runTimeViewController.getReportDataFromForm(splitDataPM.getFormKey());
        Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])bigDataDefine.getData());
        this.hideRowAndColNum(grid2Data);
        BaseBook baseBook = new BaseBook(splitDataPM.getFormKey(), formDefine.getTitle(), grid2Data);
        List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(splitDataPM.getFormKey());
        if (allRegionsInForm == null || allRegionsInForm.size() == 0) {
            throw new SplitTableException(ExceptionEnum.REGION_KEY_FAILED.getMessage());
        }
        List<RegionAndLink> regionAndLinkList = baseBook.getRegionAndLinkList();
        ArrayList allLinksInRegion = new ArrayList();
        for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
            List temp = this.runTimeViewController.getAllLinksInRegion(dataRegionDefine.getKey());
            allLinksInRegion.addAll(temp);
            RegionAndLink regionAndLink = RegionAndLink.initializer(splitDataPM, dataRegionDefine, temp);
            regionAndLinkList.add(regionAndLink);
        }
        if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM || allLinksInRegion.size() == 0) {
            this.oneRegionRes(baseBook);
            this.cache.put(baseBook.getBookKey(), (Object)SerializeUtil.serializeBaseBook(baseBook));
            log.info("\u7f13\u5b58\u6570\u636esplittable_baseBook_{}", (Object)baseBook.getBookKey());
            return baseBook.getSheetTitleMap();
        }
        this.solutionAllRegionAndLinks(baseBook);
        if (formDefine.getFormType() == FormType.FORM_TYPE_FLOAT) {
            this.solutionTotalRow(baseBook);
        }
        this.splitAllLink(baseBook);
        this.findNote(baseBook);
        int fixedRegionNum = baseBook.getFixedLinkHeapList().size();
        int floatRegionNum = this.getFloatRegionNum(regionAndLinkList);
        if (fixedRegionNum + floatRegionNum >= 2) {
            this.multiRegionRes(baseBook);
        } else {
            this.oneRegionRes(baseBook);
        }
        this.cache.put(baseBook.getBookKey(), (Object)SerializeUtil.serializeBaseBook(baseBook));
        log.info("\u7f13\u5b58\u6570\u636esplittable_baseBook_{}", (Object)baseBook.getBookKey());
        return baseBook.getSheetTitleMap();
    }

    private void hideRowAndColNum(Grid2Data grid2Data) {
        for (int i = 1; i <= grid2Data.getRowCount(); ++i) {
            for (int j = 1; j <= grid2Data.getColumnCount(); ++j) {
                GridCellData gridCellData = grid2Data.getGridCellData(j, i);
                if (gridCellData == null || gridCellData.getShowText() == null) continue;
                String showText = gridCellData.getShowText().replaceAll("\\s*", "");
                if (showText.contains("\u680f\u6b21")) {
                    grid2Data.setRowHidden(i, true);
                }
                if (!showText.contains("\u884c\u6b21")) continue;
                grid2Data.setColumnHidden(j, true);
            }
        }
    }

    private void solutionTotalRow(BaseBook baseBook) {
        List<LinkObj> fixedLinkObjList = baseBook.getFixedLinkObjList();
        if (!fixedLinkObjList.isEmpty()) {
            int posy = fixedLinkObjList.get(0).getPosy();
            if (fixedLinkObjList.parallelStream().allMatch(a -> a.getPosy() == posy)) {
                baseBook.setTotalRow(posy);
                List collect = fixedLinkObjList.stream().sorted(Comparator.comparing(LinkObj::getPosx)).collect(Collectors.toList());
                LinkObj first = (LinkObj)collect.get(0);
                LinkObj last = (LinkObj)collect.get(collect.size() - 1);
                ArrayList<LinkObj> totalRow = new ArrayList<LinkObj>(last.getPosx() - first.getPosx() + 1);
                for (int i = first.getPosx(); i <= last.getPosx(); ++i) {
                    totalRow.add(new LinkObj(posy, i));
                }
                baseBook.setFixedLinkObjList(totalRow);
            }
        }
    }

    private void findNote(BaseBook baseBook) {
        List<LinkObj> allLinkObj = baseBook.getAllLinkObj();
        List collect = allLinkObj.stream().map(LinkObj::getPosy).collect(Collectors.toList());
        int maxLinkBottom = collect.stream().max(Integer::compareTo).orElse(baseBook.getRowCount() - 1);
        RegionAndLink regionAndLink = baseBook.getRegionAndLinkList().stream().max(Comparator.comparing(RegionAndLink::getRegionBottom)).orElse(null);
        int maxRegionBottom = baseBook.getRowCount() - 1;
        if (regionAndLink != null) {
            maxRegionBottom = regionAndLink.getRegionBottom();
        }
        if (maxRegionBottom > maxLinkBottom) {
            baseBook.setHasNote(true);
            baseBook.setMaxLinkBottom(maxLinkBottom);
        }
    }

    private void splitAllLink(BaseBook baseBook) {
        List<LinkObj> floatLinkObjList = baseBook.getFloatLinkObjList();
        List<LinkObj> fixedLinkObjList = baseBook.getFixedLinkObjList();
        List<LinkHeap> floatLinkHeapList = SplitUtil.split(floatLinkObjList);
        List<LinkHeap> fixedLinkHeapList = SplitUtil.split(fixedLinkObjList);
        baseBook.setFixedLinkHeapList(fixedLinkHeapList);
        baseBook.setFloatLinkHeapList(floatLinkHeapList);
    }

    private int getFloatRegionNum(List<RegionAndLink> regionAndLinks) {
        int num = 0;
        for (RegionAndLink regionAndLink : regionAndLinks) {
            if (regionAndLink.getDataRegionKind().getValue() < DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) continue;
            ++num;
        }
        return num;
    }

    private void multiRegionRes(BaseBook baseBook) {
        int[][] arr = baseBook.getArr();
        List<Object> split = new ArrayList<LinkHeap>();
        split.addAll(baseBook.getFixedLinkHeapList());
        split.addAll(baseBook.getFloatLinkHeapList());
        split = split.stream().sorted((o1, o2) -> {
            if (o1.getFirst().getPosy() == o2.getFirst().getPosy()) {
                return o1.getFirst().getPosx() - o2.getFirst().getPosx();
            }
            return o1.getFirst().getPosy() - o2.getFirst().getPosy();
        }).collect(Collectors.toList());
        Grid2Data grid2Data = baseBook.getGrid2Data();
        for (LinkHeap linkHeap : split) {
            BaseSheet baseSheet = new BaseSheet();
            baseSheet.setLinkHeap(linkHeap);
            this.getLeftAndPos(baseSheet, grid2Data, arr);
            this.doTableHeadColumn(baseSheet, grid2Data);
            this.findTableHead(baseSheet, grid2Data, arr);
            if (baseSheet.getHeaderRow().getRow() == 0 && baseSheet.getHeaderRow().getColumn() == 0) {
                this.copyGridCellData(baseSheet.getHeaderCol(), baseBook, baseSheet, 1);
            } else {
                this.copyGridCellData(baseSheet.getHeaderRow(), baseBook, baseSheet, 1);
                this.copyGridCellData(baseSheet.getHeaderCol(), baseBook, baseSheet, baseSheet.getHeaderRow().getRow() + 1);
            }
            List<BaseSheet> sheetList = baseBook.getSheetList();
            sheetList.add(baseSheet);
        }
        List<BaseSheet> sheetList = baseBook.getSheetList();
        this.solutionMergeCell(sheetList);
        this.mergeGrid2Data(sheetList);
        sheetList = this.removeAllNull(baseBook, sheetList);
        this.copyGridRowAndCol(sheetList, grid2Data);
        this.beautifyTitle(baseBook, sheetList);
        if (baseBook.isHasNote()) {
            this.addNoteRegion(baseBook);
        }
        for (BaseSheet baseSheet : sheetList) {
            this.cache.put(baseSheet.getUuid(), (Object)SerializeUtil.serializeGrid2Data(baseSheet.getGrid2Data(), baseSheet.getOldCellObjList()));
        }
    }

    private List<BaseSheet> removeAllNull(BaseBook baseBook, List<BaseSheet> sheetList) {
        ArrayList<BaseSheet> sheetListTemp = new ArrayList<BaseSheet>(sheetList.size());
        for (BaseSheet baseSheet : sheetList) {
            if (baseSheet == null) continue;
            sheetListTemp.add(baseSheet);
        }
        baseBook.setSheetList(sheetListTemp);
        return sheetListTemp;
    }

    private void solutionMergeCell(List<BaseSheet> sheetList) {
        for (BaseSheet baseSheet : sheetList) {
            Grid2Data sheetGrid = baseSheet.getGrid2Data();
            if (sheetGrid.merges() != null) {
                for (int i = 0; i < sheetGrid.merges().count(); ++i) {
                    sheetGrid.merges().remove(i);
                }
            }
            for (MergedCell mergedCell : baseSheet.getMergedCellList()) {
                sheetGrid.merges().addMergeRect(new Grid2CellField(mergedCell.getLeft(), mergedCell.getTop(), mergedCell.getRight(), mergedCell.getBottom()));
            }
        }
    }

    private void copyGridRowAndCol(List<BaseSheet> sheetList, Grid2Data grid2Data) {
        int size = sheetList.size();
        for (int i = 0; i < size; ++i) {
            BaseSheet baseSheetTemp = sheetList.get(i);
            Grid2Data grid2DataTemp = baseSheetTemp.getGrid2Data();
            List<CellObj> cellRealXYList = baseSheetTemp.getOldCellObjList();
            int rowCount = grid2DataTemp.getRowCount();
            for (int j = 1; j < rowCount; ++j) {
                int row = cellRealXYList.get((j - 1) * (grid2DataTemp.getColumnCount() - 1)).getPosY();
                grid2DataTemp.setRowHeight(j, grid2Data.getRowHeight(row));
                grid2DataTemp.setRowAutoHeight(j, grid2Data.isRowAutoHeight(row));
                grid2DataTemp.setRowHidden(j, grid2Data.isRowHidden(row));
            }
            int colCount = grid2DataTemp.getColumnCount();
            for (int j = 1; j < colCount; ++j) {
                int col = cellRealXYList.get(j - 1).getPosX();
                grid2DataTemp.setColumnWidth(j, grid2Data.getColumnWidth(col));
                grid2DataTemp.setColumnAutoWidth(j, grid2Data.isColumnAutoWidth(col));
                grid2DataTemp.setColumnHidden(j, grid2Data.isColumnHidden(col));
            }
            grid2DataTemp.setColumnWidth(0, 40);
            baseSheetTemp.setGrid2Data(grid2DataTemp);
            sheetList.set(i, baseSheetTemp);
        }
    }

    private void getLeftAndPos(BaseSheet baseSheet, Grid2Data grid2Data, int[][] arr) {
        LinkHeap linkHeap = baseSheet.getLinkHeap();
        LinkObj first = linkHeap.getFirst();
        Header headerCol = baseSheet.getHeaderCol();
        int row = first.getPosy();
        int col = first.getPosx();
        int left = 0;
        while (col > 1) {
            GridCellData gridCellData;
            String showText;
            if ("".equals(showText = (gridCellData = grid2Data.getGridCellData(--col, row)).getShowText())) {
                if (arr[row][col] == 1) {
                    ++col;
                    break;
                }
                if (gridCellData.isMerged()) continue;
                ++col;
                break;
            }
            if ("\u2014\u2014".equals(showText)) {
                ++col;
                break;
            }
            ++left;
        }
        headerCol.setRow(linkHeap.getLast().getPosy() - row + 1);
        headerCol.setColumn(left);
        headerCol.setStartX(row);
        headerCol.setStartY(col);
    }

    private void doTableHeadColumn(BaseSheet baseSheet, Grid2Data grid2Data) {
        Header headerCol = baseSheet.getHeaderCol();
        LinkObj last = baseSheet.getLinkHeap().getLast();
        LinkObj first = baseSheet.getLinkHeap().getFirst();
        int row = headerCol.getStartX() == 0 ? first.getPosy() : headerCol.getStartX();
        int col = headerCol.getStartY() == 0 ? first.getPosx() : headerCol.getStartY();
        ArrayList<MergedCell> tempMergedCells = new ArrayList<MergedCell>();
        ArrayList<CellObj> tableHeadColList = new ArrayList<CellObj>();
        for (int i = col; i <= last.getPosx(); ++i) {
            GridCellData gridCellDataTemp = grid2Data.getGridCellData(i, row);
            if (gridCellDataTemp.isMerged()) {
                if (gridCellDataTemp.getMergeInfo() == null) {
                    tableHeadColList.add(new CellObj(row, i));
                    tempMergedCells.add(new MergedCell(i, row, i + gridCellDataTemp.getColSpan() - 1, row + gridCellDataTemp.getRowSpan() - 1));
                    continue;
                }
                if (gridCellDataTemp.getMergeInfo().y == row) continue;
                tableHeadColList.add(new CellObj(row, i));
                tempMergedCells.add(null);
                continue;
            }
            tableHeadColList.add(new CellObj(row, i));
            tempMergedCells.add(null);
        }
        baseSheet.setTableHeadColList(tableHeadColList);
        baseSheet.setTempMergedCells(tempMergedCells);
    }

    private void findTableHead(BaseSheet baseSheet, Grid2Data grid2Data, int[][] arr) {
        int[] arrTableHeadTemp = new int[grid2Data.getRowCount() + 1];
        Header headerCol = baseSheet.getHeaderCol();
        Header headerRow = baseSheet.getHeaderRow();
        LinkObj first = baseSheet.getLinkHeap().getFirst();
        int row = headerCol.getStartX() == 0 ? first.getPosy() : headerCol.getStartX();
        int col = headerCol.getStartY() == 0 ? first.getPosx() : headerCol.getStartY();
        List<CellObj> tableHeadColList = baseSheet.getTableHeadColList();
        int index = 1;
        for (int i = row - 1; i > 0; --i) {
            int tableHeadRowLen = 0;
            for (CellObj cellObj : tableHeadColList) {
                GridCellData gridCellDataTemp = grid2Data.getGridCellData(cellObj.getPosX(), i);
                if ("".equals(gridCellDataTemp.getShowText())) {
                    if (arr[i][cellObj.getPosX()] == 1) break;
                    if (gridCellDataTemp.isMerged()) {
                        ++tableHeadRowLen;
                        continue;
                    }
                    if (tableHeadColList.indexOf(cellObj) >= headerCol.getColumn()) continue;
                    ++tableHeadRowLen;
                    continue;
                }
                if ("\u2014\u2014".equals(gridCellDataTemp.getShowText()) || "\u2014".equals(gridCellDataTemp.getShowText())) continue;
                ++tableHeadRowLen;
            }
            arrTableHeadTemp[index++] = tableHeadRowLen;
        }
        int tableHeadRowUp = -1;
        int tableHeadRowBottom = -1;
        for (int i = 1; i < arrTableHeadTemp.length && arrTableHeadTemp[i] > 0; ++i) {
            if (arrTableHeadTemp[i] == tableHeadColList.size()) {
                if (tableHeadRowBottom == -1) {
                    tableHeadRowBottom = i;
                }
                tableHeadRowUp = i;
                if (arrTableHeadTemp[i + 1] == tableHeadColList.size()) continue;
                break;
            }
            if (0 < arrTableHeadTemp[i] && arrTableHeadTemp[i] >= tableHeadColList.size()) continue;
        }
        if (tableHeadRowUp > 0) {
            headerRow.setRow(tableHeadRowUp - tableHeadRowBottom + 1);
            headerRow.setColumn(tableHeadColList.size());
            headerRow.setStartX(row - tableHeadRowUp);
            headerRow.setStartY(col);
        }
    }

    private void beautifyTitle(BaseBook baseBook, List<BaseSheet> sheetList) {
        int i;
        int len = sheetList.size();
        Map<String, String> sheetTitleMap = baseBook.getSheetTitleMap();
        int[] titleArr = new int[len];
        for (i = 0; i < len - 1; ++i) {
            if (titleArr[i] != 0) continue;
            titleArr[i] = i + 1;
            for (int j = i + 1; j < len; ++j) {
                if (!this.ifBeautify(sheetList.get(i).getSheetTitle(), sheetList.get(j).getSheetTitle())) continue;
                titleArr[j] = i + 1;
            }
        }
        for (i = 0; i < len; ++i) {
            int n = titleArr[i];
            int index = 0;
            if (titleArr[i] == -1) continue;
            titleArr[i] = -1;
            BaseSheet sheetI = sheetList.get(i);
            for (int j = i + 1; j < len; ++j) {
                if (n != titleArr[j] || titleArr[j] == -1) continue;
                BaseSheet sheetJ = sheetList.get(j);
                titleArr[j] = -1;
                if (!sheetTitleMap.containsKey(sheetList.get(i).getUuid())) {
                    sheetTitleMap.put(sheetI.getUuid(), sheetI.getSheetTitle() + String.format("(\u533a\u57df%d)", ++index));
                    sheetI.setSheetTitle(sheetI.getSheetTitle() + String.format("(\u533a\u57df%d)", index));
                }
                sheetTitleMap.put(sheetJ.getUuid(), sheetJ.getSheetTitle() + String.format("(\u533a\u57df%d)", ++index));
                sheetJ.setSheetTitle(sheetJ.getSheetTitle() + String.format("(\u533a\u57df%d)", index));
            }
            if (index != 0) continue;
            String title = sheetList.get(i).getSheetTitle();
            if (baseBook.getBookTitle().equals(title)) {
                sheetTitleMap.put(sheetI.getUuid(), sheetI.getSheetTitle());
                sheetI.setSheetTitle(sheetI.getSheetTitle());
                continue;
            }
            sheetTitleMap.put(sheetI.getUuid(), sheetI.getSheetTitle());
        }
    }

    private boolean ifBeautify(String showText1, String showText2) {
        if (showText1 == null || showText2 == null) {
            return false;
        }
        return showText1.equals(showText2);
    }

    private void mergeGrid2Data(List<BaseSheet> sheetList) {
        int i;
        int len = sheetList.size();
        int[] mergeArr = new int[len];
        for (i = 0; i < len - 1; ++i) {
            if (mergeArr[i] != 0) continue;
            mergeArr[i] = i + 1;
            for (int j = i + 1; j < len; ++j) {
                if (!this.ifMerge(sheetList, i, j)) continue;
                mergeArr[j] = i + 1;
            }
        }
        for (i = 0; i < len; ++i) {
            int t = mergeArr[i];
            if (mergeArr[i] == -1) continue;
            mergeArr[i] = -1;
            BaseSheet newSheet = sheetList.get(i);
            for (int j = i + 1; j < len; ++j) {
                if (t != mergeArr[j] || mergeArr[j] == -1) continue;
                mergeArr[j] = -1;
                newSheet = this.merge(sheetList.get(i), sheetList.get(j), sheetList.get(i).getHeaderRowNum());
                sheetList.set(j, null);
            }
            if (newSheet == null) continue;
            sheetList.set(i, newSheet);
        }
    }

    private BaseSheet merge(BaseSheet baseSheetL, BaseSheet baseSheetR, int up) {
        Grid2Data grid2Data1 = baseSheetL.getGrid2Data();
        Grid2Data grid2Data2 = baseSheetR.getGrid2Data();
        CellObj[][] newCellXYArray1 = baseSheetL.getNewCellXY();
        CellObj[][] newCellXYArray2 = baseSheetR.getNewCellXY();
        int rowCount1 = grid2Data1.getRowCount();
        int rowCount2 = grid2Data2.getRowCount();
        int columnCount = grid2Data2.getColumnCount();
        grid2Data1.setRowCount(rowCount1 + rowCount2 - up - 1);
        int index = up * (columnCount - 1);
        List<CellObj> listLeft = baseSheetL.getOldCellObjList();
        List<CellObj> listRight = baseSheetR.getOldCellObjList();
        for (int i = 1; i < rowCount2 - up; ++i) {
            for (int j = 1; j < columnCount; ++j) {
                CellObj cellObj;
                GridCellData gridCellData = new GridCellData(j, i + up);
                gridCellData.copyCellData(grid2Data2.getGridCellData(j, i + up));
                gridCellData.setColIndex(j);
                gridCellData.setRowIndex(rowCount1 + i - 1);
                grid2Data1.setGridCellData(gridCellData, rowCount1 + i - 1, j);
                if (index + 1 > listRight.size()) continue;
                if (newCellXYArray2[(cellObj = listRight.get(index++)).getPosY()][cellObj.getPosX()] != null) {
                    newCellXYArray1[cellObj.getPosY()][cellObj.getPosX()] = new CellObj(rowCount1 + i - 1, j);
                }
                listLeft.add(cellObj);
            }
        }
        List<MergedCell> mergedCellList = baseSheetL.getMergedCellList();
        for (MergedCell mergedCell : baseSheetR.getMergedCellList()) {
            if (mergedCellList.contains(mergedCell)) continue;
            mergedCellList.add(mergedCell);
        }
        baseSheetL.setNewCellXY(newCellXYArray1);
        baseSheetL.setOldCellObjList(listLeft);
        baseSheetL.setGrid2Data(grid2Data1);
        baseSheetL.updateSheet();
        return baseSheetL;
    }

    private boolean ifMerge(List<BaseSheet> sheetList, int l, int r) {
        BaseSheet baseSheetL = sheetList.get(l);
        BaseSheet baseSheetR = sheetList.get(r);
        if (baseSheetL == null || baseSheetR == null) {
            return false;
        }
        if (baseSheetL.getHeaderRowNum() != baseSheetR.getHeaderRowNum()) {
            return false;
        }
        if (baseSheetL.getSheetCol() != baseSheetR.getSheetCol()) {
            return false;
        }
        if (baseSheetL.getSheetTitle() == null) {
            return false;
        }
        if (baseSheetL.getSheetTitle() != null && !baseSheetL.getSheetTitle().equals(baseSheetR.getSheetTitle())) {
            return false;
        }
        for (int i = 1; i <= baseSheetL.getHeaderRow().getRow(); ++i) {
            for (int j = 1; j < baseSheetL.getHeaderRow().getColumn(); ++j) {
                GridCellData gridCellData1 = baseSheetL.getGrid2Data().getGridCellData(j, i);
                GridCellData gridCellData2 = baseSheetR.getGrid2Data().getGridCellData(j, i);
                if (gridCellData1 == null || gridCellData2 == null || gridCellData1.getShowText().equals(gridCellData2.getShowText())) continue;
                return false;
            }
        }
        return true;
    }

    private void copyGridCellData(Header header, BaseBook baseBook, BaseSheet baseSheet, int firstRow) {
        int[][] arr = baseBook.getArr();
        Grid2Data grid2Data = baseBook.getGrid2Data();
        Grid2Data smallGrid2Data = baseSheet.getGrid2Data();
        if (smallGrid2Data == null) {
            byte[] bytes = Grid2Data.gridToBytes((Grid2Data)grid2Data);
            smallGrid2Data = Grid2Data.bytesToGrid((byte[])bytes);
            smallGrid2Data.deleteRows(0, grid2Data.getRowCount());
            smallGrid2Data.setRowCount(baseSheet.getSheetRow());
            smallGrid2Data.setColumnCount(baseSheet.getSheetCol());
        }
        List<MergedCell> tempMergedCells = baseSheet.getTempMergedCells();
        List<MergedCell> mergedCells = baseSheet.getMergedCellList();
        List<CellObj> realXYList = baseSheet.getOldCellObjList();
        if (realXYList == null) {
            realXYList = new ArrayList<CellObj>();
        }
        CellObj[][] cellObjs = new CellObj[grid2Data.getRowCount() + 1][grid2Data.getColumnCount() + 1];
        int sx = firstRow;
        for (int i = header.getStartX(); i < header.getStartX() + header.getRow(); ++i) {
            int sy = 1;
            List<CellObj> tableHeadColList = baseSheet.getTableHeadColList();
            for (int j = 0; j < tableHeadColList.size(); ++j) {
                MergedCell mergedCell;
                CellObj cellObj = tableHeadColList.get(j);
                GridCellData gridCellData = GridCellDataUtil.cellDataClone(grid2Data.getGridCellData(cellObj.getPosX(), i));
                gridCellData.setRowIndex(sx);
                gridCellData.setColIndex(sy);
                if (baseSheet.getHeaderRowNum() > 0 && gridCellData.getShowText().equals("") && gridCellData.isMerged() && sy == 1) {
                    int posX = cellObj.getPosX();
                    while (--posX > 0 && grid2Data.getGridCellData(posX, i).getShowText().equals("")) {
                    }
                    if (posX > 0) {
                        gridCellData.setShowText(grid2Data.getGridCellData(posX, i).getShowText());
                        gridCellData.setColSpan(grid2Data.getGridCellData(posX, i).getColSpan());
                        gridCellData.setRowSpan(grid2Data.getGridCellData(posX, i).getRowSpan());
                    }
                }
                if ((mergedCell = tempMergedCells.get(j)) != null) {
                    if (gridCellData.getRowIndex() < mergedCell.getTop()) {
                        gridCellData.setColSpan(gridCellData.getColSpan() - mergedCell.getColSpan() + 1);
                        if (gridCellData.getColSpan() >= smallGrid2Data.getColumnCount() - 1) {
                            gridCellData.setColSpan(smallGrid2Data.getColumnCount() - 1);
                        }
                    } else {
                        gridCellData.setColSpan(1);
                    }
                } else if (gridCellData.getColSpan() >= smallGrid2Data.getColumnCount() - 1) {
                    gridCellData.setColSpan(smallGrid2Data.getColumnCount() - 1);
                }
                if (gridCellData.getColSpan() > 1 || gridCellData.getRowSpan() > 1) {
                    int left = gridCellData.getColIndex();
                    int top = gridCellData.getRowIndex();
                    int right = gridCellData.getColIndex() + gridCellData.getColSpan() - 1;
                    int bottom = gridCellData.getRowIndex() + gridCellData.getRowSpan() - 1;
                    mergedCells.add(new MergedCell(left, top, right, bottom));
                } else {
                    gridCellData.setMerged(false);
                }
                if (sx == 1 && sy == 1 && baseSheet.getHeaderRowNum() >= 0) {
                    if (gridCellData.isMerged() && gridCellData.getColSpan() == smallGrid2Data.getColumnCount() - 1 && StringUtils.hasText(gridCellData.getShowText())) {
                        baseSheet.setSheetTitle(gridCellData.getShowText());
                    } else {
                        baseSheet.setSheetTitle(baseBook.getBookTitle());
                    }
                }
                smallGrid2Data.setGridCellData(gridCellData, sx, sy);
                realXYList.add(new CellObj(i, cellObj.getPosX()));
                if (arr[i][cellObj.getPosX()] == 1) {
                    cellObjs[i][cellObj.getPosX()] = new CellObj(sx, sy);
                }
                ++sy;
            }
            ++sx;
        }
        baseSheet.setNewCellXY(cellObjs);
        baseSheet.setGrid2Data(smallGrid2Data);
        baseSheet.setOldCellObjList(realXYList);
    }

    private void solutionAllRegionAndLinks(BaseBook baseBook) {
        int[][] arr = baseBook.getArr();
        ArrayList<LinkObj> fixedLinkObjList = new ArrayList<LinkObj>();
        ArrayList<LinkObj> floatLinkObjList = new ArrayList<LinkObj>();
        for (RegionAndLink regionAndLink : baseBook.getRegionAndLinkList()) {
            LinkObj linkObj;
            if (regionAndLink.getDataRegionKind().getValue() <= DataRegionKind.DATA_REGION_SCROLL.getValue()) {
                for (DataLinkDefine dataLinkDefine : regionAndLink.getAllLinksInRegion()) {
                    linkObj = new LinkObj(dataLinkDefine.getPosY(), dataLinkDefine.getPosX());
                    if (linkObj.getPosx() <= 0 || linkObj.getPosy() <= 0) continue;
                    arr[dataLinkDefine.getPosY()][dataLinkDefine.getPosX()] = 1;
                    fixedLinkObjList.add(linkObj);
                }
            }
            if (regionAndLink.getDataRegionKind().getValue() < DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) continue;
            for (int i = regionAndLink.getRegionTop(); i <= regionAndLink.getRegionBottom(); ++i) {
                for (int j = regionAndLink.getRegionLeft(); j <= regionAndLink.getRegionRight(); ++j) {
                    if (i <= 0 || j <= 0) continue;
                    linkObj = new LinkObj(i, j);
                    arr[i][j] = 1;
                    floatLinkObjList.add(linkObj);
                }
            }
        }
        baseBook.setFixedLinkObjList(fixedLinkObjList);
        baseBook.setFloatLinkObjList(floatLinkObjList);
        ArrayList<LinkObj> list = new ArrayList<LinkObj>();
        list.addAll(fixedLinkObjList);
        list.addAll(floatLinkObjList);
        baseBook.setAllLinkObj(list);
        baseBook.setArr(arr);
    }

    private void oneRegionRes(BaseBook baseBook) {
        if (baseBook.isHasNote()) {
            this.addNoteRegion(baseBook);
        } else {
            List<BaseSheet> baseSheets = baseBook.getSheetList();
            baseSheets.add(new BaseSheet(baseBook.getBookTitle(), baseBook.getGrid2Data(), new ArrayList<CellObj>()));
            baseBook.setSheetList(baseSheets);
        }
    }

    private void addNoteRegion(BaseBook baseBook) {
        int j;
        int i;
        byte[] bytes = Grid2Data.gridToBytes((Grid2Data)baseBook.getGrid2Data());
        Grid2Data copyGrid2Data = Grid2Data.bytesToGrid((byte[])bytes);
        Grid2Data note = new Grid2Data();
        note.setRowCount(baseBook.getRowCount() - baseBook.getMaxLinkBottom());
        note.setColumnCount(baseBook.getColCount());
        note.copyFrom(baseBook.getGrid2Data(), 1, baseBook.getMaxLinkBottom() + 1, baseBook.getColCount() - 1, baseBook.getRowCount() - 1, 1, 1);
        List<BaseSheet> sheetList = baseBook.getSheetList();
        Map<String, String> sheetTitleMap = baseBook.getSheetTitleMap();
        ArrayList<CellObj> noteRealXYList = new ArrayList<CellObj>();
        ArrayList<CellObj> realXYList = new ArrayList<CellObj>();
        for (i = baseBook.getMaxLinkBottom() + 1; i <= copyGrid2Data.getRowCount(); ++i) {
            for (j = 1; j < copyGrid2Data.getColumnCount(); ++j) {
                noteRealXYList.add(new CellObj(i, j));
            }
        }
        for (i = 1; i <= baseBook.getMaxLinkBottom(); ++i) {
            for (j = 1; j < copyGrid2Data.getColumnCount(); ++j) {
                if (i == baseBook.getTotalRow()) continue;
                realXYList.add(new CellObj(i, j));
            }
        }
        if (baseBook.getTotalRow() != 0) {
            copyGrid2Data.deleteRows(baseBook.getTotalRow(), 1);
            if (baseBook.getMaxLinkBottom() > baseBook.getTotalRow()) {
                baseBook.setMaxLinkBottom(baseBook.getMaxLinkBottom() - 1);
            }
        }
        if (sheetList.size() == 0) {
            copyGrid2Data.deleteRows(baseBook.getMaxLinkBottom() + 1, copyGrid2Data.getRowCount() - baseBook.getMaxLinkBottom() - 1);
            BaseSheet baseSheet = new BaseSheet(baseBook.getBookTitle(), copyGrid2Data, realXYList);
            sheetList.add(baseSheet);
            sheetTitleMap.put(baseSheet.getUuid(), baseSheet.getSheetTitle());
        }
        BaseSheet baseSheet = new BaseSheet("\u5176\u4ed6", note, noteRealXYList);
        sheetList.add(baseSheet);
        sheetTitleMap.put(baseSheet.getUuid(), baseSheet.getSheetTitle());
    }

    @Override
    public String getGridChildAreaByKey(String uuid) {
        if (this.cache.exists(uuid)) {
            log.info("\u83b7\u53d6\u7f13\u5b58splittable_child_" + uuid);
            Cache.ValueWrapper valueWrapper = this.cache.get(uuid);
            if (valueWrapper != null) {
                return (String)valueWrapper.get();
            }
        }
        return "{}";
    }

    @Override
    public String getCellBook(SplitDataPM splitDataPM) {
        if (!this.cache.exists(splitDataPM.getFormKey())) {
            this.getGridAreaByKey(splitDataPM);
        }
        log.info("\u83b7\u53d6\u7f13\u5b58splittable_CellBook_" + splitDataPM.getFormKey());
        Cache.ValueWrapper valueWrapper = this.cache.get("B" + splitDataPM.getFormKey());
        if (valueWrapper != null) {
            return (String)valueWrapper.get();
        }
        return null;
    }
}

