/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FormSchemeExtendPropsDefaultValue
 *  com.jiuqi.nr.definition.util.ServeCodeService
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.FormSchemeExtendPropsDefaultValue;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.EntityChangeInfo;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveSehemeHelper {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    IDesignRestService iDesignRestService;
    @Autowired
    private ServeCodeService serveCodeService;

    public void stepSaveFormScheme(FormSchemeObj formSchemeObj, Map<String, EntityChangeInfo> entityChangeMap) throws Exception {
        String schemeKey = formSchemeObj.getID();
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(schemeKey);
        String taskId = formSchemeObj.getTaskId();
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskId);
        if (taskDefine == null) {
            throw new JQException(null, "\u62a5\u8868\u65b9\u6848" + formSchemeObj.getTitle() + "\u6240\u5c5e\u4efb\u52a1\u4e0d\u5b58\u5728");
        }
        if (formSchemeObj.isIsDeleted()) {
            this.nrDesignTimeController.deleteFormSchemeDefine(schemeKey, true);
        } else if (formSchemeObj.isIsNew() && formSchemeDefine == null) {
            formSchemeDefine = this.nrDesignTimeController.createFormSchemeDefine();
            formSchemeObj.setOwnerLevelAndId(this.serveCodeService.getServeCode());
            this.initFormScheme(taskId, (TaskDefine)taskDefine, formSchemeDefine, formSchemeObj, schemeKey, entityChangeMap);
            this.nrDesignTimeController.insertFormSchemeDefine(formSchemeDefine);
        } else if (formSchemeObj.isIsDirty() && formSchemeDefine != null) {
            DesignFormSchemeDefine oldFormSchemeDefine = this.nrDesignTimeController.createFormSchemeDefine();
            BeanUtils.copyProperties(formSchemeDefine, oldFormSchemeDefine);
            this.initFormScheme(taskId, (TaskDefine)taskDefine, formSchemeDefine, formSchemeObj, schemeKey, entityChangeMap);
            formSchemeDefine.setUpdateTime(new Date());
            this.nrDesignTimeController.updateFormSchemeDefine(formSchemeDefine);
        }
    }

    private void initFormScheme(String taskID, TaskDefine taskDefine, DesignFormSchemeDefine formSchemeDefine, FormSchemeObj formSchemeObj, String schemeKey, Map<String, EntityChangeInfo> entityChangeMap) throws Exception {
        DesignTaskFlowsDefine designTaskFlowsDefine = new DesignTaskFlowsDefine();
        this.dealFSFlowsSettingIsExtend(formSchemeObj, designTaskFlowsDefine);
        this.dealFSEntitiesIsExtend(formSchemeObj, formSchemeDefine, designTaskFlowsDefine, entityChangeMap, schemeKey);
        formSchemeDefine.setFlowsSetting(designTaskFlowsDefine);
        this.dealFSPeriodIsExtend(formSchemeObj, formSchemeDefine);
        this.dealFSMeasureUnitIsExtend(formSchemeObj, formSchemeDefine);
        this.dealFSFillInAutomaticallyDueIsExtend(formSchemeObj, formSchemeDefine);
        formSchemeDefine.setKey(schemeKey);
        formSchemeDefine.setTitle(formSchemeObj.getTitle());
        if (!StringUtils.isNotEmpty((String)formSchemeDefine.getFormSchemeCode())) {
            formSchemeDefine.setFormSchemeCode(OrderGenerator.newOrder());
        }
        formSchemeDefine.setTaskKey(taskID);
        formSchemeDefine.setOrder(formSchemeObj.getOrder());
        formSchemeDefine.setOrder(formSchemeObj.getOrder());
        formSchemeDefine.setOwnerLevelAndId(formSchemeObj.getOwnerLevelAndId());
        formSchemeDefine.setFilterExpression(formSchemeObj.getFilterExpression());
    }

    private void dealFSFlowsSettingIsExtend(FormSchemeObj formSchemeObj, DesignTaskFlowsDefine taskflowDefine) throws JQException {
        try {
            taskflowDefine.setFlowsType(FormSchemeExtendPropsDefaultValue.FLOWS_TYPE_EXTEND_VALUE);
            taskflowDefine.setDesignFlowSettingDefine(FormSchemeExtendPropsDefaultValue.FLOW_SETTING_EXTEND_VALUE);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_108, (Throwable)e);
        }
    }

    private void dealFSEntitiesIsExtend(FormSchemeObj formSchemeObj, DesignFormSchemeDefine formSchemeDefine, DesignTaskFlowsDefine designTaskFlowsDefine, Map<String, EntityChangeInfo> entityChangeMap, String schemeKey) throws JQException {
        try {
            formSchemeDefine.setMasterEntitiesKey(FormSchemeExtendPropsDefaultValue.MASTER_ENTITIES_KEY_EXTEND_VALUE);
            designTaskFlowsDefine.setDesignTableDefines(FormSchemeExtendPropsDefaultValue.REPORT_ENTITIES_EXTEND_VALUE);
            designTaskFlowsDefine.setFilterCondition(FormSchemeExtendPropsDefaultValue.FILTER_CONDITION_EXTEND_VALUE);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_109, (Throwable)e);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void dealFSPeriodIsExtend(FormSchemeObj formSchemeObj, DesignFormSchemeDefine formSchemeDefine) throws JQException {
        try {
            formSchemeDefine.setPeriodType(FormSchemeExtendPropsDefaultValue.PERIOD_TYPE_EXTEND_VALUE);
            formSchemeDefine.setFromPeriod(FormSchemeExtendPropsDefaultValue.FROM_PERIOD_EXTEND_VALUE);
            formSchemeDefine.setToPeriod(FormSchemeExtendPropsDefaultValue.TO_PERIOD_EXTEND_VALUE);
            formSchemeDefine.setPeriodOffset(0);
            formSchemeDefine.setDueDateOffset(0);
            if (!formSchemeObj.getPeriodIsExtend()) return;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_110, (Throwable)e);
        }
    }

    private void dealFSMeasureUnitIsExtend(FormSchemeObj formSchemeObj, DesignFormSchemeDefine formSchemeDefine) throws JQException {
        try {
            if (formSchemeObj.getMeasureUnitIsExtend()) {
                formSchemeDefine.setMeasureUnit(FormSchemeExtendPropsDefaultValue.MEASURE_UNIT_EXTEND_VALUE);
            } else {
                formSchemeDefine.setMeasureUnit(formSchemeObj.getMeasureUnit());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_111, (Throwable)e);
        }
    }

    private void dealFSFillInAutomaticallyDueIsExtend(FormSchemeObj formSchemeObj, DesignFormSchemeDefine formSchemeDefine) throws Exception {
        if (formSchemeObj.getFillInAutomaticallyDueIsExtend()) {
            formSchemeDefine.setFillInAutomaticallyDue(new FillInAutomaticallyDue(FormSchemeExtendPropsDefaultValue.FILL_IN_AUTOMATICALLY_DUE));
        } else {
            formSchemeDefine.setFillInAutomaticallyDue(formSchemeObj.getFillInAutomaticallyDue());
        }
    }
}

