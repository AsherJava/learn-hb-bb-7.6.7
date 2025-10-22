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
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.consts.DsgnCtrlType;
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

public class FMDMParser {
    private static final Logger logger = LoggerFactory.getLogger(FMDMParser.class);
    private List<String> fmZbList;

    public final FMRepInfo BuildFMDM(String filePath, List<String> fmZbs, JQTFileMap jqtFileMap) throws Exception {
        FMRepInfo repInfo = new FMRepInfo();
        MemStream mask0 = new MemStream();
        this.fmZbList = fmZbs;
        mask0.loadFromFile(filePath);
        mask0.seek(0L, 0);
        repInfo.setCode(jqtFileMap.getCode());
        this.InitFMDMReport((Stream)mask0, jqtFileMap, repInfo);
        return repInfo;
    }

    public final FMRepInfo BuildFMDM(byte[] data, List<String> fmZbs) throws Exception {
        FMRepInfo repInfo = new FMRepInfo();
        MemStream mask0 = new MemStream();
        this.fmZbList = fmZbs;
        mask0.writeBuffer(data, 0, data.length);
        mask0.seek(0L, 0);
        JQTFileMap jqtFileMap = this.InitJQTFileMap(mask0);
        repInfo.setCode(jqtFileMap.getCode());
        mask0.seek(0L, 0);
        this.InitFMDMReport((Stream)mask0, jqtFileMap, repInfo);
        return repInfo;
    }

    private JQTFileMap InitJQTFileMap(MemStream iStream) throws StreamException {
        JQTFileMap jqtFileMap = new JQTFileMap();
        iStream.seek(0L, 0);
        ReadUtil.skipStream((Stream)iStream, 96);
        jqtFileMap.init((Stream)iStream);
        return jqtFileMap;
    }

    private void InitFMDMReport(Stream stream, JQTFileMap jqtFileMap, FMRepInfo repInfo) throws Exception {
        try {
            HashMap<String, Map<String, FMZBInfo>> rowMap = new HashMap<String, Map<String, FMZBInfo>>();
            try {
                ReadUtil.skipStream(stream, jqtFileMap.getAppearanceBlock());
                Stream mask0 = stream;
                if (jqtFileMap.gethAppearanceBlock() != 0) {
                    mask0 = ReadUtil.decompressData(stream);
                }
                this.ReadRowColSize(mask0, rowMap, repInfo);
                stream.seek(0L, 0);
                this.ReadFMStruct(stream, jqtFileMap, repInfo);
            }
            catch (RuntimeException e) {
                logger.error(e.getMessage(), e);
            }
        }
        catch (IOException e_0) {
            logger.error(e_0.getMessage(), e_0);
        }
    }

