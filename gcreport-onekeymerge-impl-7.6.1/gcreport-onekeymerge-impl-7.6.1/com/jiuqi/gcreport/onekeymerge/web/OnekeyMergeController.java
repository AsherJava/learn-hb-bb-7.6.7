/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.google.common.collect.Lists
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.onekeymerge.api.OnekeyMergeClient
 *  com.jiuqi.gcreport.onekeymerge.dto.AutoMergeDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskRecordDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO
 *  com.jiuqi.gcreport.onekeymerge.enums.CalPeriodWayEnum
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcFinishCalcResultVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcOnekeyMergeResultVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcPreParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.Message$ProgressResult
 *  com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  com.jiuqi.va.feign.util.LogUtil
 *  javax.servlet.http.HttpServletRequest
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.gcreport.onekeymerge.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.onekeymerge.api.OnekeyMergeClient;
import com.jiuqi.gcreport.onekeymerge.dto.AutoMergeDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskRecordDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO;
import com.jiuqi.gcreport.onekeymerge.enums.CalPeriodWayEnum;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.service.GcMergeTaskService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeResultService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeTaskService;
import com.jiuqi.gcreport.onekeymerge.service.impl.GcTaskResultServiceImpl;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcFinishCalcResultVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcOnekeyMergeResultVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcPreParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.sf.anno.Licence;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Primary
@RestController
public class OnekeyMergeController
implements OnekeyMergeClient {
    @Autowired
    private GcOnekeyMergeService gcOnekeyMergeService;
    @Autowired
    private GcOnekeyMergeTaskService taskService;
    @Autowired
    private GcTaskResultServiceImpl resultService;
    @Autowired
    private GcOnekeyMergeResultService onekeyMergeResultService;
    @Autowired
    private ProgressService<OnekeyProgressDataImpl, Message.ProgressResult> progressService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private GcMergeTaskService gcMergeTaskService;

    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<Object> doOnekeyMerge(@RequestBody GcActionParamsVO gcActionParamsVO, BindingResult bindingResult) {
        String taskCodeLog = OneKeyMergeUtils.buildTaskCodeLog(gcActionParamsVO.getTaskCodes());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(gcActionParamsVO.getTaskId());
        String logConent = String.format("\u4efb\u52a1%1$s-\u65f6\u671f%2$s-\u5408\u5e76\u5355\u4f4d%3$s-%4$s", taskDefine.getTitle(), gcActionParamsVO.getPeriodStr(), gcActionParamsVO.getOrgId(), taskCodeLog);
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4e2d\u5fc3", (String)logConent, (String)logConent);
        if (bindingResult.hasErrors()) {
            String error = "\u8bf7\u68c0\u67e5\u53c2\u6570,\u539f\u56e0:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return BusinessResponseEntity.error((String)"", (String)error, (String)"");
        }
        List<Object> orgs = Lists.newArrayList((Object[])new GcOrgCacheVO[]{OrgUtils.getCurrentUnit(gcActionParamsVO)});
        if (MergeTypeEnum.CUSTOM_LEVEL.equals((Object)gcActionParamsVO.getMergeType()) && !CollectionUtils.isEmpty((Collection)gcActionParamsVO.getOrgIds())) {
            orgs = OrgUtils.getOrgsByCodes(gcActionParamsVO);
        }
        this.checkOrg(orgs, gcActionParamsVO);
        OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(OneKeyMergeUtils.generateSN("process", gcActionParamsVO.getTaskLogId()));
        GcOrgTypeUtils.setContextEntityId((String)gcActionParamsVO.getOrgType());
        gcActionParamsVO.setOnekeyProgressData(onekeyProgressData);
        gcActionParamsVO.setNpContext(NpContextHolder.getContext());
        this.gcMergeTaskService.createTaskTree(orgs, gcActionParamsVO);
        this.gcMergeTaskService.mergeTask(orgs, gcActionParamsVO);
        return BusinessResponseEntity.ok();
    }

    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<Object> stopOnekeyMerge(@RequestBody GcActionParamsVO gcActionParamsVO) {
        this.gcOnekeyMergeService.stopMerge(gcActionParamsVO);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<Object> doDataPreProcess(@RequestBody GcPreParamsVO gcActionParamsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = "\u8bf7\u68c0\u67e5\u53c2\u6570,\u539f\u56e0:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return BusinessResponseEntity.error((String)"", (String)error, (String)"");
        }
        ReturnObject returnObject = this.taskService.doTask((GcActionParamsVO)gcActionParamsVO, TaskTypeEnum.DATAPROCESS);
        return BusinessResponseEntity.ok((Object)returnObject);
    }

    @Transactional(rollbackFor={Exception.class})
    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<ReturnObject> doReportPick(@RequestBody GcActionParamsVO gcActionParamsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = "\u8bf7\u68c0\u67e5\u53c2\u6570:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return BusinessResponseEntity.error((String)"", (String)error, (String)"");
        }
        ReturnObject returnObject = this.taskService.doTask(gcActionParamsVO, TaskTypeEnum.DATAPICK);
        return BusinessResponseEntity.ok((Object)returnObject);
    }

    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<ReturnObject> doRelationToMerge(GcActionParamsVO gcActionParamsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = "\u8bf7\u68c0\u67e5\u53c2\u6570:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return BusinessResponseEntity.error((String)"", (String)error, (String)"");
        }
        ReturnObject returnObject = this.taskService.doTask(gcActionParamsVO, TaskTypeEnum.RELATIONTOMERGE);
        return BusinessResponseEntity.ok((Object)returnObject);
    }

    @Transactional(rollbackFor={Exception.class})
    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<ReturnObject> doSplit(@RequestBody @Valid GcActionParamsVO gcActionParamsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = "\u8bf7\u68c0\u67e5\u53c2\u6570:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return BusinessResponseEntity.error((String)"", (String)error, (String)"");
        }
        ReturnObject returnObject = this.taskService.doTask(gcActionParamsVO, TaskTypeEnum.CONVERSION);
        return BusinessResponseEntity.ok((Object)returnObject);
    }

    @Transactional(rollbackFor={Exception.class})
    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<ReturnObject> doCalc(@RequestBody @Valid GcActionParamsVO gcActionParamsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = "\u8bf7\u68c0\u67e5\u53c2\u6570:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return BusinessResponseEntity.error((String)"", (String)error, (String)"");
        }
        ReturnObject returnObject = this.taskService.doTask(gcActionParamsVO, TaskTypeEnum.CALC);
        return BusinessResponseEntity.ok((Object)returnObject);
    }

    public BusinessResponseEntity<ReturnObject> getCurrentFinishTask(@RequestBody @Valid GcActionParamsVO gcActionParamsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String string = "\u8bf7\u68c0\u67e5\u53c2\u6570:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        }
        ReturnObject returnObject = this.gcOnekeyMergeService.checkFinishCalcState(gcActionParamsVO);
        return BusinessResponseEntity.ok((Object)returnObject);
    }

    public BusinessResponseEntity<GcFinishCalcResultVO> doCalcDone(@RequestBody @Valid GcActionParamsVO gcActionParamsVO, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            String error = "\u8bf7\u68c0\u67e5\u53c2\u6570:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            BusinessResponseEntity.error((String)error);
        }
        OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(OneKeyMergeUtils.generateSN(TaskTypeEnum.FINISHCALC.getCode(), gcActionParamsVO.getTaskLogId()));
        this.progressService.createProgressData((ProgressData)onekeyProgressData);
        gcActionParamsVO.setOnekeyProgressData(onekeyProgressData);
        this.taskService.doTask(gcActionParamsVO, TaskTypeEnum.FINISHCALC);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<GcFinishCalcResultVO> doCalcDoneAsync(@RequestBody @Valid GcActionParamsVO gcActionParamsVO, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            String error = "\u8bf7\u68c0\u67e5\u53c2\u6570:" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            BusinessResponseEntity.error((String)error);
        }
        NpContext context = NpContextHolder.getContext();
        gcActionParamsVO.setNpContext(context);
        OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(OneKeyMergeUtils.generateSN(TaskTypeEnum.FINISHCALC.getCode(), gcActionParamsVO.getTaskLogId()));
        this.progressService.createProgressData((ProgressData)onekeyProgressData);
        gcActionParamsVO.setOnekeyProgressData(onekeyProgressData);
        String ipAddr = LogUtil.getIpAddr((HttpServletRequest)request);
        gcActionParamsVO.setIpAddr(ipAddr);
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes((RequestAttributes)sra, (boolean)true);
        this.taskService.doTaskAsync(gcActionParamsVO, TaskTypeEnum.FINISHCALC);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<TaskLog> getTaskLog(@PathVariable(value="taskLogId") String taskLogId) {
        TaskLog taskLog = this.gcOnekeyMergeService.getTaskLog(taskLogId);
        return BusinessResponseEntity.ok((Object)taskLog);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<TaskLog> getTaskLogWithCode(@PathVariable(value="taskLogId") String taskLogId, @PathVariable(value="taskCode") String taskCode) {
        TaskLog taskLog = this.gcOnekeyMergeService.getTaskLogWithCode(taskLogId, taskCode);
        return BusinessResponseEntity.ok((Object)taskLog);
    }

    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<Object> getTaskResult(String taskLogId, String taskCode) {
        String resultData = this.resultService.getResultByTaskCodeAndGroupId(taskCode, taskLogId);
        return BusinessResponseEntity.ok((Object)resultData);
    }

    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<Object> getTaskProcess(@RequestParam(value="gcActionParamsVO") String gcActionParamsVO) {
        GcActionParamsVO paramsVO = (GcActionParamsVO)JsonUtils.readValue((String)gcActionParamsVO, (TypeReference)new TypeReference<GcActionParamsVO>(){});
        List<GcOnekeyMergeResultVO> ret = this.onekeyMergeResultService.getCurrentThreeResult(paramsVO);
        return BusinessResponseEntity.ok(ret);
    }

    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public BusinessResponseEntity<DebugZbInfoVO> debugZbReWrite(@Valid GcActionParamsVO gcActionParamsVO, String zbCode) {
        return BusinessResponseEntity.ok((Object)this.gcOnekeyMergeService.debugZbReWrite(gcActionParamsVO, zbCode));
    }

    @Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.onekeymerge")
    public List<Map<String, Object>> mergeItem() {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        ArrayList titles = Lists.newArrayList((Object[])new String[]{"\u6570\u636e\u63d0\u53d6", "\u6298\u7b97", "\u5408\u5e76\u8ba1\u7b97", "\u5b8c\u6210\u5408\u5e76"});
        ArrayList keyList = Lists.newArrayList((Object[])new String[]{"dataPick", "conversion", "showmergeRule", "finishCalcTask"});
        for (int i = 0; i < titles.size(); ++i) {
            HashMap data = new HashMap();
            data.put("title", titles.get(i));
            data.put("key", keyList.get(i));
            result.add(data);
        }
        return result;
    }

    public BusinessResponseEntity<MergeTaskProcessDTO> getMergeTaskProcess(@PathVariable(value="taskLogId") String taskLogId) {
        return BusinessResponseEntity.ok((Object)this.gcMergeTaskService.getProcess(taskLogId));
    }

    public BusinessResponseEntity<Object> stopMergeTask(String taskLogId) {
        this.gcMergeTaskService.stopMergeTask(taskLogId, TaskStateEnum.STOP.getCode());
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<MergeTaskResultLogDTO> getMergeTaskLogs(String taskLogId) {
        return BusinessResponseEntity.ok((Object)this.gcMergeTaskService.getMergeTaskLogs(taskLogId));
    }

    public BusinessResponseEntity<List<MergeTaskRecordDTO>> getTaskRecord(GcActionParamsVO gcActionParamsVO) {
        return BusinessResponseEntity.ok(this.gcMergeTaskService.getTaskRecord(gcActionParamsVO));
    }

    public BusinessResponseEntity<String> checkOrgInfo(GcActionParamsVO gcActionParamsVO) {
        return BusinessResponseEntity.ok((Object)this.gcMergeTaskService.checkOrgInfo(gcActionParamsVO));
    }

    public BusinessResponseEntity<String> getPeriodByType(AutoMergeDTO autoMergeDTO) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(autoMergeDTO.getTaskId());
        CalPeriodWayEnum periodWayEnum = CalPeriodWayEnum.getEnumByCode((String)autoMergeDTO.getPeriodType());
        PeriodWrapper currentPeriod = TaskPeriodUtils.getCurrentPeriod((int)taskDefine.getPeriodType().type());
        String periodStr = autoMergeDTO.getPeriodStr();
        switch (Objects.requireNonNull(periodWayEnum)) {
            case BEFORE: {
                DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
                PeriodWrapper periodWrapper = new PeriodWrapper(currentPeriod.toString());
                defaultPeriodAdapter.priorPeriod(periodWrapper);
                periodStr = periodWrapper.toString();
                break;
            }
            case CURRENT: {
                periodStr = currentPeriod.toString();
            }
        }
        return BusinessResponseEntity.ok((Object)periodStr);
    }

    private void checkOrg(List<GcOrgCacheVO> orgs, GcActionParamsVO param) {
        List<GcOrgCacheVO> filterOrgs = orgs.stream().filter(org -> GcOrgKindEnum.UNIONORG.equals((Object)org.getOrgKind())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filterOrgs)) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u5408\u5e76\u5355\u4f4d!");
        }
        GcActionParamsVO gcActionParamsVO = new GcActionParamsVO();
        BeanUtils.copyProperties(param, gcActionParamsVO);
        Map<String, UploadState> allOrgUploadStates = OrgUtils.getAllOrgUploadStates(gcActionParamsVO, filterOrgs, new HashSet<String>());
        boolean notCheckDiffOrg = Boolean.TRUE.equals(param.getDataSum()) && !Boolean.TRUE.equals(param.getRewriteDiff());
        filterOrgs.stream().forEach(org -> {
            UploadState uploadState = (UploadState)allOrgUploadStates.get(org.getCode());
            if (UploadState.UPLOADED.equals((Object)uploadState) || UploadState.CONFIRMED.equals((Object)uploadState) || UploadState.SUBMITED.equals((Object)uploadState)) {
                throw new BusinessRuntimeException(gcActionParamsVO.getOrgId() + "\u5df2\u4e0a\u62a5");
            }
            List children = org.getChildren();
            if (CollectionUtils.isEmpty((Collection)children)) {
                throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u5408\u5e76\u5355\u4f4d!");
            }
            if (!notCheckDiffOrg) {
                String diffUnitId = org.getDiffUnitId();
                if (!StringUtils.hasText(diffUnitId)) {
                    throw new BusinessRuntimeException("\u5408\u5e76\u5355\u4f4d" + org.getCode() + "\u6ca1\u6709\u7ed1\u5b9a\u5dee\u989d\u5355\u4f4d");
                }
                UploadState diffUploadState = (UploadState)allOrgUploadStates.get(diffUnitId);
                if (UploadState.UPLOADED.equals((Object)diffUploadState) || UploadState.CONFIRMED.equals((Object)diffUploadState) || UploadState.SUBMITED.equals((Object)diffUploadState)) {
                    throw new BusinessRuntimeException(diffUnitId + "\u5df2\u4e0a\u62a5");
                }
            }
        });
    }
}

