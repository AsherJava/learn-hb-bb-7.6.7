/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.snapshot.input.JudgeNameContext
 *  com.jiuqi.nr.snapshot.service.SnapshotService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataSnapshot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotExportParam;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotFormInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotParam;
import com.jiuqi.nr.dataSnapshot.param.DifferenceResult;
import com.jiuqi.nr.dataSnapshot.service.IDataSnapshotService;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.snapshot.input.JudgeNameContext;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataSnapshot/actions"})
@Api(tags={"\u5feb\u7167\u64cd\u4f5c"})
public class ActionController {
    private static final Logger logger = LoggerFactory.getLogger(ActionController.class);
    @Autowired
    private IDataSnapshotService dataSnapshotService;
    @Autowired
    private SnapshotService snapshotService;

    @PostMapping(value={"/data-snapshot/query"})
    @ApiOperation(value="\u67e5\u8be2\u5feb\u7167")
    @NRContextBuild
    public List<DataSnapshotInfo> queryDataSnapshot(@RequestBody DataSnapshotParam param) {
        return this.dataSnapshotService.queryDataSnapshot(param);
    }

    @PostMapping(value={"/data-snapshot/create"})
    @ApiOperation(value="\u521b\u5efa\u5feb\u7167")
    @NRContextBuild
    public AsyncTaskInfo createDataSnapshot(@RequestBody DataSnapshotParam param) {
        return this.dataSnapshotService.createDataSnapshot(param);
    }

    @PostMapping(value={"/data-snapshot/update"})
    @ApiOperation(value="\u66f4\u65b0\u5feb\u7167")
    @NRContextBuild
    public ReturnInfo updateDataSnapshot(@RequestBody DataSnapshotParam param) {
        return this.dataSnapshotService.updateDataSnapshot(param);
    }

    @PostMapping(value={"/data-snapshot/delete"})
    @ApiOperation(value="\u5220\u9664\u5feb\u7167")
    @NRContextBuild
    public ReturnInfo deleteDataSnapshot(@RequestBody DataSnapshotParam param) {
        return this.dataSnapshotService.deleteDataSnapshot(param);
    }

    @PostMapping(value={"/data-snapshot/restore"})
    @ApiOperation(value="\u8fd8\u539f\u5feb\u7167")
    @NRContextBuild
    public ReturnInfo restoreDataSnapshot(@RequestBody DataSnapshotParam param) {
        return this.dataSnapshotService.restoreDataSnapshot(param);
    }

    @PostMapping(value={"/data-snapshot/compareReturnTotal"})
    @ApiOperation(value="\u5feb\u7167\u5bf9\u6bd4\u6c47\u603b\u7ed3\u679c")
    @NRContextBuild
    public Map<String, Integer> compareDataSnapshotReturnTotal(@RequestBody DataSnapshotParam param) {
        return this.dataSnapshotService.compareDataSnapshotReturnTotal(param);
    }

    @PostMapping(value={"/data-snapshot/compare"})
    @ApiOperation(value="\u5bf9\u6bd4\u5feb\u7167")
    @NRContextBuild
    public DifferenceResult compareDataSnapshot(@RequestBody DataSnapshotParam param) {
        return this.dataSnapshotService.compareDataSnapshot(param);
    }

