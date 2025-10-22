/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.transfer.engine.DataMode
 *  com.jiuqi.bi.transfer.engine.Desc
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.TransferUtils
 *  com.jiuqi.bi.transfer.engine.ZipFileRecorder
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.util.type.GUID
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.base.util.ZipUtils
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.param.ReportParam
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService
 *  com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj
 *  com.jiuqi.nr.designer.planpublish.web.TaskPlanPublishController
 *  com.jiuqi.nr.designer.service.IFormFormulaService
 *  com.jiuqi.nr.designer.util.OssUploadUtil
 *  com.jiuqi.nr.designer.web.rest.vo.ExcelExportVO
 *  com.jiuqi.nr.designer.web.service.ExcelExportService
 *  com.jiuqi.nr.param.transfer.definition.TransferNodeType
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nvwa.subsystem.core.model.SubServerLevel
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferFileRecorder
 *  com.jiuqi.nvwa.transfer.TransferProgressMonitor
 *  com.jiuqi.nvwa.transfer.web.ServicesTransferUtil
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.mock.web.MockHttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.transfer.engine.DataMode;
import com.jiuqi.bi.transfer.engine.Desc;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.TransferUtils;
import com.jiuqi.bi.transfer.engine.ZipFileRecorder;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.base.util.ZipUtils;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.param.ReportParam;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.reportdatasync.config.ReportDataSyncConfig;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveTaskEO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportDataReceiveType;
import com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncService;
import com.jiuqi.gcreport.reportdatasync.util.DelegatingServletOutputStream;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj;
import com.jiuqi.nr.designer.planpublish.web.TaskPlanPublishController;
import com.jiuqi.nr.designer.service.IFormFormulaService;
import com.jiuqi.nr.designer.util.OssUploadUtil;
import com.jiuqi.nr.designer.web.rest.vo.ExcelExportVO;
import com.jiuqi.nr.designer.web.service.ExcelExportService;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nvwa.subsystem.core.model.SubServerLevel;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferFileRecorder;
import com.jiuqi.nvwa.transfer.TransferProgressMonitor;
import com.jiuqi.nvwa.transfer.web.ServicesTransferUtil;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class ReportDataSyncParamUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncParamUtils.class);
    public static final String REPORT_PARAM_META_FILENAME = "ReportParamMeta.JSON";

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ObjectInfo exportParamToOss(ReportDataSyncIssuedLogEO xfLogEO, ReportDataSyncParams syncParams) {
        ObjectInfo objectInfo;
        String syncParamAttachId = UUIDUtils.newUUIDStr();
        xfLogEO.setSyncParamAttachId(syncParamAttachId);
        String rootPath = null;
        try {
            Object serverLevelSuffix;
            boolean formulaSchemeImport;
            rootPath = BatchExportConsts.EXPORTDIR + File.separator + "reportparamsync" + File.separator + "exportparam" + File.separator + LocalDate.now() + File.separator + syncParamAttachId + File.separator;
            File rootFile = new File(rootPath);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            String fileName = xfLogEO.getTaskTitle() + DateUtils.format((Date)new Date(), (String)"yyyyMMddHHmmss") + ".zip";
            String dataZipLocation = rootPath + fileName;
            ReportDataSyncUtils.createEmptyZipFile(dataZipLocation);
            ReportDataSyncService dataSyncService = (ReportDataSyncService)SpringContextUtils.getBean(ReportDataSyncService.class);
            syncParams.setSyncType(ReportDataSyncTypeEnum.PARAM.getCode());
            dataSyncService.export(syncParams, syncParamAttachId);
            String snFolderPath = ReportDataSyncUtil.getSnFolderPath(syncParamAttachId);
            File mergeParamFile = new File(SinglePathUtil.getNewPath((String)snFolderPath, (String)syncParams.getSyncType()));
            if (mergeParamFile.exists()) {
                ZipUtils.addFile((String)dataZipLocation, (String)mergeParamFile.getCanonicalPath());
                mergeParamFile.delete();
            }
            String taskKey = xfLogEO.getTaskId();
            ReportParam reportParam = syncParams.getReportParam();
            boolean bl = formulaSchemeImport = null != reportParam && !CollectionUtils.isEmpty((Collection)reportParam.getSchemeIds()) && (!CollectionUtils.isEmpty((Collection)reportParam.getEfdcSchemes()) || !CollectionUtils.isEmpty((Collection)reportParam.getFormulaSchemes()));
            if (!StringUtils.isEmpty((String)taskKey) || formulaSchemeImport) {
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
                    if (!StringUtils.isEmpty((String)taskKey)) {
                        ReportDataSyncParamUtils.getTaskParamDatas(taskKey, syncParams.getReportParam().getSchemeIds(), syncParams.getReportParam().getFormKeys(), outputStream);
                    } else {
                        ReportDataSyncParamUtils.getformulaParamDatas(reportParam.getEfdcSchemes(), reportParam.getFormulaSchemes(), outputStream);
                    }
                    SubServerLevel serverLevel = ServicesTransferUtil.getServerLevel();
                    serverLevelSuffix = "";
                    if (serverLevel.getValue() != 0) {
                        serverLevelSuffix = "_" + serverLevel.getTitle();
                    }
                    String nvdataFileName = rootPath + "\u7cfb\u7edf\u8d44\u6e90" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (String)serverLevelSuffix + ".nvdata";
                    File nvdataFile = new File(nvdataFileName);
                    try (FileOutputStream nvdataFileOs = new FileOutputStream(nvdataFile);){
                        nvdataFileOs.write(outputStream.toByteArray());
                        ZipUtils.addFile((String)dataZipLocation, (String)nvdataFile.getCanonicalPath());
                        nvdataFile.delete();
                    }
                    catch (Exception e) {
                        throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
                    }
                }
            }
            String reportParamMetaLocation = rootPath + BatchExportConsts.SEPARATOR + REPORT_PARAM_META_FILENAME;
            File reportParamMetaMetaFile = new File(reportParamMetaLocation);
            reportParamMetaMetaFile.createNewFile();
            FileOutputStream reportParamMetaOutputStream = new FileOutputStream(reportParamMetaMetaFile);
            serverLevelSuffix = null;
            try {
                ReportDataSyncReceiveTaskVO reportDataSyncReceiveTaskVO = ReportDataSyncParamUtils.convertXfLogEOToReceiveTaskVO(xfLogEO);
                reportDataSyncReceiveTaskVO.setTaskId(reportParam == null ? null : reportParam.getTaskId());
                IOUtils.write(JsonUtils.writeValueAsString((Object)reportDataSyncReceiveTaskVO).getBytes(), (OutputStream)reportParamMetaOutputStream);
                ZipUtils.addFile((String)dataZipLocation, (String)reportParamMetaLocation);
                reportParamMetaMetaFile.delete();
            }
            catch (Throwable reportDataSyncReceiveTaskVO) {
                serverLevelSuffix = reportDataSyncReceiveTaskVO;
                throw reportDataSyncReceiveTaskVO;
            }
            finally {
                if (reportParamMetaOutputStream != null) {
                    if (serverLevelSuffix != null) {
                        try {
                            reportParamMetaOutputStream.close();
                        }
                        catch (Throwable reportDataSyncReceiveTaskVO) {
                            ((Throwable)serverLevelSuffix).addSuppressed(reportDataSyncReceiveTaskVO);
                        }
                    } else {
                        reportParamMetaOutputStream.close();
                    }
                }
            }
            File srcFile = new File(dataZipLocation);
            byte[] bytes = FileCopyUtils.copyToByteArray(srcFile);
            String originalFilename = xfLogEO.getTaskTitle() + "_" + xfLogEO.getTaskCode() + "_" + LocalDate.now() + ".zip";
            VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile("multipartFile", originalFilename, "multipart/form-data; charset=ISO-8859-1", bytes);
            CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
            objectInfo = commonFileService.uploadFileToOss((MultipartFile)multipartFile, syncParamAttachId);
        }
        catch (IOException e) {
            try {
                LOGGER.error(e.getMessage(), e);
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
                catch (TransferException e2) {
                    LOGGER.error(e2.getMessage(), e2);
                    throw new BusinessRuntimeException(e2.getMessage(), (Throwable)e2);
                }
            }
            catch (Throwable throwable) {
                if (!StringUtils.isEmpty(rootPath)) {
                    ReportDataSyncUtils.delFiles(new File(rootPath));
                }
                throw throwable;
            }
        }
        if (!StringUtils.isEmpty((String)rootPath)) {
            ReportDataSyncUtils.delFiles(new File(rootPath));
        }
        boolean isHeterogeneous = false;
        ReportDataSyncServerListService listService = (ReportDataSyncServerListService)SpringContextUtils.getBean(ReportDataSyncServerListService.class);
        List<ReportDataSyncServerInfoVO> infoVOS = listService.listServerInfos(SyncTypeEnums.PARAMDATA);
        for (ReportDataSyncServerInfoVO infoVO : infoVOS) {
            if (!infoVO.getFileFormat().equals(ReportFileFormat.YG.getCode())) continue;
            isHeterogeneous = true;
            break;
        }
        if (isHeterogeneous) {
            ReportDataSyncParamUtils.exportHeterogeneousParamToOss(xfLogEO, syncParams);
        }
        return objectInfo;
    }

    private static void getTaskParamDatas(String taskKey, List<String> schemeIds, List<String> formKeys, ByteArrayOutputStream outputStream) throws TransferException {
        String type;
        String guid;
        TransferEngine engine = new TransferEngine();
        String userId = NpContextHolder.getContext().getUserId();
        TransferContext context = new TransferContext(userId);
        String factoryId = "DEFINITION_TRANSFER_FACTORY_ID";
        ArrayList<Object> resItemList = new ArrayList<Object>();
        ReportDataSyncConfig service = (ReportDataSyncConfig)SpringContextUtils.getBean(ReportDataSyncConfig.class);
        Boolean paramAllModal = service.getParamAllModal() != null && service.getParamAllModal() != false;
        List businesses = new ArrayList();
        if (paramAllModal.booleanValue()) {
            factoryId = "ALL_MODE_FORM_SCHEME_FACTORY_ID";
        }
        if (!CollectionUtils.isEmpty(formKeys) && !paramAllModal.booleanValue()) {
            for (String formKey : formKeys) {
                guid = TransferNodeType.FORM.getValue() + "_" + formKey + "_business_t";
                type = TransferNodeType.FORM.getTitle();
                businesses = engine.selectBusinesses((ITransferContext)context, guid, type, factoryId, new ArrayList());
                resItemList.addAll(businesses);
            }
        } else {
            for (String schemeId : schemeIds) {
                guid = paramAllModal != false ? TransferNodeType.FORM_SCHEME.getValue() + "_" + schemeId + "_business_t" : TransferNodeType.FORM_SCHEME.getValue() + "_" + schemeId + "_t";
                type = TransferNodeType.FORM_SCHEME.getTitle();
                businesses = engine.selectBusinesses((ITransferContext)context, guid, type, factoryId, new ArrayList());
                resItemList.addAll(businesses);
            }
            guid = TransferNodeType.TASK.getValue() + "_" + taskKey + "_business_t";
            type = TransferNodeType.TASK.getTitle();
            businesses = engine.selectBusinesses((ITransferContext)context, guid, type, factoryId, new ArrayList());
            resItemList.addAll(businesses);
        }
        List relatedBusinesses = engine.getRelatedBusinesses((ITransferContext)context, resItemList);
        Set restItemGuids = resItemList.stream().map(ResItem::getGuid).collect(Collectors.toSet());
        relatedBusinesses = relatedBusinesses.stream().filter(item -> !restItemGuids.contains(item.getGuid())).collect(Collectors.toList());
        for (ResItem business : relatedBusinesses) {
            if (!"BaseDataDefine".equals(business.getType()) || business.getGuid().endsWith("MD_GCSUBJECT")) continue;
            business.setDataMode(DataMode.DATA);
        }
        resItemList.addAll(relatedBusinesses);
        engine.exportProcess((ITransferContext)context, resItemList, (OutputStream)outputStream);
    }

    private static void getformulaParamDatas(List<String> efdcSchemes, List<String> formulaSchemes, ByteArrayOutputStream outputStream) throws TransferException {
        List businesses;
        String type;
        String guid;
        TransferEngine engine = new TransferEngine();
        String userId = NpContextHolder.getContext().getUserId();
        TransferContext context = new TransferContext(userId);
        String factoryId = "DEFINITION_TRANSFER_FACTORY_ID";
        String BDEfactoryId = "CATEGORY_BDE_FETCHSETTING";
        ArrayList resItemList = new ArrayList();
        if (!CollectionUtils.isEmpty(efdcSchemes)) {
            for (String efdcScheme : efdcSchemes) {
                guid = TransferNodeType.FORMULA_SCHEME.getValue() + "_" + efdcScheme + "_t";
                type = TransferNodeType.FORMULA_SCHEME.getTitle();
                if (efdcScheme.length() == 16) {
                    businesses = engine.selectBusinesses((ITransferContext)context, guid, type, BDEfactoryId, new ArrayList());
                    resItemList.addAll(businesses);
                    continue;
                }
                businesses = engine.selectBusinesses((ITransferContext)context, guid, type, factoryId, new ArrayList());
                resItemList.addAll(businesses);
            }
        }
        if (!CollectionUtils.isEmpty(formulaSchemes)) {
            for (String formulaScheme : formulaSchemes) {
                guid = TransferNodeType.FORMULA_SCHEME.getValue() + "_" + formulaScheme + "_t";
                type = TransferNodeType.FORMULA_SCHEME.getTitle();
                businesses = engine.selectBusinesses((ITransferContext)context, guid, type, factoryId, new ArrayList());
                resItemList.addAll(businesses);
            }
        }
        engine.exportProcess((ITransferContext)context, resItemList, (OutputStream)outputStream);
    }

    private static ReportDataSyncReceiveTaskVO convertXfLogEOToReceiveTaskVO(ReportDataSyncIssuedLogEO xfLogEO) {
        ReportDataSyncReceiveTaskVO receiveTaskVO = new ReportDataSyncReceiveTaskVO();
        receiveTaskVO.setId(UUIDUtils.newUUIDStr());
        receiveTaskVO.setReceiveType(ReportDataReceiveType.LXDR.getCode());
        receiveTaskVO.setXfTime(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
        receiveTaskVO.setSyncTime(null);
        receiveTaskVO.setSyncVersion(xfLogEO.getSyncVersion());
        receiveTaskVO.setTaskCode(xfLogEO.getTaskCode());
        receiveTaskVO.setTaskId(xfLogEO.getTaskId());
        receiveTaskVO.setTaskTitle(xfLogEO.getTaskTitle());
        receiveTaskVO.setSyncParamAttachId(xfLogEO.getSyncParamAttachId());
        receiveTaskVO.setSyncDesAttachId(xfLogEO.getSyncDesAttachId());
        receiveTaskVO.setSyncDesAttachTitle(xfLogEO.getSyncDesAttachTitle());
        return receiveTaskVO;
    }

    public static void importParam(Set<String> importLogs, ReportDataSyncReceiveTaskEO receiveTaskEO, Boolean publish) {
        block16: {
            String syncParamAttachId = receiveTaskEO.getSyncParamAttachId();
            CommonFileDTO multipartFile = ((CommonFileService)SpringContextUtils.getBean(CommonFileService.class)).queryOssFileByFileKey(syncParamAttachId);
            try {
                String rootPath = System.getProperty(FilenameUtils.normalize("java.io.tmpdir")) + File.separator + "reportparamsync" + File.separator + "importparam" + File.separator + LocalDate.now() + File.separator + syncParamAttachId;
                String filePath = rootPath + File.separator + receiveTaskEO.getTaskTitle() + ".zip";
                File file = new File(FilenameUtils.normalize(filePath));
                FileUtils.forceMkdirParent(file);
                FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
                String snFolderPath = ReportDataSyncUtil.getSnFolderPath(syncParamAttachId);
                ReportDataSyncUtil.unzipFolder(filePath, "param", snFolderPath);
                ReportDataSyncService dataSyncService = (ReportDataSyncService)SpringContextUtils.getBean(ReportDataSyncService.class);
                String errorLogs = dataSyncService.upload(syncParamAttachId);
                if (null != errorLogs) {
                    importLogs.add(errorLogs);
                }
                String fileId = GUID.newGUID();
                ObjectStorageService service = ObjectStorageManager.getInstance().createObjectService("TEMP");
                InputStream inputStream = ReportDataSyncParamUtils.getParamInputStream(file);
                if (inputStream == null) {
                    return;
                }
                service.upload(fileId, inputStream);
                TransferFileRecorder recorder = new TransferFileRecorder(fileId);
                TransferEngine engine = new TransferEngine();
                Desc desc = engine.getDescInfo((IFileRecorder)recorder);
                String userId = NpContextHolder.getContext().getUserId();
                TransferContext context = new TransferContext(userId);
                JSONArray dataArray = TransferUtils.buildTreeTableData((ITransferContext)context, (Desc)desc);
                TransferProgressMonitor progressMonitor = new TransferProgressMonitor(context);
                JSONArray newDataArray = new JSONArray();
                ReportDataSyncParamUtils.listLeafNodeDatas(dataArray, newDataArray);
                String progressId = "import00";
                context.setProgressId(progressId);
                context.setProgressMonitor(progressMonitor);
                String compareFilesId = UUIDUtils.newUUIDStr();
                receiveTaskEO.setCompareFileId(compareFilesId);
                HashMap<String, String> extInfo = new HashMap<String, String>();
                extInfo.put("importDetailFileKey", compareFilesId);
                context.setExtInfo(extInfo);
                context.setCollectImportDetail(true);
                InputStream download = service.download(fileId);
                ZipFileRecorder zipFileRecorder = new ZipFileRecorder((ITransferContext)context, download);
                Desc zipFileDesc = engine.getDescInfo((IFileRecorder)zipFileRecorder);
                INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
                String option = nvwaSystemOptionService.get("GC_MULTILEVEL_OPTION_DECLARE", "MULTILEVEL_IMPORT_MULTILANGUAGE");
                zipFileDesc.setImportMultiLanguage("1".equals(option));
                Map changeInfos = TransferUtils.readImportSelections((Desc)zipFileDesc, (JSONArray)newDataArray);
                try {
                    engine.importProcess((ITransferContext)context, (IFileRecorder)zipFileRecorder, zipFileDesc, changeInfos);
                }
                catch (Exception e) {
                    importLogs.add(e.getMessage());
                    progressMonitor.error(e.getMessage());
                    throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
                }
                finally {
                    zipFileRecorder.destroy();
                }
                if (!publish.booleanValue()) break block16;
                byte[] bytes = null;
                try {
                    bytes = ZipUtils.unzipFileBytesAndDeleteTempFileOnExit((MultipartFile)multipartFile, (String)REPORT_PARAM_META_FILENAME);
                }
                catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                if (bytes == null) {
                    throw new BusinessRuntimeException("\u53c2\u6570\u5305\u5185\u5bb9\u4e0d\u6b63\u786e\uff0c\u7f3a\u5c11\u6587\u4ef6\uff1aReportParamMeta.JSON");
                }
                ReportDataSyncReceiveTaskVO receiveTaskVO = (ReportDataSyncReceiveTaskVO)JsonUtils.readValue((byte[])bytes, ReportDataSyncReceiveTaskVO.class);
                String taskId = receiveTaskVO.getTaskId();
                if (!StringUtils.isEmpty((String)taskId)) {
                    try {
                        TaskPlanPublishController publishController = (TaskPlanPublishController)SpringContextUtils.getBean(TaskPlanPublishController.class);
                        TaskPlanPublishObj publishObj = new TaskPlanPublishObj();
                        publishObj.setTaskID(taskId);
                        publishObj.setDeployTaskID(new UUIDUtils().toString());
                        String result = publishController.taskListPublishTask(publishObj);
                        LOGGER.info("\u4efb\u52a1id\uff1a" + taskId + "\u53d1\u5e03\u7ed3\u679c\u4e3a:" + result);
                    }
                    catch (Exception e) {
                        LOGGER.error("\u4efb\u52a1id\uff1a" + taskId + "\u53d1\u5e03\u5931\u8d25:" + e.getMessage());
                    }
                }
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                importLogs.add("\u6570\u636e\u5bfc\u5165\u5f02\u5e38\uff1a" + e.getMessage());
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
        }
    }

    public static void listLeafNodeDatas(JSONArray dataArray, JSONArray newDataArray) {
        for (int i = 0; i < dataArray.length(); ++i) {
            JSONObject resItemJson = dataArray.getJSONObject(i);
            JSONArray children = (JSONArray)resItemJson.get("children");
            if (children != null && !children.isEmpty()) {
                ReportDataSyncParamUtils.listLeafNodeDatas(children, newDataArray);
                continue;
            }
            if (2 == (Integer)resItemJson.get("changeMode")) {
                resItemJson = resItemJson.put("operatetype", 1);
            }
            if (0 == (Integer)resItemJson.get("changeMode") && (resItemJson.get("sourceModifiedTime") == null || StringUtils.isEmpty((String)((String)resItemJson.get("sourceModifiedTime"))))) {
                resItemJson = resItemJson.put("operatetype", 1);
            }
            newDataArray = newDataArray.put((Object)resItemJson);
            dataArray.toList();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static InputStream getParamInputStream(File file) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));){
            InputStream inputStream;
            ZipEntry entry;
            String nvdataFileName = "";
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.getName().endsWith(".nvdata")) continue;
                nvdataFileName = entry.getName();
                break;
            }
            InputStream inputStream2 = inputStream = ZipUtils.get((ZipFile)new ZipFile(file), (String)nvdataFileName);
            return inputStream2;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    public static void exportHeterogeneousParamToOss(ReportDataSyncIssuedLogEO xfLogEO, ReportDataSyncParams syncParams) {
        ReportParam reportParam = syncParams.getReportParam();
        if (reportParam == null) {
            return;
        }
        String rootPath = null;
        String ygSyncParamAttachId = UUIDUtils.newUUIDStr();
        String taskId = reportParam.getTaskId();
        String schemeId = (String)reportParam.getSchemeIds().get(0);
        try {
            CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
            rootPath = BatchExportConsts.EXPORTDIR + File.separator + "reportparamsync" + File.separator + "exportparam" + File.separator + LocalDate.now() + File.separator + ygSyncParamAttachId + File.separator;
            File rootFile = new File(rootPath);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            String fileName = xfLogEO.getTaskTitle() + "\u53c2\u6570\u5305.zip";
            String dataZipLocation = rootPath + fileName;
            ReportDataSyncUtils.createEmptyZipFile(dataZipLocation);
            ReportDataSyncParamUtils.exportMetadata(dataZipLocation, rootPath, taskId, schemeId);
            ReportDataSyncParamUtils.exportMetadataFormula(dataZipLocation, rootPath, schemeId);
            ReportDataSyncParamUtils.exportMetadataView(dataZipLocation, rootPath, taskId, schemeId);
            ReportDataSyncParamUtils.exportUpdataDesc(dataZipLocation, rootPath, xfLogEO.getSyncDesAttachId(), xfLogEO.getSyncDesAttachTitle());
            File srcFile = new File(dataZipLocation);
            byte[] bytes = FileCopyUtils.copyToByteArray(srcFile);
            VaParamSyncMultipartFile zipFile = new VaParamSyncMultipartFile("multipartFile", fileName, "multipart/form-data; charset=ISO-8859-1", bytes);
            commonFileService.uploadFileToOss((MultipartFile)zipFile, ygSyncParamAttachId);
        }
        catch (Exception e) {
            try {
                LOGGER.error("\u751f\u6210\u5f02\u6784\u7cfb\u7edf\u53c2\u6570\u5305\u5931\u8d25\uff1a" + e.getMessage(), e);
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
            catch (Throwable throwable) {
                if (!StringUtils.isEmpty(rootPath)) {
                    ReportDataSyncUtils.delFiles(new File(rootPath));
                }
                throw throwable;
            }
        }
        if (!StringUtils.isEmpty((String)rootPath)) {
            ReportDataSyncUtils.delFiles(new File(rootPath));
        }
        xfLogEO.setYgParamAttachId(ygSyncParamAttachId);
    }

    private static void exportMetadata(String dataZipLocation, String rootPath, String taskId, String schemeId) throws Exception {
        try {
            String filePath = CommonReportUtil.createNewPath((String)rootPath, (String)"PARAM_METADATA");
            File file = new File(filePath + File.separator + "PARAM_METADATA.csv");
            file.createNewFile();
            String[] heads = new String[]{"ID", "CODE", "TITLE", "DESCR", "DATATYPE", "ACCURACY", "FRACTIONDIGITS", "FORMCODE", "FLOATAREA", "CREATETIME", "UPTIMESTAMP"};
            List dataList = EntNativeSqlDefaultDao.getInstance().selectMap(ReportDataSyncParamUtils.getMetaDataSql(taskId, schemeId), new Object[0]);
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            Row head = sheet.createRow(0);
            for (int i = 0; i < heads.length; ++i) {
                head.createCell(i).setCellValue(heads[i]);
            }
            for (int row = 0; row < dataList.size(); ++row) {
                Map data = (Map)dataList.get(row);
                Row hssfRow = sheet.createRow(row + 1);
                for (int i = 0; i < heads.length; ++i) {
                    String fieldCode = heads[i];
                    Object cellValue = data.get(fieldCode);
                    hssfRow.createCell(i).setCellValue(cellValue == null ? null : cellValue.toString());
                }
            }
            workbook.write(Files.newOutputStream(file.toPath(), new OpenOption[0]));
            workbook.close();
            ZipUtils.addFile((String)dataZipLocation, (String)filePath);
        }
        catch (Exception e) {
            LOGGER.error("\u5f02\u6784\u7cfb\u7edf\u53c2\u6570\u5305\u5143\u6570\u636e\u5bfc\u51fa\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    private static void exportMetadataFormula(String dataZipLocation, String rootPath, String schemeId) throws Exception {
        DesignFormulaSchemeDefineService formulaSchemeService = (DesignFormulaSchemeDefineService)SpringContextUtils.getBean(DesignFormulaSchemeDefineService.class);
        IFormFormulaService formFormulaService = (IFormFormulaService)SpringContextUtils.getBean(IFormFormulaService.class);
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        try {
            String filePath = CommonReportUtil.createNewPath((String)rootPath, (String)"PARAM_METADATA_FORMULA");
            List formulaSchemeDefines = formulaSchemeService.queryFormulaSchemeDefineByFormScheme(schemeId);
            List formDefines = iRunTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
            ArrayList formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toCollection(ArrayList::new));
            for (DesignFormulaSchemeDefine formulaScheme : formulaSchemeDefines) {
                File file = new File(filePath + File.separator + formulaScheme.getTitle() + "_\u5168\u90e8\u516c\u5f0f.csv");
                file.createNewFile();
                String formulaSchemeKey = formulaScheme.getKey();
                final FileOutputStream os = new FileOutputStream(file);
                Throwable throwable = null;
                try {
                    MockHttpServletResponse res = new MockHttpServletResponse(){

                        public ServletOutputStream getOutputStream() {
                            return new DelegatingServletOutputStream(os);
                        }
                    };
                    formFormulaService.exportAllFormulas(formulaSchemeKey, formKeys, (HttpServletResponse)res, false);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (os == null) continue;
                    if (throwable != null) {
                        try {
                            os.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    os.close();
                }
            }
            ZipUtils.addFile((String)dataZipLocation, (String)filePath);
        }
        catch (Exception e) {
            LOGGER.error("\u5f02\u6784\u7cfb\u7edf\u53c2\u6570\u5305\u516c\u5f0f\u65b9\u6848\u5bfc\u51fa\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    private static void exportMetadataView(String dataZipLocation, String rootPath, String taskId, String schemeId) throws Exception {
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        ExcelExportService excelExportService = (ExcelExportService)SpringContextUtils.getBean(ExcelExportService.class);
        try {
            String filePath = CommonReportUtil.createNewPath((String)rootPath, (String)"PARAM_METADATA_VIEW");
            ExcelExportVO excelExportObj = new ExcelExportVO();
            excelExportObj.setTaskKey(taskId);
            excelExportObj.setSchemeId(schemeId);
            List formDefines = iRunTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
            String[] formKeys = (String[])formDefines.stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
            excelExportObj.setFormKeys(formKeys);
            excelExportObj.setShowForm("010");
            CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)SpringContextUtils.getBean(CacheObjectResourceRemote.class);
            String cellFileId = UUIDUtils.newUUIDStr();
            excelExportObj.setDownLoadKey(cellFileId);
            excelExportObj.setViewType("10");
            SimpleAsyncProgressMonitor cellMonitor = new SimpleAsyncProgressMonitor(cellFileId, cacheObjectResourceRemote);
            excelExportService.exportParamAsync(excelExportObj, (AsyncTaskMonitor)cellMonitor);
            File cellFile = new File(filePath + File.separator + "\u5355\u5143\u683c\u7f16\u53f7\u89c6\u56fe.csv");
            cellFile.createNewFile();
            try (FileOutputStream os = new FileOutputStream(cellFile);){
                OssUploadUtil.fileDownLoad((String)cellFileId, (OutputStream)os);
            }
            String tableFileId = UUIDUtils.newUUIDStr();
            excelExportObj.setDownLoadKey(tableFileId);
            excelExportObj.setViewType("8");
            SimpleAsyncProgressMonitor storageMonitor = new SimpleAsyncProgressMonitor(tableFileId, cacheObjectResourceRemote);
            excelExportService.exportParamAsync(excelExportObj, (AsyncTaskMonitor)storageMonitor);
            File tableFile = new File(filePath + File.separator + "\u5b58\u50a8\u8868\u89c6\u56fe.csv");
            tableFile.createNewFile();
            try (FileOutputStream os = new FileOutputStream(tableFile);){
                OssUploadUtil.fileDownLoad((String)tableFileId, (OutputStream)os);
            }
            ZipUtils.addFile((String)dataZipLocation, (String)filePath);
        }
        catch (Exception e) {
            LOGGER.error("\u5f02\u6784\u7cfb\u7edf\u53c2\u6570\u5305\u89c6\u56fe\u5bfc\u51fa\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    private static void exportUpdataDesc(String dataZipLocation, String rootPath, String syncDesAttachId, String syncDesAttachTitle) throws Exception {
        if (StringUtils.isEmpty((String)syncDesAttachId)) {
            return;
        }
        try {
            CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
            CommonFileDTO multipartDescFile = commonFileService.queryOssFileByFileKey(syncDesAttachId);
            String filePath = CommonReportUtil.createNewPath((String)rootPath, (String)"PARAM_UPDATE_DESC");
            File uploadDesc = new File(filePath + File.separator + syncDesAttachTitle);
            uploadDesc.createNewFile();
            try (FileOutputStream os = new FileOutputStream(uploadDesc);){
                byte[] bytes = multipartDescFile.getBytes();
                os.write(bytes, 0, bytes.length);
            }
            ZipUtils.addFile((String)dataZipLocation, (String)filePath);
        }
        catch (Exception e) {
            LOGGER.error("\u5f02\u6784\u7cfb\u7edf\u53c2\u6570\u5305\u9644\u4ef6\u5bfc\u51fa\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    private static String getMetaDataSql(String taskId, String schemeId) {
        return "select f.DF_KEY AS ID,\n       tb.DT_CODE || '[' || f.DF_CODE || ']' AS CODE,\n       f.DF_TITLE AS TITLE,\n       f.DF_DESC AS DESCR,\n       f.DF_DATATYPE AS DATATYPE,\n       f.DF_PRECISION AS ACCURACY,\n       f.DF_DECIMAL AS FRACTIONDIGITS,\n       form.FM_CODE || '|' || form.FM_TITLE AS FORMCODE,\n       region.DR_KEY AS FLOATAREA,\n       --tb.DT_TITLE,\n       TO_CHAR(F.DF_UPDATETIME, 'YYYY-MM-DD HH24:MI:SS') AS CREATETIME,\n       TO_CHAR(F.DF_UPDATETIME, 'YYYY-MM-DD HH24:MI:SS') AS UPTIMESTAMP\nfrom NR_PARAM_TASK task\n         join NR_PARAM_FORMSCHEME scheme on scheme.FC_TASK_KEY = task.TK_KEY\n         join NR_PARAM_FORM form on form.FM_FORMSCHEME = scheme.FC_KEY\n         join NR_PARAM_DATAREGION region on region.DR_FORM_KEY = form.FM_KEY\n         join NR_PARAM_DATALINK datalink on datalink.DL_REGION_KEY = region.DR_KEY\n         join NR_DATASCHEME_FIELD f on datalink.DL_FIELD_KEY = f.DF_KEY\n         join NR_DATASCHEME_TABLE tb on tb.DT_KEY = f.DF_DT_KEY\nwhere 1 = 1\n  and task.TK_KEY = '" + taskId + "' \n  and scheme.FC_KEY = '" + schemeId + "' ";
    }
}

