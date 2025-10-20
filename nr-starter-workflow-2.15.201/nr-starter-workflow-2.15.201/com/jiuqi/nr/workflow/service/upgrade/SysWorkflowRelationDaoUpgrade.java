/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.workflow.service.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;
import javax.sql.DataSource;

public class SysWorkflowRelationDaoUpgrade
implements CustomClassExecutor {
    WorkflowSettingDao wfSettingDao = (WorkflowSettingDao)SpringBeanUtils.getBean(WorkflowSettingDao.class);
    IRunTimeViewController rtvController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);

    public void execute(DataSource dataSource) throws Exception {
        List allTaskDefines = this.rtvController.getAllTaskDefines();
        if (allTaskDefines == null) {
            return;
        }
        for (TaskDefine taskDefine : allTaskDefines) {
            List formSchemeDefines;
            if (taskDefine == null || (formSchemeDefines = this.rtvController.queryFormSchemeByTask(taskDefine.getKey())) == null) continue;
            for (FormSchemeDefine formScheme : formSchemeDefines) {
                if (formScheme == null) continue;
                this.wfSettingDao.updateUnBindResultByFormScheme(formScheme.getKey(), formScheme.getDw());
            }
        }
    }
}

