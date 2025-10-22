/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobFactoryManager
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.manager.JobStorageManager
 *  com.jiuqi.bi.core.jobs.model.IScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.jobmanager.api.vo.JobLogDetailVO
 *  com.jiuqi.nvwa.jobmanager.api.vo.PlanTaskGroupVO
 *  com.jiuqi.nvwa.jobmanager.api.vo.PlanTaskItemVO
 *  com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO
 *  com.jiuqi.nvwa.jobmanager.service.PlanTaskGroupService
 *  com.jiuqi.nvwa.jobmanager.service.PlanTaskItemService
 *  com.jiuqi.nvwa.jobmanager.web.PlanTaskLogController
 *  org.json.JSONObject
 */
package nr.midstore.core.internal.job.service;

import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.model.IScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nvwa.jobmanager.api.vo.JobLogDetailVO;
import com.jiuqi.nvwa.jobmanager.api.vo.PlanTaskGroupVO;
import com.jiuqi.nvwa.jobmanager.api.vo.PlanTaskItemVO;
import com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO;
import com.jiuqi.nvwa.jobmanager.service.PlanTaskGroupService;
import com.jiuqi.nvwa.jobmanager.service.PlanTaskItemService;
import com.jiuqi.nvwa.jobmanager.web.PlanTaskLogController;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstorePlanTaskDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.job.service.MidstoreWorkJobFactory;
import nr.midstore.core.job.service.IMidstorePlanRegService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstorePlanRegServiceImpl
implements IMidstorePlanRegService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePlanRegServiceImpl.class);
    private static final String PLANROOTGROUPKEY = "0dcf7d08-4309-420c-9f2c-000000000001";
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private MidstoreWorkJobFactory jobFactory;
    @Autowired
    private PlanTaskItemService planTaskItemService;
    @Autowired
    private PlanTaskGroupService planTaskGroupService;
    @Autowired
    private PlanTaskLogController panTaskLogService;

    @Override
    public void regPlanTaskJobFactory() {
    }

    @Override
    public MidstoreResultObject regPlanTaskByMidstoreScheme(String midstoreSchemeId) throws MidstoreException {
        MidstoreSchemeDTO midstoreScheme = this.getMidstoreScheme(midstoreSchemeId);
        MidstoreSchemeInfoDTO midstoreSchemeInfo = this.getMidstoreSchemeInfo(midstoreSchemeId);
        return this.regPlanTaskByMidstoreScheme(midstoreScheme, midstoreSchemeInfo);
    }

    @Override
    public MidstoreResultObject regPlanTaskByMidstoreScheme(MidstoreSchemeDTO midstoreScheme, MidstoreSchemeInfoDTO midstoreSchemeInfo) throws MidstoreException {
        JobStorageManager jobStoreManage;
        JobModel jobModel;
        JobManager jobManager;
        if (!midstoreSchemeInfo.isUsePlanTask()) {
            return new MidstoreResultObject(false, "\u672a\u542f\u7528\u8ba1\u5212\u4efb\u52a1");
        }
        boolean infoHasChange = false;
        String groupKey = this.queryAndCreateGroup("\u4e2d\u95f4\u5e93", PLANROOTGROUPKEY);
        if (midstoreSchemeInfo != null) {
            jobManager = JobManager.getInstance((String)"midstore_work_job");
            try {
                jobModel = null;
                String excutePlanKey = midstoreSchemeInfo.getExcutePlanKey();
                if (StringUtils.isNotEmpty((String)excutePlanKey) && this.existPlanTask(excutePlanKey)) {
                    jobModel = jobManager.getJob(excutePlanKey);
                } else {
                    excutePlanKey = UUID.randomUUID().toString();
                }
                if (jobModel == null) {
                    midstoreSchemeInfo.setExcutePlanKey(excutePlanKey);
                    jobModel = jobManager.createJob("" + midstoreScheme.getCode() + "-" + midstoreScheme.getTitle() + "-\u6267\u884c\u8ba1\u5212", excutePlanKey);
                    jobModel.setFolderGuid(groupKey);
                    jobModel.setExtendedConfig(this.buildExcutgeJobExtendedConfig(midstoreSchemeInfo, 0));
                    jobManager.addJobModel(jobModel);
                    MidstorePlanRegServiceImpl.scheduleExcuteJob(midstoreSchemeInfo, midstoreSchemeInfo.getExcutePlanKey(), jobManager);
                    infoHasChange = true;
                } else if (StringUtils.isEmpty((String)jobModel.getFolderGuid()) || !jobModel.getFolderGuid().equalsIgnoreCase(groupKey)) {
                    jobModel.setFolderGuid(groupKey);
                    jobStoreManage = new JobStorageManager();
                    jobStoreManage.updateJobBaseinfo(jobModel);
                }
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        if (midstoreSchemeInfo != null) {
            jobManager = JobManager.getInstance((String)"midstore_clean_Job");
            try {
                jobModel = null;
                String cleanPlanKey = midstoreSchemeInfo.getCleanPlanKey();
                if (StringUtils.isNotEmpty((String)cleanPlanKey) && this.existPlanTask(cleanPlanKey)) {
                    jobModel = jobManager.getJob(cleanPlanKey);
                } else {
                    cleanPlanKey = UUID.randomUUID().toString();
                }
                if (jobModel == null) {
                    jobModel = jobManager.createJob("" + midstoreScheme.getCode() + "-" + midstoreScheme.getTitle() + "-\u6e05\u7406\u8ba1\u5212", cleanPlanKey);
                    midstoreSchemeInfo.setCleanPlanKey(cleanPlanKey);
                    jobModel.setExtendedConfig(this.buildExcutgeJobExtendedConfig(midstoreSchemeInfo, 1));
                    jobModel.setFolderGuid(groupKey);
                    jobManager.addJobModel(jobModel);
                    MidstorePlanRegServiceImpl.scheduleExcuteJob(midstoreSchemeInfo, midstoreSchemeInfo.getCleanPlanKey(), jobManager);
                    infoHasChange = true;
                } else if (StringUtils.isEmpty((String)jobModel.getFolderGuid()) || !jobModel.getFolderGuid().equalsIgnoreCase(groupKey)) {
                    jobModel.setFolderGuid(groupKey);
                    jobStoreManage = new JobStorageManager();
                    jobStoreManage.updateJobBaseinfo(jobModel);
                }
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        if (infoHasChange) {
            this.schemeInfoSevice.update(midstoreSchemeInfo);
        }
        return new MidstoreResultObject(true, "\u521b\u5efa\u8ba1\u5212\u4efb\u52a1");
    }

    @Override
    public MidstoreResultObject deletePlanTaskByMidstoreScheme(String midstoreSchemeId) throws MidstoreException {
        JobManager jobManager;
        MidstoreSchemeInfoDTO midstoreSchemeInfo = this.getMidstoreSchemeInfo(midstoreSchemeId);
        if (midstoreSchemeInfo != null && StringUtils.isNotEmpty((String)midstoreSchemeInfo.getExcutePlanKey())) {
            jobManager = JobManager.getInstance((String)"midstore_work_job");
            try {
                if (this.existPlanTask(jobManager, midstoreSchemeInfo.getExcutePlanKey())) {
                    jobManager.deleteJob(midstoreSchemeInfo.getExcutePlanKey());
                }
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        if (midstoreSchemeInfo != null && StringUtils.isNotEmpty((String)midstoreSchemeInfo.getCleanPlanKey())) {
            jobManager = JobManager.getInstance((String)"midstore_clean_Job");
            try {
                if (this.existPlanTask(jobManager, midstoreSchemeInfo.getCleanPlanKey())) {
                    jobManager.deleteJob(midstoreSchemeInfo.getCleanPlanKey());
                }
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        return new MidstoreResultObject(true, "\u5220\u9664\u8ba1\u5212\u4efb\u52a1");
    }

    @Override
    public MidstoreResultObject stopPlanTaskByMidstoreScheme(String midstoreSchemeId) throws MidstoreException {
        JobModel jobModel;
        JobManager jobManager;
        MidstoreSchemeInfoDTO midstoreSchemeInfo = this.getMidstoreSchemeInfo(midstoreSchemeId);
        if (midstoreSchemeInfo != null && StringUtils.isNotEmpty((String)midstoreSchemeInfo.getExcutePlanKey())) {
            jobManager = JobManager.getInstance((String)"midstore_work_job");
            try {
                if (this.existPlanTask(jobManager, midstoreSchemeInfo.getExcutePlanKey()) && (jobModel = jobManager.getJob(midstoreSchemeInfo.getExcutePlanKey())) != null) {
                    jobModel.setEnable(false);
                    jobManager.jobEnable(midstoreSchemeInfo.getExcutePlanKey(), false);
                }
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        if (midstoreSchemeInfo != null && StringUtils.isNotEmpty((String)midstoreSchemeInfo.getCleanPlanKey())) {
            jobManager = JobManager.getInstance((String)"midstore_clean_Job");
            try {
                if (this.existPlanTask(jobManager, midstoreSchemeInfo.getCleanPlanKey()) && (jobModel = jobManager.getJob(midstoreSchemeInfo.getCleanPlanKey())) != null) {
                    jobManager.jobEnable(midstoreSchemeInfo.getCleanPlanKey(), false);
                }
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        return new MidstoreResultObject(true, "\u505c\u6b62\u8ba1\u5212\u4efb\u52a1");
    }

    private void addjob(MidstoreSchemeDTO midstoreScheme, MidstoreSchemeInfoDTO midstoreSchemeInfo) {
        JobFactoryManager.getInstance().regJobFactory((JobFactory)this.jobFactory);
        JobManager jobManager = JobManager.getInstance((String)"midstore_work_job");
        String jobTitle = midstoreScheme.getTitle() + "-\u6267\u884c\u8ba1\u5212";
        JobModel jobModel = jobManager.createJob(jobTitle, midstoreSchemeInfo.getExcutePlanKey());
        jobModel.setDesc(midstoreScheme.getDesc() + "\u6267\u884c\u8ba1\u5212");
        jobModel.setFolderGuid(PLANROOTGROUPKEY);
        jobModel.setExtendedConfig(this.buildExcutgeJobExtendedConfig(midstoreSchemeInfo, 0));
        try {
            jobManager.addJobModel(jobModel);
            MidstorePlanRegServiceImpl.scheduleExcuteJob(midstoreSchemeInfo, midstoreSchemeInfo.getExcutePlanKey(), jobManager);
        }
        catch (JobsException e) {
            logger.info(e.getMessage());
        }
    }

    private static void scheduleExcuteJob(MidstoreSchemeInfoDTO midstoreSchemeInfo, String planTaskKey, JobManager jobManager) throws JobsException {
        SimpleScheduleMethod simpleScheduleMethod = new SimpleScheduleMethod();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime currentDateTimeWithoutSeconds = currentDateTime.minusSeconds(currentDateTime.getSecond());
        LocalDateTime beginTime = currentDateTimeWithoutSeconds.plusMinutes(60L);
        long execTime = beginTime.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        simpleScheduleMethod.setExecuteTime(execTime);
        jobManager.updateJobScheduleConf(planTaskKey, execTime, 7226582400000L, (IScheduleMethod)simpleScheduleMethod);
    }

    private String buildExcutgeJobExtendedConfig(MidstoreSchemeInfoDTO midstoreSchemeInfo, int planType) {
        JSONObject object = new JSONObject();
        object.put("midstoreScheme", (Object)midstoreSchemeInfo.getSchemeKey());
        return object.toString();
    }

    private MidstoreSchemeDTO getMidstoreScheme(String midstoreSchemeId) {
        MidstoreSchemeDTO param = new MidstoreSchemeDTO();
        param.setKey(midstoreSchemeId);
        List<MidstoreSchemeDTO> list = this.midstoreSchemeSevice.list(param);
        MidstoreSchemeDTO midstoreScheme = null;
        if (list != null && list.size() > 0) {
            midstoreScheme = list.get(0);
        }
        return midstoreScheme;
    }

    private MidstoreSchemeInfoDTO getMidstoreSchemeInfo(String midstoreSchemeId) {
        MidstoreSchemeInfoDTO param = new MidstoreSchemeInfoDTO();
        param.setSchemeKey(midstoreSchemeId);
        List<MidstoreSchemeInfoDTO> list = this.schemeInfoSevice.list(param);
        MidstoreSchemeInfoDTO midstoreSchemeInfo = null;
        if (list != null && list.size() > 0) {
            midstoreSchemeInfo = list.get(0);
        }
        return midstoreSchemeInfo;
    }

    private MidstorePlanTaskDTO tranPlanTaskVOToDTO(PlanTaskItemVO planTaskVo) {
        MidstorePlanTaskDTO result = new MidstorePlanTaskDTO();
        result.setId(planTaskVo.getId());
        result.setPlantasktitle(planTaskVo.getPlantasktitle());
        result.setEditable(planTaskVo.isEditable());
        result.setStarttime(planTaskVo.getStarttime());
        result.setEndtime(planTaskVo.getEndtime());
        result.setAdvancedsetting(planTaskVo.getAdvancedsetting());
        result.setDescription(planTaskVo.getDescription());
        result.setPlantaskcircletype(planTaskVo.getPlantaskcircletype());
        result.setNextexecutetime(planTaskVo.getNextexecutetime());
        result.setPlantasktitle(planTaskVo.getPlantasktitle());
        result.setPlantasktype(planTaskVo.getPlantasktype());
        result.setModelName(planTaskVo.getModelName());
        result.setExecuteplan(planTaskVo.getExecuteplan());
        result.setUser(planTaskVo.getUser());
        result.setGroup(planTaskVo.getGroup());
        result.setGroupTitle(planTaskVo.getGroupTitle());
        result.setOrdinal("");
        return result;
    }

    private PlanTaskItemVO tranPlanTaskDTOToVO(MidstorePlanTaskDTO planTaskDto) {
        PlanTaskItemVO result = new PlanTaskItemVO();
        result.setId(planTaskDto.getId());
        result.setPlantasktitle(planTaskDto.getPlantasktitle());
        result.setEditable(planTaskDto.isEditable());
        result.setStarttime(planTaskDto.getStarttime());
        result.setEndtime(planTaskDto.getEndtime());
        result.setAdvancedsetting(planTaskDto.getAdvancedsetting());
        result.setDescription(planTaskDto.getDescription());
        result.setPlantaskcircletype(planTaskDto.getPlantaskcircletype());
        result.setNextexecutetime(planTaskDto.getNextexecutetime());
        result.setPlantasktitle(planTaskDto.getPlantasktitle());
        result.setPlantasktype(planTaskDto.getPlantasktype());
        result.setModelName(planTaskDto.getModelName());
        result.setExecuteplan(planTaskDto.getExecuteplan());
        result.setUser(planTaskDto.getUser());
        result.setGroup(planTaskDto.getGroup());
        result.setGroupTitle(planTaskDto.getGroupTitle());
        result.setOrdinal("");
        return result;
    }

    @Override
    public boolean existPlanTask(String planTaskKey) {
        JobManager jobManager = JobManager.getInstance((String)"midstore_work_job");
        return this.existPlanTask(jobManager, planTaskKey);
    }

    private boolean existPlanTask(JobManager jobManager, String planTaskKey) {
        try {
            JobModel jobModel = jobManager.getJob(planTaskKey);
            return jobModel != null;
        }
        catch (JobsException jobsException) {
            return false;
        }
    }

    @Override
    public MidstorePlanTaskDTO queryPlanTask(String planTaskKey) throws MidstoreException {
        MidstorePlanTaskDTO planTask = null;
        try {
            PlanTaskItemVO planTaskVo = null;
            planTaskVo = this.planTaskItemService.findVOById(planTaskKey);
            if (planTaskVo != null) {
                planTask = this.tranPlanTaskVOToDTO(planTaskVo);
            }
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
        return planTask;
    }

    @Override
    public void updatePlanTask(MidstorePlanTaskDTO planTask) throws MidstoreException {
        try {
            PlanTaskItemVO planTaskVo = this.tranPlanTaskDTOToVO(planTask);
            this.planTaskItemService.modify(planTaskVo);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }

    @Override
    public void insertPlanTask(MidstorePlanTaskDTO planTask) throws MidstoreException {
        try {
            PlanTaskItemVO planTaskVo = this.tranPlanTaskDTOToVO(planTask);
            this.planTaskItemService.insert(planTaskVo);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }

    @Override
    public String getMidstorePlanTaskGroup() {
        return this.queryAndCreateGroup("\u4e2d\u95f4\u5e93", PLANROOTGROUPKEY);
    }

    @Override
    public String getPlanTaskLogDetail(String planTaskKey) throws MidstoreException {
        try {
            JobLogDetailVO detail = this.panTaskLogService.queryPlanTaskLogDetail(planTaskKey);
            if (detail != null) {
                return detail.getLogDetail();
            }
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
        return null;
    }

    private String queryAndCreateGroup(String GroupTitle, String parentGroup) {
        PlanTaskGroupVO findGroupVo = null;
        PlanTaskGroupVO parentGroupVo = null;
        List allGroups = this.planTaskGroupService.buildGroupTree();
        for (PlanTaskGroupVO group : allGroups) {
            if (!group.getId().equalsIgnoreCase(parentGroup)) continue;
            parentGroupVo = group;
            List childGroups = group.getChildren();
            if (childGroups == null) continue;
            for (PlanTaskGroupVO child : childGroups) {
                if (!StringUtils.isNotEmpty((String)child.getTitle()) || !child.getTitle().equalsIgnoreCase(GroupTitle)) continue;
                findGroupVo = child;
            }
        }
        String resultGroupKey = null;
        if (findGroupVo == null) {
            PlanTaskGroupVO group;
            resultGroupKey = UUID.randomUUID().toString();
            group = new PlanTaskGroupEO();
            group.setId(resultGroupKey);
            group.setTitle(GroupTitle);
            group.setParent(parentGroup);
            this.planTaskGroupService.addGroup((PlanTaskGroupEO)group);
        } else {
            resultGroupKey = findGroupVo.getId();
        }
        return resultGroupKey;
    }
}

