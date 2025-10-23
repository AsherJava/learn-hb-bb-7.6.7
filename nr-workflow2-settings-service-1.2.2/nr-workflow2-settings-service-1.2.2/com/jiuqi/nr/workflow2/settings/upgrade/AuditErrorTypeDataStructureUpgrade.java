/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDO
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignDao
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.settings.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignDao;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class AuditErrorTypeDataStructureUpgrade
implements CustomClassExecutor {
    private final IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private final WorkflowSettingsService workflowSettingsService = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
    private final DefaultProcessDesignService defaultProcessDesignService = (DefaultProcessDesignService)SpringBeanUtils.getBean(DefaultProcessDesignService.class);
    private final DefaultProcessDesignDao defaultProcessDesignDao = (DefaultProcessDesignDao)SpringBeanUtils.getBean(DefaultProcessDesignDao.class);
    private final AuditTypeDefineService auditTypeDefineService = (AuditTypeDefineService)SpringBeanUtils.getBean(AuditTypeDefineService.class);

    public void execute(DataSource dataSource) throws Exception {
        List auditTypes;
        List taskKeys = this.runTimeViewController.listAllTask().stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        try {
            auditTypes = this.auditTypeDefineService.queryAllAuditType();
        }
        catch (Exception exception) {
            LoggerFactory.getLogger(this.getClass()).error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
        for (String taskKey : taskKeys) {
            JSONObject workflow;
            String workflowEngine;
            WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsService.queryWorkflowSettings(taskKey);
            if (workflowSettingsDO == null || !(workflowEngine = workflowSettingsDO.getWorkflowEngine()).equals("jiuqi.nr.default")) continue;
            boolean isContainReviewEvent = false;
            String workflowDefine = workflowSettingsDO.getWorkflowDefine();
            DefaultProcessDO defaultProcessDO = this.defaultProcessDesignDao.queryDefaultProcessConfig(workflowDefine);
            try {
                workflow = new JSONObject(defaultProcessDO.getConfig());
            }
            catch (JSONException e) {
                LoggerFactory.getLogger(this.getClass()).error("\u4efb\u52a1key:{} \u5bf9\u5e94\u7684\u62a5\u8868\u4efb\u52a1\u9ed8\u8ba4\u6d41\u7a0b2.0\u5f15\u64ce\u6d41\u7a0b\u8bbe\u7f6e\u6301\u4e45\u5316\u6570\u636e\u89e3\u6790\u5f02\u5e38", (Object)taskKey, (Object)e);
                continue;
            }
            if (workflow.isNull("workflowNodes")) {
                LoggerFactory.getLogger(this.getClass()).error("\u4efb\u52a1key:{} \u5bf9\u5e94\u7684\u62a5\u8868\u4efb\u52a1\u9ed8\u8ba4\u6d41\u7a0b2.0\u5f15\u64ce\u6d41\u7a0b\u8bbe\u7f6e\u6301\u4e45\u5316\u6570\u636e\u7ed3\u6784\u5f02\u5e38", (Object)taskKey);
                continue;
            }
            JSONObject workflowNodes = workflow.getJSONObject("workflowNodes");
            for (String workflowNode : workflowNodes.keySet()) {
                JSONObject nodeConfigJson = workflowNodes.getJSONObject(workflowNode);
                JSONObject nodeEventsJson = nodeConfigJson.getJSONObject("events");
                block8: for (String actionCode : nodeEventsJson.keySet()) {
                    JSONObject actionEventJson = nodeEventsJson.getJSONObject(actionCode);
                    for (String eventId : actionEventJson.keySet()) {
                        if (!eventId.equals("complete-review-event")) continue;
                        isContainReviewEvent = true;
                        JSONObject eventParam = actionEventJson.getJSONObject(eventId);
                        ArrayList newErrorHandle = new ArrayList();
                        JSONObject errorHandle = eventParam.getJSONObject("errorHandle");
                        String hint = errorHandle.getString("hint");
                        String warning = errorHandle.getString("warning");
                        String error = errorHandle.getString("error");
                        try {
                            for (AuditType auditType : auditTypes) {
                                String auditTypeCode = String.valueOf(auditType.getCode());
                                HashMap<String, String> errorTypeItem = new HashMap<String, String>();
                                errorTypeItem.put("code", auditTypeCode);
                                errorTypeItem.put("title", auditType.getTitle());
                                errorTypeItem.put("value", "2");
                                if (auditTypeCode.equals("1")) {
                                    errorTypeItem.put("value", hint);
                                } else if (auditTypeCode.equals("2")) {
                                    errorTypeItem.put("value", warning);
                                } else if (auditTypeCode.equals("4")) {
                                    errorTypeItem.put("value", error);
                                }
                                newErrorHandle.add(errorTypeItem);
                            }
                        }
                        catch (Exception exception) {
                            LoggerFactory.getLogger(this.getClass()).error(exception.getMessage(), exception);
                            throw new RuntimeException(exception);
                        }
                        eventParam.put("errorHandle", newErrorHandle);
                        continue block8;
                    }
                }
            }
            if (!isContainReviewEvent) continue;
            this.defaultProcessDesignDao.updateDefaultProcessConfig(workflowDefine, workflow.toString());
        }
    }
}