    private void ReadRowColSize(Stream mask0, Map<String, Map<String, FMZBInfo>> rowMap, FMRepInfo repInfo) throws IOException, StreamException {
        long fmControlType = ReadUtil.readFlagValue(mask0);
        try {
            while (fmControlType != 0L) {
                String fieldName;
                FMZBInfo info = null;
                if (fmControlType == 262145L) {
                    FMZBInfo tempRef_info = new FMZBInfo();
                    this.ReadMemoCtrl(mask0, tempRef_info);
                    info = tempRef_info;
                    if ((long)repInfo.getColWidth() < info.getWidth()) {
                        repInfo.setColWidth((int)info.getWidth());
                    }
                    fieldName = info.getFieldName();
                    if (!this.fmZbList.contains(fieldName = this.getNewFieldName(fieldName))) {
                        this.fmZbList.add(fieldName);
                    }
                } else if (fmControlType == 196609L) {
                    FMZBInfo tempRef_info2 = new FMZBInfo();
                    this.ReadMemoCtrl(mask0, tempRef_info2);
                    info = tempRef_info2;
                    if ((long)repInfo.getColWidth() < info.getWidth()) {
                        repInfo.setColWidth((int)info.getWidth());
                    }
                    fieldName = info.getFieldName();
                    if (!this.fmZbList.contains(fieldName = this.getNewFieldName(fieldName))) {
                        this.fmZbList.add(fieldName);
                    }
                } else if (fmControlType == 131073L) {
                    this.ReadBevelCtrl(mask0);
                } else if ((fmControlType & 0x10000L) == 65536L) {
                    int tmpValue = (int)(fmControlType - 65536L);
                    DsgnCtrlType dType = DsgnCtrlType.forValue(tmpValue);
                    switch (dType) {
                        case DCS_LABLE: {
                            FMZBInfo tempRef_info3 = new FMZBInfo();
                            this.ReadZBControl(mask0, tempRef_info3);
                            info = tempRef_info3;
                            break;
                        }
                        case DCS_EDIT: {
                            FMZBInfo tempRef_info4 = new FMZBInfo();
                            this.ReadZBControl(mask0, tempRef_info4);
                            info = tempRef_info4;
                            if (this.fmZbList.contains(info.getFieldName())) break;
                            this.fmZbList.add(info.getFieldName());
                            break;
                        }
                        case DCS_COMBOBOX: {
                            FMZBInfo tempRef_info5 = new FMZBInfo();
                            this.ReadZBControl(mask0, tempRef_info5);
                            info = tempRef_info5;
                            if (this.fmZbList.contains(info.getFieldName())) break;
                            this.fmZbList.add(info.getFieldName());
                            break;
                        }
                    }
                    if (null != info && (long)repInfo.getColWidth() < info.getWidth()) {
                        repInfo.setColWidth((int)info.getWidth());
                    }
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
        repInfo.setRowCount(this.fmZbList.size());
    }

    private String getNewFieldName(String fieldName) {
        String result = fieldName;
        if (StringUtils.isNotEmpty((String)fieldName) && fieldName.length() > 10) {
            char a;
            String newFeildName = "";
            for (int i = 0; i < fieldName.length() && (a = fieldName.charAt(i)) != '\u0000'; ++i) {
                newFeildName = newFeildName + String.valueOf(a);
            }
            result = newFeildName;
        }
        return result;
    }

    private void ReadZBControl(Stream mask0, FMZBInfo zbInfo) throws IOException, StreamException {
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

    private void ReadMemoCtrl(Stream mask0, FMZBInfo zbInfo) throws IOException, StreamException {
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
        long LShape = ReadUtil.readIntValue(mask0);
        long LStyle = ReadUtil.readIntValue(mask0);
        String s = ReadUtil.readStringValue(mask0, 255);
        zbInfo.setFieldName(s);
        long L = ReadUtil.readIntValue(mask0);
        long T = ReadUtil.readIntValue(mask0);
        long W = ReadUtil.readIntValue(mask0);
        long H = ReadUtil.readIntValue(mask0);
    }

    private void ReadBevelCtrl(Stream mask0) throws IOException, StreamException {
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

    private void ReadTitles(Stream mask0, Map<String, Map<String, FMZBInfo>> rowMap, FMRepInfo repInfo) {
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
                String colKey = colKeyList.get(j);
                FMZBInfo fmzbInfo = colMap.get(colKey);
                cell = gridData.getGridCellData(j + 1, i + 1);
                cell.setBottomBorderColor(0x3F3F3F);
                cell.setBottomBorderStyle(2);
                if (j < colKeyList.size() - 1) {
                    cell.setRightBorderColor(0x3F3F3F);
                    cell.setRightBorderStyle(2);
                }
                if (!fmzbInfo.isZB()) {
                    cell.setShowText(fmzbInfo.getCaption());
                    cell.setSilverHead(true);
                    cell.setEditable(false);
                    continue;
                }
                String code = fmzbInfo.getFieldName();
                if (StringUtils.isEmpty((String)code)) continue;
                List<String> list = null;
                if (!StringUtils.isEmpty((String)code) && fmZBMap.containsKey(code)) {
                    list = (List)fmZBMap.get(code);
                }
                if (list == null) {
                    list = new ArrayList<String>();
                }
                list.add(j + 1 + "_" + (i + 1) + "_" + fmzbInfo.getCtrlType());
                if (fmZBMap.containsKey(code)) continue;
                fmZBMap.put(code, list);
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

    public final void ReadFMStruct(Stream mask0, JQTFileMap jqtFileMap, FMRepInfo repInfo) throws IOException, StreamException {
        repInfo.IniStruct(mask0, jqtFileMap);
    }

    public ReportDataImpl GetFMReportDatas(FMRepInfo repInfo2) throws Exception {
        FMRepInfo repInfo = repInfo2;
        if (repInfo == null) {
            throw new Exception("\u8bf7\u68c0\u67e5\u53c2\u6570\uff0c\u6ca1\u6709\u5c01\u9762\u4ee3\u7801\u8868");
        }
        Grid2Data fmGrid = new Grid2Data();
        List<String> fmZBs = this.fmZbList;
        Map<String, ZBInfo> fmZBInfoList = repInfo.getDefs().getAllZbInfosPair();
        fmGrid.setColumnCount(3);
        fmGrid.setRowCount(fmZBs.size() + 1);
        fmGrid.setColumnHidden(0, true);
        fmGrid.setRowHidden(0, true);
        fmGrid.setColumnWidth(1, repInfo.getColWidth());
        fmGrid.setColumnWidth(2, repInfo.getColWidth());
        fmGrid.setColumnAutoWidth(1, false);
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
            gridCell.setShowText(zbInfo.getZbTitle());
            gridCell.setEditText(zbInfo.getZbTitle());
            ++curRow;
        }
        ReportDataImpl dataImpl = new ReportDataImpl();
        dataImpl.setData(Grid2Data.gridToBytes((Grid2Data)fmGrid));
        dataImpl.setData(Grid2Data.gridToBytes((Grid2Data)fmGrid));
        repInfo.setReportData(dataImpl);
        dataImpl.setColCount(fmGrid.getColumnCount());
        dataImpl.setRowCount(fmGrid.getRowCount());
        return dataImpl;
    }
}

