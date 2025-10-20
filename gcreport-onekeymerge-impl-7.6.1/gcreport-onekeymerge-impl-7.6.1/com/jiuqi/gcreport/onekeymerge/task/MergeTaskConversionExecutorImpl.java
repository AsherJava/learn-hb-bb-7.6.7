/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.conversion.common.GcConversionContextEnv
 *  com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.conversion.common.GcConversionContextEnv;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskExecutor;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyTaskPoolEnum;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="conversion")
public class MergeTaskConversionExecutorImpl
implements MergeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MergeTaskConversionExecutorImpl.class);
    private static final String TASKCODE = TaskTypeEnum.CONVERSION.getCode();
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;
    @Autowired
    private ConversionSystemTaskDao taskSchemeDao;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    @Override
    public String getTaskType() {
        return TASKCODE;
    }

    @Override
    public MergeTaskEO createTask(GcActionParamsVO param) {
        MergeTaskEO mergeTaskEO = OneKeyMergeUtils.buildMergeTask(param);
        String currencyId = StringUtils.toViewString((Object)param.getCurrency());
        Assert.isNotNull((Object)currencyId, (String)"\u8bf7\u9009\u62e9\u76ee\u6807\u5e01\u79cd\u3002", (Object[])new Object[0]);
        GcOrgCacheVO currentOrg = OrgUtils.getOrgByCode(param.getPeriodStr(), param.getOrgType(), param.getOrgId());
        String currencyid = StringUtils.toViewString((Object)currentOrg.getTypeFieldValue("CURRENCYID"));
        if (null == currencyid) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u672a\u914d\u7f6e\u5e01\u79cd\u3002");
            return mergeTaskEO;
        }
        if (param.getCurrency().equals(currencyid)) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u672c\u4f4d\u5e01\u4e0e\u76ee\u6807\u5e01\u79cd\u4e00\u81f4\u3002");
            return mergeTaskEO;
        }
        ConversionSystemTaskEO taskSchemeEO = this.taskSchemeDao.queryByTaskAndScheme(param.getTaskId(), param.getSchemeId());
        if (Objects.isNull(taskSchemeEO)) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u672a\u914d\u7f6e\u6298\u7b97\u65b9\u6848\u3002");
            return mergeTaskEO;
        }
        return mergeTaskEO;
    }

    @Override
    public Object buildAsyncTaskParam(GcActionParamsVO param, String orgId) {
        GcOrgCacheVO currentOrg = OrgUtils.getOrgByCode(param.getPeriodStr(), param.getOrgType(), orgId);
        List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(param.getSchemeId());
        List formList = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        GcConversionContextEnv env = this.onekeyMergeService.buildConversionEnv(param, currentOrg);
        env.setFormIds(formList);
        return env;
    }

    @Override
    public String publishTask(GcActionParamsVO param, Object asyncTaskParam, OneKeyTaskPoolEnum taskPoolEnum) {
        return this.asyncTaskManager.publishTask(asyncTaskParam, taskPoolEnum.getTaskPool());
    }
}

