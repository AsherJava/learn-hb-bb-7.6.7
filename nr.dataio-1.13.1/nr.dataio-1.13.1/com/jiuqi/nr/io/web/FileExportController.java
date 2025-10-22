/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  io.swagger.annotations.Api
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.io.web;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.io.params.input.QueryParms;
import com.jiuqi.nr.io.service.FileExportService;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.io.util.ParamUtil;
import io.swagger.annotations.Api;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"FileExportController-\u5bfc\u51fa\u6587\u4ef6"})
public class FileExportController {
    private static final Logger log = LoggerFactory.getLogger(FileExportController.class);
    @Resource
    private Map<String, FileExportService> fileExportServices;

    @PostMapping(value={"/io/exportFile"})
    public void exportFileZip(@Valid @RequestBody QueryParms param, HttpServletResponse response, HttpServletRequest request) throws Exception {
        FileExportService fileService = null;
        String zipName = "";
        if (param.getFileType().equals(".json")) {
            fileService = this.fileExportServices.get("JsonExportServiceImpl");
            zipName = "ExportJsonDatas.zip";
        } else {
            fileService = this.fileExportServices.get("TxtExportServiceImpl");
            zipName = "ExportTxtDatas.zip";
        }
        AsyncTaskMonitor monitor = null;
        String path = fileService.getExtZipFile(ParamUtil.getAllParam(param), monitor);
        File file = new File(FilenameUtils.normalize(path));
        try (FileInputStream fis = new FileInputStream(file);){
            response.setContentType("application/force-download");
            response.addHeader("Content-disposition", "attachment;fileName=" + zipName);
            ServletOutputStream os = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
        }
        FileUtil.deleteFiles(path.replace("ExportTxtDatas.zip", ""));
    }

    @PostMapping(value={"/io/exportTxtStream"})
    public void exportFileStream(@Valid @RequestBody QueryParms param, HttpServletResponse response, HttpServletRequest request) throws Exception {
        FileExportService fileService = null;
        String zipName = "";
        if (param.getFileType().equals(".json")) {
            fileService = this.fileExportServices.get("JsonExportServiceImpl");
            zipName = "ExportJsonDatas.zip";
        } else {
            fileService = this.fileExportServices.get("TxtExportServiceImpl");
            zipName = "ExportTxtDatas.zip";
        }
        AsyncTaskMonitor monitor = null;
        String agent = request.getHeader("User-Agent").toLowerCase();
        ZipOutputStream zipos = null;
        try {
            zipName = agent.contains("MSIE") || agent.contains("Trident") ? URLEncoder.encode(zipName, "UTF-8") : new String(zipName.getBytes("UTF-8"), "ISO-8859-1");
        }
        catch (Exception e) {
            log.info("\u6587\u4ef6\u540dencode\u51fa\u9519{}", e);
        }
        response.addHeader("Content-disposition", "attachment;fileName=" + zipName);
        response.setContentType("application/octet-stream");
        zipos = new ZipOutputStream(new BufferedOutputStream((OutputStream)response.getOutputStream()));
        zipos.setMethod(8);
        fileService.getExtZipOutputStream(ParamUtil.getRealParam(param), zipos, monitor);
    }
}

