/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil$FILE_TYPE_TO_PDF
 *  com.jiuqi.nr.datareport.obj.ReportDataParam
 *  com.jiuqi.nr.datareport.obj.ReportQueryParam
 *  com.jiuqi.nr.datareport.obj.ReportTemplateObj
 *  com.jiuqi.nr.datareport.service.IReportService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.dataentry.report.rest;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.convert.pdf.utils.ConvertUtil;
import com.jiuqi.nr.dataentry.report.async.RptBatchDLTask;
import com.jiuqi.nr.dataentry.report.constant.ReportErrorEnum;
import com.jiuqi.nr.dataentry.report.helper.ReportHelper;
import com.jiuqi.nr.dataentry.report.rest.vo.DownloadReportParamsObj;
import com.jiuqi.nr.dataentry.report.rest.vo.QueryReportParmsObj;
import com.jiuqi.nr.dataentry.report.rest.vo.ReportObj;
import com.jiuqi.nr.dataentry.report.rest.vo.ReportTemplateObj;
import com.jiuqi.nr.datareport.obj.ReportDataParam;
import com.jiuqi.nr.datareport.obj.ReportQueryParam;
import com.jiuqi.nr.datareport.service.IReportService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u62a5\u544a\u4e0b\u8f7d\u76f8\u5173\u670d\u52a1"})
public class DownloadReportController {
    @Autowired
    private ReportHelper reportHelper;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IJtableDataEngineService tableDataEngineService;
    private static final Logger logger = LoggerFactory.getLogger(DownloadReportController.class);

    @ApiOperation(value="\u8fd0\u884c\u671f\u67e5\u8be2\u5f53\u524d\u65b9\u6848\u4e0b\u6ee1\u8db3\u9002\u5e94\u6761\u4ef6\u7684\u6240\u6709\u62a5\u544a\u6a21\u677f")
    @RequestMapping(value={"query-report"}, method={RequestMethod.POST})
    public List<ReportTemplateObj> queryReport(@RequestBody QueryReportParmsObj params) {
        ReportQueryParam reportQueryParam = this.reportHelper.getReportQueryParam(params);
        JtableContext jtableContext = this.reportHelper.initContext(params);
        ExecutorContext executorContext = this.tableDataEngineService.getExecutorContext(jtableContext);
        ArrayList<ReportTemplateObj> result = new ArrayList<ReportTemplateObj>();
        try {
            this.reportService.list(reportQueryParam, executorContext).forEach(o -> result.add(new ReportTemplateObj((com.jiuqi.nr.datareport.obj.ReportTemplateObj)o)));
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6570\u636e\u62a5\u544a\u6a21\u677f\u5f02\u5e38:" + e.getMessage(), e);
        }
        return result;
    }

    @ApiOperation(value="\u4e0b\u8f7d\u62a5\u544a")
    @RequestMapping(value={"download-report"}, method={RequestMethod.POST})
    public void downloadReport(@RequestBody DownloadReportParamsObj downloadParams, HttpServletResponse response) throws JQException, IOException {
        List<ReportObj> reportObjList = downloadParams.getReportObjList();
        if (CollectionUtils.isEmpty(reportObjList)) {
            throw new JQException((ErrorEnum)ReportErrorEnum.NRDESINGER_EXCEPTION_200);
        }
        JtableContext jtableContext = this.reportHelper.initContext(downloadParams);
        ExecutorContext executorContext = this.tableDataEngineService.getExecutorContext(jtableContext);
        ServletOutputStream outputStream = null;
        ReportObj reportObj = reportObjList.get(0);
        ReportDataParam reportDataParam = this.reportHelper.getReportDataParam(downloadParams).get(0);
        String fileNameExp = reportObj.getFileNameExp() == null ? "\u62a5\u544a" : reportObj.getFileNameExp();
        String fileKey = reportObj.getFileKey();
        String fileSuffix = fileKey.substring(fileKey.lastIndexOf("."));
        String downloadName = downloadParams.getDwmc() + downloadParams.getCurPeriodTitle() + fileNameExp + fileSuffix;
        try {
            byte[] bytes = this.reportService.export(reportDataParam, executorContext);
            this.reportHelper.extracted(response, downloadName);
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ReportErrorEnum.NRDESINGER_EXCEPTION_204, (Throwable)e);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @ApiOperation(value="\u9884\u89c8\u62a5\u544a")
    @RequestMapping(value={"preview-report"}, method={RequestMethod.POST})
    public void previewReport(@RequestBody DownloadReportParamsObj downloadParams, HttpServletResponse response) throws JQException, IOException {
        List<ReportObj> reportObjList = downloadParams.getReportObjList();
        if (CollectionUtils.isEmpty(reportObjList)) {
            throw new JQException((ErrorEnum)ReportErrorEnum.NRDESINGER_EXCEPTION_200);
        }
        JtableContext jtableContext = this.reportHelper.initContext(downloadParams);
        ExecutorContext executorContext = this.tableDataEngineService.getExecutorContext(jtableContext);
        ServletOutputStream outputStream = null;
        ReportObj reportObj = reportObjList.get(0);
        ReportDataParam reportDataParam = this.reportHelper.getReportDataParam(downloadParams).get(0);
        String fileKey = reportObj.getFileKey();
        String extension = fileKey.substring(fileKey.lastIndexOf("."));
        try {
            byte[] bytes = this.reportService.export(reportDataParam, executorContext);
            response.setContentType("application/pdf");
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setHeader("Content-disposition", "inline");
            outputStream = response.getOutputStream();
            try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);){
                byte[] bytesPdf = new byte[]{};
                if (extension.endsWith("doc") || extension.endsWith("wps")) {
                    bytesPdf = ConvertUtil.convertToPdf((InputStream)in, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.DOC);
                } else if (extension.endsWith("docx")) {
                    bytesPdf = ConvertUtil.convertToPdf((InputStream)in, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.DOCX);
                }
                outputStream.write(bytesPdf);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ReportErrorEnum.NRDESINGER_EXCEPTION_205, (Throwable)e);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
                response.flushBuffer();
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    @ApiOperation(value="\u6279\u91cf\u4e0b\u8f7d\u62a5\u544a")
    @RequestMapping(value={"batch-download-report"}, method={RequestMethod.POST})
    public AsyncTaskInfo batchDownloadReport(@RequestBody DownloadReportParamsObj downloadParams) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(downloadParams.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(downloadParams.getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)downloadParams));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new RptBatchDLTask());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asyncTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @ApiOperation(value="\u4e0b\u8f7d\u6279\u91cf\u5bfc\u51fa\u7684\u62a5\u544a\u538b\u7f29\u5305")
    @PostMapping(value={"download-report-zip/{fileKey}"})
    public void downloadReportZip(HttpServletResponse response, @PathVariable String fileKey) throws IOException {
        this.reportHelper.downloadZipFile(fileKey, response);
    }
}

