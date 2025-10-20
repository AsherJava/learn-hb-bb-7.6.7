/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.dto.FinishCalcAsyncTaskDTO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.onekeymerge.dto.FinishCalcAsyncTaskDTO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskExecutor;
import com.jiuqi.gcreport.onekeymerge.util.EfdcUtils;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyTaskPoolEnum;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="finishCalc")
public class MergeTaskFinishCaclExecutorImpl
implements MergeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MergeTaskFinishCaclExecutorImpl.class);
    private static final String TASKCODE = TaskTypeEnum.FINISHCALC.getCode();
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public String getTaskType() {
        return TASKCODE;
    }

    @Override
    public MergeTaskEO createTask(GcActionParamsVO param) {
        ReadWriteAccessDesc writeable;
        MergeTaskEO mergeTaskEO = OneKeyMergeUtils.buildMergeTask(param);
        GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(param);
        List children = currentUnit.getChildren();
        if (CollectionUtils.isEmpty((Collection)children)) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u5f53\u524d\u5355\u4f4d\u4e0d\u662f\u5408\u5e76\u5355\u4f4d\u3002");
            return mergeTaskEO;
        }
        ConsolidatedTaskVO taskOption = this.taskService.getTaskBySchemeId(param.getSchemeId(), param.getPeriodStr());
        Boolean enableFinishCalc = taskOption.getEnableFinishCalc();
        if (!enableFinishCalc.booleanValue()) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u4efb\u52a1\u9009\u9879\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u5b8c\u6210\u5408\u5e76\u3002");
            return mergeTaskEO;
        }
        boolean canReadFormScheme = this.authorityProvider.canReadFormScheme(param.getSchemeId());
        if (!canReadFormScheme) {
            throw new BusinessRuntimeException("\u65e0\u8be5\u62a5\u8868\u65b9\u6848\u7684\u8bbf\u95ee\u6743\u9650");
        }
        if (null == currentUnit.getOrgTypeId()) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e3a\u7a7a\u3002");
            return mergeTaskEO;
        }
        DimensionParamsVO dimensionParamsVO = OneKeyMergeUtils.convert2DimParamVO(param);
        if (param.getDataSum().booleanValue() && !(writeable = new UploadStateTool().writeable(dimensionParamsVO)).getAble().booleanValue()) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u5f53\u524d\u5355\u4f4d\u4e0d\u5141\u8bb8\u5199\u5165\u6570\u636e\u3002");
            return mergeTaskEO;
        }
        if (param.getRewriteDiff().booleanValue()) {
            String diffUnitId = currentUnit.getDiffUnitId();
            if (StringUtils.isEmpty((String)diffUnitId)) {
                mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
                mergeTaskEO.setTaskData("\u5408\u5e76\u5355\u4f4d:" + currentUnit.getTitle() + "\u6ca1\u6709\u627e\u5230\u5173\u8054\u7684\u5dee\u989d\u5355\u4f4d!");
                return mergeTaskEO;
            }
            GcOrgCacheVO diffUnitOrg = OrgUtils.getCurrentUnit(param.getOrgType(), param.getPeriodStr(), diffUnitId);
            if (!currentUnit.getOrgTypeId().equals(diffUnitOrg.getOrgTypeId())) {
                mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
                mergeTaskEO.setTaskData("\u5f53\u524d\u5355\u4f4d\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e0e\u5dee\u989d\u5355\u4f4d\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e0d\u4e00\u81f4\u3002");
                return mergeTaskEO;
            }
            String fetchScheme = EfdcUtils.getFetchScheme(param, diffUnitId);
            if (StringUtils.isEmpty((String)fetchScheme)) {
                mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
                mergeTaskEO.setTaskData("\u5dee\u989d\u5355\u4f4d:" + diffUnitOrg.getTitle() + "\u672a\u914d\u7f6e\u53d6\u6570\u65b9\u6848");
                return mergeTaskEO;
            }
            dimensionParamsVO.setOrgId(diffUnitId);
            ReadWriteAccessDesc writeable2 = new UploadStateTool().writeable(dimensionParamsVO);
            if (!writeable2.getAble().booleanValue()) {
                mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
                mergeTaskEO.setTaskData("\u5dee\u989d\u5355\u4f4d\u4e0d\u5141\u8bb8\u5199\u5165\u6570\u636e\u3002");
                return mergeTaskEO;
            }
            String failReason = this.failReasonByScheme(param, currentUnit, diffUnitOrg);
            if (failReason != null) {
                mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
                mergeTaskEO.setTaskData(failReason);
                return mergeTaskEO;
            }
        }
        return mergeTaskEO;
    }

    @Override
    public Object buildAsyncTaskParam(GcActionParamsVO param, String orgId) {
        FinishCalcAsyncTaskDTO finishCalcAsyncTaskDTO = new FinishCalcAsyncTaskDTO();
        BeanUtils.copyProperties(param, finishCalcAsyncTaskDTO);
        finishCalcAsyncTaskDTO.setOrgId(orgId);
        finishCalcAsyncTaskDTO.setUserName(NpContextHolder.getContext().getUserName());
        finishCalcAsyncTaskDTO.setLoginToken(ShiroUtil.getToken());
        finishCalcAsyncTaskDTO.setSelectAdjustCode(param.getSelectAdjustCode());
        return JsonUtils.writeValueAsString((Object)finishCalcAsyncTaskDTO);
    }

    @Override
    public String publishTask(GcActionParamsVO param, Object asyncTaskParam, OneKeyTaskPoolEnum taskPoolEnum) {
        return this.asyncTaskManager.publishTask(asyncTaskParam, taskPoolEnum.getTaskPool());
    }

    private String failReasonByScheme(GcActionParamsVO paramsVO, GcOrgCacheVO org, GcOrgCacheVO diffUnitOrg) {
        ConsolidatedTaskVO taskVO = this.taskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        if (null == taskVO) {
            return "\u5f53\u524d\u62a5\u8868\u65b9\u6848\u65e2\u4e0d\u662f\u91c7\u96c6\u586b\u62a5\u65b9\u6848\u53c8\u4e0d\u662f\u7ba1\u7406\u67b6\u6784\u65b9\u6848";
        }
        List allInputSchemeList = ConsolidatedSystemUtils.listAllInputSchemeByConTaskVO((ConsolidatedTaskVO)taskVO);
        boolean isCorporate = allInputSchemeList.contains(paramsVO.getSchemeId());
        if (isCorporate) {
            return null;
        }
        if (null != taskVO.getManageCalcUnitCodes() && taskVO.getManageCalcUnitCodes().contains(org.getId())) {
            return null;
        }
        String inputSchemeId = ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)taskVO.getTaskKey(), (String)paramsVO.getPeriodStr());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(inputSchemeId);
        GcOrgCacheVO corporateDiffUnitOrg = OrgUtils.getCurrentUnit(paramsVO.getOrgType(), paramsVO.getPeriodStr(), org.getDiffUnitId());
        String currDiffOrgTypeId = diffUnitOrg.getOrgTypeId();
        if (null == currDiffOrgTypeId) {
            return "\u5dee\u989d\u5355\u4f4d\u672a\u8bbe\u7f6e\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b";
        }
        if (null != corporateDiffUnitOrg && currDiffOrgTypeId.equals(corporateDiffUnitOrg.getOrgTypeId())) {
            return "\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0e\u91c7\u96c6\u586b\u62a5\u65b9\u6848" + formScheme.getTitle() + "\u7684\u5dee\u989d\u5355\u4f4d\u7684\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e00\u81f4,\u65e0\u9700\u91cd\u590d\u5b8c\u6210\u5408\u5e76";
        }
        return null;
    }
}

