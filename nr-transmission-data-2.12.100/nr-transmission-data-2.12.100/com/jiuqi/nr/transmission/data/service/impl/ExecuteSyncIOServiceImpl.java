/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.transmission.data.common.FileHelper;
import com.jiuqi.nr.transmission.data.common.MappingType;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import com.jiuqi.nr.transmission.data.dto.AnalysisDTO;
import com.jiuqi.nr.transmission.data.dto.AnalysisParam;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.exception.DataExportException;
import com.jiuqi.nr.transmission.data.exception.DataImportException;
import com.jiuqi.nr.transmission.data.internal.file.FileHandleService;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.ImportParam;
import com.jiuqi.nr.transmission.data.intf.ImportReturnParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.service.IExecuteSyncIOService;
import com.jiuqi.nr.transmission.data.service.IFileAnalysisService;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import com.jiuqi.nr.transmission.data.service.ISyncSchemeService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ExecuteSyncIOServiceImpl
implements IExecuteSyncIOService {
    @Autowired
    private IFileAnalysisService fileAnalysisService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ISyncSchemeService syncSchemeService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private FileHandleService fileHandleService;
    @Autowired
    private ISyncHistoryService syncHistoryService;
    private static final Logger logger = LoggerFactory.getLogger(ExecuteSyncIOServiceImpl.class);

    @Override
    public ImportReturnParam checkFlowType(InputStream is) throws Exception {
        String fileKey = "";
        try {
            fileKey = Utils.fileUpload(is);
        }
        catch (Exception e) {
            logger.error("\u591a\u7ea7\u90e8\u7f72\u79bb\u7ebf\u88c5\u5165\u89e3\u6790\u6587\u4ef6\u65f6\u5019\u88c5\u5165\u65e5\u5fd7\u670d\u52a1\u5668\u51fa\u9519" + e.getMessage(), e);
            throw new DataImportException(MultilingualLog.checkFlowTypeMessage(1, "") + e.getMessage(), e);
        }
        String tempDir = ZipUtils.newTempDir();
        String path = tempDir + "/" + "data.temp";
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                Utils.fileDelete(fileKey);
                e.printStackTrace();
            }
        }
        ImportReturnParam inportReturnParam = new ImportReturnParam();
        boolean newFile = file.createNewFile();
        if (newFile) {
            try (OutputStream fos = Files.newOutputStream(file.toPath(), new OpenOption[0]);
                 InputStream newIs = Files.newInputStream(file.toPath(), new OpenOption[0]);){
                AnalysisDTO analysisDTO;
                Utils.fileDownLoad(fileKey, fos);
                Map<String, ZipUtils.ZipSubFile> zipFiles = ZipUtils.unZip((InputStream)newIs).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFileName, f -> f));
                try {
                    AnalysisParam analysisParam = AnalysisParam.getAnalysisParam(null, MappingType.ANALYSIS_MAPPING);
                    analysisDTO = this.fileAnalysisService.analysisParam(zipFiles, analysisParam);
                }
                catch (Exception e) {
                    Utils.deleteAllFilesOfDir(file);
                    Utils.fileDelete(fileKey);
                    logger.error(e.getMessage(), e);
                    throw new DataImportException(MultilingualLog.checkFlowTypeMessage(3, "") + e.getMessage(), e);
                }
                SyncSchemeParamDTO syncSchemeParamDTO = analysisDTO.getSyncSchemeParamDTO();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(syncSchemeParamDTO.getFormSchemeKey());
                if (formScheme == null) {
                    throw new DataImportException(MultilingualLog.checkFlowTypeMessage(4, ""));
                }
                FlowsType flowsType = formScheme.getFlowsSetting().getFlowsType();
                if (analysisDTO.getFormDataZipMap().get("WORKFLOW_TRANSMISSION_DATA.zip") != null) {
                    inportReturnParam.setHasWorkFlow(true);
                }
                inportReturnParam.setMappingSchemes(analysisDTO.getMappingSchemes());
                inportReturnParam.setFlowType(!FlowsType.NOSTARTUP.equals((Object)flowsType));
                inportReturnParam.setFileKey(fileKey);
                Utils.deleteAllFilesOfDir(file);
            }
            catch (Exception e) {
                logger.error("\u591a\u7ea7\u90e8\u7f72\u79bb\u7ebf\u88c5\u5165\u89e3\u6790\u6587\u4ef6\u65f6\u4ece\u65e5\u5fd7\u670d\u52a1\u5668\u4e0b\u8f7d\u6587\u4ef6\u51fa\u9519" + e.getMessage(), e);
                Utils.fileDelete(fileKey);
                Utils.deleteAllFilesOfDir(file);
                throw new DataImportException(MultilingualLog.checkFlowTypeMessage(2, "") + e.getMessage(), e);
            }
        }
        return inportReturnParam;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TransmissionResult<DataImportResult> executeOffLineImport(ImportParam importParam, TransmissionMonitor parentMonitor, double processSize) throws Exception {
        String executeKey = importParam.getExecuteKey();
        TransmissionResult<DataImportResult> transmissionResult = new TransmissionResult<DataImportResult>();
        String tempDir = ZipUtils.newTempDir();
        String path = tempDir + "/" + "data.temp";
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (Exception e) {
                transmissionResult.setSuccess(false);
                transmissionResult.setMessage(e.getMessage());
                logger.error(e.getMessage(), e);
                Utils.deleteAllFilesOfDirByPath(tempDir);
                return transmissionResult;
            }
        }
        try (OutputStream fos = Files.newOutputStream(file.toPath(), new OpenOption[0]);){
            Utils.fileDownLoad(importParam.getFileKey(), fos);
        }
        catch (Exception e) {
            transmissionResult.setSuccess(false);
            transmissionResult.setMessage("\u6587\u4ef6\u670d\u52a1\u5668\u4e0b\u8f7d\u6587\u4ef6\u5931\u8d25");
            logger.error(e.getMessage(), e);
            Utils.deleteAllFilesOfDirByPath(tempDir);
            return transmissionResult;
        }
        TransmissionMonitor monitor = parentMonitor != null ? new TransmissionMonitor(executeKey, this.cacheObjectResourceRemote, parentMonitor, processSize) : new TransmissionMonitor(executeKey, this.cacheObjectResourceRemote);
        DataImportResult result = null;
        try (InputStream is = Files.newInputStream(file.toPath(), new OpenOption[0]);){
            importParam.setImportType(2);
            result = this.fileHandleService.fileImport(is, monitor, importParam);
            transmissionResult.setData(result);
            if (!result.isResult()) {
                throw new DataImportException(result.getLog());
            }
            if (result.getSyncErrorNum() == 0) {
                monitor.finish(result.getLog() + "\u88c5\u5165\u6210\u529f", result);
            }
            transmissionResult.setSuccess(true);
        }
        catch (Exception e) {
            monitor.error("\u88c5\u5165\u5931\u8d25", e);
            result = monitor.getProgressInfo().getResult();
            this.updateErrorHistory(executeKey, e, result);
            logger.error(e.getMessage(), e);
            transmissionResult.setSuccess(false);
            transmissionResult.setMessage(e.getMessage());
            TransmissionResult<DataImportResult> transmissionResult2 = transmissionResult;
            return transmissionResult2;
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempDir);
        }
        return transmissionResult;
    }

    private void updateErrorHistory(String executeKey, Exception e, DataImportResult result) {
        SyncHistoryDTO syncHistoryDTO = new SyncHistoryDTO();
        Date endTime = new Date();
        syncHistoryDTO.setKey(executeKey);
        syncHistoryDTO.setEndTime(endTime);
        syncHistoryDTO.setStatus(4);
        syncHistoryDTO.setDetail(e.getMessage());
        syncHistoryDTO.setResult(result);
        this.syncHistoryService.update(syncHistoryDTO);
    }

    @Override
    public void exportByHistory(SyncHistoryDTO syncHistoryDTO, HttpServletResponse response) throws Exception {
        block18: {
            String fileKey = syncHistoryDTO.getFileKey();
            String tempDir = ZipUtils.newTempDir();
            String path = tempDir + "/" + "data.temp";
            PathUtils.validatePathManipulation((String)path);
            File file = new File(path);
            SyncSchemeDTO syncSchemeDTO = this.syncSchemeService.getWithOutParam(syncHistoryDTO.getSchemeKey());
            SyncSchemeParamDO syncSchemeParamDO = syncHistoryDTO.getSyncSchemeParamDO();
            String fileName = syncSchemeDTO.getTitle() + "[" + syncSchemeParamDO.getPeriodValue() + "]";
            if (!file.exists()) {
                try {
                    FileUtils.forceMkdirParent(file);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.hasText(fileKey)) {
                try (OutputStream fos = Files.newOutputStream(file.toPath(), new OpenOption[0]);){
                    Utils.fileDownLoad(fileKey, fos);
                    fos.close();
                    FileHelper.exportFile(file, fileName, response);
                    break block18;
                }
                catch (Exception e) {
                    logger.error("\u591a\u7ea7\u90e8\u7f72\u4ece\u5386\u53f2\u8bb0\u5f55\u5bfc\u51fa\u6570\u636e\u5305\u5931\u8d25\uff0c", e);
                    throw new DataExportException(MultilingualLog.exportByHistoryMessage(1, ""));
                }
            }
            logger.error("\u8be5\u6587\u4ef6\u67e5\u8be2\u4e0d\u5230\uff0c\u8bf7\u76f4\u63a5\u5bfc\u51fa\u6587\u4ef6");
            throw new DataExportException(MultilingualLog.exportByHistoryMessage(2, ""));
        }
    }

    @Override
    public List<String> deleteFileServer(List<String> fileKeys) {
        ArrayList<String> failFile = new ArrayList<String>();
        for (String fileKey : fileKeys) {
            boolean b = Utils.fileDelete(fileKey);
            if (b) continue;
            failFile.add(fileKey);
        }
        return failFile;
    }
}

