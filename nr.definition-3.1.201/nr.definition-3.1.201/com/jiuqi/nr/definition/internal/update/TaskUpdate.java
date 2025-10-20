/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.definition.internal.update.EntityViewCache;
import com.jiuqi.nr.definition.internal.update.dao.EntityViewDefineUp;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class TaskUpdate {
    @Autowired
    private RunTimeTaskDefineDao taskDao;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;
    @Autowired
    private IRuntimeFormSchemeService formSchemeService;
    @Autowired
    private RunTimeFormSchemeDefineDao formSchemeDao;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Transactional(rollbackFor={Exception.class})
    public void updateTask(TaskDefine taskDefine, EntityViewCache cache) throws Exception {
        String designTableDefines;
        TaskFlowsDefine flowsSetting;
        String dims;
        String dateTime;
        EntityViewDefineUp periodView;
        RunTimeTaskDefineImpl impl = (RunTimeTaskDefineImpl)taskDefine;
        String dw = taskDefine.getDw();
        EntityViewDefineUp viewDefine = cache.getValue(dw);
        if (viewDefine != null) {
            impl.setDw(viewDefine.getEntityId());
            impl.setFilterExpression(viewDefine.getRowFilterExpression());
        }
        if ((periodView = cache.getValue(dateTime = taskDefine.getDateTime())) != null) {
            impl.setDatetime(PeriodUtils.removeSuffix((String)periodView.getEntityId()));
        }
        if (StringUtils.hasLength(dims = taskDefine.getDims())) {
            ArrayList<String> entityIds = new ArrayList<String>();
            for (String view : dims.split(";")) {
                EntityViewDefineUp dimView = cache.getValue(view);
                if (dimView == null) continue;
                entityIds.add(dimView.getEntityId());
            }
            if (!entityIds.isEmpty()) {
                impl.setDims(String.join((CharSequence)";", entityIds));
            }
        }
        if ((flowsSetting = taskDefine.getFlowsSetting()) != null && StringUtils.hasLength(designTableDefines = flowsSetting.getDesignTableDefines())) {
            ArrayList<String> entityIds = new ArrayList<String>();
            for (String view : designTableDefines.split(";")) {
                EntityViewDefineUp dimView = cache.getValue(view);
                if (dimView == null) continue;
                if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(dimView.getEntityId())) {
                    entityIds.add(PeriodUtils.removeSuffix((String)dimView.getEntityId()));
                    continue;
                }
                entityIds.add(dimView.getEntityId());
            }
            if (!entityIds.isEmpty()) {
                DesignTaskFlowsDefine flowsSetting1 = (DesignTaskFlowsDefine)flowsSetting;
                flowsSetting1.setDesignTableDefines(String.join((CharSequence)";", entityIds));
                RunTimeBigDataTable runTimeBigDataTable = this.bigDataDao.queryigDataDefine(taskDefine.getKey(), "FLOWSETTING");
                if (runTimeBigDataTable != null) {
                    runTimeBigDataTable.setData(DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(flowsSetting1));
                    this.bigDataDao.update(runTimeBigDataTable);
                }
            }
        }
        this.taskDao.update(impl);
        List<FormSchemeDefine> formSchemeDefines = this.formSchemeService.queryFormSchemeByTask(taskDefine.getKey());
        if (formSchemeDefines == null || formSchemeDefines.isEmpty()) {
            return;
        }
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            String fDims;
            EntityViewDefineUp value;
            String fDateTime;
            EntityViewDefineUp value2;
            String fDw = formSchemeDefine.getDw();
            if (StringUtils.hasLength(fDw) && (value2 = cache.getValue(fDw)) != null) {
                ((RunTimeFormSchemeDefineImpl)formSchemeDefine).setDw(value2.getEntityId());
                ((RunTimeFormSchemeDefineImpl)formSchemeDefine).setFilterExpression(value2.getRowFilterExpression());
            }
            if (StringUtils.hasLength(fDateTime = formSchemeDefine.getDateTime()) && (value = cache.getValue(fDateTime)) != null) {
                ((RunTimeFormSchemeDefineImpl)formSchemeDefine).setDatetime(PeriodUtils.removeSuffix((String)value.getEntityId()));
            }
            if (!StringUtils.hasLength(fDims = formSchemeDefine.getDims())) continue;
            ArrayList<String> entityIds = new ArrayList<String>();
            for (String view : fDims.split(";")) {
                EntityViewDefineUp dimView = cache.getValue(view);
                if (dimView == null) continue;
                entityIds.add(dimView.getEntityId());
            }
            if (entityIds.isEmpty()) continue;
            ((RunTimeFormSchemeDefineImpl)formSchemeDefine).setDims(String.join((CharSequence)";", entityIds));
        }
        this.formSchemeDao.update(formSchemeDefines.toArray());
    }
}

