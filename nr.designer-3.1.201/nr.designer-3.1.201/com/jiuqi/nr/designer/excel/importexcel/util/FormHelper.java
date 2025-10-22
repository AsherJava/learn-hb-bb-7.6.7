/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.nr.designer.excel.importexcel.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.designer.excel.importexcel.cache.ExcelImportContext;
import com.jiuqi.nr.designer.excel.importexcel.common.MergeCell;
import com.jiuqi.nr.designer.excel.importexcel.common.NrDesingerExcelErrorEnum;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.graphics.Point;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FormHelper {
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    private static final String URL_NAME = "AI_Field_TYPE_URL";
    private static final String OLD_URL = "http://10.2.12.111:9088/predict";
    private static final String[] ABC_ARR = new String[]{"Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static final String FIELDFLAG = "fieldObj";
    public static final String LINKFLAG = "linkObj";
    public static final String SUCCESS_FORMDATA = "successFormData";
    public static final String RESULT_ERROR_MESS = "reErrorMess";
    public static final String RESULT_FILE_ERROR = "fileError";
    public static final String SHOW_FORMAT = "@100";
    private Pattern pattern = Pattern.compile("[0-9]*");
    private Pattern r = Pattern.compile("^[0-9]+.?[0-9]*$");
    public static final String AI_FIELD_TYPE_URL = "AI_Field_TYPE_URL";
    public static final String ID = "field-group";

    public ArrayList<ArrayList<LinkedHashMap>> getFieldTypeBatchByRest(ExcelImportContext importContext) throws JQException {
        String url = this.iNvwaSystemOptionService.get(ID, "AI_Field_TYPE_URL");
        if (StringUtils.isEmpty((String)url)) {
            throw new JQException((ErrorEnum)NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_014);
        }
        if (importContext.getJsonArray() != null && importContext.getJsonArray().size() == 0) {
            return null;
        }
        ObjectNode batchData = JacksonUtils.mapper.createObjectNode();
        batchData.putPOJO("entities", importContext.getJsonArray());
        batchData.put("limit", 1);
        RestTemplate restTemplate = new RestTemplate();
        ObjectNode json = (ObjectNode)restTemplate.postForEntity(url, (Object)batchData, ObjectNode.class, new Object[0]).getBody();
        String s = JacksonUtils.objectToJson((Object)json.get("result"));
        LinkedHashMap linkedHashMap = (LinkedHashMap)JacksonUtils.jsonToObject((String)s, Object.class);
        ArrayList typeJson = (ArrayList)linkedHashMap.get("type");
        return typeJson;
    }

    public String generateNewStringByTemplet(String templetStr) {
        if (StringUtils.isEmpty((String)templetStr)) {
            return "1";
        }
        if (templetStr.length() <= 1) {
            return templetStr + "_1";
        }
        int lastIndexOfSplitter = templetStr.lastIndexOf(95);
        if (lastIndexOfSplitter >= 0) {
            if (lastIndexOfSplitter == templetStr.length() - 1) {
                return templetStr + "1";
            }
            String rearStr = templetStr.substring(lastIndexOfSplitter + 1, templetStr.length());
            if (this.pattern.matcher(rearStr).matches()) {
                int value = Integer.parseInt(rearStr);
                return templetStr.substring(0, lastIndexOfSplitter + 1) + ++value;
            }
        }
        return templetStr + "_1";
    }

    public String newUniqueCode() {
        return OrderGenerator.newOrder();
    }

    public boolean listIsEmpty(List<? extends Object> list) {
        return list == null || list.isEmpty();
    }

    public boolean mapIsEmpty(Map<? extends Object, ? extends Object> map) {
        return map == null || map.isEmpty();
    }

    public boolean isExistTableCode(String tmpTableCode) {
        return false;
    }

    public boolean checkUniqueCodeExist(String uniqueCode) {
        return false;
    }

    public String getColumnTitle(int colIndex) {
        String colTitle = "";
        if (colIndex < 0) {
            return "NEG";
        }
        if (colIndex <= 26) {
            colTitle = ABC_ARR[colIndex];
        } else {
            int sS = colIndex;
            int yS = -1;
            while (sS > 26) {
                if (yS == 0) {
                    yS = sS % 26;
                    --yS;
                } else {
                    yS = sS % 26;
                }
                sS = (int)Math.floor(sS / 26);
                colTitle = ABC_ARR[yS] + colTitle;
            }
            if (yS == 0) {
                --sS;
            }
            if (sS != 0) {
                colTitle = ABC_ARR[sS] + colTitle;
            }
        }
        return colTitle;
    }

    private String getColTitle(int colIndex, int rowIndex, ExcelImportContext importContext) {
        ArrayList<String> colTitleList = new ArrayList<String>();
        Grid2Data grid2Data = importContext.getGrid2Data();
        for (int i = colIndex - 1; i >= 1; --i) {
            String itemTitle;
            Matcher m;
            GridCellData cellObj = grid2Data.getGridCellData(i, rowIndex);
            DesignDataLinkDefine linkObject = this.getLinkByPos(i, rowIndex, importContext);
            if (linkObject != null || (m = this.r.matcher(itemTitle = cellObj.getEditText() == null ? "" : cellObj.getEditText())).matches()) continue;
            if (this.checkCelltext(itemTitle)) {
                if (cellObj.isMerged()) {
                    Point point = cellObj.getMergeInfo();
                    int mergerCol = point.x;
                    int mergerRow = point.y;
                    GridCellData mergerCell = grid2Data.getGridCellData(mergerCol, mergerRow);
                    String colTitle = mergerCell.getEditText();
                    if (StringUtils.isEmpty((String)colTitle)) {
                        colTitle = "";
                        colTitleList.add(colTitle);
                        continue;
                    }
                    if (colTitleList.size() == 0) {
                        colTitleList.add(colTitle);
                        continue;
                    }
                    if (((String)colTitleList.get(colTitleList.size() - 1)).equals(colTitle)) continue;
                    colTitleList.add(colTitle);
                    continue;
                }
                if (colTitleList.size() == 0) {
                    colTitleList.add(itemTitle);
                    continue;
                }
                if (((String)colTitleList.get(colTitleList.size() - 1)).equals(itemTitle)) continue;
                colTitleList.add(itemTitle);
                continue;
            }
            colTitleList.add("");
        }
        ArrayList<String> rsTitle = new ArrayList<String>();
        int lastEmptyStrIndex = -1;
        if (!this.listIsEmpty(colTitleList)) {
            for (int i = 0; i < colTitleList.size(); ++i) {
                String currTitle = (String)colTitleList.get(i);
                if (StringUtils.isEmpty((String)currTitle)) {
                    if (lastEmptyStrIndex + 1 != i) break;
                    lastEmptyStrIndex = i;
                    continue;
                }
                rsTitle.add(currTitle);
            }
        }
        return this.reversingTitle(rsTitle);
    }

    private String reversingTitle(List<String> titleList) {
        if (titleList.size() < 1) {
            return "";
        }
        Collections.reverse(titleList);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < titleList.size(); ++i) {
            if (i == titleList.size() - 1) {
                sb.append(titleList.get(i));
                continue;
            }
            sb.append(titleList.get(i)).append(".");
        }
        return sb.toString();
    }

    private boolean checkCelltext(String title) {
        boolean flgNum = false;
        boolean flgChart = false;
        boolean flgChi = false;
        String checkNumber = "^[0-9]*$";
        String checkChart = "^[a-zA-Z]*$";
        String chinese = "[\u4e00-\u9fa5]";
        if (Pattern.compile(checkNumber).matcher(title).matches()) {
            flgNum = true;
        }
        if (Pattern.compile(checkChart).matcher(title).matches()) {
            flgChart = true;
        }
        if (Pattern.compile(chinese).matcher(title).find()) {
            flgChi = true;
        }
        return flgNum || flgChart || flgChi;
    }

    public void initMergeCell(Grid2Data grid2Data) {
        ArrayList<MergeCell> mergeCells = new ArrayList<MergeCell>();
        int colCount = grid2Data.getColumnCount();
        int rowCount = grid2Data.getRowCount();
        for (int row = 1; row < rowCount; ++row) {
            for (int col = 1; col < colCount; ++col) {
                GridCellData gridCellData = grid2Data.getGridCellData(col, row);
                if (gridCellData.getRowSpan() <= 1 && gridCellData.getColSpan() <= 1) continue;
                MergeCell mergeCell = new MergeCell(gridCellData.getColIndex(), gridCellData.getRowIndex(), gridCellData.getColSpan(), gridCellData.getRowSpan());
                mergeCells.add(mergeCell);
            }
        }
        if (mergeCells == null || mergeCells.size() == 0) {
            return;
        }
        int mCount = mergeCells.size();
        for (int m = 0; m < mCount; ++m) {
            int mRow;
            MergeCell mergeCell = (MergeCell)mergeCells.get(m);
            int mCol = mergeCell.getCol();
            GridCellData headCellData = grid2Data.getGridCellData(mCol, mRow = mergeCell.getRow());
            if (headCellData == null) continue;
            int yCount = mergeCell.getHeight();
            for (int y = 0; y < yCount; ++y) {
                int xCount = mergeCell.getWidth();
                for (int x = 0; x < xCount; ++x) {
                    GridCellData gridCellData;
                    if (x == 0 && y == 0 || (gridCellData = grid2Data.getGridCellData(mCol + x, mRow + y)) == null) continue;
                    gridCellData.setMerged(true);
                    gridCellData.setMergeInfo(new Point(mCol, mRow));
                }
            }
            headCellData.setMerged(false);
            headCellData.setMergeInfo(null);
        }
    }

    private DesignDataLinkDefine getLinkByPos(int col, int row, ExcelImportContext importContext) {
        if (!this.mapIsEmpty(importContext.getLinkData())) {
            for (Map<String, DesignDataLinkDefine> designDataLinkDefineMap : importContext.getLinkData().values()) {
                for (DesignDataLinkDefine designDataLinkDefine : designDataLinkDefineMap.values()) {
                    if (designDataLinkDefine.getPosX() != col || designDataLinkDefine.getPosY() != row) continue;
                    return designDataLinkDefine;
                }
            }
        }
        return null;
    }
}

