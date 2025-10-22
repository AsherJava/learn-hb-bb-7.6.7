/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.zip.ZipEntry
 *  com.jiuqi.bi.util.zip.ZipOutputStream
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.graphics.Point
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 *  com.jiuqi.nr.common.importdata.ImportResultReportObject
 *  com.jiuqi.nr.common.importdata.ImportResultSheetObject
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.data.common.param.ImportFileDataRange
 *  com.jiuqi.nr.data.common.service.dto.ImportCancledResult
 *  com.jiuqi.nr.data.excel.consts.BatchExportConsts
 *  com.jiuqi.nr.data.excel.param.bean.ExcelImportResultItem
 *  com.jiuqi.nr.data.excel.param.bean.ImportResultObject
 *  com.jiuqi.nr.data.excel.utils.ExcelErrorUtil
 *  com.jiuqi.nr.data.excel.utils.ExcelImportUtil
 *  com.jiuqi.nr.dataentry.bean.ImportCancledResultParam
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.file.web.FileType
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ImportErrorData
 *  com.jiuqi.nr.io.params.input.ImportErrorTypeEnum
 *  com.jiuqi.nr.io.params.input.ImportResult
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet
 *  com.jiuqi.nr.io.service.IoQualifier
 *  com.jiuqi.nr.io.service.impl.IoQualifierImpl
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nvwa.authority.util.ExcelUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.customExcelBatchImport.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.zip.ZipEntry;
import com.jiuqi.bi.util.zip.ZipOutputStream;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.graphics.Point;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.common.importdata.ImportResultReportObject;
import com.jiuqi.nr.common.importdata.ImportResultSheetObject;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelAnalysisResultInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelCheckResultInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelOptionInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelReturnInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.ErrorInfo;
import com.jiuqi.nr.customExcelBatchImport.common.CustomExcelBatchImportConsts;
import com.jiuqi.nr.customExcelBatchImport.exception.OrgCodeAnalyzeException;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelAnalysisService;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelBatchImportService;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelRegionTitleService;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelTemplateService;
import com.jiuqi.nr.customExcelBatchImport.utils.CustomExcelImportUtil;
import com.jiuqi.nr.data.common.param.ImportFileDataRange;
import com.jiuqi.nr.data.common.service.dto.ImportCancledResult;
import com.jiuqi.nr.data.excel.consts.BatchExportConsts;
import com.jiuqi.nr.data.excel.param.bean.ExcelImportResultItem;
import com.jiuqi.nr.data.excel.param.bean.ImportResultObject;
import com.jiuqi.nr.data.excel.utils.ExcelErrorUtil;
import com.jiuqi.nr.data.excel.utils.ExcelImportUtil;
import com.jiuqi.nr.dataentry.bean.ImportCancledResultParam;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.file.web.FileType;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ImportErrorData;
import com.jiuqi.nr.io.params.input.ImportErrorTypeEnum;
import com.jiuqi.nr.io.params.input.ImportResult;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.impl.IoQualifierImpl;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nvwa.authority.util.ExcelUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CustomExcelBatchImportServiceImpl
implements ICustomExcelBatchImportService {
    private static final Logger logger = LoggerFactory.getLogger(CustomExcelBatchImportServiceImpl.class);
    @Resource
    private IRuntimeDataRegionService iRuntimeDataRegionService;
    @Resource
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Resource
    private FileInfoService fileInfoService;
    @Resource
    private IRunTimeViewController runtimeViewController;
    @Resource
    private ICustomExcelAnalysisService customExcelAnalysisService;
    @Autowired
    ICustomExcelRegionTitleService customExcelRegionTitleService;
    @Resource
    private ICustomExcelTemplateService customExcelTemplateService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private IoQualifierImpl ioQualifier;
    @Autowired
    protected IEntityDataService entityDataService;
    @Autowired
    protected IDataDefinitionRuntimeController definitionRuntimeController;
    @Autowired
    protected IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    private static final String ORGCODE_ANALYZE_ERRORMESSAGE = "orgCode\u8f6c\u6362code\u5931\u8d25\uff0c\u672a\u627e\u5230\u76f8\u5e94\u7684code\u3002orgCode\u503c\u4e3a\uff1a";

    @Override
    public List<FileInfo> getTemplateFileList(String taskKey, String periodInfo) {
        List fileInfos = new ArrayList();
        List formDefineList = new ArrayList();
        ArrayList<FileInfo> returnFiles = new ArrayList<FileInfo>();
        String separator = " ";
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runtimeViewController.querySchemePeriodLinkByPeriodAndTask(periodInfo, taskKey);
            FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            formDefineList = this.runtimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
            fileInfos = this.fileInfoService.getFileInfoByGroup("CUSTOM_EXCEL" + formSchemeDefine.getFormSchemeCode(), "CUSTOMAREA", FileStatus.AVAILABLE);
            if (formDefineList != null && formDefineList.size() > 0 && fileInfos != null && fileInfos.size() > 0) {
                Collections.sort(formDefineList, new Comparator<FormDefine>(){

                    @Override
                    public int compare(FormDefine o1, FormDefine o2) {
                        return o1.getOrder().compareToIgnoreCase(o2.getOrder());
                    }
                });
                for (FormDefine formDefine : formDefineList) {
                    List dataRegionDefines = this.iRuntimeDataRegionService.getDataRegionsInForm(formDefine.getKey());
                    if (FormType.FORM_TYPE_NEWFMDM.name().equals(formDefine.getFormType().name())) continue;
                    String title = formDefine.getTitle().length() > 10 ? formDefine.getTitle().substring(0, 10) : formDefine.getTitle();
                    String code = formDefine.getFormCode();
                    for (FileInfo fileInfo : fileInfos) {
                        String fileName = fileInfo.getName();
                        String fileTitles = fileName.substring(0, fileName.lastIndexOf("."));
                        String formTitle = fileTitles.split(separator)[1];
                        String formCode = fileTitles.split(separator)[0];
                        String regionInfo = null;
                        if (fileTitles.split(separator).length > 2) {
                            regionInfo = fileTitles.split(separator)[2];
                        }
                        if (regionInfo == null && formTitle.equals(title) && formCode.equals(code)) {
                            returnFiles.add(fileInfo);
                            continue;
                        }
                        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                            if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) continue;
                            Position position = new Position(dataRegionDefine.getRegionLeft(), dataRegionDefine.getRegionTop());
                            if (!formTitle.equals(title) || !formCode.equals(code) || !position.toString().equals(regionInfo)) continue;
                            returnFiles.add(fileInfo);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return returnFiles;
    }

    @Override
    public List<List<ErrorInfo>> coverTemplateFile(String taskKey, String periodInfo, String fileKeys) {
        ArrayList<List<ErrorInfo>> customExcelCheckErrorInfo = new ArrayList<List<ErrorInfo>>();
        try {
            String[] filekStrs;
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runtimeViewController.querySchemePeriodLinkByPeriodAndTask(periodInfo, taskKey);
            FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            String groupInfo = "CUSTOM_EXCEL" + formSchemeDefine.getFormSchemeCode();
            for (String filekStr : filekStrs = fileKeys.split(";")) {
                ObjectInfo fileInfoObject = this.fileUploadOssService.getInfo(filekStr);
                if (fileInfoObject == null || fileInfoObject.getSize() == 0L || !fileInfoObject.getName().endsWith("xlsx") && !fileInfoObject.getName().endsWith("xls")) continue;
                byte[] fileBytes = this.fileUploadOssService.downloadFileByteFormTemp(filekStr);
                List<ErrorInfo> checkWorkBookInfo = this.customExcelAnalysisService.checkWorkbook(formSchemeDefine.getKey(), fileInfoObject.getName(), ExcelUtils.create((InputStream)new ByteArrayInputStream(fileBytes)), "checkWork").getErrorInfos();
                if (checkWorkBookInfo == null || checkWorkBookInfo.size() <= 0) {
                    String fileName = fileInfoObject.getName().trim().replaceAll("\n", "");
                    String fileKey = "";
                    List fileInfos = this.fileInfoService.getFileInfoByGroup("CUSTOM_EXCEL" + formSchemeDefine.getFormSchemeCode(), "CUSTOMAREA", FileStatus.AVAILABLE);
                    if (fileInfos != null && fileInfos.size() > 0) {
                        for (FileInfo fileInfo : fileInfos) {
                            if (!fileInfoObject.getName().equals(fileInfo.getName())) continue;
                            fileKey = fileInfo.getKey();
                        }
                        if (fileKey != "" && fileKey != null) {
                            this.fileService.area("CUSTOMAREA").delete(fileKey, Boolean.valueOf(false));
                        }
                    }
                    this.fileService.area("CUSTOMAREA").uploadByGroup(fileName, groupInfo, fileBytes);
                    continue;
                }
                customExcelCheckErrorInfo.add(checkWorkBookInfo);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return customExcelCheckErrorInfo;
    }

    @Override
    public void downloadTempleFiles(String taskKey, String periodInfo, String fileKeyInfo, HttpServletResponse response) {
        String areaInfo = "CUSTOMAREA";
        String groupInfo = null;
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runtimeViewController.querySchemePeriodLinkByPeriodAndTask(periodInfo, taskKey);
            FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            groupInfo = "CUSTOM_EXCEL" + formSchemeDefine.getFormSchemeCode();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (groupInfo == null) {
            return;
        }
        List<FileInfo> files = this.getFiles(groupInfo, fileKeyInfo, areaInfo);
        if (files.isEmpty()) {
            return;
        }
        FileAreaService fileAreaService = this.fileService.area(areaInfo);
        ServletOutputStream outputStream = null;
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        if (files.size() == 1) {
            FileInfo file = files.get(0);
            FileType fileType = FileType.valueOfExtension((String)file.getExtension());
            String fileName = file.getName();
            int length = fileName.length();
            String extension = file.getExtension();
            response.setContentType(fileType.getContentType());
            byte[] databytes = fileAreaService.download(file.getKey());
            try {
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"));
                outputStream = response.getOutputStream();
                outputStream.write(databytes);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            String zipName = "\u81ea\u5b9a\u4e49\u5bfc\u5165excel\u6a21\u677f.zip";
            response.setContentType(FileType.ZIP.getContentType());
            try (ZipOutputStream zos = new ZipOutputStream((OutputStream)response.getOutputStream());){
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(zipName, "UTF-8"));
                zos.setEncoding("gb2312");
                for (FileInfo file : files) {
                    byte[] databytes = fileAreaService.download(file.getKey());
                    String fileName = file.getName();
                    int length = fileName.length();
                    String extension = file.getExtension();
                    if (null == databytes || databytes.length <= 0) continue;
                    ByteArrayInputStream swapStream = new ByteArrayInputStream(databytes);
                    Throwable throwable = null;
                    try {
                        BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                        Throwable throwable2 = null;
                        try {
                            byte[] bufs = new byte[0x6400000];
                            zos.putNextEntry(new ZipEntry(fileName));
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
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        try {
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public CustomExcelReturnInfo downExampleNoEnableManage(String taskKey, String periodInfo) {
        CustomExcelReturnInfo returnInfo = new CustomExcelReturnInfo();
        List fileInfos = new ArrayList();
        String fileKeyInfo = "";
        String message = "error";
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runtimeViewController.querySchemePeriodLinkByPeriodAndTask(periodInfo, taskKey);
            FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            String groupInfo = "CUSTOM_EXCEL" + formSchemeDefine.getFormSchemeCode();
            String area = "CUSTOMAREA";
            fileInfos = this.fileInfoService.getFileInfoByGroup(groupInfo, area, FileStatus.AVAILABLE);
            if (fileInfos != null && fileInfos.size() != 0) {
                message = "success";
                if (fileInfos.size() == 1) {
                    returnInfo.setFileName(((FileInfo)fileInfos.get(0)).getName());
                    returnInfo.setSingleFile(true);
                } else {
                    returnInfo.setSingleFile(false);
                }
                for (FileInfo file : fileInfos) {
                    fileKeyInfo = fileKeyInfo + file.getKey() + ";";
                }
                returnInfo.setFileKeys(fileKeyInfo);
            } else {
                message = "nohave";
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        returnInfo.setMessage(message);
        return returnInfo;
    }

    @Override
    public void downExampleEnableManage(String taskKey, String periodInfo, HttpServletResponse response) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runtimeViewController.querySchemePeriodLinkByPeriodAndTask(periodInfo, taskKey);
            formSchemeDefine = this.runtimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Map<String, DataRegionDefine> allRegionTitleMap = this.customExcelRegionTitleService.getAllRegionTitleMap(formSchemeDefine.getKey());
        String zipName = "\u81ea\u5b9a\u4e49\u5bfc\u5165excel\u6a21\u677f.zip";
        try (ServletOutputStream outputStream = response.getOutputStream();
             ZipOutputStream zos = new ZipOutputStream((OutputStream)outputStream);){
            response.setContentType(FileType.ZIP.getContentType());
            response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(zipName, "UTF-8"));
            zos.setEncoding("gb2312");
            for (String fileName : allRegionTitleMap.keySet()) {
                DataRegionDefine dataRegionDefine = allRegionTitleMap.get(fileName);
                Workbook workbook = this.customExcelTemplateService.createTemplate(dataRegionDefine);
                if (null == workbook) continue;
                try {
                    ByteArrayOutputStream os = new ByteArrayOutputStream(0xA00000);
                    Throwable throwable = null;
                    try {
                        workbook.write(os);
                        byte[] byteArray = os.toByteArray();
                        if (null == byteArray || byteArray.length <= 0) continue;
                        byte[] bufs = new byte[0x6400000];
                        zos.putNextEntry(new ZipEntry(fileName + ".xlsx"));
                        ByteArrayInputStream swapStream = new ByteArrayInputStream(byteArray);
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
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (os == null) continue;
                        if (throwable != null) {
                            try {
                                os.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        os.close();
                    }
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public ReturnInfo deleteTemplate(String fileKey) {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String area = "CUSTOMAREA";
            FileInfo fileInfo = this.fileInfoService.getFileInfo(fileKey, area, FileStatus.AVAILABLE);
            this.fileInfoService.deleteFile(fileInfo, Boolean.valueOf(false));
            returnInfo.setMessage("success");
            return returnInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnInfo.setMessage("error");
            return returnInfo;
        }
    }

    @Override
    public void beforeImport(CustomExcelOptionInfo customExcelOptionInfo, AsyncTaskMonitor asyncTaskMonitor) {
        try {
            String taskKey = customExcelOptionInfo.getTaskKey();
            ArrayList<File> fileList = new ArrayList<File>();
            String fileKey = customExcelOptionInfo.getFileKey();
            ObjectInfo fileObject = this.fileUploadOssService.getInfo(fileKey);
            byte[] fileByte = this.fileUploadOssService.downloadFileByteFormTemp(fileKey);
            if (fileObject.getName().endsWith(".zip")) {
                File fileOfZip = this.multipartFileTOFile(fileObject.getName(), fileByte);
                try {
                    String unZipPath = CustomExcelImportUtil.getUnZipPath(taskKey);
                    CustomExcelImportUtil.unZip(fileOfZip, unZipPath);
                    List files = FileUtil.getFiles((String)unZipPath, (String)"Attament");
                    if (files != null) {
                        fileList.addAll(files);
                    }
                }
                catch (JQException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            } else {
                File fileInfo = this.multipartFileTOFile(fileObject.getName(), fileByte);
                fileList.add(fileInfo);
            }
            if (!fileList.isEmpty()) {
                if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                    ImportCancledResult importCancledResult = new ImportCancledResult();
                    importCancledResult.setImportFileDataRange(ImportFileDataRange.REGION);
                    importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
                    ImportCancledResultParam param = new ImportCancledResultParam("0", importCancledResult.getImportFileDataRange().getTitle());
                    asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)param), (Object)JsonUtil.objectToJson((Object)importCancledResult));
                    return;
                }
                this.customExcelImport(customExcelOptionInfo, fileList, fileObject.getName(), asyncTaskMonitor);
            }
            for (File fileInfo : fileList) {
                if (fileInfo.delete()) continue;
                logger.error("\u5220\u9664\u6587\u4ef6\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)fileInfo.getAbsolutePath());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void customExcelImport(CustomExcelOptionInfo customExcelOptionInfo, List<File> fileList, String importFileName, AsyncTaskMonitor asyncTaskMonitor) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = null;
        String taskKey = customExcelOptionInfo.getTaskKey();
        String periodInfo = customExcelOptionInfo.getPeriodInfo();
        String adjustInfo = customExcelOptionInfo.getAdjust();
        boolean needAnalyzeToCode = false;
        IEntityTable entityTable = null;
        String sysOption = this.iNvwaSystemOptionService.get("nr-data-entry-group", "NR/CUSTOMIMPORT/MDCODE_ANALYZE_RULE");
        if ("ORGCODE".equals(sysOption)) {
            needAnalyzeToCode = true;
        }
        try {
            schemePeriodLinkDefine = this.runtimeViewController.querySchemePeriodLinkByPeriodAndTask(periodInfo, taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
        asyncTaskMonitor.progressAndMessage(0.1, "\u5bfc\u5165\u5f00\u59cb");
        ArrayList<ErrorInfo> errorMessageList = new ArrayList<ErrorInfo>();
        HashSet<String> skipOrgCodes = new HashSet<String>();
        ImportResultObject resultObject = new ImportResultObject(true, "");
        ArrayList<ImportResult> importResults = new ArrayList<ImportResult>();
        ImportCancledResult importCancledResult = new ImportCancledResult();
        importCancledResult.setImportFileDataRange(ImportFileDataRange.REGION);
        String dir = fileList.get(0).getAbsolutePath().replace(fileList.get(0).getName(), "");
        String temPath = dir + UUID.randomUUID();
        File importResultFile = null;
        ZipOutputStream importResultZos = null;
        String type = importFileName.endsWith(".zip") ? "zip" : "excel";
        try {
            importResultFile = FileUtil.createIfNotExists((String)(temPath + BatchExportConsts.SEPARATOR + importFileName));
            if (importFileName.endsWith(".zip")) {
                importResultZos = new ZipOutputStream(Files.newOutputStream(importResultFile.toPath(), new OpenOption[0]));
                importResultZos.setEncoding("gb2312");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        for (int index = 0; index < fileList.size(); ++index) {
            Object errorCode;
            ImportResultRegionObject regionObject;
            ImportResultReportObject reportObject;
            ImportResultSheetObject sheetObject;
            ImportResultExcelFileObject excelFileObject;
            Object errorInfo;
            int skipRowSize = 0;
            if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
                ImportCancledResultParam param = new ImportCancledResultParam(String.valueOf(importCancledResult.getSuccessFiles().size()), importCancledResult.getImportFileDataRange().getTitle());
                asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)param), (Object)JsonUtil.objectToJson((Object)importCancledResult));
                return;
            }
            String fileName = null;
            fileName = fileList.get(index).getName();
            if (!fileList.get(index).getName().endsWith("xlsx") && !fileList.get(index).getName().endsWith("xls")) {
                ErrorInfo errorInfo2 = new ErrorInfo();
                errorInfo2.setFileName(fileName);
                errorInfo2.setErrorMsg("\u5f53\u524d\u6587\u4ef6(" + fileName + ")\u65e0\u6cd5\u89e3\u6790\uff01");
                errorMessageList.add(errorInfo2);
                String message = "\u3010" + fileName + "\u3011\u65e0\u6cd5\u89e3\u6790\uff01";
                this.fileErrorDeal(message, resultObject, fileList.get(index), temPath, importResultZos, null);
                continue;
            }
            CustomExcelAnalysisResultInfo customExcelAnalysisResultInfo = this.customExcelAnalysisService.analysisWorkbookData(formSchemeDefine.getKey(), fileList.get(index), type);
            CustomExcelCheckResultInfo checkResult = customExcelAnalysisResultInfo.getCheckResult();
            String sheetName = customExcelAnalysisResultInfo.getSheetNames().get(fileName.split("\\.")[0]);
            if (checkResult == null) {
                String message = "\u3010" + fileName + "\u3011\u89e3\u6790\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u6587\u4ef6\u540d\u79f0\u53ca\u683c\u5f0f\u662f\u5426\u6b63\u5e38\uff01";
                this.fileErrorDeal(message, resultObject, fileList.get(index), temPath, importResultZos, sheetName);
                continue;
            }
            if (!CollectionUtils.isEmpty(checkResult.getErrorInfos())) {
                errorMessageList.addAll(checkResult.getErrorInfos());
                asyncTaskMonitor.progressAndMessage((double)(index + 1) * 1.0 / (double)fileList.size(), "\u5bfc\u5165\u4e2d");
                continue;
            }
            FieldDefine adjustField = customExcelAnalysisResultInfo.getCheckResult().getAdjustFiledInfo();
            boolean existAdjust = StringUtils.isNotEmpty((String)adjustInfo) && adjustField != null;
            DataRegionDefine region = checkResult.getRegion();
            List<FieldDefine> fileFields = checkResult.getFileFields();
            fileFields.add(0, customExcelAnalysisResultInfo.getCheckResult().getPeriodFiledInfo());
            if (existAdjust) {
                fileFields.add(1, adjustField);
            }
            RegionData regionData = new RegionData();
            regionData.initialize(region);
            ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
            int dwIndex = -1;
            for (int i = 0; i < fileFields.size(); ++i) {
                FieldDefine field = fileFields.get(i);
                String fieldCode = field.getCode();
                if ("MDCODE".equals(fieldCode)) {
                    dwIndex = i;
                }
                ExportFieldDefine e = new ExportFieldDefine();
                e.setCode(fieldCode);
                fields.add(e);
            }
            DimensionValueSet dimensionSet = new DimensionValueSet();
            dimensionSet.setValue("DATATIME", (Object)periodInfo);
            if (existAdjust) {
                dimensionSet.setValue("ADJUST", (Object)adjustInfo);
            }
            if (needAnalyzeToCode && entityTable == null) {
                TaskDefine taskDefine = this.runtimeViewController.queryTaskDefine(taskKey);
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
                EntityViewData entityData = new EntityViewData();
                entityData.initialize(entityDefine);
                EntityViewDefine entityViewDefine = entityData.getEntityViewDefine();
                IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
                entityQuery.setMasterKeys(dimensionSet);
                entityQuery.setEntityView(entityViewDefine);
                ExecutorContext executorContext = new ExecutorContext(this.definitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeViewController, this.definitionRuntimeController, this.entityViewRunTimeController, schemePeriodLinkDefine.getSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                executorContext.setPeriodView(formSchemeDefine.getDateTime());
                executorContext.setOrgEntityId(entityViewDefine.getEntityId());
                try {
                    entityTable = entityQuery.executeFullBuild((IContext)executorContext);
                }
                catch (Exception exception) {
                    logger.error("\u83b7\u53d6\u5b9e\u4f53\u7ed3\u679c\u8868\u65f6\u51fa\u9519\uff1a{}", (Object)exception.getMessage(), (Object)exception);
                }
            }
            TableContext context = new TableContext(taskKey, schemePeriodLinkDefine.getSchemeKey(), region.getFormKey(), dimensionSet, null, null);
            context.setCheckType(1);
            context.setIoQualifier((IoQualifier)this.ioQualifier);
            context.setValidEntityExist(true);
            context.setMdCodeCheckRule(1);
            Object regionDataSet = null;
            FormDefine formDefine = this.runtimeViewController.queryFormById(region.getFormKey());
            regionDataSet = formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT && regionData.getRegionTop() > 1 ? new SBRegionDataSet(context, regionData, fields) : new RegionDataSet(context, regionData, fields);
            List<List<Object>> dataRows = customExcelAnalysisResultInfo.getDataRows();
            Set noExistenceUnit = new HashSet();
            Set noAccessUnit = new HashSet();
            for (List list : dataRows) {
                try {
                    list.add(0, periodInfo);
                    if (existAdjust) {
                        list.add(1, adjustInfo);
                    }
                    if (needAnalyzeToCode) {
                        Object code;
                        try {
                            code = this.getCodeByOrgCode(list.get(dwIndex), entityTable);
                        }
                        catch (OrgCodeAnalyzeException e) {
                            skipOrgCodes.add((String)list.get(dwIndex));
                            ++skipRowSize;
                            if (!logger.isDebugEnabled()) continue;
                            logger.debug(e.getMessage(), e);
                            continue;
                        }
                        list.set(dwIndex, code);
                    }
                    regionDataSet.importDatas(list);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    ArrayList<ErrorInfo> errorMessage = new ArrayList<ErrorInfo>();
                    ErrorInfo errorInfo3 = new ErrorInfo();
                    errorInfo3.setErrorMsg("\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage());
                    errorInfo3.setFileName(fileName);
                    errorMessage.add(errorInfo3);
                    errorMessageList.addAll(errorMessage);
                }
            }
            try {
                regionDataSet.commit();
                Map unImportInfo = regionDataSet.getUnImport();
                noAccessUnit = (Set)unImportInfo.get("unit_noaccess");
                noExistenceUnit = (Set)unImportInfo.get("unit_inexistence");
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                ArrayList<ErrorInfo> arrayList = new ArrayList<ErrorInfo>();
                ErrorInfo errorInfo4 = new ErrorInfo();
                errorInfo4.setErrorMsg("\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage());
                errorInfo4.setFileName(fileName);
                arrayList.add(errorInfo4);
                errorMessageList.addAll(arrayList);
                String message = "\u3010" + fileName + "\u3011\u5bfc\u5165\u8fc7\u7a0b\u4e2d\u9047\u5230\u53d1\u751f\u672a\u77e5\u9519\u8bef\uff1a" + e.getMessage();
                this.fileErrorDeal(message, resultObject, fileList.get(index), temPath, importResultZos, sheetName);
            }
            ArrayList<ErrorInfo> errorMessage = new ArrayList<ErrorInfo>();
            if (noAccessUnit != null && noAccessUnit.size() > 0) {
                for (String unitInfo : noAccessUnit) {
                    if (!StringUtils.isNotEmpty((String)unitInfo)) continue;
                    errorInfo = new ErrorInfo();
                    ((ErrorInfo)errorInfo).setErrorMsg("\u5f53\u524d\u5355\u4f4d" + unitInfo + "\u6570\u636e\u5bfc\u5165\u5931\u8d25\uff01");
                    ((ErrorInfo)errorInfo).setFileName(fileName);
                    errorMessage.add((ErrorInfo)errorInfo);
                }
                checkResult.setErrorInfos(errorMessage);
            }
            if (noExistenceUnit != null && noExistenceUnit.size() > 0) {
                for (String unitInfo : noExistenceUnit) {
                    errorInfo = new ErrorInfo();
                    ((ErrorInfo)errorInfo).setErrorMsg("\u5f53\u524d\u5355\u4f4d" + unitInfo + "\u6570\u636e\u5bfc\u5165\u5931\u8d25\uff01");
                    ((ErrorInfo)errorInfo).setFileName(fileName);
                    errorMessage.add((ErrorInfo)errorInfo);
                }
                checkResult.setErrorInfos(errorMessage);
            }
            errorMessageList.addAll(checkResult.getErrorInfos());
            List list = regionDataSet.getImportErrorInfos();
            if (!list.isEmpty()) {
                resultObject.setSuccess(false);
                ImportResultObject tempImpResObj = new ImportResultObject();
                excelFileObject = new ImportResultExcelFileObject();
                sheetObject = new ImportResultSheetObject();
                reportObject = new ImportResultReportObject();
                regionObject = new ImportResultRegionObject();
                for (ImportErrorData errorInfo2 : list) {
                    errorCode = errorInfo2.getErrorType().equals((Object)ImportErrorTypeEnum.ERROR_DATA) ? ErrorCode.DATAERROR : ErrorCode.REPORTERROR;
                    ExcelImportResultItem item = new ExcelImportResultItem();
                    item.setFormName(region.getTitle());
                    item.setFileName(fileName);
                    item.setSheetName(sheetName);
                    item.setErrorCode(errorCode.getErrorCodeMsg());
                    item.setErrorInfo(errorInfo2.getErrorMessage());
                    resultObject.getFails().add(item);
                    tempImpResObj.getFails().add(item);
                    ImportErrorDataInfo dataInfo = new ImportErrorDataInfo();
                    dataInfo.getDataError().setErrorCode((ErrorCode)errorCode);
                    dataInfo.getDataError().setErrorInfo(errorInfo2.getErrorMessage());
                    Point dataSetPoint = errorInfo2.getPoint();
                    dataInfo.setExcelLocation(Objects.isNull(dataSetPoint) ? null : new Point(dataSetPoint.y == 0 ? 1 : dataSetPoint.y - 1, dataSetPoint.x + checkResult.getDimFieldIndex() + 1));
                    regionObject.getImportErrorDataInfoList().add(dataInfo);
                }
                reportObject.getImportResultRegionObjectList().add(regionObject);
                reportObject.setReportName(region.getTitle());
                sheetObject.setImportResultReportObject(reportObject);
                sheetObject.setSheetName(sheetName);
                excelFileObject.getImportResultSheetObjectList().add(sheetObject);
                this.createSheet(tempImpResObj, excelFileObject, fileList.get(index), temPath, importResultZos, true);
            } else {
                ImportResult importResult = regionDataSet.getImportResult();
                if (!skipOrgCodes.isEmpty()) {
                    importResult.setFailureDataNum(importResult.getFailureDataNum() + skipRowSize);
                    importResult.getFailureOrgs().addAll(skipOrgCodes);
                    importResult.setFailureOrgNum(importResult.getFailureOrgs().size());
                }
                importResult.setFileName(fileName);
                importResult.getSuccessOrgs().clear();
                importResult.getFailureOrgs().clear();
                importResults.add(importResult);
                List amendInfos = regionDataSet.getImportAmendInfos();
                ImportResultObject tempImpResObj = new ImportResultObject();
                excelFileObject = new ImportResultExcelFileObject();
                sheetObject = new ImportResultSheetObject();
                reportObject = new ImportResultReportObject();
                regionObject = new ImportResultRegionObject();
                if (!amendInfos.isEmpty() || !skipOrgCodes.isEmpty()) {
                    ImportErrorDataInfo dataInfo;
                    ExcelImportResultItem item;
                    for (ImportErrorData errorInfo5 : amendInfos) {
                        ErrorCode errorCode2 = errorInfo5.getErrorType().equals((Object)ImportErrorTypeEnum.ERROR_DATA) ? ErrorCode.DATAERROR : ErrorCode.REPORTERROR;
                        item = new ExcelImportResultItem();
                        item.setFormName(region.getTitle());
                        item.setFileName(fileName);
                        item.setSheetName(sheetName);
                        item.setErrorCode(errorCode2.getErrorCodeMsg());
                        item.setErrorInfo(errorInfo5.getErrorMessage());
                        tempImpResObj.getFails().add(item);
                        dataInfo = new ImportErrorDataInfo();
                        dataInfo.getDataError().setErrorCode(errorCode2);
                        dataInfo.getDataError().setErrorInfo(errorInfo5.getErrorMessage());
                        Point dataSetPoint = errorInfo5.getPoint();
                        dataInfo.setExcelLocation(Objects.isNull(dataSetPoint) ? null : new Point(dataSetPoint.y == 0 ? 1 : dataSetPoint.y - 1, dataSetPoint.x + checkResult.getDimFieldIndex() + 1));
                        regionObject.getImportErrorDataInfoList().add(dataInfo);
                    }
                    for (String orgCode : skipOrgCodes) {
                        String skipMessage = ORGCODE_ANALYZE_ERRORMESSAGE + orgCode + "\u3002\u8df3\u8fc7\u5bfc\u5165\uff01";
                        item = new ExcelImportResultItem();
                        item.setFormName(region.getTitle());
                        item.setFileName(fileName);
                        item.setSheetName(sheetName);
                        item.setErrorCode(ErrorCode.DATAERROR.getErrorCodeMsg());
                        item.setErrorInfo(skipMessage);
                        tempImpResObj.getFails().add(item);
                        dataInfo = new ImportErrorDataInfo();
                        dataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                        dataInfo.getDataError().setErrorInfo(skipMessage);
                        regionObject.getImportErrorDataInfoList().add(dataInfo);
                    }
                } else {
                    errorCode = ErrorCode.REPORTERROR;
                    StringBuilder message = new StringBuilder();
                    message.append("\u5bfc\u5165\u6210\u529f\uff01\u6210\u529f\u5bfc\u5165").append(importResult.getSuccessOrgNum()).append("\u5bb6\u5355\u4f4d").append(importResult.getSuccessDataNum()).append("\u6761\u6570\u636e\u3002");
                    ExcelImportResultItem item = new ExcelImportResultItem();
                    item.setFormName(region.getTitle());
                    item.setFileName(fileName);
                    item.setSheetName(sheetName);
                    item.setErrorCode(errorCode.getErrorCodeMsg());
                    item.setErrorInfo(message.toString());
                    tempImpResObj.getFails().add(item);
                    ImportErrorDataInfo dataInfo = new ImportErrorDataInfo();
                    dataInfo.getDataError().setErrorCode((ErrorCode)errorCode);
                    dataInfo.getDataError().setErrorInfo(message.toString());
                    regionObject.getImportErrorDataInfoList().add(dataInfo);
                }
                reportObject.getImportResultRegionObjectList().add(regionObject);
                reportObject.setReportName(region.getTitle());
                sheetObject.setImportResultReportObject(reportObject);
                sheetObject.setSheetName(sheetName);
                excelFileObject.getImportResultSheetObjectList().add(sheetObject);
                this.createSheet(tempImpResObj, excelFileObject, fileList.get(index), temPath, importResultZos, false);
                importCancledResult.getSuccessFiles().add(fileName);
            }
            asyncTaskMonitor.progressAndMessage((double)(index + 1) * 1.0 / (double)fileList.size(), "\u5bfc\u5165\u4e2d");
        }
        if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
            importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
            ImportCancledResultParam param = new ImportCancledResultParam(String.valueOf(importCancledResult.getSuccessFiles().size()), importCancledResult.getImportFileDataRange().getTitle());
            asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)param), (Object)JsonUtil.objectToJson((Object)importCancledResult));
            return;
        }
        resultObject.setImportResults(importResults);
        resultObject.setLocation(importResultFile.getPath());
        if (type.equals("zip") && Objects.nonNull(importResultZos)) {
            try {
                importResultZos.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        File excelfile = new File(FilenameUtils.normalize(resultObject.getLocation()));
        try {
            File[] fileArray = null;
            try (InputStream uploadInputStream = Files.newInputStream(excelfile.toPath(), new OpenOption[0]);){
                ObjectInfo objectInfo = this.fileUploadOssService.uploadFileStreamToTemp(excelfile.getName(), uploadInputStream);
                resultObject.setLocation(objectInfo.getKey());
            }
            catch (Throwable object) {
                fileArray = object;
                throw object;
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        finally {
            File[] tempFiles;
            for (File tempFile : tempFiles = new File(temPath).listFiles()) {
                if (tempFile.delete()) continue;
                logger.error("\u4e34\u65f6\u6587\u4ef6\u5220\u9664\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)tempFile.getAbsolutePath());
            }
        }
        if (resultObject.isSuccess()) {
            asyncTaskMonitor.finish("\u5bfc\u5165\u5b8c\u6210\uff01", (Object)resultObject);
        } else {
            asyncTaskMonitor.error("\u5bfc\u5165\u5b58\u5728\u9519\u8bef\uff01", null);
            asyncTaskMonitor.finish("\u5bfc\u5165\u5b58\u5728\u9519\u8bef\uff01", (Object)resultObject);
        }
    }

    private List<FileInfo> getFiles(String groupKey, String fileKeyInfo, String areaInfo) {
        ArrayList<FileInfo> fileInfos = new ArrayList();
        if (StringUtils.isEmpty((String)fileKeyInfo)) {
            fileInfos = this.fileInfoService.getFileInfoByGroup(groupKey, areaInfo, FileStatus.AVAILABLE);
        } else {
            String[] fileKeys;
            for (String fileKey : fileKeys = fileKeyInfo.split(";")) {
                FileInfo fileInfo = this.fileInfoService.getFileInfo(fileKey, areaInfo, FileStatus.AVAILABLE);
                if (fileInfo == null) continue;
                fileInfos.add(fileInfo);
            }
        }
        return fileInfos;
    }

    private void createSheet(ImportResultObject resultObject, ImportResultExcelFileObject excelFileObject, File file, String temPath, ZipOutputStream errorZos, boolean hasError) {
        block54: {
            try (Workbook workbookError = ExcelErrorUtil.exportExcel((ImportResultObject)resultObject, (ImportResultExcelFileObject)excelFileObject, (Workbook)ExcelImportUtil.create((File)file), (String)(hasError ? "(\u9519\u8bef\u4fe1\u606f\u6620\u5c04\u8868)" : "(\u5bfc\u5165\u660e\u7ec6\u6620\u5c04\u8868)"));){
                String filePathNew = temPath + BatchExportConsts.SEPARATOR + file.getName();
                File errorFile = new File(filePathNew);
                try {
                    errorFile.getParentFile().mkdirs();
                    if (!errorFile.createNewFile()) {
                        logger.error("\u521b\u5efa\u4e34\u65f6\u9519\u8bef\u4fe1\u606f\u6587\u4ef6\u5931\u8d25\uff0c\u6216\u6587\u4ef6\u5df2\u5b58\u5728\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)errorFile.getAbsolutePath());
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                }
                try (FileOutputStream out = new FileOutputStream(errorFile);){
                    workbookError.write(out);
                    out.flush();
                    resultObject.setLocation(filePathNew);
                    if (!Objects.nonNull(errorZos)) break block54;
                    byte[] buffer = new byte[1024];
                    try (FileInputStream fis = new FileInputStream(errorFile);
                         BufferedInputStream bis = new BufferedInputStream(fis);){
                        int bytesRead;
                        errorZos.putNextEntry(new ZipEntry(errorFile.getName()));
                        while ((bytesRead = bis.read(buffer)) > 0) {
                            errorZos.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        }
    }

    private File multipartFileTOFile(String fileName, byte[] fileByte) {
        try {
            PathUtils.validatePathManipulation((String)fileName);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File sourcefile = new File(fileName);
        fileName = sourcefile.getName();
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
        String path = com.jiuqi.nr.dataentry.util.BatchExportConsts.UPLOADDIR + com.jiuqi.nr.dataentry.util.BatchExportConsts.SEPARATOR + fileLocation + com.jiuqi.nr.dataentry.util.BatchExportConsts.SEPARATOR;
        File pathFile = new File(FilenameUtils.normalize(path));
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        File file = new File(pathFile.getPath() + CustomExcelBatchImportConsts.SEPARATOR + fileName);
        try (FileOutputStream fos = new FileOutputStream(file);){
            fos.write(fileByte);
        }
        catch (IOException | IllegalStateException e) {
            logger.info("\u4fdd\u5b58\u4e34\u65f6\u6587\u4ef6\u51fa\u9519{}", e);
        }
        return file;
    }

    private void fileErrorDeal(String message, ImportResultObject resultObject, File file, String temPath, ZipOutputStream errorZos, String sheetName) {
        String errorCode = ErrorCode.FILEERROR.getErrorCodeMsg();
        ExcelImportResultItem item = new ExcelImportResultItem();
        item.setErrorCode(errorCode);
        item.setErrorInfo(message);
        resultObject.getFails().add(item);
        resultObject.setSuccess(false);
        ImportResultObject tempImpResObj = new ImportResultObject();
        tempImpResObj.getFails().add(item);
        ResultErrorInfo resultErrorInfo = new ResultErrorInfo();
        resultErrorInfo.setErrorCode(ErrorCode.FILEERROR);
        resultErrorInfo.setErrorInfo(message);
        ImportResultSheetObject sheetObject = new ImportResultSheetObject();
        sheetObject.setSheetName(StringUtils.isEmpty((String)sheetName) ? file.getName().split("\\.")[0] : sheetName);
        sheetObject.setSheetError(resultErrorInfo);
        ImportResultExcelFileObject excelFileObject = new ImportResultExcelFileObject();
        excelFileObject.getImportResultSheetObjectList().add(sheetObject);
        this.createSheet(tempImpResObj, excelFileObject, file, temPath, errorZos, true);
    }

    private Object getCodeByOrgCode(Object orgCode, IEntityTable entityTable) throws OrgCodeAnalyzeException {
        IEntityRow entityRow = entityTable.findByCode((String)orgCode);
        if (entityRow == null) {
            throw new OrgCodeAnalyzeException(ORGCODE_ANALYZE_ERRORMESSAGE + orgCode);
        }
        String code = entityRow.getEntityKeyData();
        if (StringUtils.isEmpty((String)code)) {
            throw new OrgCodeAnalyzeException(ORGCODE_ANALYZE_ERRORMESSAGE + orgCode);
        }
        return code;
    }
}

