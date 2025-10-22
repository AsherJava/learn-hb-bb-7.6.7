/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.nr.definition.facade.print.PrintPaperDefine
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.Paper
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.grid.GridData;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.grid.Font;
import com.jiuqi.nr.bpm.de.dataflow.bean.PaperInfo;
import com.jiuqi.nr.bpm.de.dataflow.util.ProcessTrackPrintUntil;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackExcelInfo;
import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.Paper;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProcessTrackPrint {
    private static final Logger logger;
    private static final Map<String, Paper> PAPER_TYPE;

    public void printData(List<ProcessTrackExcelInfo> list) {
        try {
            Grid2Data grid2Data = ProcessTrackPrint.getGrid(list);
            GridData gridData = ProcessTrackPrintUntil.grid2DataToGridData(grid2Data, null);
            PaperInfo paperInfo = new PaperInfo();
            paperInfo.setPaperType(Paper.A4_PAPER.getSize());
            paperInfo.setMarginBottom(0.0);
            paperInfo.setMarginLeft(0.0);
            paperInfo.setMarginRight(0.0);
            paperInfo.setMarginTop(0.0);
            ProcessTrackPrint.createPrintTemplateData(paperInfo, null, null, gridData, list);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static ITemplateDocument createPrintTemplateData(PaperInfo paperInfo, String reportkey, String reportTitle, GridData reportGridData, List<ProcessTrackExcelInfo> list) {
        Font font = new Font();
        ArrayList<WordLabelDefineImpl> wordLabels = new ArrayList<WordLabelDefineImpl>();
        WordLabelDefineImpl define = new WordLabelDefineImpl();
        define.setElement(0);
        define.setHorizontalPos(1);
        define.setVerticalPos(0);
        int lastIndex = list.size() - 1;
        String workflowInfo = list.get(lastIndex).getWorkflowInfo();
        String[] Info = workflowInfo.split(",");
        String workflowTitle = Info[0];
        String unitName = Info[1];
        String period = Info[2];
        define.setText(workflowTitle);
        define.setScope(0);
        font.setBold(true);
        font.setSize(20);
        define.setFont(font);
        wordLabels.add(define);
        define = new WordLabelDefineImpl();
        define.setElement(1);
        define.setHorizontalPos(0);
        define.setVerticalPos(0);
        if (unitName != null) {
            define.setText("\u7f16\u5236\u5355\u4f4d\uff1a" + unitName);
        } else {
            define.setText("");
        }
        define.setScope(0);
        font = new Font();
        font.setBold(false);
        font.setSize(9);
        define.setFont(font);
        wordLabels.add(define);
        define = new WordLabelDefineImpl();
        define.setElement(1);
        define.setHorizontalPos(2);
        define.setVerticalPos(0);
        define.setText(period);
        define.setScope(0);
        define.setFont(font);
        wordLabels.add(define);
        ITemplateDocument templateDocument = PrintElementUtils.createTemplateDocument((PrintPaperDefine)paperInfo, wordLabels, null, (String)reportkey, (String)reportTitle, (GridData)reportGridData);
        ReportTemplateObject reportTemplate = PrintElementUtils.getReportTemplate((ITemplateDocument)templateDocument);
        reportTemplate.getFontSizeConfig().setHasFontSizeFactor(true);
        reportTemplate.getFontSizeConfig().setFontSizeFactor(-6.0);
        return templateDocument;
    }

    public static Paper getPaperByType(int paperType) {
        Collection<Paper> values = PAPER_TYPE.values();
        for (Paper paper : values) {
            if (paper.getSize() != paperType) continue;
            return paper;
        }
        return Paper.A4_PAPER;
    }

    public static Grid2Data getGrid(List<ProcessTrackExcelInfo> list) throws JQException {
        Grid2Data grid2Data = null;
        try {
            grid2Data = ProcessTrackPrint.initGrid();
            ProcessTrackPrint.initBody(grid2Data, list);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return grid2Data;
    }

    private static void initBody(Grid2Data gridData, List<ProcessTrackExcelInfo> list) {
        int size = list.size();
        int i = 1;
        gridData.insertRows(2, size - 1, 1);
        for (ProcessTrackExcelInfo object : list) {
            GridCellData dataCell = ProcessTrackPrint.getCell(1, i + 1, gridData, String.valueOf(i));
            dataCell = ProcessTrackPrint.getCell(2, i + 1, gridData, object.getNodeName());
            dataCell = ProcessTrackPrint.getCell(3, i + 1, gridData, object.getActionState());
            dataCell.setHorzAlign(1);
            dataCell = ProcessTrackPrint.getCell(4, i + 1, gridData, object.getUser());
            dataCell = ProcessTrackPrint.getCell(5, i + 1, gridData, object.getActionName());
            dataCell = ProcessTrackPrint.getCell(6, i + 1, gridData, object.getDesc());
            dataCell = ProcessTrackPrint.getCell(7, i + 1, gridData, object.getTime());
            dataCell.setHorzAlign(1);
            gridData.setRowAutoHeight(i + 1, true);
            ++i;
        }
    }

    private static Grid2Data initGrid() {
        String[] rowTitles = new String[]{"\u5e8f\u53f7", "\u6d41\u7a0b\u8282\u70b9", "\u72b6\u6001", "\u6267\u884c\u8005", "\u52a8\u4f5c", "\u8bf4\u660e", "\u65f6\u95f4"};
        Grid2Data gridData = new Grid2Data();
        int defaultColumn = 8;
        gridData.setColumnCount(defaultColumn);
        gridData.setRowCount(3);
        for (int i = 1; i < defaultColumn; ++i) {
            GridCellData headerCell = gridData.getGridCellData(i, 1);
            String title = rowTitles[i - 1];
            headerCell.setSilverHead(true);
            headerCell.setBottomBorderStyle(1);
            headerCell.setRightBorderStyle(1);
            headerCell.setSelectable(false);
            headerCell.setPersistenceData("fontSize", String.valueOf(12));
            headerCell.setForeGroundColor(0);
            headerCell.setShowText(title);
            headerCell.setHorzAlign(3);
            headerCell.setHorzAlign(3);
            headerCell.setMultiLine(true);
            headerCell.setWrapLine(true);
            gridData.setColumnAutoWidth(i, true);
        }
        gridData.setColumnHidden(0, true);
        return gridData;
    }

    private static GridCellData getCell(int col, int row, Grid2Data gridData, String title) {
        GridCellData dataCell = gridData.getGridCellData(col, row);
        dataCell.setSelectable(false);
        dataCell.setBottomBorderStyle(1);
        dataCell.setRightBorderStyle(1);
        dataCell.setHorzAlign(3);
        dataCell.setShowText(title);
        return dataCell;
    }

    static {
        Field[] fields;
        logger = LoggerFactory.getLogger(ProcessTrackPrint.class);
        PAPER_TYPE = new HashMap<String, Paper>();
        for (Field field : fields = Paper.class.getDeclaredFields()) {
            if (!field.getType().equals(Paper.class)) continue;
            try {
                Paper paper = (Paper)field.get(Paper.class);
                if (paper == null) continue;
                PAPER_TYPE.put(paper.getName(), paper);
            }
            catch (IllegalAccessException | IllegalArgumentException e) {
                logger.error(e.getMessage());
            }
        }
    }
}