    @PostMapping(value={"/data-snapshot/queryFormList"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u548c\u62a5\u8868")
    public List<DataSnapshotFormInfo> queryFormListOfSnapshot(String taskKey) {
        return this.dataSnapshotService.queryFormList(taskKey);
    }

    @CrossOrigin(value={"*"})
    @PostMapping(value={"/data-snapshot/compareResultExport"})
    @ApiOperation(value="\u5feb\u7167\u5bf9\u6bd4\u7ed3\u679cEXCEL\u5bfc\u51fa")
    @NRContextBuild
    public void exportSnapshotCompareResult(@RequestBody DataSnapshotExportParam info, HttpServletResponse response, HttpServletRequest request) {
        try {
            ExportData exportData = this.dataSnapshotService.dataSnapshotCompareResultExport(info);
            if (null != exportData) {
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(exportData.getFileName(), "UTF-8"));
                response.setContentType("application/octet-stream");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(exportData.getData());
                response.flushBuffer();
            }
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @CrossOrigin(value={"*"})
    @PostMapping(value={"/data-snapshot/compareResultBatchExport"})
    @ApiOperation(value="\u5feb\u7167\u5bf9\u6bd4\u7ed3\u679cEXCEL\u5bfc\u51fa")
    @NRContextBuild
    public void batchExportSnapshotCompareResult(@RequestBody DataSnapshotExportParam info, HttpServletResponse response, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<ExportData> exportData = this.dataSnapshotService.dataSnapshotCompareResultBatchExport(info);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String formatDate = dateFormat.format(date);
            String resultLocation = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + NpContextHolder.getContext().getUser().getName() + BatchExportConsts.SEPARATOR + formatDate + BatchExportConsts.SEPARATOR + UUID.randomUUID().toString();
            File filei = new File(FilenameUtils.normalize(resultLocation));
            if (!filei.exists()) {
                filei.mkdirs();
            }
            PathUtils.validatePathManipulation((String)(resultLocation + BatchExportConsts.SEPARATOR + info.getUnitTitle()));
            File file = new File(FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + info.getUnitTitle() + ".zip"));
            try (FileOutputStream fileOutputStream = new FileOutputStream(FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + info.getUnitTitle() + ".zip"));
                 ZipOutputStream zos = new ZipOutputStream(fileOutputStream);){
                for (ExportData data : exportData) {
                    byte[] databytes;
                    if (data == null || null == (databytes = data.getData()) || databytes.length <= 0) continue;
                    byte[] bufs = new byte[0xA00000];
                    String filePath = data.getFileName();
                    zos.putNextEntry(new ZipEntry(filePath));
                    ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData());
                    Throwable throwable = null;
                    try {
                        BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                        Throwable throwable2 = null;
                        try {
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (bis == null) continue;
                            if (throwable2 != null) {
                                try {
                                    bis.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            bis.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (swapStream == null) continue;
                        if (throwable != null) {
                            try {
                                swapStream.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        swapStream.close();
                    }
                }
            }
            byte[] bytes = new byte[(int)file.length()];
            try (FileInputStream fis = new FileInputStream(file);){
                fis.read(bytes);
            }
            HtmlUtils.validateHeaderValue((String)("attachment; filename*=UTF-8''" + URLEncoder.encode(info.getUnitTitle())));
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(info.getUnitTitle(), "UTF-8"));
            response.setContentType("application/octet-stream");
            var14_16 = null;
            try (ServletOutputStream outputStream = response.getOutputStream();){
                outputStream.write(bytes);
            }
            catch (Throwable throwable) {
                var14_16 = throwable;
                throw throwable;
            }
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        catch (SecurityContentException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value={"/data-snapshot/checkDulicateName"})
    @ApiOperation(value="\u8fd8\u539f\u5feb\u7167")
    @NRContextBuild
    public ReturnInfo checkDataSnapshotDuplicate(@RequestBody DataSnapshotParam param) {
        JudgeNameContext judgeNameContext = new JudgeNameContext();
        judgeNameContext.setSnapshotId(param.getSnapshotId());
        judgeNameContext.setTitle(param.getTitle());
        judgeNameContext.setTaskKey(param.getContext().getTaskKey());
        judgeNameContext.setDimensionValueSet(DimensionValueSetUtil.getDimensionValueSet((JtableContext)param.getContext()));
        ReturnInfo returnInfo = new ReturnInfo();
        if (this.snapshotService.judgeDuplicateNames(judgeNameContext)) {
            returnInfo.setMessage("success");
        } else {
            returnInfo.setMessage("fail");
        }
        return returnInfo;
    }

    @PostMapping(value={"/data-snapshot/querySystemParam"})
    @ApiOperation(value="\u67e5\u8be2\u5feb\u7167\u7cfb\u7edf\u53c2\u6570")
    public Map<String, Object> querySystemParam(String taskKey) {
        return this.dataSnapshotService.querySystemParam(taskKey);
    }
}

