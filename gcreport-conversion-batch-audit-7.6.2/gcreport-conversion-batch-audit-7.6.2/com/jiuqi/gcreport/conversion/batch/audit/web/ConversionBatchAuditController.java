/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gc.inspector.common.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.conversion.batch.audit.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gc.inspector.common.BusinessRuntimeException;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditEntity;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditFileEntity;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditRunnerEntity;
import com.jiuqi.gcreport.conversion.batch.audit.service.ConversionBatchAuditService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionBatchAuditController {
    private static final String GC_API_BASE_PATH = "/api/gcreport/v1/conversionBatchAudit";
    private static final Logger logger = LoggerFactory.getLogger(ConversionBatchAuditController.class);
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private IFuncExecuteService funcExecuteService;
    @Resource
    private DefinitionAuthorityProvider authorityProvider;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private ConversionBatchAuditService conversionBatchAuditService;

    @GetMapping(value={"/api/gcreport/v1/conversionBatchAudit/getFormSchemeForTaskKey"})
    public BusinessResponseEntity<Object> getFormSchemeForTaskKey(String taskKey) throws Exception {
        List formSchemeDefineList = this.iRunTimeViewController.queryFormSchemeByTask(taskKey).stream().filter(formSchemeDefine -> this.authorityProvider.canReadFormScheme(formSchemeDefine.getKey())).collect(Collectors.toList());
        ConversionBatchAuditEntity conversionBatchAuditEntity = new ConversionBatchAuditEntity();
        for (FormSchemeDefine schemeDefine : formSchemeDefineList) {
            Scheme scheme = new Scheme();
            PeriodWrapper periodWrapper = TaskPeriodUtils.getCurrentPeriod((int)schemeDefine.getPeriodType().type());
            FormSchemeDefine formSchemeDefine2 = this.funcExecuteService.queryFormScheme(taskKey, periodWrapper.toString());
            if (formSchemeDefine2 == null) continue;
            TaskPeriodUtils.setSchemeTimeByFormSchemeDefine((Scheme)scheme, (FormSchemeDefine)formSchemeDefine2);
            TaskPeriodUtils.setDefaultTime((Scheme)scheme, (FormSchemeDefine)formSchemeDefine2);
            conversionBatchAuditEntity.setScheme(scheme);
            conversionBatchAuditEntity.setPeriodStr(periodWrapper.toString());
        }
        return BusinessResponseEntity.ok((Object)conversionBatchAuditEntity);
    }

    @GetMapping(value={"/api/gcreport/v1/conversionBatchAudit/getAllBatchAudit"})
    public BusinessResponseEntity<Map<String, Object>> getAllBatchAudit(ConversionBatchAuditRunnerEntity entity) {
        return BusinessResponseEntity.ok(this.conversionBatchAuditService.getAllBatchAudit(entity));
    }

    @PostMapping(value={"/api/gcreport/v1/conversionBatchAudit/deleteSelectBatchAudit"})
    public BusinessResponseEntity<Object> deleteSelectBatchAudit(@RequestBody List<String> idList) {
        this.conversionBatchAuditService.deleteSelectBatchAudit(idList);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u62a5\u544a\u6210\u529f\u3002");
    }

    @PostMapping(value={"/api/gcreport/v1/conversionBatchAudit/addBatchAudit"})
    public BusinessResponseEntity<Object> addBatchAudit(@RequestBody String exportParam) {
        ConversionBatchAuditRunnerEntity entity = (ConversionBatchAuditRunnerEntity)JsonUtils.readValue((String)exportParam, ConversionBatchAuditRunnerEntity.class);
        entity.setCreateUser(NpContextHolder.getContext().getUser().getName());
        PeriodWrapper periodWrapper = this.conversionBatchAuditService.getParamPeriod(entity);
        entity.setAcctYear(String.valueOf(periodWrapper.getYear()));
        entity.setAcctPeriod(String.valueOf(periodWrapper.getPeriod()));
        Blob blob = this.conversionBatchAuditService.getFileBlob(entity, periodWrapper);
        if (blob == null) {
            logger.info("\u5916\u5e01\u7a3d\u6838\u751f\u6210\u62a5\u544a\u6267\u884c\u5931\u8d25");
            throw new BusinessRuntimeException("\u5916\u5e01\u7a3d\u6838\u751f\u6210\u62a5\u544a\u6267\u884c\u5931\u8d25");
        }
        entity.setFileData(blob);
        entity.setFileName("\u5916\u5e01\u6279\u91cf\u7a3d\u6838" + this.conversionBatchAuditService.getFileNameForEntity() + ".xlsx");
        this.conversionBatchAuditService.addAudit(entity);
        return BusinessResponseEntity.ok((Object)"\u65b0\u589e\u62a5\u544a\u6210\u529f\u3002");
    }

    @PostMapping(value={"/api/gcreport/v1/conversionBatchAudit/exportExcel"})
    public void exportExcel(HttpServletResponse response, @RequestBody ConversionBatchAuditFileEntity entity) {
        try {
            List<ConversionBatchAuditFileEntity> fileEntities = this.conversionBatchAuditService.getFileListForId(entity);
            if (fileEntities.size() == 1) {
                this.exportExcel(fileEntities.get(0), response);
            } else if (fileEntities.size() > 1) {
                this.exportZip(fileEntities, response);
            }
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u6587\u4ef6\u9519\u8bef\u3002", e);
            throw new BusinessRuntimeException("\u5bfc\u51fa\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u6587\u4ef6\u9519\u8bef");
        }
    }

    private void exportZip(List<ConversionBatchAuditFileEntity> fileEntities, HttpServletResponse response) throws IOException {
        String filePath;
        File outFile;
        block61: {
            OutputStream outputStream = null;
            outFile = null;
            filePath = null;
            try {
                String innerPath = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + NpContextHolder.getContext().getUserName() + BatchExportConsts.SEPARATOR + LocalDate.now() + BatchExportConsts.SEPARATOR + UUID.randomUUID();
                filePath = innerPath + File.separator;
                String fileName = "\u5916\u5e01\u7a3d\u6838\u62a5\u544a" + this.conversionBatchAuditService.getFileNameForEntity() + ".zip";
                outFile = new File(filePath + fileName);
                File fileParent = outFile.getParentFile();
                if (!fileParent.exists()) {
                    fileParent.mkdirs();
                }
                outFile.createNewFile();
                try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                     ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);){
                    for (ConversionBatchAuditFileEntity fileEntity : fileEntities) {
                        int read;
                        Blob blob = fileEntity.getFileData();
                        InputStream inputStream = blob.getBinaryStream();
                        ZipEntry entry = new ZipEntry(fileEntity.getFileName());
                        zipOutputStream.putNextEntry(entry);
                        BufferedInputStream bis = new BufferedInputStream(inputStream, 10240);
                        byte[] bufs = new byte[0xA00000];
                        while ((read = bis.read(bufs, 0, 10240)) != -1) {
                            zipOutputStream.write(bufs, 0, read);
                        }
                        bis.close();
                        inputStream.close();
                    }
                }
                response.setHeader("Charset", "UTF-8");
                response.setHeader("Content-Type", "application/force-download");
                response.setHeader("Content-Type", "application/zip");
                response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                outputStream = response.getOutputStream();
                byte[] buffer = new byte[0xA00000];
                File file = new File(filePath + fileName);
                FileInputStream fileInputStream = new FileInputStream(file);
                Object object = null;
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);){
                    int len;
                    while ((len = bufferedInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                }
                catch (Throwable throwable) {
                    object = throwable;
                    throw throwable;
                }
                finally {
                    if (fileInputStream != null) {
                        if (object != null) {
                            try {
                                fileInputStream.close();
                            }
                            catch (Throwable throwable) {
                                ((Throwable)object).addSuppressed(throwable);
                            }
                        } else {
                            fileInputStream.close();
                        }
                    }
                }
                if (outputStream == null) break block61;
            }
            catch (Exception e) {
                try {
                    logger.error("\u5bfc\u51fa\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u538b\u7f29\u6587\u4ef6\u9519\u8bef\u3002", e);
                    throw new BusinessRuntimeException("\u5bfc\u51fa\u5916\u5e01\u7a3d\u6838\u62a5\u544a\u538b\u7f29\u6587\u4ef6\u9519\u8bef");
                }
                catch (Throwable throwable) {
                    if (outputStream != null) {
                        outputStream.flush();
                        outputStream.close();
                    }
                    if (outFile != null && outFile.exists()) {
                        outFile.delete();
                    }
                    File catalogue = new File(filePath);
                    catalogue.delete();
                    throw throwable;
                }
            }
            outputStream.flush();
            outputStream.close();
        }
        if (outFile != null && outFile.exists()) {
            outFile.delete();
        }
        File catalogue = new File(filePath);
        catalogue.delete();
    }

    private void exportExcel(ConversionBatchAuditFileEntity fileEntity, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            int len;
            Blob blob = fileEntity.getFileData();
            InputStream inputStream = blob.getBinaryStream();
            response.setHeader("Charset", "UTF-8");
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileEntity.getFileName() + ".xlsx", "UTF-8"));
            response.flushBuffer();
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u5916\u5e01\u7a3d\u6838\u62a5\u544aexcel\u6587\u4ef6\u9519\u8bef\u3002", e);
            throw new BusinessRuntimeException("\u5bfc\u51fa\u5916\u5e01\u7a3d\u6838\u62a5\u544aexcel\u6587\u4ef6\u9519\u8bef");
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}

