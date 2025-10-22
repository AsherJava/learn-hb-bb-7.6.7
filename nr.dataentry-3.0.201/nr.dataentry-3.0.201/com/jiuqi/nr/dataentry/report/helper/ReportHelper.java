/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.datareport.obj.ReportDataParam
 *  com.jiuqi.nr.datareport.obj.ReportEnv
 *  com.jiuqi.nr.datareport.obj.ReportObj
 *  com.jiuqi.nr.datareport.obj.ReportQueryParam
 *  com.jiuqi.nr.datareport.service.IReportService
 *  com.jiuqi.nr.file.FileAreaConfig
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.dataentry.report.helper;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.dataentry.report.constant.ReportErrorEnum;
import com.jiuqi.nr.dataentry.report.rest.vo.DownloadReportParamsObj;
import com.jiuqi.nr.dataentry.report.rest.vo.QueryReportParmsObj;
import com.jiuqi.nr.datareport.obj.ReportDataParam;
import com.jiuqi.nr.datareport.obj.ReportEnv;
import com.jiuqi.nr.datareport.obj.ReportObj;
import com.jiuqi.nr.datareport.obj.ReportQueryParam;
import com.jiuqi.nr.datareport.service.IReportService;
import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportHelper {
    private static final Logger logger = LoggerFactory.getLogger(ReportHelper.class);
    @Autowired
    private IReportService reportService;
    @Autowired
    private IJtableDataEngineService tableDataEngineService;
    private FileAreaService fileAreaService;
    private static final String FILE_AREA_NAME = "TEMP";
    protected static final FileAreaConfig FILE_ARER_CONFIG = new FileAreaConfig(){

        public String getName() {
            return ReportHelper.FILE_AREA_NAME;
        }

        public long getMaxFileSize() {
            return 0x6400000L;
        }

        public long getExpirationTime() {
            return super.getExpirationTime();
        }

        public boolean isEnableRecycleBin() {
            return false;
        }

        public boolean isEnableEncrypt() {
            return false;
        }

        public boolean isEnableFastDownload() {
            return false;
        }

        public boolean isHashFileDate() {
            return super.isHashFileDate();
        }
    };

    @Autowired
    private void getFileArea(FileService fileService) {
        this.fileAreaService = fileService.area(FILE_ARER_CONFIG);
    }

    public ReportQueryParam getReportQueryParam(QueryReportParmsObj param) {
        ReportQueryParam reportQueryParam = new ReportQueryParam();
        reportQueryParam.setFormSchemeKey(param.getFormSchemeKey());
        reportQueryParam.setDimensionSet(param.getDimensionSet());
        return reportQueryParam;
    }

    public List<ReportDataParam> getReportDataParam(DownloadReportParamsObj obj) {
        ArrayList<ReportDataParam> params = new ArrayList<ReportDataParam>();
        ReportEnv reportEnv = new ReportEnv();
        reportEnv.setDimensionSet(obj.getDimensionSet());
        reportEnv.setFormSchemeKey(obj.getFormSchemeKey());
        reportEnv.setFormulaSchemeKey(obj.getFormulaSchemeKey());
        for (com.jiuqi.nr.dataentry.report.rest.vo.ReportObj o : obj.getReportObjList()) {
            ReportDataParam reportDataParam = new ReportDataParam();
            reportDataParam.setReportEnv(reportEnv);
            reportDataParam.setInjectContext(obj.getInjectContext());
            ReportObj reportObj = new ReportObj();
            reportObj.setKey(o.getKey());
            reportObj.setFileKey(o.getFileKey());
            reportDataParam.setReportObj(reportObj);
            params.add(reportDataParam);
        }
        return params;
    }

    public JtableContext initContext(DownloadReportParamsObj params) {
        JtableContext executorTableContext = new JtableContext();
        executorTableContext.setTaskKey(params.getTaskKey());
        executorTableContext.setFormSchemeKey(params.getFormSchemeKey());
        executorTableContext.setFormulaSchemeKey(params.getFormulaSchemeKey());
        executorTableContext.setDimensionSet(params.getDimensionSet());
        return executorTableContext;
    }

    public JtableContext initContext(QueryReportParmsObj params) {
        JtableContext executorTableContext = new JtableContext();
        executorTableContext.setTaskKey(params.getTaskKey());
        executorTableContext.setFormSchemeKey(params.getFormSchemeKey());
        executorTableContext.setDimensionSet(params.getDimensionSet());
        return executorTableContext;
    }

    public void extracted(HttpServletResponse response, String fileName) throws IOException {
        if (fileName == null || fileName.contains("..") || fileName.contains("\r") || fileName.contains("\n")) {
            throw new IOException("Invalid file name");
        }
        fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
        fileName = "attachment;filename=" + fileName;
        response.setHeader("Content-disposition", fileName);
        response.setContentType("application/octet-stream");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }

    public void batchExport(DownloadReportParamsObj downloadParams, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        List<com.jiuqi.nr.dataentry.report.rest.vo.ReportObj> reportObjList = downloadParams.getReportObjList();
        JtableContext jtableContext = this.initContext(downloadParams);
        double fileNum = reportObjList.size();
        double count = 1.0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        HashSet<String> curFileNameSet = new HashSet<String>();
        ExecutorContext executorContext = this.tableDataEngineService.getExecutorContext(jtableContext);
        List<ReportDataParam> params = this.getReportDataParam(downloadParams);
        try {
            int i = 0;
            while ((double)i < fileNum) {
                com.jiuqi.nr.dataentry.report.rest.vo.ReportObj reportObj = reportObjList.get(i);
                ReportDataParam reportDataParam = params.get(i);
                String fileNameExp = reportObj.getFileNameExp() == null ? "\u62a5\u544a" : reportObj.getFileNameExp();
                String fileKey = reportObj.getFileKey();
                String fileSuffix = fileKey.substring(fileKey.lastIndexOf("."));
                String downloadNameTemp = downloadParams.getDwmc() + downloadParams.getCurPeriodTitle() + fileNameExp;
                String downloadName = downloadNameTemp;
                if (!curFileNameSet.add(downloadName)) {
                    boolean exists;
                    int idx = 1;
                    block6: do {
                        exists = false;
                        downloadName = downloadNameTemp + "(" + idx++ + ")";
                        for (String fileName : curFileNameSet) {
                            if (fileName == null || !fileName.equals(downloadName)) continue;
                            exists = true;
                            continue block6;
                        }
                    } while (exists);
                    curFileNameSet.add(downloadName);
                }
                ZipEntry zipEntry = new ZipEntry(downloadName + fileSuffix);
                zipOutputStream.putNextEntry(zipEntry);
                byte[] bytes = this.reportService.export(reportDataParam, executorContext);
                zipOutputStream.write(bytes);
                asyncTaskMonitor.progressAndMessage(count / fileNum * 0.99, "");
                count += 1.0;
                ++i;
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)ReportErrorEnum.NRDESINGER_EXCEPTION_202, (Throwable)e);
        }
        finally {
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            outputStream.close();
        }
        ByteArrayInputStream in = new ByteArrayInputStream(outputStream.toByteArray());
        String zipName = downloadParams.getDwmc() + downloadParams.getCurPeriodTitle() + "\u62a5\u544a.zip";
        String fileKey = this.fileAreaService.upload(zipName, (InputStream)in).getKey();
        String downLoad = "download_success_info";
        asyncTaskMonitor.finish(downLoad, (Object)fileKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void downloadZipFile(String fileKey, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            FileInfo info = this.fileAreaService.getInfo(fileKey);
            String fileName = info.getName();
            this.extracted(response, fileName);
            outputStream = response.getOutputStream();
            this.fileAreaService.download(fileKey, (OutputStream)outputStream);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}

