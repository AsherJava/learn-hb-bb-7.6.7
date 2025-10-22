/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.unit.treeimpl.dataio;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treeimpl.dataio.DataExportWorker;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelTreeDataExport
implements DataExportWorker {
    private static Logger log = LoggerFactory.getLogger(LevelTreeDataExport.class);
    protected IUnitTreeEntityRowProvider nodeDataProvider;
    private int startLevel = 0;
    private static final String COL_TITLE = "\u7ea7\u4f01\u4e1a";
    private static final Map<Integer, String> levelMap = new HashMap<Integer, String>(0);
    private IBaseNodeData rootNode;
    private boolean showCode;
    private String levelSheetName;

    public LevelTreeDataExport(IUnitTreeEntityRowProvider nodeDataProvider, IBaseNodeData rootNode) {
        this.rootNode = rootNode;
        this.nodeDataProvider = nodeDataProvider;
    }

    public LevelTreeDataExport(IUnitTreeEntityRowProvider nodeDataProvider, IBaseNodeData rootNode, boolean showCode) {
        this(nodeDataProvider, rootNode);
        this.showCode = showCode;
    }

    public LevelTreeDataExport(IUnitTreeEntityRowProvider nodeDataProvider, IBaseNodeData rootNode, boolean showCode, String levelSheetName) {
        this(nodeDataProvider, rootNode, showCode);
        this.levelSheetName = levelSheetName;
    }

    @Override
    public Workbook writeExcel() {
        IEntityRow rootRow;
        ArrayList<IEntityRow> rootRows = this.nodeDataProvider.getRootRows();
        this.startLevel = 1;
        if (this.rootNode != null && (rootRow = this.nodeDataProvider.findEntityRow(this.rootNode)) != null) {
            rootRows = new ArrayList<IEntityRow>(0);
            rootRows.add(rootRow);
            this.startLevel = this.nodeDataProvider.getNodePath(this.getNodeData((IEntityRow)rootRows.get(0))).length;
        }
        SXSSFWorkbook wb = new SXSSFWorkbook(-1);
        SXSSFSheet sheet = wb.createSheet(this.levelSheetName + "(\u4f01\u4e1a\u7ea7\u6b21)");
        sheet.trackAllColumnsForAutoSizing();
        SXSSFRow row = sheet.createRow(0);
        CellStyle titleCellStyle = this.titleSXSSFCellStyle(wb);
        CellStyle contentCellStyle = this.contentSXSSFCellStyle(wb);
        this.buildBookContent(rootRows, sheet, row, titleCellStyle, contentCellStyle);
        return wb;
    }

    @Override
    public void writeExcel(OutputStream os) throws Exception {
        Workbook wb = this.writeExcel();
        wb.write(os);
        os.flush();
    }

    @Override
    public void writeExcel(HttpServletResponse response, String filename) throws Exception {
        String cFilename = filename + ".xlsx";
        this.setExportFilename(response, cFilename);
        this.writeExcel((OutputStream)response.getOutputStream());
    }

    private void setExportFilename(HttpServletResponse response, String fileName) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", 0L);
        }
        catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void buildBookContent(List<IEntityRow> rootRows, SXSSFSheet sheet, SXSSFRow hssRow, CellStyle titleCellStyle, CellStyle contentCellStyle) {
        Stack<Object> stack = new Stack<Object>();
        for (int i = rootRows.size() - 1; i >= 0; --i) {
            stack.add(rootRows.get(i));
        }
        LinkedHashMap<Integer, String> levelCountMap = new LinkedHashMap<Integer, String>(0);
        while (!stack.isEmpty()) {
            IEntityRow row = (IEntityRow)stack.pop();
            List childRows = this.nodeDataProvider.getChildRows(this.getNodeData(row));
            this.createCells(row, sheet, contentCellStyle, levelCountMap);
            if (childRows.size() <= 0) continue;
            for (int i = childRows.size() - 1; i >= 0; --i) {
                stack.add(childRows.get(i));
            }
        }
        Set keySet = levelCountMap.keySet();
        for (Integer level : keySet) {
            String countStr = (String)levelCountMap.get(level);
            int count = Integer.parseInt(countStr);
            this.createTitleCell(hssRow, level, count, titleCellStyle);
            int totalRow = sheet.getLastRowNum();
            for (int rowIdx = 1; rowIdx <= totalRow; ++rowIdx) {
                SXSSFRow row = sheet.getRow(rowIdx);
                SXSSFCell cell = row.getCell(level - this.startLevel);
                if (cell == null) {
                    cell = row.createCell(level - this.startLevel);
                }
                cell.setCellStyle(contentCellStyle);
            }
        }
    }

    private void createTitleCell(SXSSFRow row, int col, int count, CellStyle titleCellStyle) {
        SXSSFCell cell = row.createCell(col - this.startLevel);
        cell.setCellStyle(titleCellStyle);
        cell.setCellValue(levelMap.get(col - 1) + COL_TITLE + "(" + count + "\u6237)");
        row.getSheet().autoSizeColumn(col - this.startLevel);
    }

    private int createCells(IEntityRow row, SXSSFSheet sheet, CellStyle contentCellStyle, Map<Integer, String> levelCountMap) {
        int level = this.nodeDataProvider.getNodePath(this.getNodeData(row)).length;
        int totalRow = sheet.getLastRowNum();
        SXSSFRow hssfRow = sheet.createRow(totalRow + 1);
        SXSSFCell cell = hssfRow.createCell(level - this.startLevel);
        cell.setCellStyle(contentCellStyle);
        String title = row.getTitle();
        if (this.showCode) {
            String code = row.getCode();
            cell.setCellValue(code + "|" + title);
        } else {
            cell.setCellValue(title);
        }
        String countStr = levelCountMap.get(level);
        if (countStr == null) {
            countStr = "1";
            levelCountMap.put(level, countStr);
        } else {
            int parseInt = Integer.parseInt(countStr);
            levelCountMap.put(level, ++parseInt + "");
        }
        totalRow = sheet.getLastRowNum();
        sheet.getRow(totalRow - 1);
        return 0;
    }

    protected CellStyle titleSXSSFCellStyle(SXSSFWorkbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("\u9ed1\u4f53");
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    protected CellStyle contentSXSSFCellStyle(SXSSFWorkbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("\u5b8b\u4f53");
        font.setFontHeightInPoints((short)12);
        DataFormat format = wb.createDataFormat();
        short textFmt = format.getFormat("@");
        cellStyle.setFont(font);
        cellStyle.setDataFormat(textFmt);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
    }

    protected HSSFCellStyle titleHSSFCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("\u9ed1\u4f53");
        font.setFontHeightInPoints((short)14);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    private IBaseNodeData getNodeData(IEntityRow row) {
        BaseNodeDataImpl nodeData = new BaseNodeDataImpl();
        nodeData.setKey(row.getEntityKeyData());
        nodeData.setCode(row.getCode());
        nodeData.setTitle(row.getTitle());
        return nodeData;
    }

    static {
        levelMap.put(0, "\u4e00");
        levelMap.put(1, "\u4e8c");
        levelMap.put(2, "\u4e09");
        levelMap.put(3, "\u56db");
        levelMap.put(4, "\u4e94");
        levelMap.put(5, "\u516d");
        levelMap.put(6, "\u4e03");
        levelMap.put(7, "\u516b");
        levelMap.put(8, "\u4e5d");
        levelMap.put(9, "\u5341");
        levelMap.put(10, "\u5341\u4e00");
        levelMap.put(11, "\u5341\u4e8c");
        levelMap.put(12, "\u5341\u4e09");
        levelMap.put(13, "\u5341\u56db");
        levelMap.put(14, "\u5341\u4e94");
        levelMap.put(15, "\u5341\u516d");
        levelMap.put(16, "\u5341\u4e03");
        levelMap.put(17, "\u5341\u516b");
        levelMap.put(18, "\u5341\u4e5d");
        levelMap.put(19, "\u4e8c\u5341");
    }
}

