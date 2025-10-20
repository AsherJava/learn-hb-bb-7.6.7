/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.designer.web.facade.FormSchemeLifeObj
 *  com.jiuqi.nr.designer.web.rest.FormSchemeLifeController
 *  com.jiuqi.nr.reminder.web.ReminderController
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryUnitService;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.designer.web.facade.FormSchemeLifeObj;
import com.jiuqi.nr.designer.web.rest.FormSchemeLifeController;
import com.jiuqi.nr.reminder.web.ReminderController;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntermediateLibraryUnitServiceImpl
implements IntermediateLibraryUnitService {
    @Autowired
    private FormSchemeLifeController formSchemeLifeController;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private ReminderController reminderController;

    @Override
    public Map<String, Object> getTaskInfo(String taskKey) {
        try {
            List formSchemeLifeObjList = this.formSchemeLifeController.getAllFormSchemesLife(taskKey);
            for (FormSchemeLifeObj formSchemeLifeObj : formSchemeLifeObjList) {
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeLifeObj.getKey());
                PeriodWrapper periodWrapper = TaskPeriodUtils.getCurrentPeriod((int)formScheme.getPeriodType().type());
                PeriodWrapper fromPeriod = new PeriodWrapper(formSchemeLifeObj.getFromPeriod());
                PeriodWrapper toPeriod = new PeriodWrapper(formSchemeLifeObj.getToPeriod());
                if (periodWrapper.getYear() < fromPeriod.getYear() || periodWrapper.getPeriod() < fromPeriod.getPeriod() || periodWrapper.getYear() > toPeriod.getYear() || periodWrapper.getPeriod() > toPeriod.getPeriod()) continue;
                Map taskInfoMap = this.reminderController.getTaskInfo(formSchemeLifeObj.getKey());
                taskInfoMap.put("periodStr", periodWrapper.toString());
                return taskInfoMap;
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.entity.error"));
        }
        return null;
    }
}

