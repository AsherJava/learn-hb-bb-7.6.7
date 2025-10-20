/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.common.systemparam.enums.EntInitMsgType
 *  com.jiuqi.common.systemparam.executor.EntOrgDataInitExecutor
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.common.task.vo.TaskConditionBoxVO
 *  com.jiuqi.gcreport.common.task.web.TaskConditionBoxController
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishService
 *  com.jiuqi.nr.designer.web.service.ReportTaskService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.reportparam.service.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.common.systemparam.enums.EntInitMsgType;
import com.jiuqi.common.systemparam.executor.EntOrgDataInitExecutor;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.task.vo.TaskConditionBoxVO;
import com.jiuqi.gcreport.common.task.web.TaskConditionBoxController;
import com.jiuqi.gcreport.reportparam.dao.GcReportParamInitDao;
import com.jiuqi.gcreport.reportparam.dto.GcReportParamInfoDTO;
import com.jiuqi.gcreport.reportparam.enums.GcReportParamType;
import com.jiuqi.gcreport.reportparam.service.GcReportParamInitExecuteService;
import com.jiuqi.gcreport.reportparam.service.GcReportParamInitLockService;
import com.jiuqi.gcreport.reportparam.service.GcReportParamInitService;
import com.jiuqi.gcreport.reportparam.service.impl.GcReportParamInfoUtils;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamInitExecuteVO;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamInitVO;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamProgressVO;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishService;
import com.jiuqi.nr.designer.web.service.ReportTaskService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcReportParamInitServiceImpl
implements GcReportParamInitService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GcReportParamInitExecuteService gcReportParamInitExecuteService;
    @Autowired
    private GcReportParamInitDao gcReportParamInitDao;
    @Autowired
    private GcReportParamInfoUtils gcReportParamInfoUtils;
    @Autowired
    private GcReportParamInitLockService gcReportParamInitLockService;
    @Autowired
    private NRDesignTimeController designTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private TaskPlanPublishService taskPlanPublishService;
    @Autowired
    private ReportTaskService reportTaskService;
    @Autowired
    private GcReportParamInitDao gcReportParamDao;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private ProgressService<ProgressDataImpl<List<GcReportParamProgressVO>>, List<GcReportParamProgressVO>> progressService;

    @Override
    public List<GcReportParamInitVO> listReportParamPackage() {
        List gcReportParamInitEOS = this.gcReportParamInitDao.loadAll();
        Set initParamPackageNameSet = gcReportParamInitEOS.stream().filter(item -> item.getInitFlag() != null && item.getInitFlag() == 1).map(item -> item.getName()).collect(Collectors.toSet());
        List<GcReportParamInitVO> paramInitList = this.gcReportParamInfoUtils.getParamInitVOList();
        List<GcReportParamInfoDTO> gcReportParamInfoList = this.gcReportParamInfoUtils.getGcReportParamInfoList();
        Map<String, String> relatedMergeSystemMap = gcReportParamInfoList.stream().filter(dto -> !StringUtils.isEmpty((String)dto.getRelatedMergeSystem())).collect(Collectors.toMap(GcReportParamInfoDTO::getParamName, GcReportParamInfoDTO::getRelatedMergeSystem));
        for (GcReportParamInitVO vo : paramInitList) {
            vo.setInitFlag(initParamPackageNameSet.contains(vo.getName()));
            vo.setRelatedMergeSystem(relatedMergeSystemMap.get(vo.getName()));
        }
        return paramInitList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional
    @Async
    public void startInit(GcReportParamInitExecuteVO executeVO) {
        ProgressDataImpl progressData = new ProgressDataImpl(executeVO.getSn(), new CopyOnWriteArrayList(), "GcReportParamInit");
        this.progressService.createProgressData((ProgressData)progressData);
        if (!this.gcReportParamInitLockService.lock()) {
            ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u5176\u4ed6\u7528\u6237\u5df2\u7ecf\u5728\u6267\u884c\u53c2\u6570\u521d\u59cb\u5316\uff0c\u8bf7\u7b49\u5f85\u5176\u5b83\u7528\u6237\u6267\u884c\u5b8c\u6210\u540e\u5728\u6267\u884c\uff01", EntInitMsgType.ERROR));
            progressData.setSuccessFlag(false);
            progressData.setProgressValueAndRefresh(1.0);
            return;
        }
        try {
            List<String> reportParamList = executeVO.getReportParam();
            List gcReportParamInitEOS = this.gcReportParamInitDao.loadAll();
            Set initParamPackageNameSet = gcReportParamInitEOS.stream().filter(item -> item.getInitFlag() != null && item.getInitFlag() == 1).map(item -> item.getName()).collect(Collectors.toSet());
            reportParamList = reportParamList.stream().filter(item -> !initParamPackageNameSet.contains(item)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(reportParamList)) {
                ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u9009\u62e9\u7684\u521d\u59cb\u5316\u5305\u5df2\u7ecf\u88ab\u5176\u5b83\u8bf7\u6c42\u521d\u59cb\u5316\uff0c\u6267\u884c\u5b8c\u6210\uff01", EntInitMsgType.INFO));
                progressData.setSuccessFlag(true);
                progressData.setProgressValueAndRefresh(1.0);
                return;
            }
            boolean allSuccessFlag = this.importReportParamPackage((ProgressDataImpl<List<GcReportParamProgressVO>>)progressData, reportParamList);
            ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u5f00\u59cb\u8fc1\u79fb\u4efb\u52a1\u5206\u7ec4\u5e76\u53d1\u5e03\u4efb\u52a1", EntInitMsgType.INFO));
            progressData.setProgressValueAndRefresh(0.9);
            ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u5bfc\u5165\u5b8c\u6210", EntInitMsgType.INFO));
            progressData.setSuccessFlag(allSuccessFlag);
            progressData.setProgressValueAndRefresh(1.0);
        }
        finally {
            this.gcReportParamInitLockService.unLock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean importReportParamPackage(ProgressDataImpl<List<GcReportParamProgressVO>> progressData, List<String> reportParamList) {
        Double progress = 0.1;
        progressData.setProgressValueAndRefresh(progress.doubleValue());
        double singleParamProgress = 0.8 / (double)reportParamList.size();
        boolean allSuccessFlag = true;
        Map<String, List<GcReportParamInfoDTO>> fileNameToGcReportParamInfoMap = this.getFileNameToGcReportParamInfoMap(reportParamList);
        Map<String, List<GcReportParamInfoDTO>> paramNameToGcReportParamInfoMap = this.getPramaNameToGcReportParamInfoMap(reportParamList);
        for (String paramName : reportParamList) {
            try {
                AtomicBoolean currentPackageSuccess = new AtomicBoolean(true);
                ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u5f00\u59cb\u5bfc\u5165" + paramName + "\u53c2\u6570\u5305", EntInitMsgType.INFO));
                List<GcReportParamInfoDTO> gcReportParamInfoDTOS = paramNameToGcReportParamInfoMap.get(paramName);
                Map<String, List<String>> importOrgMap = this.getImportOrgList(gcReportParamInfoDTOS);
                List<String> importTypeFileList = importOrgMap.get("importOrgType");
                ArrayList<String> paramInitList = new ArrayList<String>(importTypeFileList);
                paramInitList.addAll(this.listParamFileNameByParamName(paramName));
                double singleFileProgress = singleParamProgress / (double)paramInitList.size();
                Double currentParamStartProgress = progress;
                for (int i = 0; i < paramInitList.size(); ++i) {
                    String fileName = (String)paramInitList.get(i);
                    this.gcReportParamInitExecuteService.fileImport(progressData, fileNameToGcReportParamInfoMap, currentPackageSuccess, singleFileProgress, currentParamStartProgress, fileName);
                    if (i < importTypeFileList.size()) continue;
                    this.afterParamImport(fileName, fileNameToGcReportParamInfoMap, progressData);
                    currentParamStartProgress = currentParamStartProgress + singleFileProgress;
                }
                this.importOrgData(importOrgMap.get("importOrgData"), progressData);
                ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u53c2\u6570\u5305" + paramName + "\u5bfc\u5165\u5b8c\u6210", EntInitMsgType.INFO));
                this.gcReportParamInitExecuteService.saveOrUpdate(paramName, currentPackageSuccess.get());
            }
            catch (Exception e) {
                this.logger.error("\u53c2\u6570\u5305" + paramName + "\u5bfc\u5165\u5f02\u5e38\uff1a" + e.getMessage(), e);
                ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u53c2\u6570\u5305" + paramName + "\u5bfc\u5165\u5f02\u5e38\uff1a" + e.getMessage(), EntInitMsgType.ERROR));
                allSuccessFlag = false;
            }
            finally {
                progress = progress + singleParamProgress;
            }
        }
        return allSuccessFlag;
    }

    public void importOrgData(List<String> orgTypeList, ProgressDataImpl<List<GcReportParamProgressVO>> progressData) {
        if (CollectionUtils.isEmpty(orgTypeList)) {
            return;
        }
        String result = String.join((CharSequence)",", orgTypeList);
        try {
            EntOrgDataInitExecutor orgDataInitExecutor = new EntOrgDataInitExecutor();
            orgDataInitExecutor.execute(null, orgTypeList);
            ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u7ec4\u7ec7\u673a\u6784\uff1a" + result + " \u521d\u59cb\u5316\u6570\u636e\u5bfc\u5165\u5b8c\u6210", EntInitMsgType.INFO));
        }
        catch (Exception e) {
            ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u7ec4\u7ec7\u673a\u6784\u521d\u59cb\u5316\u6570\u636e\u5bfc\u5165\u5931\u8d25", EntInitMsgType.ERROR));
        }
    }

    public Map<String, List<String>> getImportOrgList(List<GcReportParamInfoDTO> gcReportParamInfoDTOS) {
        List orgTypeCodeNameList = gcReportParamInfoDTOS.stream().map(GcReportParamInfoDTO::getRelatedOrgTypes).filter(relatedOrgTypes -> !StringUtils.isEmpty((String)relatedOrgTypes)).flatMap(relatedOrgTypes -> Arrays.stream(relatedOrgTypes.split(","))).distinct().collect(Collectors.toList());
        HashMap<String, List<String>> result = new HashMap<String, List<String>>(2);
        List<String> existOrgTypes = this.gcReportParamDao.queryAllOrgTypes();
        ArrayList<String> importTypeFileList = new ArrayList<String>();
        ArrayList<String> importDataList = new ArrayList<String>();
        for (String orgType : orgTypeCodeNameList) {
            String[] parts = orgType.split("-");
            String orgTypeCode = parts[0];
            boolean exists = existOrgTypes.contains(orgTypeCode);
            if (exists) {
                if (this.gcReportParamDao.queryOrgCount(orgTypeCode) != 0) continue;
                importDataList.add(orgTypeCode);
                continue;
            }
            importTypeFileList.add(orgType + ".nvdata");
            importDataList.add(orgTypeCode);
        }
        result.put("importOrgType", importTypeFileList);
        result.put("importOrgData", importDataList);
        return result;
    }

    private Map<String, List<GcReportParamInfoDTO>> getPramaNameToGcReportParamInfoMap(List<String> reportParam) {
        return this.gcReportParamInfoUtils.getGcReportParamInfoList().stream().filter(item -> reportParam.contains(item.getParamName())).collect(Collectors.groupingBy(GcReportParamInfoDTO::getParamName));
    }

    private Map<String, List<GcReportParamInfoDTO>> getFileNameToGcReportParamInfoMap(List<String> reportParam) {
        return this.gcReportParamInfoUtils.getGcReportParamInfoList().stream().filter(item -> reportParam.contains(item.getParamName())).collect(Collectors.groupingBy(GcReportParamInfoDTO::getFileName));
    }

    private List<String> listParamFileNameByParamName(String paramName) {
        List<GcReportParamInfoDTO> gcReportParamInfoDTOS = this.gcReportParamInfoUtils.getParamNameToInfoMap().get(paramName);
        ArrayList<String> paramFileNameList = new ArrayList<String>();
        for (GcReportParamInfoDTO gcReportParamInfoDTO : gcReportParamInfoDTOS) {
            if (paramFileNameList.contains(gcReportParamInfoDTO.getFileName())) continue;
            paramFileNameList.add(gcReportParamInfoDTO.getFileName());
        }
        return paramFileNameList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void afterParamImport(String fileName, Map<String, List<GcReportParamInfoDTO>> fileNameToGcReportParamInfoMap, ProgressDataImpl<List<GcReportParamProgressVO>> progressData) {
        List<GcReportParamInfoDTO> gcReportParamInfoDTOS = fileNameToGcReportParamInfoMap.get(fileName);
        Set taskNameSet = gcReportParamInfoDTOS.stream().map(GcReportParamInfoDTO::getTaskName).collect(Collectors.toSet());
        String paramType = gcReportParamInfoDTOS.get(0).getParamType();
        Map<String, String> groupTitleToKeyMap = this.designTimeController.getAllTaskGroup().stream().collect(Collectors.toMap(item -> item.getTitle(), item -> item.getKey()));
        String taskGroupKey = groupTitleToKeyMap.get(GcReportParamType.getByName(paramType).getTaskGroupName());
        List allTaskDefines = this.designTimeController.getAllTaskDefines().stream().filter(item -> taskNameSet.contains(item.getTitle())).collect(Collectors.toList());
        try {
            for (DesignTaskDefine taskDefine : allTaskDefines) {
                ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u5f00\u59cb\u8fc1\u79fb\u4efb\u52a1\u9ed8\u8ba4\u5206\u7ec4" + taskDefine.getTitle() + "\u5e76\u53d1\u5e03\u4efb\u52a1\uff1a", EntInitMsgType.INFO));
                this.gcReportParamInitExecuteService.movAndPublishTask(progressData, taskGroupKey, taskDefine);
            }
        }
        catch (Exception e) {
            this.logger.error("\u53d1\u5e03\u4efb\u52a1\u5f02\u5e38\uff1a" + e.getMessage(), e);
            ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u53d1\u5e03\u4efb\u52a1\u5f02\u5e38,\u8bf7\u624b\u52a8\u53d1\u5e03\u4efb\u52a1\uff1a" + e.getMessage(), EntInitMsgType.ERROR));
            progressData.setProgressValueAndRefresh(1.0);
            return;
        }
        Map<String, String> taskTitleToKeyMap = allTaskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getTitle, IBaseMetaItem::getKey, (v1, v2) -> v1));
        NpContext context = NpContextHolder.getContext();
        try {
            if (context == null || StringUtils.isEmpty((String)context.getIdentityId())) {
                GcReportParamInitServiceImpl.initUserInfo();
            }
            this.gcReportParamInitExecuteService.importTaskData(progressData, gcReportParamInfoDTOS, taskTitleToKeyMap);
            for (DesignTaskDefine taskDefine : allTaskDefines) {
                this.publishFetchScheme(taskDefine, progressData);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5bfc\u5165\u6570\u636e\u6587\u4ef6\u5f02\u5e38\uff1a" + e.getMessage(), e);
            ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u5bfc\u5165\u6570\u636e\u6587\u4ef6\u5f02\u5e38\uff1a" + e.getMessage(), EntInitMsgType.ERROR));
            progressData.setProgressValueAndRefresh(1.0);
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
    }

    public void publishFetchScheme(DesignTaskDefine taskDefine, ProgressDataImpl<List<GcReportParamProgressVO>> progressData) throws Exception {
        ((List)progressData.getResult()).add(new GcReportParamProgressVO(String.format("\u5f00\u59cb\u53d1\u5e03\u4efb\u52a1\u3010%s\u3011\u7684\u53d6\u6570\u65b9\u6848", taskDefine.getTitle()), EntInitMsgType.INFO));
        BusinessResponseEntity schemes = ((TaskConditionBoxController)SpringContextUtils.getBean(TaskConditionBoxController.class)).getSchemes(taskDefine.getKey());
        List formSchemeList = ((TaskConditionBoxVO)schemes.getData()).getSchemeList();
        if (CollectionUtils.isEmpty((Collection)formSchemeList)) {
            ((List)progressData.getResult()).add(new GcReportParamProgressVO(String.format("\u4efb\u52a1\u3010{%s\u3011\u5173\u8054\u7684\u62a5\u8868\u65b9\u6848\u4e3a\u7a7a\uff0c\u672a\u8fdb\u884c\u53d1\u5e03", taskDefine.getTitle()), EntInitMsgType.ERROR));
            return;
        }
        for (Scheme scheme : formSchemeList) {
            String formSchemeId = scheme.getScheme().getValue().toString();
            List fetchSchemeList = this.fetchSchemeService.listFetchScheme(formSchemeId);
            if (CollectionUtils.isEmpty((Collection)fetchSchemeList)) continue;
            FetchSchemeVO fetchScheme = (FetchSchemeVO)fetchSchemeList.get(0);
            FetchSettingCond fetchSettingCond = new FetchSettingCond();
            fetchSettingCond.setFetchSchemeId(fetchScheme.getId());
            fetchSettingCond.setFormSchemeId(fetchScheme.getFormSchemeId());
            this.fetchSettingService.fetchSettingPublishByFetchSchemeId(fetchSettingCond);
            ((List)progressData.getResult()).add(new GcReportParamProgressVO(String.format("\u4efb\u52a1\u3010%s\u3011\u5173\u8054\u7684\u53d6\u6570\u65b9\u6848\uff1a{}\u53d1\u5e03\u6210\u529f", taskDefine.getTitle()), EntInitMsgType.INFO));
            return;
        }
        ((List)progressData.getResult()).add(new GcReportParamProgressVO(String.format("\u4efb\u52a1\u3010{%s\u3011\u5173\u8054\u7684\u53d6\u6570\u65b9\u6848\u4e3a\u7a7a\uff0c\u672a\u8fdb\u884c\u53d1\u5e03", taskDefine.getTitle()), EntInitMsgType.ERROR));
    }

    private static void initUserInfo() throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        NpContextUser contextUser = GcReportParamInitServiceImpl.buildUserContext();
        npContext.setUser((ContextUser)contextUser);
        npContext.setIdentity((ContextIdentity)GcReportParamInitServiceImpl.buildIdentityContext(contextUser));
        String tenantId = "__default_tenant__";
        npContext.setTenant(tenantId);
        NpContextHolder.setContext((NpContext)npContext);
    }

    private static NpContextUser buildUserContext() {
        NpContextUser userContext = new NpContextUser();
        SystemUserService sysUserService = (SystemUserService)SpringContextUtils.getBean(SystemUserService.class);
        SystemUser user = (SystemUser)sysUserService.getByUsername("admin");
        if (user == null) {
            user = (SystemUser)sysUserService.getUsers().get(0);
        }
        userContext.setId("SYSTEM.ROOT");
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private static NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getName());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }
}

