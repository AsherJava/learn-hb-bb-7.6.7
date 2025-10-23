/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.transmission.data.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.internal.file.FileHandleService;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.ImportParam;
import com.jiuqi.nr.transmission.data.intf.UserInfoParam;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="TRANSMISSION_DATA_JOB", groupTitle="\u591a\u7ea7\u90e8\u7f72\u6570\u636e\u88c5\u5165\u4efb\u52a1")
public class TransmissionDataJob
extends AbstractRealTimeJob {
    private static final Logger logger = LoggerFactory.getLogger(TransmissionDataJob.class);
    private UserService<User> userService;
    private SystemUserService systemUserService;
    private ISyncHistoryService syncHistoryService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(JobContext jobContext) throws JobExecutionException {
        FileHandleService fileHandleService = (FileHandleService)SpringBeanUtils.getBean(FileHandleService.class);
        this.userService = (UserService)SpringBeanUtils.getBean(UserService.class);
        this.systemUserService = (SystemUserService)SpringBeanUtils.getBean(SystemUserService.class);
        CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)SpringBeanUtils.getBean(CacheObjectResourceRemote.class);
        Map params = this.getParams();
        String fileKey = (String)params.get("fileKey");
        String processKey = (String)params.get("processKey");
        String userName = (String)params.get("userName");
        String syncUserId = (String)params.get("syncUserId");
        String language = (String)params.get("language");
        this.setContext(userName, language);
        String tempDir = ZipUtils.newTempDir();
        String path = tempDir + "/" + "data.temp";
        try {
            PathUtils.validatePathManipulation((String)path);
        }
        catch (Exception e) {
            throw new JobExecutionException(e.getMessage());
        }
        File file = new File(path);
        TransmissionMonitor monitor = new TransmissionMonitor(processKey, cacheObjectResourceRemote);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
            }
            catch (IOException e) {
                logger.info(e.getMessage(), e);
                monitor.error("\u540c\u6b65\u5931\u8d25", new Exception("\u88c5\u5165\u6570\u636e\u6587\u4ef6\u670d\u52a1\u5668\u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25"));
                LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0a\u7ea7\u540c\u6b65\u5b8c\u6210\u4fe1\u606f", (String)"\u5931\u8d25\u539f\u56e0\uff1a\u88c5\u5165\u6570\u636e\u6587\u4ef6\u670d\u52a1\u5668\u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25", (int)0);
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file);){
            Utils.fileDownLoad(fileKey, fos);
        }
        catch (Exception e) {
            monitor.error("\u540c\u6b65\u5931\u8d25", new Exception("\u88c5\u5165\u6570\u636e\u6587\u4ef6\u670d\u52a1\u5668\u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25"));
            LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0a\u7ea7\u540c\u6b65\u5b8c\u6210\u4fe1\u606f", (String)"\u5931\u8d25\u539f\u56e0\uff1a\u88c5\u5165\u6570\u636e\u6587\u4ef6\u670d\u52a1\u5668\u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25", (int)0);
            logger.error(e.getMessage(), e);
        }
        DataImportResult result = null;
        try (FileInputStream is = new FileInputStream(file);){
            ImportParam importParam = new ImportParam();
            UserInfoParam userInfoParam = new UserInfoParam(syncUserId, userName);
            importParam.setUserInfoParam(userInfoParam);
            importParam.setImportType(1);
            result = fileHandleService.fileImport(is, monitor, importParam);
            if (!result.isResult()) {
                throw new Exception(result.getLog());
            }
        }
        catch (Exception e) {
            monitor.error("\u540c\u6b65\u5931\u8d25", e);
            result = monitor.getProgressInfo().getResult();
            this.syncHistoryService = (ISyncHistoryService)SpringBeanUtils.getBean(ISyncHistoryService.class);
            SyncHistoryDTO syncHistoryDTO = new SyncHistoryDTO();
            Date endTime = new Date();
            syncHistoryDTO.setKey(processKey);
            syncHistoryDTO.setEndTime(endTime);
            syncHistoryDTO.setStatus(4);
            syncHistoryDTO.setDetail(e.getMessage());
            syncHistoryDTO.setResult(result);
            this.syncHistoryService.update(syncHistoryDTO);
            logger.error(e.getMessage(), e);
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempDir);
        }
    }

    private void setContext(String username, String language) {
        NpContextImpl npContext;
        try {
            npContext = this.buildContext(username, language);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        NpContextHolder.setContext((NpContext)npContext);
    }

    private NpContextImpl buildContext(String userName, String language) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        if (Locale.US.toLanguageTag().equals(language)) {
            npContext.setLocale(Locale.US);
        } else if (Locale.SIMPLIFIED_CHINESE.toLanguageTag().equals(language)) {
            npContext.setLocale(Locale.SIMPLIFIED_CHINESE);
        }
        return npContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }

    private NpContextUser buildUserContext(String userName) throws JQException {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUserByUserName(userName);
        if (user == null) {
            throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        userContext.setOrgCode(user.getOrgCode());
        return userContext;
    }

    private User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((CharSequence)userName)) {
            return null;
        }
        Optional user = this.userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.findByUsername(userName);
        return sysUser.orElse(null);
    }
}

