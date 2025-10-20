/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignTaskDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.service.DesignFormSchemeDefineService;
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
public class DesTaskUpdate {
    @Autowired
    private DesignTaskDefineDao taskDefineDao;
    @Autowired
    private DesignBigDataTableDao bigDataDao;
    @Autowired
    private DesignFormSchemeDefineService formSchemeService;
    @Autowired
    private DesignFormSchemeDefineDao formSchemeDefineDao;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Transactional(rollbackFor={Exception.class})
    public void updateTask(DesignTaskDefine taskDefine, EntityViewCache cache) throws Exception {
        String designTableDefines;
        TaskFlowsDefine flowsSetting;
        String dims;
        String dateTime;
        EntityViewDefineUp periodView;
        DesignTaskDefineImpl impl = (DesignTaskDefineImpl)taskDefine;
        String dw = taskDefine.getDw();
        EntityViewDefineUp viewDefine = cache.getValue(dw);
        if (viewDefine != null) {
            impl.setDw(viewDefine.getEntityId());
            impl.setFilterExpression(viewDefine.getRowFilterExpression());
        }
        if ((periodView = cache.getValue(dateTime = taskDefine.getDateTime())) != null) {
            impl.setDateTime(PeriodUtils.removeSuffix((String)periodView.getEntityId()));
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
                DesignBigDataTable designBigDataTable = this.bigDataDao.queryigDataDefine(taskDefine.getKey(), "FLOWSETTING");
                if (designBigDataTable != null) {
                    designBigDataTable.setData(DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(flowsSetting1));
                    this.bigDataDao.update(designBigDataTable);
                }
            }
        }
        this.taskDefineDao.update(impl);
        List<DesignFormSchemeDefine> designFormSchemeDefines = this.formSchemeService.queryFormSchemeDefineByTaskKey(taskDefine.getKey());
        if (designFormSchemeDefines == null || designFormSchemeDefines.isEmpty()) {
            return;
        }
        for (DesignFormSchemeDefine formSchemeDefine : designFormSchemeDefines) {
            String fDims;
            EntityViewDefineUp value;
            String fDateTime;
            EntityViewDefineUp value2;
            String fDw = formSchemeDefine.getDw();
            if (StringUtils.hasLength(fDw) && (value2 = cache.getValue(fDw)) != null) {
                formSchemeDefine.setDw(value2.getEntityId());
                formSchemeDefine.setFilterExpression(value2.getRowFilterExpression());
            }
            if (StringUtils.hasLength(fDateTime = formSchemeDefine.getDateTime()) && (value = cache.getValue(fDateTime)) != null) {
                formSchemeDefine.setDateTime(PeriodUtils.removeSuffix((String)value.getEntityId()));
            }
            if (!StringUtils.hasLength(fDims = formSchemeDefine.getDims())) continue;
            ArrayList<String> entityIds = new ArrayList<String>();
            for (String view : fDims.split(";")) {
                EntityViewDefineUp dimView = cache.getValue(view);
                if (dimView == null) continue;
                entityIds.add(dimView.getEntityId());
            }
            if (entityIds.isEmpty()) continue;
            formSchemeDefine.setDims(String.join((CharSequence)";", entityIds));
        }
        this.formSchemeDefineDao.update(designFormSchemeDefines.toArray());
    }
}

