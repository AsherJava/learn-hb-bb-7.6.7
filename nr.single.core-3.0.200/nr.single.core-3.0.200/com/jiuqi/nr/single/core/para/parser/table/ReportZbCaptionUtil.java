/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportZbCaptionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReportZbCaptionUtil.class);
    public static final String[] ExcludeStrs = new String[]{" ", "\u3000", "*", "\uff0a", "\u00d7", "\u2573", "-", "\u2014", "\u2500", "1.", "2.", "3.", "4.", "5.", "6.", "7.", "8.", "9.", "10.", "11.", "12.", "13.", "14.", "15.", "16.", "17.", "18.", "19.", "20.", "21.", "22.", "23.", "24.", "25.", "26.", "27.", "28.", "29.", "30.", "31.", "32.", "33.", "34.", "35.", "36.", "37.", "38.", "39.", "40.", "1\uff0e", "2\uff0e", "3\uff0e", "4\uff0e", "5\uff0e", "6\uff0e", "7\uff0e", "8\uff0e", "9\uff0e", "10\uff0e", "11\uff0e", "12\uff0e", "13\uff0e", "14\uff0e", "15\uff0e", "16\uff0e", "17\uff0e", "18\uff0e", "19\uff0e", "20\uff0e", "21\uff0e", "22\uff0e", "23\uff0e", "24\uff0e", "25\uff0e", "26\uff0e", "27\uff0e", "28\uff0e", "29\uff0e", "30\uff0e", "31\uff0e", "32\uff0e", "33\uff0e", "34\uff0e", "35\uff0e", "36\uff0e", "37\uff0e", "38\uff0e", "39\uff0e", "40\uff0e", "1\u3001", "2\u3001", "3\u3001", "4\u3001", "5\u3001", "6\u3001", "7\u3001", "8\u3001", "9\u3001", "10\u3001", "11\u3001", "12\u3001", "13\u3001", "14\u3001", "15\u3001", "16\u3001", "17\u3001", "18\u3001", "19\u3001", "20\u3001", "21\u3001", "22\u3001", "23\u3001", "24\u3001", "25\u3001", "26\u3001", "27\u3001", "28\u3001", "29\u3001", "30\u3001", "31\u3001", "32\u3001", "33\u3001", "34\u3001", "35\u3001", "36\u3001", "37\u3001", "38\u3001", "39\u3001", "40\u3001", "(1)", "(2)", "(3)", "(4)", "(5)", "(6)", "(7)", "(8)", "(9)", "(10)", "(11)", "(12)", "(13)", "(14)", "(15)", "(16)", "(17)", "(18)", "(19)", "(20)", "(21)", "(22)", "(23)", "(24)", "(25)", "(26)", "(27)", "(28)", "(29)", "(30)", "(31)", "(32)", "(33)", "(34)", "(35)", "(36)", "(37)", "(38)", "(39)", "(40)", "\uff10\uff0e", "\uff11\uff0e", "\uff12\uff0e", "\uff13\uff0e", "\uff14\uff0e", "\uff15\uff0e", "\uff16\uff0e", "\uff17\uff0e", "\uff18\uff0e", "\uff19\uff0e", "\uff11\uff10\uff0e", "\uff11\uff11\uff0e", "\uff11\uff12\uff0e", "\uff11\uff13\uff0e", "\uff11\uff14\uff0e", "\uff11\uff15\uff0e", "\uff11\uff16\uff0e", "\uff11\uff17\uff0e", "\uff11\uff18\uff0e", "\uff11\uff19\uff0e", "\uff10\u3001", "\uff11\u3001", "\uff12\u3001", "\uff13\u3001", "\uff14\u3001", "\uff15\u3001", "\uff16\u3001", "\uff17\u3001", "\uff18\u3001", "\uff19\u3001", "\uff11\uff10\u3001", "\uff11\uff11\u3001", "\uff11\uff12\u3001", "\uff11\uff13\u3001", "\uff11\uff14\u3001", "\uff11\uff15\u3001", "\uff11\uff16\u3001", "\uff11\uff17\u3001", "\uff11\uff18\u3001", "\uff11\uff19\u3001", "\uff080\uff09", "\uff081\uff09", "\uff082\uff09", "\uff083\uff09", "\uff084\uff09", "\uff085\uff09", "\uff085\uff09", "\uff087\uff09", "\uff088\uff09", "\uff089\uff09", "\uff0810\uff09", "\uff0811\uff09", "\uff0812\uff09", "\uff0813\uff09", "\uff0814\uff09", "\uff0815\uff09", "\uff0815\uff09", "\uff0817\uff09", "\uff0818\uff09", "\uff0819\uff09", "\uff08\uff10\uff09", "\uff08\uff11\uff09", "\uff08\uff12\uff09", "\uff08\uff13\uff09", "\uff08\uff14\uff09", "\uff08\uff15\uff09", "\uff08\uff16\uff09", "\uff08\uff17\uff09", "\uff08\uff18\uff09", "\uff08\uff19\uff09", "\uff08\uff11\uff10\uff09", "\uff08\uff11\uff11\uff09", "\uff08\uff11\uff12\uff09", "\uff08\uff11\uff13\uff09", "\uff08\uff11\uff14\uff09", "\uff08\uff11\uff15\uff09", "\uff08\uff11\uff16\uff09", "\uff08\uff11\uff17\uff09", "\uff08\uff11\uff18\uff09", "\uff08\uff11\uff19\uff09", "(\u4e00)", "(\u4e8c)", "(\u4e09)", "(\u56db)", "(\u4e94)", "(\u516d)", "(\u4e03)", "(\u516b)", "(\u4e5d)", "(\u5341)", "(\u5341\u4e00)", "(\u5341\u4e8c)", "(\u5341\u4e09)", "(\u5341\u56db)", "(\u5341\u4e94)", "(\u5341\u516d)", "(\u5341\u4e03)", "(\u5341\u516b)", "(\u5341\u4e5d)", "(\u4e8c\u5341)", "(\u4e8c\u5341\u4e00)", "(\u4e8c\u5341\u4e8c)", "(\u4e8c\u5341\u4e09)", "(\u4e8c\u5341\u56db)", "(\u4e8c\u5341\u4e94)", "(\u4e8c\u5341\u516d)", "(\u4e8c\u5341\u4e03)", "(\u4e8c\u5341\u516b)", "(\u4e8c\u5341\u4e5d)", "(\u4e09\u5341)", "\u4e00.", "\u4e8c.", "\u4e09.", "\u56db.", "\u4e94.", "\u516d.", "\u4e03.", "\u516b.", "\u4e5d.", "\u5341.", "\u5341\u4e00.", "\u5341\u4e8c.", "\u5341\u4e09.", "\u5341\u56db.", "\u5341\u4e94.", "\u5341\u516d.", "\u5341\u4e03.", "\u5341\u516b.", "\u5341\u4e5d.", "\u4e8c\u5341.", "\u4e8c\u5341\u4e00.", "\u4e8c\u5341\u4e8c.", "\u4e8c\u5341\u4e09.", "\u4e8c\u5341\u56db.", "\u4e8c\u5341\u4e94.", "\u4e8c\u5341\u516d.", "\u4e8c\u5341\u4e03.", "\u4e8c\u5341\u516b.", "\u4e8c\u5341\u4e5d.", "\u4e09\u5341.", "\u4e00\uff0e", "\u4e8c\uff0e", "\u4e09\uff0e", "\u56db\uff0e", "\u4e94\uff0e", "\u516d\uff0e", "\u4e03\uff0e", "\u516b\uff0e", "\u4e5d\uff0e", "\u5341\uff0e", "\u5341\u4e00\uff0e", "\u5341\u4e8c\uff0e", "\u5341\u4e09\uff0e", "\u5341\u56db\uff0e", "\u5341\u4e94\uff0e", "\u5341\u516d\uff0e", "\u5341\u4e03\uff0e", "\u5341\u516b\uff0e", "\u5341\u4e5d\uff0e", "\u4e8c\u5341\uff0e", "\u4e8c\u5341\u4e00\uff0e", "\u4e8c\u5341\u4e8c\uff0e", "\u4e8c\u5341\u4e09\uff0e", "\u4e8c\u5341\u56db\uff0e", "\u4e8c\u5341\u4e94\uff0e", "\u4e8c\u5341\u516d\uff0e", "\u4e8c\u5341\u4e03\uff0e", "\u4e8c\u5341\u516b\uff0e", "\u4e8c\u5341\u4e5d\uff0e", "\u4e09\u5341\uff0e", "\u4e00\u3001", "\u4e8c\u3001", "\u4e09\u3001", "\u56db\u3001", "\u4e94\u3001", "\u516d\u3001", "\u4e03\u3001", "\u516b\u3001", "\u4e5d\u3001", "\u5341\u3001", "\u5341\u4e00\u3001", "\u5341\u4e8c\u3001", "\u5341\u4e09\u3001", "\u5341\u56db\u3001", "\u5341\u4e94\u3001", "\u5341\u516d\u3001", "\u5341\u4e03\u3001", "\u5341\u516b\u3001", "\u5341\u4e5d\u3001", "\u4e8c\u5341\u3001", "\u4e8c\u5341\u4e00\u3001", "\u4e8c\u5341\u4e8c\u3001", "\u4e8c\u5341\u4e09\u3001", "\u4e8c\u5341\u56db\u3001", "\u4e8c\u5341\u4e94\u3001", "\u4e8c\u5341\u516d\u3001", "\u4e8c\u5341\u4e03\u3001", "\u4e8c\u5341\u516b\u3001", "\u4e8c\u5341\u4e5d\u3001", "\u4e09\u5341\u3001", "\uff08\u4e00\uff09", "\uff08\u4e8c\uff09", "\uff08\u4e09\uff09", "\uff08\u56db\uff09", "\uff08\u4e94\uff09", "\uff08\u516d\uff09", "\uff08\u4e03\uff09", "\uff08\u516b\uff09", "\uff08\u4e5d\uff09", "\uff08\u5341\uff09", "\uff08\u5341\u4e00\uff09", "\uff08\u5341\u4e8c\uff09", "\uff08\u5341\u4e09\uff09", "\uff08\u5341\u56db\uff09", "\uff08\u5341\u4e94\uff09", "\uff08\u5341\u516d\uff09", "\uff08\u5341\u4e03\uff09", "\uff08\u5341\u516b\uff09", "\uff08\u5341\u4e5d\uff09", "\uff08\u4e8c\u5341\uff09", "\uff08\u4e8c\u5341\u4e00\uff09", "\uff08\u4e8c\u5341\u4e8c\uff09", "\uff08\u4e8c\u5341\u4e09\uff09", "\uff08\u4e8c\u5341\u56db\uff09", "\uff08\u4e8c\u5341\u4e94\uff09", "\uff08\u4e8c\u5341\u516d\uff09", "\uff08\u4e8c\u5341\u4e03\uff09", "\uff08\u4e8c\u5341\u516b\uff09", "\uff08\u4e8c\u5341\u4e5d\uff09", "\uff08\u4e09\u5341\uff09"};
    public static final String[] ExcludeStrs2 = new String[]{"*", "\u25b3", "#", "\u2606", "\u25b2", "\u2605"};

    public void resetFieldCaptions(RepInfo repInfo, boolean onlyEmpty) {
        ArrayList<String> RowCaptions = new ArrayList<String>();
        Grid2Data grid = Grid2Data.bytesToGrid((byte[])repInfo.getReportData().getGridBytes());
        int[] RowCptColNums = new int[grid.getRowCount()];
        for (int Row = 0; Row <= grid.getRowCount() - 1; ++Row) {
            RowCaptions.add("");
        }
        HashMap<Integer, FieldDefs> floatRowDic = new HashMap<Integer, FieldDefs>();
        Map<String, ZBInfo> allFields = repInfo.getDefs().getZbInfosPairByCRPos();
        for (FieldDefs fieldDef : repInfo.getDefs().getSubMbs()) {
            if (!floatRowDic.containsKey(fieldDef.getRegionInfo().getMapArea().getFloatRangeStartNo())) {
                floatRowDic.put(fieldDef.getRegionInfo().getMapArea().getFloatRangeStartNo(), fieldDef);
            }
            allFields.putAll(fieldDef.getZbInfosPairByCRPos());
        }
        String str = "";
        for (int Col = 1; Col <= grid.getColumnCount() - 1; ++Col) {
            int PreHead = -1;
            String ColCaption = "";
            for (int Row = 1; Row <= grid.getRowCount() - 1; ++Row) {
                ZBInfo aField = allFields.get(String.valueOf(Col) + "_" + String.valueOf(Row));
                if (aField != null) {
                    if (StringUtils.isEmpty((CharSequence)ColCaption) || "\u91d1\u989d".equalsIgnoreCase(ColCaption) || "\u6570\u91cf".equalsIgnoreCase(ColCaption)) {
                        if (floatRowDic.containsKey(Row)) {
                            if (StringUtils.isNotEmpty((CharSequence)((CharSequence)RowCaptions.get(Row)))) {
                                aField.setZbTitle((String)RowCaptions.get(Row) + " " + ColCaption);
                            } else {
                                aField.setZbTitle(ColCaption);
                            }
                        } else {
                            aField.setZbTitle((String)RowCaptions.get(Row));
                        }
                    } else if (StringUtils.isNotEmpty((CharSequence)((CharSequence)RowCaptions.get(Row)))) {
                        aField.setZbTitle((String)RowCaptions.get(Row) + " " + ColCaption);
                    } else {
                        aField.setZbTitle(ColCaption);
                    }
                    aField.setZbTitle(this.ReplaceTabAndEnter(aField.getZbTitle()));
                    continue;
                }
                str = grid.getGridCellData(Col, Row).getShowText();
                str = this.deleteNumber(str);
                if (!StringUtils.isNotEmpty((CharSequence)(str = this.deleteEmptyCell(str)))) continue;
                if (!this.isNumOrEmptyString(str)) {
                    if (!this.hasLeftConj(grid.merges(), Col, Row)) {
                        if (RowCptColNums[Row] == Col - 1) {
                            RowCaptions.set(Row, (String)RowCaptions.get(Row) + str);
                        } else {
                            RowCaptions.set(Row, str);
                        }
                    }
                    if (StringUtils.isEmpty((CharSequence)ColCaption) || PreHead != Row - 1) {
                        ColCaption = str;
                    } else if (!this.hasTopConj(grid.merges(), Col, Row)) {
                        ColCaption = ColCaption + "." + str;
                    }
                }
                PreHead = Row;
                RowCptColNums[Row] = Col;
            }
        }
    }

    public Map<String, String> getRepFieldCaptions(RepInfo repInfo, Grid2Data grid, boolean useLevel) {
        HashMap<String, String> cellCaptions = new HashMap<String, String>();
        for (int col = 1; col <= grid.getColumnCount(); ++col) {
            for (int row = 1; row <= grid.getRowCount(); ++row) {
                GridCellData cell2;
                Iterator<FieldDefs> aCode = String.valueOf(col) + "_" + String.valueOf(row);
                GridCellData cell = grid.getGridCellData(col, row);
                if (cell == null) continue;
                String str = cell.getEditText();
                if (cell.getMergeInfo() != null && (cell2 = grid.getGridCellData(cell.getMergeInfo().x, cell.getMergeInfo().y)) != null) {
                    str = cell2.getEditText();
                }
                cellCaptions.put((String)((Object)aCode), str);
            }
        }
        HashSet<String> allFields = new HashSet<String>();
        HashSet<Integer> floatRowDic = new HashSet<Integer>();
        if (repInfo.getDefs().getZbsNoZDM().size() > 0) {
            for (ZBInfo zb : repInfo.getDefs().getZbsNoZDM()) {
                if (zb.getGridPos() == null) continue;
                String aCode = String.valueOf(zb.getGridPos()[0]) + "_" + String.valueOf(zb.getGridPos()[1]);
                allFields.add(aCode);
            }
        }
        if (repInfo.getDefs().getSubMbs().size() > 0) {
            for (FieldDefs subDef : repInfo.getDefs().getSubMbs()) {
                floatRowDic.add(subDef.getRegionInfo().getMapArea().getFloatRangeStartNo());
                for (ZBInfo zb : subDef.getZbsNoZDM()) {
                    if (zb.getGridPos() == null) continue;
                    String aCode = String.valueOf(zb.getGridPos()[0]) + "_" + String.valueOf(zb.getGridPos()[1]);
                    allFields.add(aCode);
                }
            }
        }
        HashSet<String> destCells = new HashSet<String>();
        Map<String, String> destCellCations = this.getZbCaption(cellCaptions, grid.getColumnCount(), grid.getRowCount(), grid.merges(), destCells, floatRowDic, allFields, useLevel);
        return destCellCations;
    }

    private String getZbCaption(Grid2Data grid, int aCol, int aRow, Set<Integer> floatRowDic, Set<String> allFields) {
        HashMap<String, String> cellCaptions = new HashMap<String, String>();
        for (int col = 1; col <= aCol; ++col) {
            for (int row = 1; row <= aRow; ++row) {
                String aCode = String.valueOf(col) + "_" + String.valueOf(row);
                String str = grid.getGridCellData(col, row).getShowText();
                cellCaptions.put(aCode, str);
            }
        }
        return this.getZbCaption(cellCaptions, grid.getColumnCount(), grid.getRowCount(), grid.merges(), aCol, aRow, floatRowDic, allFields);
    }

    private String getZbCaption(Map<String, String> cellCaptions, int colCount, int rowCount, Grid2FieldList mergeList, int aCol, int aRow, Set<Integer> floatRowDic, Set<String> allFields) {
        String zbCaption = "";
        HashSet<String> destCells = new HashSet<String>();
        String aCode = String.valueOf(aCol) + "_" + String.valueOf(aRow);
        destCells.add(aCode);
        Map<String, String> destCellCations = this.getZbCaption(cellCaptions, colCount, rowCount, mergeList, destCells, floatRowDic, allFields, false);
        if (destCellCations != null && destCellCations.containsKey(aCode)) {
            zbCaption = destCellCations.get(aCode);
        }
        return zbCaption;
    }

    private Map<String, String> getZbCaption(Map<String, String> cellCaptions, int colCount, int rowCount, Grid2FieldList mergeList, Set<String> destCells, Set<Integer> floatRowDic, Set<String> allFields, boolean useLevel) {
        HashMap<String, String> destCellCations = new HashMap<String, String>();
        try {
            HashMap<Integer, String> floatRowTitles = new HashMap<Integer, String>();
            ArrayList<String> rowCaptions = new ArrayList<String>();
            int[] rowCptColNums = new int[rowCount + 1];
            int[] rowCellTypes = new int[rowCount + 1];
            for (int row = 0; row <= rowCount - 1; ++row) {
                rowCaptions.add("");
                rowCellTypes[row] = 0;
            }
            String str = "";
            for (int col = 1; col <= colCount; ++col) {
                int preHead = -1;
                String colCaption = "";
                for (int row = 1; row <= rowCount; ++row) {
                    String str2;
                    String aCode = String.valueOf(col) + "_" + String.valueOf(row);
                    if (allFields.contains(aCode)) {
                        if (rowCellTypes[row] == 0) {
                            rowCellTypes[row] = 2;
                        } else if (rowCellTypes[row] % 2 == 1) {
                            rowCellTypes[row] = rowCellTypes[row] + 1;
                        }
                        String zbCaptionA = "";
                        if (StringUtils.isEmpty((CharSequence)colCaption) || "\u91d1\u989d".equalsIgnoreCase(colCaption) || "\u6570\u91cf".equalsIgnoreCase(colCaption)) {
                            zbCaptionA = floatRowDic.contains(row) ? (StringUtils.isNotEmpty((CharSequence)((CharSequence)rowCaptions.get(row))) ? (String)rowCaptions.get(row) + " " + colCaption : colCaption) : (String)rowCaptions.get(row);
                        } else if (StringUtils.isNotEmpty((CharSequence)((CharSequence)rowCaptions.get(row)))) {
                            zbCaptionA = (String)rowCaptions.get(row) + " " + colCaption;
                        } else {
                            String rowCaption = "";
                            if (floatRowDic.contains(row)) {
                                if (floatRowTitles.containsKey(row)) {
                                    rowCaption = (String)floatRowTitles.get(row);
                                } else {
                                    for (int aRow = row - 1; aRow > 0; --aRow) {
                                        for (int aCol = 1; aCol <= colCount; ++aCol) {
                                            String aCode2 = String.valueOf(aCol) + "_" + String.valueOf(aRow);
                                            if (allFields.contains(aCode2)) continue;
                                            String oldStr2 = cellCaptions.get(aCode2);
                                            str2 = this.deleteNumber(oldStr2);
                                            if (!StringUtils.isNotEmpty((CharSequence)(str2 = this.deleteEmptyCell(str2)))) continue;
                                            rowCaption = str2;
                                            floatRowTitles.put(row, str2);
                                            break;
                                        }
                                        if (floatRowTitles.containsKey(row)) break;
                                    }
                                }
                            }
                            zbCaptionA = StringUtils.isNotEmpty((CharSequence)rowCaption) ? rowCaption + " " + colCaption : colCaption;
                        }
                        zbCaptionA = this.ReplaceTabAndEnter(zbCaptionA);
                        if (destCells == null || destCells.size() == 0) {
                            destCellCations.put(aCode, zbCaptionA);
                            continue;
                        }
                        if (!destCells.contains(aCode)) continue;
                        destCellCations.put(aCode, zbCaptionA);
                        continue;
                    }
                    String oldStr = cellCaptions.get(aCode);
                    str = this.deleteNumber(oldStr);
                    if (!StringUtils.isNotEmpty((CharSequence)(str = this.deleteEmptyCell(str)))) continue;
                    if (rowCellTypes[row] == 0) {
                        rowCellTypes[row] = 1;
                    } else if (rowCellTypes[row] % 2 == 0) {
                        rowCellTypes[row] = rowCellTypes[row] + 1;
                        rowCaptions.set(row, "");
                    }
                    int cellLevel = 0;
                    if (useLevel) {
                        cellLevel = this.getLevel(oldStr);
                    }
                    if (!this.isNumOrEmptyString(str)) {
                        if (useLevel && cellLevel > 0) {
                            String levelStr = "";
                            int cellLevelLast = cellLevel;
                            for (int row2 = row - 1; row2 > 0; --row2) {
                                String aCode2 = String.valueOf(col) + "_" + String.valueOf(row2);
                                str2 = cellCaptions.get(aCode2);
                                int cellLevel2 = this.getLevel(str2);
                                if (cellLevel2 < cellLevel && cellLevel2 < cellLevelLast) {
                                    if (!this.isNumOrEmptyString(str2)) {
                                        str2 = this.deleteNumber(str2);
                                        str2 = this.deleteEmptyCell(str2);
                                        levelStr = str2 + levelStr;
                                    }
                                    cellLevelLast = cellLevel2;
                                }
                                if (cellLevel2 == 0) break;
                            }
                            str = levelStr + "_" + str;
                        }
                        if (!this.hasLeftConj(mergeList, col, row)) {
                            if (rowCptColNums[row] == col - 1) {
                                rowCaptions.set(row, (String)rowCaptions.get(row) + str);
                            } else {
                                rowCaptions.set(row, str);
                            }
                        }
                        if (StringUtils.isEmpty((CharSequence)colCaption) || preHead != row - 1) {
                            colCaption = str;
                        } else if (!this.hasTopConj(mergeList, col, row)) {
                            colCaption = colCaption + "." + str;
                        }
                    }
                    preHead = row;
                    rowCptColNums[row] = col;
                }
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return destCellCations;
    }

    private boolean hasLeftConj(Grid2FieldList merges, int col, int row) {
        for (int i = 0; i < merges.count(); ++i) {
            Grid2CellField cellField = merges.get(i);
            if (col < cellField.left || col >= cellField.right || row < cellField.top || row > cellField.bottom) continue;
            return true;
        }
        return false;
    }

    private boolean hasTopConj(Grid2FieldList merges, int col, int row) {
        for (int i = 0; i < merges.count(); ++i) {
            Grid2CellField cellField = merges.get(i);
            if (col < cellField.left || col > cellField.right || row <= cellField.top || row > cellField.bottom) continue;
            return true;
        }
        return false;
    }

    private String strDeleteSub(String s, String subString) {
        if (StringUtils.isNotEmpty((CharSequence)s) && s.contains(subString)) {
            return s.replace(subString, "");
        }
        return s;
    }

    private String ReplaceTabAndEnter(String Code) {
        String str = Code.replace('\t', '\u0014');
        if (str.length() > 1) {
            if (str.substring(str.length() - 2, str.length()) == String.valueOf('\r') + String.valueOf('\n') || str.substring(str.length() - 2, str.length()) == String.valueOf('\n') + String.valueOf('\r')) {
                str = str.length() == 2 ? "" : str.substring(0, str.length() - 2);
            } else {
                str = str.replaceAll(String.valueOf('\r') + String.valueOf('\n'), "");
                str = str.replaceAll(String.valueOf('\n') + String.valueOf('\r'), "");
            }
        }
        if (str.length() > 0 && (str == String.valueOf('\n') || str.charAt(str.length() - 1) == '\r')) {
            str = str.length() == 1 ? "" : str.substring(0, str.length() - 1);
        }
        return str;
    }

    private boolean isNumOrEmptyString(String code) {
        return this.isEmptyString(code) || this.isNumberString(code);
    }

    private boolean isNumberString(String number) {
        if ((number = number.replace(",", "")) == "") {
            return false;
        }
        StringBuilder sb = this.deleteChar(number);
        for (int i = sb.length() - 1; i >= 0; --i) {
            char chr = sb.charAt(i);
            if (chr >= '0' && chr <= '9') continue;
            return false;
        }
        return true;
    }

    private StringBuilder deleteChar(String str) {
        int flag;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        if (sb.length() > 0 && (sb.charAt(0) == '+' || sb.charAt(0) == '-')) {
            sb.deleteCharAt(0);
        }
        if ((flag = sb.indexOf(".")) != -1) {
            sb.deleteCharAt(flag);
        }
        return sb;
    }

    private boolean isEmptyString(String code) {
        return "\u2014\u2014".equalsIgnoreCase(code);
    }

    private int getLevel(String str) {
        int cellLevel = 0;
        int aLen = str.length();
        for (int i = 0; i < aLen; ++i) {
            char c = str.charAt(i);
            if (c == ' ' || c == '\u0000' || c == '\t' || c == '\u3000') continue;
            if (c == '*' || c == '#') {
                cellLevel = i + 1;
                break;
            }
            if (c == '\u25b3' || c == '\u2606' || c == '\u2605' || c == '\u25b2') {
                cellLevel = i + 2;
                break;
            }
            cellLevel = i;
            break;
        }
        return cellLevel;
    }

    private String deleteNumber(String str) {
        int I;
        for (I = ExcludeStrs.length - 1; I >= 0; --I) {
            str = this.strDeleteSub(str, ExcludeStrs[I]);
        }
        for (I = ExcludeStrs2.length - 1; I >= 0; --I) {
            str = this.strDeleteSub(str, ExcludeStrs2[I]);
        }
        return str;
    }

    private String deleteEmptyCell(String str) {
        if ("\u2014\u2014".equalsIgnoreCase(str)) {
            str = "";
        } else if ("\u4e00".equalsIgnoreCase(str)) {
            str = "";
        }
        return str;
    }
}

