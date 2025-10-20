/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.common.plantask.extend.service.SettingPageTemplateInterceptor
 *  com.jiuqi.common.plantask.extend.vo.SettingPageTemplateVO
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  com.jiuqi.gcreport.dimension.basedatasync.dao.BaseDataChangeInfoDao
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.runner;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.common.plantask.extend.service.SettingPageTemplateInterceptor;
import com.jiuqi.common.plantask.extend.vo.SettingPageTemplateVO;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.integration.execute.impl.basedatasync.enums.BaseDataSyncTypeEnum;
import com.jiuqi.dc.integration.execute.impl.basedatasync.mq.BaseDataSyncParam;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.gcreport.dimension.basedatasync.dao.BaseDataChangeInfoDao;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@PlanTaskRunner(id="F1C4783DB3A54C1C9C7EECA3BFFCD59F", name="BaseDataSyncRunner", title="\u3010\u5408\u5e76\u591a\u7ef4\u3011\u57fa\u7840\u6570\u636e\u540c\u6b65", settingPage="customTemplate|DcBaseDataSyncConfig", group="\u4e00\u672c\u8d26/\u57fa\u7840\u6570\u636e")
@Component(value="BaseDataSyncRunner")
public class DcBaseDataSyncRunner
extends Runner
implements SettingPageTemplateInterceptor {
    private final Logger logger = LogFactory.getLogger(((Object)((Object)this)).getClass());
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private BaseDataChangeInfoDao baseDataChangeInfoDao;

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public boolean excute(String runnerParameter) {
        try {
            String logId;
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            if (StringUtils.isEmpty((String)runnerParameter)) {
                BaseDataSyncParam baseDataSyncParam = new BaseDataSyncParam();
                baseDataSyncParam.setSyncType(BaseDataSyncTypeEnum.ADD.getCode());
                logId = (String)taskHandlerClient.startTask("BaseDataSync", JsonUtils.writeValueAsString((Object)baseDataSyncParam)).getData();
            } else {
                logId = (String)taskHandlerClient.startTask("BaseDataSync", runnerParameter).getData();
            }
            this.appendLog(String.format("\u3010\u57fa\u7840\u6570\u636e\u3011\u57fa\u7840\u6570\u636e\u540c\u6b65taskId = %1$s\n", logId));
            taskHandlerClient.waitTaskFinished(logId);
            List unSyncBaseCodeList = this.baseDataChangeInfoDao.getUnSyncBaseCodeList();
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(11);
            if (CollectionUtils.isEmpty((Collection)unSyncBaseCodeList) && hour >= 0 && hour <= 6) {
                this.baseDataChangeInfoDao.truncateTable();
            }
            this.taskLogService.updateTaskResult(logId, "\u57fa\u7840\u6570\u636e\u540c\u6b65\u5b8c\u6210");
            return Boolean.TRUE;
        }
        catch (Exception e) {
            this.logger.error("\u57fa\u7840\u6570\u636e\u540c\u6b65\u6267\u884c\u5931\u8d25\u3002\n", (Throwable)e);
            this.appendLog("\u57fa\u7840\u6570\u636e\u540c\u6b65\u6267\u884c\u5931\u8d25\u3002\n" + LogUtil.getExceptionStackStr((Throwable)e));
            return Boolean.FALSE;
        }
    }

    public String getTemplateCode() {
        return "DcBaseDataSyncConfig";
    }

    public SettingPageTemplateVO intercept(SettingPageTemplateVO oldSettingPageTemplateVO) {
        List rowParams = oldSettingPageTemplateVO.getRowParams();
        rowParams.forEach(o -> o.getColParams().forEach(e -> {
            if (e.getName().equals("baseDataCodeList")) {
                List dimensionVOS = this.dimensionService.loadAllDimensions();
                Set baseCodeSet = dimensionVOS.stream().map(DimensionVO::getReferField).filter(referField -> !StringUtils.isEmpty((String)referField)).collect(Collectors.toSet());
                baseCodeSet.addAll(CollectionUtils.newArrayList((Object[])new String[]{"MD_ACCTSUBJECT", "MD_CURRENCY", "MD_CFITEM", "MD_GCORGTYPE", "MD_AUDITTRAIL", "MD_AGING"}));
                e.setSource(String.join((CharSequence)",", baseCodeSet));
            }
            if (e.getName().equals("orgTypeList")) {
                PageVO result = this.orgCategoryClient.list(new OrgCategoryDO());
                StringJoiner joiner = new StringJoiner(",");
                if (!CollectionUtils.isEmpty((Collection)result.getRows())) {
                    result.getRows().forEach(orgType -> joiner.add(orgType.getTitle() + ":" + orgType.getName()));
                }
                e.setSource(joiner.toString());
            }
        }));
        return oldSettingPageTemplateVO;
    }

    public int getOrder() {
        return 0;
    }
}

