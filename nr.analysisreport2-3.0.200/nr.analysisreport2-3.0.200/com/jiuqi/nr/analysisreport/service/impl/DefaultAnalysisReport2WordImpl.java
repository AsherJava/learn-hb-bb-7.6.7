/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.analysisreport.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.service.IAnalysisReport2WordService;
import com.jiuqi.nr.analysisreport.utils.CustomXWPFDocument;
import com.jiuqi.nr.analysisreport.utils.WordUtil;
import com.jiuqi.nr.analysisreport.vo.ReportExportVO;
import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.stereotype.Component;

@Component
public class DefaultAnalysisReport2WordImpl
implements IAnalysisReport2WordService {
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void report2Word(HttpServletResponse response, ReportExportVO reportExportVO, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a", "\u5bfc\u51fa\u62a5\u544a\uff1a" + reportExportVO.getTitle(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        String fileName = "JoinCheer.pdf";
        if ("pdf".equals(reportExportVO.getExportType())) {
            fileName = "JoinCheer.docx";
        }
        ByteArrayOutputStream out = this.report2Word(reportExportVO, asyncTaskMonitor);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.getOutputStream().write(out.toByteArray());
        response.flushBuffer();
        out.close();
    }

    @Override
    public ByteArrayOutputStream report2Word(ReportExportVO reportExportVO, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        WordUtil wordUtil = new WordUtil();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if ("pdf".equals(reportExportVO.getExportType())) {
            wordUtil.setPDF(true);
            CustomXWPFDocument newword = wordUtil.createWord(reportExportVO, asyncTaskMonitor);
            ZipSecureFile.setMinInflateRatio(-1.0);
            wordUtil.createPDF(newword, out);
        } else {
            CustomXWPFDocument newword = wordUtil.createWord(reportExportVO, asyncTaskMonitor);
            newword.write(out);
        }
        return out;
    }
}

