/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.batch.gather.gzw.service.BatchGatherGZWConditionEditService
 *  com.jiuqi.nr.batch.gather.gzw.service.entity.ApiResponseResult
 *  com.jiuqi.nr.batch.gather.gzw.service.entity.BatchGatherExportParam
 *  com.jiuqi.nr.batch.gather.gzw.service.entity.UploadParam
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.batch.gather.gzw.web;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.batch.gather.gzw.service.BatchGatherGZWConditionEditService;
import com.jiuqi.nr.batch.gather.gzw.service.entity.ApiResponseResult;
import com.jiuqi.nr.batch.gather.gzw.service.entity.BatchGatherExportParam;
import com.jiuqi.nr.batch.gather.gzw.service.entity.UploadParam;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/batch-gather-GZW/scheme"})
@Api(tags={"\u6c47\u603b\u65b9\u6848-\u81ea\u5b9a\u4e49\u5206\u7c7b\u6c47\u603b\u6761\u4ef6"})
public class BatchGatherGZWConditionEditController {
    @Autowired
    private BatchGatherGZWConditionEditService batchGatherGZWConditionEditService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(BatchGatherGZWConditionEditController.class);

    @PostMapping(value={"/batchGatherGZW-export"})
    @ApiOperation(value="\u6279\u91cf\u6c47\u603b\u81ea\u5b9a\u4e49\u53e3\u5f84\u7684EXCEL\u5bfc\u51fa")
    @NRContextBuild
    public void batchGatherGZWExport(@RequestBody BatchGatherExportParam batchGatherExportParam, HttpServletResponse response, HttpServletRequest request) {
        SXSSFWorkbook workbook = null;
        String CustomCategoryGatherCondition = this.i18nHelper.getMessage("Custom-Category-Gather-Condition").equals("") ? "\u81ea\u5b9a\u4e49\u5206\u7c7b\u6c47\u603b\u6761\u4ef6" : this.i18nHelper.getMessage("Custom-Category-Gather-Condition");
        try {
            workbook = this.batchGatherGZWConditionEditService.CustomCalibreExport(batchGatherExportParam);
            if (workbook != null) {
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(CustomCategoryGatherCondition + ".xlsx", "UTF-8"));
                response.setContentType("application/octet-stream");
                ServletOutputStream outputStream = response.getOutputStream();
                workbook.write((OutputStream)outputStream);
                response.flushBuffer();
            }
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/batchGatherGZW-import"})
    @ApiOperation(value="\u6279\u91cf\u6c47\u603b\u81ea\u5b9a\u4e49\u53e3\u5f84\u7684EXCEL\u5bfc\u5165")
    @NRContextBuild
    public ApiResponseResult batchGatherGZWImport(@RequestBody UploadParam param) {
        String fileName = param.getFileNameInfo();
        List customCalibreRows = null;
        try {
            PathUtils.validatePathManipulation((String)fileName);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        String fileInfoKey = "";
        File file = null;
        File pathFile = null;
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
        String path = BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
        pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        if (StringUtils.isNotEmpty((String)param.getFileKeyOfSOss())) {
            file = new File(pathFile.getPath() + BatchExportConsts.SEPARATOR + param.getFileNameInfo());
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                this.fileUploadOssService.downloadFileFormTemp(param.getFileKeyOfSOss(), (OutputStream)fileOutputStream);
                fileInfoKey = param.getFileKeyOfSOss();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else {
            FileInfo fileInfo = this.fileService.tempArea().getInfo(param.getFileKey());
            file = new File(pathFile.getPath() + BatchExportConsts.SEPARATOR + fileInfo.getName());
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                this.fileService.tempArea().download(fileInfo.getKey(), (OutputStream)fileOutputStream);
                fileInfoKey = fileInfo.getKey();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            customCalibreRows = this.batchGatherGZWConditionEditService.CustomCalibreImport(sheet, param.getScheme());
            if (customCalibreRows == null) {
                ApiResponseResult apiResponseResult = new ApiResponseResult(true, customCalibreRows);
                return apiResponseResult;
            }
            workbook.close();
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            String finalFileInfoKey = fileInfoKey;
            if (StringUtils.isNotEmpty((String)finalFileInfoKey) && StringUtils.isEmpty((String)param.getFileKeyOfSOss())) {
                try {
                    this.fileService.tempArea().delete(finalFileInfoKey, Boolean.valueOf(false));
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            try {
                if (!StringUtils.isNotEmpty((String)param.getFilePath()) && file != null && file.exists()) {
                    file.delete();
                }
                if (!StringUtils.isNotEmpty((String)param.getFilePath())) {
                    pathFile.delete();
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return new ApiResponseResult(true, customCalibreRows);
    }
}

