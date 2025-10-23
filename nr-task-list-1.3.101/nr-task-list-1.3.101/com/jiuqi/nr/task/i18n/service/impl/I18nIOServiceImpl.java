/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.task.api.file.IFileAreaService
 *  com.jiuqi.nr.task.api.file.dto.FileAreaDTO
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.task.i18n.service.impl;

import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.task.api.file.IFileAreaService;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.vo.I18nExportVO;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import com.jiuqi.nr.task.i18n.factory.I18nWorkShopFactory;
import com.jiuqi.nr.task.i18n.provider.I18nServiceProvider;
import com.jiuqi.nr.task.i18n.service.I18nIOService;
import com.jiuqi.nr.task.i18n.workshop.I18nWorkShop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class I18nIOServiceImpl
implements I18nIOService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private I18nServiceProvider serviceProvider;
    @Autowired
    private IFileAreaService fileAreaService;
    private static final String DIR_PREFIX = "TASK_";

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void i18nImport(String fileKey) throws I18nException {
        LogHelper.info((String)"\u4efb\u52a1\u8bbe\u8ba12.0", (String)"\u591a\u8bed\u8a00\u5bfc\u5165", (String)"\u4efb\u52a1\u8bbe\u8ba12.0 \u591a\u8bed\u8a00\u53c2\u6570\u5bfc\u5165");
        byte[] fileData = this.fileAreaService.download(fileKey, new FileAreaDTO(true));
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
             ZipInputStream zip = new ZipInputStream(inputStream);){
            ZipEntry entry;
            DesignTaskDefine taskDefine = null;
            while ((entry = zip.getNextEntry()) != null) {
                int len;
                String entryName = entry.getName();
                if (entry.isDirectory()) {
                    int beginIndex = entryName.lastIndexOf(95);
                    int endIndex = entryName.lastIndexOf(47);
                    String taskCode = entryName.substring(beginIndex + 1, endIndex);
                    taskDefine = this.designTimeViewController.getTaskByCode(taskCode);
                    continue;
                }
                if (taskDefine == null) {
                    taskDefine = this.getTaskRetry(entryName);
                }
                if (!entryName.endsWith(".xlsx") || taskDefine == null) continue;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while ((len = zip.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                byte[] excelBytes = baos.toByteArray();
                ByteArrayInputStream excelInput = new ByteArrayInputStream(excelBytes);
                Throwable throwable = null;
                try {
                    Workbook workbook = WorkbookFactory.create(excelInput);
                    Throwable throwable2 = null;
                    try {
                        I18nWorkShop workShop;
                        I18nResourceType resourceType;
                        int beginIndex = entryName.lastIndexOf(47);
                        int endIndex = entryName.lastIndexOf(46);
                        String fileName = entryName.substring(beginIndex + 1, endIndex);
                        if (fileName.contains("-")) {
                            fileName = fileName.split("-")[0];
                        }
                        if ((resourceType = I18nResourceType.titleOf(fileName)) == null || (workShop = I18nWorkShopFactory.createWorkShop(resourceType, this.serviceProvider)) == null) continue;
                        workShop.dataImport(workbook, taskDefine);
                    }
                    catch (Throwable throwable3) {
                        throwable2 = throwable3;
                        throw throwable3;
                    }
                    finally {
                        if (workbook == null) continue;
                        if (throwable2 != null) {
                            try {
                                workbook.close();
                            }
                            catch (Throwable throwable4) {
                                throwable2.addSuppressed(throwable4);
                            }
                            continue;
                        }
                        workbook.close();
                    }
                }
                catch (Throwable throwable5) {
                    throwable = throwable5;
                    throw throwable5;
                }
                finally {
                    if (excelInput == null) continue;
                    if (throwable != null) {
                        try {
                            ((InputStream)excelInput).close();
                        }
                        catch (Throwable throwable6) {
                            throwable.addSuppressed(throwable6);
                        }
                        continue;
                    }
                    ((InputStream)excelInput).close();
                }
            }
        }
        catch (Exception e) {
            throw new I18nException(e.getMessage());
        }
    }

    @Override
    public void i18nExport(I18nExportVO exportVO, HttpServletResponse res) throws I18nException {
        LogHelper.info((String)"\u4efb\u52a1\u8bbe\u8ba12.0", (String)"\u591a\u8bed\u8a00\u5bfc\u51fa", (String)"\u4efb\u52a1\u8bbe\u8ba12.0 \u591a\u8bed\u8a00\u53c2\u6570\u5bfc\u51fa");
        this.buildHeader(res);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);
             ServletOutputStream out = res.getOutputStream();){
            for (String taskKey : exportVO.getExportTasks()) {
                DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
                if (task == null) continue;
                String dirName = DIR_PREFIX + task.getTaskCode();
                ZipEntry dirEntry = new ZipEntry(dirName + "/");
                zipOut.putNextEntry(dirEntry);
                for (Integer resourceValue : exportVO.getExportResourceTypes()) {
                    I18nResourceType resourceType = I18nResourceType.valueOf(resourceValue);
                    I18nWorkShop workShop = I18nWorkShopFactory.createWorkShop(resourceType, this.serviceProvider);
                    if (workShop == null) continue;
                    workShop.dataExport(new I18nExportParam(zipOut, task, dirEntry));
                }
                zipOut.closeEntry();
            }
            zipOut.close();
            out.write(byteArrayOutputStream.toByteArray());
            out.flush();
        }
        catch (Exception e) {
            throw new I18nException(e.getMessage(), e);
        }
    }

    @Override
    public String i18nUpload(MultipartFile file) throws I18nException {
        try {
            ZipSecureFile.setMinInflateRatio(0.002);
            FileInfoDTO fileInfoDTO = this.fileAreaService.fileUpload(file.getOriginalFilename(), file.getInputStream(), new FileAreaDTO(true));
            return fileInfoDTO == null ? null : fileInfoDTO.getKey();
        }
        catch (Exception e) {
            throw new I18nException("\u6587\u4ef6\u4e0a\u4f20\u5931\u8d25:" + e.getMessage(), e);
        }
    }

    private void buildHeader(HttpServletResponse res) {
        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/zip");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("I18nSet tingFile", "UTF-8") + ".zip");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private DesignTaskDefine getTaskRetry(String entryName) {
        DesignTaskDefine taskDefine = null;
        if (entryName.contains(DIR_PREFIX)) {
            int beginIndex = entryName.indexOf(DIR_PREFIX) + 5;
            int endIndex = entryName.lastIndexOf(47);
            String taskCode = entryName.substring(beginIndex, endIndex);
            taskDefine = this.designTimeViewController.getTaskByCode(taskCode);
        }
        return taskDefine;
    }
}

