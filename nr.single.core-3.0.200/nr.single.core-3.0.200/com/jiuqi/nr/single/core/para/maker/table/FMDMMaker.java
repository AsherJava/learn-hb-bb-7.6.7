/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.single.core.para.maker.table;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.DsgnCtrlType;
import com.jiuqi.nr.single.core.para.exception.SingleParamMakerException;
import com.jiuqi.nr.single.core.para.impl.ReportDataImpl;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.FMZBInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FMDMMaker {
    private static final Logger logger = LoggerFactory.getLogger(FMDMMaker.class);
    private ParaInfo paraInfo;

    public final void saveFMDM(FMRepInfo repInfo, String filePath, ParaInfo info, JQTFileMap jqtFileMap) throws SingleParamMakerException {
        this.paraInfo = info;
        try {
            MemStream mask0 = new MemStream();
            mask0.seek(0L, 0);
            jqtFileMap.save((Stream)mask0);
            this.saveFMDMReport((Stream)mask0, jqtFileMap, repInfo);
            mask0.seek(0L, 0);
            jqtFileMap.save((Stream)mask0);
            mask0.saveToFile(filePath);
        }
        catch (StreamException | IOException e) {
            logger.error(e.getMessage(), e);
            throw new SingleParamMakerException(e.getMessage(), e);
        }
    }

    private void saveFMDMReport(Stream stream, JQTFileMap jqtFileMap, FMRepInfo repInfo) throws SingleParamMakerException {
        try {
            ReadUtil.skipStream(stream, jqtFileMap.getAppearanceBlock());
            Stream mask0 = stream;
            if (jqtFileMap.gethAppearanceBlock() != 0) {
                mask0 = ReadUtil.decompressData(mask0);
            }
            HashMap<String, Map<String, FMZBInfo>> rowMap = new HashMap<String, Map<String, FMZBInfo>>();
            try {
                this.writeRowColSize(mask0, rowMap, repInfo);
                this.writeTitles(mask0, rowMap, repInfo);
                this.writeFMStruct(mask0, jqtFileMap, repInfo);
            }
            catch (RuntimeException e) {
                logger.error(e.getMessage(), e);
            }
        }
        catch (StreamException | IOException e) {
            logger.error(e.getMessage(), e);
            throw new SingleParamMakerException(e.getMessage(), e);
        }
    }

    private void writeRowColSize(Stream mask0, Map<String, Map<String, FMZBInfo>> rowMap, FMRepInfo repInfo) throws IOException, StreamException {
        long fmControlType = ReadUtil.readFlagValue(mask0);
        try {
            while (fmControlType != 0L) {
                FMZBInfo info = null;
                if (fmControlType == 262145L) {
                    FMZBInfo tempRef_info = new FMZBInfo();
                    this.writeMemoCtrl(mask0, tempRef_info);
                    info = tempRef_info;
                    if ((long)repInfo.getColWidth() < info.getWidth()) {
                        repInfo.setColWidth((int)info.getWidth());
                    }
                } else if (fmControlType == 196609L) {
                    FMZBInfo tempRef_info2 = new FMZBInfo();
                    this.writeMemoCtrl(mask0, tempRef_info2);
                    info = tempRef_info2;
                } else if (fmControlType == 131073L) {
                    this.writeBevelCtrl(mask0);
                } else {
                    this.writeZBControlByEidt(mask0, repInfo, info, fmControlType);
                }
                fmControlType = ReadUtil.readFlagValue(mask0);
            }
            if (repInfo.getColWidth() > 300) {
                repInfo.setColWidth(300);
            }
        }
        catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
        repInfo.setColCount(2);
        repInfo.setRowCount(this.paraInfo.getFmzbList().size());
    }

    private void writeZBControlByEidt(Stream mask0, FMRepInfo repInfo, FMZBInfo info, long fmControlType) throws IOException, StreamException {
        if ((fmControlType & 0x10000L) == 65536L) {
            int tmpValue = (int)(fmControlType - 65536L);
            DsgnCtrlType dType = DsgnCtrlType.forValue(tmpValue);
            switch (dType) {
                case DCS_LABLE: {
                    FMZBInfo tempRef_info3 = new FMZBInfo();
                    this.writeZBControl(mask0, tempRef_info3);
                    info = tempRef_info3;
                    break;
                }
                case DCS_EDIT: {
                    FMZBInfo tempRef_info4 = new FMZBInfo();
                    this.writeZBControl(mask0, tempRef_info4);
                    info = tempRef_info4;
                    if (this.paraInfo.getFmzbList().contains(info.getFieldName())) break;
                    this.paraInfo.getFmzbList().add(info.getFieldName());
                    break;
                }
                case DCS_COMBOBOX: {
                    FMZBInfo tempRef_info5 = new FMZBInfo();
                    this.writeZBControl(mask0, tempRef_info5);
                    info = tempRef_info5;
                    if (this.paraInfo.getFmzbList().contains(info.getFieldName())) break;
                    this.paraInfo.getFmzbList().add(info.getFieldName());
                    break;
                }
            }
            if (null != info && (long)repInfo.getColWidth() < info.getWidth()) {
                repInfo.setColWidth((int)info.getWidth());
            }
        }
    }

    private void writeZBControl(Stream mask0, FMZBInfo zbInfo) throws IOException, StreamException {
        if (null == zbInfo) {
            zbInfo = new FMZBInfo();
        }
        zbInfo.setLeft(ReadUtil.readIntValue(mask0));
        zbInfo.setTop(ReadUtil.readIntValue(mask0));
        zbInfo.setWidth(ReadUtil.readIntValue(mask0));
        zbInfo.setHeight(ReadUtil.readIntValue(mask0));
        zbInfo.setTag(ReadUtil.readIntValue(mask0));
        zbInfo.setAlignMent(ReadUtil.readIntValue(mask0));
        zbInfo.setBorderStyle(ReadUtil.readIntValue(mask0));
        zbInfo.setCaption(ReadUtil.readStreams(mask0));
        zbInfo.setTabIndex(ReadUtil.readIntValue(mask0));
        ReadUtil.skipStream(mask0, 4);
        String value = ReadUtil.readStreams(mask0);
        String fieldName = ReadUtil.readStreams(mask0);
        zbInfo.setFieldName(fieldName);
    }

    private void writeMemoCtrl(Stream mask0, FMZBInfo zbInfo) throws IOException, StreamException {
        zbInfo.setLeft(ReadUtil.readIntValue(mask0));
        zbInfo.setTop(ReadUtil.readIntValue(mask0));
        zbInfo.setWidth(ReadUtil.readIntValue(mask0));
        zbInfo.setHeight(ReadUtil.readIntValue(mask0));
        zbInfo.setTag(ReadUtil.readIntValue(mask0));
        zbInfo.setAlignMent(ReadUtil.readIntValue(mask0));
        zbInfo.setBorderStyle(ReadUtil.readIntValue(mask0));
        zbInfo.setCaption(ReadUtil.readStreams(mask0));
        zbInfo.setTabIndex(ReadUtil.readIntValue(mask0));
        long LShape = ReadUtil.readIntValue(mask0);
        long LStyle = ReadUtil.readIntValue(mask0);
        String s = ReadUtil.readStringValue(mask0, 255);
        zbInfo.setFieldName(s);
        long L = ReadUtil.readIntValue(mask0);
        long T = ReadUtil.readIntValue(mask0);
        long W = ReadUtil.readIntValue(mask0);
        long H = ReadUtil.readIntValue(mask0);
    }

    private void writeBevelCtrl(Stream mask0) throws IOException, StreamException {
        long L = ReadUtil.readIntValue(mask0);
        long T = ReadUtil.readIntValue(mask0);
        long W = ReadUtil.readIntValue(mask0);
        long H = ReadUtil.readIntValue(mask0);
        long T1 = ReadUtil.readIntValue(mask0);
        long A = ReadUtil.readIntValue(mask0);
        long S = ReadUtil.readIntValue(mask0);
        String C = ReadUtil.readStreams(mask0);
        long I = ReadUtil.readIntValue(mask0);
        long L1 = ReadUtil.readIntValue(mask0);
        long W1 = ReadUtil.readIntValue(mask0);
    }

    private void writeTitles(Stream mask0, Map<String, Map<String, FMZBInfo>> rowMap, FMRepInfo repInfo) {
        Grid2Data gridData = new Grid2Data();
        gridData.setColumnCount(repInfo.getColCount() + 1);
        gridData.setRowCount(repInfo.getRowCount() + 1);
        gridData.setColumnWidth(0, 55);
        List<String> rowKeyList = this.SortSet(rowMap.keySet());
        HashMap<String, List<String>> fmZBMap = new HashMap<String, List<String>>();
        for (int i = 0; i < rowKeyList.size(); ++i) {
            String rowKey = rowKeyList.get(i);
            Map<String, FMZBInfo> colMap = rowMap.get(rowKey);
            List<String> colKeyList = this.SortSet(colMap.keySet());
            GridCellData cell = null;
            int j = 0;
            for (j = 0; j < colKeyList.size(); ++j) {
                cell = gridData.getGridCellData(j + 1, i + 1);
                this.setCell(colKeyList, fmZBMap, colMap, cell, i, j);
            }
            int cellCount = colKeyList.size();
            if (cellCount >= repInfo.getColCount() || cell == null) continue;
            gridData.mergeCells(j, i + 1, repInfo.getColCount(), i + 1);
            cell.setRightBorderStyle(1);
        }
        repInfo.setFmZBMap(fmZBMap);
        ReportDataImpl dataImpl = new ReportDataImpl();
        dataImpl.setData(Grid2Data.gridToBytes((Grid2Data)gridData));
        dataImpl.setColCount(gridData.getColumnCount());
        dataImpl.setRowCount(gridData.getRowCount());
        repInfo.setReportData(dataImpl);
    }

    private void setCell(List<String> colKeyList, Map<String, List<String>> fmZBMap, Map<String, FMZBInfo> colMap, GridCellData cell, int i, int j) {
        String colKey = colKeyList.get(j);
        FMZBInfo fmzbInfo = colMap.get(colKey);
        cell.setBottomBorderColor(0x3F3F3F);
        cell.setBottomBorderStyle(1);
        if (j < colKeyList.size() - 1) {
            cell.setRightBorderColor(0x3F3F3F);
            cell.setRightBorderStyle(1);
        }
        if (!fmzbInfo.isZB()) {
            cell.setShowText(fmzbInfo.getCaption());
            cell.setSilverHead(true);
            cell.setEditable(false);
        } else {
            String code = fmzbInfo.getFieldName();
            if (StringUtils.isEmpty((String)code)) {
                return;
            }
            List<Object> list = null;
            list = fmZBMap.containsKey(code) ? fmZBMap.get(code) : new ArrayList();
            list.add(j + 1 + "_" + (i + 1) + "_" + fmzbInfo.getCtrlType());
            if (!fmZBMap.containsKey(code)) {
                fmZBMap.put(code, list);
            }
        }
    }

    private List<String> SortSet(Collection<String> set) {
        ArrayList<String> list = new ArrayList<String>();
        for (String key : set) {
            int i;
            for (i = list.size() - 1; i >= 0; --i) {
                if (Long.parseLong(key) < Long.parseLong((String)list.get(i))) continue;
                list.add(i + 1, key);
                break;
            }
            if (i != -1) continue;
            list.add(0, key);
        }
        return list;
    }

    public final void writeFMStruct(Stream mask0, JQTFileMap jqtFileMap, FMRepInfo repInfo) throws IOException, StreamException {
        repInfo.IniStruct(mask0, jqtFileMap);
    }

    public ReportDataImpl setFMReportDatas(FMRepInfo repInfo2) throws SingleParamMakerException {
        FMRepInfo repInfo = repInfo2;
        if (repInfo == null) {
            throw new SingleParamMakerException("\u8bf7\u68c0\u67e5\u53c2\u6570\uff0c\u6ca1\u6709\u5c01\u9762\u4ee3\u7801\u8868");
        }
        Grid2Data fmGrid = new Grid2Data();
        List<String> fmZBs = this.paraInfo.getFmzbList();
        Map<String, ZBInfo> fmZBInfoList = repInfo.getDefs().getAllZbInfosPair();
        fmGrid.setColumnCount(3);
        fmGrid.setRowCount(fmZBs.size() + 1);
        fmGrid.setColumnHidden(0, true);
        fmGrid.setRowHidden(0, true);
        fmGrid.setColumnWidth(1, repInfo.getColWidth());
        fmGrid.setColumnWidth(2, repInfo.getColWidth());
        fmGrid.setColumnAutoWidth(1, true);
        int curRow = 1;
        for (int i = 0; i < fmZBs.size() - 1; ++i) {
            String fieldName = fmZBs.get(i);
            ZBInfo zbInfo = fmZBInfoList.get(fieldName);
            GridCellData gridCell = fmGrid.getGridCellData(1, curRow);
            gridCell.setShowText(zbInfo.getZbTitle());
            gridCell.setEditText(zbInfo.getZbTitle());
            gridCell.setSilverHead(true);
            gridCell.setBottomBorderColor(0x3F3F3F);
            gridCell.setBottomBorderStyle(0);
            gridCell.setRightBorderColor(0x3F3F3F);
            gridCell.setRightBorderStyle(0);
            gridCell.setEditable(false);
            gridCell.setFontSize(11);
            gridCell.setForeGroundColor(0);
            gridCell.setEditText(zbInfo.getZbTitle());
            ++curRow;
        }
        ReportDataImpl dataImpl = new ReportDataImpl();
        dataImpl.setData(Grid2Data.gridToBytes((Grid2Data)fmGrid));
        repInfo.setReportData(dataImpl);
        dataImpl.setColCount(fmGrid.getColumnCount());
        dataImpl.setRowCount(fmGrid.getRowCount());
        return dataImpl;
    }
}

