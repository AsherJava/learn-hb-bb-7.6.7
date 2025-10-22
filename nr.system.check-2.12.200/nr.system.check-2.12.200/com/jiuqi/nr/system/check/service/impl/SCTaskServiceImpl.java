/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.system.check.service.impl;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.system.check.model.response.PeriodObj;
import com.jiuqi.nr.system.check.model.response.SchemeObj;
import com.jiuqi.nr.system.check.model.response.TaskObj;
import com.jiuqi.nr.system.check.service.SCFormSchemeService;
import com.jiuqi.nr.system.check.service.SCTaskService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCTaskServiceImpl
implements SCTaskService {
    private static final Logger log = LoggerFactory.getLogger(SCTaskServiceImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewService;
    @Autowired
    private SCFormSchemeService scFormSchemeService;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Override
    public List<TaskObj> getAllRunTimeTasks() throws Exception {
        List taskDefines = this.iRunTimeViewService.getAllTaskDefines();
        ArrayList<TaskObj> taskObjs = new ArrayList<TaskObj>();
        if (taskDefines != null) {
            for (TaskDefine taskDefine : taskDefines) {
                try {
                    String taskKey = taskDefine.getKey();
                    TaskObj taskObj = new TaskObj(taskDefine);
                    List<SchemeObj> schemeObjs = this.scFormSchemeService.getAllSchemesInTask(taskKey);
                    taskObj.setSchemeObjs(schemeObjs);
                    IPeriodEntity iPeriodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
                    PeriodObj periodObj = new PeriodObj();
                    PeriodType periodType = iPeriodEntity.getPeriodType();
                    if (periodType == PeriodType.CUSTOM) {
                        periodObj.setPeriodType(periodType.type());
                        periodObj.setDefaultPeriod(false);
                        List iPeriodRowList = this.periodEngineService.getPeriodAdapter().getPeriodProvider(iPeriodEntity.getKey()).getPeriodItems();
                        periodObj.setCustomPeriodDataList(iPeriodRowList);
                    } else {
                        periodObj.setPeriodType(periodType.type());
                        periodObj.setDefaultPeriod(true);
                    }
                    taskObj.setPeriodObj(periodObj);
                    taskObjs.add(taskObj);
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return taskObjs;
    }
}

