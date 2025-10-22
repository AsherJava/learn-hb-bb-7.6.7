/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.controller;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckConfigHelper;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.TaskShortInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.controller.IMultCheckConfigController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultCheckConfigController
implements IMultCheckConfigController {
    private static final Logger logger = LoggerFactory.getLogger(MultCheckConfigController.class);
    @Autowired
    ITaskExtConfigController taskExtConfigController;
    @Autowired
    MultCheckConfigHelper multCheckConfigHelper;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public List<TaskShortInfo> getRelationTaskAndCurrentTask(String taskkey, String formSchemeKey) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskkey);
        List taskLinkDefines = this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey);
        ArrayList<TaskShortInfo> taskShortInfos = new ArrayList<TaskShortInfo>();
        HashMap<String, String> TaskShortInfoMap = new HashMap<String, String>();
        TaskShortInfo tsinfo = new TaskShortInfo();
        tsinfo.setTaskKey(taskDefine.getKey());
        tsinfo.setTaskTitle(taskDefine.getTitle());
        taskShortInfos.add(tsinfo);
        for (TaskLinkDefine taskLink : taskLinkDefines) {
            TaskShortInfo taskShortInfo = new TaskShortInfo();
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(taskLink.getRelatedFormSchemeKey());
            if (formSchemeDefine == null || formSchemeDefine.getTaskKey().equals(taskkey) || StringUtils.isEmpty((String)taskLink.getDescription()) || StringUtils.isEmpty((String)formSchemeDefine.getTaskKey()) || StringUtils.isEmpty((String)taskLink.getTitle()) || StringUtils.isEmpty((String)taskLink.getRelatedFormSchemeKey())) continue;
            taskShortInfo.setTaskKey(formSchemeDefine.getTaskKey());
            taskShortInfo.setTaskTitle(taskLink.getDescription());
            taskShortInfo.setFormSchemeKey(formSchemeDefine.getKey());
            taskShortInfo.setFormSchemeTitle(formSchemeDefine.getTitle());
            taskShortInfo.setTaskLinkDefine(taskLink);
            if (TaskShortInfoMap.containsKey(taskShortInfo.getTaskKey())) continue;
            TaskShortInfoMap.put(taskShortInfo.getTaskKey(), taskShortInfo.getTaskTitle());
            taskShortInfos.add(taskShortInfo);
        }
        return taskShortInfos;
    }

    @Override
    public int getPeriodTypeByScheme(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        return formSchemeDefine.getPeriodType().type();
    }
}

