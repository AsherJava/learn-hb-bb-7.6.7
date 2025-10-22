/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.designer.web.facade.FormSchemeLifeObj
 *  com.jiuqi.nr.designer.web.rest.FormSchemeLifeController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.gcreport.reportnotice.web;

import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.designer.web.facade.FormSchemeLifeObj;
import com.jiuqi.nr.designer.web.rest.FormSchemeLifeController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/gcreport/v1/reportNotice"})
public class ReportNoticeController {
    private final Logger logger = LoggerFactory.getLogger(ReportNoticeController.class);
    @Autowired
    private FormSchemeLifeController formSchemeLifeController;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IEntityMetaService entityMetaService;

    @GetMapping(value={"/getFormSchemeId/{taskId}"})
    public Map<String, String> getFormSchemeId(@PathVariable(value="taskId") String taskId) throws JQException {
        HashMap<String, String> key2Value = new HashMap<String, String>();
        try {
            List formSchemeLifeObjList = this.formSchemeLifeController.getAllFormSchemesLife(taskId);
            for (FormSchemeLifeObj formSchemeLifeObj : formSchemeLifeObjList) {
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeLifeObj.getKey());
                PeriodWrapper periodWrapper = TaskPeriodUtils.getCurrentPeriod((int)formScheme.getPeriodType().type());
                PeriodWrapper fromPeriod = new PeriodWrapper(formSchemeLifeObj.getFromPeriod());
                PeriodWrapper toPeriod = new PeriodWrapper(formSchemeLifeObj.getToPeriod());
                if (periodWrapper.getYear() < fromPeriod.getYear() || periodWrapper.getPeriod() < fromPeriod.getPeriod() || periodWrapper.getYear() > toPeriod.getYear() || periodWrapper.getPeriod() > toPeriod.getPeriod()) continue;
                key2Value.put("formSchemeKey", formScheme.getKey());
                return key2Value;
            }
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u5f53\u524d\u65b9\u6848\u5931\u8d25", e);
        }
        return null;
    }
}

