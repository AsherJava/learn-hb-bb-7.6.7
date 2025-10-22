/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.Paper
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.grid.GridData;
import com.jiuqi.nr.bpm.de.dataflow.bean.PaperInfo;
import com.jiuqi.nr.bpm.de.dataflow.util.PDFPrintUtil2;
import com.jiuqi.nr.bpm.de.dataflow.util.ProcessTrackPrint;
import com.jiuqi.nr.bpm.de.dataflow.util.ProcessTrackPrintUntil;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackExcelInfo;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.Paper;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfPrint {
    private static final Logger logger = LoggerFactory.getLogger(PdfPrint.class);

    public static byte[] exportPdf(List<ProcessTrackExcelInfo> list, boolean isAllExport) {
        ITemplateDocument documentTemplateObject = null;
        try {
            Grid2Data grid2Data = ProcessTrackPrint.getGrid(list);
            GridData gridData = ProcessTrackPrintUntil.grid2DataToGridData(grid2Data, null);
            PaperInfo paperInfo = new PaperInfo();
            paperInfo.setPaperType(Paper.A4_PAPER.getSize());
            paperInfo.setMarginBottom(10.0);
            paperInfo.setMarginLeft(10.0);
            paperInfo.setMarginRight(10.0);
            paperInfo.setMarginTop(10.0);
            documentTemplateObject = ProcessTrackPrint.createPrintTemplateData(paperInfo, null, null, gridData, list);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        documentTemplateObject.setNature("REPORT_PRINT_NATURE");
        PDFPrintUtil2.printPDF(documentTemplateObject, (OutputStream)out);
        return out.toByteArray();
    }
}

