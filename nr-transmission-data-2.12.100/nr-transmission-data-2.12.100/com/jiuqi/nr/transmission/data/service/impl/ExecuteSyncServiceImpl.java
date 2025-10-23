/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.subsystem.core.SubSystemException
 *  com.jiuqi.nvwa.subsystem.core.manage.ISubServerManager
 *  com.jiuqi.nvwa.subsystem.core.model.SubServer
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.transmission.data.common.FileHelper;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import com.jiuqi.nr.transmission.data.domain.TransmissionTaskUserParam;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.exception.DataExportException;
import com.jiuqi.nr.transmission.data.exception.DataImportException;
import com.jiuqi.nr.transmission.data.exception.TransmissionSyncException;
import com.jiuqi.nr.transmission.data.exception.UserNameAuthException;
import com.jiuqi.nr.transmission.data.internal.file.FileHandleService;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.ImportParam;
import com.jiuqi.nr.transmission.data.intf.SyncFileParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionDataParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.intf.UserInfoParam;
import com.jiuqi.nr.transmission.data.job.TransmissionDataJob;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.service.IExecuteSyncService;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import com.jiuqi.nr.transmission.data.service.ITransmissionDataService;
import com.jiuqi.nr.transmission.data.vo.ImportOtherVO;
import com.jiuqi.nvwa.subsystem.core.SubSystemException;
import com.jiuqi.nvwa.subsystem.core.manage.ISubServerManager;
import com.jiuqi.nvwa.subsystem.core.model.SubServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ExecuteSyncServiceImpl
implements IExecuteSyncService {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteSyncServiceImpl.class);
    @Autowired
    private ITransmissionDataService transmissionDataService;
    @Autowired
    private ISyncHistoryService syncHistoryService;
    @Autowired
    private FileHandleService fileHandleService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private ISubServerManager subServerManager;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IReportParamService reportParamService;
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public TransmissionResult executeSync(SyncSchemeParamDTO syncSchemeParamDTO) throws TransmissionSyncException {
        TransmissionResult transmissionResult;
        File file;
        String executeKey;
        logger.info("\u5f53\u524d\u670d\u52a1\u51c6\u5907\u5f00\u59cb\u591a\u7ea7\u90e8\u7f72\u6570\u636e\u6253\u5305\u53ca\u63a8\u9001\uff01");
        if (StringUtils.hasText(syncSchemeParamDTO.getKey())) {
            executeKey = syncSchemeParamDTO.getKey();
        } else {
            executeKey = UUID.randomUUID().toString();
            syncSchemeParamDTO.setKey(executeKey);
            logger.info("\u591a\u7ea7\u90e8\u7f72\u540c\u6b65\u7684\u65f6\u5019\uff0c\u524d\u7aef\u4f20\u6765\u7684\u53c2\u6570\u7f3a\u5c11\u4e3b\u952e\uff0c\u8bf7\u68c0\u67e5\u524d\u7aef\u662f\u5426\u751f\u6210\u4e86\u4e3b\u952e\uff01");
        }
        Date startTime = new Date();
        this.insertHistory(syncSchemeParamDTO, 2, "", NpContextHolder.getContext().getUserId(), startTime, "", null);
        TransmissionMonitor monitor = new TransmissionMonitor(executeKey, this.cacheObjectResourceRemote);
        try {
            String executeExportKey = UUID.randomUUID().toString();
            monitor.progressAndMessage(0.0, MultilingualLog.executeSyncMessage(1));
            TransmissionMonitor exportMonitor = new TransmissionMonitor(executeExportKey, this.cacheObjectResourceRemote, monitor, 0.9);
            file = this.fileHandleService.fileExport(exportMonitor, syncSchemeParamDTO);
            this.syncHistoryService.updateDetail(syncSchemeParamDTO.getKey(), syncSchemeParamDTO.getExportFilterMessage());
        }
        catch (Exception e) {
            this.executeSyncError(e, syncSchemeParamDTO, monitor);
            throw new TransmissionSyncException(e);
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u6570\u636e\u6253\u5305\u5b8c\u6210\uff0c\u51c6\u5907\u5c06\u6587\u4ef6\u4e0a\u4f20\u81f3\u6587\u4ef6\u670d\u52a1\u5668\uff01");
        monitor.progressAndMessage(0.91, MultilingualLog.executeSyncMessage(2));
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            String fileKey = Utils.fileUpload(fileInputStream);
            this.syncHistoryService.updateField(executeKey, "TH_FILE_KEY", fileKey);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            this.deleteFile(file);
            throw new TransmissionSyncException(e);
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u6570\u636e\u6587\u4ef6\u4e0a\u4f20\u81f3\u6587\u4ef6\u670d\u52a1\u5668\u6210\u529f\uff0c\u51c6\u5907\u63a8\u9001\u4e0a\u7ea7\u670d\u52a1\u5668\uff01");
        monitor.progressAndMessage(0.92, MultilingualLog.executeSyncMessage(3));
        TransmissionDataParam transmissionDataParam = new TransmissionDataParam();
        transmissionDataParam.setFile(file);
        transmissionDataParam.setSyncSchemeParamDTO(syncSchemeParamDTO);
        transmissionDataParam.setStartTime(startTime);
        try {
            transmissionResult = this.transmissionDataService.pushData(transmissionDataParam, this.getParentServeNode(), monitor);
        }
        catch (Exception e) {
            this.executeSyncError(e, syncSchemeParamDTO, monitor);
            this.deleteFile(file);
            throw new TransmissionSyncException(e);
        }
        transmissionResult.setExecuteKey(executeKey);
        try {
            String logHelperMessage = this.reportParamService.doLogHelperMessage(syncSchemeParamDTO);
            LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0b\u7ea7\u540c\u6b65\u524d\u4fe1\u606f", (String)(logHelperMessage + "\uff0c\u540c\u6b65\u6267\u884c\u4eba\uff1a" + NpContextHolder.getContext().getUser().getName()));
        }
        catch (Exception e) {
            logger.error("\u591a\u7ea7\u90e8\u7f72\u4e0b\u7ea7\u540c\u6b65\u524d\u65e5\u5fd7\u4fe1\u606f\u62a5\u9519\uff1a" + e.getMessage(), e);
            this.deleteFile(file);
        }
        this.deleteFile(file);
        DataImportResult dataImportResult = new DataImportResult();
        String finishMessage = MultilingualLog.executeSyncMessage(4);
        monitor.finish(finishMessage, dataImportResult);
        logger.info("\u591a\u7ea7\u90e8\u7f72\u4e0b\u7ea7\u670d\u52a1\u5668\u6570\u636e\u6253\u5305\u5e76\u63a8\u9001\u4e0a\u7ea7\u670d\u52a1\u5b8c\u6210\uff0c\u4e0a\u7ea7\u670d\u52a1\u5668\u5f00\u59cb\u6267\u884c\u88c5\u5165\uff01");
        return transmissionResult;
    }

    private void executeSyncError(Exception e, SyncSchemeParamDTO syncSchemeParamDTO, TransmissionMonitor monitor) {
        logger.error(e.getMessage(), e);
        this.updateErrorHistory(syncSchemeParamDTO.getKey(), e, null);
        monitor.error("\u540c\u6b65\u5931\u8d25", e);
        String logHelperMessage = this.reportParamService.doLogHelperMessage(syncSchemeParamDTO);
        LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0b\u7ea7\u540c\u6b65\u524d\u4fe1\u606f", (String)(logHelperMessage + "\uff0c\u540c\u6b65\u6267\u884c\u4eba\uff1a" + NpContextHolder.getContext().getUser().getName() + "\u3002\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), (int)0);
    }

    private void deleteFile(File file) {
        if (file.exists()) {
            Utils.deleteAllFilesOfDirByPath(file.getParent());
        }
    }

    @Override
    public TransmissionResult<DataImportResult> onlineImport(InputStream is, SyncFileParam syncFileParam) throws DataImportException {
        String fileKey;
        SyncSchemeParamDTO syncSchemeParamDTO = syncFileParam.getSyncSchemeParamDTO();
        UserInfoParam userInfoParam = syncFileParam.getUserInfo();
        TransmissionResult<DataImportResult> transmissionResult = new TransmissionResult<DataImportResult>();
        TransmissionMonitor monitor = new TransmissionMonitor(syncSchemeParamDTO.getKey(), this.cacheObjectResourceRemote);
        try {
            boolean checkUserResult = this.checkSelectUser(userInfoParam.getSyncUserName(), syncFileParam.getSyncServiceName());
            if (!checkUserResult) {
                throw new UserNameAuthException(userInfoParam.getSyncUserName());
            }
        }
        catch (Exception e) {
            String errorMessage = MultilingualLog.onlineImportMessage(1, "") + e.getMessage();
            monitor.error("\u540c\u6b65\u5931\u8d25", new RuntimeException(errorMessage));
            this.insertHistory(syncSchemeParamDTO, 4, "", userInfoParam.getSyncUserId(), new Date(), errorMessage, new Date());
            logger.error(e.getMessage(), e);
            throw new DataImportException(e);
        }
        try {
            fileKey = Utils.fileUpload(is);
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e);
            String fileError = MultilingualLog.onlineImportMessage(2, "");
            monitor.error("\u540c\u6b65\u5931\u8d25", new RuntimeException(fileError));
            throw new DataImportException(fileError, e);
        }
        String executeKey = syncSchemeParamDTO.getKey();
        this.insertHistory(syncSchemeParamDTO, 2, fileKey, userInfoParam.getSyncUserId(), syncFileParam.getStartTimes(), "", null);
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        TransmissionDataJob job = new TransmissionDataJob();
        job.setTitle("\u6570\u636e\u88c5\u5165");
        job.setUserGuid(user.getId());
        job.setUserName(user.getName());
        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("key", executeKey);
        params.put("fileKey", fileKey);
        params.put("processKey", executeKey);
        params.put("userName", NpContextHolder.getContext().getUserName());
        params.put("syncUserId", userInfoParam.getSyncUserId());
        params.put("language", NpContextHolder.getContext().getLocale().toLanguageTag());
        job.setParams(params);
        String instanceId = null;
        try {
            instanceId = RealTimeJobManager.getInstance().commit((AbstractRealTimeJob)job);
        }
        catch (JobsException e) {
            logger.info(e.getMessage(), e);
        }
        this.syncHistoryService.updateField(executeKey, "TH_INSTANCE_ID", instanceId);
        transmissionResult.setInstanceId(instanceId);
        transmissionResult.setSuccess(true);
        transmissionResult.setMessage(MultilingualLog.onlineImportMessage(3, ""));
        try {
            String s = this.reportParamService.doLogHelperMessage(syncSchemeParamDTO);
            LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0a\u7ea7\u540c\u6b65\u524d\u4fe1\u606f", (String)s);
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        return transmissionResult;
    }

    private boolean checkSelectUser(String syncUserName, String syncServiceName) {
        String subServerExtConfig;
        Optional username = this.systemUserService.findByUsername(syncUserName);
        if (username.isPresent()) {
            return true;
        }
        try {
            subServerExtConfig = this.subServerManager.getSubServerExtConfig(syncServiceName, "EXT_TASK_LIST");
        }
        catch (SubSystemException e) {
            throw new DataImportException(MultilingualLog.onlineImportMessage(4, ""), e);
        }
        if (StringUtils.hasText(subServerExtConfig)) {
            int selectedUserindex = subServerExtConfig.indexOf("selectedUser");
            if (selectedUserindex != -1) {
                TransmissionTaskUserParam param = (TransmissionTaskUserParam)JacksonUtils.jsonToObject((String)subServerExtConfig, TransmissionTaskUserParam.class);
                if (param != null && StringUtils.hasText(param.getSelectedUser())) {
                    String selectedUser = param.getSelectedUser();
                    String[] userList = selectedUser.split(";");
                    List<String> userLists = Arrays.asList(userList);
                    return userLists.contains(syncUserName);
                }
                return false;
            }
            return false;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TransmissionResult<DataImportResult> offLineImport(InputStream is, ImportOtherVO importOtherVO) throws Exception {
        String executeKey = importOtherVO.getExecuteKey();
        String mappingSchemeKey = importOtherVO.getMappingSchemeKey();
        assert (StringUtils.hasText(executeKey));
        TransmissionResult<DataImportResult> transmissionResult = new TransmissionResult<DataImportResult>();
        transmissionResult.setExecuteKey(executeKey);
        String tempDir = ZipUtils.newTempDir();
        String path = tempDir + "/" + "data.temp";
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                transmissionResult.setSuccess(false);
                transmissionResult.setMessage(e.getMessage());
                Utils.deleteAllFilesOfDirByPath(tempDir);
                e.printStackTrace();
            }
        }
        TransmissionMonitor monitor = new TransmissionMonitor(executeKey, this.cacheObjectResourceRemote);
        DataImportResult result = null;
        try (OutputStream fos = Files.newOutputStream(file.toPath(), new OpenOption[0]);){
            this.writeInput2Output(fos, is);
            try (InputStream newIs = Files.newInputStream(file.toPath(), new OpenOption[0]);){
                ImportParam importParam = new ImportParam();
                importParam.setExecuteKey(executeKey);
                importParam.setMappingSchemeKey(mappingSchemeKey);
                importParam.setImportType(0);
                importParam.setTaskKey(importOtherVO.getTaskKey());
                importParam.setFormSchemeKey(importOtherVO.getFormSchemeKey());
                importParam.setVariables(importOtherVO.getVariables());
                importParam.setMode(importOtherVO.getMode());
                result = this.fileHandleService.fileImport(newIs, monitor, importParam);
            }
            transmissionResult.setData(result);
            if (!result.isResult()) {
                throw new DataImportException(result.getLog());
            }
            if (result.getSyncErrorNum() == 0) {
                monitor.finish(result.getLog(), result);
            }
            transmissionResult.setSuccess(true);
        }
        catch (Exception e) {
            monitor.error("\u88c5\u5165\u5931\u8d25", e);
            result = monitor.getProgressInfo().getResult();
            this.updateErrorHistory(executeKey, e, result);
            transmissionResult.setSuccess(false);
            transmissionResult.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
            TransmissionResult<DataImportResult> transmissionResult2 = transmissionResult;
            return transmissionResult2;
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempDir);
        }
        return transmissionResult;
    }

    private void insertHistory(SyncSchemeParamDTO syncSchemeParamDTO, int status, String fileKey, String userId, Date startTime, String detail, Date endTime) {
        SyncHistoryDTO syncHistoryDTO = new SyncHistoryDTO();
        SyncSchemeParamDO syncSchemeParamDO = new SyncSchemeParamDO(syncSchemeParamDTO);
        syncHistoryDTO.setKey(syncSchemeParamDTO.getKey());
        syncHistoryDTO.setStatus(status);
        syncHistoryDTO.setType(0);
        syncHistoryDTO.setSchemeKey(syncSchemeParamDTO.getSchemeKey());
        syncHistoryDTO.setSyncSchemeParamDO(syncSchemeParamDO);
        syncHistoryDTO.setStartTime(startTime);
        syncHistoryDTO.setFileKey(fileKey);
        syncHistoryDTO.setUserId(userId);
        syncHistoryDTO.setDetail(detail);
        syncHistoryDTO.setEndTime(endTime);
        this.syncHistoryService.insert(syncHistoryDTO);
    }

    private void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
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
    public void offLineExport(SyncSchemeParamDTO syncSchemeParamDTO, HttpServletResponse response) throws Exception {
        String executeKey = syncSchemeParamDTO.getKey();
        if (!StringUtils.hasText(executeKey)) {
            executeKey = UUID.randomUUID().toString();
            syncSchemeParamDTO.setKey(executeKey);
        }
        TransmissionMonitor monitor = new TransmissionMonitor(executeKey, this.cacheObjectResourceRemote);
        String schemeName = Utils.getSchemeName(syncSchemeParamDTO);
        File file = null;
        try {
            file = this.fileHandleService.fileExport(monitor, syncSchemeParamDTO);
            String fileName = schemeName + "[" + syncSchemeParamDTO.getPeriodValue() + "]";
            FileHelper.exportFile(file, fileName, response);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataExportException(MultilingualLog.getExportFail() + e.getMessage(), e);
        }
        finally {
            if (file != null && file.exists()) {
                Utils.deleteAllFilesOfDirByPath(file.getParent());
            }
        }
    }

    private SubServer getParentServeNode() {
        SubServer currentSubServer;
        try {
            currentSubServer = this.subServerManager.getCurrectSubServer();
        }
        catch (SubSystemException e) {
            logger.error("\u4e0a\u7ea7\u670d\u52a1\u67e5\u8be2\u9519\u8bef", e);
            throw new RuntimeException(e);
        }
        return currentSubServer.getSupServer();
    }
}

